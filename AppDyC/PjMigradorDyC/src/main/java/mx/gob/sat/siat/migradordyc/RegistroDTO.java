package mx.gob.sat.siat.migradordyc;

import java.util.HashMap;

public class RegistroDTO extends HashMap<String, Object>
{
    private TablaDTO tabla;
    private Object[] valores;

    public TablaDTO getTabla() {
        return tabla;
    }

    public void setTabla(TablaDTO tabla) {
        this.tabla = tabla;
    }

    public Object[] getValores() {
        return valores;
    }

    public void setValores(Object[] valores) {
        this.valores = valores;
    }
}
