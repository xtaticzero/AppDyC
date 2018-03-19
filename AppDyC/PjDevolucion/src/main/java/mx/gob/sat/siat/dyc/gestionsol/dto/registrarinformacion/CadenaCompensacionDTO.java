package mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion;

public class CadenaCompensacionDTO {
    public CadenaCompensacionDTO() {
        super();
    }
    private String descripcionTramiteICEPDestino;
    private String descripcionConceptoICEPDestino;
    private String descripcionPeriodoICEPDestino;
    private String ejercicioICEPDestino;
    private String fechaSolventacion;
    private String tipoDeRequerimiento;
    private String numeroControl;
    private String rfc;
    private String nombreORazonSocial;

    public void setDescripcionTramiteICEPDestino(String descripcionTramiteICEPDestino) {
        this.descripcionTramiteICEPDestino = descripcionTramiteICEPDestino;
    }

    public String getDescripcionTramiteICEPDestino() {
        return descripcionTramiteICEPDestino;
    }

    public void setDescripcionConceptoICEPDestino(String descripcionConceptoICEPDestino) {
        this.descripcionConceptoICEPDestino = descripcionConceptoICEPDestino;
    }

    public String getDescripcionConceptoICEPDestino() {
        return descripcionConceptoICEPDestino;
    }

    public void setDescripcionPeriodoICEPDestino(String descripcionPeriodoICEPDestino) {
        this.descripcionPeriodoICEPDestino = descripcionPeriodoICEPDestino;
    }

    public String getDescripcionPeriodoICEPDestino() {
        return descripcionPeriodoICEPDestino;
    }

    public void setEjercicioICEPDestino(String ejercicioICEPDestino) {
        this.ejercicioICEPDestino = ejercicioICEPDestino;
    }

    public String getEjercicioICEPDestino() {
        return ejercicioICEPDestino;
    }

    public void setFechaSolventacion(String fechaSolventacion) {
        this.fechaSolventacion = fechaSolventacion;
    }

    public String getFechaSolventacion() {
        return fechaSolventacion;
    }

    public void setTipoDeRequerimiento(String tipoDeRequerimiento) {
        this.tipoDeRequerimiento = tipoDeRequerimiento;
    }

    public String getTipoDeRequerimiento() {
        return tipoDeRequerimiento;
    }

    public void setNumeroControl(String numeroControl) {
        this.numeroControl = numeroControl;
    }

    public String getNumeroControl() {
        return numeroControl;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRfc() {
        return rfc;
    }

    public void setNombreORazonSocial(String nombreORazonSocial) {
        this.nombreORazonSocial = nombreORazonSocial;
    }

    public String getNombreORazonSocial() {
        return nombreORazonSocial;
    }
}
