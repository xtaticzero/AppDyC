package mx.gob.sat.siat.dyc.service;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.banco.DyctCuentaBancoDTO;
import mx.gob.sat.siat.dyc.util.common.ParamOutputTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.vo.RemplazaCuentaClabeVO;
import mx.gob.sat.siat.dyc.vo.ReqCuentaClabeVO;


public interface DyctCuentaBancoService {
    void createCuentaBanco(DyctCuentaBancoDTO input) throws SIATException;

    ParamOutputTO<ReqCuentaClabeVO> getReqCuentasCLABE(String rfc) throws SIATException;

    void actualizaCuenta(DyctCuentaBancoDTO dyctCuentaBancoDTO);
    
    void remplazaCuenta(DyctCuentaBancoDTO dyctCuentaBancoDTO);
    
    DyctCuentaBancoDTO consultaXNumCtrl(DyctCuentaBancoDTO dyctCuentaBancoDTO ) throws SIATException;
    
    RemplazaCuentaClabeVO consultaNumCtrlTramite(String numControl) throws SIATException;
    
    List<RemplazaCuentaClabeVO> consultaCuentaClabeXPagoTesofe(String rfc);
    
}
