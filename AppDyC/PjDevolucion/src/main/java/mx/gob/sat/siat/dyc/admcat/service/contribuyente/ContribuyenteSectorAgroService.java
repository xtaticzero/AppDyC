package mx.gob.sat.siat.dyc.admcat.service.contribuyente;

import java.util.List;

import mx.gob.sat.siat.dyc.admcat.vo.DyccRfcSectorAgroVO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface ContribuyenteSectorAgroService {
    
    List<DyccRfcSectorAgroVO> consultar() throws SIATException;
    
    List<DyccRfcSectorAgroVO> consultar(DyccRfcSectorAgroVO dyccRfcSectorAgroVO) throws SIATException;
    
    void insert(DyccRfcSectorAgroVO dyccRfcSectorAgroVO) throws SIATException;

    void update(DyccRfcSectorAgroVO dyccRfcSectorAgroVO) throws SIATException;
    
    void eliminar(String rfc) throws SIATException;

        
}
