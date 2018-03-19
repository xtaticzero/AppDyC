/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.dao.cargaautomaticas.impl;

import java.util.Date;
import java.util.List;
import mx.gob.sat.siat.dyc.dao.cargaautomaticas.CargaMasivoAutomaticasDAO;
import mx.gob.sat.siat.dyc.dao.cargaautomaticas.impl.mapper.CargaMasivoAutomaticasMapper;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.vo.CargaMasivaAutomaticasRegistroVO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author root
 */
@Repository(value = "cargaMasivoAutomaticasDAO")
public class CargaMasivoAutomaticasDAOImpl implements  CargaMasivoAutomaticasDAO{
      @Autowired(required = true)
    private JdbcTemplate jdbcTemplateDYC;
         private static final Logger LOGGER = Logger.getLogger(CargaMasivoAutomaticasDAO.class);
     private static final String UPDATE_ARCHIVOS_MASIVOS= " UPDATE DYCT_ARCHIVOS_MASIVA_AUT SET FECHAINICIOPROCESO=?, FECHATERMINOPROCESO=?, NUMEROSOLICITUDESPROCESAR=?, NUMEROSOLICITUDESEXITOSAS=?, "+
             "NUMEROSOLICITUDESFALLIDAS=?, URLARCHIVOPROCESAR=?, URLARCHIVOEXITOSA=?, URLARCHIVOFALLIDA=? WHERE FECHAINICIOPROCESO IS NULL";
       private static final String UPDATE_ARCHIVOS_MASIVOS_BORRAR= " UPDATE DYCT_ARCHIVOS_MASIVA_AUT SET FECHACARGA=SYSDATE, "
               + " RESPONSABLE=? WHERE FECHAINICIOPROCESO IS NULL";
    private static final String CONSULTA_AUTOMATICAS_POR_FECHA_CARGA= " select  FECHACARGA,RESPONSABLE,FECHAINICIOPROCESO,FECHATERMINOPROCESO, \n" + 
    "    NUMEROSOLICITUDESPROCESAR, NUMEROSOLICITUDESEXITOSAS, NUMEROSOLICITUDESFALLIDAS, URLARCHIVOPROCESAR, URLARCHIVOEXITOSA, URLARCHIVOFALLIDA  \n" + 
   " FROM DYCT_ARCHIVOS_MASIVA_AUT WHERE TRUNC(FECHACARGA) BETWEEN TRUNC(?) AND TRUNC(?)  AND FECHAINICIOPROCESO IS NOT NULL order by FECHACARGA";
   private static final String CONSULTA_AUTOMATICAS_POR_3_ULTIMOS= "SELECT * FROM ( select  FECHACARGA,RESPONSABLE,FECHAINICIOPROCESO,FECHATERMINOPROCESO, \n"+
           "  NUMEROSOLICITUDESPROCESAR, NUMEROSOLICITUDESEXITOSAS, NUMEROSOLICITUDESFALLIDAS, URLARCHIVOPROCESAR, URLARCHIVOEXITOSA, URLARCHIVOFALLIDA,  \n"+
           " row_number() OVER(ORDER BY IDARCHIVOMASIVOAUT DESC) rn FROM DYCT_ARCHIVOS_MASIVA_AUT  WHERE FECHAINICIOPROCESO IS NOT NULL  )  WHERE  rn<4 order by FECHACARGA";
      private static final String CONSULTA_AUTOMATICAS_NO_PROCESADOS= "SELECT count(IDARCHIVOMASIVOAUT)  FROM DYCT_ARCHIVOS_MASIVA_AUT  WHERE FECHAINICIOPROCESO IS  NULL";
       private static final String INSERTAR_ARCHIVOS_MASIVOS= " INSERT INTO  DYCT_ARCHIVOS_MASIVA_AUT(IDARCHIVOMASIVOAUT,FECHACARGA,RESPONSABLE)  "+
             " VALUES(DYCQ_IDARCHIVOMASIVOAUT.nextval,sysdate,?) ";
   /**
     *
     * @param fechaCargaInicio
     * @param fechaCargaFin
     * @return
     */
    @Override
    public  List<CargaMasivaAutomaticasRegistroVO> obtenerRegistrosAutomaticos(Date fechaCargaInicio,Date fechaCargaFin){
           List<CargaMasivaAutomaticasRegistroVO> registros;
        try {

                 registros =
                    jdbcTemplateDYC.query(CONSULTA_AUTOMATICAS_POR_FECHA_CARGA, new Object[] { fechaCargaInicio,fechaCargaFin },
                                          new CargaMasivoAutomaticasMapper());

        } catch (DataAccessException dae) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      CONSULTA_AUTOMATICAS_POR_FECHA_CARGA + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      " fechaInicio carga" + fechaCargaInicio+ "fecha fin carga"+fechaCargaFin);
            throw dae;
        }
        return registros;
     }
    
     @Override
    public  List<CargaMasivaAutomaticasRegistroVO> obtenerUltimosRegistros(){
           List<CargaMasivaAutomaticasRegistroVO> registros;
        try {

                 registros =
                    jdbcTemplateDYC.query(CONSULTA_AUTOMATICAS_POR_3_ULTIMOS,
                                          new CargaMasivoAutomaticasMapper());

        } catch (DataAccessException dae) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      CONSULTA_AUTOMATICAS_POR_3_ULTIMOS + ConstantesDyC1.TEXTO_3_ERROR_DAO );
            throw dae;
        }
        return registros;
     }
      @Override
       public  void updateCargaMasiva(CargaMasivaAutomaticasRegistroVO archivo){
           
        try {

                 
                    jdbcTemplateDYC.update(UPDATE_ARCHIVOS_MASIVOS,new Object[] {archivo.getFechaInicioProcesoDate(),archivo.getFechaTerminoProcesoDate(),
                    archivo.getNumeroSolicitudesProcesar(),archivo.getNumeroSolicitudesExitosas(),archivo.getNumeroSolicitudesFallidas(),
                    archivo.getRutaArchivoProcesar(),archivo.getRutaArchivoExitoso(),archivo.getRutaArchivoFallido()});
     

        } catch (DataAccessException dae) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      UPDATE_ARCHIVOS_MASIVOS + ConstantesDyC1.TEXTO_3_ERROR_DAO );
            throw dae;
        }
     }
      @Override
       public  void updateCargaMasivaBorrado(String rfc){
           
        try {

                    jdbcTemplateDYC.update(UPDATE_ARCHIVOS_MASIVOS_BORRAR,new Object[] {rfc});

        } catch (DataAccessException dae) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      UPDATE_ARCHIVOS_MASIVOS_BORRAR + ConstantesDyC1.TEXTO_3_ERROR_DAO );
            throw dae;
        }
     }
      @Override
       public  Integer consultaRegistroNoprocesado(){
           Integer resultado;
        try {

                 resultado =
                    jdbcTemplateDYC.queryForObject(CONSULTA_AUTOMATICAS_NO_PROCESADOS,
                                         Integer.class);

        } catch (DataAccessException dae) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      CONSULTA_AUTOMATICAS_NO_PROCESADOS + ConstantesDyC1.TEXTO_3_ERROR_DAO );
            throw dae;
        }
        return resultado;
     }  
       
      @Override
        public  void insertarCargaMasiva(String rfc){
        
        try {

                    jdbcTemplateDYC.update(INSERTAR_ARCHIVOS_MASIVOS,new Object[] {rfc});

        } catch (DataAccessException dae) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      INSERTAR_ARCHIVOS_MASIVOS + ConstantesDyC1.TEXTO_3_ERROR_DAO );
            throw dae;
        }
     }  
}
