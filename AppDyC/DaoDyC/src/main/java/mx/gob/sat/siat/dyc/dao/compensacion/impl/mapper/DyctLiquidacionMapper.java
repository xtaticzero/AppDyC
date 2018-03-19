package mx.gob.sat.siat.dyc.dao.compensacion.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctLiquidacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctResolCompDTO;

import org.springframework.jdbc.core.RowMapper;


public class DyctLiquidacionMapper implements RowMapper<DyctLiquidacionDTO>
{
    private DyctResolCompDTO resolComp;
    
    @Override
    public DyctLiquidacionDTO mapRow(ResultSet rs, int i) throws SQLException
    {
        DyctLiquidacionDTO liquidacion = new DyctLiquidacionDTO();

        DyctResolCompDTO resolCompL;
        
        if(getResolComp() != null){
            resolCompL = getResolComp();
        }
        else{
            DycpServicioDTO servicio = new DycpServicioDTO();
            servicio.setNumControl(rs.getString("NUMCONTROL"));
            DycpCompensacionDTO compensacion = new DycpCompensacionDTO();
            compensacion.setDycpServicioDTO(servicio);
            resolCompL = new DyctResolCompDTO();
            resolCompL.setDycpCompensacionDTO(compensacion);
        }

        liquidacion.setDyctResolCompDTO(resolCompL);
        liquidacion.setImporteActualizar(rs.getBigDecimal("IMPORTEACTUALIZA"));
        liquidacion.setImporteRecargo(rs.getBigDecimal("IMPORTERECARGO"));
        liquidacion.setImporteMulta(rs.getBigDecimal("IMPORTEMULTA"));
        liquidacion.setNumDocDeterminante(rs.getString("NUMDOCDETERMINANTE"));
        liquidacion.setFundamentacion(rs.getString("FUNDAMENTACION"));
        liquidacion.setMesInicioInpc(rs.getString("MESINICIOINPC"));
        liquidacion.setMesFinalInpc(rs.getString("MESFINALINPC"));
        liquidacion.setAnioInicialInpc(rs.getInt("ANIOINICIALINPC"));
        liquidacion.setAnioFinalInpc(rs.getInt("ANIOFINALINPC"));
        liquidacion.setMesInicioTasaRec(rs.getString("MESINICIOTASAREC"));
        liquidacion.setMesFinalTasaRec(rs.getString("MESFINALTASAREC"));
        liquidacion.setAnioInicialTasaRec(rs.getInt("ANIOINICIALTASAREC"));
        liquidacion.setAnioFinalTasaRec(rs.getInt("ANIOFINALTASAREC"));
        liquidacion.setImporteImprocedente(rs.getBigDecimal("IMPORTEIMPRO"));
       
        return liquidacion;
    }

    public DyctResolCompDTO getResolComp() {
        return resolComp;
    }

    public void setResolComp(DyctResolCompDTO resolComp) {
        this.resolComp = resolComp;
    }
}
