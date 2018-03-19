package mx.gob.sat.siat.archivoshistorico.service.impl;

import java.io.File;
import java.io.IOException;
import javax.annotation.Resource;

import mx.gob.sat.siat.archivoshistorico.dao.ConsultasHistoricoDao;
import mx.gob.sat.siat.archivoshistorico.service.GenerarArchivosService;
import mx.gob.sat.siat.archivoshistorico.utils.Constantes;
import mx.gob.sat.siat.archivoshistorico.utils.Utils;
import mx.gob.sat.siat.dyc.util.common.SIATException;

import mx.gob.sat.siat.dyc.util.excepcion.DAOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service(value = "generarArchivosService")
public class GenerarArchivosServiceImpl implements GenerarArchivosService {
    public GenerarArchivosServiceImpl() {
        super();
    }
    
    private static final Integer LONGITUD_NUMCONTROL=12;
    private static final Integer LONGITUD_NUMCONTROLDOC=12;
    private static final Logger LOGGER = Logger.getLogger(GenerarArchivosServiceImpl.class);
    
    @Resource(name = "ruta")
    private String rutaDestino;
    
    @Autowired
    private ConsultasHistoricoDao consultasHistoricoDao;

    /**
     * Genera archivos de prueba para hacer testing de este proyecto. 
     *
     * @param parametro 1. Genera registros de prueba. 2. Borra los registros de prueba.
     * @param numeroRegistros N&uacute;mero de registros a crear
     * @throws DAOException Error de DAO
     * @throws SIATException Error de negocio
     */
    @Override
    public void generarArchivosDePueba(Integer parametro, Integer numeroRegistros) throws SIATException {
        
        if(parametro==1) {
            LOGGER.info("GENERA REGISTROS DE PRUEBA.");
            generarInserts(numeroRegistros);
            
        } else if(parametro==2) {
            LOGGER.info("BORRA REGISTROS DE PRUEBA.");
            borrarRegistrosDePrueba();
            
        } else {
            LOGGER.warn("SE HA SELECCIONADO UNA OPCIN ICORRECTA.");
        }
    }
    
    /**
     * Inserta en base de datos los registros de prueba para probar este proyecto.
     *
     * @param numeroRegistros Es el n&uacute;mero m&aacute;ximo de registros a probar.
     * @throws SIATException
     */
    private void generarInserts(Integer numeroRegistros) throws SIATException {
        StringBuilder numcontrol = null;
        StringBuilder rutaTemporal = null;
        
        String numControlTemp="";
        String numControlDocTemp="";
        String query="";
        String rutaDocumento="";
        
        for (int i=1;i<=numeroRegistros; i++) {
            numcontrol   = new StringBuilder();
            rutaTemporal = new StringBuilder();
            numControlTemp    = numcontrol.append("AH").append(Utils.agregarCerosALaIzquierda(String.valueOf(i), LONGITUD_NUMCONTROL)).toString();
            numControlDocTemp = Utils.agregarCerosALaIzquierda(String.valueOf(i), LONGITUD_NUMCONTROLDOC);
            
            LOGGER.info("numControl="+numControlTemp+"\n");
            LOGGER.info("numControlDoc="+numControlDocTemp+"\n");
            rutaDocumento=rutaTemporal.append(Constantes.FILESYSTEM_BASE).append(Constantes.RUTA_INTERMEDIA).append(numControlTemp).append("/gestiondoc").toString();
            
            query="Insert into DYCP_SERVICIO (NUMCONTROL,IDTIPOSERVICIO,NUMEMPLEADODICT,IDORIGENSALDO,IDTIPOTRAMITE,RFCCONTRIBUYENTE,CLAVEADM,BOID) values ('"+numControlTemp+"',1,75324,1,103,'VECH521124LV9',11,'477454887580243546247423004384')";
            try {
                consultasHistoricoDao.insertar(query);
          
            
            query="Insert into DYCP_SOLICITUD (NUMCONTROL,FECHAPRESENTACION,FECHAINICIOTRAMITE,IMPORTESOLICITADO,INFOADICIONAL,DIOTNUMOPERACION,DIOTFECHAPRESENTA,RETENEDORRFC,ALTEXCONSTANCIA,ESCERTIFICADO,IDESTADOSOLICITUD,IDSUBORIGENSALDO,IDSUBTIPOTRAMITE,FECHAFINTRAMITE,IDACTIVIDAD,RESOLAUTOMATICA,IDSALDOICEP,CADENAORIGINAL,SELLODIGITAL) values ('"+numControlTemp+"',to_date('19/10/15','DD/MM/RR'),to_date('19/10/15','DD/MM/RR'),50000,'pagos',null,null,null,null,0,15,33,null,null,null,0,13,'CADENA','SELLO')";
            consultasHistoricoDao.insertar(query);
            
            query="Insert into DYCT_DOCUMENTO (NUMCONTROLDOC,IDTIPODOCUMENTO,NUMCONTROL,URL,FECHAREGISTRO,NOMBREARCHIVO,IDESTADODOC,IDESTADOREQ,IDPLANTILLA,FECHAINIPLAZOLEGAL,FECHAFINPLAZOLEGAL,IDTIPOFIRMA,NUMEMPLEADOAPROB,FOLIONYV,CADENAORIGINAL,SELLODIGITAL,FIRMAELECTRONICA) values ('"+numControlDocTemp+"',1,'"+numControlTemp+"','"+rutaDocumento+"',to_date('19/10/15','DD/MM/RR'),'"+numControlDocTemp+".txt',7,5,1,null,null,2,43667,null,'CADENA','SELLO',null)";
            consultasHistoricoDao.insertar(query);
              } catch (DAOException ex) {
                 LOGGER.error("Error al consultar la base",ex);
                 throw new SIATException(ex.getMensaje(),ex);
            }
            copiarArchivoDePrueba(rutaDocumento+"/"+numControlDocTemp+".txt");
            numcontrol = null;
        }
    }
    
    
    private void borrarRegistrosDePrueba() {
        LOGGER.info("DEBERiA BORRAR COSAS PERO NO HUBO TIEMPO");
    }
    
   
    private void copiarArchivoDePrueba(String archivoACrear) throws SIATException {
        File archivoParaCopiar = new File(rutaDestino);
        File archivoDestino = new File(archivoACrear);
        
        LOGGER.info("JAHO - Archivo a crear: "+archivoACrear);

        try {
            FileUtils.copyFile(archivoParaCopiar, archivoDestino);
        } catch (IOException e) {
            LOGGER.error("error al copiar"+e);
            throw new SIATException("Error al copiar archivos. ",e);
        }
    }
}
