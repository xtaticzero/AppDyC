/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.consulta.devautisr.util;

/**
 *
 * @author root
 */
public final class ConstantesConsultaDevAutISR {
    private ConstantesConsultaDevAutISR(){
        
    }
    public static final int NO_PAGADO=9;
    public static final int PRE_AUTORIZADA=2;
    public static final int RECHAZADA_PROCESO=5;
    public static final int RECHAZADA_CONTROL_SALDOS=6;
    public static final int EN_PROCESO_PAGO=7;
    public static final int PAGADO=8;
    public static final int RECHAZADA_USUARIO=13;
    public static final String FAVOR_PEQUENO="FP";
    public static final String UL = "</ul>";
    
    public static final String FOLIO="<folio>";
    public static final String EJERCICIO="<ejercicio>";
    public static final String FECHAPRESENTACION="fechapresentacion";
    public static final String MOTIVORECHAZO="<motivorechazo>";
    public static final String LISTARECHAZOS="<listarechazos>";
    public static final String LISTAINCONSISTENCIAS="<listainconsistencia>";
    public static final String LISTAACCIONCORRECTIVA="<listaaccioncorrectiva>";
    public static final String PROCESO_DEVOLUCION ="<p>Para poder continuar con el proceso de devolución:</p><ul>";
    public static final String PROCESO_DEVOLUCION_2017 ="<p>Para poder continuar con el proceso de devolución se te solicita:</p><ul>";
    public static final String CONTRIBUYENTE = "<p>Estimado contribuyente,</p>\n";
    public static final String SAT_INCONCISTENCIAS = "<p>El Servicio de Administración Tributaria localizó la(s) siguiente(s) inconsistencia(s):</p><ul>";
    public static final String SAT_INCONCISTENCIAS2017 = "<p>El Servicio de Administración Tributaria localizó la(s) siguiente(s) inconsistencia(s) en las deducciones:</p><ul>";
    public static final String FALTA_PAGO="<p>La falta de pago total o parcial no constituye una negativa a su devolución ni se considera resolución definitiva.</p>";
    public static final String MSG_NO_PAGADO="<p>El Servicio de Administración Tributaria le comunica que la devolución correspondiente al saldo a favor del Impuesto Sobre la Renta que se autodeterminó en la declaración anual del ejercicio "+EJERCICIO+", presentada con fecha "+FECHAPRESENTACION+" registrada con el número de operación "+FOLIO+", fue autorizada.</p>\n" +
"\n" +
"<p>Así mismo, se le informa que el importe autorizado en devolución, no pudo ser depositado en la cuenta manifestada en la declaración, toda vez que la Institución Bancaria la tiene registrada como "+MOTIVORECHAZO+".</p>\n" +
"\n" +
"<p>A efecto de poder llevar a cabo el depósito de la devolución, deberá proporcionar su número de cuenta bancaria activa para transferencias electrónicas a 18 dígitos (CLABE), la cual deberá estar a su nombre, e institución bancaria correspondiente, en el Portal del SAT mediante Buzón tributario o Trámites (www.sat.gob.mx). </p>\n" +
"\n" +
"<p>En caso de duda, le sugerimos llamar a marcaSAT al 627 22 728 desde la Ciudad de México o, 01 (55) 627 22 728 del resto del país, donde recibirá orientación de nuestros asesores.</p>";

public static final String MSG_RECHAZADOS= CONTRIBUYENTE +
"\n" +
SAT_INCONCISTENCIAS +
LISTARECHAZOS+ UL +
"\n" +
PROCESO_DEVOLUCION +
LISTAACCIONCORRECTIVA+ UL + 
FALTA_PAGO;        



public static final String MSG_RECHAZADOS2=CONTRIBUYENTE +
"\n" +
SAT_INCONCISTENCIAS +
LISTARECHAZOS+ UL +
"\n" +
PROCESO_DEVOLUCION +
LISTAACCIONCORRECTIVA+ UL +
"\n" + FALTA_PAGO;



public static final String MSG_INCONSISTENCIA="<p>Estimado contribuyente, </p>\n\n" +
"<p>El Servicio de Administración Tributaria localizo la(s) siguiente(s) inconsistencia(s): </p><ul>" +
LISTAINCONSISTENCIAS+ UL +
"<p>Para poder continuar con el proceso de devolución: </p><ul>" +
LISTAACCIONCORRECTIVA+ UL +
"<p>La falta de pago total o parcial no constituye una negativa a su devolución ni se considera resolución definitiva. </p>";

public static final String MSG_INCONSISTENCIA2=CONTRIBUYENTE +
"\n" +
SAT_INCONCISTENCIAS +
LISTAINCONSISTENCIAS+ UL +
"\n" +
PROCESO_DEVOLUCION +
LISTAACCIONCORRECTIVA+ UL +
"\n" + FALTA_PAGO;


public static final String MSG_INCONSISTENCIA3=CONTRIBUYENTE +
"\n" +
SAT_INCONCISTENCIAS2017 +
LISTAINCONSISTENCIAS+ UL +
"\n" +
PROCESO_DEVOLUCION_2017 +
LISTAACCIONCORRECTIVA+ UL +
"\n" +
"<p><b>La falta de pago total o parcial no constituye una negativa a su devolución ni se considera resolución definitiva.<b></p>";


public static final String MSG_INCONSISTENCIA4=CONTRIBUYENTE +
"\n" +
SAT_INCONCISTENCIAS +
LISTAINCONSISTENCIAS+ UL +
"\n" +
PROCESO_DEVOLUCION_2017 +
LISTAACCIONCORRECTIVA+ UL +
"\n" + FALTA_PAGO;


}
