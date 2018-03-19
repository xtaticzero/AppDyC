package mx.gob.sat.siat.dyc.gestionsol.dao.aprobardocumento.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.gestionsol.dao.aprobardocumento.BandejaDocumentosDAO;
import mx.gob.sat.siat.dyc.gestionsol.dao.aprobardocumento.impl.mapper.AprobarDocumentoMapper;
import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.BandejaDocumentosDTO;
import mx.gob.sat.siat.dyc.util.common.Paginador;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleAprobarDoc;

import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

/**
 * @author Ericka Janth Ibarra Ponce
 * @date 02/2014
 *
 *
 */
@Repository(value = "bandejaDocumentosDAO")
public class BandejaDocumentosDAOImpl implements BandejaDocumentosDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private static final String FORMAT_SEARCH_STRING = "%%%s%%";

    public BandejaDocumentosDAOImpl() {
        super();
    }

    private final Logger log = Logger.getLogger(BandejaDocumentosDAOImpl.class.getName());

    @Override
    public List<BandejaDocumentosDTO> buscarBandejaDoc(String numEmpleado, String numControl, String rfc, Paginador paginador) throws SIATException {

        try {
            SimpleJdbcCall simpleCall
                    = new SimpleJdbcCall(jdbcTemplateDYC).
                    withFunctionName("DYCSF_DOCUMENTOSPAGINADOR").
                    withoutProcedureColumnMetaDataAccess().
                    declareParameters(
                            new SqlOutParameter("cur", OracleTypes.CURSOR, new AprobarDocumentoMapper()),
                            new SqlParameter("V_NUMEMPLEADOAPROB", OracleTypes.NUMBER),
                            new SqlParameter("V_NUMCONTROL", OracleTypes.VARCHAR),
                            new SqlParameter("V_RFCCONTRIBUYENTE", OracleTypes.VARCHAR),
                            new SqlParameter("V_INFERIOR", OracleTypes.NUMBER),
                            new SqlParameter("V_SUPERIOR", OracleTypes.NUMBER)
                    );

            SqlParameterSource parametros
                    = new MapSqlParameterSource().
                    addValue("V_NUMEMPLEADOAPROB", Integer.valueOf(numEmpleado)).
                    addValue("V_NUMCONTROL", String.format(FORMAT_SEARCH_STRING, numControl)).
                    addValue("V_RFCCONTRIBUYENTE", String.format(FORMAT_SEARCH_STRING, rfc)).
                    addValue("V_INFERIOR", paginador.getFrom()).
                    addValue("V_SUPERIOR", paginador.getTo());

            Map<String, Object> execute = simpleCall.execute(parametros);

            return (ArrayList<BandejaDocumentosDTO>) execute.get("cur");

        } catch (Exception dae) {
            log.error(
                    String.format(
                            "%s %s %s %s %s %s", ConstantesDyC1.TEXTO_1_ERROR_DAO, dae.getMessage(), ConstantesDyC1.TEXTO_2_ERROR_DAO,
                            SQLOracleAprobarDoc.DYCSF_DOCUMENTOSPAGINADOR, ConstantesDyC1.TEXTO_3_ERROR_DAO,
                            String.format(
                                    "V_NUMEMPLEADOAPROB: %s, V_NUMCONTROL: %s , V_RFCCONTRIBUYENTE: %s, V_INFERIOR: %s, V_SUPERIOR: %s",
                                    numEmpleado,
                                    numControl,
                                    rfc,
                                    paginador.getFrom(),
                                    paginador.getTo()
                            )
                    )
            );

            throw new SIATException(dae);
        }
    }

    @Override
    public Long countBuscarBandejaDoc(String numEmpleado, String numControl, String rfc) throws SIATException {
        Long count = 0L;

        try {

            count = jdbcTemplateDYC.queryForObject(
                    SQLOracleAprobarDoc.DYCSF_DOCUMENTOSPAGINADORTOT,
                    new Object[]{
                        Integer.parseInt(numEmpleado),
                        String.format(FORMAT_SEARCH_STRING, numControl),
                        String.format(FORMAT_SEARCH_STRING, rfc)
                    },
                    Long.class
            );

        } catch (DataAccessException dae) {
            log.error(
                    String.format(
                            "%s %s %s %s %s %s %s", ConstantesDyC1.TEXTO_1_ERROR_DAO, dae.getMessage(), ConstantesDyC1.TEXTO_2_ERROR_DAO,
                            SQLOracleAprobarDoc.DYCSF_DOCUMENTOSPAGINADORTOT, ConstantesDyC1.TEXTO_3_ERROR_DAO,
                            numEmpleado,
                            String.format(
                                    "V_NUMEMPLEADOAPROB: %s, V_NUMCONTROL: %s , V_RFCCONTRIBUYENTE: %s",
                                    numEmpleado,
                                    numControl,
                                    rfc
                            )
                    )
            );
            throw new SIATException(dae);
        }

        return count;
    }

   @Override
    public List<BandejaDocumentosDTO> buscarBandejaSivadMorsa(int idPlantilla, String numControl, String rfc, Paginador paginador, String claveADM) throws SIATException {

        List<BandejaDocumentosDTO> registros = null;
        String consultaBandejaAbonoNoEfectuado = getConsultaBandejaAbonoNoEfectuado( idPlantilla );

        try {

            registros = jdbcTemplateDYC.query(
                    consultaBandejaAbonoNoEfectuado,
                    new Object[]{
                        String.format(FORMAT_SEARCH_STRING, numControl),
                        String.format(FORMAT_SEARCH_STRING, rfc),
                        idPlantilla,
                        Integer.parseInt(claveADM),
                        paginador.getFrom(),
                        paginador.getTo(),},
                    new AprobarDocumentoMapper()
            );

        } catch (DataAccessException dae) {
            log.error(
                    String.format(
                            "%s ###%s### %s [%s] [%s] [%s] [%s] : %s", ConstantesDyC1.TEXTO_1_ERROR_DAO,
                            dae.getMessage(), ConstantesDyC1.TEXTO_3_ERROR_DAO, numControl, rfc, paginador.getFrom(), paginador.getTo(), idPlantilla
                    )
            );
            throw new SIATException(dae);
        }

        return registros;
    }

    private String getConsultaBandejaAbonoNoEfectuado ( int idPlantilla ){

        String condicionPlantilla;
        StringBuilder consultaBandejaAbonoNoEfectuado = new StringBuilder();

        if ( plantillaEsDeAgaff( idPlantilla ) ){
            condicionPlantilla = SQLOracleAprobarDoc.DYCSF_DOCUMENTOS_SIVAD_MORSA_CONDICION_PLANTILLA_AGAFF;
        } else {
            condicionPlantilla = SQLOracleAprobarDoc.DYCSF_DOCUMENTOS_SIVAD_MORSA_CONDICION_PLANTILLA;
        }

        consultaBandejaAbonoNoEfectuado
            .append( SQLOracleAprobarDoc.DYCSF_DOCUMENTOS_SIVAD_MORSA )
            .append( condicionPlantilla )
            .append( SQLOracleAprobarDoc.DYCSF_DOCUMENTOS_SIVAD_MORSA_2 );

        return consultaBandejaAbonoNoEfectuado.toString();
    }

    private String getConsultaBandejaAbonoNoEfectuadoConteo ( int idPlantilla ){

        String condicionPlantilla;
        StringBuilder consultaBandejaAbonoNoEfectuado = new StringBuilder();

        if ( plantillaEsDeAgaff( idPlantilla ) ){
            condicionPlantilla = SQLOracleAprobarDoc.DYCSF_DOCUMENTOS_SIVAD_MORSA_COUNT_CONDICION_PLANTILLA_AGAFF;
        } else {
            condicionPlantilla = SQLOracleAprobarDoc.DYCSF_DOCUMENTOS_SIVAD_MORSA_COUNT_CONDICION_PLANTILLA;
        }

        consultaBandejaAbonoNoEfectuado
            .append( SQLOracleAprobarDoc.DYCSF_DOCUMENTOS_SIVAD_MORSA_COUNT )
            .append( condicionPlantilla )
            .append( SQLOracleAprobarDoc.DYCSF_DOCUMENTOS_SIVAD_MORSA_COUNT_2 );

        return consultaBandejaAbonoNoEfectuado.toString();
    }

    private boolean plantillaEsDeAgaff ( int idPlantilla ){
        return idPlantilla == ConstantesDyCNumerico.VALOR_22;
    }

    @Override
    public Long countBuscarBandejaSivadMorsa(int idPlantilla, String numControl, String rfc, String claveADM) throws SIATException {
        Long count = 0L;

        String consultaBandejaAbonoNoEfectuado = getConsultaBandejaAbonoNoEfectuadoConteo( idPlantilla );

        try {

            count = jdbcTemplateDYC.queryForObject(
                    consultaBandejaAbonoNoEfectuado,
                    new Object[]{
                        String.format(FORMAT_SEARCH_STRING, numControl),
                        String.format(FORMAT_SEARCH_STRING, rfc),
                        idPlantilla,
                        Integer.parseInt(claveADM)
                    },
                    Long.class
            );

        } catch (DataAccessException dae) {
            log.error(
                    String.format(
                            "%s %s %s [%s] [%s] : %s", ConstantesDyC1.TEXTO_1_ERROR_DAO,
                            dae.getMessage(), ConstantesDyC1.TEXTO_3_ERROR_DAO, numControl, rfc, idPlantilla
                    )
            );
            throw new SIATException(dae);
        }

        return count;
    }

}
