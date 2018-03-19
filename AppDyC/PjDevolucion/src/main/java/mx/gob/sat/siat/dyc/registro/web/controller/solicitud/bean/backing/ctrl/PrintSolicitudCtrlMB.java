/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.registro.web.controller.solicitud.bean.backing.ctrl;


import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.math.BigDecimal;

import java.net.MalformedURLException;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import javax.servlet.http.HttpServletResponse;

import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaIdentificacionDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaUbicacionDTO;
import mx.gob.sat.siat.dyc.domain.regsolicitud.AdicionarSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.regsolicitud.TramiteDTO;
import mx.gob.sat.siat.dyc.generico.util.common.Utilerias;
import mx.gob.sat.siat.dyc.registro.web.controller.solicitud.bean.backing.AdicionarSolicitudMB;
import mx.gob.sat.siat.dyc.servicio.dto.devoluciones.ValidaDatosSolicitud;
import mx.gob.sat.siat.dyc.util.JSFUtils;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.vo.ArchivoVO;
import mx.gob.sat.siat.dyc.vo.InformacionSaldoFavorTO;

import org.apache.log4j.Logger;


/**
 * @author FOGJ
 */
@ManagedBean(name = "printSolicitudCtrlMB")
@RequestScoped
public class PrintSolicitudCtrlMB {

    private static final Logger LOG = Logger.getLogger(PrintSolicitudCtrlMB.class);
    private static final String FORMATO_FEHCA = "dd/MM/yyyy";
    private static final int SIZE = 40240;
    private Font fuente2;
    private Font fuente1;
    private TramiteDTO datosCont;
    private AdicionarSolicitudDTO viewRow;


    @ManagedProperty(value = "#{adicionarSolicitudMB}")
    private AdicionarSolicitudMB adicionarSolicitudPage;

    @PostConstruct
    public void init() {
        datosCont = adicionarSolicitudPage.getTramite();
        viewRow = adicionarSolicitudPage.getFlagsSolicitud();
        fuente2 = new Font();
        fuente1 = new Font();
        fuente2.setSize(ConstantesDyCNumerico.VALOR_9);
        fuente1.setStyle(Font.BOLD);
        fuente1.setSize(ConstantesDyCNumerico.VALOR_11);
    }

    public void downloadSolicitudPDF() throws SIATException {
        LOG.info("INIT PRINTER SOLICITUD:::::::::::::::");
        imprimirReporte();
        HttpServletResponse response = (HttpServletResponse)JSFUtils.getExternalContext().getResponse();
        File file1 = new File("/siat/dyc", "templeteSolicitud.pdf");
        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            input = new BufferedInputStream(new FileInputStream(file1), SIZE);
            response.reset();
            response.setHeader("Content-Type", "application/pdf");
            response.setHeader("Content-Length", String.valueOf(file1.length()));
            response.setHeader("Content-Disposition", "inline; filename=\"" + "impresion.pdf" + "\"");
            output = new BufferedOutputStream(response.getOutputStream(), SIZE);

            byte[] buffer = new byte[SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();

        } catch (IOException e) {
            throw new SIATException(e);
        } finally {
            close(output);
            close(input);
        }
        LOG.info("SUCCESS PRINTER SOLICITUD:::::::::::::::");
        JSFUtils.getFacesContext().responseComplete();
    }

    private void imprimirReporte() throws SIATException {
        LOG.info("GENERAR REPORTE");
        PersonaDTO contribuyente = adicionarSolicitudPage.getTramite().getPersona();
        Paragraph parrafo = new Paragraph("Datos de la Solicitud de Devolución de Impuestos Federales", fuente1);
        parrafo.setAlignment(Element.ALIGN_CENTER);
        Document documento =
            new Document(PageSize.LETTER, ConstantesDyCNumerico.VALOR_35, ConstantesDyCNumerico.VALOR_35,
                         ConstantesDyCNumerico.VALOR_30, ConstantesDyCNumerico.VALOR_60);
        try {
            PdfWriter.getInstance(documento, new FileOutputStream("/siat/dyc/templeteSolicitud.pdf"));
            documento.open();
            documento.add(getImagen());
            documento.add(new Paragraph(" "));
            documento.add(parrafo);
            documento.add(new Paragraph(" "));
            documento.add(new Paragraph("Fecha de impresión: " +
                                        new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()), fuente2));
            documento.add(new Paragraph("Datos del contribuyente", fuente1));
            documento.add(getDatosContribuyente(contribuyente.getPersonaIdentificacion(),
                                                contribuyente.getRfcVigente()));

            documento.add(new Paragraph("Domicilio fiscal", fuente1));
            documento.add(getDatosDomicilioFiscal(contribuyente.getDomicilio()));

            documento.add(new Paragraph("Información del trámite", fuente1));
            documento.add(getInfoTramite());

            documento.add(new Paragraph("Datos del impuesto, concepto, periodo y ejercicio", fuente1));
            documento.add(getDatosICEP());

            documento.add(new Paragraph("Información del saldo a favor", fuente1));
            documento.add(getDatosInfoSaldo(datosCont.getSaldoFavor()));

            if (viewRow.isBloqDeclaracionNormal()) {
                documento.add(new Paragraph("Datos de la declaración normal", fuente1));
                documento.add(getDatosDecNormal(datosCont.getSaldoFavor().getNormalFechaPresentacion(),
                                                datosCont.getSaldoFavor().getNormalNumOperacion(),
                                                datosCont.getSaldoFavor().getNormalSaldoFavor()));
                documento.add(new Paragraph(" "));
            }
            if (viewRow.getBloqInfoDIOT()) {
                documento.add(new Paragraph("Datos de la DIOT", fuente1));
                documento.add(getDatosDIOT());

            }
            if (viewRow.isRowALTEX()) {
                documento.add(new Paragraph("Datos ALTEX", fuente1));
                documento.add(getDatosALTEX());

            }
            documento.add(new Paragraph("Información del banco", fuente1));
            documento.add(getDatosBanco());

            documento.add(new Paragraph("Inconsistencias de la solicitud de la devolución", fuente1));
            documento.add(getInconsistencias());

            if (viewRow.getBloqAnexos()) {
                documento.add(new Paragraph("Anexos", fuente1));
                documento.add(getAnexos());

            }
            documento.add(new Paragraph("Documentos adjuntos", fuente1));
            documento.add(getDocumentos());
        } catch (DocumentException de) {
            throw new SIATException(de);
        } catch (FileNotFoundException fe) {
            throw new SIATException(fe);
        } finally {
            documento.close();
        }
    }

    private Image getImagen() {
        Image imagen = null;
        try {
            imagen = Image.getInstance("/siat/imagenes/logoSAT.jpg");
            imagen.setAlignment(Element.ALIGN_LEFT);
            imagen.scaleAbsolute(ConstantesDyCNumerico.VALOR_120, ConstantesDyCNumerico.VALOR_60);
        } catch (BadElementException bee) {
            bee.getMessage();
        } catch (MalformedURLException murle) {
            murle.getMessage();
        } catch (IOException ie) {
            ie.getMessage();
        }
        return imagen;
    }


    private void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                LOG.error(e.getMessage());
            }
        }
    }

    private PdfPTable getInfoTramite() {
        PdfPTable tableInfoTramite = new PdfPTable(ConstantesDyCNumerico.VALOR_2);
        tableInfoTramite.addCell(getPdfPCell("Origen de la devolución: "));
        tableInfoTramite.addCell(getPdfPCell(datosCont.getOrigenSaldo().getDescripcion()));
        tableInfoTramite.addCell(getPdfPCell("Tipo de trámite: "));
        tableInfoTramite.addCell(getPdfPCell(datosCont.getTipoTramite().getIdString()));

        if (viewRow.isShowSubtipoTramite()) {
            tableInfoTramite.addCell(getPdfPCell("Suborigen del saldo: "));
            tableInfoTramite.addCell(getPdfPCell(datosCont.getSubOrigenSaldo().getDescripcion()));
        }
        tableInfoTramite.addCell(getPdfPCell("Información adicional: "));
        tableInfoTramite.addCell(getPdfPCell(datosCont.getInfoAdicional()));
        if (viewRow.isVerRFC()) {
            tableInfoTramite.addCell(getPdfPCell("RFC del retenedor nacional: "));
            tableInfoTramite.addCell(getPdfPCell(adicionarSolicitudPage.getFlagsSolicitud().getRfcTerceros()));
        }
        if (viewRow.isVerRFCControlador()) {
            tableInfoTramite.addCell(getPdfPCell("RFC de la sociedad controladora que recupera el IDE: "));
            for (int i = 0; i < datosCont.getRfcControladora().size(); i++) {
                tableInfoTramite.addCell(getPdfPCell(datosCont.getRfcControladora().get(i)));
            }
        }
        tableInfoTramite.setWidthPercentage(ConstantesDyCNumerico.VALOR_100);
        tableInfoTramite.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
        return tableInfoTramite;
    }


    private PdfPTable getDatosICEP() {
        PdfPTable tableICEP = new PdfPTable(ConstantesDyCNumerico.VALOR_2);
        tableICEP.addCell(getPdfPCell("Impuesto: "));
        tableICEP.addCell(getPdfPCell(datosCont.getImpuesto().getDescripcion()));
        tableICEP.addCell(getPdfPCell("Concepto: "));
        tableICEP.addCell(getPdfPCell(datosCont.getConcepto().getDescripcion()));
        tableICEP.addCell(getPdfPCell("Tipo de periodo: "));
        tableICEP.addCell(getPdfPCell(datosCont.getTipoPeriodo().getDescripcion()));
        tableICEP.addCell(getPdfPCell("Periodo: "));
        tableICEP.addCell(getPdfPCell(datosCont.getPeriodo().getDescripcion()));
        tableICEP.addCell(getPdfPCell("Ejercicio: "));
        tableICEP.addCell(getPdfPCell(String.valueOf(datosCont.getEjercicio().getIdNum())));
        if (viewRow.isShowSubtipoTramite()) {
            tableICEP.addCell(getPdfPCell("Subtipo de trámite: "));
            tableICEP.addCell(getPdfPCell(datosCont.getSubTramite().getDescripcion()));
        }
        tableICEP.setWidthPercentage(ConstantesDyCNumerico.VALOR_100);
        tableICEP.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
        return tableICEP;
    }

    private PdfPTable getDatosContribuyente(PersonaIdentificacionDTO contribuyente, String rfc) {
        PdfPTable tableContribuyente = new PdfPTable(ConstantesDyCNumerico.VALOR_2);
        if (contribuyente.getTipoPersona().equals("M")) {
            StringBuffer razonSocial = new StringBuffer();
            razonSocial.append(contribuyente.getRazonSocial());
            razonSocial.append(" ");
            razonSocial.append(contribuyente.getTipoSociedad());
            tableContribuyente.addCell(getPdfPCell("Denominación o razón social: "));
            tableContribuyente.addCell(getPdfPCell(razonSocial.toString()));
        } else {
            tableContribuyente.addCell(getPdfPCell("Apellido paterno : "));
            tableContribuyente.addCell(getPdfPCell(contribuyente.getApPaterno()));
            tableContribuyente.addCell(getPdfPCell("Apellido materno : "));
            tableContribuyente.addCell(getPdfPCell(contribuyente.getApMaterno()));
            tableContribuyente.addCell(getPdfPCell("Nombre : "));
            tableContribuyente.addCell(getPdfPCell(contribuyente.getNombre()));
        }
        tableContribuyente.addCell(getPdfPCell("RFC: "));
        tableContribuyente.addCell(getPdfPCell(rfc));
        tableContribuyente.setWidthPercentage(ConstantesDyCNumerico.VALOR_100);
        tableContribuyente.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
        return tableContribuyente;
    }

    private PdfPTable getDatosDomicilioFiscal(PersonaUbicacionDTO domFiscal) {
        PdfPTable tableDomicilioFiscal = new PdfPTable(ConstantesDyCNumerico.VALOR_2);
        tableDomicilioFiscal.addCell(getPdfPCell("Calle: "));
        tableDomicilioFiscal.addCell(getPdfPCell(domFiscal.getCalle()));
        tableDomicilioFiscal.addCell(getPdfPCell("No. exterior: "));
        tableDomicilioFiscal.addCell(getPdfPCell(domFiscal.getNumeroExt()));
        tableDomicilioFiscal.addCell(getPdfPCell("No. interior: "));
        tableDomicilioFiscal.addCell(getPdfPCell(domFiscal.getNumeroInt()));
        tableDomicilioFiscal.addCell(getPdfPCell("Colonia: "));
        tableDomicilioFiscal.addCell(getPdfPCell(domFiscal.getColonia()));
        tableDomicilioFiscal.addCell(getPdfPCell("Delegación o municipio: "));
        tableDomicilioFiscal.addCell(getPdfPCell(domFiscal.getMunicipio()));
        tableDomicilioFiscal.addCell(getPdfPCell("Código postal: "));
        tableDomicilioFiscal.addCell(getPdfPCell(domFiscal.getCodigoPostal()));
        tableDomicilioFiscal.addCell(getPdfPCell("Localidad: "));
        tableDomicilioFiscal.addCell(getPdfPCell(domFiscal.getLocalidad()));
        tableDomicilioFiscal.addCell(getPdfPCell("Entidad federativa: "));
        tableDomicilioFiscal.addCell(getPdfPCell(domFiscal.getEntFed()));
        tableDomicilioFiscal.addCell(getPdfPCell("Número de Administración Desconcentrada de Auditoría Fiscal: "));
        tableDomicilioFiscal.addCell(getPdfPCell(String.valueOf(domFiscal.getClaveAdmonLocal())));
        tableDomicilioFiscal.addCell(getPdfPCell("Descripción de la Administración de Auditoría que corresponde: "));
        tableDomicilioFiscal.addCell(getPdfPCell(domFiscal.getAdmonLocal()));
        tableDomicilioFiscal.setWidthPercentage(ConstantesDyCNumerico.VALOR_100);
        tableDomicilioFiscal.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
        return tableDomicilioFiscal;
    }

    private PdfPTable getDatosInfoSaldo(InformacionSaldoFavorTO infoSaldo) {
        PdfPTable tableDomicilio = new PdfPTable(ConstantesDyCNumerico.VALOR_2);

        if (viewRow.isRowTipoDeclaracion()) {
            tableDomicilio.addCell(getPdfPCell("Tipo de declaración: "));
            tableDomicilio.addCell(getPdfPCell(infoSaldo.getTipoDeclaracion().equals("1") ? "Normal" :
                                               "Complementaria"));
        }
        if (viewRow.isRowFechaPresentacion()) {
            tableDomicilio.addCell(getPdfPCell("Fecha de presentación de la declaración: "));
            tableDomicilio.addCell(getPdfPCell(new SimpleDateFormat(FORMATO_FEHCA).format(infoSaldo.getFechaPresentacion())));
        }

        if (viewRow.isRowFechaCaucion()) {
            tableDomicilio.addCell(getPdfPCell("Fecha causación: "));
            tableDomicilio.addCell(getPdfPCell(new SimpleDateFormat(FORMATO_FEHCA).format(infoSaldo.getFechaCaucion())));
        }

        if (viewRow.isRowNumeroOperacion()) {
            tableDomicilio.addCell(getPdfPCell("Número de operación: "));
            tableDomicilio.addCell(getPdfPCell(infoSaldo.getNumeroOperacion()));
        }
        if (viewRow.isRowNumeroDocumento()) {
            tableDomicilio.addCell(getPdfPCell("Número de documento: "));
            tableDomicilio.addCell(getPdfPCell(infoSaldo.getNumeroDocumento().toString()));
        }

        if (viewRow.isRowSaldoFavor()) {
            tableDomicilio.addCell(getPdfPCell(infoSaldo.getEtiquetaSaldo()));
            tableDomicilio.addCell(getPdfPCell(ValidaDatosSolicitud.formatoMoneda(infoSaldo.getImporteSaldoFavor())));
        }

        if (viewRow.isRowImporteDevComEfectuadas()) {
            tableDomicilio.addCell(getPdfPCell("Importe de las devoluciones y/o compensaciones \nanteriores(sin incluir actualización):"));
            tableDomicilio.addCell(getPdfPCell(ValidaDatosSolicitud.formatoMoneda(infoSaldo.getImporteAcreditramientoEfectuado())));
        }

        if (viewRow.isRowImporteAcreditamiento()) {
            tableDomicilio.addCell(getPdfPCell("Importe del acreditamiento efectuado: "));
            tableDomicilio.addCell(getPdfPCell(ValidaDatosSolicitud.formatoMoneda(infoSaldo.getImporteEfectuados())));
        }

        tableDomicilio.addCell(getPdfPCell("Importe solicitado en devolución:"));
        tableDomicilio.addCell(getPdfPCell(ValidaDatosSolicitud.formatoMoneda(infoSaldo.getImporteSolicitadoDevolucion())));


        tableDomicilio.setWidthPercentage(ConstantesDyCNumerico.VALOR_100);
        tableDomicilio.setHorizontalAlignment(Element.ALIGN_LEFT);
        return tableDomicilio;
    }

    private PdfPTable getDatosDecNormal(Date fecha, String numDoc, BigDecimal importe) {
        PdfPTable tableDocNormal = new PdfPTable(ConstantesDyCNumerico.VALOR_2);
        tableDocNormal.addCell(getPdfPCell("Tipo de declaración: "));
        tableDocNormal.addCell(getPdfPCell("Normal"));
        tableDocNormal.addCell(getPdfPCell("Fecha de presentación de la declaración: "));
        tableDocNormal.addCell(getPdfPCell(new SimpleDateFormat(FORMATO_FEHCA).format(fecha)));
        tableDocNormal.addCell(getPdfPCell("Número de operación: "));
        tableDocNormal.addCell(getPdfPCell(numDoc));
        tableDocNormal.addCell(getPdfPCell("Importe del saldo a favor: "));
        tableDocNormal.addCell(getPdfPCell(ValidaDatosSolicitud.formatoMoneda(importe)));
        tableDocNormal.setWidthPercentage(ConstantesDyCNumerico.VALOR_100);
        tableDocNormal.setHorizontalAlignment(Element.ALIGN_LEFT);
        return tableDocNormal;
    }

    private PdfPTable getDatosDIOT() {
        PdfPTable tableDIOT = new PdfPTable(ConstantesDyCNumerico.VALOR_2);
        tableDIOT.addCell(getPdfPCell("Número de documento: "));
        tableDIOT.addCell(getPdfPCell(Utilerias.isNull(datosCont.getInfoDeclarativa().getDiotNumOperacion())));
        tableDIOT.addCell(getPdfPCell("Fecha de presentación de la declaración: "));
        tableDIOT.addCell(getPdfPCell(null != datosCont.getInfoDeclarativa().getDiotFechPrecentacion() ?
                                      new SimpleDateFormat(FORMATO_FEHCA).format((datosCont.getInfoDeclarativa().getDiotFechPrecentacion())) :
                                      ""));
        tableDIOT.setWidthPercentage(ConstantesDyCNumerico.VALOR_100);
        tableDIOT.setHorizontalAlignment(Element.ALIGN_LEFT);
        return tableDIOT;
    }

    private PdfPTable getDatosALTEX() {
        PdfPTable tableALTEX = new PdfPTable(ConstantesDyCNumerico.VALOR_2);
        tableALTEX.addCell(getPdfPCell("Número de constancia: "));
        tableALTEX.addCell(getPdfPCell(Utilerias.isNull(datosCont.getInfoDeclarativa().getAltexNumConstancia())));
        tableALTEX.setWidthPercentage(ConstantesDyCNumerico.VALOR_100);
        tableALTEX.setHorizontalAlignment(Element.ALIGN_LEFT);
        return tableALTEX;
    }


    private PdfPTable getDatosBanco() {
        PdfPTable tableBanco = new PdfPTable(ConstantesDyCNumerico.VALOR_2);
        tableBanco.addCell(getPdfPCell("Nombre del banco: "));
        tableBanco.addCell(getPdfPCell(datosCont.getInstitucionFinanciera().getDyccInstCreditoDTO().getDescripcion()));
        tableBanco.addCell(getPdfPCell("CLABE: "));
        tableBanco.addCell(getPdfPCell(datosCont.getInstitucionFinanciera().getClabe()));
        tableBanco.setWidthPercentage(ConstantesDyCNumerico.VALOR_100);
        tableBanco.setHorizontalAlignment(Element.ALIGN_LEFT);
        return tableBanco;
    }

    private PdfPTable getInconsistencias() {
        PdfPTable tableInconsistencias = new PdfPTable(ConstantesDyCNumerico.VALOR_1);
        if (datosCont.getInconsistencias().size() != 0) {
            for (int i = 0; i < datosCont.getInconsistencias().size(); i++) {
                tableInconsistencias.addCell(getPdfPCell(datosCont.getInconsistencias().get(i).getDescripcion()));
            }
        } else {
            tableInconsistencias.addCell(getPdfPCell("No se encontraron registros"));
        }
        tableInconsistencias.setWidthPercentage(ConstantesDyCNumerico.VALOR_100);
        tableInconsistencias.setHorizontalAlignment(Element.ALIGN_LEFT);
        return tableInconsistencias;
    }

    private PdfPTable getAnexos() {
        PdfPTable tableAnexos = new PdfPTable(ConstantesDyCNumerico.VALOR_1);
        if (!datosCont.getAnexos().isEmpty()) {
            for (int i = 0; i < datosCont.getAnexos().size(); i++) {
                tableAnexos.addCell(getPdfPCell(datosCont.getAnexos().get(i).getNombreArchivo()));
            }
        } else {
            tableAnexos.addCell(getPdfPCell("No se encontraron registros"));
        }
        tableAnexos.setWidthPercentage(ConstantesDyCNumerico.VALOR_100);
        tableAnexos.setHorizontalAlignment(Element.ALIGN_LEFT);
        return tableAnexos;
    }

    private PdfPTable getDocumentos() {
        List<ArchivoVO> docAdjuntos = adicionarSolicitudPage.getDocumentosAdjuntos();
        PdfPTable tableDocumentos = new PdfPTable(ConstantesDyCNumerico.VALOR_1);
        if (null != docAdjuntos && docAdjuntos.size() != 0) {
            for (int i = 0; i < docAdjuntos.size(); i++) {
                tableDocumentos.addCell(getPdfPCell(docAdjuntos.get(i).getNombreArchivo()));
            }
        } else {
            tableDocumentos.addCell(getPdfPCell("No se encontraron registros"));
        }
        tableDocumentos.setWidthPercentage(ConstantesDyCNumerico.VALOR_100);
        tableDocumentos.setHorizontalAlignment(Element.ALIGN_LEFT);
        return tableDocumentos;
    }

    private PdfPCell getPdfPCell(String nameCell) {
        PdfPCell pdfPCell = new PdfPCell(new Paragraph(null != nameCell ? nameCell : "", fuente2));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        return pdfPCell;
    }


    public void setAdicionarSolicitudPage(AdicionarSolicitudMB adicionarSolicitudPage) {
        this.adicionarSolicitudPage = adicionarSolicitudPage;
    }

    public AdicionarSolicitudMB getAdicionarSolicitudPage() {
        return adicionarSolicitudPage;
    }

    public void setFuente2(Font fuente2) {
        this.fuente2 = fuente2;
    }

    public Font getFuente2() {
        return fuente2;
    }

    public void setFuente1(Font fuente1) {
        this.fuente1 = fuente1;
    }

    public Font getFuente1() {
        return fuente1;
    }

    public void setDatosCont(TramiteDTO datosContribuyente) {
        this.datosCont = datosContribuyente;
    }

    public TramiteDTO getDatosCont() {
        return datosCont;
    }

}
