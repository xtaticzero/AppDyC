package mx.gob.sat.siat.dyc.admcat.service.contribuyente.impl;

import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.dyc.admcat.dao.contribuyente.DyccRFCSectorAgroDAO;
import mx.gob.sat.siat.dyc.admcat.service.contribuyente.ContribuyenteSectorAgroService;
import mx.gob.sat.siat.dyc.admcat.vo.DyccRfcSectorAgroVO;
import mx.gob.sat.siat.dyc.dao.regsolicitud.DycpSolicitudDAO;
import mx.gob.sat.siat.dyc.trabajo.util.ValidadorCamposUtil;
import mx.gob.sat.siat.dyc.trabajo.util.constante.EnumErrorRFCSectorAgrario;
import mx.gob.sat.siat.dyc.util.common.SIATException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service(value = "contribuyenteSectorAgroService")
public class ContribuyenteSectorAgroServiceImp implements ContribuyenteSectorAgroService{
    
    @Autowired
    private DyccRFCSectorAgroDAO dyccRFCSectorAgroDAO;

    @Autowired    
    private DycpSolicitudDAO dycpSolicitudDAO;
    
    private static final int TRAMITE_IVA_SECTOR_APROPECAURIO = 130; 
    
    public List<DyccRfcSectorAgroVO> consultar() throws SIATException{
        return dyccRFCSectorAgroDAO.consultar();                                                  
    }

    public List<DyccRfcSectorAgroVO> consultar(DyccRfcSectorAgroVO dyccRfcSectorAgroVO) throws SIATException{
        return dyccRFCSectorAgroDAO.consultar(dyccRfcSectorAgroVO);                                                  
    }
    
    
    public void insert(DyccRfcSectorAgroVO dyccRfcSectorAgroVO) throws SIATException{
        if ( dyccRfcSectorAgroVO.getRfc() == null){
            throw new SIATException(EnumErrorRFCSectorAgrario.ERROR02.getId(), EnumErrorRFCSectorAgrario.ERROR02.getDescripcion() );
        }
        
        if ( dyccRfcSectorAgroVO.getRfc().trim().equals("")){
            throw new SIATException(EnumErrorRFCSectorAgrario.ERROR02.getId(), EnumErrorRFCSectorAgrario.ERROR02.getDescripcion() );
        }
        
        if (!ValidadorCamposUtil.esRFC( dyccRfcSectorAgroVO.getRfc() )){
            throw new SIATException(EnumErrorRFCSectorAgrario.ERROR04.getId(), EnumErrorRFCSectorAgrario.ERROR04.getDescripcion() );
        }
           
        if (dyccRFCSectorAgroDAO.existe(dyccRfcSectorAgroVO.getRfc())){
            throw new SIATException(EnumErrorRFCSectorAgrario.ERROR01.getId(), EnumErrorRFCSectorAgrario.ERROR01.getDescripcion() );
        }    
        
        if ( dyccRfcSectorAgroVO.getNombre() == null){
            throw new SIATException(EnumErrorRFCSectorAgrario.ERROR03.getId(), EnumErrorRFCSectorAgrario.ERROR03.getDescripcion() );
        }
        
        if ( dyccRfcSectorAgroVO.getNombre().trim().equals("")){
            throw new SIATException(EnumErrorRFCSectorAgrario.ERROR03.getId(), EnumErrorRFCSectorAgrario.ERROR03.getDescripcion() );
        }
        
    
        if (!ValidadorCamposUtil.esRazonSocial( dyccRfcSectorAgroVO.getNombre() )){
            throw new SIATException(EnumErrorRFCSectorAgrario.ERROR05.getId(), EnumErrorRFCSectorAgrario.ERROR05.getDescripcion() );
        }
        
        if (!dycpSolicitudDAO.existeTramite(dyccRfcSectorAgroVO.getRfc(), TRAMITE_IVA_SECTOR_APROPECAURIO)){
            throw new SIATException(EnumErrorRFCSectorAgrario.ERROR06.getId(), EnumErrorRFCSectorAgrario.ERROR06.getDescripcion() );
        }
        
        dyccRFCSectorAgroDAO.insert(dyccRfcSectorAgroVO);
        
    }

    public void update(DyccRfcSectorAgroVO dyccRfcSectorAgroVO) throws SIATException{
        if (dyccRfcSectorAgroVO.getActivo() == 1){ 
            dyccRfcSectorAgroVO.setFechaFin(null);
        }else{
            dyccRfcSectorAgroVO.setFechaFin(new Date());
        }
        
        dyccRFCSectorAgroDAO.update(dyccRfcSectorAgroVO);
    }
    
    public void eliminar(String rfc) throws SIATException{
    
        dyccRFCSectorAgroDAO.eliminar(rfc);
    }

    public void setDyccRFCSectorAgroDAO(DyccRFCSectorAgroDAO dyccRFCSectorAgroDAO) {
        this.dyccRFCSectorAgroDAO = dyccRFCSectorAgroDAO;
    }

    public DyccRFCSectorAgroDAO getDyccRFCSectorAgroDAO() {
        return dyccRFCSectorAgroDAO;
    }


    public void setDycpSolicitudDAO(DycpSolicitudDAO dycpSolicitudDAO) {
        this.dycpSolicitudDAO = dycpSolicitudDAO;
    }

    public DycpSolicitudDAO getDycpSolicitudDAO() {
        return dycpSolicitudDAO;
    }
}
