/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.adminproceso;

/**
 * DTO para el catalogo DYCT_PROCESOS
 * @author  Alfredo Ramirez
 * @since   25/04/2014
 */
public class DyctProcesosDTO {

    private Integer idProceso;
    private String nombre;
    private String descripcion;
    private DyctAdminProcesosDTO dyctAdminProcesosDTO;

    public DyctProcesosDTO() {
    }

    public DyctProcesosDTO(Integer idProceso, String nombre, String descripcion) {
        this.idProceso = idProceso;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public void setIdProceso(Integer idProceso) {
        this.idProceso = idProceso;
    }

    public Integer getIdProceso() {
        return idProceso;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDyctAdminProcesosDTO(DyctAdminProcesosDTO dyctAdminProcesosDTO) {
        this.dyctAdminProcesosDTO = dyctAdminProcesosDTO;
    }

    public DyctAdminProcesosDTO getDyctAdminProcesosDTO() {
        return dyctAdminProcesosDTO;
    }
}
