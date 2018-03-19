/** Todos los Derechos Reservados 2013 SAT.
* Servicio de Administraci&oacute;n Tributaria (SAT). 
* 
* Este software contiene informaci&oacute;n propiedad exclusiva del SAT considerada 
* Confidencial. Queda totalmente prohibido su uso o divulgaci&oacuten en forma 
* parcial o total.
*/

package mx.gob.sat.siat.dyc.generico.util.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.nio.charset.Charset;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import mx.gob.sat.sgi.ClienteSelladora;
import mx.gob.sat.sgi.SelladoraException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC3;

import org.apache.log4j.Logger;


/** Selladora: Clase que obtiene un sello d&iacute;gital a partir de una cadena.
 * @author  Alfredo Ramirez
 * @version 1.0 Noviembre 2013
 */
public class Selladora implements Serializable {

    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(Selladora.class);

    private String ip;
    private String puerto;

    public Selladora() {
    }

    /**
     * Retorna un sello digital a trav&eacute;s de una instancia a la clase ClienteSelladora.
     *
     * @param  tipo : El tipo de sello que se generar&aacute;.
     * @param  clave : La clave que se codificar&aacute;.
     * @return sello : El sello digital generado.
     */
    public String retornarParametros(String tipo, String clave) throws IOException {
        String sello = null;
        InputStream is = null;
        try {
            Properties prop = new Properties();
            is = new FileInputStream(ConstantesDyC3.SELLADORA);
            prop.load(is);
            ip = prop.getProperty("selladora.ip");
            puerto = prop.getProperty("selladora.puerto");
            sello = generarSello(this.getIp(), this.getPuerto(), tipo, clave);
            is.close();
        } finally {
            try {
                if (is instanceof InputStream) {
                    is.close();
                }
            } catch (Exception e) {
                LOGGER.info(e.getCause());
            }
        }
        return sello;
    }

    /**
     * Genera el sello digital.
     *
     * @param  ip : La ip del servicio al que se invocar&aacute;.
     * @param  puerto : El puerto del servicio al que se invocar&aacute;.
     * @param  tipo : El tipo de sello que se generar&aacute;.
     * @param  clave : La clave que se codificar&aacute;.
     * @return sSello : El sello digital generado.
     */
    private String generarSello(String ip, String puerto, String tipo, String clave) {
        String sSello = null;
        List<String> cadena2 = new ArrayList<String>();
        cadena2.add(ip);
        cadena2.add(puerto);
        cadena2.add(tipo);
        cadena2.add(clave);

        try {
            if (cadena2.size() != ConstantesDyCNumerico.VALOR_4) {
                LOGGER.debug("Argumentos: " + cadena2.size());
                LOGGER.debug("Uso : IP Puerto 1|2|3 cadena");
            }
            int puertoServer = Integer.valueOf(cadena2.get(1)).intValue();
            ClienteSelladora sello = new ClienteSelladora(cadena2.get(0), puertoServer);
            if (sello.conectaSelladora()) {
                if (cadena2.get(2).compareTo("1") == 0) {
                    sSello =
                            sello.solSello(cadena2.get(ConstantesDyCNumerico.VALOR_3).getBytes(Charset.forName("UTF-8")),  Boolean.TRUE);
                } else if (cadena2.get(2).compareTo("2") == 0) {
                    sello.solNumSerieSelladora();
                } else if (cadena2.get(2).compareTo("3") == 0 || cadena2.get(2).compareTo("5") == 0) {
                    sSello =
                            sello.solSello(cadena2.get(ConstantesDyCNumerico.VALOR_3).getBytes(Charset.forName("UTF-8")),  Boolean.FALSE);
                } else {
                    LOGGER.debug("Opción inválida.");
                }
                sello.desconectaSelladora();
            } else {
                LOGGER.error("No es posible lograr la conexion. Verifique sus permisos");
            }
        } catch (SelladoraException ex) {
            LOGGER.error("No es posible lograr la conexion con selladora:" + ex.getMessage() , ex);
        }
        return sSello;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }

    public String getPuerto() {
        return puerto;
    }

}
