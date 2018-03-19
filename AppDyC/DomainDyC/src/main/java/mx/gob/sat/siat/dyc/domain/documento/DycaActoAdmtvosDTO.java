package mx.gob.sat.siat.dyc.domain.documento;

import java.io.Serializable;

/**
 * DTO para la tabla DYCA_ACTOADMTVOS
 * @author  Luis Alberto Dominguez LADP
 * @since   19/08/2014
 */
public class DycaActoAdmtvosDTO implements Serializable{

    @SuppressWarnings("compatibility:325556140741480326")
    private static final long serialVersionUID = 1L;
    
    private DyccMatDocumentosDTO dyccMatDocumentosDTO;
    private String prefijo;
    private Integer cveUnidadAdmtva;
    private Integer cveActoAdmtvo;
    private String nombrePlantilla;
    private String cveDocumentoTipo;
    private Integer cveActoAdmtvoFase2;

    public DycaActoAdmtvosDTO() {
        super();
    }

    public void setDyccMatDocumentosDTO(DyccMatDocumentosDTO dyccMatDocumentosDTO) {
        this.dyccMatDocumentosDTO = dyccMatDocumentosDTO;
    }

    public DyccMatDocumentosDTO getDyccMatDocumentosDTO() {
        return dyccMatDocumentosDTO;
    }

    public void setCveUnidadAdmtva(Integer cveUnidadAdmtva) {
        this.cveUnidadAdmtva = cveUnidadAdmtva;
    }

    public Integer getCveUnidadAdmtva() {
        return cveUnidadAdmtva;
    }

    public void setCveActoAdmtvo(Integer cveActoAdmtvo) {
        this.cveActoAdmtvo = cveActoAdmtvo;
    }

    public Integer getCveActoAdmtvo() {
        return cveActoAdmtvo;
    }

    public void setPrefijo(String prefijo) {
        this.prefijo = prefijo;
    }

    public String getPrefijo() {
        return prefijo;
    }

    public void setNombrePlantilla(String nombrePlantilla) {
        this.nombrePlantilla = nombrePlantilla;
    }

    public String getNombrePlantilla() {
        return nombrePlantilla;
    }

    public String getCveDocumentoTipo() {
        return cveDocumentoTipo;
    }

    public void setCveDocumentoTipo(String cveDocumentoTipo) {
        this.cveDocumentoTipo = cveDocumentoTipo;
    }

    public Integer getCveActoAdmtvoFase2() {
        return cveActoAdmtvoFase2;
    }

    public void setCveActoAdmtvoFase2(Integer cveActoAdmtvoFase2) {
        this.cveActoAdmtvoFase2 = cveActoAdmtvoFase2;
    }
    @Override
    public String toString() {
        return "DycaActoAdmtvosDTO{" + "dyccMatDocumentosDTO=" + dyccMatDocumentosDTO + ", prefijo=" + prefijo + ", cveUnidadAdmtva=" + cveUnidadAdmtva + ", cveActoAdmtvo=" + cveActoAdmtvo + ", nombrePlantilla=" + nombrePlantilla + '}';
    }
    
}
