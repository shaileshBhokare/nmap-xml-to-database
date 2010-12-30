package no.security.nmap.nmapparser.handlers;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import no.security.nmap.nmapparser.api.Host;
import no.security.nmap.nmapparser.api.HostAddress;
import no.security.nmap.nmapparser.api.NmapScan;
import no.security.nmap.nmapparser.api.Port;
import no.security.nmap.nmapparser.api.Protocol;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.filter.ElementFilter;
import org.jdom.input.SAXBuilder;

/**
 *
 * @author K4rrax
 */
public class XmlHandler {

    public static NmapScan parseXML(String xml) throws JDOMException, IOException {
        SAXBuilder builder = new SAXBuilder();
        StringReader r = new StringReader(xml);
        Document nmapXml = builder.build(r);
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
                        ha.setAddrtype(e.getAttribute("addrtype").getValue());
                        String vendor = e.getAttributeValue("vendor");
                        if (vendor != null) {
                            ha.setVendor(vendor);
                        }
                        addressList.add(ha);
                    }
                    host.getAddressList().addAll(addressList);
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
                                port.setReasonTtl(state.getAttribute("reason_ttl").getIntValue());
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
                    host.getPortList().addAll(portList);
                }
                nmapScan.getHostList().add(host);
            }
        }
        return nmapScan;
    }
}
