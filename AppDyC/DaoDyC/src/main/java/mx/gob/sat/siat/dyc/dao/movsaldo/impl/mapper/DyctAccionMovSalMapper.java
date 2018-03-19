package mx.gob.sat.siat.dyc.dao.movsaldo.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.dao.documento.impl.mapper.DyctAccionPrivAjusMapper;
import mx.gob.sat.siat.dyc.domain.movsaldo.DyctAccionPrivAjusDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctAccionMovSalDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctMovSaldoDTO;
import mx.gob.sat.siat.dyc.util.BuscadorConstantes;
import mx.gob.sat.siat.dyc.util.UtilsDominio;

import org.springframework.jdbc.core.RowMapper;


/**
 *
 * @author Softtek
 */
public class DyctAccionMovSalMapper implements RowMapper<DyctAccionMovSalDTO>
{
    private DyctMovSaldoDTO movSaldo;

    private DyctMovSaldoMapper mapperMovSaldo;
    private DyctAccionPrivAjusMapper mapperPrivilegios;

    public static final  String SUBFIJO = "_ACCIONMOVSAL";

    @Override
    public DyctAccionMovSalDTO mapRow(ResultSet rs, int i) throws SQLException
    {
        DyctMovSaldoDTO movSaldoL;
        if(movSaldo != null){
            movSaldoL = movSaldo;
        }
        else
        {
            if(mapperMovSaldo != null){
                movSaldoL = mapperMovSaldo.mapRow(rs, i);
            }
            else{
                movSaldoL = new DyctMovSaldoDTO();
                movSaldoL.setIdMovSaldo(rs.getInt(UtilsDominio.obtenerNombreColumna("IDMOVSALDO", SUBFIJO, rs)));
            }
        }

        DyctAccionPrivAjusDTO accionOtorgarPerm;
        if(mapperPrivilegios != null){
            accionOtorgarPerm = mapperPrivilegios.mapRow(rs, i);
        }
        else{
            accionOtorgarPerm = new DyctAccionPrivAjusDTO();
            accionOtorgarPerm.setIdAccionPrivAjus(rs.getInt(UtilsDominio.obtenerNombreColumna("IDACCIONPRIVAJUS", SUBFIJO, rs)));
        }

        DyctAccionMovSalDTO accionMovSaldo = new DyctAccionMovSalDTO();
        accionMovSaldo.setIdAccionMovSal(rs.getInt(UtilsDominio.obtenerNombreColumna("IDACCIONMOVSAL", SUBFIJO, rs)));
        accionMovSaldo.setDyctMovSaldoDTO(movSaldoL);
        accionMovSaldo.setTipoAccionMovSal(BuscadorConstantes.obtenerTipoAccionMovSaldo(rs.getInt(UtilsDominio.obtenerNombreColumna("IDTIPOACCIONMOVSAL", SUBFIJO, rs))));
        accionMovSaldo.setFechaRegistro(rs.getTimestamp(UtilsDominio.obtenerNombreColumna("FECHAREGISTRO", SUBFIJO, rs)));
        accionMovSaldo.setJustificacion(rs.getString(UtilsDominio.obtenerNombreColumna("JUSTIFICACION", SUBFIJO, rs)));
        accionMovSaldo.setDyctAccionPrivAjusDTO(accionOtorgarPerm);
        return accionMovSaldo;
    }

    public DyctMovSaldoDTO getMovSaldo() {
        return movSaldo;
    }

    public void setMovSaldo(DyctMovSaldoDTO movSaldo) {
        this.movSaldo = movSaldo;
    }

    public DyctMovSaldoMapper getMapperMovSaldo() {
        return mapperMovSaldo;
    }

    public void setMapperMovSaldo(DyctMovSaldoMapper mapperMovSaldo) {
        this.mapperMovSaldo = mapperMovSaldo;
    }

    public DyctAccionPrivAjusMapper getMapperPrivilegios() {
        return mapperPrivilegios;
    }

    public void setMapperPrivilegios(DyctAccionPrivAjusMapper mapperPrivilegios) {
        this.mapperPrivilegios = mapperPrivilegios;
    }

}
