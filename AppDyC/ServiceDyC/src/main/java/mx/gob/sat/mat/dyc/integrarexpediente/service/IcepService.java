/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.mat.dyc.integrarexpediente.service;

import java.io.IOException;

import java.sql.SQLException;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;

import mx.gob.sat.siat.dyc.domain.icep.IcepSioUrucple1DTO;
import mx.gob.sat.siat.dyc.domain.icep.IcepUrdcFatDTO;
import mx.gob.sat.siat.dyc.domain.icep.IcepUrdcsilDTO;
import mx.gob.sat.siat.dyc.domain.icep.ObtieneIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;


/**
 * @author Israel Chavez
 */
public interface IcepService {

    IcepUrdcFatDTO obtieneIcepUrdFat(IcepUrdcFatDTO icep) throws SQLException;

    IcepUrdcsilDTO obtieneIcepUrdcsil(IcepUrdcsilDTO icepUrdcsilDTO) throws SQLException;

    IcepSioUrucple1DTO obtieneIcepSioUrucple1DTO(IcepSioUrucple1DTO icepSioUrucple1DTO) throws SQLException;

    IcepSioUrucple1DTO obtenerIcepPorTipoTramite(IcepSioUrucple1DTO icep) throws SQLException;

    ObtieneIcepDTO obtenerIcep(ObtieneIcepDTO obtieneIcepDTO) throws SQLException, IOException;

    IcepSioUrucple1DTO obtieneIcepUrdcsil1(IcepSioUrucple1DTO icepDTO) throws SQLException;

    DyctSaldoIcepDTO encontrar(String rfc, DyccConceptoDTO concepto, DyccEjercicioDTO ejercicio, DyccPeriodoDTO periodo, DyccOrigenSaldoDTO dyccOrigenSaldoEjercicioDTO);
}
