/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.siat.dyc.dao.controlsaldos.icep;

import java.util.List;

import javax.annotation.PostConstruct;

import mx.gob.sat.siat.dyc.controlsaldos.vo.DeclaracionDwhVO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.vo.DeclaracionIcepParamVO;

import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author christian.ventura
 */
public interface SiosConsultaPagosStoredProcedureInterface {

    @PostConstruct
    void compilar();

    @Transactional(value = "TMdataSourceInformixDYC")
    List<DeclaracionDwhVO> execute(DeclaracionIcepParamVO declaracionIcepParam) throws SIATException;
    
}
