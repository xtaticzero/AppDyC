package cte.dyc.dto;

import java.io.Serializable;

public class EmpleadoDTO implements Serializable {

    @SuppressWarnings("compatibility:8131281094983798932")
    private static final long serialVersionUID = 1L;
    private Integer numEmpleado;
    private String rfc;
    private String roles;

    public EmpleadoDTO() {
        super();
    }

    public void setNumEmpleado(Integer numEmpleado) {
        this.numEmpleado = numEmpleado;
    }

    public Integer getNumEmpleado() {
        return numEmpleado;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getRoles() {
        return roles;
    }
}
