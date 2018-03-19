package mx.gob.sat.siat.dyc.dao.rfc.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.rfc.DycpRfcDAO;
import mx.gob.sat.siat.dyc.dao.rfc.impl.mapper.DycpRfcMapper;
import mx.gob.sat.siat.dyc.domain.rfc.DycpRfcDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository(value = "dycpRfcDAO")
public class DycpRfcDAOImpl implements DycpRfcDAO {

    private Logger log = Logger.getLogger(DycpRfcDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    public DycpRfcDAOImpl() {
        super();
    }

    @Override
    public List<DycpRfcDTO> obtenerRfcNoConfiables(String rfc, Integer activo, Integer inactivo, Integer padron) {
        return jdbcTemplateDYC.query(SQLOracleDyC.OBTENER_RFC_NOCONFIABLES.toString(), new Object[] { rfc, activo, inactivo, padron },
                                     new DycpRfcMapper());
    }

    @Override
    public List<DycpRfcDTO> obtenerRfcConfiables(Integer activo, Integer inactivo, Integer padron) {
        return jdbcTemplateDYC.query(SQLOracleDyC.OBTENER_RFC_CONFIABLES.toString(), new Object[] { activo, inactivo, padron },
                                     new DycpRfcMapper());
    }
    @Override
    public void insertar(DycpRfcDTO dycpRfcDTO) throws SIATException {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.INSERTAR_RFC_NOCONFIABLE.toString(),
                                   new Object[] { dycpRfcDTO.getRfc(), dycpRfcDTO.getNombreCompleto(),
                                                  dycpRfcDTO.getEsConfiable(), dycpRfcDTO.getEsNoConfiable(),
                                                  dycpRfcDTO.getPadronConfiable(),
                                                  dycpRfcDTO.getPadronNoConfiable() });
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.INSERTAR_RFC_NOCONFIABLE.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(dycpRfcDTO));
            throw new SIATException(dae);
        }
    }


    @Override
    public void actualizarNoConfiable(DycpRfcDTO dycpRfcDTO) throws SIATException {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.ACTUALIZAR_RFC_NOCONFIABLE.toString(),
                                   new Object[] { dycpRfcDTO.getEsNoConfiable(),
                                                  dycpRfcDTO.getRfc() });
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.ACTUALIZAR_RFC_NOCONFIABLE.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(dycpRfcDTO));
            throw new SIATException(dae);
        }
    }
    
    @Override
    public void actualizarConfiable(DycpRfcDTO dycpRfcDTO) throws SIATException {
        try {
            jdbcTemplateDYC.update(SQLOracleDyC.ACTUALIZAR_RFC_CONFIABLE.toString(),
                                   new Object[] { dycpRfcDTO.getEsConfiable(),
                                                  dycpRfcDTO.getRfc() });
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.ACTUALIZAR_RFC_NOCONFIABLE + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(dycpRfcDTO));
            throw new SIATException(dae);
        }
    }

    @Override
    public DycpRfcDTO encontrar(String rfc) {
        String sql = "SELECT * FROM DYCP_RFC WHERE RFC = ? ";
        DycpRfcDTO dycpRfcDTO = null;
        List<DycpRfcDTO> listDycpRfcDTO = null;
        try 
        {
            listDycpRfcDTO = jdbcTemplateDYC.query(sql, new Object[] { rfc }, new DycpRfcMapper());
            dycpRfcDTO = listDycpRfcDTO.get(0);
                
        } catch (Exception dae) 
        {
            if(listDycpRfcDTO!=null && listDycpRfcDTO.size() > 2)
            {
                log.error("La lista tiene " + listDycpRfcDTO.size() + " resultados ");
            }
            else
            {
                log.info("La lista no tiene  resultados: " + dae.getCause());
            }
        }
        return dycpRfcDTO;
    }
}
