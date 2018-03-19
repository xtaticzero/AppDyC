package mx.gob.sat.siat.dyc.admcat.web.controller.bean.support;

import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;

public class DyccTipoTramiteVO {
    
    public DyccTipoTramiteVO(DyccTipoTramiteDTO dyccTipoTramiteDTO, String status) {
        this.dyccTipoTramiteDTO = dyccTipoTramiteDTO;
        this.status = status;
    }
    
    private DyccTipoTramiteDTO dyccTipoTramiteDTO;
    private String status;

    public void setDyccTipoTramiteDTO(DyccTipoTramiteDTO dyccTipoTramiteDTO) {
        this.dyccTipoTramiteDTO = dyccTipoTramiteDTO;
    }

    public DyccTipoTramiteDTO getDyccTipoTramiteDTO() {
        return dyccTipoTramiteDTO;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
