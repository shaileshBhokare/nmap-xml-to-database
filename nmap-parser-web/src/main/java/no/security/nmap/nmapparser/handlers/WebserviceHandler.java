/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package no.security.nmap.nmapparser.handlers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import javax.xml.namespace.QName;
import no.security.nmap.nmapparser.api.NmapParserAPI;
import no.security.nmap.nmapparser.api.NmapParserAPIService;

/**
 *
 * @author K4rrax
 */
public class WebserviceHandler {

    private static final String PROPERTIES_FILE = "webservice";

    public static NmapParserAPI getNmapParserAPI() throws MalformedURLException, IOException {
        //TODO: Use Properties insteadof ResourceBundle
        ResourceBundle props = ResourceBundle.getBundle(PROPERTIES_FILE);
        NmapParserAPI port = new NmapParserAPIService(
                new URL(props.getString("nmapparserapi.wsdl.url")),
                new QName(props.getString("nmapparserapi.wsdl.qname.namespace"),
                props.getString("nmapparserapi.wsdl.qname.localpart"))).getNmapParserAPIPort();
        return port;
    }

}
