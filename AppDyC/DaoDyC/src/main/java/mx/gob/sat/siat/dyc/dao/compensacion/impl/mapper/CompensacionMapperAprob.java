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


public class CompensacionMapperAprob implements RowMapper<DycpCompensacionDTO> {
    private Logger logger = Logger.getLogger(CompensacionMapper.class.getName());

    private DyccDictaminadorDTO dictaminador;
    private DycpServicioMapper mapperServicio;
    private DycpCompensacionDTO compensacion;
    private DyctResolCompMapper mapperResolComp;
    private DyctSaldoIcepDTO saldoIcepOrigen;

    public DycpCompensacionDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        DyctSaldoIcepDTO saldoIcepOrigenL;
        if (saldoIcepOrigen != null) {
            saldoIcepOrigenL = saldoIcepOrigen;
        } else {
            saldoIcepOrigenL = new DyctSaldoIcepDTO();
            saldoIcepOrigenL.setIdSaldoIcep(rs.getInt("IDSALDOICEPORIGEN"));
        }

        DycpCompensacionDTO compensacionL;

        if (compensacion != null) {
            compensacionL = compensacion;
        } else {
            compensacionL = new DycpCompensacionDTO();
        }
        compensacionL.setNumControl(rs.getString("NUMCONTROL"));

        compensacionL.setDycpServicioDTO(mappeoServicio(rs, rowNum));

        DyccAprobadorDTO aprobador = new DyccAprobadorDTO();
        aprobador.setNumEmpleado(rs.getInt("NUMEMPLEADOAPROB"));
        aprobador.setClaveNivel(rs.getInt("CLAVENIVEL"));

        compensacionL.setFechaInicioTramite(rs.getDate("FECHAINICIOTRAMITE"));
        compensacionL.setFechaPresentacion(rs.getDate("FECHAPRESENTACION"));
        compensacionL.setImporteCompensado(rs.getBigDecimal("IMPORTECOMPENSADO"));
        compensacionL.setFechaPresentaDec(rs.getDate("FECHAPRESENTADEC"));
        compensacionL.setNumOperacionDec(rs.getString("NUMOPERACIONDEC"));

        compensacionL.setDyccTipoDeclaraDTO(BuscadorConstantes.obtenerTipoDeclaracion(rs.getInt("IDTIPODECLARACION")));
        Integer idEstadoComp = rs.getInt("IDESTADOCOMP");
        logger.debug("idEstadoComp ->" + idEstadoComp + "<-");
        compensacionL.setDyccEstadoCompDTO(BuscadorConstantes.obtenerEstadoComp(idEstadoComp));
        compensacionL.setDyccAprobadorDTO(aprobador);

        DyctSaldoIcepDTO dyctSaldoIcepDestinoDTO = new DyctSaldoIcepDTO();
        dyctSaldoIcepDestinoDTO.setIdSaldoIcep(rs.getInt("IDSALDOICEPDESTINO"));

        compensacionL.setDyctSaldoIcepOrigenDTO(saldoIcepOrigenL);
        compensacionL.setDyctSaldoIcepDestinoDTO(dyctSaldoIcepDestinoDTO);

        DycpAvisoCompDTO avisoComp = null;
        //TODO: validar NULO con resulset
        String fa = rs.getString("FOLIOAVISO");
        if (fa != null && !"".equals(fa)) {
            avisoComp = new DycpAvisoCompDTO();
            avisoComp.setFolioAviso(fa);
        }
        compensacionL.setDycpAvisoCompDTO(avisoComp);
        compensacionL.setRemanenteHistorico(rs.getBigDecimal("REMANENTEHISTORICO"));
        compensacionL.setRemanenteAct(rs.getBigDecimal("REMANENTEACT"));

        if (mapperResolComp != null) {
            try {
                if (rs.getString("NUMCONTROL_RESOLCOMP") != null) {
                    compensacionL.setDyctResolCompDTO(mapperResolComp.mapRow(rs, rowNum));
                }
            } catch (java.sql.SQLException sqle) {
                logger.error("Mensaje de la excepcion ->" + sqle.getMessage() + "<- Si se settea el mapper " +
                             "'mapperResolComp' es necesario poner en la sentencia SQL el alias 'NUMCONTROL_RESOLCOMP' " +
                             "a la columna 'NUMCONTROL' de la tabla 'DYCT_RESOLCOMP'");
            }
        }

        return compensacionL;
    }

    private DycpServicioDTO mappeoServicio(ResultSet rs, int rowNum) throws SQLException {
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
}
