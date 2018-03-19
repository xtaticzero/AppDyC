package mx.gob.sat.siat.dyc.controlsaldos.service.impl;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mx.gob.sat.mat.dyc.controlsaldos.service.ControlSaldoImporte;

import mx.gob.sat.mat.dyc.controlsaldos.service.ObtenerDeclaracionesDHWService;
import mx.gob.sat.mat.dyc.controlsaldos.service.impl.ObtenerFechaHistoricaServiceImpl;
import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.mat.dyc.obtenersesion.service.impl.RegistroPistasAuditoriaServiceImpl;
import mx.gob.sat.siat.dyc.casocomp.util.Utils;
import mx.gob.sat.siat.dyc.controlsaldos.service.DetalleIcepService;
import mx.gob.sat.siat.dyc.controlsaldos.service.DevueltoHelper;
import mx.gob.sat.siat.dyc.controlsaldos.util.ConstantesCS;
import mx.gob.sat.siat.dyc.controlsaldos.vo.DeclaracionDwhVO;
import mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.utility.FilaGridCompensacionesBean;
import mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.utility.FilaGridDeclaracionesBean;
import mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.utility.FilaGridDevolucionesBean;
import mx.gob.sat.siat.dyc.dao.compensacion.IDycpCompensacionDAO;
import mx.gob.sat.siat.dyc.dao.detalleicep.DyctSaldoIcepDAO;
import mx.gob.sat.siat.dyc.dao.icep.DyctDeclaraIcepDAO;
import mx.gob.sat.siat.dyc.dao.movsaldo.DyctMovDevolucionDAO;
import mx.gob.sat.siat.dyc.dao.movsaldo.DyctMovSaldoDAO;
import mx.gob.sat.siat.dyc.dao.regsolicitud.DycpSolicitudDAO;
import mx.gob.sat.siat.dyc.dao.util.DyctCompHistoricaDAO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaIdentificacionDTO;
import mx.gob.sat.siat.dyc.domain.controlsaldos.ControlSaldoImportesDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaracionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctCompHistoricaDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaraIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctMovDevolucionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctMovSaldoDTO;
import mx.gob.sat.siat.dyc.servicio.service.contribuyente.PersonaIDCService;
import mx.gob.sat.siat.dyc.util.BuscadorConstantes;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.common.ParamOutputTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import mx.gob.sat.siat.dyc.util.constante.ConstantesACC;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service(value = "bdDetalleIcep")
public class DetalleIcepServiceImpl implements DetalleIcepService
{
    private static final Logger LOG = Logger.getLogger(DetalleIcepServiceImpl.class);
    
    @Autowired
    private DyctSaldoIcepDAO daoSaldoIcep;

    @Autowired
    private DyctDeclaraIcepDAO daoDeclaraIcep;

    @Autowired
    private DyctMovSaldoDAO daoMovSaldo;

    @Autowired
    private DyctCompHistoricaDAO daoMovCompensacion;

    @Autowired
    private DyctMovDevolucionDAO daoMovDevolucion;
    
    @Autowired
    private ObtenerDeclaracionesDHWService serviceObtenerDeclsDWH;
    
    @Autowired
    private PersonaIDCService serviceIdentPersona;
    
    @Autowired
    private RegistroPistasAuditoriaServiceImpl registroPistasAudService;
    
    @Autowired
    private CompensadoHistoricoHelper helperCompHist;
    
    @Autowired
    private CargarSaldoIcepServiceImpl serviceCargarSaldoIcep;
    
    @Autowired
    private ObtenerSesionServiceImpl serviceObtenerSesion;
    
    @Autowired
    private MovimientosSaldoBussinesDel delegateAjustesSaldos;
    
    @Autowired(required = true)
    private DycpSolicitudDAO dycpSolicitudDAO;
    
    @Autowired
    private IDycpCompensacionDAO daoCompensacion;
    
    @Autowired
    private ControlSaldoImporte controlSaldoImporteService;
    
    @Autowired
    private ObtenerFechaHistoricaServiceImpl serviceObtenerFechaHistorica;
    
    @Autowired
    private DevueltoHelper devueltoHelper;
    
    private static final String IMPORTE_EFECTUADO = "importeEfectuado";
    private static final String IMPORTE_ACREDITACION = "importeAcreditacion";
    private static final String NOMBRE_EMPLEADO = "<NombreEmpleado>";
    private static final String NUMERO_EMPLEADO = "<NumEmpleado>";
    private static final String ID_OPERACION = "<IdOperacion>";
    private static final String FECHA_CALCULO = "fechaCalculo";
    private static final String FECHA_PRIMER_DEC_VALIDAD = "fechaPrimerDecValidada";
    
    @Override
    public Map<String, Object> obtenerInfoInicial(Integer idSaldoIcep, AccesoUsr accesoUsrL) throws SIATException {
        DyctSaldoIcepDTO saldoIcep = serviceCargarSaldoIcep.execute(idSaldoIcep);
        Map<String, Object> infoInicial = obtenerInfo(saldoIcep);
        infoInicial.put("permisoAjustar", delegateAjustesSaldos.obtenerPermisoAjuste(accesoUsrL));
        return infoInicial;
    }

    @Override
    public Map<String, Object> obtenerInfoInicialConsulta(String numcontrol) throws SIATException {
        Integer idSaldoIcep;
        if (numcontrol.toUpperCase().startsWith("AC") || numcontrol.toUpperCase().startsWith("CC")) {
            DycpCompensacionDTO compensacionDTO = daoCompensacion.encontrar(numcontrol);
            idSaldoIcep = compensacionDTO.getDyctSaldoIcepOrigenDTO().getIdSaldoIcep();
        } else {
            DycpSolicitudDTO solicitud = dycpSolicitudDAO.consultarSolicitud(numcontrol);
            idSaldoIcep = solicitud.getDyctSaldoIcepDTO().getIdSaldoIcep();
        }
        DyctSaldoIcepDTO saldoIcep = serviceCargarSaldoIcep.execute(idSaldoIcep);
        return obtenerInfo(saldoIcep);
    }
    
    private Map<String, Object> obtenerInfo(DyctSaldoIcepDTO saldoIcep) throws SIATException{
        
        ControlSaldoImportesDTO controlSaldoImportes = controlSaldoImporteService.calcularImportes(saldoIcep);
        LOG.info("calculos nuevo:: " + controlSaldoImportes);
        Map<String, Object> infoInicial = new HashMap<String, Object>();
        infoInicial.put("existenAjustes", existenAjustes(saldoIcep));
        infoInicial.put("descripcionIcep", saldoIcep.toString());
        cargarInfoInicialFija(infoInicial, saldoIcep);
        
        List<FilaGridDeclaracionesBean> declaraciones = crearFilasDeclaraciones(saldoIcep.getDyctDeclaraIcepList());
        List<FilaGridDevolucionesBean> devoluciones = devueltoHelper.obtenerDevoluciones(saldoIcep);
        infoInicial.put("devoluciones", devoluciones);       
        
        if(declaraciones.isEmpty()){
            obtenerInfoSaldoAFavor(saldoIcep, infoInicial);
        }
        List<DyctDeclaraIcepDTO> listaCompleta = new ArrayList<DyctDeclaraIcepDTO>();
        listaCompleta.addAll(saldoIcep.getDyctDeclaraIcepListCancel());
        listaCompleta.addAll(saldoIcep.getDyctDeclaraIcepList());
        declaraciones = crearFilasDeclaraciones(listaCompleta);
        infoInicial.put("declaracionesCanceladas", saldoIcep.getDyctDeclaraIcepListCancel());
        infoInicial.put("declaracionesActivas", saldoIcep.getDyctDeclaraIcepList());
        infoInicial.put("declaraciones", declaraciones);

        Date fechaCalculo = serviceObtenerFechaHistorica.execute(saldoIcep);
        Date fechaPrimerDecValidada = serviceObtenerFechaHistorica.obtenerPrimerDeclaracionValida(saldoIcep);
        infoInicial.put(FECHA_CALCULO, fechaCalculo);
        infoInicial.put(FECHA_PRIMER_DEC_VALIDAD, fechaPrimerDecValidada);
        infoInicial.put("msjRemanenteReal", "remanente al " + Utils.formatearFecha(fechaCalculo) +
                                            " ->" + Utils.formatearMoneda(controlSaldoImportes.getRemanente().doubleValue()));

        infoInicial.put("ultimoImpDecl",  controlSaldoImportes.getSaldoFavor().doubleValue());
        infoInicial.put(IMPORTE_EFECTUADO, controlSaldoImportes.getEfectuado().doubleValue());
        infoInicial.put(IMPORTE_ACREDITACION, controlSaldoImportes.getAcreditamiento().doubleValue());

        List<FilaGridCompensacionesBean> filas = helperCompHist.obtenerCompensaciones(saldoIcep);
        infoInicial.put("compensaciones", filas);
        infoInicial.put("importeResuelto", controlSaldoImportes.getResuelto().doubleValue());
        infoInicial.put("remanenteFavCargo",  controlSaldoImportes.getRemanente().doubleValue());

        saldoIcep.setRemanente(controlSaldoImportes.getRemanente());
        saldoIcep.setFechaBaseCalculo(fechaCalculo);
        saldoIcep.setActRemanente(new Date());
        daoSaldoIcep.actualizarRemFebaseActrem(saldoIcep);
     
        return infoInicial;
    }
    
    private Boolean existenAjustes(DyctSaldoIcepDTO saldoIcep)
    {
        LOG.debug("saldoIcep.getDyctMovSaldoList() ->" + saldoIcep.getDyctMovSaldoList());
        if(saldoIcep.getDyctMovSaldoList() != null){
            for(DyctMovSaldoDTO movSaldo : saldoIcep.getDyctMovSaldoList()){
                if(movSaldo.getDyctAccionMovSalDTOList() != null && !movSaldo.getDyctAccionMovSalDTOList().isEmpty()){
                    return Boolean.TRUE;
                }
            }
        }

        return Boolean.FALSE;
    }

    private void cargarInfoInicialFija(Map<String, Object> infoInicial, DyctSaldoIcepDTO saldoIcep) throws SIATException {
        String nombreRazonSocial = obtenerNombreRazonSocialRFCAmpliado(saldoIcep.getRfc());
        infoInicial.put("idSaldoIcep", saldoIcep.getIdSaldoIcep());
        infoInicial.put("nombreRazonSocial", nombreRazonSocial);
        infoInicial.put("rfc", saldoIcep.getRfc());
        infoInicial.put("impuesto", saldoIcep.getDyccConceptoDTO().getDyccImpuestoDTO().getDescripcion());
        DyccConceptoDTO cpto = saldoIcep.getDyccConceptoDTO();
        infoInicial.put("concepto", cpto.getIdConcepto() + " - " + cpto.getDescripcion());
        infoInicial.put("periodo", saldoIcep.getDyccPeriodoDTO().getDescripcion());
        infoInicial.put("ejercicio", saldoIcep.getDyccEjercicioDTO().getIdEjercicio());
        String descrOrigenSaldo = saldoIcep.getDyccOrigenSaldoDTO() != null ? saldoIcep.getDyccOrigenSaldoDTO().getDescripcion() : "";
        infoInicial.put("tipoSaldo", descrOrigenSaldo);
    }

    private void obtenerInfoSaldoAFavor(DyctSaldoIcepDTO saldoIcep, Map<String, Object> infoInicial)
    {
        DyctDeclaracionDTO infoSaldoAFavor = null;
        LOG.debug("numero de solicitudes ->" + saldoIcep.getDycpSolicitudList().size());
        for(DycpSolicitudDTO sol : saldoIcep.getDycpSolicitudList())
        {
            if(!sol.getDycpServicioDTO().getDyctDeclaracionList().isEmpty()){
                LOG.debug("numero de info saldo a favor de la solicitud iterada ->" + sol.getDycpServicioDTO().getDyctDeclaracionList().size());
                infoSaldoAFavor = sol.getDycpServicioDTO().getDyctDeclaracionList().get(0);
                infoInicial.put("origenSaldo", sol.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDycaServOrigenDTO().getDyccOrigenSaldoDTO().getDescripcion());
                break;
            }
        }

        if(infoSaldoAFavor != null)
        {
            LOG.debug("se encontro un DYCT_DECLARACION ->" + infoSaldoAFavor + "<- id:" + infoSaldoAFavor.getIdDeclaracion());
            String strNumOperacion = infoSaldoAFavor.getNumOperacion() != null ? infoSaldoAFavor.getNumOperacion() : "";
            
            infoInicial.put("fechaPresentacion", infoSaldoAFavor.getFechaPresentacion());
            LOG.debug("fechaPresentacion ->" + infoSaldoAFavor.getFechaPresentacion());
            infoInicial.put("fechaCausacion", infoSaldoAFavor.getFechaCausacion());
            LOG.debug("fechaCausacion ->" + infoSaldoAFavor.getFechaCausacion());
            infoInicial.put("numOperacion", strNumOperacion);
            infoInicial.put("numDocumento", infoSaldoAFavor.getNumDocumento());
            LOG.debug("numDocumento ->" + infoSaldoAFavor.getNumDocumento());
            infoInicial.put("saldoAFavor", infoSaldoAFavor.getSaldoAfavor());
            LOG.debug("saldoAFavor ->" + infoSaldoAFavor.getSaldoAfavor());
        }
    }

    public String obtenerNombreRazonSocialRFCAmpliado(String rfc)
    {
        String nombreRazonSocial = "";
        try {
            ParamOutputTO<PersonaDTO> paramPersonaDTO = serviceIdentPersona.findContribuyente(rfc);
            PersonaDTO contribuyente = paramPersonaDTO.getOutput();
            if(contribuyente != null)
            {
                PersonaIdentificacionDTO ident = contribuyente.getPersonaIdentificacion();
                if(ident.getNombre() != null){
                    nombreRazonSocial = ident.getNombre() + " " + ident.getApPaterno() + " " + ident.getApMaterno();
                }
                else{
                    nombreRazonSocial = ident.getRazonSocial() + " " + ident.getTipoSociedad();
                }
            }
            else{
                LOG.debug("No se encontro el RFC ->" + rfc + "<-");
            }
        } catch (SIATException e) {
            LOG.error(e.getMessage());
        }
        return nombreRazonSocial;
    }

    private List<FilaGridDeclaracionesBean> crearFilasDeclaraciones(List<DyctDeclaraIcepDTO> declsIcep)
    {
        List<FilaGridDeclaracionesBean> filasDecls = new ArrayList<FilaGridDeclaracionesBean>();
        boolean edd = ConstantesCS.existenDeclsDominantes(declsIcep);
        LOG.debug("existenDeclsDominantes ->" + edd);
        for(DyctDeclaraIcepDTO dtoDeclara : declsIcep)
        {
            FilaGridDeclaracionesBean fila = new FilaGridDeclaracionesBean();
            fila.setIdDeclaraIcep(dtoDeclara.getIdDeclaraIcep());
            fila.setFechaPresentacion(dtoDeclara.getFechaPresentacion());
            fila.setNumOperacion(dtoDeclara.getNumOperacion());
            fila.setTipoDeclaracion(dtoDeclara.getDyccTipoDeclaraDTO().getDescripcion());
            LOG.debug("importe declarado ->" + dtoDeclara.getSaldoAFavor() + "<-");
            fila.setImporteDeclarado(dtoDeclara.getSaldoAFavor().doubleValue());
            fila.setFechaPresentacionStr(Utils.formatearFecha(dtoDeclara.getFechaPresentacion()));
            fila.setImporteDeclaradoStr(Utils.formatearMoneda(dtoDeclara.getSaldoAFavor().doubleValue()));
            fila.setValidadaDWH(dtoDeclara.getValidacionDWH() != null ? "Si" : "No");
            fila.setFechaFin(dtoDeclara.getFechaFin());
            LOG.debug("numOperacion:" + dtoDeclara.getNumOperacion() + "; ValidacionDWH ->" + dtoDeclara.getValidacionDWH());
            LOG.debug("origenDeclara ->" + dtoDeclara.getOrigenDeclara());
            boolean esDominante = dtoDeclara.getValidacionDWH() != null || Arrays.asList(ConstantesCS.getDeclsDoms()).contains(dtoDeclara.getOrigenDeclara());
            LOG.debug("numOperacion:" + dtoDeclara.getNumOperacion() + "; dtoDeclara.getOrigenDeclara() ->" + dtoDeclara.getOrigenDeclara() + "; esDominante ->" + esDominante);
            fila.setEfectiva(esDominante || !edd ? Boolean.TRUE : Boolean.FALSE);
            if(!fila.getEfectiva()){
                fila.setInfoAdicional(ConstantesCS.MSJ_DECL_NO_EFECTIVA);
            }

            if(dtoDeclara.getOrigenDeclara() == null || dtoDeclara.getOrigenDeclara() == 0)
            {
                LOG.debug("no se cuenta con el OrigenDeclara ->" + dtoDeclara.getOrigenDeclara());
                if(dtoDeclara.getValidacionDWH() != null){
                    fila.setOrigenDeclara(ConstantesCS.LBL_ORIGEN_DWH);
                }
                else{
                    fila.setOrigenDeclara("Desconocido");
                }
            }
            else
            {
                LOG.debug("SI se cuenta con el OrigenDeclara ->" + dtoDeclara.getOrigenDeclara());
                switch(dtoDeclara.getOrigenDeclara())
                {
                    case Constantes.OrigenesDeclaracion.CONTRIBUYENTE:
                        fila.setOrigenDeclara("Contribuyente");
                    break;
                    case Constantes.OrigenesDeclaracion.MANUAL: 
                        fila.setOrigenDeclara(ConstantesCS.LBL_DECLARA_MANUAL);
                        StringBuilder sbDescrO = new StringBuilder("Agregada por ");
                        sbDescrO.append(dtoDeclara.getUsrRegistro());
                        sbDescrO.append(" el ");
                        sbDescrO.append(Utils.formatearFechaHora(dtoDeclara.getFechaRegistro()));
                        fila.setInfoAdicional(sbDescrO.toString());
                    break;
                    case Constantes.OrigenesDeclaracion.DATAWAREHOUSE: 
                        fila.setOrigenDeclara(ConstantesCS.LBL_ORIGEN_DWH);
                    break;
                    case Constantes.OrigenesDeclaracion.VALIDADA_EMPLEADO: 
                        fila.setOrigenDeclara(ConstantesCS.LBL_VALIDADA_MANUALMENTE);
                        StringBuilder sbInfoAdicValidManual = new StringBuilder("Validada por ");
                        sbInfoAdicValidManual.append(dtoDeclara.getUsrRegistro());
                        sbInfoAdicValidManual.append(" el ");
                        sbInfoAdicValidManual.append(Utils.formatearFechaHora(dtoDeclara.getValidacionDWH()));
                        sbInfoAdicValidManual.append(" Justificación: ");
                        sbInfoAdicValidManual.append(dtoDeclara.getNotas());
                        fila.setInfoAdicional(sbInfoAdicValidManual.toString());
                    break;
                    default:
                        fila.setOrigenDeclara("Desconocido"); 
                    break;
                }
            }

            fila.setNotas(dtoDeclara.getNotas());

            filasDecls.add(fila);
        }

        return filasDecls;
    }

    

    @Override
    public Map<String, Object> buscarDeclaraciones(Integer idSaldoIcep) throws SIATException
    {
        LOG.debug("idSaldoIcep ->" + idSaldoIcep + "<- ");

        DyctSaldoIcepDTO saldoIcep = daoSaldoIcep.encontrar(idSaldoIcep);
        
        List<DeclaracionDwhVO> declsDWH = serviceObtenerDeclsDWH.execute(saldoIcep);
        List<DyctDeclaraIcepDTO> declsRegistradas = daoDeclaraIcep.selecXSaldoicep(saldoIcep);

        LOG.debug("decls size ->" + declsDWH.size());

        for(DeclaracionDwhVO declaraDWH : declsDWH)
        {
            DyctDeclaraIcepDTO declaraRegistrada  = yaExisteDeclaracion(declsRegistradas, declaraDWH);
            
            if(declaraRegistrada != null){
                declaraRegistrada.setValidacionDWH(new Date());
                daoDeclaraIcep.actualizarValidacionDWH(declaraRegistrada);
            }
            else{
                afectarSaldosXRegistroDeclaracion(declaraDWH, saldoIcep);
            }
        }
        //TODO: regresar una respuesta con valores o cambiar el tipo de retorno del metodo a void
        return Collections.emptyMap();
    }

    private DyctDeclaraIcepDTO yaExisteDeclaracion(List<DyctDeclaraIcepDTO> declsRegistradas, DeclaracionDwhVO declaraDWH)
    {
        for(DyctDeclaraIcepDTO declaraRegistrada : declsRegistradas)
        {
            if(sonIguales(declaraRegistrada, declaraDWH)){
                return declaraRegistrada;
            }
        }
        return null;
    }

    private boolean sonIguales(DyctDeclaraIcepDTO declaraRegistrada, DeclaracionDwhVO declaraDWH)
    {
        if(declaraRegistrada.getNumOperacion().longValue() != declaraDWH.getNumOperacion().longValue()){
            return Boolean.FALSE;
        }

        if(declaraRegistrada.getSaldoAFavor().doubleValue() != declaraDWH.getSaldo().doubleValue()){
            return Boolean.FALSE;
        }

        Date fechaTReg = DateUtils.round( declaraRegistrada.getFechaPresentacion(), Calendar.DAY_OF_MONTH);
        Date fechaTDWH = DateUtils.round( declaraDWH.getFechaPresentacion(), Calendar.DAY_OF_MONTH);
        if(fechaTReg.compareTo(fechaTDWH) != 0){
            return Boolean.FALSE;
        }

        if(declaraRegistrada.getDyccTipoDeclaraDTO().getIdTipoDeclaracion().intValue() != declaraDWH.getTipoDeclaracion().intValue()){
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    private void afectarSaldosXRegistroDeclaracion(DeclaracionDwhVO declaraDWH, DyctSaldoIcepDTO saldoIcep) throws SIATException
    {
        Date ahora = new Date();
        DyctDeclaraIcepDTO declaraDTO = new DyctDeclaraIcepDTO();
        declaraDTO.setDyctSaldoIcepDTO(saldoIcep);
        declaraDTO.setNumOperacion(declaraDWH.getNumOperacion());
        declaraDTO.setFechaPresentacion(declaraDWH.getFechaPresentacion());
        declaraDTO.setDyccTipoDeclaraDTO(BuscadorConstantes.obtenerTipoDeclaracion(declaraDWH.getTipoDeclaracion()));
        declaraDTO.setSaldoAFavor(new BigDecimal(declaraDWH.getSaldo()));
        declaraDTO.setValidacionDWH(ahora);
        daoDeclaraIcep.insertar(declaraDTO);
        
        DyctMovSaldoDTO abonoAltaSaldo = new DyctMovSaldoDTO();
        abonoAltaSaldo.setDyctSaldoIcepDTO(saldoIcep);
        abonoAltaSaldo.setImporte(new BigDecimal(declaraDWH.getSaldo()));
        abonoAltaSaldo.setFechaRegistro(ahora);
        abonoAltaSaldo.setFechaOrigen(declaraDWH.getFechaPresentacion());
        abonoAltaSaldo.setDyccMovIcepDTO(Constantes.MovsIcep.ALTA_SALDO);
        abonoAltaSaldo.setIdOrigen(declaraDWH.getNumOperacion().toString());
        daoMovSaldo.insertar(abonoAltaSaldo);
    }

    @Override
    @Transactional
    public Map<String, Object> insertarDeclaracion(FilaGridDeclaracionesBean filaDeclara, 
                                                   Integer idSaldoIcep, String usuarioInserta)
    {
        Map<String, Object> respuesta = new HashMap<String, Object>();
        try
        {
            DyctSaldoIcepDTO saldoIcep = new DyctSaldoIcepDTO();
            saldoIcep.setIdSaldoIcep(idSaldoIcep);
            Date validacionDWH = new Date();

            DyctDeclaraIcepDTO declaraIcep = new DyctDeclaraIcepDTO();
            declaraIcep.setDyctSaldoIcepDTO(saldoIcep);
            declaraIcep.setNumOperacion(filaDeclara.getNumOperacion());
            
            Date fechaPresentacion = filaDeclara.getFechaPresentacion();

            Calendar calHoy = Calendar.getInstance();
            Calendar cal = (Calendar) Calendar.getInstance().clone();
            cal.setTime(fechaPresentacion);
            cal.set(Calendar.HOUR, calHoy.get(Calendar.HOUR));
            cal.set(Calendar.MINUTE, calHoy.get(Calendar.MINUTE));
            cal.set(Calendar.SECOND, calHoy.get(Calendar.SECOND));
            fechaPresentacion = cal.getTime();
            
            declaraIcep.setFechaPresentacion(fechaPresentacion);
            declaraIcep.setDyccTipoDeclaraDTO(BuscadorConstantes.obtenerTipoDeclaracion(filaDeclara.getIdTipoDeclaracion()));
            declaraIcep.setSaldoAFavor(new BigDecimal(filaDeclara.getImporteDeclarado()));
            declaraIcep.setValidacionDWH(validacionDWH);
            declaraIcep.setOrigenDeclara(Constantes.OrigenesDeclaracion.MANUAL);
            declaraIcep.setFechaRegistro(new Date());
            declaraIcep.setUsrRegistro(usuarioInserta); 
            declaraIcep.setNotas(filaDeclara.getNotas());

            DyctMovSaldoDTO abonoAltaSaldo = new DyctMovSaldoDTO();
            abonoAltaSaldo.setDyctSaldoIcepDTO(saldoIcep);
            abonoAltaSaldo.setImporte(declaraIcep.getSaldoAFavor());
            abonoAltaSaldo.setFechaRegistro(new Date());
            abonoAltaSaldo.setFechaOrigen(declaraIcep.getFechaPresentacion());
            abonoAltaSaldo.setDyccMovIcepDTO(Constantes.MovsIcep.ALTA_SALDO);
            abonoAltaSaldo.setIdOrigen(declaraIcep.getNumOperacion().toString());

            Map<String, String> valsDinamicosPistAud = new HashMap<String, String>();
            AccesoUsr accesoUsrL = serviceObtenerSesion.execute();
            valsDinamicosPistAud.put("<NumeroDeControl>", filaDeclara.getNumOperacion().toString());
            valsDinamicosPistAud.put(NOMBRE_EMPLEADO, accesoUsrL.getNombreCompleto());
            valsDinamicosPistAud.put(NUMERO_EMPLEADO, accesoUsrL.getNumeroEmp());

            executeTransactionInsertarDeclaraIcep(declaraIcep, abonoAltaSaldo, valsDinamicosPistAud);
            respuesta.put(KEY_SUCCESS, Boolean.TRUE);
        }
        catch(SIATException siate)
        {
            LOG.error("error al insertarDeclaracionManual ->" + siate.getMessage());
            respuesta.put(KEY_SUCCESS, Boolean.FALSE);
            respuesta.put(KEY_MENSAJE, siate.getMessage());
        }
        catch(org.springframework.dao.DuplicateKeyException dke)
        {
            LOG.error("error al insertarDeclaracionManual ->" + dke.getMessage());
            String msj =  dke.getMessage().contains("DYCT_DECLARAICEP") 
                            ? "Ya existe una declaración con los mismos datos." 
                            : dke.getMessage();

            respuesta.put(KEY_SUCCESS, Boolean.FALSE);
            respuesta.put(KEY_MENSAJE, msj);
        }
        
        return respuesta;
    }

    @Transactional(readOnly = false, rollbackFor = SIATException.class)
    public void executeTransactionInsertarDeclaraIcep(DyctDeclaraIcepDTO declaraIcep, DyctMovSaldoDTO abonoAltaSaldo , Map<String, String> valsDinamicosPistAud) throws SIATException
    {
        daoDeclaraIcep.insertar(declaraIcep);

        daoMovSaldo.insertar(abonoAltaSaldo);

        registroPistasAudService.registrarPistaAuditoria(ConstantesACC.PISTAS_IDMENSAJE_CREAR_DECLARACION, ConstantesACC.IDGRUPOOPERACION_CONSULTA_MOV_ICEP, 
                    ConstantesACC.MOVIMIENTO_PISTASA_AGREGA_DECLARACION, valsDinamicosPistAud);
    }
    
    @Override
    @Transactional(readOnly = false, rollbackFor = SIATException.class)
    public Map<String, Object> eliminarDeclaraHist(Integer idDeclaraIcep)
    {
        Map<String, Object> respuesta = new HashMap<String, Object>();
        try {
            LOG.debug("idDeclaraIcep ->" + idDeclaraIcep);
            DyctDeclaraIcepDTO declaraBorrar = daoDeclaraIcep.encontrar(idDeclaraIcep);
            
            List<DyctMovSaldoDTO> movsDeclara = daoMovSaldo.selecXSaldoicepIdorigen(declaraBorrar.getDyctSaldoIcepDTO(), declaraBorrar.getNumOperacion().toString());
            for(DyctMovSaldoDTO movSaldoBorrar : movsDeclara){
                LOG.debug("movSaldoBorrar ->" + movSaldoBorrar.getIdMovSaldo() + "<-");
                daoMovSaldo.borrar(movSaldoBorrar.getIdMovSaldo());
            }
            daoDeclaraIcep.borrar(idDeclaraIcep);
            //RegistraPista
            Map<String, String> valsDinamicosPistAud = new HashMap<String, String>();
            AccesoUsr accesoUsrL = serviceObtenerSesion.execute();
            valsDinamicosPistAud.put(ID_OPERACION, idDeclaraIcep.toString());
            valsDinamicosPistAud.put(NOMBRE_EMPLEADO, accesoUsrL.getNombreCompleto());
            valsDinamicosPistAud.put(NUMERO_EMPLEADO, accesoUsrL.getNumeroEmp());
            registroPistasAudService.registrarPistaAuditoria(ConstantesACC.PISTAS_IDMENSAJE_ELIMINAR_DECLARACION, ConstantesACC.IDGRUPOOPERACION_CONSULTA_MOV_ICEP, ConstantesACC.MOVIMIENTO_PISTASA_ELIMINA_DECLARACION, valsDinamicosPistAud);
        }
        catch (SIATException e)
        {
            LOG.error("Ocurrio un error en eliminarDeclaraHist, mensaje ->" + e.getMessage());
            ManejadorLogException.getTraceError(e);
        }

        return respuesta;        
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = SIATException.class)
    public Map<String, Object> reactivarDeclaraHist(Integer idDeclaraIcep)
    {
        Map<String, Object> respuesta = new HashMap<String, Object>();
        try {
            LOG.debug("idDeclaraIcep ->" + idDeclaraIcep);
            DyctDeclaraIcepDTO declaraReactivar = daoDeclaraIcep.encontrarConNulos(idDeclaraIcep);
            
            List<DyctMovSaldoDTO> movsDeclara = daoMovSaldo.selecXSaldoicepIdorigenConNulos(declaraReactivar.getDyctSaldoIcepDTO(), declaraReactivar.getNumOperacion().toString());
            for(DyctMovSaldoDTO movSaldoReactivar : movsDeclara){
                LOG.debug("movSaldoBorrar ->" + movSaldoReactivar.getIdMovSaldo() + "<-");
                daoMovSaldo.reactivar(movSaldoReactivar.getIdMovSaldo());
            }
            daoDeclaraIcep.reactivar(idDeclaraIcep);
            
        }
        catch (SIATException e)
        {
            LOG.error("Ocurrio un error en reactivarDeclaraHist, mensaje ->" + e.getMessage());
            ManejadorLogException.getTraceError(e);
        }

        return respuesta;        
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = SIATException.class)
    public Map<String, Object> eliminarCompensacionHist(Integer idMovCompensacion)
    {
        LOG.debug("INICIO eliminarCompensacionHist ->" + idMovCompensacion);
        try {
            DyctCompHistoricaDTO movCompensacion = daoMovCompensacion.encontrar(idMovCompensacion);
            List<DyctMovSaldoDTO> movsCompensacion = daoMovSaldo.selecXSaldoicepIdorigen(movCompensacion.getDyctSaldoIcepOrigenDTO(), movCompensacion.getNumControl());
            LOG.debug("numero de movsCompensacion ->" + movsCompensacion.size());
            for(DyctMovSaldoDTO movComp : movsCompensacion)
            {
                LOG.debug("se borrara logicamente el movSaldo con id ->" + movComp.getIdMovSaldo() + "<-");
                daoMovSaldo.borrar(movComp.getIdMovSaldo());
            }
            daoMovCompensacion.borrar(idMovCompensacion);
            
            //RegistraPista
            Map<String, String> valsDinamicosPistAud = new HashMap<String, String>();
            AccesoUsr accesoUsrL = serviceObtenerSesion.execute();
            valsDinamicosPistAud.put(ID_OPERACION, idMovCompensacion.toString());
            valsDinamicosPistAud.put(NOMBRE_EMPLEADO, accesoUsrL.getNombreCompleto());
            valsDinamicosPistAud.put(NUMERO_EMPLEADO, accesoUsrL.getNumeroEmp());
            registroPistasAudService.registrarPistaAuditoria(ConstantesACC.PISTAS_IDMENSAJE_ELIMINAR_COMPENSACION, ConstantesACC.IDGRUPOOPERACION_CONSULTA_MOV_ICEP, ConstantesACC.MOVIMIENTO_PISTASA_ELIMINA_COMPENSACION, valsDinamicosPistAud);  
        } catch (SIATException e) {
            LOG.error("Ocurrio un error en eliminarCompensacionHist, mensaje ->" + e.getMessage());
            ManejadorLogException.getTraceError(e);
        }
        return Collections.emptyMap();
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = SIATException.class)
    public Map<String, Object> eliminarDevolucionHist(Integer idMovDevolucion)
    {
        LOG.debug("INICIO eliminarDevolucionHist ->" + idMovDevolucion);
        try {
            DyctMovDevolucionDTO movDevolucion = daoMovDevolucion.encontrar(idMovDevolucion);
            List<DyctMovSaldoDTO> movsDevHist = daoMovSaldo.selecXSaldoicepIdorigen(movDevolucion.getDyctSaldoIcepDTO(), movDevolucion.getNumControl());
            LOG.debug("numero de movsDevHist ->" + movsDevHist.size());
            for(DyctMovSaldoDTO mov : movsDevHist)
            {
                LOG.debug("se borrara logicamente el movSaldo con id ->" + mov.getIdMovSaldo() + "<-");
                daoMovSaldo.borrar(mov.getIdMovSaldo());
            }
            
            daoMovDevolucion.borrar(idMovDevolucion);
            //RegistraPista
            Map<String, String> valsDinamicosPistAud = new HashMap<String, String>();
            AccesoUsr accesoUsrL = serviceObtenerSesion.execute();
            valsDinamicosPistAud.put(ID_OPERACION, idMovDevolucion.toString());
            valsDinamicosPistAud.put(NOMBRE_EMPLEADO, accesoUsrL.getNombreCompleto());
            valsDinamicosPistAud.put(NUMERO_EMPLEADO, accesoUsrL.getNumeroEmp());
            registroPistasAudService.registrarPistaAuditoria(ConstantesACC.PISTAS_IDMENSAJE_ELIMINAR_DEVOLUCION, ConstantesACC.IDGRUPOOPERACION_CONSULTA_MOV_ICEP, ConstantesACC.MOVIMIENTO_PISTASA_ELIMINA_DEVOLUCION, valsDinamicosPistAud);
        } 
        catch (SIATException e) {
            LOG.error("Ocurrio un error en eliminarDevolucionHist, mensaje ->" + e.getMessage());
            ManejadorLogException.getTraceError(e);
        }
        return Collections.emptyMap();
    }

    public DycpSolicitudDAO getDycpSolicitudDAO() {
        return dycpSolicitudDAO;
    }

    public void setDycpSolicitudDAO(DycpSolicitudDAO dycpSolicitudDAO) {
        this.dycpSolicitudDAO = dycpSolicitudDAO;
    }
    
  

}
