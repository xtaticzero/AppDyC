/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.dao.documento.impl;

import mx.gob.sat.siat.dyc.dao.documento.DycaDocumentoDAO;
import mx.gob.sat.siat.dyc.domain.archivo.DyctArchivoDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * Implementaci&oacute;n DAO para consulta de documentos adjuntos
 * @author Federico Chopin Gachuz
 *
 * */
@Repository(value = "dycaDocumentoDAO")
public class DycaDocumentoDAOImpl implements DycaDocumentoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    public DycaDocumentoDAOImpl() {
        super();
    }

    private Logger log = Logger.getLogger(DycaDocumentoDAOImpl.class.getName());

    @Override
    public void actualizarDocumentoAprobacion(Integer numEmpleadoAprob, Long idDocumento) throws SIATException{

        try {

            jdbcTemplateDYC.update(SQLOracleDyC.ADMINISTRARSOLICITUDES_ACTUALIZARDOCUMENTO.toString(),
                                   new Object[] { numEmpleadoAprob, idDocumento });
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.ADMINISTRARSOLICITUDES_ACTUALIZARDOCUMENTO.toString() +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + " numEmpleadoAprob " + numEmpleadoAprob + " idDocumento " +
                      idDocumento);
            throw new SIATException(dae);
        }

    }

    @Override
    public void insertarDocumentoComentario(DyctArchivoDTO dyctArchivoDTO,
                                            Long idSequencia) throws SIATException {

        try {
            
            jdbcTemplateDYC.update(SQLOracleDyC.INSERTADOCUMENTOCOMENTARIO.toString(),
                                   new Object[] { dyctArchivoDTO.getNombreArchivo(), 
                                                  dyctArchivoDTO.getUrl(),
                                                  dyctArchivoDTO.getDycpServicioDTO().getNumControl(),
                                                  dyctArchivoDTO.getFechaRegistro(),
                                                  dyctArchivoDTO.getDescripcion (),
                                                  dyctArchivoDTO.getOcultoContribuyente ()
                                                  });

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.INSERTADOCUMENTOCOMENTARIO.toString() +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(dyctArchivoDTO) + idSequencia);
            throw dae;
        }
    }

    
}
