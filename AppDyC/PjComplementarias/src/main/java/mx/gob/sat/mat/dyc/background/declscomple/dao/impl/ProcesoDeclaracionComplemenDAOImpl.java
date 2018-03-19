package mx.gob.sat.mat.dyc.background.declscomple.dao.impl;

import java.util.List;

import mx.gob.sat.mat.dyc.background.declscomple.dao.ProcesoDeclaracionComplemenDAO;
import mx.gob.sat.mat.dyc.background.declscomple.dao.impl.mapper.ProcesoDeclaracionComplemenMapper;
import mx.gob.sat.mat.dyc.background.declscomple.vo.DeclaracionComplementariaVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository(value = "procesoDeclaracionComplemenDAO")
public class ProcesoDeclaracionComplemenDAOImpl implements ProcesoDeclaracionComplemenDAO
{
    @Autowired
    private JdbcTemplate jdbcTemplateDWHDYP;

    @Override
    @Transactional("TMdataSourceDWHDYP")
    public List<DeclaracionComplementariaVO> selectXDeclaracionComplemen()
    {
        String query = "select a.C_IDE_RFCEEOG1 as rfc,\n" +
            "             a.N_DEC_NCUAMSE1 as iddeclaracion,\n" +
            "             a.N_DEC_NOUPMEE1 as numerooperacion,\n" +
            "             a.C_DEC_CTDLIEA1 as tipodeclaracion,\n" +
            "             a.C_DEC_CPLEARV1 as periodo,\n" +
            "             a.N_DEC_EJERCIC1 as ejercicio,\n" +
            "             a.F_DEC_FPERCEH1 as fechapresentacion,\n" +
            "             a.F_DEC_FCEACRH1 as fechacarga,       \n" +
            "             b.I_PAG_CFAANVT1 as cantidadafavor,\n" +
            "             c.I_PAG_IFMAPVU1 as saldoafavor,\n" +
            "             c.c_obl_ccloanv1 as concepto,\n" +
            "             d.c_OBL_ctclioa1 as impuesto\n" +
            "            from PS_DD_DEC_DIADTEO1 a, PS_DD_PAG_PIAMGPO1 b, PS_DD_DEC_ARIDCEM1 c, PS_CI_OBL_CCAOTNA1 d \n" +
            "            where a.c_ide_rfceeog1 = b.c_ide_rfceeog1 and\n" +
            "            b.c_ide_rfceeog1 = c.c_ide_rfceeog1 and\n" +
            "            a.n_dec_ncuamse1 = b.n_dec_ncuamse1 and\n" +
            "            b.n_dec_ncuamse1 = c.n_dec_ncuamse1 and\n" +
            "            b.c_obl_ccloanv1 = c.c_obl_ccloanv1 and\n" +
            "            c.c_obl_ccloanv1 = d.c_OBL_ccloanv1 \n" +
            "            /* and a.c_dec_ctdliea1 in (002,003,004,005) and\n" +
            "            a.c_ide_rfceeog1 in ('KPH970829IQ2')\n" +
            "            a.f_dec_fceacrh1 > to_date('26-MAR-13')-1 and a.f_dec_fceacrh1 < to_date('26-MAR-13') */\n" +
            "            order by a.C_IDE_RFCEEOG1, a.N_DEC_NCUAMSE1, a.N_DEC_NOUPMEE1";

        return jdbcTemplateDWHDYP.query(query, new ProcesoDeclaracionComplemenMapper());
    }

}
