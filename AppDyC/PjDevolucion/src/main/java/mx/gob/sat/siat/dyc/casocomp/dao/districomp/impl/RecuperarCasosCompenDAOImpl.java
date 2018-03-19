/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.casocomp.dao.districomp.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.casocomp.dao.districomp.RecuperarCasosCompenDAO;
import mx.gob.sat.siat.dyc.casocomp.dao.districomp.impl.mapper.CrearCasoCompMapper;
import mx.gob.sat.siat.dyc.casocomp.dto.districomp.CasoCompensacionVO;
import mx.gob.sat.siat.dyc.domain.caso.DyctCasoPendienteDTO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/


/**
 * RecuperarCasosCompenDAOImpl
 * @author [LADP] Luis Alberto Dominguez Palomino
 *
 */
@Repository(value = "recuperarCasosCompenDAO")
public class RecuperarCasosCompenDAOImpl implements RecuperarCasosCompenDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateSIAT;

    private Logger log = Logger.getLogger(CreaCasoCompenDAOImpl.class.getName());

    public RecuperarCasosCompenDAOImpl() {
        super();
    }

    public void setJdbcTemplateSIAT(JdbcTemplate jdbcTemplateSIAT) {
        this.jdbcTemplateSIAT = jdbcTemplateSIAT;
    }

    public JdbcTemplate getJdbcTemplateSIAT() {
        return jdbcTemplateSIAT;
    }

    public void setLog(Logger log) {
        this.log = log;
    }

    public Logger getLog() {
        return log;
    }

    @Override
    public List<CasoCompensacionVO> obtenDeclaracionesPorIdDeclaracion(DyctCasoPendienteDTO dyctCasoPenDTO) {
        String sql = "select \n" + 
        "                         'TEMPORAL' as ESTADO,\n" + 
        "                          a.c_ide_rfceeog1 as RFC,\n" + 
        "                          a.n_dec_ncuamse1 as idDeclaracion,\n" + 
        "                          a.n_dec_noupmee1 as NUMEROOPERACION,\n" + 
        "                          a.c_dec_ctdliea1 as TIPODECLARACION,\n" + 
        "                          a.f_dec_fperceh1 as FECHAPRESENTACION,\n" + 
        "                          b.i_dec_isqamau1 as IMPORTECOMPENSADO,\n" + 
        "                          c.c_idc_ctclioa1 as IMPUESTO,\n" + 
        "                          b.c_obl_ccdloea1 as CONCEPTO,\n" + 
        "                          a.n_dec_ejercic1 as EJERCICIO,\n" + 
        "                          a.c_dec_cplearv1 as PERIODO,\n" + 
        "                          a.f_dec_fceacrh1 as FECHACARGA,\n" + 
        "                          case b.c_dec_ctclioa2\n" + 
        "                          when 'SAF' then '1'\n" + 
        "                          when 'PDI' then '2'\n" + 
        "                          else ''\n" + 
        "                          end as OrigenSaldo,\n" + 
        "                          b.c_dec_cplearv1 as PERIOSALDOFAVOR,\n" + 
        "                          b.n_dec_ejercic1 as EJERCSALDOFAVOR,\n" + 
        "                          b.f_dec_fcieamc1 as FECHACAUSACION,\n" + 
        "                          b.c_obl_ccloanv1 as CONCESALDOFAVOR,\n" + 
        "                          b.i_dec_isqamau1 as SALDOAPLICAR,\n" + 
        "                          b.i_dec_iosfmra1 as MONTOSALDOFAVOR,\n" + 
        "                          b.f_dec_fddmsfe1 as FECHAPRESENTDECLARACION,\n" + 
        "                          b.I_dec_rvhaaea1 as REMANENTEHISTORICO,\n" + 
        "                          b.i_dec_rvaaaea1 as REMANENTEACTUALIZADO\n" + 
        "                          from  ps_dd_dec_diadteo1 a, ps_dd_dec_dcdpeoe1 b, DWH_ODS.ci_idc_ccaotna1 c\n" + 
        "                          where a.c_ide_rfceeog1 = b.c_ide_rfceeog1 and\n" + 
        "                          a.n_dec_ncuamse1 = b.n_dec_ncuamse1 and\n" + 
        "                          b.c_obl_ccdloea1 = c.c_idc_ccloanv1 (+) and\n" + 
        "                          b.i_dec_iosfmra1 > 0 and\n" + 
        "                          a.n_dec_noupmee1 = ? and\n" + 
        "                          a.n_dec_ncuamse1 = ? and\n" + 
        "                          c.c_idc_ctclioa1 = ? and\n" + 
        "                          b.c_obl_ccdloea1 = ? and\n" + 
        "                          a.n_dec_ejercic1 = ? and\n" + 
        "                          a.c_dec_cplearv1 = ? ";
        
        return jdbcTemplateSIAT.query(sql, new Object[] { dyctCasoPenDTO.getNumOperacion(),
                                                          dyctCasoPenDTO.getIdDeclaracion(),
                                                          dyctCasoPenDTO.getIdImpuesto(),
                                                          dyctCasoPenDTO.getIdConcepto(),
                                                          dyctCasoPenDTO.getIdEjercicio(),
                                                          dyctCasoPenDTO.getIdPeriodo()}, new CrearCasoCompMapper());
    }

}
