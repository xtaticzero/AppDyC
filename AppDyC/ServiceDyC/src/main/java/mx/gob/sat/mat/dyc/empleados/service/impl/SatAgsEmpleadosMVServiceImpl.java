/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.mat.dyc.empleados.service.impl;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import mx.gob.sat.mat.dyc.empleados.service.SatAgsEmpleadosMVService;
import mx.gob.sat.siat.dyc.dao.ags.SatAgsEmpleadosMVDAO;
import mx.gob.sat.siat.dyc.domain.ags.SatAgsEmpleadosMVDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC;

import org.apache.log4j.Logger;

/**import org.primefaces.context.RequestContext;*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author RRAL
 */
@Service("satAgsEmpleadosMVService")
public class SatAgsEmpleadosMVServiceImpl implements SatAgsEmpleadosMVService {

    private static final Logger LOGGER = Logger.getLogger(SatAgsEmpleadosMVServiceImpl.class);

    private static final String MENSAJE_ERROR_VALIDACION_APROBADOR = "El aprobador seleccionado está Inactivo o causo baja en AGS, seleccione otro aprobador.";

    @Autowired
    private SatAgsEmpleadosMVDAO satAgsEmpleadosMVDAO;

    @Override
    public boolean getEstatusEmpleadoActivo(Object param) throws SIATException {
        String estado = satAgsEmpleadosMVDAO.getEstatusEmpleado(param, isNumeric(param));
        LOGGER.info("estatus del empleado -->" + estado);
        if (estado == null || estado.equals("")) {
            return false;
        } else {
            return estado.equals(ConstantesDyC.ESTADO_EMPLEADO_ACTIVO) ? Boolean.TRUE : false;
        }

    }

    @Override
    public String getEstatusEmpleado(Object param) throws SIATException {
        String estado = satAgsEmpleadosMVDAO.getEstatusEmpleado(param, isNumeric(param));
        LOGGER.info("estatus del empleado -->" + estado);
        return estado;
    }

    @Override
    public SatAgsEmpleadosMVDTO getEmpleadoAGS(Object param) throws SIATException {
        return satAgsEmpleadosMVDAO.getEmpleadoAGS(param, isNumeric(param));
    }

    private boolean isNumeric(Object param) {
        String numoRfc = param.toString();
        try {
            Integer.parseInt(numoRfc);
            return Boolean.TRUE;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    @Override
    public boolean aprobadorValido(Integer numEmpAprobador) {
        try {
            return getEstatusEmpleadoActivo(numEmpAprobador);

        } catch (SIATException ex) {
            LOGGER.info("Error al validar al aprobador : " + numEmpAprobador + " " + ex.getDescripcion());
        }

        return false;
    }

    @Override
    public void muestraMensajeAprobadorNoValido() {
        FacesMessage mensajeProceso = getMensajeErrorValidacionAprobador();
        muestraMensaje(null, mensajeProceso);
    }

    /**@Override
    public void ejecutaJs(String cadenaJs) {
        RequestContext.getCurrentInstance().execute(cadenaJs);
    }*/

    @Override
    public void muestraMensajeAprobadorNoValido(String destino) {
        FacesMessage mensajeProceso = getMensajeErrorValidacionAprobador();
        muestraMensaje(destino, mensajeProceso);
    }

    private FacesMessage getMensajeErrorValidacionAprobador() {
        return new FacesMessage(FacesMessage.SEVERITY_ERROR, MENSAJE_ERROR_VALIDACION_APROBADOR, "");
    }

    private void muestraMensaje(String destino, FacesMessage mensaje) {
        FacesContext.getCurrentInstance().addMessage(destino, mensaje);
    }

}
