/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.template.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.template.dto.TemplateNumberDTO;
import mx.gob.sat.siat.dyc.util.constante.ConstantesPlantillador;

import org.springframework.jdbc.core.RowMapper;


/**
 *
 * @author Yolanda Martínez Sánchez
 * @since  17 th February, 2014
 *
 */


public class TemplateNumberMapper implements RowMapper<TemplateNumberDTO>, ConstantesPlantillador {

    private Integer numTemplate;


    public TemplateNumberMapper() {
        super();
    }

    public TemplateNumberMapper(Integer template) {
        numTemplate = template;

    }


    @Override
    public TemplateNumberDTO mapRow(ResultSet rs, int i) throws SQLException {

        TemplateNumberDTO templateNumberDTO = new TemplateNumberDTO();
        templateNumberDTO.setBoId(rs.getString(BO_ID));
        templateNumberDTO.setAdministracion(rs.getString(ADMINISTRACION) != null ? rs.getString(ADMINISTRACION) : " ");
        templateNumberDTO.setNumeroDocumento(rs.getString(NUMERO_DOCUMENTO) != null ? rs.getString(NUMERO_DOCUMENTO) :
                                             " ");
        templateNumberDTO.setRazonSocial(rs.getString(RAZON_SOCIAL) != null ? rs.getString(RAZON_SOCIAL) : " ");
        templateNumberDTO.setClaveAdministracion(rs.getString(CLAVE_ADMINISTRACION) != null ?
                                                 rs.getString(CLAVE_ADMINISTRACION) : " ");

        boolean banderaFiltro = numTemplate != LABEL_SIXTY_SIX && numTemplate != LABEL_ONE_HUNDRED_AND_FOURTEEN;
        if (banderaFiltro && numTemplate != LABEL_ONE_HUNDRED_AND_THIRTY_ONE) {
            templateNumberDTO.setEjercicio(rs.getString(EJERCICIO) != null ? rs.getString(EJERCICIO) : " ");
            templateNumberDTO.setNumControl(rs.getString(NUM_CONTROL) != null ? rs.getString(NUM_CONTROL) : " ");
            templateNumberDTO.setPeriodo(rs.getString(PERIODO) != null ? rs.getString(PERIODO) : " ");
        }


        templateNumberDTO.setRfcContribuyente(rs.getString(RFC_CONTRIBUYENTE) != null ?
                                              rs.getString(RFC_CONTRIBUYENTE) : " ");
        
        
        
        
        
        boolean primeraCondicionI = numTemplate <= LABEL_TWENTY || numTemplate == LABEL_TWENTY_FOUR || numTemplate == LABEL_TWENTY_FIVE ||
           numTemplate == LABEL_SEVENTY_FOUR;
        
        boolean segundaCondicionI = numTemplate == LABEL_SEVENTY_FIVE || numTemplate == LABEL_SEVENTY_SIX || numTemplate == LABEL_SEVENTY_SEVEN;
        
        boolean terceraCondicionI = numTemplate == LABEL_SEVENTY_EIGHT || numTemplate == LABEL_SEVENTY_NINE || numTemplate == LABEL_EIGHTY_ONE || numTemplate == LABEL_EIGHTY_THREE;
        
        
        boolean primeraCondicionII = numTemplate != LABEL_FOUR || numTemplate == LABEL_SEVENTY_FOUR || numTemplate == LABEL_SEVENTY_SIX ||
                numTemplate == LABEL_SEVENTY_SEVEN;
        
        boolean segundaCondicionII = numTemplate == LABEL_SEVENTY_EIGHT || numTemplate == LABEL_SEVENTY_NINE || numTemplate == LABEL_EIGHTY_ONE ||
                numTemplate == LABEL_EIGHTY_THREE;
     
        
        if(primeraCondicionI || segundaCondicionI || terceraCondicionI) {
                 
            if (primeraCondicionII || segundaCondicionII) {
                
                templateNumberDTO.setFraccion(rs.getString(FRACCION) != null ? rs.getString(FRACCION) : " ");
                
            }

            templateNumberDTO.setCiudadAdmLocal(rs.getString(CIUDAD_ADM_LOCAL) != null ?
                                                rs.getString(CIUDAD_ADM_LOCAL) : " ");

            templateNumberDTO.setDomicilioAlaf(rs.getString(DOMICILIO_ALAF) != null ? rs.getString(DOMICILIO_ALAF) :
                                               " ");
            templateNumberDTO.setSerieDocumental(rs.getString(SERIE_DOCUMENTAL) != null ?
                                                 rs.getString(SERIE_DOCUMENTAL) : " ");
        }


        switch (numTemplate) {

        case LABEL_ONE:
        case LABEL_FIVE:
        case LABEL_EIGHT:
        case LABEL_ELEVEN:
        case LABEL_FOURTEEN:
        case LABEL_TWENTY:
        case LABEL_FORTY_FIVE:
        case LABEL_FORTY_NINE:
        case LABEL_FIFTY_TWO:
        case LABEL_FIFTY_FIVE:
        case LABEL_FIFTY_EIGHT:
        case LABEL_ONE_HUNDRED_AND_ONE:
        case LABEL_ONE_HUNDRED_AND_SEVEN:
        case LABEL_ONE_HUNDRED_AND_EIGHT:
        case LABEL_ONE_HUNDRED_AND_NINE:
        case LABEL_ONE_HUNDRED_AND_TEN: 
        case LABEL_ONE_HUNDRED_AND_TWENTY_ONE:
        case LABEL_ONE_HUNDRED_AND_TWENTY_FOUR:
        case LABEL_ONE_HUNDRED_AND_TWENTY_THREE:
        case LABEL_ONE_HUNDRED_AND_TWENTY_SIX:
        case LABEL_ONE_HUNDRED_AND_TWENTY_FIVE:
        case LABEL_ONE_HUNDRED_AND_THIRTY:


            templateNumberDTO.setFechaPresentacion(rs.getString(FECHA_PRESENTACION) != null ?
                                                   rs.getString(FECHA_PRESENTACION) : " ");
            templateNumberDTO.setImporteSolicitado(rs.getString(IMPORTE_SOLICITADO) != null ?
                                                   rs.getString(IMPORTE_SOLICITADO) : " ");
            templateNumberDTO.setConcepto(rs.getString(CONCEPTO) != null ? rs.getString(CONCEPTO) : " ");
            templateNumberDTO.setCalleNum(rs.getString(CALLE_NUM) != null ? rs.getString(CALLE_NUM) : " ");
            templateNumberDTO.setColonia(rs.getString(COLONIA) != null ? rs.getString(COLONIA) : " ");
            templateNumberDTO.setEntreCalles(rs.getString(ENTRE_CALLES) != null ? rs.getString(ENTRE_CALLES) : " ");
            templateNumberDTO.setCp(rs.getString(CP) != null ? rs.getString(CP) : " ");
            templateNumberDTO.setEntidad(rs.getString(ENTIDAD) != null ? rs.getString(ENTIDAD) : " ");
            templateNumberDTO.setMunicipio(rs.getString(MUNICIPIO) != null ? rs.getString(MUNICIPIO) : " ");
            templateNumberDTO.setOrigen(rs.getString(ORIGEN) != null ? rs.getString(ORIGEN) : " ");

            templateNumberDTO.setLeyenda(rs.getString(LEYENDA) != null ? rs.getString(LEYENDA) : " ");

            break;

        case LABEL_TWO:
        case LABEL_FORTY_SIX:
        case LABEL_ONE_HUNDRED_AND_TWO:
        case LABEL_ONE_HUNDRED_AND_TWENTY_TWO:

            templateNumberDTO.setFechaPresentacion(rs.getString(FECHA_PRESENTACION) != null ?
                                                   rs.getString(FECHA_PRESENTACION) : " ");
            templateNumberDTO.setImporteSolicitado(rs.getString(IMPORTE_SOLICITADO) != null ?
                                                   rs.getString(IMPORTE_SOLICITADO) : " ");
            templateNumberDTO.setConcepto(rs.getString(CONCEPTO) != null ? rs.getString(CONCEPTO) : " ");
            templateNumberDTO.setCalleNum(rs.getString(CALLE_NUM) != null ? rs.getString(CALLE_NUM) : " ");
            templateNumberDTO.setColonia(rs.getString(COLONIA) != null ? rs.getString(COLONIA) : " ");
            templateNumberDTO.setEntreCalles(rs.getString(ENTRE_CALLES) != null ? rs.getString(ENTRE_CALLES) : " ");
            templateNumberDTO.setCp(rs.getString(CP) != null ? rs.getString(CP) : " ");
            templateNumberDTO.setEntidad(rs.getString(ENTIDAD) != null ? rs.getString(ENTIDAD) : " ");
            templateNumberDTO.setMunicipio(rs.getString(MUNICIPIO) != null ? rs.getString(MUNICIPIO) : " ");
            templateNumberDTO.setOrigen(rs.getString(ORIGEN) != null ? rs.getString(ORIGEN) : " ");
            templateNumberDTO.setFechaSolventacion(rs.getString(FECHA_SOLVENTACION) != null ?
                                                   rs.getString(FECHA_SOLVENTACION) : " ");
            templateNumberDTO.setFechaNotificacion(rs.getString(FECHA_NOTIFICACION) != null ?
                                                   rs.getString(FECHA_NOTIFICACION) : " ");
            templateNumberDTO.setNumControlDoc(rs.getString(NUM_CONTROL_DOC) != null ? rs.getString(NUM_CONTROL_DOC) :
                                               " ");


            templateNumberDTO.setLeyenda(rs.getString(LEYENDA) != null ? rs.getString(LEYENDA) : " ");
            break;
        case LABEL_THREE:
        case LABEL_ONE_HUNDRED_AND_THIRTY_FIVE:
            templateNumberDTO.setFechaPresentacion(rs.getString(FECHA_PRESENTACION) != null ?
                                                   rs.getString(FECHA_PRESENTACION) : " ");

            templateNumberDTO.setLeyenda(rs.getString(LEYENDA) != null ? rs.getString(LEYENDA) : " ");

            break;
        case LABEL_FOUR:
            templateNumberDTO.setCalleNum(rs.getString(CALLE_NUM) != null ? rs.getString(CALLE_NUM) : " ");
            templateNumberDTO.setColonia(rs.getString(COLONIA) != null ? rs.getString(COLONIA) : " ");
            templateNumberDTO.setEntreCalles(rs.getString(ENTRE_CALLES) != null ? rs.getString(ENTRE_CALLES) : " ");
            templateNumberDTO.setCp(rs.getString(CP) != null ? rs.getString(CP) : " ");
            templateNumberDTO.setEntidad(rs.getString(ENTIDAD) != null ? rs.getString(ENTIDAD) : " ");
            templateNumberDTO.setMunicipio(rs.getString(MUNICIPIO) != null ? rs.getString(MUNICIPIO) : " ");
            templateNumberDTO.setOrigen(rs.getString(ORIGEN) != null ? rs.getString(ORIGEN) : " ");
            templateNumberDTO.setConcepto(rs.getString(CONCEPTO) != null ? rs.getString(CONCEPTO) : " ");

            templateNumberDTO.setLeyenda(rs.getString(LEYENDA) != null ? rs.getString(LEYENDA) : " ");

            break;


        case LABEL_SEVENTEEN:
        case LABEL_ONE_HUNDRED_AND_TWENTY_SEVEN:
            templateNumberDTO.setImporteSolicitado(rs.getString(IMPORTE_SOLICITADO) != null ?
                                                   rs.getString(IMPORTE_SOLICITADO) : " ");
            templateNumberDTO.setCalleNum(rs.getString(CALLE_NUM) != null ? rs.getString(CALLE_NUM) : " ");
            templateNumberDTO.setColonia(rs.getString(COLONIA) != null ? rs.getString(COLONIA) : " ");
            templateNumberDTO.setEntreCalles(rs.getString(ENTRE_CALLES) != null ? rs.getString(ENTRE_CALLES) : " ");
            templateNumberDTO.setCp(rs.getString(CP) != null ? rs.getString(CP) : " ");
            templateNumberDTO.setEntidad(rs.getString(ENTIDAD) != null ? rs.getString(ENTIDAD) : " ");
            templateNumberDTO.setMunicipio(rs.getString(MUNICIPIO) != null ? rs.getString(MUNICIPIO) : " ");
            templateNumberDTO.setFechaPresentacion(rs.getString(FECHA_PRESENTACION) != null ?
                                                   rs.getString(FECHA_PRESENTACION) : " ");
            
            templateNumberDTO.setConcepto(rs.getString(CONCEPTO) != null ? rs.getString(CONCEPTO) : " ");
            
            templateNumberDTO.setOrigen(rs.getString(ORIGEN) != null ? rs.getString(ORIGEN) : " ");

            templateNumberDTO.setLeyenda(rs.getString(LEYENDA) != null ? rs.getString(LEYENDA) : " ");
            break;
        case LABEL_EIGHTEEN:
        case LABEL_ONE_HUNDRED_AND_TWENTY_EIGHT:
            templateNumberDTO.setConcepto(rs.getString(CONCEPTO) != null ? rs.getString(CONCEPTO) : " ");
            templateNumberDTO.setCalleNum(rs.getString(CALLE_NUM) != null ? rs.getString(CALLE_NUM) : " ");
            templateNumberDTO.setColonia(rs.getString(COLONIA) != null ? rs.getString(COLONIA) : " ");
            templateNumberDTO.setEntreCalles(rs.getString(ENTRE_CALLES) != null ? rs.getString(ENTRE_CALLES) : " ");
            templateNumberDTO.setCp(rs.getString(CP) != null ? rs.getString(CP) : " ");
            templateNumberDTO.setEntidad(rs.getString(ENTIDAD) != null ? rs.getString(ENTIDAD) : " ");
            templateNumberDTO.setMunicipio(rs.getString(MUNICIPIO) != null ? rs.getString(MUNICIPIO) : " ");
            templateNumberDTO.setImporteSolicitado(rs.getString(IMPORTE_SOLICITADO) != null ?
                                                   rs.getString(IMPORTE_SOLICITADO) : " ");
            templateNumberDTO.setFechaNotificacion(rs.getString(FECHA_NOTIFICACION) != null ?
                                                   rs.getString(FECHA_NOTIFICACION) : " ");
            templateNumberDTO.setFechaPresentacion(rs.getString(FECHA_PRESENTACION) != null ?
                                                   rs.getString(FECHA_PRESENTACION) : " ");
            templateNumberDTO.setNumControlDoc(rs.getString(NUM_CONTROL_DOC) != null ? rs.getString(NUM_CONTROL_DOC) :
                                               " ");
            
            templateNumberDTO.setOrigen(rs.getString(ORIGEN) != null ? rs.getString(ORIGEN) : " ");

            templateNumberDTO.setLeyenda(rs.getString(LEYENDA) != null ? rs.getString(LEYENDA) : " ");
            break;
        case LABEL_NINETEEN:
        case LABEL_ONE_HUNDRED_AND_TWENTY_NINE:
            templateNumberDTO.setConcepto(rs.getString(CONCEPTO) != null ? rs.getString(CONCEPTO) : " ");
            templateNumberDTO.setCalleNum(rs.getString(CALLE_NUM) != null ? rs.getString(CALLE_NUM) : " ");
            templateNumberDTO.setColonia(rs.getString(COLONIA) != null ? rs.getString(COLONIA) : " ");
            templateNumberDTO.setEntreCalles(rs.getString(ENTRE_CALLES) != null ? rs.getString(ENTRE_CALLES) : " ");
            templateNumberDTO.setCp(rs.getString(CP) != null ? rs.getString(CP) : " ");
            templateNumberDTO.setEntidad(rs.getString(ENTIDAD) != null ? rs.getString(ENTIDAD) : " ");
            templateNumberDTO.setMunicipio(rs.getString(MUNICIPIO) != null ? rs.getString(MUNICIPIO) : " ");
            templateNumberDTO.setImporteSolicitado(rs.getString(IMPORTE_SOLICITADO) != null ?
                                                   rs.getString(IMPORTE_SOLICITADO) : " ");
            templateNumberDTO.setFechaNotificacion(rs.getString(FECHA_NOTIFICACION) != null ?
                                                   rs.getString(FECHA_NOTIFICACION) : " ");
            templateNumberDTO.setFechaPresentacion(rs.getString(FECHA_PRESENTACION) != null ?
                                                   rs.getString(FECHA_PRESENTACION) : " ");
            templateNumberDTO.setFechaSolventacion(rs.getString(FECHA_SOLVENTACION) != null ?
                                                   rs.getString(FECHA_SOLVENTACION) : " ");
            templateNumberDTO.setFechaNotificacion2(rs.getString(FECHA_NOTIFICACION2) != null ?
                                                    rs.getString(FECHA_NOTIFICACION2) : " ");
            
            templateNumberDTO.setOrigen(rs.getString(ORIGEN) != null ? rs.getString(ORIGEN) : " ");

            templateNumberDTO.setLeyenda(rs.getString(LEYENDA) != null ? rs.getString(LEYENDA) : " ");
            templateNumberDTO.setNumControlDoc(rs.getString(NUM_CONTROL_DOC) != null ? rs.getString(NUM_CONTROL_DOC) :
                                               " ");
            
            break;

        case LABEL_TWENTY_FOUR:
        case LABEL_TWENTY_FIVE:
        case LABEL_SIXTY_ONE:
        case LABEL_SIXTY_FOUR:
        case LABEL_SEVENTY_TWO:
        case LABEL_SEVENTY_THREE:
        case LABEL_ONE_HUNDRED_AND_SIX: 
        case LABEL_ONE_HUNDRED_AND_ELEVEN:     
        case LABEL_ONE_HUNDRED_AND_TWELVE:
        case LABEL_ONE_HUNDRED_AND_THIRTEEN:
        case LABEL_ONE_HUNDRED_AND_THIRTY_THREE:
        case LABEL_ONE_HUNDRED_AND_THIRTY_TWO:
            templateNumberDTO.setFechaPresentacion(rs.getString(FECHA_PRESENTACION) != null ?
                                                   rs.getString(FECHA_PRESENTACION) : " ");
            templateNumberDTO.setImporteSolicitado(rs.getString(IMPORTE_SOLICITADO) != null ?
                                                   rs.getString(IMPORTE_SOLICITADO) : " ");
            templateNumberDTO.setConcepto(rs.getString(CONCEPTO) != null ? rs.getString(CONCEPTO) : " ");
            templateNumberDTO.setCalleNum(rs.getString(CALLE_NUM) != null ? rs.getString(CALLE_NUM) : " ");
            templateNumberDTO.setColonia(rs.getString(COLONIA) != null ? rs.getString(COLONIA) : " ");
            templateNumberDTO.setEntreCalles(rs.getString(ENTRE_CALLES) != null ? rs.getString(ENTRE_CALLES) : " ");
            templateNumberDTO.setCp(rs.getString(CP) != null ? rs.getString(CP) : " ");
            templateNumberDTO.setEntidad(rs.getString(ENTIDAD) != null ? rs.getString(ENTIDAD) : " ");
            templateNumberDTO.setMunicipio(rs.getString(MUNICIPIO) != null ? rs.getString(MUNICIPIO) : " ");

            templateNumberDTO.setLeyenda(rs.getString(LEYENDA) != null ? rs.getString(LEYENDA) : " ");

            break;
        case LABEL_SIXTY_TWO:
        case LABEL_ONE_HUNDRED_AND_FOUR:
            templateNumberDTO.setFechaPresentacion(rs.getString(FECHA_PRESENTACION) != null ?
                                                   rs.getString(FECHA_PRESENTACION) : " ");
            templateNumberDTO.setImporteSolicitado(rs.getString(IMPORTE_SOLICITADO) != null ?
                                                   rs.getString(IMPORTE_SOLICITADO) : " ");
            templateNumberDTO.setConcepto(rs.getString(CONCEPTO) != null ? rs.getString(CONCEPTO) : " ");
            templateNumberDTO.setCalleNum(rs.getString(CALLE_NUM) != null ? rs.getString(CALLE_NUM) : " ");
            templateNumberDTO.setColonia(rs.getString(COLONIA) != null ? rs.getString(COLONIA) : " ");
            templateNumberDTO.setEntreCalles(rs.getString(ENTRE_CALLES) != null ? rs.getString(ENTRE_CALLES) : " ");
            templateNumberDTO.setCp(rs.getString(CP) != null ? rs.getString(CP) : " ");
            templateNumberDTO.setEntidad(rs.getString(ENTIDAD) != null ? rs.getString(ENTIDAD) : " ");
            templateNumberDTO.setMunicipio(rs.getString(MUNICIPIO) != null ? rs.getString(MUNICIPIO) : " ");
            templateNumberDTO.setNumControlDoc(rs.getString(NUM_CONTROL_DOC) != null ? rs.getString(NUM_CONTROL_DOC) :
                                               " ");
            templateNumberDTO.setFechaNotificacion(rs.getString(FECHA_NOTIFICACION) != null ?
                                                   rs.getString(FECHA_NOTIFICACION) : " ");

            templateNumberDTO.setLeyenda(rs.getString(LEYENDA) != null ? rs.getString(LEYENDA) : " ");
            /**templateNumberDTO.setDomicilio(rs.getString(DOMICILIO) != null ? rs.getString(DOMICILIO) : " ");*/

            break;
        case LABEL_SIXTY_THREE:
        case LABEL_ONE_HUNDRED_AND_FIVE:
            templateNumberDTO.setFechaPresentacion(rs.getString(FECHA_PRESENTACION) != null ?
                                                   rs.getString(FECHA_PRESENTACION) : " ");
            templateNumberDTO.setImporteSolicitado(rs.getString(IMPORTE_SOLICITADO) != null ?
                                                   rs.getString(IMPORTE_SOLICITADO) : " ");
            templateNumberDTO.setConcepto(rs.getString(CONCEPTO) != null ? rs.getString(CONCEPTO) : " ");
            templateNumberDTO.setCalleNum(rs.getString(CALLE_NUM) != null ? rs.getString(CALLE_NUM) : " ");
            templateNumberDTO.setColonia(rs.getString(COLONIA) != null ? rs.getString(COLONIA) : " ");
            templateNumberDTO.setEntreCalles(rs.getString(ENTRE_CALLES) != null ? rs.getString(ENTRE_CALLES) : " ");
            templateNumberDTO.setCp(rs.getString(CP) != null ? rs.getString(CP) : " ");
            templateNumberDTO.setEntidad(rs.getString(ENTIDAD) != null ? rs.getString(ENTIDAD) : " ");
            templateNumberDTO.setMunicipio(rs.getString(MUNICIPIO) != null ? rs.getString(MUNICIPIO) : " ");
            templateNumberDTO.setEjercicioOrigen(rs.getString(EJERCICIO_ORIGEN) != null ?
                                                 rs.getString(EJERCICIO_ORIGEN) : " ");
            templateNumberDTO.setFechaSolventacion(rs.getString(FECHA_SOLVENTACION) != null ?
                                                   rs.getString(FECHA_SOLVENTACION) : " ");
            templateNumberDTO.setPeriodoOrigen(rs.getString(PERIODO_ORIGEN) != null ? rs.getString(PERIODO_ORIGEN) :
                                               " ");
            templateNumberDTO.setFechaNotificacion2(rs.getString(FECHA_NOTIFICACION2) != null ?
                                                    rs.getString(FECHA_NOTIFICACION2) : " ");
            templateNumberDTO.setFechaNotificacion(rs.getString(FECHA_NOTIFICACION) != null ?
                                                   rs.getString(FECHA_NOTIFICACION) : " ");

            templateNumberDTO.setLeyenda(rs.getString(LEYENDA) != null ? rs.getString(LEYENDA) : " ");
            /**templateNumberDTO.setDomicilio(rs.getString(DOMICILIO) != null ? rs.getString(DOMICILIO) : " ");*/
            break;
        case LABEL_SIXTY_EIGHT:
        case LABEL_SIXTY_NINE:
            templateNumberDTO.setFechaPresentacion(rs.getString(FECHA_PRESENTACION) != null ?
                                                   rs.getString(FECHA_PRESENTACION) : " ");
            templateNumberDTO.setImporteSolicitado(rs.getString(IMPORTE_SOLICITADO) != null ?
                                                   rs.getString(IMPORTE_SOLICITADO) : " ");
            templateNumberDTO.setCalleNum(rs.getString(CALLE_NUM) != null ? rs.getString(CALLE_NUM) : " ");
            templateNumberDTO.setColonia(rs.getString(COLONIA) != null ? rs.getString(COLONIA) : " ");
            templateNumberDTO.setEntreCalles(rs.getString(ENTRE_CALLES) != null ? rs.getString(ENTRE_CALLES) : " ");
            templateNumberDTO.setCp(rs.getString(CP) != null ? rs.getString(CP) : " ");
            templateNumberDTO.setEntidad(rs.getString(ENTIDAD) != null ? rs.getString(ENTIDAD) : " ");
            templateNumberDTO.setMunicipio(rs.getString(MUNICIPIO) != null ? rs.getString(MUNICIPIO) : " ");

            templateNumberDTO.setLeyenda(rs.getString(LEYENDA) != null ? rs.getString(LEYENDA) : " ");

            break;
            /**         case LABEL_SEVENTY_FIVE:
            templateNumberDTO.setEjercicioOrigen(rs.getString(EJERCICIO_ORIGEN) != null ?
                                                 rs.getString(EJERCICIO_ORIGEN) : " ");
            templateNumberDTO.setPeriodoOrigen(rs.getString(PERIODO_ORIGEN) != null ? rs.getString(PERIODO_ORIGEN) :
                                               " ");

            break; */
        case LABEL_SEVENTY_FOUR:
        case LABEL_SEVENTY_FIVE:
        case LABEL_SEVENTY_SIX:
        case LABEL_SEVENTY_SEVEN:
        case LABEL_SEVENTY_EIGHT:
        case LABEL_SEVENTY_NINE:
        case LABEL_EIGHTY_ONE:
        case LABEL_EIGHTY_THREE:
        case LABEL_ONE_HUNDRED_AND_THIRTY_FOUR:
        case LABEL_ONE_HUNDRED_AND_THIRTY_SIX:
        case LABEL_ONE_HUNDRED_AND_THIRTY_SEVEN:


            templateNumberDTO.setLeyenda(rs.getString(LEYENDA) != null ? rs.getString(LEYENDA) : " ");

            templateNumberDTO.setCalleNum(rs.getString(CALLE_NUM) != null ? rs.getString(CALLE_NUM) : " ");
            templateNumberDTO.setColonia(rs.getString(COLONIA) != null ? rs.getString(COLONIA) : " ");
            templateNumberDTO.setEntreCalles(rs.getString(ENTRE_CALLES) != null ? rs.getString(ENTRE_CALLES) : " ");
            templateNumberDTO.setCp(rs.getString(CP) != null ? rs.getString(CP) : " ");
            templateNumberDTO.setEntidad(rs.getString(ENTIDAD) != null ? rs.getString(ENTIDAD) : " ");
            templateNumberDTO.setMunicipio(rs.getString(MUNICIPIO) != null ? rs.getString(MUNICIPIO) : " ");

            /** templateNumberDTO.setDomicilio(rs.getString(DOMICILIO) != null ? rs.getString(DOMICILIO) : " "); */

            templateNumberDTO.setFechaPresentacion(rs.getString(FECHA_PRESENTACION) != null ?
                                                   rs.getString(FECHA_PRESENTACION) : " ");
            templateNumberDTO.setNumOperacion(rs.getString(NUM_OPERACION) != null ? rs.getString(NUM_OPERACION) : " ");
            templateNumberDTO.setOrigen(rs.getString(ORIGEN) != null ? rs.getString(ORIGEN) : " ");
            templateNumberDTO.setImpuestoOrigen(rs.getString(IMPUESTO_ORIGEN) != null ? rs.getString(IMPUESTO_ORIGEN) :
                                                " ");
            templateNumberDTO.setPeriodoOrigen(rs.getString(PERIODO_ORIGEN) != null ? rs.getString(PERIODO_ORIGEN) :
                                               " ");
            templateNumberDTO.setPeriodo(rs.getString(PERIODO) != null ? rs.getString(PERIODO) : " ");
            templateNumberDTO.setEjercicioOrigen(rs.getString(EJERCICIO_ORIGEN) != null ?
                                                 rs.getString(EJERCICIO_ORIGEN) : " ");
            templateNumberDTO.setEjercicio(rs.getString(EJERCICIO) != null ? rs.getString(EJERCICIO) : " ");
            templateNumberDTO.setImporteTotalComp(rs.getString(IMPORTE_TOTAL_COMP) != null ?
                                                  rs.getString(IMPORTE_TOTAL_COMP) : " ");
            templateNumberDTO.setImpuestoDestino(rs.getString(IMPUESTO_COMPENSADO) != null ?
                                                 rs.getString(IMPUESTO_COMPENSADO) : " ");

            templateNumberDTO.setFechaComp(rs.getString(FECHA_COMP) != null ? rs.getString(FECHA_COMP) : " ");
            templateNumberDTO.setImporteSolicitado(rs.getString(IMPORTE_SOLICITADO) != null ?
                                                   rs.getString(IMPORTE_SOLICITADO) : " ");
            templateNumberDTO.setConceptoDestino(rs.getString(CONCEPTO_DESTINO) != null ?
                                                 rs.getString(CONCEPTO_DESTINO) : " ");
            templateNumberDTO.setConcepto(rs.getString(CONCEPTO) != null ? rs.getString(CONCEPTO) : " ");


            if (numTemplate == LABEL_SEVENTY_EIGHT || numTemplate == LABEL_EIGHTY_THREE || numTemplate == LABEL_ONE_HUNDRED_AND_THIRTY_SEVEN) {
                templateNumberDTO.setMesCompensa(rs.getString(MESCOMPENSA) != null ? rs.getString(MESCOMPENSA) : " ");
                templateNumberDTO.setAnioCompensa(rs.getString(ANIOCOMPENSA) != null ? rs.getString(ANIOCOMPENSA) :
                                                  " ");
                templateNumberDTO.setFundamentacion(rs.getString(FUNDAMENTACION) != null ?
                                                    rs.getString(FUNDAMENTACION) : " ");
                templateNumberDTO.setImporteActualizado(rs.getString(IMPORTEACTUALIZA) != null ?
                                                        rs.getString(IMPORTEACTUALIZA) : " ");
                templateNumberDTO.setImporteRecargo(rs.getString(IMPORTERECARGO) != null ?
                                                    rs.getString(IMPORTERECARGO) : " ");
            }

            break;
        case LABEL_NINETY_FIVE:
            templateNumberDTO.setCalleNum(rs.getString(CALLE_NUM) != null ? rs.getString(CALLE_NUM) : " ");
            templateNumberDTO.setColonia(rs.getString(COLONIA) != null ? rs.getString(COLONIA) : " ");
            templateNumberDTO.setEntreCalles(rs.getString(ENTRE_CALLES) != null ? rs.getString(ENTRE_CALLES) : " ");
            templateNumberDTO.setCp(rs.getString(CP) != null ? rs.getString(CP) : " ");
            templateNumberDTO.setEntidad(rs.getString(ENTIDAD) != null ? rs.getString(ENTIDAD) : " ");
            templateNumberDTO.setMunicipio(rs.getString(MUNICIPIO) != null ? rs.getString(MUNICIPIO) : " ");
            templateNumberDTO.setFechaPresentacion(rs.getString(FECHA_PRESENTACION) != null ?
                                                   rs.getString(FECHA_PRESENTACION) : " ");
            templateNumberDTO.setImporteSolicitado(rs.getString(IMPORTE_SOLICITADO) != null ?
                                                   rs.getString(IMPORTE_SOLICITADO) : " ");

            templateNumberDTO.setLeyenda(rs.getString(LEYENDA) != null ? rs.getString(LEYENDA) : " ");
            break;

        case LABEL_TWENTY_TWO:
            templateNumberDTO.setCalleNum(rs.getString(CALLE_NUM) != null ? rs.getString(CALLE_NUM) : " ");
            templateNumberDTO.setColonia(rs.getString(COLONIA) != null ? rs.getString(COLONIA) : " ");
            templateNumberDTO.setEntreCalles(rs.getString(ENTRE_CALLES) != null ? rs.getString(ENTRE_CALLES) : " ");
            templateNumberDTO.setCp(rs.getString(CP) != null ? rs.getString(CP) : " ");
            templateNumberDTO.setEntidad(rs.getString(ENTIDAD) != null ? rs.getString(ENTIDAD) : " ");
            templateNumberDTO.setMunicipio(rs.getString(MUNICIPIO) != null ? rs.getString(MUNICIPIO) : " ");
            templateNumberDTO.setMotivoSinDeposito(rs.getString(MOTIVO_SIN_DEPOSITO) != null ?
                                                   rs.getString(MOTIVO_SIN_DEPOSITO) : " ");
            templateNumberDTO.setNumControl(rs.getString(NUM_CONTROL) != null ? rs.getString(NUM_CONTROL) : " ");
            templateNumberDTO.setFechaPresentacion(rs.getString(FECHA_PRESENTACION) != null ?
                                                   rs.getString(FECHA_PRESENTACION) : " ");
            templateNumberDTO.setCurp(rs.getString(CURP) != null ? rs.getString(CURP) : " ");
            templateNumberDTO.setFraccion(rs.getString(FRACCION) != null ? rs.getString(FRACCION) : " ");
            templateNumberDTO.setNomFunAutorizado(rs.getString(NOM_FUN_AUTORIZADO) != null ?
                                                  rs.getString(NOM_FUN_AUTORIZADO) : " ");

            templateNumberDTO.setDomicilioAlaf(rs.getString(DOMICILIO_ALAF) != null ? rs.getString(DOMICILIO_ALAF) :
                                               " ");
            templateNumberDTO.setConcepto(rs.getString(CONCEPTO) != null ? rs.getString(CONCEPTO) : " ");
            templateNumberDTO.setOrigen(rs.getString(ORIGEN) != null ? rs.getString(ORIGEN) : " ");
             templateNumberDTO.setImporteSolicitado(rs.getString(IMPORTE_SOLICITADO) != null ?
                                                   rs.getString(IMPORTE_SOLICITADO) : " ");

            break;
        case LABEL_SIXTY_SIX:
        case LABEL_ONE_HUNDRED_AND_FOURTEEN:
            templateNumberDTO.setCalleNum(rs.getString(CALLE_NUM) != null ? rs.getString(CALLE_NUM) : " ");
            templateNumberDTO.setColonia(rs.getString(COLONIA) != null ? rs.getString(COLONIA) : " ");
            templateNumberDTO.setEntreCalles(rs.getString(ENTRE_CALLES) != null ? rs.getString(ENTRE_CALLES) : " ");
            templateNumberDTO.setCp(rs.getString(CP) != null ? rs.getString(CP) : " ");
            templateNumberDTO.setEntidad(rs.getString(ENTIDAD) != null ? rs.getString(ENTIDAD) : " ");
            templateNumberDTO.setMunicipio(rs.getString(MUNICIPIO) != null ? rs.getString(MUNICIPIO) : " ");
            templateNumberDTO.setNumControl(rs.getString(NUM_CONTROL) != null ? rs.getString(NUM_CONTROL) : " ");
            templateNumberDTO.setMotivoSinDeposito(rs.getString(MOTIVO_SIN_DEPOSITO) != null ?
                                                   rs.getString(MOTIVO_SIN_DEPOSITO) : " ");
            templateNumberDTO.setFechaPresentacion(rs.getString(FECHA_PRESENTACION) != null ?
                                                   rs.getString(FECHA_PRESENTACION) : " ");
            templateNumberDTO.setFraccion(rs.getString(FRACCION) != null ? rs.getString(FRACCION) : " ");
            break;
        
        case LABEL_ONE_HUNDRED_AND_THIRTY_ONE:
            templateNumberDTO.setBoId(rs.getString(BO_ID) != null ? rs.getString(BO_ID) : " ");
            templateNumberDTO.setAdministracion(rs.getString(ADMINISTRACION) != null ? rs.getString(ADMINISTRACION) : " ");
            templateNumberDTO.setRfcContribuyente(rs.getString(RFC_CONTRIBUYENTE) != null ? rs.getString(RFC_CONTRIBUYENTE) : " ");
            templateNumberDTO.setRazonSocial(rs.getString(RAZON_SOCIAL) != null ? rs.getString(RAZON_SOCIAL) : " ");
            templateNumberDTO.setCurp(rs.getString(CURP) != null ? rs.getString(CURP) : " ");
            templateNumberDTO.setDomicilioAlaf(rs.getString(DOMICILIO_ALAF) != null ? rs.getString(DOMICILIO_ALAF) : " ");
            templateNumberDTO.setDomicilio(rs.getString(DOMICILIO) != null ? rs.getString(DOMICILIO) : " ");
            templateNumberDTO.setFechaPresentacion(rs.getString(FECHA_PRESENTACION) != null ? rs.getString(FECHA_PRESENTACION) : " ");
            templateNumberDTO.setNumControl(rs.getString(NUM_CONTROL) != null ? rs.getString(NUM_CONTROL) : " ");
            templateNumberDTO.setMotivoSinDeposito(rs.getString(MOTIVO_SIN_DEPOSITO) != null ?
                                                   rs.getString(MOTIVO_SIN_DEPOSITO) : " ");
            templateNumberDTO.setNumeroDocumento(rs.getString(NUMERO_DOCUMENTO) != null ? rs.getString(NUMERO_DOCUMENTO) : " ");
            templateNumberDTO.setClaveAdministracion(rs.getString(CLAVE_ADMINISTRACION) != null ? rs.getString(CLAVE_ADMINISTRACION) : " ");
            templateNumberDTO.setFraccion(rs.getString(FRACCION) != null ? rs.getString(FRACCION) : " ");
            templateNumberDTO.setLeyenda(rs.getString(LEYENDA) != null ? rs.getString(LEYENDA) : " ");
            templateNumberDTO.setImporteNeto(rs.getString(IMPORTENETO) != null ? rs.getString(IMPORTENETO) : " ");
            break;
        case LABEL_ONE_HUNDRED_AND_THIRTY_EIGHT:

            String valorCalleNum            = rs.getString( CALLE_NUM )           != null ? rs.getString( CALLE_NUM )           : " ";
            String valorColonia             = rs.getString( COLONIA )             != null ? rs.getString( COLONIA )             : " ";
            String valorEntreCalles         = rs.getString( ENTRE_CALLES )        != null ? rs.getString( ENTRE_CALLES )        : " ";
            String valorCp                  = rs.getString( CP )                  != null ? rs.getString( CP )                  : " ";
            String valorEntidad             = rs.getString( ENTIDAD )             != null ? rs.getString( ENTIDAD )             : " ";
            String valorMunicipio           = rs.getString( MUNICIPIO )           != null ? rs.getString( MUNICIPIO )           : " ";
            String valorMotivoSinDeposito   = rs.getString( MOTIVO_SIN_DEPOSITO ) != null ? rs.getString( MOTIVO_SIN_DEPOSITO ) : " ";
            String valorNumControl          = rs.getString( NUM_CONTROL )         != null ? rs.getString( NUM_CONTROL )         : " ";
            String valorFechaPresentacion   = rs.getString( FECHA_PRESENTACION )  != null ? rs.getString( FECHA_PRESENTACION )  : " ";
            String valorCurp                = rs.getString( CURP )                != null ? rs.getString( CURP )                : " ";
            String valorFraccion            = rs.getString( FRACCION )            != null ? rs.getString( FRACCION )            : " ";
            String valorNomFunAutorizado    = rs.getString( NOM_FUN_AUTORIZADO )  != null ? rs.getString( NOM_FUN_AUTORIZADO )  : " ";
            String valorDomicilioAlaf       = rs.getString( DOMICILIO_ALAF )      != null ? rs.getString( DOMICILIO_ALAF )      : " ";
            String valorConcepto            = rs.getString( CONCEPTO )            != null ? rs.getString( CONCEPTO )            : " ";
            String valorOrigen              = rs.getString( ORIGEN )              != null ? rs.getString( ORIGEN )              : " ";
            String valorImporteSolicitado   = rs.getString( IMPORTE_SOLICITADO )  != null ? rs.getString( IMPORTE_SOLICITADO )  : " ";
            String valorMontoAplicar        = rs.getString( MONTO_APLICAR )       != null ? rs.getString( MONTO_APLICAR )       : " ";
            String valorTipoResolucion      = rs.getString( TIPO_RESOLUCION )     != null ? rs.getString( TIPO_RESOLUCION )     : " ";

            templateNumberDTO.setCp(                valorCp );
            templateNumberDTO.setCurp(              valorCurp );
            templateNumberDTO.setOrigen(            valorOrigen );
            templateNumberDTO.setEntidad(           valorEntidad );
            templateNumberDTO.setColonia(           valorColonia );
            templateNumberDTO.setCalleNum(          valorCalleNum );
            templateNumberDTO.setConcepto(          valorConcepto );
            templateNumberDTO.setFraccion(          valorFraccion );
            templateNumberDTO.setMunicipio(         valorMunicipio );
            templateNumberDTO.setNumControl(        valorNumControl );
            templateNumberDTO.setEntreCalles(       valorEntreCalles );
            templateNumberDTO.setMontoAplicar(      valorMontoAplicar );
            templateNumberDTO.setDomicilioAlaf(     valorDomicilioAlaf );
            templateNumberDTO.setTipoResolucion(    valorTipoResolucion );
            templateNumberDTO.setNomFunAutorizado(  valorNomFunAutorizado );
            templateNumberDTO.setFechaPresentacion( valorFechaPresentacion );
            templateNumberDTO.setImporteSolicitado( valorImporteSolicitado );
            templateNumberDTO.setMotivoSinDeposito( valorMotivoSinDeposito );

            break;
        default:
            break;
        }

        return templateNumberDTO;
    }


}


