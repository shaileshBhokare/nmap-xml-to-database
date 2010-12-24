package no.ngt.tech.security.nmapparser;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.filter.ElementFilter;
import org.jdom.input.SAXBuilder;

public class NmapScanRunner {

    private static final Logger logger = Logger.getLogger(NmapScanRunner.class.getName());
    private final String dbHost = "127.0.0.1";
//    private final String dbHost = "10.0.0.107";
    private final String database = "nmap";
    private final String username = "nmap";
    private final String password = "nmap";

    public static void main(String[] args) {
        NmapScanRunner p = new NmapScanRunner();
        p.run();
    }

    private void run() {
        try {
            SAXBuilder builder = new SAXBuilder();
            Document nmapXml = builder.build(new File("inside.xml"));
            NmapScan nmapScan = new NmapScan();
            String args = nmapXml.getRootElement().getAttributeValue("args");
            nmapScan.setArgs(args);
            Element runstats = nmapXml.getRootElement().getChild("runstats");
            if (runstats != null) {
                Element finished = runstats.getChild("finished");
                if (finished != null) {
                    long time = finished.getAttribute("time").getLongValue();
                    double elapsed = Double.valueOf(finished.getAttribute("elapsed").getValue());
                    nmapScan.setTime(time);
                    nmapScan.setElapsed(elapsed);
                }

                Iterator hostIterator = nmapXml.getDescendants(new ElementFilter("host"));

                while (hostIterator.hasNext()) {
                    Element hostXml = (Element) hostIterator.next();
                    Element hostStatus = hostXml.getChild("status");
                    Host host = new Host();
                    if (hostStatus != null) {
                        host.setState(hostStatus.getAttribute("state").getValue());
                        host.setReason(hostStatus.getAttribute("reason").getValue());
                    }
                    @SuppressWarnings("unchecked")
                    List<Element> addressListXml = hostXml.getChildren("address");
                    if (addressListXml != null) {
                        List<HostAddress> addressList = new ArrayList<HostAddress>();
                        for (Element e : addressListXml) {
                            HostAddress ha = new HostAddress();
                            ha.setAddr(e.getAttribute("addr").getValue());
                            ha.setAddrtype(AddressType.valueOf(e.getAttribute("addrtype").getValue()));
                            String vendor = e.getAttributeValue("vendor");
                            if (vendor != null) {
                                ha.setVendor(vendor);
                            }
                            addressList.add(ha);
                        }
                        host.setAddressList(addressList);
                    }
                    @SuppressWarnings("unchecked")
                    Element portsXml = hostXml.getChild("ports");
                    if (portsXml != null) {
                        List<Port> portList = new ArrayList<Port>();
                        @SuppressWarnings("unchecked")
                        List<Element> portListXml = portsXml.getChildren("port");
                        if (portListXml != null) {
                            for (Element portXml : portListXml) {
                                Port port = new Port();
                                port.setProtocol(Protocol.valueOf(portXml.getAttributeValue("protocol").toUpperCase()));
                                port.setPortid(portXml.getAttribute("portid").getIntValue());
                                Element state = portXml.getChild("state");
                                if (state != null) {
                                    port.setState(state.getAttributeValue("state"));
                                    port.setReason(state.getAttributeValue("reason"));
                                    port.setReason_ttl(state.getAttribute("reason_ttl").getIntValue());
                                }
                                Element service = portXml.getChild("service");
                                if (service != null) {
                                    port.setService(service.getAttributeValue("name"));
                                    port.setMethod(service.getAttributeValue("method"));
                                    //service.getAttribute("conf").getIntValue();
                                }
                                portList.add(port);
                            }
                        }
                        host.setPortList(portList);
                    }
                    nmapScan.getHostList().add(host);
                }
                System.out.println("This scan was run at: " + nmapScan.getTime());
                System.out.println("Elapsed time in seconds: " + nmapScan.getElapsed());
                for (Host h : nmapScan.getHostList()) {
                    System.out.println(h);
                }
                boolean success = storeToDatabase(nmapScan);
                logger.log(Level.FINE, "Store function has run. Success = {0}", success);
            }
        } catch (JDOMException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    private Connection getMysqlConnection(String hostname, String dbname, String username, String password) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Connection result = null;
        String driverName = "com.mysql.jdbc.Driver";
        Class.forName(driverName).newInstance();
        // Create a connection to the database
        String url = "jdbc:mysql://" + hostname + "/" + dbname; // a
        result = DriverManager.getConnection(url, username, password);
        return result;
    }

    boolean storeToDatabase(NmapScan scan) {
        Connection conn = null;
        boolean result = false;
        try {
            conn = getMysqlConnection(dbHost, database, username, password);
            int scanId = storeGeneralScanInformation(conn, scan);
            if (scanId > 0) {
                scan = storeHosts(conn, scan);
                storePorts(conn, scan);
                result = true;
            }
        } catch (ClassNotFoundException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            logger.log(Level.SEVERE, null, ex);
        } finally {
            close(conn);
        }
        return result;
    }

    private int getLastInsertId(Connection conn) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int result = 0;
        try {
            String query = "SELECT LAST_INSERT_ID()";
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                result = rs.getInt(1);
            }
        } finally {
            close(pstmt);
            close(rs);
        }
        return result;
    }

    private List<Integer> storePorts(Connection conn, NmapScan scan) {
        String insertPortQuery = "INSERT INTO open_ports (portnumber, state, reason, protocol, service, method, hostId) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement insertPortStmt = null;
        String portAlreadyExistQuery = "SELECT state, reason, protocol, service, method FROM open_ports WHERE portnumber = ? and hostId = ?";
        PreparedStatement portAlreadyExistStmt = null;
        try {
            insertPortStmt = conn.prepareStatement(insertPortQuery);
            portAlreadyExistStmt = conn.prepareStatement(portAlreadyExistQuery);
            for (Host h : scan.getHostList()) {
                for (Port p : h.getPortList()) {
                    portAlreadyExistStmt.setInt(1, p.getPortid());
                    portAlreadyExistStmt.setInt(2, h.getDatabaseId());
                    ResultSet portAlreadyExistRs = portAlreadyExistStmt.executeQuery();
                    if (portAlreadyExistRs.next()) {
                        //Port already exists. Check if we need to update any of the data
                        String state = portAlreadyExistRs.getString("state");
                        if (state != null && !state.equals(p.getState())) {
                            //TODO: Update state
                        }
                        String reason = portAlreadyExistRs.getString("reason");
                        if (reason != null && !reason.equals(p.getReason())) {
                            //TODO: Update reason
                        }
                        String protocol = portAlreadyExistRs.getString("protocol");
                        if (protocol != null && !protocol.equals(p.getProtocol().getPrettyName())) {
                            //TODO: Update protocol
                        }
                        String service = portAlreadyExistRs.getString("service");
                        if (state != null && !state.equals(p.getState())) {
                            //TODO: Update state
                        }
                        String method = portAlreadyExistRs.getString("method");
                        if (state != null && !state.equals(p.getState())) {
                            //TODO: Update state
                        }
                    } else {
                        //Port does not exist. Lets store it
                        insertPortStmt.setInt(1, p.getPortid());
                        insertPortStmt.setString(2, p.getState());
                        insertPortStmt.setString(3, p.getReason());
                        insertPortStmt.setString(4, p.getProtocol().getPrettyName());
                        insertPortStmt.setString(5, p.getService());
                        insertPortStmt.setString(6, p.getMethod());
                        insertPortStmt.setInt(7, h.getDatabaseId());
                        insertPortStmt.executeUpdate();
                    }

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(NmapScanRunner.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            close(portAlreadyExistStmt);
            close(insertPortStmt);
        }
        return null;
    }

    /**
     * Stores all hosts found in the NmapScan
     * @param conn Database connection
     * @param scan NmapScan
     * @return Number of new hosts added
     */
    private NmapScan storeHosts(Connection conn, NmapScan scan) {
        String hostExistQuery = "SELECT a.vendor, a.type, a.host_id FROM host_address a WHERE a.address = ?";
        PreparedStatement hostExistStmt = null;
        String insertHostQuery = "INSERT INTO host (state, reason) VALUES (?, ?)";
        PreparedStatement insertHostStmt = null;
        String insertAddressQuery = "INSERT INTO host_address (address, vendor, type, host_id) VALUES (?, ?, ?, ?)";
        PreparedStatement insertAddressStmt = null;
        String selectHostQuery = "SELECT id, state, reason FROM host WHERE id = ?";
        PreparedStatement selectHostStmt = null;
        String updateHostQuery = "UPDATE host SET state = ?, reason = ? where id = ?";
        PreparedStatement updateHostStmt = null;
        String lastInsertedIdQuery = "SELECT last_insert_id()";
        PreparedStatement lastInsertStmt = null;
        try {
            hostExistStmt = conn.prepareStatement(hostExistQuery);
            insertHostStmt = conn.prepareStatement(insertHostQuery);
            insertAddressStmt = conn.prepareStatement(insertAddressQuery);
            selectHostStmt = conn.prepareStatement(selectHostQuery);
            updateHostStmt = conn.prepareStatement(updateHostQuery);
            lastInsertStmt = conn.prepareStatement(lastInsertedIdQuery);
            for (Host h : scan.getHostList()) {
                List<HostAddress> hostAddressWithNoHostList = new ArrayList<HostAddress>();
                boolean foundHost = false;
                int hostId = 0;
                for (HostAddress ha : h.getAddressList()) {
                    hostExistStmt.setString(1, ha.getAddr());
                    logger.log(Level.FINER, "Checking if we find host with address {0}", ha.getAddr());
                    ResultSet rs = null;
                    try {
                        rs = hostExistStmt.executeQuery();
                        if (rs.next()) {
                            //Address already points to a host in DB. Check if for some reason we have updated vendor or type
                            logger.log(Level.FINER, "Found host");
                            String vendor = rs.getString("vendor");
                            String type = rs.getString("type");
                            hostId = rs.getInt("host_id");
                            if (vendor == null && ha.getVendor() != null) {
                                //TODO: Update vendor with new information
                            } else if (vendor != null && !vendor.equals(ha.getVendor())) {
                                //TODO: Update vendor
                            }
                            if (!type.equals(ha.getAddrtype().getPrettyName())) {
                                //TODO: Update address type
                            }
                            //We need to keep track that we found this host, incase we find addresses that does not point to this host
                            foundHost = true;
                        } else {
                            //Address did not point to a host. Store the address incase we find another address pointing to this host
                            hostAddressWithNoHostList.add(ha);
                        }
                        //Unsure if this is needed. If it is, we need to make the first if(rs.next()) into a while.
                        if (rs.next()) {
                            throw new UnsupportedOperationException("System does not support one address pointing to two hosts");
                        }
                    } finally {
                        close(rs);
                    }
                }
                if (hostAddressWithNoHostList.size() > 0) {
                    if (foundHost) {
                        if (hostId == 0) {
                            throw new IllegalStateException("Incosistency in database. Host was found in database, but the hostId is 0");
                        }
                        //We already know this host, but did not have this address pointing to it
                        for (HostAddress ha : hostAddressWithNoHostList) {
                            insertAddressOnHost(insertAddressStmt, ha, hostId);
                        }
                        //Check if we need to update the state of this host
                        selectHostStmt.setInt(1, hostId);
                        ResultSet rs = null;
                        try {
                            rs = selectHostStmt.executeQuery();
                            if (rs.next()) {
                                if (!h.getState().equals(rs.getString("state")) || !h.getState().equals(rs.getString("state"))) {
                                    //Updating the host
                                    updateHostStmt.setString(1, h.getState());
                                    updateHostStmt.setString(2, h.getReason());
                                    updateHostStmt.setInt(3, hostId);
                                    updateHostStmt.executeUpdate();
                                }
                            } else {
                                throw new IllegalStateException("Incosistency in database. Host was found in database on a HostAddress, but not in the host table.");
                            }
                        } finally {
                            close(rs);
                        }
                    } else {
                        //New host found. Adding it to database
                        insertHostStmt.setString(1, h.getState());
                        insertHostStmt.setString(2, h.getReason());
                        insertHostStmt.execute();
                        ResultSet lastIdRs = lastInsertStmt.executeQuery();
                        if (lastIdRs.next()) {
                            hostId = lastIdRs.getInt(1);
                        }
                        //Time to add the addresses to this host
                        for (HostAddress ha : hostAddressWithNoHostList) {
                            insertAddressOnHost(insertAddressStmt, ha, hostId);
                        }
                    }
                }
                h.setDatabaseId(hostId);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NmapScanRunner.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            close(hostExistStmt);
            close(insertHostStmt);
            close(insertAddressStmt);
            close(selectHostStmt);
            close(updateHostStmt);
            close(lastInsertStmt);
        }
        return scan;
    }

    private Integer insertAddressOnHost(PreparedStatement insertAddressStmt, HostAddress ha, Integer hostId) throws SQLException {
        insertAddressStmt.setString(1, ha.getAddr());
        insertAddressStmt.setString(2, ha.getVendor());
        insertAddressStmt.setString(3, ha.getAddrtype().getPrettyName());
        insertAddressStmt.setInt(4, hostId);
        return insertAddressStmt.executeUpdate();
    }

    private Integer storeGeneralScanInformation(Connection connection, NmapScan scan) {
        int result = 0;
        try {
            String newScanQuery = "INSERT INTO scans (elapsedtime, runtime, args) VALUES (?, FROM_UNIXTIME(?) ,?)";
            PreparedStatement newScanStmt = connection.prepareStatement(newScanQuery);
            newScanStmt.setDouble(1, scan.getElapsed());
            newScanStmt.setLong(2, scan.getTime());
            newScanStmt.setString(3, scan.getArgs());
            newScanStmt.executeUpdate();
            result = getLastInsertId(connection);
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return result;
    }

    private void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
    }

    private void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
    }

    private void close(PreparedStatement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
    }
}
