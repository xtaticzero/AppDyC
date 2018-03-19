/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.gestionsol.dto.dycadministracion;

import java.io.Serializable;


/**
 * Clase DTO para el manejo de elementos en el formulario para
 * administracion devoluciones y casos de compensacion
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @date Septimbre 23, 2013
 * @since 0.1
 *
 * */
public class FrmDyCAdminVO implements Serializable {

    @SuppressWarnings("compatibility:8917308355113669258")
    private static final long serialVersionUID = 1L;
    // LISTA PARA RESULTADOS
    private String tituloPagina;
    private int numResultados;
    private boolean paginador;
    private int ifrmAccion;
    private int rowsPaginador;
    
    // FIELDSET
    private boolean collapsed;
    
    private boolean render;
    

    public FrmDyCAdminVO() {
        super();
    }


    /** ACCESSOR'S ******************************************************* */
    public void setRender(boolean render) {
        this.render = render;
    }

    public boolean isRender() {
        return render;
    }

    public void setTituloPagina(String tituloPagina) {
        this.tituloPagina = tituloPagina;
    }

    public String getTituloPagina() {
        return tituloPagina;
    }

    public void setNumResultados(int numResultados) {
        this.numResultados = numResultados;
    }

    public int getNumResultados() {
        return numResultados;
    }

    public void setPaginador(boolean paginador) {
        this.paginador = paginador;
    }

    public boolean isPaginador() {
        return paginador;
    }

    public void setIfrmAccion(int ifrmAccion) {
        this.ifrmAccion = ifrmAccion;
    }

    public int getIfrmAccion() {
        return ifrmAccion;
    }

    public void setRowsPaginador(int rowsPaginador) {
        this.rowsPaginador = rowsPaginador;
    }

    public int getRowsPaginador() {
        return rowsPaginador;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
    }

    public boolean isCollapsed() {
        return collapsed;
    }
}
