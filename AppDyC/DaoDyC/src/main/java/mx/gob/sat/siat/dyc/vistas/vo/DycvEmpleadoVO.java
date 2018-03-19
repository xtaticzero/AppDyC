package mx.gob.sat.siat.dyc.vistas.vo;

import mx.gob.sat.siat.dyc.domain.vistas.DycvEmpleadoDTO;
import mx.gob.sat.siat.dyc.vo.AprobadorVO;
import mx.gob.sat.siat.dyc.vo.DictaminadorVO;

public class DycvEmpleadoVO extends DycvEmpleadoDTO {

    @SuppressWarnings("compatibility:-3910751795589868109")
    private static final long serialVersionUID = 1L;

    private Integer activoPortal;
    private Integer claveAdm;
    private Integer claveAdmOp;
    private String claveComision;
    private String descComision;

    private DictaminadorVO dictaminador;
    private AprobadorVO aprobador;

    public DycvEmpleadoVO() {
        super();
    }

    public void setActivoPortal(Integer activoPortal) {
        this.activoPortal = activoPortal;
    }

    public Integer getActivoPortal() {
        return activoPortal;
    }

    public void setClaveAdm(Integer claveAdm) {
        this.claveAdm = claveAdm;
    }

    public Integer getClaveAdm() {
        return claveAdm;
    }

    public void setClaveAdmOp(Integer claveAdmOp) {
        this.claveAdmOp = claveAdmOp;
    }

    public Integer getClaveAdmOp() {
        return claveAdmOp;
    }

    public void setClaveComision(String claveComision) {
        this.claveComision = claveComision;
    }

    public String getClaveComision() {
        return claveComision;
    }

    public void setDescComision(String descComision) {
        this.descComision = descComision;
    }

    public String getDescComision() {
        return descComision;
    }

    public void setDictaminador(DictaminadorVO dictaminador) {
        this.dictaminador = dictaminador;
    }

    public DictaminadorVO getDictaminador() {
        return dictaminador;
    }

    public void setAprobador(AprobadorVO aprobador) {
        this.aprobador = aprobador;
    }

    public AprobadorVO getAprobador() {
        return aprobador;
    }
}

