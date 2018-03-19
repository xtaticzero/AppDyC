package mx.gob.sat.siat.dyc.dao.util.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.dao.req.impl.mapper.InfoARequerirMapper;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccInfoARequerirDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccInfoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;

import org.springframework.jdbc.core.RowMapper;


public class InfoTramiteMapper implements RowMapper<DyccInfoTramiteDTO> {
    private DyccTipoTramiteDTO tipoTramite;
    private Boolean mapearInfoARequerir = false;

    public DyccInfoTramiteDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        DyccInfoTramiteDTO infoTramite = new DyccInfoTramiteDTO();
        if (tipoTramite != null) {
            infoTramite.setDyccTipoTramiteDTO(tipoTramite);
        } else {
            DyccTipoTramiteDTO tte = new DyccTipoTramiteDTO();
            tte.setIdTipoTramite(rs.getInt("IDTIPOTRAMITE"));
            infoTramite.setDyccTipoTramiteDTO(tte);
        }

        if (mapearInfoARequerir) {
            InfoARequerirMapper mapper = new InfoARequerirMapper();
            infoTramite.setDyccInfoARequerirDTO(mapper.mapRow(rs, rowNum));
        } else {
            DyccInfoARequerirDTO infoAReqv = new DyccInfoARequerirDTO();
            infoAReqv.setIdInfoArequerir(rs.getInt("IDINFOAREQUERIR"));
            infoAReqv.setDescripcion(rs.getString("DESCRIPCION"));
            infoTramite.setDyccInfoARequerirDTO(infoAReqv);
        }

        return infoTramite;
    }

    public DyccTipoTramiteDTO getTipoTramite() {
        return tipoTramite;
    }

    public void setTipoTramite(DyccTipoTramiteDTO tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public Boolean getMapearInfoARequerir() {
        return mapearInfoARequerir;
    }

    public void setMapearInfoARequerir(Boolean mapearInfoARequerir) {
        this.mapearInfoARequerir = mapearInfoARequerir;
    }
}
