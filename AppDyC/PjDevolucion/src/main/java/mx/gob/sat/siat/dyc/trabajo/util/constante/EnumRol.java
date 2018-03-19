package mx.gob.sat.siat.dyc.trabajo.util.constante;

public enum EnumRol
{
    DICTAMINADOR("SAT_DYC_DICT"),
    ADMINISTRADOR("SAT_DYC_ADMIN_APRO"),
    USUARIO_FISCALIZACION("SAT_DYC_USR_FISC");
    
    private String nombre;
      
    private EnumRol(String nombre)
    {
        this.nombre = nombre;      
    }
    
    public String getNombre()
    {
        return nombre;
    }
    
}
