package mx.gob.sat.siat.dyc.avisocomp.service.impl;


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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.util.Date;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.avisocomp.service.AvisoCompensacionCtrlService;
import mx.gob.sat.siat.dyc.avisocomp.util.UtilCrearResumenAviso;
import mx.gob.sat.siat.dyc.avisocomp.vo.AnexoVO;
import mx.gob.sat.siat.dyc.avisocomp.vo.ArchivoVO;
import mx.gob.sat.siat.dyc.avisocomp.vo.CuadroVO;
import mx.gob.sat.siat.dyc.casocomp.util.ValidadorCasoComp;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaDTO;
import mx.gob.sat.siat.dyc.domain.inconsistencia.DycaAvInconsistDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaracionDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service(value = "avisoCompensacionCtrlService")
public class AvisoCompensacionCtrlServiceImpl implements AvisoCompensacionCtrlService {

    private Logger log = Logger.getLogger(AvisoCompensacionCtrlServiceImpl.class);

    @Autowired
    private UtilCrearResumenAviso utilCrearResumenAviso;

    @Autowired
    private ValidadorCasoComp validadorCasoComp;

    @Override
    public void imprimiResumen(Map<String, Object> objetosReportes, PersonaDTO contribuyente,
                               List<CuadroVO> listaCuadros, List<DycaAvInconsistDTO> listaDeInconsistencias,
                               List<AnexoVO> cuadroListaAnexos, List<ArchivoVO> cuadroListaDocumentos) {
        try {
            Document documento =
                new Document(PageSize.LETTER, ConstantesDyCNumerico.VALOR_80, ConstantesDyCNumerico.VALOR_80,
                             ConstantesDyCNumerico.VALOR_75, ConstantesDyCNumerico.VALOR_75);

            FileOutputStream ficheroPdf;
            ficheroPdf = new FileOutputStream("/siat/dyc/acuseResumen.pdf");

            PdfWriter.getInstance(documento, ficheroPdf);

            Font fuente1 = new Font();
            fuente1.setStyle(Font.BOLD);
            fuente1.setSize(ConstantesDyCNumerico.VALOR_11);

            Font fuente2 = new Font();
            fuente2.setSize(ConstantesDyCNumerico.VALOR_9);

            documento.open();
            documento.add(new Paragraph("Datos generales del aviso de compensación", fuente1));
            documento.add(new Paragraph("Datos del contribuyente", fuente1));

            documento.add(utilCrearResumenAviso.datosContribuyente(contribuyente));

            documento.add(new Paragraph("Domicilio fiscal", fuente1));

            documento.add(utilCrearResumenAviso.datosDomicilio(contribuyente));
            documento.add(new Paragraph(" "));

            documento.add(new Paragraph("Información del aviso de compensación", fuente1));

            PdfPTable tableAviso = new PdfPTable(ConstantesDyCNumerico.VALOR_2);
            PdfPCell celda1TA = new PdfPCell(new Paragraph("Tipo de aviso: ", fuente2));
            celda1TA.setBorder(Rectangle.NO_BORDER);
            tableAviso.addCell(celda1TA);
            PdfPCell celda2ta =
                new PdfPCell(new Paragraph(objetosReportes.get("cuadroDescripcionTipoAviso").toString(), fuente2));
            celda2ta.setBorder(Rectangle.NO_BORDER);
            tableAviso.addCell(celda2ta);

            tableAviso.setWidthPercentage(ConstantesDyCNumerico.VALOR_100);
            tableAviso.setHorizontalAlignment(Element.ALIGN_LEFT);
            documento.add(tableAviso);

            documento.add(new Paragraph("Información de la declaración en que se compensó", fuente1));

            PdfPTable tableInfoA = new PdfPTable(ConstantesDyCNumerico.VALOR_2);
            PdfPCell celda1InfoA = new PdfPCell(new Paragraph("Tipo de declaración: ", fuente2));
            celda1InfoA.setBorder(Rectangle.NO_BORDER);
            tableInfoA.addCell(celda1InfoA);
            PdfPCell celda2InfoA =
                new PdfPCell(new Paragraph(objetosReportes.get("cuadroDescripcionTipoDeclaracion").toString(),
                                           fuente2));
            celda2InfoA.setBorder(Rectangle.NO_BORDER);
            tableInfoA.addCell(celda2InfoA);
            PdfPCell celda3InfoA = new PdfPCell(new Paragraph("Concepto: ", fuente2));
            celda3InfoA.setBorder(Rectangle.NO_BORDER);
            tableInfoA.addCell(celda3InfoA);
            PdfPCell celda4InfoA =
                new PdfPCell(new Paragraph(objetosReportes.get("cuadroDescripcionConcepto").toString(), fuente2));
            celda4InfoA.setBorder(Rectangle.NO_BORDER);
            tableInfoA.addCell(celda4InfoA);
            PdfPCell celda5InfoA = new PdfPCell(new Paragraph("Tipo de periodo: ", fuente2));
            celda5InfoA.setBorder(Rectangle.NO_BORDER);
            tableInfoA.addCell(celda5InfoA);
            PdfPCell celda6InfoA =
                new PdfPCell(new Paragraph(objetosReportes.get("cuadroDescripcionTipoPeriodo").toString(), fuente2));
            celda6InfoA.setBorder(Rectangle.NO_BORDER);
            tableInfoA.addCell(celda6InfoA);
            PdfPCell celda7InfoA = new PdfPCell(new Paragraph("Periodo: ", fuente2));
            celda7InfoA.setBorder(Rectangle.NO_BORDER);
            tableInfoA.addCell(celda7InfoA);
            PdfPCell celda8InfoA =
                new PdfPCell(new Paragraph(objetosReportes.get("cuadroDescripcionPeriodo").toString(), fuente2));
            celda8InfoA.setBorder(Rectangle.NO_BORDER);
            tableInfoA.addCell(celda8InfoA);
            PdfPCell celda9InfoA = new PdfPCell(new Paragraph("Ejercicio: ", fuente2));
            celda9InfoA.setBorder(Rectangle.NO_BORDER);
            tableInfoA.addCell(celda9InfoA);
            PdfPCell celda10InfoA = new PdfPCell(new Paragraph(objetosReportes.get("ejercicio").toString(), fuente2));
            celda10InfoA.setBorder(Rectangle.NO_BORDER);
            tableInfoA.addCell(celda10InfoA);
            PdfPCell celda11InfoA = new PdfPCell(new Paragraph("Fecha de presentación de la declaración: ", fuente2));
            celda11InfoA.setBorder(Rectangle.NO_BORDER);
            tableInfoA.addCell(celda11InfoA);
            PdfPCell celda12InfoA =
                new PdfPCell(new Paragraph(objetosReportes.get("fechaPresentaDec").toString(), fuente2));
            celda12InfoA.setBorder(Rectangle.NO_BORDER);
            tableInfoA.addCell(celda12InfoA);
            PdfPCell celda13InfoA = new PdfPCell(new Paragraph("Numero de operación: ", fuente2));
            celda13InfoA.setBorder(Rectangle.NO_BORDER);
            tableInfoA.addCell(celda13InfoA);
            PdfPCell celda14InfoA =
                new PdfPCell(new Paragraph(objetosReportes.get("numOperacion").toString(), fuente2));
            celda14InfoA.setBorder(Rectangle.NO_BORDER);
            tableInfoA.addCell(celda14InfoA);
            PdfPCell celda15InfoA = new PdfPCell(new Paragraph("Importe compensado: ", fuente2));
            celda15InfoA.setBorder(Rectangle.NO_BORDER);
            tableInfoA.addCell(celda15InfoA);
            PdfPCell celda16InfoA =
                new PdfPCell(new Paragraph(objetosReportes.get("importeCompensado").toString(), fuente2));
            celda16InfoA.setBorder(Rectangle.NO_BORDER);
            tableInfoA.addCell(celda16InfoA);

            tableInfoA.setWidthPercentage(ConstantesDyCNumerico.VALOR_100);
            tableInfoA.setHorizontalAlignment(Element.ALIGN_LEFT);
            documento.add(tableInfoA);

            for (int i = 0; i < listaCuadros.size(); i++) {
                documento.add(new Paragraph("Saldo " + listaCuadros.get(i).getNumCuadro(), fuente1));

                documento.add(new Paragraph("Origen del importe a favor", fuente1));
                documento.add(utilCrearResumenAviso.datosOrigenComp(listaCuadros, i));

                documento.add(new Paragraph("Información del impuesto", fuente1));
                documento.add(utilCrearResumenAviso.datosInfoImpuesto(listaCuadros, i));

                documento.add(new Paragraph("Información de la declaración con importe a favor", fuente1));
                documento.add(utilCrearResumenAviso.datosInfoDeclaracion(listaCuadros, i));

                documento.add(new Paragraph("Cantidad a compensar", fuente1));
                documento.add(utilCrearResumenAviso.datosCantidadComp(listaCuadros, i));
            }

            if (listaDeInconsistencias.size() > 0) {
                documento.add(new Paragraph("Inconsistencias del aviso de compensación", fuente1));
                documento.add(new Paragraph(" "));
                documento.add(utilCrearResumenAviso.listaInconsistencias(listaDeInconsistencias));
                documento.add(new Paragraph(" "));

            } else {
                documento.add(new Paragraph("Inconsistencias del aviso de compensación", fuente1));
                documento.add(new Paragraph("No se encontraron registros.", fuente2));
            }

            if (cuadroListaAnexos.size() > 0) {
                documento.add(new Paragraph("Anexos", fuente1));
                documento.add(new Paragraph(" "));
                documento.add(utilCrearResumenAviso.listaAnexos(cuadroListaAnexos));
                documento.add(new Paragraph(" "));
            } else {
                documento.add(new Paragraph("Anexos", fuente1));
                documento.add(new Paragraph("No se encontraron registros.", fuente2));
            }

            if (cuadroListaDocumentos.size() > 0) {
                documento.add(new Paragraph("Documentos adjuntos", fuente1));
                documento.add(new Paragraph(" "));
                documento.add(utilCrearResumenAviso.listaDocumentos(cuadroListaDocumentos));
                documento.add(new Paragraph(" "));
            } else {
                documento.add(new Paragraph("Documentos Adjuntos", fuente1));
                documento.add(new Paragraph("No se encontraron registros.", fuente2));
            }

            documento.close();

        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        } catch (DocumentException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public boolean validaFechaPeriodoDestino(DyccPeriodoDTO dyccPeriodoDTO, DyccEjercicioDTO dyccEjercicioDTO,
                                             DycpCompensacionDTO dycpCompensacionDTO) {
        return (!validadorCasoComp.validadorPresentvsPeriodo(dyccPeriodoDTO.getPeriodoInicioFin(),
                                                         dyccEjercicioDTO.getIdEjercicio(),
                                                         dycpCompensacionDTO.getFechaPresentaDec()));
    }

    @Override
    public boolean validaFechasNormalComplementaria(Date fechaPresentacion, Date fechaNormalPresenta) {
        return fechaPresentacion.before(fechaNormalPresenta);
    }

    @Override
    public Integer validaFechaPeriodoOrigen(DyccPeriodoDTO dyccPeriodoOrigenDTO, DyccEjercicioDTO dyccDiEjercicioDTO,
                                            DycpCompensacionDTO dycpCompensacionDTO,
                                            DyctDeclaracionDTO dyctDeclaracionDTO)
    {
        if (!validadorCasoComp.validadorPresentvsPeriodo(dyccPeriodoOrigenDTO.getPeriodoInicioFin(),
                                                         dyccDiEjercicioDTO.getIdEjercicio(),
                                                         dyctDeclaracionDTO.getFechaPresentacion())) {
            return ConstantesDyCNumerico.VALOR_1;
        } else if (dycpCompensacionDTO.getFechaPresentaDec().before(dyctDeclaracionDTO.getFechaPresentacion())) {
            return ConstantesDyCNumerico.VALOR_2;
        }
        return ConstantesDyCNumerico.VALOR_3;
    }

}
