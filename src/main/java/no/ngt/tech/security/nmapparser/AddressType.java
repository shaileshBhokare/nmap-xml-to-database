/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package no.ngt.tech.security.nmapparser;

/**
 *
 * @author cad
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
