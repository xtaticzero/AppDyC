/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.dao.req.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctInfoRequeridaDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctReqInfoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccInfoARequerirDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccInfoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author Federico Chopin Gachuz
 * @author Jesus Alfredo Hernandez Orozco
 * @date Junio 12, 2014
 *
 * */
public class DyctInfoRequeridaMapper implements RowMapper<DyctInfoRequeridaDTO> {
   
    @Override
    public DyctInfoRequeridaDTO mapRow(ResultSet rs, int i) throws SQLException {
        
        DyctInfoRequeridaDTO dyctInfoRequeridaDTO = new DyctInfoRequeridaDTO();
        DyctReqInfoDTO dyctReqInfoDTO= new DyctReqInfoDTO();
        DyccInfoTramiteDTO dyccInfoTramiteDTO = new DyccInfoTramiteDTO();
        DyccTipoTramiteDTO dyccTipoTramiteDTO = new DyccTipoTramiteDTO();
        DyccInfoARequerirDTO dyccInfoARequerirDTO = new DyccInfoARequerirDTO();
       
        
        dyccTipoTramiteDTO.setIdTipoTramite(rs.getInt("IDTIPOTRAMITE"));
        dyccInfoARequerirDTO.setIdInfoArequerir(rs.getInt("IDINFOAREQUERIR"));
        dyctReqInfoDTO.setNumControlDoc(rs.getString("NUMCONTROLDOC"));
        
        dyccInfoTramiteDTO.setDyccTipoTramiteDTO(dyccTipoTramiteDTO);
        dyctInfoRequeridaDTO.setDyccInfoTramiteDTO(dyccInfoTramiteDTO);
        dyctInfoRequeridaDTO.setDyctReqInfoDTO(dyctReqInfoDTO);
        
        return dyctInfoRequeridaDTO;
    }
}