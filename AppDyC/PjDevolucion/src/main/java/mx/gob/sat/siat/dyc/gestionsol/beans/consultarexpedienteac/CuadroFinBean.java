package mx.gob.sat.siat.dyc.gestionsol.beans.consultarexpedienteac;

public class CuadroFinBean {
    private CuadroBean cuadro;
    private CuadroDeclaracionBean cuadroDeclaracionC;
    private CuadroDeclaracionBean cuadroDeclaracionN;

    public void setCuadro(CuadroBean cuadro) {
        this.cuadro = cuadro;
    }

    public CuadroBean getCuadro() {
        return cuadro;
    }

    public void setCuadroDeclaracionC(CuadroDeclaracionBean cuadroDeclaracionC) {
        this.cuadroDeclaracionC = cuadroDeclaracionC;
    }

    public CuadroDeclaracionBean getCuadroDeclaracionC() {
        return cuadroDeclaracionC;
    }

    public void setCuadroDeclaracionN(CuadroDeclaracionBean cuadroDeclaracionN) {
        this.cuadroDeclaracionN = cuadroDeclaracionN;
    }

    public CuadroDeclaracionBean getCuadroDeclaracionN() {
        return cuadroDeclaracionN;
    }
}
