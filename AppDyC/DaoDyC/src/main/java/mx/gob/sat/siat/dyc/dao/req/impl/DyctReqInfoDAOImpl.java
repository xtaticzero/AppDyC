/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
 */
package mx.gob.sat.siat.dyc.dao.req.impl;

import java.sql.Date;
import java.sql.Types;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.compensacion.impl.mapper.CompensacionMapper;
import mx.gob.sat.siat.dyc.dao.compensacion.impl.mapper.DycpServicioMapper;
import mx.gob.sat.siat.dyc.dao.documento.impl.mapper.DyctDocumentoMapper;
import mx.gob.sat.siat.dyc.dao.req.DyctReqInfoDAO;
import mx.gob.sat.siat.dyc.dao.req.impl.mapper.ReqInfoMapper;
import mx.gob.sat.siat.dyc.dao.usuario.impl.mapper.AprobadorMapper;
import mx.gob.sat.siat.dyc.domain.DyccEstadoReqDTO;
import mx.gob.sat.siat.dyc.domain.documento.DyccEstadoDocDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctReqInfoDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author Federico Chopin Gachuz
 * @date Enero 23, 2014
 *
 *
 */
@Repository(value = "dyctReqInfoDAO")
public class DyctReqInfoDAOImpl implements DyctReqInfoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(DyctReqInfoDAOImpl.class.getName());

    public DyctReqInfoDAOImpl() {
        super();
    }

    @Override
    public void insertar(DyctReqInfoDTO dyctReqInfoDTO) throws SIATException {
        String sentencia
                = " INSERT INTO DYCT_REQINFO (NUMCONTROLDOC, FECHANOTIFICACION, FECHASOLVENTACION) VALUES (?, ?, ?)";
        try {
            int[] tipos = new int[]{Types.VARCHAR, Types.DATE, Types.DATE};

            jdbcTemplateDYC.update(sentencia,
                    new Object[]{dyctReqInfoDTO.getNumControlDoc(), dyctReqInfoDTO.getFechaNotificacion(),
                        dyctReqInfoDTO.getFechaSolventacion()}, tipos);
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + sentencia + ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(dyctReqInfoDTO));
            throw new SIATException(dae);
        }
    }

    @Override
    public DyctReqInfoDTO buscarReqInfo(String numControlDoc) throws SIATException {
        DyctReqInfoDTO objeto2;
        String sentencia
                = "select NUMCONTROLDOC,FECHANOTIFICACION,FECHASOLVENTACION from   dyct_reqinfo where NUMCONTROLDOC=?";
        try {
            objeto2
                    = jdbcTemplateDYC.queryForObject(sentencia,
                            new Object[]{numControlDoc},
                            new ReqInfoMapper());
        } catch (EmptyResultDataAccessException e) {
            objeto2 = null;
        } catch (DataAccessException dae) {
            log.error("buscarReqInfo" + ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + sentencia + ConstantesDyC1.TEXTO_3_ERROR_DAO + numControlDoc);
            throw new SIATException(dae);
        }

        return objeto2;
    }

    /**
     * Actualiza cualquier fecha de la tabla de DYCT_REQINFO
     *
     * @param query query que indica que fecha es la que se va a acutalizar.
     * @param fecha Es la fecha a insertar en la base de datos.
     * @param numControlDoc identificador del documento.
     */
    @Override
    public int actualizarFecha(String query, Date fecha, String numControlDoc) throws SIATException {
        int actualizado = 0;
        try {
            actualizado = jdbcTemplateDYC.update(SQLOracleDyC.ACTUALIZAR_FECHANOTIFICACIONDEREQUERIMIENTO.toString(),
                    new Object[]{fecha, numControlDoc});

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO + query
                    + ConstantesDyC1.TEXTO_3_ERROR_DAO + "fecha:" + fecha + ", numControlDoc:" + numControlDoc
                    + ConstantesDyC1.TEXTO_8_CAUSAS + dae.getCause());
            throw new SIATException(dae);
        }
        return actualizado;
    }

    @Override
    public List<DyctReqInfoDTO> selecUCompensacionXEstadoreqEstadodoc(DyccEstadoReqDTO estadoReq,
            DyccEstadoDocDTO estadoDoc) {
        String query
                = " SELECT * FROM DYCT_REQINFO RI, DYCT_DOCUMENTO D, DYCP_COMPENSACION C, DYCC_DICTAMINADOR DI, DYCC_APROBADOR A"
                + " WHERE RI.NUMCONTROLDOC = D.NUMCONTROLDOC" + " AND A.NUMEMPLEADO = D.NUMEMPLEADOAPROB"
                + " AND DI.NUMEMPLEADO = C.NUMEMPLEADODICT" + " AND D.NUMCONTROL = C.NUMCONTROL"
                + " AND D.IDESTADOREQ = ? " + " AND D.IDESTADODOC = ? ";

        CompensacionMapper mapperCompensacion = new CompensacionMapper();
        DycpServicioMapper mapperServicio = new DycpServicioMapper();
        mapperServicio.setMapperCompensacion(mapperCompensacion);
        AprobadorMapper mapperAprobador = new AprobadorMapper();
        DyctDocumentoMapper mapperDocumento = new DyctDocumentoMapper();
        mapperDocumento.setMapperServicio(mapperServicio);
        mapperDocumento.setMapperAprobador(mapperAprobador);
        ReqInfoMapper mapper = new ReqInfoMapper();
        mapper.setMapperDocumento(mapperDocumento);
        return jdbcTemplateDYC.query(query, new Object[]{estadoReq.getIdEstadoReq(), estadoDoc.getIdEstadoDoc()},
                mapper);
    }

    @Override
    public List<DyctReqInfoDTO> buscarReqInfoReimpresion(String numControlDoc) {
        List<DyctReqInfoDTO> objeto2;
        objeto2
                = jdbcTemplateDYC.query(SQLOracleDyC.FIND_REQINFO_X_NUMDOC.toString(),
                        new Object[]{numControlDoc},
                        new ReqInfoMapper());
        return objeto2;
    }
}
