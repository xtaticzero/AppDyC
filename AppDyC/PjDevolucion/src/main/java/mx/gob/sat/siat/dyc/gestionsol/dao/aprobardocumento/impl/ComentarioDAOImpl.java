package mx.gob.sat.siat.dyc.gestionsol.dao.aprobardocumento.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.matriz.impl.mapper.DyccMatrizMapper;
import mx.gob.sat.siat.dyc.dao.tipotramite.impl.mapper.TipoTramiteMapper;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.gestionsol.dao.aprobardocumento.ComentarioDAO;
import mx.gob.sat.siat.dyc.gestionsol.dao.aprobardocumento.impl.mapper.ComentariosMapper;
import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.BandejaDocumentosDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.CatalogoAprobadorDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.SeguimientoDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleAprobarDoc;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;
import mx.gob.sat.siat.dyc.vo.DyccMatrizDocVO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @author Ericka Janth Ibarra Ponce
 * @date 02/2014
 *
 * */
@Repository(value = "comentarioDAO")
public class ComentarioDAOImpl implements ComentarioDAO {
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    public ComentarioDAOImpl() {
        super();
    }

    private Logger log = Logger.getLogger(ComentarioDAOImpl.class.getName());

    /**
     * Metodo que se utiliza para buscar los aprobadores que son de mayor rago al escalar
     *
     * @param cveADM clave de administracion desconcentrada
     * @param numEmpleado numero de empleado del aprobador que va a escalar la solicitud
     * @param claveNivel Es el puesto o nivel que tene el empleado (mientras mas bajo sea el numero, mayor es el rango)
     * @param centroCostoOP Es el centro de costos en el cual esta trabajando el empleado actualmente
     * @return lista de aprobadores de mayor rango en comparacion del que escala
     * @throws SIATException
     */
    @Override
    public List<CatalogoAprobadorDTO> buscarAprobador(int cveADM, String numEmpleado, Integer claveNivel, Integer centroCostoOP) throws SIATException {
        
        try {
            List<CatalogoAprobadorDTO> listacatalogoAprobadorDTO =
                jdbcTemplateDYC.query(SQLOracleAprobarDoc.DYCC_APROBADOR, new Object[] {
                    cveADM, 
                    numEmpleado, 
                    claveNivel, 
                    centroCostoOP, 
                    numEmpleado, 
                    claveNivel}, 
                    new ComentariosMapper());

            return listacatalogoAprobadorDTO;

        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleAprobarDoc.DYCC_APROBADOR);
            throw new SIATException(dae);
        }
    }

    @Override
    public List<DyccMatrizDocVO> buscarMatrizRR() throws SIATException {

        try {

            List<DyccMatrizDocVO> listaDyccMatrizDocDTO =
                jdbcTemplateDYC.query(SQLOracleAprobarDoc.OBTENER_MATRIZ_RESOL_REQUE, new Object[] { },
                                      new DyccMatrizMapper());

            return listaDyccMatrizDocDTO;


        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleAprobarDoc.OBTENER_MATRIZ_RESOL_REQUE);
            throw new SIATException(dae);
        }
    }
    
    @Override
    public void insertaSeguimiento(SeguimientoDTO seguimientoDTO) throws SIATException {
        try {

            jdbcTemplateDYC.update(SQLOracleAprobarDoc.INSERTASEGUIMIENTO,
                                   new Object[] { seguimientoDTO.getIdAccionSeg(), seguimientoDTO.getNumControlDoc(),
                                                  seguimientoDTO.getResponsable(), seguimientoDTO.getFecha(),
                                                  seguimientoDTO.getComentarios() });

        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleAprobarDoc.INSERTASEGUIMIENTO + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(seguimientoDTO));

            throw new SIATException(dae);
        }
    }

    @Override
    public void actualizarDocumento(Integer idEstado, String numControlDoc) throws SIATException {
        try {

            jdbcTemplateDYC.update(SQLOracleAprobarDoc.ACTUALIZARSEGUIMIENTO,
                                   new Object[] { idEstado, numControlDoc });

        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleAprobarDoc.ACTUALIZARSEGUIMIENTO + ConstantesDyC1.TEXTO_3_ERROR_DAO + idEstado +
                      numControlDoc);
            throw new SIATException(dae);
        }

    }

    @Override
    public void actualizarTipoFirma(Integer idTipoFirma, String numControlDoc) throws SIATException {
        try {
            log.info("ENTRA A ACTUALIZAR TIPO FIRMA --APROBAR DOCUMENTO--");
            jdbcTemplateDYC.update(SQLOracleAprobarDoc.ACTUALIZARFIRMA, new Object[] { idTipoFirma, numControlDoc });            
        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleAprobarDoc.ACTUALIZARFIRMA + ConstantesDyC1.TEXTO_3_ERROR_DAO + idTipoFirma +
                      numControlDoc);
            throw new SIATException(dae);
        }

    }

    @Override
    public void actualizarResolucion(Integer idEstadoResol, String numControl) throws SIATException {
        try {

            jdbcTemplateDYC.update(SQLOracleAprobarDoc.ACTUALIZARRESOLUCION,
                                   new Object[] { idEstadoResol, numControl });

        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleAprobarDoc.ACTUALIZARSEGUIMIENTO + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                    idEstadoResol + numControl);
            throw new SIATException(dae);
        }

    }

    @Override
    public void actualizadDocuentoReq(Integer numEmpleado, String numControlDoc) throws SIATException {
        try {

            jdbcTemplateDYC.update(SQLOracleAprobarDoc.ACTUALIZADOCUMNTOREQ,
                                   new Object[] { numEmpleado, numControlDoc });

        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleAprobarDoc.ACTUALIZADOCUMNTOREQ + ConstantesDyC1.TEXTO_3_ERROR_DAO + numEmpleado +
                      numControlDoc);
            throw new SIATException(dae);
        }

    }

    @Override
    public void actuaDocuentoReq(Integer idEstadoReq, String numControlDoc) throws SIATException {
        try {

            jdbcTemplateDYC.update(SQLOracleAprobarDoc.ACTUDOCUMNTOREQ, new Object[] { idEstadoReq, numControlDoc });

        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleAprobarDoc.ACTUDOCUMNTOREQ + ConstantesDyC1.TEXTO_3_ERROR_DAO + idEstadoReq +
                      numControlDoc);
            throw new SIATException(dae);
        }

    }

    @Override
    public void insertar(DyctDocumentoDTO documento, BandejaDocumentosDTO andejaDoc) throws SIATException {
        try {
            Object[] parametros = new Object[ConstantesDyCNumerico.VALOR_10];

            parametros[ConstantesDyCNumerico.VALOR_0] = andejaDoc.getNumControlDoc();
            parametros[ConstantesDyCNumerico.VALOR_1] = andejaDoc.getIdTipoDocumento();
            parametros[ConstantesDyCNumerico.VALOR_2] = documento.getUrl();
            parametros[ConstantesDyCNumerico.VALOR_3] = documento.getFechaRegistro();
            parametros[ConstantesDyCNumerico.VALOR_4] = documento.getNombreArchivo();
            Integer numEmpleadoAprobador = null;
            if (documento.getDyccAprobadorDTO() != null) {
                numEmpleadoAprobador = documento.getDyccAprobadorDTO().getNumEmpleado();
            }
            parametros[ConstantesDyCNumerico.VALOR_5] = numEmpleadoAprobador;
            parametros[ConstantesDyCNumerico.VALOR_6] = documento.getDyccEstadoReqDTO().getIdEstadoReq();
            parametros[ConstantesDyCNumerico.VALOR_7] = documento.getDyccEstadoDocDTO().getIdEstadoDoc();
            parametros[ConstantesDyCNumerico.VALOR_8] = documento.getDycpServicioDTO().getNumControl();
            parametros[ConstantesDyCNumerico.VALOR_9] = documento.getDyccMatDocumentosDTO().getIdPlantilla();

            this.jdbcTemplateDYC.update(SQLOracleDyC.ADMINISTRARSOLICITUDES_INSERTARDOCUMENTO.toString(), parametros);
        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleDyC.ADMINISTRARSOLICITUDES_INSERTARDOCUMENTO.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                    ExtractorUtil.ejecutar(documento) + ExtractorUtil.ejecutar(andejaDoc));
            throw new SIATException(dae);
        }
    }
    
    @Override
    public List<DyccTipoTramiteDTO> obtenerTipoTramite(String numControlDoc) throws SIATException{
        List<DyccTipoTramiteDTO> tipoTramite =  null;       
        try {
                tipoTramite =
                jdbcTemplateDYC.query(SQLOracleAprobarDoc.OBTENERTIPOTRAMITE, new Object[] { }, new TipoTramiteMapper());            

        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    SQLOracleAprobarDoc.DYCC_APROBADOR);
            throw new SIATException(dae);
        }
                
        return tipoTramite;
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }
}
