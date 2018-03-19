/*
* Todos los Derechos Reservados 2016 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.dao.accion.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.accion.DyctAccionMovSalDAO;
import mx.gob.sat.siat.dyc.dao.documento.impl.mapper.DyctAccionPrivAjusMapper;
import mx.gob.sat.siat.dyc.dao.movsaldo.impl.mapper.DyctAccionMovSalMapper;
import mx.gob.sat.siat.dyc.dao.movsaldo.impl.mapper.DyctMovSaldoMapper;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctAccionMovSalDTO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 *
 * @author softtek
 */
@Repository
public class DyctAccionMovSalDAOImpl implements DyctAccionMovSalDAO
{
    private static final Logger LOG = Logger.getLogger(DyctAccionMovSalDAOImpl.class);
    
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;
     
    @Override
    public int insertar(DyctAccionMovSalDTO accionMovSal)
    {
        LOG.debug("INICIO insertar ->" + accionMovSal);
        String sentInsert = " INSERT INTO DYCT_ACCIONMOVSAL " +
                            " (IDACCIONMOVSAL, IDMOVSALDO, IDTIPOACCIONMOVSAL, FECHAREGISTRO, JUSTIFICACION, IDACCIONPRIVAJUS)" +
                            " VALUES (?, ?, ?, ?, ?, ?)";

        accionMovSal.setIdAccionMovSal(jdbcTemplateDYC.queryForObject("SELECT DYCQ_IDACCIONMOVSAL.NEXTVAL FROM DUAL",
                                                                  Integer.class));

        LOG.debug("accionMovSal.getDyctMovSaldoDTO().getIdMovSaldo() ->" + accionMovSal.getDyctMovSaldoDTO().getIdMovSaldo());
        LOG.debug("accionMovSal.getTipoAccionMovSal().getId() ->" + accionMovSal.getTipoAccionMovSal().getId());
        
        return jdbcTemplateDYC.update(sentInsert, new Object[] {accionMovSal.getIdAccionMovSal(),
                                                                accionMovSal.getDyctMovSaldoDTO().getIdMovSaldo(),
                                                                accionMovSal.getTipoAccionMovSal().getId(),
                                                                accionMovSal.getFechaRegistro(),
                                                                accionMovSal.getJustificacion(),
                                                                accionMovSal.getDyctAccionPrivAjusDTO().getIdAccionPrivAjus()});
    }

    @Override
    public List<DyctAccionMovSalDTO> seleccionarOrder(String orderBy)
    {
        StringBuilder query = new StringBuilder(" SELECT * FROM DYCT_ACCIONMOVSAL ACC INNER JOIN DYCT_MOVSALDO MOV ON MOV.IDMOVSALDO = ACC.IDMOVSALDO ");
        query.append(orderBy);

        DyctAccionMovSalMapper mapper = new DyctAccionMovSalMapper();
        mapper.setMapperMovSaldo(new DyctMovSaldoMapper());

        return jdbcTemplateDYC.query(query.toString(), mapper);
    }
    
    @Override
    public List<DyctAccionMovSalDTO> selecBitacora()
    {
        String query =  " SELECT REGS.*, ROWNUM FROM(\n" +
                        " SELECT ACC.*, PRIV.NUMEMPLEADO, PRIV.RESPPRIV, PRIV.FECHAREGISTROPRIV, PRIV.TIPOACCIONPRIV,\n" +
                        " EMP.RFC RFC_EMP, EMP.NOMBRE_COMPLETO NOMBRE_COMPLETO_EMP, EMP.CENTRO_COSTO_DESCR CENTRO_COSTO_DESCR_EMP,\n" +
                        " RESP.RFC RFC_RESP, RESP.NOMBRE_COMPLETO NOMBRE_COMPLETO_RESP, RESP.CENTRO_COSTO_DESCR CENTRO_COSTO_DESCR_RESP,\n" +
                        " MOV.IDSALDOICEP, MOV.IMPORTE, MOV.FECHAREGISTRO FECHAREGISTRO_MOVSALDO, MOV.FECHAORIGEN, " + 
                        " MOV.IDMOVIMIENTO, MOV.IDAFECTACION, MOV.IDORIGEN, MOV.FECHAFIN\n" +
                        " FROM DYCT_ACCIONMOVSAL ACC INNER JOIN DYCT_MOVSALDO MOV ON MOV.IDMOVSALDO = ACC.IDMOVSALDO \n" +
                        " INNER JOIN DYCT_ACCIONPRIVAJUS PRIV ON ACC.IDACCIONPRIVAJUS = PRIV.IDACCIONPRIVAJUS\n" +
                        " LEFT OUTER JOIN DYCV_EMPLEADO EMP ON PRIV.NUMEMPLEADO = TO_NUMBER(EMP.NO_EMPLEADO)\n" +
                        " LEFT OUTER JOIN DYCV_EMPLEADO RESP ON PRIV.RESPPRIV = TO_NUMBER(RESP.NO_EMPLEADO)\n" +
                        " ORDER BY ACC.FECHAREGISTRO DESC) REGS\n" +
                        " WHERE ROWNUM <= 100";

        DyctAccionMovSalMapper mapper = new DyctAccionMovSalMapper();
        mapper.setMapperMovSaldo(new DyctMovSaldoMapper());
        DyctAccionPrivAjusMapper mapperPrivilegios = new DyctAccionPrivAjusMapper();
        mapperPrivilegios.setMapearUsuarios(Boolean.TRUE);
        mapper.setMapperPrivilegios(mapperPrivilegios);

        return jdbcTemplateDYC.query(query, mapper);
    }

    @Override
    public List<DyctAccionMovSalDTO> selecOrderXSaldoicep(DyctSaldoIcepDTO saldoIcep, String orderBy)
    {
        StringBuilder sbQuery = new StringBuilder(" SELECT * FROM DYCT_ACCIONMOVSAL ACCMOV INNER JOIN DYCT_MOVSALDO MOV ON MOV.IDMOVSALDO = ACCMOV.IDMOVSALDO ");
        sbQuery.append(" INNER JOIN DYCT_ACCIONPRIVAJUS PRIV ON PRIV.IDACCIONPRIVAJUS = ACCMOV.IDACCIONPRIVAJUS " );
        sbQuery.append(" LEFT OUTER JOIN DYCV_EMPLEADO EMP ON TO_NUMBER(EMP.NO_EMPLEADO) = PRIV.NUMEMPLEADO ");
        sbQuery.append(" WHERE IDSALDOICEP = ? ");
        sbQuery.append(orderBy);
         
        DyctMovSaldoMapper mapperMovSaldo = new DyctMovSaldoMapper();
        DyctAccionPrivAjusMapper mapperPrivilegios = new DyctAccionPrivAjusMapper();
        mapperPrivilegios.setMapearEmpleado(Boolean.TRUE);
        DyctAccionMovSalMapper mapper = new DyctAccionMovSalMapper();
        mapper.setMapperMovSaldo(mapperMovSaldo);
        mapper.setMapperPrivilegios(mapperPrivilegios);

        return jdbcTemplateDYC.query(sbQuery.toString(), new Object[]{ saldoIcep.getIdSaldoIcep() }, mapper);
    }
}
