package mx.gob.sat.siat.dyc.dao.documento.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.vo.DyctDocumentoVO;

import org.springframework.jdbc.core.RowMapper;


/**
 * Mapea los elementos de la tabla: DYCT_DOCUMENTO con la diferencia que el mapeo solo contiene un objeto
 * con los campos que tiene la tabla. En vez de tener objetos relacionados con otras tablas.
 *
 * Esto con el fin de recuperar los datos en java que provienen de base de datos con mayor rapidez.
 *
 * @author Jesus Alfredo Hernandez Orozco.
 */
public class DyctDocumentoVOMapper implements RowMapper<DyctDocumentoVO> {
    public DyctDocumentoVOMapper() {
        super();
    }

    /**
     * Mapea los datos de la tabla DYCT_DOCUMENTO con el objeto DyctDocumentoVO
     *
     * @param rs
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public DyctDocumentoVO mapRow(ResultSet rs, int i ) throws SQLException {
        DyctDocumentoVO objeto = new DyctDocumentoVO();
        objeto.setNumControlDoc(rs.getString("NUMCONTROLDOC"));
        objeto.setIdTipoDocumento(rs.getInt("IDTIPODOCUMENTO"));
        objeto.setNumControl(rs.getString("NUMCONTROL"));
        objeto.setUrl(rs.getString("URL"));
        objeto.setFechaRegistro(rs.getDate("FECHAREGISTRO"));
        objeto.setNombreArchivo(rs.getString("NOMBREARCHIVO"));
        objeto.setIdEstadoDoc(rs.getInt("IDESTADODOC"));
        objeto.setIdEstadoReq(rs.getInt("IDESTADOREQ"));
        objeto.setIdPlantilla(rs.getInt("IDPLANTILLA"));
        objeto.setFechaIniPlazoLegal(rs.getDate("FECHAINIPLAZOLEGAL"));
        objeto.setFechaFinPlazoLegal(rs.getDate("FECHAFINPLAZOLEGAL"));
        objeto.setIdTipoFirma(rs.getInt("IDTIPOFIRMA"));
        objeto.setNumEmpleadoAprob(rs.getInt("NUMEMPLEADOAPROB"));
        objeto.setFolioNYV(rs.getString("FOLIONYV"));
        objeto.setCadenaOriginal(rs.getString("CADENAORIGINAL"));
        objeto.setSelloDigital(rs.getString("SELLODIGITAL"));
        objeto.setFirmaElectronica(rs.getString("FIRMAELECTRONICA"));
        
        return objeto;
    }
}
