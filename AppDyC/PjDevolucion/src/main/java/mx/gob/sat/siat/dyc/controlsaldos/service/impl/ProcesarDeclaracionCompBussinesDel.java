package mx.gob.sat.siat.dyc.controlsaldos.service.impl;

import java.math.BigDecimal;

import java.util.Date;
import java.util.Map;

import mx.gob.sat.siat.dyc.controlsaldos.service.ProcesarDeclaracionCompBussinesDelInterface;
import mx.gob.sat.siat.dyc.dao.detalleicep.DyctSaldoIcepDAO;
import mx.gob.sat.siat.dyc.dao.icep.DyctDeclaraIcepDAO;
import mx.gob.sat.siat.dyc.dao.movsaldo.DyctMovSaldoDAO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaraIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctMovSaldoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author root
 */
@Service(value = "bdProcDeclaraComplement")
public class ProcesarDeclaracionCompBussinesDel implements ProcesarDeclaracionCompBussinesDelInterface
{
    private static final Logger LOG = Logger.getLogger(ProcesarDeclaracionCompBussinesDel.class);
    
    @Autowired
    private DyctSaldoIcepDAO daoSaldoIcep;

    @Autowired
    private DyctDeclaraIcepDAO daoDeclaraIcep;

    @Autowired
    private DyctMovSaldoDAO daoMovSaldo;

    /**
     *
     * @param params
     * @return
     */
    @Override
    @Transactional
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

    /**
     *
     * @param params
     * @throws SIATException
     */
    @Override
    @Transactional(propagation = Propagation.NESTED)
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
        try{
            daoDeclaraIcep.insertar(declaracion);
        }catch(DataAccessException ex){
            LOG.info("la declaracion no se pudo insertar porque ya existe");
        }
        DyctMovSaldoDTO abonoSAF = new DyctMovSaldoDTO();
        abonoSAF.setDyctSaldoIcepDTO(saldoIcep);
        abonoSAF.setImporte(saldoAFavor);
        abonoSAF.setFechaRegistro(ahora);
        abonoSAF.setFechaOrigen(fechaPresentacion);
        abonoSAF.setDyccMovIcepDTO(Constantes.MovsIcep.ALTA_SALDO);
        abonoSAF.setIdOrigen(numOperacion);
        daoMovSaldo.insertar(abonoSAF);
    }
}
