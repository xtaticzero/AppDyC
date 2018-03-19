package mx.gob.sat.siat.dyc.gestionsol.vo.emitirresolucion;

import java.io.Serializable;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import mx.gob.sat.siat.dyc.generico.util.exportador.informe.Marshal;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder =
         { "claveConceptoLey", "descripcionConceptoLey", "importeActualizacion", "importeDeterminado", "importeGastosTotales", "importeRecargos", "importeTotal",
           "numeroDeCredito", "numeroDocumentoDeterminante" })
@XmlRootElement(name = "DocumentosDyC")
public class FirmezaVO extends Marshal implements Serializable {
    
    @SuppressWarnings("compatibility:-8677883607074169834")
    private static final long serialVersionUID = 1910354590513783063L;
    
    @XmlElement
    private String claveConceptoLey;
    
    @XmlElement
    private String descripcionConceptoLey;
    
    @XmlElement
    private BigDecimal importeActualizacion;
    
    @XmlElement
    private BigDecimal importeDeterminado;
    
    @XmlElement
    private BigDecimal importeGastosTotales;
    
    @XmlElement
    private BigDecimal importeRecargos;
    
    @XmlElement
    private BigDecimal importeTotal;
    
    @XmlElement
    private String numeroDeCredito;
    
    @XmlElement
    private String numeroDocumentoDeterminante;
    
    public FirmezaVO() {
        super();
    }

    public void setClaveConceptoLey(String claveConceptoLey) {
        this.claveConceptoLey = claveConceptoLey;
    }

    public String getClaveConceptoLey() {
        return claveConceptoLey;
    }

    public void setDescripcionConceptoLey(String descripcionConceptoLey) {
        this.descripcionConceptoLey = descripcionConceptoLey;
    }

    public String getDescripcionConceptoLey() {
        return descripcionConceptoLey;
    }

    public void setImporteActualizacion(BigDecimal importeActualizacion) {
        this.importeActualizacion = importeActualizacion;
    }

    public BigDecimal getImporteActualizacion() {
        return importeActualizacion;
    }

    public void setImporteDeterminado(BigDecimal importeDeterminado) {
        this.importeDeterminado = importeDeterminado;
    }

    public BigDecimal getImporteDeterminado() {
        return importeDeterminado;
    }

    public void setImporteGastosTotales(BigDecimal importeGastosTotales) {
        this.importeGastosTotales = importeGastosTotales;
    }

    public BigDecimal getImporteGastosTotales() {
        return importeGastosTotales;
    }

    public void setImporteRecargos(BigDecimal importeRecargos) {
        this.importeRecargos = importeRecargos;
    }

    public BigDecimal getImporteRecargos() {
        return importeRecargos;
    }

    public void setImporteTotal(BigDecimal importeTotal) {
        this.importeTotal = importeTotal;
    }

    public BigDecimal getImporteTotal() {
        return importeTotal;
    }

    public void setNumeroDeCredito(String numeroDeCredito) {
        this.numeroDeCredito = numeroDeCredito;
    }

    public String getNumeroDeCredito() {
        return numeroDeCredito;
    }

    public void setNumeroDocumentoDeterminante(String numeroDocumentoDeterminante) {
        this.numeroDocumentoDeterminante = numeroDocumentoDeterminante;
    }

    public String getNumeroDocumentoDeterminante() {
        return numeroDocumentoDeterminante;
    }
}
