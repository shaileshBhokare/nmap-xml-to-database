package no.security.nmap.nmapparser.utils;

/**
 *
 * @author K4rrax
 */
public enum Protocol {

    TCP("TCP"), UDP("UDP");
    private String prettyName;

    private Protocol(String prettyName) {
        this.prettyName = prettyName;
    }

    public String getPrettyName() {
        return prettyName;
    }
}
