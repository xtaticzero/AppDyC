/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.dyc.dao.compensacion.impl.mapper;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpDatosSolicitudDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author jose.aguilarl
 */
public class DycpDatosSolicitudMapper implements RowMapper<DycpDatosSolicitudDTO> {
    
    private String numControl;
    private Integer idEstadoSolicitud;

    @Override
    public DycpDatosSolicitudDTO mapRow(ResultSet rs, int i) throws SQLException {
 
        DycpDatosSolicitudDTO datosSolicitud = new DycpDatosSolicitudDTO();
        datosSolicitud.setIdEstadoSolicitud(rs.getInt("IDESTADOSOLICITUD"));
        datosSolicitud.setNumControl(rs.getString("NUMCONTROL"));
        datosSolicitud.setNumDictaminador(rs.getInt("NUMEMPLEADODICT"));
        return datosSolicitud;
    }
    
    public String getNumControl() {
        return numControl;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public Integer getIdEstadoSolicitud() {
        return idEstadoSolicitud;
    }

    public void setIdEstadoSolicitud(Integer idEstadoSolicitud) {
        this.idEstadoSolicitud = idEstadoSolicitud;
    }
    
}
