package mx.gob.sat.siat.dyc.casocomp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.casocomp.service.BandejaDocumentosService;
import mx.gob.sat.siat.dyc.casocomp.util.Utils;
import mx.gob.sat.siat.dyc.casocomp.web.controller.bean.utility.FilaGridDocumentosBean;
import mx.gob.sat.siat.dyc.dao.documento.DyctDocumentoDAO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctContribuyenteDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.generico.util.calculo.CalcularFechasService;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import mx.gob.sat.siat.dyc.util.constante.ConstantesACC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("serviceBandejaDocumentos")
public class BandejaDocumentosServiceImpl implements BandejaDocumentosService {
    private static final Logger LOG = Logger.getLogger(BandejaDocumentosServiceImpl.class);

    @Autowired
    private DyctDocumentoDAO daoDocumento;

    @Autowired
    private CalcularFechasService serviceCalcularFechas;

    @Override
    public List<FilaGridDocumentosBean> obtenerDocumentos(Map<String, Object> params) {
        LOG.debug("INICIO obtenerDocumentos");
        DyccDictaminadorDTO dictaminador = new DyccDictaminadorDTO();
        dictaminador.setNumEmpleado((Integer)params.get("numEmpleado"));
        LOG.debug("numEmpleado dictaminador ->" + dictaminador.getNumEmpleado());
        List<DyctDocumentoDTO> documentos =
            daoDocumento.selecUnionCompensacionXEstadoDictaminador(Constantes.EstadosDoc.EN_APROBACION, dictaminador);
        documentos.addAll(daoDocumento.selecUnionCompensacionXEstadoDictaminador(Constantes.EstadosDoc.RECHAZADO,
                                                                                 dictaminador));
        List<FilaGridDocumentosBean> filas = new ArrayList<FilaGridDocumentosBean>();
        for (DyctDocumentoDTO dtoDocumento : documentos) {
            LOG.debug("dtoDocumento ->" + dtoDocumento);
            DycpServicioDTO servicio = dtoDocumento.getDycpServicioDTO();
            DycpCompensacionDTO comp = servicio.getDycpCompensacionDTO();
            LOG.debug("comp ->" + comp);
            DyctContribuyenteDTO contribuyente = servicio.getDyctContribuyenteDTO();
            LOG.debug("contribuyente ->" + contribuyente + "<-");
            DyccTipoTramiteDTO tipoTramite = servicio.getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO();
            String sfrt = tipoTramite != null ? obtenerFechaLimite(comp, tipoTramite) : "";

            FilaGridDocumentosBean fila = new FilaGridDocumentosBean();
            fila.setNumControlDoc(dtoDocumento.getNumControlDoc());
            fila.setNumeroControl(dtoDocumento.getDycpServicioDTO().getNumControl());
            fila.setRfc(servicio.getRfcContribuyente());
            //Estado de dictaminación provisto por el servicio de IDC
            fila.setDictaminado(contribuyente.getRolDictaminado() == null ? ConstantesACC.ESTADOSI :
                                ConstantesACC.ESTADONO);
            fila.setTipoTramite(tipoTramite != null ? tipoTramite.getDescripcion() : "");
            fila.setFechaRecepTramite(Utils.formatearFecha(comp.getFechaInicioTramite()));
            fila.setNombreDocumento(dtoDocumento.getNombreArchivo());
            fila.setFechaLimResolTramite(sfrt);
            fila.setEstadoDocumento(dtoDocumento.getDyccEstadoDocDTO().getDescripcion());
            fila.setMontoCompensado(comp.getImporteCompensado()+"");
            filas.add(fila);
        }
        return filas;
    }

    private String obtenerFechaLimite(DycpCompensacionDTO comp, DyccTipoTramiteDTO tipoTramite) {
        try {
            Integer idTipoPlazo = tipoTramite.getDyccTipoPlazoDTO().getIdTipoPlazo();
            LOG.debug("comp.getFechaRegistro() ->" + comp.getFechaInicioTramite());
            LOG.debug("tipoTramite.getPlazo() ->" + tipoTramite.getPlazo());
            LOG.debug("idTipoPlazo ->" + idTipoPlazo);
            java.util.Date fechaLimite =
                serviceCalcularFechas.calcularFechaFinal(comp.getFechaInicioTramite(), tipoTramite.getPlazo(),
                                                         idTipoPlazo);
            return Utils.formatearFecha(fechaLimite);
        } catch (Exception e) {
            LOG.error("ERROR: " + e.getMessage());
        }
        return "";
    }

    @Override
    public Map<String, Object> obtenerInfoDescargarDocumento(String numControlDoc) throws SIATException {
        LOG.debug("numControlDoc ->" + numControlDoc + "<-");
        Map<String, Object> info = new HashMap<String, Object>();
        try {
            DyctDocumentoDTO documento = daoDocumento.encontrar(numControlDoc);
            LOG.debug("documento ->" + documento + "<-");
            info.put("pathArchivo", documento.getUrl() + "/" + documento.getNombreArchivo());
        } catch (SIATException e) {
            throw new SIATException(e);
        }
        return info;
    }

    @Override
    public Map<String, Object> enviarAAprobacion(String numControlDoc, Integer numEmpleadoAprob) throws SIATException {
        Map<String, Object> respuesta = new HashMap<String, Object>();
        try {
            DyccAprobadorDTO aprobador = new DyccAprobadorDTO();
            aprobador.setNumEmpleado(numEmpleadoAprob);
            DyctDocumentoDTO documento = new DyctDocumentoDTO();
            documento.setDyccAprobadorDTO(aprobador);
            documento.setDyccEstadoDocDTO(Constantes.EstadosDoc.EN_APROBACION);
            documento.setNumControlDoc(numControlDoc);
            daoDocumento.actualizar(documento);
        } catch (SIATException e) {
            throw new SIATException(e);
        }
        return respuesta;
    }
}
