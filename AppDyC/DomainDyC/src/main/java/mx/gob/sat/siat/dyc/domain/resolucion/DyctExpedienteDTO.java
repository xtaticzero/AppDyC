/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.resolucion;

import java.io.InputStream;
import java.io.Serializable;

import java.math.BigDecimal;


/**
 * DTO de la tabla DYCT_EXPEDIENTE
 * @author  Alfredo Ramirez
 * @since   01/04/2014
 */
public class DyctExpedienteDTO implements Serializable {


    @SuppressWarnings("compatibility:-2145981130508637466")
    private static final long serialVersionUID = 1L;

    private DycpServicioDTO servicioDTO;
    private String perfilDeRiesgo;
    private BigDecimal saldoIcep;
    private transient InputStream datosRetenedorBanc;
    private transient InputStream datosCPR;
    private transient InputStream datosDIOT;
    private transient InputStream datosALTEX;
    private transient InputStream datosPagos;
    private transient InputStream datosCompensacion;
    private transient InputStream datosPedimentos;
    private transient InputStream datosDevoluciones;
    private transient InputStream datosDictamenes;
    private transient InputStream datosDeclaraciones;
    private transient InputStream datosConsolidacion;
    private transient InputStream datosPagoDerechos;
    private transient InputStream datosPagoMultas;
    private transient InputStream datosDeterminaISR;
    private transient InputStream datosDeterminaIMP;
    private transient InputStream datosSaldoICEP;
    private transient InputStream datosRetenedorN;
    private BigDecimal saldoRetenedorN;
    private Integer manifiestaEdocta;
    private Integer protesta;
    private Integer infoAgropecuario;
    private Integer aplicaFacilidad;
    private Integer estadoPadron;
    private Integer estadoActual;
    private DyccEdoPadronAgroDTO dyccEdoPadronAgroDTO;

    public DyctExpedienteDTO() {
    }

    public DyctExpedienteDTO(DycpServicioDTO servicioDTO, String perfilDeRiesgo, BigDecimal saldoIcep,
                             BigDecimal saldoRetenedorN, Integer manifiestaEdocta, Integer protesta) {
        this.servicioDTO = servicioDTO;
        this.perfilDeRiesgo = perfilDeRiesgo;
        this.saldoIcep = saldoIcep;
        this.saldoRetenedorN = saldoRetenedorN;
        this.manifiestaEdocta = manifiestaEdocta;
        this.protesta = protesta;
    }

    public void setServicioDTO(DycpServicioDTO servicioDTO) {
        this.servicioDTO = servicioDTO;
    }

    public DycpServicioDTO getServicioDTO() {
        return servicioDTO;
    }

    public void setPerfilDeRiesgo(String perfilDeRiesgo) {
        this.perfilDeRiesgo = perfilDeRiesgo;
    }

    public String getPerfilDeRiesgo() {
        return perfilDeRiesgo;
    }

    public void setSaldoIcep(BigDecimal saldoIcep) {
        this.saldoIcep = saldoIcep;
    }

    public BigDecimal getSaldoIcep() {
        return saldoIcep;
    }

    public void setDatosRetenedorBanc(InputStream datosRetenedorBanc) {
        this.datosRetenedorBanc = datosRetenedorBanc;
    }

    public InputStream getDatosRetenedorBanc() {
        return datosRetenedorBanc;
    }

    public void setDatosCPR(InputStream datosCPR) {
        this.datosCPR = datosCPR;
    }

    public InputStream getDatosCPR() {
        return datosCPR;
    }

    public void setDatosDIOT(InputStream datosDIOT) {
        this.datosDIOT = datosDIOT;
    }

    public InputStream getDatosDIOT() {
        return datosDIOT;
    }

    public void setDatosALTEX(InputStream datosALTEX) {
        this.datosALTEX = datosALTEX;
    }

    public InputStream getDatosALTEX() {
        return datosALTEX;
    }

    public void setDatosPagos(InputStream datosPagos) {
        this.datosPagos = datosPagos;
    }

    public InputStream getDatosPagos() {
        return datosPagos;
    }

    public void setDatosCompensacion(InputStream datosCompensacion) {
        this.datosCompensacion = datosCompensacion;
    }

    public InputStream getDatosCompensacion() {
        return datosCompensacion;
    }

    public void setDatosPedimentos(InputStream datosPedimentos) {
        this.datosPedimentos = datosPedimentos;
    }

    public InputStream getDatosPedimentos() {
        return datosPedimentos;
    }

    public void setDatosDevoluciones(InputStream datosDevoluciones) {
        this.datosDevoluciones = datosDevoluciones;
    }

    public InputStream getDatosDevoluciones() {
        return datosDevoluciones;
    }

    public void setDatosDictamenes(InputStream datosDictamenes) {
        this.datosDictamenes = datosDictamenes;
    }

    public InputStream getDatosDictamenes() {
        return datosDictamenes;
    }

    public void setDatosDeclaraciones(InputStream datosDeclaraciones) {
        this.datosDeclaraciones = datosDeclaraciones;
    }

    public InputStream getDatosDeclaraciones() {
        return datosDeclaraciones;
    }

    public void setDatosConsolidacion(InputStream datosConsolidacion) {
        this.datosConsolidacion = datosConsolidacion;
    }

    public InputStream getDatosConsolidacion() {
        return datosConsolidacion;
    }

    public void setDatosPagoDerechos(InputStream datosPagoDerechos) {
        this.datosPagoDerechos = datosPagoDerechos;
    }

    public InputStream getDatosPagoDerechos() {
        return datosPagoDerechos;
    }

    public void setDatosPagoMultas(InputStream datosPagoMultas) {
        this.datosPagoMultas = datosPagoMultas;
    }

    public InputStream getDatosPagoMultas() {
        return datosPagoMultas;
    }

    public void setDatosDeterminaISR(InputStream datosDeterminaISR) {
        this.datosDeterminaISR = datosDeterminaISR;
    }

    public InputStream getDatosDeterminaISR() {
        return datosDeterminaISR;
    }

    public void setDatosDeterminaIMP(InputStream datosDeterminaIMP) {
        this.datosDeterminaIMP = datosDeterminaIMP;
    }

    public InputStream getDatosDeterminaIMP() {
        return datosDeterminaIMP;
    }

    public void setDatosSaldoICEP(InputStream datosSaldoICEP) {
        this.datosSaldoICEP = datosSaldoICEP;
    }

    public InputStream getDatosSaldoICEP() {
        return datosSaldoICEP;
    }

    public void setDatosRetenedorN(InputStream datosRetenedorN) {
        this.datosRetenedorN = datosRetenedorN;
    }

    public InputStream getDatosRetenedorN() {
        return datosRetenedorN;
    }

    public void setSaldoRetenedorN(BigDecimal saldoRetenedorN) {
        this.saldoRetenedorN = saldoRetenedorN;
    }

    public BigDecimal getSaldoRetenedorN() {
        return saldoRetenedorN;
    }

    public void setManifiestaEdocta(Integer manifiestaEdocta) {
        this.manifiestaEdocta = manifiestaEdocta;
    }

    public Integer getManifiestaEdocta() {
        return manifiestaEdocta;
    }

    public void setProtesta(Integer protesta) {
        this.protesta = protesta;
    }

    public Integer getProtesta() {
        return protesta;
    }

    public void setInfoAgropecuario(Integer infoAgropecuario) {
        this.infoAgropecuario = infoAgropecuario;
    }

    public Integer getInfoAgropecuario() {
        return infoAgropecuario;
    }

    public void setAplicaFacilidad(Integer aplicaFacilidad) {
        this.aplicaFacilidad = aplicaFacilidad;
    }

    public Integer getAplicaFacilidad() {
        return aplicaFacilidad;
    }

    public void setEstadoPadron(Integer estadoPadron) {
        this.estadoPadron = estadoPadron;
    }

    public Integer getEstadoPadron() {
        return estadoPadron;
    }

    public void setDyccEdoPadronAgroDTO(DyccEdoPadronAgroDTO dyccEdoPadronAgroDTO) {
        this.dyccEdoPadronAgroDTO = dyccEdoPadronAgroDTO;
    }

    public DyccEdoPadronAgroDTO getDyccEdoPadronAgroDTO() {
        return dyccEdoPadronAgroDTO;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getClass().getName());
        buffer.append("@");
        buffer.append(Integer.toHexString(hashCode()));
        buffer.append('[');
        buffer.append("perfilDeRiesgo=");
        buffer.append(getPerfilDeRiesgo());
        buffer.append(',');
        buffer.append("saldoIcep=");
        buffer.append(getSaldoIcep());
        buffer.append(',');
        buffer.append("saldoRetenedorN=");
        buffer.append(getSaldoRetenedorN());
        buffer.append(',');
        buffer.append("manifiestaEdocta=");
        buffer.append(getManifiestaEdocta());
        buffer.append(',');
        buffer.append("protesta=");
        buffer.append(getProtesta());
        buffer.append(',');
        buffer.append("infoAgropecuario=");
        buffer.append(getInfoAgropecuario());
        buffer.append(',');
        buffer.append("aplicaFacilidad=");
        buffer.append(getAplicaFacilidad());
        buffer.append(',');
        buffer.append("estadoPadron=");
        buffer.append(getEstadoPadron());
        buffer.append(',');
        buffer.append("estadoActual=");
        buffer.append(getEstadoActual());
        buffer.append(']');
        return buffer.toString();
    }


    public void setEstadoActual(Integer estadoActual) {
        this.estadoActual = estadoActual;
    }

    public Integer getEstadoActual() {
        return estadoActual;
    }
}
