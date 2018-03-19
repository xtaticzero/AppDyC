package mx.gob.sat.siat.dyc.template.web.util;

import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.generico.util.FechaUtil;
import mx.gob.sat.siat.dyc.template.dto.TemplateNumberDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesPlantillador;
import mx.gob.sat.siat.pjplantillador.dto.Items;

import org.apache.log4j.Logger;


public class LoadItems implements ConstantesPlantillador {

    private static final Logger LOG = Logger.getLogger(LoadItems.class);


    public LoadItems() {
        super();

    }

    public String actualizaNombreDF(String estado){
        if (estado.contains(MEX + DF)){
            return estado.replace(MEX+DF, CDMX);
        }
        if (estado.contains(MEX + ", " + DF)){
            return estado.replace(MEX + ", " + DF, CDMX);
        }
        return estado.replace(DF, CDMX);
        }
   
    public List<Items> loadItemsFromAnyWhere(String idTemplate, TemplateNumberDTO datosDocumento)
    {
        try {
            if (!datosDocumento.getRazonSocial().toUpperCase().contains("Ñ")){
                datosDocumento.setRazonSocial(new String(datosDocumento.getRazonSocial().getBytes("ISO-8859-1"),"UTF-8"));
            }
        } catch (UnsupportedEncodingException e) {
            LOG.info("Error en conversion iso a utf8: "+e);
        }
        StringBuilder id =
            idTemplate.length() < 2 ? new StringBuilder("00").append(idTemplate) : new StringBuilder(idTemplate);


        ArrayList<Items> items = new ArrayList<Items>();
        
        String valorFechaTag30 = FechaUtil.formatearFechaTag30(datosDocumento.getFechaNotificacion());
        
        {
            
            items.add(new Items(TagNumber.TAG_003.getTagDescreption(),
                                datosDocumento.getAdministracion() != null ? datosDocumento.getAdministracion() :
                                " "));

            items.add(new Items(TagNumber.TAG_097.getTagDescreption(),
                                datosDocumento.getRazonSocial() != null ? datosDocumento.getRazonSocial() :
                                " "));

            items.add(new Items(TagNumber.TAG_110.getTagDescreption(),
                                datosDocumento.getNumeroDocumento() != null ? id.append(datosDocumento.getNumeroDocumento()).toString() :
                                " "));

            if (Integer.valueOf(idTemplate) != LABEL_SIXTY_SIX && Integer.valueOf(idTemplate) != LABEL_ONE_HUNDRED_AND_FOURTEEN) {

                items.add(new Items(TagNumber.TAG_019.getTagDescreption(),
                                    datosDocumento.getEjercicio() != null ? datosDocumento.getEjercicio() :
                                    " "));
                items.add(new Items(TagNumber.TAG_103.getTagDescreption(),
                                    datosDocumento.getNumControl() != null ? datosDocumento.getNumControl().toString() :
                                    " "));
                items.add(new Items(TagNumber.TAG_119.getTagDescreption(),
                                    datosDocumento.getPeriodo() != null ? datosDocumento.getPeriodo().toString() :
                                    " "));
            }


            items.add(new Items(TagNumber.TAG_132.getTagDescreption(),
                                datosDocumento.getRfcContribuyente() != null ?
                                datosDocumento.getRfcContribuyente().toString() : " "));
            
            boolean primeraCondicionI = Integer.valueOf(idTemplate) <= LABEL_TWENTY || Integer.valueOf(idTemplate) == LABEL_FOUR ||
                Integer.valueOf(idTemplate) == LABEL_FIVE || Integer.valueOf(idTemplate) == LABEL_SEVENTY_FOUR;
                
            boolean segundaCondicionI = Integer.valueOf(idTemplate) == LABEL_SEVENTY_FIVE ||
                Integer.valueOf(idTemplate) == LABEL_SEVENTY_SIX ||
                Integer.valueOf(idTemplate) == LABEL_SEVENTY_SEVEN ||
                Integer.valueOf(idTemplate) == LABEL_SEVENTY_EIGHT;
                        
            boolean terceraCondicionI = Integer.valueOf(idTemplate) == LABEL_SEVENTY_NINE || Integer.valueOf(idTemplate) == LABEL_EIGHTY_ONE ||
                Integer.valueOf(idTemplate) == LABEL_EIGHTY_THREE;
            
            
            boolean primeraCondicionII = Integer.valueOf(idTemplate) != LABEL_FOUR || Integer.valueOf(idTemplate) == LABEL_SEVENTY_FOUR ||
                    Integer.valueOf(idTemplate) == LABEL_SEVENTY_SIX ||
                    Integer.valueOf(idTemplate) == LABEL_SEVENTY_SEVEN;
                
            boolean segundaCondicionII = Integer.valueOf(idTemplate) == LABEL_SEVENTY_EIGHT ||
                    Integer.valueOf(idTemplate) == LABEL_SEVENTY_NINE ||
                    Integer.valueOf(idTemplate) == LABEL_EIGHTY_ONE ||
                    Integer.valueOf(idTemplate) == LABEL_EIGHTY_THREE;
            
                
            if(primeraCondicionI || segundaCondicionI || terceraCondicionI) {

                if (primeraCondicionII || segundaCondicionII) {
                    items.add(new Items(TagNumber.TAG_048.getTagDescreption(),
                                        datosDocumento.getFraccion() != null ?
                                        datosDocumento.getFraccion().toString() : " "));
                }
                items.add(new Items(TagNumber.TAG_134.getTagDescreption(),
                                    datosDocumento.getCiudadAdmLocal() != null ?
                                    actualizaNombreDF(datosDocumento.getCiudadAdmLocal().toString()) : " "));

                items.add(new Items(TagNumber.TAG_200.getTagDescreption(),
                                    datosDocumento.getDomicilioAlaf() != null ?
                                    actualizaNombreDF(datosDocumento.getDomicilioAlaf().toString()) : " "));
                items.add(new Items(TagNumber.TAG_270.getTagDescreption(),
                                    datosDocumento.getSerieDocumental() != null ?
                                    datosDocumento.getSerieDocumental().toString() : " "));
            }


            switch (Integer.valueOf(idTemplate)) {
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

                items.add(new Items(TagNumber.TAG_010.getTagDescreption(),
                                    datosDocumento.getConcepto() != null ? datosDocumento.getConcepto() : " "));

                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getCalleNum() != null ? datosDocumento.getCalleNum() : " "));
                if (!datosDocumento.getColonia().equals("")) {
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(), datosDocumento.getColonia()));
                }
                if (!datosDocumento.getEntreCalles().equals(" ")) {
                    items.add(new Items(TagNumber.TAG_017.getTagDescreption(), datosDocumento.getEntreCalles()));
                }
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getCp() != null ? datosDocumento.getCp() : " "));
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getMunicipio() != null ? datosDocumento.getMunicipio() :
                                    " "));
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getEntidad() != null ? datosDocumento.getEntidad() : " "));
                items.add(new Items(TagNumber.TAG_035.getTagDescreption(),
                                    datosDocumento.getFechaPresentacion() != null ?
                                    datosDocumento.getFechaPresentacion() : " "));
                items.add(new Items(TagNumber.TAG_076.getTagDescreption(),
                                    datosDocumento.getImporteSolicitado() != null ?
                                    datosDocumento.getImporteSolicitado() : " "));
                items.add(new Items(TagNumber.TAG_115.getTagDescreption(),
                                    datosDocumento.getOrigen() != null ? datosDocumento.getOrigen() : " "));


                items.add(new Items(TagNumber.TAG_088.getTagDescreption(),
                                    datosDocumento.getLeyenda() != null ? datosDocumento.getLeyenda().toString() :
                                    " "));


                break;


            case LABEL_TWO:
            case LABEL_FORTY_SIX:
            case LABEL_ONE_HUNDRED_AND_TWO:
            case LABEL_ONE_HUNDRED_AND_TWENTY_TWO:
                
                items.add(new Items(TagNumber.TAG_010.getTagDescreption(),
                                    datosDocumento.getConcepto() != null ? datosDocumento.getConcepto() : " "));
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getCalleNum() != null ? datosDocumento.getCalleNum() : " "));
                if (!datosDocumento.getColonia().equals("")) {
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(), datosDocumento.getColonia()));
                }
                if (!datosDocumento.getEntreCalles().equals(" ")) {
                    items.add(new Items(TagNumber.TAG_017.getTagDescreption(), datosDocumento.getEntreCalles()));
                }
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getCp() != null ? datosDocumento.getCp() : " "));
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getMunicipio() != null ? datosDocumento.getMunicipio() :
                                    " "));
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getEntidad() != null ? datosDocumento.getEntidad() : " "));
                items.add(new Items(TagNumber.TAG_030.getTagDescreption(), valorFechaTag30));
                items.add(new Items(TagNumber.TAG_035.getTagDescreption(),
                                    datosDocumento.getFechaPresentacion() != null ?
                                    datosDocumento.getFechaPresentacion() : " "));
                items.add(new Items(TagNumber.TAG_046.getTagDescreption(),
                                    datosDocumento.getFechaSolventacion() != null ?
                                    datosDocumento.getFechaSolventacion() : " "));
                items.add(new Items(TagNumber.TAG_076.getTagDescreption(),
                                    datosDocumento.getImporteSolicitado() != null ?
                                    datosDocumento.getImporteSolicitado() : " "));
                items.add(new Items(TagNumber.TAG_115.getTagDescreption(),
                                    datosDocumento.getOrigen() != null ? datosDocumento.getOrigen() : " "));
                items.add(new Items(TagNumber.TAG_247.getTagDescreption(),
                                    datosDocumento.getNumControlDoc() != null ?
                                    datosDocumento.getNumControlDoc() : " "));


                items.add(new Items(TagNumber.TAG_088.getTagDescreption(),
                                    datosDocumento.getLeyenda() != null ? datosDocumento.getLeyenda().toString() :
                                    " "));

                break;

            case LABEL_THREE:
            case LABEL_ONE_HUNDRED_AND_THIRTY_FIVE:
                items.add(new Items(TagNumber.TAG_035.getTagDescreption(),
                                    datosDocumento.getFechaPresentacion() != null ?
                                    datosDocumento.getFechaPresentacion() : " "));

                items.add(new Items(TagNumber.TAG_088.getTagDescreption(),
                                    datosDocumento.getLeyenda() != null ? datosDocumento.getLeyenda().toString() :
                                    " "));
                break;

            case LABEL_FOUR:

                items.add(new Items(TagNumber.TAG_010.getTagDescreption(),
                                    datosDocumento.getConcepto() != null ? datosDocumento.getConcepto() : " "));
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getCalleNum() != null ? datosDocumento.getCalleNum() : " "));
                if (!datosDocumento.getColonia().equals("")) {
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(), datosDocumento.getColonia()));
                }
                if (!datosDocumento.getEntreCalles().equals(" ")) {
                    items.add(new Items(TagNumber.TAG_017.getTagDescreption(), datosDocumento.getEntreCalles()));
                }
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getCp() != null ? datosDocumento.getCp() : " "));
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getMunicipio() != null ? datosDocumento.getMunicipio() :
                                    " "));
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getEntidad() != null ? datosDocumento.getEntidad() : " "));
                items.add(new Items(TagNumber.TAG_115.getTagDescreption(),
                                    datosDocumento.getOrigen() != null ? datosDocumento.getOrigen() : " "));

                items.add(new Items(TagNumber.TAG_088.getTagDescreption(),
                                    datosDocumento.getLeyenda() != null ? datosDocumento.getLeyenda().toString() :
                                    " "));

                break;


            case LABEL_SEVENTEEN:
            case LABEL_ONE_HUNDRED_AND_TWENTY_SEVEN:
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getCalleNum() != null ? datosDocumento.getCalleNum() : " "));
                if (!datosDocumento.getColonia().equals("")) {
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(), datosDocumento.getColonia()));
                }
                if (!datosDocumento.getEntreCalles().equals(" ")) {
                    items.add(new Items(TagNumber.TAG_017.getTagDescreption(), datosDocumento.getEntreCalles()));
                }
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getCp() != null ? datosDocumento.getCp() : " "));
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getMunicipio() != null ? datosDocumento.getMunicipio() :
                                    " "));
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getEntidad() != null ? datosDocumento.getEntidad() : " "));
                items.add(new Items(TagNumber.TAG_035.getTagDescreption(),
                                    datosDocumento.getFechaPresentacion() != null ?
                                    datosDocumento.getFechaPresentacion() : " "));
                items.add(new Items(TagNumber.TAG_076.getTagDescreption(),
                                    datosDocumento.getImporteSolicitado() != null ?
                                    datosDocumento.getImporteSolicitado() : " "));
                
                items.add(new Items(TagNumber.TAG_010.getTagDescreption(),
                                    datosDocumento.getConcepto() != null ? datosDocumento.getConcepto() : " "));


                items.add(new Items(TagNumber.TAG_088.getTagDescreption(),
                                    datosDocumento.getLeyenda() != null ? datosDocumento.getLeyenda().toString() :
                                    " "));
                items.add(new Items(TagNumber.TAG_115.getTagDescreption(),
                                    datosDocumento.getOrigen() != null ? datosDocumento.getOrigen() : " "));                

                break;


            case LABEL_EIGHTEEN:
            case LABEL_ONE_HUNDRED_AND_TWENTY_EIGHT:
                items.add(new Items(TagNumber.TAG_010.getTagDescreption(),
                                    datosDocumento.getConcepto() != null ? datosDocumento.getConcepto() : " "));

                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getCalleNum() != null ? datosDocumento.getCalleNum() : " "));
                if (!datosDocumento.getColonia().equals("")) {
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(), datosDocumento.getColonia()));
                }
                if (!datosDocumento.getEntreCalles().equals(" ")) {
                    items.add(new Items(TagNumber.TAG_017.getTagDescreption(), datosDocumento.getEntreCalles()));
                }
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getCp() != null ? datosDocumento.getCp() : " "));
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getMunicipio() != null ? datosDocumento.getMunicipio() :
                                    " "));
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getEntidad() != null ? datosDocumento.getEntidad() : " "));

                items.add(new Items(TagNumber.TAG_030.getTagDescreption(), valorFechaTag30));
                
                items.add(new Items(TagNumber.TAG_035.getTagDescreption(),
                                    datosDocumento.getFechaPresentacion() != null ?
                                    datosDocumento.getFechaPresentacion() : " "));

                items.add(new Items(TagNumber.TAG_076.getTagDescreption(),
                                    datosDocumento.getImporteSolicitado() != null ?
                                    datosDocumento.getImporteSolicitado() : " "));
                items.add(new Items(TagNumber.TAG_247.getTagDescreption(),
                                    datosDocumento.getNumControlDoc() != null ?
                                    datosDocumento.getNumControlDoc() : " "));


                items.add(new Items(TagNumber.TAG_088.getTagDescreption(),
                                    datosDocumento.getLeyenda() != null ? datosDocumento.getLeyenda().toString() :
                                    " "));
                
                items.add(new Items(TagNumber.TAG_115.getTagDescreption(),
                                    datosDocumento.getOrigen() != null ? datosDocumento.getOrigen() : " "));  
                
                break;

            case LABEL_NINETEEN:
            case LABEL_ONE_HUNDRED_AND_TWENTY_NINE:

                items.add(new Items(TagNumber.TAG_010.getTagDescreption(),
                                    datosDocumento.getConcepto() != null ? datosDocumento.getConcepto().toString() :
                                    " "));

                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getCalleNum() != null ? datosDocumento.getCalleNum() : " "));
                if (!datosDocumento.getColonia().equals("")) {
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(), datosDocumento.getColonia()));
                }
                if (!datosDocumento.getEntreCalles().equals(" ")) {

                    items.add(new Items(TagNumber.TAG_017.getTagDescreption(), datosDocumento.getEntreCalles()));

                }
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getCp() != null ? datosDocumento.getCp() : " "));
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getMunicipio() != null ? datosDocumento.getMunicipio() :
                                    " "));
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getEntidad() != null ? datosDocumento.getEntidad() : " "));

                items.add(new Items(TagNumber.TAG_030.getTagDescreption(), valorFechaTag30));
                
                items.add(new Items(TagNumber.TAG_035.getTagDescreption(),
                                    datosDocumento.getFechaPresentacion() != null ?
                                    datosDocumento.getFechaPresentacion() : " "));

                items.add(new Items(TagNumber.TAG_046.getTagDescreption(),
                                    datosDocumento.getFechaSolventacion() != null ?
                                    datosDocumento.getFechaSolventacion() : " "));

                items.add(new Items(TagNumber.TAG_076.getTagDescreption(),
                                    datosDocumento.getImporteSolicitado() != null ?
                                    datosDocumento.getImporteSolicitado() : " "));
                items.add(new Items(TagNumber.TAG_291.getTagDescreption(),
                                    datosDocumento.getFechaNotificacion2() != null ?
                                    datosDocumento.getFechaNotificacion2() : " "));


                items.add(new Items(TagNumber.TAG_088.getTagDescreption(),
                                    datosDocumento.getLeyenda() != null ? datosDocumento.getLeyenda().toString() :
                                    " "));
                
                items.add(new Items(TagNumber.TAG_115.getTagDescreption(),
                                    datosDocumento.getOrigen() != null ? datosDocumento.getOrigen() : " "));  

                items.add(new Items(TagNumber.TAG_247.getTagDescreption(),
                                    datosDocumento.getNumControlDoc() != null ?
                                    datosDocumento.getNumControlDoc() : " "));
                
                items.add(new Items(TagNumber.TAG_287.getTagDescreption(),
                                    datosDocumento.getFechaSolventacion() != null ?
                                    datosDocumento.getFechaSolventacion() : " "));
                break;

            case LABEL_SIXTY_TWO:
            case LABEL_ONE_HUNDRED_AND_FOUR:
                items.add(new Items(TagNumber.TAG_010.getTagDescreption(),
                                    datosDocumento.getConcepto() != null ? datosDocumento.getConcepto().toString() :
                                    " "));

                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getCalleNum() != null ? datosDocumento.getCalleNum() : " "));
                if (!datosDocumento.getColonia().equals("")) {
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(), datosDocumento.getColonia()));
                }
                if (!datosDocumento.getEntreCalles().equals(" ")) {

                    items.add(new Items(TagNumber.TAG_017.getTagDescreption(), datosDocumento.getEntreCalles()));
                }
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getCp() != null ? datosDocumento.getCp() : " "));
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getMunicipio() != null ? datosDocumento.getMunicipio() :
                                    " "));
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getEntidad() != null ? datosDocumento.getEntidad() : " "));
                items.add(new Items(TagNumber.TAG_030.getTagDescreption(), valorFechaTag30));
                
                items.add(new Items(TagNumber.TAG_035.getTagDescreption(),
                                    datosDocumento.getFechaPresentacion() != null ?
                                    datosDocumento.getFechaPresentacion() : " "));
                items.add(new Items(TagNumber.TAG_076.getTagDescreption(),
                                    datosDocumento.getImporteSolicitado() != null ?
                                    datosDocumento.getImporteSolicitado() : " "));
                items.add(new Items(TagNumber.TAG_247.getTagDescreption(),
                                    datosDocumento.getNumControlDoc() != null ?
                                    datosDocumento.getNumControlDoc() : " "));


                items.add(new Items(TagNumber.TAG_088.getTagDescreption(),
                                    datosDocumento.getLeyenda() != null ? datosDocumento.getLeyenda().toString() :
                                    " "));
                items.add(new Items(TagNumber.TAG_400.getTagDescreption(),
                                    datosDocumento.getDomicilio() != null ? datosDocumento.getDomicilio().toString() :
                                    " "));


                break;
            case LABEL_SIXTY_THREE:
            case LABEL_ONE_HUNDRED_AND_FIVE:

                items.add(new Items(TagNumber.TAG_010.getTagDescreption(),
                                    datosDocumento.getConcepto() != null ? datosDocumento.getConcepto().toString() :
                                    " "));

                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getCalleNum() != null ? datosDocumento.getCalleNum() : " "));
                if (!datosDocumento.getColonia().equals("")) {
                    items.add(new Items(TagNumber.TAG_017.getTagDescreption(), datosDocumento.getColonia()));
                }
                if (!datosDocumento.getEntreCalles().equals(" ")) {
                    items.add(new Items(TagNumber.TAG_017.getTagDescreption(), datosDocumento.getEntreCalles()));
                }
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getCp() != null ? datosDocumento.getCp() : " "));
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getMunicipio() != null ? datosDocumento.getMunicipio() :
                                    " "));
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getEntidad() != null ? datosDocumento.getEntidad() : " "));
                items.add(new Items(TagNumber.TAG_018.getTagDescreption(),
                                    datosDocumento.getEjercicioOrigen() != null ?
                                    datosDocumento.getEjercicioOrigen() : " "));
                items.add(new Items(TagNumber.TAG_030.getTagDescreption(), valorFechaTag30));
                items.add(new Items(TagNumber.TAG_035.getTagDescreption(),
                                    datosDocumento.getFechaPresentacion() != null ?
                                    datosDocumento.getFechaPresentacion() : " "));
                items.add(new Items(TagNumber.TAG_046.getTagDescreption(),
                                    datosDocumento.getFechaSolventacion() != null ?
                                    datosDocumento.getFechaSolventacion() : " "));
                items.add(new Items(TagNumber.TAG_076.getTagDescreption(),
                                    datosDocumento.getImporteSolicitado() != null ?
                                    datosDocumento.getImporteSolicitado() : " "));
                items.add(new Items(TagNumber.TAG_118.getTagDescreption(),
                                    datosDocumento.getPeriodoOrigen() != null ?
                                    datosDocumento.getPeriodoOrigen() : " "));
                items.add(new Items(TagNumber.TAG_291.getTagDescreption(),
                                    datosDocumento.getFechaNotificacion2() != null ?
                                    datosDocumento.getFechaNotificacion2() : " "));

                items.add(new Items(TagNumber.TAG_088.getTagDescreption(),
                                    datosDocumento.getLeyenda() != null ? datosDocumento.getLeyenda().toString() :
                                    " "));
                items.add(new Items(TagNumber.TAG_400.getTagDescreption(),
                                    datosDocumento.getDomicilio() != null ? datosDocumento.getDomicilio().toString() :
                                    " "));

                break;


            case LABEL_SIXTY_EIGHT:
            case LABEL_SIXTY_NINE:
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getCalleNum() != null ? datosDocumento.getCalleNum() : " "));
                if (!datosDocumento.getColonia().equals("")) {
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(), datosDocumento.getColonia()));
                }
                if (!datosDocumento.getEntreCalles().equals(" ")) {
                    items.add(new Items(TagNumber.TAG_017.getTagDescreption(), datosDocumento.getEntreCalles()));
                }
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getCp() != null ? datosDocumento.getCp() : " "));
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getMunicipio() != null ? datosDocumento.getMunicipio() :
                                    " "));
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getEntidad() != null ? datosDocumento.getEntidad() : " "));
                items.add(new Items(TagNumber.TAG_035.getTagDescreption(),
                                    datosDocumento.getFechaPresentacion() != null ?
                                    datosDocumento.getFechaPresentacion() : " "));
                items.add(new Items(TagNumber.TAG_076.getTagDescreption(),
                                    datosDocumento.getImporteSolicitado() != null ?
                                    datosDocumento.getImporteSolicitado() : " "));

                items.add(new Items(TagNumber.TAG_088.getTagDescreption(),
                                    datosDocumento.getLeyenda() != null ? datosDocumento.getLeyenda().toString() :
                                    " "));


                break;
                /**
            case LABEL_SEVENTY_FIVE:
                items.add(new Items(TagNumber.TAG_018.getTagDescreption(),
                                    dtoTemplateNumber.getEjercicioOrigen() != null ?
                                    dtoTemplateNumber.getEjercicioOrigen() : " "));
                items.add(new Items(TagNumber.TAG_118.getTagDescreption(),
                                    dtoTemplateNumber.getPeriodoOrigen() != null ?
                                    dtoTemplateNumber.getPeriodoOrigen() : " "));


                break;

 */
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

                items.add(new Items(TagNumber.TAG_010.getTagDescreption(),
                                    datosDocumento.getConcepto() != null ? datosDocumento.getConcepto() : " "));
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getCalleNum() != null ? datosDocumento.getCalleNum() : " "));
                if (!datosDocumento.getColonia().equals("")) {
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(), datosDocumento.getColonia()));
                }
                if (!datosDocumento.getEntreCalles().equals(" ")) {
                    items.add(new Items(TagNumber.TAG_017.getTagDescreption(), datosDocumento.getEntreCalles()));
                }
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getCp() != null ? datosDocumento.getCp() : " "));
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getMunicipio() != null ? datosDocumento.getMunicipio() :
                                    " "));
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getEntidad() != null ? datosDocumento.getEntidad() : " "));
                items.add(new Items(TagNumber.TAG_035.getTagDescreption(),
                                    datosDocumento.getFechaPresentacion() != null ?
                                    datosDocumento.getFechaPresentacion() : " "));
                items.add(new Items(TagNumber.TAG_076.getTagDescreption(),
                                    datosDocumento.getImporteSolicitado() != null ?
                                    datosDocumento.getImporteSolicitado() : " "));


                items.add(new Items(TagNumber.TAG_088.getTagDescreption(),
                                    datosDocumento.getLeyenda() != null ? datosDocumento.getLeyenda().toString() :
                                    " "));
                items.add(new Items(TagNumber.TAG_200.getTagDescreption(),
                                    datosDocumento.getDomicilioAlaf() != null ?
                                    actualizaNombreDF(datosDocumento.getDomicilioAlaf().toString()) : " "));

                if (Integer.valueOf(idTemplate) == LABEL_TWENTY_FOUR ||
                    Integer.valueOf(idTemplate) == LABEL_TWENTY_FIVE) {
                    items.add(new Items(TagNumber.TAG_048.getTagDescreption(),
                                        datosDocumento.getFraccion() != null ?
                                        datosDocumento.getFraccion().toString() : " "));
                    items.add(new Items(TagNumber.TAG_134.getTagDescreption(),
                                        datosDocumento.getCiudadAdmLocal() != null ?
                                        actualizaNombreDF(datosDocumento.getCiudadAdmLocal().toString()) : " "));

                    items.add(new Items(TagNumber.TAG_270.getTagDescreption(),
                                        datosDocumento.getSerieDocumental() != null ?
                                        datosDocumento.getSerieDocumental().toString() : " "));
                }


                break;
                /**
            case LABEL_EIGHTY_THREE:
                Calendar currentDate = Calendar.getInstance();


                items.add(new Items(TagNumber.TAG_010.getTagDescreption(),
                                    dtoTemplateNumber.getConcepto() != null ? dtoTemplateNumber.getConcepto() : " "));
                items.add(new Items(TagNumber.TAG_011.getTagDescreption(),
                                    dtoTemplateNumber.getImpuestoOrigen() != null ?
                                    dtoTemplateNumber.getImpuestoOrigen() : " "));
                items.add(new Items(TagNumber.TAG_015.getTagDescreption(),
                                    dtoTemplateNumber.getConceptoDestino() != null ?
                                    dtoTemplateNumber.getConceptoDestino() : " "));
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    dtoTemplateNumber.getCalleNum() != null ? dtoTemplateNumber.getCalleNum() : " "));
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    dtoTemplateNumber.getColonia() != null ? dtoTemplateNumber.getColonia() : " "));
                if (!dtoTemplateNumber.getEntreCalles().equals(" ")) {
                    items.add(new Items(TagNumber.TAG_017.getTagDescreption(), dtoTemplateNumber.getEntreCalles()));
                }
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    dtoTemplateNumber.getCp() != null ? dtoTemplateNumber.getCp() : " "));
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    dtoTemplateNumber.getMunicipio() != null ? dtoTemplateNumber.getMunicipio() :
                                    " "));
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    dtoTemplateNumber.getEntidad() != null ? dtoTemplateNumber.getEntidad() : " "));
                items.add(new Items(TagNumber.TAG_018.getTagDescreption(),
                                    dtoTemplateNumber.getEjercicioOrigen() != null ?
                                    dtoTemplateNumber.getEjercicioOrigen() : " "));
                items.add(new Items(TagNumber.TAG_035.getTagDescreption(),
                                    dtoTemplateNumber.getFechaPresentacion() != null ?
                                    dtoTemplateNumber.getFechaPresentacion() : " "));
                items.add(new Items(TagNumber.TAG_036.getTagDescreption(),
                                    dtoTemplateNumber.getFechaComp() != null ? dtoTemplateNumber.getFechaComp() :
                                    " "));

                items.add(new Items(TagNumber.TAG_062.getTagDescreption(),
                                    dtoTemplateNumber.getImporteTotalComp() != null ?
                                    dtoTemplateNumber.getImporteTotalComp() : " "));
                items.add(new Items(TagNumber.TAG_118.getTagDescreption(),
                                    dtoTemplateNumber.getPeriodoOrigen() != null ?
                                    dtoTemplateNumber.getPeriodoOrigen() : " "));
                items.add(new Items(TagNumber.TAG_134.getTagDescreption(),
                                    dtoTemplateNumber.getCiudadAdmLocal() != null ?
                                    dtoTemplateNumber.getCiudadAdmLocal() : " "));
                items.add(new Items(TagNumber.TAG_209.getTagDescreption(),
                                    dtoTemplateNumber.getNumOperacion() != null ? dtoTemplateNumber.getNumOperacion() :
                                    " "));

                items.add(new Items(TagNumber.TAG_278.getTagDescreption(),
                                    String.valueOf(currentDate.get(Calendar.MONTH))));

                items.add(new Items(TagNumber.TAG_279.getTagDescreption(),
                                    String.valueOf(currentDate.get(Calendar.YEAR))));

                break;
*/
            case LABEL_NINETY_FIVE:


                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getCalleNum() != null ? datosDocumento.getCalleNum() : " "));
                if (!datosDocumento.getColonia().equals("")) {
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(), datosDocumento.getColonia()));
                }
                if (!datosDocumento.getEntreCalles().equals(" ")) {
                    items.add(new Items(TagNumber.TAG_017.getTagDescreption(), datosDocumento.getEntreCalles()));
                }
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getCp() != null ? datosDocumento.getCp() : " "));
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getMunicipio() != null ? datosDocumento.getMunicipio() :
                                    " "));
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getEntidad() != null ? datosDocumento.getEntidad() : " "));
                items.add(new Items(TagNumber.TAG_035.getTagDescreption(),
                                    datosDocumento.getFechaPresentacion() != null ?
                                    datosDocumento.getFechaPresentacion() : " "));
                items.add(new Items(TagNumber.TAG_076.getTagDescreption(),
                                    datosDocumento.getImporteSolicitado() != null ?
                                    datosDocumento.getImporteSolicitado() : " "));


                items.add(new Items(TagNumber.TAG_088.getTagDescreption(),
                                    datosDocumento.getLeyenda() != null ? datosDocumento.getLeyenda().toString() :
                                    " "));

                break;

            case LABEL_TWENTY_TWO:
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getCalleNum() != null ? datosDocumento.getCalleNum() : " "));
                if (!datosDocumento.getColonia().equals("")) {
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(), datosDocumento.getColonia()));
                }
                if (!datosDocumento.getEntreCalles().equals(" ")) {
                    items.add(new Items(TagNumber.TAG_017.getTagDescreption(), datosDocumento.getEntreCalles()));
                }
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getCp() != null ? datosDocumento.getCp() : " "));
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getMunicipio() != null ? datosDocumento.getMunicipio() :
                                    " "));
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getEntidad() != null ? datosDocumento.getEntidad() : " "));
                items.add(new Items(TagNumber.TAG_022.getTagDescreption(),
                                    datosDocumento.getMotivoSinDeposito() != null ?
                                    datosDocumento.getMotivoSinDeposito() : " "));
                items.add(new Items(TagNumber.TAG_035.getTagDescreption(),
                                    datosDocumento.getFechaPresentacion() != null ?
                                    datosDocumento.getFechaPresentacion() : " "));

                items.add(new Items(TagNumber.TAG_076.getTagDescreption(),
                                    datosDocumento.getImporteSolicitado() != null ?
                                    datosDocumento.getImporteSolicitado() : " "));

                items.add(new Items(TagNumber.TAG_115.getTagDescreption(),
                                    datosDocumento.getOrigen() != null ? datosDocumento.getOrigen() : " "));
                
                items.add(new Items(TagNumber.TAG_010.getTagDescreption(),
                                    datosDocumento.getConcepto() != null ? datosDocumento.getConcepto() : " "));
                
                items.add(new Items(TagNumber.TAG_103.getTagDescreption(),
                                    datosDocumento.getNumControl() != null ? datosDocumento.getNumControl().toString() :
                                    " "));

                items.add(new Items(TagNumber.TAG_200.getTagDescreption(),
                                    datosDocumento.getDomicilioAlaf() != null ?
                                    actualizaNombreDF(datosDocumento.getDomicilioAlaf().toString()) : " "));
                items.add(new Items(TagNumber.TAG_327.getTagDescreption(),
                                    datosDocumento.getCurp() != null ? datosDocumento.getCurp() : " "));


                items.add(new Items(TagNumber.TAG_048.getTagDescreption(),
                                    datosDocumento.getFraccion() != null ? datosDocumento.getFraccion() : " "));
                items.add(new Items(TagNumber.TAG_101.getTagDescreption(),
                                    datosDocumento.getNomFunAutorizado() != null ?
                                    datosDocumento.getNomFunAutorizado() : " "));

                break;
            case LABEL_SIXTY_SIX:
            case LABEL_ONE_HUNDRED_AND_FOURTEEN:
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getCalleNum() != null ? datosDocumento.getCalleNum() : " "));
                if (!datosDocumento.getColonia().equals("")) {
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(), datosDocumento.getColonia()));
                }
                if (!datosDocumento.getEntreCalles().equals(" ")) {
                    items.add(new Items(TagNumber.TAG_017.getTagDescreption(), datosDocumento.getEntreCalles()));
                }
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getCp() != null ? datosDocumento.getCp() : " "));
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getMunicipio() != null ? datosDocumento.getMunicipio() :
                                    " "));
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getEntidad() != null ? datosDocumento.getEntidad() : " "));
                items.add(new Items(TagNumber.TAG_022.getTagDescreption(),
                                    datosDocumento.getMotivoSinDeposito() != null ?
                                    datosDocumento.getMotivoSinDeposito() : " "));
                items.add(new Items(TagNumber.TAG_035.getTagDescreption(),
                                    datosDocumento.getFechaPresentacion() != null ?
                                    datosDocumento.getFechaPresentacion() : " "));

                items.add(new Items(TagNumber.TAG_103.getTagDescreption(),
                                    datosDocumento.getNumControl() != null ? datosDocumento.getNumControl().toString() :
                                    " "));


                items.add(new Items(TagNumber.TAG_048.getTagDescreption(),
                                    datosDocumento.getFraccion() != null ? datosDocumento.getFraccion() : " "));
                items.add(new Items(TagNumber.TAG_101.getTagDescreption(),
                                    datosDocumento.getNomFunAutorizado() != null ?
                                    datosDocumento.getNomFunAutorizado() : " "));
                items.add(new Items(TagNumber.TAG_200.getTagDescreption(),
                                    datosDocumento.getDomicilioAlaf() != null ?
                                    actualizaNombreDF(datosDocumento.getDomicilioAlaf().toString()) : " "));
                break;

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
                items.add(new Items(TagNumber.TAG_010.getTagDescreption(),
                                    datosDocumento.getConcepto() != null ? datosDocumento.getConcepto() : " "));

                items.add(new Items(TagNumber.TAG_088.getTagDescreption(),
                                    datosDocumento.getLeyenda() != null ? datosDocumento.getLeyenda() : " "));

                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getCalleNum() != null ? datosDocumento.getCalleNum() : " "));
                if (!datosDocumento.getColonia().equals("")) {
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(), datosDocumento.getColonia()));
                }
                if (!datosDocumento.getEntreCalles().equals(" ")) {
                    items.add(new Items(TagNumber.TAG_017.getTagDescreption(), datosDocumento.getEntreCalles()));
                }
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getCp() != null ? datosDocumento.getCp() : " "));
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getMunicipio() != null ? datosDocumento.getMunicipio() :
                                    " "));
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getEntidad() != null ? datosDocumento.getEntidad() : " "));
                
                items.add(new Items(TagNumber.TAG_035.getTagDescreption(),
                                    datosDocumento.getFechaPresentacion() != null ?
                                    datosDocumento.getFechaPresentacion() : " "));
                items.add(new Items(TagNumber.TAG_209.getTagDescreption(),
                                    datosDocumento.getNumOperacion() != null ? datosDocumento.getNumOperacion() :
                                    " "));
                items.add(new Items(TagNumber.TAG_115.getTagDescreption(),
                                    datosDocumento.getOrigen() != null ? datosDocumento.getOrigen() : " "));
                items.add(new Items(TagNumber.TAG_011.getTagDescreption(),
                                    datosDocumento.getImpuestoOrigen() != null ?
                                    datosDocumento.getImpuestoOrigen() : " "));
                items.add(new Items(TagNumber.TAG_012.getTagDescreption(),
                                    datosDocumento.getImpuestoDestino() != null ?
                                    datosDocumento.getImpuestoDestino() : " "));
                items.add(new Items(TagNumber.TAG_118.getTagDescreption(),
                                    datosDocumento.getPeriodoOrigen() != null ?
                                    datosDocumento.getPeriodoOrigen() : " "));
                items.add(new Items(TagNumber.TAG_018.getTagDescreption(),
                                    datosDocumento.getEjercicioOrigen() != null ?
                                    datosDocumento.getEjercicioOrigen() : " "));
                items.add(new Items(TagNumber.TAG_062.getTagDescreption(),
                                    datosDocumento.getImporteTotalComp() != null ?
                                    datosDocumento.getImporteTotalComp() : " "));
                items.add(new Items(TagNumber.TAG_036.getTagDescreption(),
                                    datosDocumento.getFechaComp() != null ? datosDocumento.getFechaComp() :
                                    " "));
                items.add(new Items(TagNumber.TAG_076.getTagDescreption(),
                                    datosDocumento.getImporteSolicitado() != null ?
                                    datosDocumento.getImporteSolicitado() : " "));
                items.add(new Items(TagNumber.TAG_015.getTagDescreption(),
                                    datosDocumento.getConceptoDestino() != null ?
                                    datosDocumento.getConceptoDestino() : " "));


                if (Integer.valueOf(idTemplate) == LABEL_SEVENTY_EIGHT ||
                    Integer.valueOf(idTemplate) == LABEL_EIGHTY_THREE ||
                    Integer.valueOf(idTemplate) == LABEL_ONE_HUNDRED_AND_THIRTY_SEVEN) {
                    items.add(new Items(TagNumber.TAG_064.getTagDescreption(),
                                        datosDocumento.getImporteActualizado() != null ?
                                        datosDocumento.getImporteActualizado() : " "));
                    items.add(new Items(TagNumber.TAG_070.getTagDescreption(),
                                        datosDocumento.getImporteRecargo() != null ?
                                        datosDocumento.getImporteRecargo() : " "));
                    items.add(new Items(TagNumber.TAG_236.getTagDescreption(),
                                        datosDocumento.getFundamentacion() != null ?
                                        datosDocumento.getFundamentacion() : " "));
                    items.add(new Items(TagNumber.TAG_278.getTagDescreption(),
                                        datosDocumento.getMesCompensa() != null ?
                                        datosDocumento.getMesCompensa() : " "));
                    items.add(new Items(TagNumber.TAG_279.getTagDescreption(),
                                        datosDocumento.getAnioCompensa() != null ?
                                        datosDocumento.getAnioCompensa() : " "));
                }


                break;
            case LABEL_ONE_HUNDRED_AND_THIRTY_ONE:
                items.add(new Items(TagNumber.TAG_200.getTagDescreption(),
                                    datosDocumento.getDomicilioAlaf() != null ?
                                    actualizaNombreDF(datosDocumento.getDomicilioAlaf().toString()) : " "));
                items.add(new Items(TagNumber.TAG_327.getTagDescreption(),
                                    datosDocumento.getCurp() != null ? datosDocumento.getCurp() : " "));
                items.add(new Items(TagNumber.TAG_035.getTagDescreption(),
                                    datosDocumento.getFechaPresentacion() != null ?
                                    datosDocumento.getFechaPresentacion() : " "));
                items.add(new Items(TagNumber.TAG_022.getTagDescreption(),
                                    datosDocumento.getMotivoSinDeposito() != null ?
                                    datosDocumento.getMotivoSinDeposito() : " "));
                items.add(new Items(TagNumber.TAG_103.getTagDescreption(),
                                    datosDocumento.getNumControl() != null ? datosDocumento.getNumControl().toString() :
                                    " "));
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getCalleNum() != null ? datosDocumento.getCalleNum() : " "));
                if (!datosDocumento.getColonia().equals("")) {
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(), datosDocumento.getColonia()));
                }
                if (!datosDocumento.getEntreCalles().equals(" ")) {
                    items.add(new Items(TagNumber.TAG_017.getTagDescreption(), datosDocumento.getEntreCalles()));
                }
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getCp() != null ? datosDocumento.getCp() : " "));
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getMunicipio() != null ? datosDocumento.getMunicipio() :
                                    " "));
                items.add(new Items(TagNumber.TAG_017.getTagDescreption(),
                                    datosDocumento.getEntidad() != null ? datosDocumento.getEntidad() : " "));
                items.add(new Items(TagNumber.TAG_088.getTagDescreption(),
                                    datosDocumento.getLeyenda() != null ? datosDocumento.getLeyenda() : " "));
                items.add(new Items(TagNumber.TAG_055.getTagDescreption(),
                                    datosDocumento.getImporteNeto() != null ? datosDocumento.getImporteNeto() : " "));
                break;
            case LABEL_ONE_HUNDRED_AND_THIRTY_EIGHT:
                String tag010Descripcion = TagNumber.TAG_010.getTagDescreption();
                String tag017Descripcion = TagNumber.TAG_017.getTagDescreption();
                String tag022Descripcion = TagNumber.TAG_022.getTagDescreption();
                String tag035Descripcion = TagNumber.TAG_035.getTagDescreption();
                String tag076Descripcion = TagNumber.TAG_076.getTagDescreption();
                String tag101Descripcion = TagNumber.TAG_101.getTagDescreption();
                String tag103Descripcion = TagNumber.TAG_103.getTagDescreption();
                String tag115Descripcion = TagNumber.TAG_115.getTagDescreption();
                String tag200Descripcion = TagNumber.TAG_200.getTagDescreption();
                String tag327Descripcion = TagNumber.TAG_327.getTagDescreption();
                String tagDoEDescripcion = TagNumber.TAG_D_O_E.getTagDescreption();
                String tagFDescripcion   = TagNumber.TAG_F.getTagDescreption();

                String valorCalleNum          = datosDocumento.getCalleNum();
                String valorColonia           = datosDocumento.getColonia();
                String valorEntreCalles       = datosDocumento.getEntreCalles();
                String valorCp                = datosDocumento.getCp();
                String valorMunicipio         = datosDocumento.getMunicipio();
                String valorEntidad           = datosDocumento.getEntidad();
                String valorMotivoSinDeposito = datosDocumento.getMotivoSinDeposito();
                String valorFechaPresentacion = datosDocumento.getFechaPresentacion();
                String valorImporteSolicitado = datosDocumento.getImporteSolicitado();
                String valorOrigen            = datosDocumento.getOrigen();
                String valorConcepto          = datosDocumento.getConcepto();
                String valorNumControl        = datosDocumento.getNumControl();
                String valorDomicilioAlaf     = datosDocumento.getDomicilioAlaf();
                String valorCurp              = datosDocumento.getCurp();
                String valorNomFunAutorizado  = datosDocumento.getNomFunAutorizado();
                String valorMontoAplicar      = datosDocumento.getMontoAplicar();
                String valorTipoResolucion    = datosDocumento.getTipoResolucion();

                items.add( new Items( tag017Descripcion, valorCalleNum != null ? valorCalleNum : " "));
                if ( !valorColonia.equals("") ) {
                    items.add( new Items( tag017Descripcion, valorColonia ) );
                }

                if ( !valorEntreCalles.equals(" ") ) {
                    items.add( new Items( tag017Descripcion, valorEntreCalles ) );
                }

                items.add( new Items( tag017Descripcion, valorCp                 != null ? valorCp : " ") );
                items.add( new Items( tag017Descripcion, valorMunicipio          != null ? valorMunicipio : " ") );
                items.add( new Items( tag017Descripcion, valorEntidad            != null ? valorEntidad : " ") );
                items.add( new Items( tag022Descripcion, valorMotivoSinDeposito  != null ? valorMotivoSinDeposito : " ") );
                items.add( new Items( tag035Descripcion, valorFechaPresentacion  != null ? valorFechaPresentacion : " ") );
                items.add( new Items( tag076Descripcion, valorImporteSolicitado  != null ? valorImporteSolicitado : " ") );
                items.add( new Items( tag115Descripcion, valorOrigen             != null ? valorOrigen : " ") );
                items.add( new Items( tag010Descripcion, valorConcepto           != null ? valorConcepto : " ") );
                items.add( new Items( tag103Descripcion, valorNumControl         != null ? valorNumControl : " ") );
                items.add( new Items( tag200Descripcion, valorDomicilioAlaf      != null ? actualizaNombreDF( valorDomicilioAlaf ) : " ") );
                items.add( new Items( tag327Descripcion, valorCurp               != null ? valorCurp : " ") );
                items.add( new Items( tag101Descripcion, valorNomFunAutorizado   != null ? valorNomFunAutorizado : " ") );
                items.add( new Items( tagFDescripcion,   valorMontoAplicar       != null ? valorMontoAplicar : " ") );
                items.add( new Items( tagDoEDescripcion, valorTipoResolucion     != null ? valorTipoResolucion : " ") );
                break;
            default:
                break;
            }
        }


        return items;


    }

    public List<Items> loadItemsFromMapAnyWhere(Map<String, Object> in) throws SIATException {

        List<Items> itemLoadOfHash = new ArrayList<Items>();


        try {
            Iterator iterator = in.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry entryHash = (Map.Entry)iterator.next();
                String keyEntryHashMap = entryHash.getKey() != null ? entryHash.getKey().toString() : null;

                if (keyEntryHashMap != null && in.get(keyEntryHashMap) != null) {
                    List<String> valueOfItemsHash = new ArrayList<String>();
                    if (isArray(in.get(keyEntryHashMap).getClass().getName())) {
                        valueOfItemsHash =
                                in.get(keyEntryHashMap) != null ? (List<String>)in.get(keyEntryHashMap) : valueOfItemsHash;

                    }

                    else {
                        valueOfItemsHash.add(in.get(keyEntryHashMap).toString());
                    }

                    for (String value : valueOfItemsHash) {
                        for (TagNumber tag : TagNumber.values()) {
                            if (keyEntryHashMap.equals(tag.getField())) {
                                itemLoadOfHash.add(new Items(tag.getTagDescreption(), value));
                                break;
                            }
                        }

                    }
                }
            }


        } catch (Exception ex) {

            LOG.error(ex);
            throw new SIATException(ex);

        }

        return itemLoadOfHash;
    }

    public Boolean isArray(String nameOfClass) {
        Boolean array = false;

        if (nameOfClass.equals(ARRAYLIST) || nameOfClass.equals(LIST)) {
            array = Boolean.TRUE;
        }

        return array;

    }

    public List<Items> completeTemplate(String selloDigital, String cadenaOriginal, String firmaDigital, String employeeName) {
            List<Items> endOfTemplate = new ArrayList<Items>();
            
            if (firmaDigital != null) {
                endOfTemplate.add(new Items(TagNumber.TAG_232.getTagDescreption(), firmaDigital));
            }
            
            if (cadenaOriginal != null) {
                endOfTemplate.add(new Items(TagNumber.TAG_233.getTagDescreption(), cadenaOriginal));
            }

            if (selloDigital != null) {
                endOfTemplate.add(new Items(TagNumber.TAG_285.getTagDescreption(), selloDigital));
            }


            if (employeeName != null) {
                endOfTemplate.add(new Items(TagNumber.TAG_101.getTagDescreption(), employeeName));
            }
            
            endOfTemplate.add(new Items(TagNumber.TAG_047.getTagDescreption(), FechaUtil.formatearFechaCompleta(new Date())));

            return endOfTemplate;

        }
}
