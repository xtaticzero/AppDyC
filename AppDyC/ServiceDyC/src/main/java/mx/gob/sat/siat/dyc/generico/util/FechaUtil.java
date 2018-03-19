package mx.gob.sat.siat.dyc.generico.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;


/**
 * Utilerias para las fechas
 * @author Mario Bastida
 * @date Abril, 2014
 *
 * */
public final class FechaUtil {
    
    private FechaUtil(){
        
    }
    
    /**
     * Metodo para elimianar la hora y milisegundos
     * @param date
     * @since 23-Ene-2014
     * @return fecha modificada
     */
    public static Date  eliminarHoraMilisegundos(final Date date){
        Calendar calendar = GregorianCalendar.getInstance();

        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0 );
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }
    
    public static int comparar(final Date date1, final Date date2){
        Date newDate1 =  FechaUtil.eliminarHoraMilisegundos (date1);
        Date newDate2 =  FechaUtil.eliminarHoraMilisegundos (date2);
        
        return newDate1.compareTo(newDate2);
    }   
    
    public static Date lowDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 0);
        return calendar.getTime();
    }
    
    public static Date upDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, ConstantesDyCNumerico.VALOR_59);
        calendar.set(Calendar.HOUR, ConstantesDyCNumerico.VALOR_23);
        return calendar.getTime();
    }

    /**
     * <html> <body> Formatea la fecha al siguiente formato 'NUMERO DE DIA' de
     * 'NOMBRE DE MES' de 'AÑO' </body> </html>
     *
     * @param fecha
     * @return
     */
    public static String formatearFechaCompleta(Date fecha) {
        if (fecha == null) {
            return "";
        } else {
            Locale localeMexico = new Locale("es", "MX");
            SimpleDateFormat formatoSAT = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", localeMexico);
            return formatoSAT.format(fecha);
        }
    }

    public static String formatearFechaTag30(String valorFechaTag30) {
        if (valorFechaTag30 == null) {
            return " ";
        }
        try {
            java.text.DateFormat datef = new SimpleDateFormat("dd/MM/yy");
            Date fechaTag30 = datef.parse(valorFechaTag30);
            return FechaUtil.formatearFechaCompleta(fechaTag30);
        } catch (ParseException e) {
            return valorFechaTag30;
        } catch (Exception e) {
            return " ";
        }
    }

    /**
     * Da formato a una fecha especificada
     *
     * @param fecha Fecha a la cual dar formato.
     * @param formatoFecha Formato de fecha
     * @return Fecha con formato.
     */
    public static String darFormatoFecha(Date fecha, String formatoFecha) throws ParseException {
        String fechaConFormato = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatoFecha);
        fechaConFormato = simpleDateFormat.format(fecha);
        return fechaConFormato;
    }

    public enum Comparacion {
         MENOR(-1),
         IGUAL(0),
         MAYOR(1);
         
         private final int valor;
         
         private  Comparacion(int valor){
             this.valor = valor;
         }
         
         public int getValor(){
             return valor;
         }
         
    }
    
}
