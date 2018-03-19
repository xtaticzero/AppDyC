package mx.gob.sat.siat.dyc.casocomp.dao.districomp.impl;

import mx.gob.sat.siat.dyc.casocomp.dao.districomp.DistribuirCompDAO;
import mx.gob.sat.siat.dyc.casocomp.dto.districomp.DistribuirCompVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository(value = "distribuirCompDAO")
public class DistribuirCompDAOImpl implements DistribuirCompDAO
{
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    @Override
    public void actualizarEstado(Integer estado, String numControl)
    {
        String sql = "update dycp_compensacion set idestadocomp = ? where numcontrol = ? ";
        jdbcTemplateDYC.update(sql, new Object[] {estado, numControl });
    }

    @Override
    public Integer encontrarIcep(DistribuirCompVO distribuirCompDTO){
        
        String sqlCC =
            "select count(*) from dycp_servicio dycps, dycp_compensacion dycp, dycc_tipotramite dycc, dyct_saldoicep dyct_s\n" +
            "                where dycp.numcontrol = dycps.numcontrol and\n" +
            "                dycc.idtipotramite = dycps.idtipotramite and\n" +
            "                dycps.rfccontribuyente = dyct_s.rfc and\n" +
            "                dycp.idsaldoiceporigen = dyct_s.idsaldoicep and\n" +
            "                dycps.rfccontribuyente = ? and \n" +
            "                dyct_s.idconcepto = ? and\n" +
            "                dyct_s.idejercicio = ? and\n" +
            "                dyct_s.idperiodo = ? and\n" +
            "                dycp.idestadocomp = 2";
        
        return jdbcTemplateDYC.queryForObject(sqlCC, new Object[] { distribuirCompDTO.getRfcContribunyente(), distribuirCompDTO.getConcepto(),
                                                                 distribuirCompDTO.getEjercicio(),
                                                                 distribuirCompDTO.getPeriodo() }, Integer.class);
    }
    

}


