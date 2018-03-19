package mx.gob.sat.siat.dyc.dao.compensacion.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.dao.tipotramite.impl.mapper.TipoTramiteMapper;
import mx.gob.sat.siat.dyc.dao.usuario.impl.mapper.ContribuyenteMapper;
import mx.gob.sat.siat.dyc.dao.usuario.impl.mapper.DictaminadorMapper;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaOrigenTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaServOrigenDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoServicioDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.util.BuscadorConstantes;
import mx.gob.sat.siat.dyc.util.UtilsDominio;
import mx.gob.sat.siat.dyc.vistas.dao.impl.mapper.AdmcUnidadAdmvaMapper;

import org.apache.log4j.Logger;

import org.springframework.jdbc.core.RowMapper;


public class DycpServicioMapper implements RowMapper<DycpServicioDTO>
{
    private static final Logger LOG = Logger.getLogger(DycpServicioMapper.class);
    
    private DyccTipoServicioDTO tipoServicio;
    private DyccDictaminadorDTO dictaminador;
    private DycaOrigenTramiteDTO dycaOrigenTramiteDTO;
    private ContribuyenteMapper mapperContribuyente;
    private DycpServicioDTO servicio;
    private TipoTramiteMapper mapperTipoTramite;
    private DictaminadorMapper mapperDictaminador;
    private CompensacionMapper mapperCompensacion;
    private DycpSolicitudMapper mapperSolicitud;
    private AdmcUnidadAdmvaMapper mapperUnidadAdmva;

    public static final  String SUBFIJO = "_SERVICIO";

    @Override
    public DycpServicioDTO mapRow(ResultSet rs, int i) throws SQLException {
        DycpServicioDTO servicioL;
        if (servicio != null) {
            servicioL = servicio;
        } else {
            servicioL = new DycpServicioDTO();
        }

        servicioL.setNumControl(rs.getString(UtilsDominio.obtenerNombreColumna("NUMCONTROL", SUBFIJO, rs)));
        servicioL.setClaveAdm(rs.getInt(UtilsDominio.obtenerNombreColumna("CLAVEADM", SUBFIJO, rs)));
        servicioL.setRfcContribuyente(rs.getString(UtilsDominio.obtenerNombreColumna("RFCCONTRIBUYENTE", SUBFIJO, rs)));

        DyccDictaminadorDTO dictaminadorAux;
        if (dictaminador != null) {
            dictaminadorAux = dictaminador;
        } 
        else 
        {
            Integer numEmpleadoDict = rs.getInt(UtilsDominio.obtenerNombreColumna("NUMEMPLEADODICT", SUBFIJO, rs));
            LOG.debug("numEmpleadoDict ->" + numEmpleadoDict);
            if(!rs.wasNull())
            {
                if (mapperDictaminador != null) {
                    dictaminadorAux = mapperDictaminador.mapRow(rs, i);
                } else {
                    dictaminadorAux = new DyccDictaminadorDTO();
                    dictaminadorAux.setNumEmpleado(numEmpleadoDict);
                }
            }
            else{
                LOG.debug("numEmpleadoDict ->" + numEmpleadoDict + " es nulo el dictaminador");
                dictaminadorAux = null;
            }
        }
        servicioL.setDyccDictaminadorDTO(dictaminadorAux);

        DycaOrigenTramiteDTO origenTramiteL;

        if (dycaOrigenTramiteDTO != null) {
            origenTramiteL = dycaOrigenTramiteDTO;
        } else {
            origenTramiteL = new DycaOrigenTramiteDTO();
            DyccTipoTramiteDTO tipoTramite;
            if (mapperTipoTramite != null) {
                tipoTramite = mapperTipoTramite.mapRow(rs, i);
            } else {
                tipoTramite = new DyccTipoTramiteDTO();
                tipoTramite.setIdTipoTramite(rs.getInt(UtilsDominio.obtenerNombreColumna("IDTIPOTRAMITE", SUBFIJO, rs)));
            }

            DycaServOrigenDTO tiposervicioOrigen = new DycaServOrigenDTO();
            tiposervicioOrigen.setDyccOrigenSaldoDTO(BuscadorConstantes.obtenerOrigenSaldo(rs.getInt(UtilsDominio.obtenerNombreColumna("IDORIGENSALDO", SUBFIJO, rs))));

            if (tipoServicio != null) {
                tiposervicioOrigen.setDyccTipoServicioDTO(tipoServicio);
            } else {
                tiposervicioOrigen.setDyccTipoServicioDTO(BuscadorConstantes.obtenerTipoServicio(rs.getInt(UtilsDominio.obtenerNombreColumna("IDTIPOSERVICIO", SUBFIJO, rs))));
            }

            origenTramiteL.setDycaServOrigenDTO(tiposervicioOrigen);
            origenTramiteL.setDyccTipoTramiteDTO(tipoTramite);
        }

        servicioL.setDycaOrigenTramiteDTO(origenTramiteL);

        if (mapperContribuyente != null) {
            /** TODO: Revisar recursividad en ExtractorUtils
           mapperContribuyente.setServicio(servicioL);  */
            servicioL.setDyctContribuyenteDTO(mapperContribuyente.mapRow(rs, i));
        }

        if (mapperCompensacion != null) {
            DycpCompensacionDTO comp = mapperCompensacion.mapRow(rs, i);
            servicioL.setDycpCompensacionDTO(comp);
        }

        if (mapperSolicitud != null) {
            DycpSolicitudDTO sol = mapperSolicitud.mapRow(rs, i);
            servicioL.setDycpSolicitudDTO(sol);
        }

        if(mapperUnidadAdmva != null){
            servicioL.setDyccUnidadAdmvaDTO(mapperUnidadAdmva.mapRow(rs, i));
        }

        return servicioL;
    }

    public DyccTipoServicioDTO getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(DyccTipoServicioDTO tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public ContribuyenteMapper getMapperContribuyente() {
        return mapperContribuyente;
    }

    public void setMapperContribuyente(ContribuyenteMapper mapperContribuyente) {
        this.mapperContribuyente = mapperContribuyente;
    }

    public DycpServicioDTO getServicio() {
        return servicio;
    }

    public void setServicio(DycpServicioDTO servicio) {
        this.servicio = servicio;
    }

    public void setDictaminador(DyccDictaminadorDTO dyccDictaminadorDTO) {
        this.dictaminador = dyccDictaminadorDTO;
    }

    public DyccDictaminadorDTO getDictaminador() {
        return dictaminador;
    }

    public void setDycaOrigenTramiteDTO(DycaOrigenTramiteDTO dycaOrigenTramiteDTO) {
        this.dycaOrigenTramiteDTO = dycaOrigenTramiteDTO;
    }

    public DycaOrigenTramiteDTO getDycaOrigenTramiteDTO() {
        return dycaOrigenTramiteDTO;
    }

    public void setMapperTipoTramite(TipoTramiteMapper mapperTipoTramite) {
        this.mapperTipoTramite = mapperTipoTramite;
    }

    public TipoTramiteMapper getMapperTipoTramite() {
        return mapperTipoTramite;
    }

    public void setMapperDictaminador(DictaminadorMapper mapperDictaminador) {
        this.mapperDictaminador = mapperDictaminador;
    }

    public DictaminadorMapper getMapperDictaminador() {
        return mapperDictaminador;
    }

    public void setMapperCompensacion(CompensacionMapper mapperCompensacion) {
        this.mapperCompensacion = mapperCompensacion;
    }

    public CompensacionMapper getMapperCompensacion() {
        return mapperCompensacion;
    }

    public AdmcUnidadAdmvaMapper getMapperUnidadAdmva() {
        return mapperUnidadAdmva;
    }

    public void setMapperUnidadAdmva(AdmcUnidadAdmvaMapper mapperUnidadAdmva) {
        this.mapperUnidadAdmva = mapperUnidadAdmva;
    }

    public void setMapperSolicitud(DycpSolicitudMapper mapperSolicitud) {
        this.mapperSolicitud = mapperSolicitud;
    }

    public DycpSolicitudMapper getMapperSolicitud() {
        return mapperSolicitud;
    }
}
