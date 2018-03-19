package mx.gob.sat.siat.dyc.domain.vistas;

public enum EnumTipoUnidadAdmvaDyC 
{
    LOCAL(13, "ALAF", "Administraciones locales de Auditoría Fiscal"),
    GRANDES_CONTRIBUYENTES(16, "AC Grandes Contribuyentes", "Administraciones Centrales de Grandes Contribuyentes"),
    DEVOLUCIONES(17, "AC de Auditoría Fiscal", "Administraciones Centrales de Auditoría Fiscal");

    private Integer idTipoUnidadAdmva;
    private String nombre;
    private String descripcion;
    
    private EnumTipoUnidadAdmvaDyC(Integer idTipoUnidadAdmvaL, String nombreL, String descripcionL)
    {
        idTipoUnidadAdmva = idTipoUnidadAdmvaL;
        nombre = nombreL;
        descripcion = descripcionL;
    }
    
    public Integer getIdTipoUnidadAdmva()
    {
        return idTipoUnidadAdmva;
    }

    public String getNombre()
    {
        return nombre;
    }

    public String getDescripcion()
    {
        return descripcion;
    }

}
