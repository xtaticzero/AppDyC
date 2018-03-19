
package mx.gob.sat.siat.dyc.dao.devolucionaut.impl;


import java.sql.Types;
import java.util.Date;
import java.util.List;
import mx.gob.sat.siat.dyc.dao.devolucionaut.DyccDevolucionAutDAO;
import mx.gob.sat.siat.dyc.domain.inconsistencia.DycaSolInconsistDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante1.ConstanteArchivoTemp;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.incrementer.OracleSequenceMaxValueIncrementer;
import org.springframework.stereotype.Repository;


@Repository(value = "dyccDevolucionAutDAO")
public class DyccDevolucionAutDAOImpl implements DyccDevolucionAutDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private static final Logger LOG = Logger.getLogger( DyccDevolucionAutDAOImpl.class );
    private static final String PIPE = " | ";

    public static final int ID_TIPO_SERVICIO_DEV_AUT = 2;

    @Override
    public String getNumeroConsecutivoDevolucionAutomatica ( String claveSir ){

        long numeroConsecutivo;

        OracleSequenceMaxValueIncrementer incrementer = new OracleSequenceMaxValueIncrementer();
        /**
         * Antes de generar la secuencia se revisa si existe una fecha de
         * reinicio de secuencia en la tabla DYCT_REINICIOSECPARAM y se reinicia
         * si es tiempo de reiniciar, despues se inactiva la fecha de reinicio
         *
         * DyctReinicioSecParamDTO fechaReinicioActivo =
         * reinicioSecParamDAOImpl.buscarFechaReinicioActivo(); if
         * (reinicioSecParamDAOImpl.seReinicianSecuencias(fechaReinicioActivo))
         * {
         *
         */
        incrementer.setDataSource(jdbcTemplateDYC.getDataSource());
        incrementer.setIncrementerName("DYCQ_NUMCTRLDEVISR" + claveSir);
        incrementer.setPaddingLength(1);
        incrementer.afterPropertiesSet();

        numeroConsecutivo = incrementer.nextLongValue();

        /**
         * *
         * reinicioSecParamDAOImpl.inactivarFechaReinicioSecuencia(fechaReinicioActivo);
         * } else {
         *
         * incrementer.setDataSource(jdbcTemplateDYC.getDataSource());
         * incrementer.setIncrementerName("DYCQ_NUMCTRLDEVISR" + claveSir);
         * incrementer.setPaddingLength(1); incrementer.afterPropertiesSet();
         *
         * numeroConsecutivo = incrementer.nextLongValue(); }
         */
        return String.format("%08d", numeroConsecutivo);
    }

    @Override
    public boolean actualizaEstadoBloqueoSaldoIcep ( DyctSaldoIcepDTO saldoIcep ){
        String query = SQLOracleDyC.ACTUALIZA_BLOQUEO.toString();

        try {

            jdbcTemplateDYC.update( 
                query,
                new Object[] { 
                    saldoIcep.getBloqueado(), 
                    saldoIcep.getIdSaldoIcep() 
                }
            );

            return Boolean.TRUE;

        } catch ( DataAccessException dae ){

            LOG.error( dae.getMessage() );
            registraError( query, ExtractorUtil.ejecutar( saldoIcep ) );

            ManejadorLogException.getTraceError( dae );

            return Boolean.FALSE;
        }

    }

    private void registraError ( String consulta, String descripcionObjeto ){
        StringBuilder error = new StringBuilder();

        error
            .append( ConstantesDyC1.TEXTO_1_ERROR_DAO ).append( consulta )
            .append( ConstantesDyC1.TEXTO_3_ERROR_DAO ).append( descripcionObjeto );

        LOG.error( error.toString() );
    }

    @Override
    public boolean insertarInconsistencia ( DycaSolInconsistDTO inconsistencia ){
        String query = SQLOracleDyC.INSERTAINCONSISTENCIAS.toString();
        try {

            jdbcTemplateDYC.update( query, new Object[] { inconsistencia.getDyccInconsistDTO().getIdInconsistencia(),
                                                          inconsistencia.getDycpSolicitudDTO().getNumControl(),
                                                          inconsistencia.getDescripcion() });

            return Boolean.TRUE;

        } catch ( DataAccessException dae ){
            LOG.error( dae.getMessage() );

            registraError( query, ExtractorUtil.ejecutar( inconsistencia ) );

            return Boolean.FALSE;
        }
    }

    @Override
    public String consultaReglaRNFDC35AGAFF ( String rfc ){
        try {

            return jdbcTemplateDYC.queryForObject(
                SQLOracleDyC.CONSULTA_DICTAMINADOR_AGAFF.toString(), 
                new Object[] { rfc },
                String.class
            );

        } catch ( EmptyResultDataAccessException erdae ){
            LOG.debug("No se encontro el RFC ->" + rfc);
        }

        return null;
    }

    @Override
    public boolean validarRegresoDictaminadorA ( Integer numeEmpleado, Date fechaRegistro ){

        List<Date> diasInhabiles;

        try {

            diasInhabiles = jdbcTemplateDYC.queryForList(
                SQLOracleDyC.DYCC_CALDICTAMIN_AUTOMATICA.toString(), 
                new Object[] { numeEmpleado, fechaRegistro },
                Date.class
            );

            return validaDiasInhabiles( diasInhabiles );

            
        } catch (DataAccessException dae) {

            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.DYCC_CALDICTAMIN_AUTOMATICA + ConstantesDyC1.TEXTO_3_ERROR_DAO + numeEmpleado);
        }

        return false;
    }

    private boolean validaDiasInhabiles ( List<Date> diasInhabiles ){
        List<Date> periodoDeDiezDias;
        Integer contDias = 0;

        if ( !diasInhabiles.isEmpty() ){

            periodoDeDiezDias = jdbcTemplateDYC.queryForList( SQLOracleDyC.CONSULTA_SEMANA.toString(), Date.class );

            for ( int itDiasInhabiles = ConstantesDyCNumerico.VALOR_0; itDiasInhabiles < diasInhabiles.size(); itDiasInhabiles++ ){

                if ( diaDentroPeriodoDiezDiasInhabilies( diasInhabiles, periodoDeDiezDias, itDiasInhabiles ) ){

                    contDias = contDias + 1;
                    if ( contDias > ConstantesDyCNumerico.VALOR_5 ||
                        itDiasInhabiles == ConstantesDyCNumerico.VALOR_9 ) {
                        break;
                    }

                } else {
                    break;
                }

            }

            if ( contDias <= ConstantesDyCNumerico.VALOR_5 ){
                return Boolean.TRUE;
            }

        }

        return Boolean.FALSE;
    }

    private boolean diaDentroPeriodoDiezDiasInhabilies ( List<Date> diasInhabiles, List<Date> periodoDeDiezDias, int itDiasInhabiles ){

        return diasInhabiles.get( itDiasInhabiles )
                    .equals( periodoDeDiezDias.get( itDiasInhabiles ) );
    }

    @Override
    public boolean valida4DiasInhabilesContinuosA ( Integer numeEmpleado, Date fechaRegistro ){
        List<Date> diasInhabiles;
        List<Date> diasSemana;
        boolean esReasignable = false;
        Integer diasCond = 0;

        try {

            diasInhabiles = jdbcTemplateDYC.queryForList( 
                SQLOracleDyC.DYCC_CALDICTAMIN_AUTOMATICA.toString(),
                new Object[] { numeEmpleado, fechaRegistro }, 
                Date.class
            );

            if (diasInhabiles.size() >= ConstantesDyCNumerico.VALOR_5) {

                diasSemana = this.jdbcTemplateDYC.queryForList(SQLOracleDyC.CONSULTA_SEMANA.toString(), Date.class);

                for (int j = ConstantesDyCNumerico.VALOR_0; j < diasInhabiles.size(); j++) {
                    if (diasInhabiles.get(j).equals(diasSemana.get(j))) {
                        diasCond = diasCond + ConstantesDyCNumerico.VALOR_1;
                        if (diasCond == ConstantesDyCNumerico.VALOR_5) {
                            esReasignable = Boolean.FALSE;
                            break;
                        }
                    } else {
                        esReasignable = Boolean.TRUE;
                        break;
                    }
                }

            } else {
                esReasignable = Boolean.TRUE;
            }

        } catch (DataAccessException dae) {

            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.DYCC_CALDICTAMIN + ConstantesDyC1.TEXTO_3_ERROR_DAO + numeEmpleado);
        }

        return esReasignable;
    }

    @Override
    public boolean validarDiaActualA ( Integer numeEmpleado, Date fechaRegistro ){
        try {

            List<Date> fechasInhabiles =
                jdbcTemplateDYC.queryForList(SQLOracleDyC.CONSULTA_DIA_INHABIL_TRAMITE.toString(),
                                             new Object[] { fechaRegistro, numeEmpleado }, Date.class);
            return !fechasInhabiles.isEmpty();

        } catch (DataAccessException dae) {

            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTA_DIA_INHABIL + ConstantesDyC1.TEXTO_3_ERROR_DAO + numeEmpleado);
        }

        return false;
    }

    @Override
    public boolean existeRegistroSolicitud ( String numeroControl ) throws SIATException {
        Integer numeroConsecutivo;
        try {

            numeroConsecutivo = jdbcTemplateDYC.queryForObject(
                SQLOracleDyC.CUENTA_SOLICITUD_X_NUMERO_CONTROL, 
                new Object[]{ numeroControl }, 
                Integer.class
            );

            return  numeroConsecutivo != 0;

        } catch ( EmptyResultDataAccessException exEmpty ){
            throw new SIATException( exEmpty );
        }
    }

@Override
    public boolean existeRegistroSolicitud ( String numeroControl, String rfc, Long icep ) throws SIATException {
        Integer numeroConsecutivo;
        try {

            String query = "SELECT count(1) fROM DYCP_SOLICITUD SOL INNER JOIN DYCP_SERVICIO SER ON SOL.NUMCONTROL = SER.NUMCONTROL WHERE SOL.NUMCONTROL = ? AND SER.RFCCONTRIBUYENTE = ?"
                    + " AND SOL.IDSALDOICEP = ?";
            numeroConsecutivo = jdbcTemplateDYC.queryForObject(
                    query,
                new Object[]{ numeroControl, rfc ,icep},
                Integer.class
            );

            return  numeroConsecutivo != 0;

        } catch ( EmptyResultDataAccessException exEmpty ){
            throw new SIATException( exEmpty );
        }
    }

    @Override
    public void insertaServicioSol(DycpSolicitudDTO input, Integer numEmpleado) throws SIATException {
        LOG.info(ConstanteArchivoTemp.INSERT + input.getFechaInicioTramite() + PIPE + input.getNumControl() + PIPE +
                 input.getDyctSaldoIcepDTO().getIdSaldoIcep());
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.CREATE_SERVICO_DEV_AUT.toString(),
                                   new Object[] { input.getNumControl(), 
                                                  ID_TIPO_SERVICIO_DEV_AUT, 
                                                  numEmpleado,
                                                  input.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDycaServOrigenDTO().getDyccOrigenSaldoDTO().getIdOrigenSaldo(),
                                                  input.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getIdTipoTramite(),
                                                  input.getDycpServicioDTO().getRfcContribuyente(),
                                                  input.getDycpServicioDTO().getClaveAdm(),
                                                  input.getDycpServicioDTO().getBoid() });

            String sentSQL =
                null != input.getFechaInicioTramite() ? SQLOracleDyC.INSERTARSOLICITUD.toString() : SQLOracleDyC.INSERTARSOLICITUD1.toString();
            jdbcTemplateDYC.update(sentSQL, getDatosSolicitud(input),
                                   getTypes(null != input.getFechaInicioTramite() ? 1 : 0));
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.INSERTARSOLICITUD.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO + input);
            throw new SIATException(dae);
        }
    }
    
    private Object[] getDatosSolicitud(DycpSolicitudDTO input) {
        /**
         * (NUMCONTROL,FECHAINICIOTRAMITE,IMPORTESOLICITADO,INFOADICIONAL,
         * DIOTNUMOPERACION,\n" +
         * "DIOTFECHAPRESENTA,RETENEDORRFC,ALTEXCONSTANCIA,ESCERTIFICADO,IDESTADOSOLICITUD,IDSUBORIGENSALDO,IDSUBTIPOTRAMITE,IDACTIVIDAD,RESOLAUTOMATICA,IDSALDOICEP,FECHAPRESENTACI
         * O N )
         */

        if (null != input.getFechaInicioTramite()) {
            return new Object[] { input.getNumControl(), input.getImporteSolicitado(), input.getInfoAdicional(),
                                  input.getDiotNumOperacion(), input.getDiotFechaPresenta(), input.getRetenedorRfc(),
                                  input.getAltexConstancia(), input.getEsCertificado(),
                                  input.getDyccEstadoSolDTO().getIdEstadoSolicitud(),
                                  input.getDyccActividadDTO().getDyccSubOrigSaldoDTO().getIdSuborigenSaldo(),
                                  input.getDyccSubtramiteDTO().getIdSubTipoTramite() != 0 ?
                                  input.getDyccSubtramiteDTO().getIdSubTipoTramite() : null,
                                  input.getDyccActividadDTO().getIdActividad(),
                                  input.getDyctSaldoIcepDTO().getIdSaldoIcep(), input.getCadenaOriginal(),
                                  input.getSelloDigital(), input.getFechaInicioTramite() };
        } else {
            return new Object[] { input.getNumControl(), input.getImporteSolicitado(), input.getInfoAdicional(),
                                  input.getDiotNumOperacion(), input.getDiotFechaPresenta(), input.getRetenedorRfc(),
                                  input.getAltexConstancia(), input.getEsCertificado(),
                                  input.getDyccEstadoSolDTO().getIdEstadoSolicitud(),
                                  input.getDyccActividadDTO().getDyccSubOrigSaldoDTO().getIdSuborigenSaldo(),
                                  input.getDyccSubtramiteDTO().getIdSubTipoTramite() != 0 ?
                                  input.getDyccSubtramiteDTO().getIdSubTipoTramite() : null,
                                  input.getDyccActividadDTO().getIdActividad(),
                                  input.getDyctSaldoIcepDTO().getIdSaldoIcep(), input.getCadenaOriginal(),
                                  input.getSelloDigital() };

        }
    }
    
    private int[] getTypes(int flag) {
        int[] types = null;
        if (flag != 0) {
            types =
                new int[] { Types.VARCHAR, Types.DECIMAL, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.VARCHAR, Types.VARCHAR,
                Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.VARCHAR,
                Types.VARCHAR, Types.TIMESTAMP };
        } else {

            types =
                new int[] { Types.VARCHAR, Types.DECIMAL, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.VARCHAR, Types.VARCHAR,
                Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.VARCHAR,
                Types.VARCHAR };
        }
        return types;
    }

}
