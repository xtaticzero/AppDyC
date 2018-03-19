package mx.gob.sat.siat.dyc.domain.devolucionaut.catalogos;

import java.io.Serializable;

/**
 * DTO de la tabla DYCC_MONTOSALFAV
 * @author  Jose Luis Aguilar
 * @since   06/10/2016
 */
public class DycMotivoRiesgoDTO implements Serializable {

    @SuppressWarnings("compatibility:279465436847886487")
    private static final long serialVersionUID = 1L;

    public DycMotivoRiesgoDTO() {
        super();
    }
    
    private Integer idMotivoRiesgo;
    private Integer codigo;
    private String regla;
    private String modelo;
    private String estado;
    
    private Integer idCompuestaTemp;
    
    /**
     * @return the idMotivoRiesgo
     */
    public Integer getIdMotivoRiesgo() {
        return idMotivoRiesgo;
    }
    /**
     * @param idMotivoRiesgo the idMotivoRiesgo to set
     */
    public void setIdMotivoRiesgo(Integer idMotivoRiesgo) {
        this.idMotivoRiesgo = idMotivoRiesgo;
    }
    /**
     * @return the codigo
     */
    public Integer getCodigo() {
        return codigo;
    }
    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }
    /**
     * @return the regla
     */
    public String getRegla() {
        return regla;
    }
    /**
     * @param regla the regla to set
     */
    public void setRegla(String regla) {
        this.regla = regla;
    }
    /**
     * @return the modelo
     */
    public String getModelo() {
        return modelo;
    }
    /**
     * @param modelo the modelo to set
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }
    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * @return the idCompuestaTemp
     */
    public Integer getIdCompuestaTemp() {
        return idCompuestaTemp;
    }

    /**
     * @param idCompuestaTemp the idCompuestaTemp to set
     */
    public void setIdCompuestaTemp(Integer idCompuestaTemp) {
        this.idCompuestaTemp = idCompuestaTemp;
    }
    
    
}
