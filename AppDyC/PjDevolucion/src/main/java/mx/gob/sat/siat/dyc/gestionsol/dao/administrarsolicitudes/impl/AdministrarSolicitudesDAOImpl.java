/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.gestionsol.dao.administrarsolicitudes.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import mx.gob.sat.siat.dyc.dao.seguimiento.DycaReasignacionDAO;

import mx.gob.sat.siat.dyc.dao.util.impl.mapper.DycaDocumentoAdministrarSolMapper;
import mx.gob.sat.siat.dyc.dao.util.impl.mapper.DyctSeguimientoAdministrarSolMapper;
import mx.gob.sat.siat.dyc.domain.ReasignacionDTO;
import mx.gob.sat.siat.dyc.domain.declaracion.DyctFacultadesDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSeguimientoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.gestionsol.dao.administrarsolicitudes.AdministrarSolicitudesDAO;
import mx.gob.sat.siat.dyc.gestionsol.dao.administrarsolicitudes.impl.mapper.AdministrarSolicitudesMapper;
import mx.gob.sat.siat.dyc.gestionsol.dao.administrarsolicitudes.impl.mapper.IdReqConsecutivoMapper;
import mx.gob.sat.siat.dyc.gestionsol.dao.emitirresolucion.impl.mapper.EmitirResolucionIdentificadorMapper;
import mx.gob.sat.siat.dyc.gestionsol.vo.administrarsolicitudes.AdministrarSolicitudesVO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;
import mx.gob.sat.siat.dyc.vo.DocumentoAdministrarSolVO;
import mx.gob.sat.siat.dyc.vo.SeguimientoAdministrarSolVO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


/**
 * @author Federico Chopin Gachuz
 * @date Noviembre 11, 2013
 *
 * */
@Repository(value = "administrarSolicitudesDAO")
public class AdministrarSolicitudesDAOImpl implements AdministrarSolicitudesDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;
    
    @Autowired
    private DycaReasignacionDAO dycaReasignacionDAO;

    private final Logger log = Logger.getLogger(AdministrarSolicitudesDAOImpl.class.getName());

    public AdministrarSolicitudesDAOImpl() {
        super();
    }
    
    public static final String LOG_01 = "numControl: ";

    @Override
    public AdministrarSolicitudesVO obtenerDiferencia(String numControlDoc) {

        List<AdministrarSolicitudesVO> ladministrarSolicitudesVO;
        AdministrarSolicitudesVO administrarSolicitudesVO = null;

        try {

            ladministrarSolicitudesVO =
                    jdbcTemplateDYC.query(SQLOracleDyC.ADMINISTRARSOLICITUDES_OBTENERDIFERENCIA.toString(), new Object[] { numControlDoc },
                                          new AdministrarSolicitudesMapper());

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.ADMINISTRARSOLICITUDES_OBTENERDIFERENCIA.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      " numControlDoc " + numControlDoc);
            throw dae;
        }

        if (ladministrarSolicitudesVO.size() > 0) {
            administrarSolicitudesVO = ladministrarSolicitudesVO.get(0);
        }

        return administrarSolicitudesVO;


    }

    @Override
    public Integer obtenerDiasHabiles(Date fNotificacion, Date fSolventacion) {

        Integer resultado = 0;

        try {

            resultado =
                    jdbcTemplateDYC.queryForObject(SQLOracleDyC.ADMINISTRARSOLICITUDES_OBTENERDIASHABILES.toString(), new Object[] { fNotificacion,
                                                                                                                          fSolventacion },
                                                   Integer.class);

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.ADMINISTRARSOLICITUDES_OBTENERDIASHABILES.toString() +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + "Fecha Notificacion: " + fNotificacion +
                      " Fecha Solventacion: " + fSolventacion);
            throw dae;
        }

        return resultado;

    }
    
    @Override
    public Integer obtenerIdEstadoDocumento(String numControl) {

        Integer idDocumento = 0;

        try {

            idDocumento =
                    jdbcTemplateDYC.queryForObject(SQLOracleDyC.ADMINISTRARSOLICITUDES_OBTENERIDESTADODOCUMENTO.toString(), new Object[] { numControl, numControl, numControl, numControl },
                                                   Integer.class);

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.ADMINISTRARSOLICITUDES_OBTENERIDESTADODOCUMENTO.toString() +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + ConstantesDyC1.NUMCONTROL + numControl);
            throw dae;
        }

        return idDocumento;

    }

    @Override
    public Integer obtenerDiasFacultades(String numControl, long idFacultades) throws SIATException {

        Integer resultado = 0;

        try {

            resultado =
                    jdbcTemplateDYC.queryForObject(SQLOracleDyC.ADMINISTRARSOLICITUDES_OBTENERDIASFACULTADES.toString(), new Object[] { numControl,
                                                                                                                             idFacultades },
                                                   Integer.class);

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.ADMINISTRARSOLICITUDES_OBTENERDIASFACULTADES.toString() +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + ConstantesDyC1.NUMCONTROL + numControl + " idFacultades: " +
                      idFacultades);
            throw new SIATException(dae);
        }

        return resultado;

    }

    @Override
    public Integer verificarExistenciaInicioFacultades(String numControl) throws SIATException {

        Integer resultado = 0;

        try {

            resultado =
                    jdbcTemplateDYC.queryForObject(SQLOracleDyC.ADMINISTRARSOLICITUDES_VERIFICAREXISTENCIADIASFACULTADES.toString(),
                                                   new Object[] { numControl }, Integer.class);

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO +
                      SQLOracleDyC.ADMINISTRARSOLICITUDES_VERIFICAREXISTENCIADIASFACULTADES.toString() +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + ConstantesDyC1.NUMCONTROL);
            throw new SIATException(dae);
        }

        return resultado;
    }

    @Override
    public void actualizarEstados(String numControl, String numControlDoc, boolean  esActualizable) {
        String qry = "";
        try {
            if (esActualizable) {
                actualizarEstadoReq(numControlDoc);
                qry = SQLOracleDyC.ADMINISTRARSOLICITUDES_ACTUALIZARESTADOTRAMITE.toString();
                jdbcTemplateDYC.update(qry, new Object[] { numControl });
            }
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + qry);
            throw dae;
        }

    }
    
    @Override
    public void actualizarEstadosComp(String numControl, String numControlDoc) {
        String qry = "";
        try {
            actualizarEstadoReq(numControlDoc);
            qry = SQLOracleDyC.ADMINISTRARSOLICITUDES_ACTUALIZARESTADOTRAMITECOMP.toString();
            jdbcTemplateDYC.update(qry, new Object[] { numControl });

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + qry);
            throw dae;
        }

    }

    @Override
    public void actualizarDocumento(String numControlDoc, Integer numEmp) throws SIATException {

        try {

            jdbcTemplateDYC.update(SQLOracleDyC.ADMINISTRARSOLICITUDES_ACTUALIZARDOCUMENTOBANDEJADOCUMENTOS.toString(),
                                   new Object[] { numEmp, numControlDoc });

        } catch (DataAccessException dae) {

            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO +
                      SQLOracleDyC.ADMINISTRARSOLICITUDES_ACTUALIZARDOCUMENTOBANDEJADOCUMENTOS.toString() +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + "numControlDoc: " + numControlDoc);
            throw new SIATException(dae);

        }

    }

    @Override
    public void actualizarEstadoSolicitud(String numControl, Integer estadoSolicitud) throws SIATException {

        try {

            jdbcTemplateDYC.update(SQLOracleDyC.ADMINISTRARSOLICITUDES_ACTUALIZARESTATUSSOLICITUD.toString(),
                                   new Object[] { estadoSolicitud, numControl });

        } catch (DataAccessException dae) {

            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO +
                      SQLOracleDyC.ADMINISTRARSOLICITUDES_ACTUALIZARESTATUSSOLICITUD.toString() +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + LOG_01 + numControl + "estadoSolicitud: " +
                      estadoSolicitud);
            throw new SIATException(dae);

        }

    }

    @Override
    public void actualizarFechaFinFacultades(String numControl) throws SIATException {

        try {

            jdbcTemplateDYC.update(SQLOracleDyC.ADMINISTRARSOLICITUDES_ACTUALIZARFECHAFINFACULTADES.toString(),
                                   new Object[] { numControl });

        } catch (DataAccessException dae) {

            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO +
                      SQLOracleDyC.ADMINISTRARSOLICITUDES_ACTUALIZARFECHAFINFACULTADES.toString() +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + LOG_01 + numControl);
            throw new SIATException(dae);

        }

    }

    @Override
    public void accionAprobarInicioFacultades(DyctFacultadesDTO dyctFacultadesDTO) throws SIATException {

        try {

            jdbcTemplateDYC.update(SQLOracleDyC.ADMINISTRARSOLICITUDES_APROBARINICIOFACULTADES.toString(),
                                   new Object[] { dyctFacultadesDTO.getFolio(),
                                                  dyctFacultadesDTO.getFechaInicio(),
                                                  dyctFacultadesDTO.getDycpServicioDTO().getNumControl() });

        } catch (DataAccessException dae) {

            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.ADMINISTRARSOLICITUDES_APROBARINICIOFACULTADES.toString() +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + "dyctFacultadesDTO: " + dyctFacultadesDTO);
            throw new SIATException(dae);

        }

    }

    @Override
    public void accionFinalizarInicioFacultades(DyctFacultadesDTO dyctFacultadesDTO) throws SIATException {

        try {

            jdbcTemplateDYC.update(SQLOracleDyC.ADMINISTRARSOLICITUDES_FINALIZARINICIOFACULTADES.toString(),
                                   new Object[] { dyctFacultadesDTO.getFechaFin(),
                                                  dyctFacultadesDTO.getDycpServicioDTO().getNumControl() });

        } catch (DataAccessException dae) {

            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.ADMINISTRARSOLICITUDES_FINALIZARINICIOFACULTADES.toString() +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + "dyctFacultadesDTO: " + dyctFacultadesDTO);
            throw new SIATException(dae);

        }

    }

    @Override
    public void iniciarFlujoFacultades(DyctFacultadesDTO dyctFacultadesDTO) throws SIATException {

        try {

            jdbcTemplateDYC.update(SQLOracleDyC.ADMINISTRARSOLICITUDES_INSERTARINICIOFACULTADES.toString(),
                                   new Object[] { dyctFacultadesDTO.getDycpServicioDTO().getNumControl(),
                                                  dyctFacultadesDTO.getDyccAprobadorDTO().getNumEmpleado() });

        } catch (DataAccessException dae) {

            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.ADMINISTRARSOLICITUDES_INSERTARINICIOFACULTADES.toString() +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + "dyctFacultadesDTO: " + dyctFacultadesDTO);
            throw new SIATException(dae);

        }

    }

    @Override
    public Integer obtenerEstadoReq(String idDocReq) {

        Integer estado = 0;

        try {

            estado =
                    jdbcTemplateDYC.queryForObject(SQLOracleDyC.ADMINISTRARSOLICITUDES_OBTENERESTADOREQ.toString(), new Object[] { idDocReq },
                                                   Integer.class);

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.ADMINISTRARSOLICITUDES_OBTENERESTADOREQ.toString() +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + "idDocReq: " + idDocReq);
            throw dae;
        }

        return estado;

    }

    @Override
    public Integer obtenerNumeroReq(String idDocReq) {

        Integer numReq = 0;

        try {

            numReq =
                    jdbcTemplateDYC.queryForObject(SQLOracleDyC.ADMINISTRARSOLICITUDES_OBTENERNUMREQ.toString(), new Object[] { idDocReq },
                                                   Integer.class);

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.ADMINISTRARSOLICITUDES_OBTENERNUMREQ.toString() +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + "idDocReq: " + idDocReq);
            throw dae;
        }

        return numReq;

    }

    @Override
    public String obtenerNumControlDoc(String numControl) {

        String idDocumentoReq = null;

        try {

            idDocumentoReq =
                    jdbcTemplateDYC.queryForObject(SQLOracleDyC.ADMINISTRARSOLICITUDES_NUMCONTROLDOC.toString(), new Object[] { numControl,
                                                                                                                     numControl },
                                                   String.class);

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.ADMINISTRARSOLICITUDES_NUMCONTROLDOC.toString() +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + LOG_01 + numControl);
            throw dae;
        }

        return idDocumentoReq;

    }

    public List<String> obtenerNumControlListDocs(String numControl) {

        List<String> idDocumentoReq = null;

        try {

            idDocumentoReq =
                    jdbcTemplateDYC.query(SQLOracleDyC.ADMINISTRARSOLICITUDES_NUMCONTROLDOCLIST.toString(), new Object[] { numControl
                                                                                                                      }, new RowMapper<String>() {
                    @Override
                    public String mapRow(ResultSet rs, int i) throws SQLException {
                        return rs.getString("NUMCONTROLDOC");

                    }
                });
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.ADMINISTRARSOLICITUDES_NUMCONTROLDOCLIST.toString() +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + LOG_01 + numControl);
            throw dae;
        }

        return idDocumentoReq;

    }
    
    @Override
    public Date obtenerFechaSolventacion(String idDocReq) {

        Date fechaSolventacion = null;

        try {

            fechaSolventacion =
                    jdbcTemplateDYC.queryForObject(SQLOracleDyC.ADMINISTRARSOLICITUDES_OBTENERFECSOLVENTACION.toString(),
                                                   new Object[] { idDocReq }, Date.class);

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.ADMINISTRARSOLICITUDES_OBTENERFECSOLVENTACION.toString() +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + "idDocReq: " + idDocReq);
            throw dae;
        }

        return fechaSolventacion;

    }

    @Override
    public Long getIdDocumento() {

        Long idDocumento = null;
        try {
            idDocumento =
                    (Long)jdbcTemplateDYC.queryForObject(SQLOracleDyC.ADMINISTRARSOLICITUDES_GETIDDOCUMENTOREQ.toString(), new Object[] { },
                                                         new IdReqConsecutivoMapper());
        } catch (DataAccessException e) {
            e.getMessage();
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.ADMINISTRARSOLICITUDES_GETIDDOCUMENTOREQ.toString());
            throw e;
        }

        if (null != idDocumento) {
            return idDocumento;
        }
        return 0L;

    }

    @Override
    public List<SeguimientoAdministrarSolVO> selecXServicio(DycpServicioDTO servicio) {

        try {

            List<SeguimientoAdministrarSolVO> lSeguimientoAdministrarSolVO =
                jdbcTemplateDYC.query(SQLOracleDyC.BUSCARSEGUIMIENTOSPORSERVICIO.toString(),
                                      new Object[] { servicio.getNumControl() },
                                      new DyctSeguimientoAdministrarSolMapper());
            
            ReasignacionDTO reasignacion=this.dycaReasignacionDAO.obtenerReasignacion(servicio.getNumControl());
            if(null!=reasignacion){
                log.info("Llenando seguimiento de reasiganacion----------");
                lSeguimientoAdministrarSolVO.add(llenarSeguimientoReasignacion(reasignacion));                
            }

            return lSeguimientoAdministrarSolVO;

        } catch (DataAccessException dae) {
            log.error("Se ha presentado un error en la ejecucion de : " + SQLOracleDyC.BUSCARSEGUIMIENTOSPORSERVICIO.toString() +
                      " con los siguientes parametros " + " servicio " + servicio);
            throw dae;
        }
    }

    @Override
    public List<DyctSeguimientoDTO> obtenerIdentificadores(DyctDocumentoDTO dyctDocumentoDTO) {

        try {

            List<DyctSeguimientoDTO> lista =
                jdbcTemplateDYC.query(SQLOracleDyC.EMITIRRESOLUCION_OBTENERSEGUIMIENTOS.toString(), new Object[] { dyctDocumentoDTO.getDycpServicioDTO().getNumControl() },
                                      new EmitirResolucionIdentificadorMapper());

            return lista;

        } catch (DataAccessException dae) {
            log.error("Se ha presentado un error en la ejecucion de : " +
                      SQLOracleDyC.EMITIRRESOLUCION_OBTENERSEGUIMIENTOS.toString() + " con los siguientes parametros " +
                      " dyctDocumentoDTO " + dyctDocumentoDTO);
            throw dae;
        }

    }

    @Override
    public List<DocumentoAdministrarSolVO> buscarDocsDictaminador(String numEmpleado) {

        try {

            List<DocumentoAdministrarSolVO> lDocumentoAdministrarSolVO =
                jdbcTemplateDYC.query(SQLOracleDyC.ADMINISTRARSOLICITUDES_BUSCARDOCSDICTAMINADOR.toString(),
                                      new Object[] { numEmpleado }, new DycaDocumentoAdministrarSolMapper());

            return lDocumentoAdministrarSolVO;

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.ADMINISTRARSOLICITUDES_BUSCARDOCSDICTAMINADOR.toString() +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + " numEmpleado " + numEmpleado);
            throw dae;
        }
    }
    
    private SeguimientoAdministrarSolVO llenarSeguimientoReasignacion(ReasignacionDTO reasignacion){
        SeguimientoAdministrarSolVO seguimiento= new SeguimientoAdministrarSolVO();
        seguimiento.setNombreArchivo("No aplica");
        seguimiento.setAccion("Reasignado");
        seguimiento.setResponsable(reasignacion.getNombreResponsable());
        seguimiento.setFecha(reasignacion.getFechaReasignacion());
        seguimiento.setComentarios("No aplica");
        return seguimiento;
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }
    
    @Override
    public void actualizarEstadoReq(String numControlDoc) {
        String qry = "";
        try {
            qry = SQLOracleDyC.ADMINISTRARSOLICITUDES_ACTUALIZARESTADOREQUERIMIENTO.toString();
            jdbcTemplateDYC.update(qry, new Object[] { numControlDoc });
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + qry);
            throw dae;
        }
    }
    
    @Override
    public void actualizarEdoCompDesistido(String numControl, String numControlDoc, boolean esActualizable){
        String qry = "";
        try {
            if (esActualizable) {
                actualizarEstadoReq(numControlDoc);
                qry = SQLOracleDyC.ADMINISTRARSOLICITUDES_ACTUALIZARESTADOTRAMITECOMP_DESISTIDO.toString();
                jdbcTemplateDYC.update(qry, new Object[] { numControl });
            }
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + qry);
            throw dae;
        }
    }

}
