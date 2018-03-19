/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.avisocomp.vo;

import mx.gob.sat.siat.dyc.domain.anexo.DyccMatrizAnexosDTO;

import org.primefaces.model.UploadedFile;


/**
 * @author  Alfredo Ramirez
 * @since   20/05/2014
 */
public class AnexoVO extends DyccMatrizAnexosDTO {

    @SuppressWarnings("compatibility:-1047560306716355949")
    private static final long serialVersionUID = 1L;

    private transient UploadedFile file;
    private Integer tipoTramite;
    private String cuadroSaldo;
    private String adjuntado;
    private String folioServTemp;

    public AnexoVO() {
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setTipoTramite(Integer tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public Integer getTipoTramite() {
        return tipoTramite;
    }

    public void setCuadroSaldo(String cuadroSaldo) {
        this.cuadroSaldo = cuadroSaldo;
    }

    public String getCuadroSaldo() {
        return cuadroSaldo;
    }

    public void setAdjuntado(String adjuntado) {
        this.adjuntado = adjuntado;
    }

    public String getAdjuntado() {
        return adjuntado;
    }

    public void setFolioServTemp(String folioServTemp) {
        this.folioServTemp = folioServTemp;
    }

    public String getFolioServTemp() {
        return folioServTemp;
    }
}
