package mx.gob.sat.siat.dyc.dao.ddcdm.impl;

import mx.gob.sat.siat.dyc.dao.ddcdm.DdCdmSda1Oen2DAO;
import mx.gob.sat.siat.dyc.domain.ddcdm.DdCdmSda1OEn2DTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @author Jesus Alfredo Hernandez Orozco
 */
@Repository(value = "ddCdmSda1Oen2DAO")
public class DdCdmSda1Oen2DAOImpl implements DdCdmSda1Oen2DAO {
    public DdCdmSda1Oen2DAOImpl() {
        super();
    }
    
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;
    
    private static final Logger LOGGER = Logger.getLogger(DdCdmSda1Oen2DAOImpl.class);
    private static final String INSERTAR="insert into dd_cdm_sda1oen2 (NUMCONTROL,C_ID_CONSC,D_RFC00001,D_NDSOEOM1,F_EFPJIEE1," +
                                         "N_ISDMOEP1,D_RIBFNAC1,D_IBQRRIN1,N_CBLAANB1,N_IDEMEFP1,N_EGXRCAE1,N_IDE00001," +
                                         "N_TIDEOME1,N_TEGOXRT1,N_TIODTEA1) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    
    /**
     * <html>
     * <body>
     * Inserta un registro en la tabla Dd_Cdm_Sda1Oen2 utilizando como parametro el objeto DdCdmSda1OEn2DTO 
     * </body>
     * </html>
     *
     * @param
     * @throws SIATException
     */
    @Override
    public void insertar (DdCdmSda1OEn2DTO objeto) throws SIATException {
        try {
            jdbcTemplateDYC.update(INSERTAR, new Object[] { objeto.getNumControl(),
                                                            objeto.getCIDConsc(),
                                                            objeto.getDRFC00001(),
                                                            objeto.getDNDS0E0M1(),
                                                            objeto.getFEFPJIEE1(),
                                                            objeto.getNISDMOEP1(),
                                                            objeto.getDRIBFNAC1(),
                                                            objeto.getDIBQRRIN1(),
                                                            objeto.getNCBLAANB1(),
                                                            objeto.getNIDEMEFP1(),
                                                            objeto.getNEGXRCAE1(),
                                                            objeto.getNIDE00001(), 
                                                            objeto.getNTIDEOME1(), 
                                                            objeto.getNTEGOXRT1(), 
                                                            objeto.getNTIODTEA1()
                                                          }
                                   );
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                         SQLOracleDyC.CONSULTA_ACTOADMTVO + ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(objeto));
            throw new SIATException(e);
        }
    }
}
