package mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.backing;

import java.io.IOException;
import java.io.InputStreamReader;

import java.math.BigDecimal;

import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import mx.gob.sat.mat.dyc.empleados.service.SatAgsEmpleadosMVService;
import mx.gob.sat.siat.dyc.admcat.service.matrizdictaminadores.DictaminadorService;
import mx.gob.sat.siat.dyc.domain.archivo.DyctArchivoDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaIdentificacionDTO;
import mx.gob.sat.siat.dyc.domain.declaracion.DyctFacultadesDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctContribuyenteDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;
import mx.gob.sat.siat.dyc.generico.util.ArchivoCargaDescarga;
import mx.gob.sat.siat.dyc.generico.util.Mensaje;
import mx.gob.sat.siat.dyc.generico.util.common.Utilerias;
import mx.gob.sat.siat.dyc.gestionsol.service.administrarsolicitudes.AdministrarSolicitudesService;
import mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.backing.consultarexpediente.DyCConsultarExpedienteMB;
import mx.gob.sat.siat.dyc.registro.service.contribuyente.ConsultarExpedienteService;
import mx.gob.sat.siat.dyc.registro.service.solicitud.ReasignarManSolicDevolucionyCasosCompService;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesAdministrarSolicitud;
import mx.gob.sat.siat.dyc.util.constante.ConstantesArchivo;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyC;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCURL;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.vo.DictaminadorSolBean;
import mx.gob.sat.siat.dyc.vo.DictaminadorVO;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;


/**
 * Managed Bean para consultar expediente
 * @author Federico Chopin
 * @date  23/04/2015
 */
@ManagedBean(name = "iniciarFacultadesMB")
@SessionScoped
public class IniciarFacultadesMB {

    private static final Logger LOG = Logger.getLogger(IniciarFacultadesMB.class.getName());

    @ManagedProperty("#{consultarExpedienteService}")
    private ConsultarExpedienteService consultarExpedienteService;

    @ManagedProperty(value = "#{dyCConsultarExpedienteMB}")
    private DyCConsultarExpedienteMB dyCConsultarExpedienteMB;

    @ManagedProperty(value = "#{administrarSolicitudesService}")
    private AdministrarSolicitudesService administrarSolicitudesService;

    @ManagedProperty("#{dictaminadorService}")
    private DictaminadorService dictaminadorService;

    @ManagedProperty("#{satAgsEmpleadosMVService}")
    private SatAgsEmpleadosMVService validacionAgs;
    
    @ManagedProperty(value = "#{reasignarManSolicDevolucionyCasosCompService}")
    private ReasignarManSolicDevolucionyCasosCompService reasignacionTramites;

    private DyctContribuyenteDTO dyctContribuyenteDTO;
    private PersonaDTO personaDTO;
    private PersonaIdentificacionDTO personaIdentificacion;

    private String numControl;
    private int claveAdm;
    private String rfcContribuyente;
    private BigDecimal importeSolicitado;
    private String fechaStr;
    private String folioFacultades;
    private boolean btnDocFacultades;
    private boolean ocultarFechaFinal;
    private Mensaje mensaje;
    private String folio;
    private boolean validarFolio;

    private Date valorFechaFinal;

    private Date valorFechaInicio;

    private UploadedFile file;
    private boolean dialogFacultades;
    private ArchivoCargaDescarga archivo;
    private boolean btnAceptar;
    private String nombreArchivo;
    private Date fechaInicio;
    
    private Date fechaPresentacion;

    private String etiBoton;

    private boolean validacionFechaInicio1;
    private boolean validacionFechaInicio2;

    private Date fechaActualInicio;
    
    private String nombreDocumento = "";

    private String nombreDictaminador = "";
    private String numEmpDictaminador = "";

    private DictaminadorVO dictaminadorSeleccionado = null;
    private List<DictaminadorVO> listaDictaminadoresReasignacion = new ArrayList<DictaminadorVO>();

    private static final String MOSTRAR_OPCION_REASIGNAR_DICTAMINADOR = "wvDlgOpcionReasignarFacultades.show();";
    private static final String OCULTAR_OPCION_REASIGNAR_DICTAMINADOR = "wvDlgOpcionReasignarFacultades.hide();";

    private static final String MOSTRAR_LISTADO_DICTAMINADORES = "wvDlgReasignacionFacultades.show();";
    private static final String OCULTAR_LISTADO_DICTAMINADORES = "wvDlgReasignacionFacultades.hide();";

    private static final String MENSAJE_DICTAMINADOR_NO_VALIDO = "El dictaminador seleccionado está Inactivo o causo baja en AGS, seleccione otro aprobador.";
    private static final String MENSAJE_DICTAMINADOR_NO_SELECCIONADO = "El dictaminador seleccionado está Inactivo o causo baja en AGS, seleccione otro aprobador.";
    
    private static final String NO_REDIRECCIONAMIENTO = "";

    private boolean confirmacionReasignacion = false;
    private boolean realizarReasignacionDictaminador = false;
    private int claveAdmon = 0;

    public void init() {

        fechaActualInicio = new Date();


        if (null != folio) {
            etiBoton = "Fin facultades";
        } else {
            etiBoton = "Inicio facultades";
        }

        archivo = new ArchivoCargaDescarga();
        mensaje = new Mensaje();
        btnDocFacultades = Boolean.TRUE;
        ocultarFechaFinal = false;
        validarFolio = false;
        dialogFacultades = false;
        btnAceptar = false;

        SimpleDateFormat formateadorFecha = new SimpleDateFormat("dd/MM/yyyy");

        if (null != fechaInicio) {
            validacionFechaInicio1 = Boolean.TRUE;
            validacionFechaInicio2 = false;
            fechaStr = formateadorFecha.format(fechaInicio);
        } else {
            validacionFechaInicio2 = Boolean.TRUE;
            validacionFechaInicio1 = false;
        }

        dyctContribuyenteDTO = consultarExpedienteService.buscarNumcontrol(numControl);
        
        try {

            if (null != dyctContribuyenteDTO && null != dyctContribuyenteDTO.getDatosContribuyente()) {
                JAXBContext jaxbContext = JAXBContext.newInstance(PersonaDTO.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                InputStreamReader datosContribuyente = new InputStreamReader( dyctContribuyenteDTO.getDatosContribuyente(), ConstantesDyC1.CODIFICACION_UTF8 );
                personaDTO = (PersonaDTO)jaxbUnmarshaller.unmarshal( datosContribuyente );
            }

            if (null != personaDTO && null != personaDTO.getPersonaIdentificacion()) {
                personaIdentificacion = personaDTO.getPersonaIdentificacion();
            }

        } catch (JAXBException e) {
            LOG.error(e.getMessage());
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }

    }

    public void validarInicioFacultades() {

        if (null != folio && !folio.isEmpty()) {

            btnDocFacultades = false;
            ocultarFechaFinal = Boolean.TRUE;

            dialogFacultades = Boolean.TRUE;

            validarFolio = Boolean.TRUE;
            valorFechaFinal = null;

            folioFacultades = folio;
            btnAceptar = Boolean.TRUE;

        } else {
            dialogFacultades = Boolean.TRUE;
            folioFacultades = null;
            valorFechaInicio = null;
        }

    }

    public String rechazarFacultades() {
        
        try {
            
            if ( realizarReasignacionDictaminador && cambioDictaminadorAsignado() ){
                reasignaTramiteADictaminador( dyctContribuyenteDTO, dictaminadorSeleccionado.getNumEmpleado());
            }
            
            administrarSolicitudesService.actualizarEstadoSolicitud(numControl, ConstantesDyCNumerico.VALOR_3);

            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(
                    FacesMessage.SEVERITY_INFO, 
                    "El flujo se rechazó correctamente",
                    null
                )
            );

        } catch ( SIATException e ){
            LOG.error( e.getMessage() );
            muestraMensajeErrorRechazoFacultades();
        }
        
        ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
        BandejaInicioFacultadesMB referenciaBeanSession = (BandejaInicioFacultadesMB) contexto.getSessionMap().get( "bandejaInicioFacultadesMB" );
        referenciaBeanSession.init();

        return "facSolicitudes";
    }
    
    private boolean cambioDictaminadorAsignado (){
        return !numEmpDictaminador.equals( dictaminadorSeleccionado.getNumEmpleado().toString() );
    }
    
    private void reasignaTramiteADictaminador( DyctContribuyenteDTO dyctContribuyenteDTO, Integer numEmpDictaminador ){
        
        List<DictaminadorSolBean> listaSolicitudes = getListaSolicitudes( dyctContribuyenteDTO );

        DyccDictaminadorDTO dictaminadorDestino = new DyccDictaminadorDTO();
        dictaminadorDestino.setNumEmpleado( numEmpDictaminador );
        
        reasignacionTramites.reasignacionManualSolicitud( listaSolicitudes, null, dictaminadorDestino, numControl );
    }
    
    private List<DictaminadorSolBean> getListaSolicitudes( DyctContribuyenteDTO dyctContribuyenteDTO ){
        
        DictaminadorSolBean solicitud = new DictaminadorSolBean();
        
        solicitud.setNumControl( dyctContribuyenteDTO.getNumControl() );
        
        List<DictaminadorSolBean> listaSolicitudes = new ArrayList<DictaminadorSolBean>();
        listaSolicitudes.add( solicitud );
        
        return listaSolicitudes;
    }
    
    private void muestraMensajeErrorRechazoFacultades (){
        FacesContext.getCurrentInstance().addMessage( null, 
            new FacesMessage( 
                FacesMessage.SEVERITY_ERROR, 
                "Ocurrió un error al rechazar el trámite",
                null
            )
        );
    }

    public String accionAprobarInicioFacultades() {
        
        if (null != folio && !"".equals(folio)) {
            if(null == valorFechaFinal) {
                mensaje.addError(ConstantesAdministrarSolicitud.MENSAJE_FACULTADES, "Ingresar datos requeridos");
                return "";
            }
        } else {
            if ( null == valorFechaInicio || null == folioFacultades || "".equals(folioFacultades.trim()) ) {
                mensaje.addError(ConstantesAdministrarSolicitud.MENSAJE_FACULTADES, "Ingresar datos requeridos");
                return "";
            }
        }
        
        
        DycpServicioDTO dycpServicioDTO = new DycpServicioDTO();
        dycpServicioDTO.setNumControl(numControl);

        DyctFacultadesDTO dyctFacultadesDTO = new DyctFacultadesDTO();
        dyctFacultadesDTO.setFolio(folioFacultades.trim());
        dyctFacultadesDTO.setDycpServicioDTO(dycpServicioDTO);

        if (null != folio && !"".equals(folio)) {

            StringBuilder ruta = new StringBuilder();

            ruta.append(ConstantesDyCURL.URL_DOCUMENTOS);
            ruta.append("/");
            ruta.append(String.valueOf(claveAdm).concat("/"));
            /**ruta.append(rfcContribuyente.concat("/"));*/
            ruta.append(Utilerias.corregirRFC(rfcContribuyente).concat("/"));
            ruta.append(numControl);
            ruta.append(ConstantesDyCURL.TIPO_ARCHIVO_ARCHIVOS);

            DyctArchivoDTO dyctArchivoDTO1 = new DyctArchivoDTO();
            dyctArchivoDTO1.setNombreArchivo(nombreArchivo);
            dyctArchivoDTO1.setUrl(ruta.toString());
            dyctArchivoDTO1.setDescripcion(nombreDocumento.toUpperCase());
            dyctArchivoDTO1.setDycpServicioDTO(dycpServicioDTO);
            dyctArchivoDTO1.setFechaRegistro(new Date());

            dyctFacultadesDTO.setFechaFin(valorFechaFinal);


            try {
                administrarSolicitudesService.accionFinalizarInicioFacultades(dyctFacultadesDTO, dyctArchivoDTO1);

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se aprobo el documento de manera exitosa", null));
            
                
            } catch (SIATException e) {
                LOG.error(e.getMessage());

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocurrió un error al aprobar el documento", null));
                
            }
        } else {
            
            dyctFacultadesDTO.setFechaInicio(valorFechaInicio);

            try {
                administrarSolicitudesService.accionAprobarInicioFacultades(dyctFacultadesDTO);

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se aprobó correctamente el inicio de facultades del artículo 22", null));
                

            } catch (SIATException e) {
                LOG.error(e.getMessage());

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocurrió un error al aprobar el inicio de facultades del artículo 22", null));
                
            }
        }
        RequestContext.getCurrentInstance().execute("dlgFacultades.hide();");
        
        dialogFacultades = false;

        ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
        BandejaInicioFacultadesMB referenciaBeanSession =
            (BandejaInicioFacultadesMB)contexto.getSessionMap().get("bandejaInicioFacultadesMB");        
            referenciaBeanSession.init();
       
       /** estuvo antes de redirect para ir a otra pagina y en vez de void fue string en el metodo-- return "facSolicitudes";*/
       //Tambien se comentaron otros return
        return "facSolicitudes";
    }


    public void fileUpload() {

        LOG.info("------------------- CARGAR DOCUMENTO ---------------");
        HttpServletRequest request =
            (HttpServletRequest)(FacesContext.getCurrentInstance().getExternalContext().getRequest());
        String cabeceraVirus = request.getHeader("X-Content-Scanning");
        LOG.info("cabeceraVirus ->" + cabeceraVirus + "<-");

        if (cabeceraVirus != null) {
            
            LOG.info("Se detecto virus");
            mensaje.addError(ConstantesAdministrarSolicitud.MENSAJE_FACULTADES,
                    "Se detecto virus en el archivo que intenta subir!");
            
            return;
        }

        long tamanioMax = ConstantesDyCNumerico.VALOR_4194304;

        String contentType = null;

        String nom1 = null;
        String nomCorrecto1 = null;

        if (null != file) {
            contentType = file.getFileName();
            nom1 = file.getFileName();
            nomCorrecto1 = nom1.substring(nom1.lastIndexOf('\\') + 1, nom1.length());
        }

        try {

            if (null != contentType) {
                contentType = contentType.substring(contentType.lastIndexOf(ConstantesArchivo.PUNTO)).toLowerCase();
            }

            validaciones(contentType, tamanioMax, nomCorrecto1);

        } catch (StringIndexOutOfBoundsException e) {
            LOG.error(e.getMessage()); 
            mensaje.addError(ConstantesAdministrarSolicitud.MENSAJE_FACULTADES, "Error: seleccione un archivo valido");
        } catch (Exception e) {
            LOG.error(e.getMessage());
            mensaje.addError(ConstantesAdministrarSolicitud.MENSAJE_FACULTADES, ConstantesDyC.ERROR_ENVIAR_DOCUMENTO);
        }
    }
    
    public void clear() {
        this.nombreDocumento = "";
    }

    public void validaciones(String contentType, long tamanioMax, String nomCorrecto1) {

        String zip = ".zip";

        try {

            if (null == file) {

                mensaje.addError(ConstantesAdministrarSolicitud.MENSAJE_FACULTADES,
                        "Error: debe seleccionar un archivo");

            } else if (null == nombreDocumento || "".equals(nombreDocumento.trim())) {
            
                mensaje.addError(ConstantesAdministrarSolicitud.MENSAJE_FACULTADES,
                        "Campo requerido (nombre documento)");
            
            } else if (file.getSize() > tamanioMax) {

                mensaje.addError(ConstantesAdministrarSolicitud.MENSAJE_FACULTADES,
                        "Error: el archivo " + nomCorrecto1 + " sobrepasa el peso permitido (4Mb)");

            } else if (!contentType.equals(zip)) {

                mensaje.addError(ConstantesAdministrarSolicitud.MENSAJE_FACULTADES,
                        "Error: el archivo " + nomCorrecto1 + " no es de tipo .zip");

            } else {

                String nom = file.getFileName();
                String nomCorrecto = nom.substring(nom.lastIndexOf('\\') + 1, nom.length());

                if (nomCorrecto.length() > ConstantesDyCNumerico.VALOR_80) {

                    mensaje.addError(ConstantesAdministrarSolicitud.MENSAJE_FACULTADES,
                            "Error: tamaño de nombre de archivo inválido");

                } else {

                    guardarArchivo(nomCorrecto);

                }
            }

        } catch (Exception e) {
            LOG.error(e.getMessage());
            FacesMessage errorArchivo =
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al tratar de enviar el documento.", null);
            FacesContext.getCurrentInstance().addMessage(null, errorArchivo);
        }

    }

    public void guardarArchivo(String nomCorrecto) {

        StringBuilder ruta = new StringBuilder();

        ruta.append(ConstantesDyCURL.URL_DOCUMENTOS);
        ruta.append("/");
        ruta.append(String.valueOf(claveAdm).concat("/"));
        /**ruta.append(rfcContribuyente.concat("/"));*/
        ruta.append(Utilerias.corregirRFC(rfcContribuyente).concat("/"));
        ruta.append(numControl);
        ruta.append(ConstantesDyCURL.TIPO_ARCHIVO_ARCHIVOS);


        try {

            archivo.escribirArchivo(nomCorrecto, file.getInputstream(), ruta.toString(),
                                    ConstantesDyCNumerico.VALOR_4096);

            nombreArchivo = nomCorrecto;

            btnAceptar = false;

            mensaje.addInfo(ConstantesAdministrarSolicitud.MENSAJE_FACULTADES,
                    "Archivo: " + nomCorrecto + " cargado con éxito.");

        } catch (SIATException e) {
            LOG.error(e.getMessage());
            mensaje.addInfo(ConstantesAdministrarSolicitud.MENSAJE_FACULTADES,
                    "Ocurrio un error al cargar el archivo.");
        } catch (IOException e) {
            LOG.error(e.getMessage());
            mensaje.addInfo(ConstantesAdministrarSolicitud.MENSAJE_FACULTADES,
                    "Ocurrio un error al cargar el archivo.");
        }

    }

    public void cancelarFacultades() {
        dialogFacultades = false;
    }

    public String irDictaminarSolicitud() {

        return "bandejaInicioFacultades1";

    }

    public String irConsultarExpediente() {

        dyCConsultarExpedienteMB.setParametroRegresar(ConstantesDyCNumerico.VALOR_4);

        dyCConsultarExpedienteMB.setNumControl(numControl);
        dyCConsultarExpedienteMB.setClaveAdm(claveAdm);
        dyCConsultarExpedienteMB.setRfcContribuyente(rfcContribuyente);
        dyCConsultarExpedienteMB.setImporteSolicitado(importeSolicitado);

        dyCConsultarExpedienteMB.init();

        return "irConsultarExpediente";
    }

    public void setDyctContribuyenteDTO(DyctContribuyenteDTO dyctContribuyenteDTO) {
        this.dyctContribuyenteDTO = dyctContribuyenteDTO;
    }

    public DyctContribuyenteDTO getDyctContribuyenteDTO() {
        return dyctContribuyenteDTO;
    }

    public void setConsultarExpedienteService(ConsultarExpedienteService consultarExpedienteService) {
        this.consultarExpedienteService = consultarExpedienteService;
    }

    public ConsultarExpedienteService getConsultarExpedienteService() {
        return consultarExpedienteService;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setPersonaDTO(PersonaDTO personaDTO) {
        this.personaDTO = personaDTO;
    }

    public PersonaDTO getPersonaDTO() {
        return personaDTO;
    }

    public void setPersonaIdentificacion(PersonaIdentificacionDTO personaIdentificacion) {
        this.personaIdentificacion = personaIdentificacion;
    }

    public PersonaIdentificacionDTO getPersonaIdentificacion() {
        return personaIdentificacion;
    }

    public void setDyCConsultarExpedienteMB(DyCConsultarExpedienteMB dyCConsultarExpedienteMB) {
        this.dyCConsultarExpedienteMB = dyCConsultarExpedienteMB;
    }

    public DyCConsultarExpedienteMB getDyCConsultarExpedienteMB() {
        return dyCConsultarExpedienteMB;
    }

    public void setClaveAdm(int claveAdm) {
        this.claveAdm = claveAdm;
    }

    public int getClaveAdm() {
        return claveAdm;
    }

    public void setRfcContribuyente(String rfcContribuyente) {
        this.rfcContribuyente = rfcContribuyente;
    }

    public String getRfcContribuyente() {
        return rfcContribuyente;
    }

    public void setFechaStr(String fechaStr) {
        this.fechaStr = fechaStr;
    }

    public String getFechaStr() {
        return fechaStr;
    }

    public void setAdministrarSolicitudesService(AdministrarSolicitudesService administrarSolicitudesService) {
        this.administrarSolicitudesService = administrarSolicitudesService;
    }

    public AdministrarSolicitudesService getAdministrarSolicitudesService() {
        return administrarSolicitudesService;
    }

    public void setFolioFacultades(String folioFacultades) {
        this.folioFacultades = folioFacultades;
    }

    public String getFolioFacultades() {
        return folioFacultades;
    }

    public void setBtnDocFacultades(boolean btnDocFacultades) {
        this.btnDocFacultades = btnDocFacultades;
    }

    public boolean isBtnDocFacultades() {
        return btnDocFacultades;
    }

    public void setOcultarFechaFinal(boolean ocultarFechaFinal) {
        this.ocultarFechaFinal = ocultarFechaFinal;
    }

    public boolean isOcultarFechaFinal() {
        return ocultarFechaFinal;
    }

    public void setMensaje(Mensaje mensaje) {
        this.mensaje = mensaje;
    }

    public Mensaje getMensaje() {
        return mensaje;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getFolio() {
        return folio;
    }

    public void setValidarFolio(boolean validarFolio) {
        this.validarFolio = validarFolio;
    }

    public boolean isValidarFolio() {
        return validarFolio;
    }

    public void setValorFechaFinal(Date valorFechaFinal) {
        if (null != valorFechaFinal) {
            this.valorFechaFinal = (Date)valorFechaFinal.clone();
        } else {
            this.valorFechaFinal = null;
        }
    }

    public Date getValorFechaFinal() {
        if (null != valorFechaFinal) {
            return (Date)valorFechaFinal.clone();
        } else {
            return null;
        }
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setDialogFacultades(boolean dialogFacultades) {
        this.dialogFacultades = dialogFacultades;
    }

    public boolean isDialogFacultades() {
        return dialogFacultades;
    }

    public void setArchivo(ArchivoCargaDescarga archivo) {
        this.archivo = archivo;
    }

    public ArchivoCargaDescarga getArchivo() {
        return archivo;
    }

    public void setBtnAceptar(boolean btnAceptar) {
        this.btnAceptar = btnAceptar;
    }

    public boolean isBtnAceptar() {
        return btnAceptar;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setFechaInicio(Date fechaInicio) {
        if (null != fechaInicio) {
            this.fechaInicio = (Date)fechaInicio.clone();
        } else {
            this.fechaInicio = null;
        }
    }

    public Date getFechaInicio() {
        if (null != fechaInicio) {
            return (Date)fechaInicio.clone();
        } else {
            return null;
        }
    }
    
    public void setFechaPresentacion(Date fechaPresentacion) {
        if (null != fechaPresentacion) {
            this.fechaPresentacion = (Date)fechaPresentacion.clone();
        } else {
            this.fechaPresentacion = null;
        }
    }

    public Date getFechaPresentacion() {
        if (null != fechaPresentacion) {
            return (Date)fechaPresentacion.clone();
        } else {
            return null;
        }
    }
    
    public void setValorFechaInicio(Date valorFechaInicio) {
        if (null != valorFechaInicio) {
            this.valorFechaInicio = (Date)valorFechaInicio.clone();
        } else {
            this.valorFechaInicio = null;
        }
    }

    public Date getValorFechaInicio() {
        if (null != valorFechaInicio) {
            return (Date)valorFechaInicio.clone();
        } else {
            return null;
        }
    }

    public void setImporteSolicitado(BigDecimal importeSolicitado) {
        this.importeSolicitado = importeSolicitado;
    }

    public BigDecimal getImporteSolicitado() {
        return importeSolicitado;
    }

    public void setEtiBoton(String etiBoton) {
        this.etiBoton = etiBoton;
    }

    public String getEtiBoton() {
        return etiBoton;
    }

    public void setValidacionFechaInicio1(boolean validacionFechaInicio1) {
        this.validacionFechaInicio1 = validacionFechaInicio1;
    }

    public boolean isValidacionFechaInicio1() {
        return validacionFechaInicio1;
    }

    public void setValidacionFechaInicio2(boolean validacionFechaInicio2) {
        this.validacionFechaInicio2 = validacionFechaInicio2;
    }

    public boolean isValidacionFechaInicio2() {
        return validacionFechaInicio2;
    }

    public void setFechaActualInicio(Date fechaActualInicio) {
        if (null != fechaActualInicio) {
            this.fechaActualInicio = (Date)fechaActualInicio.clone();
        } else {
            this.fechaActualInicio = null;
        }
    }

    public Date getFechaActualInicio() {
        if (null != fechaActualInicio) {
            return (Date)fechaActualInicio.clone();
        } else {
            return null;
        }
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }
    
    public void mostrarOpcionDictaminadoresReasignacion (){
        
        mostrarOpcionReasignacionDictaminador();
        ocultarListadoReasignacionDictaminador();
    }

    public void mostrarListaDictaminadoresReasignacion (){
        LOG.info( "mostrarListaDictaminadoresReasignacion " );
        cargarListaDictaminadores();
        ocultarOpcionReasignacionDictaminador();
        mostrarListadoReasignacionDictaminador();
        
        realizarReasignacionDictaminador = Boolean.TRUE;
    }

    private void ocultarOpcionReasignacionDictaminador (){
        RequestContext.getCurrentInstance().execute( OCULTAR_OPCION_REASIGNAR_DICTAMINADOR );
    }
    
    private void mostrarOpcionReasignacionDictaminador (){
        RequestContext.getCurrentInstance().execute( MOSTRAR_OPCION_REASIGNAR_DICTAMINADOR );
    }

    private void mostrarListadoReasignacionDictaminador (){
        RequestContext.getCurrentInstance().execute( MOSTRAR_LISTADO_DICTAMINADORES );
    }

    private void cargarListaDictaminadores (){
        
        DictaminadorVO dictaminador = new DictaminadorVO();
        dictaminador.setClaveAdm( claveAdmon );
        
        List<DictaminadorVO> registros;
        
        try {
             registros = dictaminadorService.obtenerDictaminadoresActivos( dictaminador );
        } catch ( SQLException error ){
            registros = new ArrayList<DictaminadorVO>();
            LOG.info( "No se pudieron obtener dictaminadores con la clvAdm : " + claveAdmon + 
                        " " + error.getMessage() );
        }

        listaDictaminadoresReasignacion = registros;
    }

    public String omitirReasignarDictaminador (){
        realizarReasignacionDictaminador = false;
        confirmacionReasignacion = false;
        dictaminadorSeleccionado = null;

        return rechazarFacultades();
    }

    public void setNombreDictaminador ( String nombreDictaminador ){
        this.nombreDictaminador = nombreDictaminador;
    }

    public void setNumEmpDictaminador ( String numEmpDictaminador ){
        this.numEmpDictaminador = numEmpDictaminador;
    }

    public String getNombreDictaminador (){
        return nombreDictaminador;
    }

    public String getNumEmpDictaminador (){
        return numEmpDictaminador;
    }

    public void setDictaminadorSeleccionado ( DictaminadorVO dictaminadorSeleccionado ){
        this.dictaminadorSeleccionado = dictaminadorSeleccionado;
    }

    public DictaminadorVO getDictaminadorSeleccionado (){
        return dictaminadorSeleccionado;
    }

    public List<DictaminadorVO> getListaDictaminadoresReasignacion (){
        return listaDictaminadoresReasignacion;
    }

    public void setListaDictaminadoresReasignacion ( List<DictaminadorVO> listaDictaminadoresReasignacion ){
        this.listaDictaminadoresReasignacion = listaDictaminadoresReasignacion;
    }

    public void onRowSelect ( SelectEvent evento ){
        recuperaRegistroSeleccionado( evento );
        habilitaConfirmacionReasignacion();
        LOG.info( "registro seleccionado :" + dictaminadorSeleccionado.getNumEmpleado() );
    }

    private void recuperaRegistroSeleccionado ( SelectEvent evento ) {
        dictaminadorSeleccionado = getRegistroSeleccionado( evento );
    }
    
    private DictaminadorVO getRegistroSeleccionado ( SelectEvent evento ){
        return (DictaminadorVO) evento.getObject();
    }

    private void habilitaConfirmacionReasignacion (){
        confirmacionReasignacion = Boolean.TRUE;
    }

    public boolean isConfirmacionReasignacion (){
        return confirmacionReasignacion;
    }

    public String reasignarDictaminador (){
        LOG.info( "reasignarDictaminador" );

        if ( dictaminadorReasignacionValido() /*&& false*/ ){
            ocultarListadoReasignacionDictaminador();

            return rechazarFacultades();
        }

        muestraMensajeDictaminadorNoValido();

        return NO_REDIRECCIONAMIENTO;
    }

    private boolean dictaminadorReasignacionValido (){
        try {
            return validacionAgs.getEstatusEmpleadoActivo( dictaminadorSeleccionado.getNumEmpleado() );

        } catch ( SIATException ex ){
            LOG.info( 
                "Error al validar el empleado : " + dictaminadorSeleccionado.getNumEmpleado() + 
                " " + ex.getDescripcion()
            );
        }
        
        return false;
    }

    private void ocultarListadoReasignacionDictaminador (){
        RequestContext.getCurrentInstance().execute( OCULTAR_LISTADO_DICTAMINADORES );
    }

    private void muestraMensajeDictaminadorNoValido (){
        FacesMessage mensajeProceso = new FacesMessage( FacesMessage.SEVERITY_ERROR, getDetalleErrorDictaminador(), "" );
        FacesContext.getCurrentInstance().addMessage( null, mensajeProceso );
    }

    private String getDetalleErrorDictaminador (){
        if ( dictaminadorSeleccionado != null ){
            return MENSAJE_DICTAMINADOR_NO_VALIDO;
        }

        return MENSAJE_DICTAMINADOR_NO_SELECCIONADO;
    }

    public String cancelarReasignarDictaminador (){
        confirmacionReasignacion = false;
        dictaminadorSeleccionado = null;
        
        ocultarListadoReasignacionDictaminador();
        mostrarOpcionReasignacionDictaminador();

        return NO_REDIRECCIONAMIENTO;
    }

    public DictaminadorService getDictaminadorService (){
        return dictaminadorService;
    }

    public void setDictaminadorService ( DictaminadorService dictaminadorService ){
        this.dictaminadorService = dictaminadorService;
    }

    public void setValidacionAgs( SatAgsEmpleadosMVService validacionAgs ){
        this.validacionAgs = validacionAgs;
    }

    public SatAgsEmpleadosMVService getValidacionAgs (){
        return validacionAgs;
    }

    public void setClaveAdmon ( int claveAdmon ){
        this.claveAdmon = claveAdmon;
    }
    
    public void setReasignacionTramites ( ReasignarManSolicDevolucionyCasosCompService reasignacionTramites ){
        this.reasignacionTramites = reasignacionTramites;
    }
    
    public ReasignarManSolicDevolucionyCasosCompService getReasignacionTramites (){
        return reasignacionTramites;
    }
    
}
