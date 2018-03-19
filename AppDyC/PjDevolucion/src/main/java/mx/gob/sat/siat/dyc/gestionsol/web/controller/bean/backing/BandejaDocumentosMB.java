package mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.backing;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.dyc.admcat.service.matrizaprobador.AprobadorService;
import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;
import mx.gob.sat.siat.dyc.generico.util.calculo.CalcularFechasService;
import mx.gob.sat.siat.dyc.generico.util.common.AbstractPage;
import mx.gob.sat.siat.dyc.generico.util.common.SIATDataModel;
import mx.gob.sat.siat.dyc.gestionsol.service.administrarsolicitudes.AdministrarSolicitudesService;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.vo.DocumentoAdministrarSolVO;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;


/**
 * @author Federico Chopin Gachuz
 * */
@ManagedBean(name = "bandejaDocumentosMB")
@SessionScoped
public class BandejaDocumentosMB extends AbstractPage {

    private static final Logger LOG = Logger.getLogger(BandejaDocumentosMB.class);

    @ManagedProperty(value = "#{administrarSolicitudesService}")
    private AdministrarSolicitudesService administrarSolicitudesService;

    @ManagedProperty(value = "#{calcularFechasService}")
    private CalcularFechasService calcularFechasService;

    @ManagedProperty(value = "#{aprobadorService}")
    private AprobadorService aprobadorService;

    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    private List<DocumentoAdministrarSolVO> listaDocsDictaminador = new ArrayList<DocumentoAdministrarSolVO>();

    private List<DyccAprobadorDTO> listaJefesSup = new ArrayList<DyccAprobadorDTO>();

    private DocumentoAdministrarSolVO documentoAdministrarSolVO;
    private boolean varBotonCon;
    private boolean varBotonApr;
    private String etiquetaMantenerDoc;
    private String numEmpleadoStr = "";
    private AccesoUsr accesoUsr;
    private StreamedContent fileDocumentos;

    private int idUnidad;
    private int centroCosto;
    private Integer numEmp;


    @PostConstruct
    public void init() {

        numEmp = 0;

        this.accesoUsr = serviceObtenerSesion.execute();

        numEmpleadoStr = accesoUsr.getNumeroEmp();

        idUnidad = Integer.parseInt(this.accesoUsr.getClaveSir());
        centroCosto = Integer.parseInt(this.accesoUsr.getCentroCosto());

        setDataModel(new SIATDataModel());

        varBotonCon = Boolean.TRUE;
        varBotonApr = Boolean.TRUE;

        listaDocsDictaminador = administrarSolicitudesService.buscarDocsDictaminador(numEmpleadoStr);

        if (!listaDocsDictaminador.isEmpty()) {
            for (DocumentoAdministrarSolVO objeto : listaDocsDictaminador) {
                objeto.setFechaLimite(calculoFecha(objeto));
            }
        }

        getDataModel().setWrappedData(listaDocsDictaminador);

    }

    public void accionGuardarJefes() {

        Date fechaLimite = null;

        try {
            administrarSolicitudesService.actualizarDocumento(documentoAdministrarSolVO.getNumControlDoc(), numEmp);

            FacesContext.getCurrentInstance().addMessage(null,
                                                         new FacesMessage(FacesMessage.SEVERITY_INFO, "El documento se mando a aprobación correctamente",
                                                                          null));

            listaDocsDictaminador = administrarSolicitudesService.buscarDocsDictaminador(numEmpleadoStr);
            if (!listaDocsDictaminador.isEmpty()) {

                for (DocumentoAdministrarSolVO objeto : listaDocsDictaminador) {

                    fechaLimite = calculoFecha(objeto);
                    objeto.setFechaLimite(fechaLimite);

                }

            }
            getDataModel().setWrappedData(listaDocsDictaminador);


        } catch (SIATException e) {
            LOG.error("ERROR: " + e.getMessage());

            FacesContext.getCurrentInstance().addMessage(null,
                                                         new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocurrio un error al intentar mandar el documento a aprobación",
                                                                          null));
        }

        RequestContext.getCurrentInstance().execute("dlgJefesSup.hide();");


    }

    public void enviarAprob() {

        if (documentoAdministrarSolVO.getIdEstadoDoc() == ConstantesDyCNumerico.VALOR_6) {

            listaJefesSup = this.aprobadorService.consultarAprobadores(idUnidad, centroCosto);
            RequestContext.getCurrentInstance().execute("dlgJefesSup.show();");

        }

    }

    public Date calculoFecha(DocumentoAdministrarSolVO objeto) {

        Date fechaLimite = null;

        try {

            fechaLimite =
                    calcularFechasService.calcularFechaFinal(objeto.getFechaPresentacion(), objeto.getDias(), objeto.getTipoDia());

            return fechaLimite;

        } catch (Exception e) {
            LOG.error("ERROR: " + e.getMessage());
        }

        return fechaLimite;
    }

    public void downloadDocumentos() {

        StringBuilder ruta = new StringBuilder();

        ruta.append(documentoAdministrarSolVO.getUrl().concat("/"));
        ruta.append(documentoAdministrarSolVO.getNombreDocumento());

        File tempFile = new File(ruta.toString());

        byte[] contenido = null;
        try {
            contenido = IOUtils.toByteArray(tempFile.toURI());
        } catch (IOException e) {
            LOG.error("ERROR: " + e.getMessage());
        }

        fileDocumentos =
                new DefaultStreamedContent(new ByteArrayInputStream(contenido), new MimetypesFileTypeMap().getContentType(tempFile),
                                           documentoAdministrarSolVO.getNombreDocumento());

    }

    public void onRowSelect() {
        varBotonCon = false;
        varBotonApr = false;
    }

    public void setVarBotonCon(boolean varBotonCon) {
        this.varBotonCon = varBotonCon;
    }

    public boolean isVarBotonCon() {
        return varBotonCon;
    }

    public void setEtiquetaMantenerDoc(String etiquetaMantenerDoc) {
        this.etiquetaMantenerDoc = etiquetaMantenerDoc;
    }

    public String getEtiquetaMantenerDoc() {
        return etiquetaMantenerDoc;
    }

    public void setNumEmpleadoStr(String numEmpleadoStr) {
        this.numEmpleadoStr = numEmpleadoStr;
    }

    public String getNumEmpleadoStr() {
        return numEmpleadoStr;
    }

    public void setAccesoUsr(AccesoUsr accesoUsr) {
        this.accesoUsr = accesoUsr;
    }

    public AccesoUsr getAccesoUsr() {
        return accesoUsr;
    }

    public void setListaJefesSup(List<DyccAprobadorDTO> listaJefesSup) {
        this.listaJefesSup = listaJefesSup;
    }

    public List<DyccAprobadorDTO> getListaJefesSup() {
        return listaJefesSup;
    }

    public void setAdministrarSolicitudesService(AdministrarSolicitudesService administrarSolicitudesService) {
        this.administrarSolicitudesService = administrarSolicitudesService;
    }

    public AdministrarSolicitudesService getAdministrarSolicitudesService() {
        return administrarSolicitudesService;
    }

    public void setCalcularFechasService(CalcularFechasService calcularFechasService) {
        this.calcularFechasService = calcularFechasService;
    }

    public CalcularFechasService getCalcularFechasService() {
        return calcularFechasService;
    }

    public void setFileDocumentos(StreamedContent fileDocumentos) {
        this.fileDocumentos = fileDocumentos;
    }

    public StreamedContent getFileDocumentos() {
        return fileDocumentos;
    }

    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }

    public int getIdUnidad() {
        return idUnidad;
    }

    

    public void setListaDocsDictaminador(List<DocumentoAdministrarSolVO> listaDocsDictaminador) {
        this.listaDocsDictaminador = listaDocsDictaminador;
    }

    public List<DocumentoAdministrarSolVO> getListaDocsDictaminador() {
        return listaDocsDictaminador;
    }

    public void setDocumentoAdministrarSolVO(DocumentoAdministrarSolVO documentoAdministrarSolVO) {
        this.documentoAdministrarSolVO = documentoAdministrarSolVO;
    }

    public DocumentoAdministrarSolVO getDocumentoAdministrarSolVO() {
        return documentoAdministrarSolVO;
    }

    public void setVarBotonApr(boolean varBotonApr) {
        this.varBotonApr = varBotonApr;
    }

    public boolean isVarBotonApr() {
        return varBotonApr;
    }

    public void setNumEmp(Integer numEmp) {
        this.numEmp = numEmp;
    }

    public Integer getNumEmp() {
        return numEmp;
    }

    public void setAprobadorService(AprobadorService aprobadorService) {
        this.aprobadorService = aprobadorService;
    }

    public AprobadorService getAprobadorService() {
        return aprobadorService;
    }

    public void setCentroCosto(int centroCosto) {
        this.centroCosto = centroCosto;
    }

    public int getCentroCosto() {
        return centroCosto;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }
}
