package mx.gob.sat.siat.dyc.tesofe.dto;

import java.io.IOException;

import java.math.BigDecimal;

import org.primefaces.model.StreamedContent;


public class InformacionEnvioATESOFE {
    
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(InformacionEnvioATESOFE.class);
        
    private String compentencia;
    private String nombre;
    private String dat;
    private String csv;
    private String tipo;
    private BigDecimal totalAPagar;
    private StreamedContent fileCSV;
    private StreamedContent fileDAT;
    private Long numeroDev;

    public void setCompentencia(String compentencia) {
        this.compentencia = compentencia;
    }

    public String getCompentencia() {
        return compentencia;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setDat(String dat) {
        this.dat = dat;
    }

    public String getDat() {
        return dat;
    }

    public void setCsv(String csv) {
        this.csv = csv;
    }

    public String getCsv() {
        return csv;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTotalAPagar(BigDecimal totalAPagar) {
        this.totalAPagar = totalAPagar;
    }

    public BigDecimal getTotalAPagar() {
        return totalAPagar;
    }

    public void setFileCSV(StreamedContent fileCSV) {
        this.fileCSV = fileCSV;
    }

    public StreamedContent getFileCSV() {
        if(fileCSV!=null){
            try {
                fileCSV.getStream().reset();
            } catch (IOException ex) {
                LOGGER.error(ex);
            }
        }
        return fileCSV;
    }

    public void setFileDAT(StreamedContent fileDAT) {
        this.fileDAT = fileDAT;
    }

    public StreamedContent getFileDAT() {
        if(fileDAT!=null){
            try {
                fileDAT.getStream().reset();
            } catch (IOException ex) {
                LOGGER.error(ex);
            }
        }
        return fileDAT;
    }

    public void setNumeroDev(Long numeroDev) {
        this.numeroDev = numeroDev;
    }

    public Long getNumeroDev() {
        return numeroDev;
    }
    
}
