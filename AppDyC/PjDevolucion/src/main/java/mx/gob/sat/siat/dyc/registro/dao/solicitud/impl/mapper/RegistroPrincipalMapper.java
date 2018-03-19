package mx.gob.sat.siat.dyc.registro.dao.solicitud.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.banco.DyccInstCreditoDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccImpuestoDTO;
import mx.gob.sat.siat.dyc.domain.regsolicitud.InformacionDeclarativaDTO;
import mx.gob.sat.siat.dyc.domain.regsolicitud.TramiteDTO;
import mx.gob.sat.siat.dyc.util.common.CatalogoTO;

import org.springframework.jdbc.core.RowMapper;


public class RegistroPrincipalMapper implements RowMapper<TramiteDTO> {
    public RegistroPrincipalMapper() {
        super();
    }

    @Override
    public TramiteDTO mapRow(ResultSet rs, int i) throws SQLException {
        CatalogoTO catalogo = new CatalogoTO();
        CatalogoTO cataTipoTramite = new CatalogoTO();
        CatalogoTO cataSubOrigenSal = new CatalogoTO();
        CatalogoTO cataPeriodo = new CatalogoTO();
        CatalogoTO cataTipoPeriodo = new CatalogoTO();
        CatalogoTO cataEjercicio = new CatalogoTO();
        CatalogoTO cataSubTramite = new CatalogoTO();

        TramiteDTO tramite = new TramiteDTO();
        DyccConceptoDTO concepto = new DyccConceptoDTO();
        DyccImpuestoDTO impuesto = new DyccImpuestoDTO();
        DyccInstCreditoDTO credito = new DyccInstCreditoDTO();
        InformacionDeclarativaDTO informacionD = new InformacionDeclarativaDTO();

        catalogo.setIdNum(rs.getInt("IDORIGENSALDO"));
        catalogo.setDescripcion(rs.getString("DESCRIPCION"));
        tramite.setOrigenSaldo(catalogo);

        cataTipoTramite.setIdNum(rs.getInt("IDTIPOTRAMITE"));
        cataTipoTramite.setDescripcion(rs.getString("DESCRIPTRAMI"));
        tramite.setTipoTramite(cataTipoTramite);

        cataSubOrigenSal.setIdNum(rs.getInt("IDSUBORIGENSALDO"));
        cataSubOrigenSal.setDescripcion(rs.getString("DESCRIPSUBORI"));
        tramite.setSubOrigenSaldo(cataSubOrigenSal);

        /** tramite.setRfcRetenedor(rs.getString("RETENEDORRFC")); */
        tramite.setInfoAdicional(rs.getString("INFOADICIONAL"));

        informacionD.setDiotNumOperacion(rs.getString("DIOTNUMOPERACION"));
        informacionD.setDiotFechPrecentacion(rs.getDate("DIOTFECHAPRESENTA"));
        tramite.setInfoDeclarativa(informacionD);

        cataPeriodo.setIdNum(rs.getInt("IDPERIODO"));
        cataPeriodo.setDescripcion(rs.getString("DESCRIPPERIO"));
        tramite.setPeriodo(cataPeriodo);

        cataTipoPeriodo.setIdString(rs.getString("IDTIPOPERIODO"));
        cataTipoPeriodo.setDescripcion(rs.getString("DESCRIPTIPOPERI"));
        tramite.setTipoPeriodo(cataTipoPeriodo);

        cataEjercicio.setIdNum(rs.getInt("IDEJERCICIO"));
        cataEjercicio.setDescripcion(rs.getString("PERMITESELECCION"));
        tramite.setEjercicio(cataEjercicio);

        cataSubTramite.setIdNum(rs.getInt("IDSUBTIPOTRAMITE"));
        cataSubTramite.setDescripcion(rs.getString("DESCRIPSUBTRA"));
        tramite.setSubTramite(cataSubTramite);

        concepto.setDescripcion(rs.getString("DESCRIPCONCEPTO"));
        tramite.setConcepto(concepto);

        impuesto.setDescripcion(rs.getString("DESCRIPIMPUES"));
        tramite.setImpuesto(impuesto);

        credito.setDescripcion(rs.getString("DESCRIPCREDITO"));
        /**tramite.setInstitucionFinanciera(credito);*/

        return tramite;
    }
}
