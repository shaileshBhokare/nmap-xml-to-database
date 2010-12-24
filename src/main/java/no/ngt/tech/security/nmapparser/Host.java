package no.ngt.tech.security.nmapparser;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author ChrisAD
 */
public class Host {

    private int databaseId = 0;
    private String state;
    private String reason;
    private List<HostAddress> addressList;
    private List<Port> portList;

    public int getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(int databaseId) {
        this.databaseId = databaseId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<HostAddress> getAddressList() {
        if (addressList == null) {
            addressList = new ArrayList<HostAddress>();
        }
        return addressList;
    }

    public void setAddressList(List<HostAddress> addressList) {
        this.addressList = addressList;
    }

    public List<Port> getPortList() {
        if (portList == null) {
            portList = new ArrayList<Port>();
        }
        return portList;
    }

    public void setPortList(List<Port> portList) {
        this.portList = portList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public String toString() {
        List<String> noinclude = new ArrayList<String>();
        StringBuilder result = new StringBuilder();
        result.append(this.getClass().getName()).append("[");
        for (Field f : this.getClass().getDeclaredFields()) {
            if (!noinclude.contains(f.getName())) {
                try {
                    if (f.get(this) instanceof Collection) {
                        result.append(f.getName()).append(" = ");
                        for (Object o : (List<Object>) f.get(this)) {
                            result.append(o.toString()).append(",\n\r");
                        }
                        result.append(", ");
                    } else {
                        result.append(f.getName()).append(" = ").append(f.get(this)).append(", ");
                    }
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(Host.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Host.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        result.delete(result.length() - 1, result.length());
        result.append("]");
        return result.toString();
    }
}
