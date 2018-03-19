/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.avisocomp.vo;

import mx.gob.sat.siat.dyc.domain.archivo.DyctArchivoAvisoDTO;

import org.primefaces.model.UploadedFile;


/**
 * @author  Alfredo Ramirez
 * @since   02/07/2014
 */
public class ArchivoVO extends DyctArchivoAvisoDTO {


    @SuppressWarnings("compatibility:2847102671903092851")
    private static final long serialVersionUID = 1L;
    private transient UploadedFile file;

    public ArchivoVO() {
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public UploadedFile getFile() {
        return file;
    }
}
