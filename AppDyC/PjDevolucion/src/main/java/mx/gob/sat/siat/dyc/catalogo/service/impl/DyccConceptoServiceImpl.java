/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.catalogo.service.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.catalogo.service.DyccConceptoService;
import mx.gob.sat.siat.dyc.dao.concepto.DyccConceptoDAO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Servicio para el DTO DyccConceptoDTO
 * @author Paola Rivera
 */
@Service(value = "dyccConceptoService")
public class DyccConceptoServiceImpl implements DyccConceptoService {

    @Autowired
    private DyccConceptoDAO dyccConceptoDAO;

    private Logger log = Logger.getLogger(DyccConceptoServiceImpl.class);

    /**
     * @param idTipoTramite
     * @return
     */
    @Override
    public DyccConceptoDTO obtieneConceptoPorIdTipoTramite(long idTipoTramite) {
        return dyccConceptoDAO.obtieneConceptoPorIdTramite(idTipoTramite);
    }

    @Override
    public List<DyccConceptoDTO> obtieneConceptoPorIdImpuesto(int idImpuesto) {
        List<DyccConceptoDTO> listConcepto = new ArrayList<DyccConceptoDTO>();
        try {
            listConcepto = dyccConceptoDAO.obtieneConceptoPorImpuesto(idImpuesto);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return listConcepto;
    }
    
    @Override
    public DyccConceptoDTO obtieneIdConcepto(int idConcepto) {
        return dyccConceptoDAO.obtieneIdConcepto(idConcepto);
    }

    @Override
    public List<DyccConceptoDTO> obtenerConceptosPorTipoTramite(int tipoTramite) {
        return dyccConceptoDAO.obtenerConceptosPorTipoTramite(tipoTramite);
    }
    
    /**
     * OBTENER CONCEPTO POR TIPO ROL PARA AVISO DE COMPENSACION
     * @author Luis Alberto Dominguez Palomino [LADP]
     * @param tipoRol
     * @return
     */
    @Override
    public List<DyccConceptoDTO> obtenerConceptosPorTipoRol(int tipoRol) {
        List<DyccConceptoDTO> listConcepto = new ArrayList<DyccConceptoDTO>();
        try {
            listConcepto = dyccConceptoDAO.obtenerConceptosPorTipoRol(tipoRol);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return listConcepto;
    }
    
    /**
     * OBTENER CONCEPTO POR IDCONCEPTDESTNO, ORIGENSALDO Y PROVISIONAL PARA ORIGEN DE AVISO DE COMPENSACION
     * @author Luis Alberto Dominguez Palomino [LADP]
     * @param idConceptoDestino
     * @param origenSaldo
     * @param provisional
     * @return
     */
    @Override
    public List<DyccConceptoDTO> obtenerConceptosOrigen(int tipoRol, int idConceptoDestino, int origenSaldo, int provisional) {
        List<DyccConceptoDTO> listaConceptoOrigen = new ArrayList<DyccConceptoDTO>();
        try{
            listaConceptoOrigen = dyccConceptoDAO.obtenerConceptosOrigen(tipoRol, idConceptoDestino, origenSaldo, provisional);
        }catch(Exception e){
            log.error(e.getMessage());
        }
        return listaConceptoOrigen;
    }

    public DyccConceptoDTO obtieneConceptoPorIdConcepto(int tipotramite) {
         return dyccConceptoDAO.obtieneIdConcepto(tipotramite);
    }
}
