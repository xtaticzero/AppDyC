/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.util.constantebd;

/**
 * Interface consultas origen Informix
 * @author Luis Fernando Barrios Quiroz [ LuFerMX ]9
 * @date Agosto 7, 2013
 * @since 0.1
 *
 * */
public interface SQLInformixSIAT {

    //***** QUERYS INFORMIX *****

    String CONSULTA_PAGOS_ANUALES =
        " SELECT    '' AS V_FECHA_CAUSACION, FPERCEH1 AS V_FPERCEH1, IAPIDNE1 AS V_IAPIDNE1, iapfdne1 as v_iapfdne1," +
        "           importe_acargo as v_importe_acargo, importe_afavor as v_importel_afavor, compensacion as v_compensacion," +
        "           n_numero_operacion as v_n_numero_operacion, tdiepco1 as v_tdiepco1 FROM  pagos_{0}" +
        " WHERE     rfceeog1 IN(?, ?, ?) AND   c_n_periodo=? AND n_ejercicio = ?" +
        " AND       n_renglon  = (   SELECT unique c_sti_rpeangg1 FROM   c_sti_ccstssa1  WHERE  c_sti_ccloanv1=?" +
        " AND    c_sti_rpeangg1 is NOT NULL)";

    String CONSLUTAR_PAGOS_ELECTRONICOSDAO_INFORMACION_PAGO_INF01 =
        " SELECT    UNIQUE C_STI_RlPEANGG1 FROM  C_STI_CCSTSSA1  WHERE  C_STI_CCLOANV1 = ?  AND C_STI_RPEANGG1 IS NOT NULL";

    String CONSLUTAR_PAGOS_ELECTRONICOSDAO_INFORMACION_PAGO_INF02 =
        " SELECT    C.N_DEC_IMPIDEE1, C.N_DEC_IMPFDEE1" + " FROM      CI_DEC_CPAETRA1 C" +
        " WHERE     C_DEC_CPLEARV1 = ?";

    String CONSULTAR_CARATULA_DECLARACION_PF_13_CONSULTA_DATOS_DECLARACION =
        " SELECT    n_numero_operacion, '' AS v_fecha_causacion, tdiepco1, fperceh1, importe_acargo, parte_act," +
        "           recargos, multa_correc, tot_contribuc, credito_sal, compensacion, credito_dis, diesel_auto," +
        "           carretera_cuo, otros_estim, tot_aplica, i_imptepagant, can_acargo, imp_pri_par, imp_sin_par," +
        "           canti_favor, can_pagar" + " FROM      pagos_2003" + " WHERE     rfceeog1 IN(?, ?, ?)" +
        " AND       n_renglon =  (   SELECT  c_sti_rpeangg1" + "                           FROM    c_sti_ccstssa1" +
        "                           WHERE   c_sti_ccloanv1 = ?" +
        "                           AND     c_sti_rpeangg1 IS NOT NULL" + "                           GROUP BY 1)" +
        " AND       c_n_periodo  = ?" + " AND       n_ejercicio   = ?";

    String CONSULTAR_CARATULA_DECLARACION_SOCIEDAD_INTEGRADORA3 =
        " SELECT    unique c_sti_rpeangg1" + " FROM      c_sti_ccstssa1" + " WHERE     c_sti_ccloanv1  = ?" +
        " AND       c_sti_rpeangg1 is NOT NULL";

    String CONSULTAR_CARATULA_DECLARACION_SOCIEDAD_INTEGRADORA3_FORMA3 =
        " SELECT    ldleacv1 as v_n_numero_operacion,'' as v_fecha_causacion, fperceh1 as v_fperceh1, impidee1 as v_impidee1," +
        "           iapidne1 as v_iapidne1, impfdee1 as v_impfdee1, iapfdne1 as v_iapfdne1, tdiepco1 as v_tdiepco1, '' as v_c_118228," +
        "           '' as v_c_110004,c_120007 as v_c_120007,c_130005 as v_c_130005, i_201010 as v_i_201010," +
        "           c_100025 as v_c_100025, c_100009 as v_c_100009, c_100013 as v_c_100013, i_201011 as v_i_201011," +
        "           c_950018 as v_c_950018, i_201012 as v_i_201012, i_201013 as v_i_201013, c_950047 as v_c_950047," +
        "           c_950048 as v_c_950048, c_950049 as v_c_950049, c_950052 as v_c_950052, c_950022 as v_c_950022," +
        "           c_950019 as v_c_950019, c_950020 as v_c_950020, i_201014 as v_i_201014, i_201015 as v_i_201015," +
        "           i_205004 as v_i_205004, i_201016 as v_i_201016, i_201017 as v_i_201017, '' as v_i_201018," +
        "           i_201019 as v_i_201019, c_910004 as v_c_910004, c_900000 as v_c_900000, '' as v_c_205001" +
        " FROM      forma_31,ci_dec_cpaetra1" + " WHERE      rfceeog1 IN(?,?,?)" + " AND       iapidne1 &gt;= ?" +
        " AND       iapfdne1 &lt;= ?" + " AND       impidee1 = n_dec_impidee1" +
        " AND       impfdee1 = n_dec_impfdee1" + " AND       c_dec_cplearv1= ?";

    String CONSULTAR_CARATULA_DECLARACION_SOCIEDAD_INTEGRADORA3_REGISTRO2002 =
        " SELECT    n_numero_operacion as v_n_numero_operacion,'' as v_fecha_causacion, fperceh1 as v_fperceh1," +
        "           impidee1 as v_impidee1, iapidne1 as v_iapidne1, impfdee1 as v_impfdee1,iapfdne1 as v_iapfdne1," +
        "           tdiepco1 as v_tdiepco1, '' as v_c_118228, decode(n_renglon,1,importe_acargo,null) as v_c_110004," +
        "           decode(n_renglon,2,importe_acargo,null) as v_c_120007, decode(n_renglon,3, importe_acargo,null) as v_c_130005," +
        "           tot_contribuc as v_i_201010,parte_act as v_c_100025, recargos as v_c_100009,multa_correc as v_c_100013," +
        "           '' as v_i_201011,credito_sal as v_c_950018, can_acargo as v_i_201012,canti_favor as v_i_201013,decode(n_renglon,1," +
        "           compensacion,null) as v_c_950047,decode(n_renglon,2,compensacion,null) as v_c_950048, decode(n_renglon,3," +
        "           compensacion,null) as v_c_950049,decode(n_renglon,4,compensacion,null) as v_c_950052,credito_sal as v_c_950022," +
        "           credito_dis as v_c_950019, otros_estim as v_c_950020,'' as v_i_201014,i_imptepagant as v_i_201015," +
        "           f_pago_anterior as v_i_205004, can_acargo as v_i_201016,importe_afavor as v_i_201017,'' as v_i_201018," +
        "           '' as v_i_201019,'' as v_c_910004,can_pagar as v_c_900000, c_numtrans as v_c_205001" +
        " FROM      pagos_2002" + " WHERE     rfceeog1  IN(?,?,?)" + " AND        c_n_periodo = ?" +
        " AND    n_ejercicio   = ?" + " AND       n_renglon  = ?";

    String CONSULTAR_CARATULA_DECLARACION_SOCIEDAD_INTEGRADORA3_REGISTRO =
        " SELECT    n_numero_operacion as v_n_numero_operacion,'' as v_fecha_causacion, fperceh1 as v_fperceh1," +
        "           impidee1 as v_impidee1,iapidne1  as v_iapidne1, impfdee1 as v_impfdee1,iapfdne1 as v_iapfdne1," +
        "           tdiepco1 as v_tdiepco1,'' as v_c_118228,decode(n_renglon,1,importe_acargo,null) as v_c_110004," +
        "           decode(n_renglon,2,importe_acargo,null) as v_c_120007, decode(n_renglon,3,importe_acargo,null) as v_c_130005," +
        "           tot_contribuc as v_i_201010,parte_act as v_c_100025,recargos as   v_c_100009, multa_correc as v_c_100013," +
        "           '' as v_i_201011,credito_sal as v_c_950018,can_acargo as v_i_201012,canti_favor as v_i_201013," +
        "           decode(n_renglon,1,compensacion,null)  as v_c_950047, decode(n_renglon,2,compensacion,null) as v_c_950048," +
        "           decode(n_renglon,3,compensacion,null) as v_c_950049, decode(n_renglon,4,compensacion,null) as v_c_950052," +
        "           credito_sal as v_c_950022,credito_dis as v_c_950019, otros_estim as v_c_950020,'' as v_i_201014," +
        "           i_imptepagant as v_i_201015, f_pago_anterior as v_i_205004,can_acargo as v_i_201016,importe_afavor as v_i_201017," +
        "           '' as v_i_201018,imp_pri_par as v_i_201019,imp_sin_par as v_c_910004, can_pagar as v_c_900000,c_numtrans as v_c_205001" +
        " FROM      pagos_{0} " + " WHERE     rfceeog1 IN (?,?,?)" + " AND       c_n_periodo   = ?" +
        " AND       n_ejercicio   = ?" + " AND       n_renglon =   ?";

    String CONSULTAR_CARATULA_DECLARACION_SOCIEDAD_CONTROLADORA2A_RENGLON =
        " SELECT    unique c_sti_rpeangg1" + " FROM      c_sti_ccstssa1" + " WHERE     c_sti_ccloanv1 =   ?" +
        " AND       c_sti_rpeangg1 is NOT NULL";

    String CONSULTAR_CARATULA_DECLARACION_SOCIEDAD_CONTROLADORA2A_FORMA2A =
        " SELECT    ldleacv1 as v_n_numero_operacion,'' as v_fecha_causacion,fperceh1 as v_fperceh1," +
        "           impidee1 as v_impidee1,iapidne1 as  v_iapidne1,impfdee1 as v_impfdee1, iapfdne1 as v_iapfdne1," +
        "           tdiepco1 as v_tdiepco1,'' as v_c_118228, c_110001 as v_c_110001,c_120007 as v_c_120007," +
        "           c_130004 as v_c_130004,i_201010 as v_i_201010,c_100025 as v_c_100025,c_100009 as v_c_100009," +
        "           c_100013 as v_c_100013,i_201011 as v_i_201011,c_950018 as v_c_950018,i_201012 as v_i_201012," +
        "           i_201013 as v_i_201013,c_950047 as v_c_950047,c_950048 as v_c_950048,c_950049 as v_c_950049," +
        "           c_950052 as v_c_950052,c_950022 as v_c_950022,c_950019 as v_c_950019,c_950020 as v_c_950020," +
        "           i_201014 as v_i_201014,i_201015 as v_i_201015,i_205004 as v_i_205004,i_201016 as v_i_201016," +
        "           i_201017 as v_i_201017,'' as v_i_201018,i_201019 as v_i_201019,c_910004 as v_c_910004," +
        "           c_900000 as v_c_900000,'' as v_c_205001" + " FROM      forma_22,ci_dec_cpaetra1" +
        " WHERE     rfceeog1 IN(?,?,?) " + " AND       iapidne1 &gt;= ?" + " AND       iapfdne1 &lt;= ?" +
        " AND       impidee1 = n_dec_impidee1" + " AND       impfdee1 = n_dec_impfdee1" +
        " AND       c_dec_cplearv1= ?";

    String CONSULTAR_CARATULA_DECLARACION_SOCIEDAD_CONTROLADORA2A_REGISTRO2002 =
        " SELECT     n_numero_operacion as v_n_numero_operacion,'' as v_fecha_causacion,fperceh1 as v_fperceh1," +
        "           impidee1 as v_impidee1,iapidne1 as v_iapidne1,impfdee1 as v_impfdee1,iapfdne1  as v_iapfdne1," +
        "           tdiepco1 as v_tdiepco1,'' as v_c_118228,decode(n_renglon,1,importe_acargo,null) as v_c_110001," +
        "           decode(n_renglon,2,importe_acargo,null) as v_c_120007,decode(n_renglon,3,importe_acargo,null)  as v_c_130004," +
        "           tot_contribuc as v_i_201010,parte_act as v_c_100025,recargos as v_c_100009,multa_correc as v_c_100013," +
        "           '' as v_i_201011,credito_sal as v_c_950018,can_acargo as v_i_201012,canti_favor as v_i_201013," +
        "           decode(n_renglon,1,compensacion,null) as  v_c_950047,decode(n_renglon,2,compensacion,null) as v_c_950048," +
        "           decode(n_renglon,3,compensacion,null) as v_c_950049,decode(n_renglon,4,compensacion,null) as v_c_950052," +
        "           credito_sal as v_c_950022,credito_dis as v_c_950019,otros_estim as v_c_950020,'' as v_i_201014," +
        "           i_imptepagant as v_i_201015,f_pago_anterior as v_i_205004,can_acargo as v_i_201016,importe_afavor as v_i_201017," +
        "           '' as v_i_201018,'' as v_i_201019,'' as v_c_910004,can_pagar as v_c_900000,c_numtrans as v_c_205001" +
        " FROM      pagos_2002" + " WHERE     rfceeog1 IN(?,?,?)  " + " AND        c_n_periodo    = ?" +
        " AND    n_ejercicio   = ?" + " AND       n_renglon  = ?";

    String CONSULTAR_CARATULA_DECLARACION_SOCIEDAD_CONTROLADORA2A_REGISTRO =
        " SELECT      n_numero_operacion as v_n_numero_operacion,'' as v_fecha_causacion,fperceh1 as v_fperceh1," +
        "           impidee1 as v_impidee1,iapidne1 as v_iapidne1,impfdee1 as v_impfdee1,iapfdne1 as  v_iapfdne1," +
        "           tdiepco1 as v_tdiepco1,'' as v_c_118228,decode(n_renglon,1,importe_acargo,null) as v_c_110001," +
        "           decode(n_renglon,2,importe_acargo,null) as v_c_120007,decode(n_renglon,3,importe_acargo,null) as  v_c_130004," +
        "           tot_contribuc as v_i_201010,parte_act as v_c_100025,recargos as v_c_100009,multa_correc as v_c_100013," +
        "           '' as  v_i_201011,credito_sal as v_c_950018,can_acargo as v_i_201012,canti_favor as v_i_201013," +
        "           decode(n_renglon,1,compensacion,null) as v_c_950047,decode(n_renglon,2,compensacion,null)  as v_c_950048," +
        "           decode(n_renglon,3,compensacion,null) as v_c_950049,decode(n_renglon,4,compensacion,null) as v_c_950052," +
        "           credito_sal as v_c_950022,credito_dis as v_c_950019,otros_estim as v_c_950020,'' as v_i_201014,i_imptepagant as v_i_201015," +
        "           f_pago_anterior as v_i_205004,can_acargo as v_i_201016,importe_afavor as v_i_201017,'' as v_i_201018," +
        "           imp_pri_par as v_i_201019,imp_sin_par as v_c_910004,can_pagar as v_c_900000,c_numtrans as v_c_205001" +
        " FROM      pagos_{0}" + " WHERE     rfceeog1 IN  (?,?,?)" + " AND       c_n_periodo     = ?" +
        " AND     n_ejercicio    = ?" + " AND       n_renglon   = ?";

    String CONSULTAR_CARATULA_DECLARACION_MORAL12_RENGLON =
        " SELECT    unique c_sti_rpeangg1" + " FROM      c_sti_ccstssa1" + " WHERE     c_sti_ccloanv1 = ?" +
        " AND       c_sti_rpeangg1 is not null";

    String CONSULTAR_CARATULA_DECLARACION_MORAL12_FORMA2A =
        " SELECT    ldleacv1 as v_n_numero_operacion,'' as v_fecha_causacion,fperceh1 as v_fperceh1,impidee1 as v_impidee1," +
        "           iapidne1 as v_iapidne1, impfdee1 as v_impfdee1, iapfdne1 as v_iapfdne1, '' as v_tdiepco1,'' as v_c_118228," +
        "           c_110001 as v_c_110001, c_120007 as v_c_120007, c_130004 as v_c_130004, i_201010 as v_i_201010," +
        "           c_100025 as v_c_100025, c_100009 as v_c_100009, c_100013 as v_c_100013, i_201011 as v_i_201011," +
        "           c_950018 as v_c_950018, i_201012 as v_i_201012, i_201013 as v_i_201013, c_950047 as v_c_950047," +
        "           c_950048 as v_c_950048, c_950049 as v_c_950049, c_950052 as v_c_950052, c_950022 as v_c_950022," +
        "           c_950019 as v_c_950019, c_950020 as v_c_950020, i_201014 as v_i_201014, i_201015 as v_i_201015," +
        "           i_205004 as v_i_205004, i_201016 as v_i_201016, i_201017 as v_i_201017, '' as v_i_201018," +
        "           i_201019 as v_i_201019, c_910004 as v_c_910004, c_900000 as v_c_900000, '' as v_c_205001" +
        " FROM      forma_21,ci_dec_cpaetra1" + " WHERE     rfceeog1    IN(?,?,?)" + " AND       iapidne1 &gt;= ?" +
        " AND       iapfdne1 &lt;= ?" + " AND       impidee1 = n_dec_impidee1" +
        " AND       impfdee1 = n_dec_impfdee1" + " AND       c_dec_cplearv1= ?";

    String CONSULTAR_CARATULA_DECLARACION_MORAL12_REGISTRO2002 =
        " SELECT    n_numero_operacion as v_n_numero_operacion,'' as v_fecha_causacion,fperceh1 as v_fperceh1," +
        "           impidee1 as v_impidee1,iapidne1 as v_iapidne1,impfdee1 as v_impfdee1,iapfdne1 as v_iapfdne1," +
        "            tdiepco1 as v_tdiepco1,'' as v_c_118228,decode(n_renglon,1,importe_acargo,null) as v_c_110001," +
        "           decode(n_renglon,2,importe_acargo,null) as v_c_120007,decode(n_renglon,3,importe_acargo,null) as v_c_130004," +
        "           tot_contribuc as v_i_201010,parte_act as v_c_100025,recargos as v_c_100009,multa_correc as v_c_100013," +
        "           '' as v_i_201011, credito_sal as v_c_950018,can_acargo as v_i_201012,canti_favor as v_i_201013," +
        "           decode(n_renglon,1,compensacion,null)   as v_c_950047,decode(n_renglon,2,compensacion,null) as v_c_950048," +
        "           decode(n_renglon,3,compensacion,null) as v_c_950049,decode(n_renglon,4,compensacion,null) as v_c_950052," +
        "           credito_sal as v_c_950022,credito_dis as v_c_950019,otros_estim as v_c_950020,'' as v_i_201014," +
        "           i_imptepagant as v_i_201015,f_pago_anterior as v_i_205004,can_acargo as v_i_201016,importe_afavor as v_i_201017," +
        "           '' as v_i_201018,'' as v_i_201019,'' as v_c_910004,can_pagar as v_c_900000,c_numtrans as v_c_205001" +
        " FROM      pagos_2002" + " WHERE     rfceeog1 IN(?,?,?)" + " AND        c_n_periodo  =  ?" +
        " AND   n_ejercicio  = ?" + " AND       n_renglon = ?";

    String CONSULTAR_CARATULA_DECLARACION_MORAL12_REGISTRO =
        " SELECT    n_numero_operacion as v_n_numero_operacion,'' as v_fecha_causacion,fperceh1 as v_fperceh1," +
        "           impidee1 as v_impidee1,iapidne1 as v_iapidne1,impfdee1 as v_impfdee1,iapfdne1 as v_iapfdne1," +
        "             tdiepco1 as v_tdiepco1,'' as v_c_118228,decode(n_renglon,1,importe_acargo,null) as v_c_110001," +
        "           decode(n_renglon,2,importe_acargo,null) as v_c_120007,decode(n_renglon,3,importe_acargo,null) as v_c_130004," +
        "           tot_contribuc as v_i_201010,parte_act as v_c_100025,recargos   as v_c_100009,multa_correc as v_c_100013," +
        "           '' as v_i_201011,credito_sal as  v_c_950018,can_acargo as v_i_201012,canti_favor as v_i_201013," +
        "           decode(n_renglon,1,compensacion,null) as v_c_950047,decode(n_renglon,2,compensacion,null) as v_c_950048," +
        "           decode(n_renglon,3,compensacion,null)as v_c_950049,decode(n_renglon,4,compensacion,null) as v_c_950052," +
        "           credito_sal as v_c_950022,credito_dis as v_c_950019,otros_estim as v_c_950020,'' as v_i_201014," +
        "           i_imptepagant as v_i_201015,f_pago_anterior as v_i_205004,can_acargo as v_i_201016,importe_afavor as v_i_201017," +
        "           '' as v_i_201018,imp_pri_par as v_i_201019,imp_sin_par as v_c_910004,can_pagar as v_c_900000,c_numtrans as v_c_205001" +
        " FROM      pagos_{0}" + " WHERE     rfceeog1 IN(?,?,?)" + " AND       c_n_periodo  = ?" +
        " AND  n_ejercicio  = ?" + " AND       n_renglon = ?";

    String CONSULTAR_COMPENSACIONES_FECHAS =
        " SELECT    n_dec_impidee1, n_dec_impfdee1" + " FROM      ci_dec_cpaetra1" + " WHERE     c_dec_cplearv1 = ?";

    String CONSULTAR_COMPENSACIONES_INFORMACION_COMPENSACIONES =
        " SELECT    ncuomne1, fperceh1, ismoplo1, nruemse1, freecsh1" + " FROM      dd_compensaciones" +
        " WHERE     rfceeog1 IN (?, ?, ?)" + " AND       psfieaa1 = ?" + " AND       psffeaa1 = ?" +
        " AND       cilmapv1 IN (   SELECT  c_sti_ctslria1" + "                           FROM     c_sti_ccstssa1" +
        "                           WHERE   c_sti_ccloanv1   = ? )";

    String CONSULTAR_DETALLE_CREDITOS_FISCALES_OPERACION45 =
        " SELECT    a.ncurmee1,a.ipmapgo1" + " FROM      mm_creditos a" + " WHERE     a.ctalrca1 NOT IN (60,61)" +
        " AND       rfceeog1 IN (?,?,?)";

    String CONSULTA_CONTRIBUYENTES_ALTAMENTE_EXPORTADORES =
        " SELECT    naltex, axoaltex, apepat_rsint, delegacion, cp, calle, tel, fax, colonia, estado, no_interior," +
        "           no_exterior" + " FROM      c_altex" + " WHERE     rfc IN (?, ?, ?)" + " AND       naltex = ?";

    String CONSULTA_DEVOLUCIONES_REGISTRADAS =
        " SELECT    ncuomne1, freecch1, ismoplo1, nruemse1, iadmuep1" + " FROM      dd_devoluciones" +
        " WHERE     rfceeog1 IN (?, ?, ?)" + " AND       psfieaa1 = (CONCAT(LPAD((   SELECT  n_dec_impidee1" +
        "                                       FROM    ci_dec_cpaetra1" +
        "                                       WHERE   c_dec_cplearv1 = ?),2,'0'), ?))" +
        " AND       psffeaa1 = (CONCAT(LPAD((   SELECT  n_dec_impfdee1" +
        "                                       FROM    ci_dec_cpaetra1" +
        "                                       WHERE   c_dec_cplearv1 = ?),2,'0'), ?))" +
        " AND       cilmapv1 IN (SELECT c_sti_ctslria1" + "                       FROM    c_sti_ccstssa1 " +
        "                       WHERE   c_sti_ccloanv1 =   ? )";

    String CONSULTAR_DICTAMENES_CONSULTA_DICTAMEN_CONTRIBUYENTE =
        " SELECT    ejercicio_fiscal, id_sector, tipo_dictamen, fecha_recepcion, obligado, no_cpr, rfc_cpr," +
        "           nombre_cpr" + " FROM      general" + " WHERE     rfc_contribuyente IN (?, ?, ?)" +
        " AND       ejercicio_fiscal = ?";

    String CONSULTAR_DETERMINACION_IMPUESTO_CDIDPDIFANIO =
        " SELECT    n_ejercicio as n_ejercicio, c_n_periodo as c_n_periodo, tdiepco1 as tdiepco1,fperceh1 as fperceh1," +
        "           n_numero_operacion  as n_numero_operacion,'NEPE' as forma, importe_acargo as i_111021," +
        "           importe_afavor as i_111024" + " FROM      pagos_{0}" + " WHERE     rfceeog1 IN  (?, ?, ?)" +
        " AND       n_renglon = (SELECT     c_sti_rpeangg1" + "                       FROM        c_sti_ccstssa1 " +
        "                       WHERE       c_sti_ccloanv1 = ?" +
        "                       AND         c_sti_rpeangg1 IS NOT NULL group by 1)" + " AND       c_n_periodo =   ?" +
        " AND       n_ejercicio = ?";

    String CONSULTAR_DETERMINACION_IMPUESTO_CDI_IMPUESTOS =
        " SELECT    c_sti_cilmapv1" + " FROM      c_sti_ccstssa1  " + " WHERE     c_sti_ccloanv1 = ?" + " GROUP BY  1";

    String CONSULTAR_DETERMINACION_IMPUESTO_FORMA13 =
        " SELECT    n_ejercicio as n_ejercicio, c_n_periodo as c_n_periodo,tdiepco1 as tdiepco1, fperceh1 as fperceh1," +
        "           n_numero_operacion as n_numero_operacion, 'Forma 13' as forma,i_111021 as i_111021, i_111024 as i_111024" +
        " FROM      forma_13" + " WHERE     rfceeog1 IN   (?, ?, ?)" + " AND       c_n_periodo = ?" +
        " AND       n_ejercicio = ?";

    String CONSULTAR_DETERMINACION_IMPUESTO_FORMA13A =
        " SELECT    n_ejercicio as n_ejercicio, c_n_periodo as c_n_periodo, i_205002  as tdiepco1,fperceh1 as fperceh1," +
        "           n_numero_operacion as n_numero_operacion, 'Forma 13A' as forma, i_111021 as i_111021,i_111024 as i_111024" +
        " FROM      forma13a" + " WHERE     rfceeog1 IN (?, ?, ?) " + " AND       c_n_periodo = ?" +
        " AND       n_ejercicio = ?";

    String CONSULTAR_DETERMINACION_IMPUESTO_DID =
        " SELECT    n_dec_ejercic1 as n_ejercicio, c_dec_cplearv1 as c_n_periodo, c_dec_ctdalie1 as tdiepco1," +
        "           f_dec_fperceh1 as fperceh1, n_dec_noupmee1 as n_numero_operacion, 'DID' as forma, i_dec_ircmeap1 as i_111021," +
        "           i_dec_irfmeap1 as i_111024" + " FROM      dd_dec_didcemi1" +
        " WHERE     d_dec_rfceeog1 IN (?, ?, ?)" + " AND       c_dec_cplearv1 = ?" + " AND       n_dec_ejercic1 = ?";

    String CONSULTAR_DETERMINACION_IMPUESTO_FORMA2 =
        " SELECT    a.iapidne1 as n_ejercicio, b.c_dec_cplearv1 as c_n_periodo, a.tdiepco1 as tdiepco1, a.fperceh1 as fperceh1," +
        "           '' as n_numero_operacion, 'Forma 2' as forma,a.i_111021 as i_111021, a.i_111024 as i_111024" +
        " FROM      forma_21 a, ci_dec_cpaetra1 b" + " WHERE     a.impidee1 = b.n_dec_impidee1" +
        " AND       a.impfdee1 = b.n_dec_impfdee1" + " AND       b.c_dec_cplearv1 = ?" + " AND       a.iapidne1 = ?" +
        " AND       a.iapfdne1 = ?" + " AND       a.rfceeog1 IN (?, ?, ?)";

    String CONSULTAR_DETERMINACION_IMPUESTO_FORMA2A =
        " SELECT a.iapidne1 as n_ejercicio, b.c_dec_cplearv1 as c_n_periodo, " +
        "    a.tdiepco1 as tdiepco1, a.fperceh1 as fperceh1 " +
        "    ,'' as n_numero_operacion, 'Forma 2A' as forma, a.i_111021 as i_111021, a.i_111024 as i_111024 " +
        " FROM forma_22 a ,ci_dec_cpaetra1 b " + " WHERE a.impidee1 = b.n_dec_impidee1 " +
        " AND  a.impfdee1 = b.n_dec_impfdee1 " + "  AND b.c_dec_cplearv1 = ? " + " AND a.iapidne1 = ? " +
        "  AND a.iapfdne1 = ? " + "  AND a.rfceeog1 in (?, ?, ?)";

    String CONSULTAR_DETERMINACION_IMPUESTO_FORMA3 =
        "SELECT a.iapidne1 as n_ejercicio, b.c_dec_cplearv1 as c_n_periodo, a.tdiepco1 as tdiepco1, " +
        "a.fperceh1 as fperceh1,'' as n_numero_operacion, 'Forma 3' as forma, a.i_111116 as i_111021, a.i_111118 as i_111024 " +
        "FROM forma_31 a,ci_dec_cpaetra1 b " + "WHERE a.impidee1 = b.n_dec_impidee1 " +
        "AND   a.impfdee1 = b.n_dec_impfdee1 " + "AND  b.c_dec_cplearv1 = ? " + "AND a.iapidne1 =  ? " +
        "AND  a.iapfdne1 = ? " + "AND a.rfceeog1  in (?, ?, ?)";

    String CONSULTAR_DETERMINACION_IMPUESTO_FORMA13_2 =
        "SELECT n_ejercicio  as n_ejercicio " + ",c_n_periodo as c_n_periodo , tdiepco1 as tdiepco1, fperceh1 as fperceh1, n_numero_operacion as n_numero_operacion " +
        ",'Forma 13' as forma, i_111021 as i_111021, i_121019 as i_111024 " + "FROM forma_13 " +
        " WHERE rfceeog1 in   (?, ?, ?) " + " AND  c_n_periodo =   ? " + "AND n_ejercicio = ?";

    String CONSULTAR_DETERMINACION_IMPUESTO_DID2 =
        "SELECT n_dec_ejercic1 as n_ejercicio ,c_dec_cplearv1 as c_n_periodo , " +
        " c_dec_ctdalie1 as tdiepco1 ,f_dec_fperceh1 as fperceh1 " +
        " ,n_dec_noupmee1 as n_numero_operacion,'DID' as forma, i_dec_iaaicem1 as i_111021, i_dec_iasfemc1 as i_111024 " +
        " FROM dd_dec_didcemi1 " + " WHERE d_dec_rfceeog1 in (?, ?, ?) " + " AND c_dec_cplearv1 = ? " +
        " AND n_dec_ejercic1 = ? ";

    String CONSULTAR_DETERMINACION_IMPUESTO_FORMA2_2 =
        "SELECT a.iapidne1 as n_ejercicio ,b.c_dec_cplearv1 as c_n_periodo " +
        ",a.tdiepco1 as tdiepco1 ,a.fperceh1 as fperceh1 ,'' as n_numero_operacion ,'Forma 2' as forma , " +
        "a.i_121024 as i_111021 ,a.i_121022 as i_111024 " + "FROM forma_21 a ,ci_dec_cpaetra1 b " +
        "WHERE  a.impidee1 = b.n_dec_impidee1 " + "AND a.impfdee1  = b.n_dec_impfdee1 " +
        "AND   b.c_dec_cplearv1 = ? " + "AND a.iapidne1 = ? " + "AND a.iapfdne1 =   ? " +
        "AND a.rfceeog1 in (?, ?, ?)";

    String CONSULTAR_DETERMINACION_IMPUESTO_FORMA2A_2 =
        "SELECT a.iapidne1 as n_ejercicio ,b.c_dec_cplearv1 as c_n_periodo " +
        ",a.tdiepco1 as tdiepco1 ,a.fperceh1 as fperceh1 ,'' as n_numero_operacion ,'Forma 2A' as forma " +
        ",a.i_121024 as i_111021 ,a.i_121022 as i_111024 " + "FROM forma_22 a ,ci_dec_cpaetra1 b " +
        "WHERE a.impidee1  = b.n_dec_impidee1 " + "AND a.impfdee1 =  b.n_dec_impfdee1 " +
        "AND b.c_dec_cplearv1  = ? " + "AND a.iapidne1 = ? " + "AND   a.iapfdne1 = ? " +
        "AND a.rfceeog1 in  (?, ?, ?)";

    String CONSULTAR_DETERMINACION_IMPUESTO_FORMA3_2 =
        "SELECT a.iapidne1 as n_ejercicio ,b.c_dec_cplearv1 as c_n_periodo,a.tdiepco1 as tdiepco1  " +
        ",a.fperceh1 as fperceh1 ,'' as n_numero_operacion ,'Forma 3' as forma ,a.i_121024 as i_111021 ,a.i_121022 as i_111024  " +
        "FROM forma_31 a,ci_dec_cpaetra1 b " + "WHERE a.impidee1 =  b.n_dec_impidee1 " +
        "AND a.impfdee1 =    b.n_dec_impfdee1 " + "AND b.c_dec_cplearv1 =  ? " + " AND a.iapidne1 = ? " +
        "AND a.iapfdne1 = ? " + "AND a.rfceeog1 in (?, ?, ?)";

    String CONSULTAR_DETERMINACION_IMPUESTO_DID3 =
        "SELECT n_dec_ejercic1 as n_ejercicio ,c_dec_cplearv1 as c_n_periodo " +
        ",c_dec_ctdalie1 as tdiepco1 ,f_dec_fperceh1 as fperceh1 ,n_dec_noupmee1 as n_numero_operacion ,'DID' as forma  " +
        ",i_dec_ietuicm1 as i_111021 ,i_dec_ietuifm1 as i_111024 " + "FROM dd_dec_didcemi1  " +
        "WHERE d_dec_rfceeog1 in (?, ?, ?) " + "AND c_dec_cplearv1 = ? " + "AND n_dec_ejercic1 = ? ";

    String CONSULTAR_DETERMINACION_IMPUESTO_FORMA_1E =
        "SELECT a.iapidne1 as n_ejercicio ,b.c_dec_cplearv1 as c_n_periodo, a.tdiepco1 as tdiepco1 " +
        ",a.fperceh1 as fperceh1 ,'' as n_numero_operacion ,'Forma 1E' as forma ,a.p2_141915  as i_111021 " +
        ",a.p2_141916 as i_111024 " + "FROM forma1e1 a ,ci_dec_cpaetra1 b where " + "a.impidee1 = b.n_dec_impidee1 " +
        " AND a.impfdee1 = b.n_dec_impfdee1 " + "AND b.c_dec_cplearv1 = ? " + "AND a.iapidne1  = ? " +
        "AND a.iapfdne1 = ? " + "AND a.rfceeog1 in (?, ?, ?) ";

    String CONSULTAR_DECLARACION_ISR_SOCIEDAD_CONTROLADORA_1 =
        "SELECT ldleacv1 as v_n_numero_operacion,iapidne1 as v_iapidne1,c_dec_cplearv1 as v_cplearv1, " +
        "tdiepco1 as v_tdiepco1,fperceh1 as v_fperceh1,i_111025 as v_i_111025,i_111026 as v_i_111026,i_111027 as v_i_111027 " +
        ",i_111028 as v_i_111028,i_111029 as v_i_111029,'' as v_i_3100302,'' as v_i_3100402,'' as v_i_3110202,'' as v_i_3110602, " +
        "'' as v_i_3110302,i_111030 as v_i_111030,'' as v_i_3101702,i_111031 as v_i_111031,i_111032 as v_i_111032,i_111033 as v_i_111033, " +
        "i_111034 as v_i_111034,i_111035 as v_i_111035,i_111036 as v_i_111036,i_111037 as v_i_111037,'' as v_i_3102402,'' as v_i_3102502, " +
        "'' as v_i_3102702,'' as v_i_3102802,i_111038 as v_i_111038,i_111039 as v_i_111039,i_111040 as v_i_111040,i_111041 as v_i_111041, " +
        "i_111042 as v_i_111042,i_111043 as v_i_111043,i_111044 as v_i_111044,i_111045 as v_i_111045,'' as v_i_3103702,i_111003 as v_i_111003, " +
        "i_111004 as v_i_111004,i_111046 as v_i_111046,i_111005 as v_i_111005,i_111006 as v_i_111006,i_111007 as v_i_111007,'' as v_i_3107302, " +
        "'' as v_i_3107402,'' as v_i_3104602,i_111008 as v_i_111008,i_111009 as v_i_111009,i_111047 as v_i_111047,i_111048 as v_i_111048, " +
        "'' as v_i_3105002,'' as v_i_3107502,'' as v_i_3110502,'' as v_i_3107602,i_111049 as v_i_111049,i_111050 as v_i_111050, " +
        "i_111013 as v_i_111013,i_111051 as v_i_111051,i_111052 as v_i_111052,i_111014 as v_i_111014,i_111053 as v_i_111053,i_111054 as v_i_111054, " +
        "i_111055 as v_i_111055,i_111056 as v_i_111056,i_111017 as v_i_111017,i_111057 as v_i_111057,'' as v_i_3110702,'' as v_i_3110802, " +
        "'' as v_i_3110902,'' as v_i_3106502,'' as v_i_3106602,'' as v_i_3107702,'' as v_i_3111002,i_111058 as v_i_111058,'' as v_i_3108002, " +
        "'' as v_i_3110402,i_111018 as v_i_111018,i_111019 as v_i_111019,'' as v_i_3111202,'' as v_i_3107202,i_111020 as v_i_111020, " +
        "i_111904 as v_i_111904,i_111021 as v_i_111021,i_111022 as v_i_111022,'' as v_i_11109,'' as v_i_3111102,i_111024 as v_i_111024 " +
        "FROM forma_22, ci_dec_cpaetra1 " + "WHERE " + "impidee1 = n_dec_impidee1 " +
        "  AND impfdee1 = n_dec_impfdee1 " + "AND rfceeog1  in(?,?,?) " + "AND   iapidne1 >= ? " +
        "AND   iapfdne1 <= ? " + "AND c_dec_cplearv1= ? ";

    String CONSULTAR_DECLARACION_ISR_SOCIEDAD_CONTROLADORA_2 =
        "Select n_numero_operacion as v_n_numero_operacion,iapidne1 as v_iapidne1,c_dec_cplearv1 as v_cplearv1," +
        "tdiepco1 as v_tdiepco1,fperceh1 as v_fperceh1,i_111025 as v_i_111025,'' as v_i_111026,i_111027 as v_i_111027,i_111028 as v_i_111028, " +
        "i_111029 as v_i_111029,i_111001 as v_i_3100302,i_111002 as v_i_3100402,'' as v_i_3110202,'' as v_i_3110602,'' as v_i_3110302, " +
        "i_111030 as v_i_111030,i_111091 as v_i_3101702,i_111031 as v_i_111031,i_111032 as v_i_111032,i_111033 as v_i_111033,i_111034 as v_i_111034, " +
        "i_111035 as v_i_111035,i_111036 as v_i_111036,i_111037 as v_i_111037,i_111876 as v_i_3102402,'' as v_i_3102502,i_111877 as v_i_3102702, " +
        "i_111878 as v_i_3102802,i_111038 as v_i_111038,i_111039 as v_i_111039,i_111040 as v_i_111040,i_111041 as v_i_111041,i_111042 as v_i_111042, " +
        "i_111043 as v_i_111043,i_111044 as v_i_111044,i_111045 as v_i_111045,i_111039 as v_i_3103702,i_111003 as v_i_111003,i_111004 as v_i_111004, " +
        "i_111046 as v_i_111046,i_111005 as v_i_111005,i_111006 as v_i_111006,i_111007 as v_i_111007,i_121010 as v_i_3107302,'' as v_i_3107402, " +
        "'' as v_i_3104602,'' as v_i_111008,'' as v_i_111009,i_111047 as v_i_111047,i_111048 as v_i_111048,i_111860 as v_i_3105002,'' as v_i_3107502, " +
        "'' as v_i_3110502,'' as v_i_3107602,i_111049  as v_i_111049,i_111050 as v_i_111050,i_111013 as v_i_111013,'' as v_i_111051,'' as v_i_111052, " +
        "'' as v_i_111014,i_111053 as v_i_111053,i_111054 as v_i_111054,i_111055 as v_i_111055,i_111056 as v_i_111056,i_111017 as v_i_111017, " +
        "i_111057 as v_i_111057,'' as v_i_3110702,'' as v_i_3110802,'' as v_i_3110902,i_131069 as v_i_3106502,'' as v_i_3106602,'' as v_i_3107702, " +
        "i_111018 as v_i_3111002,'' as v_i_111058,'' as v_i_3108002,'' as v_i_3110402,'' as v_i_111018,'' as v_i_111019,'' as v_i_3111202, " +
        "'' as v_i_3107202,'' as v_i_111020,'' as v_i_111904,'' as v_i_111021,'' as v_i_111022,'' as v_i_11109,'' as v_i_3111102,i_111024 as v_i_111024 " +
        "from  forma_19,ci_dec_cpaetra1 " + " WHERE " + "impidee1 = n_dec_impidee1 " +
        "AND impfdee1  = n_dec_impfdee1 " + "AND rfceeog1 in(?,?,?) " + " AND iapidne1    >= ? " +
        "AND iapfdne1 <= ? " + "AND c_dec_cplearv1= ?";

    String CONSULTAR_DECLARACION_ISR_MORAL =
        "SELECT rfceeog1, iapidne1, iapfdne1, c_dec_cplearv1," + "n_numero_operacion as numero_operacion,n_ejercicio as v_iapidne1,c_n_periodo as v_c_dec_cpl, " +
        "i_205002 as v_tdiepco1,fperceh1 as v_fperceh1,i_111923 as i_111923,i_121019 as i_121019,i_118566 as v_i_800601,i_118567 as v_i_800701, " +
        "i_118216 as v_p6a_118216,i_111001 as v_i_111000,i_111002 as v_i_111001,i_111098 as v_i_3110201,i_111099 as v_i_3110301,i_111003 as v_i_111003, " +
        "i_111004 as v_i_111004,i_111005 as v_i_111005,i_111006 as v_i_111006,i_111009 as v_I_111007,'' as v_i_3110601,'' as v_i_3110701, " +
        "i_111010 as v_i_111010,i_111012 as v_i_111009,'' as v_i_111008,i_111860 as v_i_3105001,'' as v_i_3110801,i_111995 as v_i_3110501, " +
        "'' as v_i_3110901,i_111013 as v_i_3105301,'' as v_i_111013,'' as v_i_111014,i_111016 as v_i_111016,i_111015 as v_i_111015, " +
        "i_111017 as v_i_111017,'' as v_i_3111101,i_121011 as v_i_121011,'' as v_i_111011,'' as v_i_111018,'' as v_i_111019,i_111018 as v_i_3106501, " +
        "i_111019 as v_i_3106601,'' as v_i_3111201,i_901731 as v_i_3106701,i_111021 as v_i_111021,i_111023 as v_i_111023,'' as v_i_3111801, " +
        "'' as v_i_111024,i_111024 as v_i_3107201,i_111058 as v_i_3106801,i_111890 as v_i_111058,'' as v_i_111020,'' as v_i_111904 " +
        "FROM forma_18, ci_dec_cpaetra1 " + "WHERE " + "impidee1 = n_dec_impidee1 " +
        "AND impfdee1 =   n_dec_impfdee1 " + "AND rfceeog1 in(?,? ,? ) " + "AND  iapidne1 >= ? " +
        "AND iapfdne1 <= ?" + "AND c_dec_cplearv1 = ?";

    String CONSULTAR_DECLARACION_ISR_SOCIEDAD_INTEGRADORA =
        "SELECT ldleacv1 as v_ldleacv1,fperceh1 as v_fperceh1,tdiepco1 as v_tdiepco1,i_111101 as v_i_111101," +
        "i_111102 as v_i_111102,i_111103 as v_i_111103,i_111104 as v_i_111104,i_111105 as v_i_111105,i_111106 as v_i_111106,i_111107 as v_i_111107, " +
        "i_111108 as v_i_111108,i_111109 as v_i_111109,i_111110 as v_i_111110,i_111111 as v_i_111111,i_111112 as v_i_111112,i_111113 as v_i_111113, " +
        "i_111114 as v_i_111114,i_111115 as v_i_111115,i_111904 as v_i_111904,i_121904 as v_i_121904,i_131904 as v_i_131904,i_111116 as v_i_111116, " +
        "i_111117 as v_i_111117,i_111023 as v_i_111023,i_111118 as v_i_111118,p2_115134 as v_p2_115134,p2_115135 as v_p2_115135,p2_115136 as v_p2_115136, " +
        "p2_115137 as v_p2_115137 from  forma_31, ci_dec_cpaetra1 " + " WHERE rfceeog1   in(?,?,?)" +
        "AND c_dec_cplearv1= ? " + "AND iapidne1 >=  ? " + "AND iapfdne1 <= ? " + "AND impidee1 = n_dec_impidee1 " +
        "AND impfdee1 = n_dec_impfdee1 ";

    String CONSULTAR_DECLARACION_ISR_PF13_17_DECLARASAT_PART1 =
        "SELECT     a.N_DEC_NOUPMEE1 AS numero_operacion, a.C_DEC_CTDALIE1 AS tdiepco1, a.F_DEC_FPERCEH1 AS f_dec_fperceh1," +
        "           a.I_DEC_IUACNTC1 AS i_111837, a.I_DEC_PERDIDA1 AS i_111838, a.I_DEC_TIAONCT1 AS i_111810," +
        "           a.I_DEC_DPEEDRU1 AS i_111811, a.I_DEC_IRBGMEA1 AS i_111812, a.I_DEC_IRCTAME1 AS i_111813," +
        "           a.I_DEC_SEUMBPS1 AS i_dec_seumbps1, a.I_DEC_IIAMNCP1 AS i_111848, a.I_DEC_IIAMNCP2 AS i_111817," +
        "           a.I_DEC_RIREMED1 AS i_111815, a.I_DEC_IRCEMEA1 AS i_111818, a.I_DEC_IREFADE1 AS i_111860," +
        "           a.I_DEC_EPIDTSR1 AS i_dec_epidtsr1, a.I_DEC_EPIPCNS1 AS i_dec_epipcns1, a.I_DEC_PEEFAFN1 AS i_dec_peefafn1," +
        "           a.I_DEC_PPEARFG1 AS i_111820, a.I_DEC_IRACPTM1 AS i_111819, a.I_DEC_IAPEMCA1 AS i_111822," +
        "           a.I_DEC_CFIETUD1 AS i_dec_cfietud1, a.I_DEC_IIRRPMN1 AS i_dec_iirrpmn1, a.I_DEC_IROCCCM1 AS a_111888," +
        "           a.I_DEC_IROCFCM1 AS a_111889, a.I_DEC_IRICEME1 AS i_111021, a.I_DEC_IRPEMEA1 AS i_111827," +
        "           a.I_DEC_IADEMCE1 AS i_dec_iademce1, a.I_DEC_IRCMEAP1 AS i_dec_dciafre1, a.I_DEC_IRPEAIA1 AS i_111023," +
        "           a.I_DEC_IRPEAIE1 AS i_dec_irpeaie1, a.I_DEC_IRSFEME1 AS i_111024, b.I_DEC_TISSCAO1 AS a_111201," +
        "           b.I_DEC_IEISGPU1 AS a_111202, b.I_DEC_IAISGPU1 AS a_111207, b.I_DEC_IANCGUR1 AS a_111206," +
        "           b.I_DEC_TICONOT1 AS a_111401, b.I_DEC_IEAGOUG1 AS a_111421, b.I_DEC_DAGEPEU1 AS a_111402," +
        "           b.I_DEC_ILPIOUG1 AS i_dec_ilpioug1, b.I_DEC_EFPDAMA1 AS i_dec_efpdama1, b.I_DEC_PREDIAL1 AS a_111422," +
        "           b.I_DEC_DOEPDCU1 AS a_111407, b.I_DEC_IAAGOUG1 AS a_111404, b.I_DEC_DQEIPEU1 AS a_111938," +
        "           b.I_DEC_TIEBONN1 AS a_111501, b.I_DEC_IEEBNXN1 AS a_111504, b.I_DEC_DAGEBEU1 AS a_111523," +
        "           b.I_DEC_ILIEBIM1 AS i_dec_iliebim1, b.I_DEC_PEBEAAE1 AS a_111544, b.I_DEC_GAACNUA1 AS a_111535," +
        "           b.I_DEC_PEBENIR1 AS a_111840, b.I_DEC_TIABOND1 AS a_111550, b.I_DEC_IEABNXD1 AS a_111551," +
        "           b.I_DEC_DAGABEU1 AS a_111552, b.I_DEC_IAABNCD1 AS a_111553, b.I_DEC_DPABIED1 AS a_111842," +
        "           b.I_DEC_INPSFNO1 AS a_111743, b.I_DEC_INPSFNO2 AS a_111730, b.I_DEC_IRPSFNE1 AS a_111732,";


    String CONSULTAR_DECLARACION_ISR_PF13_17_DECLARASAT_PART2 =
        "           b.I_DEC_IRPSFNE2 AS a_111744, b.I_DEC_PPSFERI1 AS a_111734, b.I_DEC_PPSFERI2 AS a_111728," +
        "           b.I_DEC_PORBETM1 AS a_111740, b.I_DEC_IEPNXRG1 AS a_111741, b.I_DEC_IAPNCRG1 AS a_111742," +
        "           b.I_DEC_MDUOITN1 AS a_111724, b.I_DEC_IPSEDUC1 AS a_111927, b.I_DEC_IADUNCI1 AS a_111726," +
        "           b.I_DEC_IONBGTR1 AS a_111850, b.I_DEC_IEDINXE1 AS a_111851, b.I_DEC_DAGDIEU1 AS a_111852," +
        "           b.I_DEC_IADINCE1 AS a_111856, b.I_DEC_PDIEENR1 AS a_111853, b.I_DEC_TIONTGA1 AS a_111880," +
        "           b.I_DEC_IEAPSPA1 AS a_111881, b.I_DEC_DAAPSPA1 AS a_111882, b.I_DEC_UFTIISL1 AS a_111883," +
        "           b.I_DEC_PFEIRSD1 AS a_111884, b.I_DEC_PUTPEAT1 AS i_dec_putpeat1, b.I_DEC_DUAPSPA1 AS i_dec_duapspa1," +
        "           b.I_DEC_DPAPSPA1 AS i_dec_dpapspa1, b.I_DEC_PFEAAEE1 AS a_111885, b.I_DEC_UGATRCI1 AS a_111886," +
        "           c.I_DEC_TICONOT1 AS a_111142, c.I_DEC_IENXGER1 AS a_111143, c.I_DEC_DIENDVU1 AS a_111192," +
        "           c.I_DEC_ILIAEMO1 AS i_dec_iliaemo1, c.I_DEC_EFPDAMS1 AS i_dec_efpdams1, c.I_DEC_ODAGTEU1 AS a_111144," +
        "           c.I_DEC_UFTIISL1 AS i3_dec_uftiisl1, c.I_DEC_PFEIRSD1 AS i3_dec_pfeirsd1, c.I_DEC_PUTPEAT1 AS i3_dec_putpeat1," +
        "           c.I_DEC_DUITFIE1 AS a_111145, c.I_DEC_DPIEFRE1 AS a_111146, c.I_DEC_PFEAAEE1 AS a_111147," +
        "           c.I_DEC_UGATRCI1 AS a_111148, d.I_DEC_ITPANFN1 AS a_116392, d.I_DEC_ITPAEFN1 AS a_116393," +
        "           d.I_DEC_TIFONIT1 AS a_116399, d.I_DEC_GRIDTAE1 AS i_dec_gridtae1, d.I_DEC_TDAFOEU1 AS a_117918," +
        "           d.I_DEC_UFTIISL1 AS a_118952, d.I_DEC_PFEIRSD1 AS a_118954, d.I_DEC_PUTEATR1 AS i_dec_puteatr1," +
        "           d.I_DEC_DUITFIE1 AS i4_dec_duitfie1, d.I_DEC_DPIEFRE1 AS i4_dec_dpiefre1, d.I_DEC_PFEAAEE1 AS a_111005," +
        "           d.I_DEC_UGATRCI1 AS a_118955, b.I_DEC_SSPTCDA1 AS a_117001, b.I_DEC_CPEIREI1 AS a_117073," +
        "           b.I_DEC_SFEIGAU1 AS a_117071, b.I_DEC_VGVIAIA1 AS a_117076, b.I_DEC_GMTAARS1 AS a_117285," +
        "           b.I_DEC_ASEIJVP1 AS a_117065, b.I_DEC_CAIMSSU1 AS a_117066, b.I_DEC_ODTERDA1 AS a_117889," +
        "           b.I_DEC_PUTDCAC1 AS a_201356, b.I_DEC_PFEAPAA1 AS a_111191" + " FROM      dd_dec_didcemi1 a," +
        " OUTER     dd_dec_dida2ne1 b," + " OUTER     dd_dec_dida3ir1 c," + " OUTER     dd_dec_dida4ae1 d" +
        " WHERE     a.C_DEC_CERDALN1 = b.C_DEC_CERDALN1 AND a.C_DEC_CERDALN1 = c.C_DEC_CERDALN1" +
        " AND       a.C_DEC_CERDALN1 = d.C_DEC_CERDALN1 AND a.C_DEC_LDLEATV1 = b.C_DEC_LDLEATV1" +
        " AND       a.C_DEC_LDLEATV1 = c.C_DEC_LDLEATV1 AND a.C_DEC_LDLEATV1 = d.C_DEC_LDLEATV1" +
        " AND       a.C_DEC_FREECGH1 = b.C_DEC_FREECGH1 AND a.C_DEC_FREECGH1 = c.C_DEC_FREECGH1" +
        " AND       a.C_DEC_FREECGH1 = d.C_DEC_FREECGH1 AND a.D_DEC_RFCEEOG1 = b.D_DEC_RFCEEOG1" +
        " AND       a.D_DEC_RFCEEOG1 = c.D_DEC_RFCEEOG1 AND a.D_DEC_RFCEEOG1 = d.D_DEC_RFCEEOG1" +
        " AND       a.N_DEC_EJERCIC1 = b.N_DEC_EJERCIC1 AND a.N_DEC_EJERCIC1 = c.N_DEC_EJERCIC1" +
        " AND       a.N_DEC_EJERCIC1 = d.N_DEC_EJERCIC1 AND a.C_DEC_CPLEARV1 = b.C_DEC_CPLEARV1" +
        " AND       a.C_DEC_CPLEARV1 = c.C_DEC_CPLEARV1 AND a.C_DEC_CPLEARV1 = d.C_DEC_CPLEARV1 " +
        " AND       a.D_DEC_RFCEEOG1 in(?, ?, ?) AND a.N_DEC_EJERCIC1 = ? AND a.C_DEC_CPLEARV1 = ?";

    String CONSULTAR_DECLARACION_ISR_PF13_17_FORMA13 =
        " SELECT    n_numero_operacion AS numero_operacion, tdiepco1 AS tdiepco1, fperceh1 AS f_dec_fperceh1, i_111837 AS i_111837," +
        "           i_111838 AS i_111838, i_111810 AS i_111810, i_111811 AS i_111811, i_111812 AS i_111812, i_111813 AS i_111813," +
        "           '' AS i_dec_seumbps1, i_111848 AS i_111848, i_111817 AS i_111817, i_111815 AS i_111815, i_111818 AS i_111818," +
        "           i_111860 AS i_111860, '' AS i_dec_epidtsr1, '' AS i_dec_epipcns1, '' AS i_dec_peefafn1, i_111820 AS i_111820," +
        "           i_111819 AS i_111819, i_111822 AS i_111822, '' AS i_dec_cfietud1, '' AS i_dec_iirrpmn1, a_111888 AS a_111888," +
        "           a_111889 AS a_111889, i_111021 AS i_111021, i_111827 AS i_111827, '' AS i_dec_iademce1, '' AS i_dec_dciafre1," +
        "           i_111023 AS i_111023, '' AS i_dec_irpeaie1, i_111024 AS i_111024, a_111201 AS a_111201, a_111202 AS a_111202," +
        "           a_111207 AS a_111207, a_111206 AS a_111206, a_111401 AS a_111401, a_111421 AS a_111421, a_111402 AS a_111402," +
        "           '' AS i_dec_ilpioug1, '' AS i_dec_efpdama1, a_111422 AS a_111422, a_111407 AS a_111407, a_111404 AS a_111404," +
        "           a_111938 AS a_111938, a_111501 AS a_111501, a_111504 AS a_111504, a_111523 AS a_111523, '' AS i_dec_iliebim1," +
        "           a_111544 AS a_111544, a_111535 AS a_111535, a_111840 AS a_111840, a_111550 AS a_111550, a_111551 AS a_111551," +
        "           a_111552 AS a_111552, a_111553 AS a_111553, a_111842 AS a_111842, a_111743 AS a_111743, a_111730 AS a_111730," +
        "           a_111732 AS a_111732, a_111744 AS a_111744, a_111734 AS a_111734, a_111728 AS a_111728, a_111740 AS a_111740," +
        "           a_111741 AS a_111741, a_111742 AS a_111742, a_111724 AS a_111724, a_111927 AS a_111927, a_111726 AS a_111726," +
        "           a_111850 AS a_111850, a_111851 AS a_111851, a_111852 AS a_111852, a_111856 AS a_111856, a_111853 AS a_111853," +
        "           a_111880 AS a_111880, a_111881 AS a_111881, a_111882 AS a_111882, a_111883 AS a_111883, a_111884 AS a_111884," +
        "           '' AS i_dec_putpeat1, '' AS i_dec_duapspa1, '' AS i_dec_dpapspa1, a_111885 AS a_111885, a_111886 AS a_111886," +
        "           a_111142 AS a_111142, a_111143 AS a_111143, a_111192 AS a_111192, '' AS i_dec_iliaemo1, '' AS i_dec_efpdams1," +
        "           a_111144 AS a_111144, '' AS i3_dec_uftiisl1, '' AS i3_dec_pfeirsd1, '' AS i3_dec_putpeat1, a_111145 AS a_111145," +
        "           a_111146 AS a_111146, a_111147 AS a_111147, a_111148 AS a_111148, a_116392 AS a_116392, a_116393 AS a_116393," +
        "           a_116399 AS a_116399, '' AS i_dec_gridtae1, a_117918 AS a_117918, a_118952 AS a_118952, a_118954 AS a_118954," +
        "           '' AS i_dec_puteatr1, '' AS i4_dec_duitfie1, '' AS i4_dec_dpiefre1, a_111005 AS a_111005, a_118955 AS a_118955," +
        "           a_117001 AS a_117001, a_117073 AS a_117073, a_117071 AS a_117071, a_117076 AS a_117076, a_117285 AS a_117285," +
        "           a_117065 AS a_117065, a_117066 AS a_117066, a_117889 AS a_117889, a_201356 AS a_201356, a_111191 AS a_111191" +
        " FROM      forma_13" + " WHERE     rfceeog1 in(?, ?, ?) AND n_ejercicio = ?  AND  c_n_periodo = ?";

    String CONSULTAR_DECLARACION_ISR_PF13_17_FORMA13_A =
        "SELECT     n_numero_operacion AS numero_operacion, tdiepco1  AS tdiepco1, fperceh1 AS f_dec_fperceh1, '' AS i_111837," +
        "           '' AS i_111838, '' AS i_111810, i_111811 AS i_111811, i_111812 AS i_111812, i_111813 AS i_111813, '' AS i_dec_seumbps1," +
        "           i_111848 AS i_111848, i_111817 AS i_111817, '' AS i_111815, i_111818 AS i_111818, '' AS i_111860, '' AS i_dec_epidtsr1," +
        "           '' AS i_dec_epipcns1, '' AS i_dec_peefafn1, i_111820 AS i_111820, i_111819 AS i_111819, i_111822 AS i_111822," +
        "           '' AS i_dec_cfietud1, '' AS i_dec_iirrpmn1, i_111888 AS a_111888, i_111889 AS a_111889, i_111021 AS i_111021," +
        "           '' AS i_111827, '' AS i_dec_iademce1, '' AS i_dec_dciafre1, '' AS i_111023, '' AS i_dec_irpeaie1, i_111024 AS i_111024," +
        "           i_111201 AS a_111201, i_111202 AS a_111202, i_111207 AS a_111207, i_111206 AS a_111206, '' AS a_111401, '' AS a_111421," +
        "           '' AS a_111402, '' AS i_dec_ilpioug1, '' AS i_dec_efpdama1, '' AS a_111422, '' AS a_111407, '' AS a_111404, '' AS a_111938," +
        "           '' AS a_111501, '' AS a_111504, '' AS a_111523, '' AS i_dec_iliebim1, '' AS a_111544, '' AS a_111535, '' AS a_111840, '' AS a_111550," +
        "           '' AS a_111551, '' AS a_111552, '' AS a_111553, '' AS a_111842, '' AS a_111743, '' AS a_111730, '' AS a_111732, '' AS a_111744," +
        "           '' AS a_111734, '' AS a_111728, '' AS a_111740, '' AS a_111741, '' AS a_111742, '' AS a_111724, '' AS a_111927, '' AS a_111726," +
        "           '' AS a_111850, '' AS a_111851, '' AS a_111852, '' AS a_111856, '' AS a_111853, '' AS a_111880, '' AS a_111881, '' AS a_111882," +
        "           '' AS a_111883, '' AS a_111884, '' AS i_dec_putpeat1, '' AS i_dec_duapspa1, '' AS i_dec_dpapspa1, '' AS a_111885, '' AS a_111886," +
        "           '' AS a_111142, '' AS a_111143, '' AS a_111192, '' AS i_dec_iliaemo1, '' AS i_dec_efpdams1, '' AS a_111144, '' AS i3_dec_uftiisl1," +
        "           '' AS i3_dec_pfeirsd1, '' AS i3_dec_putpeat1, '' AS a_111145, '' AS a_111146, '' AS a_111147, '' AS a_111148, '' AS a_116392," +
        "           '' AS a_116393, '' AS a_116399, '' AS i_dec_gridtae1, '' AS a_117918, '' AS a_118952, '' AS a_118954, '' AS i_dec_puteatr1," +
        "           '' AS i4_dec_duitfie1, '' AS i4_dec_dpiefre1, '' AS a_111005, '' AS a_118955, '' AS a_117001, '' AS a_117073, '' AS a_117071," +
        "           '' AS a_117076, '' AS a_117285, '' AS a_117065, '' AS a_117066, '' AS a_117889, '' AS a_201356, '' AS a_111191" +
        " FROM      forma13A" + " WHERE     rfceeog1 in(?, ?, ?) AND n_ejercicio = ? AND   c_n_periodo = ?";

    String CONSULTAR_DECLARACION_ISR_PF13_17A_FORMA13_ANE =
        " SELECT    n_numero_operacion AS numero_operacion, tdiepco1, fperceh1, tipo_ingreso, rfc_ret_exp, importe1," +
        "           importe4" + " FROM      forma_13_ane" +
        " WHERE     rfceeog1 in(?, ?, ?) AND c_n_periodo   =  ? AND n_ejercicio = ? AND retencion_deduccion = 2";

    String CONSULTAR_DECLARACION_ISR_PF13_17A_FORMA13_A_ANE =
        " SELECT    n_numero_operacion AS numero_operacion, tdiepco1, fperceh1, tipo_ingreso, rfc_ret_exp, importe1," +
        "           importe4" + " FROM      forma13a_ane" +
        " WHERE     rfceeog1 in(?, ?, ?) AND c_n_periodo  = ? AND n_ejercicio = ? AND retencion_deduccion = 2";

    String CONSULTAR_DECLARACION_ISR_PF13_17A_DD_DEC_DIDA1DD1 =
        " SELECT    n_dec_noupmee1 AS numero_operacion, c_dec_ctdalie1 AS tdiepco1, f_dec_fperceh1 AS fperceh1," +
        "           c_dec_ctdpfda1 AS tipo_ingreso, d_dec_rfcqece1 AS rfc_ret_exp, i_dec_importe1 AS importe1," +
        "           i_dec_sccrgeu1 AS importe4" + " FROM      dd_dec_dida1dd1" +
        " WHERE     d_dec_rfceeog1 in(?, ?, ?) AND n_dec_ejercic1 = ? AND c_dec_cplearv1 = ?";

    String CONSULTAR_DECLARACION_ISR_PF13_17B_ANEXO5_A2 =
        " SELECT    b.n_numero_operacion AS numero_operacion, b.tdiepco1 AS tdiepco1, a.f_fperceh1 AS f_fperceh1," +
        "           a.n_c1_80218 AS n_c1_80218, a.i_c1_05223 AS i_c1_05223, a.d_c1_05736 AS d_c1_05736, a.d_c1_05246 AS d_c1_05246," +
        "           a.i_c1_3109205 AS i_c1_3109205, a.i_c1_3109305 AS i_c1_3109305, a.i_c1_3109405 AS i_c1_3109405" +
        " FROM      forma_13_5a2 a, forma_13 b" +
        " WHERE     a.c_rfceeog1 = b.rfceeog1 AND       a.c_rfceeog1 in(?, ?, ?) AND a.n_periodo1 = ? AND a.n_ejercic1 = ?";
    String CONSULTA_DECLARACION_IA_PF_DATOS_DECLARACIONES_01 =
        "SELECT n_numero_operacion, n_ejercicio, c_n_periodo, tdiepco1, fperceh1, i_121026, i_121003, i_121004, i_121006, a_121102, a_121101, i_121039, i_121007, " +
        "i_121008, i_121009, i_121012, i_121013, i_121014, i_121015, i_121860, i_121017, i_121016, i_121019, i_111923, a_121048, a_121049, i_121021 " +
        "FROM forma_13 " + " WHERE  rfceeog1 in(?, ?, ?) " + "AND c_n_periodo = ? " + "AND n_ejercicio = ?";

    String CONSULTA_DECLARACION_IA_PF_DATOS_DECLARACIONES_02 =
        "SELECT n_dec_noupmee1, n_dec_ejercic1, c_dec_cplearv1, c_dec_ctdalie1, f_dec_fperceh1, n_dec_soaa5li1, i_dec_ptreorm1, i_dec_pafdrci1, i_dec_pdreoum1, i_dec_vctaael1, i_dec_tbeoijt1, i_dec_dsmgvea1, " +
        "i_dec_vaaclto1, i_dec_idmeptu1, i_dec_idaa5li1, i_dec_iaicmcm1, i_dec_iraemec1, i_dec_iraeame1, i_dec_oatcrro1, i_dec_iaaefad1, i_dec_ppepaar1, i_dec_dciafre1, i_dec_iasfemc1, i_dec_irpeaia2, i_dec_iaaoccc1, " +
        "i_dec_iaaocfc1, i_dec_iaaicem1 from dd_dec_didcemi1 " + "WHERE d_dec_rfceeog1 in(?, ?, ?) " +
        "AND c_dec_cplearv1 = ? " + "AND n_dec_ejercic1 = ?";

    String CONSULTAR_DECLARACION_ISR_PF13_17B_ANEX0_1 =
        " SELECT    N_DEC_NOUPMEE1 AS numero_operacion, C_DEC_CTDALIE1 AS tdiepco1, F_DEC_FPERCEH1 AS f_fperceh1," +
        "           c_dec_ctspfda1 AS n_c1_80218, d_dec_rfccscs1 AS i_c1_05223, n_dec_ppoarrc1 AS d_c1_05736," +
        "           i_dec_iqlcnue1 AS d_c1_05246, i_dec_aaqlccc1 AS i_c1_3109205, i_dec_irqlcme1 AS i_c1_3109305," +
        "           i_dec_iavaqlc1 AS i_c1_3109405" + " FROM      dd_dec_dida1ic1" +
        " WHERE     d_dec_rfceeog1 in(?, ?, ?) AND c_dec_cplearv1 = ? AND n_dec_ejercic1 = ?";

    String CONSULTAR_DECLARACION_ISR_PF13_17C_FORMA13 =
        " SELECT    n_numero_operacion AS numero_operacion, tdiepco1 AS tdiepco1, fperceh1 AS fperceh1, tipo_ingreso AS tipo_ingreso," +
        "           rfc_ret_exp AS rfc_ret_exp, importe1 AS importe1, importe4 AS importe4" +
        " FROM      forma_13_ane" +
        " WHERE     rfceeog1 in(?, ?, ?) AND c_n_periodo = ? AND n_ejercicio = ? AND retencion_deduccion = 2";

    String CONSULTAR_DECLARACION_ISR_PF13_17C_FORMA13_A =
        " SELECT    n_numero_operacion AS numero_operacion, tdiepco1 AS tdiepco1, fperceh1 AS fperceh1, tipo_ingreso AS tipo_ingreso," +
        "           rfc_ret_exp AS rfc_ret_exp, importe1 AS importe1, importe4 AS importe4" +
        " FROM      forma13a_ane" +
        " WHERE     rfceeog1 in(?, ?, ?) AND c_n_periodo = ? AND n_ejercicio = ? AND retencion_deduccion = 2";

    String CONSULTAR_DECLARACION_ISR_PF13_17C_FORMA_DID =
        " SELECT    n_dec_noupmee1 AS numero_operacion, c_dec_ctdalie1 AS tdiepco1, f_dec_fperceh1 AS fperceh1," +
        "           c_dec_ctdpfda1 AS tipo_ingreso, d_dec_rfcqece1 AS rfc_ret_exp, i_dec_importe1 AS importe1," +
        "           i_dec_sccrgeu1 AS importe4" + " FROM      dd_dec_dida1dd1" +
        " WHERE     d_dec_rfceeog1 in(?, ?, ?) AND n_dec_ejercic1 = ? AND c_dec_cplearv1 = ?";

    String CONSULTAR_DECLARACION_IEPS4_CONSULTA_DATOS_DECLARACION =
        "SELECT ldleacv1, p2_141906 AS v_p2_141906, p2_141907 AS v_p2_141907, p2_141908 AS v_p2_141908, p2_141917 AS v_p2_141917, p2_141918 AS v_p2_141918, p2_141910 AS v_p2_141910, p2_141911 AS v_p2_141911, " +
        "p2_141521 AS v_p2_141521, p2_141912 AS v_p2_141912, p2_141919 AS v_p2_141919, p2_141920 AS v_p2_141920, p2_141914 AS v_p2_141914, p2_141915 AS v_p2_141915, p2_141916 AS v_p2_141916 " +
        "FROM forma1e1, ci_dec_cpaetra1 " + "  WHERE rfceeog1 in   (?, ?, ?) " + "   AND iapidne1 >= ? " +
        "AND iapfdne1 <= ? " + " AND c_dec_cplearv1 = ? " + "AND impidee1 = n_dec_impidee1 " +
        " AND impfdee1 = n_dec_impfdee1";

    /** LLamadas a Store Procedure (ddwh.) */
    String STORE_PROCEDURE_BUSCA_ICEP_URDC_FAT = "SP_SIO_URDCFAT1";
    String STORE_PROCEDURE_BUSCA_ICEP_URDCSIL1 = "SP_SIO_URDCSIL1";
    String STORE_PROCEDURE_BUSCA_ICEP_URUCPLE = "sp_sio_urucple1";
    String STORE_PROCEDURE_BUSCA_CPR = "sp_consultardatoscprx";
    String STORE_PROCEDURE_BUSCA_ALTEX = "sp_consultaraltex";
    String STORE_PROCEDURE_BUSCA_DIOT = "sp_consultardiot";

    /** StoreProcedure Immex (ddwh.)*/
    String STORE_PROCEDURE_BUSCA_IMMEX = "sp_sio_cvecimm1";

    /** StoreProcedure COnsultaUltimaDeclaracionPF (ddwh.)*/
    String STORE_PROCEDURE_BUSCA_ULTIMA_DECLARACION_PF = "sp_sio_cudpfeo1";

    /** Registrar Informacion Del ICEP (ddwh.)*/
    String STORE_PROCEDURE_BUSCA_PAGOS_ICEP_IFX = "sp_sio_trvcpoe1";
    String STORE_PROCEDURE_BUSCA_PAGOS_TRAMITE_ICEP_IFX = "sp_sio_trvcfat1";
    String STORE_PROCEDURE_BUSCA_DECLARACONES_ICEP_ORA = "SP_SIO_TRVCSIO1";
    String STORE_PROCADURE_EMPRESAS_CERTIFICADAS = "sp_sio_decemev1";


}
