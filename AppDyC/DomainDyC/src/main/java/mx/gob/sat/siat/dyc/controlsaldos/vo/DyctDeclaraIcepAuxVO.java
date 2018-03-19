package mx.gob.sat.siat.dyc.controlsaldos.vo;

import java.io.Serializable;

import java.lang.reflect.InvocationTargetException;

import java.math.BigDecimal;

import mx.gob.sat.siat.dyc.vo.DyctDeclaraIcepAuxDTO;

import org.apache.commons.beanutils.BeanUtils;


public class DyctDeclaraIcepAuxVO extends DyctDeclaraIcepAuxDTO implements Serializable {


    @SuppressWarnings("compatibility:-8424299181645341230")
    private static final long serialVersionUID = -2633626236833999551L;

    private BigDecimal montoUsado;
    
    private boolean declaracionUsada = false;
    
    private boolean safMenor = false;
    
    private double safAct;

    
    private double saldoAfavorNuevo;
    
    private double montoCargo;

    public DyctDeclaraIcepAuxVO() {
    }

    public DyctDeclaraIcepAuxVO(DyctDeclaraIcepAuxDTO dyctDeclaraIcepDTO) {
        try {

            BeanUtils.copyProperties(this, dyctDeclaraIcepDTO);

        } catch (IllegalAccessException e) {
            e.getMessage();
        } catch (InvocationTargetException e) {
            e.getMessage();
        }
    }


    public void setDeclaracionUsada(boolean declaracionUsada) {
        this.declaracionUsada = declaracionUsada;
    }

    public boolean esDeclaracionUsada() {
        return declaracionUsada;
    }


    public void setSAFMenor(boolean safMenor) {
        this.safMenor = safMenor;
    }

    public boolean esSAFMenor() {
        return safMenor;
    }


    public void setSafAct(double safAct) {
        this.safAct = safAct;
    }

    public double getSafAct() {
        return safAct;
    }


    public void setSaldoAfavorNuevo(double saldoAfavorNuevo) {
        this.saldoAfavorNuevo = saldoAfavorNuevo;
    }

    public double getSaldoAfavorNuevo() {
        return saldoAfavorNuevo;
    }

    public boolean isDeclaracionUsada() {
        return declaracionUsada;
    }

    public boolean isSafMenor() {
        return safMenor;
    }

    public void setMontoCargo(double montoCargo) {
        this.montoCargo = montoCargo;
    }

    public double getMontoCargo() {
        return montoCargo;
    }


    public void setMontoUsado(BigDecimal montoUsado) {
        this.montoUsado = montoUsado;
    }

    public BigDecimal getMontoUsado() {
        return montoUsado;
    }
}
