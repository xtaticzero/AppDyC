package mx.gob.sat.siat.dyc.vistas.dao.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.vistas.DycvEmpleadoDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesIds;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;
import mx.gob.sat.siat.dyc.vistas.dao.DycvEmpleadoDAO;
import mx.gob.sat.siat.dyc.vistas.dao.impl.mapper.AdmcUnidadAdmDomMapper;
import mx.gob.sat.siat.dyc.vistas.dao.impl.mapper.AdmcUnidadAdmvaMapper;
import mx.gob.sat.siat.dyc.vistas.dao.impl.mapper.DycvEmpleadoMapper;
import mx.gob.sat.siat.dyc.vistas.dao.impl.mapper.DycvEmpleadoVOMapper;
import mx.gob.sat.siat.dyc.vistas.dao.impl.mapper.NivelDireccionMapper;
import mx.gob.sat.siat.dyc.vistas.vo.DycvEmpleadoVO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;


@Repository(value = "dycvEmpleadoDAO")
public class DycvEmpleadoDAOImpl implements DycvEmpleadoDAO{
    
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private static final String CONSULTA_DYCV_EMPLEADO =  " SELECT * FROM DYCV_EMPLEADO  ";
    private static final String WHERE = " WHERE ";
    private static final String RFC_EMPLEADO = "  RFC = ?", NUM_EMPLEADO = "  NO_EMPLEADO = ? ";
    
    private static final Logger LOG = Logger.getLogger(DycvEmpleadoDAOImpl.class);

    private List<DycvEmpleadoVO> listaEmpleado;

    @Override
    public DycvEmpleadoDTO consultarXIDEmpleado(Integer idEmpleado) throws SIATException
    {
        try {
            return jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTA_SATSIAT01MV_X_IDEMPLEADO.toString(), new Object[] {idEmpleado},
                                                   new DycvEmpleadoMapper());
        } 
        catch(EmptyResultDataAccessException emptyEx)
        {
            LOG.info("No se encontro ningun empleado con el numero de empleado ->" + idEmpleado + "<-");
            return null;
        }
        catch (Exception e) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTA_SATSIAT01MV_X_IDEMPLEADO + ConstantesDyC1.TEXTO_3_ERROR_DAO + " idEmpleado: " +
                      idEmpleado);
            throw new SIATException(e);
        }
    }

    @Override
    public DycvEmpleadoVO consultaInformacionEmpleado(DycvEmpleadoVO empleado) throws SIATException {
        StringBuilder consulta = new StringBuilder();
        DycvEmpleadoVO empleado2 = null;
        
        try {
            NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplateDYC);
            SqlParameterSource sqlNamedParameters = new BeanPropertySqlParameterSource(empleado);
            consulta.append(SQLOracleDyC.CONSULTA_INFO_EMPLEADO);

            consulta.replace(consulta.indexOf(ConstantesDyC1.INNER),
                             (consulta.indexOf(ConstantesDyC1.INNER) + ConstantesDyC1.INNER.length()), "        ");
            consulta.replace(consulta.indexOf(ConstantesDyC1.ROWS),
                             (consulta.indexOf(ConstantesDyC1.ROWS) + ConstantesDyC1.ROWS.length()), "     ");

            if (null != empleado.getClaveAdm()) {
                consulta = formatoAConsulta(empleado, consulta);
            }
            consulta.replace(consulta.indexOf(ConstantesDyC1.AND),
                             consulta.indexOf(ConstantesDyC1.AND) + ConstantesDyC1.AND.length(), "    ");

            this.listaEmpleado = template.query(consulta.toString(), sqlNamedParameters, new DycvEmpleadoVOMapper());
            if (null != this.listaEmpleado && ConstantesDyC1.CERO < listaEmpleado.size()) {
                empleado2 = this.listaEmpleado.get(ConstantesDyC1.CERO);
            }
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO + consulta +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(empleado));
            throw new SIATException(dae);
        }
        return empleado2;
    }
    
    /**
     * 
     *
     * @param empleado
     * @param consulta
     * @return
     */
    public StringBuilder formatoAConsulta(DycvEmpleadoVO empleado, StringBuilder consulta) {
        if (empleado.getClaveAdm() == ConstantesIds.ACFECF || empleado.getClaveAdm() == ConstantesIds.ACFSF ||
            empleado.getClaveAdm() == ConstantesIds.ACFGCD) {
            consulta.replace(consulta.indexOf(ConstantesDyC1.AND),
                             (consulta.indexOf(ConstantesDyC1.AND) + ConstantesDyC1.AND.length()),
                             SQLOracleDyC.CONSULTA_IS_ACFGC + ConstantesDyC1.AND);
        }   
        return consulta;
    }

    public List<DycvEmpleadoVO> consultaNivelDireccion() throws SIATException {
        try {
            this.listaEmpleado =
                    this.jdbcTemplateDYC.query(SQLOracleDyC.CONSULTA_DIRECCION_NIVEL.toString(), new NivelDireccionMapper());
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTA_DIRECCION_NIVEL);
            throw new SIATException(dae);
        }
        return this.listaEmpleado;
    }
    
    @Override
    public DycvEmpleadoDTO encontrar(Integer numEmpleado)
    {
        String query =  " SELECT * FROM DYCV_EMPLEADO WHERE NO_EMPLEADO = ? ";
        try
        {
            return jdbcTemplateDYC.queryForObject(query, new Object[] {numEmpleado}, new DycvEmpleadoMapper());
        }
        catch(EmptyResultDataAccessException emptyEx)
        {
            LOG.info("No se encontro ningun empleado con el numero de empleado ->" + numEmpleado + "<-");
            return null;
        }
    }

    @Override
    public DycvEmpleadoDTO encontrarxRfcoNum(Object param)
    {
        StringBuilder query = new StringBuilder(CONSULTA_DYCV_EMPLEADO).append(WHERE);
        query.append(param instanceof String ? RFC_EMPLEADO : NUM_EMPLEADO);
        
        try
        {
            return jdbcTemplateDYC.queryForObject(query.toString(), new Object[] {param}, 
                    new DycvEmpleadoMapper());
        }
        catch(EmptyResultDataAccessException emptyEx)
        {
            LOG.info("No se encontro ningun empleado con el rfc/num. empleado ->" + param + "<-");
            return null;
        }
    }
    
    public void setListaEmpleado(List<DycvEmpleadoVO> listaEmpleado) {
        this.listaEmpleado = listaEmpleado;
    }

    public List<DycvEmpleadoVO> getListaEmpleado() {
        return listaEmpleado;
    }

    @Override
    public DycvEmpleadoDTO selecXRfc(String rfc){
        String query =  " SELECT * FROM DYCV_EMPLEADO E LEFT OUTER JOIN DYCC_UNIDADADMVA A ON E.CENTRO_COSTO = A.CLAVE_AGRS " + 
                        " LEFT OUTER JOIN DYCC_UNIDADADMDOM DA ON DA.IDUNIDADADMDOM = A.IDUNIDADADMDOM  WHERE E.RFC = ? AND ROWNUM = 1";
        try
        {
            AdmcUnidadAdmDomMapper mapperDomicilio = new AdmcUnidadAdmDomMapper();
            AdmcUnidadAdmvaMapper mapperUnidadAdmva = new AdmcUnidadAdmvaMapper();
            mapperUnidadAdmva.setMapperAdmcUnidadAdmDom(mapperDomicilio);
            DycvEmpleadoMapper mapper = new DycvEmpleadoMapper();
            mapper.setMapperAdmcUnidadAdmva(mapperUnidadAdmva);
            return jdbcTemplateDYC.queryForObject(query, new Object[] {rfc}, mapper);
        } 
        catch(EmptyResultDataAccessException exEmpty)
        {
            LOG.info("No se encontro ningun empleado con el RFC ->" + rfc + "<-");
            return null;
        }
    }

    @Override
    public DycvEmpleadoDTO selecXRfccorto(String rfcCorto)
    {
        String query =  " SELECT * FROM DYCV_EMPLEADO E LEFT OUTER JOIN DYCC_UNIDADADMVA A ON E.CENTRO_COSTO = A.CLAVE_AGRS " + 
                        " LEFT OUTER JOIN DYCC_UNIDADADMDOM DA ON DA.IDUNIDADADMDOM = A.IDUNIDADADMDOM  WHERE E.RFC_CORTO = ? ";
        try
        {
            AdmcUnidadAdmDomMapper mapperDomicilio = new AdmcUnidadAdmDomMapper();
            AdmcUnidadAdmvaMapper mapperUnidadAdmva = new AdmcUnidadAdmvaMapper();
            mapperUnidadAdmva.setMapperAdmcUnidadAdmDom(mapperDomicilio);
            DycvEmpleadoMapper mapper = new DycvEmpleadoMapper();
            mapper.setMapperAdmcUnidadAdmva(mapperUnidadAdmva);
            return jdbcTemplateDYC.queryForObject(query, new Object[] {rfcCorto}, mapper);
        }
        catch(EmptyResultDataAccessException exEmpty)
        {
            LOG.info("No se encontro ningun empleado con el rfcCorto ->" + rfcCorto + "<-");
            return null;
        }
    }
}
