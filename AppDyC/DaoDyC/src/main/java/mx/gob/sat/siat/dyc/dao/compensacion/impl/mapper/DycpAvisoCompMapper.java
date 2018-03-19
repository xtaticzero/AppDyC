package mx.gob.sat.siat.dyc.dao.compensacion.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpAvisoCompDTO;
import mx.gob.sat.siat.dyc.util.BuscadorConstantes;

import org.apache.log4j.Logger;

import org.springframework.jdbc.core.RowMapper;


public class DycpAvisoCompMapper implements RowMapper<DycpAvisoCompDTO>
{
    private static final Logger LOG = Logger.getLogger(DycpAvisoCompMapper.class);
    
    private DycpAvisoCompDTO avisoNormal;
    
    @Override
    public DycpAvisoCompDTO mapRow(ResultSet rs, int i) throws SQLException
    {
        DycpAvisoCompDTO avisoComp = new DycpAvisoCompDTO();
        //TODO: una vez que se valide que todos los querys que ocupan este mapper traen todas las columnas, retirar el try catch innecesario
        try{
            DycpAvisoCompDTO avisoNormalL;
            if(avisoNormal != null){
                avisoNormalL = avisoNormal;
            }
            else{
                avisoNormalL = new DycpAvisoCompDTO();
                avisoNormalL.setFolioAviso(rs.getString("FOLIOAVISONORMAL"));
            }
    
            avisoComp.setFolioAviso(rs.getString("FOLIOAVISO"));
            avisoComp.setDycpAvisoCompNormalDTO(avisoNormalL);
            avisoComp.setDyccTipoAvisoDTO(BuscadorConstantes.obtenerTipoAviso(rs.getInt("IDTIPOAVISO")));
            avisoComp.setCadenaOriginal(rs.getString("CADENAORIGINAL"));
            avisoComp.setSelloDigital(rs.getString("SELLODIGITAL"));
            avisoComp.setAvisoNormalExterno(rs.getString("AVISONORMALEXTERNO"));
        }
        catch(Exception e){
            LOG.error("ocurrio un error en el mapper al generar el DTO AvisoComp ->" + e.getMessage());
        }

        return avisoComp;
    }

    public DycpAvisoCompDTO getAvisoNormal() {
        return avisoNormal;
    }

    public void setAvisoNormal(DycpAvisoCompDTO avisoNormal) {
        this.avisoNormal = avisoNormal;
    }
}
