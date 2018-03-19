package mx.gob.sat.siat.dyc.dao.matriz.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.DyccMatrizCompDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaServOrigenDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoServicioDTO;

import org.springframework.jdbc.core.RowMapper;


public class DyccMatrizCompMapper implements RowMapper<DyccMatrizCompDTO> {

    private DyccConceptoDTO conceptoOrigen;
    private DyccConceptoDTO conceptoDestino;
    private DyccOrigenSaldoDTO origenSaldo;

    @Override
    public DyccMatrizCompDTO mapRow(ResultSet resultSet, int i) throws SQLException {

        DyccConceptoDTO conceptoOrigenL;
        DyccConceptoDTO conceptoDestinoL;
        DyccOrigenSaldoDTO origenSaldoL;

        if (conceptoOrigen != null) {
            conceptoOrigenL = conceptoOrigen;

        } else {
            conceptoOrigenL = new DyccConceptoDTO();
            conceptoOrigenL.setIdConcepto(resultSet.getInt("idconceptoorigen"));
        }

        if (conceptoDestino != null) {
            conceptoDestinoL = conceptoDestino;
        } else {
            conceptoDestinoL = new DyccConceptoDTO();
            conceptoDestinoL.setIdConcepto(resultSet.getInt("idconceptodestino"));
        }
        
        if (origenSaldo != null) {
            origenSaldoL = origenSaldo;

        } else {
            origenSaldoL = new DyccOrigenSaldoDTO();
            origenSaldoL.setIdOrigenSaldo(resultSet.getInt("idorigensaldo"));
        }


        DycaServOrigenDTO servOrigenL = new DycaServOrigenDTO();
        DyccTipoServicioDTO tipoServicio = new DyccTipoServicioDTO();
        tipoServicio.setIdTipoServicio(resultSet.getInt("idtiposervicio"));
        servOrigenL.setDyccTipoServicioDTO(tipoServicio);
        servOrigenL.setDyccOrigenSaldoDTO(origenSaldoL);

        DyccMatrizCompDTO dyccMatrizComp = new DyccMatrizCompDTO();
        dyccMatrizComp.setDyccConceptoDTO1(conceptoOrigenL);
        dyccMatrizComp.setDyccConceptoDTO2(conceptoDestinoL);
        dyccMatrizComp.setProvisional(resultSet.getInt("provisional"));
        dyccMatrizComp.setDycaServOrigenDTO(servOrigenL);

        return dyccMatrizComp;
    }

    public DyccConceptoDTO getConceptoOrigen() {
        return conceptoOrigen;
    }

    public void setConceptoOrigen(DyccConceptoDTO conceptoOrigen) {
        this.conceptoOrigen = conceptoOrigen;
    }

    public DyccConceptoDTO getConceptoDestino() {
        return conceptoDestino;
    }

    public void setConceptoDestino(DyccConceptoDTO conceptoDestino) {
        this.conceptoDestino = conceptoDestino;
    }


    public void setOrigenSaldo(DyccOrigenSaldoDTO origenSaldo) {
        this.origenSaldo = origenSaldo;
    }

    public DyccOrigenSaldoDTO getOrigenSaldo() {
        return origenSaldo;
    }
}
