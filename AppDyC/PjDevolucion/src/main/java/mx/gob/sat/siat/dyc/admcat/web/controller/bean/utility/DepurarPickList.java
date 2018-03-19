package mx.gob.sat.siat.dyc.admcat.web.controller.bean.utility;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.anexo.DyccMatrizAnexosDTO;
import mx.gob.sat.siat.dyc.domain.documento.DyccSubTramiteDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccTipoPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.suborigensal.DyccSubOrigSaldoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccInfoARequerirDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccRolDTO;
import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidAdmvaTipoDTO;


public class DepurarPickList {
    public DepurarPickList() {
        super();
    }
    
    /**
     * Depura TipoUnidadesAdmvas, dejando de lado izquierdo los elementos que no se 
     * guardaron en base de datos.
     *
     * @param datosCatalogo
     * @param datosBase
     * @return
     */
    public List <AdmcUnidAdmvaTipoDTO> depurarTipoUnidadesAdmvas(List <AdmcUnidAdmvaTipoDTO> datosCatalogo,  List <AdmcUnidAdmvaTipoDTO> datosBase) {      
        for (AdmcUnidAdmvaTipoDTO base : datosBase) {
            for(AdmcUnidAdmvaTipoDTO datoDeCatalogo: datosCatalogo) {
                if(datoDeCatalogo.getIdUnidadAdmvaTipo().equals(base.getIdUnidadAdmvaTipo())) {
                    datosCatalogo.remove(datoDeCatalogo);
                    break;
                }
            }
        }
        return datosCatalogo;
    }
    
    /**
     * Depura InfoARequerir, dejando de lado izquierdo los elementos que no se 
     * guardaron en base de datos.
     *
     * @param datosCatalogo
     * @param datosBase
     * @return
     */
    public List <DyccInfoARequerirDTO> depurarInfoARequerir(List <DyccInfoARequerirDTO> datosCatalogo,  List <DyccInfoARequerirDTO> datosBase) {      
        for (DyccInfoARequerirDTO base : datosBase) {
            for(DyccInfoARequerirDTO datoDeCatalogo: datosCatalogo) {
                if(datoDeCatalogo.getIdInfoArequerir().equals(base.getIdInfoArequerir())) {
                    datosCatalogo.remove(datoDeCatalogo);
                    break;
                }
            }
        }
        return datosCatalogo;
    }
    
    /**
     * Depura MatrizAnexos, dejando de lado izquierdo los elementos que no se 
     * guardaron en base de datos.
     *
     * @param datosCatalogo
     * @param datosBase
     * @return
     */
    public List <DyccMatrizAnexosDTO> depurarMatrizAnexos(List <DyccMatrizAnexosDTO> datosCatalogo,  List <DyccMatrizAnexosDTO> datosBase) {      
        for (DyccMatrizAnexosDTO base : datosBase) {
            for(DyccMatrizAnexosDTO datoDeCatalogo: datosCatalogo) {
                if(datoDeCatalogo.getIdAnexo().equals(base.getIdAnexo())) {
                    datosCatalogo.remove(datoDeCatalogo);
                    break;
                }
            }
        }
        return datosCatalogo;
    }
    
    /**
     * Depura Roles, dejando de lado izquierdo los elementos que no se 
     * guardaron en base de datos.
     *
     * @param datosCatalogo
     * @param datosBase
     * @return
     */
    public List <DyccRolDTO> depurarRoles(List <DyccRolDTO> datosCatalogo,  List <DyccRolDTO> datosBase) {      
        for (DyccRolDTO base : datosBase) {
            for(DyccRolDTO datoDeCatalogo: datosCatalogo) {
                if(datoDeCatalogo.getIdRol().equals(base.getIdRol())) {
                    datosCatalogo.remove(datoDeCatalogo);
                    break;
                }
            }
        }
        return datosCatalogo;
    }
    
    /**
     * Depura SubOrigenesSaldo, dejando de lado izquierdo los elementos que no se 
     * guardaron en base de datos.
     *
     * @param datosCatalogo
     * @param datosBase
     * @return
     */
    public List <DyccSubOrigSaldoDTO> depurarSubOrigenesSaldo(List <DyccSubOrigSaldoDTO> datosCatalogo,  List <DyccSubOrigSaldoDTO> datosBase) {      
        for (DyccSubOrigSaldoDTO base : datosBase) {
            for(DyccSubOrigSaldoDTO datoDeCatalogo: datosCatalogo) {
                if(datoDeCatalogo.getIdSuborigenSaldo().equals(base.getIdSuborigenSaldo())) {
                    datosCatalogo.remove(datoDeCatalogo);
                    break;
                }
            }
        }
        return datosCatalogo;
    }
    
    /**
     * Depura Subtramites, dejando de lado izquierdo los elementos que no se 
     * guardaron en base de datos.
     *
     * @param datosCatalogo
     * @param datosBase
     * @return
     */
    public List <DyccSubTramiteDTO> depurarSubtramites(List <DyccSubTramiteDTO> datosCatalogo,  List <DyccSubTramiteDTO> datosBase) {      
        for (DyccSubTramiteDTO base : datosBase) {
            for(DyccSubTramiteDTO datoDeCatalogo: datosCatalogo) {
                if(datoDeCatalogo.getIdSubTipoTramite().equals(base.getIdSubTipoTramite())) {
                    datosCatalogo.remove(datoDeCatalogo);
                    break;
                }
            }
        }
        return datosCatalogo;
    }
    
    /**
     * Depura TipoPeriodo, dejando de lado izquierdo los elementos que no se 
     * guardaron en base de datos.
     *
     * @param datosCatalogo
     * @param datosBase
     * @return
     */
    public List <DyccTipoPeriodoDTO> depurarTipoPeriodo(List <DyccTipoPeriodoDTO> datosCatalogo,  List <DyccTipoPeriodoDTO> datosBase) {      
        for (DyccTipoPeriodoDTO base : datosBase) {
            for(DyccTipoPeriodoDTO datoDeCatalogo: datosCatalogo) {
                if(datoDeCatalogo.getIdTipoPeriodo().equals(base.getIdTipoPeriodo())) {
                    datosCatalogo.remove(datoDeCatalogo);
                    break;
                }
            }
        }
        return datosCatalogo;
    }
}
