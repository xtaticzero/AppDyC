/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.dyc.servicio.common.impl;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import mx.gob.sat.siat.dyc.servicio.common.impl.ReporteJasperUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public final class GeneradorReportes {

    /**
     * String de error para formato no soportado.
     */
    public static final String ERROR_TIPO_REPORTE_NO_SOPORTADO = "extencion.no.soportada";
    public static final String ERROR_NOMBRE_REPORTE = "nombre.reporte";
    public static final String ERROR_GENERAR_REPORTE = "nombre.reporte";

    private GeneradorReportes() {

    }

    /**
     * M&eacute;todo generico para crear reportes.
     *
     * @param reportIS
     * @param nombreReporte nombre del reporte con todo y su extenci&oacute;n
     * (.xls o .pdf). Ej. miReporte.pdf
     * @param parametros Mapa con los parametros que estaran en el reporte.
     * @param detalle Lista de mapas para insertar en la banda detail del
     * reporte.
     * @return arreglo de bytes del reporte generado.
     * @throws SIATException en caso de haber un problema al
     * generar el reporte.
     */
    public static byte[] crearReporte(InputStream reportIS, String nombreReporte,
            Map<String, Object> parametros,
            List<?> detalle) throws SIATException {

        ReporteJasperUtil reporte = new ReporteJasperUtil();
        reporte.setReporteJasper(reportIS);
        reporte.setNombreReporte(nombreReporte);
        reporte.setFormatoReporte(getFormatoReporte(nombreReporte));
        reporte.setParametrosReporte(parametros);
        reporte.setDatosReporte(detalle);
        try {
            return reporte.generarBytesReporte();
        } catch (SIATException cex) {
            if (cex.getMessage() != null) {
                throw new SIATException(cex.getMessage(), cex);
            } else {
                throw new SIATException(ERROR_GENERAR_REPORTE, cex);
            }
        }
    }
    
    public static byte[] generarBytesReportePDF(String plantilla, Map<String, Object> hm) throws SIATException {
        ReporteJasperUtil reporte = new ReporteJasperUtil();
        return reporte.generarBytesReportePDF(plantilla, hm);
    }

    private static String getFormatoReporte(String nombreReporteCrear) throws SIATException {

        if (nombreReporteCrear == null || nombreReporteCrear.isEmpty()) {
            throw new SIATException(ERROR_TIPO_REPORTE_NO_SOPORTADO, nombreReporteCrear);
        }

        String nombreReporte = nombreReporteCrear.toLowerCase();
        if (nombreReporte.endsWith(ReporteJasperUtil.XLS)) {
            return ReporteJasperUtil.XLS;
        }
        if (nombreReporte.endsWith(ReporteJasperUtil.XLSX)) {
            return ReporteJasperUtil.XLSX;
        }
        if (nombreReporte.endsWith(ReporteJasperUtil.PDF)) {
            return ReporteJasperUtil.PDF;
        }
        if (nombreReporte.endsWith(ReporteJasperUtil.CSV)) {
            return ReporteJasperUtil.CSV;
        }
        if (nombreReporte.endsWith(ReporteJasperUtil.DOC)) {
            return ReporteJasperUtil.DOC;
        }
        if (nombreReporte.endsWith(ReporteJasperUtil.DOCX)) {
            return ReporteJasperUtil.DOCX;
        }
        throw new SIATException(ERROR_TIPO_REPORTE_NO_SOPORTADO, nombreReporte);

    }
}
