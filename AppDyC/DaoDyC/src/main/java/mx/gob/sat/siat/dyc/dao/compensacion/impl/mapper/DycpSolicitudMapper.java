package mx.gob.sat.siat.dyc.dao.compensacion.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.dao.resolucion.impl.mapper.DyctResolucionMapper;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccEstadoSolDTO;
import mx.gob.sat.siat.dyc.domain.documento.DyccSubTramiteDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctResolucionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.suborigensal.DyccActividadDTO;
import mx.gob.sat.siat.dyc.domain.suborigensal.DyccSubOrigSaldoDTO;
import mx.gob.sat.siat.dyc.domain.suborigensal.DyccSubSaldoTramDTO;
import mx.gob.sat.siat.dyc.util.BuscadorConstantes;
import mx.gob.sat.siat.dyc.util.UtilsDominio;
import mx.gob.sat.siat.dyc.util.constante.ConstantesIds;

import org.apache.log4j.Logger;

import org.springframework.jdbc.core.RowMapper;

public class DycpSolicitudMapper implements RowMapper<DycpSolicitudDTO> {

    private static final Logger LOG = Logger.getLogger(DycpSolicitudMapper.class);

    private DyctSaldoIcepDTO saldoIcep;
    private DyctResolucionMapper mapperResolucion;
    private DycpServicioMapper mapperServicio;
    private DyctSaldoIcepMapper mapperSaldoIcep;

    @Override
    public DycpSolicitudDTO mapRow(ResultSet rs, int i) throws SQLException {
        DycpSolicitudDTO solicitud = new DycpSolicitudDTO();

        DycpServicioDTO servicioL;

        if (mapperServicio != null) {
            servicioL = mapperServicio.mapRow(rs, i);
        } else {
            servicioL = new DycpServicioDTO();
            servicioL.setNumControl(rs.getString("NUMCONTROL"));
        }

        solicitud.setDycpServicioDTO(servicioL);

        solicitud.setNumControl(rs.getString("NUMCONTROL"));
        solicitud.setFechaPresentacion(rs.getDate("FECHAPRESENTACION"));
        solicitud.setFechaInicioTramite(rs.getDate("FECHAINICIOTRAMITE"));
        solicitud.setImporteSolicitado(rs.getBigDecimal("IMPORTESOLICITADO"));
        solicitud.setInfoAdicional(rs.getString("INFOADICIONAL"));
        solicitud.setDiotNumOperacion(rs.getString("DIOTNUMOPERACION"));
        solicitud.setDiotFechaPresenta(rs.getDate("DIOTFECHAPRESENTA"));
        solicitud.setRetenedorRfc(rs.getString("RETENEDORRFC"));
        solicitud.setAltexConstancia(rs.getString("ALTEXCONSTANCIA"));
        Integer esCertificado = rs.getInt("ESCERTIFICADO");
        if (!rs.wasNull()) {
            if (esCertificado.equals(ConstantesIds.ES_CERTIFICADO)) {
                solicitud.setEsCertificado(Boolean.TRUE);
            } else {
                solicitud.setEsCertificado(Boolean.FALSE);
            }
        }

        solicitud.setDyccEstadoSolDTO(obtenerEstadoSol(rs));
        DyccSubOrigSaldoDTO subOrigSaldo = new DyccSubOrigSaldoDTO();
        subOrigSaldo.setIdSuborigenSaldo(rs.getInt("IDSUBORIGENSALDO"));
        DyccSubSaldoTramDTO subSaldoTram = new DyccSubSaldoTramDTO();
        subSaldoTram.setDyccSuborigSaldoDTO(subOrigSaldo);
        DyccSubTramiteDTO subTramite = new DyccSubTramiteDTO();
        subTramite.setIdSubTipoTramite(rs.getInt("IDSUBTIPOTRAMITE"));
        solicitud.setDyccSubtramiteDTO(subTramite);
        solicitud.setFechaFinTramite(rs.getDate("FECHAFINTRAMITE"));
        DyccActividadDTO actividad = new DyccActividadDTO();
        actividad.setIdActividad(rs.getInt("IDACTIVIDAD"));
        solicitud.setDyccActividadDTO(actividad);

        solicitud.setResolAutomatica(rs.getInt("RESOLAUTOMATICA"));

        if (mapperSaldoIcep != null) {
            solicitud.setDyctSaldoIcepDTO(mapperSaldoIcep.mapRow(rs, i));
        } else {
            if (saldoIcep != null) {
                solicitud.setDyctSaldoIcepDTO(saldoIcep);
            } else {
                DyctSaldoIcepDTO dyctSaldoIcepDTO = new DyctSaldoIcepDTO();
                dyctSaldoIcepDTO.setIdSaldoIcep(rs.getInt("IDSALDOICEP"));
                solicitud.setDyctSaldoIcepDTO(dyctSaldoIcepDTO);
            }
        }

        solicitud.setDyctResolucionDTO(obtenerResolucion(rs, i, solicitud));

        solicitud.setCadenaOriginal(rs.getString("CADENAORIGINAL"));
        solicitud.setSelloDigital(rs.getString("SELLODIGITAL"));

        return solicitud;
    }

    private DyctResolucionDTO obtenerResolucion(ResultSet rs, int i, DycpSolicitudDTO solicitud) throws SQLException {

        if (mapperResolucion != null) {
            if (UtilsDominio.existeColumna(rs, "NUMCONTROL_RESOLUCION")) {
                if (null != rs.getString("NUMCONTROL_RESOLUCION")) {
                    mapperResolucion.setSolicitud(solicitud);
                    return mapperResolucion.mapRow(rs, i);
                }
            } else {
                LOG.error("si se settea el mapperResolucion se tiene que agregar el alias NUMCONTROL_RESOLUCION");
            }
        }

        return null;
    }

    private DyccEstadoSolDTO obtenerEstadoSol(ResultSet rs) throws SQLException {

        if (rs.getString("IDESTADOSOLICITUD") != null) {
            return BuscadorConstantes.obtenerEstadoSolicitud(rs.getInt("IDESTADOSOLICITUD"));
        }
        return null;
    }

    public DyctSaldoIcepDTO getSaldoIcep() {
        return saldoIcep;
    }

    public void setSaldoIcep(DyctSaldoIcepDTO saldoIcep) {
        this.saldoIcep = saldoIcep;
    }

    public DyctResolucionMapper getMapperResolucion() {
        return mapperResolucion;
    }

    public void setMapperResolucion(DyctResolucionMapper mapperResolucion) {
        this.mapperResolucion = mapperResolucion;
    }

    public DycpServicioMapper getMapperServicio() {
        return mapperServicio;
    }

    public void setMapperServicio(DycpServicioMapper mapperServicio) {
        this.mapperServicio = mapperServicio;
    }

    public DyctSaldoIcepMapper getMapperSaldoIcep() {
        return mapperSaldoIcep;
    }

    public void setMapperSaldoIcep(DyctSaldoIcepMapper mapperSaldoIcep) {
        this.mapperSaldoIcep = mapperSaldoIcep;
    }

}
