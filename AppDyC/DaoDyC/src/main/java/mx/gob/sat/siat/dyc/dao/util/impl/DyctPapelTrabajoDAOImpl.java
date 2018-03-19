/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.dao.util.impl;

import java.sql.Types;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.util.DyctPapelTrabajoDAO;
import mx.gob.sat.siat.dyc.dao.util.impl.mapper.DyctPapelTrabajoMapper;
import mx.gob.sat.siat.dyc.dao.util.impl.mapper.PapelTrabajoMapper;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctExpedienteDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctPapelTrabajoDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @author Federico Chopin Gachuz
 * @date Noviembre 15, 2013
 *
 * */
@Repository(value = "dyctPapelTrabajoDAO")
public class DyctPapelTrabajoDAOImpl implements DyctPapelTrabajoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(DyctPapelTrabajoDAOImpl.class.getName());

    public DyctPapelTrabajoDAOImpl() {
        super();
    }

    @Override
    public List<DyctPapelTrabajoDTO> buscarPapelTrabajo(String numControl) {

        try {

            List<DyctPapelTrabajoDTO> lDyctPapelTrabajoDTO =
                jdbcTemplateDYC.query(SQLOracleDyC.ADMINISTRARSOLICITUDES_BUSCARPAPELTRABAJOPORNUMERODECONTROL.toString(),
                                      new Object[] { numControl }, new DyctPapelTrabajoMapper());

            return lDyctPapelTrabajoDTO;

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.ADMINISTRARSOLICITUDES_BUSCARPAPELTRABAJOPORNUMERODECONTROL.toString() +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + " Numero de control " + numControl);
            throw dae;
        }

    }

    @Override
    public void insertarPapelTrabajo(DyctPapelTrabajoDTO dyctPapelTrabajoDTO, boolean nuevoOreemplazo) throws SIATException{

        try {
            if (nuevoOreemplazo) {
                jdbcTemplateDYC.update(SQLOracleDyC.ADMINISTRARSOLICITUDES_INSERTARPAPELTRABAJO.toString(),
                                       new Object[] { dyctPapelTrabajoDTO.getNombreArchivo(),
                                                      dyctPapelTrabajoDTO.getDescripcion(),
                                                      dyctPapelTrabajoDTO.getUrl(),
                                                      dyctPapelTrabajoDTO.getFechaRegistro(),
                                                      dyctPapelTrabajoDTO.getDyctExpedienteDTO().getServicioDTO().getNumControl() });
            } else {
                jdbcTemplateDYC.update(SQLOracleDyC.ADMINISTRARSOLICITUDES_REEMPLAZARPAPELTRABAJO.toString(),
                                       new Object[] { dyctPapelTrabajoDTO.getDescripcion(),
                                                      dyctPapelTrabajoDTO.getFechaRegistro(),
                                                      dyctPapelTrabajoDTO.getIdPapelTrabajo() });
            }
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.ADMINISTRARSOLICITUDES_INSERTARPAPELTRABAJO.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(dyctPapelTrabajoDTO));
            throw new SIATException(dae);
        }

    }

    @Override
    public void bajaPapelTrabajo(Integer idPapel) throws SIATException{


        try {

            jdbcTemplateDYC.update(SQLOracleDyC.ADMINISTRARSOLICITUDES_ELIMINARPAPELTRABAJO.toString(), new Object[] { idPapel });
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.ADMINISTRARSOLICITUDES_ELIMINARPAPELTRABAJO.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      " idPapel " + idPapel);
            throw new SIATException(dae);
        }
    }

    @Override
    public List<DyctPapelTrabajoDTO> selecXServicio(DycpServicioDTO servicio) {
        String query = " SELECT * FROM DYCT_PAPELTRABAJO WHERE NUMCONTROL = ? ";
        PapelTrabajoMapper mapper = new PapelTrabajoMapper();
        return jdbcTemplateDYC.query(query, new Object[] { servicio.getNumControl() }, mapper);
    }

    @Override
    public Integer insertar(DyctPapelTrabajoDTO papelTrabajo) {
        Integer idPapelTrabajo = null;
        try {
            idPapelTrabajo =
                    this.jdbcTemplateDYC.queryForObject("SELECT DYCQ_IDPAPELTRABAJO.NEXTVAL FROM DUAL", Integer.class);

            String sentInsert =
                "INSERT INTO DYCT_PAPELTRABAJO(NOMBREARCHIVO, DESCRIPCION, URL, FECHAREGISTRO, NUMCONTROL, FECHABAJA, IDPAPELTRABAJO) VALUES(?, ?, ?, ?, ?, ?, ?) ";

            int[] tiposDatos = new int[ConstantesDyCNumerico.VALOR_7];
            tiposDatos[ConstantesDyCNumerico.VALOR_0] = Types.VARCHAR;
            tiposDatos[ConstantesDyCNumerico.VALOR_1] = Types.VARCHAR;
            tiposDatos[ConstantesDyCNumerico.VALOR_2] = Types.VARCHAR;
            tiposDatos[ConstantesDyCNumerico.VALOR_3] = Types.DATE;
            tiposDatos[ConstantesDyCNumerico.VALOR_4] = Types.VARCHAR;
            tiposDatos[ConstantesDyCNumerico.VALOR_5] = Types.DATE;
            tiposDatos[ConstantesDyCNumerico.VALOR_6] = Types.INTEGER;

            jdbcTemplateDYC.update(sentInsert,
                                   new Object[] { papelTrabajo.getNombreArchivo(), papelTrabajo.getDescripcion(),
                                                  papelTrabajo.getUrl(), papelTrabajo.getFechaRegistro(),
                                                  papelTrabajo.getDyctExpedienteDTO().getServicioDTO().getNumControl(),
                                                  papelTrabajo.getFechaBaja(), idPapelTrabajo }, tiposDatos);
        } catch (Exception e) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO + ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(papelTrabajo));
        }
        return idPapelTrabajo;
    }

    @Override
    public List<DyctPapelTrabajoDTO> selecXServicioFechabajanula(DycpServicioDTO servicio) {
        log.info("DyctPapelTrabajoDAOImpl INICIO selecXServicioFechabajanula");
        String query = " SELECT * FROM DYCT_PAPELTRABAJO WHERE NUMCONTROL = ? AND FECHABAJA IS NULL ";
        PapelTrabajoMapper mapper = new PapelTrabajoMapper();
        return jdbcTemplateDYC.query(query, new Object[] { servicio.getNumControl() }, mapper);
    }

    @Override
    public void actualizarFechaBaja(DyctPapelTrabajoDTO papelTrabajo) {
        String sentUpdate = " UPDATE DYCT_PAPELTRABAJO SET FECHABAJA = ? WHERE IDPAPELTRABAJO = ? ";
        jdbcTemplateDYC.update(sentUpdate,
                               new Object[] { papelTrabajo.getFechaBaja(), papelTrabajo.getIdPapelTrabajo() });
    }

    @Override
    public Integer buscaPapelTrabajo(String numControl, String papel) {
        return jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTA_PAPELTRABAJO_NUMCTRLYARCHIVO.toString(),
                                              new Object[] { numControl, papel }, Integer.class);
    }

    @Override
    public void actualizarUrl(String numControl, String url) {
        String sql = SQLOracleDyC.UPDATE_PAPELTRABAJO + url + SQLOracleDyC.WHERE_PAPELTRABAJO;
        jdbcTemplateDYC.update(sql, new Object[] { numControl });
    }

    @Override
    public List<DyctPapelTrabajoDTO> selecXExpediente(DyctExpedienteDTO expediente) {
        String query = " SELECT * FROM DYCT_PAPELTRABAJO WHERE NUMCONTROL = ? ";
        PapelTrabajoMapper mapper = new PapelTrabajoMapper();
        mapper.setExpediente(expediente);
        return jdbcTemplateDYC.query(query, new Object[] { expediente.getServicioDTO().getNumControl() }, mapper);
    }

    @Override
    public DyctPapelTrabajoDTO encontrar(Integer idPapelTrabajo) {
        String query = " SELECT * FROM DYCT_PAPELTRABAJO WHERE IDPAPELTRABAJO = ? ";
        PapelTrabajoMapper mapper = new PapelTrabajoMapper();
        return jdbcTemplateDYC.queryForObject(query, new Object[] { idPapelTrabajo }, mapper);
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }
}
