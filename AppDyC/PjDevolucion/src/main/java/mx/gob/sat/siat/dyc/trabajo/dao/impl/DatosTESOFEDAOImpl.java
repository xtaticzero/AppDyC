package mx.gob.sat.siat.dyc.trabajo.dao.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.tesofe.dto.DatosEMP;
import mx.gob.sat.siat.dyc.trabajo.dao.DatosTESOFEDAO;
import mx.gob.sat.siat.dyc.trabajo.dao.impl.mapper.DatosTESOFEDAOMapper;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author Jesus Alfredo Hernandez Orozco
 */
@Repository(value = "datosTESOFEDAO")
public class DatosTESOFEDAOImpl implements DatosTESOFEDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private static final String A_PATERNO = "     EXTRACTVALUE(e.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apPaterno')||' '||\n";
    private static final String DYCP_SOLICITUD = "from dycp_solicitud a \n";

    private static final String CONSULTA_PARTE1 = "select c.idInstCredito as banco, c.clabe, b.importeneto as importe, \n"
            + "     decode(EXTRACTVALUE(e.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/nombre')||' '||\n"
            + A_PATERNO
            + "     EXTRACTVALUE(e.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apMaterno'), '  ',\n"
            + "     EXTRACTVALUE(e.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/razonSocial'), \n"
            + "     EXTRACTVALUE(e.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/nombre')||' '||\n"
            + A_PATERNO
            + "     EXTRACTVALUE(e.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apMaterno')) as beneficiario, \n"
            + "     d.rfccontribuyente as RFC, h.claveComputo, d.idtipotramite, d.numControl, \n"
            + "     decode(i.idmotivorechazo, null, 1, 2) as rechazo \n"
            + DYCP_SOLICITUD
            + "inner join dyct_resolucion    b on (a.numControl  = b.numControl) \n"
            + "inner join dyct_cuentabanco   c on (a.numControl  = c.numControl)  \n"
            + "inner join dycp_servicio      d on (d.numControl  = a.numControl) \n"
            + "inner join dyct_contribuyente e on (a.numControl  = e.numControl) \n"
            + "inner join dycc_tipotramite   h on (d.idtipotramite = h.idtipotramite)  \n";

    private static final String CONSULTA_NUMCONTROL_TESOFE = "select a.numControl \n"
            + DYCP_SOLICITUD
            + "inner join dyct_resolucion    b on (a.numControl  = b.numControl) \n"
            + "inner join dyct_cuentabanco   c on (a.numControl  = c.numControl) \n"
            + "inner join dycp_servicio      d on (d.numControl  = a.numControl) \n"
            + "inner join dyct_contribuyente e on (a.numControl  = e.numControl) \n"
            + "inner join dycc_tipotramite   h on (d.idtipotramite = h.idtipotramite)  \n";

    private static final String CONSULTA_X_CVESRASTREO = "select c.idInstCredito as banco, c.clabe, b.importeneto as importe, \n"
            + "     decode(EXTRACTVALUE(e.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/nombre')||' '||\n"
            + A_PATERNO
            + "     EXTRACTVALUE(e.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apMaterno'), '  ',\n"
            + "     EXTRACTVALUE(e.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/razonSocial'), \n"
            + "     EXTRACTVALUE(e.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/nombre')||' '||\n"
            + A_PATERNO
            + "     EXTRACTVALUE(e.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apMaterno')) as beneficiario, \n"
            + "     d.rfccontribuyente as RFC, h.claveComputo, d.idtipotramite, d.numControl, \n"
            + "     decode(i.idmotivorechazo, null, 1, 2) as rechazo \n"
            + DYCP_SOLICITUD
            + "inner join dyct_resolucion    b on (a.numControl  = b.numControl) \n"
            + "inner join dyct_cuentabanco   c on (a.numControl  = c.numControl)  \n"
            + "inner join dycp_servicio      d on (d.numControl  = a.numControl) \n"
            + "inner join dyct_contribuyente e on (a.numControl  = e.numControl) \n"
            + "inner join dycc_tipotramite   h on (d.idtipotramite = h.idtipotramite)  \n"
            + "left  join dycp_solPago       i on (a.numcontrol  = i.numcontrol) \n"
            + "where B.CLAVERASTREO IN ";

    private static final String SELECT_MOTIVO_RECHAZO = "select decode(c.idmotivorechazo, null, 1, 2) as rechazo \n"
            + "from dycp_solicitud a \n"
            + "inner join dyct_resolucion b on (a.numControl  = b.numControl) \n"
            + "left  join dycp_solPago    c on (a.numcontrol  = c.numcontrol) \n"
            + "where B.CLAVERASTREO =?";

    private static final String SELECT_CONSECUTIVO_ARCHIVO = "SELECT DYCQ_ARCHIVOTESOFE.NEXTVAL FROM DUAL";

    private static final String UNION_SOLPAGOS_SIN_RECHAZO = " left  join dycp_solPago       i on (a.numcontrol  = i.numcontrol ) \nwhere i.idmotivorechazo is null and \n";

    private static final String UNION_SOLPAGOS_CON_RECHAZO = " inner join dycp_solPago       i on (a.numcontrol  = i.numcontrol and i.idmotivorechazo is not null)\nwhere \n";

    private static final String WHERE_ESTADOS_SIN_RECHAZO = " a.IDESTADOSOLICITUD IN (10, 11, 12, 19, 20, 22)  AND b.pagoEnviado=0 AND D.CLAVEADM ";

    private static final String WHERE_ESTADOS_CON_RECHAZO = " a.IDESTADOSOLICITUD IN (10, 11, 12, 19, 20, 22, 24)  AND b.pagoEnviado=0 AND D.CLAVEADM ";

    private static final String AND_CUENTA_VALIDA = " and c.cuentavalida=1 and b.importeneto>0 ";

    private static final String AND_TRAMITE_ISR = " and d.idtipotramite = 132";

    private static final String AND_TRAMITE_MANUAL = " and d.idtipotramite <> 132";

    private static final Logger LOGGER = Logger.getLogger(DatosTESOFEDAOImpl.class);

    /**
     * Lista los datos que se enviaran a la TESOFE para que posteriormente se
     * anexen a a un archivo.
     *
     * @param claves
     * @param rechazo
     * @param esAutomaticaISR
     * @return Lista de datos que se enviaran a la TESOFE .
     */
    @Override
    public List<DatosEMP> listarDatosTESOFE(String claves, Integer rechazo, Boolean esAutomaticaISR) throws SIATException {
        String finalQuery;

        String finalCondicion = (!esAutomaticaISR) ? AND_TRAMITE_MANUAL : AND_TRAMITE_ISR;

        List<DatosEMP> lista = null;
        try {
            if (rechazo == 1) {
                finalQuery = CONSULTA_PARTE1 + UNION_SOLPAGOS_SIN_RECHAZO + WHERE_ESTADOS_SIN_RECHAZO + claves + AND_CUENTA_VALIDA + finalCondicion;
            } else {
                finalQuery = CONSULTA_PARTE1 + UNION_SOLPAGOS_CON_RECHAZO + WHERE_ESTADOS_CON_RECHAZO + claves + AND_CUENTA_VALIDA + finalCondicion;
            }

            lista = jdbcTemplateDYC.query(finalQuery, new DatosTESOFEDAOMapper());

        } catch (Exception e) {
            LOGGER.error("listarDatosTESOFE(); causa=" + e.getCause());
            throw new SIATException(e);
        }

        return lista;
    }

    /**
     * Obtiene el numero de emision
     *
     * @return Obtiene el numero consecutivo con el numero de emision
     * @throws SIATException
     */
    @Override
    public Integer obtenerSecuencia() throws SIATException {
        Integer secuencia = 0;
        try {
            secuencia = jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTA_SECUENCIATESOFE.toString(), Integer.class);

        } catch (DataAccessException dae) {
            LOGGER.error("obtenerSecuencia(); causa=" + dae.getCause());
            throw new SIATException(dae);
        }
        return secuencia;
    }

    /**
     * Cuenta el total de datos que va a traer el registro de TESOFE.
     *
     * @param claves
     * @param rechazo
     * @return
     */
    @Override
    public List<DatosEMP> contarListaDatosTESOFE(String claves, Integer rechazo, Boolean esAutomaticaISR) throws SIATException {
        String finalQuery;

        String finalCondicion = (!esAutomaticaISR) ? AND_TRAMITE_MANUAL : AND_TRAMITE_ISR;
        List<DatosEMP> lstDatos = null;

        if (rechazo == 1) {
            finalQuery = CONSULTA_NUMCONTROL_TESOFE + UNION_SOLPAGOS_SIN_RECHAZO + WHERE_ESTADOS_SIN_RECHAZO + claves + AND_CUENTA_VALIDA + finalCondicion;
        } else {
            finalQuery = CONSULTA_NUMCONTROL_TESOFE + UNION_SOLPAGOS_CON_RECHAZO + WHERE_ESTADOS_CON_RECHAZO + claves + AND_CUENTA_VALIDA + finalCondicion;
        }
        try {
            lstDatos = jdbcTemplateDYC.query(finalQuery, new DatosTESOFEDAOMapper());

        } catch (Exception e) {
            LOGGER.error("contarListaDatosTESOFE(); causa=" + e.getCause());
            throw new SIATException(e);
        }
        return lstDatos;
    }

    /**
     * Lista los datos que se van a utilizar para el envio de archivos para
     * TESOFE utilizando las claves de rastreo de los archivos generados
     * anteriormente.
     *
     * @param clavesDeRastreo
     * @return
     */
    @Override
    public List<DatosEMP> listarDatosTESOFEConClaveDeRastreo(String clavesDeRastreo) {
        List<DatosEMP> lista = null;
        try {
            lista = jdbcTemplateDYC.query(CONSULTA_X_CVESRASTREO + clavesDeRastreo, new DatosTESOFEDAOMapper());

        } catch (Exception e) {
            LOGGER.error("listarDatosTESOFE(); causa=" + e.getCause());
        }
        return lista;
    }

    /**
     * Regresa un identificador, el cual dice si las claves de rastreo vienen a
     * partir de un rechazo o no.
     *
     * @param claveRastreo clave de rastreo a buscar
     * @return 1 si viene a partir de un rechazo, 2 en caso contrario.
     * @throws SIATException
     */
    @Override
    public Integer obtenerMotivoRechazo(String claveRastreo) throws SIATException {
        Integer motivoRechazo = 1;
        try {
            motivoRechazo = jdbcTemplateDYC.queryForObject(SELECT_MOTIVO_RECHAZO, new Object[]{claveRastreo}, Integer.class);
        } catch (Exception e) {
            LOGGER.info("obtenerMotivoRechazo(); causa=" + e.getCause());
        }
        return motivoRechazo;
    }

    /**
     * Obtiene el numero de documento que se enviará a Tesorería Federal.
     *
     * @return el numero de documento que se enviará a Tesorería Federal.
     * @throws SIATException
     */
    @Override
    public Integer obtenerNumerDeArchivo() throws SIATException {
        Integer consecutivo = 0;
        try {
            consecutivo = jdbcTemplateDYC.queryForObject(SELECT_CONSECUTIVO_ARCHIVO, Integer.class);
        } catch (Exception e) {
            LOGGER.info("obtenerMotivoRechazo(); causa=" + e.getCause());
        }
        return consecutivo;
    }
}
