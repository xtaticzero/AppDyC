package mx.gob.sat.mat.dyc.compensaciones.dao.impl;

import java.util.Date;
import java.util.List;
import mx.gob.sat.mat.dyc.compensaciones.dao.CreaCasoCompDWHDAO;
import mx.gob.sat.siat.dyc.casocomp.dao.districomp.impl.mapper.CrearCasoCompMapper;
import mx.gob.sat.siat.dyc.casocomp.dto.districomp.CasoCompensacionVO;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.constante.SQLOracleSIAT;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class CreaCasoCompDWHDAOImpl implements SQLOracleSIAT, CreaCasoCompDWHDAO
{
    private static final Logger LOG = Logger.getLogger(CreaCasoCompDWHDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplateDWHDYP;

    private static final String CAT_CONCEPTO = "PS_CI_OBL_CCAOTNA1";

    @Override
    @Transactional("TMdataSourceDWHDYP")
    public List<CasoCompensacionVO> selecXDeclaracion(Date fechaBuscar)
    {

                String query =  "SELECT  \n" +
        "        'NORMAL' as ESTADO, \n" +
        "        a.c_ide_rfceeog1 as RFC,                  \n" +
        "        a.n_dec_ncuamse1 as idDeclaracion,        \n" +
        "        a.n_dec_noupmee1 as NUMEROOPERACION,      \n" +
        "        a.c_dec_ctdliea1 as TIPODECLARACION,      \n" +
        "        a.f_dec_fperceh1 as FECHAPRESENTACION,    \n" +
        "        a.n_dec_ejercic1 as EJERCICIO,            \n" +
        "        a.c_dec_cplearv1 as PERIODO,               \n" +
        "        a.f_dec_fceacrh1 as FECHACARGA,          \n" +
        "        b.c_obl_ccdloea1 as CONCEPTO,            \n" +
        "        b.i_dec_isqamau1 as IMPORTECOMPENSADO,   \n" +
        "        case b.c_dec_ctclioa2   \n" +
        "        when 'SAF' then '1'     \n" +
        "        when 'PDI' then '2'  \n" +
        "        else ''  \n" +
        "        end as OrigenSaldo,     \n" +
        "        b.c_dec_cplearv1 as PERIOSALDOFAVOR,          \n" +
        "        b.n_dec_ejercic1 as EJERCSALDOFAVOR,          \n" +
        "        b.f_dec_fcieamc1 as FECHACAUSACION,           \n" +
        "        b.c_obl_ccloanv1 as CONCESALDOFAVOR,          \n" +
        "        b.i_dec_iosfmra1 as MONTOSALDOFAVOR,          \n" +
        "        b.f_dec_fddmsfe1 as FECHAPRESENTDECLARACION,  \n" +
        "        b.I_dec_rvhaaea1 as REMANENTEHISTORICO,       \n" +
        "        b.i_dec_rvaaaea1 as REMANENTEACTUALIZADO,     \n" +
        "        c.C_OBL_CTCLIOA1 as IMPUESTO                  \n" +
        "        FROM  ps_dd_dec_diadteo1 a, ps_dd_dec_dcdpeoe1 b, " + CAT_CONCEPTO + " c  \n" +
        "        WHERE a.c_ide_rfceeog1 = b.c_ide_rfceeog1 and  \n" +
        "        a.n_dec_ncuamse1 = b.n_dec_ncuamse1 and \n" +
        "        b.c_obl_ccdloea1 = c.C_OBL_CCLOANV1 (+) and  \n" +
        "        b.i_dec_iosfmra1 > 0  \n" +
           /*  " and trunc(a.f_dec_fperceh1 )=trunc(?) "+   */
                 "        AND ROWNUM <10   \n" +
    /*" "  and a.n_dec_ncuamse1=50152829    AND ROWNUM <20 "+ 
      "      and  a.c_ide_rfceeog1 = 'MIN931015ES9' \n" + 
            a.f_dec_fceacrh1 >= to_date('17-MAR-13')-1 and a.f_dec_fceacrh1 < to_date('17-MAR-13')  \n" + */
        "        group by 'NORMAL', a.c_ide_rfceeog1, a.n_dec_ncuamse1, a.n_dec_noupmee1, a.c_dec_ctdliea1, a.f_dec_fperceh1, " +
                    " a.n_dec_ejercic1, a.c_dec_cplearv1, a.f_dec_fceacrh1, b.c_obl_ccdloea1, case b.c_dec_ctclioa2   \n" +
        "        when 'SAF' then '1'  " +
        "        when 'PDI' then '2'  " +
        "        else ''  \n" +
        "        end, b.c_dec_cplearv1, b.n_dec_ejercic1, b.f_dec_fcieamc1, b.c_obl_ccloanv1, b.i_dec_isqamau1, b.i_dec_iosfmra1, " +
        "        b.f_dec_fddmsfe1, b.I_dec_rvhaaea1, b.i_dec_rvaaaea1, c.C_OBL_CTCLIOA1";

        try{
            return jdbcTemplateDWHDYP.query(query,new CrearCasoCompMapper());
        }catch(DataAccessException dae){
            LOG.error("Ocurrio un error al ejecutar la consulta para extraer los casos de compensacion en DWH-DYP; mensaje ->" + dae.getMessage());
            ManejadorLogException.getTraceError(dae);
            throw dae;
        }
    }

}
