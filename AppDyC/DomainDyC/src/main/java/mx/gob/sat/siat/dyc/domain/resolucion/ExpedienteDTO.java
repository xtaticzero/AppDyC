/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.domain.resolucion;

import java.io.InputStream;


/**
 * DTO para insertcion Expediente
 * @author Luis Fernando Barrios Quiroz [ LuFerMX ]
 * @actualizacion Israel Chavez ISCC 29-08-2013
 * @actualizacion Luis Fernando Barrios Quiroz [ LuFerMX ] 06-11-2013
 *
 **/

public class ExpedienteDTO {
    public ExpedienteDTO() {
        super();
    }

    private String numControl;
    private int idTipoServicio;
    private String perfilDeRiesgo;
    private Double saldoicep;
    private InputStream datosRetenedorBanc;
    private InputStream datosCPR;
    private InputStream datosDIOT;
    private InputStream datosALTEX;
    private InputStream datosPagos;
    private InputStream datosCompensacion;
    private InputStream datosCredFis;
    private InputStream datosPedimentos;
    private InputStream datosDevoluciones;
    private InputStream datosDictamenes;
    private InputStream datosDeclaraciones;
    private InputStream datosConsolidacion;
    private InputStream datosPagoDerechos;
    private InputStream datosPagoMultas;
    private InputStream datosDeterminaISR;
    private InputStream datosDeterminaIMP;
    private InputStream datosSaldoICEP;
    private InputStream datosRetenedorN;
    private Double saldoRetenedorN;

    // TODO: ACCESSORS *****************************************************

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setIdTipoServicio(int idTipoServicio) {
        this.idTipoServicio = idTipoServicio;
    }

    public int getIdTipoServicio() {
        return idTipoServicio;
    }

    public void setPerfilDeRiesgo(String perfilDeRiesgo) {
        this.perfilDeRiesgo = perfilDeRiesgo;
    }

    public String getPerfilDeRiesgo() {
        return perfilDeRiesgo;
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

    public void setDatosCredFis(InputStream datosCredFis) {
        this.datosCredFis = datosCredFis;
    }

    public InputStream getDatosCredFis() {
        return datosCredFis;
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

    public void setSaldoicep(Double saldoicep) {
        this.saldoicep = saldoicep;
    }

    public Double getSaldoicep() {
        return saldoicep;
    }

    public void setDatosRetenedorN(InputStream datosRetenedorN) {
        this.datosRetenedorN = datosRetenedorN;
    }

    public InputStream getDatosRetenedorN() {
        return datosRetenedorN;
    }

    public void setSaldoRetenedorN(Double saldoRetenedorN) {
        this.saldoRetenedorN = saldoRetenedorN;
    }

    public Double getSaldoRetenedorN() {
        return saldoRetenedorN;
    }
}
