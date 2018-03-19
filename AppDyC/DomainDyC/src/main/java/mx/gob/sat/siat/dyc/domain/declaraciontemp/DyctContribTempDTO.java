/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.declaraciontemp;

import java.io.InputStream;
import java.io.Serializable;

import java.util.Date;


/**
 * DTO de la tabla DYCT_CONTRIBTEMP
 * @author  Alfredo Ramirez
 * @since   03/04/2014
 */
public class DyctContribTempDTO implements Serializable {


    @SuppressWarnings("compatibility:2344132791697660038")
    private static final long serialVersionUID = 1L;
    
    private transient InputStream datosContribuyente;
    private Date fechaConsulta;
    private Integer folioServTemp;
    private Boolean rolDictaminado;
    private Boolean rolGranContrib;
    private Boolean rolPf;
    private Boolean rolPm;
    private Boolean rolSociedadControl;
    private DyctServicioTempDTO dyctServicioTempDTO;
    private Integer amparado;

    public DyctContribTempDTO() {
    }
    
    public void setDatosContribuyente(InputStream datosContribuyente) {
        this.datosContribuyente = datosContribuyente;
    }

    public InputStream getDatosContribuyente() {
        return datosContribuyente;
    }

    public void setFechaConsulta(Date fechaConsulta) {
        if (null != fechaConsulta) {
            this.fechaConsulta = (Date)fechaConsulta.clone();
        } else {
            this.fechaConsulta = null;
        }
    }

    public Date getFechaConsulta() {
        if (null != fechaConsulta) {
            return (Date)fechaConsulta.clone();
        } else {
            return null;
        }
    }

    public void setFolioServTemp(Integer folioServTemp) {
        this.folioServTemp = folioServTemp;
    }

    public Integer getFolioServTemp() {
        return folioServTemp;
    }

    public void setRolDictaminado(Boolean rolDictaminado) {
        this.rolDictaminado = rolDictaminado;
    }

    public Boolean getRolDictaminado() {
        return rolDictaminado;
    }

    public void setRolGranContrib(Boolean rolGranContrib) {
        this.rolGranContrib = rolGranContrib;
    }

    public Boolean getRolGranContrib() {
        return rolGranContrib;
    }

    public void setRolPf(Boolean rolPf) {
        this.rolPf = rolPf;
    }

    public Boolean getRolPf() {
        return rolPf;
    }

    public void setRolPm(Boolean rolPm) {
        this.rolPm = rolPm;
    }

    public Boolean getRolPm() {
        return rolPm;
    }

    public void setRolSociedadControl(Boolean rolSociedadControl) {
        this.rolSociedadControl = rolSociedadControl;
    }

    public Boolean getRolSociedadControl() {
        return rolSociedadControl;
    }

    public void setDyctServicioTempDTO(DyctServicioTempDTO dyctServicioTempDTO) {
        this.dyctServicioTempDTO = dyctServicioTempDTO;
    }

    public DyctServicioTempDTO getDyctServicioTempDTO() {
        return dyctServicioTempDTO;
    }
    
    public void setAmparado(Integer amparado) {
        this.amparado = amparado;
    }

    public Integer getAmparado() {
        return amparado;
    }    
}
