package mx.gob.sat.siat.dyc.dao.tipotramite.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.tipotramite.DycaOrigenTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaServOrigenDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoServicioDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author J. Isaac Carbajal Bernal
 * @modidiedby Jesus Alfredo Hernandez Orozco
 * @date 07/01/2015
 */
public class DycaOrigenTramiteMapper implements RowMapper<DycaOrigenTramiteDTO> {

    @Override
    public DycaOrigenTramiteDTO mapRow(ResultSet rs, int i) throws SQLException {
        DycaOrigenTramiteDTO objeto = new DycaOrigenTramiteDTO();

        //AGREGANDO EL ORIGEN DEL SALDO:
        DycaServOrigenDTO dycaServOrigenDTO = new DycaServOrigenDTO();
        DyccOrigenSaldoDTO dyccOrigenSaldoDTO = new DyccOrigenSaldoDTO();
        dyccOrigenSaldoDTO.setIdOrigenSaldo(rs.getInt("IDORIGENSALDO"));
        dycaServOrigenDTO.setDyccOrigenSaldoDTO(dyccOrigenSaldoDTO);

        //AGREGANDO EL TIPO DE SERVICIO:
        DyccTipoServicioDTO dyccTipoServicioDTO = new DyccTipoServicioDTO();
        dyccTipoServicioDTO.setIdTipoServicio(rs.getInt("IDTIPOSERVICIO"));
        dycaServOrigenDTO.setDyccTipoServicioDTO(dyccTipoServicioDTO);
        objeto.setDycaServOrigenDTO(dycaServOrigenDTO);

        //AGREGANDO EL TIPO DEL TRAMITE:
        TipoTramiteMapper tramiteMapper = new TipoTramiteMapper();
        DyccTipoTramiteDTO dyccTipoTramiteDTO = tramiteMapper.mapRow(rs, i);
        /**
         * dyccTipoTramiteDTO.setIdTipoTramite(rs.getInt("IDTIPOTRAMITE"));
         */
        objeto.setDyccTipoTramiteDTO(dyccTipoTramiteDTO);

        objeto.setFechaFin(rs.getDate("FECHAFIN"));

        return objeto;
    }
}
