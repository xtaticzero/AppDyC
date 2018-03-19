    package mx.gob.sat.siat.dyc.util.common;

import org.apache.log4j.Logger;

public class ManejadorLogException extends Exception{
    
    
    @SuppressWarnings("compatibility:-5375504477455630361")
    private static final long serialVersionUID = -1465057316461259979L;
                    
    private static final Logger LOGGER = Logger.getLogger(ManejadorLogException.class);
            
    public ManejadorLogException() {
        super();
    }
                    
                    
    public static void getTraceError(Exception ex) {
            
            StringBuilder sb = new StringBuilder();
            sb.append(EnumManejadorEx.INICIO.getId()).append(EnumManejadorEx.SEPARADOR.getId());  
            LOGGER.error(EnumManejadorEx.TIPO.getId()+ex.getClass().getName());
            LOGGER.error(EnumManejadorEx.CAUSA.getId()+ex.getCause());
            LOGGER.error(EnumManejadorEx.CLASE.getId()+ex.getLocalizedMessage());
                for (StackTraceElement element : ex.getStackTrace()) {
                    if(element.toString().contains(EnumManejadorEx.PAQUETE.getId())){
                    sb.append(element.toString());
                    sb.append(EnumManejadorEx.SEPARADOR.getId());
                    }
                }
                sb.append(EnumManejadorEx.FIN.getId());
                LOGGER.error(sb.toString());
        }
        

        public enum EnumManejadorEx {
             INICIO("****BEGIN EXCEPTION****")
            ,FIN("****END EXCEPTION****")
            ,TIPO("TYPE EXCEPTION-->")
            ,CAUSA("**CAUSA**")
            ,CLASE("**CLASE**")
            ,SEPARADOR("\n")
            ,PAQUETE("mx.");
            

            private String id; 
            
            private EnumManejadorEx(String id){
                this.id = id;
            }
            
            public String getId(){
               return id;
            } 
        }
            
        
        
        }