/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.dao.regsolicitud.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccImpuestoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaOrigenTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaServOrigenDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.vo.SolicitudAdministrarSolVO;

import org.apache.log4j.Logger;

import org.springframework.jdbc.core.RowMapper;


/**
 * Clase Mapper para la tabla [DYCP_SOLICITUD]
 * @author Federico Chopin Gachuz
 * @date Octubre 30, 2013
 *
 * */
public class DycpSolicitudAdministrarSolMapper implements RowMapper<SolicitudAdministrarSolVO> {


    private static final Logger LOG = Logger.getLogger(DycpSolicitudAdministrarSolMapper.class.getName());

    public DycpSolicitudAdministrarSolMapper() {
        super();
    }

    @Override
    public SolicitudAdministrarSolVO mapRow(ResultSet rs, int i) throws SQLException {

        DyccImpuestoDTO dyccImpuestoDTO = new DyccImpuestoDTO();
        dyccImpuestoDTO.setIdImpuesto(rs.getInt("IDIMPUESTO"));

        DyccPeriodoDTO dyccPeriodoDTO = new DyccPeriodoDTO();
        dyccPeriodoDTO.setIdPeriodo(rs.getInt("IDPERIODO"));

        DyccEjercicioDTO dyccEjercicioDTO = new DyccEjercicioDTO();
        dyccEjercicioDTO.setIdEjercicio(rs.getInt("EJERCICIO"));

        DyccConceptoDTO dyccConceptoDTO = new DyccConceptoDTO();
        dyccConceptoDTO.setDyccImpuestoDTO(dyccImpuestoDTO);
        dyccConceptoDTO.setIdConcepto(rs.getInt("IDCONCEPTO"));

        DyctSaldoIcepDTO dyctSaldoIcepDTO = new DyctSaldoIcepDTO();
        dyctSaldoIcepDTO.setDyccEjercicioDTO(dyccEjercicioDTO);
        dyctSaldoIcepDTO.setDyccPeriodoDTO(dyccPeriodoDTO);
        dyctSaldoIcepDTO.setDyccConceptoDTO(dyccConceptoDTO);

        SolicitudAdministrarSolVO sol = new SolicitudAdministrarSolVO();
        sol.setNumControl(rs.getString("NUMCONTROL"));
        sol.setDycpServicioDTO(mappeoServicioDTO(rs));
        sol.setFechaPresentacion(rs.getDate("FECHAPRESENTACION"));
        sol.setTipoTramite(rs.getString("TIPOTRAMITE"));
        sol.setImporteSolicitado(rs.getBigDecimal("IMPORTESOLICITADO"));
        sol.setEstadoSolicitud(rs.getString("ESTADOSOLICITUD"));
        sol.setNombre(rs.getString("NOMBRE"));
        sol.setApPaterno(rs.getString("APELLIDOPATERNO"));
        sol.setApMaterno(rs.getString("APELLIDOMATERNO"));
        sol.setRolGranContribuyente(rs.getInt("ROLGRANCONTRIB"));
        sol.setDias(rs.getInt("DIAS"));
        sol.setTipoDia(rs.getInt("TIPODIA"));
        sol.setDyctSaldoIcepDTO(dyctSaldoIcepDTO);
        sol.setPeriodo(rs.getString("PERIODO"));
        sol.setOrigenDevolucion(rs.getString("ORIGEN"));
        sol.setRolDictaminado(rs.getString("ROLDICTAMINADO"));
        sol.setNumRequerimiento(rs.getInt("NUMREQUERIMIENTO"));
        sol.setEstadoNotificacion(rs.getString("ESTADONOTIFICACION"));
        sol.setNumControlDoc(rs.getString("NUMCONTROLDOC"));
        sol.setFechaResolucion(rs.getDate("FECHARESOLUCION"));

        if (rs.getObject("IMPORTEAUTORIZADO") != null) {
            sol.setImpAutorizado(rs.getDouble("IMPORTEAUTORIZADO"));
        } else {
            sol.setImpAutorizado(null);
        }

        sol.setIdFacultades(rs.getLong("IDFACULTADES"));
        sol.setNumEmpleadoFac(rs.getInt("NUMEMPLEADOFAC"));
        sol.setFolio(rs.getString("FOLIO"));
        sol.setFechaInicio(rs.getDate("FECHAINICIO"));
        sol.setFechaAprobacion(rs.getDate("FECHAAPROBACION"));

        return sol;
    }

    private DycpServicioDTO mappeoServicioDTO(ResultSet rs) throws SQLException {
        DyccOrigenSaldoDTO dyccOrigenSaldoDTO = new DyccOrigenSaldoDTO();
        DycaServOrigenDTO dycaServOrigenDTO = new DycaServOrigenDTO();
        DyccDictaminadorDTO dyccDictaminadorDTO = new DyccDictaminadorDTO();

        try {
            dyccDictaminadorDTO.setNumEmpleado(rs.getInt("NUMEMPLEADODICT"));
        } catch (Exception e) {
            LOG.debug(String.format("%s: %s", "No se encuentra el campo NUMEMPLEADODICT: ", e.getMessage()));
        }

        dyccOrigenSaldoDTO.setIdOrigenSaldo(rs.getInt("IDORIGENSALDO"));
        dycaServOrigenDTO.setDyccOrigenSaldoDTO(dyccOrigenSaldoDTO);

        DyccTipoTramiteDTO dyccTipoTramiteDTO = new DyccTipoTramiteDTO();
        DycaOrigenTramiteDTO dycaOrigenTramiteDTO = new DycaOrigenTramiteDTO();
        DycpServicioDTO dycpServicioDTO = new DycpServicioDTO();

        dyccTipoTramiteDTO.setIdTipoTramite(rs.getInt("IDTIPOTRAMITE"));
        dycaOrigenTramiteDTO.setDyccTipoTramiteDTO(dyccTipoTramiteDTO);
        dycaOrigenTramiteDTO.setDycaServOrigenDTO(dycaServOrigenDTO);

        dycpServicioDTO.setClaveAdm(rs.getInt("CLAVEADMINISTRACION"));
        dycpServicioDTO.setRfcContribuyente(rs.getString("RFCCONTRIBUYENTE"));

        dycpServicioDTO.setDycaOrigenTramiteDTO(dycaOrigenTramiteDTO);
        dycpServicioDTO.setDyccDictaminadorDTO(dyccDictaminadorDTO);

        return dycpServicioDTO;
    }
}
