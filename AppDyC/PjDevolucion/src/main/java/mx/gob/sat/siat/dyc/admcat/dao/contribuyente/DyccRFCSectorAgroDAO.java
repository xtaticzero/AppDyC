package mx.gob.sat.siat.dyc.admcat.dao.contribuyente;

import java.util.List;

import mx.gob.sat.siat.dyc.admcat.vo.DyccRfcSectorAgroVO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface DyccRFCSectorAgroDAO {


    List<DyccRfcSectorAgroVO> consultar() throws SIATException;

    List<DyccRfcSectorAgroVO> consultar(DyccRfcSectorAgroVO dyccRfcSectorAgroVO) throws SIATException;

    void update(DyccRfcSectorAgroVO dyccRfcSectorAgroVO) throws SIATException;

    void insert(DyccRfcSectorAgroVO dyccRfcSectorAgroVO) throws SIATException;

    boolean existe(String rfc) throws SIATException;


    Integer findRfcSectorAgro(String rfc) throws SIATException;

    void eliminar(String rfc) throws SIATException;

}
