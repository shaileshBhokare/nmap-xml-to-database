
package no.security.nmap.nmapparser.api;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for host complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="host">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="addressList" type="{http://api.nmapparser.nmap.security.no/}hostAddress" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="databaseId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="portList" type="{http://api.nmapparser.nmap.security.no/}port" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="reason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "host", propOrder = {
    "addressList",
    "databaseId",
    "portList",
    "reason",
    "state"
})
public class Host {

    @XmlElement(nillable = true)
    protected List<HostAddress> addressList;
    protected int databaseId;
    @XmlElement(nillable = true)
    protected List<Port> portList;
    protected String reason;
    protected String state;

    /**
     * Gets the value of the addressList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the addressList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAddressList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HostAddress }
     * 
     * 
     */
    public List<HostAddress> getAddressList() {
        if (addressList == null) {
            addressList = new ArrayList<HostAddress>();
        }
        return this.addressList;
    }

    /**
     * Gets the value of the databaseId property.
     * 
     */
    public int getDatabaseId() {
        return databaseId;
    }

    /**
     * Sets the value of the databaseId property.
     * 
     */
    public void setDatabaseId(int value) {
        this.databaseId = value;
    }

    /**
     * Gets the value of the portList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the portList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPortList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Port }
     * 
     * 
     */
    public List<Port> getPortList() {
        if (portList == null) {
            portList = new ArrayList<Port>();
        }
        return this.portList;
    }

    /**
     * Gets the value of the reason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReason(String value) {
        this.reason = value;
    }

    /**
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setState(String value) {
        this.state = value;
    }

}
