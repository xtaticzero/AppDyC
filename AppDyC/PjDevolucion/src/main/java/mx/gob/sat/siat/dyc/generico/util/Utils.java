/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 **/
package mx.gob.sat.siat.dyc.generico.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesIds;
import mx.gob.sat.siat.dyc.util.constante.ConstantesTimer;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import org.tempuri2.ArrayOfMedioComunicacion;
import org.tempuri2.BuzonNotificaciones;
import org.tempuri2.BuzonNotificacionesSoap;


/**
 * Clase de utileria para el paso de parametros o el redireccionamiento
 *
 * @author Paola Rivera
 */
public final class Utils {

    private static final Logger LOGGER = Logger.getLogger(Utils.class);

    /**
     * Constructor privado puesto que la clase es Utils.
     */
    private Utils() {
    }

    private static Utils instance = null;

    /**
     * Creador sincronizado para protegerse de posibles problemas multi-hilo
     */
    private static synchronized void createInstance() {
        if (instance == null) {
            instance = new Utils();
        }
    }

    /**
     * Obtiene instancia
     *
     * @return
     */
    public static Utils getInstance() {
        createInstance();
        return instance;
    }

    /**
     * Metodo que sirve para obtener Parametros de un Managed Bean a otro
     *
     * @param nomParam
     * @return
     */
    public static Object getFlasParam(String nomParam) {
        Flash flash;
        flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        if (!flash.isEmpty()) {
            return flash.get(nomParam);
        } else {
            return null;
        }
    }

    public static void setFlashParam(String nomParam, Object param) {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        flash.put(nomParam, param);
    }

    /**
     * @param nomParam
     * @return
     */
    public static Object getParametro(String nomParam) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest)facesContext.getExternalContext().getRequest();

        return request.getAttribute(nomParam);
    }

    /**
     * @param nomParam
     * @param param
     */
    public static void setParametro(String nomParam, Object param) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest)facesContext.getExternalContext().getRequest();
        request.setAttribute(nomParam, param);
    }

    /**
     * @param url
     */
    public static void redirecciona(String url) throws IOException {
        FacesContext contex = FacesContext.getCurrentInstance();
        contex.getExternalContext().redirect(url);
    }

    /**
     * Valida si el usuario del portal de empleado tiene derecho a abrir la
     * pantalla solicitada.
     *
     * @param idProceso
     */
    public static void validarSesion(AccesoUsr accesoUsr, String idProceso) {
        HttpSession session = null;

        try {
            session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(Boolean.TRUE);

        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage(), e);
        }

        LOGGER.debug("accesoUsr.getProcesos() ->" + accesoUsr.getProcesos());
        if (!accesoUsr.getProcesos().containsValue(idProceso)) {

            LOGGER.error("Se intenta ingresar a un proceso no asignado. RFC:" + accesoUsr.getRfcCorto() + ", ruta: " +
                         ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURI());
            session.setAttribute("tipo", "2");
            session.setAttribute("error", "No se tiene permisos suficientes para acceder a este proceso");
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() +
                                                                                "/faces/resources/pages/errores/errorProceso.jsf");

            } catch (Exception e2) {
                LOGGER.error("Error al direccionar a la pantalla de error.");
            }
        }
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

    public static Integer obtenerPropiedadDelMenu(String parametro) {
        Integer resultado = 0;
        if ((FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(parametro)) != null) {
            resultado =
                    Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(parametro));
        }
        return resultado;
    }

    public static String obtenerPropiedadStringDelMenu(String parametro) {
        String resultado = "";
        if ((FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(parametro)) != null) {
            resultado = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(parametro);
        }
        return resultado;
    }

    public static boolean consultaSiRfcAmparado(String rfc) {
        BuzonNotificaciones buzonNotificaciones = new BuzonNotificaciones();
        BuzonNotificacionesSoap buzonNotificacionesSoap = buzonNotificaciones.getBuzonNotificacionesSoap();
        ArrayOfMedioComunicacion amc = buzonNotificacionesSoap.obtieneMediosComunicacion(rfc);
        if (amc.getMedioComunicacion().get(0).getAmparado() == 0) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public static boolean desdeTramitesYNoEstaAmparado(String rfc) {
        try {
            if (obtenerPropiedadDelMenu("tramites") == 1 && !consultaSiRfcAmparado(rfc)) {
                return  Boolean.TRUE;
            }
            return  Boolean.FALSE;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return  Boolean.TRUE;
        }
    }

    public static void muestraMensaje(String pantalla) {

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() +
                                                                            "/faces/resources/pages/registro/pruebas.jsf?pantalla=" +
                                                                            pantalla);
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
    }

    public static void redirecBuzonContribuyente() {
        try {
            Thread.sleep(ConstantesTimer.UNSEGUNDO * ConstantesDyCNumerico.VALOR_3);
        } catch (InterruptedException e) {
            LOGGER.info(e.getMessage());
        }
        FileInputStream archivo = null;
        try {
            Properties properties = new Properties();
            archivo = new FileInputStream("/siat/dyc/configuraciondyc/parametrosdyc.properties");
            properties.load(archivo);
            String host = properties.getProperty("buzon.host").trim();
            String server = properties.getProperty("buzon.server").trim();
            String url =
                host + "/PTSC/faces/pages/lanzador.jsf?url=/PTSC/buzonTributario/faces/Pages/Principal.jsf&tipoLogeo=c&target=principal&hostserver=" +
                server;
            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        } finally {
            IOUtils.closeQuietly(archivo);
        }
    }

    public static String formatearFechaTag30(String valorFechaTag30) {
        if (valorFechaTag30 == null) {
            return " ";
        }
        try {
            DateFormat datef = new SimpleDateFormat("dd/MM/yy");
            Date fechaTag30 = datef.parse(valorFechaTag30);
            return formatearFechaCompleta(fechaTag30);
        } catch (ParseException e) {
            return valorFechaTag30;
        } catch (Exception e) {
            return " ";
        }
    }

    /**
     * Crea una carpeta con el nombre indicado y le asigna los permisos 777 para todos los usuarios.
     *
     * @param nombreCarpeta nombre de la carpeta a crear. Este nombre debe de crearse con la
     * ruta completa.
     */
    public static boolean crearCarpeta(String nombreCarpeta) {
        boolean bandera = false;
        File archivo = new File(nombreCarpeta);
        boolean a = archivo.mkdirs();
        boolean b = archivo.setWritable( Boolean.TRUE,  Boolean.FALSE);
        boolean c = archivo.setExecutable( Boolean.TRUE,  Boolean.FALSE);
        boolean d = archivo.setReadable( Boolean.TRUE,  Boolean.FALSE);
        LOGGER.debug(nombreCarpeta + " Creado: " + a + ", Escribible: " + b + ", Ejecutable: " + c + ", Leible: " + d);
        if (a) {
            bandera = b && c && d;
        }
        return bandera;
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

    public static boolean esCompetenciaAGAFF(int claveAdm) {
        boolean esAgaff;
        switch (claveAdm) {
        case ConstantesDyCNumerico.VALOR_80:
        case ConstantesDyCNumerico.VALOR_81:
        case ConstantesDyCNumerico.VALOR_82:
        case ConstantesDyCNumerico.VALOR_90:
        case ConstantesDyCNumerico.VALOR_91:
        case ConstantesDyCNumerico.VALOR_97:
            esAgaff =  Boolean.FALSE;
            break;
        default:
            esAgaff =  Boolean.TRUE;
            break;
        }
        return esAgaff;
    }

    public static Integer identificaCompetencia(int claveAdm) {
        Integer competencia;
        switch (claveAdm) {
        case ConstantesDyCNumerico.VALOR_90:
        case ConstantesDyCNumerico.VALOR_91:
        case ConstantesDyCNumerico.VALOR_97:
            competencia = ConstantesIds.COMPETENCIA_AGGC;
            break;
        case ConstantesDyCNumerico.VALOR_80:
            competencia = ConstantesIds.COMPETENCIA_AGACE;
            break;
        case ConstantesDyCNumerico.VALOR_81:
        case ConstantesDyCNumerico.VALOR_82:
            competencia = ConstantesIds.COMPETENCIA_AGH;
            break;
        default:
            competencia = ConstantesIds.COMPETENCIA_AGGAF;
            break;
        }
        return competencia;
    }
    
    public static String sqlCreaParamIn(String[] arrString) {
        return (Arrays.toString(arrString)).replaceAll("\\[", "").replaceAll("\\]", "");
    }

    /**
     * obtiene los ultimos caracteres especificados de la cadena
     *
     * @param cadena cadena de caracteres de la cual se obtienen los ultimos
     * caracteres
     * @param numberOfCharacters el numero de caracteres a obtener de la cadena
     * de caracteres
     * @return cadena con los ultimos caracteres
     */
    public static String getLastCharacters(String cadena, int numberOfCharacters) {

        if (cadena.length() > numberOfCharacters) {
            return cadena.substring(cadena.length() - numberOfCharacters, cadena.length());
        }
        return cadena;
    }
}
