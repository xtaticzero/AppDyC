package mx.gob.sat.siat.dyc.dao.concepto.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccImpuestoDTO;

import org.springframework.jdbc.core.RowMapper;


public class ConceptoMapper implements RowMapper<DyccConceptoDTO>
{
    private DyccImpuestoDTO impuesto;
    private ImpuestoMapper mapperImpuesto;

    @Override
    public DyccConceptoDTO mapRow(ResultSet rs, int i) throws SQLException
    {
        DyccImpuestoDTO impuestoL;
        
        if(impuesto != null){
            impuestoL = impuesto;
        }
        else
        {
            if(mapperImpuesto != null){
                impuestoL = mapperImpuesto.mapRow(rs, i);
            }
            else
            {
                impuestoL = new DyccImpuestoDTO();
                impuestoL.setIdImpuesto(rs.getInt("IDIMPUESTO"));
            }
        }

        DyccConceptoDTO concepto = new DyccConceptoDTO();
        concepto.setIdConcepto(rs.getInt("IDCONCEPTO"));
        concepto.setDescripcion(rs.getString("DESCRIPCION"));
        concepto.setFechaInicio(rs.getDate("FECHAINICIO"));
        concepto.setDyccImpuestoDTO(impuestoL);
        return concepto;
    }

    public void setMapperImpuesto(ImpuestoMapper mapperImpuesto) {
        this.mapperImpuesto = mapperImpuesto;
    }

    public ImpuestoMapper getMapperImpuesto() {
        return mapperImpuesto;
    }

    public DyccImpuestoDTO getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(DyccImpuestoDTO impuesto) {
        this.impuesto = impuesto;
    }
}
