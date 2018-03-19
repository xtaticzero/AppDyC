package mx.gob.sat.mat.dyc.herramientas.analizador.negocio;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.mat.dyc.herramientas.analizador.beans.DeclaracionDWHBean;
import mx.gob.sat.mat.dyc.herramientas.analizador.dao.storeprocedure.BuscaDeclsCtrolSaldosStoreProcedure;
import mx.gob.sat.mat.dyc.herramientas.analizador.dao.storeprocedure.ObtenerDeclsDWHStoreProcedure;
import mx.gob.sat.mat.dyc.herramientas.analizador.dao.storeprocedure.SiosConsultaPagosStoredProcedure;
import mx.gob.sat.siat.dyc.dao.concepto.DyccConceptoDAO;
import mx.gob.sat.siat.dyc.dao.util.DyccEjercicioDAO;
import mx.gob.sat.siat.dyc.dao.concepto.DyccImpuestoDAO;
import mx.gob.sat.siat.dyc.dao.periodo.DyccPeriodoDAO;
import mx.gob.sat.siat.dyc.dao.periodo.DyccTipoPeriodoDAO;
import mx.gob.sat.siat.dyc.dao.tipotramite.DyccTipoTramiteDAO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccImpuestoDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccTipoPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstanteProcedureIcep;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
public class PruebasSPDeclsDWHIcepDelegate
{
    @Autowired
    private DyccPeriodoDAO daoPeriodo;
    
    @Autowired
    private DyccTipoPeriodoDAO daoTipoPeriodo;
    
    @Autowired
    private DyccImpuestoDAO daoImpuesto;
    
    @Autowired
    private DyccEjercicioDAO daoEjercicio;

    @Autowired
    private DyccTipoTramiteDAO daoTipoTramite;

    @Autowired
    private DyccConceptoDAO daoConcepto;

    @Autowired
    private ObtenerDeclsDWHStoreProcedure sp;
    
    @Autowired
    private BuscaDeclsCtrolSaldosStoreProcedure spBuscaDeclsCtrolSaldos;

    @Autowired
    @Qualifier(value = "SiosConsultaPagosStoredProcedureMigrador")
    private SiosConsultaPagosStoredProcedure spSios;

    private static final String CODIGO_DECLARACION_VACIA = "2";
    
    public static final String PARAM_USUARIO_SUGERIDO = "prueba";

    public Object[] obtenerTiposPeriodo(){
        return daoTipoPeriodo.obtieneTipoPeriodo().toArray();
    }

    public Object[] obtenerPeriodos(DyccTipoPeriodoDTO tipoPeriodo){
        return daoPeriodo.selecXTipoPeriodo(tipoPeriodo).toArray();
    }
    
    public Object[] obtenerImpuestos(){
        return daoImpuesto.obtieneImpuestos().toArray();
    }

    public Object[] obtenerConceptos(DyccImpuestoDTO impuesto){
        return daoConcepto.selecOrderXImpuesto(impuesto, " ORDER BY IDCONCEPTO ASC ").toArray();
    }

    public Object[] obtenerEjercicios(){
        return daoEjercicio.seleccionar().toArray();
    }

    public Object[] obtenerTiposTramite(){
        try {
            return daoTipoTramite.obtieneTipoTramite().toArray();
        } catch (SIATException e) {
            return null;
        }
    }

    public DyccPeriodoDTO[] obtenerPeriodos(){
        List<DyccPeriodoDTO> periodos = new ArrayList<DyccPeriodoDTO>();
        
        return (DyccPeriodoDTO[])periodos.toArray();
    }
    
    public Map<String, Object> probarSP(Map<String, Object> params)
    {
        Map<String, Object> respuesta = new HashMap<String, Object>();
        String rfc = (String)params.get("rfc");
        DyccPeriodoDTO periodo = (DyccPeriodoDTO)params.get("periodo");
        DyccEjercicioDTO ejercicio = (DyccEjercicioDTO)params.get("ejercicio");
        DyccConceptoDTO concepto = (DyccConceptoDTO)params.get("concepto");
        DyccTipoTramiteDTO tipoTramite = (DyccTipoTramiteDTO)params.get("tipoTramite");
        String user = (String)params.get("user");
        
        Map<String, Object> inParameters = new HashMap<String, Object>();
        
        inParameters.put(ConstanteProcedureIcep.TXT_RFC, rfc);
        inParameters.put(ConstanteProcedureIcep.PERIODO_ICEP, periodo.getIdPeriodo());
        inParameters.put(ConstanteProcedureIcep.EJERCICIO_ICEP, ejercicio.getIdEjercicio());
        inParameters.put(ConstanteProcedureIcep.CONCEPTO_ICEP, concepto.getIdConcepto());
        inParameters.put(ConstanteProcedureIcep.TIPOTRAMITE_ICEP, tipoTramite.getIdTipoTramite());
        inParameters.put(ConstanteProcedureIcep.TXT_USR, user);

        List<DeclaracionDWHBean> declaraciones = null;
        try {
            declaraciones = sp.executeProc(inParameters);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        List<DeclaracionDWHBean> declsBuenas = new ArrayList<DeclaracionDWHBean>();
        for(DeclaracionDWHBean declAux : declaraciones)
        {
            if(!CODIGO_DECLARACION_VACIA.equals(declAux.getEstatus())){
                declsBuenas.add(declAux);
            }
        }

        System.out.println("numero de declaraciones encontradas en el SP ->" + declsBuenas.size());
        respuesta.put("declaraciones", declsBuenas);
        for(DeclaracionDWHBean declAux : declsBuenas)
        {
            System.out.println(declAux.getEstatus());
            System.out.println(declAux.getTipoDeclaracion());
            System.out.println(declAux.getFechPresentacion());
            System.out.println(declAux.getNumOper());
            System.out.println(declAux.getSaldoFavor());
        }

        return respuesta;
    }

    public Map<String, Object> ejecutarSP_TRVCPOE1(Map<String, Object> params)
    {
        Map<String, Object> respuesta = new HashMap<String, Object>();
        String rfc = (String)params.get("rfc");
        DyccPeriodoDTO periodo = (DyccPeriodoDTO)params.get("periodo");
        DyccEjercicioDTO ejercicio = (DyccEjercicioDTO)params.get("ejercicio");
        DyccConceptoDTO concepto = (DyccConceptoDTO)params.get("concepto");
        DyccTipoTramiteDTO tipoTramite = (DyccTipoTramiteDTO)params.get("tipoTramite");
        String user = (String)params.get("user");
        
        Map<String, Object> inParameters = new HashMap<String, Object>();
        
        inParameters.put(ConstanteProcedureIcep.TXT_RFC, rfc);
        inParameters.put(ConstanteProcedureIcep.PERIODO_ICEP, periodo.getIdPeriodo());
        inParameters.put(ConstanteProcedureIcep.EJERCICIO_ICEP, ejercicio.getIdEjercicio());
        inParameters.put(ConstanteProcedureIcep.CONCEPTO_ICEP, concepto.getIdConcepto());
        inParameters.put(ConstanteProcedureIcep.TIPOTRAMITE_ICEP, tipoTramite.getIdTipoTramite());
        inParameters.put(ConstanteProcedureIcep.TXT_USR, user);

        List<DeclaracionDWHBean> declaraciones = null;

        try {
            declaraciones = spBuscaDeclsCtrolSaldos.executeProc(inParameters);
        } catch (SIATException e) {
            e.printStackTrace();
        }

        System.out.println("numero de declaraciones encontradas en el SP ->" + declaraciones.size());
        respuesta.put("declaraciones", declaraciones);

        return respuesta;
    }
    
    public Map<String, Object> ejecutarSP_SIO_TRVCFAT1(Map<String, Object> params)
    {
        Map<String, Object> respuesta = new HashMap<String, Object>();
        String rfc = (String)params.get("rfc");
        DyccPeriodoDTO periodo = (DyccPeriodoDTO)params.get("periodo");
        DyccEjercicioDTO ejercicio = (DyccEjercicioDTO)params.get("ejercicio");
        DyccConceptoDTO concepto = (DyccConceptoDTO)params.get("concepto");
        DyccTipoTramiteDTO tipoTramite = (DyccTipoTramiteDTO)params.get("tipoTramite");
        String user = (String)params.get("user");
        
        Map<String, Object> inParameters = new HashMap<String, Object>();
        
        inParameters.put(ConstanteProcedureIcep.TXT_RFC, rfc);
        inParameters.put(ConstanteProcedureIcep.PERIODO_ICEP, periodo.getIdPeriodo());
        inParameters.put(ConstanteProcedureIcep.EJERCICIO_ICEP, ejercicio.getIdEjercicio());
        inParameters.put(ConstanteProcedureIcep.CONCEPTO_ICEP, concepto.getIdConcepto());
        inParameters.put(ConstanteProcedureIcep.TIPOTRAMITE_ICEP, tipoTramite.getIdTipoTramite());
        inParameters.put(ConstanteProcedureIcep.TXT_USR, user);

        List<DeclaracionDWHBean> declaraciones = null;

        try {
            declaraciones = spSios.executeProc(inParameters);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("numero de declaraciones encontradas en el SP ->" + declaraciones.size());
        respuesta.put("declaraciones", declaraciones);

        return respuesta;
    }

    public String obtenerInfoSP_SIO_TRVCFAT1(){
        return "vpmf2";
    }
}
