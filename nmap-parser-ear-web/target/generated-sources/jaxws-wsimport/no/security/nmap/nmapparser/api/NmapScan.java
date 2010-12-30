
package no.security.nmap.nmapparser.api;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for nmapScan complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="nmapScan">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="args" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="elapsed" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="hostList" type="{http://api.nmapparser.nmap.security.no/}host" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="time" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "nmapScan", propOrder = {
    "args",
    "elapsed",
    "hostList",
    "time"
})
public class NmapScan {

    protected String args;
    protected double elapsed;
    @XmlElement(nillable = true)
    protected List<Host> hostList;
    protected long time;

    /**
     * Gets the value of the args property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArgs() {
        return args;
    }

    /**
     * Sets the value of the args property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArgs(String value) {
        this.args = value;
    }

    /**
     * Gets the value of the elapsed property.
     * 
     */
    public double getElapsed() {
        return elapsed;
    }

    /**
     * Sets the value of the elapsed property.
     * 
     */
    public void setElapsed(double value) {
        this.elapsed = value;
    }

    /**
     * Gets the value of the hostList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hostList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHostList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Host }
     * 
     * 
     */
    public List<Host> getHostList() {
        if (hostList == null) {
            hostList = new ArrayList<Host>();
        }
        return this.hostList;
    }

    /**
     * Gets the value of the time property.
     * 
     */
    public long getTime() {
        return time;
    }

    /**
     * Sets the value of the time property.
     * 
     */
    public void setTime(long value) {
        this.time = value;
    }

}
