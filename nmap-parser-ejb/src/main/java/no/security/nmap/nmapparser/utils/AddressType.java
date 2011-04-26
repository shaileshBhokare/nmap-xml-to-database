
package no.security.nmap.nmapparser.utils;

/**
 * NOT IN USE
 * @author K4rrax
 */
public enum AddressType {
    ipv4("ipv4"),ipv6("ipv6"),mac("mac");

    private String prettyName;

    private AddressType(String prettyName) {
        this.prettyName = prettyName;
    }

    public String getPrettyName() {
        return prettyName;
    }
}
