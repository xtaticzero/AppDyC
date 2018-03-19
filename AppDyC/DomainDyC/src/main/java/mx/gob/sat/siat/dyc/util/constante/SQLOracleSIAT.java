/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.util.constante;

/**
 * Interface consultas origen SIAT
 * @author Luis Fernando Barrios Quiroz [ LuFerMX ]
 * @date Agosto 7, 2013
 * @since 0.1
 *
 * */
public interface SQLOracleSIAT {

    //***** QUERYS ORACLE SIAT *****

    String CONSULTAR_PAGOS_ELECTRONICOSDAO_INFORMACION_PAGO =
        " SELECT    a.f_dec_fcieamc1, a.F_DEC_FPERCEH1, b.I_PAG_ICMAPRU1, b.I_PAG_IFMAPVU1, b.I_PAG_ACPOLMI1," +
        "           a.C_DEC_CTDLIEA1" + " FROM      ps_dd_dec_diadteo1 a, ps_dd_pag_piamgpo1 b, ps_ci_dec_cpaetra1 c" +
        " WHERE     a.c_ide_rfceeog1 IN(?, ?, ?)" + " AND       a.c_ide_rfceeog1 = b.c_ide_rfceeog1" +
        " AND       a.n_dec_ncuamse1 = b.n_dec_ncuamse1" + " AND       b.c_dec_crleanv1 = ?" +
        " AND       a.c_dec_cplearv1=?" + " AND       a.n_dec_ejercic1=?";

    String CONSULTA_DECLARACION_PROVISIONAL_O_DEFINITIVA_DE_IMPUESTOSFED_IVA_CONSULTA_IMPUESTOS =
        "SELECT     a.n_dec_noupmee1 as v_n_dec_noupmee1, a.f_dec_fcieamc1 as v_f_dec_fcieamc1," +
        "           b.i_dec_taapt1i1 as v_i_dec_taapt1i1, b.i_dec_taapt1i2 as v_i_dec_taapt1i2," +
        "           b.i_dec_taapibs1 as v_i_dec_taapibs1, b.i_dec_taapibs2 as v_i_dec_taapibs2," +
        "           b.i_dec_tdaapt01 as v_i_dec_tdaapt01, b.i_dec_taapqsp1 as v_i_dec_taapqsp1," +
        "           c.i_dec_tiaapt11 as v_i_dec_tiaapt11, c.i_dec_tiaapt12 as v_i_dec_tiaapt12," +
        "           c.i_dec_tiaapib1 as v_i_dec_tiaapib1, c.i_dec_tiaapib2 as v_i_dec_tiaapib2," +
        "           c.i_dec_titacep1 as v_i_dec_titacep1, c.i_dec_itabdia1 as v_i_dec_itabdia1," +
        "           c.i_dec_ipiabdi1 as v_i_dec_ipiabdi1, c.i_dec_itaider1 as v_i_dec_itaider1," +
        "           c.i_dec_ipiider1 as v_i_dec_ipiider1, c.i_dec_ticaago1 as v_i_dec_ticaago1," +
        "           c.i_dec_itpiabd1 as v_i_dec_itpiabd1, c.i_dec_itpiide1 as v_i_dec_itpiide1," +
        "           c.i_dec_pucaa5l1 as v_i_dec_pucaa5l1, c.i_dec_pucaa5l2 as v_i_dec_pucaa5l2," +
        "           c.i_dec_iabuira1 as v_i_dec_iabuira1, c.i_dec_iavcare1 as v_i_dec_iavcare1," +
        "           c.i_dec_maaidao1 as v_i_dec_maaidao1, c.i_dec_tiapovc1 as v_i_dec_tiapovc1," +
        "           d.i_dec_vaagt1a1 as v_i_dec_vaagt1a1, d.i_dec_vaagt1a2 as v_i_dec_vaagt1a2," +
        "           d.i_dec_vaagt0e1 as v_i_dec_vaagt0e1, d.i_dec_vaagt0o1 as v_i_dec_vaagt0o1," +
        "           d.i_dec_saagucc1 as v_i_dec_saagucc1, d.i_dec_vaaqsdp1 as v_i_dec_vaaqsdp1," +
        "           d.i_dec_icmapuu1 as v_i_dec_icmapuu1, d.i_dec_cardaac1 as v_i_dec_cardaac1," +
        "           d.i_dec_iracvel1 as v_i_dec_iracvel1, d.i_dec_tiaovct1 as v_i_dec_tiaovct1," +
        "           d.i_dec_occctaa1 as v_i_dec_occctaa1, d.i_dec_ocfctaa1 as v_i_dec_ocfctaa1," +
        "           d.i_dec_ccaanrt1 as v_i_dec_ccaanrt1, d.i_dec_sfaalvd1 as v_i_dec_sfaalvd1," +
        "           d.i_dec_dioenbv1 as v_i_dec_dioenbv1, d.i_dec_sfpaael1 as v_i_dec_sfpaael1," +
        "           d.i_dec_asfpa_e1 as v_i_dec_asfpa_e1, d.i_dec_iaaadmi1 as v_i_dec_iaaadmi1," +
        "           d.i_dec_icmapru1 as v_i_dec_icmapru1, d.i_dec_rsfiaaa1 as v_i_dec_rsfiaaa1" +
        " FROM      ps_dd_dec_diadteo1 a, ps_dd_dec_iavamaa1 b, ps_dd_dec_divaaem1 c, ps_dd_dec_divaema1 d" +
        " WHERE     a.c_ide_rfceeog1 IN(?, ?, ?)" + " AND       a.n_dec_ejercic1=?" + " AND       a.c_dec_cplearv1=?" +
        " AND       a.c_ide_rfceeog1=b.c_ide_rfceeog1" + " AND       a.n_dec_ncuamse1=b.n_dec_ncuamse1" +
        " AND       a.c_ide_rfceeog1=c.c_ide_rfceeog1" + " AND       a.n_dec_ncuamse1=c.n_dec_ncuamse1" +
        " AND       a.c_ide_rfceeog1=d.c_ide_rfceeog1" + " AND       a.n_dec_ncuamse1=d.n_dec_ncuamse1";

    String CONSULTA_DETERMINACION_IMPUESTO_CDI_DPDIF =
        " SELECT    a.n_dec_ejercic1 as n_ejercicio, a.c_dec_cplearv1 as c_n_periodo, a.c_dec_ctdliea1 as tdiepco1," +
        "   l        a.f_dec_fperceh1 as fperceh1, a.n_dec_noupmee1 as n_numero_operacion, 'DPDIF' as formulario," +
        "           b.i_pag_icmapru1 as i_111021, b.i_pag_ifmapvu1 as i_111024" +
        " FROM      ps_dd_dec_diadteo1 a, ps_dd_pag_piamgpo1 b, ps_ci_dec_crcaeot1 c" +
        " WHERE     a.c_ide_rfceeog1 = b.c_ide_rfceeog1" + " AND       a.n_dec_ncuamse1 = b.n_dec_ncuamse1" +
        " AND       b.c_dec_crleanv1 = c.c_dec_crleanv1" + " AND       a.c_ide_rfceeog1 IN (?, ?, ?)" +
        " AND       a.c_dec_cplearv1 = ?" + " AND       a.n_dec_ejercic1 = ?" + " AND       c.c_obl_ccloanv1 = ?";

    //Variable para store procedure
    String STORE_PROCEDURE_BUSCA_ICEP_URDCSIL = "DWH_ODS.SP_SIO_URDCSIL1";


    String CONSULTAR_DECLARACION_PROVICIONAL_O_DEFINITIVA_DE_IMPUESTOS_FEDERALES_OP_36B =
        "select dia.f_dec_fcieamc1 as f_dec_fcieamc1, " + " dia.f_dec_fperceh1 as f_dec_fperceh1, " +
        " dia.c_dec_ctdliea1 as c_dec_ctdliea1, " + " crc.c_obl_ccloanv1 as c_obl_ccloanv1, " +
        " crc.d_dec_drceeos1 as d_dec_drceeos1, " + " doe.c_dec_ceflsia1 as c_dec_ceflsia1, " +
        " doe.i_dec_meosntt1 as i_dec_meosntt1 " + "From ps_dd_dec_diadteo1 dia, " + " ps_dd_dec_doefets1 doe, " +
        " ps_ci_dec_crcaeot1 crc " + "Where dia.c_ide_rfceeog1 = doe.c_ide_rfceeog1 and " +
        " dia.n_dec_ncuamse1 = doe.n_dec_ncuamse1 and " + " doe.c_obl_ccdloea1 = crc.c_obl_ccloanv1 and " +
        " dia.c_ide_rfceeog1 in ('%s','%s','%s') and " + " dia.n_dec_ejercic1 = :ejercicio and " +
        " dia.c_dec_cplearv1 = :periodo";

    String CONSULTAR_DECLARACION_PROVISIONAL_O_DEFINITIVA_DE_IMPUESTOS_FEDERALES_36 =
        " select dia.n_dec_noupmee1 as n_dec_noupmee1, " + " dia.f_dec_fperceh1 as f_dec_fperceh1, " +
        " dia.f_dec_fcieamc1 as f_dec_fcieamc1, " + " dia.c_dec_ctdliea1 as c_dec_ctdliea1, " +
        " crc.c_obl_ccloanv1 as c_obl_ccloanv1, " + " crc.d_dec_drceeos1 as d_dec_drceeos1, " +
        " gpo.i_pag_ifmapvu1 as i_pag_ifmapvu1, " + " gpo.i_pag_icmapru1 as i_pag_icmapru1, " +
        " gpo.i_pag_capaada1 as i_pag_capaada1, " + " gpo.i_pag_carader1 as i_pag_carader1, " +
        " gpo.i_pag_camcadu1 as i_pag_camcadu1, " + " gpo.i_pag_tcootna1 as i_pag_tcootna1, " +
        " gpo.i_pag_acasprl1 as i_pag_acasprl1, " + " gpo.i_pag_seumbps1 as i_pag_seumbps1, " +
        " gpo.i_pag_acpolmi1 as i_pag_acpolmi1, " + " gpo.i_pag_acidspm1 as i_pag_acidspm1, " +
        " gpo.i_pag_adatpiu1 as i_pag_adatpiu1, " + " gpo.i_pag_auiccps1 as i_pag_auiccps1, " +
        " gpo.i_pag_apaprgl1 as i_pag_apaprgl1, " + " gpo.i_pag_aoeptsl1 as i_pag_aoeptsl1, " +
        " gpo.i_pag_actpeel1 as i_pag_actpeel1, " + " gpo.i_pag_admpial1 as i_pag_admpial1, " +
        " gpo.i_pag_taoptla1 as i_pag_taoptla1, " + " gpo.f_pag_fpraeae1 as f_pag_fpraeae1, " +
        " gpo.i_pag_mpaoann1 as i_pag_mpaoann1, " + " gpo.i_pag_mpatoan1 as i_pag_mpatoan1, " +
        " gpo.i_pag_ccaanrt1 as i_pag_ccaanrt1, " + " gpo.d_pag_appalri1 as d_pag_appalri1, " +
        " gpo.i_pag_pi1pamr1 as i_pag_pi1pamr1, " + " gpo.i_pag_pi1pamr2 as i_pag_pi1pamr2, " +
        " gpo.i_pag_cfaanvt1 as i_pag_cfaanvt1, " + " gpo.i_pag_cpaangt1 as i_pag_cpaangt1, " +
        " gpo.c_ubi_ceflnea1 as c_ubi_ceflnea1 " + "from ps_dd_dec_diadteo1 dia, " + " ps_dd_pag_piamgpo1 gpo, " +
        " ps_ci_dec_crcaeot1  crc " + "where dia.c_ide_rfceeog1 = gpo.c_ide_rfceeog1 and " +
        " dia.n_dec_ncuamse1 = gpo.n_dec_ncuamse1 and " + " gpo.c_dec_crleanv1 = crc.c_dec_crleanv1 and " +
        " dia.c_ide_rfceeog1 in('%s','%s','%s') and " + " dia.n_dec_ejercic1 = :ejercicio and " +
        " dia.c_dec_cplearv1 = :periodo";

    String CONSULTAR_DECLARACION_PROVISIONAL_O_DEFINITIVA_DE_IMPUESTOS_FEDERALES_36A =
        "select dia.f_dec_fcieamc1 as f_dec_fcieamc1, " + " dia.f_dec_fperceh1 as f_dec_fperceh1, " +
        " dia.c_dec_ctdliea1 as c_dec_ctdliea1, " + " crc.c_obl_ccloanv1 as c_obl_ccloanv1, " +
        " crc.d_dec_drceeos1 as d_dec_drceeos1, " + " cpa.d_obl_dpeesrc1 as d_obl_dpeesrc1, " +
        " dcd.c_obl_ccloanv1 as c_obl_ccloanv2, " + " dcd.i_dec_isqamau1 as i_dec_isqamau1, " +
        " dcd.i_dec_iosfmra1 as i_dec_iosfmra1, " + " dcd.f_dec_fddmsfe1 as f_dec_fddmsfe1, " +
        " dcd.i_dec_rvhaaea1 as i_dec_rvhaaea1, " + " dcd.i_dec_rvaaaea1 as i_dec_rvaaaea1 " +
        "from ps_dd_dec_diadteo1 dia, " + " ps_dd_dec_dcdpeoe1 dcd, " + " ps_ci_dec_crcaeot1 crc, " +
        " ps_ci_obl_cpaetra1 cpa " + "where dia.c_ide_rfceeog1 = dcd.c_ide_rfceeog1 and " +
        " dia.n_dec_ncuamse1 = dcd.n_dec_ncuamse1 and " + " dcd.c_obl_ccdloea1 = crc.c_obl_ccloanv1 and " +
        " dcd.c_obl_cplearv1 = cpa.c_obl_cplearv1 and " + " dia.c_ide_rfceeog1 in ('%s','%s','%s') and " +
        " dia.n_dec_ejercic1 = :ejercicio and " + " dia.c_dec_cplearv1 = :periodo";

    String OBTIENEN_ICEP_URDCSIL_ORACLE =
        " SELECT  z.x, " + " z.c_dec_ctdliea1, " + " z.f_dec_fperceh1, " + " z.n_dec_noupmee1, " +
        " z.i_dec_sfaadmi1  " + " FROM (SELECT  rownum x,   " + "y.c_dec_ctdliea1, " + "y.f_dec_fperceh1, " +
        "y.n_dec_noupmee1, " + "y.i_dec_sfaadmi1  " + " FROM (  SELECT  a.c_dec_ctdliea1, " + " a.f_dec_fperceh1, " +
        " a.n_dec_noupmee1, " + " b.i_dec_sfaadmi1  " + " FROM  DWH_ODS.PS_DD_DEC_DIADTEO1 a, " +
        " DWH_ODS.PS_DD_DEC_DIEPSAA1 b " + " WHERE a.c_ide_rfceeog1 = b.c_ide_rfceeog1 " +
        " AND   a.n_dec_ncuamse1 = b.n_dec_ncuamse1 " + " AND   a.c_ide_rfceeog1  in (?, ?, ?) " +
        " AND   a.c_dec_cplearv1 = ? " + " AND   a.n_dec_ejercic1 = ? " + " AND   b.i_dec_sfaadmi1 > 0        " +
        " ORDER BY  a.f_dec_fperceh1 desc, a.n_dec_noupmee1 desc) y ) z " + " WHERE z.x = 1 ";

    String OBTIENE_DECLARACIONES = 
        "select  \n" + 
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
        "        c.c_idc_ctclioa1 as IMPUESTO                  \n" + 
        "        from  DWH_ODS.ps_dd_dec_diadteo1 a, DWH_ODS.ps_dd_dec_dcdpeoe1 b, DWH_ODS.ci_idc_ccaotna1 c  \n" + 
        "        where a.c_ide_rfceeog1 = b.c_ide_rfceeog1 and  \n" + 
        "        a.n_dec_ncuamse1 = b.n_dec_ncuamse1 and \n" + 
        "        b.c_obl_ccdloea1 = c.c_idc_ccloanv1 (+) and  \n" + 
        "        b.i_dec_iosfmra1 > 0 and \n" + 
        "        a.c_ide_rfceeog1 = 'MIN931015ES9' \n" + 
        /*"        a.f_dec_fceacrh1 >= to_date('17-MAR-13')-1 and a.f_dec_fceacrh1 < to_date('17-MAR-13')  \n" + */
        "        group by 'NORMAL', a.c_ide_rfceeog1, a.n_dec_ncuamse1, a.n_dec_noupmee1, a.c_dec_ctdliea1, a.f_dec_fperceh1, a.n_dec_ejercic1, a.c_dec_cplearv1, a.f_dec_fceacrh1, b.c_obl_ccdloea1, case b.c_dec_ctclioa2   \n" + 
        "        when 'SAF' then '1'     \n" + 
        "        when 'PDI' then '2'  \n" + 
        "        else ''  \n" + 
        "        end, b.c_dec_cplearv1, b.n_dec_ejercic1, b.f_dec_fcieamc1, b.c_obl_ccloanv1, b.i_dec_isqamau1, b.i_dec_iosfmra1, b.f_dec_fddmsfe1, b.I_dec_rvhaaea1, b.i_dec_rvaaaea1, c.c_idc_ctclioa1";
}
