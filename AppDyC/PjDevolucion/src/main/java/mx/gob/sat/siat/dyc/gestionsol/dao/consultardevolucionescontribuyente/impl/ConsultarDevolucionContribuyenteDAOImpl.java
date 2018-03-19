/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.gestionsol.dao.consultardevolucionescontribuyente.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.gestionsol.dao.consultardevolucionescontribuyente.ConsultarDevolucionContribuyenteDAO;
import mx.gob.sat.siat.dyc.gestionsol.dao.consultardevolucionescontribuyente.impl.mapper.ConsultarDevolucionContribuyenteMapper;
import mx.gob.sat.siat.dyc.gestionsol.dto.consultardevolucionescontribuyente.ConsultarDevolucionesContribuyenteDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 *
 * @author  Alfredo Ramirez
 * @since   08/10/2013
 *
 */
@Repository(value = "consultarDevolucionContribuyenteDAO")
public class ConsultarDevolucionContribuyenteDAOImpl implements ConsultarDevolucionContribuyenteDAO{

    private static final String CONSULTAR_COMPENSACION_A_SOLVENTAR="SELECT A.NUMCONTROL, d.descripcion AS tramiteDescripcion, G.DESCRIPCION AS impuestoDescripcion, \n" + 
    "       F.descripcion AS conceptoDescripcion, H.descripcion AS periodoDescripcion, \n" + 
    "       E.IDEJERCICIO, A.FECHAPRESENTACION, B.DESCRIPCION AS estadoDescripcion, A.IDESTADOCOMP as idestadosolicitud, \n" + 
    "       E.RFC, c.CLAVEADM, i.numcontroldoc, i.idestadodoc, i.idestadoreq, a.importeCompensado as importe \n" + 
    "FROM DYCP_COMPENSACION A \n" + 
    "INNER JOIN DYCC_ESTADOCOMP  B ON (A.IDESTADOCOMP=B.IDESTADOCOMP)\n" + 
    "inner join dycp_servicio    c on (a.numcontrol=c.numcontrol)\n" + 
    "inner join dycc_tipoTramite d on (c.idtipotramite=d.idtipotramite)\n" + 
    "INNER JOIN DYCT_SALDOICEP   E ON (E.IDsaldoicep=A.IDSALDOICEPORIGEN)\n" + 
    "INNER JOIN DYCC_CONCEPTO    F ON (F.IDCONCEPTO=E.IDCONCEPTO)\n" + 
    "INNER JOIN DYCC_IMPUESTO    G ON (G.IDIMPUESTO=F.IDIMPUESTO)\n" + 
    "INNER JOIN DYCC_PERIODO     H ON (H.IDPERIODO=E.IDPERIODO)\n" + 
    "INNER join dyct_documento   I ON (i.numControl = a.numControl AND IDESTADODOC=2 AND (IDPLANTILLA=74 OR IDPLANTILLA=79 OR IDPLANTILLA=134))\n" + 
    "WHERE A.IDESTADOCOMP = 6 AND I.NUMCONTROL=? and e.rfc=?";
    
    private static final String CONSULTADYP_OBTENERSOLICITUDESPORCONTRIBUYENTE_PART1 =
    "select distinct a.numControl, c.descripcion AS tramiteDescripcion, F.DESCRIPCION AS impuestoDescripcion, \n" + 
    "       e.descripcion AS conceptoDescripcion, G.descripcion AS periodoDescripcion, \n" + 
    "       D.IDEJERCICIO,     A.FECHAPRESENTACION, H.descripcion AS estadoDescripcion, A.idestadosolicitud,\n" + 
    "       D.rfc, B.CLAVEADM, null as numcontroldoc, null as idestadodoc, null as idestadoreq, a.importesolicitado as importe\n" + 
    "from dycp_solicitud a \n" + 
    "inner join dycp_servicio       b on (a.numControl = b.numControl) \n" + 
    "inner join dycc_tipoTramite    c on (c.idTipoTramite = b.idTipoTramite)\n" + 
    "INNER JOIN DYCT_SALDOICEP      D ON (D.IDSALDOICEP = A.IDSALDOICEP)\n" + 
    "INNER JOIN DYCC_CONCEPTO       E ON (E.IDCONCEPTO = D.IDCONCEPTO)\n" + 
    "INNER JOIN dycc_impuesto       F ON (F.IDIMPUESTO = E.IDIMPUESTO)\n" + 
    "INNER JOIN DYCC_PERIODO        G ON (G.IDPERIODO = D.IDPERIODO)\n" + 
    "INNER JOIN dycc_estadosol      H ON (H.idestadosolicitud = A.idestadosolicitud) \n" + 
    "LEFT OUTER JOIN DYCT_DOCUMENTO i ON (a.NUMCONTROL=i.NUMCONTROL and i.idplantilla not in (3, 4, 75, 76 , 77, 81) or i.idplantilla is null)\n" + 
    "WHERE A.IDESTADOSOLICITUD in (";
    
    private static final String CONSULTADYP_OBTENERSOLICITUDESPORCONTRIBUYENTE_PART2 =") \nAND b.RFCcontribuyente = ?  and ((sysdate-a.fechainicioTramite)<=1825)";
    private static final String OBTENER_SOLICITUDES_DEV_AUT_IVA_EJERCICIO =" \nAND b.RFCcontribuyente = ? and b.idTipoTramite = 139  and D.IDEJERCICIO = ?";
    private static final String OBTENER_SOLICITUDES_SIN_DEV_AUT_IVA_EJERCICIO =" \nAND b.RFCcontribuyente = ? and b.idTipoTramite NOT IN (132, 139)  and D.IDEJERCICIO = ?";

    private static final String CONSULTA_SOLICITUDES_CONTRIBUYENTE
            = "select distinct a.numControl, c.descripcion AS tramiteDescripcion, F.DESCRIPCION AS impuestoDescripcion, \n"
            + "       e.descripcion AS conceptoDescripcion, G.descripcion AS periodoDescripcion, \n"
            + "       D.IDEJERCICIO,     A.FECHAPRESENTACION, H.descripcion AS estadoDescripcion, A.idestadosolicitud,\n"
            + "       D.rfc, B.CLAVEADM, null as numcontroldoc, null as idestadodoc, null as idestadoreq, a.importesolicitado as importe,\n"
            + "       R.fechapago as fechapago, R.importeneto as importepagado \n"
            + "from dycp_solicitud a \n"
            + "inner join dycp_servicio       b on (a.numControl = b.numControl) \n"
            + "inner join dycc_tipoTramite    c on (c.idTipoTramite = b.idTipoTramite)\n"
            + "INNER JOIN DYCT_SALDOICEP      D ON (D.IDSALDOICEP = A.IDSALDOICEP)\n"
            + "INNER JOIN DYCC_CONCEPTO       E ON (E.IDCONCEPTO = D.IDCONCEPTO)\n"
            + "INNER JOIN dycc_impuesto       F ON (F.IDIMPUESTO = E.IDIMPUESTO)\n"
            + "INNER JOIN DYCC_PERIODO        G ON (G.IDPERIODO = D.IDPERIODO)\n"
            + "INNER JOIN dycc_estadosol      H ON (H.idestadosolicitud = A.idestadosolicitud) \n"
            + "LEFT JOIN DYCT_RESOLUCION     R ON (R.NUMCONTROL = a.NUMCONTROL) \n"
            + "LEFT OUTER JOIN DYCT_DOCUMENTO i ON (a.NUMCONTROL=i.NUMCONTROL and i.idplantilla not in (3, 4, 75, 76 , 77, 81) or i.idplantilla is null)\n"
            + "WHERE A.IDESTADOSOLICITUD = ";
    
    private static final String TEXTO_RFC = " rfc: ";

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger logger = Logger.getLogger(ConsultarDevolucionContribuyenteDAOImpl.class);

    public ConsultarDevolucionContribuyenteDAOImpl() {
        super();
    }

    /**
     * Se listan todas las solicitudes que se pueden solventar a partir del status seleccionado y del rfc del 
     * contribuyente que ingresa.
     *
     * @param rfc
     * @param status
     * @return
     */
    @Override
    public List<ConsultarDevolucionesContribuyenteDTO> listaSolicitudesPorContribuyente(String rfc, String status) {
        List<ConsultarDevolucionesContribuyenteDTO> listaSolCon =
            new ArrayList<ConsultarDevolucionesContribuyenteDTO>();
        String query=CONSULTADYP_OBTENERSOLICITUDESPORCONTRIBUYENTE_PART1 + status + 
                     CONSULTADYP_OBTENERSOLICITUDESPORCONTRIBUYENTE_PART2;
        logger.info("query: "+query);
        try {
            listaSolCon =
                    this.jdbcTemplateDYC.query(query, new Object[] { rfc },
                                               new ConsultarDevolucionContribuyenteMapper());
        } catch (Exception dae) {
            logger.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    query + ConstantesDyC1.TEXTO_3_ERROR_DAO + rfc);
        }
        return listaSolCon;
    }

    /**
     * Se listan todas las solicitudes que se pueden solventar a partir del status seleccionado y del rfc del 
     * contribuyente que ingresa.
     *
     * @param rfc, etatus, ejercicio
     * @param status
     * @return
     */
    @Override
    public List<ConsultarDevolucionesContribuyenteDTO> getSolicitudesContribuyenteDevAutIVA(String rfc, String status, String ejercicio) {
        List<ConsultarDevolucionesContribuyenteDTO> listaSolCon =
            new ArrayList<ConsultarDevolucionesContribuyenteDTO>();
        String query=CONSULTA_SOLICITUDES_CONTRIBUYENTE + status + 
                     OBTENER_SOLICITUDES_DEV_AUT_IVA_EJERCICIO;
        logger.info("query: "+query);
        try {
            listaSolCon =
                    this.jdbcTemplateDYC.query(query, new Object[] { rfc, ejercicio },
                                               new ConsultarDevolucionContribuyenteMapper());
        } catch (Exception dae) {
            logger.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    query + ConstantesDyC1.TEXTO_3_ERROR_DAO + rfc);
        }
        return listaSolCon;
    }
    
    /**
     * Se listan todas las solicitudes que se pueden solventar a partir del status seleccionado y del rfc del 
     * contribuyente que ingresa.
     *
     * @param rfc, etatus, ejercicio
     * @param status
     * @return
     */
    @Override
    public List<ConsultarDevolucionesContribuyenteDTO> getSolicitudesContribuyenteSinDevAutIVA(String rfc, String status, String ejercicio) {
        List<ConsultarDevolucionesContribuyenteDTO> listaSolCon =
            new ArrayList<ConsultarDevolucionesContribuyenteDTO>();
        String query=CONSULTA_SOLICITUDES_CONTRIBUYENTE + status + 
                     OBTENER_SOLICITUDES_SIN_DEV_AUT_IVA_EJERCICIO;

        try {
            listaSolCon =
                    this.jdbcTemplateDYC.query(query, new Object[] { rfc, ejercicio },
                                               new ConsultarDevolucionContribuyenteMapper());
        } catch (Exception dae) {
            logger.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    query + ConstantesDyC1.TEXTO_3_ERROR_DAO + rfc);
        }
        return listaSolCon;
    }
    
    /**
     * Se listan todas las compensaciones que se pueden solventar a partir del status seleccionado y del rfc del 
     * contribuyente que ingresa.
     *
     * @param rfc
     * @return
     */
    @Override
    public List<ConsultarDevolucionesContribuyenteDTO> listaCompensacionesPorContribuyente(String rfc) throws SIATException {
        List<ConsultarDevolucionesContribuyenteDTO> listaSolCon =
            new ArrayList<ConsultarDevolucionesContribuyenteDTO>();
        try {
            listaSolCon =
                    this.jdbcTemplateDYC.query(SQLOracleDyC.CONSULTADYP_OBTENERCOMPENSACIONESPORCONTRIBUYENTE.toString(), new Object[] { rfc },
                                               new ConsultarDevolucionContribuyenteMapper());
        } catch (Exception dae) {
            logger.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleDyC.CONSULTADYP_OBTENERCOMPENSACIONESPORCONTRIBUYENTE.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                    rfc);
        }
        return listaSolCon;
    }

/**
     * Se listan todas las compensaciones que se pueden solventar a partir del status seleccionado y del rfc del 
     * contribuyente que ingresa.
     *
     * @param rfc
     * @return
     */
    @Override
    public List<ConsultarDevolucionesContribuyenteDTO> getCompensacionesContribuyente(String rfc, String status, String ejercicio) throws SIATException {
        List<ConsultarDevolucionesContribuyenteDTO> listaSolCon =
            new ArrayList<ConsultarDevolucionesContribuyenteDTO>();
        try {
            listaSolCon =
                    this.jdbcTemplateDYC.query(SQLOracleDyC.OBTENERCOMPENSACIONESPORCONTRIBUYENTE_EJERCICIO.toString(), new Object[] { rfc, status, Integer.parseInt(ejercicio) },
                                               new ConsultarDevolucionContribuyenteMapper());
        } catch (Exception dae) {
            logger.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleDyC.OBTENERCOMPENSACIONESPORCONTRIBUYENTE_EJERCICIO.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                    rfc);
        }
        return listaSolCon;
    }

    /**
     * Conulta los datos de la solicitud que se va a solventar que ha sido seleccionada en pantalla.
     *
     * @param numControl
     * @return
     */
    @Override
    public ConsultarDevolucionesContribuyenteDTO obtenerDocumento(String numControl) {
        ConsultarDevolucionesContribuyenteDTO objeto = null;
        try {
            objeto = jdbcTemplateDYC.queryForObject(SQLOracleDyC.OBTENER_SOLICITUD_A_SOLVENTAR.toString(), new Object[] { numControl, numControl },
                                                   new ConsultarDevolucionContribuyenteMapper());
        } catch (Exception e) {
            logger.warn(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleDyC.OBTENER_SOLICITUD_A_SOLVENTAR.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                    " numControl: " + numControl);
        }
        return objeto;
    }

    @Override
    public ConsultarDevolucionesContribuyenteDTO obtenercompensacion(String numControl, String rfc) {
        ConsultarDevolucionesContribuyenteDTO objeto = null;
        try {
            objeto = jdbcTemplateDYC.queryForObject(CONSULTAR_COMPENSACION_A_SOLVENTAR, new Object[] { numControl, rfc },
                                                   new ConsultarDevolucionContribuyenteMapper());
        } catch (Exception e) {
            logger.warn(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    CONSULTAR_COMPENSACION_A_SOLVENTAR + ConstantesDyC1.TEXTO_3_ERROR_DAO + " numControl: " + numControl+ ", rfc: "+rfc);
        }
        return objeto;
    }

    /**
     * Lista 
     *
     * @param rfc
     * @return
     * @throws SIATException
     */
    @Override
    public List<Integer> listarStatusDeDevoluciones(String rfc) throws SIATException {
        List<Integer> listaDeStatus = null;
        
        try {
            listaDeStatus = jdbcTemplateDYC.queryForList(SQLOracleDyC.CONSULTAR_STATUS_SOLICITUDES_PORCONTRIBUYENTE.toString(), new Object[]{rfc,rfc}, Integer.class);
        } catch (Exception e) {
            logger.warn(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleDyC.CONSULTAR_STATUS_SOLICITUDES_PORCONTRIBUYENTE.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                    TEXTO_RFC + rfc);
        }
        return listaDeStatus;
    }

    /**
     * Lista 
     *
     * @param rfc, ejercicio
     * @return
     * @throws SIATException
     */
    @Override
    public List<Integer> listarStatusDeDevoluciones(final String rfc, final String ejercicio) throws SIATException {
        List<Integer> listaDeStatus = null;
        
        try {
            listaDeStatus = jdbcTemplateDYC.queryForList(SQLOracleDyC.CONSULTAR_STATUS_SOLICITUDES_PORCONTRIBUYENTE_EJERCICIO.toString(), new Object[]{rfc,Integer.parseInt(ejercicio),rfc,Integer.parseInt(ejercicio)}, Integer.class);
        } catch (Exception e) {
            logger.warn(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleDyC.CONSULTAR_STATUS_SOLICITUDES_PORCONTRIBUYENTE_EJERCICIO.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                    TEXTO_RFC + rfc);
        }
        return listaDeStatus;
    }
    
    /**
     * Lista 
     *
     * @param rfc, fechaIni, fechaFin
     * @return
     * @throws SIATException
     */
    @Override
    public List<Integer> listarStatusDeDevolucionesAutIVA(final String rfc, final String ejercicio) throws SIATException {
        List<Integer> listaDeStatus = null;
        
        try {
            listaDeStatus = jdbcTemplateDYC.queryForList(SQLOracleDyC.SOLICITUDES_AUT_IVA_PORCONTRIBUYENTE_EJERCICIO.toString(), new Object[]{rfc,rfc,Integer.parseInt(ejercicio)}, Integer.class);
        } catch (Exception e) {
            logger.warn(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleDyC.SOLICITUDES_AUT_IVA_PORCONTRIBUYENTE_EJERCICIO.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                    TEXTO_RFC + rfc);
        }
        return listaDeStatus;
    }
    
    /**
     * Consulta si tiene una compensacion asociada a su lista de documentos a solventar.
     *
     * @param rfc
     * @return
     * @throws SIATException
     */
    @Override
    public Integer consultarSiTieneCompensacion(String rfc) throws SIATException {
        Integer idEstadoComp = null;
        try {
            idEstadoComp = jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTAR_STATUS_COMPENACIONES_PORCONTRIBUYENTE.toString(), 
                                                          new Object[] { rfc },
                                                          Integer.class);
        } catch (Exception e) {
            logger.warn(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleDyC.CONSULTAR_STATUS_COMPENACIONES_PORCONTRIBUYENTE.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                    TEXTO_RFC + rfc);
        }
        
        return idEstadoComp;
    }
    
    @Override
    public  List<Integer> consultarSiTieneCompensacionCC(final String rfc, final Integer idEjercicio) throws SIATException {
       List<Integer> listaDeStatus = null;
        try {
            listaDeStatus = jdbcTemplateDYC.queryForList(SQLOracleDyC.CONSULTAR_STATUS_COMPENACIONES_POR_CONTRIBUYENTE.toString(), 
                                                          new Object[] { rfc, idEjercicio },
                                                          Integer.class);
        } catch (Exception e) {
            logger.warn(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleDyC.CONSULTAR_STATUS_COMPENACIONES_POR_CONTRIBUYENTE.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                    TEXTO_RFC + rfc);
        }
        
        return listaDeStatus;
    }
    
  

    @Override
    public List<Integer> listaSolicitudesPorContribuyente(String rfc) throws SIATException {
        List<Integer> listaDeStatus = null;
        
        try {
            listaDeStatus = jdbcTemplateDYC.queryForList(SQLOracleDyC.CONSULTAR_STATUS_SOLICITUDES_POR_CONTRIBUYENTE.toString(), new Object[]{rfc,rfc}, Integer.class);
        } catch (Exception e) {
            logger.warn(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleDyC.CONSULTAR_STATUS_SOLICITUDES_POR_CONTRIBUYENTE.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                    TEXTO_RFC + rfc);
        }
        return listaDeStatus;
    }
    
    
    
}
