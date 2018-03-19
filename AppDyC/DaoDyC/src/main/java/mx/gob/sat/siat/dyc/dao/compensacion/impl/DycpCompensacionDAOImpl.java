/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/


package mx.gob.sat.siat.dyc.dao.compensacion.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.compensacion.IDycpCompensacionDAO;
import mx.gob.sat.siat.dyc.dao.compensacion.impl.mapper.CompensacionMapper;
import mx.gob.sat.siat.dyc.dao.compensacion.impl.mapper.CompensacionMapperAprob;
import mx.gob.sat.siat.dyc.dao.compensacion.impl.mapper.DycpCompensacionMapper;
import mx.gob.sat.siat.dyc.dao.compensacion.impl.mapper.DycpServicioMapper;
import mx.gob.sat.siat.dyc.dao.compensacion.impl.mapper.DyctSaldoIcepMapper;
import mx.gob.sat.siat.dyc.dao.concepto.impl.mapper.ConceptoMapper;
import mx.gob.sat.siat.dyc.dao.concepto.impl.mapper.ImpuestoMapper;
import mx.gob.sat.siat.dyc.dao.periodo.impl.mapper.PeriodoMapper;
import mx.gob.sat.siat.dyc.dao.resolucion.impl.mapper.DyctResolCompMapper;
import mx.gob.sat.siat.dyc.dao.tipotramite.impl.mapper.TipoTramiteMapper;
import mx.gob.sat.siat.dyc.dao.usuario.impl.mapper.DictaminadorMapper;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccEstadoCompDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpAvisoCompDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoServicioDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.UtilsDominio;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;
import mx.gob.sat.siat.dyc.vistas.dao.impl.mapper.AdmcUnidadAdmvaMapper;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository(value = "iDycpCompensacionDAO")
public class DycpCompensacionDAOImpl implements IDycpCompensacionDAO {
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(DycpCompensacionDAOImpl.class.getName());

    /**
     * Busca los datos de la compensacion a traves del numero de control
     *
     * @param numControl numero de control al que esta asociado la compensacion
     * @return objeto DycpCompensacionDTO con todos los datos de la compensacion
     * @throws SIATException
     */
    @Override
    public DycpCompensacionDTO encontrar(String numControl) throws SIATException {
        try {
            String query =
                " SELECT C.*, S.*, TT.*, SI.*, PER.*, CON.*, IMP.*, CON.DESCRIPCION, PER.DESCRIPCION as DESCRIPCION_PERIODO, IMP.DESCRIPCION  as   DESCRIPCION_IMPUESTO "
                    + "FROM DYCP_COMPENSACION C, DYCP_SERVICIO S, DYCC_TIPOTRAMITE TT, DYCT_SALDOICEP SI, "
                    + "DYCC_PERIODO PER, DYCC_CONCEPTO CON, DYCC_IMPUESTO IMP "
                    + "WHERE C.NUMCONTROL = S.NUMCONTROL "
                    + "AND TT.IDTIPOTRAMITE = S.IDTIPOTRAMITE AND SI.IDSALDOICEP = C.IDSALDOICEPORIGEN "
                    + "AND PER.IDPERIODO = SI.IDPERIODO  AND CON.IDCONCEPTO = SI.IDCONCEPTO AND IMP.IDIMPUESTO = CON.IDIMPUESTO " 
                    + "AND C.NUMCONTROL = ? ORDER BY C.IDSALDOICEPDESTINO DESC";
            TipoTramiteMapper mapperTipoTramite = new TipoTramiteMapper();
            DycpServicioMapper mapperServicio = new DycpServicioMapper();
            mapperServicio.setMapperTipoTramite(mapperTipoTramite);
            CompensacionMapper mapper = new CompensacionMapper();
            DyctSaldoIcepMapper mapperSaldoIcep = new DyctSaldoIcepMapper();
            PeriodoMapper mapperPeriodo = new PeriodoMapper();
            ConceptoMapper mapperConcepto = new ConceptoMapper();
            ImpuestoMapper mapperImpuesto = new ImpuestoMapper();
            mapperSaldoIcep.setMapperPeriodo(mapperPeriodo);
            mapperConcepto.setMapperImpuesto(mapperImpuesto);
            mapperSaldoIcep.setMapperConcepto(mapperConcepto);
            mapper.setMapperServicio(mapperServicio);
            mapper.setMapperSaldoIcepOrigen(mapperSaldoIcep);
            return jdbcTemplateDYC.queryForObject(query, new Object[] { numControl }, mapper);
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTA_DYCP_COMPENSACION + ConstantesDyC1.TEXTO_3_ERROR_DAO + "numcontrol" + numControl +
                      ConstantesDyC1.TEXTO_8_CAUSAS + dae.getCause());
            throw new SIATException(dae);
        }
    }

    /**
     * Busca los datos de la compensacion a traves del numero de control
     *
     * @param numControl numero de control al que esta asociado la compensacion
     * @return objeto DycpCompensacionDTO con todos los datos de la compensacion
     * @throws SIATException
     */
    @Override
    public DycpCompensacionDTO buscarCompensacionIceps(String numControl) throws SIATException {
        try {
            return jdbcTemplateDYC.queryForObject(SQLOracleDyC.BUSCAR_COMPENSACION_ICEPS.toString(), new Object[] { numControl, numControl },
                                                  new DycpCompensacionMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.BUSCAR_COMPENSACION_ICEPS + ConstantesDyC1.TEXTO_3_ERROR_DAO + " numcontrol:" + numControl +
                      ConstantesDyC1.TEXTO_8_CAUSAS + dae.getCause());
            throw new SIATException(dae);
        }
    }


    @Override
    public List<DycpCompensacionDTO> selecXTiposervicio(DyccTipoServicioDTO tipoServicio) throws SIATException {
        try {
            final String query =
                " SELECT C.* FROM DYCP_COMPENSACION C, DYCP_SERVICIO S WHERE C.NUMCONTROL = S.NUMCONTROL AND S.IDTIPOSERVICIO = ?";
            return jdbcTemplateDYC.query(query, new Object[] { tipoServicio.getIdTipoServicio() },
                                         new CompensacionMapper());
        } catch (DataAccessException dae) {
            throw new SIATException(dae);
        }
    }


    @Override
    @Transactional(readOnly = false)
    public void actualizar(Integer numEmpleado, String numControl) throws SIATException {
        try {
            log.info("Aviso " + numControl + " asignando a " + numEmpleado);
            jdbcTemplateDYC.update(SQLOracleDyC.ASIGNAR_DICTAMINADOR_AVISOCOMP.toString(), numEmpleado, numControl);
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.ASIGNAR_DICTAMINADOR_AVISOCOMP +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + "numEmpleado" + numEmpleado + " y NUMCONTROL = " + numControl);
            throw new SIATException(dae);
        }

    }

    @Override
    public List<DycpCompensacionDTO> selecXDictaminadorEstadocomp(DyccDictaminadorDTO dictaminador,
                                                                  DyccEstadoCompDTO estadoComp) {
        String query =
            " SELECT * FROM DYCP_COMPENSACION C, DYCP_SERVICIO S WHERE C.NUMEMPLEADODICT = ? AND C.IDESTADOCOMP = ? AND C.NUMCONTROL = S.NUMCONTROL";
        CompensacionMapper mapper = new CompensacionMapper();
        DycpServicioMapper mapperServicio = new DycpServicioMapper();
        mapper.setMapperServicio(mapperServicio);
        /**mapper.setDictaminador(dictaminador);*/
        return jdbcTemplateDYC.query(query,
                                     new Object[] { dictaminador.getNumEmpleado(), estadoComp.getIdEstadoComp() },
                                     mapper);
    }

    @Override
    public List<DycpCompensacionDTO> selecXEstadocompClaveadm(DyccEstadoCompDTO estadoComp, Integer claveAdm) {
        String query = " SELECT * FROM DYCP_COMPENSACION WHERE IDESTADOCOMP = ? AND CLAVEADM = ? ";
        return jdbcTemplateDYC.query(query, new Object[] { estadoComp.getIdEstadoComp(), claveAdm },
                                     new CompensacionMapper());
    }

    /**
     * Actualiza el estatus del tramite de la compensacion
     *
     * @param status es el valor del estatus a actualizar
     * @param numControl es numero de control de la solicitud
     */
    @Override
    @Transactional(readOnly = false)
    public void actualizarStatus(Integer status, String numControl) throws SIATException {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.ACTUALIZAR_TRAMITECOMPENSACION.toString(), new Object[] { status, numControl });
        } catch (DataAccessException dae) {
            log.error("actualizarStatus(); parametros= status:" + status + ", numControl:" + numControl +
                      ConstantesDyC1.TEXTO_8_CAUSAS + dae.getCause());
            throw new SIATException(dae);
        }
    }

    /**
     * Inserta una nueva Compensacion
     * @param regAut
     * @autor [LADP] Luis Alberto Dominguez Palomino
     * @param dycpCompensacionDTO a insertar
     */
    @Override
    @Transactional(readOnly = false)
    public void insertar(DycpCompensacionDTO dycpCompensacionDTO, boolean regAut) throws SIATException {
        try {
            jdbcTemplateDYC.update(null != dycpCompensacionDTO.getFechaInicioTramite() ? SQLOracleDyC.INSERTAR_CASOCOMPENSACION.toString() :
                                   SQLOracleDyC.INSERTAR_CASOCOMPENSACION1.toString(), getDatosCompensacion(dycpCompensacionDTO, regAut));
            log.info("Se guardo en dycp_compensacion : " + dycpCompensacionDTO.getDycpServicioDTO().getNumControl());
        } catch (DataAccessException siatE) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + siatE.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.INSERTAR_CASOCOMPENSACION + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      dycpCompensacionDTO.getNumControl() );
            throw new SIATException(siatE);
        }
    }

    private Object[] getDatosCompensacion(DycpCompensacionDTO dycpCompensacionDTO, boolean regAut) {
        dycpCompensacionDTO.setDyccEstadoCompDTO(regAut? Constantes.EstadosCompensacion.REGISTRO: Constantes.EstadosCompensacion.EN_PROCESO);
        if (null != dycpCompensacionDTO.getFechaInicioTramite()) {
            return new Object[] { dycpCompensacionDTO.getDycpServicioDTO().getNumControl(),
                                  dycpCompensacionDTO.getFechaPresentacion(),
                                  dycpCompensacionDTO.getImporteCompensado(),
                                  dycpCompensacionDTO.getFechaPresentaDec(), dycpCompensacionDTO.getNumOperacionDec(),
                                  dycpCompensacionDTO.getDyccTipoDeclaraDTO().getIdTipoDeclaracion(),
                                  dycpCompensacionDTO.getDyccEstadoCompDTO().getIdEstadoComp(),
                                  dycpCompensacionDTO.getDycpAvisoCompDTO().getFolioAviso(),
                                  dycpCompensacionDTO.getDyctSaldoIcepDestinoDTO().getIdSaldoIcep(),
                                  dycpCompensacionDTO.getRemanenteHistorico(), dycpCompensacionDTO.getRemanenteAct(),
                                  dycpCompensacionDTO.getDyctSaldoIcepOrigenDTO().getIdSaldoIcep() };
        } else {
            return new Object[] { dycpCompensacionDTO.getDycpServicioDTO().getNumControl(),
                                  dycpCompensacionDTO.getImporteCompensado(),
                                  dycpCompensacionDTO.getFechaPresentaDec(), dycpCompensacionDTO.getNumOperacionDec(),
                                  dycpCompensacionDTO.getDyccTipoDeclaraDTO().getIdTipoDeclaracion(),
                                   dycpCompensacionDTO.getDyccEstadoCompDTO().getIdEstadoComp(),
                                  dycpCompensacionDTO.getDycpAvisoCompDTO().getFolioAviso(),
                                  dycpCompensacionDTO.getDyctSaldoIcepDestinoDTO().getIdSaldoIcep(),
                                  dycpCompensacionDTO.getRemanenteHistorico(), dycpCompensacionDTO.getRemanenteAct(),
                                  dycpCompensacionDTO.getDyctSaldoIcepOrigenDTO().getIdSaldoIcep() };
        }
    }

    @Override
    public List<DycpCompensacionDTO> selecXEstadocomp(DyccEstadoCompDTO estadoComp) {
        String query = " SELECT * FROM DYCP_COMPENSACION WHERE IDESTADOCOMP = ? ";
        return jdbcTemplateDYC.query(query, new Object[] { estadoComp.getIdEstadoComp() }, new CompensacionMapper());
    }

    @Override
    public int actualizarEstadocomp(DycpCompensacionDTO compensacion)
    {
        String sentencia = "UPDATE DYCP_COMPENSACION SET IDESTADOCOMP = ? WHERE NUMCONTROL = ? ";
        String numControl = compensacion.getNumControl() != null ? compensacion.getNumControl() : compensacion.getDycpServicioDTO().getNumControl();
        return jdbcTemplateDYC.update(sentencia,
                               new Object[] {compensacion.getDyccEstadoCompDTO().getIdEstadoComp(), numControl });
    }

    @Override
    @Transactional(readOnly = false)
    public void insertarCompensacion(DycpAvisoCompDTO dycpAvisoCompDTO) throws SIATException {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.INSERTAR_CASOCOMPENSACION.toString(),
                                   new Object[] { dycpAvisoCompDTO.getDycpCompensacionDTO().getNumControl(),
                                                  dycpAvisoCompDTO.getDycpCompensacionDTO().getFechaInicioTramite(),
                                                  dycpAvisoCompDTO.getDycpCompensacionDTO().getFechaPresentacion(),
                                                  dycpAvisoCompDTO.getDycpCompensacionDTO().getImporteCompensado(),
                                                  dycpAvisoCompDTO.getDycpCompensacionDTO().getFechaPresentaDec(),
                                                  dycpAvisoCompDTO.getDycpCompensacionDTO().getNumOperacionDec(),
                                                  dycpAvisoCompDTO.getDycpCompensacionDTO().getDyccTipoDeclaraDTO().getIdTipoDeclaracion(),
                                                  dycpAvisoCompDTO.getDycpCompensacionDTO().getDycpServicioDTO().getClaveAdm() });
            log.info("Se guardo en dycp_compensacion : " + dycpAvisoCompDTO.getDycpCompensacionDTO().getNumControl());
        } catch (DataAccessException siatE) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + siatE.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.INSERTAR_CASOCOMPENSACION + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(dycpAvisoCompDTO));
            throw new SIATException(siatE);
        }
    }

    /**
     * Actualiza de forma independiente los campos IDESTADOCOMP, NUMEMPLEADOAPROB, si estos NO son nulos
     * @param compensacion
     */
    @Override
    public void actualizar(DycpCompensacionDTO compensacion) throws SIATException {
        try {
            List<Object> params = new ArrayList<Object>();
            boolean comma = false;
            StringBuilder valoresSettear = new StringBuilder();

            if (compensacion.getDyccEstadoCompDTO() != null) {
                valoresSettear.append(" IDESTADOCOMP = ?");
                params.add(compensacion.getDyccEstadoCompDTO().getIdEstadoComp());
                comma = Boolean.TRUE;
            }

            if (compensacion.getDyccAprobadorDTO() != null) {
                if (comma) {
                    valoresSettear.append(",");
                }
                valoresSettear.append(" NUMEMPLEADOAPROB = ?");
                params.add(compensacion.getDyccAprobadorDTO().getNumEmpleado());
            }

            String sentencia = " UPDATE DYCP_COMPENSACION SET " + valoresSettear.toString() + " WHERE NUMCONTROL = ?";
            log.info("sentencia ->" + sentencia);

            params.add(compensacion.getNumControl());

            Object[] p = new Object[params.size()];
            int i = ConstantesDyCNumerico.VALOR_0;

            for (Object o : params) {
                p[i] = o;
                log.info("parametro en update compensacion ->" + o + "<-");
                i++;
            }

            jdbcTemplateDYC.update(sentencia, p);
        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(compensacion) + ConstantesDyC1.TEXTO_8_CAUSAS + dae);
            throw new SIATException(dae);
        }
    }

    @Override
    public List<DycpCompensacionDTO> obtenerAprobadasXIdSaldoOrigen(Integer idSaldoIcep) throws SIATException {
        List<DycpCompensacionDTO> listDycpCompensacionDTO = null;
        try {
            listDycpCompensacionDTO =
                    jdbcTemplateDYC.query(SQLOracleDyC.DYCP_COMPENSACION_APROBADAS_X_IDSALDO_ORIGEN.toString(), new Object[] { idSaldoIcep },
                                          new CompensacionMapper());

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTA_DYCP_COMPENSACION + ConstantesDyC1.TEXTO_3_ERROR_DAO + "idSaldoIcep " + idSaldoIcep +
                      ConstantesDyC1.TEXTO_8_CAUSAS + dae.getCause());
            throw new SIATException(dae);
        }
        return listDycpCompensacionDTO;
    }

    @Override
    public List<DycpCompensacionDTO> obtenerAprobadasXIdSaldoDestino(Integer idSaldoIcep) throws SIATException {
        List<DycpCompensacionDTO> listDycpCompensacionDTO = null;
        try {
            listDycpCompensacionDTO =
                    jdbcTemplateDYC.query(SQLOracleDyC.DYCP_COMPENSACION_APROBADAS_X_IDSALDO_DESTINO.toString(), new Object[] { idSaldoIcep },
                                          new CompensacionMapper());

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTA_DYCP_COMPENSACION + ConstantesDyC1.TEXTO_3_ERROR_DAO + "idSaldoIcep " + idSaldoIcep +
                      ConstantesDyC1.TEXTO_8_CAUSAS + dae.getCause());
            throw new SIATException(dae);
        }
        return listDycpCompensacionDTO;
    }

    @Override
    public List<DycpCompensacionDTO> selecXDictaminadorEstadocompOrder(DyccDictaminadorDTO dictaminador,
                                                                       DyccEstadoCompDTO estadoComp, String orderBy) {
        StringBuilder query =
            new StringBuilder(" SELECT * FROM DYCP_COMPENSACION C LEFT OUTER JOIN DYCT_RESOLCOMP R ON (C.NUMCONTROL = R.NUMCONTROL)," +
                              " DYCP_SERVICIO S, DYCC_TIPOTRAMITE TT" +
                              " WHERE C.NUMCONTROL = S.NUMCONTROL AND TT.IDTIPOTRAMITE = S.IDTIPOTRAMITE AND S.NUMEMPLEADODICT = ? AND C.IDESTADOCOMP = ? ");

        if (orderBy != null) {
            query.append(orderBy);
        }
        log.info("QUERY: \n\n\n" +
                query + "\n\n");


        TipoTramiteMapper mapperTipoTramite = new TipoTramiteMapper();
        DycpServicioMapper mapperServicio = new DycpServicioMapper();
        mapperServicio.setDictaminador(dictaminador);
        mapperServicio.setMapperTipoTramite(mapperTipoTramite);
        CompensacionMapper mapper = new CompensacionMapper();
        /**mapper.setMapperResolComp(mapperResolComp);*/
        mapper.setMapperServicio(mapperServicio);

        return this.jdbcTemplateDYC.query(query.toString(),
                                          new Object[] { dictaminador.getNumEmpleado(), estadoComp.getIdEstadoComp() },
                                          mapper);
    }

    @Override
    public List<DycpCompensacionDTO> selecXEstadocompClaveadmOrder(DyccEstadoCompDTO estadoComp, Integer claveAdm,
                                                                   String orderBy) {
        log.debug("claveAdm ->" + claveAdm);
        String query =
            " SELECT C.*, S.*, D.*, TT.*, A.CLAVENIVEL FROM DYCP_COMPENSACION C LEFT OUTER JOIN DYCC_APROBADOR A ON C.NUMEMPLEADOAPROB = A.NUMEMPLEADO, DYCP_SERVICIO S " +
            " LEFT OUTER JOIN DYCC_DICTAMINADOR D ON S.NUMEMPLEADODICT = D.NUMEMPLEADO, "+
            " DYCC_TIPOTRAMITE TT WHERE S.NUMCONTROL = C.NUMCONTROL "+
            " AND TT.IDTIPOTRAMITE = S.IDTIPOTRAMITE AND C.IDESTADOCOMP = ? AND S.CLAVEADM = ?";
    
        if (orderBy != null) {
            query += orderBy;
        }

        TipoTramiteMapper mapperTipoTramite = new TipoTramiteMapper();
        DictaminadorMapper mapperDictaminador = new DictaminadorMapper();
        DycpServicioMapper mapperServicio = new DycpServicioMapper();
        mapperServicio.setMapperDictaminador(mapperDictaminador);
        mapperServicio.setMapperTipoTramite(mapperTipoTramite);
        CompensacionMapperAprob mapper = new CompensacionMapperAprob();
        mapper.setMapperServicio(mapperServicio);

        return this.jdbcTemplateDYC.query(query, new Object[] { estadoComp.getIdEstadoComp(), claveAdm }, mapper);
    }
    
    @Override
    public List<DycpCompensacionDTO> selecXEstadocompClaveadmOrderRegistrada(DyccEstadoCompDTO estadoComp, Integer claveAdm,
                                                                   String orderBy, Integer numEmpleado) {
        log.debug("claveAdm ->" + claveAdm);
        String query =
            " SELECT C.*, S.*, D.*, TT.*, A.CLAVENIVEL FROM DYCP_COMPENSACION C LEFT OUTER JOIN DYCC_APROBADOR A ON C.NUMEMPLEADOAPROB = A.NUMEMPLEADO, DYCP_SERVICIO S " +
            " LEFT OUTER JOIN DYCC_DICTAMINADOR D ON S.NUMEMPLEADODICT = D.NUMEMPLEADO, "+
            " DYCC_TIPOTRAMITE TT WHERE S.NUMCONTROL = C.NUMCONTROL "+
            " AND TT.IDTIPOTRAMITE = S.IDTIPOTRAMITE AND C.IDESTADOCOMP = ? AND C.NUMEMPLEADOAPROB = ?";
    
        if (orderBy != null) {
            query += orderBy;
        }

        TipoTramiteMapper mapperTipoTramite = new TipoTramiteMapper();
        DictaminadorMapper mapperDictaminador = new DictaminadorMapper();
        DycpServicioMapper mapperServicio = new DycpServicioMapper();
        mapperServicio.setMapperDictaminador(mapperDictaminador);
        mapperServicio.setMapperTipoTramite(mapperTipoTramite);
        CompensacionMapperAprob mapper = new CompensacionMapperAprob();
        mapper.setMapperServicio(mapperServicio);

        return this.jdbcTemplateDYC.query(query, new Object[] { estadoComp.getIdEstadoComp(), numEmpleado }, mapper);
    }

    @Override
    public List<DycpCompensacionDTO> selecXSaldoiceporigen(DyctSaldoIcepDTO saldoIcep) {
        String query =
            " SELECT * FROM DYCP_COMPENSACION C INNER JOIN DYCP_SERVICIO S ON C.NUMCONTROL = S.NUMCONTROL" + 
            " WHERE IDSALDOICEPORIGEN = ? ORDER BY IDSALDOICEPORIGEN";

        DycpServicioMapper mapperServicio = new DycpServicioMapper();
        CompensacionMapper mapper = new CompensacionMapper();
        mapper.setSaldoIcepOrigen(saldoIcep);
        mapper.setMapperServicio(mapperServicio);

        return jdbcTemplateDYC.query(query, new Object[] { saldoIcep.getIdSaldoIcep() }, mapper);
    }

    @Override
    public List<DycpCompensacionDTO> selecXSaldoicepdestino(DyctSaldoIcepDTO saldoIcep) {
        String query = "SELECT * FROM DYCP_COMPENSACION CO INNER JOIN DYCP_SERVICIO SE ON CO.NUMCONTROL = SE.NUMCONTROL " +
            "WHERE CO.IDSALDOICEPDESTINO = ? ORDER BY CO.IDSALDOICEPDESTINO";

        CompensacionMapper mapper = new CompensacionMapper();
        mapper.setSaldoIcepOrigen(saldoIcep);
        return jdbcTemplateDYC.query(query, new Object[] { saldoIcep.getIdSaldoIcep() }, mapper);
    }

    @Override
    public List<DycpCompensacionDTO> buscaXFolioAviso(String folioAvisoNormal) {
        String query = "SELECT * FROM DYCP_COMPENSACION CO INNER JOIN DYCP_SERVICIO SE ON CO.NUMCONTROL = SE.NUMCONTROL WHERE CO.FOLIOAVISO = ?";
        return jdbcTemplateDYC.query(query, new Object[] { folioAvisoNormal }, new CompensacionMapper());
    }

    @Override
    public List<DycpCompensacionDTO> obtenerCompensacionResol(String numControl, int and) throws SIATException
    {
        String query = null;
        try {
            CompensacionMapper mapper = new CompensacionMapper();
            DyctResolCompMapper mapperResolComp = new DyctResolCompMapper();
            mapper.setMapperResolComp(mapperResolComp);
            query =
                and == ConstantesDyCNumerico.VALOR_1 ? SQLOracleDyC.CONSULTA_COMPENSACION_RESOLCOMP.toString().replace("[AND]", SQLOracleDyC.AND_COMPENSACION_RESOLCOMP) :
                SQLOracleDyC.CONSULTA_COMPENSACION_RESOLCOMP.toString().replace("[AND]", "");
            return jdbcTemplateDYC.query(query, new Object[] { numControl }, mapper);
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO + query +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + ConstantesDyC1.TEXTO_8_CAUSAS + dae.getCause());
            throw new SIATException(dae);
        }
    }
    
    @Override
    public List<DycpCompensacionDTO> obtenerCompensacionResol(String numControl) throws SIATException
    {
        String query = null;
        try {
            CompensacionMapper mapper = new CompensacionMapper();
            DyctResolCompMapper mapperResolComp = new DyctResolCompMapper();
            mapper.setMapperResolComp(mapperResolComp);
            query = SQLOracleDyC.CONSULTA_COMPENSACION_RESOLCOMP_FECHA_RESOLUCION.toString();
            return jdbcTemplateDYC.query(query, new Object[] { numControl }, mapper);
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO + query +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + ConstantesDyC1.TEXTO_8_CAUSAS + dae.getCause());
            throw new SIATException(dae);
        }
    }

    @Override
    public List<DycpCompensacionDTO> buscaCompensacionAprobador(Integer numEmpleadoAprob) {
        List<DycpCompensacionDTO> lista = null;
        String sql = "SELECT * FROM DYCP_COMPENSACION WHERE IDESTADOCOMP IN(4, 8) AND NUMEMPLEADOAPROB = ? ";
        try {
            lista = this.jdbcTemplateDYC.query(sql, new Object[] { numEmpleadoAprob }, new CompensacionMapper());

        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO + sql +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + numEmpleadoAprob);
        }
        return lista;
    }

    @Override
    public void actualizarNumEmpleadoAprob(Integer numEmpleadoAprob, String numControl) throws SIATException {
        String sql = "UPDATE DYCP_COMPENSACION SET NUMEMPLEADOAPROB = ? WHERE NUMCONTROL = ?";
        try {
            this.jdbcTemplateDYC.update(sql, new Object[] { numEmpleadoAprob, numControl });
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO + sql +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + numEmpleadoAprob + "y" + numControl);
            throw new SIATException(dae);
        }
    }
    
    @Override
    public List<DycpCompensacionDTO> selecXAvisocomp(DycpAvisoCompDTO avisoComp)
    {
        String query =  " SELECT CO.*, SE.*, SI.*, U.*, T.*, " +
                        UtilsDominio.obtenerAliasColumnas("DYCC_DICTAMINADOR", "DICT", jdbcTemplateDYC) + 
                        " FROM (DYCP_COMPENSACION CO INNER JOIN DYCP_SERVICIO SE ON CO.NUMCONTROL = SE.NUMCONTROL)" +
                        " INNER JOIN DYCT_SALDOICEP SI ON CO.IDSALDOICEPORIGEN = SI.IDSALDOICEP " +
                        " LEFT OUTER JOIN DYCC_UNIDADADMVA U ON SE.CLAVEADM = U.CLAVE_SIR " +
                        " INNER JOIN DYCC_TIPOTRAMITE T ON T.IDTIPOTRAMITE = SE.IDTIPOTRAMITE " +
                        " LEFT OUTER JOIN DYCC_DICTAMINADOR DICT ON SE.NUMEMPLEADODICT = DICT.NUMEMPLEADO " +
                        " WHERE CO.FOLIOAVISO = ?";

        AdmcUnidadAdmvaMapper mapperUnidadAdmva = new AdmcUnidadAdmvaMapper();
        TipoTramiteMapper mapperTipoTramite = new TipoTramiteMapper();
        DictaminadorMapper mapperDictaminador = new DictaminadorMapper();
        DycpServicioMapper mapperServicio = new DycpServicioMapper();
        mapperServicio.setMapperDictaminador(mapperDictaminador);
        mapperServicio.setMapperTipoTramite(mapperTipoTramite);
        mapperServicio.setMapperUnidadAdmva(mapperUnidadAdmva);
        DyctSaldoIcepMapper mapperSaldoIcep = new DyctSaldoIcepMapper();
        CompensacionMapper mapper = new CompensacionMapper();
        mapper.setAvisoComp(avisoComp);
        mapper.setMapperServicio(mapperServicio);
        mapper.setMapperSaldoIcepOrigen(mapperSaldoIcep);

        return jdbcTemplateDYC.query(query, new Object[] { avisoComp.getFolioAviso() }, mapper);
    }

   
}
