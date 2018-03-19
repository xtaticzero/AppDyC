package mx.gob.sat.siat.dyc.util.common;

import java.io.Serializable;

public class Paginador implements Serializable {
    @SuppressWarnings("compatibility:-7646356255040830012")
    private static final long serialVersionUID = -1833437516608468244L;

    private Integer nRegPorPagina;
    private Integer nPaginaActual;

    public void setNRegPorPagina(Integer nRegPorPagina) {
        this.nRegPorPagina = nRegPorPagina;
    }

    public Integer getNRegPorPagina() {
        return nRegPorPagina;
    }

    public void setNPaginaActual(Integer nPaginaActual) {
        this.nPaginaActual = nPaginaActual;
    }

    public Integer getNPaginaActual() {
        return nPaginaActual;
    }

    public int getFrom() {
        return ((this.nPaginaActual - 1) * this.nRegPorPagina) + 1;
    }

    public int getTo() {
        return (this.nPaginaActual * this.nRegPorPagina);
    }
}
