/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.admcat.dto.institucioncredito;

import java.io.Serializable;

/**
 * DTO Form Institucion Credito
 * @author [LuFerMX] Luis Fernando Barrios Quiroz [LuFerMX]
 * @date Septiembre 5, 2013
 * @since 0.1
 *
 * */
public class FrmInstitucionCreditoDTO implements Serializable {

    @SuppressWarnings("compatibility:4418182852203385374")
    private static final long serialVersionUID = 1L;

    // Tabla Lista Institucio Credito
    private boolean paginador;
    private int nColumnas;
    private boolean verTabla;
    private int totalResultados;

    // Dialog Acciones
    private String tituloDialog;
    private String mensaje;
    private int accion;

    // Nueva Institucion de credito
    private boolean nuevaIC;

    // Editar Institucion de credito
    private boolean editaIC;

    // Form Mensje Error
    private String strMsgIdInstCredito;
    private String strMsgNombInstCredito;

    public FrmInstitucionCreditoDTO() {
        super();
    }


    /** ACCESSORS */
    public void setPaginador(boolean paginador) {
        this.paginador = paginador;
    }

    public boolean isPaginador() {
        return paginador;
    }

    public void setVerTabla(boolean verTabla) {
        this.verTabla = verTabla;
    }

    public boolean isVerTabla() {
        return verTabla;
    }

    public void setTotalResultados(int totalResultados) {
        this.totalResultados = totalResultados;
    }

    public int getTotalResultados() {
        return totalResultados;
    }

    public void setTituloDialog(String tituloDialog) {
        this.tituloDialog = tituloDialog;
    }

    public String getTituloDialog() {
        return tituloDialog;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setNColumnas(int nColumnas) {
        this.nColumnas = nColumnas;
    }

    public int getNColumnas() {
        return nColumnas;
    }

    public void setNuevaIC(boolean nuevaIC) {
        this.nuevaIC = nuevaIC;
    }

    public boolean isNuevaIC() {
        return nuevaIC;
    }

    public void setEditaIC(boolean editaIC) {
        this.editaIC = editaIC;
    }

    public boolean isEditaIC() {
        return editaIC;
    }

    public void setAccion(int accion) {
        this.accion = accion;
    }

    public int getAccion() {
        return accion;
    }

    public void setStrMsgIdInstCredito(String strMsgIdInstCredito) {
        this.strMsgIdInstCredito = strMsgIdInstCredito;
    }

    public String getStrMsgIdInstCredito() {
        return strMsgIdInstCredito;
    }

    public void setStrMsgNombInstCredito(String strMsgNombInstCredito) {
        this.strMsgNombInstCredito = strMsgNombInstCredito;
    }

    public String getStrMsgNombInstCredito() {
        return strMsgNombInstCredito;
    }


    public String getParameterReport() {

        StringBuffer sb = new StringBuffer();

        sb.append("nColumnas:").append(this.getNColumnas()).append(", ");
        sb.append("verTabla:").append(this.isVerTabla()).append(", ");
        sb.append("totalResultados:").append(this.getTotalResultados()).append(", ");
        sb.append("tituloDialog:").append(this.getTituloDialog()).append(", ");
        sb.append("mensaje:").append(this.getMensaje()).append(", ");
        sb.append("accion:").append(this.getAccion()).append(", ");
        sb.append("nuevaIC:").append(this.isNuevaIC()).append(", ");
        sb.append("editaIC:").append(this.isEditaIC()).append(", ");
        sb.append("strMsgIdInstCredito:").append(this.getStrMsgNombInstCredito()).append(", ");
        sb.append("strMsgNombInstCredito:").append(this.getStrMsgIdInstCredito()).append(", ");

        return sb.toString();
    }

}
