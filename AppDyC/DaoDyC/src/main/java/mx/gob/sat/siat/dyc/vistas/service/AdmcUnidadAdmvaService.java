package mx.gob.sat.siat.dyc.vistas.service;

import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.domain.ags.DyccDeptAgsDTO;
import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidadAdmvaDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * @author  Luis Alberto Dominguez Palomino [LADP]
 * @since   20/10/2014
 */
public interface AdmcUnidadAdmvaService {
    
    /**
     * @param admva Objeto de tipo [CbzcUnidadadmvaDTO]
     * @return ArrayList de tipo [CbzcUnidadadmvaDTO]
     */
    List<AdmcUnidadAdmvaDTO> consultarUnidadAdmvaList(AdmcUnidadAdmvaDTO admva);

    /**
     * @param depto Objeto de tipo [DyccDeptAgsDTO]
     * @return ArrayList de tipo [CbzcUnidadadmvaDTO]
     */
    List<AdmcUnidadAdmvaDTO> consultarUnidadAdmvaList(DyccDeptAgsDTO depto);

    /**
     * @param admva
     * @return
     */
    List<AdmcUnidadAdmvaDTO> consultarUnidadAdmvaCentral(AdmcUnidadAdmvaDTO admva);

    boolean isALSC(Integer idTramite) throws SIATException;

    Map<String, Object> crearAccesoUsr(String numEmpleado);
    
    Object obtenerEmpleadoDyc(Integer numEmpleado);
    
    Object obtenerEmpleadoDycApDic(Integer numEmpleado);
    
    List<AdmcUnidadAdmvaDTO> obtenerClaveSirxCCostoOp(String centroCostoOp) throws SIATException; 
    
    boolean esRFCCorto(String str);

    boolean esRFCValido(String str);
    
    Integer obtieneCentroCostos(Integer claveAdm);
}

