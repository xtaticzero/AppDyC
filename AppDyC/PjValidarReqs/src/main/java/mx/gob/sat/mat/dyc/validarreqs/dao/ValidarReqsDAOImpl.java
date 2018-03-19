package mx.gob.sat.mat.dyc.validarreqs.dao;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.impl.mapper.DycpServicioMapper;
import mx.gob.sat.siat.dyc.dao.impl.mapper.DyctDocumentoMapper;
import mx.gob.sat.siat.dyc.dao.impl.mapper.ReqInfoMapper;
import mx.gob.sat.siat.dyc.domain.documento.DyctReqInfoDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


@Service
public class ValidarReqsDAOImpl
{
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    public List<DyctReqInfoDTO> obtenerReqsValidar()
    {
        String query =  " SELECT * FROM DYCT_DOCUMENTO D INNER JOIN DYCT_REQINFO R ON D.NUMCONTROLDOC = R.NUMCONTROLDOC\n" + 
        "INNER JOIN DYCP_SERVICIO S ON D.NUMCONTROL = S.NUMCONTROL\n" + 
        "WHERE IDTIPODOCUMENTO IN (1, 2) AND IDESTADOREQ = 2 AND IDESTADODOC IN (7, 8)";

        DycpServicioMapper mapperServicio = new DycpServicioMapper();
        DyctDocumentoMapper mapperDocumento = new DyctDocumentoMapper();
        mapperDocumento.setMapperServicio(mapperServicio);
        ReqInfoMapper mapper = new ReqInfoMapper();
        mapper.setMapperDocumento(mapperDocumento);

        return this.jdbcTemplateDYC.query(query, mapper);
    }
}
