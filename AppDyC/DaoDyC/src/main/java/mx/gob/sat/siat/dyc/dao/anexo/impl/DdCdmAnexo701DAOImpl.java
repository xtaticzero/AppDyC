package mx.gob.sat.siat.dyc.dao.anexo.impl;

import mx.gob.sat.siat.dyc.dao.anexo.DdCdmAnexo701DAO;
import mx.gob.sat.siat.dyc.domain.ddcdm.DdCdmAnexo701DTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository(value = "ddCdmAnexo701DAO")
public class DdCdmAnexo701DAOImpl implements DdCdmAnexo701DAO {
    public DdCdmAnexo701DAOImpl() {
        super();
    }
    private static final String INSERTAR = "insert into dd_cdm_anexo701 (NUMCONTROL,C_CDM_RCFOCNT1,F_CDM_PTERRAI1,I_CDM_IBISGMI1," +
        "I_CDM_OAAGTCC1,I_CDM_SAAT1UC1,I_CDM_IBISMIN1,I_CDM_OAAGTCC2,I_CDM_SAAT1UC2,I_CDM_EXPORTA1,I_CDM_OAAGTCC3," +
        "I_CDM_SAAT0UC1,I_CDM_SAAGUCC1,I_CDM_VAAQSDP1,I_CDM_VAAQSOI1,I_CDM_TVAAPOA1,I_CDM_TITACOV1,I_CDM_TIPIBTO1," +
        "I_CDM_TIPIBIO1,I_CDM_TIPISOV1,I_CDM_SIPIUVA1,I_CDM_TITPOVR1,I_CDM_ITABVRD1,I_CDM_IPIABIV1,I_CDM_IPIBTVA1," +
        "I_CDM_ITAIDER1,I_CDM_IPIBTQS1,I_CDM_TICAAGO1,I_CDM_IPIABIS1,I_CDM_IPIABTD1,I_CDM_IPIBTQS2,I_CDM_IBUIRAA1," +
        "I_CDM_PUCAA451,I_CDM_IABUIRA1,I_CDM_IAVCARE1,I_CDM_MAAIDAO1,I_CDM_TIAPOVC1,I_CDM_ICPA1MA1,I_CDM_ICPA1MA2," +
        "I_CDM_CARDAAC1,I_CDM_SICUMAM1,I_CDM_IRACVEL1,I_CDM_IAPVCEA1,I_CDM_FAVOR001,I_CDM_DIOENBV1,I_CDM_SFPAAEL1," +
        "I_CDM_VAAGACC1,I_CDM_VAAQSDP2,I_CDM_VAAAQSO1,I_CDM_VAATACC1,I_CDM_IBSICST1,I_CDM_IBSICST2,I_CDM_IBSICST3," +
        "I_CDM_IBSICST4,I_CDM_EAFGCDA1,I_CDM_EAFGCDA2,I_CDM_EAFGCDA3,I_CDM_EAFGCDA4,I_CDM_DGIRVAI1,I_CDM_DQSDPII1," +
        "I_CDM_DAQSOII1,I_CDM_DTIOVTI1,I_CDM_EAPSDPC1,I_CDM_EAPSDPC2,I_CDM_EAPSDPC3,I_CDM_EAPSDPC4,I_CDM_EMNEPOP1," +
        "I_CDM_EMNEPOP2,I_CDM_EMNEPOP3,I_CDM_EMNEPOP4,I_CDM_IGCGNAA1,I_CDM_IGCQSDP1,I_CDM_IGCAQSO1,I_CDM_IGCTNAA1," +
        "I_CDM_ETAFGNR1,I_CDM_ETAFQSD1,I_CDM_ETAFAQS1,I_CDM_ETAFTNR1,I_CDM_EBADPAG1,I_CDM_EBADPAQ1,I_CDM_EBADPAA1," +
        "I_CDM_EBADPAT1,I_CDM_OFDGPIE1,I_CDM_OFDQSDP1,I_CDM_OFDAQSO1,I_CDM_OFDTPIE1,I_CDM_ECPIAGN1,I_CDM_ECPIAQS1," +
        "I_CDM_ECPIAAQ1,I_CDM_ECPIATN1) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
        "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?" +
        ",?,?)";
    private static final Logger LOGGER = Logger.getLogger(DdCdmAnexo701DAOImpl.class);
    
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    @Override
    public void insertar(DdCdmAnexo701DTO objeto) throws SIATException {
        try {
            jdbcTemplateDYC.update(INSERTAR, new Object[] { objeto.getNumControl(), objeto.getCCDMRCFOCNT1(),
                objeto.getFCDMPTERRAI1(), objeto.getICDMIBISGMI1(), objeto.getICDMOAAGTCC1(), objeto.getICDMSAAT1UC1(),
                objeto.getICDMIBISMIN1(), objeto.getICDMOAAGTCC2(), objeto.getICDMSAAT1UC2(), objeto.getICDMEXPORTA1(),
                objeto.getICDMOAAGTCC3(), objeto.getICDMSAAT0UC1(), objeto.getICDMSAAGUCC1(), objeto.getICDMVAAQSDP1(),
                objeto.getICDMVAAQSOI1(), objeto.getICDMTVAAPOA1(), objeto.getICDMTITACOV1(), objeto.getICDMTIPIBTO1(),
                objeto.getICDMTIPIBIO1(), objeto.getICDMTIPISOV1(), objeto.getICDMSIPIUVA1(), objeto.getICDMTITPOVR1(),
                objeto.getICDMITABVRD1(), objeto.getICDMIPIABIV1(), objeto.getICDMIPIBTVA1(), objeto.getICDMITAIDER1(),
                objeto.getICDMIPIBTQS1(), objeto.getICDMTICAAGO1(), objeto.getICDMIPIABIS1(), objeto.getICDMIPIABTD1(),
                objeto.getICDMIPIBTQS2(), objeto.getICDMIBUIRAA1(), objeto.getICDMPUCAA451(), objeto.getICDMIABUIRA1(),
                objeto.getICDMIAVCARE1(), objeto.getICDMMAAIDAO1(), objeto.getICDMTIAPOVC1(), objeto.getICDMICPA1MA1(),
                objeto.getICDMICPA1MA2(), objeto.getICDMCARDAAC1(), objeto.getICDMSICUMAM1(), objeto.getICDMIRACVEL1(),
                objeto.getICDMIAPVCEA1(), objeto.getICDMFAVOR001(), objeto.getICDMDIOENBV1(), objeto.getICDMSFPAAEL1(),
                objeto.getICDMVAAGACC1(), objeto.getICDMVAAQSDP2(), objeto.getICDMVAAAQSO1(), objeto.getICDMVAATACC1(),
                objeto.getICDMIBSICST1(), objeto.getICDMIBSICST2(), objeto.getICDMIBSICST3(), objeto.getICDMIBSICST4(),
                objeto.getICDMEAFGCDA1(), objeto.getICDMEAFGCDA2(), objeto.getICDMEAFGCDA3(), objeto.getICDMEAFGCDA4(),
                objeto.getICDMDGIRVAI1(), objeto.getICDMDQSDPII1(), objeto.getICDMDAQSOII1(), objeto.getICDMDTIOVTI1(),
                objeto.getICDMEAPSDPC1(), objeto.getICDMEAPSDPC2(), objeto.getICDMEAPSDPC3(), objeto.getICDMEAPSDPC4(),
                objeto.getICDMEMNEPOP1(), objeto.getICDMEMNEPOP2(), objeto.getICDMEMNEPOP3(), objeto.getICDMEMNEPOP4(),
                objeto.getICDMIGCGNAA1(), objeto.getICDMIGCQSDP1(), objeto.getICDMIGCAQSO1(), objeto.getICDMIGCTNAA1(),
                objeto.getICDMETAFGNR1(), objeto.getICDMETAFQSD1(), objeto.getICDMETAFAQS1(), objeto.getICDMETAFTNR1(),
                objeto.getICDMEBADPAG1(), objeto.getICDMEBADPAQ1(), objeto.getICDMEBADPAA1(), objeto.getICDMEBADPAT1(),
                objeto.getICDMOFDGPIE1(), objeto.getICDMOFDQSDP1(), objeto.getICDMOFDAQSO1(), objeto.getICDMOFDTPIE1(),
                objeto.getICDMECPIAGN1(), objeto.getICDMECPIAQS1(), objeto.getICDMECPIAAQ1(), objeto.getICDMECPIATN1() 
                                                            }
                                   );
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + INSERTAR +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(objeto));
            throw new SIATException(e);
        }
    }
}
