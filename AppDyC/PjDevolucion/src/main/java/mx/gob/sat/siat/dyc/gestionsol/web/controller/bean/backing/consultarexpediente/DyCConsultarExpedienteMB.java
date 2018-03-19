/*
 *  Todos los Derechos Reservados 2013 SAT.
 *  Servicio de Administracion Tributaria (SAT).
 *
 *  Este software contiene informacion propiedad exclusiva del SAT considerada
 *  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 *  parcial o total.
 */
package mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.backing.consultarexpediente;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;

import java.math.BigDecimal;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.cobranza.diahabil.service.DiaHabilService;
import mx.gob.sat.siat.dyc.catalogo.service.DyccInconsistenciaService;
import mx.gob.sat.siat.dyc.catalogo.service.DyctDeclaracionService;
import mx.gob.sat.siat.dyc.controlsaldos.service.DetalleIcepService;
import mx.gob.sat.siat.dyc.controlsaldos.service.icep.CalcularSaldoRemanenteICEPService;
import mx.gob.sat.siat.dyc.domain.altex.SpConsultarAltexDTO;
import mx.gob.sat.siat.dyc.domain.anexo.DyctAnexoReqDTO;
import mx.gob.sat.siat.dyc.domain.archivo.DyctArchivoDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaIdentificacionDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaUbicacionDTO;
import mx.gob.sat.siat.dyc.domain.declaracion.DyctFacultadesDTO;
import mx.gob.sat.siat.dyc.domain.declaracion.DyctNotaDTO;
import mx.gob.sat.siat.dyc.domain.devolucionaut.DyctDictAutomaticaDTO;
import mx.gob.sat.siat.dyc.domain.diot.DiotDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctInfoRequeridaDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctContribuyenteDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaracionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.ExpedienteDTO;
import mx.gob.sat.siat.dyc.domain.inconsistencia.DycaSolInconsistDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.generico.util.ArchivoCargaDescarga;
import mx.gob.sat.siat.dyc.generico.util.common.AbstractPage;
import mx.gob.sat.siat.dyc.generico.util.common.SIATDataModel;
import mx.gob.sat.siat.dyc.generico.util.common.Utilerias;
import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.BandejaDocumentosDTO;
import mx.gob.sat.siat.dyc.gestionsol.service.administrarsolicitudes.AdministrarSolicitudesService;
import mx.gob.sat.siat.dyc.gestionsol.util.recurso.UtileriasGestionSol;
import mx.gob.sat.siat.dyc.gestionsol.vo.administrarsolicitudes.AdministrarSolicitudesVO;
import mx.gob.sat.siat.dyc.gestionsol.vo.consultarexpediente.DeclaracionConsultarExpedienteVO;
import mx.gob.sat.siat.dyc.registro.service.contribuyente.ConsultarExpedienteService;
import mx.gob.sat.siat.dyc.registro.service.solicitud.DycpSolicitudService;
import mx.gob.sat.siat.dyc.service.DyctAnexoReqService;
import mx.gob.sat.siat.dyc.service.DyctAnexoService;
import mx.gob.sat.siat.dyc.service.DyctArchivoService;
import mx.gob.sat.siat.dyc.service.DyctFacultadesService;
import mx.gob.sat.siat.dyc.service.DyctInfoRequeridaService;
import mx.gob.sat.siat.dyc.service.DyctNotasExpService;
import mx.gob.sat.siat.dyc.util.RegistroPistasAuditoria;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesAdministrarSolicitud;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDictaminacionAutomatica;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCURL;
import mx.gob.sat.siat.dyc.util.constante.ConstantesIds;
import mx.gob.sat.siat.dyc.util.constante.Procesos;
import mx.gob.sat.siat.dyc.util.constante1.ConstantePerfilUsr;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.constante2.ConstantesTipoArchivo;
import mx.gob.sat.siat.dyc.util.constante2.ConstantesValContribuyente;
import mx.gob.sat.siat.dyc.vo.ConsultarExpedienteVO;
import mx.gob.sat.siat.dyc.vo.PistaAuditoriaVO;
import mx.gob.sat.siat.dyc.vo.SolicitudAdministrarSolVO;
import mx.gob.sat.siat.dyc.vo.SolicitudConsultarExpedienteVO;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 * Managed Bean para consultar expediente
 *
 * @author Federico Chopin
 * @date 23/09/2013
 */
@ManagedBean(name = "dyCConsultarExpedienteMB")
@SessionScoped
public class DyCConsultarExpedienteMB extends AbstractPage {

    private static final Logger LOG = Logger.getLogger(DyCConsultarExpedienteMB.class.getName());

    @ManagedProperty("#{consultarExpedienteService}")
    private ConsultarExpedienteService consultarExpedienteService;

    @ManagedProperty("#{dycpSolicitudService}")
    private DycpSolicitudService dycpSolicitudService;

    @ManagedProperty("#{dyccInconsistenciaService}")
    private DyccInconsistenciaService dyccInconsistenciaService;

    @ManagedProperty("#{dyctArchivoService}")
    private DyctArchivoService dyctArchivoService;

    @ManagedProperty("#{dyctInfoRequeridaService}")
    private DyctInfoRequeridaService dyctInfoRequeridaService;

    @ManagedProperty("#{dyctAnexoReqService}")
    private DyctAnexoReqService dyctAnexoReqService;

    @ManagedProperty("#{dyctNotasExpService}")
    private DyctNotasExpService dyctNotasExpService;

    @ManagedProperty("#{dyctFacultadesService}")
    private DyctFacultadesService dyctFacultadesService;

    @ManagedProperty("#{dyctAnexoService}")
    private DyctAnexoService dyctAnexoService;

    @ManagedProperty("#{dyctDeclaracionService}")
    private DyctDeclaracionService dyctDeclaracionService;

    @ManagedProperty("#{calcularSaldoRemanenteICEPService}")
    private CalcularSaldoRemanenteICEPService calcularSaldoRemanenteICEPService;

    @ManagedProperty("#{registroPistasAuditoria}")
    private RegistroPistasAuditoria registroPistasAuditoria;

    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    @ManagedProperty(value = "#{bdDetalleIcep}")
    private DetalleIcepService bussinesDelegate;

    @ManagedProperty(value = "#{administrarSolicitudesService}")
    private AdministrarSolicitudesService administrarSolicitudesService;

    @ManagedProperty(value = "#{diaHabilService}")
    private DiaHabilService diaHabilService;

    private PistaAuditoriaVO pistaAuditoria;

    private Map<String, String> reemplaceMensajes;

    private StreamedContent fileDocs;
    private StreamedContent fileAnexos;
    private StreamedContent fileDocsGestion;
    private StreamedContent fileDocsRequeridos;
    private StreamedContent fileAnexosRequeridos;

    private DyctContribuyenteDTO dyctContribuyenteDTO;
    private SolicitudConsultarExpedienteVO dycpSolicitudDTO;
    private DeclaracionConsultarExpedienteVO consultarExpedienteDTO;
    private PersonaDTO personaDTO;
    private PersonaDTO personaDTO2;
    private SpConsultarAltexDTO spConsultarAltexDTO;
    private DiotDTO diotDTO;
    private PersonaIdentificacionDTO personaIdentificacion;
    private PersonaIdentificacionDTO personaIdentificacion2;
    private PersonaUbicacionDTO domicilio;
    private ExpedienteDTO expedienteDTO;
    private DyctArchivoDTO dyctArchivoDTO;
    private DyctDocumentoDTO dyctDocumentoDTO;
    private SolicitudAdministrarSolVO solicitudAdministrarSolVO;
    private ConsultarExpedienteVO consultarExpedienteVO;
    private DyctInfoRequeridaDTO dyctInfoRequeridaDTO;
    private DyctAnexoReqDTO dyctAnexoReqDTO;
    private DyctDeclaracionDTO declaracion;
    private DyctDeclaracionDTO declaracionAux;

    private List<DycaSolInconsistDTO> listaInconsistenciasDev = new ArrayList<DycaSolInconsistDTO>();
    private List<DyctNotaDTO> listaNotas = new ArrayList<DyctNotaDTO>();
    private List<ConsultarExpedienteVO> listaAnexos = new ArrayList<ConsultarExpedienteVO>();
    private List<DyctArchivoDTO> listaDocsAdjuntos = new ArrayList<DyctArchivoDTO>();
    private List<DyctInfoRequeridaDTO> listaDocsAdjuntosRequeridos = new ArrayList<DyctInfoRequeridaDTO>();
    private List<DyctAnexoReqDTO> listaDocsAnexosRequeridos = new ArrayList<DyctAnexoReqDTO>();
    private List<SolicitudAdministrarSolVO> listaDocsGestion = new ArrayList<SolicitudAdministrarSolVO>();
    private List<DyctDeclaracionDTO> listaDeclaraciones = new ArrayList<DyctDeclaracionDTO>();
    private List<DyctFacultadesDTO> listaArticulo22 = new ArrayList<DyctFacultadesDTO>();
    private List<DyctDictAutomaticaDTO> listaMotivosRiesgo = new ArrayList<DyctDictAutomaticaDTO>();

    private boolean varBotonDocs;
    private boolean varBotonAnexos;
    private boolean varBotonDocsRequeridos;
    private boolean varBotonAnexosRequeridos;
    private boolean varBotonGestion;
    private boolean varBotonAnexDoc;

    private String nombreDocumento = "";
    private String numControl;
    private String esIdEstado = "";
    private String proceso = "En Proceso";

    private AccesoUsr accesoUsr;
    private UploadedFile file;

    private boolean gridDIOT = false;
    private boolean gridALTEX = false;
    private boolean gridRetenedor = false;
    private boolean gridDeclaracionAuxiliar = false;

    private String msgGeneral;
    private int parametroRegresar;

    private boolean panelAnexos;
    private boolean panelInconsistencias;
    private boolean botonTramite1;
    private boolean botonTramite2;

    private ArchivoCargaDescarga archivo;

    private int claveAdm;
    private String rfcContribuyente;
    private BigDecimal importeSolicitado;

    private boolean paginador1;
    private boolean paginador2;
    private boolean paginador3;
    private boolean paginador4;
    private boolean paginador5;
    private boolean paginador6;
    private boolean paginador7;
    private boolean paginador8;

    private String nombreCompletoEmpleado;
    private String rfcEmpleado;
    private String folioAviso;

    private static final int DEFAULT_BUFFER_SIZE = 10240;

    private Document documento;

    private BigDecimal remanenteHistorico;

    private BandejaDocumentosDTO bandejaDocumentosSolDTO;

    private boolean ocultarPanelArt22;
    private boolean ocultarPanelMotRies;
    private String mensajeErrorNyV;

    private String fechaNyV;
    private String folioNyV;
    private String numControlDoc;
    private boolean tieneFolio;

    private String mensajeFecha;
    private boolean tieneRolDYC008;
    private boolean mensajeFechaB;
    private boolean mensajeFechaError;
    private String nombreArchivo;
    public static final String LOG_01 = "Error al redireccionar a la página de error ";
    public static final String EMPTY = "";
    private Date fechaPago;

    public void crearTabla1(Font fuente1, Font fuente2) {

        try {
            documento.add(new Paragraph("Datos resumidos del contribuyente", fuente1));
            documento.add(new Paragraph(" Datos del contribuyente", fuente1));

            PdfPTable t1 = new PdfPTable(ConstantesDyCNumerico.VALOR_2);
            PdfPCell c1 = new PdfPCell(new Paragraph("RFC:", fuente2));
            PdfPCell c2 = new PdfPCell(new Paragraph(personaDTO.getRfcVigente(), fuente2));
            PdfPCell c3 = new PdfPCell(new Paragraph((personaIdentificacion.getRazonSocial() != null && !personaIdentificacion.getRazonSocial().isEmpty())
                    ? "Denominación o razón social:"
                    : "Nombre:", fuente2));
            PdfPCell c4 = new PdfPCell(new Paragraph((personaIdentificacion.getRazonSocial() != null && !personaIdentificacion.getRazonSocial().isEmpty())
                    ? personaIdentificacion.getRazonSocial()
                    : personaIdentificacion.getNombre() + " " + personaIdentificacion.getApPaterno() + " " + personaIdentificacion.getApMaterno(), fuente2));
            c1.setBorder(Rectangle.NO_BORDER);
            c2.setBorder(Rectangle.NO_BORDER);
            c3.setBorder(Rectangle.NO_BORDER);
            c4.setBorder(Rectangle.NO_BORDER);
            t1.addCell(c1);
            t1.addCell(c2);
            t1.addCell(c3);
            t1.addCell(c4);
            t1.setWidthPercentage(ConstantesDyCNumerico.VALOR_100);
            t1.setHorizontalAlignment(Element.ALIGN_LEFT);
            documento.add(t1);

        } catch (DocumentException e) {
            LOG.error(e.getMessage());
        }

    }

    public void crearTabla2(Font fuente1, Font fuente2) {

        try {

            documento.add(new Paragraph(" Domicilio fiscal", fuente1));

            PdfPTable t2 = new PdfPTable(ConstantesDyCNumerico.VALOR_2);
            PdfPCell c5 = new PdfPCell(new Paragraph("Calle:", fuente2));
            PdfPCell c6 = new PdfPCell(new Paragraph(domicilio.getCalle(), fuente2));

            PdfPCell c7 = new PdfPCell(new Paragraph("Delegación o municipio:", fuente2));
            PdfPCell c8 = new PdfPCell(new Paragraph(domicilio.getMunicipio(), fuente2));

            PdfPCell c9 = new PdfPCell(new Paragraph("Entidad federativa:", fuente2));
            PdfPCell c10 = new PdfPCell(new Paragraph(domicilio.getEntFed(), fuente2));

            PdfPCell c11
                    = new PdfPCell(new Paragraph("Número de Administración Desconcentrada de auditoría fiscal:", fuente2));
            PdfPCell c12 = new PdfPCell(new Paragraph(String.valueOf(domicilio.getClaveAdmonLocal()), fuente2));

            PdfPCell c13
                    = new PdfPCell(new Paragraph("Descripción de la administración de auditoría fiscal que corresponde:",
                                    fuente2));
            PdfPCell c14 = new PdfPCell(new Paragraph(domicilio.getAdmonLocal(), fuente2));

            c5.setBorder(Rectangle.NO_BORDER);
            c6.setBorder(Rectangle.NO_BORDER);
            c7.setBorder(Rectangle.NO_BORDER);
            c8.setBorder(Rectangle.NO_BORDER);
            c9.setBorder(Rectangle.NO_BORDER);
            c10.setBorder(Rectangle.NO_BORDER);
            c11.setBorder(Rectangle.NO_BORDER);
            c12.setBorder(Rectangle.NO_BORDER);
            c13.setBorder(Rectangle.NO_BORDER);
            c14.setBorder(Rectangle.NO_BORDER);
            t2.addCell(c5);
            t2.addCell(c6);
            t2.addCell(c7);
            t2.addCell(c8);
            t2.addCell(c9);
            t2.addCell(c10);
            t2.addCell(c11);
            t2.addCell(c12);
            t2.addCell(c13);
            t2.addCell(c14);
            t2.setWidthPercentage(ConstantesDyCNumerico.VALOR_100);
            t2.setHorizontalAlignment(Element.ALIGN_LEFT);
            documento.add(t2);
        } catch (DocumentException e) {
            LOG.error(e.getMessage());
        }

    }

    public void crearTabla3(Font fuente1, Font fuente2, SimpleDateFormat formateadorFecha) {

        try {

            documento.add(new Paragraph("Información resumida del trámite", fuente1));
            documento.add(new Paragraph(" Información del trámite", fuente1));

            PdfPTable t3 = new PdfPTable(ConstantesDyCNumerico.VALOR_2);
            PdfPCell c15 = new PdfPCell(new Paragraph("Fecha de registro del trámite:", fuente2));
            PdfPCell c16
                    = new PdfPCell(new Paragraph(formateadorFecha.format(dycpSolicitudDTO.getFechaPresentacion()).toString(),
                                    fuente2));
            PdfPCell c17 = new PdfPCell(new Paragraph("Número de control:", fuente2));
            PdfPCell c18 = new PdfPCell(new Paragraph(dycpSolicitudDTO.getNumControl(), fuente2));
            c15.setBorder(Rectangle.NO_BORDER);
            c16.setBorder(Rectangle.NO_BORDER);
            c17.setBorder(Rectangle.NO_BORDER);
            c18.setBorder(Rectangle.NO_BORDER);
            t3.addCell(c15);
            t3.addCell(c16);
            t3.addCell(c17);
            t3.addCell(c18);
            t3.setWidthPercentage(ConstantesDyCNumerico.VALOR_100);
            t3.setHorizontalAlignment(Element.ALIGN_LEFT);
            documento.add(t3);

        } catch (DocumentException e) {
            LOG.error(e.getMessage());
        }

    }

    public void crearTabla4(Font fuente1, Font fuente2, SimpleDateFormat formateadorFecha) {

        try {

            /**
             * tabla notas
             */
            documento.add(new Paragraph("  "));
            documento.add(new Paragraph("Notas registradas al expediente del trámite", fuente1));
            documento.add(new Paragraph("  "));

            PdfPTable table1 = new PdfPTable(ConstantesDyCNumerico.VALOR_3);

            PdfPCell celda1 = new PdfPCell(new Paragraph("Fecha de creación", fuente1));
            table1.addCell(celda1);

            PdfPCell celda2 = new PdfPCell(new Paragraph("Responsable", fuente1));
            table1.addCell(celda2);

            PdfPCell celda3 = new PdfPCell(new Paragraph("Nota", fuente1));
            table1.addCell(celda3);

            for (int i = 0; i < listaNotas.size(); i++) {
                PdfPCell celda4
                        = new PdfPCell(new Paragraph(formateadorFecha.format(listaNotas.get(i).getFecha()).toString(),
                                        fuente2));
                table1.addCell(celda4);
                PdfPCell celda5 = new PdfPCell(new Paragraph(listaNotas.get(i).getUsuario(), fuente2));
                table1.addCell(celda5);
                PdfPCell celda6 = new PdfPCell(new Paragraph(listaNotas.get(i).getTexto(), fuente2));
                table1.addCell(celda6);
            }

            table1.setWidthPercentage(ConstantesDyCNumerico.VALOR_100);
            table1.setHorizontalAlignment(Element.ALIGN_LEFT);

            documento.add(table1);

        } catch (DocumentException e) {
            LOG.error(e.getMessage());
        }

    }

    public void crearTabla5(Font fuente1, Font fuente2, SimpleDateFormat formateadorFecha) {

        try {

            /**
             * tabla documentos adjuntos
             */
            documento.add(new Paragraph("  "));
            documento.add(new Paragraph("Documentos adjuntos", fuente1));
            documento.add(new Paragraph("  "));

            PdfPTable table3 = new PdfPTable(ConstantesDyCNumerico.VALOR_4);

            PdfPCell celda111 = new PdfPCell(new Paragraph(ConstantesDyC1.FECHA_REGISTRO, fuente1));
            table3.addCell(celda111);

            PdfPCell celda222 = new PdfPCell(new Paragraph("Nombre archivo", fuente1));
            table3.addCell(celda222);

            PdfPCell celda333 = new PdfPCell(new Paragraph("Nombre documento", fuente1));
            table3.addCell(celda333);

            PdfPCell celda444 = new PdfPCell(new Paragraph(ConstantesDyC1.ESTADO, fuente1));
            table3.addCell(celda444);

            for (int i = 0; i < listaDocsAdjuntos.size(); i++) {
                PdfPCell celda555
                        = new PdfPCell(new Paragraph(formateadorFecha.format(listaDocsAdjuntos.get(i).getFechaRegistro()).toString(),
                                        fuente2));
                table3.addCell(celda555);
                PdfPCell celda666 = new PdfPCell(new Paragraph(listaDocsAdjuntos.get(i).getNombreArchivo(), fuente2));
                table3.addCell(celda666);
                PdfPCell celda777 = new PdfPCell(new Paragraph(listaDocsAdjuntos.get(i).getDescripcion(), fuente2));
                table3.addCell(celda777);
                PdfPCell celda888 = new PdfPCell(new Paragraph(ConstantesDyC1.ADJUNTADO, fuente2));
                table3.addCell(celda888);
            }

            table3.setWidthPercentage(ConstantesDyCNumerico.VALOR_100);
            table3.setHorizontalAlignment(Element.ALIGN_LEFT);

            documento.add(table3);

        } catch (DocumentException e) {
            LOG.error(e.getMessage());
        }

    }

    public void crearTabla6(Font fuente1, Font fuente2, SimpleDateFormat formateadorFecha) {

        try {

            /**
             * tabla documentos anexos
             */
            documento.add(new Paragraph("  "));
            documento.add(new Paragraph("Documentos anexos", fuente1));
            documento.add(new Paragraph("  "));

            PdfPTable table4 = new PdfPTable(ConstantesDyCNumerico.VALOR_3);

            PdfPCell celdaDA1 = new PdfPCell(new Paragraph(ConstantesDyC1.FECHA_REGISTRO, fuente1));
            table4.addCell(celdaDA1);

            PdfPCell celdaDA2 = new PdfPCell(new Paragraph("Nombre documento", fuente1));
            table4.addCell(celdaDA2);

            PdfPCell celdaDA3 = new PdfPCell(new Paragraph(ConstantesDyC1.ESTADO, fuente1));
            table4.addCell(celdaDA3);

            for (int i = 0; i < listaAnexos.size(); i++) {
                PdfPCell celdaDA4
                        = new PdfPCell(new Paragraph(formateadorFecha.format(listaAnexos.get(i).getFechaRegistro()).toString(),
                                        fuente2));
                table4.addCell(celdaDA4);
                PdfPCell celdaDA5 = new PdfPCell(new Paragraph(listaAnexos.get(i).getNombreAnexo(), fuente2));
                table4.addCell(celdaDA5);
                PdfPCell celdaDA6 = new PdfPCell(new Paragraph("Adjuntado", fuente2));
                table4.addCell(celdaDA6);
            }

            table4.setWidthPercentage(ConstantesDyCNumerico.VALOR_100);
            table4.setHorizontalAlignment(Element.ALIGN_LEFT);

            documento.add(table4);

        } catch (DocumentException e) {
            LOG.error(e.getMessage());
        }

    }

    public void crearTabla7(Font fuente1, Font fuente2, SimpleDateFormat formateadorFecha) {

        try {

            /**
             * tabla documentos adjuntos requeridos
             */
            documento.add(new Paragraph("  "));
            documento.add(new Paragraph("Documentos adjuntos requeridos", fuente1));
            documento.add(new Paragraph("  "));

            PdfPTable table5 = new PdfPTable(ConstantesDyCNumerico.VALOR_3);

            PdfPCell celdaDAR1 = new PdfPCell(new Paragraph(ConstantesDyC1.FECHA_REGISTRO, fuente1));
            table5.addCell(celdaDAR1);

            PdfPCell celdaDAR2 = new PdfPCell(new Paragraph("Nombre archivo", fuente1));
            table5.addCell(celdaDAR2);

            PdfPCell celdaDAR3 = new PdfPCell(new Paragraph(ConstantesDyC1.ESTADO, fuente1));
            table5.addCell(celdaDAR3);

            for (int i = 0; i < listaDocsAdjuntosRequeridos.size(); i++) {
                PdfPCell celdaDAR4
                        = new PdfPCell(new Paragraph(formateadorFecha.format(listaDocsAdjuntosRequeridos.get(i).getDyctReqInfoDTO().getFechaRegistro()).toString(),
                                        fuente2));
                table5.addCell(celdaDAR4);

                PdfPCell celdaDAR5
                        = new PdfPCell(new Paragraph(listaDocsAdjuntosRequeridos.get(i).getListaDyctArchivoInfReqDTO().get(0).getNombreArchivo(),
                                        fuente2));
                table5.addCell(celdaDAR5);

                PdfPCell celdaDAR6 = new PdfPCell(new Paragraph("Adjuntado", fuente2));
                table5.addCell(celdaDAR6);
            }

            table5.setWidthPercentage(ConstantesDyCNumerico.VALOR_100);
            table5.setHorizontalAlignment(Element.ALIGN_LEFT);

            documento.add(table5);

        } catch (DocumentException e) {
            LOG.error(e.getMessage());
        }

    }

    public void crearTabla8(Font fuente1, Font fuente2, SimpleDateFormat formateadorFecha) {

        try {

            /**
             * tabla documentos anexos requeridos
             */
            documento.add(new Paragraph("  "));
            documento.add(new Paragraph("Documentos anexos requeridos", fuente1));
            documento.add(new Paragraph("  "));

            PdfPTable table6 = new PdfPTable(ConstantesDyCNumerico.VALOR_3);

            PdfPCell celdaDANR1 = new PdfPCell(new Paragraph("Fecha de registro", fuente1));
            table6.addCell(celdaDANR1);

            PdfPCell celdaDANR2 = new PdfPCell(new Paragraph("Nombre archivo", fuente1));
            table6.addCell(celdaDANR2);

            PdfPCell celdaDANR3 = new PdfPCell(new Paragraph("Estado", fuente1));
            table6.addCell(celdaDANR3);

            for (int i = 0; i < listaDocsAnexosRequeridos.size(); i++) {
                PdfPCell celdaDANR4
                        = new PdfPCell(new Paragraph(formateadorFecha.format(listaDocsAnexosRequeridos.get(i).getDyctReqInfoDTO().getFechaRegistro()).toString(),
                                        fuente2));
                table6.addCell(celdaDANR4);
                PdfPCell celdaDANR5
                        = new PdfPCell(new Paragraph(listaDocsAnexosRequeridos.get(i).getNombreArchivo(), fuente2));
                table6.addCell(celdaDANR5);
                PdfPCell celdaDANR6 = new PdfPCell(new Paragraph("Adjuntado", fuente2));
                table6.addCell(celdaDANR6);
            }

            table6.setWidthPercentage(ConstantesDyCNumerico.VALOR_100);
            table6.setHorizontalAlignment(Element.ALIGN_LEFT);

            documento.add(table6);

        } catch (DocumentException e) {
            LOG.error(e.getMessage());
        }

    }

    public void crearTabla9(Font fuente1, Font fuente2, SimpleDateFormat formateadorFecha) {

        try {

            /**
             * tabla inconsistencias
             */
            documento.add(new Paragraph("  "));
            documento.add(new Paragraph("Inconsistencias de la solicitud de devolución", fuente1));
            documento.add(new Paragraph("  "));

            PdfPTable table2 = new PdfPTable(2);

            PdfPCell celda11 = new PdfPCell(new Paragraph("Fecha de creación", fuente1));
            table2.addCell(celda11);

            PdfPCell celda22 = new PdfPCell(new Paragraph("Descripción", fuente1));
            table2.addCell(celda22);

            for (int i = 0; i < listaInconsistenciasDev.size(); i++) {
                PdfPCell celda33
                        = new PdfPCell(new Paragraph(formateadorFecha.format(listaInconsistenciasDev.get(i).getFechaRegistro()).toString(),
                                        fuente2));
                table2.addCell(celda33);
                PdfPCell celda44
                        = new PdfPCell(new Paragraph(listaInconsistenciasDev.get(i).getDescripcion(), fuente2));
                table2.addCell(celda44);
            }

            table2.setWidthPercentage(ConstantesDyCNumerico.VALOR_100);
            table2.setHorizontalAlignment(Element.ALIGN_LEFT);

            documento.add(table2);

        } catch (DocumentException e) {
            LOG.error(e.getMessage());
        }

    }

    public void crearTablaArt22(Font fuente1, Font fuente2, SimpleDateFormat formateadorFecha) {

        try {

            /**
             * tabla articulo 22
             */
            UtileriasGestionSol utilerias = new UtileriasGestionSol();

            documento.add(new Paragraph("  "));
            documento.add(new Paragraph("Artículo 22", fuente1));
            documento.add(new Paragraph("  "));

            PdfPTable table22 = new PdfPTable(ConstantesDyCNumerico.VALOR_4);

            PdfPCell celda21 = new PdfPCell(new Paragraph("Número de orden", fuente1));
            table22.addCell(celda21);

            PdfPCell celda22 = new PdfPCell(new Paragraph("Fecha de registro", fuente1));
            table22.addCell(celda22);

            PdfPCell celda23 = new PdfPCell(new Paragraph("Fecha Inicio", fuente1));
            table22.addCell(celda23);

            PdfPCell celda24 = new PdfPCell(new Paragraph("Fecha Fin", fuente1));
            table22.addCell(celda24);

            for (int i = 0; i < listaArticulo22.size(); i++) {

                PdfPCell celda32 = new PdfPCell(new Paragraph(listaArticulo22.get(i).getFolio(), fuente2));

                table22.addCell(celda32);

                PdfPCell celda33
                        = new PdfPCell(new Paragraph(utilerias.obtenerCadenaParaFecha(listaArticulo22.get(i).getFechaRegistro(),
                                                formateadorFecha), fuente2));

                table22.addCell(celda33);

                PdfPCell celda44
                        = new PdfPCell(new Paragraph(utilerias.obtenerCadenaParaFecha(listaArticulo22.get(i).getFechaInicio(),
                                                formateadorFecha), fuente2));

                table22.addCell(celda44);

                PdfPCell celda55
                        = new PdfPCell(new Paragraph(utilerias.obtenerCadenaParaFecha(listaArticulo22.get(i).getFechaFin(),
                                                formateadorFecha), fuente2));

                table22.addCell(celda55);

            }

            table22.setWidthPercentage(ConstantesDyCNumerico.VALOR_100);
            table22.setHorizontalAlignment(Element.ALIGN_LEFT);

            documento.add(table22);

        } catch (DocumentException e) {
            LOG.error(e.getMessage());
        }

    }

    public void crearPDF() {

        try {

            SimpleDateFormat formateadorFecha = new SimpleDateFormat("dd/MM/yyyy");

            UtileriasGestionSol utilerias = new UtileriasGestionSol();

            documento
                    = new Document(PageSize.LETTER, ConstantesDyCNumerico.VALOR_80, ConstantesDyCNumerico.VALOR_80, ConstantesDyCNumerico.VALOR_75,
                            ConstantesDyCNumerico.VALOR_75);

            FileOutputStream ficheroPdf;
            ficheroPdf = new FileOutputStream("/siat/dyc/impresion.pdf");

            PdfWriter.getInstance(documento, ficheroPdf);

            Font fuente1 = new Font();
            fuente1.setStyle(Font.BOLD);
            fuente1.setSize(ConstantesDyCNumerico.VALOR_11);

            Font fuente2 = new Font();
            fuente2.setSize(ConstantesDyCNumerico.VALOR_9);

            documento.open();

            crearTabla1(fuente1, fuente2);
            crearTabla2(fuente1, fuente2);

            documento.add(new Paragraph("  "));

            crearTabla3(fuente1, fuente2, formateadorFecha);
            crearTabla4(fuente1, fuente2, formateadorFecha);
            crearTabla5(fuente1, fuente2, formateadorFecha);
            crearTabla6(fuente1, fuente2, formateadorFecha);
            crearTabla7(fuente1, fuente2, formateadorFecha);
            crearTabla8(fuente1, fuente2, formateadorFecha);
            crearTabla9(fuente1, fuente2, formateadorFecha);

            /**
             * tabla documentos gestion
             */
            documento.add(new Paragraph("  "));
            documento.add(new Paragraph("Documentos de gestión", fuente1));
            documento.add(new Paragraph("  "));

            PdfPTable tblGestion = new PdfPTable(ConstantesDyCNumerico.VALOR_4);

            PdfPCell celdaDG1 = new PdfPCell(new Paragraph("Fecha de registro", fuente1));
            tblGestion.addCell(celdaDG1);

            PdfPCell celdaDG7 = new PdfPCell(new Paragraph("Fecha de notificación", fuente1));
            tblGestion.addCell(celdaDG7);

            PdfPCell celdaDG2 = new PdfPCell(new Paragraph("Nombre documento", fuente1));
            tblGestion.addCell(celdaDG2);

            PdfPCell celdaDG3 = new PdfPCell(new Paragraph("Estado", fuente1));
            tblGestion.addCell(celdaDG3);

            for (int i = 0; i < listaDocsGestion.size(); i++) {
                PdfPCell celdaDG4
                        = new PdfPCell(new Paragraph(formateadorFecha.format(listaDocsGestion.get(i).getFechaRegistro()).toString(),
                                        fuente2));

                PdfPCell celdaDG8
                        = new PdfPCell(new Paragraph(utilerias.obtenerCadenaParaFecha(listaDocsGestion.get(i).getFechaNotificacion(),
                                                formateadorFecha), fuente2));

                tblGestion.addCell(celdaDG4);
                tblGestion.addCell(celdaDG8);
                PdfPCell celdaDG5 = new PdfPCell(new Paragraph(listaDocsGestion.get(i).getNombreArchivo(), fuente2));
                tblGestion.addCell(celdaDG5);
                PdfPCell celdaDG6 = new PdfPCell(new Paragraph("Aprobado", fuente2));
                tblGestion.addCell(celdaDG6);
            }

            tblGestion.setWidthPercentage(ConstantesDyCNumerico.VALOR_100);
            tblGestion.setHorizontalAlignment(Element.ALIGN_LEFT);

            documento.add(tblGestion);

            crearTablaArt22(fuente1, fuente2, formateadorFecha);

            documento.close();

        } catch (FileNotFoundException e) {
            LOG.error(e.getMessage());
        } catch (DocumentException e) {
            LOG.error(e.getMessage());
        }

    }

    public void downloadPDF() throws IOException {

        crearPDF();

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

        File file1 = new File("/siat/dyc", "impresion.pdf");
        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {

            input = new BufferedInputStream(new FileInputStream(file1), DEFAULT_BUFFER_SIZE);

            response.reset();
            response.setHeader("Content-Type", "application/pdf");
            response.setHeader("Content-Length", String.valueOf(file1.length()));
            response.setHeader("Content-Disposition", "inline; filename=\"" + "impresion.pdf" + "\"");
            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;

            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            output.flush();

        } finally {
            close(output);
            close(input);
        }

        facesContext.responseComplete();

    }

    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                LOG.error(e.getMessage());
            }
        }
    }

    public void init() {
        DyctSaldoIcepDTO objSaldoIcep = null;

        tieneFolio = Boolean.FALSE;
        mensajeFecha = EMPTY;
        mensajeFechaB = Boolean.FALSE;
        mensajeFechaError = Boolean.FALSE;
        nombreArchivo = EMPTY;
        inicializarVariables();

        registrarPistaAuditoria();

        mostrarPaneles();

        try {

            if (parametroRegresar == ConstantesDyCNumerico.VALOR_6) {

                objSaldoIcep = new DyctSaldoIcepDTO();
                objSaldoIcep.setIdSaldoIcep(bandejaDocumentosSolDTO.getIdSaldoIcep());

                remanenteHistorico = calcularSaldoRemanenteICEPService.execute(objSaldoIcep);

            }

            if (numControl.startsWith("DC")) {
                ocultarPanelArt22 = Boolean.TRUE;
            } else if (numControl.startsWith("AC") || numControl.startsWith("CC")) {
                ocultarPanelArt22 = false;
            }

            dyctContribuyenteDTO = consultarExpedienteService.buscarNumcontrol(numControl);

            consultarExpedienteDTO = consultarExpedienteService.buscarOrigenSaldo(numControl);

            expedienteDTO = consultarExpedienteService.buscarExpedienteNumControl(numControl);

            Map<String, Object> respuesta = getBussinesDelegate().obtenerInfoInicialConsulta(numControl);

            expedienteDTO.setSaldoicep((Double) respuesta.get("remanenteFavCargo"));

            llenarObjetoSolicitud();

            consultarDocumentos();

            consultarInconsistencias();

            consultarNotas();

            consultarTramites();

            consultarDeclaraciones();

            if (esIdEstado.equalsIgnoreCase(proceso + " " + ConstantesDictaminacionAutomatica.MODELO_SIVAD) || esIdEstado.equalsIgnoreCase(proceso + " " + ConstantesDictaminacionAutomatica.MODELO_MORSA)) {
                consultarMotivosRiesgo();
            }

            if (null != dyctContribuyenteDTO && null != dyctContribuyenteDTO.getDatosContribuyente()) {
                InputStreamReader inputStream
                        = new InputStreamReader(dyctContribuyenteDTO.getDatosContribuyente(), ConstantesDyC1.CODIFICACION_UTF8);
                JAXBContext jaxbContext = JAXBContext.newInstance(PersonaDTO.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                personaDTO = (PersonaDTO) jaxbUnmarshaller.unmarshal(inputStream);
            }

            llenarObjetos();

            if (null != personaDTO) {
                if (null != personaDTO.getPersonaIdentificacion()) {
                    personaIdentificacion = personaDTO.getPersonaIdentificacion();
                }
                if (null != personaDTO.getDomicilio()) {
                    domicilio = personaDTO.getDomicilio();
                }
            }

            if (null != personaDTO2 && null != personaDTO2.getPersonaIdentificacion()) {
                personaIdentificacion2 = personaDTO2.getPersonaIdentificacion();
            }

            this.getDataModel().setWrappedData(listaDocsAdjuntos);
            this.getDataModelAnexo().setWrappedData(listaAnexos);

            this.getDataModelDocumentoRequerido().setWrappedData(listaDocsAdjuntosRequeridos);
            this.getDataModelAnexoRequerido().setWrappedData(listaDocsAnexosRequeridos);

            this.getDataModelDocsGestion().setWrappedData(listaDocsGestion);
            this.getDataModelArt22().setWrappedData(listaArticulo22);

            if (accesoUsr.getRoles().contains("SAT_DYC_GEN_REP")) {
                tieneRolDYC008 = Boolean.TRUE;
            } else {
                tieneRolDYC008 = Boolean.FALSE;
            }

        } catch (JAXBException e) {
            LOG.error(e.getMessage());
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }

    }

    public void llenarObjetoSolicitud() {
        if (parametroRegresar == ConstantesDyCNumerico.VALOR_1 || parametroRegresar == ConstantesDyCNumerico.VALOR_6) {
            dycpSolicitudDTO = dycpSolicitudService.buscarNumcontrolComp(numControl);
        } else {
            dycpSolicitudDTO = dycpSolicitudService.buscarNumcontrol(numControl);
        }
    }

    public void llenarObjetos() {

        try {

            if (null != expedienteDTO) {
                if (null != expedienteDTO.getDatosALTEX()) {
                    gridALTEX = Boolean.TRUE;
                    InputStreamReader inputStream
                            = new InputStreamReader(expedienteDTO.getDatosALTEX(), ConstantesDyC1.CODIFICACION_UTF8);
                    JAXBContext jaxbContext3 = JAXBContext.newInstance(SpConsultarAltexDTO.class);
                    Unmarshaller jaxbUnmarshaller3 = jaxbContext3.createUnmarshaller();
                    spConsultarAltexDTO = (SpConsultarAltexDTO) jaxbUnmarshaller3.unmarshal(inputStream);
                }

                if (null != expedienteDTO.getDatosDIOT()) {
                    gridDIOT = Boolean.TRUE;
                    InputStreamReader inputStream
                            = new InputStreamReader(expedienteDTO.getDatosDIOT(), ConstantesDyC1.CODIFICACION_UTF8);
                    JAXBContext jaxbContext4 = JAXBContext.newInstance(DiotDTO.class);
                    Unmarshaller jaxbUnmarshaller4 = jaxbContext4.createUnmarshaller();
                    diotDTO = (DiotDTO) jaxbUnmarshaller4.unmarshal(inputStream);
                }

                if (null != expedienteDTO.getDatosRetenedorN()) {
                    gridRetenedor = Boolean.TRUE;
                    InputStreamReader inputStream
                            = new InputStreamReader(expedienteDTO.getDatosRetenedorN(), ConstantesDyC1.CODIFICACION_UTF8);
                    JAXBContext jaxbContext5 = JAXBContext.newInstance(PersonaDTO.class);
                    Unmarshaller jaxbUnmarshaller5 = jaxbContext5.createUnmarshaller();
                    personaDTO2 = (PersonaDTO) jaxbUnmarshaller5.unmarshal(inputStream);
                }
            }

        } catch (JAXBException e) {
            LOG.error(e.getMessage());
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    public void consultarDeclaraciones() {

        listaDeclaraciones = dyctDeclaracionService.buscarDeclaracionesNumControl(numControl);

        if (!listaDeclaraciones.isEmpty() && listaDeclaraciones.size() == ConstantesDyCNumerico.VALOR_1) {

            declaracion = listaDeclaraciones.get(ConstantesDyCNumerico.VALOR_0);

        } else if (!listaDeclaraciones.isEmpty() && listaDeclaraciones.size() == ConstantesDyCNumerico.VALOR_2) {

            gridDeclaracionAuxiliar = Boolean.TRUE;

            declaracion = listaDeclaraciones.get(ConstantesDyCNumerico.VALOR_0);
            declaracionAux = listaDeclaraciones.get(ConstantesDyCNumerico.VALOR_1);
        }
    }

    public void consultarNotas() {

        listaNotas = dyctNotasExpService.buscarNotasXNumControl(numControl);

        if (listaNotas.size() > ConstantesDyCNumerico.VALOR_5) {
            paginador5 = Boolean.TRUE;
        } else {
            paginador5 = false;
        }
    }

    public void consultarTramites() {
        listaArticulo22 = dyctFacultadesService.buscarFacultadesXNumControl(numControl);

        if (listaArticulo22.size() > ConstantesDyCNumerico.VALOR_5) {
            paginador8 = Boolean.TRUE;
        } else {
            paginador8 = false;
        }
    }

    /**
     * Se consulta la tabla de DYCA_SOLINCONSIST (DESCRIPCION y FECHAREGISTRO) y
     * el catalogo de inconsistencias DYCC_INCONSIST
     * (IDINCONSISTENCIA,DESCRIPCION )
     */
    public void consultarInconsistencias() {

        listaInconsistenciasDev = dyccInconsistenciaService.buscarSolicitudDev(numControl);
        /**
         * idInconsistencia = 1 contiene la descripcion: El contribuyente se
         * encuentra activo en el Padron casos a Verificar, se debe cambiar a:
         * Se ha detectado un credito fiscal relacionado al Contribuyente, que
         * se encuentra almacenado en la tabla relacionada (DYCA_SOLINCONSIST)
         */
        for (DycaSolInconsistDTO solicitudInconsistencias : listaInconsistenciasDev) {
            /**
             * Cuando el id de la inconsistencia no es 1, se muestra la
             * descripcion del catalogo de inconsistencias DYCC_INCONSIST,
             * DycaSolInconsistDTO contiene los datos de la tabla
             * DYCA_SOLINCONSIST
             */
            if (cambiarLeyenda(solicitudInconsistencias.getDyccInconsistDTO().getIdInconsistencia())) {
                int indexOf = listaInconsistenciasDev.indexOf(solicitudInconsistencias);
                String leyenda = "";
                /* tiene almacenado el campo: DYCC_INCONSIST.DESCRIPCION */
                leyenda = solicitudInconsistencias.getDyccInconsistDTO().getDescripcion();
                listaInconsistenciasDev.get(indexOf).setDescripcion(leyenda);
            }
        }

        if (listaInconsistenciasDev.size() > ConstantesDyCNumerico.VALOR_5) {
            paginador6 = Boolean.TRUE;
        } else {
            paginador6 = false;
        }

    }

    public boolean cambiarLeyenda(int inconsistenciaId) {
        LOG.info("cambiarLeyenda: inconsistenciaId " + inconsistenciaId);
        int tieneCreditoFiscal = ConstantesDyCNumerico.VALOR_1;
        int estaAmparado = ConstantesDyCNumerico.VALOR_9;
        int sinMedioDeContacto = ConstantesDyCNumerico.VALOR_10;

        boolean cambiar = true;
        if (inconsistenciaId == tieneCreditoFiscal) {
            LOG.info("tieneCreditoFiscal");
            cambiar = false;
        } else if (inconsistenciaId == estaAmparado) {
            LOG.info("estaAmparado");
            cambiar = false;
        } else if (inconsistenciaId == sinMedioDeContacto) {
            LOG.info("sinMedioDeContacto");
            cambiar = false;
        }

        return cambiar;
    }

    public void consultarMotivosRiesgo() {
        listaMotivosRiesgo = dyccInconsistenciaService.buscarMotivosRiesgo(numControl);

        if (!listaMotivosRiesgo.isEmpty()) {
            ocultarPanelMotRies = Boolean.TRUE;
            int acumulaInt = ConstantesDyCNumerico.VALOR_0;

            while (acumulaInt < listaMotivosRiesgo.size()) {
                String[] reglas = null;
                reglas = listaMotivosRiesgo.get(acumulaInt).getNumControl().split("\\|");
                listaMotivosRiesgo.get(acumulaInt).setRfc(reglas[1]);
                acumulaInt++;
            }

        }

    }

    public void consultarDocumentos() {

        listaDocsAdjuntos = dyctArchivoService.buscarDocsAdjuntos(numControl);

        if (listaDocsAdjuntos.size() > ConstantesDyCNumerico.VALOR_5) {
            paginador1 = Boolean.TRUE;
        } else {
            paginador1 = false;
        }

        listaAnexos = dyctAnexoService.buscarDocsAnexos(numControl);

        if (listaAnexos.size() > ConstantesDyCNumerico.VALOR_5) {
            paginador2 = Boolean.TRUE;
        } else {
            paginador2 = false;
        }

        listaDocsAdjuntosRequeridos = dyctInfoRequeridaService.buscarDocsAdjuntosRequeridos(numControl);

        if (listaDocsAdjuntosRequeridos.size() > ConstantesDyCNumerico.VALOR_5) {
            paginador3 = Boolean.TRUE;
        } else {
            paginador3 = false;
        }

        listaDocsAnexosRequeridos = dyctAnexoReqService.buscarDocsAnexosRequeridos(numControl);

        if (listaDocsAnexosRequeridos.size() > ConstantesDyCNumerico.VALOR_5) {
            paginador4 = Boolean.TRUE;
        } else {
            paginador4 = false;
        }

        listaDocsGestion = consultarExpedienteService.selecXNumControlEstAprobado(numControl);

        if (listaDocsGestion.size() > ConstantesDyCNumerico.VALOR_5) {
            paginador7 = Boolean.TRUE;
        } else {
            paginador7 = false;
        }

    }

    public void mostrarPaneles() {
        if (parametroRegresar == ConstantesDyCNumerico.VALOR_1 || parametroRegresar == ConstantesDyCNumerico.VALOR_6) {
            botonTramite2 = Boolean.TRUE;
            panelAnexos = Boolean.TRUE;
            panelInconsistencias = false;
        } else {
            botonTramite1 = Boolean.TRUE;
            panelAnexos = Boolean.TRUE;
            panelInconsistencias = Boolean.TRUE;
        }
    }

    public void inicializarVariables() {

        nombreCompletoEmpleado = "";
        rfcEmpleado = "";

        this.accesoUsr = serviceObtenerSesion.execute();

        nombreCompletoEmpleado = accesoUsr.getNombreCompleto();
        rfcEmpleado = accesoUsr.getUsuario();

        reemplaceMensajes = new HashMap<String, String>();

        declaracion = new DyctDeclaracionDTO();
        declaracionAux = new DyctDeclaracionDTO();

        this.setDataModel(new SIATDataModel<Serializable>());
        this.setDataModelAnexo(new SIATDataModel<Serializable>());
        this.setDataModelDocumentoRequerido(new SIATDataModel<Serializable>());
        this.setDataModelAnexoRequerido(new SIATDataModel<Serializable>());
        this.setDataModelDocsGestion(new SIATDataModel<Serializable>());
        this.setDataModelArt22(new SIATDataModel<Serializable>());

        archivo = new ArchivoCargaDescarga();

        varBotonDocs = Boolean.TRUE;
        varBotonAnexos = Boolean.TRUE;

        varBotonDocsRequeridos = Boolean.TRUE;
        varBotonAnexosRequeridos = Boolean.TRUE;

        varBotonGestion = Boolean.TRUE;
        varBotonAnexDoc = false;

        panelAnexos = false;
        panelInconsistencias = false;

        botonTramite1 = false;
        botonTramite2 = false;

        personaIdentificacion = null;
        personaIdentificacion2 = null;
        personaDTO = null;
        personaDTO2 = null;
        domicilio = null;
        expedienteDTO = null;
        spConsultarAltexDTO = null;
        diotDTO = null;
        declaracion = null;
        declaracionAux = null;
        dyctContribuyenteDTO = null;
        consultarExpedienteDTO = null;
        dycpSolicitudDTO = null;
        ocultarPanelMotRies = false;

        disabledBtnsRolConsulta();

    }

    private void disabledBtnsRolConsulta() {
        if (isRolConsultaAGSC()) {
            varBotonDocs = Boolean.TRUE;
            varBotonAnexos = Boolean.TRUE;
            varBotonDocsRequeridos = Boolean.TRUE;
            varBotonAnexosRequeridos = Boolean.TRUE;
            varBotonGestion = Boolean.TRUE;
            varBotonAnexDoc = Boolean.TRUE;
        }

    }

    private boolean isRolConsultaAGSC() {
        Integer centroCostos = Integer.parseInt(accesoUsr.getCentroCosto());
        if (accesoUsr.getRoles().contains(ConstantePerfilUsr.PERFIL_CONS_GENERAL)
                && centroCostos >= ConstantesIds.CENTRO_COSTOS_INI_AGSC
                && centroCostos <= ConstantesIds.CENTRO_COSTOS_FIN_AGSC) {
            return Boolean.TRUE;
        }

        return false;
    }

    public void registrarPistaAuditoria() {

        String hora = "";
        String fecha = "";

        Date fechaActual = new Date();

        DateFormat horaF = new SimpleDateFormat("HH:mm:ss");
        hora = horaF.format(fechaActual);

        DateFormat fechaF = new SimpleDateFormat("dd/MM/yyyy");
        fecha = fechaF.format(fechaActual);

        StringBuilder texto = new StringBuilder();
        texto.append(rfcEmpleado);
        texto.append(", ");
        texto.append(nombreCompletoEmpleado);

        reemplaceMensajes.put("<RFC, Nombres y Apellidos>", texto.toString());
        reemplaceMensajes.put("<NumeroDeControl>", numControl);
        reemplaceMensajes.put("<FechaDelSistema>", fecha);
        reemplaceMensajes.put("<HoraDelSistema>", hora);

        pistaAuditoria = new PistaAuditoriaVO();

        pistaAuditoria.setRemplaceMensajes(reemplaceMensajes);
        pistaAuditoria.setIdGrupoOperacion(ConstantesDyCNumerico.VALOR_35);
        pistaAuditoria.setIdProceso(Procesos.DYC00005);
        pistaAuditoria.setIdMensaje(ConstantesDyCNumerico.VALOR_109);
        pistaAuditoria.setMovimiento(ConstantesDyCNumerico.VALOR_530);
        pistaAuditoria.setIdentificador(numControl);

        this.registroPistasAuditoria.registrarPistaAuditoriaSTrans(pistaAuditoria);

    }

    public String dictaminarSolicitud() {

        gridDIOT = false;
        gridALTEX = false;

        gridRetenedor = false;

        gridDeclaracionAuxiliar = false;

        if (parametroRegresar == ConstantesDyCNumerico.VALOR_1) {

            return "dictaminarCaso";

        } else if (parametroRegresar == ConstantesDyCNumerico.VALOR_3
                || parametroRegresar == ConstantesDyCNumerico.VALOR_6) {

            return "aprobarDoc";

        } else if (parametroRegresar == ConstantesDyCNumerico.VALOR_4) {

            return "irInicioFacultades";

        } else if (parametroRegresar == ConstantesDyCNumerico.VALOR_5) {

            return "regresarABandejaRC";

        } else {

            /**
             * RequestContext.getCurrentInstance().execute("window.history.back();");
             */
            FacesContext context = FacesContext.getCurrentInstance();
            try {
                ServletContext sc = (ServletContext) context.getExternalContext().getContext();
                context.getExternalContext().redirect(sc.getContextPath()
                        + "/faces/resources/pages/gestionsol/dictaminarSolicitud.jsf");
            } catch (IOException e) {
                LOG.error(LOG_01 + e.getMessage());
            }

            /**
             * return "dictaminarSolicitud2";
             */
            return "";

        }

    }

    public String detalleTramite1() {
        RequestContext.getCurrentInstance().execute("window.scrollTo(0,0);");
        return "detalleTramite";

    }

    public String detalleTramite2() {
        RequestContext.getCurrentInstance().execute("window.scrollTo(0,0);");
        return "detalleTramite2";

    }

    public void mensaje() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(null, "Número de control no encontrado"));

    }

    public void clear() {
        this.nombreDocumento = "";
    }

    public void fileUpload() {

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
                contentType = contentType.substring(contentType.lastIndexOf(ConstantesTipoArchivo.PUNTO)).toLowerCase();
            }

            validaciones(contentType, tamanioMax, nomCorrecto1);

        } catch (StringIndexOutOfBoundsException e) {
            LOG.error(e.getMessage());
            FacesMessage errorTipo
                    = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: seleccione un archivo valido", null);
            FacesContext.getCurrentInstance().addMessage(null, errorTipo);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            FacesMessage errorArchivo
                    = new FacesMessage(FacesMessage.SEVERITY_ERROR, ConstantesDyC1.ERROR_ENVIAR_DOCUMENTO, null);
            FacesContext.getCurrentInstance().addMessage(null, errorArchivo);
        }
    }

    public void validaciones(String contentType, long tamanioMax, String nomCorrecto1) {

        String zip = ".zip";

        try {

            if (null == file) {

                FacesMessage errorNulo
                        = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: debe seleccionar un archivo", null);
                FacesContext.getCurrentInstance().addMessage(null, errorNulo);

            } else if (file.getSize() > tamanioMax) {

                FacesMessage errorTamanio
                        = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: el archivo " + nomCorrecto1
                                + " sobrepasa el peso permitido (4Mb)", null);
                FacesContext.getCurrentInstance().addMessage(null, errorTamanio);

            } else if (!contentType.equals(zip)) {

                FacesMessage errorTipo
                        = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: el archivo " + nomCorrecto1
                                + " no es de tipo .zip", null);
                FacesContext.getCurrentInstance().addMessage(null, errorTipo);

            } else {

                String nom = file.getFileName();
                String nomCorrecto = nom.substring(nom.lastIndexOf('\\') + 1, nom.length());

                if (nomCorrecto.length() > ConstantesDyCNumerico.VALOR_80) {

                    FacesMessage errorTamanio2
                            = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: tamaño de nombre de archivo inválido",
                                    null);
                    FacesContext.getCurrentInstance().addMessage(null, errorTamanio2);

                } else {

                    boolean existe = false;

                    for (DyctArchivoDTO objeto : listaDocsAdjuntos) {
                        if (objeto.getNombreArchivo().equals(nomCorrecto)) {
                            existe = Boolean.TRUE;
                            break;
                        }
                    }

                    if (!existe) {

                        guardarArchivo(nomCorrecto);

                    } else {

                        FacesMessage errorExiste
                                = new FacesMessage(FacesMessage.SEVERITY_ERROR, "El documento " + nomCorrecto
                                        + " ya existe.", null);
                        FacesContext.getCurrentInstance().addMessage(null, errorExiste);

                    }
                }
            }

        } catch (Exception e) {
            LOG.error(e.getMessage());
            FacesMessage errorArchivo
                    = new FacesMessage(FacesMessage.SEVERITY_ERROR, ConstantesDyC1.ERROR_DUCUMENTO, null);
            FacesContext.getCurrentInstance().addMessage(null, errorArchivo);
        }

    }

    public void guardarArchivo(String nomCorrecto) {

        try {

            StringBuilder ruta = new StringBuilder();

            ruta.append(ConstantesDyCURL.URL_DOCUMENTOS);
            ruta.append("/");
            ruta.append(String.valueOf(claveAdm).concat("/"));
            /**
             * ruta.append(rfcContribuyente.concat("/"));
             */
            ruta.append(Utilerias.corregirRFC(rfcContribuyente).concat("/"));

            if (folioAviso != null) {
                ruta.append(folioAviso.concat("/"));
            }

            ruta.append(numControl);
            ruta.append(ConstantesDyCURL.TIPO_ARCHIVO_ARCHIVOS);

            archivo.escribirArchivo(nomCorrecto, file.getInputstream(), ruta.toString(),
                    ConstantesDyCNumerico.VALOR_4096);

            DycpServicioDTO dycpServicioDTO = new DycpServicioDTO();
            dycpServicioDTO.setNumControl(numControl);

            DyctArchivoDTO dyctArchivoDTO1 = new DyctArchivoDTO();
            dyctArchivoDTO1.setNombreArchivo(nomCorrecto);
            dyctArchivoDTO1.setUrl(ruta.toString());
            dyctArchivoDTO1.setDescripcion(nombreDocumento.toUpperCase());
            dyctArchivoDTO1.setDycpServicioDTO(dycpServicioDTO);
            dyctArchivoDTO1.setFechaRegistro(new Date());
            dyctArchivoDTO1.setOcultoContribuyente(ConstantesValContribuyente.SI_OCULTO);
            dyctArchivoService.insertarDocumentoExpediente(dyctArchivoDTO1);

            listaDocsAdjuntos = dyctArchivoService.buscarDocsAdjuntos(numControl);
            this.getDataModel().setWrappedData(listaDocsAdjuntos);

            FacesMessage exitoArchivo
                    = new FacesMessage(FacesMessage.SEVERITY_INFO, "Archivo: " + nomCorrecto + " cargado con éxito.", null);
            FacesContext.getCurrentInstance().addMessage(null, exitoArchivo);

        } catch (IOException e) {
            LOG.error(e.getMessage());
            FacesMessage errorArchivo
                    = new FacesMessage(FacesMessage.SEVERITY_ERROR, ConstantesDyC1.ERROR_DUCUMENTO, null);
            FacesContext.getCurrentInstance().addMessage(null, errorArchivo);
        } catch (SIATException e) {
            LOG.error(e.getMessage());
            FacesMessage errorArchivo
                    = new FacesMessage(FacesMessage.SEVERITY_ERROR, ConstantesDyC1.ERROR_DUCUMENTO, null);
            FacesContext.getCurrentInstance().addMessage(null, errorArchivo);
        }

    }

    public void actualizaArchivo(String nomCorrecto) {

        try {

            StringBuilder ruta = new StringBuilder();

            ruta.append(ConstantesDyCURL.URL_DOCUMENTOS);
            ruta.append("/");
            ruta.append(String.valueOf(claveAdm).concat("/"));
            /**
             * ruta.append(rfcContribuyente.concat("/"));
             */
            ruta.append(Utilerias.corregirRFC(rfcContribuyente).concat("/"));

            if (folioAviso != null) {
                ruta.append(folioAviso.concat("/"));
            }

            ruta.append(numControl);
            ruta.append(ConstantesDyCURL.TIPO_ARCHIVO_ARCHIVOS);

            archivo.escribirArchivo(nomCorrecto, file.getInputstream(), ruta.toString(),
                    ConstantesDyCNumerico.VALOR_4096);

            DycpServicioDTO dycpServicioDTO = new DycpServicioDTO();
            dycpServicioDTO.setNumControl(numControl);

            DyctArchivoDTO dyctArchivoDTO1 = new DyctArchivoDTO();
            dyctArchivoDTO1.setNombreArchivo(nomCorrecto);
            dyctArchivoDTO1.setUrl(ruta.toString());
            dyctArchivoDTO1.setDescripcion(nombreDocumento.toUpperCase());
            dyctArchivoDTO1.setDycpServicioDTO(dycpServicioDTO);
            dyctArchivoDTO1.setFechaRegistro(new Date());
            dyctArchivoService.reactivaDocumentoExpediente(dyctArchivoDTO1);

            listaDocsAdjuntos = dyctArchivoService.buscarDocsAdjuntos(numControl);
            this.getDataModel().setWrappedData(listaDocsAdjuntos);

            FacesMessage exitoArchivo
                    = new FacesMessage(FacesMessage.SEVERITY_INFO, "Archivo: " + nomCorrecto + " cargado con éxito.", null);
            FacesContext.getCurrentInstance().addMessage(null, exitoArchivo);

        } catch (IOException e) {
            LOG.error(e.getMessage());
            FacesMessage errorArchivo
                    = new FacesMessage(FacesMessage.SEVERITY_ERROR, ConstantesDyC1.ERROR_DUCUMENTO, null);
            FacesContext.getCurrentInstance().addMessage(null, errorArchivo);
        } catch (SIATException e) {
            LOG.error(e.getMessage());
            FacesMessage errorArchivo
                    = new FacesMessage(FacesMessage.SEVERITY_ERROR, ConstantesDyC1.ERROR_DUCUMENTO, null);
            FacesContext.getCurrentInstance().addMessage(null, errorArchivo);
        }

    }

    public void downloadDocs() {

        StringBuilder ruta = new StringBuilder();

        ruta.append(dyctArchivoDTO.getUrl().concat("/"));
        ruta.append(dyctArchivoDTO.getNombreArchivo());

        File tempFile = new File(ruta.toString());

        byte[] contenido = null;
        try {
            contenido = IOUtils.toByteArray(tempFile.toURI());
        } catch (IOException e) {
            LOG.error(ConstantesDyC1.TEXTO_ERROR + e.getMessage());
        }

        fileDocs
                = new DefaultStreamedContent(new ByteArrayInputStream(contenido), new MimetypesFileTypeMap().getContentType(tempFile),
                        dyctArchivoDTO.getNombreArchivo());

    }

    public void borrarDocs() {

        StringBuilder ruta = new StringBuilder();

        ruta.append(dyctArchivoDTO.getUrl().concat("/"));
        ruta.append(dyctArchivoDTO.getNombreArchivo());

        File tempFile = new File(ruta.toString());
        try {
            dyctArchivoService.eliminarArchivoPorUrl(dyctArchivoDTO);
            if (tempFile.delete()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Se borró el documento: "
                                + dyctArchivoDTO.getNombreArchivo(), ""));
                listaDocsAdjuntos = dyctArchivoService.buscarDocsAdjuntos(numControl);
                if (listaDocsAdjuntos.size() > ConstantesDyCNumerico.VALOR_5) {
                    paginador1 = Boolean.TRUE;
                } else {
                    paginador1 = false;
                }
                this.getDataModel().setWrappedData(listaDocsAdjuntos);
            } else {
                LOG.error("No se pudo borrar el archivo en file system: " + tempFile.getPath());
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo borrar el documento seleccionado, intente nuevamente.",
                                ""));
            }
        } catch (SIATException e) {
            LOG.error("Falló al borrar el archivo en base de datos: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo borrar el documento seleccionado, intente nuevamente.",
                            ""));
        }

    }

    public void downloadAnexos() {

        StringBuilder ruta = new StringBuilder();

        ruta.append(consultarExpedienteVO.getUrl().concat("/"));
        ruta.append(consultarExpedienteVO.getNombreArchivo());

        File tempFile = new File(ruta.toString());

        byte[] contenido = null;
        try {
            contenido = IOUtils.toByteArray(tempFile.toURI());
        } catch (IOException e) {
            LOG.error(ConstantesDyC1.TEXTO_ERROR + e.getMessage());
        }

        fileAnexos
                = new DefaultStreamedContent(new ByteArrayInputStream(contenido), new MimetypesFileTypeMap().getContentType(tempFile),
                        consultarExpedienteVO.getNombreArchivo());

    }

    public void downloadDocsGestion() {

        StringBuilder ruta = new StringBuilder();

        ruta.append(solicitudAdministrarSolVO.getUrl().concat("/"));
        ruta.append(solicitudAdministrarSolVO.getNombreArchivo());

        File tempFile = new File(ruta.toString());

        byte[] contenido = null;
        try {
            contenido = IOUtils.toByteArray(tempFile.toURI());
        } catch (IOException e) {
            LOG.error(ConstantesDyC1.TEXTO_ERROR + e.getMessage());
        }

        fileDocsGestion
                = new DefaultStreamedContent(new ByteArrayInputStream(contenido), new MimetypesFileTypeMap().getContentType(tempFile),
                        solicitudAdministrarSolVO.getNombreArchivo());

    }

    public void downloadDocsRequeridos() {

        StringBuilder ruta = new StringBuilder();

        ruta.append(dyctInfoRequeridaDTO.getListaDyctArchivoInfReqDTO().get(0).getUrl().concat("/"));
        ruta.append(dyctInfoRequeridaDTO.getListaDyctArchivoInfReqDTO().get(0).getNombreArchivo());

        File tempFile = new File(ruta.toString());

        byte[] contenido = null;
        try {
            contenido = IOUtils.toByteArray(tempFile.toURI());
        } catch (IOException e) {
            LOG.error(ConstantesDyC1.TEXTO_ERROR + e.getMessage());
        }

        fileDocsRequeridos
                = new DefaultStreamedContent(new ByteArrayInputStream(contenido), new MimetypesFileTypeMap().getContentType(tempFile),
                        dyctInfoRequeridaDTO.getListaDyctArchivoInfReqDTO().get(0).getNombreArchivo());

    }

    public void downloadAnexosRequeridos() {

        StringBuilder ruta = new StringBuilder();

        ruta.append(dyctAnexoReqDTO.getUrl().concat("/"));
        ruta.append(dyctAnexoReqDTO.getNombreArchivo());

        File tempFile = new File(ruta.toString());

        byte[] contenido = null;
        try {
            contenido = IOUtils.toByteArray(tempFile.toURI());
        } catch (IOException e) {
            LOG.error(ConstantesDyC1.TEXTO_ERROR + e.getMessage());
        }

        fileAnexosRequeridos
                = new DefaultStreamedContent(new ByteArrayInputStream(contenido), new MimetypesFileTypeMap().getContentType(tempFile),
                        dyctAnexoReqDTO.getNombreArchivo());

    }

    public void onRowSelectDocs() {
        varBotonDocs = isRolConsultaAGSC();
    }

    public void onRowSelectAnexos() {
        varBotonAnexos = isRolConsultaAGSC();

    }

    public void onRowSelectDocsRequeridos() {
        varBotonDocsRequeridos = isRolConsultaAGSC();

    }

    public void onRowSelectAnexosRequeridos() {
        varBotonAnexosRequeridos = isRolConsultaAGSC();

    }

    public void onRowCancel(RowEditEvent event) {

    }

    public void onRowEdit(RowEditEvent event) {

        int compare;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        mensajeFechaError = Boolean.FALSE;
        SolicitudAdministrarSolVO solicitud = (SolicitudAdministrarSolVO) event.getObject();
        mensajeFecha = EMPTY;

        FacesContext contextX = FacesContext.getCurrentInstance();
        compare = solicitud.getFechaRegistro().compareTo(solicitud.getFechaNotificacion());

        if (compare > 0) {
            if (!solicitud.isTieneFolioNyV()) {

                ((SolicitudAdministrarSolVO) event.getObject()).setFolio(null);
            }
            if (!solicitud.isTieneFechaNotificacion()) {
                ((SolicitudAdministrarSolVO) event.getObject()).setFechaNotificacion(null);
            }

            mensajeFechaB = Boolean.FALSE;

            mensajeFechaError = Boolean.TRUE;
        } else {

            mensajeFecha = sdf.format(solicitud.getFechaNotificacion());
            folioNyV = ((SolicitudAdministrarSolVO) event.getObject()).getFolio();
            fechaNyV = mensajeFecha;
            numControlDoc = solicitud.getNumControlDoc();
            mensajeFechaB = Boolean.TRUE;
            nombreArchivo = ((SolicitudAdministrarSolVO) event.getObject()).getNombreArchivo();

        }

        try {
            ServletContext sc = (ServletContext) contextX.getExternalContext().getContext();
            contextX.getExternalContext().redirect(sc.getContextPath()
                    + "/faces/resources/pages/gestionsol/consultarExpediente.jsf");
        } catch (IOException e) {
            LOG.error(LOG_01 + e.getMessage());
        }

    }

    public void salir() {
        FacesContext contextX = FacesContext.getCurrentInstance();
        mensajeFechaError = Boolean.FALSE;
        try {
            ServletContext sc = (ServletContext) contextX.getExternalContext().getContext();
            contextX.getExternalContext().redirect(sc.getContextPath()
                    + "/faces/resources/pages/gestionsol/consultarExpediente.jsf");
        } catch (IOException e) {
            LOG.error(LOG_01 + e.getMessage());
        }
    }

    /**
     * Actualiza el estado del requerimiento y si este es vencido se desiste el
     * tramite.
     *
     * @throws SIATException
     */
    private void actualizaEstados(boolean esCompensacion, boolean esPrimerReqActualizable) throws SIATException {
        if (nombreArchivo.contains("2do")) {
            if (!esCompensacion) {
                //Se actualiza el estado del requerimiento y se desiste el tramite tipo compensacion
                administrarSolicitudesService.actualizarEstados(numControl, numControlDoc, esPrimerReqActualizable);
            } else {
                administrarSolicitudesService.actualizarEdoCompDesistido(numControl, numControlDoc, esPrimerReqActualizable);
            }
        } else {
            //Valida si se tiene mas de un requerimiento.
            boolean listaDocsGestionVacia = listaDocsGestion != null && !listaDocsGestion.isEmpty();
            if (listaDocsGestionVacia && listaDocsGestion.size() > 1) {
                //Si se tiene mas de un requerimiento se actualiza solo el estado del requerimiento.
                administrarSolicitudesService.actualizarEstadoReq(numControlDoc);
            } else {
                if (!esCompensacion) {
                    //Se actualiza el estado del requerimiento y se desiste el tramite tipo compensacion
                    administrarSolicitudesService.actualizarEstados(numControl, numControlDoc, esPrimerReqActualizable);
                } else {
                    administrarSolicitudesService.actualizarEdoCompDesistido(numControl, numControlDoc, esPrimerReqActualizable);
                }
            }
        }
    }

    private void validaExisteResolucion(boolean esAcualizableRequ) throws SIATException {
        //Valida si es un tramite de compensacion
        boolean esCompensacion = parametroRegresar == ConstantesDyCNumerico.VALOR_1 || parametroRegresar == ConstantesDyCNumerico.VALOR_6;
        //Busca si el tramite tiene una resolucion
        boolean tieneResolucion = administrarSolicitudesService.existeResolucion(numControl, esCompensacion);
        if (tieneResolucion) {
            //Solo actualiza el estado del requerimiento pero no desiste el tramite.
            administrarSolicitudesService.actualizarEstadoReq(numControlDoc);
        } else {
            actualizaEstados(esCompensacion, esAcualizableRequ);
        }
    }

    public void saveNyV() {
        AdministrarSolicitudesVO administrarSolicitudesVO = null;
        boolean esActualizable = true;
        try {
            //Acutaliza la fecha de notificacion
            consultarExpedienteService.actualizarFolioNYVFechaNoti(numControlDoc, numControl, folioNyV, fechaNyV);
            //Consulta la informacion del requerimiento
            administrarSolicitudesVO = administrarSolicitudesService.obtenerDiferencia(numControlDoc);
            //Valida si es el primer requerimiento, mediante el nombre
            boolean esPrimerReq = nombreArchivo.contains("1er");
                //Le suma a la fecha notificacion los 21 o 11 dias habiles dependiendo de que requerimeinto sea
            //si es el requerimiento 1 se suman 21 dias si es el requerimiento 2 se suman 11 dias
            Date fechaResultado = getDiaHabilService().buscarDiaFederalRecaudacion(administrarSolicitudesVO.getFechaNotificacion(), esPrimerReq ? ConstantesDyCNumerico.VALOR_21 : ConstantesDyCNumerico.VALOR_11);
            //Valida si la fecha de solventacion es mayor a la fecha resultado
            Date fechaHoy = new Date();
            SimpleDateFormat formatResultado = new SimpleDateFormat(ConstantesAdministrarSolicitud.FORMATO_FECHA);
            String fResultado = formatResultado.format(fechaResultado);

            SimpleDateFormat formatHoy = new SimpleDateFormat(ConstantesAdministrarSolicitud.FORMATO_FECHA);
            String fHoy = formatHoy.format(fechaHoy);
            
            fechaResultado = formatResultado.parse(fResultado);
            fechaHoy = formatHoy.parse(fHoy);
            
            if (administrarSolicitudesVO.getFechaSolventacion() == null) {
                if (fechaHoy.after(fechaResultado)) {
                    validaExisteResolucion(esActualizable);
                }
            } else {
                if (nombreArchivo.contains("1er")) {
                    esActualizable = false;
                }
                if (administrarSolicitudesVO.getFechaSolventacion().after(fechaResultado)) {
                    validaExisteResolucion(esActualizable);
                }
            }

            mensajeFechaB = Boolean.FALSE;
            mensajeFechaError = Boolean.FALSE;
            tieneRolDYC008 = Boolean.FALSE;

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Se guardó con éxito la información capturada"));
        }catch (ParseException e) {
                LOG.error("ERROR: " + e.getMessage());
            }
        catch (SIATException exception) {
            LOG.error(exception.getMessage(), exception);
        }
    }

    public void cancelar() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            ServletContext sc = (ServletContext) context.getExternalContext().getContext();
            context.getExternalContext().redirect(sc.getContextPath()
                    + "/faces/resources/pages/gestionsol/dycAdministracion.jsf");
        } catch (IOException e) {
            LOG.error(LOG_01 + e.getMessage());
        }
    }

    public void onRowSelectGestion() {
        varBotonGestion = isRolConsultaAGSC();
        FacesContext context = FacesContext.getCurrentInstance();
        ServletContext sc;
        if (solicitudAdministrarSolVO == null) {
            return;
        }

        /* si tiene folioNyV y fecha de notificacion, no permite editar */
        if (solicitudAdministrarSolVO.isTieneFolioNyV() && solicitudAdministrarSolVO.isTieneFechaNotificacion()) {

            sc = (ServletContext) context.getExternalContext().getContext();
            try {
                context.getExternalContext().redirect(sc.getContextPath() + "/faces/resources/pages/gestionsol/consultarExpediente.jsf");
            } catch (IOException ex) {
                LOG.error(LOG_01 + ex.getMessage());
            }
        }
    }

    public void setDyctContribuyenteDTO(DyctContribuyenteDTO dyctContribuyenteDTO) {
        this.dyctContribuyenteDTO = dyctContribuyenteDTO;
    }

    public DyctContribuyenteDTO getDyctContribuyenteDTO() {
        return dyctContribuyenteDTO;
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

    public void setDomicilio(PersonaUbicacionDTO domicilio) {
        this.domicilio = domicilio;
    }

    public PersonaUbicacionDTO getDomicilio() {
        return domicilio;
    }

    public void setConsultarExpedienteService(ConsultarExpedienteService consultarExpedienteService) {
        this.consultarExpedienteService = consultarExpedienteService;
    }

    public ConsultarExpedienteService getConsultarExpedienteService() {
        return consultarExpedienteService;
    }

    public void setDycpSolicitudService(DycpSolicitudService dycpSolicitudService) {
        this.dycpSolicitudService = dycpSolicitudService;
    }

    public DycpSolicitudService getDycpSolicitudService() {
        return dycpSolicitudService;
    }

    public void setDyccInconsistenciaService(DyccInconsistenciaService dyccInconsistenciaService) {
        this.dyccInconsistenciaService = dyccInconsistenciaService;
    }

    public DyccInconsistenciaService getDyccInconsistenciaService() {
        return dyccInconsistenciaService;
    }

    public void setExpedienteDTO(ExpedienteDTO expedienteDTO) {
        this.expedienteDTO = expedienteDTO;
    }

    public ExpedienteDTO getExpedienteDTO() {
        return expedienteDTO;
    }

    public void setDyctDeclaracionService(DyctDeclaracionService dyctDeclaracionService) {
        this.dyctDeclaracionService = dyctDeclaracionService;
    }

    public DyctDeclaracionService getDyctDeclaracionService() {
        return dyctDeclaracionService;
    }

    public void setListaDeclaraciones(List<DyctDeclaracionDTO> listaDeclaraciones) {
        this.listaDeclaraciones = listaDeclaraciones;
    }

    public List<DyctDeclaracionDTO> getListaDeclaraciones() {
        return listaDeclaraciones;
    }

    public void setMsgGeneral(String msgGeneral) {
        this.msgGeneral = msgGeneral;
    }

    public String getMsgGeneral() {
        return msgGeneral;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public void setDyctAnexoService(DyctAnexoService dyctAnexoService) {
        this.dyctAnexoService = dyctAnexoService;
    }

    public DyctAnexoService getDyctAnexoService() {
        return dyctAnexoService;
    }

    public void setFileDocs(StreamedContent fileDocs) {
        this.fileDocs = fileDocs;
    }

    public StreamedContent getFileDocs() {
        return fileDocs;
    }

    public void setFileAnexos(StreamedContent fileAnexos) {
        this.fileAnexos = fileAnexos;
    }

    public StreamedContent getFileAnexos() {
        return fileAnexos;
    }

    public void setVarBotonDocs(boolean varBotonDocs) {
        this.varBotonDocs = varBotonDocs;
    }

    public boolean isVarBotonDocs() {
        return varBotonDocs;
    }

    public void setVarBotonAnexos(boolean varBotonAnexos) {
        this.varBotonAnexos = varBotonAnexos;
    }

    public boolean isVarBotonAnexos() {
        return varBotonAnexos;
    }

    public void setAccesoUsr(AccesoUsr accesoUsr) {
        this.accesoUsr = accesoUsr;
    }

    public AccesoUsr getAccesoUsr() {
        return accesoUsr;
    }

    public void setSpConsultarAltexDTO(SpConsultarAltexDTO spConsultarAltexDTO) {
        this.spConsultarAltexDTO = spConsultarAltexDTO;
    }

    public SpConsultarAltexDTO getSpConsultarAltexDTO() {
        return spConsultarAltexDTO;
    }

    public void setDiotDTO(DiotDTO diotDTO) {
        this.diotDTO = diotDTO;
    }

    public DiotDTO getDiotDTO() {
        return diotDTO;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setGridDIOT(boolean gridDIOT) {
        this.gridDIOT = gridDIOT;
    }

    public boolean isGridDIOT() {
        return gridDIOT;
    }

    public void setGridALTEX(boolean gridALTEX) {
        this.gridALTEX = gridALTEX;
    }

    public boolean isGridALTEX() {
        return gridALTEX;
    }

    public void setDyctArchivoService(DyctArchivoService dyctArchivoService) {
        this.dyctArchivoService = dyctArchivoService;
    }

    public DyctArchivoService getDyctArchivoService() {
        return dyctArchivoService;
    }

    public void setListaDocsAdjuntos(List<DyctArchivoDTO> listaDocsAdjuntos) {
        this.listaDocsAdjuntos = listaDocsAdjuntos;
    }

    public List<DyctArchivoDTO> getListaDocsAdjuntos() {
        return listaDocsAdjuntos;
    }

    public void setDyctArchivoDTO(DyctArchivoDTO dyctArchivoDTO) {
        this.dyctArchivoDTO = dyctArchivoDTO;
    }

    public DyctArchivoDTO getDyctArchivoDTO() {
        return dyctArchivoDTO;
    }

    public void setParametroRegresar(int parametroRegresar) {
        this.parametroRegresar = parametroRegresar;
    }

    public int getParametroRegresar() {
        return parametroRegresar;
    }

    public void setPanelAnexos(boolean panelAnexos) {
        this.panelAnexos = panelAnexos;
    }

    public boolean isPanelAnexos() {
        return panelAnexos;
    }

    public void setPanelInconsistencias(boolean panelInconsistencias) {
        this.panelInconsistencias = panelInconsistencias;
    }

    public boolean isPanelInconsistencias() {
        return panelInconsistencias;
    }

    public void setBotonTramite1(boolean botonTramite1) {
        this.botonTramite1 = botonTramite1;
    }

    public boolean isBotonTramite1() {
        return botonTramite1;
    }

    public void setBotonTramite2(boolean botonTramite2) {
        this.botonTramite2 = botonTramite2;
    }

    public boolean isBotonTramite2() {
        return botonTramite2;
    }

    public void setDyctNotasExpService(DyctNotasExpService dyctNotasExpService) {
        this.dyctNotasExpService = dyctNotasExpService;
    }

    public DyctNotasExpService getDyctNotasExpService() {
        return dyctNotasExpService;
    }

    public void setListaInconsistenciasDev(List<DycaSolInconsistDTO> listaInconsistenciasDev) {
        this.listaInconsistenciasDev = listaInconsistenciasDev;
    }

    public List<DycaSolInconsistDTO> getListaInconsistenciasDev() {
        return listaInconsistenciasDev;
    }

    public void setArchivo(ArchivoCargaDescarga archivo) {
        this.archivo = archivo;
    }

    public ArchivoCargaDescarga getArchivo() {
        return archivo;
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

    public void setListaNotas(List<DyctNotaDTO> listaNotas) {
        this.listaNotas = listaNotas;
    }

    public List<DyctNotaDTO> getListaNotas() {
        return listaNotas;
    }

    public void setListaAnexos(List<ConsultarExpedienteVO> listaAnexos) {
        this.listaAnexos = listaAnexos;
    }

    public List<ConsultarExpedienteVO> getListaAnexos() {
        return listaAnexos;
    }

    public void setConsultarExpedienteVO(ConsultarExpedienteVO consultarExpedienteVO) {
        this.consultarExpedienteVO = consultarExpedienteVO;
    }

    public ConsultarExpedienteVO getConsultarExpedienteVO() {
        return consultarExpedienteVO;
    }

    public void setDycpSolicitudDTO(SolicitudConsultarExpedienteVO dycpSolicitudDTO) {
        this.dycpSolicitudDTO = dycpSolicitudDTO;
    }

    public SolicitudConsultarExpedienteVO getDycpSolicitudDTO() {
        return dycpSolicitudDTO;
    }

    public void setConsultarExpedienteDTO(DeclaracionConsultarExpedienteVO consultarExpedienteDTO) {
        this.consultarExpedienteDTO = consultarExpedienteDTO;
    }

    public DeclaracionConsultarExpedienteVO getConsultarExpedienteDTO() {
        return consultarExpedienteDTO;
    }

    public void setGridRetenedor(boolean gridRetenedor) {
        this.gridRetenedor = gridRetenedor;
    }

    public boolean isGridRetenedor() {
        return gridRetenedor;
    }

    public void setDeclaracion(DyctDeclaracionDTO declaracion) {
        this.declaracion = declaracion;
    }

    public DyctDeclaracionDTO getDeclaracion() {
        return declaracion;
    }

    public void setDeclaracionAux(DyctDeclaracionDTO declaracionAux) {
        this.declaracionAux = declaracionAux;
    }

    public DyctDeclaracionDTO getDeclaracionAux() {
        return declaracionAux;
    }

    public void setGridDeclaracionAuxiliar(boolean gridDeclaracionAuxiliar) {
        this.gridDeclaracionAuxiliar = gridDeclaracionAuxiliar;
    }

    public boolean isGridDeclaracionAuxiliar() {
        return gridDeclaracionAuxiliar;
    }

    public void setPersonaDTO2(PersonaDTO personaDTO2) {
        this.personaDTO2 = personaDTO2;
    }

    public PersonaDTO getPersonaDTO2() {
        return personaDTO2;
    }

    public void setPersonaIdentificacion2(PersonaIdentificacionDTO personaIdentificacion2) {
        this.personaIdentificacion2 = personaIdentificacion2;
    }

    public PersonaIdentificacionDTO getPersonaIdentificacion2() {
        return personaIdentificacion2;
    }

    public void setImporteSolicitado(BigDecimal importeSolicitado) {
        this.importeSolicitado = importeSolicitado;
    }

    public BigDecimal getImporteSolicitado() {
        return importeSolicitado;
    }

    public void setDyctInfoRequeridaService(DyctInfoRequeridaService dyctInfoRequeridaService) {
        this.dyctInfoRequeridaService = dyctInfoRequeridaService;
    }

    public DyctInfoRequeridaService getDyctInfoRequeridaService() {
        return dyctInfoRequeridaService;
    }

    public void setListaDocsAdjuntosRequeridos(List<DyctInfoRequeridaDTO> listaDocsAdjuntosRequeridos) {
        this.listaDocsAdjuntosRequeridos = listaDocsAdjuntosRequeridos;
    }

    public List<DyctInfoRequeridaDTO> getListaDocsAdjuntosRequeridos() {
        return listaDocsAdjuntosRequeridos;
    }

    public void setDyctAnexoReqService(DyctAnexoReqService dyctAnexoReqService) {
        this.dyctAnexoReqService = dyctAnexoReqService;
    }

    public DyctAnexoReqService getDyctAnexoReqService() {
        return dyctAnexoReqService;
    }

    public void setListaDocsAnexosRequeridos(List<DyctAnexoReqDTO> listaDocsAnexosRequeridos) {
        this.listaDocsAnexosRequeridos = listaDocsAnexosRequeridos;
    }

    public List<DyctAnexoReqDTO> getListaDocsAnexosRequeridos() {
        return listaDocsAnexosRequeridos;
    }

    public void setPaginador1(boolean paginador1) {
        this.paginador1 = paginador1;
    }

    public boolean isPaginador1() {
        return paginador1;
    }

    public void setPaginador2(boolean paginador2) {
        this.paginador2 = paginador2;
    }

    public boolean isPaginador2() {
        return paginador2;
    }

    public void setPaginador3(boolean paginador3) {
        this.paginador3 = paginador3;
    }

    public boolean isPaginador3() {
        return paginador3;
    }

    public void setPaginador4(boolean paginador4) {
        this.paginador4 = paginador4;
    }

    public boolean isPaginador4() {
        return paginador4;
    }

    public void setFileDocsRequeridos(StreamedContent fileDocsRequeridos) {
        this.fileDocsRequeridos = fileDocsRequeridos;
    }

    public StreamedContent getFileDocsRequeridos() {
        return fileDocsRequeridos;
    }

    public void setFileAnexosRequeridos(StreamedContent fileAnexosRequeridos) {
        this.fileAnexosRequeridos = fileAnexosRequeridos;
    }

    public StreamedContent getFileAnexosRequeridos() {
        return fileAnexosRequeridos;
    }

    public void setVarBotonDocsRequeridos(boolean varBotonDocsRequeridos) {
        this.varBotonDocsRequeridos = varBotonDocsRequeridos;
    }

    public boolean isVarBotonDocsRequeridos() {
        return varBotonDocsRequeridos;
    }

    public void setVarBotonAnexosRequeridos(boolean varBotonAnexosRequeridos) {
        this.varBotonAnexosRequeridos = varBotonAnexosRequeridos;
    }

    public boolean isVarBotonAnexosRequeridos() {
        return varBotonAnexosRequeridos;
    }

    public void setDyctInfoRequeridaDTO(DyctInfoRequeridaDTO dyctInfoRequeridaDTO) {
        this.dyctInfoRequeridaDTO = dyctInfoRequeridaDTO;
    }

    public DyctInfoRequeridaDTO getDyctInfoRequeridaDTO() {
        return dyctInfoRequeridaDTO;
    }

    public void setDyctAnexoReqDTO(DyctAnexoReqDTO dyctAnexoReqDTO) {
        this.dyctAnexoReqDTO = dyctAnexoReqDTO;
    }

    public DyctAnexoReqDTO getDyctAnexoReqDTO() {
        return dyctAnexoReqDTO;
    }

    public void setPaginador5(boolean paginador5) {
        this.paginador5 = paginador5;
    }

    public boolean isPaginador5() {
        return paginador5;
    }

    public void setPaginador6(boolean paginador6) {
        this.paginador6 = paginador6;
    }

    public boolean isPaginador6() {
        return paginador6;
    }

    public void setPistaAuditoria(PistaAuditoriaVO pistaAuditoria) {
        this.pistaAuditoria = pistaAuditoria;
    }

    public PistaAuditoriaVO getPistaAuditoria() {
        return pistaAuditoria;
    }

    public void setNombreCompletoEmpleado(String nombreCompletoEmpleado) {
        this.nombreCompletoEmpleado = nombreCompletoEmpleado;
    }

    public String getNombreCompletoEmpleado() {
        return nombreCompletoEmpleado;
    }

    public void setRfcEmpleado(String rfcEmpleado) {
        this.rfcEmpleado = rfcEmpleado;
    }

    public String getRfcEmpleado() {
        return rfcEmpleado;
    }

    public void setReemplaceMensajes(Map<String, String> reemplaceMensajes) {
        this.reemplaceMensajes = reemplaceMensajes;
    }

    public Map<String, String> getReemplaceMensajes() {
        return reemplaceMensajes;
    }

    public void setRegistroPistasAuditoria(RegistroPistasAuditoria registroPistasAuditoria) {
        this.registroPistasAuditoria = registroPistasAuditoria;
    }

    public RegistroPistasAuditoria getRegistroPistasAuditoria() {
        return registroPistasAuditoria;
    }

    public String getFolioAviso() {
        return folioAviso;
    }

    public void setFolioAviso(String folioAviso) {
        this.folioAviso = folioAviso;
    }

    public void setListaDocsGestion(List<SolicitudAdministrarSolVO> listaDocsGestion) {
        this.listaDocsGestion = listaDocsGestion;
    }

    public List<SolicitudAdministrarSolVO> getListaDocsGestion() {
        return listaDocsGestion;
    }

    public void setPaginador7(boolean paginador7) {
        this.paginador7 = paginador7;
    }

    public boolean isPaginador7() {
        return paginador7;
    }

    public void setVarBotonGestion(boolean varBotonGestion) {
        this.varBotonGestion = varBotonGestion;
    }

    public boolean isVarBotonGestion() {
        return varBotonGestion;
    }

    public boolean isVarBotonAnexDoc() {
        return varBotonAnexDoc;
    }

    public void setVarBotonAnexDoc(boolean varBotonAnexDoc) {
        this.varBotonAnexDoc = varBotonAnexDoc;
    }

    public void setDyctDocumentoDTO(DyctDocumentoDTO dyctDocumentoDTO) {
        this.dyctDocumentoDTO = dyctDocumentoDTO;
    }

    public DyctDocumentoDTO getDyctDocumentoDTO() {
        return dyctDocumentoDTO;
    }

    public void setFileDocsGestion(StreamedContent fileDocsGestion) {
        this.fileDocsGestion = fileDocsGestion;
    }

    public StreamedContent getFileDocsGestion() {
        return fileDocsGestion;
    }

    public void setDocumento(Document documento) {
        this.documento = documento;
    }

    public Document getDocumento() {
        return documento;
    }

    public void setDyctFacultadesService(DyctFacultadesService dyctFacultadesService) {
        this.dyctFacultadesService = dyctFacultadesService;
    }

    public DyctFacultadesService getDyctFacultadesService() {
        return dyctFacultadesService;
    }

    public void setListaArticulo22(List<DyctFacultadesDTO> listaArticulo22) {
        this.listaArticulo22 = listaArticulo22;
    }

    public List<DyctFacultadesDTO> getListaArticulo22() {
        return listaArticulo22;
    }

    public void setPaginador8(boolean paginador8) {
        this.paginador8 = paginador8;
    }

    public boolean isPaginador8() {
        return paginador8;
    }

    public void setSolicitudAdministrarSolVO(SolicitudAdministrarSolVO solicitudAdministrarSolVO) {
        this.solicitudAdministrarSolVO = solicitudAdministrarSolVO;
    }

    public SolicitudAdministrarSolVO getSolicitudAdministrarSolVO() {
        return solicitudAdministrarSolVO;
    }

    public void setRemanenteHistorico(BigDecimal remanenteHistorico) {
        this.remanenteHistorico = remanenteHistorico;
    }

    public BigDecimal getRemanenteHistorico() {
        return remanenteHistorico;
    }

    public void setBandejaDocumentosSolDTO(BandejaDocumentosDTO bandejaDocumentosSolDTO) {
        this.bandejaDocumentosSolDTO = bandejaDocumentosSolDTO;
    }

    public BandejaDocumentosDTO getBandejaDocumentosSolDTO() {
        return bandejaDocumentosSolDTO;
    }

    public void setCalcularSaldoRemanenteICEPService(CalcularSaldoRemanenteICEPService calcularSaldoRemanenteICEPService) {
        this.calcularSaldoRemanenteICEPService = calcularSaldoRemanenteICEPService;
    }

    public CalcularSaldoRemanenteICEPService getCalcularSaldoRemanenteICEPService() {
        return calcularSaldoRemanenteICEPService;
    }

    public void setOcultarPanelArt22(boolean ocultarPanelArt22) {
        this.ocultarPanelArt22 = ocultarPanelArt22;
    }

    public boolean isOcultarPanelArt22() {
        return ocultarPanelArt22;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }

    public List<DyctDictAutomaticaDTO> getListaMotivosRiesgo() {
        return listaMotivosRiesgo;
    }

    public void setListaMotivosRiesgo(List<DyctDictAutomaticaDTO> listaMotivosRiesgo) {
        this.listaMotivosRiesgo = listaMotivosRiesgo;
    }

    public void setEsIdEstado(String esIdEstado) {
        this.esIdEstado = esIdEstado;
    }

    public String getEsIdEstado() {
        return esIdEstado;
    }

    public void setOcultarPanelMotRies(boolean ocultarPanelMotRies) {
        this.ocultarPanelMotRies = ocultarPanelMotRies;
    }

    public boolean getOcultarPanelMotRies() {
        return ocultarPanelMotRies;
    }

    public String getMensajeErrorNyV() {
        return mensajeErrorNyV;
    }

    public void setMensajeErrorNyV(String mensajeErrorNyV) {
        this.mensajeErrorNyV = mensajeErrorNyV;
    }

    public String getMensajeFecha() {
        return mensajeFecha;
    }

    public void setMensajeFecha(String mensajeFecha) {
        this.mensajeFecha = mensajeFecha;
    }

    public String getFechaNyV() {
        return fechaNyV;
    }

    public void setFechaNyV(String fechaNyV) {
        this.fechaNyV = fechaNyV;
    }

    public String getFolioNyV() {
        return folioNyV;
    }

    public void setFolioNyV(String folioNyV) {
        this.folioNyV = folioNyV;
    }

    public boolean isTieneFolio() {
        return tieneFolio;
    }

    public void setTieneFolio(boolean tieneFolio) {
        this.tieneFolio = tieneFolio;
    }

    public DetalleIcepService getBussinesDelegate() {
        return bussinesDelegate;
    }

    public void setBussinesDelegate(DetalleIcepService bussinesDelegate) {
        this.bussinesDelegate = bussinesDelegate;
    }

    public boolean getTieneRolDYC008() {
        return tieneRolDYC008;
    }

    public void setTieneRolDYC008(boolean tieneRolDYC008) {
        this.tieneRolDYC008 = tieneRolDYC008;
    }

    public boolean isMensajeFechaB() {
        return mensajeFechaB;
    }

    public void getMensajeFechaB(boolean mensajeFechaB) {
        this.mensajeFechaB = mensajeFechaB;
    }

    public AdministrarSolicitudesService getAdministrarSolicitudesService() {
        return administrarSolicitudesService;
    }

    public void setAdministrarSolicitudesService(AdministrarSolicitudesService administrarSolicitudesService) {
        this.administrarSolicitudesService = administrarSolicitudesService;
    }

    public boolean isMensajeFechaError() {
        return mensajeFechaError;
    }

    public void setMensajeFechaError(boolean mensajeFechaError) {
        this.mensajeFechaError = mensajeFechaError;
    }

    public DiaHabilService getDiaHabilService() {
        return diaHabilService;
    }

    public void setDiaHabilService(DiaHabilService diaHabilService) {
        this.diaHabilService = diaHabilService;
    }

    public Date getFechaPago() {
        return (fechaPago != null) ? (Date) fechaPago.clone() : null;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago != null ? new Date(fechaPago.getTime()) : null;
    }

}
