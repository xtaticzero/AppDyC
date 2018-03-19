package mx.gob.sat.siat.dyc.dao.movsaldo.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.movsaldo.impl.mapper.DyctAccionMovSalMapper;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctAccionMovSalDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctMovSaldoDTO;
import mx.gob.sat.siat.dyc.util.BuscadorConstantes;
import mx.gob.sat.siat.dyc.util.UtilsDominio;

import org.apache.log4j.Logger;

import org.springframework.jdbc.core.ResultSetExtractor;


/**
 *
 * @author softtek
 *
 * Recibe un resultset del left outer join de DYCT_MOVSALDO y DYCT_ACCIONMOVSALDO
 */
public class DyctMovSaldoExtractorResultSet implements ResultSetExtractor<List<DyctMovSaldoDTO>>
{
    private static final Logger LOG = Logger.getLogger(DyctMovSaldoExtractorResultSet.class);

    private DyctSaldoIcepDTO saldoIcep;
    
    @Override
    public List<DyctMovSaldoDTO> extractData(ResultSet rs) throws SQLException 
    {
        List<DyctMovSaldoDTO> listMovsSaldo = new ArrayList<DyctMovSaldoDTO>();
        DyctAccionMovSalMapper mapperDetalleActividad = new DyctAccionMovSalMapper();
        int i = 0;
        while(rs.next())
        {
            DyctSaldoIcepDTO saldoIcepL;
            if(saldoIcep != null){
                saldoIcepL = saldoIcep;
            }
            else{
                saldoIcepL = new DyctSaldoIcepDTO();
                saldoIcepL.setIdSaldoIcep(rs.getInt("IDSALDOICEP"));
            }

            rs.getInt(UtilsDominio.obtenerNombreColumna("IDACCIONMOVSAL", DyctAccionMovSalMapper.SUBFIJO, rs));

            DyctAccionMovSalDTO accionMovSaldo = !rs.wasNull() ? mapperDetalleActividad.mapRow(rs, i) : null;
            i++;
            Integer idMovSaldo = rs.getInt("IDMOVSALDO");

            DyctMovSaldoDTO movSaldoFila = obtenerMovSaldoPrevio(listMovsSaldo, idMovSaldo);

            if(movSaldoFila == null)
            {
                movSaldoFila = new DyctMovSaldoDTO();
                movSaldoFila.setIdMovSaldo(idMovSaldo);
                movSaldoFila.setDyctSaldoIcepDTO(saldoIcepL);
                movSaldoFila.setImporte(rs.getBigDecimal("IMPORTE"));
                movSaldoFila.setFechaRegistro(rs.getTimestamp("FECHAREGISTRO"));
                movSaldoFila.setFechaOrigen(rs.getDate("FECHAORIGEN"));
                movSaldoFila.setDyccMovIcepDTO(BuscadorConstantes.obtenerMovIcep(rs.getInt("IDMOVIMIENTO"), rs.getInt("IDAFECTACION")));
                movSaldoFila.setIdOrigen(rs.getString("IDORIGEN"));
                movSaldoFila.setFechaFin(rs.getTimestamp("FECHAFIN"));

                movSaldoFila.setDyctAccionMovSalDTOList(new ArrayList<DyctAccionMovSalDTO>());

                listMovsSaldo.add(movSaldoFila);
            }
            LOG.debug("accionMovSaldo ->" + accionMovSaldo);
            if(accionMovSaldo != null){
                movSaldoFila.getDyctAccionMovSalDTOList().add(accionMovSaldo);
            }
        }  

        return listMovsSaldo;   
    }  

    private DyctMovSaldoDTO obtenerMovSaldoPrevio(List<DyctMovSaldoDTO> movsSaldo, Integer idMovSaldo)
    {
        LOG.debug("obtenerMovSaldoPrevio idMovSaldo ->" + idMovSaldo);
        for(DyctMovSaldoDTO movSaldoIt : movsSaldo){
            if(movSaldoIt.getIdMovSaldo().intValue() == idMovSaldo){
                LOG.debug("el idMovSaldo ya esta contenido en la lista");
                return movSaldoIt;
            }

        }
        LOG.debug("obtenerMovSaldoPrevio se retornará nulo");
        return null;
    }

    public DyctSaldoIcepDTO getSaldoIcep() {
        return saldoIcep;
    }

    public void setSaldoIcep(DyctSaldoIcepDTO saldoIcep) {
        this.saldoIcep = saldoIcep;
    }
} 