package mx.gob.sat.siat.dyc.comunica.service.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.mat.dyc.controlsaldos.service.AfectarSaldosXDevolucionesService;
import mx.gob.sat.siat.dyc.comunica.service.EjecutaAccDocService;
import mx.gob.sat.siat.dyc.comunica.util.constante.Constantes;
import mx.gob.sat.siat.dyc.comunica.util.recurso.MetodosGenerales;
import mx.gob.sat.siat.dyc.comunica.util.recurso.ReglasNegocio;
import mx.gob.sat.siat.dyc.controlsaldos.service.icep.AfectarSaldosXCompensacionesService;
import mx.gob.sat.siat.dyc.dao.compensacion.IDycpCompensacionDAO;
import mx.gob.sat.siat.dyc.dao.documento.DyctDocumentoDAO;
import mx.gob.sat.siat.dyc.dao.regsolicitud.DycpSolicitudDAO;
import mx.gob.sat.siat.dyc.dao.resolucion.DyctResolCompDAO;
import mx.gob.sat.siat.dyc.dao.resolucion.DyctResolucionDAO;
import mx.gob.sat.siat.dyc.dao.tiposerv.IDycpServicioDAO;
import mx.gob.sat.siat.dyc.dao.tipotramite.DyccTipoTramiteDAO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctResolCompDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctResolucionDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.AprobarDocumentoDTO;
import mx.gob.sat.siat.dyc.gestionsol.service.administrarsolicitudes.AdministrarSolicitudesService;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Jesus Alfredo Hernandez Orozco
 */
@Service(value = "ejecutaAccDocService")
public class EjecutaAccDocServiceImpl implements EjecutaAccDocService {
    
    private static final Logger LOGGER = Logger.getLogger(EjecutaAccDocServiceImpl.class);
    
    @Autowired
    private DyctResolucionDAO dyctResolucionDAO;

    @Autowired
    private IDycpServicioDAO iDycpServicioDAO;

    @Autowired
    private DyctDocumentoDAO dyctDocumentoReqDAO;

    @Autowired(required = true)
    private DycpSolicitudDAO dycpSolicitudDAO;

    @Autowired
    private DyccTipoTramiteDAO dyccTipoTramiteDAO;

    @Autowired
    private IDycpCompensacionDAO iDycpCompensacionDAO;
    
    @Autowired
    private DyctResolCompDAO dyctResolCompDAO;

    @Autowired
    private AfectarSaldosXDevolucionesService afectarSaldosXDevolucionesService;
    
    @Autowired
    private AfectarSaldosXCompensacionesService afectarSaldosXCompensacionesService;
                  
    @Autowired
    private AdministrarSolicitudesService administrarSolicitudesService;
    
    /**
     * Obtiene los datos referentes al tramite, su reolucion y el documento
     * 
     *verdadero si procesa correctamente el caso de uso, falso en caso contrario.
     * 
     * @param aprobarDocumentoDTO
     * @throws mx.gob.sat.siat.dyc.util.common.SIATException
     */
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void consultarTramiteyDocumento(AprobarDocumentoDTO aprobarDocumentoDTO) throws SIATException {
        LOGGER.info("JAHO - ENTRA A EJECUTAR ACCIONES APROBAR DOCUMENTO.");
        dyctDocumentoReqDAO.actualizarCadenaSelloYFirma(aprobarDocumentoDTO.getNumControlDoc(), aprobarDocumentoDTO.getCadenaOriginal(), aprobarDocumentoDTO.getSello(), aprobarDocumentoDTO.getFirmaDigital());
        //OBTIENE EL TIPO DE TRAMITE AL QUE PERTENECE:
        DycpServicioDTO objetoServicio = iDycpServicioDAO.encontrar(aprobarDocumentoDTO.getNumControlTramite());

        //OBTIENE EL DOCUMENTO:
        DyctDocumentoDTO objetoDocumento = dyctDocumentoReqDAO.consultarXNumControlDoc(aprobarDocumentoDTO.getNumControlDoc());
        actualizarEstadoRequerimientoyTramite(objetoServicio, objetoDocumento, aprobarDocumentoDTO);
    }


    /**
     * EN CASO DE QUE SEA UNA RESOLUCION, VALIDA EL TIPO DE RESOLUCION, INCLUYE LA INTEGRACION DEL EXPEDIENTE Y CAMBIA EL ESTADO DE LA RESOLUCION.
     * EN CASO DE QUE SEA UN REQUERIMIENTO, CAMBIA EL ESTATUS DEL REQUERIMIENTO A AUTORIZADO Y EL ESTATUS DEL TRAMITE A REQUERIDO.
     *
     * @param objetoServicio Contiene todos los datos del servicio.
     * @param objetoDocumento Contiene todos los datos del requerimiento.
     */
    private boolean actualizarEstadoRequerimientoyTramite(DycpServicioDTO objetoServicio,
                                                          DyctDocumentoDTO objetoDocumento,
                                                          AprobarDocumentoDTO aprobarDocumentoDTO) throws SIATException {
        
        boolean banderaRNFDC955 = false;
        boolean banderActualizarERT = false;
        boolean banderaTiposDocumentos = false;
        String idsAutorizacion = "";
        String idsRNFDC955 = "";

        List<Integer> listaResolucionAutorizacion = hacerListaResolucionAutorizacion();
        List<Integer> listaRNFDC955 = crearListaRN955();

        MetodosGenerales objetoMetodosGenerales = new MetodosGenerales();

        idsAutorizacion = objetoMetodosGenerales.generarClausulaIN(listaResolucionAutorizacion);
        idsRNFDC955     = objetoMetodosGenerales.generarClausulaIN(listaRNFDC955);
        banderaRNFDC955 = dyctDocumentoReqDAO.buscarDocumentoEnPlantillas(objetoDocumento.getNumControlDoc(), idsRNFDC955)>0;

        //4.0 VALIDA QUE SEA UNA RESOLUCION, Q ESTE DENTRO DE LOS TIPOS (8, 52, 14, 58), Q TENGA UNA MARCA DE RESOLUCION AUTOMATICA Y FIRMA DE DOCUMENTO FIEL:
        if (objetoDocumento.getDyccTipoDocumentoDTO().getIdTipoDocumento() == Constantes.IDRESOLUCION &&
            dyctDocumentoReqDAO.buscarAutorizacionTotalOParcial(objetoServicio.getNumControl(), Constantes.IDAPROBADA, idsAutorizacion) > 0 
            && dyccTipoTramiteDAO.verificaSiEsResolucionAutomatica(objetoServicio.getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getIdTipoTramite()) 
            && objetoDocumento.getDyccTipoFirmaDTO().getIdTipoFirma()==Constantes.FIRMAFIEL) {
            
            //4.1 ACTUALIZAR EL EXPEDIENTE ASOCIADO:

        } else {
            LOGGER.info("4.0 VALIDA LA REGLA DE NEGOCIO RNFDC955");
            banderaTiposDocumentos = objetoDocumento.getDyccTipoDocumentoDTO().getIdTipoDocumento() == Constantes.IDPRIMERREQUERIMIENTO ||
                 objetoDocumento.getDyccTipoDocumentoDTO().getIdTipoDocumento() == Constantes.IDSEGUNDOREQUERIMIENTO;
            
            hacerPasos7y8(banderaTiposDocumentos, objetoDocumento, banderaRNFDC955, objetoServicio);

            //10. CAMBIAR EL ESTADO DEL DOCUMENTO A APROBADO:
            dyctDocumentoReqDAO.actualizarStatusYFirmaXidDocumentoRequerimiento(Constantes.IDAPROBADA, objetoDocumento.getNumControlDoc(), aprobarDocumentoDTO.getFirma());
            if(validarComunicados(objetoDocumento.getDyccMatDocumentosDTO().getIdPlantilla())){
                LOGGER.info("9.1 CAMBIA EL ESTADO DEL TRAMITE A EN_PROCESO");
                iDycpCompensacionDAO.actualizarStatus(Constantes.EN_PROCESO, objetoDocumento.getDycpServicioDTO().getNumControl());
            }
            
        }
        
        if(objetoServicio.getDycaOrigenTramiteDTO().getDycaServOrigenDTO().getDyccTipoServicioDTO().getIdTipoServicio() == Constantes.IDAVISOCOMPENSACION &&
           objetoDocumento.getDyccTipoDocumentoDTO().getIdTipoDocumento() == Constantes.LIQUIDACION)   {
            
            DyctResolCompDTO resolComp = new DyctResolCompDTO();
            DycpCompensacionDTO objComp = new  DycpCompensacionDTO();
            DycpServicioDTO objServ = new  DycpServicioDTO();
            
            objServ.setNumControl(objetoServicio.getNumControl());
            objComp.setDycpServicioDTO(objServ);
            resolComp.setDycpCompensacionDTO(objComp);
            resolComp.setDyccEstResolDTO(mx.gob.sat.siat.dyc.util.constante.Constantes.EstadosResolucion.APROBADA);
            iDycpCompensacionDAO.actualizarStatus(Constantes.IMPROCEDENTE, objetoServicio.getNumControl());
            dyctResolCompDAO.actualizarEstado(resolComp);
        }                                                                                                                                                            

        banderActualizarERT = Boolean.TRUE;
        objetoMetodosGenerales = null;

        return banderActualizarERT;
    }
    
    /**
     * Caso de uso que hace los pasos del 7 al 8 del caso de uso ejecutar acciones aprobar documento.
     *
     * @param banderaTiposDocumentos
     * @param objetoDocumento
     * @param banderaRNFDC955
     * @param objetoServicio
     */
    private void hacerPasos7y8(boolean banderaTiposDocumentos, DyctDocumentoDTO objetoDocumento, boolean banderaRNFDC955, DycpServicioDTO objetoServicio) throws SIATException {
        if ( autorizaDocumentoXPlantilla( objetoDocumento ) ){
            dyctDocumentoReqDAO.actualizarStatusRequerimiento(Constantes.IDAUTORIZADO, objetoDocumento.getNumControlDoc());
        
        } else {
            if (banderaTiposDocumentos && banderaRNFDC955) {
                //7.1 CAMBIA EL ESTADO DEL REQUERIMIENTO A AUTORIZADO:
                dyctDocumentoReqDAO.actualizarStatusRequerimiento(Constantes.IDAUTORIZADO, objetoDocumento.getNumControlDoc());
    
                //7.2 CAMBIA EL ESTADO DEL TRAMITE A REQUERIDO:
                dycpSolicitudDAO.actualizarStatus(Constantes.IDREQUERIDA, objetoServicio.getNumControl());
    
            //8.0
            } else if ((objetoDocumento.getDyccTipoDocumentoDTO().getIdTipoDocumento() == Constantes.IDRESOLUCION 
                    || objetoDocumento.getDyccTipoDocumentoDTO().getIdTipoDocumento() == Constantes.LIQUIDACION) 
                    && banderaRNFDC955) {
                DyctResolucionDTO objetoResolucion = null;
                if(!(objetoServicio.getDycaOrigenTramiteDTO().getDycaServOrigenDTO().getDyccTipoServicioDTO().getIdTipoServicio() == Constantes.CASO_COMPENSACION 
                || objetoServicio.getDycaOrigenTramiteDTO().getDycaServOrigenDTO().getDyccTipoServicioDTO().getIdTipoServicio() == Constantes.AVISO_COMPENSACION)){
                    objetoResolucion=dyctResolucionDAO.consultarDatosResolucionXNumeroControl(objetoDocumento.getDycpServicioDTO().getNumControl());
                }
                
                hacerPasos81a84(objetoServicio, objetoResolucion, objetoDocumento);
                
                //11. CAMBIAR EL ESTADO DEL TRAMITE:
                cambiarElEstadoDelTramite(objetoServicio, objetoResolucion);
                
                //Se agrega la fecha en que la resolucion fue aprobada:
                dyctResolucionDAO.actualizarFechaAprobacion(objetoServicio.getNumControl());
            }
            
            if(validarComunicados(objetoDocumento.getDyccMatDocumentosDTO().getIdPlantilla()) || 
               objetoDocumento.getDyccTipoDocumentoDTO().getIdTipoDocumento() == Constantes.CARTASOLICITUDCONTRIBUYENTE ||
               objetoDocumento.getDyccTipoDocumentoDTO().getIdTipoDocumento() == Constantes.CARTA_CONFIRMACION) {
                dyctDocumentoReqDAO.actualizarStatusRequerimiento(Constantes.IDAUTORIZADO, objetoDocumento.getNumControlDoc());
                
                     
                String idDocReq = administrarSolicitudesService.obtenerNumControlDoc(objetoDocumento.getDycpServicioDTO().getNumControl());
               
                Integer estado = 0;

                if (null != idDocReq) {
                    estado = administrarSolicitudesService.obtenerEstadoReq(idDocReq);
                }
                
                
                if((null != idDocReq && estado == Constantes.IDAUTORIZADO) && 
                   !esRequerimientoCuentaCLABE(objetoDocumento.getDyccMatDocumentosDTO().getIdPlantilla())) {
                    dycpSolicitudDAO.actualizarStatus(Constantes.IDREQUERIDA, objetoServicio.getNumControl());
                } else {
                    dycpSolicitudDAO.actualizarStatus(Constantes.EN_PROCESO, objetoServicio.getNumControl()); 
                }
                
                    
            }
            if (objetoServicio.getDycaOrigenTramiteDTO().getDycaServOrigenDTO().getDyccTipoServicioDTO().getIdTipoServicio() == Constantes.IDCASOCOMPENSACION && 
                (objetoDocumento.getDyccMatDocumentosDTO().getIdPlantilla()==Constantes.IDREQUERIMIENTODOCUMENTACION74 || 
                objetoDocumento.getDyccMatDocumentosDTO().getIdPlantilla()==Constantes.IDREQUERIMIENTODOCUMENTACION79 || 
                objetoDocumento.getDyccMatDocumentosDTO().getIdPlantilla()==Constantes.IDREQUERIMIENTODOCUMENTACION134)) {
                iDycpCompensacionDAO.actualizarStatus(Constantes.REQUERIDA, objetoDocumento.getDycpServicioDTO().getNumControl());
            }
        }
    }
    
    private boolean autorizaDocumentoXPlantilla ( DyctDocumentoDTO objetoDocumento ){
        Integer idPlantilla = objetoDocumento.getDyccMatDocumentosDTO().getIdPlantilla();

        switch ( idPlantilla ){
            case ConstantesDyCNumerico.VALOR_22  :
            case ConstantesDyCNumerico.VALOR_66  :
            case ConstantesDyCNumerico.VALOR_114 :
            case ConstantesDyCNumerico.VALOR_131 :
            case ConstantesDyCNumerico.VALOR_138:    
                return Boolean.TRUE;
            default:
                return Boolean.FALSE;
        }

    }
    
    /**
     * Valida si el tipo de documento es un comunicado.
     *
     * @param tipoDocumento
     * @return
     */
    private boolean validarComunicados(Integer tipoDocumento) {
        List<Integer> listaDeComunicados = new ArrayList<Integer>();
        listaDeComunicados.add(ConstantesDyCNumerico.VALOR_4);
        listaDeComunicados.add(ConstantesDyCNumerico.VALOR_76);
        listaDeComunicados.add(ConstantesDyCNumerico.VALOR_77);
        listaDeComunicados.add(ConstantesDyCNumerico.VALOR_81);
        listaDeComunicados.add(ConstantesDyCNumerico.VALOR_136);
        return listaDeComunicados.contains(tipoDocumento);
    }
    
    /**
     * Agrega todos los datos para hacer la lista para validar la regla de negocio
     *
     * @return
     */
    private List<Integer> hacerListaResolucionAutorizacion() {
        List<Integer> listaResolucionAutorizacion = new ArrayList<Integer>();
        listaResolucionAutorizacion.add(Constantes.IDRESAUTORIZACIONPARCIAL14);
        listaResolucionAutorizacion.add(Constantes.IDRESAUTORIZACIONPARCIAL58);
        listaResolucionAutorizacion.add(Constantes.IDRESAUTORIZACIONTOTAL08);
        listaResolucionAutorizacion.add(Constantes.IDRESAUTORIZACIONTOTAL52);
        return listaResolucionAutorizacion;
    }
    
    /**
     * Identifica si el documento es un requerimiento de cuenta CLABE a trav&eacute;s del ID de la plantilla.
     *
     * @param idPlantilla
     * @return
     */
    private boolean esRequerimientoCuentaCLABE(int idPlantilla) {
        boolean bandera1=idPlantilla==ConstantesDyCNumerico.VALOR_22 || idPlantilla==ConstantesDyCNumerico.VALOR_66;
        boolean bandera2=idPlantilla==ConstantesDyCNumerico.VALOR_114 || idPlantilla==ConstantesDyCNumerico.VALOR_131;
        return bandera1 && bandera2;
    }

    /**
     * Realiza los pasos del punto 8.1 al 8.4 del caso EjecutarAccionesAprobarDocumento
     *
     * @param objetoServicio
     * @param objetoResolucion
     * @param objetoDocumentoReq
     * @throws SIATException
     */
    private void hacerPasos81a84(DycpServicioDTO objetoServicio, DyctResolucionDTO objetoResolucion,
                                 DyctDocumentoDTO objetoDocumentoReq) 
                                 throws SIATException {
        
        //8.2
        if (objetoServicio.getDycaOrigenTramiteDTO().getDycaServOrigenDTO().getDyccTipoServicioDTO().getIdTipoServicio() == Constantes.IDSOLICITUDDEVOLUCION ||
            objetoServicio.getDycaOrigenTramiteDTO().getDycaServOrigenDTO().getDyccTipoServicioDTO().getIdTipoServicio() == Constantes.IDDEVOLUCIONAUTOMATICA) {
        
            //8.1 CAMBIAR LA RESOLUCION A APROBADA
            LOGGER.info("JAHO - Actualiza el estatus de la resolucion");        
            dyctResolucionDAO.actualizarEstResol(Constantes.IDAPROBADA, objetoResolucion.getDycpSolicitudDTO().getNumControl());
            
            LOGGER.info("JAHO - Entra al subflujo 1");        
            sf1(objetoDocumentoReq, objetoResolucion);
            
        //8.3
        } else if(objetoServicio.getDycaOrigenTramiteDTO().getDycaServOrigenDTO().getDyccTipoServicioDTO().getIdTipoServicio() == Constantes.CASO_COMPENSACION 
                || objetoServicio.getDycaOrigenTramiteDTO().getDycaServOrigenDTO().getDyccTipoServicioDTO().getIdTipoServicio() == Constantes.AVISO_COMPENSACION) {
            afectarSaldosXCompensacionesService.afectarXLiquidacion(objetoDocumentoReq.getNumControlDoc());
        }
    }

    /**
     * Actualiza el estado del tramite validando la regla de negocio RNFDC957
     * Esta regla consiste en que a partir de el tipo de resolucion actualizara estado del tramite.
     *
     * @param objetoServicio
     */
    private void cambiarElEstadoDelTramite(DycpServicioDTO objetoServicio, DyctResolucionDTO objetoResolucion) throws SIATException {

        Integer estadoTramite = 0;
        ReglasNegocio objetoReglas = new ReglasNegocio();

        if (objetoServicio.getDycaOrigenTramiteDTO().getDycaServOrigenDTO().getDyccTipoServicioDTO().getIdTipoServicio() == Constantes.IDSOLICITUDDEVOLUCION  ||
                objetoServicio.getDycaOrigenTramiteDTO().getDycaServOrigenDTO().getDyccTipoServicioDTO().getIdTipoServicio() == Constantes.IDDEVOLUCIONAUTOMATICA) {
            estadoTramite =
                    objetoReglas.validarReglaNegocioRNFDC957(objetoResolucion.getDyccTipoResolDTO().getIdTipoResol());
            if (estadoTramite > 0) {
                dycpSolicitudDAO.actualizarStatus(estadoTramite, objetoServicio.getNumControl());
            }

        }
        objetoReglas = null;
    }

    /**
     * Ejecuta el sub flujo uno.
     *
     * @param objetoDocumentoReq documento de requerimiento
     */
    private void sf1(DyctDocumentoDTO objetoDocumentoReq, DyctResolucionDTO objetoResolucion) throws SIATException {
        String ids = "";
        List<Integer> listaRN956 = new ArrayList<Integer>();
        MetodosGenerales objetoMetodosGenerales = new MetodosGenerales();

        listaRN956.add(Constantes.IDRESOLUCIONDEVOLUCION05);
        listaRN956.add(Constantes.IDRESOLUCIONDEVOLUCION049);
        listaRN956.add(Constantes.IDRESAUTORIZACIONTOTAL08);
        listaRN956.add(Constantes.IDRESAUTORIZACIONTOTAL52);
        listaRN956.add(Constantes.IDRESAUTORIZACIONPARCIAL11);
        listaRN956.add(Constantes.IDRESAUTORIZACIONPARCIAL55);
        listaRN956.add(Constantes.IDRESAUTORIZACIONPARCIAL14);
        listaRN956.add(Constantes.IDRESAUTORIZACIONPARCIAL58);
        listaRN956.add(Constantes.IDRESOLUCIONNEGATIVA17);
        listaRN956.add(Constantes.IDRESOLUCIONNEGATIVA61);
        listaRN956.add(Constantes.IDRESOLUCIODESISTIMIENTO18);
        listaRN956.add(Constantes.IDRESOLUCIODESISTIMIENTO62);
        listaRN956.add(Constantes.IDRESOLUCIODESISTIMIENTO19);
        listaRN956.add(Constantes.IDRESOLUCIODESISTIMIENTO63);
        listaRN956.add(Constantes.IDRESOLUCIODESISTIMIENTO20);
        listaRN956.add(Constantes.IDRESOLUCIODESISTIMIENTO64);
        listaRN956.add(Constantes.IDRESOLUCIONLIQUIDACION21);
        listaRN956.add(Constantes.IDRESOLUCIONSENTENCIA24);
        listaRN956.add(Constantes.IDRESOLUCIONSENTENCIA72);
        listaRN956.add(Constantes.IDRESOLUCIONSENTENCIA25);
        listaRN956.add(Constantes.IDRESOLUCIONSENTENCIA73);
        listaRN956.add(Constantes.IDLIQUIDACIONCOMPENSACION78);
        listaRN956.add(Constantes.IDLIQUIDACIONCOMPENSACION83);
        listaRN956.add(Constantes.IDRESOLUCIONAUTORIZADA68);
        listaRN956.add(Constantes.IDRESOLUCIONAUTORIZADA69);
        listaRN956.add(Constantes.IDRESOLUCIONAUTORIZADA70);
        listaRN956.add(Constantes.IDRESOLUCIONAUTORIZADA71);
        listaRN956.add(Constantes.NEGADA_95);
        listaRN956.add(ConstantesDyCNumerico.VALOR_104);
        listaRN956.add(ConstantesDyCNumerico.VALOR_105);
        listaRN956.add(ConstantesDyCNumerico.VALOR_106);
        listaRN956.add(ConstantesDyCNumerico.VALOR_107);
        listaRN956.add(ConstantesDyCNumerico.VALOR_108);
        listaRN956.add(ConstantesDyCNumerico.VALOR_109);
        listaRN956.add(ConstantesDyCNumerico.VALOR_110);
        listaRN956.add(ConstantesDyCNumerico.VALOR_111);
        listaRN956.add(ConstantesDyCNumerico.VALOR_112);
        listaRN956.add(ConstantesDyCNumerico.VALOR_113);
        listaRN956.add(ConstantesDyCNumerico.VALOR_128);
        listaRN956.add(ConstantesDyCNumerico.VALOR_129);
        listaRN956.add(ConstantesDyCNumerico.VALOR_130);
        listaRN956.add(ConstantesDyCNumerico.VALOR_123);
        listaRN956.add(ConstantesDyCNumerico.VALOR_124);
        listaRN956.add(ConstantesDyCNumerico.VALOR_125);
        listaRN956.add(ConstantesDyCNumerico.VALOR_126);
        listaRN956.add(ConstantesDyCNumerico.VALOR_127);
        listaRN956.add(ConstantesDyCNumerico.VALOR_132);
        listaRN956.add(ConstantesDyCNumerico.VALOR_133);
        
        ids = objetoMetodosGenerales.generarClausulaIN(listaRN956);

        if (dyctDocumentoReqDAO.buscarDocumentoEnPlantillas(objetoDocumentoReq.getNumControlDoc(), ids) > 0) {
            sf2(objetoDocumentoReq.getNumControlDoc(), objetoResolucion);
        }

        objetoMetodosGenerales = null;
    }
    
    /**
     * Ejecuta el subflujo dos
     *
     * @param numControlDocumento Numero de control de documento.
     */
    @Transactional(propagation = Propagation.MANDATORY)
    private void sf2(String numControlDocumento, DyctResolucionDTO objetoResolucion) throws SIATException {
        if(objetoResolucion.getDyccTipoResolDTO().getIdTipoResol().equals(ConstantesDyCNumerico.VALOR_5)) {
            afectarSaldosXDevolucionesService.afectarXDesistimiento(numControlDocumento);
        } else {
            afectarSaldosXDevolucionesService.afectarXResolucion(numControlDocumento);
        }
    }

    /**
     * Crea la lista de IDs a validar para la regla de negocio RNFDC955
     *
     * @return
     */
    private List<Integer> crearListaRN955() {
        List<Integer> lista955 = new ArrayList<Integer>();
        lista955.add(ConstantesDyCNumerico.VALOR_1 );
        lista955.add(ConstantesDyCNumerico.VALOR_2 );
        lista955.add(ConstantesDyCNumerico.VALOR_3 );
        lista955.add(ConstantesDyCNumerico.VALOR_4 );
        lista955.add(ConstantesDyCNumerico.VALOR_5 );
        lista955.add(ConstantesDyCNumerico.VALOR_8 );
        lista955.add(ConstantesDyCNumerico.VALOR_11);
        lista955.add(ConstantesDyCNumerico.VALOR_14);
        lista955.add(ConstantesDyCNumerico.VALOR_17);
        lista955.add(ConstantesDyCNumerico.VALOR_18);
        lista955.add(ConstantesDyCNumerico.VALOR_19);
        lista955.add(ConstantesDyCNumerico.VALOR_20);
        lista955.add(ConstantesDyCNumerico.VALOR_21);
        lista955.add(ConstantesDyCNumerico.VALOR_22);
        lista955.add(ConstantesDyCNumerico.VALOR_24);
        lista955.add(ConstantesDyCNumerico.VALOR_25);
        lista955.add(ConstantesDyCNumerico.VALOR_45);
        lista955.add(ConstantesDyCNumerico.VALOR_46);
        lista955.add(ConstantesDyCNumerico.VALOR_49);
        lista955.add(ConstantesDyCNumerico.VALOR_52);
        lista955.add(ConstantesDyCNumerico.VALOR_55);
        lista955.add(ConstantesDyCNumerico.VALOR_58);
        lista955.add(ConstantesDyCNumerico.VALOR_61);
        lista955.add(ConstantesDyCNumerico.VALOR_62);
        lista955.add(ConstantesDyCNumerico.VALOR_63);
        lista955.add(ConstantesDyCNumerico.VALOR_64);
        lista955.add(ConstantesDyCNumerico.VALOR_66);
        lista955.add(ConstantesDyCNumerico.VALOR_68);
        lista955.add(ConstantesDyCNumerico.VALOR_69);
        lista955.add(ConstantesDyCNumerico.VALOR_72);
        lista955.add(ConstantesDyCNumerico.VALOR_73);
        lista955.add(ConstantesDyCNumerico.VALOR_74);
        lista955.add(ConstantesDyCNumerico.VALOR_75);
        lista955.add(ConstantesDyCNumerico.VALOR_76);
        lista955.add(ConstantesDyCNumerico.VALOR_77);
        lista955.add(ConstantesDyCNumerico.VALOR_78);
        lista955.add(ConstantesDyCNumerico.VALOR_79);
        lista955.add(ConstantesDyCNumerico.VALOR_81);
        lista955.add(ConstantesDyCNumerico.VALOR_83);
        lista955.add(ConstantesDyCNumerico.VALOR_95);
        lista955.add(ConstantesDyCNumerico.VALOR_101);
        lista955.add(ConstantesDyCNumerico.VALOR_102);
        lista955.add(ConstantesDyCNumerico.VALOR_104);
        lista955.add(ConstantesDyCNumerico.VALOR_105);
        lista955.add(ConstantesDyCNumerico.VALOR_106);
        lista955.add(ConstantesDyCNumerico.VALOR_107);
        lista955.add(ConstantesDyCNumerico.VALOR_108);
        lista955.add(ConstantesDyCNumerico.VALOR_109);
        lista955.add(ConstantesDyCNumerico.VALOR_110);
        lista955.add(ConstantesDyCNumerico.VALOR_111);
        lista955.add(ConstantesDyCNumerico.VALOR_112);
        lista955.add(ConstantesDyCNumerico.VALOR_113);
        lista955.add(ConstantesDyCNumerico.VALOR_114);
        lista955.add(ConstantesDyCNumerico.VALOR_121);
        lista955.add(ConstantesDyCNumerico.VALOR_122);
        lista955.add(ConstantesDyCNumerico.VALOR_123);
        lista955.add(ConstantesDyCNumerico.VALOR_124);
        lista955.add(ConstantesDyCNumerico.VALOR_125);
        lista955.add(ConstantesDyCNumerico.VALOR_126);
        lista955.add(ConstantesDyCNumerico.VALOR_127);
        lista955.add(ConstantesDyCNumerico.VALOR_128);
        lista955.add(ConstantesDyCNumerico.VALOR_129);
        lista955.add(ConstantesDyCNumerico.VALOR_130);
        lista955.add(ConstantesDyCNumerico.VALOR_131);
        lista955.add(ConstantesDyCNumerico.VALOR_132);
        lista955.add(ConstantesDyCNumerico.VALOR_133);
        lista955.add(ConstantesDyCNumerico.VALOR_134);
        lista955.add(ConstantesDyCNumerico.VALOR_136);
        lista955.add(ConstantesDyCNumerico.VALOR_137);
        
        return lista955;
    }
}