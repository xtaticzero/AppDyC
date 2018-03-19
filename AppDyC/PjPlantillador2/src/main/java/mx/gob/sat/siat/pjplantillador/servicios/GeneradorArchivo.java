/*
*  Todos los Derechos Reservados 2014 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.pjplantillador.servicios;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBException;
import org.apache.log4j.Logger;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import mx.gob.sat.siat.dyc.generico.util.common.SIATException;
import mx.gob.sat.siat.pjplantillador.dto.Inforem;
import mx.gob.sat.siat.pjplantillador.dto.Items;

/**
 * Clase que genera el documento mediante los archivos de configuracion
 * y la plantilla.
 * @author Agustin Romero Mata / Softtek
 */
public class GeneradorArchivo extends DocumentoBaseServicios {
    private static final Logger LOG = Logger.getLogger(GeneradorArchivo.class);  
    public  static final int EARCHNOENCONTRADO = -4;
    public  static final int EAPIDOCX4J = -1;
    public  static final int EAPIJAXB = -3;
    public  static final int ENOCARGOCONFIGURACION = -5;
    public  static final int EFALTAPIVTAB = -6;
    List<IdentificadorDeTags> tagsConf;
    
    private String                                      archConf;
    private List<Inforem>                               infoDoc;
    private List<Items>                                 infoDocEnv;
    private WordprocessingMLPackage                     wordMLPackage;
    private List<String>                                listaCampos;
    private List<String>                                listaRenglones;
    private Map<String,List<String>>                    mapaRenglones;
    private String                                      tablaActual;    
    private String                                      idConf;
    
    /**
     * Constructor estatico.
     * @param archConf
     * @param archPlantilla
     * @param archGenerado 
     */
    public GeneradorArchivo(String archConf, String archPlantilla,
        String archGenerado) {

        super(archPlantilla, archGenerado);
        this.archConf = archConf;
        
        String retorno = "\n*************************************************************************************\n";
        retorno += "Archivo de Plantilla " + this.getDocTemplate() + '\n';
        retorno += "Archivo de Configuracion " + archConf + '\n';
        retorno += "Archivo de Destino " + this.getDocDestino() + '\n';
        
        LOG.info(retorno);
    }
    
    /**
     * Constructor id de DB.
     * @param idConf
     * @param archGenerado 
     */
    public GeneradorArchivo(String idConf, String archGenerado) {
        super("",archGenerado);        
        this.archConf = "";        
        this.idConf = idConf;
        
        String retorno = "\n*************************************************************************************\n";
        retorno += "Archivo de Plantilla " + this.getDocTemplate() + '\n';
        retorno += "Archivo de Configuracion " + archConf + '\n';
        retorno += "Archivo de Destino " + this.getDocDestino() + '\n';
        
        LOG.info(retorno);
    }
               
    /**
     * Funcion que lee del archivo las configuraciones para el documento.
     * @return 
     */
    public int leerConfiguracion(int conf, String def, int reng) throws SIATException {
        LectorConfiguraciones lector = new LectorConfiguraciones();
                
        if (conf == 0) {            
            infoDoc = lector.obtenerConfiguraciones(this.archConf, this.infoDocEnv);    
        } else {            
            infoDoc = lector.obtenerConfiguraciones(this.archConf, def, reng);
        }
        
        tagsConf = lector.getListaTags();
        
        if (infoDoc == null) {
            return EARCHNOENCONTRADO;
        }
        
        return 0;
    }
    
    /**
     * Funcion principal de generacion de documento.
     * @return 
     */
    public int generacion() throws SIATException {
        int retorno;        
                                       
        try {                                                                                   
            this.wordMLPackage = WordprocessingMLPackage.load(
                new java.io.File(this.getDocTemplate()));
        
            retorno = this.procesoDocumento();
            
            if (retorno < 0) {
                return retorno;
            }                        
            
            if (tablaActual != null) {
                sustitucionTabla(tablaActual);
            }
            
            writeDocxToStream(this.wordMLPackage,this.getDocDestino());
        } catch (Docx4JException e) {
            retorno = EAPIDOCX4J;
            throw new SIATException(e);            
        } catch (IOException e) {            
            retorno = EAPIJAXB;
            throw new SIATException(e);
        }
                
        return retorno;
    }
    
    /**
     * Selecciona los elemento existentes en el documento a sustituir
     */
    private int procesoDocumento() throws SIATException {
        int retorno = 0;
       
        if (infoDoc == null) {            
            retorno = ENOCARGOCONFIGURACION;
        } else {
            for(Inforem item : infoDoc) {                        
                switch(item.getTipo()) {
                case 'I': sustitucionTexto(item);                    
                  break;
                case 'T': valTablaActual(item);                                         
                  break;                    
                default:
                    break;
                }               
            }
        }
        
        return retorno;
    }
    
    /**
     * 
     * @param item
     */
    private void valTablaActual(Inforem item) throws SIATException {
      if (tablaActual == null) {                    
            tablaActual = item.getTag();     
      }
      selTabla(item);
    }
    
    /**
     * Funcion para la sustitucion de texto.
     * @param item
     * @return 
     */
    private int sustitucionTexto(Inforem item) {
        int retorno = 0;
        
        for(int ii = 0; ii < item.getLstarea().size(); ii++) {
            if (item.getContenido().length() > item.getTamano()) {
                item.setContenido(item.getContenido().substring(0,
                    item.getTamano()));
            }            
            
            switch(item.getLstarea().get(ii).intValue()) {
            case LectorConfiguraciones.HEADER:                                 
              replacePlaceholderHeader(wordMLPackage, item.getContenido(),
                item.getTag());                
              break;
            case LectorConfiguraciones.DOCUMENT: 
              replacePlaceholder(wordMLPackage, item.getContenido(), 
                item.getTag());            
              break;
            case LectorConfiguraciones.FOOTER: 
              replacePlaceholderFooter(wordMLPackage, item.getContenido(), 
                item.getTag());                
              break;            
            default:
                break;
            }            
        }
        
        return retorno;
    }

    /**
     * Funcion para la creacion del contenido de una tabla.
     * @param item 
     */
    private void selTabla(Inforem item) throws SIATException {
        if ((listaCampos == null) && (mapaRenglones == null)) {
            mapaRenglones = new HashMap<String,List<String>>();
            listaCampos = new ArrayList<String>();
            listaRenglones = new ArrayList<String>();
        }
        if (mismaTabla(item.getIdTabla())) {
            adicionCampoTabla(item);
        } else {
            sustitucionTabla(tablaActual);
            tablaActual = item.getTag();
            mapaRenglones.clear();
            listaCampos.clear();
            listaRenglones.clear();
            adicionCampoTabla(item);
        }
    }
    
    /**
     * Funcion que adiciona el campo de la tabla.
     * @param item 
     */
    private void adicionCampoTabla(Inforem item) {
        if (!existeCampo(item.getTag())) {
            listaCampos.add(item.getTag());
        }
        for (int ii = 0; ii < item.getContenidoList().size(); ii++ ) {
            String itemCont = item.getContenidoList().get(ii);
            if (mapaRenglones.get(String.valueOf(ii)) == null) {
                List<String> campos = new ArrayList<String>();
                campos.add(itemCont);
                mapaRenglones.put(String.valueOf(ii), campos);
                listaRenglones.add(String.valueOf(ii));  
            } else {
                mapaRenglones.get(String.valueOf(ii)).add(itemCont);
            }
        }
    }
    
    /**
     * Funcion para generacion de la tabla.
     * @param tabla
     * @return 
     */
    private int sustitucionTabla(String tabla) throws SIATException {
        int retorno = 0;
        List<Map<String,String>>  renglones;
        
        try  {
            renglones = (ArrayList<Map<String,String>>) reglonesTabla();
            replaceTable(tabla, renglones, this.wordMLPackage);
        } catch (Docx4JException e) {            
            retorno = EAPIDOCX4J;
            throw new SIATException(e);
        } catch (JAXBException e) {            
            retorno = EAPIJAXB;
            throw new SIATException(e);
        } catch (Exception e) {                                    
            retorno = EFALTAPIVTAB;
            throw new SIATException(e);
        }
        
        return retorno;
    }
    
    /**
     * Funcion para ordenar la informacion de los renglones de la tabla.
     * @return 
     */
    private List<Map<String,String>> reglonesTabla() {
        List<Map<String,String>>  renglones = 
            new ArrayList<Map<String,String>>();
        
        for(String renglon : listaRenglones) {
            List<String> renglonData = mapaRenglones.get(renglon);
            Map<String,String> mapa = new HashMap<String,String>();
            int ii = 0;
            for (String campo : renglonData) {
                mapa.put(listaCampos.get(ii), campo);
                ii ++;
            }
            renglones.add(mapa);
        }
        
        return renglones;
    }
    
    /**
     * Funcion para la carga dummy mediante archivo.
     * @param archivoDeCarga 
     */
     public void getCargaDummy(String archivoDeCarga) throws SIATException {
        InputStream inputStream;
        BufferedReader is;
        String inputLine, item1, item2;
        Items item;
        int posicion;
        
        try {
            inputStream = new FileInputStream(archivoDeCarga);
            is = new BufferedReader(new InputStreamReader(inputStream, "UTF8"));           
            if (infoDocEnv == null) {
                infoDocEnv = new ArrayList<Items>();
            }
            while ((inputLine = is.readLine( )) != null) {
                posicion = inputLine.indexOf('=');
                item1 = inputLine.substring(0, posicion);
                item2 = inputLine.substring(posicion+1);
                item = new Items(item1, item2);
                infoDocEnv.add(item);                
            }
            is.close( );
        } catch (IOException e) {
            throw new SIATException(e);      
        }                
    }

    /**
     * Seleccion de la tabla actual.
     * @param idTabla
     * @return 
     */
    public boolean mismaTabla(String idTabla) {
        boolean retorno = Boolean.TRUE;
        
        if (!this.tablaActual.equals(idTabla)) {
            retorno = Boolean.FALSE;        
        }
        
        return retorno;
    }
    
    /**
     * Funcion para la busqueda del campo.
     * @param campoBusq
     * @return 
     */
    public boolean existeCampo(String campoBusq) {
        boolean retorno = Boolean.FALSE;
        
        for (String campo : this.listaCampos) { 
            if (campo.equals(campoBusq)) {
                retorno = Boolean.TRUE;
                break;
            }
        }
                
        return retorno;
    }
    
    /**
     * Funcion que obtiene el nombre 
     * del archivo de configuracion
     * @return 
     */
    public String getArchConf() {    
        return archConf;
    }
    
    /**
     * Funcion que inicializa el nombre 
     * del archivo configurador.
     * @param archConf 
     */
    public void setArchConf(String archConf) {    
        this.archConf = archConf;
    }    
    
    /**
     * Funcion que inicializa la lista de infoDoc
     * @param infoDoc 
     */
    public void setInfoDoc(List<Inforem> infoDoc) {    
        this.infoDoc = (ArrayList<Inforem>)infoDoc;
    }
    
    /**
     * Funcion que obtiene la lista de items
     * @return 
     */
    public List<Inforem> getInfoDoc() {    
        return this.infoDoc;
    }
    
    /**
     * Funcion que obtiene la lista de items
     * @return 
     */
    public List<Items> getInfoDocEnv() {    
        return infoDocEnv;
    }
    
    /**
     * Funcion que inicializa la lista de items
     * @param infoDocEnv 
     */
    public void setInfoDocEnv(List<Items> infoDocEnv) {    
        this.infoDocEnv = infoDocEnv;
    }
    
    /**
     * 
     * @return 
     */
    public String getDatos() {
        String retorno = "\n";
        
        if (infoDoc == null) {
           retorno += "La lista de campos a sustituir es nula \n";
        } else {
            for (Inforem item : infoDoc) {
                retorno += item.toString();
            }
        }
        
        retorno += "*************************************************************************************\n";               
        return retorno;        
    }  
    
    public void obtieneListaTagsFaltantes() {
        for(IdentificadorDeTags item : tagsConf) {
            if (item.getBandera() == 0) {
                LOG.info("EL tag " + item.getTag() + " con identificador " + item.getNumTag() + " No esta siendo inyectado");
            }
        }
    }
}

