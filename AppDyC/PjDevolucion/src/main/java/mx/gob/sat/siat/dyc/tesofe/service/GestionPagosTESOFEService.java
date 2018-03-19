package mx.gob.sat.siat.dyc.tesofe.service;

import java.io.File;

import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.dyc.tesofe.dto.InformacionEnvioATESOFE;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface GestionPagosTESOFEService {
    
    /**
     * Implementa la gestion de pagos ante la TESOFE.
     * Este método, genera una un archivo por cada una de las claves administrativas.
     */
    List<InformacionEnvioATESOFE> gestionarPagoAnteTESOFE(Date fechaPago, Date fechaPresentacion, Boolean isAutoISR);
    
    /**
     * Obtiene la información de los registros agregados al archivo, para que posteriormente genere nuevos registros.
     *
     * @param fechaPago
     * @param fechaPresentacion
     * @param rutaArchivo
     * @return
     */
    List<InformacionEnvioATESOFE> modificar(Date fechaPago, Date fechaPresentacion, String rutaArchivo, Boolean isAutoISR);
    
    /**
     * Consulta la información que va a ser mostrada en el reporte de Excel, la cual incluye los datos de diario que se
     * generan a partir de la retroalimentación de TESOFE.
     *
     * @param fecha Dia sobre el cual se realiza la consulta.
     * @param esAutoISR
     * @throws SIATException
     */
    byte[] listarDatosParaReporteDeRetroDeTESOFE(Date fecha, Boolean esAutoISR) throws SIATException;
    
    /**
     * Lista todos los archivos de pago que se han generado en una fecha 
     *
     * @param fecha D&iacute;a a coonsultar los archivos de pago.
     * @param isAutoISR
     * @return Lista de archivos de pago.
     * @throws SIATException
     */
    List<File> listarArchivosDePagoPorFecha(Date fecha, Boolean isAutoISR) throws SIATException;
}
