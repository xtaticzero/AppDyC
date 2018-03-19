package mx.gob.sat.siat.dyc.dao.resolucion.impl;

import java.sql.Types;

import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.resolucion.DyctResolucionDAO;
import mx.gob.sat.siat.dyc.dao.resolucion.impl.mapper.DyctResolucionMapper;
import mx.gob.sat.siat.dyc.dao.util.impl.mapper.EmitirResolucionMapper;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctResolucionDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;
import mx.gob.sat.siat.dyc.vo.EmitirResolucionVO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository(value = "dyctResolucionDAO")
public class DyctResolucionDAOImpl implements DyctResolucionDAO {

    private static final String NUMEROCONTROL = " Numero de control ";
    private static final String SELECT_TABLA  = "SELECT * FROM DYCT_RESOLUCION WHERE NUMCONTROL = ?";
    private static final String ACUTALIZAR_FECHAS_ENVIO = "UPDATE DYCT_RESOLUCION SET FECHAEMISION = SYSDATE, FECHAPRESENTACION = ?, FECHAPAGO = ? WHERE NUMCONTROL=?";
    private static final String SELECT_X_CVEYADMIN = "SELECT * FROM DYCT_RESOLUCION WHERE CLAVERASTREO = ? AND SUBSTR(NUMCONTROL,3,2) ";
    
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(DyctResolucionDAOImpl.class);


    /**
     * Obtiene los datos de la resolucion y los deposita en un objeto DyctResolucionDTO a partir
     * del numero de control.
     *
     * @param numControl
     * @return
     * @throws SIATException
     */
    @Override
    public DyctResolucionDTO obtener(String numControl) throws SIATException {
        DyctResolucionDTO dyctResolucionDTO = null;
        try {
            dyctResolucionDTO =
                    jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTA_RESOLUCION.toString(), new Object[] { numControl },
                                                   new DyctResolucionMapper());

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTA_RESOLUCION + ConstantesDyC1.TEXTO_3_ERROR_DAO + "numControl: " +
                      numControl);
        }
        return dyctResolucionDTO;
    }


    @Override
    public void actualizarEstResol(Integer idEstResol, String numControl) {
        try {
            jdbcTemplateDYC.update("UPDATE DYCT_RESOLUCION SET IDESTRESOL = ? WHERE NUMCONTROL = ?",
                                   new Object[] { idEstResol, numControl });


        } catch (DataAccessException dae) {
            log.info(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                     "UPDATE DYCT_RESOLUCION SET IDESTRESOL = ? WHERE NUMCONTROL = ?" +
                     ConstantesDyC1.TEXTO_3_ERROR_DAO + "IDESTRESOL= " + idEstResol + " y NUMCONTROL= " + numControl);
        }
    }

    @Override
    public Integer existeResolucionAprobadaEnAprobacion(String numControl) throws SIATException {

        Integer resolucion = 0;

        try {

            resolucion =
                    jdbcTemplateDYC.queryForObject(SQLOracleDyC.EMITIRRESOLUCION_EXISTERESOLUCION.toString(), new Object[] { numControl },
                                                   Integer.class);

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.EMITIRRESOLUCION_EXISTERESOLUCION + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      " numControl: " + numControl);
            throw new SIATException(dae);
        }

        return resolucion;

    }

    @Override
    public Integer existeResolucionNoAprobada(String numControl) throws SIATException{

        Integer resolucion = 0;

        try {

            resolucion =
                    jdbcTemplateDYC.queryForObject(SQLOracleDyC.EMITIRRESOLUCION_EXISTERESOLUCIONNOAPROBADA.toString(), new Object[] { numControl },
                                                   Integer.class);

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.EMITIRRESOLUCION_EXISTERESOLUCIONNOAPROBADA + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      " numControl: " + numControl);
            throw new SIATException(dae);
        }

        return resolucion;

    }

    @Override
    public Integer existeResolucion(String numControl) throws SIATException{

        Integer resolucion = 0;

        try {

            resolucion =
                    jdbcTemplateDYC.queryForObject(SQLOracleDyC.EMITIRRESOLUCION_EXISTERESOLUCIONESTATUS.toString(), new Object[] { numControl },
                                                   Integer.class);

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.EMITIRRESOLUCION_EXISTERESOLUCIONESTATUS +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + " numControl: " + numControl);
            throw new SIATException(dae);
        }

        return resolucion;

    }

    public boolean existe(String numControl) {

        Integer countReg = 0;
        boolean existe = false;

        try {

            countReg =
                    jdbcTemplateDYC.queryForObject(SQLOracleDyC.EXISTE_RESOLUCION.toString(), new Object[] { numControl }, Integer.class);

        } catch (DataAccessException dae) {
            log.error(dae.toString());
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.EXISTE_RESOLUCION + ConstantesDyC1.TEXTO_3_ERROR_DAO + "numControl: " + numControl);
            throw dae;
        }


        if (countReg > 0) {
            existe = Boolean.TRUE;
        }

        return existe;
    }


    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    /**
     * Obtiene los datos de la resolucion mediante el numero de control
     * y lo deposita en el objeto DyctResolucionDTO
     *
     * @param numControl numero de control.
     * @return ojeto DyctResolucionDTO que contiene todos los datos de la resolucion.
     */
    @Override
    public DyctResolucionDTO consultarDatosResolucionXNumeroControl(String numControl) throws SIATException {
        DyctResolucionDTO objetoResolucion = null;
        try {
            objetoResolucion =
                    jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTA_RESOLUCIONXNOCONTROL.toString(), new Object[] { numControl },
                                                   new DyctResolucionMapper());
        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTA_RESOLUCIONXNOCONTROL + ConstantesDyC1.TEXTO_3_ERROR_DAO + " numControl " +
                      numControl);
            throw new SIATException(dae);

        }
        return objetoResolucion;
    }


    public void actualizarSaldoAFavor(String numControl, double saldoAFavorAntRes,
                                      double saldoAFavorDesRes) throws SIATException {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.DYCT_RESOLUCION_UPDATE_SALDOAFAVOR.toString(),
                                   new Object[] { saldoAFavorAntRes, saldoAFavorDesRes, numControl });


        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.DYCT_RESOLUCION_UPDATE_SALDOAFAVOR + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      NUMEROCONTROL + numControl + " saldoAFavorAntRes " + saldoAFavorAntRes + " saldoAFavorDesRes " +
                      saldoAFavorDesRes);
            throw new SIATException(dae);
        }


    }

    @Override
    public List<EmitirResolucionVO> buscarInformacionRequerida(String numControl, int tipoDocumento) {

        try {

            List<EmitirResolucionVO> lInfo =
                jdbcTemplateDYC.query(SQLOracleDyC.EMITIRRESOLUCION_BUSCARINFORMACIONREQUERIDA.toString(),
                                      new Object[] { numControl, tipoDocumento }, new EmitirResolucionMapper());

            return lInfo;

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.EMITIRRESOLUCION_BUSCARINFORMACIONREQUERIDA + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      NUMEROCONTROL + numControl + " Tipo de documento " + tipoDocumento);
            throw dae;
        }

    }

    @Override
    public List<EmitirResolucionVO> buscarAnexosRequerir(String numControl, int tipoDocumento) {

        try {

            List<EmitirResolucionVO> lAnexos =
                jdbcTemplateDYC.query(SQLOracleDyC.EMITIRRESOLUCION_BUSCARANEXOSREQUERIR.toString(),
                                      new Object[] { numControl, tipoDocumento }, new EmitirResolucionMapper());

            return lAnexos;

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.EMITIRRESOLUCION_BUSCARANEXOSREQUERIR + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      NUMEROCONTROL + numControl + " Tipo de documento " + tipoDocumento);
            throw dae;
        }
    }

    @Override
    public List<EmitirResolucionVO> buscarOtraInfoRequerir(String numControl, int tipoDocumento) {

        try {

            List<EmitirResolucionVO> lOtraInfo =
                jdbcTemplateDYC.query(SQLOracleDyC.EMITIRRESOLUCION_BUSCAROTRAINFOREQUERIR.toString(),
                                      new Object[] { numControl, tipoDocumento }, new EmitirResolucionMapper());

            return lOtraInfo;

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.EMITIRRESOLUCION_BUSCAROTRAINFOREQUERIR.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      NUMEROCONTROL + numControl + " Tipo de documento " + tipoDocumento);
            throw dae;
        }
    }

    /**
     * Actualiza el estatus del envio de la solicitud, si ya fue enviada a TESOFE, se actualiza con valor de 1,
     * en caso en que haya un rechazo y necesite reenvio se actualiza con cero.
     *
     * @param pagoEnviado 1, si se ha enviado a TESOFE, 0, Si se realizara un reenvio
     * @param numControl numero de control de la solicitud.
     */
    @Override
    public void actualizarPagoEnviado(Integer pagoEnviado, String numControl) throws SIATException {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.ACTUALIZAR_PAGOENVIADO.toString(), new Object[] { pagoEnviado, numControl });

        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.ACTUALIZAR_PAGOENVIADO.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO + NUMEROCONTROL +
                      numControl + " pagoEnviado " + pagoEnviado);
            throw new SIATException(dae);
        }
    }

    /**
     * Se obtienen todas las resoluciones aprobadas para pago a tesoreria.
     *
     *
     * @param idSaldoICEP 1, si se ha enviado a TESOFE, 0, Si se realizara un reenvio
     * @return lista de todas las resoluciones
     */
    @Override
    public List<DyctResolucionDTO> obtenerAprobadasPorIdSaldoICEP(Integer idSaldoICEP) throws SIATException {
        List<DyctResolucionDTO> list = null;

        try {

            list =
jdbcTemplateDYC.query(SQLOracleDyC.DYCT_RESOLUCION_APROBADAS_X_IDSALDOICEP.toString(), new Object[] { idSaldoICEP },
                      new DyctResolucionMapper());

            return list;

        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.EMITIRRESOLUCION_BUSCAROTRAINFOREQUERIR + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      " Id Saldo ICEP " + idSaldoICEP);
            throw new SIATException(dae);
        }
    }


    /**
     * Genera una Resolucion Autorizada Total.
     * @param dyctResolucionDTO
     * @throws mx.gob.sat.siat.dyc.util.common.SIATException
     * @params dyctResolucionDTO.
     *
     */
    @Override
    public void insertar(DyctResolucionDTO dyctResolucionDTO) throws SIATException{
        
        int[] types =
            { Types.VARCHAR, Types.INTEGER, Types.DATE, Types.DOUBLE, Types.DOUBLE, 
              Types.DOUBLE, Types.DOUBLE, Types.DOUBLE, Types.DOUBLE, Types.DOUBLE, 
              Types.VARCHAR, Types.INTEGER, Types.DOUBLE, Types.DOUBLE, Types.DOUBLE, 
              Types.INTEGER, Types.DATE, Types.VARCHAR, Types.DATE, Types.DATE, Types.DATE};    


        try {
            jdbcTemplateDYC.update(SQLOracleDyC.DYCT_RESOLUCION_INSERTAR_RESOLUCION.toString(), 
                new Object[] { dyctResolucionDTO.getDycpSolicitudDTO().getNumControl(),
                               dyctResolucionDTO.getDyccTipoResolDTO().getIdTipoResol(),
                               dyctResolucionDTO.getFechaRegistro(), 
                               dyctResolucionDTO.getImporteSolicitado(),
                               dyctResolucionDTO.getImpAutorizado(), 

                               dyctResolucionDTO.getImpActualizacion(), 
                               dyctResolucionDTO.getIntereses(),
                               dyctResolucionDTO.getRetIntereses(), 
                               dyctResolucionDTO.getImpCompensado(),
                               dyctResolucionDTO.getImporteNeto(), 

                               dyctResolucionDTO.getFundamentacion(),
                               dyctResolucionDTO.getDyccEstreSolDTO().getIdEstResol(),
                               dyctResolucionDTO.getSaldoAfavorAntRes(), 
                               dyctResolucionDTO.getSaldoAfavorDesRes(),
                               dyctResolucionDTO.getImpNegado(), 

                               dyctResolucionDTO.getPagoEnviado(),
                               dyctResolucionDTO.getFechaAprobacion(),
                               dyctResolucionDTO.getClaveRastreo(),
                               dyctResolucionDTO.getFechaEmision(),
                               dyctResolucionDTO.getFechaPresentacion(),
                               
                               dyctResolucionDTO.getFechaPago(),
                    }, types);

        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.DYCT_RESOLUCION_INSERTAR_RESOLUCION + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(dyctResolucionDTO));
            throw new SIATException("Error al ejecutar DyctResolucionDAOImpl.insertar", dae);
        }
    }
    
    @Override
    public void insertResolucionSimulador(String numControl, double importeAutorizado, double importeNegado, double interes) throws SIATException{
        String sql = "INSERT INTO DYCT_RESOLUCION (\n" + 
            "     NUMCONTROL, IDTIPORESOL, FECHAREGISTRO\n" + 
            "    ,IMPORTESOLICITADO, IMPAUTORIZADO, IMPACTUALIZADO\n" + 
            "    ,IMPACTUALIZACION, INTERESES, RETINTERESES\n" + 
            "    ,IMPCOMPENSADO, IMPORTENETO, FUNDAMENTACION\n" + 
            "    ,IDESTRESOL, SALDOAFAVORANTRES, SALDOAFAVORDESRES\n" + 
            "    ,IMPNEGADO, PAGOENVIADO\n" + 
            ") \n" + 
            "values \n" + 
            "(\n" + 
            "    ?, 2, SYSDATE\n" + 
            "    ,45424, ?, 68\n" + 
            "    ,34, ?, 0\n" + 
            "    ,45, 23, 'dddd'\n" + 
            "    ,2, 31231, null\n" +        
            "    ,?, 0\n" + 
            ")";
        
        
        try {
            jdbcTemplateDYC.update(sql,
                                   new Object[] { numControl, importeAutorizado, interes, importeNegado });
        } catch (Exception dae) {
             log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                       SQLOracleDyC.EMITIRRESOLUCION_BUSCAROTRAINFOREQUERIR + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                       " Id Saldo ICEP " + numControl  + " importeAutorizado " + importeAutorizado + " importeNegado  " + importeNegado + " interes " +  interes );
            throw new SIATException(dae);
        }
    }
    
    /**
     * Una vez que la resolucion es aprobada, se le coloca la fecha de aprobacion.
     *
     * @param numeroControl es el numero de control de la solicitud.
     * @throws SIATException
     */
    @Override
    public void actualizarFechaAprobacion(String numeroControl) throws SIATException {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.ACTUALIZAR_FECHAAPROBACION.toString(), new Object[] { numeroControl});

        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.ACTUALIZAR_FECHAAPROBACION + ConstantesDyC1.TEXTO_3_ERROR_DAO + NUMEROCONTROL +
                      numeroControl);
            throw new SIATException(dae);
        }
    }

    /**
     * Agrega la clave de rastreo que se genera una vez que se crea el archivo para enviar los datos a TESOFE.
     *
     * @param numeroControl numero de control de la solicitud
     * @param claveRastreo clave generada en el CU de gestion de pagos de la TESOFE
     * @throws SIATException
     */
    @Override
    public void actualizarClaveRastreo(String numeroControl, String claveRastreo) throws SIATException {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.ACTUALIZAR_CLAVERASTREO.toString(), new Object[] { claveRastreo, numeroControl});

        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.ACTUALIZAR_CLAVERASTREO + ConstantesDyC1.TEXTO_3_ERROR_DAO + NUMEROCONTROL +
                      numeroControl + ", CLAVE DE RASTREO: "+claveRastreo);
            throw new SIATException(dae);
        }
    }

    /**
     * Selecciona el registro de dyct_resolucion a traves de la clave de rastreo. Este metodo se utiliza en el caso de
     * uso de retroalimentacion de la TESOFE.
     *
     * @param claveRastreo Clave generada para el envio de informacion de la TESOFE en el caso de uso de Gestion de pago
     * de la TESOFE.
     * @return Objeto DyctResolucionDTO
     * @throws SIATException
     */
    @Override
    public DyctResolucionDTO consultarDatosResolucionXCveRastreo(String claveRastreo) throws SIATException {
        DyctResolucionDTO objetoResolucion = null;
        try {
            objetoResolucion =
                    jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTA_RESOLUCIONXCLAVERASTREO.toString(), new Object[] { claveRastreo },
                                                   new DyctResolucionMapper());
        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTA_RESOLUCIONXCLAVERASTREO + ConstantesDyC1.TEXTO_3_ERROR_DAO + ", claveRastreo: " +
                      claveRastreo);
            throw new SIATException(dae);

        }
        return objetoResolucion;
    }

    @Override
    public DyctResolucionDTO encontrar(DycpSolicitudDTO solicitud)
    {
        try {
            DyctResolucionMapper mapper = new DyctResolucionMapper();
            mapper.setSolicitud(solicitud);
            return jdbcTemplateDYC.queryForObject(SELECT_TABLA, new Object[] { solicitud.getDycpServicioDTO().getNumControl() }, mapper);
            
        } catch (org.springframework.dao.EmptyResultDataAccessException exEmpty) {
            log.info("NO se encontro Resolucion para la devolucion ->" + solicitud.getDycpServicioDTO().getNumControl() + "<-" +  exEmpty.getMessage());
            return null;
        }
    }

    /**
     * Verifica si la clave de rastreo generada viene de AGAF o de AGGC
     *
     * @param claveRastreo Clave de rastreo generada al momento de enviar a TESOFE.
     * @param cveAdministracion
     * @return verdadero si viene de AGAF, falso en caso contrario.
     */
    @Override
    public boolean verificarSiEsAGAFoAGGC(String claveRastreo, String cveAdministracion) {
        boolean bandera = Boolean.FALSE;
        try {
            jdbcTemplateDYC.queryForObject(SELECT_X_CVEYADMIN + cveAdministracion, new Object[] { claveRastreo }, new DyctResolucionMapper());
            bandera = Boolean.TRUE;
            
        } catch(Exception e) {
            log.info(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SELECT_X_CVEYADMIN + cveAdministracion + ConstantesDyC1.TEXTO_3_ERROR_DAO + ", claveRastreo: " +
                      claveRastreo);
        }
        
        return bandera;
    }

    /**
     * Actualiza las fechas de pago, presentacion y emision al generar un registro en TESOFE.
     *
     * @param fechaPresentacion
     * @param fechaPago
     * @param numControl
     * @throws SIATException
     */
    @Override
    public void actualizarFechasDeEnvioATESOFE(Date fechaPresentacion, Date fechaPago, String numControl) throws SIATException {
        try {
            jdbcTemplateDYC.update(ACUTALIZAR_FECHAS_ENVIO, new Object[] { fechaPresentacion, fechaPago, numControl });
            
        } catch(Exception e) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      ACUTALIZAR_FECHAS_ENVIO + ConstantesDyC1.TEXTO_3_ERROR_DAO +"fechaPresentacion:" +fechaPresentacion+", fechaPago: "+fechaPago+", numControl: " +
                      numControl);
            throw new SIATException(e);
        }
    }
    
    @Override
    public void actualizarResolucion(DyctResolucionDTO dyctResolucionDTO) throws SIATException {

        String query = " UPDATE DYCT_RESOLUCION SET IDTIPORESOL = ?, FECHAREGISTRO = ?, IMPORTESOLICITADO = ?, IMPAUTORIZADO = ?, "
                    + " IMPACTUALIZACION = ?, INTERESES = ?, RETINTERESES = ?, IMPCOMPENSADO = ?, IMPORTENETO = ?, FECHAAPROBACION = ?, "
                    + " FUNDAMENTACION = ?, IDESTRESOL = ?, SALDOAFAVORANTRES = ?, IMPNEGADO = ?, PAGOENVIADO = ? WHERE NUMCONTROL = ?";
        try {            
            jdbcTemplateDYC.update(query,
                    new Object[]{dyctResolucionDTO.getDyccTipoResolDTO().getIdTipoResol(),
                        dyctResolucionDTO.getFechaRegistro(),
                        dyctResolucionDTO.getImporteSolicitado(),
                        dyctResolucionDTO.getImpAutorizado(),
                        dyctResolucionDTO.getImpActualizacion(),
                        dyctResolucionDTO.getIntereses(),
                        dyctResolucionDTO.getRetIntereses(),
                        dyctResolucionDTO.getImpCompensado(),
                        dyctResolucionDTO.getImporteNeto(),
                        dyctResolucionDTO.getFechaAprobacion(),
                        dyctResolucionDTO.getFundamentacion(),
                        dyctResolucionDTO.getDyccEstreSolDTO().getIdEstResol(),
                        dyctResolucionDTO.getSaldoAfavorAntRes(),
                        dyctResolucionDTO.getImpNegado(),
                        dyctResolucionDTO.getPagoEnviado(),
                        dyctResolucionDTO.getDycpSolicitudDTO().getNumControl()});

        } catch (DataAccessException dae) {
            log.error("ERROR_actualizarResolucion", dae);
            throw new SIATException(dae);
        }

    }
}
