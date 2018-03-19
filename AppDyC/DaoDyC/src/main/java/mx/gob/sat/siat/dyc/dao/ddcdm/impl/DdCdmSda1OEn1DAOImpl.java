package mx.gob.sat.siat.dyc.dao.ddcdm.impl;

import mx.gob.sat.siat.dyc.dao.ddcdm.DdCdmSda1OEn1DAO;
import mx.gob.sat.siat.dyc.domain.ddcdm.DdCdmSda1OEn1DTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @author Jesus Alfredo Hernandez Orozco
 */
@Repository(value = "ddCdmSda1OEn1DAO")
public class DdCdmSda1OEn1DAOImpl implements DdCdmSda1OEn1DAO {
    public DdCdmSda1OEn1DAOImpl() {
        super();
    }
    
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;
    
    private static final Logger LOGGER = Logger.getLogger(DdCdmSda1OEn1DAOImpl.class);
    private static final String INSERTAR="insert into dd_cdm_sda1oen1 (NUMCONTROL, D_RFC00001, F_EFPJIEE1, D_NDSOEOM1, N_ISDMOEP1, " +
        "N_MGORNAT1, N_TASA0001, N_IDEDMEF1, N_IRDEECA1, N_IPDAEGA1, N_ITDOETA1, N_REESCTA1, N_RREECAA1, N_IFDDAEE1, " +
        "N_ICIPMAS1, N_IAIPMCS1, N_TDIPIES1, N_PIPESER1, N_EIPJSEE1, N_NOIPOPS1, N_ICISMAS1, N_IAISMCS1, N_TDISIES1, " +
        "N_PISESAR1, N_EISJSAE1, N_NOISOPS1, N_ICIHMAS1, N_IAIHMCS1, N_TDIHIES1, N_PIHESOR1, N_EIHJSOE1, N_NOIHOPS1, " +
        "N_ICIAMAS1, N_IAIAMCS1, N_TDIAIES1, N_PIAESRR1, N_EIAJSRE1, N_NOIAOPS1, N_ICIRMAS1, N_IAIRMCS1, N_TDIRIES1, " +
        "N_PIRESER1, N_EIRJSEE1, N_NOIROPS1, N_ICIRAEM1, N_IAIRAEM1, N_TDIRAEI1, N_PIRAEES1, N_EIRAEJS1, N_NOIRAEO1, " +
        "N_ICIORXM1, N_IAIORXM1, N_TDIORXI1, N_PIORXES1, N_EIORXJS1, N_NOIORXO1, N_TAOCTRA1, N_RDIEIDM1, N_ICIMOVP1, " +
        "N_TDIIEVP1, N_PIEVRAI1, N_EIJVEAR1, N_NOIUPVM1, N_FACIOVO1, N_ICIMOEP1, N_TDIIEEP1, N_PIEERTI1, N_EIJEETR1, " +
        "N_NOIUPEM1, N_FACIOVO2, N_ICIMOEP2, N_TDIIEEP2, N_PIEERPI1, N_EIJEEPR1, N_NOIUPEM2, N_FACIOVO3, N_TCOOTMA1, " +
        "N_IFDAEVO1, N_TAEIOCF1, N_TCETA2C1, N_RISDEDU1, D_A3CODR21, D_DFOPDEE1) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
        "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
        "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * <html>
     * <body>
     * Inserta un registro a la tabla Dd_Cdm_Sda1OEn1 a traves del paso del objeto DdCdmSda1OEn1DTO
     * </body>
     * </html>
     *
     * @param objeto
     * @throws SIATException
     */
    @Override
    public void insertar(DdCdmSda1OEn1DTO objeto) throws SIATException {
        try {
            jdbcTemplateDYC.update(INSERTAR, new Object[] { objeto.getNumControl(), objeto.getDRFC00001(),
                objeto.getFEFPJIEE1(), objeto.getDNDSOEOM1(), objeto.getNISDMOEP1(), objeto.getNMGORNAT1(),
                objeto.getNTASA0001(), objeto.getNIDEDMEF1(), objeto.getNIRDEECA1(), objeto.getNIPDAEGA1(),
                objeto.getNITDOETA1(), objeto.getNREESCTA1(), objeto.getNRREECAA1(), objeto.getNIFDDAEE1(),
                objeto.getNICIPMAS1(), objeto.getNIAIPMCS1(), objeto.getNTDIPIES1(), objeto.getNPIPESER1(),
                objeto.getNEIPJSEE1(), objeto.getNNOIPOPS1(), objeto.getNICISMAS1(), objeto.getNIAISMCS1(),
                objeto.getNTDISIES1(), objeto.getNPISESAR1(), objeto.getNEISJSAE1(), objeto.getNNOISOPS1(),
                objeto.getNICIHMAS1(), objeto.getNIAIHMCS1(), objeto.getNTDIHIES1(), objeto.getNPIHESOR1(),
                objeto.getNEIHJSOE1(), objeto.getNNOIHOPS1(), objeto.getNICIAMAS1(), objeto.getNIAIAMCS1(),
                objeto.getNTDIAIES1(), objeto.getNPIAESRR1(), objeto.getNEIAJSRE1(), objeto.getNNOIAOPS1(),
                objeto.getNICIRMAS1(), objeto.getNIAIRMCS1(), objeto.getNTDIRIES1(), objeto.getNPIRESER1(),
                objeto.getNEIRJSEE1(), objeto.getNNOIROPS1(), objeto.getNICIRAEM1(), objeto.getNIAIRAEM1(),
                objeto.getNTDIRAEI1(), objeto.getNPIRAEES1(), objeto.getNEIRAEJS1(), objeto.getNNOIRAEO1(),
                objeto.getNICIORXM1(), objeto.getNIAIORXM1(), objeto.getNTDIORXI1(), objeto.getNPIORXES1(),
                objeto.getNEIORXJS1(), objeto.getNNOIORXO1(), objeto.getNTAOCTRA1(), objeto.getNRDIEIDM1(),
                objeto.getNICIMOVP1(), objeto.getNTDIIEVP1(), objeto.getNPIEVRAI1(), objeto.getNEIJVEAR1(),
                objeto.getNNOIUPVM1(), objeto.getNFACIOVO1(), objeto.getNICIMOEP1(), objeto.getNTDIIEEP1(),
                objeto.getNPIEERTI1(), objeto.getNEIJEETR1(), objeto.getNNOIUPEM1(), objeto.getNFACIOVO2(),
                objeto.getNICIMOEP2(), objeto.getNTDIIEEP2(), objeto.getNPIEERPI1(), objeto.getNEIJEEPR1(),
                objeto.getNNOIUPEM2(), objeto.getNFACIOVO3(), objeto.getNTCOOTMA1(), objeto.getNIFDAEVO1(),
                objeto.getNTAEIOCF1(), objeto.getNTCETA2C1(), objeto.getNRISDEDU1(), objeto.getDA3CODR21(),
                objeto.getDDFOPDEE1()});
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + INSERTAR +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(objeto));
            throw new SIATException(e);   
        }
    }
}
