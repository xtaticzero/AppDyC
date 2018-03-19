/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain;

import java.io.Serializable;

import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaServOrigenDTO;


/**
 * DTO de la tabla DYCC_MATRIZCOMP
 * @author  Alfredo Ramirez
 * @since   09/06/2014
 */
public class DyccMatrizCompDTO implements Serializable {

    @SuppressWarnings("compatibility:-4491753820859237909")
    private static final long serialVersionUID = 1L;

    private Integer provisional;
    private DyccConceptoDTO dyccConceptoDTO1;
    private DyccConceptoDTO dyccConceptoDTO2;
    private DycaServOrigenDTO dycaServOrigenDTO;

    public DyccMatrizCompDTO() {
    }

    public DyccMatrizCompDTO(Integer provisional) {
        this.provisional = provisional;
    }

    public void setProvisional(Integer provisional) {
        this.provisional = provisional;
    }

    public Integer getProvisional() {
        return provisional;
    }

    public void setDyccConceptoDTO1(DyccConceptoDTO dyccConceptoDTO1) {
        this.dyccConceptoDTO1 = dyccConceptoDTO1;
    }

    public DyccConceptoDTO getDyccConceptoDTO1() {
        return dyccConceptoDTO1;
    }

    public void setDyccConceptoDTO2(DyccConceptoDTO dyccConceptoDTO2) {
        this.dyccConceptoDTO2 = dyccConceptoDTO2;
    }

    public DyccConceptoDTO getDyccConceptoDTO2() {
        return dyccConceptoDTO2;
    }

    public void setDycaServOrigenDTO(DycaServOrigenDTO dycaServOrigenDTO) {
        this.dycaServOrigenDTO = dycaServOrigenDTO;
    }

    public DycaServOrigenDTO getDycaServOrigenDTO() {
        return dycaServOrigenDTO;
    }
}
