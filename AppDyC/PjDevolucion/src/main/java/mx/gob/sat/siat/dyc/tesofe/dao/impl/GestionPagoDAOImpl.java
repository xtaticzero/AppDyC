package mx.gob.sat.siat.dyc.tesofe.dao.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.tesofe.dao.GestionPagoDAO;
import mx.gob.sat.siat.dyc.tesofe.dao.impl.mapper.GestionPagoMapper;
import mx.gob.sat.siat.dyc.tesofe.dto.DatosRetroTESOFE;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Esta clase se utiliza para consultar la información del reporte de diario que
 * se genera por la retroalimentación de la TESOFE.
 *
 * @author Jesus Alfredo Hernandez Orozco
 */
@Repository(value = "gestionPagoDAO")
public class GestionPagoDAOImpl implements GestionPagoDAO {

    public GestionPagoDAOImpl() {
        super();
    }
    private static final Logger LOGGER = Logger.getLogger(GestionPagoDAOImpl.class);

    private static final String QUERY_CONSULTA = "select C.IDEJERCICIO, to_char(A.FECHAPAGO,'YYYY') AS ANIO, to_char(A.FECHAPAGO,'MM') AS MES, E.NOMabreviado as nombre,  \n"
            + "extractValue(i.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/claveAdmonLocal') as CLAVEADM_ORIGEN,  to_char(A.FECHAPRESENTACION, 'YYYY') AS ANIO_E,   \n"
            + "A.IMPORTENETO, D.IDTIPOTRAMITE, l.descripcion as clasificacion,  \n"
            + "F.DESCRIPCION as claveDescripcion, F.CLAVECOMPUTO, j.IDORIGENSALDO, m.PERIODOINICIOFIN, m.FECHAINICIO, A.FECHAEMISION AS fechaEnvioATESOFE,  \n"
            + "DECODE(G.IDESTADOPAGO, 2, 20, 4) as STATUS_E, h.descripcion as statusDescripcion,   \n"
            + "A.FECHAAPROBACION AS FECHAEMISION, decode(h.idestadopago, 2, fechaabono, null) as fechaCancelacion,    \n"
            + "decode(h.idestadopago, 1, fechaabono, null) as fechaAbono,    \n"
            + "decode(rolpf, 0, 'M', 'F') as tipoPersona,    \n"
            + "n.CUENTAVALIDA, G.IDMOTIVORECHAZO             \n"
            + "from dyct_resolucion A    \n"
            + "INNER JOIN DYCP_SOLICITUD     B ON (A.NUMCONTROL=B.NUMCONTROL)    \n"
            + "INNER JOIN dyct_saldoicep     C ON (B.IDSALDOICEP=C.IDSALDOICEP)  \n"
            + "INNER JOIN DYCP_SERVICIO      D ON (A.NUMCONTROL=D.NUMCONTROL)    \n"
            + "INNER JOIN DYCC_UNIDADADMVA   e ON (D.CLAVEADM=e.CLAVE_SIR AND e.IDUNIDADMVATIPO=13)   \n"
            + "INNER JOIN DYCC_TIPOTRAMITE   F ON (F.IDTIPOTRAMITE=D.IDTIPOTRAMITE)   \n"
            + "INNER JOIN DYCP_SOLPAGO       G ON (A.NUMCONTROL=G.NUMCONTROL)    \n"
            + "inner join dycc_estadopago    h on (h.idestadopago = g.idestadopago)   \n"
            + "inner join dyct_contribuyente i on (a.numcontrol=i.numcontrol)    \n"
            + "inner join DYCt_saldoicep     j on (j.idsaldoicep = b.idsaldoicep)  \n"
            + "inner join dycc_periodo       k on (j.idperiodo=k.idperiodo)      \n"
            + "inner join dycc_concepto      l on (l.idconcepto=f.idconcepto)    \n"
            + "inner join DYCC_PERIODO       m on (m.idperiodo = j.idperiodo)    \n"
            + "inner join dyct_cuentabanco   n on (n.NUMCONTROL = A.NUMCONTROL)  \n"
            + "where claverastreo is not null \n"
            + "and b.fechafintramite is not null\n"
            + "and b.idEstadoSolicitud = 13\n"
            + "and fechapago between to_date(?, 'YYYY/MM/DD') and to_date(?, 'YYYY/MM/DD')";

    private static final String CONDICION_DEV_MANUAL = " and F.IDTIPOTRAMITE <> 132";
    private static final String CONDICION_AUTO_ISR = " and F.IDTIPOTRAMITE = 132";

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    /**
     * Consulta la información que va a ser mostrada en el reporte de Excel, la
     * cual incluye los datos de diario que se generan a partir de la
     * retroalimentación de TESOFE.
     *
     * @param fecha1 Dia sobre el cual se realiza la consulta.
     * @param fecha2 Dia +1 sobre el cual se realiza la consulta.
     * @param esAutoISR identifica si se trata de tramites de ISR PF Automaticas
     * @return Lista con los datos del reporte.
     * @throws SIATException
     */
    @Override
    public List<DatosRetroTESOFE> listarDatosParaReporteDeRetroDeTESOFE(String fecha1, String fecha2, Boolean esAutoISR) throws SIATException {
        List<DatosRetroTESOFE> lista = null;
        String condcionFinal = (!esAutoISR) ? CONDICION_DEV_MANUAL : CONDICION_AUTO_ISR;
        try {
            lista = jdbcTemplateDYC.query(QUERY_CONSULTA + condcionFinal, new Object[]{fecha1, fecha2}, new GestionPagoMapper());
        } catch (Exception dae) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + QUERY_CONSULTA + ConstantesDyC1.TEXTO_3_ERROR_DAO + "fecha1: " + fecha1 + ", fecha2: " + fecha2);
            throw new SIATException(dae);
        }
        return lista;
    }
}
