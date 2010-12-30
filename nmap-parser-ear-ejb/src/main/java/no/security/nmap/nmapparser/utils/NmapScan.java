
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
public class NmapScan {

    private long time;
    private double elapsed;
    private List<Host> hostList;
    private String args;

    public double getElapsed() {
        return elapsed;
    }

    public void setElapsed(double elapsed) {
        this.elapsed = elapsed;
    }


    public List<Host> getHostList() {
        if (hostList == null) {
            hostList = new ArrayList<Host>();
        }
        return hostList;
    }

    public void setHostList(List<Host> hostList) {
        this.hostList = hostList;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }



    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
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
                    Logger.getLogger(NmapScan.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(NmapScan.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        result.delete(result.length() - 1, result.length());
        result.append("]");
        return result.toString();
    }

}
