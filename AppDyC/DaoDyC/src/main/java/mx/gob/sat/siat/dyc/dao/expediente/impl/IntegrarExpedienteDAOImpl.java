/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.dao.expediente.impl;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.sql.SQLException;
import java.sql.Types;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.expediente.IntegrarExpedienteDAO;
import mx.gob.sat.siat.dyc.dao.expediente.impl.mapper.ConsultaNumeroControlDAOMapper;
import mx.gob.sat.siat.dyc.dao.expediente.impl.mapper.IntegrarExpedienteDAOMapper;
import mx.gob.sat.siat.dyc.domain.regsolicitud.TramiteCortoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctExpedienteDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.ExpedienteDTO;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;


/**
 * Implementaci&oacute;n DAO para insertar Expediente
 * @author Luis Fernando Barrios Quiroz [ LuFerMX ]
 * @since 0.1
 *
 * */
@Repository(value = "integrarExpedienteDAO")
public class IntegrarExpedienteDAOImpl implements IntegrarExpedienteDAO {

    private static final Logger LOG = Logger.getLogger(IntegrarExpedienteDAOImpl.class);

    public IntegrarExpedienteDAOImpl() {
        super();
    }

    private String strWhere;

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    /**
     * Inserta un Objetos Expediente con Columnas XMLType en la tabla de trabajo DYCT_EXPEDIENTE
     * @param Expediente
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Override
    public void insertarExpediente(DyctExpedienteDTO expediente) throws FileNotFoundException, ClassNotFoundException,
                                                                        SQLException {
        if (expediente != null) {
            Object[] objParam = getPaseParametros(expediente);
            int[] typ = getTipoDatos();
            jdbcTemplateDYC.update(SQLOracleDyC.INTEGRAREXPEDIENTE_INSERTAR.toString(), objParam, typ);
        } //Termina If null
    }

    /**
     * Inserta una lista de Objetos de Tipo Expediente con Columnas XMLType en la tabla de trabajo DYCT_EXPEDIENTE
     * @param Lista de tipo <ExpedienteDTO>
     * @return No regresa valores
     *
     * */
    @Override
    public void insertarExpediente(List<DyctExpedienteDTO> listaExpedientes) throws FileNotFoundException,
                                                                                    ClassNotFoundException,
                                                                                    SQLException {
        for (DyctExpedienteDTO expdt : listaExpedientes) {
            if (expdt != null) {

                jdbcTemplateDYC.update(SQLOracleDyC.INTEGRAREXPEDIENTE_INSERTAR.toString(), getPaseParametros(expdt), getTipoDatos());

            }
        } /* Termina For */

    }

    /**
     * Obtiene un solo registro de tipo ExpedienteDTO usando el NumControl como filtro de la tabla de trabajo DYCT_EXPEDIENTE
     * @param expedienteDTO
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    @Override
    public ExpedienteDTO buscarExpedienteNumControl(String noControl) {

        ExpedienteDTO expediente = null;
        List<ExpedienteDTO> expedientePersistido = null;

        try {

            expedientePersistido =
                    jdbcTemplateDYC.query(SQLOracleDyC.INTEGRAREXPEDIENTE_BUSCAREXPEDIENTEPORNUMERODECONTROL.toString(), new Object[] { noControl },
                                          new IntegrarExpedienteDAOMapper());
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.INTEGRAREXPEDIENTE_BUSCAREXPEDIENTEPORNUMERODECONTROL + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      " Numero de control " + noControl);
            throw dae;
        }

        if (expedientePersistido.size() > 0) {
            expediente = (ExpedienteDTO)expedientePersistido.get(0);
        }

        return expediente;
    }

    /**
     * Obtiene un solo registro de tipo ExpedienteDTO usando el NumControl como filtro de la tabla de trabajo DYCT_EXPEDIENTE
     * @param expedienteDTO
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    @Override
    public TramiteCortoDTO buscaNumeroControl(String noControl, String rfc) {

        TramiteCortoDTO tramite = null;
        List<TramiteCortoDTO> lstTramite = null;

        try {

            lstTramite =
                    jdbcTemplateDYC.query(SQLOracleDyC.CONSULTANUMEROCONTROLRFC.toString(), new Object[] { noControl, rfc }, new ConsultaNumeroControlDAOMapper());
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTANUMEROCONTROLRFC + ConstantesDyC1.TEXTO_3_ERROR_DAO + " Numero de control " + noControl);
            throw dae;
        }

        if (lstTramite.size() > 0) {
            tramite = (TramiteCortoDTO)lstTramite.get(0);
        }


        return tramite;
    }

    /**
     * Obtiene un solo registro de tipo ExpedienteDTO usando el NumControl como filtro de la tabla de trabajo DYCT_EXPEDIENTE
     * @param expedienteDTO
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    @Override
    public TramiteCortoDTO buscaNumeroControl(String noControl) {

        TramiteCortoDTO tramite = null;
        List<TramiteCortoDTO> lstTramite = null;

        try {

            lstTramite =
                    jdbcTemplateDYC.query(SQLOracleDyC.CONSULTANUMEROCONTROL.toString(), new Object[] { noControl }, new ConsultaNumeroControlDAOMapper());
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTANUMEROCONTROL + ConstantesDyC1.TEXTO_3_ERROR_DAO + " Numero de control " + noControl);
            throw dae;
        }

        if (lstTramite.size() > 0) {
            tramite = (TramiteCortoDTO)lstTramite.get(0);
        }


        return tramite;
    }

    public Object[] getPaseParametros(DyctExpedienteDTO expediente) {
        LobHandler lobHandler = new DefaultLobHandler();
        SqlLobValue sqlLVDatosRetenedorBanc = new SqlLobValue("", lobHandler);
        SqlLobValue sqlLVDatosCPR = new SqlLobValue("", lobHandler);
        SqlLobValue sqlLVDatosDIOT = new SqlLobValue("", lobHandler);
        SqlLobValue sqlLVDatosAltex = new SqlLobValue("", lobHandler);
        SqlLobValue sqlLVDatosPagos = new SqlLobValue("", lobHandler);
        SqlLobValue sqlLVDatosCompensacion = new SqlLobValue("", lobHandler);
        SqlLobValue sqlLVDatosPedimentos = new SqlLobValue("", lobHandler);
        SqlLobValue sqlLVDatosDevoluciones = new SqlLobValue("", lobHandler);
        SqlLobValue sqlLVDatosDictamenes = new SqlLobValue("", lobHandler);
        SqlLobValue sqlLVDatosDeclaraciones = new SqlLobValue("", lobHandler);
        SqlLobValue sqlLVDatosConsolidacion = new SqlLobValue("", lobHandler);
        SqlLobValue sqlLVDatosPagoDerechos = new SqlLobValue("", lobHandler);
        SqlLobValue sqlLVDatosPagoMultas = new SqlLobValue("", lobHandler);
        SqlLobValue sqlLVDatosDeterminaISR = new SqlLobValue("", lobHandler);
        SqlLobValue sqlLVDatosDeterminaIMP = new SqlLobValue("", lobHandler);
        SqlLobValue sqlLVDatosSaldoICEP = new SqlLobValue("", lobHandler);
        SqlLobValue sqlLVDatosRetenedorN = new SqlLobValue("", lobHandler);

        try {
            if (null != expediente.getDatosRetenedorBanc()) {
                byte[] bt = IOUtils.toByteArray(expediente.getDatosRetenedorBanc());
                sqlLVDatosRetenedorBanc = new SqlLobValue(new ByteArrayInputStream(bt), bt.length, lobHandler);
            }
            if (null != expediente.getDatosCPR()) {
                byte[] bt = IOUtils.toByteArray(expediente.getDatosCPR());
                sqlLVDatosCPR = new SqlLobValue(new ByteArrayInputStream(bt), bt.length, lobHandler);
            }
            if (null != expediente.getDatosDIOT()) {
                byte[] bt = IOUtils.toByteArray(expediente.getDatosDIOT());
                sqlLVDatosDIOT = new SqlLobValue(new ByteArrayInputStream(bt), bt.length, lobHandler);
            }
            if (null != expediente.getDatosALTEX()) {
                byte[] bt = IOUtils.toByteArray(expediente.getDatosALTEX());
                sqlLVDatosAltex = new SqlLobValue(new ByteArrayInputStream(bt), bt.length, lobHandler);
            }
            if (null != expediente.getDatosPagos()) {
                byte[] bt = IOUtils.toByteArray(expediente.getDatosPagos());
                sqlLVDatosPagos = new SqlLobValue(new ByteArrayInputStream(bt), bt.length, lobHandler);
            }
            if (null != expediente.getDatosCompensacion()) {
                byte[] bt = IOUtils.toByteArray(expediente.getDatosCompensacion());
                sqlLVDatosCompensacion = new SqlLobValue(new ByteArrayInputStream(bt), bt.length, lobHandler);
            }
            if (null != expediente.getDatosPedimentos()) {
                byte[] bt = IOUtils.toByteArray(expediente.getDatosPedimentos());
                sqlLVDatosPedimentos = new SqlLobValue(new ByteArrayInputStream(bt), bt.length, lobHandler);
            }
            if (null != expediente.getDatosDevoluciones()) {
                byte[] bt = IOUtils.toByteArray(expediente.getDatosDevoluciones());
                sqlLVDatosDevoluciones = new SqlLobValue(new ByteArrayInputStream(bt), bt.length, lobHandler);
            }
            if (null != expediente.getDatosDictamenes()) {
                byte[] bt = IOUtils.toByteArray(expediente.getDatosDIOT());
                sqlLVDatosDictamenes = new SqlLobValue(new ByteArrayInputStream(bt), bt.length, lobHandler);
            }
            if (null != expediente.getDatosDeclaraciones()) {
                byte[] bt = IOUtils.toByteArray(expediente.getDatosDeclaraciones());
                sqlLVDatosDeclaraciones = new SqlLobValue(new ByteArrayInputStream(bt), bt.length, lobHandler);
            }
            if (null != expediente.getDatosConsolidacion()) {
                byte[] bt = IOUtils.toByteArray(expediente.getDatosConsolidacion());
                sqlLVDatosConsolidacion = new SqlLobValue(new ByteArrayInputStream(bt), bt.length, lobHandler);
            }
            if (null != expediente.getDatosPagoDerechos()) {
                byte[] bt = IOUtils.toByteArray(expediente.getDatosPagoDerechos());
                sqlLVDatosPagoDerechos = new SqlLobValue(new ByteArrayInputStream(bt), bt.length, lobHandler);
            }
            if (null != expediente.getDatosPagoMultas()) {
                byte[] bt = IOUtils.toByteArray(expediente.getDatosPagoMultas());
                sqlLVDatosPagoMultas = new SqlLobValue(new ByteArrayInputStream(bt), bt.length, lobHandler);
            }
            if (null != expediente.getDatosDeterminaISR()) {
                byte[] bt = IOUtils.toByteArray(expediente.getDatosDeterminaISR());
                sqlLVDatosDeterminaISR = new SqlLobValue(new ByteArrayInputStream(bt), bt.length, lobHandler);
            }
            if (null != expediente.getDatosDeterminaIMP()) {
                byte[] bt = IOUtils.toByteArray(expediente.getDatosDeterminaIMP());
                sqlLVDatosDeterminaIMP = new SqlLobValue(new ByteArrayInputStream(bt), bt.length, lobHandler);
            }
            if (null != expediente.getDatosSaldoICEP()) {
                byte[] bt = IOUtils.toByteArray(expediente.getDatosSaldoICEP());
                sqlLVDatosSaldoICEP = new SqlLobValue(new ByteArrayInputStream(bt), bt.length, lobHandler);
            }
            if (null != expediente.getDatosRetenedorN()) {
                byte[] bt = IOUtils.toByteArray(expediente.getDatosRetenedorN());
                sqlLVDatosRetenedorN = new SqlLobValue(new ByteArrayInputStream(bt), bt.length, lobHandler);
            }
        } catch (IOException e) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage());
        }
        Object[] params =
            new Object[] { expediente.getServicioDTO().getNumControl(), expediente.getPerfilDeRiesgo(), sqlLVDatosRetenedorBanc,
                           sqlLVDatosCPR, sqlLVDatosDIOT, sqlLVDatosAltex, sqlLVDatosPagos, sqlLVDatosCompensacion,
                           sqlLVDatosPedimentos, sqlLVDatosDevoluciones,
                           sqlLVDatosDictamenes, sqlLVDatosDeclaraciones, sqlLVDatosConsolidacion,
                           sqlLVDatosPagoDerechos, sqlLVDatosPagoMultas, sqlLVDatosDeterminaISR,
                           sqlLVDatosDeterminaIMP, sqlLVDatosSaldoICEP, sqlLVDatosRetenedorN,
                           expediente.getSaldoIcep(), expediente.getSaldoRetenedorN(),
                           expediente.getManifiestaEdocta(), expediente.getProtesta(),
                           expediente.getInfoAgropecuario(), expediente.getAplicaFacilidad(),
                           expediente.getEstadoPadron(), expediente.getEstadoActual() };
        return params;
    }

    public int[] getTipoDatos() {

        int[] tipoDatos =
            new int[] { Types.VARCHAR, Types.VARCHAR, Types.CLOB, Types.CLOB, Types.CLOB, Types.CLOB,
                        Types.CLOB, Types.CLOB, Types.CLOB, Types.CLOB, Types.CLOB, Types.CLOB, Types.CLOB, Types.CLOB,
                        Types.CLOB, Types.CLOB, Types.CLOB, Types.CLOB, Types.CLOB, Types.DECIMAL, Types.DECIMAL,
                        Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER };

        return tipoDatos;
    }


    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }

    public void setStrWhere(String strWhere) {
        this.strWhere = strWhere;
    }

    public String getStrWhere() {
        return strWhere;
    }
}
