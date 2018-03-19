package mx.gob.sat.siat.dyc.generico.util.common;

public class FrmTemplateVO {

    private String tituloPagina;
    private StringBuilder frmMensaje;
    // LISTA PAGINA
    private int numResultados;
    private boolean paginador;
    private int rowsPaginador;
    // DIALOG
    private String dlgTitulo;

    public FrmTemplateVO() {
        super();
    }


    public void setTituloPagina(String tituloPagina) {
        this.tituloPagina = tituloPagina;
    }

    public String getTituloPagina() {
        return tituloPagina;
    }

    public void setFrmMensaje(StringBuilder frmMensaje) {
        this.frmMensaje = frmMensaje;
    }

    public StringBuilder getFrmMensaje() {
        return frmMensaje;
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

    public void setRowsPaginador(int rowsPaginador) {
        this.rowsPaginador = rowsPaginador;
    }

    public int getRowsPaginador() {
        return rowsPaginador;
    }

    public void setDlgTitulo(String dlgTitulo) {
        this.dlgTitulo = dlgTitulo;
    }

    public String getDlgTitulo() {
        return dlgTitulo;
    }
}
