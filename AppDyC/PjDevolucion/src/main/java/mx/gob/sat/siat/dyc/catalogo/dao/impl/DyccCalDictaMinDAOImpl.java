/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.catalogo.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.cobranza.diahabil.api.DiasHabilFacade;
import mx.gob.sat.siat.cobranza.diahabil.domain.DiaGeneral;
import mx.gob.sat.siat.dyc.admcat.dao.mantenercalendariodictaminador.impl.mapper.MantenerCalendarioDiasInhabilesMapper;
import mx.gob.sat.siat.dyc.admcat.dao.mantenercalendariodictaminador.impl.mapper.MantenerCalendarioDictaminadorMapper;
import mx.gob.sat.siat.dyc.admcat.dao.mantenercalendariodictaminador.impl.mapper.MantenerCalendarioIndividualMapper;
import mx.gob.sat.siat.dyc.admcat.dto.matenercalendariodictaminador.MantenerCalendarioIndividualDTO;
import mx.gob.sat.siat.dyc.catalogo.dao.DyccCalDictaMinDAO;
import mx.gob.sat.siat.dyc.domain.DyccCalDictaminDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 *
 * DAO creado para el DTO DyccDictaminadorDTO
 * @author Alfredo Ramirez
 * @since 16/08/2013
 *
 */
@Repository(value = "dyccCalDictaMinDAO")
public class DyccCalDictaMinDAOImpl implements DyccCalDictaMinDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    @Autowired
    private DiasHabilFacade diaHabilFacade;

    private Logger log = Logger.getLogger(DyccCalDictaMinDAOImpl.class);

    public DyccCalDictaMinDAOImpl() {
        super();
    }

    @Override
    public List<MantenerCalendarioIndividualDTO> consultarDictaminador(long numEmpDictaminador) {
        List<MantenerCalendarioIndividualDTO> dyccCalDictaMin = new ArrayList<MantenerCalendarioIndividualDTO>();
        try {
            dyccCalDictaMin =
                    this.jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARCATALOGOS_OBTIENEDICTAMINADOR.toString(),
                                               new Object[] { numEmpDictaminador },
                                               new MantenerCalendarioIndividualMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_OBTIENEDICTAMINADOR + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      numEmpDictaminador);
        }
        return dyccCalDictaMin;
    }

    @Override
    public List<DyccCalDictaminDTO> existenInhabiles(DyccCalDictaminDTO dyccCalDictaminDTO) {
        List<DyccCalDictaminDTO> dyccCalDictaMin = new ArrayList<DyccCalDictaminDTO>();
        try {
            dyccCalDictaMin =
                    this.jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARCATALOGOS_EXISTENINHABILES, new Object[] { dyccCalDictaminDTO.getDyccDictaminadorDTO().getNumEmpleado(),
                                                                                                                dyccCalDictaminDTO.getFechaInicial() },
                                               new MantenerCalendarioDiasInhabilesMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_EXISTENINHABILES + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(dyccCalDictaminDTO));
        }
        return dyccCalDictaMin;
    }

    @Override
    public void adicionarDiaInhabil(DyccCalDictaminDTO dyccCalDictaminDTO) {
        try {
            String existe = "0";
            String existeInhabilI = "0";
            String existeInhabilG = "0";
            existe =
                    this.getJdbcTemplateDYC().queryForObject(SQLOracleDyC.FECHA_EXISTE_INACTIVA_PARA_DICTAMINADOR, new Object[] { dyccCalDictaminDTO.getDyccDictaminadorDTO().getNumEmpleado(),
                                                                                                                                  dyccCalDictaminDTO.getFechaInicial() },
                                                             String.class);
            existeInhabilI =
                    this.getJdbcTemplateDYC().queryForObject(SQLOracleDyC.EXISTE_FECHA_INHABIL_IND, new Object[] { dyccCalDictaminDTO.getDyccDictaminadorDTO().getNumEmpleado(),
                                                                                                                   dyccCalDictaminDTO.getFechaInicial() },
                                                             String.class);
            existeInhabilG =
                    this.getJdbcTemplateDYC().queryForObject(SQLOracleDyC.EXISTE_FECHA_INHABIL_GEN, new Object[] { dyccCalDictaminDTO.getDyccDictaminadorDTO().getNumEmpleado(),
                                                                                                                   dyccCalDictaminDTO.getFechaInicial() },
                                                             String.class);
            if (existe.equals("0")) {
                this.jdbcTemplateDYC.update(SQLOracleDyC.CONSULTARCATALOGOS_INSERTARMANTENERCALENDARIOINDIVIDUAL.toString(),
                                            new Object[] { dyccCalDictaminDTO.getDyccDictaminadorDTO().getNumEmpleado(),
                                                           dyccCalDictaminDTO.getFechaInicial(),
                                                           dyccCalDictaminDTO.getTipoCalendario(),
                                                           dyccCalDictaminDTO.getDyccMotivoInhabilDTO().getIdMotivoInhabil(),
                                                           dyccCalDictaminDTO.getDescripcionMotivo(),
                                                           dyccCalDictaminDTO.getEsInhabil() });
            } else {
                if (existeInhabilI.equals("0") && dyccCalDictaminDTO.getTipoCalendario().equals("i")) {
                    this.jdbcTemplateDYC.update(SQLOracleDyC.ACTUALIZA_FECHA_EXISTE_INACTIVA_PARA_DICTAMINADOR.toString(),
                                                new Object[] { dyccCalDictaminDTO.getFechaInicial(),
                                                               dyccCalDictaminDTO.getTipoCalendario(),
                                                               dyccCalDictaminDTO.getDyccMotivoInhabilDTO().getIdMotivoInhabil(),
                                                               dyccCalDictaminDTO.getDescripcionMotivo(),
                                                               dyccCalDictaminDTO.getEsInhabil(),
                                                               dyccCalDictaminDTO.getDyccDictaminadorDTO().getNumEmpleado(),
                                                               dyccCalDictaminDTO.getFechaInicial() });
                } else if (existeInhabilG.equals("0") && dyccCalDictaminDTO.getTipoCalendario().equals("g")) {
                    this.jdbcTemplateDYC.update(SQLOracleDyC.ACTUALIZA_FECHA_EXISTE_INACTIVA_PARA_DICTAMINADOR.toString(),
                                                new Object[] { dyccCalDictaminDTO.getFechaInicial(),
                                                               dyccCalDictaminDTO.getTipoCalendario(),
                                                               dyccCalDictaminDTO.getDyccMotivoInhabilDTO().getIdMotivoInhabil(),
                                                               dyccCalDictaminDTO.getDescripcionMotivo(),
                                                               dyccCalDictaminDTO.getEsInhabil(),
                                                               dyccCalDictaminDTO.getDyccDictaminadorDTO().getNumEmpleado(),
                                                               dyccCalDictaminDTO.getFechaInicial() });
                }
            }
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_INSERTARMANTENERCALENDARIOINDIVIDUAL +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(dyccCalDictaminDTO));
        }
    }

    @Override
    public void modificarDiaIndividual(DyccCalDictaminDTO dyccCalDictaminDTO) {
        try {
            this.jdbcTemplateDYC.update(SQLOracleDyC.CONSULTARCATALOGOS_ACTUAKIZARMANTENERCALENDARIOINDIVIDUAL.toString(),
                                        new Object[] { dyccCalDictaminDTO.getDyccMotivoInhabilDTO().getIdMotivoInhabil(),
                                                       dyccCalDictaminDTO.getDescripcionMotivo(),
                                                       dyccCalDictaminDTO.getDyccDictaminadorDTO().getNumEmpleado(),
                                                       dyccCalDictaminDTO.getFechaInicial() });
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_ACTUAKIZARMANTENERCALENDARIOINDIVIDUAL +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(dyccCalDictaminDTO));
        }
    }

    @Override
    public void eliminarDiaIndividual(DyccCalDictaminDTO dyccCalDictaminDTO) {
        try {
            this.jdbcTemplateDYC.update(SQLOracleDyC.CONSULTARCATALOGOS_ELIMINARMANTENERCALENDARIOINDIVIDUAL,
                                        new Object[] { dyccCalDictaminDTO.getDyccDictaminadorDTO().getNumEmpleado(),
                                                       dyccCalDictaminDTO.getFechaInicial() });
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_ELIMINARMANTENERCALENDARIOINDIVIDUAL +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(dyccCalDictaminDTO));
        }
    }

    @Override
    public List<MantenerCalendarioIndividualDTO> consultarCalendarioGeneral(int idunidadadmva) {
        List<MantenerCalendarioIndividualDTO> dyccDictaminador = new ArrayList<MantenerCalendarioIndividualDTO>();
        try {
            dyccDictaminador =
                    this.jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARCATALOGOS_OBTIENECALENDARIOGENERAL.toString(),
                                               new Object[] { idunidadadmva },
                                               new MantenerCalendarioDictaminadorMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_OBTIENECALENDARIOGENERAL + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      idunidadadmva);
        }
        return dyccDictaminador;
    }

    @Override
    public void eliminarDiaGeneral(MantenerCalendarioIndividualDTO mantenerCalendarioIndividualDto) {
        try {
            this.jdbcTemplateDYC.update(SQLOracleDyC.CONSULTARCATALOGOS_ELIMINARCALENDARIOGENERAL.toString(),
                                        new Object[] { mantenerCalendarioIndividualDto.getFechaInicial(),
                                                       mantenerCalendarioIndividualDto.getMotivoDescripcion(),
                                                       mantenerCalendarioIndividualDto.getDescripcionMotivo(),
                                                       mantenerCalendarioIndividualDto.getIdUnidad() });
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_ELIMINARCALENDARIOGENERAL + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(mantenerCalendarioIndividualDto));
        }
    }

    public List<MantenerCalendarioIndividualDTO> consultarDiasInhabilesGeneralDictaminador(int idUnidad) {
        List<MantenerCalendarioIndividualDTO> dyccCalDictaMin = new ArrayList<MantenerCalendarioIndividualDTO>();
        try {
            dyccCalDictaMin =
                    this.jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARCATALOGOS_OBTIENEDICTAMINADOR.toString(),
                                               new Object[] { idUnidad }, new MantenerCalendarioIndividualMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_OBTIENEDICTAMINADOR + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      idUnidad);
        }
        return dyccCalDictaMin;
    }

    /**
     * TODO ISCC
     * @return
     * @throws Exception
     */
    public boolean verificarAsistenciaDictaminador(long numeEmpleado, Date fechaRegistro) {
        List<MantenerCalendarioIndividualDTO> dyccCalDictaMin = new ArrayList<MantenerCalendarioIndividualDTO>();
        boolean esInasistente = Boolean.TRUE;
        try {
            dyccCalDictaMin =
                    this.jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARASISTENCIADICTAMINADOR.toString(), new Object[] { numeEmpleado,
                                                                                                                       fechaRegistro },
                                               new MantenerCalendarioIndividualMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARASISTENCIADICTAMINADOR.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      numeEmpleado + " " + fechaRegistro);
        }
        if (dyccCalDictaMin.isEmpty()) {
            esInasistente = Boolean.FALSE;
        }
        return esInasistente;
    }

    /**
     * TODO
     * @param numeEmpleado
     * @param fechaRegistro
     * @return
     * @throws Exception
     */
    public boolean consultarDisponibilidadDictaminador(long numeEmpleado, Date fechaRegistro) {
        List<MantenerCalendarioIndividualDTO> dyccCalDictaMin = new ArrayList<MantenerCalendarioIndividualDTO>();
        boolean esDisponible = Boolean.TRUE;
        try {
            dyccCalDictaMin =
                    this.jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARDISPONIBILIDADDICTAMINADOR.toString(), new Object[] { numeEmpleado,
                                                                                                                           fechaRegistro,
                                                                                                                           fechaRegistro,
                                                                                                                           fechaRegistro,
                                                                                                                           fechaRegistro },
                                               new MantenerCalendarioIndividualMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARDISPONIBILIDADDICTAMINADOR.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      numeEmpleado + " " + fechaRegistro);
        }

        if (dyccCalDictaMin.size() > 0) {
            esDisponible = Boolean.FALSE;
        }
        return esDisponible;
    }

    /**
     * TODO
     * @param numeEmpleado
     * @param fechaRegistro
     * @return
     * @throws Exception
     */
    public boolean consultarDisponibilidadAlRegresoDictaminador(long numeEmpleado, Date fechaRegistro) {
        List<MantenerCalendarioIndividualDTO> dyccCalDictaMin = new ArrayList<MantenerCalendarioIndividualDTO>();
        boolean esDisponible = Boolean.TRUE;
        try {
            dyccCalDictaMin =
                    this.jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARDISPONIBILIDADAALREGRESODICTAMINADOR.toString(),
                                               new Object[] { numeEmpleado, fechaRegistro },
                                               new MantenerCalendarioIndividualMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARDISPONIBILIDADAALREGRESODICTAMINADOR.toString() +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + numeEmpleado + " " + fechaRegistro);
        }
        if (dyccCalDictaMin.isEmpty()) {
            esDisponible = Boolean.FALSE;
        }
        return esDisponible;
    }

    @Override
    public boolean validarRegresoDictamin(long numeEmpleado) {

        List<MantenerCalendarioIndividualDTO> dyccCalDictaMin = new ArrayList<MantenerCalendarioIndividualDTO>();
        boolean esAsignable = Boolean.FALSE;
        try {
            dyccCalDictaMin =
                    this.jdbcTemplateDYC.query(SQLOracleDyC.VALIDAR5DIASOMENOSREINICIOACTIVIDADES.toString(), new Object[] { numeEmpleado },
                                               new MantenerCalendarioIndividualMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.VALIDAR5DIASOMENOSREINICIOACTIVIDADES.toString() +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + numeEmpleado);
        }
        if (dyccCalDictaMin.size() > 0) {
            esAsignable = Boolean.TRUE;
        }
        return esAsignable;
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }

    public void setLog(Logger log) {
        this.log = log;
    }

    public Logger getLog() {
        return log;
    }

    @Override
    public boolean validarRegresoDelDictaminador(long numeEmpleado, Date fechaRegistro) {
        List<Date> diasInhabiles;
        List<Date> periodoDeDiezDias;
        boolean esReasignable = false;
        Integer diasCond = 0;

        try {

            diasInhabiles =
                    this.jdbcTemplateDYC.queryForList(SQLOracleDyC.DYCC_CALDICTAMIN_AUTOMATICA.toString(), new Object[] { numeEmpleado },
                                                      Date.class);

            if (diasInhabiles.size() != 0) {
                periodoDeDiezDias =
                        this.jdbcTemplateDYC.queryForList(SQLOracleDyC.CONSULTA_SEMANA.toString(), Date.class);

                for (int j = 0; j < diasInhabiles.size(); j++) {
                    if (diasInhabiles.get(j).equals(periodoDeDiezDias.get(j))) {
                        diasCond = diasCond + 1;
                    } else {
                        break;
                    }
                }
                if (diasCond <= ConstantesDyCNumerico.VALOR_5) {
                    esReasignable = Boolean.TRUE;
                }
            }
        } catch (DataAccessException dae) {

            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.DYCC_CALDICTAMIN_AUTOMATICA + ConstantesDyC1.TEXTO_3_ERROR_DAO + numeEmpleado);
        }
        return esReasignable;
    }

    /**
     *
     * @param numeEmpleado
     * @return
     * Validacion actualizada por cambio en tabla dycc_caldictamin
     */
    @Override
    public boolean validarDiaActual(Integer numeEmpleado) {

        List<Date> fechaInhabil = new ArrayList<Date>();
        boolean esInhabilEnElDictamin = Boolean.FALSE;

        try {

            fechaInhabil =
                    this.jdbcTemplateDYC.queryForList(SQLOracleDyC.CONSULTA_DIA_INHABIL.toString(), new Object[] { numeEmpleado },
                                                      Date.class);
        } catch (DataAccessException dae) {

            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTA_DIA_INHABIL + ConstantesDyC1.TEXTO_3_ERROR_DAO + numeEmpleado);
        }

        if (fechaInhabil.size() > 0) {
            esInhabilEnElDictamin = Boolean.TRUE;
        }

        return esInhabilEnElDictamin;

    }

    @Override
    public boolean validarRegresoDictaminador(Integer numeEmpleado) {
        List<Date> diasInhabiles;
        List<Date> periodoDeDiezDias;
        boolean esReasignable = Boolean.FALSE;
        Integer diasCond = 0;

        try {

            diasInhabiles =
                    this.jdbcTemplateDYC.queryForList(SQLOracleDyC.DYCC_CALDICTAMIN.toString(), new Object[] { numeEmpleado },
                                                      Date.class);

            if (diasInhabiles.size() != ConstantesDyCNumerico.VALOR_0) {
                periodoDeDiezDias =
                        this.jdbcTemplateDYC.queryForList(SQLOracleDyC.CONSULTA_SEMANA.toString(), Date.class);

                for (int j = ConstantesDyCNumerico.VALOR_0; j < diasInhabiles.size(); j++) {
                    if (diasInhabiles.get(j).equals(periodoDeDiezDias.get(j))) {
                        diasCond = diasCond + ConstantesDyCNumerico.VALOR_1;
                        if (diasCond > ConstantesDyCNumerico.VALOR_5 || j == ConstantesDyCNumerico.VALOR_9) {
                            break;
                        }
                    } else {
                        break;
                    }
                }
                if (diasCond <= ConstantesDyCNumerico.VALOR_5) {
                    esReasignable = Boolean.TRUE;
                }
            }
        } catch (DataAccessException dae) {

            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.DYCC_CALDICTAMIN + ConstantesDyC1.TEXTO_3_ERROR_DAO + numeEmpleado);
        }
        return esReasignable;
    }

    @Override
    public boolean valida4DiasInhabilesContinuos(Integer numeEmpleado) {
        List<Date> diasInhabiles;
        List<DiaGeneral> diasHabiles;
        boolean esReasignable = Boolean.FALSE;
        Integer diasCond = 0;

        try {

            diasInhabiles =
                    this.jdbcTemplateDYC.queryForList(SQLOracleDyC.DYCC_CALDICTAMIN.toString(), new Object[] { numeEmpleado },
                                                      Date.class);

            if (diasInhabiles.size() > ConstantesDyCNumerico.VALOR_0) {
                List<DiaGeneral> diasHabilesEmpleado =
                    this.diaHabilFacade.obtenerCalendarioDescripcionComputoPlazo(diasInhabiles.get(ConstantesDyCNumerico.VALOR_0),
                                                                                 diasInhabiles.size(), Boolean.TRUE);
                for (int i = ConstantesDyCNumerico.VALOR_0; i < diasInhabiles.size(); i++) {
                    if (diasInhabiles.get(i).getTime() == diasHabilesEmpleado.get(i).getFecha().getTime()) {
                        diasCond++;
                    }
                }


                if (diasCond >= ConstantesDyCNumerico.VALOR_5) {

                    Calendar calendario = Calendar.getInstance();
                    calendario.setTime(new Date());
                    calendario.add(Calendar.DAY_OF_YEAR, ConstantesDyCNumerico.VALOR_1);
                    diasHabiles =
                            this.diaHabilFacade.obtenerCalendarioDescripcionComputoPlazo(calendario.getTime(), ConstantesDyCNumerico.VALOR_4,
                                                                                         Boolean.TRUE);

                    int ultimo = diasHabiles.size() - ConstantesDyCNumerico.VALOR_1;
                    log.info(diasInhabiles.get(ConstantesDyCNumerico.VALOR_0) + " " +
                             diasHabiles.get(ConstantesDyCNumerico.VALOR_0) + " " + diasHabiles.get(ultimo));
                    if (diasInhabiles.get(ConstantesDyCNumerico.VALOR_0).getTime() >=
                        diasHabiles.get(ConstantesDyCNumerico.VALOR_0).getFecha().getTime() &&
                        diasInhabiles.get(ConstantesDyCNumerico.VALOR_0).getTime() <=
                        diasHabiles.get(ultimo).getFecha().getTime()) {
                        esReasignable = Boolean.FALSE;
                    } else {
                        esReasignable = Boolean.TRUE;
                    }

                } else {
                    esReasignable = Boolean.TRUE;
                }
            } else {
                esReasignable = Boolean.TRUE;
            }
        } catch (DataAccessException dae) {

            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.DYCC_CALDICTAMIN + ConstantesDyC1.TEXTO_3_ERROR_DAO + numeEmpleado);
        }
        return esReasignable;
    }

    @Override
    public boolean validarDiaActualA(Integer numeEmpleado, Date fechaRegistro) {
        try {
            List<Date> fechasInhabiles =
                jdbcTemplateDYC.queryForList(SQLOracleDyC.CONSULTA_DIA_INHABIL_TRAMITE.toString(),
                                             new Object[] { fechaRegistro, numeEmpleado }, Date.class);
            return !fechasInhabiles.isEmpty();
        } catch (DataAccessException dae) {

            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTA_DIA_INHABIL + ConstantesDyC1.TEXTO_3_ERROR_DAO + numeEmpleado);
        }

        return false;
    }

    @Override
    public boolean validarRegresoDictaminadorA(Integer numeEmpleado, Date fechaRegistro) {
        List<Date> diasInhabiles;
        List<Date> periodoDeDiezDias;
        boolean esReasignable = false;
        Integer contDias = 0;

        try {

            diasInhabiles =
                    this.jdbcTemplateDYC.queryForList(SQLOracleDyC.DYCC_CALDICTAMIN_AUTOMATICA.toString(), new Object[] { numeEmpleado,
                                                                                                                          fechaRegistro },
                                                      Date.class);

            if (diasInhabiles.size() != ConstantesDyCNumerico.VALOR_0) {
                periodoDeDiezDias =
                        this.jdbcTemplateDYC.queryForList(SQLOracleDyC.CONSULTA_SEMANA.toString(), Date.class);

                for (int itDiasInhabiles = ConstantesDyCNumerico.VALOR_0; itDiasInhabiles < diasInhabiles.size();
                     itDiasInhabiles++) {
                    if (diasInhabiles.get(itDiasInhabiles).equals(periodoDeDiezDias.get(itDiasInhabiles))) {
                        contDias = contDias + 1;
                        if (contDias > ConstantesDyCNumerico.VALOR_5 ||
                            itDiasInhabiles == ConstantesDyCNumerico.VALOR_9) {
                            break;
                        }
                    } else {
                        break;
                    }
                }
                if (contDias <= ConstantesDyCNumerico.VALOR_5) {
                    esReasignable = Boolean.TRUE;
                }
            }
        } catch (DataAccessException dae) {

            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.DYCC_CALDICTAMIN_AUTOMATICA + ConstantesDyC1.TEXTO_3_ERROR_DAO + numeEmpleado);
        }
        return esReasignable;
    }

    @Override
    public boolean valida4DiasInhabilesContinuosA(Integer numeEmpleado, Date fechaRegistro) {
        List<Date> diasInhabiles;
        List<Date> diasSemana;
        boolean esReasignable = Boolean.FALSE;
        Integer diasCond = 0;

        try {

            diasInhabiles =
                    this.jdbcTemplateDYC.queryForList(SQLOracleDyC.DYCC_CALDICTAMIN_AUTOMATICA.toString(), new Object[] { numeEmpleado,
                                                                                                                          fechaRegistro },
                                                      Date.class);
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

            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.DYCC_CALDICTAMIN + ConstantesDyC1.TEXTO_3_ERROR_DAO + numeEmpleado);
        }
        return esReasignable;
    }
}
