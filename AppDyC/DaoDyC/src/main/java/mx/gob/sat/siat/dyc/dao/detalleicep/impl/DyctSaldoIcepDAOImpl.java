package mx.gob.sat.siat.dyc.dao.detalleicep.impl;

import java.math.BigDecimal;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.siat.dyc.dao.compensacion.impl.mapper.DycpDatosSolicitudMapper;

import mx.gob.sat.siat.dyc.dao.compensacion.impl.mapper.DyctSaldoIcepMapper;
import mx.gob.sat.siat.dyc.dao.concepto.impl.mapper.ConceptoMapper;
import mx.gob.sat.siat.dyc.dao.concepto.impl.mapper.ImpuestoMapper;
import mx.gob.sat.siat.dyc.dao.detalleicep.DyctSaldoIcepDAO;
import mx.gob.sat.siat.dyc.dao.detalleicep.impl.mapper.DyctDeclaraIcepAuxMapper;
import mx.gob.sat.siat.dyc.dao.detalleicep.impl.mapper.DyctSaldoIcepDisponibleMapper;
import mx.gob.sat.siat.dyc.dao.detalleicep.impl.mapper.EjercicioMapper;
import mx.gob.sat.siat.dyc.dao.documento.impl.DycaDocumentoDAOImpl;
import mx.gob.sat.siat.dyc.dao.periodo.impl.mapper.PeriodoMapper;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpDatosSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.excepcion.DycDaoExcepcion;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;
import mx.gob.sat.siat.dyc.vo.DyctSaldoIcepAuxVO;
import mx.gob.sat.siat.dyc.vo.IcepVO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DyctSaldoIcepDAOImpl implements DyctSaldoIcepDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(DycaDocumentoDAOImpl.class.getName());

    public static final String DYCT_SALDOICEP_QUERY
            = " SELECT * FROM DYCT_SALDOICEP WHERE RFC = ? AND IDCONCEPTO = ?  AND IDEJERCICIO = ? AND IDPERIODO = ?  AND IDORIGENSALDO = ?";

    public static final String DYCT_SALDOICEP_QUERY_ISR
            = " SELECT IDSALDOICEP FROM DYCT_SALDOICEP WHERE RFC = ? AND IDCONCEPTO = ?  AND IDEJERCICIO = ? AND IDPERIODO = ?  AND IDORIGENSALDO = ?";

    public static final String DYCT_SALDOICEP_QUERY_FOLIO
            = " SELECT  FOLIOAVISO as folioICEPS FROM DYCP_COMPENSACION cn  INNER JOIN dyct_saldoicep sicep on cn.IDSALDOICEPDestino = sicep.IDSALDOICEP WHERE sicep.IDCONCEPTO = ? AND sicep.IDEJERCICIO = ? AND sicep.IDPERIODO = ? AND sicep.RFC =? AND cn.IDESTADOCOMP = 3 AND sicep.IDORIGENSALDO is null AND ROWNUM = 1";

    @Override
    public DyctSaldoIcepDTO encontrar(Integer idSaldoIcep) {
        StringBuilder query
                = new StringBuilder().append(" SELECT I.DESCRIPCION AS DESCRIPCION_IMPUESTO, P.DESCRIPCION AS DESCRIPCION_PERIODO, S.*, C.*, I.*, E.*, P.*  ").append(" FROM DYCT_SALDOICEP S, DYCC_CONCEPTO C, DYCC_IMPUESTO I, DYCC_EJERCICIO E, DYCC_PERIODO P ").append(" WHERE C.IDCONCEPTO = S.IDCONCEPTO AND I.IDIMPUESTO = C.IDIMPUESTO AND E.IDEJERCICIO = S.IDEJERCICIO ").append(" AND P.IDPERIODO = S.IDPERIODO AND IDSALDOICEP = ? ORDER BY IDSALDOICEP DESC");
        try {
            ImpuestoMapper mapperImpuesto = new ImpuestoMapper();
            ConceptoMapper mapperConcepto = new ConceptoMapper();
            mapperConcepto.setMapperImpuesto(mapperImpuesto);
            EjercicioMapper mapperEjercicio = new EjercicioMapper();
            PeriodoMapper mapperPeriodo = new PeriodoMapper();
            DyctSaldoIcepMapper mapper = new DyctSaldoIcepMapper();
            mapper.setMapperConcepto(mapperConcepto);
            mapper.setMapperEjercicio(mapperEjercicio);
            mapper.setMapperPeriodo(mapperPeriodo);
            return this.jdbcTemplateDYC.queryForObject(query.toString(), new Object[]{idSaldoIcep}, mapper);
        } catch (org.springframework.dao.EmptyResultDataAccessException exEmpty) {
            log.error("NO se encontro ICEP con idSaldoIcep ->" + idSaldoIcep);
            return null;
        }
    }

    /**
     * Metodo que obtiene el registro de [DYCT_SALDOICEP] por ICEP y RFC.
     *
     * @param icepDTO
     * @param rfc
     * @return
     */
    @Override
    public DyctSaldoIcepDTO getSaldoIcepByIcep(IcepVO icepDTO, String rfc) throws SIATException {
        try {
            List<DyctSaldoIcepDTO> dyctSaldoIcepD
                    = this.jdbcTemplateDYC.query(SQLOracleDyC.OBTENER_SALDOICEP_BY_ICEP.toString(),
                            new Object[]{rfc, icepDTO.getIdEjercicio(), icepDTO.getIdPeriodo(),
                                icepDTO.getIdConcepto(), icepDTO.getIdImpuesto(),},
                            new DyctSaldoIcepMapper());

            if (dyctSaldoIcepD != null && dyctSaldoIcepD.size() > 0) {
                return dyctSaldoIcepD.get(0);
            }
        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.OBTENER_SALDOICEP_BY_ICEP.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO
                    + " icepDTO " + icepDTO.toString() + " rfc " + rfc);
            throw new SIATException(dae);
        }

        return null;
    }

    @Override
    public void eliminarIcep(Long idSaldoIcep) throws SIATException {
        try {
            this.jdbcTemplateDYC.update(SQLOracleDyC.ELIMINAR_ICEP.toString(), new Object[]{idSaldoIcep});
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.ELIMINAR_ICEP.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO + idSaldoIcep);
        }
    }

    @Override
    public List<DyctSaldoIcepAuxVO> selectIcep(Long idSaldoIcep) throws SIATException {
        List<DyctSaldoIcepAuxVO> dyctSaldoIcepAuxList = new ArrayList<DyctSaldoIcepAuxVO>();
        try {
            this.jdbcTemplateDYC.query(SQLOracleDyC.OBTENER_DETALLE_ICEP.toString(), new Object[]{idSaldoIcep},
                    new DyctDeclaraIcepAuxMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.OBTENER_DETALLE_ICEP.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO + idSaldoIcep);
        }

        return dyctSaldoIcepAuxList;
    }

    @Override
    public boolean existeXIcepRfc(IcepVO icepDTO) throws SIATException {

        Integer countReg = 0;
        boolean existe = false;

        try {

            countReg
                    = jdbcTemplateDYC.queryForObject(SQLOracleDyC.EXISTE_SALDOICEP_BY_ICEP.toString(), new Object[]{icepDTO.getRfc(),
                icepDTO.getIdEjercicio(),
                icepDTO.getIdPeriodo(),
                icepDTO.getIdConcepto(),
                icepDTO.getIdImpuesto(),
                icepDTO.getIdTipoSaldo()},
                            Integer.class);

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.EXISTE_SALDOICEP_BY_ICEP.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO
                    + ExtractorUtil.ejecutar(icepDTO));
            throw new SIATException(dae);
        }

        if (countReg > 0) {
            existe = Boolean.TRUE;
        }

        return existe;
    }

    public void actualizarRemanente(Integer idSaldoIcep, BigDecimal remanente) throws SIATException {

        try {

            this.jdbcTemplateDYC.update(SQLOracleDyC.ACTUALIZAR_SALDO_ICEP_REMANENTE.toString(),
                    new Object[]{remanente, idSaldoIcep});
        } catch (DataAccessException dae) {
            log.error(dae.getMessage());
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.ACTUALIZAR_SALDO_ICEP_REMANENTE.toString()
                    + ConstantesDyC1.TEXTO_3_ERROR_DAO + " idSaldoIcep " + idSaldoIcep + " remanente " + remanente);
        }

    }

    @Override
    public boolean existeXnumControl(String numControl) throws SIATException {

        Integer countReg = 0;
        boolean existe = false;

        try {

            countReg
                    = jdbcTemplateDYC.queryForObject(SQLOracleDyC.DYCT_SALDOICEP_EXISTE_X_NUMCONTROL.toString(), new Object[]{numControl},
                            Integer.class);

        } catch (DataAccessException dae) {
            log.error(dae.getMessage());
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.DYCT_SALDOICEP_EXISTE_X_NUMCONTROL.toString()
                    + ConstantesDyC1.TEXTO_3_ERROR_DAO + " numControl " + numControl);
            throw new SIATException(dae);
        }

        if (countReg > 0) {
            existe = Boolean.TRUE;
        }

        return existe;
    }

    /**
     * Metodo que obtiene el registro de [DYCT_SALDOICEP] por ICEP y RFC.
     *
     * @param numControl
     * @return
     */
    @Override
    public List<DyctSaldoIcepDTO> obtenerXnumCOntrol(String numControl) throws SIATException {

        List<DyctSaldoIcepDTO> dyctSaldo = null;
        try {
            dyctSaldo
                    = this.jdbcTemplateDYC.query(SQLOracleDyC.DYCT_SALDOICEP_OBTENER_X_NUMCONTROL.toString(), new Object[]{numControl},
                            new DyctSaldoIcepMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.DYCT_SALDOICEP_OBTENER_X_NUMCONTROL.toString()
                    + ConstantesDyC1.TEXTO_3_ERROR_DAO + " numControl " + numControl);
            throw new SIATException(dae);
        }
        return dyctSaldo;
    }

    @Override
    public boolean existeXId(long idSaldoICEP) throws SIATException {

        Integer countReg = 0;
        boolean existe = false;

        try {

            countReg
                    = jdbcTemplateDYC.queryForObject(SQLOracleDyC.EXISTE_SALDOICEP_X_ID.toString(), new Object[]{idSaldoICEP},
                            Integer.class);

        } catch (DataAccessException dae) {
            log.error(dae.getMessage());
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.EXISTE_SALDOICEP_X_ID.toString()
                    + ConstantesDyC1.TEXTO_3_ERROR_DAO + " idSaldoICEP " + idSaldoICEP);
            throw new SIATException(dae);
        }

        if (countReg > 0) {
            existe = Boolean.TRUE;
        }

        return existe;
    }

    @Override
    public DyctSaldoIcepDTO obtenerXId(long idSaldoICEP) throws SIATException {

        DyctSaldoIcepDTO dyctSaldoIcepT = null;
        try {
            String query = "SELECT * FROM  dyct_saldoicep  WHERE idsaldoicep =  ? AND fechafin IS NULL ";

            dyctSaldoIcepT
                    = this.jdbcTemplateDYC.queryForObject(query, new Object[]{idSaldoICEP}, new DyctSaldoIcepMapper());
        } catch (DataAccessException dae) {
            log.error(dae.getMessage());
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.DYCT_SALDOICEP_OBTENER_X_ID
                    + ConstantesDyC1.TEXTO_3_ERROR_DAO + " idSaldoICEP " + idSaldoICEP);
            throw new SIATException(dae);
        }

        return dyctSaldoIcepT;
    }

    @Override
    public List<DyctSaldoIcepDTO> obtenerSaldosDisponibles() throws SIATException {
        List<DyctSaldoIcepDTO> listDyctSaldoIcepDTO = null;

        try {
            listDyctSaldoIcepDTO
                    = this.jdbcTemplateDYC.query(SQLOracleDyC.DYCTSALDOICEP_OBTENER_SALDOS_DISPONIBLES.toString(),
                            new Object[]{}, new DyctSaldoIcepDisponibleMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.OBTENER_DETALLE_ICEP);
        }

        return listDyctSaldoIcepDTO;
    }

    public void insert(DyctSaldoIcepDTO saldoIcep) throws SIATException {
        StringBuilder sentInsertIcep = new StringBuilder();
        sentInsertIcep.append(" INSERT INTO DYCT_SALDOICEP (RFC, IDSALDOICEP, IDCONCEPTO, IDEJERCICIO, IDPERIODO, REMANENTE, BLOQUEADO ,FECHAFIN, IDORIGENSALDO ) ");
        sentInsertIcep.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

        try {
            if (saldoIcep.getIdSaldoIcep() == null) {
                saldoIcep.setIdSaldoIcep(jdbcTemplateDYC.queryForObject("SELECT DYCQ_IDSALDOICEP.NEXTVAL FROM DUAL",
                        Integer.class));
            }

            int[] tiposParams
                    = new int[]{Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.NUMERIC,
                        Types.INTEGER, Types.DATE, Types.INTEGER};

            Integer idOrigenSaldo
                    = saldoIcep.getDyccOrigenSaldoDTO() != null ? saldoIcep.getDyccOrigenSaldoDTO().getIdOrigenSaldo()
                            : null;

            jdbcTemplateDYC.update(sentInsertIcep.toString(),
                    new Object[]{saldoIcep.getRfc(), saldoIcep.getIdSaldoIcep(),
                        saldoIcep.getDyccConceptoDTO().getIdConcepto(),
                        saldoIcep.getDyccEjercicioDTO().getIdEjercicio(),
                        saldoIcep.getDyccPeriodoDTO().getIdPeriodo(),
                        saldoIcep.getRemanente(), saldoIcep.getBloqueado(),
                        saldoIcep.getFechaFin(), idOrigenSaldo}, tiposParams);
        } catch (DataAccessException dae) {

            log.error(dae.getMessage());
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + sentInsertIcep + ConstantesDyC1.TEXTO_3_ERROR_DAO
                    + ExtractorUtil.ejecutar(saldoIcep));
            ManejadorLogException.getTraceError(dae);
            throw new SIATException(dae);

        }
    }

    @Override
    public Integer obtenerSecuencia() throws SIATException {
        Integer secuencia = 0;
        try {
            secuencia
                    = jdbcTemplateDYC.queryForObject(SQLOracleDyC.DYCT_DETALLEICEP_SECUENCIA.toString(), Integer.class);

        } catch (DataAccessException dae) {
            log.error("obtenerSecuencia(); causa=" + dae.getCause());
            throw new SIATException(dae);
        }
        return secuencia;
    }

    @Override
    public DyctSaldoIcepDTO obtenerXIcepRfc(IcepVO icepDTO) throws SIATException {

        DyctSaldoIcepDTO dyctSaldoIcepDTO = null;
        try {
            dyctSaldoIcepDTO
                    = jdbcTemplateDYC.queryForObject(SQLOracleDyC.DYCT_SALDOICEP_OBTENER_POR_ICEP_RFC.toString(),
                            new Object[]{icepDTO.getRfc(), icepDTO.getIdEjercicio(),
                                icepDTO.getIdPeriodo(), icepDTO.getIdConcepto(),
                                icepDTO.getIdImpuesto(), icepDTO.getIdTipoSaldo()},
                            new DyctSaldoIcepMapper());
        } catch (DataAccessException dae) {
            log.error(dae.getMessage());
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.DYCT_SALDOICEP_OBTENER_POR_ICEP_RFC.toString()
                    + ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(icepDTO));
            throw new SIATException(dae);
        }

        return dyctSaldoIcepDTO;
    }

    @Override
    public List<DyctSaldoIcepDTO> selecXRfc(String rfc) throws SIATException {
        try {
            String query
                    = " SELECT S.*, C.*, I.*, P.*, I.DESCRIPCION AS DESCRIPCION_IMPUESTO, P.DESCRIPCION AS DESCRIPCION_PERIODO "
                    + " FROM DYCT_SALDOICEP S INNER JOIN DYCC_CONCEPTO C ON S.IDCONCEPTO = C.IDCONCEPTO "
                    + " INNER JOIN DYCC_IMPUESTO I ON I.IDIMPUESTO = C.IDIMPUESTO "
                    + " INNER JOIN DYCC_PERIODO P ON S.IDPERIODO = P.IDPERIODO "
                    + " WHERE RFC = ? ORDER BY S.IDSALDOICEP DESC";

            ImpuestoMapper mapperImpuesto = new ImpuestoMapper();
            ConceptoMapper mapperConcepto = new ConceptoMapper();
            mapperConcepto.setMapperImpuesto(mapperImpuesto);
            PeriodoMapper mapperPeriodo = new PeriodoMapper();
            DyctSaldoIcepMapper mapper = new DyctSaldoIcepMapper();
            mapper.setMapperConcepto(mapperConcepto);
            mapper.setMapperPeriodo(mapperPeriodo);
            return jdbcTemplateDYC.query(query, new Object[]{rfc}, mapper);
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + SQLOracleDyC.OBTENER_DETALLE_ICEP);
            throw new SIATException(dae);
        }
    }

    @Override
    public DyctSaldoIcepDTO encontrar(String rfc, DyccConceptoDTO concepto, DyccEjercicioDTO ejercicio,
            DyccPeriodoDTO periodo) {
        try {
            String query
                    = " SELECT * FROM DYCT_SALDOICEP WHERE RFC = ? AND IDCONCEPTO = ? " + " AND IDEJERCICIO = ? AND IDPERIODO = ? ";

            DyctSaldoIcepMapper mapper = new DyctSaldoIcepMapper();
            mapper.setConcepto(concepto);
            mapper.setEjercicio(ejercicio);
            mapper.setPeriodo(periodo);

            return jdbcTemplateDYC.queryForObject(query,
                    new Object[]{rfc, concepto.getIdConcepto(), ejercicio.getIdEjercicio(),
                        periodo.getIdPeriodo()}, mapper);
        } catch (org.springframework.dao.EmptyResultDataAccessException exEmpty) {
            return null;
        }
    }

    /**
     * Este metodo consulta si recupera una consula a la tabla DYCC_SALDOICEP
     *
     * @param rfc es el RFC del contribuyente
     * @param concepto
     * @param ejercicio tipo de ejercicio a realizar.
     * @param periodo periodo del cual se genera el tramite.
     * @param idOrigen es el tipo de origen del tramite.
     * @return regresa un objeto lleno de tipo <Object>DyctSaldoIcepDTO</Object>
     */
    @Override
    public DyctSaldoIcepDTO encontrar(final String rfc, final DyccConceptoDTO concepto,
            final DyccEjercicioDTO ejercicio, final DyccPeriodoDTO periodo,
            final DyccOrigenSaldoDTO dyccOrigenSaldoEjercicioDTO) {
        try {

            DyctSaldoIcepMapper mapper = new DyctSaldoIcepMapper();
            mapper.setConcepto(concepto);
            mapper.setEjercicio(ejercicio);
            mapper.setPeriodo(periodo);
            mapper.setOrigenSaldo(dyccOrigenSaldoEjercicioDTO);

            return jdbcTemplateDYC.queryForObject(DYCT_SALDOICEP_QUERY,
                    new Object[]{rfc, concepto.getIdConcepto(), ejercicio.getIdEjercicio(),
                        periodo.getIdPeriodo(),
                        dyccOrigenSaldoEjercicioDTO.getIdOrigenSaldo()},
                    mapper);
        } catch (org.springframework.dao.EmptyResultDataAccessException exEmpty) {
            return null;
        }
    }

    /**
     * Este metodo consulta si recupera una consula a la tabla DYCC_SALDOICEP
     *
     * @param rfc es el RFC del contribuyente
     * @param concepto
     * @param ejercicio tipo de ejercicio a realizar.
     * @param periodo periodo del cual se genera el tramite.
     * @return regresa un objeto lleno de tipo <Object>DyctSaldoIcepDTO</Object>
     */
    public DyctSaldoIcepDTO encontrarIcep(final String rfc, final int idConcepto, final int idEjercicio,
            final int idPeriodo) {
        try {

            return jdbcTemplateDYC.queryForObject(DYCT_SALDOICEP_QUERY_FOLIO,
                    new Object[]{idConcepto, idEjercicio, idPeriodo, rfc},
                    new DyctSaldoIcepMapper());
        } catch (org.springframework.dao.EmptyResultDataAccessException exEmpty) {
            return null;
        }
    }

    /**
     * Este metodo consulta si recupera una consula a la tabla DYCC_SALDOICEP
     *
     * @param rfc es el RFC del contribuyente
     * @param concepto
     * @param ejercicio tipo de ejercicio a realizar.
     * @param periodo periodo del cual se genera el tramite.
     * @return regresa un objeto lleno de tipo <Object>DyctSaldoIcepDTO</Object>
     */
    public DyctSaldoIcepDTO encontrarIcepISR(final String rfc, final int idConcepto, final int idEjercicio,
            final int idPeriodo) {
        try {

            return jdbcTemplateDYC.queryForObject(DYCT_SALDOICEP_QUERY_ISR,
                    new Object[]{idConcepto, idEjercicio, idPeriodo, rfc},
                    new DyctSaldoIcepMapper());
        } catch (org.springframework.dao.EmptyResultDataAccessException exEmpty) {
            return null;
        }
    }

    @Override
    public List<DyctSaldoIcepDTO> selecXRfcConceptoEjercicioPeriodo(String rfc, DyccConceptoDTO concepto,
            DyccEjercicioDTO ejercicio,
            DyccPeriodoDTO periodo) {
        String query
                = " SELECT * FROM DYCT_SALDOICEP WHERE RFC = ? AND IDCONCEPTO = ? AND IDEJERCICIO = ? AND IDPERIODO = ? "
                + " ORDER BY IDSALDOICEP DESC";

        DyctSaldoIcepMapper mapper = new DyctSaldoIcepMapper();
        mapper.setConcepto(concepto);
        mapper.setEjercicio(ejercicio);
        mapper.setPeriodo(periodo);

        return jdbcTemplateDYC.query(query,
                new Object[]{rfc, concepto.getIdConcepto(), ejercicio.getIdEjercicio(), periodo.getIdPeriodo()},
                mapper);
    }

    @Override
    public List<DyctSaldoIcepDTO> selecXRfcConceptoEjercicioPeriodoOrigen(String rfc, DyccConceptoDTO concepto,
            DyccEjercicioDTO ejercicio, DyccPeriodoDTO periodo, DyccOrigenSaldoDTO origenSaldo) {
        String query
                = " SELECT * FROM DYCT_SALDOICEP WHERE RFC = ? AND IDCONCEPTO = ? AND IDEJERCICIO = ? AND IDPERIODO = ?  and IDORIGENSALDO = ?"
                + " ORDER BY IDSALDOICEP DESC";

        DyctSaldoIcepMapper mapper = new DyctSaldoIcepMapper();
        mapper.setConcepto(concepto);
        mapper.setEjercicio(ejercicio);
        mapper.setPeriodo(periodo);

        return jdbcTemplateDYC.query(query,
                new Object[]{rfc, concepto.getIdConcepto(), ejercicio.getIdEjercicio(), periodo.getIdPeriodo(), origenSaldo.getIdOrigenSaldo()},
                mapper);
    }

    @Override
    public void actualizarRemFebaseActrem(DyctSaldoIcepDTO saldoIcep) throws SIATException {
        try {
            String sentencia
                    = "UPDATE DYCT_SALDOICEP SET REMANENTE = ?, FECHABASECALCULO = ?, ACTREMANENTE = ? WHERE IDSALDOICEP = ? ";
            this.jdbcTemplateDYC.update(sentencia,
                    new Object[]{saldoIcep.getRemanente(), saldoIcep.getFechaBaseCalculo(),
                        saldoIcep.getActRemanente(), saldoIcep.getIdSaldoIcep()});
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new SIATException(e);
        }
    }

    @Override
    public DyctSaldoIcepDTO encontrar(String rfc, DyccConceptoDTO concepto, DyccEjercicioDTO ejercicio,
            DyccPeriodoDTO periodo, Integer origenSaldo) throws DycDaoExcepcion {

        try {
            String query
                    = " SELECT * FROM DYCT_SALDOICEP WHERE RFC = ? AND IDCONCEPTO = ? AND IDEJERCICIO = ? AND IDPERIODO = ? AND IDORIGENSALDO = ?";

            DyctSaldoIcepMapper mapper = new DyctSaldoIcepMapper();
            mapper.setConcepto(concepto);
            mapper.setEjercicio(ejercicio);
            mapper.setPeriodo(periodo);

            return jdbcTemplateDYC.queryForObject(query,
                    new Object[]{rfc, concepto.getIdConcepto(), ejercicio.getIdEjercicio(),
                        periodo.getIdPeriodo(), origenSaldo}, mapper);
        } catch (org.springframework.dao.EmptyResultDataAccessException exEmpty) {

            log.debug("No se encuentra el ICEP registrado.");
            DyctSaldoIcepDTO fail = new DyctSaldoIcepDTO();
            fail.setBoId("-1");
            return fail;
        }
    }

    @Override
    public List<DycpDatosSolicitudDTO> getSolicitud(Integer idSaldoIcep) {

        String query
                = " SELECT S.IDESTADOSOLICITUD, S.NUMCONTROL,SE.NUMEMPLEADODICT FROM DYCP_SOLICITUD S INNER JOIN DYCP_SERVICIO SE "
                + " ON  SE.NUMCONTROL=S.NUMCONTROL  WHERE S.IDSALDOICEP = ?  ORDER BY S.NUMCONTROL ASC";
        return jdbcTemplateDYC.query(query,
                new Object[]{idSaldoIcep}, new DycpDatosSolicitudMapper());

    }

    public String getCompensacionPorIcep(Integer idSaldoIcep) {
        try {
            String query
                    = " SELECT  NUMCONTROL FROM  DYCP_COMPENSACION where   IDSALDOICEPORIGEN=? and rownum<2 ";
            return jdbcTemplateDYC.queryForObject(query,
                    new Object[]{idSaldoIcep}, String.class);
        } catch (org.springframework.dao.EmptyResultDataAccessException exEmpty) {
            log.debug("No se encuentra el ICEP registrado en compensaciones.");
            return null;
        }

    }
}
