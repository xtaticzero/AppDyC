/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.siat.dyc.casocomp.util;

import java.math.BigDecimal;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;


public final class Utils {
    private Utils() {
    }

    public static String formatearFecha(Date fecha) {
        if (fecha == null) {
            return "";
        } else {
            SimpleDateFormat formatoSAT = new SimpleDateFormat("dd/MM/yyyy");
            return formatoSAT.format(fecha);
        }
    }

    public static String formatearMoneda(Double cantidad) {
        if (cantidad != null) {
            return new DecimalFormat("$ ###,##0.00").format(cantidad);
        } else {
            return "";
        }
    }

    public static BigDecimal obtenerCantidadMoneda(String str) {
        String moneda = str.replace("$ ", "");
        return new BigDecimal(moneda.replaceAll(",", ""));
    }
    
    public static String formatearFechaCompleta(Date fecha) {
        if (fecha == null) {
            return "";
        } else {
            Locale localeMexico = new Locale("es", "MX");
            SimpleDateFormat formatoSAT = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy'.' HH':'mm 'Hrs.'", localeMexico);
            String x = formatoSAT.format(fecha);
            return x.substring(0, 1).toUpperCase() + x.substring(1);
        }
    }

    public static String formatearFechaMesAnio(Date fecha) {
        if (fecha == null) {
            return "";
        } else {
            Locale localeMexico = new Locale("es", "MX");
            SimpleDateFormat formatoSAT = new SimpleDateFormat("MMMM yyyy", localeMexico);
            String x = formatoSAT.format(fecha);
            return x.substring(0, 1).toUpperCase() + x.substring(1);
        }
    }
    
    public static String formatearFechaHora(Date fecha) {
        if (fecha == null) {
            return "";
        } else {
            Locale localeMexico = new Locale("es", "MX");
            SimpleDateFormat formatoSAT = new SimpleDateFormat("dd/MM/yyyy'. a las ' HH':'mm 'Hrs.'", localeMexico);
            String x = formatoSAT.format(fecha);
            return x.substring(0, 1).toUpperCase() + x.substring(1);
        }
    }
}
