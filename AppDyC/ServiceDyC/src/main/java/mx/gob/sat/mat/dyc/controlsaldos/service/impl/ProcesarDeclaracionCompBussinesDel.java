/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.mat.dyc.controlsaldos.service.impl;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.dao.detalleicep.DyctSaldoIcepDAO;
import mx.gob.sat.siat.dyc.dao.icep.DyctDeclaraIcepDAO;
import mx.gob.sat.siat.dyc.dao.movsaldo.DyctMovSaldoDAO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.controlsaldos.DycControlSaldosObtenerIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaraIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctMovSaldoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import mx.gob.sat.siat.dyc.util.constante.EDycDaoCodigoError;
import mx.gob.sat.siat.dyc.util.constante.EDycServiceCodigoError;
import mx.gob.sat.siat.dyc.util.excepcion.DycDaoExcepcion;
import mx.gob.sat.siat.dyc.util.excepcion.DycServiceExcepcion;
import mx.gob.sat.siat.dyc.vo.DycLogEstadoVariable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author GAER8674
 */
@Service
public class ProcesarDeclaracionCompBussinesDel
{
    @Autowired
    private DyctSaldoIcepDAO daoSaldoIcep;

    @Autowired
    private DyctDeclaraIcepDAO daoDeclaraIcep;

    @Autowired
    private DyctMovSaldoDAO daoMovSaldo;

    public DyctSaldoIcepDTO obtenerIcep(Map<String, Object> params)
    {
        String rfc = (String)params.get("rfc");
        DyccConceptoDTO concepto = new DyccConceptoDTO();
        concepto.setIdConcepto((Integer)params.get("idConcepto"));
        DyccEjercicioDTO ejercicio = new DyccEjercicioDTO();
        ejercicio.setIdEjercicio((Integer)params.get("idEjercicio"));
        DyccPeriodoDTO periodo = new DyccPeriodoDTO();
        periodo.setIdPeriodo((Integer)params.get("idPeriodo"));

        return daoSaldoIcep.encontrar(rfc, concepto, ejercicio, periodo);
    }

    public void afectarSaldosXDeclaComplementaria(Map<String, Object> params) throws SIATException
    {
        String numOperacion = (String)params.get("numOperacion");
        Date fechaPresentacion = (Date)params.get("fechaPresentacion");
        BigDecimal saldoAFavor = (BigDecimal)params.get("saldoAFavor");
        DyctSaldoIcepDTO saldoIcep = (DyctSaldoIcepDTO)params.get("saldoIcep");
        Date ahora = new Date();
        
        DyctDeclaraIcepDTO declaracion = new DyctDeclaraIcepDTO();
        declaracion.setDyctSaldoIcepDTO(saldoIcep);
        declaracion.setNumOperacion(Long.parseLong(numOperacion));
        declaracion.setFechaPresentacion(fechaPresentacion);
        declaracion.setDyccTipoDeclaraDTO(Constantes.TiposDeclaracion.COMPLEMENTARIA);
        declaracion.setSaldoAFavor(saldoAFavor);
        declaracion.setValidacionDWH(ahora);
        daoDeclaraIcep.insertar(declaracion);

        DyctMovSaldoDTO abonoSAF = new DyctMovSaldoDTO();
        abonoSAF.setDyctSaldoIcepDTO(saldoIcep);
        abonoSAF.setImporte(saldoAFavor);
        abonoSAF.setFechaRegistro(ahora);
        abonoSAF.setFechaOrigen(fechaPresentacion);
        abonoSAF.setDyccMovIcepDTO(Constantes.MovsIcep.ALTA_SALDO);
        abonoSAF.setIdOrigen(numOperacion);
        daoMovSaldo.insertar(abonoSAF);
    }

    /**
     * 2016-02-29
     * @param dycControlSaldosObtenerIcepDTO
     * @return 
     * @throws mx.gob.sat.siat.dyc.util.excepcion.DycServiceExcepcion 
     */
    public DyctSaldoIcepDTO obtenerIcep(DycControlSaldosObtenerIcepDTO dycControlSaldosObtenerIcepDTO) throws DycServiceExcepcion
    {
        List<DycLogEstadoVariable> estadoVariables = new ArrayList<DycLogEstadoVariable>();
        estadoVariables.add( new DycLogEstadoVariable("rfc", dycControlSaldosObtenerIcepDTO.getRfc()) );
        estadoVariables.add( new DycLogEstadoVariable("concepto", ""+dycControlSaldosObtenerIcepDTO.getIdConcepto()) );
        estadoVariables.add( new DycLogEstadoVariable("ejercicio", ""+dycControlSaldosObtenerIcepDTO.getIdEjercicio()) );
        estadoVariables.add( new DycLogEstadoVariable("periodo", ""+dycControlSaldosObtenerIcepDTO.getIdPeriodo()) );
        estadoVariables.add( new DycLogEstadoVariable("origenSaldo", ""+dycControlSaldosObtenerIcepDTO.getIdOrigenSaldo()) );
        
        try {
            String rfc = dycControlSaldosObtenerIcepDTO.getRfc();
            DyccConceptoDTO concepto = new DyccConceptoDTO();
            concepto.setIdConcepto(dycControlSaldosObtenerIcepDTO.getIdConcepto());
            DyccEjercicioDTO ejercicio = new DyccEjercicioDTO();
            ejercicio.setIdEjercicio(dycControlSaldosObtenerIcepDTO.getIdEjercicio());
            DyccPeriodoDTO periodo = new DyccPeriodoDTO();
            periodo.setIdPeriodo(dycControlSaldosObtenerIcepDTO.getIdPeriodo());
            
            return daoSaldoIcep.encontrar(rfc, concepto, ejercicio, periodo, dycControlSaldosObtenerIcepDTO.getIdOrigenSaldo());
        } catch (DycDaoExcepcion ex) {
            if(ex.getCodigoError().equals(EDycDaoCodigoError.BD_DYC_SALDOICEP_NOENCONTRADO)){
                throw new DycServiceExcepcion(EDycServiceCodigoError.BD_DYC_SALDOICEP_NOENCONTRADO, estadoVariables, ex);
            }
            else{
                throw new DycServiceExcepcion(EDycServiceCodigoError.BD_DYC_SALDOICEP_GENERAL, estadoVariables, ex);
            }
        }
    }
}
