/*
*  Todos los Derechos Reservados 2014 SAT.
*  Servicio de Administracion Tributaria (SAT).
*l
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.pjplantillador;

import mx.gob.sat.siat.dyc.generico.util.common.SIATException;
import mx.gob.sat.siat.pjplantillador.servicios.GeneradorArchivo;

import org.apache.log4j.Logger;

/**
 * Clase demo de la generacion de documento mediante el plantillador.
 * @author Agustin Romero Mata / Softtek
 */
public class App {
    private static final Logger LOG = Logger.getLogger(App.class);    
    public  static final int ARCHCONFIG     = 0;
    public  static final int ARCHPLANTILLA  = 1;
    public  static final int ARCHGENERADO   = 2;
    public  static final int ARCHCARGADUMMY = 3;
    
    protected App() {
        super();
    }
    
    /**
     * Funcion principal de ejecucion de la generacion del documento.
     * @param args 
     */
    public static void main( String[] args ) {        
        try {
            GeneradorArchivo app = new GeneradorArchivo(args[ARCHCONFIG],
            args[ARCHPLANTILLA],args[ARCHGENERADO]);  
            app.getCargaDummy(args[ARCHCARGADUMMY]);
            int retorno = app.leerConfiguracion(0,"info",5);                                                          
            
            LOG.debug("Volcado de informacion " + app.getDatos());
            app.obtieneListaTagsFaltantes();
            
            if (retorno < 0) {
                LOG.info("No pudo leerse el archivo de configuracion! " + app.getArchConf()); 
                return;
            }
                        
            if (app.generacion() == 0) {
                LOG.info("Generacion de archivo con exito! " + app.getDocDestino());                
            } else {
                LOG.info("No se genero el archivo! " + app.getDocDestino());
            } 
        } catch (Exception e) {
        }
    }
}