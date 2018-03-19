package mx.gob.sat.siat.dyc.dao.util.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.dao.compensacion.impl.mapper.DyctSaldoIcepMapper;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctCompHistoricaDTO;
import mx.gob.sat.siat.dyc.util.BuscadorConstantes;

import org.springframework.jdbc.core.RowMapper;


public class DyctCompHistoricaMapper implements RowMapper<DyctCompHistoricaDTO>
    {
        private DyctSaldoIcepMapper mapperIcepDestino;

        @Override
        public DyctCompHistoricaDTO mapRow(ResultSet rs, int i) throws SQLException
        {
            DyctSaldoIcepDTO icepOrigen = new DyctSaldoIcepDTO();
            icepOrigen.setIdSaldoIcep(rs.getInt("IDSALDOICEPORIGEN"));

            DyctSaldoIcepDTO icepDestino;
            if(mapperIcepDestino != null){
                icepDestino = mapperIcepDestino.mapRow(rs, i);
            }
            else{
                icepDestino= new DyctSaldoIcepDTO();
                icepDestino.setIdSaldoIcep(rs.getInt("IDSALDOICEPDESTINO"));
            }

            DyctCompHistoricaDTO compHistorica = new DyctCompHistoricaDTO();
            compHistorica.setNumControl(rs.getString("NUMCONTROL"));
            compHistorica.setFechaDeclaraDest(rs.getTimestamp("FECHADECLARADEST"));
            compHistorica.setImporteCompensado(rs.getBigDecimal("IMPORTECOMPENSADO"));
            compHistorica.setImporteLiquidado(rs.getBigDecimal("IMPORTELIQUIDADO"));
            compHistorica.setFechaResolucion(rs.getTimestamp("FECHARESOLUCION"));
            compHistorica.setDyccTipoResolDTO(BuscadorConstantes.obtenerTipoResolucion(rs.getInt("IDTIPORESOL")));
            compHistorica.setIdMovCompensacion(rs.getInt("IDMOVCOMPENSACION"));
            compHistorica.setDyctSaldoIcepOrigenDTO(icepOrigen);
            compHistorica.setDyctSaldoIcepDestinoDTO(icepDestino);
            compHistorica.setNumDocDeterminante(rs.getString("NUMDOCDETERMINANTE"));
            compHistorica.setNotas(rs.getString("NOTAS"));
            compHistorica.setFechaFin(rs.getTimestamp("FECHAFIN"));
            return compHistorica;
        }

        public DyctSaldoIcepMapper getMapperIcepDestino() {
            return mapperIcepDestino;
        }

        public void setMapperIcepDestino(DyctSaldoIcepMapper mapperIcepDestino) {
            this.mapperIcepDestino = mapperIcepDestino;
        }}
