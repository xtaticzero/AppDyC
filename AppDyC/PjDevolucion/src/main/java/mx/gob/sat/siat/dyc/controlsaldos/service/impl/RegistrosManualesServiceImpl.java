package mx.gob.sat.siat.dyc.controlsaldos.service.impl;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.mat.dyc.controlsaldos.service.impl.ObtenerFechaHistoricaServiceImpl;
import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.mat.dyc.obtenersesion.service.impl.RegistroPistasAuditoriaServiceImpl;
import mx.gob.sat.siat.dyc.casocomp.util.Utils;
import mx.gob.sat.siat.dyc.catalogo.dao.DyccTipoResolDAO;
import mx.gob.sat.siat.dyc.controlsaldos.service.RegistrosManualesService;
import mx.gob.sat.siat.dyc.controlsaldos.service.icep.impl.CalcularSaldoRemanenteICEPServiceImpl;
import mx.gob.sat.siat.dyc.controlsaldos.service.icep.impl.ConvertirSaldoServiceImpl;
import mx.gob.sat.siat.dyc.dao.detalleicep.DyctSaldoIcepDAO;
import mx.gob.sat.siat.dyc.dao.movsaldo.DyctMovDevolucionDAO;
import mx.gob.sat.siat.dyc.dao.movsaldo.DyctMovSaldoDAO;
import mx.gob.sat.siat.dyc.dao.tiposerv.IDycpServicioDAO;
import mx.gob.sat.siat.dyc.dao.util.DyctCompHistoricaDAO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccMovIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctCompHistoricaDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctMovDevolucionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctMovSaldoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoResolDTO;
import mx.gob.sat.siat.dyc.util.BuscadorConstantes;
import mx.gob.sat.siat.dyc.util.common.ItemComboBean;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import mx.gob.sat.siat.dyc.util.constante.ConstantesACC;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service(value = "bdRegistrosManuales")
public class RegistrosManualesServiceImpl implements RegistrosManualesService
{
    private static final Logger LOG = Logger.getLogger(RegistrosManualesServiceImpl.class.getName());

    @Autowired
    private DyccTipoResolDAO daoTipoResol;

    @Autowired
    private DyctMovDevolucionDAO daoMovDevolucion;

    @Autowired
    private DyctMovSaldoDAO daoMovSaldo;

    @Autowired
    private ConvertirSaldoServiceImpl convertidorSaldos;

    @Autowired
    private ObtenerFechaHistoricaServiceImpl serviceObtFechaHist;

    @Autowired
    private DyctCompHistoricaDAO daoMovCompensacion;

    @Autowired
    private DyctSaldoIcepDAO daoSaldoIcep;
    
    @Autowired
    private IDycpServicioDAO daoServicio;

    @Autowired
    private CalcularSaldoRemanenteICEPServiceImpl serviceCalcRemanente;
    
    @Autowired
    private RegistroPistasAuditoriaServiceImpl registroPistasAudService;

    @Autowired
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    private static final String NUMCONTROL = "numControl";
    private static final String INSERTO = "inserto";

    @Override
    public Map<String, Object> obtenerInfoInicial()
    {
        List<DyccTipoResolDTO> tiposResolucion = daoTipoResol.buscarTiposResolucion(Constantes.TiposServicio.SOLICITUD_DEVOLUCION.getIdTipoServicio());
        List<ItemComboBean> beansTiposResolucion = new ArrayList<ItemComboBean> ();
        for(DyccTipoResolDTO dto : tiposResolucion)
        {
            ItemComboBean bean = new ItemComboBean();
            bean.setId(dto.getIdTipoResol());
            bean.setDescripcion(dto.getDescripcion());
            beansTiposResolucion.add(bean);
        }
        
        Map<String, Object> infoInicial = new HashMap<String, Object>();
        infoInicial.put("tiposResolucion", beansTiposResolucion);
        return infoInicial;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = SIATException.class)
    public Map<String, Object> insertarDevolucion(Map<String, Object> params)  throws SIATException 
    {
        Map<String, Object> respuesta = new HashMap<String, Object>();
        LOG.debug("INICIO insertarDevolucion");
        LOG.debug("idTipoResolucion ->" + (Integer)params.get("idTipoResolucion") + "<-");
        DyccTipoResolDTO tipoResolucion = BuscadorConstantes.obtenerTipoResolucion((Integer)params.get("idTipoResolucion"));
        LOG.debug("tipoResolucion ->" + tipoResolucion + "<-");
        DyctSaldoIcepDTO saldoIcep = new DyctSaldoIcepDTO();
        saldoIcep.setIdSaldoIcep((Integer)params.get("idSaldoIcep"));

        double importeAutorizado = params.get("importeAutorizado") != null ? (Double)params.get("importeAutorizado") : 0d;
        /**Desistida*/
        if(tipoResolucion == Constantes.TiposResolucion.DESISTIDA){
            importeAutorizado = 0d;
        }

        double actualizacion = params.get("actualizacion") != null ? (Double)params.get("actualizacion") : 0d;
        double intereses = params.get("intereses") != null ? (Double)params.get("intereses") : 0d;
        double retIntereses = params.get("retIntereses") != null ? (Double)params.get("retIntereses") : 0d;
        double importeCompensado = params.get("importeCompensado") != null ? (Double)params.get("importeCompensado") : 0d;
        double importeNetoDevolver = params.get("importeNetoDevolver") != null ? (Double)params.get("importeNetoDevolver") : 0d;
        double importeNegado = params.get("importeNegado") != null ? (Double)params.get("importeNegado") : 0d;

        DyctMovDevolucionDTO devolucionManual = new DyctMovDevolucionDTO();
        devolucionManual.setNumControl((String)params.get(NUMCONTROL));
        devolucionManual.setFechaResolucion((Date)params.get("fechaResolucion"));
        devolucionManual.setImporteSolDev( new BigDecimal((Double)params.get("importeSolicitado")));
        devolucionManual.setImporteAutorizado(new BigDecimal(importeAutorizado));
        devolucionManual.setImporteNegado(new BigDecimal(importeNegado));
        devolucionManual.setActualizacion(new BigDecimal(actualizacion));
        devolucionManual.setIntereses(new BigDecimal(intereses));
        devolucionManual.setImporteNetoDev(new BigDecimal(importeNetoDevolver));
        devolucionManual.setIdTipoResol(tipoResolucion);
        devolucionManual.setDyctSaldoIcepDTO(saldoIcep);
        devolucionManual.setRetIntereses(new BigDecimal(retIntereses));
        devolucionManual.setImpCompensado(new BigDecimal(importeCompensado));
        LOG.debug("devolucionManual ->" + devolucionManual + "<-");
        daoMovDevolucion.insertar(devolucionManual);        
        /**guardar cargo*/

        /**Autorizada Total*/
        /**Autorizada Parcial con remanente disponible*/
        if(tipoResolucion == Constantes.TiposResolucion.AUTORIZADA_TOTAL || tipoResolucion == Constantes.TiposResolucion.AUTORIZADA_PARCIAL_REM_DISP)
        {
            DyctMovSaldoDTO cargoImpAutorizado = new DyctMovSaldoDTO();
            cargoImpAutorizado.setDyctSaldoIcepDTO(saldoIcep);
            cargoImpAutorizado.setImporte(new BigDecimal(importeAutorizado));
            cargoImpAutorizado.setFechaRegistro(new Date());
            cargoImpAutorizado.setFechaOrigen(devolucionManual.getFechaResolucion());
            cargoImpAutorizado.setDyccMovIcepDTO(Constantes.MovsIcep.CARGO_DEV_HIST);
            cargoImpAutorizado.setIdOrigen(devolucionManual.getNumControl());
            daoMovSaldo.insertar(cargoImpAutorizado);        
        }

        /**Negada*/
        if(tipoResolucion == Constantes.TiposResolucion.NEGADA || tipoResolucion == Constantes.TiposResolucion.AUTORIZADA_PARCIAL_REM_NEGADO)
        {
            DyctMovSaldoDTO cargoImpNegado = new DyctMovSaldoDTO();
            cargoImpNegado.setDyctSaldoIcepDTO(saldoIcep);
            cargoImpNegado.setImporte(new BigDecimal(importeNegado));
            cargoImpNegado.setFechaRegistro(new Date());
            cargoImpNegado.setFechaOrigen(devolucionManual.getFechaResolucion());
            cargoImpNegado.setDyccMovIcepDTO(Constantes.MovsIcep.CARGO_SALDONEG_DEV_HIST);
            cargoImpNegado.setIdOrigen(devolucionManual.getNumControl());
            daoMovSaldo.insertar(cargoImpNegado);        
        }
        
        /**RegistraPista*/
        Map<String, String> valsDinamicosPistAud = new HashMap<String, String>();
        AccesoUsr accesoUsrL = serviceObtenerSesion.execute();
        valsDinamicosPistAud.put("<NumeroDeControl>", params.get(NUMCONTROL).toString());
        valsDinamicosPistAud.put("<NombreEmpleado>", accesoUsrL.getNombreCompleto());
        valsDinamicosPistAud.put("<NumEmpleado>", accesoUsrL.getNumeroEmp());
        registroPistasAudService.registrarPistaAuditoria(ConstantesACC.PISTAS_IDMENSAJE_CREAR_DEVOLUCION, ConstantesACC.IDGRUPOOPERACION_CONSULTA_MOV_ICEP, ConstantesACC.MOVIMIENTO_PISTASA_AGREGA_DEVOLUCION, valsDinamicosPistAud);

        respuesta.put(INSERTO, Boolean.TRUE);
        return respuesta;
    }

    @Override
    public Double calcularActualizacion(Integer idSaldoIcep, Date fechaDestino, Double importeAutorizado)  throws SIATException 
    {
        DyctSaldoIcepDTO saldoIcep = new DyctSaldoIcepDTO();
        saldoIcep.setIdSaldoIcep(idSaldoIcep);
        Date fechaOrigen = serviceObtFechaHist.execute(saldoIcep);
        LOG.debug("fechaOrigen ->" + fechaOrigen);
        return convertidorSaldos.execute(fechaOrigen, fechaDestino, importeAutorizado) - importeAutorizado;
    }
    
    @Override
    @Transactional(readOnly = false, rollbackFor = SIATException.class)
    public Map<String, Object> insertarCompensacion(Map<String, Object> params) throws SIATException
    {
        Map<String, Object> respuesta = new HashMap<String, Object> ();
        DyctSaldoIcepDTO icepOrigen = new DyctSaldoIcepDTO();
        icepOrigen.setIdSaldoIcep((Integer)params.get("idSaldoIcep"));

        DyccConceptoDTO concepto = new DyccConceptoDTO();
        concepto.setIdConcepto((Integer)params.get("idConceptoDest"));
        DyccPeriodoDTO periodo = new DyccPeriodoDTO();
        periodo.setIdPeriodo((Integer)params.get("idPeriodoDest"));
        DyccEjercicioDTO ejercicio = new DyccEjercicioDTO();
        ejercicio.setIdEjercicio((Integer)params.get("idEjercicioDest"));

        DyctSaldoIcepDTO icepDestino;
        List<DyctSaldoIcepDTO> iceps = daoSaldoIcep.selecXRfcConceptoEjercicioPeriodo((String)params.get("rfc"), concepto, ejercicio, periodo);
        if(!iceps.isEmpty()){
            icepDestino = iceps.get(0);
            LOG.debug("el icep destino ya existe en DyC, se utilizara para la compensacion manual; idsaldoicep ->" + icepDestino.getIdSaldoIcep());
        }
        else
        {
            LOG.debug("el icep destino NO existe; se insertara un nuevo registro");
            icepDestino = new DyctSaldoIcepDTO();
            icepDestino.setRfc((String)params.get("rfc"));
            LOG.debug("concepto iddd ->" + concepto.getIdConcepto());
            icepDestino.setDyccConceptoDTO(concepto);
            icepDestino.setDyccPeriodoDTO(periodo);
            icepDestino.setDyccEjercicioDTO(ejercicio);
            daoSaldoIcep.insert(icepDestino);
          }

        Map<String, Object> datosRemanente = serviceCalcRemanente.obtenerDatosRemanente(icepOrigen);
        Date fechaCalcRem = (Date)datosRemanente.get("fechaCalculo");
        Double remanente = (Double)datosRemanente.get("remanenteReal");
        
        
        Double importeCompensado = (Double)params.get("importeComp");
        Date fechaDeclDest = (Date)params.get("fechaDeclDest");
        Double importeCompDeflactado = convertidorSaldos.execute(fechaDeclDest, fechaCalcRem, importeCompensado);
        
        LOG.debug("remanente ->" + remanente + "<-; fechaCalcRem ->" + fechaCalcRem + "<-");

        if(importeCompDeflactado > remanente)
        {
            respuesta.put(INSERTO, Boolean.FALSE);
            respuesta.put("mensaje","El importe compensado deflactado (" + Utils.formatearMoneda((importeCompDeflactado)) + ") no puede exceder el remanente del saldo a favor (" +
                                    Utils.formatearMoneda(remanente) + ")");
            
        /** Double importeCompMaximo = serviceCalcRemanente.execute(icepOrigen, fechaDeclDest);
            respuesta.put("mensaje","El importe maximo a compensar con el saldo a favor de este ICEP (en una declaración cuya fecha de presentación es " + Utils.formatearFecha(fechaDeclDest) +
                                    ") es " + Utils.formatearMoneda(importeCompMaximo));*/

            
            return respuesta;
        }

        DyccTipoResolDTO tipoResol = BuscadorConstantes.obtenerTipoResolucion((Integer)params.get("idTipoResol"));

        DyctCompHistoricaDTO compManual = new DyctCompHistoricaDTO();
        compManual.setNumControl((String)params.get(NUMCONTROL));
        compManual.setFechaDeclaraDest(fechaDeclDest);
        compManual.setImporteCompensado(new BigDecimal(importeCompensado));
        compManual.setImporteLiquidado((BigDecimal)params.get("importeLiquidado"));
        compManual.setFechaResolucion((Date)params.get("fechaResol"));
        compManual.setDyccTipoResolDTO(tipoResol);
        compManual.setDyctSaldoIcepOrigenDTO(icepOrigen);
        compManual.setDyctSaldoIcepDestinoDTO(icepDestino);
        compManual.setNumDocDeterminante((String)params.get("numDocDeterminante"));
        compManual.setNotas((String)params.get("notas"));
        daoMovCompensacion.insertar(compManual);
        
         DyccMovIcepDTO movIcepDTO;
         if(tipoResol == Constantes.TiposResolucion.SALDOAFAVOR_IMPROCEDENTE){
             movIcepDTO = Constantes.MovsIcep.SALDO_IMPROCEDENTE;
         }else if(tipoResol == Constantes.TiposResolucion.COMPENSACION_IMPROCEDENTE){
             movIcepDTO = Constantes.MovsIcep.CARGO_COMPENSACION_IMPROCEDENTE;
         }else{
             movIcepDTO = Constantes.MovsIcep.CARGO_COMP_HIST;
         }
         
        /**insertar movimientos*/
        DyctMovSaldoDTO cargoComp = new DyctMovSaldoDTO();
        cargoComp.setDyctSaldoIcepDTO(icepOrigen);
        cargoComp.setImporte(new BigDecimal((Double)params.get("importeComp")));
        cargoComp.setFechaRegistro(new Date());
        cargoComp.setFechaOrigen(compManual.getFechaDeclaraDest());
        cargoComp.setDyccMovIcepDTO(movIcepDTO);
        cargoComp.setIdOrigen((String)params.get(NUMCONTROL));
        daoMovSaldo.insertar(cargoComp);

        if(tipoResol == Constantes.TiposResolucion.SALDOAFAVOR_IMPROCEDENTE){
            LOG.debug("No se hace ningun abono, el importe a liquidar se cobrará al destino, regresar mensaje");
        }
        else if(tipoResol == Constantes.TiposResolucion.COMPENSACION_IMPROCEDENTE){
            LOG.debug("Se le hace un abono, el importe a liquidar se cobrará al destino");
            DyctMovSaldoDTO abonoCompImpro = new DyctMovSaldoDTO();
            abonoCompImpro.setDyctSaldoIcepDTO(icepOrigen);
            abonoCompImpro.setImporte(new BigDecimal((Double)params.get("importeComp")));
            abonoCompImpro.setFechaRegistro(new Date());
            abonoCompImpro.setFechaOrigen(compManual.getFechaDeclaraDest());
            abonoCompImpro.setDyccMovIcepDTO(Constantes.MovsIcep.COMPENSACION_IMPROCEDENTE);
            abonoCompImpro.setIdOrigen((String)params.get(NUMCONTROL));
            daoMovSaldo.insertar(abonoCompImpro);
        }
        
        /**RegistraPista*/
        Map<String, String> valsDinamicosPistAud = new HashMap<String, String>();
        AccesoUsr accesoUsrL = serviceObtenerSesion.execute();
        valsDinamicosPistAud.put("<NumeroDeControl>", params.get(NUMCONTROL).toString());
        valsDinamicosPistAud.put("<NombreEmpleado>", accesoUsrL.getNombreCompleto());
        valsDinamicosPistAud.put("<NumEmpleado>", accesoUsrL.getNumeroEmp());
        registroPistasAudService.registrarPistaAuditoria(ConstantesACC.PISTAS_IDMENSAJE_CREAR_COMPENSACION, ConstantesACC.IDGRUPOOPERACION_CONSULTA_MOV_ICEP, ConstantesACC.MOVIMIENTO_PISTASA_AGREGA_COMPENSACION, valsDinamicosPistAud);

        respuesta.put(INSERTO, Boolean.TRUE);
        return respuesta;
    }

    @Override
    public Boolean existeNumControl(String numControl)
    {
        LOG.debug("numControl ->" + numControl + "<-");

        try {
            if(daoServicio.encontrar(numControl) != null){
                return Boolean.TRUE;
            }
            
            if(daoMovCompensacion.encontrarXNumcontrol(numControl) != null){
                return Boolean.TRUE;
            }
            
            if(daoMovDevolucion.consultar(numControl) != null){
                LOG.debug("el numcontrol ->" + numControl + "<- Ya existe para una devolucion Histórica");
                return Boolean.TRUE;
            }
        }
        catch (SIATException e) {
            LOG.error(e.getMessage());
        }

        return Boolean.FALSE;
    }

}
