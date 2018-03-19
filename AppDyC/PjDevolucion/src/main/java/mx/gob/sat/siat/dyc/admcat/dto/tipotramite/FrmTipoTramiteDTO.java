/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.admcat.dto.tipotramite;

import java.io.Serializable;

/**
 * DTO Formulario Tipo Tramite
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @date Octubre 2, 2013
 * @since 0.1
 *
 * */
public class FrmTipoTramiteDTO implements Serializable {

    @SuppressWarnings("compatibility:8944172294031124321")
    private static final long serialVersionUID = 1L;

    // Tabla Lista Institucio Credito
    private boolean paginador;
    private int nColumnas;
    private boolean verTabla;
    private int totalResultados;
    private String nombreCU;

    // CAMPOS FORMA
    private int selectTipo;
    private boolean requiereCCI;
    private boolean rAutomatica;
    private boolean rPlazo;
    private int iPlazo1;
    private int iPlazo2;
    private int iPlazo3;

    // DIALOGO ACCIONES
    private String tituloDialog;
    private String mensaje;
    private int accion;

    // COMPETENCIA
    private boolean pagCompetencia;
    private int nColCompentencia;
    private int trCompentencia;
    // ORIGEN SALDO
    private boolean pagOrigens;
    private int nColOrigens;
    private int trOrigens;
    // ROL
    private boolean pagRol;
    private int nColRol;
    private int trRol;

    // MENSAJES CAMPOS
    private String strInpCodigo;

    // ESTADOS FORMA
    private boolean inicia;
    private boolean devolucion;
    private boolean compensacion;
    private boolean bPlazo;
    private String strPlazo;
    private boolean cmbConcepto;

    // Nuevo Tipo Tramite
    private boolean nuevaTT;

    // Editar Tipo Tramite
    private boolean editaTT;

    // Form Mensje Error
    private String strMsgTTSelect;
    private String strMsgTTCodigo;
    private String strMsgTTNombre;
    private String strMsgTTImpuesto;
    private String strMsgTTPuntos;
    private String strMsgTTConcepto;

    //FORM OPERACION
    private String strOperAdicion;
    private String strOperModifica;
    private String strOperElimina;

    public FrmTipoTramiteDTO() {
        super();
    }


    /** ACCESSORS *************************************************************************************************** */
    public void setPaginador(boolean paginador) {
        this.paginador = paginador;
    }

    public boolean isPaginador() {
        return paginador;
    }

    public void setNColumnas(int nColumnas) {
        this.nColumnas = nColumnas;
    }

    public int getNColumnas() {
        return nColumnas;
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

    public void setAccion(int accion) {
        this.accion = accion;
    }

    public int getAccion() {
        return accion;
    }

    public void setStrOperAdicion(String strOperAdicion) {
        this.strOperAdicion = strOperAdicion;
    }

    public String getStrOperAdicion() {
        return strOperAdicion;
    }

    public void setStrOperModifica(String strOperModifica) {
        this.strOperModifica = strOperModifica;
    }

    public String getStrOperModifica() {
        return strOperModifica;
    }

    public void setStrOperElimina(String strOperElimina) {
        this.strOperElimina = strOperElimina;
    }

    public String getStrOperElimina() {
        return strOperElimina;
    }

    public void setNombreCU(String nombreCU) {
        this.nombreCU = nombreCU;
    }

    public String getNombreCU() {
        return nombreCU;
    }

    public void setNuevaTT(boolean nuevaTT) {
        this.nuevaTT = nuevaTT;
    }

    public boolean isNuevaTT() {
        return nuevaTT;
    }

    public void setEditaTT(boolean editaTT) {
        this.editaTT = editaTT;
    }

    public boolean isEditaTT() {
        return editaTT;
    }

    public void setRequiereCCI(boolean requiereCCI) {
        this.requiereCCI = requiereCCI;
    }

    public boolean isRequiereCCI() {
        return requiereCCI;
    }

    public void setRAutomatica(boolean rAutomatica) {
        this.rAutomatica = rAutomatica;
    }

    public boolean isRAutomatica() {
        return rAutomatica;
    }

    public void setPagCompetencia(boolean pagCompetencia) {
        this.pagCompetencia = pagCompetencia;
    }

    public boolean isPagCompetencia() {
        return pagCompetencia;
    }

    public void setNColCompentencia(int nColCompentencia) {
        this.nColCompentencia = nColCompentencia;
    }

    public int getNColCompentencia() {
        return nColCompentencia;
    }

    public void setTrCompentencia(int trCompentencia) {
        this.trCompentencia = trCompentencia;
    }

    public int getTrCompentencia() {
        return trCompentencia;
    }

    public void setPagOrigens(boolean pagOrigens) {
        this.pagOrigens = pagOrigens;
    }

    public boolean isPagOrigens() {
        return pagOrigens;
    }

    public void setNColOrigens(int nColOrigens) {
        this.nColOrigens = nColOrigens;
    }

    public int getNColOrigens() {
        return nColOrigens;
    }

    public void setTrOrigens(int trOrigens) {
        this.trOrigens = trOrigens;
    }

    public int getTrOrigens() {
        return trOrigens;
    }

    public void setPagRol(boolean pagRol) {
        this.pagRol = pagRol;
    }

    public boolean isPagRol() {
        return pagRol;
    }

    public void setNColRol(int nColRol) {
        this.nColRol = nColRol;
    }

    public int getNColRol() {
        return nColRol;
    }

    public void setTrRol(int trRol) {
        this.trRol = trRol;
    }

    public int getTrRol() {
        return trRol;
    }

    public void setSelectTipo(int selectTipo) {
        this.selectTipo = selectTipo;
    }

    public int getSelectTipo() {
        return selectTipo;
    }

    public void setStrInpCodigo(String strInpCodigo) {
        this.strInpCodigo = strInpCodigo;
    }

    public String getStrInpCodigo() {
        return strInpCodigo;
    }

    public void setDevolucion(boolean devolucion) {
        this.devolucion = devolucion;
    }

    public boolean isDevolucion() {
        return devolucion;
    }

    public void setCompensacion(boolean compensacion) {
        this.compensacion = compensacion;
    }

    public boolean isCompensacion() {
        return compensacion;
    }

    public void setInicia(boolean inicia) {
        this.inicia = inicia;
    }

    public boolean isInicia() {
        return inicia;
    }

    public void setBPlazo(boolean bPlazo) {
        this.bPlazo = bPlazo;
    }

    public boolean isBPlazo() {
        return bPlazo;
    }

    public void setStrPlazo(String strPlazo) {
        this.strPlazo = strPlazo;
    }

    public String getStrPlazo() {
        return strPlazo;
    }

    public void setCmbConcepto(boolean cmbConcepto) {
        this.cmbConcepto = cmbConcepto;
    }

    public boolean isCmbConcepto() {
        return cmbConcepto;
    }

    public void setStrMsgTTSelect(String strMsgTTSelect) {
        this.strMsgTTSelect = strMsgTTSelect;
    }

    public String getStrMsgTTSelect() {
        return strMsgTTSelect;
    }

    public void setStrMsgTTCodigo(String strMsgTTCodigo) {
        this.strMsgTTCodigo = strMsgTTCodigo;
    }

    public String getStrMsgTTCodigo() {
        return strMsgTTCodigo;
    }

    public void setStrMsgTTNombre(String strMsgTTNombre) {
        this.strMsgTTNombre = strMsgTTNombre;
    }

    public String getStrMsgTTNombre() {
        return strMsgTTNombre;
    }

    public void setStrMsgTTImpuesto(String strMsgTTImpuesto) {
        this.strMsgTTImpuesto = strMsgTTImpuesto;
    }

    public String getStrMsgTTImpuesto() {
        return strMsgTTImpuesto;
    }

    public void setStrMsgTTPuntos(String strMsgTTPuntos) {
        this.strMsgTTPuntos = strMsgTTPuntos;
    }

    public String getStrMsgTTPuntos() {
        return strMsgTTPuntos;
    }

    public void setStrMsgTTConcepto(String strMsgTTConcepto) {
        this.strMsgTTConcepto = strMsgTTConcepto;
    }

    public String getStrMsgTTConcepto() {
        return strMsgTTConcepto;
    }

    public void setRPlazo(boolean rPlazo) {
        this.rPlazo = rPlazo;
    }

    public boolean isRPlazo() {
        return rPlazo;
    }

    public void setIPlazo1(int iPlazo1) {
        this.iPlazo1 = iPlazo1;
    }

    public int getIPlazo1() {
        return iPlazo1;
    }

    public void setIPlazo2(int iPlazo2) {
        this.iPlazo2 = iPlazo2;
    }

    public int getIPlazo2() {
        return iPlazo2;
    }

    public void setIPlazo3(int iPlazo3) {
        this.iPlazo3 = iPlazo3;
    }

    public int getIPlazo3() {
        return iPlazo3;
    }
}
