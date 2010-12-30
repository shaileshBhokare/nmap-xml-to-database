
package no.security.nmap.nmapparser.api;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the no.security.nmap.nmapparser.api package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _StoreScan_QNAME = new QName("http://api.nmapparser.nmap.security.no/", "storeScan");
    private final static QName _StoreScanResponse_QNAME = new QName("http://api.nmapparser.nmap.security.no/", "storeScanResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: no.security.nmap.nmapparser.api
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link HostAddress }
     * 
     */
    public HostAddress createHostAddress() {
        return new HostAddress();
    }

    /**
     * Create an instance of {@link Port }
     * 
     */
    public Port createPort() {
        return new Port();
    }

    /**
     * Create an instance of {@link StoreScan }
     * 
     */
    public StoreScan createStoreScan() {
        return new StoreScan();
    }

    /**
     * Create an instance of {@link StoreScanResponse }
     * 
     */
    public StoreScanResponse createStoreScanResponse() {
        return new StoreScanResponse();
    }

    /**
     * Create an instance of {@link NmapScan }
     * 
     */
    public NmapScan createNmapScan() {
        return new NmapScan();
    }

    /**
     * Create an instance of {@link Host }
     * 
     */
    public Host createHost() {
        return new Host();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StoreScan }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.nmapparser.nmap.security.no/", name = "storeScan")
    public JAXBElement<StoreScan> createStoreScan(StoreScan value) {
        return new JAXBElement<StoreScan>(_StoreScan_QNAME, StoreScan.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StoreScanResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.nmapparser.nmap.security.no/", name = "storeScanResponse")
    public JAXBElement<StoreScanResponse> createStoreScanResponse(StoreScanResponse value) {
        return new JAXBElement<StoreScanResponse>(_StoreScanResponse_QNAME, StoreScanResponse.class, null, value);
    }

}
