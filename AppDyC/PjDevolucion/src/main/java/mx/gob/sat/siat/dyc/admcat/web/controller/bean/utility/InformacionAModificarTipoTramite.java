package mx.gob.sat.siat.dyc.admcat.web.controller.bean.utility;

import mx.gob.sat.siat.dyc.admcat.dto.tipotramite.GuardadoTipoTramiteVO;


public class InformacionAModificarTipoTramite {
    public InformacionAModificarTipoTramite() {
        super();
        datosNuevos = new GuardadoTipoTramiteVO();
        datosOriginales = new GuardadoTipoTramiteVO();
    }
    
    private GuardadoTipoTramiteVO datosNuevos;
    private GuardadoTipoTramiteVO datosOriginales;

    public void setDatosNuevos(GuardadoTipoTramiteVO datosNuevos) {
        this.datosNuevos = datosNuevos;
    }

    public GuardadoTipoTramiteVO getDatosNuevos() {
        return datosNuevos;
    }

    public void setDatosOriginales(GuardadoTipoTramiteVO datosOriginales) {
        this.datosOriginales = datosOriginales;
    }

    public GuardadoTipoTramiteVO getDatosOriginales() {
        return datosOriginales;
    }
}
