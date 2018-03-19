package mx.gob.sat.siat.dyc.dao.banco;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.banco.DyctCuentaBancoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.excepcion.DycDaoExcepcion;
import mx.gob.sat.siat.dyc.vo.RemplazaCuentaClabeVO;
import mx.gob.sat.siat.dyc.vo.ReqCuentaClabeVO;


/**
 * @author JEFG
 */
public interface DyctCuentaBancoDAO {
    void createCuentaBanco(DyctCuentaBancoDTO input) throws SIATException;
    
    void insertar(DyctCuentaBancoDTO input) throws DycDaoExcepcion;
    
    List<ReqCuentaClabeVO> getReqCuentasCLABE(String rfc);
    
    void update(DyctCuentaBancoDTO dyctCuentaBancoDTO);
    
    void remplaza(DyctCuentaBancoDTO dyctCuentaBancoDTO);
    
    DyctCuentaBancoDTO consultaXNumCtrl(DyctCuentaBancoDTO dyctCuentaBancoDTO ) throws SIATException;
    
    RemplazaCuentaClabeVO consultaNumCtrlTramite(String numControl) throws SIATException;
    
    List<RemplazaCuentaClabeVO> consultaCuentaClabeXPagoTesofe(String rfc) throws SIATException;
    
    void actualizarCuentaValida(Integer status, String numeroControl) throws SIATException;
}
