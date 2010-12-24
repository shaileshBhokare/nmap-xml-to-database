/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.ngt.tech.security.nmapparser;

/**
 *
 * @author cad
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
