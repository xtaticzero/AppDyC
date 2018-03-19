/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.tipotramite;

import java.io.Serializable;

import java.util.List;


/**
 * DTO para el tabla DYCA_SERVORIGEN
 * @author  Alfredo Ramirez
 * @since   31/03/2014
 */
public class DycaServOrigenDTO implements Serializable {

    @SuppressWarnings("compatibility:-6617921017639355521")
    private static final long serialVersionUID = 1L;

    private List<DycaOrigenTramiteDTO> dycaOrigenTramiteList;
    private DyccOrigenSaldoDTO dyccOrigenSaldoDTO;
    private DyccTipoServicioDTO dyccTipoServicioDTO;

    public DycaServOrigenDTO() {
    }

    public DycaServOrigenDTO(DyccOrigenSaldoDTO dyccOrigenSaldoDTO, DyccTipoServicioDTO dyccTipoServicioDTO) {
        this.dyccOrigenSaldoDTO = dyccOrigenSaldoDTO;
        this.dyccTipoServicioDTO = dyccTipoServicioDTO;
    }

    public void setDycaOrigenTramiteList(List<DycaOrigenTramiteDTO> dycaOrigenTramiteList) {
        this.dycaOrigenTramiteList = dycaOrigenTramiteList;
    }

    public List<DycaOrigenTramiteDTO> getDycaOrigenTramiteList() {
        return dycaOrigenTramiteList;
    }

    public void setDyccOrigenSaldoDTO(DyccOrigenSaldoDTO dyccOrigenSaldoDTO) {
        this.dyccOrigenSaldoDTO = dyccOrigenSaldoDTO;
    }

    public DyccOrigenSaldoDTO getDyccOrigenSaldoDTO() {
        return dyccOrigenSaldoDTO;
    }

    public void setDyccTipoServicioDTO(DyccTipoServicioDTO dyccTipoServicioDTO) {
        this.dyccTipoServicioDTO = dyccTipoServicioDTO;
    }

    public DyccTipoServicioDTO getDyccTipoServicioDTO() {
        return dyccTipoServicioDTO;
    }
}
