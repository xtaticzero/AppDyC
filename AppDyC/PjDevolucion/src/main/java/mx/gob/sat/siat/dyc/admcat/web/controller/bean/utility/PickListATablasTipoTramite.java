package mx.gob.sat.siat.dyc.admcat.web.controller.bean.utility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mx.gob.sat.siat.dyc.domain.anexo.DyccAnexoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.anexo.DyccMatrizAnexosDTO;
import mx.gob.sat.siat.dyc.domain.periodo.DycaTipoPeriodoTtDTO;
import mx.gob.sat.siat.dyc.domain.documento.DyccSubTramiteDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccTipoPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccTtSubtramiteDTO;
import mx.gob.sat.siat.dyc.domain.suborigensal.DyccSubOrigSaldoDTO;
import mx.gob.sat.siat.dyc.domain.suborigensal.DyccSubSaldoTramDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaOrigenTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaServOrigenDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccInfoARequerirDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccInfoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccRolDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoServicioDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTramiteRolDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccUnidadTramiteDTO;
import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidAdmvaTipoDTO;


/**
 * @author Jesus Alfredo Hernandez Orozco
 */
public class PickListATablasTipoTramite {
    
    /**
     * Obtiene todos los datos seleccionados en pantalla para hacer el registro de la base de datos de las undades 
     * del tramite.
     *
     * @return
     */
    public List<DyccUnidadTramiteDTO> obtenerUnidadesTramite(List<AdmcUnidAdmvaTipoDTO> listaUnidadAdmtva, Integer idTramite) {
        
        Iterator it = null;
        AdmcUnidAdmvaTipoDTO unidadAdministrativa = null;
        DyccUnidadTramiteDTO unidadTramite = null;
        DyccTipoTramiteDTO objetoTramite = new DyccTipoTramiteDTO();
        List<DyccUnidadTramiteDTO> lista = new ArrayList<DyccUnidadTramiteDTO>();
        it = listaUnidadAdmtva.iterator();
        
        while (it.hasNext()) {
            unidadTramite = new DyccUnidadTramiteDTO();
            unidadAdministrativa = (AdmcUnidAdmvaTipoDTO)it.next();
            unidadTramite.setIdTipoUnidadAdmva(unidadAdministrativa.getIdUnidadAdmvaTipo());
            objetoTramite.setIdTipoTramite(idTramite);
            unidadTramite.setDyccTipoTramiteDTO(objetoTramite);
            lista.add(unidadTramite);
        }
        
        it = null;
        objetoTramite = null;
        unidadTramite = null;
        return lista;
    }
    
    /**
     * Obtiene la informacion para guardar en DyCC_TramiteROL
     *
     * @return
     */
    public List<DyccTramiteRolDTO> obtenerTramiteRol(List<DyccRolDTO> listaRoles, Integer idTramite) {
        Iterator it = null;
        DyccRolDTO objetoRol = null;
        DyccTramiteRolDTO  objetoTramiteRol = null;
        DyccTipoTramiteDTO objetoTramite    = new DyccTipoTramiteDTO();
        List<DyccTramiteRolDTO> tramiteRol  = new ArrayList<DyccTramiteRolDTO>();
        it = listaRoles.iterator();
        
        while (it.hasNext()) {
            objetoTramiteRol = new DyccTramiteRolDTO();
            objetoRol = (DyccRolDTO)it.next();
            objetoTramiteRol.setDyccRolDTO(objetoRol);   
            objetoTramite.setIdTipoTramite(idTramite);
            objetoTramiteRol.setDyccTipoTramiteDTO(objetoTramite);
            tramiteRol.add(objetoTramiteRol);
        }
        
        it = null;
        objetoRol = null;
        return tramiteRol;
    }
    
    /**
     * Obtiene los datos necesarios para insertar en la tabla de DYCC_ANEXOTRAMITE
     *
     * @return
     */
    public List<DyccAnexoTramiteDTO> obtenerAnexoTramite(List<DyccMatrizAnexosDTO> listaMatrizAnexos, Integer idTramite) {
        Iterator it = null;
        DyccMatrizAnexosDTO anexo = null;
        DyccAnexoTramiteDTO anexoTramite = null;
        DyccTipoTramiteDTO  tipoTramite  = new DyccTipoTramiteDTO();
        DyccMatrizAnexosDTO matrizAnexo  = null;
        List<DyccAnexoTramiteDTO> lista  = new ArrayList<DyccAnexoTramiteDTO>();
        it = listaMatrizAnexos.iterator();
        
        while (it.hasNext()) {
            anexo = (DyccMatrizAnexosDTO)it.next();
            matrizAnexo  = new DyccMatrizAnexosDTO();
            anexoTramite = new DyccAnexoTramiteDTO();
            tipoTramite.setIdTipoTramite(idTramite);
            anexoTramite.setDyccTipoTramiteDTO(tipoTramite);
            matrizAnexo.setIdAnexo(anexo.getIdAnexo());
            anexoTramite.setDyccMatrizAnexosDTO(matrizAnexo);
            lista.add(anexoTramite);
        }
        
        it = null;
        anexoTramite = null;
        tipoTramite  = null;
        matrizAnexo  = null;
        return lista;
    }
    
    /**
     * Relaciona la informacion seleccionada en la pantalla para insertar en la tabla Dycc_InfoTramite
     *
     * @return
     */
    public List<DyccInfoTramiteDTO> obtenerInforTramite(List<DyccInfoARequerirDTO> listaInfoARequerir, Integer idTramite) {
        Iterator it = null;
        DyccInfoARequerirDTO objetoInfoARequerir = null;
        DyccInfoTramiteDTO   objetoInfoTramite   = null;
        DyccTipoTramiteDTO tipoTramite = new DyccTipoTramiteDTO();
        List<DyccInfoTramiteDTO> lista = new ArrayList<DyccInfoTramiteDTO>();
        it = listaInfoARequerir.iterator();
        
        while(it.hasNext()) {
            objetoInfoTramite = new DyccInfoTramiteDTO();
            objetoInfoARequerir = (DyccInfoARequerirDTO)it.next();
            objetoInfoTramite.setDyccInfoARequerirDTO(objetoInfoARequerir);
            tipoTramite.setIdTipoTramite(idTramite);
            objetoInfoTramite.setDyccTipoTramiteDTO(tipoTramite);
            lista.add(objetoInfoTramite);
        }
        
        it = null;
        objetoInfoTramite = null;
        tipoTramite  = null;
        return lista;
    }
    
    /**
     * Obtiene los datos para insertar los datos en la tabla Dycc_TtSubtramite
     *
     * @return
     */
    public List<DyccTtSubtramiteDTO> obtenerSubtramite(List<DyccSubTramiteDTO> listaSubtramite, Integer idTramite) {
        Iterator it = null;
        DyccTtSubtramiteDTO objetoSubtramite = null;
        DyccSubTramiteDTO subtramite    = null;
        DyccTipoTramiteDTO tipoTramite  = new DyccTipoTramiteDTO();
        List<DyccTtSubtramiteDTO> lista = new ArrayList<DyccTtSubtramiteDTO>();
        it = listaSubtramite.iterator();
        
        while (it.hasNext()) {
            objetoSubtramite = new DyccTtSubtramiteDTO();
            subtramite = (DyccSubTramiteDTO)it.next();
            objetoSubtramite.setDyccSubTramiteDTO(subtramite);
            tipoTramite.setIdTipoTramite(idTramite);
            objetoSubtramite.setDyccTipoTramiteDTO(tipoTramite);
            lista.add(objetoSubtramite);
        }
        
        it = null;
        objetoSubtramite = null;
        subtramite  = null;
        tipoTramite = null;
        
        return lista;
    }
    
    /**
     * Obteiene los datos para insertar en la tabla de Dycc_SubSaldoTram
     *
     * @return
     */
    public List<DyccSubSaldoTramDTO> obtenerSUbSaldoTramite(List<DyccSubOrigSaldoDTO> listaSubOrigenesSaldo, Integer idTramite) {
        Iterator it = null;
        DyccSubOrigSaldoDTO objeto = null;
        DyccSubSaldoTramDTO subSaldoTram = null;
        DyccTipoTramiteDTO  tipoTramite  = new DyccTipoTramiteDTO();
        List<DyccSubSaldoTramDTO> lista  = new ArrayList<DyccSubSaldoTramDTO>();
        it = listaSubOrigenesSaldo.iterator();
        
        while (it.hasNext()) {
            subSaldoTram = new DyccSubSaldoTramDTO();
            objeto = (DyccSubOrigSaldoDTO)it.next();
            subSaldoTram.setDyccSuborigSaldoDTO(objeto);
            tipoTramite.setIdTipoTramite(idTramite);
            subSaldoTram.setDyccTipoTramiteDTO(tipoTramite);
            lista.add(subSaldoTram);
        }
        
        it = null;
        objeto       = null;
        subSaldoTram = null;
        tipoTramite  = null;
        return lista;
    }
    
    /**
     * Obtiene los datos para insertar en DYCA_OrigenTramite
     *
     * @return
     */
    public List<DycaTipoPeriodoTtDTO> obtenerTipoPeriodoTt(List<DyccTipoPeriodoDTO> listaTipoPeriodo, Integer idTramite) {
        Iterator it = null;
        DyccTipoPeriodoDTO objeto = null;
        DycaTipoPeriodoTtDTO tipoPeriodo = null;
        DyccTipoTramiteDTO   tipoTramite = new DyccTipoTramiteDTO();
        List<DycaTipoPeriodoTtDTO> lista = new ArrayList<DycaTipoPeriodoTtDTO>();
        it = listaTipoPeriodo.iterator();
        
        while (it.hasNext()) {
            tipoPeriodo = new DycaTipoPeriodoTtDTO();
            objeto = (DyccTipoPeriodoDTO)it.next();
            tipoPeriodo.setDyccTipoPeriodoDTO(objeto);
            tipoTramite.setIdTipoTramite(idTramite);
            tipoPeriodo.setDyccTipoTramiteDTO(tipoTramite);
            lista.add(tipoPeriodo);
        }
        
        it = null;
        objeto      = null;
        tipoPeriodo = null;
        return lista;
    }
    
    /**
     * Obtiene los datos del origen del tramite para insertar en la tabla de DYCA_OrigenTramite
     *
     * @return
     */
    public DycaOrigenTramiteDTO obtenerOrigenTramite(Integer tipoServicio, Integer idTramite, Integer origenSaldo) {
        DycaOrigenTramiteDTO objeto = new DycaOrigenTramiteDTO();
        
        DyccTipoTramiteDTO tipoTramite = new DyccTipoTramiteDTO();
        tipoTramite.setIdTipoTramite(idTramite);
        
        DycaServOrigenDTO objetoServicio = new DycaServOrigenDTO();
        DyccTipoServicioDTO objetoTipoServicio = new DyccTipoServicioDTO();
        DyccOrigenSaldoDTO objetoOrigenSaldo = new DyccOrigenSaldoDTO();
        objetoTipoServicio.setIdTipoServicio(tipoServicio);
        objetoServicio.setDyccTipoServicioDTO(objetoTipoServicio);        
        objetoOrigenSaldo.setIdOrigenSaldo(origenSaldo);
        objetoServicio.setDyccOrigenSaldoDTO(objetoOrigenSaldo);
        
        objeto.setDycaServOrigenDTO(objetoServicio);
        objeto.setDyccTipoTramiteDTO(tipoTramite);
        
        return objeto;
    }
}
