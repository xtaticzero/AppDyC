/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.resolucion;

import java.io.InputStream;
import java.io.Serializable;

import java.util.Date;


/**
 * DTO de la tabla DYCT_CONTRIBUYENTE
 * @author  Alfredo Ramirez
 * @since   01/04/2014
 */
public class DyctContribuyenteDTO implements Serializable {


    @SuppressWarnings("compatibility:-8988201110627769874")
    private static final long serialVersionUID = 1L;

    private transient InputStream datosContribuyente;
    private String xmlContribuyente;
    private Date fechaConsulta;
    private String numControl;
    private Integer rolDictaminado;
    private Integer rolGranContrib;
    private Integer rolPf;
    private Integer rolPm;
    private Integer rolSociedadControl;
    private DycpServicioDTO dycpServicioDTO;

    public DyctContribuyenteDTO() {
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

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setRolDictaminado(Integer rolDictaminado) {
        this.rolDictaminado = rolDictaminado;
    }

    public Integer getRolDictaminado() {
        return rolDictaminado;
    }

    public void setRolGranContrib(Integer rolGranContrib) {
        this.rolGranContrib = rolGranContrib;
    }

    public Integer getRolGranContrib() {
        return rolGranContrib;
    }

    public void setRolPf(Integer rolPf) {
        this.rolPf = rolPf;
    }

    public Integer getRolPf() {
        return rolPf;
    }

    public void setRolPm(Integer rolPm) {
        this.rolPm = rolPm;
    }

    public Integer getRolPm() {
        return rolPm;
    }

    public void setRolSociedadControl(Integer rolSociedadControl) {
        this.rolSociedadControl = rolSociedadControl;
    }

    public Integer getRolSociedadControl() {
        return rolSociedadControl;
    }

    public void setDycpServicioDTO(DycpServicioDTO dycpServicioDTO) {
        this.dycpServicioDTO = dycpServicioDTO;
    }

    public DycpServicioDTO getDycpServicioDTO() {
        return dycpServicioDTO;
    }

    public String gteParameterReport() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getClass().getName());
        buffer.append("@");
        buffer.append(Integer.toHexString(hashCode()));
        buffer.append('[');
        buffer.append("datosContribuyente=");
        buffer.append(getDatosContribuyente());
        buffer.append(',');
        buffer.append("fechaConsulta=");
        buffer.append(getFechaConsulta());
        buffer.append(',');
        buffer.append("numControl=");
        buffer.append(getNumControl());
        buffer.append(',');
        buffer.append("rolDictaminado=");
        buffer.append(getRolDictaminado());
        buffer.append(',');
        buffer.append("rolGranContrib=");
        buffer.append(getRolGranContrib());
        buffer.append(',');
        buffer.append("rolPf=");
        buffer.append(getRolPf());
        buffer.append(',');
        buffer.append("rolPm=");
        buffer.append(getRolPm());
        buffer.append(',');
        buffer.append("rolSociedadControl=");
        buffer.append(getRolSociedadControl());
        buffer.append(']');
        return buffer.toString();
    }

    /**
     * @return the xmlContribuyente
     */
    public String getXmlContribuyente() {
        return xmlContribuyente;
    }

    /**
     * @param xmlContribuyente the xmlContribuyente to set
     */
    public void setXmlContribuyente(String xmlContribuyente) {
        this.xmlContribuyente = xmlContribuyente;
    }


}
