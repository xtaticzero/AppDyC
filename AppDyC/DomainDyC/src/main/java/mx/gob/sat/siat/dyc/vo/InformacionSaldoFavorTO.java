package mx.gob.sat.siat.dyc.vo;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;


/**
 * @author JEFG
 */
public class InformacionSaldoFavorTO implements Serializable {
    @SuppressWarnings("compatibility:3097804689624932327")
    private static final long serialVersionUID = 1L;

    private String tipoDeclaracion;
    private Date fechaPresentacion;
    private Date fechaCaucion;
    private String numeroOperacion;
    private String numeroDocumento;
    private String etiquetaSaldo;
    private BigDecimal importeSaldoFavor;
    private BigDecimal importeEfectuados;
    private BigDecimal importeAcreditramientoEfectuado;
    private BigDecimal importeSolicitadoDevolucion;
    private Date normalFechaPresentacion;
    private String normalNumOperacion;
    private BigDecimal normalSaldoFavor;
    private String datosCorrectos;
    private boolean saldoICEP;


    public InformacionSaldoFavorTO() {
        super();
    }

    public void setTipoDeclaracion(String tipoDeclaracion) {
        this.tipoDeclaracion = tipoDeclaracion;
    }

    public String getTipoDeclaracion() {
        return tipoDeclaracion;
    }

    public void setFechaPresentacion(Date fechaPresentacion) {
        if (null != fechaPresentacion) {
            this.fechaPresentacion = (Date)fechaPresentacion.clone();
        } else {
            this.fechaPresentacion = null;
        }
    }

    public Date getFechaPresentacion() {
        if (null != fechaPresentacion) {
            return (Date)fechaPresentacion.clone();
        } else {
            return null;
        }

    }

    public void setFechaCaucion(Date fechaCaucion) {
        if (null != fechaCaucion) {
            this.fechaCaucion = (Date)fechaCaucion.clone();
        } else {
            this.fechaCaucion = null;
        }
    }

    public Date getFechaCaucion() {
        if (null != fechaCaucion) {
            return (Date)fechaCaucion.clone();
        } else {
            return null;
        }
    }


    public void setNormalFechaPresentacion(Date normalFechaPresentacion) {
        if (null != normalFechaPresentacion) {
            this.normalFechaPresentacion = (Date)normalFechaPresentacion.clone();
        } else {
            this.normalFechaPresentacion = null;
        }
    }

    public Date getNormalFechaPresentacion() {
        if (null != normalFechaPresentacion) {
            return (Date)normalFechaPresentacion.clone();
        } else {
            return null;
        }
    }


    public void setDatosCorrectos(String datosCorrectos) {
        this.datosCorrectos = datosCorrectos;
    }

    public String getDatosCorrectos() {
        return datosCorrectos;
    }

    public void setNumeroOperacion(String numeroOperacion) {
        this.numeroOperacion = numeroOperacion;
    }

    public String getNumeroOperacion() {
        return numeroOperacion;
    }

    public void setNormalNumOperacion(String normalNumOperacion) {
        this.normalNumOperacion = normalNumOperacion;
    }

    public String getNormalNumOperacion() {
        return normalNumOperacion;
    }

    public void setSaldoICEP(boolean saldoICEP) {
        this.saldoICEP = saldoICEP;
    }

    public boolean isSaldoICEP() {
        return saldoICEP;
    }

    public void setEtiquetaSaldo(String etiquetaSaldo) {
        this.etiquetaSaldo = etiquetaSaldo;
    }

    public String getEtiquetaSaldo() {
        return etiquetaSaldo;
    }

    public void setFechaPresentacion1(Date fechaPresentacion) {
        this.fechaPresentacion = (fechaPresentacion != null) ? (Date) fechaPresentacion.clone() : null;
    }

    public Date getFechaPresentacion1() {
        return (fechaPresentacion != null) ? (Date) fechaPresentacion.clone() : null;
    }

    public void setFechaCaucion1(Date fechaCaucion) {
        this.fechaCaucion = (fechaCaucion != null) ? (Date) fechaCaucion.clone() : null;
    }

    public Date getFechaCaucion1() {
        return (fechaCaucion != null) ? (Date) fechaCaucion.clone() : null;
    }

    public void setImporteSaldoFavor(BigDecimal importeSaldoFavor) {
        this.importeSaldoFavor = importeSaldoFavor;
    }

    public BigDecimal getImporteSaldoFavor() {
        return importeSaldoFavor;
    }

    public void setImporteEfectuados(BigDecimal importeEfectuados) {
        this.importeEfectuados = importeEfectuados;
    }

    public BigDecimal getImporteEfectuados() {
        return importeEfectuados;
    }

    public void setImporteAcreditramientoEfectuado(BigDecimal importeAcreditramientoEfectuado) {
        this.importeAcreditramientoEfectuado = importeAcreditramientoEfectuado;
    }

    public BigDecimal getImporteAcreditramientoEfectuado() {
        return importeAcreditramientoEfectuado;
    }

    public void setImporteSolicitadoDevolucion(BigDecimal importeSolicitadoDevolucion) {
        this.importeSolicitadoDevolucion = importeSolicitadoDevolucion;
    }

    public BigDecimal getImporteSolicitadoDevolucion() {
        return importeSolicitadoDevolucion;
    }

    public void setNormalFechaPresentacion1(Date normalFechaPresentacion) {
        this.normalFechaPresentacion = (normalFechaPresentacion != null) ? (Date) normalFechaPresentacion.clone() : null;
    }

    public Date getNormalFechaPresentacion1() {
        return (normalFechaPresentacion != null) ? (Date) normalFechaPresentacion.clone() : null;
    }

    public void setNormalSaldoFavor(BigDecimal normalSaldoFavor) {
        this.normalSaldoFavor = normalSaldoFavor;
    }

    public BigDecimal getNormalSaldoFavor() {
        return normalSaldoFavor;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }
}
