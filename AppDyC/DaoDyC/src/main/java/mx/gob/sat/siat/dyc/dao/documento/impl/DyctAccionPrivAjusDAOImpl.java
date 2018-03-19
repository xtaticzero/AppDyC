package mx.gob.sat.siat.dyc.dao.documento.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.documento.DyctAccionPrivAjusDAO;
import mx.gob.sat.siat.dyc.dao.documento.impl.mapper.DyctAccionPrivAjusMapper;
import mx.gob.sat.siat.dyc.domain.movsaldo.DyctAccionPrivAjusDTO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 *
 * @author softtek
 */
@Repository
public class DyctAccionPrivAjusDAOImpl implements DyctAccionPrivAjusDAO
{
    private static final Logger LOG = Logger.getLogger(DyctAccionPrivAjusDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    @Override
    public int insertar(DyctAccionPrivAjusDTO accionPrivAjus) {
        LOG.debug("INICIO insertar ->" + accionPrivAjus);
        String sentInsert = " INSERT INTO DYCT_ACCIONPRIVAJUS " +
                            " (IDACCIONPRIVAJUS, NUMEMPLEADO, RESPPRIV, FECHAREGISTROPRIV, TIPOACCIONPRIV)" +
                            " VALUES (?, ?, ?, ?, ?)";

        accionPrivAjus.setIdAccionPrivAjus(jdbcTemplateDYC.queryForObject("SELECT DYCQ_IDACCIONPRIVAJUS.NEXTVAL FROM DUAL",
                                                                  Integer.class));

        return jdbcTemplateDYC.update(sentInsert, new Object[] {accionPrivAjus.getIdAccionPrivAjus(),
                                                                accionPrivAjus.getNumEmpleado(),
                                                                accionPrivAjus.getRespPriv(),
                                                                accionPrivAjus.getFechaRegistroPriv(),
                                                                accionPrivAjus.getTipoAccionPriv()});
    }

    @Override
    public List<DyctAccionPrivAjusDTO> seleccionar() {
        String query =  " SELECT ACCPRIV.*, EMP.RFC RFC_EMP, EMP.NOMBRE_COMPLETO NOMBRE_COMPLETO_EMP, EMP.CENTRO_COSTO_DESCR CENTRO_COSTO_DESCR_EMP,\n" +
                        " RESP.RFC RFC_RESP, RESP.NOMBRE_COMPLETO NOMBRE_COMPLETO_RESP, RESP.CENTRO_COSTO_DESCR CENTRO_COSTO_DESCR_RESP\n" +
                        " FROM DYCT_ACCIONPRIVAJUS ACCPRIV LEFT OUTER JOIN DYCV_EMPLEADO EMP ON EMP.NO_EMPLEADO = ACCPRIV.NUMEMPLEADO\n" +
                        " LEFT OUTER JOIN DYCV_EMPLEADO RESP ON RESP.NO_EMPLEADO = ACCPRIV.RESPPRIV";

        DyctAccionPrivAjusMapper mapper = new DyctAccionPrivAjusMapper();
        mapper.setMapearUsuarios(Boolean.TRUE);
        return jdbcTemplateDYC.query(query, mapper);
    }

    @Override
    public List<DyctAccionPrivAjusDTO> selecUltimasAccionesXEmpleado() 
    {
        String query =  " SELECT ACC.* , EMP.RFC RFC_EMP, EMP.NOMBRE_COMPLETO NOMBRE_COMPLETO_EMP, EMP.CENTRO_COSTO_DESCR CENTRO_COSTO_DESCR_EMP,\n" +
                        " RESP.RFC RFC_RESP, RESP.NOMBRE_COMPLETO NOMBRE_COMPLETO_RESP, RESP.CENTRO_COSTO_DESCR CENTRO_COSTO_DESCR_RESP\n" +
                        " FROM (SELECT MAX(FECHAREGISTROPRIV) FECHAACCION, NUMEMPLEADO FROM DYCT_ACCIONPRIVAJUS GROUP BY NUMEMPLEADO) ULTIMAS_ACC\n" +
                        " INNER JOIN DYCT_ACCIONPRIVAJUS ACC ON ACC.NUMEMPLEADO = ULTIMAS_ACC.NUMEMPLEADO AND ACC.FECHAREGISTROPRIV = ULTIMAS_ACC.FECHAACCION\n" +
                        " LEFT OUTER JOIN DYCV_EMPLEADO EMP ON EMP.NO_EMPLEADO = ACC.NUMEMPLEADO\n" +
                        " LEFT OUTER JOIN DYCV_EMPLEADO RESP ON RESP.NO_EMPLEADO = ACC.RESPPRIV";

        DyctAccionPrivAjusMapper mapper = new DyctAccionPrivAjusMapper();
        mapper.setMapearUsuarios(Boolean.TRUE);
        return jdbcTemplateDYC.query(query, mapper);
    }

    @Override
    public DyctAccionPrivAjusDTO selecUltimaAccionEmpleado(Integer numEmpleado)
    {
        try{
            String query =  " SELECT * FROM DYCT_ACCIONPRIVAJUS WHERE FECHAREGISTROPRIV =\n" +
                            " (SELECT MAX(FECHAREGISTROPRIV) FROM DYCT_ACCIONPRIVAJUS WHERE NUMEMPLEADO = ?)\n" +
                            " AND NUMEMPLEADO = ?";

            return jdbcTemplateDYC.queryForObject(query, new Object[]{numEmpleado, numEmpleado}, new DyctAccionPrivAjusMapper());
        }
        catch (EmptyResultDataAccessException edea){
            LOG.info("No existen permisos para el empleado ->" + numEmpleado);
            return null;
        }
    }

}
