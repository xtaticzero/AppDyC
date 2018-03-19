package mx.gob.sat.siat.dyc.dao.compensacion.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.dao.resolucion.impl.mapper.DyctResolCompMapper;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpAvisoCompDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaOrigenTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaServOrigenDTO;
import mx.gob.sat.siat.dyc.util.BuscadorConstantes;
import mx.gob.sat.siat.dyc.util.UtilsDominio;

import org.apache.log4j.Logger;

import org.springframework.jdbc.core.RowMapper;


public class CompensacionMapper implements RowMapper<DycpCompensacionDTO>
{
    private static final Logger LOG = Logger.getLogger(CompensacionMapper.class);

    private DycpServicioMapper mapperServicio;
    private DyctResolCompMapper mapperResolComp;
    private DyctSaldoIcepMapper mapperSaldoIcepOrigen;
    private DyccDictaminadorDTO dictaminador;
    private DycpCompensacionDTO compensacion;
    private DyctSaldoIcepDTO saldoIcepOrigen;
    private DycpAvisoCompDTO avisoComp;

    public static final  String SUBFIJO = "_COMPENSACION";

    public DycpCompensacionDTO mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        DyctSaldoIcepDTO saldoIcepOrigenL;
        
        if(mapperSaldoIcepOrigen != null){
            saldoIcepOrigenL = mapperSaldoIcepOrigen.mapRow(rs, rowNum);
        }
        else{
            if (saldoIcepOrigen != null) {
                saldoIcepOrigenL = saldoIcepOrigen;
            } else {
                saldoIcepOrigenL = new DyctSaldoIcepDTO();
                saldoIcepOrigenL.setIdSaldoIcep(rs.getInt(UtilsDominio.obtenerNombreColumna("IDSALDOICEPORIGEN", SUBFIJO, rs)));
            }
        }

        DycpCompensacionDTO compensacionL;

        if (compensacion != null) {
            compensacionL = compensacion;
        } else {
            compensacionL = new DycpCompensacionDTO();
        }
        compensacionL.setNumControl(rs.getString(UtilsDominio.obtenerNombreColumna("NUMCONTROL", SUBFIJO, rs)));

        compensacionL.setDycpServicioDTO(settearServicio(rs, rowNum));

        DyccAprobadorDTO aprobador = new DyccAprobadorDTO();
        aprobador.setNumEmpleado(rs.getInt(UtilsDominio.obtenerNombreColumna("NUMEMPLEADOAPROB", SUBFIJO, rs)));

        compensacionL.setFechaInicioTramite(rs.getDate(UtilsDominio.obtenerNombreColumna("FECHAINICIOTRAMITE", SUBFIJO, rs)));
        compensacionL.setFechaPresentacion(rs.getDate(UtilsDominio.obtenerNombreColumna("FECHAPRESENTACION", SUBFIJO, rs)));
        compensacionL.setImporteCompensado(rs.getBigDecimal(UtilsDominio.obtenerNombreColumna("IMPORTECOMPENSADO", SUBFIJO, rs)));
        compensacionL.setFechaPresentaDec(rs.getDate(UtilsDominio.obtenerNombreColumna("FECHAPRESENTADEC", SUBFIJO, rs)));
        compensacionL.setNumOperacionDec(rs.getString(UtilsDominio.obtenerNombreColumna("NUMOPERACIONDEC", SUBFIJO, rs)));

        compensacionL.setDyccTipoDeclaraDTO(BuscadorConstantes.obtenerTipoDeclaracion(rs.getInt(UtilsDominio.obtenerNombreColumna("IDTIPODECLARACION", SUBFIJO, rs))));
        Integer idEstadoComp = rs.getInt(UtilsDominio.obtenerNombreColumna("IDESTADOCOMP", SUBFIJO, rs));
        LOG.debug("idEstadoComp ->" + idEstadoComp + "<-");
        compensacionL.setDyccEstadoCompDTO(BuscadorConstantes.obtenerEstadoComp(idEstadoComp));
        compensacionL.setDyccAprobadorDTO(aprobador);

        DyctSaldoIcepDTO dyctSaldoIcepDestinoDTO = new DyctSaldoIcepDTO();
        dyctSaldoIcepDestinoDTO.setIdSaldoIcep(rs.getInt(UtilsDominio.obtenerNombreColumna("IDSALDOICEPDESTINO", SUBFIJO, rs)));

        compensacionL.setDyctSaldoIcepOrigenDTO(saldoIcepOrigenL);
        compensacionL.setDyctSaldoIcepDestinoDTO(dyctSaldoIcepDestinoDTO);

        DycpAvisoCompDTO avisoCompL;
        
        if(avisoComp != null){
            avisoCompL = avisoComp;
        }
        else
        {
            //TODO: validar NULO con resulset
            String fa = rs.getString(UtilsDominio.obtenerNombreColumna("FOLIOAVISO", SUBFIJO, rs));
            if (fa != null && !"".equals(fa)) {
                avisoCompL = new DycpAvisoCompDTO();
                avisoCompL.setFolioAviso(fa);
            }
            else{
                avisoCompL = null;
            }
        }

        compensacionL.setDycpAvisoCompDTO(avisoCompL);
        compensacionL.setRemanenteHistorico(rs.getBigDecimal(UtilsDominio.obtenerNombreColumna("REMANENTEHISTORICO", SUBFIJO, rs)));
        compensacionL.setRemanenteAct(rs.getBigDecimal(UtilsDominio.obtenerNombreColumna("REMANENTEACT", SUBFIJO, rs)));

        if (mapperResolComp != null) {
            try {
                if (rs.getString("NUMCONTROL_RESOLCOMP") != null) {
                    compensacionL.setDyctResolCompDTO(mapperResolComp.mapRow(rs, rowNum));
                }
            } catch (java.sql.SQLException sqle) {
                LOG.error("Mensaje de la excepcion ->" + sqle.getMessage() + "<- Si se settea el mapper " +
                             "'mapperResolComp' es necesario poner en la sentencia SQL el alias 'NUMCONTROL_RESOLCOMP' " +
                             "a la columna 'NUMCONTROL' de la tabla 'DYCT_RESOLCOMP'");
            }
        }

        return compensacionL;
    }

    private DycpServicioDTO settearServicio(ResultSet rs, int rowNum) throws SQLException
    {
        DycpServicioDTO servicio;
        
        if (mapperServicio != null) {
            servicio = mapperServicio.mapRow(rs, rowNum);
        } else {
            servicio = new DycpServicioDTO();
            servicio.setNumControl(rs.getString("NUMCONTROL"));
            //TODO: Quitar las siguientes 2 lineas, no cumplen con el estandar, son redundantes
            if (UtilsDominio.existeColumna(rs, "RFCCONTRIBUYENTE")) {
                servicio.setRfcContribuyente(rs.getString("RFCCONTRIBUYENTE"));
            }
            if (UtilsDominio.existeColumna(rs, "CLAVEADM")) {
                servicio.setClaveAdm(rs.getInt("CLAVEADM"));
            }            

            if (UtilsDominio.existeColumna(rs, "IDTIPOSERVICIO")) {
                DycaServOrigenDTO serviOri = new DycaServOrigenDTO();
                serviOri.setDyccTipoServicioDTO(BuscadorConstantes.obtenerTipoServicio(rs.getInt("IDTIPOSERVICIO")));
                DycaOrigenTramiteDTO origenTramite = new DycaOrigenTramiteDTO();
                origenTramite.setDycaServOrigenDTO(serviOri);
                servicio.setDycaOrigenTramiteDTO(origenTramite);
            }
            else{
                LOG.debug("el resultSet no contiene la columna IDTIPOSERVICIO");
            }
        }
        return servicio;
    }

    public DyccDictaminadorDTO getDictaminador() {
        return dictaminador;
    }

    public void setDictaminador(DyccDictaminadorDTO dictaminador) {
        this.dictaminador = dictaminador;
    }

    public DycpServicioMapper getMapperServicio() {
        return mapperServicio;
    }

    public void setMapperServicio(DycpServicioMapper mapperServicio) {
        this.mapperServicio = mapperServicio;
    }

    public DycpCompensacionDTO getCompensacion() {
        return compensacion;
    }

    public void setCompensacion(DycpCompensacionDTO compensacion) {
        this.compensacion = compensacion;
    }

    public DyctResolCompMapper getMapperResolComp() {
        return mapperResolComp;
    }

    public void setMapperResolComp(DyctResolCompMapper mapperResolComp) {
        this.mapperResolComp = mapperResolComp;
    }

    public DyctSaldoIcepDTO getSaldoIcepOrigen() {
        return saldoIcepOrigen;
    }

    public void setSaldoIcepOrigen(DyctSaldoIcepDTO saldoIcepOrigen) {
        this.saldoIcepOrigen = saldoIcepOrigen;
    }

    public DycpAvisoCompDTO getAvisoComp() {
        return avisoComp;
    }

    public void setAvisoComp(DycpAvisoCompDTO avisoComp) {
        this.avisoComp = avisoComp;
    }

    public DyctSaldoIcepMapper getMapperSaldoIcepOrigen() {
        return mapperSaldoIcepOrigen;
    }

    public void setMapperSaldoIcepOrigen(DyctSaldoIcepMapper mapperSaldoIcepOrigen) {
        this.mapperSaldoIcepOrigen = mapperSaldoIcepOrigen;
    }
}
