/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.generico.util;

import java.io.*;


import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletResponse;


import mx.gob.sat.siat.dyc.servicio.common.impl.GeneradorReportes;
import mx.gob.sat.siat.dyc.util.common.SIATException;


import org.apache.log4j.Logger;


/**
 * Clase generica de reportes con jasper
 * @author  Alfredo Ramirez
 * @since   24/03/2014
 */

public class Reporte {

    private final Logger log = Logger.getLogger(Reporte.class);

    public void imprimirReporte(String plantilla, Map<String, Object> hm, String nombreReporte) throws SIATException {
        try {
            byte bytes[] = GeneradorReportes.generarBytesReportePDF(plantilla, hm);

            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse)facesContext.getExternalContext().getResponse();
            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition", "inline;filename=" + nombreReporte + ".pdf");
            response.getOutputStream().write(bytes);
            response.setStatus(HttpServletResponse.SC_OK);
            response.flushBuffer();
            response.getOutputStream().close();
            facesContext.responseComplete();
        } catch (IOException ex) {
            throw new SIATException(ex.getMessage(), ex);
        }
    }
    
    public void imprimirReporteDownload(String plantilla, Map<String, Object> hm, String nombreReporte) throws SIATException {
        try {
            byte bytes[] = GeneradorReportes.generarBytesReportePDF(plantilla, hm);

            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse)facesContext.getExternalContext().getResponse();
            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition", "attachment;filename=" + nombreReporte + ".pdf");
            response.getOutputStream().write(bytes);
            response.setStatus(HttpServletResponse.SC_OK);
            response.flushBuffer();
            response.getOutputStream().close();
            facesContext.responseComplete();
        } catch (IOException ex) {
            throw new SIATException(ex.getMessage(), ex);
        }
    }

    public byte[] makeReport(String rutaReporte, String nombreReporte, Map<String, Object> parametros, List<?> detalle) throws SIATException {
        try {
            InputStream fileIS = new FileInputStream(rutaReporte);
            return GeneradorReportes.crearReporte(fileIS, nombreReporte, parametros, detalle);
        } catch (FileNotFoundException ex) {
            log.error("makeReport Error al intentar abrir el archivo :" + rutaReporte);
            log.error(ex.getMessage(), ex);
            throw new SIATException(ex.getMessage(), ex);
        }
    }
}
