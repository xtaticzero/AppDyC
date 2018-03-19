/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.casocomp.service.emitirliquidacion.impl;

import java.io.File;
import java.io.InputStream;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.casocomp.bean.ICEPBean;
import mx.gob.sat.siat.dyc.casocomp.service.IAdmCasosCompensacionService;
import mx.gob.sat.siat.dyc.casocomp.service.emitirliquidacion.EmitirLiquidacionService;
import mx.gob.sat.siat.dyc.casocomp.util.Utils;
import mx.gob.sat.siat.dyc.dao.compensacion.DyctLiquidacionDAO;
import mx.gob.sat.siat.dyc.dao.compensacion.IDycpCompensacionDAO;
import mx.gob.sat.siat.dyc.dao.detalleicep.DyctSaldoIcepDAO;
import mx.gob.sat.siat.dyc.dao.documento.DyctDocumentoDAO;
import mx.gob.sat.siat.dyc.dao.resolucion.DyctResolCompDAO;
import mx.gob.sat.siat.dyc.domain.documento.DyccMatDocumentosDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctLiquidacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctResolCompDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.generico.util.common.GetBigDecimal;
import mx.gob.sat.siat.dyc.template.service.TemplateNumberService;
import mx.gob.sat.siat.dyc.util.RegistroPistasAuditoria;
import mx.gob.sat.siat.dyc.util.common.ItemComboBean;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import mx.gob.sat.siat.dyc.util.constante.ConstantesACC;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.Procesos;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.vo.PistaAuditoriaVO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Clase Service para la Negocio CC Liquidación / Administrar Casos de Compensaciones.
 * @ author Huetzin Cruz Lozano
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @since 0.1
 *
 * @date Junio 5, 2015
 * */
@Service(value = "emitirliquidacionService")
public class EmitirLiquidacionServiceImpl implements EmitirLiquidacionService {
    private static final Logger LOGGER = Logger.getLogger(EmitirLiquidacionServiceImpl.class);

    @Autowired
    private IDycpCompensacionDAO daoCompensacion;

    @Autowired
    private DyctResolCompDAO daoResolComp;

    @Autowired
    private DyctDocumentoDAO daoDocumento;

    @Autowired
    private TemplateNumberService servicePlantillador;

    @Autowired
    private DyctSaldoIcepDAO daoSaldoIcep;

    @Autowired
    private DyctLiquidacionDAO daoLiquidacion;

    @Autowired
    private IAdmCasosCompensacionService serviceAdmCC;

    @Autowired
    private RegistroPistasAuditoria serviceRegistroPistasAud;

    @Override
    public Map<String, Object> obtenerInfoInicial(Map<String, Object> params) {
        DycpCompensacionDTO compensacion;
        Map<String, Object> infoInicial = new HashMap<String, Object>();
        try {
            compensacion = daoCompensacion.encontrar((String)params.get(Constantes.NombresParametros.NUMERO_CONTROL));

            infoInicial.put("dyccPeriodos", new ArrayList<DyccPeriodoDTO>());
            List<Integer> anios = new ArrayList<Integer>();
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            int anioActual = cal.get(Calendar.YEAR);
            for (int i = 0; i < ConstantesACC.NUM_ANIOS_ANTERIORES; i++) {
                anios.add(anioActual - i);
            }
            infoInicial.put("anios", anios);


            infoInicial.put("rfcContribuyente", compensacion.getDycpServicioDTO().getRfcContribuyente());
            infoInicial.put("tipoTramite",
                            compensacion.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getDescripcion());

            List<ItemComboBean> meses = new ArrayList<ItemComboBean>();
            meses.add(new ItemComboBean(ConstantesACC.ID_ENERO, "Enero"));
            meses.add(new ItemComboBean(ConstantesACC.ID_FEBRERO, "Febrero"));
            meses.add(new ItemComboBean(ConstantesACC.ID_MARZO, "Marzo"));
            meses.add(new ItemComboBean(ConstantesACC.ID_ABRIL, "Abril"));
            meses.add(new ItemComboBean(ConstantesACC.ID_MAYO, "Mayo"));
            meses.add(new ItemComboBean(ConstantesACC.ID_JUNIO, "Junio"));
            meses.add(new ItemComboBean(ConstantesACC.ID_JULIO, "Julio"));
            meses.add(new ItemComboBean(ConstantesACC.ID_AGOSTO, "Agosto"));
            meses.add(new ItemComboBean(ConstantesACC.ID_SEPTIEMBRE, "Septiembre"));
            meses.add(new ItemComboBean(ConstantesACC.ID_OCTUBRE, "Octubre"));
            meses.add(new ItemComboBean(ConstantesACC.ID_NOVIEMBRE, "Noviembre"));
            meses.add(new ItemComboBean(ConstantesACC.ID_DICIEMBRE, "Diciembre"));
            infoInicial.put("meses", meses);

            ICEPBean icepCompensado = crearBeanIcep(compensacion.getDyctSaldoIcepDestinoDTO().getIdSaldoIcep());
            icepCompensado.setFechaPresDeclara(Utils.formatearFecha(compensacion.getFechaPresentaDec()));
            icepCompensado.setImporteCompensado(compensacion.getImporteCompensado());

            ICEPBean icepOrigen = crearBeanIcep(compensacion.getDyctSaldoIcepOrigenDTO().getIdSaldoIcep());
            icepOrigen.setImporteCompensado(icepCompensado.getImporteCompensado());

            infoInicial.put("icepCompensado", icepCompensado);
            infoInicial.put("icepOrigen", icepOrigen);
        } catch (SIATException se) {
            LOGGER.error(se.getMessage());
        }
        return infoInicial;
    }

    private ICEPBean crearBeanIcep(Integer idSaldoIcep) throws SIATException {
        DyctSaldoIcepDTO dtoIcep = daoSaldoIcep.encontrar(idSaldoIcep);
        ICEPBean beanIcep = new ICEPBean();
        beanIcep.setImpuesto(dtoIcep.getDyccConceptoDTO().getDyccImpuestoDTO().getDescripcion());
        beanIcep.setConcepto(dtoIcep.getDyccConceptoDTO().getDescripcion());
        beanIcep.setEjercicio(dtoIcep.getDyccEjercicioDTO().getIdEjercicio());
        beanIcep.setPeriodo(dtoIcep.getDyccPeriodoDTO().getDescripcion());
        return beanIcep;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { SIATException.class })
    public Map<String, Object> emitirLiquidacion(Map<String, Object> params) throws SIATException {
        DycpServicioDTO servicio = new DycpServicioDTO();
        servicio.setNumControl((String)params.get(Constantes.NombresParametros.NUMERO_CONTROL));
        Map<String, Object> respuesta = null;

        try {
            respuesta = generarDocLiquidacion(params);
            if ((Boolean)respuesta.get("success")) {
                /** respuesta.put(Constantes.NombresParametros.NOMBRE_ARCHIVO,
                              respPlantillador.get(Constantes.NombresParametros.NOMBRE_ARCHIVO));*/
                StringBuilder ruta = new StringBuilder();
                ruta.append(respuesta.get("url"));
                ruta.append("/");
                ruta.append(respuesta.get(Constantes.NombresParametros.NOMBRE_ARCHIVO));
                respuesta.put("archivo", new File(ruta.toString()));
                respuesta.put("numControlDoc", respuesta.get("NUMERODOCUMENTO"));
            }
        } catch (Exception se) {
            LOGGER.error("###\n\n" +
                    se.getMessage());
            throw new SIATException(se);

        }
        return respuesta;
    }

    @Transactional(readOnly = false, propagation = Propagation.MANDATORY, rollbackFor = { SIATException.class })
    private void insertarLiquidacion(DycpCompensacionDTO compensacion,
                                     Map<String, Object> params) throws SIATException {
        try {
            // insertar ResolComp
            DyctResolCompDTO resolComp = new DyctResolCompDTO();
            resolComp.setDycpCompensacionDTO(compensacion);
            resolComp.setDyccAccionSegDTO(Constantes.AccionesSeg.ESCALACION);
            resolComp.setFechaResolucion(new Date());
            resolComp.setDyccEstResolDTO(Constantes.EstadosResolucion.EN_APROBACION);
            resolComp.setDyccTipoResolDTO((Character)params.get("tipoResolucion") == 'S' ?
                                          Constantes.TiposResolucion.SALDOAFAVOR_IMPROCEDENTE :
                                          Constantes.TiposResolucion.COMPENSACION_IMPROCEDENTE);

            DyctResolCompDTO resolComTmp = daoResolComp.encontrar(compensacion);

            if (null == resolComTmp) {
                daoResolComp.insertar(resolComp);
            } else {
                daoResolComp.actualizar(resolComp);
            }

            // insertar Liquidacion
            DyctLiquidacionDTO liquidacion = new DyctLiquidacionDTO();
            liquidacion.setDyctResolCompDTO(resolComp);
            liquidacion.setImporteActualizar(GetBigDecimal.parse(params.get("importeActualizarD")));
            liquidacion.setImporteRecargo(GetBigDecimal.parse(params.get("importeRecargoD")));
            liquidacion.setImporteMulta(GetBigDecimal.parse(params.get("importeMultaD")));
            liquidacion.setNumDocDeterminante((String)params.get("agaffNumOficio"));
            liquidacion.setMesInicioInpc((params.get("lMesAnteriorA")).toString());
            liquidacion.setAnioInicialInpc((Integer)params.get("lAnioAnteriorA"));
            liquidacion.setMesFinalInpc((params.get("lMesAnteriorR")).toString());
            liquidacion.setAnioFinalInpc((Integer)params.get("lAnioAnteriorR"));
            liquidacion.setMesInicioTasaRec((params.get("mesInicioTasaRec")).toString());
            liquidacion.setAnioInicialTasaRec((Integer)params.get("anioInicialTasaRec"));
            liquidacion.setMesFinalTasaRec((params.get("mesFinalTasaRec")).toString());
            liquidacion.setAnioFinalTasaRec((Integer)(params.get("anioFinalTasaRec")));
            liquidacion.setImporteImprocedente(params.get("importeIC") != null ?
                                               GetBigDecimal.parse(params.get("importeIC")) : BigDecimal.ZERO);
            liquidacion.setFundamentacion((String)params.get("funMotivacion"));

            DyctLiquidacionDTO liquidacionTmp = daoLiquidacion.encontrar(resolComp);

            if (null == liquidacionTmp) {
                daoLiquidacion.insertar(liquidacion);
            } else {
                daoLiquidacion.actualizar(liquidacion);
            }

        } catch (Exception se) {
            throw new SIATException(se);
        }
    }

    private Map<String, Object> generarDocLiquidacion(Map<String, Object> params) throws SIATException {

        Integer idPlantilla;
        Boolean esGranContribuyente = (Boolean)params.get("esGranContribuyente");

        int clave = (Integer)params.get("claveAdm");

        if (clave == ConstantesDyCNumerico.VALOR_81 || clave == ConstantesDyCNumerico.VALOR_82) {
            idPlantilla = ConstantesDyCNumerico.VALOR_137;
        } else if (esGranContribuyente) {
            idPlantilla = ConstantesACC.PLANTILLA_LIQUIDACION_GRAN_CONTTE;
        } else {
            idPlantilla = ConstantesACC.PLANTILLA_LIQUIDACION;
        }

        params.put("idPlantilla", idPlantilla);
        params.put("claveAdm", params.get("claveAdm"));
        params.put("fecha", new Date());
        /**      TODO: Obtener Valor dinámicamente
              datos.put("impTotal", ConstantesACC.DUMMY_IMPORTETOTAL); */
        params.put("queryAConsultar", idPlantilla);

        Map<String, Object> respPlantillador = servicePlantillador.templateCreated(params);
        if ((Boolean)respPlantillador.get("success")) {
            LOGGER.info("\n###\n El documento se genero satisfactoriamente: \n Archivo ->" +
                        (String)respPlantillador.get("url") +
                        (String)respPlantillador.get(Constantes.NombresParametros.NOMBRE_ARCHIVO));
        } else {
            LOGGER.debug("\n###\n OCURRIO UN ERROR EN EL PLANTILLADOR");
        }
        respPlantillador.put("idPlantilla", idPlantilla);

        return respPlantillador;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT,
                   rollbackFor = SIATException.class)
    public String enviarAAprobacion(Map<String, Object> params) throws SIATException {
        PistaAuditoriaVO pistaAuditoria = new PistaAuditoriaVO();
        String mensaje = null;

        DyccAprobadorDTO aprobador = new DyccAprobadorDTO();
        aprobador.setNumEmpleado((Integer)params.get("idSuperior"));
        try {
            // actualizar el estatus de la compensacion a "PendienteDeResolver"
            DycpServicioDTO servicio = new DycpServicioDTO();
            servicio.setNumControl((String)params.get(Constantes.NombresParametros.NUMERO_CONTROL));
            DycpCompensacionDTO compensacion = new DycpCompensacionDTO();
            compensacion.setNumControl((String)params.get(Constantes.NombresParametros.NUMERO_CONTROL));
            compensacion.setDyccAprobadorDTO(aprobador);
            compensacion.setDycpServicioDTO(servicio);
            compensacion.setDyccEstadoCompDTO(Constantes.EstadosCompensacion.EN_REVISION);
            daoCompensacion.actualizar(compensacion);

            insertarLiquidacion(compensacion, params);

            // GUARDAR DOCUMENTO
            InputStream secuenciaEntrada = (InputStream)params.get("secuenciaEntrada");
            String url =
                serviceAdmCC.guardarArchivo(servicio, secuenciaEntrada, (String)params.get(Constantes.NombresParametros.NOMBRE_ARCHIVO));
            // Insertar documento
            DyctDocumentoDTO documento = new DyctDocumentoDTO();
            documento.setFechaRegistro(new Date());
            documento.setDyccAprobadorDTO(aprobador);
            documento.setDyccEstadoReqDTO(Constantes.EstadosReq.EMITIDO);
            documento.setDyccEstadoDocDTO(Constantes.EstadosDoc.EN_APROBACION);
            documento.setNombreArchivo((String)params.get(Constantes.NombresParametros.NOMBRE_ARCHIVO));
            documento.setNumControlDoc((String)params.get("numControlDoc"));
            documento.setUrl(url);
            documento.setDycpServicioDTO(servicio);
            documento.setDyccTipoDocumentoDTO(Constantes.TiposDocumento.LIQUIDACION);
            String idPlantilla = (String)params.get(Constantes.NombresParametros.NOMBRE_ARCHIVO);
            DyccMatDocumentosDTO plantilla = new DyccMatDocumentosDTO();
            plantilla.setIdPlantilla(Integer.parseInt(idPlantilla.substring(ConstantesDyCNumerico.VALOR_0,
                                                                            ConstantesDyCNumerico.VALOR_3)));
            documento.setDyccMatDocumentosDTO(plantilla);
            daoDocumento.insertarE(documento);

            // PISTAS DE AUDITORIA
            pistaAuditoria.setIdProceso(Procesos.DYC00012);
            pistaAuditoria.setIdGrupoOperacion(ConstantesACC.IDGRUPOOPERACION_EMITIRLIQUIDACION);
            pistaAuditoria.setIdMensaje(ConstantesDyCNumerico.VALOR_3);
            pistaAuditoria.setMovimiento(ConstantesACC.MOVIMIENTO_PISTASA_EMITE_LIQUIDACION);
            pistaAuditoria.setIdentificador((String)params.get("numControlDoc"));

            serviceRegistroPistasAud.registrarPistaAuditoria(pistaAuditoria);

            mensaje =
                    serviceRegistroPistasAud.obtenerMensaje((Character)params.get("tipoLiquidacion") == 'C' ? ConstantesDyCNumerico.VALOR_3 :
                                                            ConstantesDyCNumerico.VALOR_8,
                                                            ConstantesDyCNumerico.VALOR_3,
                                                            ConstantesDyCNumerico.VALOR_57);
        } catch (NumberFormatException nfe) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + nfe.getMessage());
            throw new SIATException("Error al enviar a aprobación", nfe);
        } catch (SIATException se) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + se.getMessage());
            throw new SIATException("Error al enviar a aprobación", se);
        }
        return mensaje;
    }

}
