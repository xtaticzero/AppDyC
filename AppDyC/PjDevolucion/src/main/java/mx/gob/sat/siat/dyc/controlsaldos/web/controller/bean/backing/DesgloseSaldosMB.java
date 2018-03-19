package mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.backing;

import java.math.BigDecimal;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import mx.gob.sat.siat.dyc.controlsaldos.service.icep.impl.DesgloseSaldosServiceImpl;
import mx.gob.sat.siat.dyc.controlsaldos.vo.MovimientoSaldoBean;
import mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.utility.CuadroSaldoAFavorBean;

import org.apache.log4j.Logger;


@ManagedBean(name = "mbDesgloseSaldos")
@ViewScoped
public class DesgloseSaldosMB
{
    private static final Logger LOG = Logger.getLogger(DesgloseSaldosMB.class);

    private List<CuadroSaldoAFavorBean> cuadros;

    @ManagedProperty ("#{serviceDesgloseSaldos}")
    private DesgloseSaldosServiceImpl service;

    @ManagedProperty(value = "#{sesionControlSaldos}")
    private ManagerSesionControlSaldosMB mbSession;

    private BigDecimal remanenteSIR;

    private List<MovimientoSaldoBean> saldosNegativos;

    @PostConstruct
    public void inicializar()
    {
        LOG.debug("INICIO inicializar ->" + getMbSession().getIdSaldoIcep());
        Map<String, Object> infoDesglose = service.obtenerCuadrosSAF(getMbSession().getIdSaldoIcep());
        cuadros = (List<CuadroSaldoAFavorBean>)infoDesglose.get("cuadrosSAF");
        saldosNegativos = (List<MovimientoSaldoBean>)infoDesglose.get("saldosNegativos");
        
        Map<String, String> parameterMap = (Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Double remanenteFavCargo = Double.valueOf(parameterMap.get("remanenteFavCargo"));
        
        remanenteSIR = BigDecimal.valueOf(remanenteFavCargo);
        LOG.debug("numero de cuadros ->" + cuadros.size() + "<-");
    }

    public String regresar() {
        return "detalleIcep";
    }

    public List<CuadroSaldoAFavorBean> getCuadros() {
        return cuadros;
    }

    public void setCuadros(List<CuadroSaldoAFavorBean> cuadros) {
        this.cuadros = cuadros;
    }

    public DesgloseSaldosServiceImpl getService() {
        return service;
    }

    public void setService(DesgloseSaldosServiceImpl service) {
        this.service = service;
    }

    public ManagerSesionControlSaldosMB getMbSession() {
        return mbSession;
    }

    public void setMbSession(ManagerSesionControlSaldosMB mbSession) {
        this.mbSession = mbSession;
    }

    public BigDecimal getRemanenteSIR() {
        return remanenteSIR;
    }

    public void setRemanenteSIR(BigDecimal remanenteSIR) {
        this.remanenteSIR = remanenteSIR;
    }

    public List<MovimientoSaldoBean> getSaldosNegativos() {
        return saldosNegativos;
    }

    public void setSaldosNegativos(List<MovimientoSaldoBean> saldosNegativos) {
        this.saldosNegativos = saldosNegativos;
    }
    
}
