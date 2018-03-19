/*
    * Todos los Derechos Reservados 2013 SAT.
    * Servicio de Administracion Tributaria (SAT).
    *
    * Este software contiene informacion propiedad exclusiva del SAT considerada
    * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
    **/
package mx.gob.sat.siat.dyc.gestionsol.dao.registrarinformacion.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.AnexoRequeridoDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 *
 * DAO creado para el DTO
 * @author  David Guerrero Reyes
 * @since   19/11/2013
 *
 */
    public class ConsultaAnexoReqMapper implements RowMapper {

        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

            AnexoRequeridoDTO anexoRequerido = new AnexoRequeridoDTO();
            
            anexoRequerido.setIdTabla(rs.getLong("idTabla"));
            anexoRequerido.setNumControl(rs.getString("numcontrol"));
            anexoRequerido.setNumRequerimiento(rs.getString("numcontroldoc"));
            anexoRequerido.setDescripcionAnexo(rs.getString("descripcionanexo"));
            anexoRequerido.setIdAnexo(rs.getInt("idanexo"));
            anexoRequerido.setIdAnexo(rs.getInt("idanexo"));
            anexoRequerido.setIdTipoTramite (rs.getInt("idtipotramite"));
            anexoRequerido.setUrl (rs.getString("url"));

            return anexoRequerido;
            
        }

    }
