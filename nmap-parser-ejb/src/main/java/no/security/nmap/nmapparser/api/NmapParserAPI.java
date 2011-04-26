
package no.security.nmap.nmapparser.api;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.sql.DataSource;
import no.security.nmap.nmapparser.persistence.PersistenceHandler;
import no.security.nmap.nmapparser.utils.NmapScan;

/**
 *
 * @author K4rrax
 */
@WebService
@Stateless
public class NmapParserAPI {

    @Resource(mappedName = "jdbc/mysql")
    private DataSource ds;

    @WebMethod
    public boolean storeScan(NmapScan scan) {
        PersistenceHandler ph = new PersistenceHandler(ds);
        boolean result = ph.storeToDatabase(scan);
        return result;
    }

}
