/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.cargaaut.web.controller.bean.backing;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import mx.gob.sat.mat.dyc.cargaautomaticas.CargaMasivoAutomaticasService;
import mx.gob.sat.siat.dyc.vo.CargaMasivaAutomaticasRegistroVO;
import org.primefaces.model.StreamedContent;
import mx.gob.sat.siat.dyc.generico.util.ArchivoCargaDescarga;
import mx.gob.sat.siat.dyc.util.JSFUtils;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.primefaces.model.DefaultStreamedContent;
import org.apache.log4j.Logger;

/**
 *
 * @author root
 */
@ManagedBean(name = "generacionReporteMasivoAutMB")
@ViewScoped
public class GeneracionReporteMasivoAutMB {

    private static final Logger LOGGER = Logger.getLogger(GeneracionReporteMasivoAutMB.class);
    private Date fechaPagoInicio;
    private Date fechaPagoFin;
    private StreamedContent file;

    private List<CargaMasivaAutomaticasRegistroVO> listaRegistros;

    @ManagedProperty("#{cargaMasivoAutomaticasService}")
    private CargaMasivoAutomaticasService cargaMasivoAutomaticasService;

    @PostConstruct
    public void init() {
        listaRegistros = cargaMasivoAutomaticasService.obtenerUltimosRegistros();
    }

    public void buscarPorFecha() {
        try {
        if(fechaPagoInicio==null || fechaPagoFin==null){
        JSFUtils.messageGlobal("Los campos marcados con * son requeridos.", FacesMessage.SEVERITY_ERROR);
        }else 
            if(!validaFecha()){
                listaRegistros = cargaMasivoAutomaticasService.obtenerRegistrosAutomaticos(fechaPagoInicio, fechaPagoFin);
            }
        } catch (Exception ex) {
           LOGGER.error("ocurrio un error al buscar los registros", ex);
        }
    }

    private boolean validaFecha() throws SIATException {
        if (fechaPagoFin.before(fechaPagoInicio)) {
            JSFUtils.messageGlobal("La fecha 'a'  no debe ser menor a la  fecha de carga", FacesMessage.SEVERITY_ERROR);
            return true;
        }
        return false;
    }

    public void limpiaformulario() {
        fechaPagoInicio = null;
        fechaPagoFin = null;
    }

    public Date getFechaPagoInicio() {
           if (fechaPagoInicio != null) {
            return (Date)fechaPagoInicio.clone();
        } else {
            return null;
        }
    }

    public void setFechaPagoInicio(Date fechaPagoInicio) {
          if (fechaPagoInicio != null) {
            this.fechaPagoInicio = (Date)fechaPagoInicio.clone();
        } else {
            this.fechaPagoInicio = null;
        }
    }

    public Date getFechaPagoFin() {
         if (fechaPagoFin != null) {
            return (Date)fechaPagoFin.clone();
        } else {
            return null;
        }
    }

    public void setFechaPagoFin(Date fechaPagoFin) {
        if (fechaPagoFin != null) {
            this.fechaPagoFin = (Date)fechaPagoFin.clone();
        } else {
            this.fechaPagoFin = null;
        }
    }

    public List<CargaMasivaAutomaticasRegistroVO> getListaRegistros() {
        return listaRegistros;
    }

    public void setListaRegistros(List<CargaMasivaAutomaticasRegistroVO> listaRegistros) {
        this.listaRegistros = listaRegistros;
    }

    public CargaMasivoAutomaticasService getCargaMasivoAutomaticasService() {
        return cargaMasivoAutomaticasService;
    }

    public void setCargaMasivoAutomaticasService(CargaMasivoAutomaticasService cargaMasivoAutomaticasService) {
        this.cargaMasivoAutomaticasService = cargaMasivoAutomaticasService;
    }

    public void descargarDocumentoGenerado(final ActionEvent event) {
        file = null;
        String docSelect = (String) event
                .getComponent().getAttributes().get("archivoDocumento");
        ArchivoCargaDescarga objetoDescarga = new ArchivoCargaDescarga();
        if (docSelect != null) {

            file = objetoDescarga.descargarArchivo(docSelect);
        }
    }

    public void descargarExcelGenerado(final ActionEvent event) {
        file = null;
        String docSelect = (String) event
                .getComponent().getAttributes().get("archivoDocumento");
        if (docSelect != null) {

          
             ArchivoCargaDescarga objetoDescarga = new ArchivoCargaDescarga();
                file = objetoDescarga.descargarArchivoTipoExcel(docSelect.replace(".txt",".xls"));
        }
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }

    public StreamedContent convertirExcel(String inputFile) {
        String[] splitnombre = inputFile.split("/");
        StreamedContent defaultStreamContent = null;
        BufferedReader reader = null;
        InputStream in = null;
        ByteArrayOutputStream out = null;
        try {

            reader =new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "UTF-8"));
            String[] dataArray = null;
            String delimiter = "\\|";
            String text = null;

            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("overlayReport");
            Map<Integer, String[]> data = new HashMap<Integer, String[]>();

          
            int i = 1;
            while ((text = reader.readLine()) != null) {
                dataArray = text.split(delimiter);

                data.put(i, dataArray);
                i++;

            }

            int rownum = 0;
            for (int k = 1; k < i; k++) {
                Row row = sheet.createRow(rownum++);
                String[] strArr = data.get(k);
                int cellnum = 0;
                for (Object obj : strArr) {
                    Cell cell = row.createCell(cellnum++);
                    cell.setCellValue((String) obj);
                }
            }

            out
                    = new ByteArrayOutputStream();
            workbook.write(out);
            out.close();
            byte[] xls = out.toByteArray();
            in = new ByteArrayInputStream(xls);
            defaultStreamContent = new DefaultStreamedContent(in,
                    "application/xls", splitnombre[splitnombre.length - 1].replace(".txt", ".xls"));
        } catch (Exception e) {
            LOGGER.error("error general", e);
              JSFUtils.messageGlobal("ocurrio un error al mostrar el excel", FacesMessage.SEVERITY_ERROR);
        } finally {
            try {
                if (reader != null) {
                    reader.close();

                }
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
                LOGGER.error("error al cerrar archivos", ex);
            }
        }
        return defaultStreamContent;
    }

}
