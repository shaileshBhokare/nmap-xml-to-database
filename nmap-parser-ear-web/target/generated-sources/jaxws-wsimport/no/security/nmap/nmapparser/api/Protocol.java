
package no.security.nmap.nmapparser.api;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for protocol.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="protocol">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="TCP"/>
 *     &lt;enumeration value="UDP"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "protocol")
@XmlEnum
public enum Protocol {

    TCP,
    UDP;

    public String value() {
        return name();
    }

    public static Protocol fromValue(String v) {
        return valueOf(v);
    }

}
