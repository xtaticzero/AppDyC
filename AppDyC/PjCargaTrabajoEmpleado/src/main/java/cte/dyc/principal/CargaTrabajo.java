package cte.dyc.principal;


import cte.dyc.dao.DocumentoJarDAO;
import cte.dyc.dao.DycpServicioDAO;
import cte.dyc.dto.EmpleadoDTO;
import cte.dyc.generico.ExtractorUtil;
import cte.dyc.generico.util.constante.ConstantesDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class CargaTrabajo {
    static final Logger LOG = Logger.getLogger("loggerDyC");

    @Autowired(required = true)
    private DycpServicioDAO dycpServicioDAO;

    @Autowired(required = true)
    private DocumentoJarDAO documentoJarDAO;

    public void setDycpServicioDAO(DycpServicioDAO dycpServicioDAO) {
        this.dycpServicioDAO = dycpServicioDAO;
    }

    public DycpServicioDAO getDycpServicioDAO() {
        return dycpServicioDAO;
    }

    public void setDocumentoJarDAO(DocumentoJarDAO documentoJarDAO) {
        this.documentoJarDAO = documentoJarDAO;
    }

    public DocumentoJarDAO getDocumentoJarDAO() {
        return documentoJarDAO;
    }

    /**
     * @param empleado. EmpleadoDTO[Integer:numEmpleado, String:rfc, String:roles ]
     * @return boolean. <br />[false] En caso de tener solicitudes/casos de compensación pendientes, documentos por aprobar <br/> [true] En caso de no contar con tramites asignados  ]
     */
    public boolean inactivarComisionarRNFDC615(EmpleadoDTO empleado) {
        boolean paso = false;
        try {
            ApplicationContext appContext = new ClassPathXmlApplicationContext(ConstantesDyC.APP_CONTEXT);
            DycpServicioDAO servicioDAO = (DycpServicioDAO)appContext.getBean(ConstantesDyC.DAO_SERVICIO);
            DocumentoJarDAO documentoDAO = (DocumentoJarDAO)appContext.getBean(ConstantesDyC.DAO_DOCUMENTO);
            
            int dictaminador = servicioDAO.consultaDictaminador(empleado);    
            int aprobador = servicioDAO.consultaAprobador(empleado);
            if (ConstantesDyC.CERO != dictaminador ? Boolean.TRUE : Boolean.FALSE) {
                if (ConstantesDyC.CERO == servicioDAO.obtenerSolicitudServicio(dictaminador).size() ? Boolean.TRUE : Boolean.FALSE) {
                    paso = Boolean.TRUE;
                    LOG.info(ConstantesDyC.MENSAJE_T3);
                } else {
                    paso = Boolean.FALSE;
                    LOG.info(ConstantesDyC.MENSAJE_T1);
                }
            } else if (ConstantesDyC.CERO != aprobador ? Boolean.TRUE : Boolean.FALSE) {
                if (ConstantesDyC.CERO == documentoDAO.consultarDocumentoAprobador(aprobador).size() ? Boolean.TRUE : Boolean.FALSE) {
                    paso = Boolean.TRUE;
                    LOG.info(ConstantesDyC.MENSAJE_T3);
                } else {
                    paso = Boolean.FALSE;
                    LOG.info(ConstantesDyC.MENSAJE_T1);
                }
            } else {
                paso = Boolean.TRUE;
                LOG.info(ConstantesDyC.MENSAJE_T2 +
                         ExtractorUtil.ejecutar(empleado));
            }

        } catch (Exception e) {
            LOG.error(ConstantesDyC.TEXTO_ERROR + e);
        }
        return paso;
    }
    
    public static void main(String [] args){
        CargaTrabajo ct = new CargaTrabajo ();
        EmpleadoDTO empleado = new EmpleadoDTO();
        /**empleado.setRfc("LUCL7809147W0");
        empleado.setNumEmpleado(94119);*/
        
        boolean valor = ct.inactivarComisionarRNFDC615(empleado);   
        
        LOG.info(valor);
    }
}
