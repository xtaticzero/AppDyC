package mx.gob.sat.siat.dyc.adminprocesos.web.controller.bean.bussiness;

import mx.gob.sat.siat.dyc.adminprocesos.dto.Cron;
import mx.gob.sat.siat.dyc.adminprocesos.util.constante.Constantes;

public class CronMaker {
    public CronMaker() {
        super();
    }
        
    /**
     *
     * @param idBoton id del boton seleccionado
     * @return Retorna la expresion cron que define a que hora y con que frecuencia se debe de ejecutar una tarea en 
     * base de datos.
     */
    public String generarCron(String idBoton, Cron cron) {
        return seleccionarMetodoCron(idBoton, cron);
    }
    
    /**
     * Dependiendo de que boton fue seleccionado, se identifica que metodo se va a utilizar para generar la expresion
     * cron.
     *
     * @param idBoton id del boton seleccionado
     * @return Retorna la expresion cron que define a que hora y con que frecuencia se debe de ejecutar una tarea en 
     * base de datos.
     * @throws Exception
     */
    private String seleccionarMetodoCron(String idBoton, Cron cron) {
        String cadenaCron = "";
        if(idBoton.equals(Constantes.MINUTO)) {
            cadenaCron = generarXMinuto(cron);
            
        } else if (idBoton.equals(Constantes.HORA)) {
            cadenaCron = generarXHora(cron);
                                          
        } else if (idBoton.equals(Constantes.DIA)) {
            cadenaCron = generarXDia(cron);
                                          
        } else if (idBoton.equals(Constantes.SEMANA)) {
            cadenaCron = generarXSemana(cron);
                                          
        } else if (idBoton.equals(Constantes.MES)) {
            cadenaCron = generarXMes(cron);
                                          
        } else if (idBoton.equals(Constantes.ANIO_SIN_TILDE)) {
            cadenaCron = generarXAnio(cron);
        }
        return cadenaCron;
    }
    
    /**
     * Genera una expresion Cron para ejecutar una tarea por minuto.
     *
     * @param cron
     * @return
     * @throws Exception
     */
    private String generarXMinuto(Cron cron) {
        StringBuilder cadena = new StringBuilder();
        return cadena.append( "0 0/").append(cron.getMinuto()).append( " * 1/1 * ? * ").toString();
    }
    
    /**
     * Genera una expresion Cron para ejecutar una tarea por hora.
     *
     * @param cron
     * @return
     * @throws Exception
     */
    private String generarXHora(Cron cron) {
        String cadenaCron = "";
        StringBuilder cadena = new StringBuilder();
        
        if(cron.getBanderaHora().equals(Constantes.OPCION1)) {
            cadenaCron = cadena.append( "0 0 0/").append(cron.getHora()).append(Constantes.ESPACIO).append( "1/1 * ? * ").toString();    
        } else {
            cadenaCron = cadena.append(Constantes.CERO).append(Constantes.ESPACIO).append(cron.getMinuto()).append(Constantes.ESPACIO).append(cron.getHora()).append(Constantes.ESPACIO).append( "1/1 * ? *").toString();
        }
        return cadenaCron;
    }
    
    /**
     * <pre>
     * Genera una expresion Cron para ejecutar una tarea por dia; aqui hay dos variantes, puede ejcutarse: 
     *  - Cada cierto numero de dias: cada 2 dias, cada 3 dias,  cada 5 dias, etc.
     *  - Diario: Implica todos los dias de la semana de lunes a domingo.
     * </pre>
     *
     * @param cron
     * @return
     * @throws Exception
     */
    private String generarXDia(Cron cron) {
        String cadenaCron = "";
        StringBuilder cadena = new StringBuilder();
        
        if(cron.getBanderaDia().equals(Constantes.OPCION1)) {
            cadenaCron = cadena.append(Constantes.CERO).append(Constantes.ESPACIO).append(cron.getMinuto()).append(Constantes.ESPACIO).append(cron.getHora()).append(Constantes.ESPACIO).append(Constantes.UNODIAGONAL).append(cron.getNumeroDia()).append(Constantes.ESPACIO).append( "* ? *").toString();
            
        } else {
            cadenaCron = cadena.append(Constantes.CERO).append(Constantes.ESPACIO).append(cron.getMinuto()).append(Constantes.ESPACIO).append(cron.getHora()).append(Constantes.ESPACIO).append("? * MON,TUE,WED,THU,FRI,SAT,SUN *").toString();
        }
        return cadenaCron;
    }
    
    /**
     * Genera una expresion Cron la cual se ejecutara semanalmente, en la cual se eligen los dias de la semana que se 
     * ejecutara la tarea.
     *
     * @param cron
     * @return
     * @throws Exception
     */
    private String generarXSemana(Cron cron) {
        String cadenaCron = "";
        StringBuilder cadena = new StringBuilder();
        cadenaCron = cadena.append(Constantes.CERO).append(Constantes.ESPACIO).append(cron.getMinuto()).append(Constantes.ESPACIO). append(cron.getHora()). append(Constantes.ESPACIO). append("? * "). append(cron.getDia()). append(" *"). toString();
        return cadenaCron;
    }
    
    /**
     * <pre>
     * Genera una expresion Cron la cual se ejcuta mensualmente, esta se puede programar de dos maneras: 
     *  - Escogiendo el numero de dia del calendario y cada cuantos meses se va a ejecutar. Ejemplos: el dia 5 de cada mes, el dia 10 de cada 3 meses.
     *  - Escogiendo el dia de la semana y cada cuantos meses. Ejemplo: El primer lunes de cada mes, el segundo miercoles de cada 2 meses.
     * </pre>
     *
     * @param cron
     * @return
     * @throws Exception
     */
    private String generarXMes(Cron cron) {
        String cadenaCron = "";
        StringBuilder cadena = new StringBuilder();
        if(cron.getBanderaMes().equals(Constantes.OPCION1)) {
            cadenaCron = cadena.append(Constantes.CERO).append(Constantes.ESPACIO).append(cron.getMinuto()).append(Constantes.ESPACIO).append(cron.getHora()).append(Constantes.ESPACIO).append(cron.getNumeroDia()).append(Constantes.ESPACIO).append(Constantes.UNODIAGONAL).append(cron.getNumeroMes()).append(Constantes.ESPACIO).append( "? *").toString();
            
        } else {
            cadenaCron = cadena.append(Constantes.CERO).append(Constantes.ESPACIO).append(cron.getMinuto()).append(Constantes.ESPACIO).append(cron.getHora()).append(Constantes.ESPACIO).append("? 1/").append(cron.getNumeroMes()).append(Constantes.ESPACIO).append(cron.getDia()).append("#").append(cron.getPosicion()).append(" *").toString();
        }
        return cadenaCron;
    }
    
    /**
     * <pre>
     * Genera una expresion Cron la cual se ejecuta anualmente, esta se puede programar de dos maneras:
     *  - Escogiendo un dia de cada mes.
     *  - Escogiendo el primer, segundo, tercer o cuarto dia de la semana de cada mes.
     * </pre>
     *
     * @param cron
     * @return
     * @throws Exception
     */
    private String generarXAnio(Cron cron) {
        String cadenaCron = "";
        StringBuilder cadena = new StringBuilder();
        if(cron.getBanderaAnual().equals(Constantes.OPCION1)) {
            cadenaCron = cadena.append(Constantes.CERO).append(Constantes.ESPACIO).append(cron.getMinuto()).append(Constantes.ESPACIO).append(cron.getHora()).append(Constantes.ESPACIO).append(cron.getNumeroDia()).append(Constantes.ESPACIO).append(cron.getNumeroMes()).append(" ? *").toString();
            
        } else {
            cadenaCron = cadena.append(Constantes.CERO).append(Constantes.ESPACIO).append(cron.getMinuto()).append(Constantes.ESPACIO).append(cron.getHora()).append(Constantes.ESPACIO).append("? ").append(cron.getNumeroMes()).append(Constantes.ESPACIO).append(cron.getDia()).append("#").append(cron.getPosicion()).append(" *").toString();
        }
        return cadenaCron;
    }
}
