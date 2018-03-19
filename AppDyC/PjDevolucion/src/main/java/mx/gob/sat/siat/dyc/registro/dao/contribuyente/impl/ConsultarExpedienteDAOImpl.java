/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.registro.dao.contribuyente.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.domain.regsolicitud.DocumentoReqDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctContribuyenteDTO;
import mx.gob.sat.siat.dyc.gestionsol.vo.consultarexpediente.DeclaracionConsultarExpedienteVO;
import mx.gob.sat.siat.dyc.registro.dao.contribuyente.ConsultarExpedienteDAO;
import mx.gob.sat.siat.dyc.registro.dao.contribuyente.impl.mapper.ConsultarDocRequeridoDAOMapper;
import mx.gob.sat.siat.dyc.registro.dao.contribuyente.impl.mapper.ConsultarExpedienteCompMapper;
import mx.gob.sat.siat.dyc.registro.dao.contribuyente.impl.mapper.ConsultarExpedienteDAOMapper;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * Implementaci&oacute;n DAO para consulta de expediente
 * @author Federico Chopin Gachuz
 *
 * */
@Repository(value = "consultarExpedienteDAO")
public class ConsultarExpedienteDAOImpl implements ConsultarExpedienteDAO {
   
    private Logger log = Logger.getLogger(ConsultarExpedienteDAOImpl.class.getName());

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    /**
     * Obtiene un solo registro de tipo DyctContribuyenteDTO usando el NumControl como filtro de la tabla de trabajo DYCT_CONTRIBUYENTE
     * @param String NumControl
     * @return Objeto dyctContribuyenteDTO<DyctContribuyenteDTO>
     *
     * */
    @Override
    public DyctContribuyenteDTO buscarNumcontrol(String numControl) {

        List<DyctContribuyenteDTO> dyctContribuyente = null;
        DyctContribuyenteDTO dyctContribuyenteDTO = null;

        try {

            dyctContribuyente =
                    jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARCONTRIBUYENTE_BUSCARCONTRIBUYENTEPORNUMERODECONTROL.toString(),
                                          new Object[] { numControl }, new ConsultarExpedienteDAOMapper());

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCONTRIBUYENTE_BUSCARCONTRIBUYENTEPORNUMERODECONTROL +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + " Numero de control " + numControl);
            throw dae;
        }

        if (dyctContribuyente.size() > 0) {
            dyctContribuyenteDTO = dyctContribuyente.get(0);
        }

        return dyctContribuyenteDTO;

    }

    @Override
    public DeclaracionConsultarExpedienteVO buscarOrigenSaldo(String numControl) {

        List<DeclaracionConsultarExpedienteVO> lDeclaracionConsultarExpedienteVO;
        DeclaracionConsultarExpedienteVO declaracionConsultarExpedienteVO = null;

        try {

            lDeclaracionConsultarExpedienteVO =
                    jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARCONTRIBUYENTE_BUSCARORIGENSALDO.toString(),
                                          new Object[] { numControl }, new ConsultarExpedienteCompMapper());

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCONTRIBUYENTE_BUSCARORIGENSALDO + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      " Numero de control " + numControl);
            throw dae;
        }

        if (lDeclaracionConsultarExpedienteVO.size() > 0) {
            declaracionConsultarExpedienteVO = lDeclaracionConsultarExpedienteVO.get(0);
        }

        return declaracionConsultarExpedienteVO;

    }


    /**
     * Obtiene la lista de los documentos requeridos para una solicitud de devolucion
     * @param Long id
     * @return Objeto DocumentoReqDTO
     *
     * */
    @Override
    public List<DocumentoReqDTO> buscaDocumentoRequerido(String numControl) {

        List<DocumentoReqDTO> lstDocumentoReqDTO = new ArrayList<DocumentoReqDTO>();
        try {
            lstDocumentoReqDTO =
                    this.jdbcTemplateDYC.query(SQLOracleDyC.CONSULTAANEXO.toString(), new Object[] { }, new ConsultarDocRequeridoDAOMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_OBTIENEMATRIZANEXOS + ConstantesDyC1.TEXTO_3_ERROR_DAO + numControl);
        }
        return lstDocumentoReqDTO;

    }
}
