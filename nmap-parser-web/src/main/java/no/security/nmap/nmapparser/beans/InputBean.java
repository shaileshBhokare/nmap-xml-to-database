/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.security.nmap.nmapparser.beans;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.security.nmap.nmapparser.handlers.WebserviceHandler;
import no.security.nmap.nmapparser.handlers.XmlHandler;
import org.jdom.JDOMException;

/**
 *
 * @author K4rrax
 */
public class InputBean {

    private static final Logger logger = Logger.getLogger(InputBean.class.getName());
    private String nmapScanXML;

    /** Creates a new instance of InputBean */
    public InputBean() {
    }

    public String actionPersistScan() {
        if (nmapScanXML != null && !nmapScanXML.isEmpty()) {
            try {

                WebserviceHandler.getNmapParserAPI().storeScan(XmlHandler.parseXML(nmapScanXML));

            } catch (MalformedURLException ex) {
                logger.log(Level.SEVERE, null, ex);
                //TODO: Error Handling (user feedback and so on)
            } catch (IOException ex) {
                logger.log(Level.SEVERE, null, ex);
                //TODO: Error Handling (user feedback and so on)
            } catch (JDOMException ex) {
                Logger.getLogger(InputBean.class.getName()).log(Level.SEVERE, null, ex);
                //TODO: Error Handling (user feedback and so on). XML parsing failed
            }
        } else {
            //TODO: Error Handling (user feedback and so on)
        }
        return null;
    }

    public String getNmapScanXML() {
        return nmapScanXML;
    }

    public void setNmapScanXML(String nmapScanXML) {
        this.nmapScanXML = nmapScanXML;
    }
}
