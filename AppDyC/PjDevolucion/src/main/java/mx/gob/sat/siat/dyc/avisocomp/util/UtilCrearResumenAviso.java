package mx.gob.sat.siat.dyc.avisocomp.util;


import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import java.util.List;

import mx.gob.sat.siat.dyc.avisocomp.vo.AnexoVO;
import mx.gob.sat.siat.dyc.avisocomp.vo.ArchivoVO;
import mx.gob.sat.siat.dyc.avisocomp.vo.CuadroVO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaDTO;
import mx.gob.sat.siat.dyc.domain.inconsistencia.DycaAvInconsistDTO;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.springframework.stereotype.Component;


@Component(value = "utilCrearResumenAviso")
public class UtilCrearResumenAviso {

    private Font fuente2;
    private Font fuente1;
    private DateFormat formatoFechaS = new SimpleDateFormat("dd/MM/yyyy");
    private DecimalFormat formatoDecimal = new DecimalFormat("$ ###,###.##");

    public UtilCrearResumenAviso() {
        fuente2 = new Font();
        fuente1 = new Font();
        fuente2.setSize(ConstantesDyCNumerico.VALOR_9);
        fuente1.setStyle(Font.BOLD);
        fuente1.setSize(ConstantesDyCNumerico.VALOR_11);
    }

    public PdfPTable datosContribuyente(PersonaDTO contribuyente) {

        PdfPTable tableDatos = new PdfPTable(ConstantesDyCNumerico.VALOR_2);
        PdfPCell celda1RFC = new PdfPCell(new Paragraph("RFC: ", fuente2));
        celda1RFC.setBorder(Rectangle.NO_BORDER);
        tableDatos.addCell(celda1RFC);
        PdfPCell celda2RFC = new PdfPCell(new Paragraph(contribuyente.getRfcVigente(), fuente2));
        celda2RFC.setBorder(Rectangle.NO_BORDER);
        tableDatos.addCell(celda2RFC);

        String razonSocial = null;
        if (contribuyente.getPersonaIdentificacion().getTipoPersona().equals("F")) {
            razonSocial =
                    contribuyente.getPersonaIdentificacion().getApPaterno() + " " + contribuyente.getPersonaIdentificacion().getApMaterno() +
                    " " + contribuyente.getPersonaIdentificacion().getNombre();
        } else {
            razonSocial = contribuyente.getPersonaIdentificacion().getRazonSocial();
        }

        PdfPCell celda1DR = new PdfPCell(new Paragraph("Denominación o razón social: ", fuente2));
        celda1DR.setBorder(Rectangle.NO_BORDER);
        tableDatos.addCell(celda1DR);
        PdfPCell celda2DR = new PdfPCell(new Paragraph(razonSocial, fuente2));
        celda2DR.setBorder(Rectangle.NO_BORDER);
        tableDatos.addCell(celda2DR);

        tableDatos.setWidthPercentage(ConstantesDyCNumerico.VALOR_100);
        tableDatos.setHorizontalAlignment(Element.ALIGN_LEFT);
        return tableDatos;
    }

    public PdfPTable datosDomicilio(PersonaDTO contribuyente) {

        PdfPTable tableDomicilio = new PdfPTable(ConstantesDyCNumerico.VALOR_2);
        PdfPCell celda1C = new PdfPCell(new Paragraph("Calle: ", fuente2));
        celda1C.setBorder(Rectangle.NO_BORDER);
        tableDomicilio.addCell(celda1C);
        PdfPCell celda2C = new PdfPCell(new Paragraph(contribuyente.getDomicilio().getCalle(), fuente2));
        celda2C.setBorder(Rectangle.NO_BORDER);
        tableDomicilio.addCell(celda2C);
        PdfPCell celda1Ex = new PdfPCell(new Paragraph("No. Exterior: ", fuente2));
        celda1Ex.setBorder(Rectangle.NO_BORDER);
        tableDomicilio.addCell(celda1Ex);
        PdfPCell celda2Ex = new PdfPCell(new Paragraph(contribuyente.getDomicilio().getNumeroExt(), fuente2));
        celda2Ex.setBorder(Rectangle.NO_BORDER);
        tableDomicilio.addCell(celda2Ex);
        PdfPCell celda1DM = new PdfPCell(new Paragraph("Delegación o Municipio: ", fuente2));
        celda1DM.setBorder(Rectangle.NO_BORDER);
        tableDomicilio.addCell(celda1DM);
        PdfPCell celda2DM = new PdfPCell(new Paragraph(contribuyente.getDomicilio().getMunicipio(), fuente2));
        celda2DM.setBorder(Rectangle.NO_BORDER);
        tableDomicilio.addCell(celda2DM);
        PdfPCell celda1CP = new PdfPCell(new Paragraph("Código postal: ", fuente2));
        celda1CP.setBorder(Rectangle.NO_BORDER);
        tableDomicilio.addCell(celda1CP);
        PdfPCell celda2CP = new PdfPCell(new Paragraph(contribuyente.getDomicilio().getCodigoPostal(), fuente2));
        celda2CP.setBorder(Rectangle.NO_BORDER);
        tableDomicilio.addCell(celda2CP);
        PdfPCell celda1L = new PdfPCell(new Paragraph("Localidad: ", fuente2));
        celda1L.setBorder(Rectangle.NO_BORDER);
        tableDomicilio.addCell(celda1L);
        PdfPCell celda2L = new PdfPCell(new Paragraph(contribuyente.getDomicilio().getLocalidad(), fuente2));
        celda2L.setBorder(Rectangle.NO_BORDER);
        tableDomicilio.addCell(celda2L);
        PdfPCell celda1E = new PdfPCell(new Paragraph("Entidad Federativa: ", fuente2));
        celda1E.setBorder(Rectangle.NO_BORDER);
        tableDomicilio.addCell(celda1E);
        PdfPCell celda2E = new PdfPCell(new Paragraph(contribuyente.getDomicilio().getEntFed(), fuente2));
        celda2E.setBorder(Rectangle.NO_BORDER);
        tableDomicilio.addCell(celda2E);
        PdfPCell celda1NoAdm =
            new PdfPCell(new Paragraph("Número de Administración Desconcentrada de Auditoria Fiscal: ", fuente2));
        celda1NoAdm.setBorder(Rectangle.NO_BORDER);
        tableDomicilio.addCell(celda1NoAdm);
        PdfPCell celda2NoAdm =
            new PdfPCell(new Paragraph(String.valueOf(contribuyente.getDomicilio().getClaveAdmonLocal()), fuente2));
        celda2NoAdm.setBorder(Rectangle.NO_BORDER);
        tableDomicilio.addCell(celda2NoAdm);
        PdfPCell celda1DesAdm =
            new PdfPCell(new Paragraph("Descripción de la Administración de Auditoria que corresponde: ", fuente2));
        celda1DesAdm.setBorder(Rectangle.NO_BORDER);
        tableDomicilio.addCell(celda1DesAdm);
        return envioDatosContribuyente(tableDomicilio, contribuyente);
    }

    private PdfPTable envioDatosContribuyente(PdfPTable tableDomicilio, PersonaDTO contribuyente) {
        PdfPCell celda2DesAdm = new PdfPCell(new Paragraph(contribuyente.getDomicilio().getAdmonLocal(), fuente2));
        celda2DesAdm.setBorder(Rectangle.NO_BORDER);
        tableDomicilio.addCell(celda2DesAdm);
        tableDomicilio.setWidthPercentage(ConstantesDyCNumerico.VALOR_100);
        tableDomicilio.setHorizontalAlignment(Element.ALIGN_LEFT);
        return tableDomicilio;
    }

    public PdfPTable datosOrigenComp(List<CuadroVO> listaCuadros, int i) {

        PdfPTable tableSaldosO = new PdfPTable(ConstantesDyCNumerico.VALOR_2);
        PdfPCell celda1S = new PdfPCell(new Paragraph("Origen de la compensación: ", fuente2));
        celda1S.setBorder(Rectangle.NO_BORDER);
        tableSaldosO.addCell(celda1S);
        PdfPCell celda2S = new PdfPCell(new Paragraph(listaCuadros.get(i).getDescOrigenSaldo(), fuente2));
        celda2S.setBorder(Rectangle.NO_BORDER);
        tableSaldosO.addCell(celda2S);
        PdfPCell celda3S = new PdfPCell(new Paragraph("Tipo trámite: ", fuente2));
        celda3S.setBorder(Rectangle.NO_BORDER);
        tableSaldosO.addCell(celda3S);
        PdfPCell celda4S =
            new PdfPCell(new Paragraph(listaCuadros.get(i).getDycctipoTramiteDTO().getDescripcion(), fuente2));
        celda4S.setBorder(Rectangle.NO_BORDER);
        tableSaldosO.addCell(celda4S);
        tableSaldosO.setWidthPercentage(ConstantesDyCNumerico.VALOR_100);
        tableSaldosO.setHorizontalAlignment(Element.ALIGN_LEFT);
        return tableSaldosO;
    }

    public PdfPTable datosInfoImpuesto(List<CuadroVO> listaCuadros, int i) {

        PdfPTable tableSaldosI = new PdfPTable(ConstantesDyCNumerico.VALOR_2);
        PdfPCell celda5S = new PdfPCell(new Paragraph("Tipo de periodo: ", fuente2));
        celda5S.setBorder(Rectangle.NO_BORDER);
        tableSaldosI.addCell(celda5S);
        PdfPCell celda6S = new PdfPCell(new Paragraph(listaCuadros.get(i).getDescTipoPeriodo(), fuente2));
        celda6S.setBorder(Rectangle.NO_BORDER);
        tableSaldosI.addCell(celda6S);
        PdfPCell celda7S = new PdfPCell(new Paragraph("Periodo: ", fuente2));
        celda7S.setBorder(Rectangle.NO_BORDER);
        tableSaldosI.addCell(celda7S);
        PdfPCell celda8S = new PdfPCell(new Paragraph(listaCuadros.get(i).getDescPeriodo(), fuente2));
        celda8S.setBorder(Rectangle.NO_BORDER);
        tableSaldosI.addCell(celda8S);
        PdfPCell celda9S = new PdfPCell(new Paragraph("Ejercicio: ", fuente2));
        celda9S.setBorder(Rectangle.NO_BORDER);
        tableSaldosI.addCell(celda9S);
        PdfPCell celda10S = new PdfPCell(new Paragraph(listaCuadros.get(i).getDescEjercicio(), fuente2));
        celda10S.setBorder(Rectangle.NO_BORDER);
        tableSaldosI.addCell(celda10S);
        tableSaldosI.setWidthPercentage(ConstantesDyCNumerico.VALOR_100);
        tableSaldosI.setHorizontalAlignment(Element.ALIGN_LEFT);
        return tableSaldosI;
    }

    public PdfPTable datosInfoDeclaracion(List<CuadroVO> listaCuadros, int i) {

        PdfPTable tableSaldosIF = new PdfPTable(ConstantesDyCNumerico.VALOR_2);
        PdfPCell celda11S = new PdfPCell(new Paragraph("Es remanente: ", fuente2));
        celda11S.setBorder(Rectangle.NO_BORDER);
        tableSaldosIF.addCell(celda11S);
        if (listaCuadros.get(i).getDyctOrigenAvisoDTO().getEsRemanente() == 1) {
            PdfPCell celda12S = new PdfPCell(new Paragraph("Si", fuente2));
            celda12S.setBorder(Rectangle.NO_BORDER);
            tableSaldosIF.addCell(celda12S);
            PdfPCell celda122S =
                new PdfPCell(new Paragraph(listaCuadros.get(i).getDyctOrigenAvisoDTO().getNumControlRem(), fuente2));
            celda122S.setBorder(Rectangle.NO_BORDER);
            tableSaldosIF.addCell(celda122S);
        } else {
            PdfPCell celda12S = new PdfPCell(new Paragraph("No", fuente2));
            celda12S.setBorder(Rectangle.NO_BORDER);
            tableSaldosIF.addCell(celda12S);
        }
        PdfPCell celda13S = new PdfPCell(new Paragraph("Tipo de declaración: ", fuente2));
        celda13S.setBorder(Rectangle.NO_BORDER);
        tableSaldosIF.addCell(celda13S);
        PdfPCell celda14S = new PdfPCell(new Paragraph(listaCuadros.get(i).getDescTipoDeclaracion(), fuente2));
        celda14S.setBorder(Rectangle.NO_BORDER);
        tableSaldosIF.addCell(celda14S);
        PdfPCell celda15S = new PdfPCell(new Paragraph("Fecha de presentación de la declaración: ", fuente2));
        celda15S.setBorder(Rectangle.NO_BORDER);
        tableSaldosIF.addCell(celda15S);
        PdfPCell celda16S =
            new PdfPCell(new Paragraph(formatoFechaS.format(listaCuadros.get(i).getDyctDeclaracionDTO().getFechaPresentacion()),
                                       fuente2));
        celda16S.setBorder(Rectangle.NO_BORDER);
        tableSaldosIF.addCell(celda16S);
        PdfPCell celda17S = new PdfPCell(new Paragraph("Número de operación: ", fuente2));
        celda17S.setBorder(Rectangle.NO_BORDER);
        tableSaldosIF.addCell(celda17S);
        PdfPCell celda18S =
            new PdfPCell(new Paragraph(listaCuadros.get(i).getDyctDeclaraTempDTO().getNumOperacion(), fuente2));
        celda18S.setBorder(Rectangle.NO_BORDER);
        tableSaldosIF.addCell(celda18S);
        PdfPCell celda19S = new PdfPCell(new Paragraph("Importe del saldo o cantidad a favor: ", fuente2));
        celda19S.setBorder(Rectangle.NO_BORDER);
        tableSaldosIF.addCell(celda19S);
        PdfPCell celda20S =
            new PdfPCell(new Paragraph(formatoDecimal.format(listaCuadros.get(i).getDyctDeclaraTempDTO().getSaldoAFavor()),
                                       fuente2));
        celda20S.setBorder(Rectangle.NO_BORDER);
        tableSaldosIF.addCell(celda20S);
        tableSaldosIF.setWidthPercentage(ConstantesDyCNumerico.VALOR_100);
        tableSaldosIF.setHorizontalAlignment(Element.ALIGN_LEFT);
        return tableSaldosIF;
    }

    public PdfPTable datosCantidadComp(List<CuadroVO> listaCuadros, int i) {

        PdfPTable tableSaldosCC = new PdfPTable(ConstantesDyCNumerico.VALOR_2);
        PdfPCell celda21S = new PdfPCell(new Paragraph("Importe actualizado antes de la aplicación: ", fuente2));
        celda21S.setBorder(Rectangle.NO_BORDER);
        tableSaldosCC.addCell(celda21S);
        PdfPCell celda22S =
            new PdfPCell(new Paragraph(formatoDecimal.format(listaCuadros.get(i).getDyctOrigenAvisoDTO().getImpActualizado()),
                                       fuente2));
        celda22S.setBorder(Rectangle.NO_BORDER);
        tableSaldosCC.addCell(celda22S);
        PdfPCell celda23S = new PdfPCell(new Paragraph("Cantidad que de este importe se compensa: ", fuente2));
        celda23S.setBorder(Rectangle.NO_BORDER);
        tableSaldosCC.addCell(celda23S);
        PdfPCell celda24S =
            new PdfPCell(new Paragraph(formatoDecimal.format(listaCuadros.get(i).getDyctOrigenAvisoDTO().getImporteSolicitado()),
                                       fuente2));
        celda24S.setBorder(Rectangle.NO_BORDER);
        tableSaldosCC.addCell(celda24S);
        PdfPCell celda25S = new PdfPCell(new Paragraph("Remanente del importe que compensa: ", fuente2));
        celda25S.setBorder(Rectangle.NO_BORDER);
        tableSaldosCC.addCell(celda25S);
        PdfPCell celda26S =
            new PdfPCell(new Paragraph(formatoDecimal.format(listaCuadros.get(i).getDyctOrigenAvisoDTO().getImpRemanente()),
                                       fuente2));
        celda26S.setBorder(Rectangle.NO_BORDER);
        tableSaldosCC.addCell(celda26S);

        tableSaldosCC.setWidthPercentage(ConstantesDyCNumerico.VALOR_100);
        tableSaldosCC.setHorizontalAlignment(Element.ALIGN_LEFT);
        return tableSaldosCC;
    }

    public PdfPTable listaInconsistencias(List<DycaAvInconsistDTO> listaDeInconsistencias) {
        PdfPTable tableInconsis = new PdfPTable(ConstantesDyCNumerico.VALOR_2);
        PdfPCell celda1Inconsis = new PdfPCell(new Paragraph("Fecha de creación", fuente1));
        tableInconsis.addCell(celda1Inconsis);
        PdfPCell celda2Inconsis = new PdfPCell(new Paragraph("Descripción", fuente1));
        tableInconsis.addCell(celda2Inconsis);

        for (int i = 0; i < listaDeInconsistencias.size(); i++) {
            PdfPCell celda4Inconsis =
                new PdfPCell(new Paragraph(formatoFechaS.format(listaDeInconsistencias.get(i).getFechaRegistro()),
                                           fuente2));
            tableInconsis.addCell(celda4Inconsis);
            PdfPCell celda5Inconsis =
                new PdfPCell(new Paragraph(listaDeInconsistencias.get(i).getDescripcion(), fuente2));
            tableInconsis.addCell(celda5Inconsis);
        }

        tableInconsis.setWidthPercentage(ConstantesDyCNumerico.VALOR_100);
        tableInconsis.setHorizontalAlignment(Element.ALIGN_LEFT);
        return tableInconsis;
    }

    public PdfPTable listaAnexos(List<AnexoVO> cuadroListaAnexos) {
        PdfPTable table1 = new PdfPTable(ConstantesDyCNumerico.VALOR_2);
        PdfPCell celda1 = new PdfPCell(new Paragraph("Saldo", fuente1));
        table1.addCell(celda1);
        PdfPCell celda2 = new PdfPCell(new Paragraph("Archivo", fuente1));
        table1.addCell(celda2);
        for (int i = 0; i < cuadroListaAnexos.size(); i++) {
            PdfPCell celda4 = new PdfPCell(new Paragraph(cuadroListaAnexos.get(i).getCuadroSaldo(), fuente2));
            table1.addCell(celda4);
            PdfPCell celda5 = new PdfPCell(new Paragraph(cuadroListaAnexos.get(i).getNombreAnexo(), fuente2));
            table1.addCell(celda5);
        }
        table1.setWidthPercentage(ConstantesDyCNumerico.VALOR_100);
        table1.setHorizontalAlignment(Element.ALIGN_LEFT);
        return table1;
    }

    public PdfPTable listaDocumentos(List<ArchivoVO> cuadroListaDocumentos) {
        PdfPTable tableD = new PdfPTable(ConstantesDyCNumerico.VALOR_2);
        PdfPCell celda1D = new PdfPCell(new Paragraph("Archivo", fuente1));
        tableD.addCell(celda1D);
        PdfPCell celda2D = new PdfPCell(new Paragraph("Nombre del documento", fuente1));
        tableD.addCell(celda2D);

        for (int i = 0; i < cuadroListaDocumentos.size(); i++) {
            PdfPCell celda3D = new PdfPCell(new Paragraph(cuadroListaDocumentos.get(i).getNombreArchivo(), fuente2));
            tableD.addCell(celda3D);
            PdfPCell celda4D = new PdfPCell(new Paragraph(cuadroListaDocumentos.get(i).getDescripcion(), fuente2));
            tableD.addCell(celda4D);
        }

        tableD.setWidthPercentage(ConstantesDyCNumerico.VALOR_100);
        tableD.setHorizontalAlignment(Element.ALIGN_LEFT);
        return tableD;
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
}
