package mx.gob.sat.siat.dyc.trabajo.web.controller.bean.backing;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import mx.gob.sat.siat.dyc.trabajo.service.impl.ExtractorInfo2BDelegate;

import org.apache.log4j.Logger;


@ManagedBean(name = "mbExtracInfo2")
@SessionScoped
public class ExtractorInfo2MB
{
    private static final Logger LOG = Logger.getLogger(ExtractorInfo2MB.class.getName());
    
    private Integer idSaldoIcep;
    private String infoExtraida;

    @ManagedProperty(value = "#{bdExtractorInfo2}")
    private ExtractorInfo2BDelegate delegate;
    
    public void extraerInfo()
    {
        LOG.debug("idSaldoIcep ->" + idSaldoIcep);
        setInfoExtraida(delegate.extraerInfo(idSaldoIcep));
    }

    public Integer getIdSaldoIcep() {
        return idSaldoIcep;
    }

    public void setIdSaldoIcep(Integer idSaldoIcep) {
        this.idSaldoIcep = idSaldoIcep;
    }

    public ExtractorInfo2BDelegate getDelegate() {
        return delegate;
    }

    public void setDelegate(ExtractorInfo2BDelegate delegate) {
        this.delegate = delegate;
    }

    public String getInfoExtraida() {
        return infoExtraida;
    }

    public void setInfoExtraida(String infoExtraida) {
        this.infoExtraida = infoExtraida;
    }
}
