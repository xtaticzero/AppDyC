package mx.gob.sat.siat.dyc.tesofe.service.impl;

import java.sql.Date;

import java.util.HashMap;
import java.util.Map;

import mx.gob.sat.siat.dyc.comunica.dto.ParametrosSelloDigitalDTO;
import mx.gob.sat.siat.dyc.dao.documento.DyctDocumentoDAO;
import mx.gob.sat.siat.dyc.dao.matriz.DyccMatrizDocDAO;
import mx.gob.sat.siat.dyc.dao.tiposerv.IDycpServicioDAO;
import mx.gob.sat.siat.dyc.dao.usuario.DyccAprobadorDAO;
import mx.gob.sat.siat.dyc.domain.DyccEstadoReqDTO;
import mx.gob.sat.siat.dyc.domain.documento.DyccEstadoDocDTO;
import mx.gob.sat.siat.dyc.domain.documento.DyccMatDocumentosDTO;
import mx.gob.sat.siat.dyc.domain.documento.DyccTipoDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;
import mx.gob.sat.siat.dyc.template.service.TemplateNumberService;
import mx.gob.sat.siat.dyc.tesofe.service.EmitirComunicadosAutService;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.vo.DyccMatrizDocVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.apache.log4j.Logger;

@Service(value = "emitirComunicadosAutService")
public class EmitirComunicadosAutServiceImpl implements EmitirComunicadosAutService {

    private static final Logger LOGGER = Logger.getLogger(EmitirComunicadosAutServiceImpl.class);

    private static final int EMITIDO = 1;
    private static final int ENAPROBACION = 5;
    private static final int IDPLANTILLA22 = 22;
    private static final int IDPLANTILLA66 = 66;
    private static final int IDPLANTILLA114 = 114;
    private static final int IDPLANTILLA131 = 131;
    private static final int IDPLANTILLA138 = 138;
    private static final int SOLICITUDCUENTA = 1;
    private static final int SOLICITUDDEVOLUCION = 1;

    private static final int SOLICITUD_DEVOLUCION_AUT = 2;

    private static final String NUMERODOCUMENTO = "NUMERODOCUMENTO";
    private static final String URL = "url";
    private static final String NOMBREARCHIVO = "nombreArchivo";

    @Autowired
    private DyctDocumentoDAO dyctDocumentoDAO;

    @Autowired
    private DyccMatrizDocDAO dyccMatrizDocDAO;

    @Autowired(required = true)
    private TemplateNumberService templateNumberService;

    @Autowired
    private IDycpServicioDAO iDycpServicioDAO;

    @Autowired(required = true)
    private DyccAprobadorDAO daoAprobador;

    /**
     * Emite los comunicados automaticamente para el caso de uso de
     * retroalimentarTESOFE
     *
     * @param numeroControl
     * @param tipoComunicado
     * @param datosTESOFE
     */
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void emitirComunicadosAutomaticamente(String numeroControl, int tipoComunicado, String[] datosTESOFE) throws SIATException {

        Map<String, Object> mapa = null;

        ParametrosSelloDigitalDTO objetoSelloDigital = new ParametrosSelloDigitalDTO();
        DycpServicioDTO objetoServicio = iDycpServicioDAO.encontrar(numeroControl);

        objetoSelloDigital.setNumControl(numeroControl);

        if (tipoComunicado == SOLICITUDCUENTA) {
            boolean esAGGC = false;
            int tipoServicio = objetoServicio.getDycaOrigenTramiteDTO().getDycaServOrigenDTO().getDyccTipoServicioDTO().getIdTipoServicio();

            if ((tipoServicio == SOLICITUDDEVOLUCION || tipoServicio == SOLICITUD_DEVOLUCION_AUT)
                    && sinComunicadosAnteriores(numeroControl)) {

                int claveAdm = objetoServicio.getClaveAdm();

                if (claveAdm == ConstantesDyCNumerico.VALOR_90
                        || claveAdm == ConstantesDyCNumerico.VALOR_91
                        || claveAdm == ConstantesDyCNumerico.VALOR_97) {

                    esAGGC = Boolean.TRUE;
                }
                int idPlantillaDocumento = getPlantillaDocumento(numeroControl);
                objetoSelloDigital.setIdPlantilla(idPlantillaDocumento);

                mapa = crearMapa(objetoSelloDigital);

                //AQUI SE MANDA LLAMAR EL NUEVO METODO QUE HARA LA FUNCIONALIDAD DE ENVIAR NOTIFICACIONES...
                Map mapaRespuesta = templateNumberService.createTemplateOfJustMap(mapa);

                if (mapaRespuesta != null) {
                    insertarEnDocumento(numeroControl, mapaRespuesta, objetoServicio, esAGGC);
                }

            }

        }
        objetoSelloDigital = null;
    }

    private boolean sinComunicadosAnteriores(String numeroControl) throws SIATException {

        return dyctDocumentoDAO.contarPlantillasGeneradas22o66(numeroControl) == 0;
    }

    /**
     * Inserta el documento en base de datos. Indica espec&iacute;ficamente que
     * plantilla es la que se genera a partir de la administraci&oacute;n de la
     * que proviene.
     *
     * @param numeroControl
     * @param objetoContribuyente
     * @param mapaRespuesta
     * @param objetoServicio
     * @throws SIATException
     */
    private void insertarEnDocumento(String numeroControl, Map mapaRespuesta,
            DycpServicioDTO objetoServicio, boolean esAGGC) throws SIATException {

        int idPlantilla;

        if (esDevolucionAutomaticaSad(numeroControl)) {
            idPlantilla = IDPLANTILLA138;

        } else if (esAGGC) {
            idPlantilla = IDPLANTILLA66;

        } else if (esHidrocarburos(numeroControl)) {
            idPlantilla = IDPLANTILLA131;

        } else if (esAgace(numeroControl)) {
            idPlantilla = IDPLANTILLA114;

        } else {
            idPlantilla = IDPLANTILLA22;

        }

        String numeroDocumento = mapaRespuesta.get(NUMERODOCUMENTO).toString();
        String urlDocumento = mapaRespuesta.get(URL).toString();
        String nombreArchivo = mapaRespuesta.get(NOMBREARCHIVO).toString();

        insertarDocumento(numeroDocumento, urlDocumento, nombreArchivo, idPlantilla, objetoServicio);
    }

    /**
     * Asigna un n&uacute;mero de plantilla dependiendo de la
     * administraci&oacute;n que priviene. Este dato se obtiene a paritir del
     * n&uacute;mero de control de documento.
     *
     * @param numeroControl
     * @return
     */
    private int getPlantillaDocumento(String numeroControl) {
        int numeroDePlantilla = 0;

        if (esDevolucionAutomaticaSad(numeroControl)) {
            numeroDePlantilla = IDPLANTILLA138;

        } else if (esHidrocarburos(numeroControl)) {
            numeroDePlantilla = IDPLANTILLA131;

        } else if (esAgace(numeroControl)) {
            numeroDePlantilla = IDPLANTILLA114;

        } else if (esGrandesContribuyentes(numeroControl)) {
            numeroDePlantilla = IDPLANTILLA66;

        } else {
            //O DE AGAFF:
            numeroDePlantilla = IDPLANTILLA22;
        }

        return numeroDePlantilla;
    }

    private boolean esDevolucionAutomaticaSad(String numeroControl) {

        return numeroControl.startsWith("SA");
    }

    private boolean esHidrocarburos(String numeroControl) {

        return numeroControl.startsWith("DC81")
                || numeroControl.startsWith("DC82");
    }

    private boolean esAgace(String numeroControl) {

        return numeroControl.startsWith("DC80");
    }

    private boolean esGrandesContribuyentes(String numeroControl) {

        return numeroControl.startsWith("DC90")
                || numeroControl.startsWith("DC91")
                || numeroControl.startsWith("DC97");
    }

    /**
     * Se crea un mapa el cual se enviara al servicio de GestionArchivos.
     *
     * @param objetoSelloDigital
     * @return Mapa con la plantilla, numero de control, cadena original y sello
     * digital
     */
    private Map<String, Object> crearMapa(ParametrosSelloDigitalDTO objetoSelloDigital) {
        Map<String, Object> mapa = new HashMap<String, Object>();

        mapa.put("idPlantilla", objetoSelloDigital.getIdPlantilla());
        mapa.put("queryAConsultar", objetoSelloDigital.getIdPlantilla());
        mapa.put("numControl", objetoSelloDigital.getNumControl());
        mapa.put("cadenaOriginal", null);
        mapa.put("selloDigital", null);

        return mapa;
    }

    /**
     * Inserta el documento para darle seguimiento
     *
     * @param numControlDoc
     * @param ruta
     * @param nombreArchivo
     * @param idPlantilla
     * @param objetoServicio
     * @return
     * @throws SIATException
     */
    private DyctDocumentoDTO insertarDocumento(String numControlDoc, String ruta, String nombreArchivo, int idPlantilla, DycpServicioDTO objetoServicio) throws SIATException {

        DyctDocumentoDTO objeto = null;
        DyctDocumentoDTO objetoTemporal = null;

        try {
            DyccMatrizDocVO objetoMatriz = dyccMatrizDocDAO.buscarMatrizDoc(idPlantilla);

            objeto = new DyctDocumentoDTO();
            objetoTemporal = dyctDocumentoDAO.encontrarUltimoRegistroXNumControl(objetoServicio.getNumControl());
            if (objetoTemporal == null) {
                objetoTemporal = new DyctDocumentoDTO();
                DyccAprobadorDTO dyccAprobadorDTO = new DyccAprobadorDTO();
                Integer cveAdministrativa = objetoServicio.getDyccUnidadAdmvaDTO().getIdUnidAdmvaTipo();
                LOGGER.info("CveAdministrativa: " + cveAdministrativa);
                Integer noEmpleado = daoAprobador.consultarAprobadorActivoXClaveAdm(cveAdministrativa).getNumEmpleado();
                LOGGER.info("NoEmpleado: " + noEmpleado);
                dyccAprobadorDTO.setNumEmpleado(noEmpleado);
                objetoTemporal.setDyccAprobadorDTO(dyccAprobadorDTO);
            }

            DyccEstadoDocDTO objetoEstadoDoc = new DyccEstadoDocDTO();
            DyccEstadoReqDTO objetoEstadoReq = new DyccEstadoReqDTO();
            DyccAprobadorDTO objetoAprobador = new DyccAprobadorDTO();
            DyccMatDocumentosDTO objetoMatrizDoc = new DyccMatDocumentosDTO();
            DyccTipoDocumentoDTO objetoTipoDoc = new DyccTipoDocumentoDTO();
            objetoAprobador.setNumEmpleado(objetoTemporal.getDyccAprobadorDTO().getNumEmpleado());

            objeto.setNumControlDoc(numControlDoc);
            objetoEstadoDoc.setIdEstadoDoc(ENAPROBACION);
            objetoMatrizDoc.setIdPlantilla(idPlantilla);
            objetoTipoDoc.setIdTipoDocumento(objetoMatriz.getIdTipo());

            objeto.setDyccTipoDocumentoDTO(objetoTipoDoc);
            objeto.setDycpServicioDTO(objetoServicio);
            objeto.setUrl(ruta);
            objeto.setFechaRegistro(new Date(System.currentTimeMillis()));
            objeto.setNombreArchivo(nombreArchivo);
            objeto.setDyccEstadoDocDTO(objetoEstadoDoc);
            objetoEstadoReq.setIdEstadoReq(EMITIDO);
            objeto.setDyccEstadoReqDTO(objetoEstadoReq);
            objeto.setDyccMatDocumentosDTO(objetoMatrizDoc);
            objeto.setDyccAprobadorDTO(objetoAprobador);

            dyctDocumentoDAO.insertar(objeto);

        } catch (Exception e) {
            throw new SIATException(e);
        }

        return objeto;
    }
}
