package mx.gob.sat.siat.dyc.casocomp.service.impl;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.casocomp.service.EmitirRequerimientoService;
import mx.gob.sat.siat.dyc.casocomp.service.IAdmCasosCompensacionService;
import mx.gob.sat.siat.dyc.dao.anexo.DyccAnexoTramiteDAO;
import mx.gob.sat.siat.dyc.dao.anexo.DyctAnexoReqDAO;
import mx.gob.sat.siat.dyc.dao.compensacion.IDycpCompensacionDAO;
import mx.gob.sat.siat.dyc.dao.documento.DyccInfoTramiteDAO;
import mx.gob.sat.siat.dyc.dao.documento.DyctDocumentoDAO;
import mx.gob.sat.siat.dyc.dao.req.DyctInfoRequeridaDAO;
import mx.gob.sat.siat.dyc.dao.req.DyctOtraInfoReqDAO;
import mx.gob.sat.siat.dyc.dao.req.DyctReqInfoDAO;
import mx.gob.sat.siat.dyc.domain.anexo.DyccAnexoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.anexo.DyccMatrizAnexosDTO;
import mx.gob.sat.siat.dyc.domain.anexo.DyctAnexoReqDTO;
import mx.gob.sat.siat.dyc.domain.documento.DyccMatDocumentosDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctInfoRequeridaDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctOtraInfoReqDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctReqInfoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccInfoARequerirDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccInfoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.util.common.ItemComboBean;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service("serviceEmitirRequerimiento")
public class EmitirRequerimientoServiceImpl implements EmitirRequerimientoService {
    private static final Logger LOG = Logger.getLogger(EmitirRequerimientoServiceImpl.class);

    @Autowired
    private DyctDocumentoDAO daoDocumento;

    @Autowired
    private DyctInfoRequeridaDAO daoInfoRequerida;

    @Autowired
    private DyctOtraInfoReqDAO daoOtraInfoReq;

    @Autowired
    private DyctAnexoReqDAO daoAnexoReq;

    @Autowired
    private DyccInfoTramiteDAO daoInfoTramite;

    @Autowired
    private DyccAnexoTramiteDAO daoAnexoTramite;

    @Autowired
    private DyctReqInfoDAO daoReqInfo;

    @Autowired
    private IDycpCompensacionDAO daoCompensacion;

    @Autowired
    private IAdmCasosCompensacionService serviceAdmCC;

    @Override
    public Map<String, Object> obtenerInfoIniEmitirReq(String numControl) {
        HashMap<String, Object> infoInicial = new HashMap<String, Object>();
        DycpServicioDTO servicio = new DycpServicioDTO();
        servicio.setNumControl(numControl);
        try {
            List<DyctDocumentoDTO> documentos = daoDocumento.selecXServicioTipodocumento(servicio, Constantes.TiposDocumento.PRIMER_REQUERIMIENTO);
            Boolean yaExisteReqValido = Boolean.FALSE;
            for(DyctDocumentoDTO primerReq : documentos){
                if(primerReq.getDyccEstadoDocDTO() != Constantes.EstadosDoc.RECHAZADO){
                    yaExisteReqValido = Boolean.TRUE;
                }
            }

            infoInicial.put("existe", yaExisteReqValido);
            if (yaExisteReqValido)
            {
                DyctReqInfoDTO reqInfo = new DyctReqInfoDTO();
                reqInfo.setNumControlDoc(documentos.get(0).getNumControlDoc());
                List<DyctInfoRequeridaDTO> lInfoReqSel = daoInfoRequerida.selecXReqinfo(reqInfo);
                List<ItemComboBean> infoARequerirSelec = new ArrayList<ItemComboBean>();
                for (DyctInfoRequeridaDTO dto : lInfoReqSel) {
                    ItemComboBean item = new ItemComboBean();
                    item.setId(dto.getDyccInfoTramiteDTO().getDyccInfoARequerirDTO().getIdInfoArequerir());
                    item.setDescripcion(dto.getDyccInfoTramiteDTO().getDyccInfoARequerirDTO().getDescripcion());
                    infoARequerirSelec.add(item);
                }

                List<DyctOtraInfoReqDTO> lotraInfoReq = daoOtraInfoReq.selecXReqinfo(reqInfo);
                List<String> otrosReqs = new ArrayList<String>();
                for (DyctOtraInfoReqDTO dto : lotraInfoReq) {
                    otrosReqs.add(dto.getDescripcion());
                }

                List<DyctAnexoReqDTO> lAnexosReqs = daoAnexoReq.selecXReqinfo(reqInfo);
                List<ItemComboBean> anexosSelec = new ArrayList<ItemComboBean>();
                for (DyctAnexoReqDTO dto : lAnexosReqs) {
                    ItemComboBean item = new ItemComboBean();
                    item.setId(dto.getDyccAnexoTramiteDTO().getDyccMatrizAnexosDTO().getIdAnexo());
                    item.setDescripcion(dto.getDyccAnexoTramiteDTO().getDyccMatrizAnexosDTO().getNombreAnexo());
                    anexosSelec.add(item);
                }

                infoInicial.put("idEstado", documentos.get(0).getDyccEstadoReqDTO().getIdEstadoReq());
                infoInicial.put("infoARequerirSelec", infoARequerirSelec);
                infoInicial.put("otrosReqs", otrosReqs);
                infoInicial.put("anexosSelec", anexosSelec);
            } else {
                DycpCompensacionDTO origen = daoCompensacion.encontrar(numControl);

                List<ItemComboBean> infoAReq = new ArrayList<ItemComboBean>();
                DyccTipoTramiteDTO tt = origen.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO();
                LOG.info("idTipoTramite ->" + tt.getIdTipoTramite() + "<-");
                List<DyccInfoTramiteDTO> listInfoTramite = daoInfoTramite.selecXTipotramite(tt);

                for (DyccInfoTramiteDTO infoTramite : listInfoTramite) {
                    DyccInfoARequerirDTO dtoInfoAReq = infoTramite.getDyccInfoARequerirDTO();
                    ItemComboBean infoAReqICVBean = new ItemComboBean();
                    infoAReqICVBean.setId(dtoInfoAReq.getIdInfoArequerir());
                    infoAReqICVBean.setDescripcion(dtoInfoAReq.getDescripcion());
                    infoAReq.add(infoAReqICVBean);
                }

                List<ItemComboBean> anexos = new ArrayList<ItemComboBean>();
                List<DyccAnexoTramiteDTO> relsAnexoTramite = daoAnexoTramite.selecXTipotramite(tt);
                for (DyccAnexoTramiteDTO relAnexoTramite : relsAnexoTramite) {
                    DyccMatrizAnexosDTO dtoAnexo = relAnexoTramite.getDyccMatrizAnexosDTO();
                    ItemComboBean anexoBean = new ItemComboBean();
                    anexoBean.setId(dtoAnexo.getIdAnexo());
                    anexoBean.setDescripcion(dtoAnexo.getNombreAnexo() + " - " + dtoAnexo.getDescripcionAnexo());
                    anexos.add(anexoBean);
                }
                infoInicial.put("infoARequerirDisp", infoAReq);
                infoInicial.put("anexosDisp", anexos);
            }
        } catch (SIATException e) {
            LOG.error(e);
        }

        return infoInicial;
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW,
                   rollbackFor = { SIATException.class })
    public Map<String, Object> generarRequerimiento(Map<String, Object> params) throws SIATException {
        //  guardar archivo
        DycpServicioDTO servicio = new DycpServicioDTO();
        DycpCompensacionDTO compensacion = new DycpCompensacionDTO();
        // TODO: incluir <<CU MantenerDocumento>>
        HashMap<String, Object> respuesta = new HashMap<String, Object>();

        String numControl = (String)params.get("numControl");
        servicio.setNumControl(numControl);
        compensacion.setNumControl(numControl);
        InputStream secuenciaEntrada = (InputStream)params.get("secuenciaEntrada");
        String nombreArchivo = (String)params.get("nombreArchivo");

        try {
            serviceAdmCC.guardarArchivo(servicio, secuenciaEntrada, nombreArchivo);

            List<ItemComboBean> listaInfoARequerir = (List<ItemComboBean>)params.get("infoARequerir");
            List<String> reqsPersonalizados = (List<String>)params.get("reqsPersonalizados");
            List<ItemComboBean> anexos = (List<ItemComboBean>)params.get("anexos");

            java.sql.Date fechaCreacionReq = new java.sql.Date(new java.util.Date().getTime());

            DyccMatDocumentosDTO plantilla = new DyccMatDocumentosDTO();
            plantilla.setIdPlantilla((Integer)params.get("idPlantilla"));

            DyccAprobadorDTO aprobador = new DyccAprobadorDTO();
            aprobador.setNumEmpleado((Integer)params.get("idSuperior"));
            compensacion.setDyccAprobadorDTO(aprobador);

            //crear e insertar documento
            DyctReqInfoDTO reqInfo = new DyctReqInfoDTO();
            reqInfo.setNumControlDoc((String)params.get("numControlDoc"));
            LOG.debug("documento.getNumControlDoc() ->" + reqInfo.getNumControlDoc() + "<-");
            reqInfo.setDyccTipoDocumentoDTO(Constantes.TiposDocumento.PRIMER_REQUERIMIENTO);
            reqInfo.setUrl((String)params.get("url"));
            reqInfo.setDycpServicioDTO(servicio);
            reqInfo.setFechaRegistro(fechaCreacionReq);
            reqInfo.setNombreArchivo((String)params.get("nombreArchivo"));
            reqInfo.setDyccEstadoReqDTO(Constantes.EstadosReq.EMITIDO);
            reqInfo.setDyccEstadoDocDTO(Constantes.EstadosDoc.EN_APROBACION);
            reqInfo.setDyccMatDocumentosDTO(plantilla);
            reqInfo.setDyccAprobadorDTO(aprobador);
            daoDocumento.insertarE(reqInfo);

            // guardar requerimiento
            daoReqInfo.insertar(reqInfo);

            DycpCompensacionDTO origen = daoCompensacion.encontrar(numControl);
            DyccTipoTramiteDTO tipoTramite =
                origen.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO();
            LOG.debug("idTipoTramite ->" + tipoTramite.getIdTipoTramite());

            // guardar info a req
            if (listaInfoARequerir.size() > 0) {
                for (ItemComboBean irccBean : listaInfoARequerir) {
                    DyccInfoARequerirDTO infoARequerir = new DyccInfoARequerirDTO();
                    infoARequerir.setIdInfoArequerir(irccBean.getId());

                    DyccInfoTramiteDTO infoTramite = new DyccInfoTramiteDTO();
                    infoTramite.setDyccTipoTramiteDTO(tipoTramite);
                    infoTramite.setDyccInfoARequerirDTO(infoARequerir);

                    DyctInfoRequeridaDTO infoRequerida = new DyctInfoRequeridaDTO();
                    infoRequerida.setDyctReqInfoDTO(reqInfo);
                    infoRequerida.setDyccInfoTramiteDTO(infoTramite);

                    daoInfoRequerida.insertar(infoRequerida);
                }
            }

            // guardar requerimientos Personalizados
            for (String reqPersonalizado : reqsPersonalizados) {
                DyctOtraInfoReqDTO otraInfoReq = new DyctOtraInfoReqDTO();
                otraInfoReq.setDescripcion(reqPersonalizado);
                otraInfoReq.setDyctReqInfoDTO(reqInfo);
                daoOtraInfoReq.insertar(otraInfoReq);
            }

            // guardar anexos ItemComboBean
            for (ItemComboBean anexo : anexos) {
                DyctAnexoReqDTO anexoReq = new DyctAnexoReqDTO();
                anexoReq.setDyctReqInfoDTO(reqInfo);

                DyccAnexoTramiteDTO anexoTramite = new DyccAnexoTramiteDTO();
                DyccMatrizAnexosDTO matrizAnexos = new DyccMatrizAnexosDTO();
                matrizAnexos.setIdAnexo(anexo.getId());
                anexoTramite.setDyccMatrizAnexosDTO(matrizAnexos);
                anexoTramite.setDyccTipoTramiteDTO(tipoTramite);
                anexoReq.setDyccAnexoTramiteDTO(anexoTramite);

                daoAnexoReq.insertar(anexoReq);
            }

            compensacion.setDyccEstadoCompDTO(Constantes.EstadosCompensacion.EN_REVISION);
            daoCompensacion.actualizar(compensacion);

            respuesta.put("satisfactorio", Boolean.TRUE);
            respuesta.put("mensaje", "El requerimiento se genero satisfactoriamente");
        } catch (Exception e) {
            LOG.error(e);
            throw new SIATException(e);
        }

        return respuesta;
    }
}
