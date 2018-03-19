/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.avisocomp.vo;

import java.io.Serializable;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.declaraciontemp.DyctDeclaraTempDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaracionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctOrigenAvisoDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccTipoPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;


/**
 * @author  Alfredo Ramirez
 * @since   06/06/2014
 */

public class CuadroVO implements Serializable {

    @SuppressWarnings("compatibility:8577537901672936489")
    private static final long serialVersionUID = 1L;

    private Integer numCuadro;
    private Integer idCuadro;
    private String numControl;
    private String descOrigenSaldo;
    private String descTipoTramite;
    private boolean preguntaPresentoDIOT;

    private DyctOrigenAvisoDTO dyctOrigenAvisoDTO;
    private DycpCompensacionDTO dycpCompensacionDTO;
    private DycpServicioDTO dycpServicioDTO;

    private String strDiotNumOperacion;

    private String diTipoPeriodo;
    private String descTipoPeriodo;
    private String descPeriodo;
    private String descEjercicio;
    private String descNumOperacionDec;
    private boolean diConcepto;
    private DyccConceptoDTO dyccDiConceptoDTO;
    private String descConcepto;

    private boolean ddRequiereNumControl;
    private String descTipoDeclaracion;
    private boolean cuadroMostrarNumDocumento;
    private String ddNumDocumento;
    private boolean pestanaDatosDeclaracionNormal;
    private DyctDeclaracionDTO dyctDeclaracionDTO;

    private DyctDeclaraTempDTO dyctDeclaraTempDTO;
    private List<DyccTipoPeriodoDTO> listaTiposDePeriodosOrigen;
    private List<DyccPeriodoDTO> listaPeriodosOrigen;
    private String idTipoPeriodoOrigen;
    private Integer ddIdTipoDeclaracion;
    private Boolean isAcreditamiento = Boolean.FALSE;
    private Boolean isDevueltoCompensado = Boolean.FALSE;
    private DyccPeriodoDTO  dyccPeriodoOrigenDTO;
    private DyccEjercicioDTO  dyccDiEjercicioDTO;
    private DyccConceptoDTO dyccConceptoOrigen;
    private List<DyccTipoTramiteDTO> listaTiposDeTramites;
    private List<DyccOrigenSaldoDTO> listaOrigenesDeSaldos;
    private DyccTipoTramiteDTO dycctipoTramiteDTO;
    private List<DyccConceptoDTO> listaConceptosOrigen;
    private String descConceptoOrigen;
    private String numOperacion;
    private String fechaPresentOrigen;

    public void setNumCuadro(Integer numCuadro) {
        this.numCuadro = numCuadro;
    }

    public Integer getNumCuadro() {
        return numCuadro;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setDescOrigenSaldo(String descOrigenSaldo) {
        this.descOrigenSaldo = descOrigenSaldo;
    }

    public String getDescOrigenSaldo() {
        return descOrigenSaldo;
    }

    public void setDescTipoTramite(String descTipoTramite) {
        this.descTipoTramite = descTipoTramite;
    }

    public String getDescTipoTramite() {
        return descTipoTramite;
    }

    public void setPreguntaPresentoDIOT(boolean preguntaPresentoDIOT) {
        this.preguntaPresentoDIOT = preguntaPresentoDIOT;
    }

    public boolean isPreguntaPresentoDIOT() {
        return preguntaPresentoDIOT;
    }

    public void setDyctOrigenAvisoDTO(DyctOrigenAvisoDTO dyctOrigenAvisoDTO) {
        this.dyctOrigenAvisoDTO = dyctOrigenAvisoDTO;
    }

    public DyctOrigenAvisoDTO getDyctOrigenAvisoDTO() {
        return dyctOrigenAvisoDTO;
    }

    public void setStrDiotNumOperacion(String strDiotNumOperacion) {
        this.strDiotNumOperacion = strDiotNumOperacion;
    }

    public String getStrDiotNumOperacion() {
        return strDiotNumOperacion;
    }

    public void setDiTipoPeriodo(String diTipoPeriodo) {
        this.diTipoPeriodo = diTipoPeriodo;
    }

    public String getDiTipoPeriodo() {
        return diTipoPeriodo;
    }

    public void setDescTipoPeriodo(String descTipoPeriodo) {
        this.descTipoPeriodo = descTipoPeriodo;
    }

    public String getDescTipoPeriodo() {
        return descTipoPeriodo;
    }

    public void setDescPeriodo(String descPeriodo) {
        this.descPeriodo = descPeriodo;
    }

    public String getDescPeriodo() {
        return descPeriodo;
    }

    public void setDescEjercicio(String descEjercicio) {
        this.descEjercicio = descEjercicio;
    }

    public String getDescEjercicio() {
        return descEjercicio;
    }

    public void setDescNumOperacionDec(String descNumOperacionDec) {
        this.descNumOperacionDec = descNumOperacionDec;
    }

    public String getDescNumOperacionDec() {
        return descNumOperacionDec;
    }

    public void setDiConcepto(boolean diConcepto) {
        this.diConcepto = diConcepto;
    }

    public boolean isDiConcepto() {
        return diConcepto;
    }

    public void setDyccDiConceptoDTO(DyccConceptoDTO dyccDiConceptoDTO) {
        this.dyccDiConceptoDTO = dyccDiConceptoDTO;
    }

    public DyccConceptoDTO getDyccDiConceptoDTO() {
        return dyccDiConceptoDTO;
    }

    public void setDescConcepto(String descConcepto) {
        this.descConcepto = descConcepto;
    }

    public String getDescConcepto() {
        return descConcepto;
    }

    public void setDdRequiereNumControl(boolean ddRequiereNumControl) {
        this.ddRequiereNumControl = ddRequiereNumControl;
    }

    public boolean isDdRequiereNumControl() {
        return ddRequiereNumControl;
    }

    public void setDescTipoDeclaracion(String descTipoDeclaracion) {
        this.descTipoDeclaracion = descTipoDeclaracion;
    }

    public String getDescTipoDeclaracion() {
        return descTipoDeclaracion;
    }

    public void setCuadroMostrarNumDocumento(boolean cuadroMostrarNumDocumento) {
        this.cuadroMostrarNumDocumento = cuadroMostrarNumDocumento;
    }

    public boolean isCuadroMostrarNumDocumento() {
        return cuadroMostrarNumDocumento;
    }

    public void setDdNumDocumento(String ddNumDocumento) {
        this.ddNumDocumento = ddNumDocumento;
    }

    public String getDdNumDocumento() {
        return ddNumDocumento;
    }

    public void setPestanaDatosDeclaracionNormal(boolean pestanaDatosDeclaracionNormal) {
        this.pestanaDatosDeclaracionNormal = pestanaDatosDeclaracionNormal;
    }

    public boolean isPestanaDatosDeclaracionNormal() {
        return pestanaDatosDeclaracionNormal;
    }

    public void setDyctDeclaracionDTO(DyctDeclaracionDTO dyctDeclaracionDTO) {
        this.dyctDeclaracionDTO = dyctDeclaracionDTO;
    }

    public DyctDeclaracionDTO getDyctDeclaracionDTO() {
        return dyctDeclaracionDTO;
    }

    public void setDyctDeclaraTempDTO(DyctDeclaraTempDTO dyctDeclaraTempDTO) {
        this.dyctDeclaraTempDTO = dyctDeclaraTempDTO;
    }

    public DyctDeclaraTempDTO getDyctDeclaraTempDTO() {
        return dyctDeclaraTempDTO;
    }

    public void setDycpCompensacionDTO(DycpCompensacionDTO dycpCompensacionDTO) {
        this.dycpCompensacionDTO = dycpCompensacionDTO;
    }

    public DycpCompensacionDTO getDycpCompensacionDTO() {
        return dycpCompensacionDTO;
    }

    public void setDycpServicioDTO(DycpServicioDTO dycpServicioDTO) {
        this.dycpServicioDTO = dycpServicioDTO;
    }

    public DycpServicioDTO getDycpServicioDTO() {
        return dycpServicioDTO;
    }

    public void setListaTiposDePeriodosOrigen(List<DyccTipoPeriodoDTO> listaTiposDePeriodosOrigen) {
        this.listaTiposDePeriodosOrigen = listaTiposDePeriodosOrigen;
    }

    public List<DyccTipoPeriodoDTO> getListaTiposDePeriodosOrigen() {
        return listaTiposDePeriodosOrigen;
    }

    public void setListaPeriodosOrigen(List<DyccPeriodoDTO> listaPeriodosOrigen) {
        this.listaPeriodosOrigen = listaPeriodosOrigen;
    }

    public List<DyccPeriodoDTO> getListaPeriodosOrigen() {
        return listaPeriodosOrigen;
    }

    public void setIdTipoPeriodoOrigen(String idTipoPeriodoOrigen) {
        this.idTipoPeriodoOrigen = idTipoPeriodoOrigen;
    }

    public String getIdTipoPeriodoOrigen() {
        return idTipoPeriodoOrigen;
    }

    public void setDdIdTipoDeclaracion(Integer ddIdTipoDeclaracion) {
        this.ddIdTipoDeclaracion = ddIdTipoDeclaracion;
    }

    public Integer getDdIdTipoDeclaracion() {
        return ddIdTipoDeclaracion;
    }

   
    public void setListaOrigenesDeSaldos(List<DyccOrigenSaldoDTO> listaOrigenesDeSaldos) {
        this.listaOrigenesDeSaldos = listaOrigenesDeSaldos;
    }

    public List<DyccOrigenSaldoDTO> getListaOrigenesDeSaldos() {
        return listaOrigenesDeSaldos;
    }

    public void setIsAcreditamiento(Boolean isAcreditamiento) {
        this.isAcreditamiento = isAcreditamiento;
    }

    public Boolean getIsAcreditamiento() {
        return isAcreditamiento;
    }

    public void setIsDevueltoCompensado(Boolean isDevueltoCompensado) {
        this.isDevueltoCompensado = isDevueltoCompensado;
    }

    public Boolean getIsDevueltoCompensado() {
        return isDevueltoCompensado;
    }

    public void setDyccPeriodoOrigenDTO(DyccPeriodoDTO dyccPeriodoOrigenDTO) {
        this.dyccPeriodoOrigenDTO = dyccPeriodoOrigenDTO;
    }

    public DyccPeriodoDTO getDyccPeriodoOrigenDTO() {
        return dyccPeriodoOrigenDTO;
    }

    public void setDyccDiEjercicioDTO(DyccEjercicioDTO dyccDiEjercicioDTO) {
        this.dyccDiEjercicioDTO = dyccDiEjercicioDTO;
    }

    public DyccEjercicioDTO getDyccDiEjercicioDTO() {
        return dyccDiEjercicioDTO;
    }

    public void setDyccConceptoOrigen(DyccConceptoDTO dyccConceptoOrigen) {
        this.dyccConceptoOrigen = dyccConceptoOrigen;
    }

    public DyccConceptoDTO getDyccConceptoOrigen() {
        return dyccConceptoOrigen;
    }

    public void setDycctipoTramiteDTO(DyccTipoTramiteDTO dycctipoTramiteDTO) {
        this.dycctipoTramiteDTO = dycctipoTramiteDTO;
    }

    public DyccTipoTramiteDTO getDycctipoTramiteDTO() {
        return dycctipoTramiteDTO;
    }

    public void setListaTiposDeTramites(List<DyccTipoTramiteDTO> listaTiposDeTramites) {
        this.listaTiposDeTramites = listaTiposDeTramites;
    }

    public List<DyccTipoTramiteDTO> getListaTiposDeTramites() {
        return listaTiposDeTramites;
    }

    public void setListaConceptosOrigen(List<DyccConceptoDTO> listaConceptosOrigen) {
        this.listaConceptosOrigen = listaConceptosOrigen;
    }

    public List<DyccConceptoDTO> getListaConceptosOrigen() {
        return listaConceptosOrigen;
    }

    public void setDescConceptoOrigen(String descConceptoOrigen) {
        this.descConceptoOrigen = descConceptoOrigen;
    }

    public String getDescConceptoOrigen() {
        return descConceptoOrigen;
    }


    public void setNumOperacion(String numOperacion) {
        this.numOperacion = numOperacion;
    }

    public String getNumOperacion() {
        return numOperacion;
    }

    public void setFechaPresentOrigen(String fechaPresentOrigen) {
        this.fechaPresentOrigen = fechaPresentOrigen;
    }

    public String getFechaPresentOrigen() {
        return fechaPresentOrigen;
    }

    public Integer getIdCuadro() {
        return idCuadro;
    }

    public void setIdCuadro(Integer idCuadro) {
        this.idCuadro = idCuadro;
    }
    
     @Override
    public boolean equals(Object o) {
        if (o instanceof CuadroVO) {
            CuadroVO p = (CuadroVO) o;
            return this.descConceptoOrigen.equals(p.descConceptoOrigen);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.idCuadro*descConceptoOrigen.length();
    }

}
