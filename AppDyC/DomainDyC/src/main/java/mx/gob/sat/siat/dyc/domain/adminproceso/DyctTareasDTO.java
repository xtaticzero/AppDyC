/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.adminproceso;

/**
 * DTO para el catalogo DYCT_TAREAS
 * @author  Alfredo Ramirez
 * @since   25/04/2014
 */
public class DyctTareasDTO {

    private String nombreJOB;
    private String nombreTRIGGER;
    private String horarioEjecucion;
    private DyctProcesosDTO dyctProcesosDTO;

    public DyctTareasDTO() {
    }

    public DyctTareasDTO(String nombreJOB, String nombreTRIGGER, String horarioEjecucion) {
        this.nombreJOB = nombreJOB;
        this.nombreTRIGGER = nombreTRIGGER;
        this.horarioEjecucion = horarioEjecucion;
    }

    public void setNombreJOB(String nombreJOB) {
        this.nombreJOB = nombreJOB;
    }

    public String getNombreJOB() {
        return nombreJOB;
    }

    public void setNombreTRIGGER(String nombreTRIGGER) {
        this.nombreTRIGGER = nombreTRIGGER;
    }

    public String getNombreTRIGGER() {
        return nombreTRIGGER;
    }

    public void setHorarioEjecucion(String horarioEjecucion) {
        this.horarioEjecucion = horarioEjecucion;
    }

    public String getHorarioEjecucion() {
        return horarioEjecucion;
    }

    public void setDyctProcesosDTO(DyctProcesosDTO dyctProcesosDTO) {
        this.dyctProcesosDTO = dyctProcesosDTO;
    }

    public DyctProcesosDTO getDyctProcesosDTO() {
        return dyctProcesosDTO;
    }
}
