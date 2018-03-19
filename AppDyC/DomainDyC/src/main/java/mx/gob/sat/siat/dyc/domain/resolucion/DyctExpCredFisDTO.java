package mx.gob.sat.siat.dyc.domain.resolucion;

import java.io.InputStream;
import java.io.Serializable;


public class DyctExpCredFisDTO implements Serializable {


    @SuppressWarnings("compatibility:-7562164500333705347")
    private static final long serialVersionUID = 1L;

    private DyctExpedienteDTO dyctExpedienteDTO;
    private transient InputStream datosCredFis;

    public DyctExpCredFisDTO() {
        super();
    }

    public void setDyctExpedienteDTO(DyctExpedienteDTO dyctExpedienteDTO) {
        this.dyctExpedienteDTO = dyctExpedienteDTO;
    }

    public DyctExpedienteDTO getDyctExpedienteDTO() {
        return dyctExpedienteDTO;
    }


    public void setDatosCredFis(InputStream datosCredFis) {
        this.datosCredFis = datosCredFis;
    }

    public InputStream getDatosCredFis() {
        return datosCredFis;
    }
}
