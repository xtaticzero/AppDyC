/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.dao.motivo.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.motivo.DycaResolMotivoDAO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycaResolMotivoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @author Federico Chopin Gachuz
 * @date Abril 30, 2014
 *
 * */
@Repository(value = "dycaResolMotivoDAO")
public class DycaResolMotivoDAOImpl implements DycaResolMotivoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(DycaResolMotivoDAOImpl.class.getName());

    public DycaResolMotivoDAOImpl() {
        super();
    }

    @Override
    public void insertarResolMotivo(List<DycaResolMotivoDTO> lDycaResolMotivoDTO) throws SIATException{

        String query = null;

        try {

            for (DycaResolMotivoDTO dycaResolMotivoDTO : lDycaResolMotivoDTO) {
                
                query = null;

                if (null != dycaResolMotivoDTO.getDescripcionOtros()) {
                
                    query = SQLOracleDyC.EMITIRRESOLUCION_INSERTARRESOLMOTIVODESCRIPCION.toString();

                    jdbcTemplateDYC.update(query,
                                           new Object[] { dycaResolMotivoDTO.getDyctResolucionDTO().getDycpSolicitudDTO().getNumControl(),
                                                          dycaResolMotivoDTO.getDyctResolucionDTO().getDyccTipoResolDTO().getIdTipoResol(),
                                                          dycaResolMotivoDTO.getDyccMotivoTipoResDTO().getDyccMotivoResDTO().getIdMotivoRes(),
                                                          dycaResolMotivoDTO.getDescripcionOtros() });
                } else {
                    
                    query = SQLOracleDyC.EMITIRRESOLUCION_INSERTARRESOLMOTIVO.toString();
                    
                    jdbcTemplateDYC.update(query,
                                           new Object[] { dycaResolMotivoDTO.getDyctResolucionDTO().getDycpSolicitudDTO().getNumControl(),
                                                          dycaResolMotivoDTO.getDyctResolucionDTO().getDyccTipoResolDTO().getIdTipoResol(),
                                                          dycaResolMotivoDTO.getDyccMotivoTipoResDTO().getDyccMotivoResDTO().getIdMotivoRes() });
                }
            }

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + query +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + " lDycaResolMotivoDTO " + lDycaResolMotivoDTO);
            throw new SIATException(dae);
        }

    }

    @Override
    public void borrarMotivosSubmotivos(DyctDocumentoDTO dyctDocumentoDTO) throws SIATException{

        try {

            jdbcTemplateDYC.update(SQLOracleDyC.EMITIRRESOLUCION_BORRARMOTIVOSSUBMOTIVOS.toString(),
                                   new Object[] { dyctDocumentoDTO.getDycpServicioDTO().getNumControl() });

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.EMITIRRESOLUCION_BORRARMOTIVOSSUBMOTIVOS.toString() +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + " dyctDocumentoDTO " + dyctDocumentoDTO);
            throw new SIATException(dae);
        }

    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }
}
