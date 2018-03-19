package mx.gob.sat.siat.dyc.util.constante;

public final class ConstantesIds {
    private ConstantesIds() {
        super();
    }
    
    public static class ClavesAdministraciones {
        public static final int GENERAL_COMERCIO_EXTERIOR = 80;
        public static final int CENTRAL_VERIF_HIDROCARBUROS = 81;
        public static final int CENTRAL_FISC_HIDROCARBUROS = 82;
        public static final int CENTRAL_EMPR_CONSOLIDAN_FISC = 90;
        public static final int CENTRAL_FISC_SECTOR_FINANCIERO = 91;
        public static final int CENTRAL_GRANDES_CONTRIBUYENTES = 97;
        public static final int CENTRAL_DYC = 100;
    }

    public static class IdsParametrosApp {
        public static final int MONTOLIMITE_GRANDESCONT_REGCOMP = 8;
        public static final int MONTOLIMITE_CONT_ALAF_REGCOMP = 9;
    }

    public static class IdsTiposPersona {
        public static final int PERSONA_FISICA = 9;
        public static final int PERSONA_MORAL = 2;
        public static final int SOC_MERCAN_CONTROL = 300063;
    }

    public static class IdsTiposTramite {
        public static final int DEVOL_SAF_ISR_PERSMORAL = 111;
        public static final int DEVOL_SAF_ISR_PERSFISICA = 115;
        public static final int DEVAUT_SAF_ISR_PERSFIS = 132;
    }

    public static class IdsConceptos {
        public static final int ISR_PERSONAS_MORALES = 101;
        public static final int ISR_PERSONAS_FISICAS = 119;
        public static final int IEPS_ALCOHOL_DESNAT_MIELES = 404;
    }

    public static class IdsContribuyentesConfiables {
        public static final int ES_CONFIABLE = 1;
        public static final int ES_NO_CONFIABLE = 0;
    }

    public static class IdsTipoPeriodo {
        public static final int MENSUAL = 0;
        public static final int BIMESTRAL = 1;
        public static final int TRIMESTRAL = 2;
        public static final int SEMESTRAL_A = 3;
        public static final int SEMESTRAL_B = 4;
    }

    public static class IdsValidacionesTramite {
        public static final int PROTESTA_DECIR_VERDAD = 7;
    }
    
    public static final int ID_ADM_ADAF = 51;
    public static final int ID_ADM_AGSC = 70;
    public static final int ID_ADM_AGACE = 11;
    public static final int ID_ADM_AGH = 19;
    public static final int ID_ADM_ACDC = 50;
    public static final int ID_ADM_AGGC = 90;
    
    /**ValidadorRNRegistro*/
    public static final String CLAVE_SIR_NUM_CTRL = "claveSirNumControl";
    public static final String CLAVE_ADMON = "claveAdministracion";

    public static final int CENTRO_COSTOS_INI_AGSC = 700;
    public static final int CENTRO_COSTOS_FIN_AGSC = 799;
    public static final int CENTRO_COSTOS_INI_ADAF = 501;
    public static final int CENTRO_COSTOS_FIN_ADAF = 599;

    public static final int CENTRO_COSTOS_AGGC = 900;
    public static final int CENTRO_COSTOS_AGH = 199;
    public static final int CENTRO_COSTOS_ACDC = 500;
    
    public static final int COMPETENCIA_AGGC  = 1;
    public static final int COMPETENCIA_AGACE = 2;
    public static final int COMPETENCIA_AGH   = 3;
    public static final int COMPETENCIA_AGGAF = 4;
    
    /** tramites IVA productos  */
    public static final int IVA_NUEVAS_INVERCIONES = 133;
    public static final int IVA_MEDICINA = 134;
    public static final int IVA_PRODUCTOS_ALIMENTACION = 135;
    public static final int IVA_CONVENCIONAL = 103;
    public static final int IVA_EXPORTACION = 137;
    public static final int ISR_PERSONAS_FISICAS = 115;
    public static final int ISR_HIDROCARBUROS = 136;
    
    public static final Integer IDPARAM_MONTOLIMITE_JEFEDPTO_AGAFF = 1;
    public static final Integer IDPARAM_MONTOLIMITE_JEFEDPTO_ACGC = 2;
    public static final Integer IDPARAM_MONTOLIMITE_SUBADM_AGAFF = 3;
    public static final Integer IDPARAM_MONTOLIMITE_SUBADM_ACGC = 4;
    public static final Integer IDPARAM_MONTOLIMITE_ADMAREA_ACGC = 6;

    public static final int ES_CERTIFICADO = 1;
    public static final int ES_REMANENTE = 1;
    public static final int ES_ROL_PERSONA_FISICA = 1;
    public static final int ES_ROL_PERSONA_MORAL = 1;
    public static final int ES_ROL_GRAN_CONTRIBUYENTE = 1;
    public static final int ES_ROL_DICTAMINADOR = 1;
    public static final int ES_ROL_SOCIEDAD_CONTROL = 1;
    public static final int PORCENTAJE_PERM_COMP_IA_ISR = 12;
    
    public static final int ID_QUERY_74 = 74;
    public static final int ID_PLANTILLA_78 = 78;
    public static final int ID_PLANTILLA_83 = 83;
    
    //ConstantesDyC para los estados del requerimiento
    public static final Integer ESTADO_REQ_EMITIDO = 1;
    public static final Integer ESTADO_REQ_AUTORIZADO = 2;
    public static final Integer ESTADO_REQ_RECHAZADO = 3;
    public static final Integer ESTADO_REQ_VENCIDO = 4;
    public static final Integer ESTADO_REQ_SOLVENTADO = 5;

    //ConstantesDYC para los estados de accion seguimiento
    public static final Integer ACCION_SEGUIMIENTO = 3;

    //ConstantesDYC para los estados de documento
    public static final Integer ESTADO_DOC_ADJUNTADO = 1;

    //ConstantesDyC para los estados de la solicitud
    public static final Integer ESTADO_SOL_RECIBIDA = 2;
    public static final Integer ESTADO_SOL_EN_PROCESO = 3;
    public static final Integer ESTADO_SOL_RECIBIDA_A_INSISTENCIA_DEL_CONTRIBUYENTE = 14;
    
    //ConstantesDyC para validacion Identificar Administracion
    public static final int ACDC = 100;
    public static final int ACDC_NUMCONTROL = 10;
    public static final int ACFECF = 90;
    public static final int ACFSF = 91;
    public static final int ACFGCD = 97;
    public static final int AGACE = 110;

    public static final int GC_ALAF_PF = 300488;
    public static final int GC_ALAF_PM = 300489;
    public static final int CC = 300063;
    public static final int SOC_MERCAN_CONTROL = 300063;
    public static final int SOC_MERCAN_CONTROLA = 300293;
    public static final int INTEGRADORA_PM = 300531;
    public static final int INTEGRADA_PM = 300532;
    public static final int GC_ACFECF_PM = 300544;
    public static final int SECTOR_FINANCIERO = 300252;
    public static final int SOC_MUTUAL_Q_NOPERE = 300079;
    public static final int SOC_CONTROL_GRUPOS = 300081;
    public static final int SF_ARRENDADORA_FINAN = 300253;
    public static final int SF_BANCA_DESARROLLO = 300254;
    public static final int SF_BANCA_MULTIPLE = 300255;
    public static final int SF_BANCO_DE_MEXICO = 300256;
    public static final int SF_BOLSA_DE_VALORES = 300257;
    public static final int SF_BOLSA_MEXI_VALORES = 300258;
    public static final int SF_CASA_BOLSA = 300259;
    public static final int SF_CASA_CAMBIO = 300260;
    public static final int SF_COMPA_AFIANZADOR = 300261;
    public static final int SF_GRUPOS_FINANCIERO = 300266;
    public static final int SF_INMOBILIA_FINANCIERO = 300267;
    public static final int SF_INSTITU_FIANZAS = 300268;
    public static final int SF_INSTITU_SEGUROS = 300269;
    public static final int SF_SOCIEDAD_AHORRO_PRES = 300270;
    public static final int SF_SOCIEDAD_INVER = 300271;
    public static final int SF_SOCIEDAD_INVER_ESP = 300272;
    public static final int SF_INST_DEPOS_VALORES = 300274;
    public static final int SF_ADM_FONDOS_PEL_RETIRO = 300275;
    public static final int SF_ALMACEN_GRAL_DEPO = 300276;
    public static final int SF_ASOC_MEX_INST_SEGUROS = 300277;
    public static final int SF_COM_NAC_BANCARIA_VALORES = 300278;
    public static final int SF_COM_NAC_SEGUROS_FIANZA = 300279;
    public static final int SF_EMPRESAS_FACTORAJE = 300280;
    public static final int SF_FILIALES_BANCOS_EXTRAN = 300281;
    public static final int SF_OPERADORAS_SOC_INVERSION = 300282;
    public static final int SF_REP_BANCA_EXTRANJERA = 300285;
    public static final int SF_SOC_INVER_INST_ADEUDA = 300286;
    public static final int SF_SOC_INVER_RENTA_FIJA_COM = 300287;
    public static final int SF_SOC_INVERSI_CAPITAL = 300288;
    public static final int SF_SOCIEDAD_INVER_COMUN = 300289;
    public static final int SF_COM_NAC_SIS_AHORRO_RETIRO = 300305;
    public static final int SF_SOC_FINANCIE_OBJ_LTDO = 300306;
    public static final int SF_SOFOM_ER = 300437;
    public static final int SF_SOFOM_ENR = 300438;
    public static final int SOCIEDAD_FINANCIERA_POPULAR = 300456;
    public static final int SOCIEDAD_INVERSION_SALUD = 300457;
    public static final int SF_OTRA_ENTIDAD_INTER_FINAN = 300284;
    public static final int OBLIGADO_DICTAMEN_PM = 300034;
    public static final int DICTAMINADO_OPCIONAL_PM = 300035;
    public static final int CONTRIBUYENTE_DICTAMIN_PF = 300152;
    public static final int CONTRIBUYENTE_DICTAMIN_PM = 300153;
    public static final int DICTAMINADO_OPCIONAL_PF = 300162;
    public static final int OBLIGADO_DICTAMEN_PF = 300243;
    public static final int AGH_FISCALIZACION_PM = 300557;
    public static final int AGH_FISCALIZACION_PF = 300558;
    public static final int AGH_VERIFICACION_PM = 300559;
    public static final int AGH_VERIFICACION_PF = 300560;
    public static final int AG_AUDITORIA_FISCAL_FED = 5;
    
    //ConstantesDyC para caso de uso 17 mantener el suborigen del saldo
    public static final int SUBORIGEN_DEL_SALDO_ID_CASO_DE_USO = 17;
    public static final int SUBORIGEN_DEL_SALDO_MENSAJE1 = 1;
    public static final int SUBORIGEN_DEL_SALDO_MENSAJE2 = 2;
    public static final int SUBORIGEN_DEL_SALDO_MENSAJE3 = 3;
    public static final int SUBORIGEN_DEL_SALDO_MENSAJE4 = 4;
    public static final int SUBORIGEN_DEL_SALDO_MENSAJE5 = 5;
    public static final int SUBORIGEN_DEL_SALDO_MENSAJE6 = 6;
    public static final int SUBORIGEN_DEL_SALDO_MENSAJE7 = 7;
    public static final int SUBORIGEN_DEL_SALDO_MENSAJE8 = 8;
    public static final int SUBORIGEN_DEL_SALDO_MENSAJE9 = 9;

    //ConstantesDyC Integrar Expediente
    public static final int TIPO_TRAMITE_NO334 = 334;
    public static final int TIPO_TRAMITE_NO382 = 382;
    public static final int TIPO_TRAMITE_NO347 = 347;
    public static final int TIPO_TRAMITE_NO120 = 120;
    public static final int TIPO_TRAMITE_NO451 = 451;
    public static final int TIPO_TRAMITE_NO489 = 489;
    public static final int TIPO_TRAMITE_NO545 = 545;
    public static final int TIPO_TRAMITE_NO553 = 553;
    public static final int TIPO_TRAMITE_NO124 = 124;
    public static final int TIPO_TRAMITE_NO117 = 117;
    public static final int TIPO_TRAMITE_NO119 = 119;
    public static final int TIPO_TRAMITE_NO114 = 114;
    
    //Constantes DyC Mensajes Control Saldo
    public static final int CU_CONSULTAR_MOVIMIENTO_ICEP = 92;
    public static final int ID_MOV_MOD_COMP = 537;
    public static final int ID_MOV_ELIM_ICEP = 538;
    public static final int MSG_CM_2 = 2;
    public static final int MSG_CM_3 = 3;
    public static final int MSG_CM_4 = 4;
    public static final int MSG_CM_5 = 5;
    public static final int MSG_CM_8 = 8;
    public static final int MSG_CM_9 = 9;
    public static final int MSG_CM_11 = 11;
    public static final int MSG_CM_12 = 12;
    public static final int MSG_CM_13 = 13;
    public static final int MSG_CM_14 = 14;
    public static final int MSG_CM_15 = 15;
    
    //ConstantesDyC para los mensajes
    public static final int CU_REGISTRO_SOLDEVLINEA = 1;
    public static final int MSG_1 = 1;
    public static final int MSG_2 = 2;
    public static final int MSG_3 = 3;
    public static final int MSG_4 = 4;
    public static final int MSG_5 = 5;
    public static final int MSG_6 = 6;
    public static final int MSG_7 = 7;
    public static final int MSG_8 = 8;
    public static final int MSG_9 = 9;
    public static final int MSG_10 = 10;
    public static final int MSG_11 = 11;
    public static final int MSG_13 = 13;
    public static final int MSG_14 = 14;
    public static final int MSG_15 = 15;
    public static final int MSG_16 = 16;
    public static final int MSG_23 = 23;
    public static final int MSG_24 = 24;
    public static final int MSG_25 = 25;
    public static final int MSG_26 = 26;
    public static final int MSG_27 = 27;
    public static final int MSG_28 = 28;
    public static final int MSG_31 = 31;
    public static final int MSG_37 = 37;
    public static final int MSG_38 = 38;
    public static final int MSG_40 = 40;
    public static final int MSG_41 = 41;
    public static final int MSG_42 = 42;
    public static final int MSG_45 = 45;
    public static final int MSG_46 = 46;
    public static final int MSG_47 = 47;
    public static final int MSG_48 = 48;
    public static final int MSG_51 = 51;
    public static final int MSG_52 = 52;
    public static final int MSG_53 = 53;
    public static final int MSG_54 = 54;
    public static final int MSG_55 = 55;
    public static final int MSG_71 = 71;
    public static final int MSG_114 = 114;
    public static final int MSG_115 = 115;
    public static final int MSG_116 = 116;
    public static final int MSG_117 = 117;
    public static final int MSG_118 = 118;
    public static final int CU_1 = 1;
    public static final int CU_53 = 53;
}
