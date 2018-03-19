package mx.gob.sat.siat.dyc.domain.tramites;

import java.io.Serializable;


public class TramitesValidacionDTO implements Serializable {
    @SuppressWarnings("compatibility:8895954608117277156")
    private static final long serialVersionUID = 9084446974363110258L;

    private boolean tramUltimaDelclaracion;
    private boolean tramConsultaSaldoICEPSP;
    private boolean tramitesNumDocumento;
    private boolean tramitesRNFDC19;
    private boolean tramitesDIOT;
    private String presentacionSaldo;


    public TramitesValidacionDTO() {
        super();
    }

    public void setTramUltimaDelclaracion(boolean tramUltimaDelclaracion) {
        this.tramUltimaDelclaracion = tramUltimaDelclaracion;
    }

    public boolean isTramUltimaDelclaracion() {
        return tramUltimaDelclaracion;
    }

    public void setTramConsultaSaldoICEPSP(boolean tramConsultaSaldoICEPSP) {
        this.tramConsultaSaldoICEPSP = tramConsultaSaldoICEPSP;
    }

    public boolean isTramConsultaSaldoICEPSP() {
        return tramConsultaSaldoICEPSP;
    }

    public void setTramitesNumDocumento(boolean tramitesNumDocumento) {
        this.tramitesNumDocumento = tramitesNumDocumento;
    }

    public boolean isTramitesNumDocumento() {
        return tramitesNumDocumento;
    }

    public void setTramitesRNFDC19(boolean tramitesRNFDC19) {
        this.tramitesRNFDC19 = tramitesRNFDC19;
    }

    public boolean isTramitesRNFDC19() {
        return tramitesRNFDC19;
    }

    public void setTramitesDIOT(boolean tramitesDIOT) {
        this.tramitesDIOT = tramitesDIOT;
    }

    public boolean isTramitesDIOT() {
        return tramitesDIOT;
    }

    public void setPresentacionSaldo(String presentacionSaldo) {
        this.presentacionSaldo = presentacionSaldo;
    }

    public String getPresentacionSaldo() {
        return presentacionSaldo;
    }
}
