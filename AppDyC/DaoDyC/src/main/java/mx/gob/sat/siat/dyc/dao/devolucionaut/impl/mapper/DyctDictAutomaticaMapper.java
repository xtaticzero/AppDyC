/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.dao.devolucionaut.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.devolucionaut.DyctDictAutomaticaDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 *
 * @author root
 */
public class DyctDictAutomaticaMapper implements RowMapper<DyctDictAutomaticaDTO> {

    @Override
    public DyctDictAutomaticaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        DyctDictAutomaticaDTO dictAutomaticaDTO = new DyctDictAutomaticaDTO();
        dictAutomaticaDTO.setNumControl(rs.getString("NUMCONTROL"));
        dictAutomaticaDTO.setNumLote(rs.getInt("NUMLOTE"));
        dictAutomaticaDTO.setRfc(rs.getString("RFC"));
        dictAutomaticaDTO.setImpuesto(rs.getString("IMPUESTO"));
        dictAutomaticaDTO.setConcepto(rs.getInt("CONCEPTO"));
        dictAutomaticaDTO.setImporteSaldoF(rs.getBigDecimal("IMPORTESALDOF"));
        dictAutomaticaDTO.setFechaProcModelo(rs.getDate("FECHAPROCMODELO"));
        dictAutomaticaDTO.setFechaRegistro(rs.getDate("FECHAREGISTRO"));
        dictAutomaticaDTO.setIdModelo(rs.getInt("IDMODELO"));
        dictAutomaticaDTO.setIdMarcResultado(rs.getString("IDMARCRESULTADO"));
        dictAutomaticaDTO.setMotRiesgo(rs.getString("MOTRIESGO")!=null ? rs.getInt("MOTRIESGO") : 0);
        dictAutomaticaDTO.setFechaProceso(rs.getString("FECHAPROCESO")!=null?rs.getDate("FECHAPROCESO") :null);
        return dictAutomaticaDTO;
    }

}
