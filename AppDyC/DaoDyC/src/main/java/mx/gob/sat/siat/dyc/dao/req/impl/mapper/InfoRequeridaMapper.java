package mx.gob.sat.siat.dyc.dao.req.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctInfoRequeridaDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctReqInfoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccInfoARequerirDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccInfoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;

import org.springframework.jdbc.core.RowMapper;


public class InfoRequeridaMapper implements RowMapper<DyctInfoRequeridaDTO>
{
    private DyctReqInfoDTO reqInfo;
    private InfoARequerirMapper mapperInfoARequerir;
    
    public DyctInfoRequeridaDTO mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        DyctReqInfoDTO reqInfoAux;
        if(reqInfo == null)
        {
            reqInfoAux = new DyctReqInfoDTO();
            DyctDocumentoDTO documentoReq = new DyctDocumentoDTO();
            documentoReq.setNumControlDoc(rs.getString("NUMCONTROLDOC"));
        }
        else
        {
            reqInfoAux = reqInfo;
        }
        
        DyccTipoTramiteDTO tipoTramite = new DyccTipoTramiteDTO();
        tipoTramite.setIdTipoTramite(rs.getInt("IDTIPOTRAMITE"));
        DyccInfoARequerirDTO infoARequerir;
        if(mapperInfoARequerir != null)
        {
            infoARequerir = mapperInfoARequerir.mapRow(rs, rowNum);
        }
        else
        {
            infoARequerir = new DyccInfoARequerirDTO();
            infoARequerir.setIdInfoArequerir(rs.getInt("IDINFOAREQUERIR"));
        }
        
        DyccInfoTramiteDTO infoTramite = new DyccInfoTramiteDTO();
        infoTramite.setDyccTipoTramiteDTO(tipoTramite);
        infoTramite.setDyccInfoARequerirDTO(infoARequerir);        

        DyctInfoRequeridaDTO infoRequerida = new DyctInfoRequeridaDTO();
        infoRequerida.setDyctReqInfoDTO(reqInfoAux);
        infoRequerida.setDyccInfoTramiteDTO(infoTramite);
        /**infoRequerida.setUrl(rs.getString("URL"));
        infoRequerida.setNombreArchivo(rs.getString("NOMBREARCHIVO"));*/
        return infoRequerida;
    }

    public DyctReqInfoDTO getReqInfo() {
        return reqInfo;
    }

    public void setReqInfo(DyctReqInfoDTO reqInfo) {
        this.reqInfo = reqInfo;
    }

    public InfoARequerirMapper getMapperInfoARequerir() {
        return mapperInfoARequerir;
    }

    public void setMapperInfoARequerir(InfoARequerirMapper mapperInfoARequerir) {
        this.mapperInfoARequerir = mapperInfoARequerir;
    }
}
