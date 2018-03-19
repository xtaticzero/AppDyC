/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.mat.dyc.controlsaldos.service.impl;

import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.siat.dyc.dao.detalleicep.DyctSaldoIcepDAO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.controlsaldos.DycControlSaldosObtenerIcepDTO;
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
public class ProcesarDeclaracionCompBussinesDelTestImpl {
    @Autowired
    private DyctSaldoIcepDAO daoSaldoIcep;

    
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
            throw new DycServiceExcepcion(EDycServiceCodigoError.BD_DYC_SALDOICEP_GENERAL, estadoVariables, ex);
        }
    }
}
