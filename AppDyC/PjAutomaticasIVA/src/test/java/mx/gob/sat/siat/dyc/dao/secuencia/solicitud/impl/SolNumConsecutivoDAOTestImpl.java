/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.siat.dyc.dao.secuencia.solicitud.impl;

import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.siat.dyc.dao.secuencia.solicitud.SolNumConsecutivoDAO;
import mx.gob.sat.siat.dyc.dao.secuencia.solicitud.impl.mapper.DycqNumControlMapper;
import mx.gob.sat.siat.dyc.domain.secuencia.DycqNumControlDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyC;
import mx.gob.sat.siat.dyc.util.constante.EDycDaoCodigoError;
import mx.gob.sat.siat.dyc.util.excepcion.DycDaoExcepcion;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;
import static mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC.FROM_DUAL;
import static mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC.SELECT;
import mx.gob.sat.siat.dyc.vo.DycLogEstadoVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author GAER8674
 */
@Repository
public class SolNumConsecutivoDAOTestImpl implements SolNumConsecutivoDAO, SQLOracleDyC {
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    @Override
    public DycqNumControlDTO getNumConsecutivo(String claveSir) throws SIATException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 2016-03-01
     * @param claveSir
     * @return
     * @throws DycDaoExcepcion 
     */
    @Override
    public String getNumConsecutivoDevIva(String claveSir) throws DycDaoExcepcion{
        return getNumConsecutivo("dycq_numcontrol", claveSir, ConstantesDyC.CONSECUTIVO_AUTOMATICASIVA);
    }

    /**
     * 2016-03-01
     * @param nombreBaseSecuencia
     * @param claveSir
     * @param mascaraSecuencia
     * @return
     * @throws DycDaoExcepcion 
     */
    private String getNumConsecutivo(String nombreBaseSecuencia, String claveSir, String mascaraSecuencia) throws DycDaoExcepcion{
        String num = null;
        DycqNumControlDTO numControl = null;
        final String nombreSecuencia = nombreBaseSecuencia + claveSir;
        List<DycLogEstadoVariable> estadoVariables = new ArrayList<DycLogEstadoVariable>();
        estadoVariables.add( new DycLogEstadoVariable("nombreSecuencia", nombreSecuencia) );

        try {
            numControl =
                    (DycqNumControlDTO)jdbcTemplateDYC.queryForObject(SELECT + nombreSecuencia + FROM_DUAL,
                                                                      new Object[] { }, new DycqNumControlMapper());
        } catch (DataAccessException dae) {
            throw new DycDaoExcepcion(EDycDaoCodigoError.BD_DYC_QUERY_SECUENCIA, estadoVariables, dae);
        }
        
        if (null != numControl) {
            if(numControl.getSecuencia().length() > mascaraSecuencia.length()){
                estadoVariables.add( new DycLogEstadoVariable("valorSecuencia", numControl.getSecuencia()) );
                throw new DycDaoExcepcion(EDycDaoCodigoError.BD_DYC_SECUENCIA_REQUIEREREINICIAR, estadoVariables);
            }
            
            num = mascaraSecuencia.substring((numControl.getSecuencia().length()));
            num = num + numControl.getSecuencia();
        }

        return num;
    }

    @Override
    public DycqNumControlDTO getNumConsecutivoCasoCom(String claveSir) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DycqNumControlDTO getNumConsecutivoOrigenSafCC() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer getNumConsecutivoSaldoIcep() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer getNumConsecutivoDetalleIcep() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer getFolioServicioTemp() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer getIdArchivo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer getIdDeclaracion() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
