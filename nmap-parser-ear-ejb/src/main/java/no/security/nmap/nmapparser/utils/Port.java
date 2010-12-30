/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package no.security.nmap.nmapparser.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author K4rrax
 */
public class Port {

    private Protocol protocol;
    private int portid;
    private String state;
    private String reason;
    private int reason_ttl;
    private String service;
    private String method;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getPortid() {
        return portid;
    }

    public void setPortid(int portid) {
        this.portid = portid;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getReason_ttl() {
        return reason_ttl;
    }

    public void setReason_ttl(int reason_ttl) {
        this.reason_ttl = reason_ttl;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
                    Logger.getLogger(Port.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Port.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        result.delete(result.length() - 1, result.length());
        result.append("]");
        return result.toString();
    }

}
