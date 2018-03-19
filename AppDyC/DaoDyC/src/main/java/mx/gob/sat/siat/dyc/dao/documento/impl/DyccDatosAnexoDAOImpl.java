package mx.gob.sat.siat.dyc.dao.documento.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.documento.DyccDatosAnexoDAO;
import mx.gob.sat.siat.dyc.dao.documento.impl.mapper.DyccDatosAnexoMapper;
import mx.gob.sat.siat.dyc.domain.anexo.DyccDatosAnexoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @author Jesus Alfredo Hernandez Orozco
 */
@Repository(value = "dyccDatosAnexoDAO")
public class DyccDatosAnexoDAOImpl implements DyccDatosAnexoDAO {
    public DyccDatosAnexoDAOImpl() {
        super();
    }
    
    private static final String CONSULTA_X_IDANEXO="select * from dycc_datosanexo where idAnexo=?";
    private static final Logger LOGGER = Logger.getLogger(DyccDatosAnexoDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    /**
     * <html>
     * <body>
     * consultarPorIDAnexo();<br />
     * <br />
     * Consulta los datos de la tabla de dycc_DatosAnexo que concuerden con el identificador del anexo dado.
     * </body>
     * </html>
     *
     * @param idAnexo
     * @return
     * @throws SIATException
     */
    @Override
    public List<DyccDatosAnexoDTO> consultarPorIDAnexo(Integer idAnexo) throws SIATException {
        List<DyccDatosAnexoDTO> lista = null;
        
        try {
            lista = jdbcTemplateDYC.query(CONSULTA_X_IDANEXO, new Object[] { idAnexo }, new DyccDatosAnexoMapper());
            
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + CONSULTA_X_IDANEXO + ConstantesDyC1.TEXTO_3_ERROR_DAO + 
                         " idAnexo " + idAnexo);
            throw new SIATException(e);
        }
        return lista;
    }
}
