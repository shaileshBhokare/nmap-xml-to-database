package no.ngt.tech.security.nmapparser;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author cad
 */
public class HostAddress {

    private String addr;
    private AddressType addrtype;
    private String vendor;

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public AddressType getAddrtype() {
        return addrtype;
    }

    public void setAddrtype(AddressType addrtype) {
        this.addrtype = addrtype;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
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
                    Logger.getLogger(HostAddress.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(HostAddress.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        result.delete(result.length() - 1, result.length());
        result.append("]");
        return result.toString();
    }
}
