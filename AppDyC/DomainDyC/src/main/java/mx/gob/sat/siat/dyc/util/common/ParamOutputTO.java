package mx.gob.sat.siat.dyc.util.common;

import java.io.Serializable;

import java.util.List;


/**
 * JEFG
 * Clase que representa un Transfer Object para los parametros de salida de los
 * servicios
 *
 * */
public class ParamOutputTO<T> implements Serializable {

    /** Version del objeto serializable */
    @SuppressWarnings("compatibility:769017099211161076")
    private static final long serialVersionUID = 1L;

    private T output;
    private List<T> outputs;

    /** Constructor por omision */
    public ParamOutputTO() {
    }

    public ParamOutputTO(T output) {
        this.output = output;
    }

    public ParamOutputTO(List<T> outputs) {
        super();
        this.outputs = outputs;
    }

    public List<T> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<T> outputs) {
        this.outputs = outputs;
    }

    public T getOutput() {
        return output;
    }

    public void setOutput(T output) {
        this.output = output;
    }
}
