package mx.gob.sat.siat.dyc.trabajo.util.constante;

public enum EnumTipoAdministracion
{
    AGAFF(6, "AGAFF", "Administración General de Auditoría Fiscal Federal"),
    ACGC(7, "ACGC", "Administración Central de Grandes Contribuyentes");

    private Integer claveAdministrador;
    private String siglas;
    private String descripcion;
        
    private EnumTipoAdministracion(Integer claveAdm, String sig, String desc)
    {
        claveAdministrador = claveAdm;
        siglas = sig;
        descripcion = desc;
    }

    public Integer getClaveAdministrador() {
        return claveAdministrador;
    }

    public String getSiglas() {
        return siglas;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
