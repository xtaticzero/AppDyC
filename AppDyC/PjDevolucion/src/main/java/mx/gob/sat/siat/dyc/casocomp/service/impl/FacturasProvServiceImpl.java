package mx.gob.sat.siat.dyc.casocomp.service.impl;

import java.io.InputStream;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.casocomp.service.FacturasProvService;
import mx.gob.sat.siat.dyc.casocomp.util.Utils;
import mx.gob.sat.siat.dyc.casocomp.web.controller.bean.utility.FilaGridFacturasProvBean;
import mx.gob.sat.siat.dyc.dao.compensacion.IDycpCompensacionDAO;
import mx.gob.sat.siat.dyc.dao.documento.DyctDocumentoDAO;
import mx.gob.sat.siat.dyc.dao.documento.DyctFacturaReqDAO;
import mx.gob.sat.siat.dyc.dao.req.DyctReqConfProvDAO;
import mx.gob.sat.siat.dyc.domain.documento.DyccMatDocumentosDTO;
import mx.gob.sat.siat.dyc.domain.documento.DyccTipoDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctFacturaReqDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctReqConfProvDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;
import mx.gob.sat.siat.dyc.generico.util.ArchivoCargaDescarga;
import mx.gob.sat.siat.dyc.generico.util.common.Utilerias;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service("serviceFacturasProvComp")
public class FacturasProvServiceImpl implements FacturasProvService {
    private static final Logger LOG = Logger.getLogger(FacturasProvServiceImpl.class);

    @Autowired
    private DyctReqConfProvDAO daoReqConfProv;

    @Autowired
    private DyctFacturaReqDAO daoFacturaReq;

    @Autowired
    private DyctDocumentoDAO daoDocumento;

    @Autowired
    private IDycpCompensacionDAO daoCompensacion;

    @Override
    public Map<String, Object> obtenerInfoReqConfOpProvs(String numControl, String rfcProveedor) {
        LOG.debug("AdmCasosCompensacionServiceImpl INICIO obtenerInfoReqConfOpProvs");
        Map<String, Object> respuesta = new HashMap<String, Object>();
        Boolean rfcProveedorValido = rfcValido(rfcProveedor);
        respuesta.put("rfcValido", rfcProveedorValido);
        LOG.debug("rfcProveedorValido: " + rfcProveedorValido);
        if (rfcProveedorValido) {
            DycpServicioDTO servicio = new DycpServicioDTO();
            servicio.setNumControl(numControl);
            List<DyctReqConfProvDTO> reqsConfOpProv = daoReqConfProv.selecXServicioProveedor(servicio, rfcProveedor);
            if (!reqsConfOpProv.isEmpty()) {
                LOG.debug("se encontro un requerimiento de facturas");
                DyctReqConfProvDTO req = reqsConfOpProv.get(0);
                respuesta.put("idRequerimiento", req.getDyctDocumentoDTO().getNumControlDoc());
                respuesta.put("idEstadoRequerimiento ",
                              req.getDyctDocumentoDTO().getDyccEstadoReqDTO().getIdEstadoReq());

                List<FilaGridFacturasProvBean> filasFacturas = new ArrayList<FilaGridFacturasProvBean>();
                List<DyctFacturaReqDTO> dtosFacturas = daoFacturaReq.selecXReqconf(req);
                for (DyctFacturaReqDTO dtoFactura : dtosFacturas) {
                    FilaGridFacturasProvBean fila = new FilaGridFacturasProvBean();
                    fila.cargar(dtoFactura);
                    filasFacturas.add(fila);
                }
                respuesta.put("filasFacturas", filasFacturas);
            }
        }
        return respuesta;
    }

    private Boolean rfcValido(String rfc) {
        LOG.info(rfc);
        return Boolean.TRUE;
    }

    @Override
    public Map<String, Object> generarRequerimientoFacturas(Map<String, Object> params) {
        DycpServicioDTO servicio = new DycpServicioDTO();
        servicio.setNumControl((String)params.get("numControl"));

        DyccTipoDocumentoDTO tipoDocReq = new DyccTipoDocumentoDTO();
        tipoDocReq.setIdTipoDocumento(1);
        DyccMatDocumentosDTO plantilla = new DyccMatDocumentosDTO();
        plantilla.setIdPlantilla(1);

        DyctReqConfProvDTO reqConfProv = new DyctReqConfProvDTO();
        reqConfProv.getDyctDocumentoDTO().setDyccTipoDocumentoDTO(tipoDocReq);
        reqConfProv.getDyctDocumentoDTO().setDycpServicioDTO(servicio);
        reqConfProv.getDyctDocumentoDTO().setFechaRegistro(new java.util.Date());
        reqConfProv.getDyctDocumentoDTO().setDyccEstadoReqDTO(Constantes.EstadosReq.EMITIDO);
        reqConfProv.getDyctDocumentoDTO().setDyccEstadoDocDTO(Constantes.EstadosDoc.GENERADO);
        reqConfProv.getDyctDocumentoDTO().setNombreArchivo("Requerimiento Caso Compensacion");

        reqConfProv.setRfcProveedor((String)params.get("rfcProveedor"));

        List<FilaGridFacturasProvBean> beansFacturas = (List<FilaGridFacturasProvBean>)params.get("facturas");
        for (FilaGridFacturasProvBean beanFactura : beansFacturas) {
            java.util.Date fechaEm = convertirAFecha(beanFactura.getFecha());

            DyctFacturaReqDTO facturaReq = new DyctFacturaReqDTO();
            facturaReq.setNumeroFactura(beanFactura.getNumFactura());
            facturaReq.setFechaEmision(fechaEm);
            facturaReq.setConcepto(beanFactura.getConcepto());
            facturaReq.setImporte(new BigDecimal(beanFactura.getImporte()));
            facturaReq.setIvaTrasladado(new BigDecimal(beanFactura.getIvaTrasladado()));
            facturaReq.setTotal(new BigDecimal(beanFactura.getTotal()));
            facturaReq.setDyctReqConfProvDTO(reqConfProv);
            /**daoFacturaReq.insertar(facturaReq);*/
        }

        Map<String, Object> respuesta = new HashMap<String, Object>();
        /**Map<String, Object> resultMantenerDoc = new HashMap<String, Object>();*/
        /**
        respuesta.put("resultado", resultMantenerDoc.get("resultado"));
        respuesta.put("url", resultMantenerDoc.get("url"));
        respuesta.put("nombreArchivo", resultMantenerDoc.get("nombreArchivo"));
        respuesta.put("idPlantilla", plantilla.getIdPlantilla());
        respuesta.put("mensaje", resultMantenerDoc.get("mensaje"));
          */
        respuesta.put("resultado", Boolean.TRUE);
        respuesta.put("url", "/url/provisional");
        respuesta.put("nombreArchivo", "reqFcaturas.docx");
        respuesta.put("idPlantilla", plantilla.getIdPlantilla());

        return respuesta;
    }

    private java.util.Date convertirAFecha(String fechaStr) {
        LOG.info(fechaStr);
        return new java.util.Date();
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { SIATException.class })
    public Map<String, Object> enviarAAprobacionReqFacturas(Map<String, Object> params) {
        LOG.info("INICIO enviarAAprobacion");
        DycpServicioDTO servicio = new DycpServicioDTO();
        servicio.setNumControl((String)params.get("numControl"));

        
        InputStream secuenciaEntrada = (InputStream)params.get("secuenciaEntrada");
        String nombreArchivo = (String)params.get("nombreArchivo");

        Map<String, Object> respuesta = new HashMap<String, Object>();
        try {

            guardarArchivo(servicio, secuenciaEntrada, nombreArchivo);

            // ********
            DyccMatDocumentosDTO plantilla = new DyccMatDocumentosDTO();
            plantilla.setIdPlantilla((Integer)params.get("idPlantilla"));
            DyccAprobadorDTO aprobador = new DyccAprobadorDTO();
            aprobador.setNumEmpleado((Integer)params.get("numEmpleadoAprobador"));

            //insertar DyctDocumento
            DyctDocumentoDTO documento = new DyctDocumentoDTO();
            documento.setNumControlDoc((String)params.get("numControlDoc"));
            documento.setDyccTipoDocumentoDTO(Constantes.TiposDocumento.REQUERIMIENTO_FACTURAS);
            documento.setUrl((String)params.get("url"));
            documento.setFechaRegistro(new java.util.Date());
            documento.setNombreArchivo((String)params.get("nombreArchivo"));
            documento.setDyccAprobadorDTO(aprobador);
            documento.setDyccEstadoReqDTO(Constantes.EstadosReq.EMITIDO);
            documento.setDyccEstadoDocDTO(Constantes.EstadosDoc.EN_APROBACION);
            documento.setDycpServicioDTO(servicio);
            documento.setDyccMatDocumentosDTO(plantilla);
            daoDocumento.insertarE(documento);

            //insertar Dyct_ReqConfProv
            DyctReqConfProvDTO reqConfProv = new DyctReqConfProvDTO();
            reqConfProv.setDyctDocumentoDTO(documento);
            reqConfProv.setRfcProveedor((String)params.get("rfcProveedor"));
            daoReqConfProv.insertar(reqConfProv);

            //insertar DyctFacturaReq
            List<FilaGridFacturasProvBean> beansFacturas = (List<FilaGridFacturasProvBean>)params.get("facturas");
            for (FilaGridFacturasProvBean beanFactura : beansFacturas) {
                LOG.debug("insertando factura ....");
                java.util.Date fechaEm = convertirAFecha(beanFactura.getFecha());

                DyctFacturaReqDTO facturaReq = new DyctFacturaReqDTO();
                facturaReq.setNumeroFactura(beanFactura.getNumFactura());
                facturaReq.setDyctReqConfProvDTO(reqConfProv);
                facturaReq.setFechaEmision(fechaEm);
                facturaReq.setConcepto(beanFactura.getConcepto());

                BigDecimal a = Utils.obtenerCantidadMoneda(beanFactura.getImporte());
                LOG.debug("a ->" + a);
                facturaReq.setImporte(a);

                facturaReq.setIvaTrasladado(Utils.obtenerCantidadMoneda(beanFactura.getIvaTrasladado()));

                facturaReq.setTotal(Utils.obtenerCantidadMoneda(beanFactura.getTotal()));

                daoFacturaReq.insertar(facturaReq);
            }
            
            daoCompensacion.actualizarStatus(Constantes.EstadosCompensacion.EN_REVISION.getIdEstadoComp(), servicio.getNumControl());
            
            respuesta.put("resultado", Boolean.TRUE);
            respuesta.put("mensaje", "El requerimiento se envío a aprobación satisfactoriamente");
        } catch (SIATException e) {
            LOG.error(e);
        }

        return respuesta;
    }

    private void guardarArchivo(DycpServicioDTO servicio, InputStream secuenciaEntrada, String nombreArchivo) throws SIATException {
        DycpCompensacionDTO compensacion = daoCompensacion.encontrar(servicio.getNumControl());
        String claveAdm = String.format("%02d", compensacion.getDycpServicioDTO().getClaveAdm());


        StringBuilder ruta = new StringBuilder();
        ruta.append("/siat/dyc/documentos/");
        ruta.append(claveAdm + "/");
        /**ruta.append(compensacion.getDycpServicioDTO().getRfcContribuyente() + "/");*/
        ruta.append(Utilerias.corregirRFC(compensacion.getDycpServicioDTO().getRfcContribuyente()) + "/");
        ruta.append(compensacion.getDycpServicioDTO().getNumControl() + "/");
        ruta.append("gestiondoc/");
        ArchivoCargaDescarga utileriaArchivos = new ArchivoCargaDescarga();
        utileriaArchivos.escribirArchivo(nombreArchivo, secuenciaEntrada, ruta.toString(), ConstantesDyCNumerico.VALOR_4096);
    }
}
