package mx.gob.sat.siat.dyc.tesofe.dao;

import java.util.List;

import mx.gob.sat.siat.dyc.tesofe.dto.DatosRetroTESOFE;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * Esta clase se utiliza para consultar la información del reporte de diario que se genera por la retroalimentación de
 * la TESOFE.
 *
 * @author Jesus Alfredo Hernandez Orozco
 */
public interface GestionPagoDAO {
    
    /**
     * Consulta la información que va a ser mostrada en el reporte de Excel, la cual incluye los datos de diario que se
     * generan a partir de la retroalimentación de TESOFE.
     *
     * @param fecha1 Dia sobre el cual se realiza la consulta.
     * @param fecha2 Dia +1 sobre el cual se realiza la consulta.
     * @param esAutoISR true si se va a generar el reporte de Devoluciones Automaticas de ISR PF Tramite 132 
     * @return Lista con los datos del reporte.
     * @throws SIATException
     */
    List <DatosRetroTESOFE> listarDatosParaReporteDeRetroDeTESOFE(String fecha1, String fecha2, Boolean esAutoISR) throws SIATException;
}
