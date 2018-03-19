/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.dao.util.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.vo.DocumentoAdministrarSolVO;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author Federico Chopin Gachuz
 * @date Noviembre 29, 2013
 *
 * */
public class DycaDocumentoAdministrarSolMapper implements RowMapper<DocumentoAdministrarSolVO> {
    
    @Override
    public DocumentoAdministrarSolVO mapRow(ResultSet resultSet, int i) throws SQLException{
        
        DocumentoAdministrarSolVO documentoAdministrarSolVO = new DocumentoAdministrarSolVO();
        
        documentoAdministrarSolVO.setNumControl(resultSet.getString("NUMCONTROL"));
        documentoAdministrarSolVO.setRfcContribuyente(resultSet.getString("RFC"));
        documentoAdministrarSolVO.setRolDictaminado(resultSet.getString("ROLDICTAMINADO"));
        documentoAdministrarSolVO.setTipoTramite(resultSet.getString("TIPOTRAMITE"));
        documentoAdministrarSolVO.setFechaPresentacion(resultSet.getDate("FECHAPRESENTACION"));
        documentoAdministrarSolVO.setNombreDocumento(resultSet.getString("NOMBREARCHIVO"));
        documentoAdministrarSolVO.setEstadoDesc(resultSet.getString("ESTADODOC"));
        documentoAdministrarSolVO.setImporteSolicitado(resultSet.getDouble("IMPORTESOLICITADO"));
        documentoAdministrarSolVO.setDias(resultSet.getInt("DIAS"));
        documentoAdministrarSolVO.setTipoDia(resultSet.getInt("TIPODIA"));
        documentoAdministrarSolVO.setIdEstadoDoc(resultSet.getInt("IDESTADODOC"));
        documentoAdministrarSolVO.setClaveAdm(resultSet.getInt("CLAVEADMINISTRACION"));
        documentoAdministrarSolVO.setUrl(resultSet.getString("URL"));
        documentoAdministrarSolVO.setNumControlDoc(resultSet.getString("NUMCONTROLDOC"));
                   
        return documentoAdministrarSolVO;
        
    }
   
}
