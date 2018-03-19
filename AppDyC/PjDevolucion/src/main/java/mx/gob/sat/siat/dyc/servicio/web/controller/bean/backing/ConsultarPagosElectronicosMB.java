/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.web.controller.bean.backing;

import java.io.FileNotFoundException;
import java.io.Serializable;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;

import mx.gob.sat.siat.dyc.servicio.dto.pagos.ConsultarPagosElectronicosAnioDTO;
import mx.gob.sat.siat.dyc.servicio.dto.pagos.InformacionDePagoInf01DTO;
import mx.gob.sat.siat.dyc.servicio.dto.pagos.InformacionDePagoInf02DTO;
import mx.gob.sat.siat.dyc.servicio.dto.pagos.InformacionDePagoORADTO;
import mx.gob.sat.siat.dyc.servicio.service.pagos.ConsultarPagosElectronicosService;
import mx.gob.sat.siat.dyc.servicio.util.exportador.informe.pagos.ConsultarPagosElectronicosXML;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.errores.ConstantesErrorTextos;

import org.apache.log4j.Logger;


/**
 *
 * @author Alfredo Ramirez
 * @since 18/07/2013
 *
 */
@ManagedBean(name = "consultarPagosElectronicosMB")
@ViewScoped
public class ConsultarPagosElectronicosMB implements Serializable {
    private static final Logger LOG = Logger.getLogger(ConsultarPagosElectronicosMB.class.getName());

    @SuppressWarnings("compatibility:8033668036090842745")
    private static final long serialVersionUID = 1L;

    @ManagedProperty(value = "#{consultarPagosElectronicosService}")
    private transient ConsultarPagosElectronicosService consultarPagosElectronicosService;

    private List<ConsultarPagosElectronicosAnioDTO> pagosAnioArray;
    private List<InformacionDePagoInf01DTO> pagosInf01Array;
    private List<InformacionDePagoInf02DTO> pagosInf02Array;
    private List<InformacionDePagoORADTO> pagosInfOraArray;

    public ConsultarPagosElectronicosMB() {
        pagosAnioArray = new ArrayList<ConsultarPagosElectronicosAnioDTO>();
        pagosInf01Array = new ArrayList<InformacionDePagoInf01DTO>();
        pagosInfOraArray = new ArrayList<InformacionDePagoORADTO>();
    }

    @PostConstruct
    public void init() {
        HttpServletRequest r = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        try {
            r.setCharacterEncoding("ISO-8859-1");
        } catch (Exception e) {
            LOG.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getMessage());
        }
    }

    public void crearXMlConsultaPagosElectronicos() throws FileNotFoundException, ClassNotFoundException,
                                                           SQLException {
        ConsultarPagosElectronicosAnioDTO pagosElectronicoAnioDto = new ConsultarPagosElectronicosAnioDTO();
        pagosElectronicoAnioDto.setTxtrfc("PCM930618MD4");
        pagosElectronicoAnioDto.setNumper(1);
        pagosElectronicoAnioDto.setNumeje(ConstantesDyCNumerico.VALOR_2010);
        pagosElectronicoAnioDto.setCStiCcloanv1(ConstantesDyCNumerico.VALOR_304);
        this.pagosAnioArray = this.consultarPagosElectronicosService.consultaRegistrosPorAnno(pagosElectronicoAnioDto);

        InformacionDePagoInf01DTO informacionDePagoInf01Dto = new InformacionDePagoInf01DTO();
        informacionDePagoInf01Dto.setCStiCcloanv1(ConstantesDyCNumerico.VALOR_101);
        this.pagosInf01Array =
                this.consultarPagosElectronicosService.informacionDePagoInf01(informacionDePagoInf01Dto);

        InformacionDePagoInf02DTO informacionDePagoInf02Dto = new InformacionDePagoInf02DTO();
        informacionDePagoInf02Dto.setCDecCplearv1(ConstantesDyCNumerico.VALOR_41);
        pagosInf02Array = this.consultarPagosElectronicosService.informacionDePagoInf02(informacionDePagoInf02Dto);

        InformacionDePagoORADTO informacionDePagoORADto = new InformacionDePagoORADTO();
        informacionDePagoORADto.setCIdeRfceeog1("TOMA670807PSA");
        informacionDePagoORADto.setCDecCrleanv1(ConstantesDyCNumerico.VALOR_23);
        informacionDePagoORADto.setCDecCplearv1("099");
        informacionDePagoORADto.setNDecEjercic1(ConstantesDyCNumerico.VALOR_2009);
        pagosInfOraArray = this.consultarPagosElectronicosService.informacionDePagoOra(informacionDePagoORADto);
        ConsultarPagosElectronicosXML pagosElectronicos = new ConsultarPagosElectronicosXML();
        pagosElectronicos.setConsultarPagosElectronicosAnioDto(this.pagosAnioArray);
        pagosElectronicos.setInformacionDePagoInf01Dto(this.pagosInf01Array);
        pagosElectronicos.setInformacionDePagoInf02Dto(pagosInf02Array);
        pagosElectronicos.setInformacionDePagoORADto(pagosInfOraArray);
        FacesContext.getCurrentInstance().addMessage(null,
                                                     new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Se creó el XML correctamente."));
    }

    public void setConsultarPagosElectronicosService(ConsultarPagosElectronicosService consultarPagosElectronicosService) {
        this.consultarPagosElectronicosService = consultarPagosElectronicosService;
    }

    public ConsultarPagosElectronicosService getConsultarPagosElectronicosService() {
        return consultarPagosElectronicosService;
    }

    public void setPagosAnioArray(List<ConsultarPagosElectronicosAnioDTO> pagosAnioArray) {
        this.pagosAnioArray = pagosAnioArray;
    }

    public List<ConsultarPagosElectronicosAnioDTO> getPagosAnioArray() {
        return pagosAnioArray;
    }

    public void setPagosInf01Array(List<InformacionDePagoInf01DTO> pagosInf01Array) {
        this.pagosInf01Array = pagosInf01Array;
    }

    public List<InformacionDePagoInf01DTO> getPagosInf01Array() {
        return pagosInf01Array;
    }

    public void setPagosInf02Array(List<InformacionDePagoInf02DTO> pagosInf02Array) {
        this.pagosInf02Array = pagosInf02Array;
    }

    public List<InformacionDePagoInf02DTO> getPagosInf02Array() {
        return pagosInf02Array;
    }

    public void setPagosInfOraArray(List<InformacionDePagoORADTO> pagosInfOraArray) {
        this.pagosInfOraArray = pagosInfOraArray;
    }

    public List<InformacionDePagoORADTO> getPagosInfOraArray() {
        return pagosInfOraArray;
    }
}
