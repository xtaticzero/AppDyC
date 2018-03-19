package mx.gob.sat.siat.dyc.dao.anexo.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.anexo.DyccAnexoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.anexo.DyccMatrizAnexosDTO;
import mx.gob.sat.siat.dyc.domain.anexo.DyctAnexoReqDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctReqInfoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;

import org.springframework.jdbc.core.RowMapper;


public class AnexoReqMapper implements RowMapper<DyctAnexoReqDTO> {
    private DyctReqInfoDTO reqInfo;
    private MatrizAnexosMapper mapperMatrizAnexos;

    public DyctAnexoReqDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        DyctReqInfoDTO reqInfoAux;
        if (reqInfo != null) {
            reqInfoAux = reqInfo;
        } else {
            reqInfoAux = new DyctReqInfoDTO();
            reqInfoAux.setNumControlDoc(rs.getString("NUMCONTROLDOC"));
        }

        DyccMatrizAnexosDTO anexo;
        if (getMapperMatrizAnexos() != null) {
            anexo = getMapperMatrizAnexos().mapRow(rs, rowNum);
        } else {
            anexo = new DyccMatrizAnexosDTO();
            anexo.setIdAnexo(rs.getInt("IDANEXO"));
        }
        DyccTipoTramiteDTO tipoTramite = new DyccTipoTramiteDTO();
        tipoTramite.setIdTipoTramite(rs.getInt("IDTIPOTRAMITE"));
        DyccAnexoTramiteDTO anexoTramite = new DyccAnexoTramiteDTO();
        anexoTramite.setDyccMatrizAnexosDTO(anexo);
        anexoTramite.setDyccTipoTramiteDTO(tipoTramite);

        DyctAnexoReqDTO anexoReq = new DyctAnexoReqDTO();
        anexoReq.setDyctReqInfoDTO(reqInfoAux);
        anexoReq.setDyccAnexoTramiteDTO(anexoTramite);
        anexoReq.setNombreArchivo(rs.getString("NOMBREARCHIVO"));
        anexoReq.setUrl(rs.getString("URL"));
        return anexoReq;
    }

    public DyctReqInfoDTO getReqInfo() {
        return reqInfo;
    }

    public void setReqInfo(DyctReqInfoDTO reqInfo) {
        this.reqInfo = reqInfo;
    }

    public MatrizAnexosMapper getMapperMatrizAnexos() {
        return mapperMatrizAnexos;
    }

    public void setMapperMatrizAnexos(MatrizAnexosMapper mapperMatrizAnexos) {
        this.mapperMatrizAnexos = mapperMatrizAnexos;
    }
}
