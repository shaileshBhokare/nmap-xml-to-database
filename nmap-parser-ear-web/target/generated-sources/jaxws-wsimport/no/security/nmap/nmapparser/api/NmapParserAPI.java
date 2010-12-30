
package no.security.nmap.nmapparser.api;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.3-b02-
 * Generated source version: 2.1
 * 
 */
@WebService(name = "NmapParserAPI", targetNamespace = "http://api.nmapparser.nmap.security.no/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface NmapParserAPI {


    /**
     * 
     * @param arg0
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "storeScan", targetNamespace = "http://api.nmapparser.nmap.security.no/", className = "no.security.nmap.nmapparser.api.StoreScan")
    @ResponseWrapper(localName = "storeScanResponse", targetNamespace = "http://api.nmapparser.nmap.security.no/", className = "no.security.nmap.nmapparser.api.StoreScanResponse")
    public boolean storeScan(
        @WebParam(name = "arg0", targetNamespace = "")
        NmapScan arg0);

}
