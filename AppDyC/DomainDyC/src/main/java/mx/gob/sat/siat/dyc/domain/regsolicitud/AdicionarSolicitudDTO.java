/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.domain.regsolicitud;

import java.io.Serializable;


/**
 * Clase para los objetos de vista que se renderean, (se muestran y ocultan) de AdicionarSolicitud.jsf
 * @author Paola Rivera
 */
public class AdicionarSolicitudDTO implements Serializable {
    @SuppressWarnings("compatibility:2888734136910578859")
    private static final long serialVersionUID = 1L;

    /**Tab activo*/
    private boolean activo;
    private boolean flagTemp;

    /**Muestra Tabs*/
    private boolean verRFC;
    private boolean verRFCControlador;
    private boolean rowTipoDeclaracion;
    private boolean bloqDatosIcep;

    /**Bloquea tabs*/
    private boolean bloqInfoSaldoFavor;
    private boolean bloqInfoDIOT;
    private boolean bloqAnexos;
    private boolean bloqDoctos;
    private boolean bloqDeclaracionNormal;
    private boolean showSubtipoTramite;
    private boolean confirmarICEP;
    private boolean importeSaldosDyC;

    /**Rows del tab Informacion del Saldo*/
    private boolean rowFechaPresentacion;
    private boolean rowFechaCaucion;
    private boolean rowNumeroOperacion;
    private boolean rowNumeroDocumento;
    private boolean rowSaldoFavor;
    private boolean rowImporteAcreditamiento;
    private boolean rowImporteDevComEfectuadas;
    private boolean rowSubOrigenSaldo;
    private boolean rowActividad;
    private boolean rowDIOT;
    private boolean flagFechDiot;
    private boolean rowALTEX;
    private boolean showUploadFile;
    private boolean showEdoCta;
    private boolean flagOperaciones;
    private boolean flagSecAgp;

    /**Lables */
    private int competencia;
    private String flagDeclaracion;
    private String messageSol;
    private String nombreDocumento;
    private String cuentaClabe;
    private String step;
    private String rfcTerceros;

    private String messageProtestaEdoCta;
    private String messageGlobal;
    private String messageSecAgp;
    private String messageProtestaOperaciones;
    private String protesta;
    private Integer numDocAdjuntos;
    private boolean flgEdoCta;
    private boolean resSecAgp;
    private boolean endSolicitud;
    private boolean presentaDoc;
    private boolean showMessageError;
    private boolean showMessageErrorAsig;
    private boolean showMessageErrorAsigFolio;
    private boolean showMessageErrorAsigConsulta;


    public AdicionarSolicitudDTO() {
        super();
    }


    public void setBloqInfoSaldoFavor(boolean bloqInfoSaldoFavor) {
        this.bloqInfoSaldoFavor = bloqInfoSaldoFavor;
    }

    public boolean getBloqInfoSaldoFavor() {
        return bloqInfoSaldoFavor;
    }

    public void setBloqInfoDIOT(boolean bloqInfoDIOT) {
        this.bloqInfoDIOT = bloqInfoDIOT;
    }

    public boolean getBloqInfoDIOT() {
        return bloqInfoDIOT;
    }

    public void setBloqAnexos(boolean bloqAnexos) {
        this.bloqAnexos = bloqAnexos;
    }

    public boolean getBloqAnexos() {
        return bloqAnexos;
    }

    public void setBloqDoctos(boolean bloqDoctos) {
        this.bloqDoctos = bloqDoctos;
    }

    public boolean getBloqDoctos() {
        return bloqDoctos;
    }

    public void setRowFechaCaucion(boolean rowFechaCaucion) {
        this.rowFechaCaucion = rowFechaCaucion;
    }

    public boolean isRowFechaCaucion() {
        return rowFechaCaucion;
    }

    public void setRowNumeroOperacion(boolean rowNumeroOperacion) {
        this.rowNumeroOperacion = rowNumeroOperacion;
    }

    public boolean isRowNumeroOperacion() {
        return rowNumeroOperacion;
    }

    public void setRowNumeroDocumento(boolean rowNumeroDocumento) {
        this.rowNumeroDocumento = rowNumeroDocumento;
    }

    public boolean isRowNumeroDocumento() {
        return rowNumeroDocumento;
    }

    public void setRowSaldoFavor(boolean rowSaldoFavor) {
        this.rowSaldoFavor = rowSaldoFavor;
    }

    public boolean isRowSaldoFavor() {
        return rowSaldoFavor;
    }

    public void setRowImporteAcreditamiento(boolean rowImporteAcreditamiento) {
        this.rowImporteAcreditamiento = rowImporteAcreditamiento;
    }

    public boolean isRowImporteAcreditamiento() {
        return rowImporteAcreditamiento;
    }

    public void setBloqDeclaracionNormal(boolean bloqDeclaracionNormal) {
        this.bloqDeclaracionNormal = bloqDeclaracionNormal;
    }

    public boolean isBloqDeclaracionNormal() {
        return bloqDeclaracionNormal;
    }

    public void setRowImporteDevComEfectuadas(boolean rowImporteDevComEfectuadas) {
        this.rowImporteDevComEfectuadas = rowImporteDevComEfectuadas;
    }

    public boolean isRowImporteDevComEfectuadas() {
        return rowImporteDevComEfectuadas;
    }

    public void setShowUploadFile(boolean showUploadFile) {
        this.showUploadFile = showUploadFile;
    }

    public boolean isShowUploadFile() {
        return showUploadFile;
    }


    public void setVerRFC(boolean verRFC) {
        this.verRFC = verRFC;
    }

    public boolean isVerRFC() {
        return verRFC;
    }

    public void setVerRFCControlador(boolean verRFCControlador) {
        this.verRFCControlador = verRFCControlador;
    }

    public boolean isVerRFCControlador() {
        return verRFCControlador;
    }

    public void setFlagDeclaracion(String flagDeclaracion) {
        this.flagDeclaracion = flagDeclaracion;
    }

    public String getFlagDeclaracion() {
        return flagDeclaracion;
    }

    public void setShowSubtipoTramite(boolean showSubtipoTramite) {
        this.showSubtipoTramite = showSubtipoTramite;
    }

    public boolean isShowSubtipoTramite() {
        return showSubtipoTramite;
    }

    public void setConfirmarICEP(boolean confirmarICEP) {
        this.confirmarICEP = confirmarICEP;
    }

    public boolean isConfirmarICEP() {
        return confirmarICEP;
    }

    public void setRowSubOrigenSaldo(boolean rowSubOrigenSaldo) {
        this.rowSubOrigenSaldo = rowSubOrigenSaldo;
    }

    public boolean isRowSubOrigenSaldo() {
        return rowSubOrigenSaldo;
    }


    public void setRowFechaPresentacion(boolean rowFechaPresentacion) {
        this.rowFechaPresentacion = rowFechaPresentacion;
    }

    public boolean isRowFechaPresentacion() {
        return rowFechaPresentacion;
    }

    public void setImporteSaldosDyC(boolean importeSaldosDyC) {
        this.importeSaldosDyC = importeSaldosDyC;
    }

    public boolean isImporteSaldosDyC() {
        return importeSaldosDyC;
    }

    public void setRowDIOT(boolean rowDIOT) {
        this.rowDIOT = rowDIOT;
    }

    public boolean isRowDIOT() {
        return rowDIOT;
    }

    public void setRowALTEX(boolean rowALTEX) {
        this.rowALTEX = rowALTEX;
    }

    public boolean isRowALTEX() {
        return rowALTEX;
    }

    public void setRowTipoDeclaracion(boolean rowTipoDeclaracion) {
        this.rowTipoDeclaracion = rowTipoDeclaracion;
    }

    public boolean isRowTipoDeclaracion() {
        return rowTipoDeclaracion;
    }

    public void setBloqDatosIcep(boolean bloqDatosIcep) {
        this.bloqDatosIcep = bloqDatosIcep;
    }

    public boolean isBloqDatosIcep() {
        return bloqDatosIcep;
    }

    public void setShowEdoCta(boolean showEdoCta) {
        this.showEdoCta = showEdoCta;
    }

    public boolean isShowEdoCta() {
        return showEdoCta;
    }

    public void setRowActividad(boolean rowActividad) {
        this.rowActividad = rowActividad;
    }

    public boolean isRowActividad() {
        return rowActividad;
    }

    public void setFlagOperaciones(boolean flagOperaciones) {
        this.flagOperaciones = flagOperaciones;
    }

    public boolean isFlagOperaciones() {
        return flagOperaciones;
    }

    public void setFlagSecAgp(boolean flagSecAgp) {
        this.flagSecAgp = flagSecAgp;
    }

    public boolean isFlagSecAgp() {
        return flagSecAgp;
    }

    public void setFlagTemp(boolean flagTemp) {
        this.flagTemp = flagTemp;
    }

    public boolean isFlagTemp() {
        return flagTemp;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setCompetencia(int competencia) {
        this.competencia = competencia;
    }

    public int getCompetencia() {
        return competencia;
    }

    public void setMessageSol(String messageSol) {
        this.messageSol = messageSol;
    }

    public String getMessageSol() {
        return messageSol;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public void setCuentaClabe(String cuentaClabe) {
        this.cuentaClabe = cuentaClabe;
    }

    public String getCuentaClabe() {
        return cuentaClabe;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getStep() {
        return step;
    }

    public void setRfcTerceros(String rfcTerceros) {
        this.rfcTerceros = rfcTerceros;
    }

    public String getRfcTerceros() {
        return rfcTerceros;
    }

    public void setMessageProtestaEdoCta(String messageProtestaEdoCta) {
        this.messageProtestaEdoCta = messageProtestaEdoCta;
    }

    public String getMessageProtestaEdoCta() {
        return messageProtestaEdoCta;
    }

    public void setMessageGlobal(String messageGlobal) {
        this.messageGlobal = messageGlobal;
    }

    public String getMessageGlobal() {
        return messageGlobal;
    }

    public void setMessageSecAgp(String messageSecAgp) {
        this.messageSecAgp = messageSecAgp;
    }

    public String getMessageSecAgp() {
        return messageSecAgp;
    }

    public void setMessageProtestaOperaciones(String messageProtestaOperaciones) {
        this.messageProtestaOperaciones = messageProtestaOperaciones;
    }

    public String getMessageProtestaOperaciones() {
        return messageProtestaOperaciones;
    }

    public void setProtesta(String protesta) {
        this.protesta = protesta;
    }

    public String getProtesta() {
        return protesta;
    }

    public void setNumDocAdjuntos(Integer numDocAdjuntos) {
        this.numDocAdjuntos = numDocAdjuntos;
    }

    public Integer getNumDocAdjuntos() {
        return numDocAdjuntos;
    }

    public void setFlgEdoCta(boolean flgEdoCta) {
        this.flgEdoCta = flgEdoCta;
    }

    public boolean isFlgEdoCta() {
        return flgEdoCta;
    }

    public void setResSecAgp(boolean flgSecAgp) {
        this.resSecAgp = flgSecAgp;
    }

    public boolean isResSecAgp() {
        return resSecAgp;
    }

    public void setEndSolicitud(boolean endSolicitud) {
        this.endSolicitud = endSolicitud;
    }

    public boolean isEndSolicitud() {
        return endSolicitud;
    }

    public void setPresentaDoc(boolean presentaDoc) {
        this.presentaDoc = presentaDoc;
    }

    public boolean isPresentaDoc() {
        return presentaDoc;
    }

    public void setShowMessageError(boolean showMessageError) {
        this.showMessageError = showMessageError;
    }

    public boolean isShowMessageError() {
        return showMessageError;
    }

    public void setShowMessageErrorAsig(boolean showMessageErrorAsig) {
        this.showMessageErrorAsig = showMessageErrorAsig;
    }

    public boolean isShowMessageErrorAsig() {
        return showMessageErrorAsig;
    }

    public boolean isFlagFechDiot() {
        return flagFechDiot;
    }

    public void setFlagFechDiot(boolean flagFechDiot) {
        this.flagFechDiot = flagFechDiot;
    }
    
    
    public boolean isShowMessageErrorAsigFolio() {
        return showMessageErrorAsigFolio;
    }

    public void setShowMessageErrorAsigFolio(boolean showMessageErrorAsigFolio) {
        this.showMessageErrorAsigFolio = showMessageErrorAsigFolio;
    }

    public boolean isShowMessageErrorAsigConsulta() {
        return showMessageErrorAsigConsulta;
    }

    public void setShowMessageErrorAsigConsulta(boolean showMessageErrorAsigConsulta) {
        this.showMessageErrorAsigConsulta = showMessageErrorAsigConsulta;
    }
    
    
}
