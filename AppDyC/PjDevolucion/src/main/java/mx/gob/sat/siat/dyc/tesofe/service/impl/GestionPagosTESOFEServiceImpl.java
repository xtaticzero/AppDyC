package mx.gob.sat.siat.dyc.tesofe.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.math.BigDecimal;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import mx.gob.sat.siat.dyc.dao.resolucion.DyctResolucionDAO;
import mx.gob.sat.siat.dyc.generico.util.ArchivoCargaDescarga;
import mx.gob.sat.siat.dyc.registro.service.solicitud.DycpSolicitudService;
import mx.gob.sat.siat.dyc.tesofe.dao.GestionPagoDAO;
import mx.gob.sat.siat.dyc.tesofe.dto.DatosEMP;
import mx.gob.sat.siat.dyc.tesofe.dto.DatosPagoDTO;
import mx.gob.sat.siat.dyc.tesofe.dto.DatosRetroTESOFE;
import mx.gob.sat.siat.dyc.tesofe.dto.InformacionEnvioATESOFE;
import mx.gob.sat.siat.dyc.tesofe.service.GestionPagosTESOFEService;
import mx.gob.sat.siat.dyc.tesofe.util.constante.ConstantesTESOFE;
import mx.gob.sat.siat.dyc.tesofe.util.recurso.UtileriasTESOFE;
import mx.gob.sat.siat.dyc.trabajo.dao.DatosTESOFEDAO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCURL;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Jesus Alfredo Hernandez Orozco
 */
@Service(value = "gestionPagosTESOFEService")
public class GestionPagosTESOFEServiceImpl implements GestionPagosTESOFEService {

    private Date fechaPago;
    private Date fechaPresentacion;

    private Boolean esAutomaticaISR;

    private static final Logger LOGGER = Logger.getLogger(GestionPagosTESOFEServiceImpl.class);
    private static final String FORMAT_INTEGER = "%02d";
    private static final int LIMITE_PARTICION = 1000;
    private static final int INICIO_NOMBRE_ISR = 0;
    private static final int FIN_NOMBRE_ISR = 2;
    private static final String CONSTANTE_NULA = ",'null'";
    private static final int TAMANO_GRUPO = 500;

    @Autowired(required = true)
    private DatosTESOFEDAO datosTESOFEDAO;

    @Autowired
    private DyctResolucionDAO dyctResolucionDAO;

    @Autowired
    private GestionPagoDAO gestionPagoDAO;

    @Autowired
    private DycpSolicitudService dycpSolicitudService;

    @Resource(name = "rutaEnviarTESOFE")
    private String rutaEnviarTESOFE;

    private int tamanoGrupo;

    private Long consecutivoModificacion;

    public GestionPagosTESOFEServiceImpl() {
        tamanoGrupo = TAMANO_GRUPO;
    }

    /**
     * Implementa la gestion de pagos ante la TESOFE. Este método, genera una un
     * archivo por cada una de las claves administrativas.
     */
    @Override
    public List<InformacionEnvioATESOFE> gestionarPagoAnteTESOFE(Date fechaPago, Date fechaPresentacion, Boolean isAutoISR) {
        Integer numeroDeArchivo = null;
        String nombreArchivo = "";
        calcularFechaDePresentacionyPago(fechaPago, fechaPresentacion);
        BigDecimal importeTotal = new BigDecimal(ConstantesTESOFE.CERO);
        List<InformacionEnvioATESOFE> listaDeArchivosGenerados = new ArrayList<InformacionEnvioATESOFE>();
        List<DatosEMP> lstTramitesAutorizados = new ArrayList<DatosEMP>();
        Integer numeroDevoluciones;
        DatosPagoDTO objetoDatoPago = null;
        this.esAutomaticaISR = isAutoISR;
        String competencia = (!this.esAutomaticaISR) ? ConstantesTESOFE.AGAFF_NOMBRE : ConstantesTESOFE.PDA_NOMBRE;

        try {
            lstTramitesAutorizados
                    = datosTESOFEDAO.contarListaDatosTESOFE(ConstantesTESOFE.AGAFF, ConstantesTESOFE.SIN_RECHAZO, this.esAutomaticaISR);

            if (lstTramitesAutorizados.size() > 0) {

                numeroDeArchivo = datosTESOFEDAO.obtenerNumerDeArchivo();
                nombreArchivo = generarNombreParaArchivos((!this.esAutomaticaISR) ? ConstantesTESOFE.AGAFF : ConstantesTESOFE.PDA_NOMBRE);
                objetoDatoPago
                        = generarArchivosDAT(nombreArchivo, ConstantesTESOFE.AGAFF, ConstantesTESOFE.SIN_RECHAZO,
                                numeroDeArchivo, false, null);
                importeTotal = objetoDatoPago.getImporteTotal();
                numeroDevoluciones = objetoDatoPago.getNumeroDeRegistros();
                generarArchivoCSV(nombreArchivo, importeTotal, ConstantesTESOFE.SIN_RECHAZO);
                listaDeArchivosGenerados.add(regresarObjeto(nombreArchivo, competencia,
                        ConstantesTESOFE.SIN_RECHAZO, importeTotal,
                        numeroDevoluciones));
                actualizaEstadoTramites(lstTramitesAutorizados);
            }

            lstTramitesAutorizados
                    = datosTESOFEDAO.contarListaDatosTESOFE(ConstantesTESOFE.AGAFF, ConstantesTESOFE.CON_RECHAZO, this.esAutomaticaISR);
            if (lstTramitesAutorizados.size() > 0) {
                numeroDeArchivo = datosTESOFEDAO.obtenerNumerDeArchivo();
                nombreArchivo = generarNombreParaArchivos((!this.esAutomaticaISR) ? ConstantesTESOFE.AGAFF : ConstantesTESOFE.PDA_NOMBRE);
                objetoDatoPago
                        = generarArchivosDAT(nombreArchivo, ConstantesTESOFE.AGAFF, ConstantesTESOFE.CON_RECHAZO,
                                numeroDeArchivo, false, null);
                importeTotal = objetoDatoPago.getImporteTotal();
                numeroDevoluciones = objetoDatoPago.getNumeroDeRegistros();
                generarArchivoCSV(nombreArchivo, importeTotal, ConstantesTESOFE.CON_RECHAZO);
                listaDeArchivosGenerados.add(regresarObjeto(nombreArchivo, competencia,
                        ConstantesTESOFE.CON_RECHAZO, importeTotal,
                        numeroDevoluciones));
                actualizaEstadoTramites(lstTramitesAutorizados);
            }

            if (!this.esAutomaticaISR) {
                lstTramitesAutorizados
                        = datosTESOFEDAO.contarListaDatosTESOFE(ConstantesTESOFE.AGGC, ConstantesTESOFE.SIN_RECHAZO, this.esAutomaticaISR);
                if (lstTramitesAutorizados.size() > 0) {

                    numeroDeArchivo = datosTESOFEDAO.obtenerNumerDeArchivo();
                    nombreArchivo = generarNombreParaArchivos(ConstantesTESOFE.AGGC);
                    objetoDatoPago
                            = generarArchivosDAT(nombreArchivo, ConstantesTESOFE.AGGC, ConstantesTESOFE.SIN_RECHAZO, numeroDeArchivo,
                                    false, null);
                    importeTotal = objetoDatoPago.getImporteTotal();
                    numeroDevoluciones = objetoDatoPago.getNumeroDeRegistros();
                    generarArchivoCSV(nombreArchivo, importeTotal, ConstantesTESOFE.SIN_RECHAZO);
                    listaDeArchivosGenerados.add(regresarObjeto(nombreArchivo, ConstantesTESOFE.AGGC_NOMBRE,
                            ConstantesTESOFE.SIN_RECHAZO, importeTotal,
                            numeroDevoluciones));
                    actualizaEstadoTramites(lstTramitesAutorizados);
                }

                lstTramitesAutorizados
                        = datosTESOFEDAO.contarListaDatosTESOFE(ConstantesTESOFE.AGGC, ConstantesTESOFE.CON_RECHAZO, this.esAutomaticaISR);
                if (lstTramitesAutorizados.size() > 0) {

                    numeroDeArchivo = datosTESOFEDAO.obtenerNumerDeArchivo();
                    nombreArchivo = generarNombreParaArchivos(ConstantesTESOFE.AGGC);
                    objetoDatoPago
                            = generarArchivosDAT(nombreArchivo, ConstantesTESOFE.AGGC, ConstantesTESOFE.CON_RECHAZO, numeroDeArchivo,
                                    false, null);
                    importeTotal = objetoDatoPago.getImporteTotal();
                    numeroDevoluciones = objetoDatoPago.getNumeroDeRegistros();
                    generarArchivoCSV(nombreArchivo, importeTotal, ConstantesTESOFE.CON_RECHAZO);
                    listaDeArchivosGenerados.add(regresarObjeto(nombreArchivo, ConstantesTESOFE.AGGC_NOMBRE,
                            ConstantesTESOFE.CON_RECHAZO, importeTotal,
                            numeroDevoluciones));
                    actualizaEstadoTramites(lstTramitesAutorizados);
                }
                lstTramitesAutorizados
                        = datosTESOFEDAO.contarListaDatosTESOFE(ConstantesTESOFE.AGH, ConstantesTESOFE.SIN_RECHAZO, this.esAutomaticaISR);
                if (lstTramitesAutorizados.size() > 0) {

                    numeroDeArchivo = datosTESOFEDAO.obtenerNumerDeArchivo();
                    nombreArchivo = generarNombreParaArchivos(ConstantesTESOFE.AGH);
                    objetoDatoPago
                            = generarArchivosDAT(nombreArchivo, ConstantesTESOFE.AGH, ConstantesTESOFE.SIN_RECHAZO, numeroDeArchivo,
                                    false, null);
                    importeTotal = objetoDatoPago.getImporteTotal();
                    numeroDevoluciones = objetoDatoPago.getNumeroDeRegistros();
                    generarArchivoCSV(nombreArchivo, importeTotal, ConstantesTESOFE.SIN_RECHAZO);
                    listaDeArchivosGenerados.add(regresarObjeto(nombreArchivo, ConstantesTESOFE.AGH_NOMBRE,
                            ConstantesTESOFE.SIN_RECHAZO, importeTotal,
                            numeroDevoluciones));
                    actualizaEstadoTramites(lstTramitesAutorizados);
                }
                lstTramitesAutorizados
                        = datosTESOFEDAO.contarListaDatosTESOFE(ConstantesTESOFE.AGH, ConstantesTESOFE.CON_RECHAZO, this.esAutomaticaISR);
                if (lstTramitesAutorizados.size() > 0) {

                    numeroDeArchivo = datosTESOFEDAO.obtenerNumerDeArchivo();
                    nombreArchivo = generarNombreParaArchivos(ConstantesTESOFE.AGH);
                    objetoDatoPago
                            = generarArchivosDAT(nombreArchivo, ConstantesTESOFE.AGH, ConstantesTESOFE.CON_RECHAZO, numeroDeArchivo,
                                    false, null);
                    importeTotal = objetoDatoPago.getImporteTotal();
                    numeroDevoluciones = objetoDatoPago.getNumeroDeRegistros();
                    generarArchivoCSV(nombreArchivo, importeTotal, ConstantesTESOFE.CON_RECHAZO);
                    listaDeArchivosGenerados.add(regresarObjeto(nombreArchivo, ConstantesTESOFE.AGH_NOMBRE,
                            ConstantesTESOFE.CON_RECHAZO, importeTotal,
                            numeroDevoluciones));
                    actualizaEstadoTramites(lstTramitesAutorizados);
                }
                lstTramitesAutorizados
                        = datosTESOFEDAO.contarListaDatosTESOFE(ConstantesTESOFE.AGACE, ConstantesTESOFE.SIN_RECHAZO, this.esAutomaticaISR);
                if (lstTramitesAutorizados.size() > 0) {

                    numeroDeArchivo = datosTESOFEDAO.obtenerNumerDeArchivo();
                    nombreArchivo = generarNombreParaArchivos(ConstantesTESOFE.AGACE);
                    objetoDatoPago
                            = generarArchivosDAT(nombreArchivo, ConstantesTESOFE.AGACE, ConstantesTESOFE.SIN_RECHAZO,
                                    numeroDeArchivo, false, null);
                    importeTotal = objetoDatoPago.getImporteTotal();
                    numeroDevoluciones = objetoDatoPago.getNumeroDeRegistros();
                    generarArchivoCSV(nombreArchivo, importeTotal, ConstantesTESOFE.SIN_RECHAZO);
                    listaDeArchivosGenerados.add(regresarObjeto(nombreArchivo, ConstantesTESOFE.AGACE_NOMBRE,
                            ConstantesTESOFE.SIN_RECHAZO, importeTotal,
                            numeroDevoluciones));
                    actualizaEstadoTramites(lstTramitesAutorizados);
                }
                lstTramitesAutorizados
                        = datosTESOFEDAO.contarListaDatosTESOFE(ConstantesTESOFE.AGACE, ConstantesTESOFE.CON_RECHAZO, this.esAutomaticaISR);
                if (lstTramitesAutorizados.size() > 0) {

                    numeroDeArchivo = datosTESOFEDAO.obtenerNumerDeArchivo();
                    nombreArchivo = generarNombreParaArchivos(ConstantesTESOFE.AGACE);
                    objetoDatoPago
                            = generarArchivosDAT(nombreArchivo, ConstantesTESOFE.AGACE, ConstantesTESOFE.CON_RECHAZO,
                                    numeroDeArchivo, false, null);
                    importeTotal = objetoDatoPago.getImporteTotal();
                    numeroDevoluciones = objetoDatoPago.getNumeroDeRegistros();
                    generarArchivoCSV(nombreArchivo, importeTotal, ConstantesTESOFE.CON_RECHAZO);
                    listaDeArchivosGenerados.add(regresarObjeto(nombreArchivo, ConstantesTESOFE.AGACE_NOMBRE,
                            ConstantesTESOFE.CON_RECHAZO, importeTotal,
                            numeroDevoluciones));
                    actualizaEstadoTramites(lstTramitesAutorizados);
                }
            }
        } catch (SIATException e) {
            LOGGER.error("gestionarPagoAnteTESOFE(): " + e);
        }
        return listaDeArchivosGenerados;
    }

    private int actualizaEstadoTramites(List<DatosEMP> listTramitesAutorizados) {
        int retorno = 0;

        try {
            List<String> lstNumControl = obtenerNumeroControl(listTramitesAutorizados);
            String[] arregloNumDeControl = new String[lstNumControl.size()];
            arregloNumDeControl = lstNumControl.toArray(arregloNumDeControl);

            int particiones = arregloNumDeControl.length / LIMITE_PARTICION;
            int sobrante = arregloNumDeControl.length % LIMITE_PARTICION;

            particiones = (sobrante != 0) ? (particiones + 1) : (particiones + 0);

            for (int i = 0; i < particiones; i++) {
                String numerosDeControl = "";
                int inicio = (i == 0) ? 0 : ((i * LIMITE_PARTICION));
                int fin = (((i + 1) * LIMITE_PARTICION));

                numerosDeControl = UtileriasTESOFE.generarParamNumControl(Arrays.asList(Arrays.copyOfRange(arregloNumDeControl, inicio, fin)));
                numerosDeControl = numerosDeControl.replaceAll(CONSTANTE_NULA, "");
                retorno = dycpSolicitudService.actualizarStatusPago(Constantes.EstadosSolicitud.EN_PROCESO_PAGO.getIdEstadoSolicitud(),
                        numerosDeControl);
            }

        } catch (SIATException ex) {
            LOGGER.error("error al actualizar los tramites: " + ex);
        }
        return retorno;
    }

    private List<String> obtenerNumeroControl(List<DatosEMP> lstTramitesAutorizados) {

        List<String> lstNumControl = new ArrayList<String>();
        for (DatosEMP dato : lstTramitesAutorizados) {
            lstNumControl.add(dato.getNumControl());
        }

        return lstNumControl;
    }

    /**
     * <br />
     * Regresa un objeto con los datos de la información del envío a TESOFE,
     * esta información es la que se muestra listada en la pantalla y que además
     * permite la descarga de los archivos.
     *
     * @param nombreArchivo Nombre del archivo generado.
     * @param competencia Indica si esta priviene de AGAFF o AGGC
     * @param identificadorRechazo Indica si viene de un rechazo o si es un
     * primer envío
     * @param importeTotal Es el importe total por el cual se generó el archivo.
     * @return Objeto con los siguientes datos: compentencia, nombre, archivos
     * CSV y DAT, tipo de envio y total a pagar.
     */
    private InformacionEnvioATESOFE regresarObjeto(String nombreArchivo, String competencia,
            Integer identificadorRechazo, BigDecimal importeTotal,
            Integer numeroDev) {
        try {
            InformacionEnvioATESOFE objeto = new InformacionEnvioATESOFE();
            ArchivoCargaDescarga objetoDescarga = new ArchivoCargaDescarga();
            objeto.setCompentencia(competencia);
            objeto.setNombre(nombreArchivo);
            objeto.setDat(nombreArchivo + ConstantesTESOFE.DAT);
            objeto.setCsv(nombreArchivo + ConstantesTESOFE.CSV);
            objeto.setFileCSV(objetoDescarga.descargarArchivo(ConstantesTESOFE.URI_TESOFE_ENVIAR.concat(nombreArchivo
                    + ConstantesTESOFE.CSV)));
            objeto.setFileDAT(objetoDescarga.descargarArchivo(ConstantesTESOFE.URI_TESOFE_ENVIAR.concat(nombreArchivo.concat(ConstantesTESOFE.DAT))));
            if (identificadorRechazo.equals(1)) {
                objeto.setTipo(ConstantesTESOFE.PRIMER_ENVIO);
                objeto.setTotalAPagar(importeTotal);

            } else if (identificadorRechazo.equals(ConstantesDyCNumerico.VALOR_2)) {
                objeto.setTipo(ConstantesTESOFE.RECHAZO);

                objeto.setTotalAPagar(importeTotal);

            } else if (identificadorRechazo.equals(ConstantesDyCNumerico.VALOR_3)) {
                objeto.setTipo(ConstantesTESOFE.MODIFICACION);
                objeto.setTotalAPagar(importeTotal);
            }

            objeto.setNumeroDev(Long.valueOf(numeroDev));
            return objeto;
        } catch (Exception e) {
            LOGGER.error("regresarObjeto(): " + e.getMessage());
        }
        return null;
    }

    /**
     * Obtiene la información de los registros agregados al archivo, para que
     * posteriormente genere nuevos registros.
     *
     * @param fechaPago
     * @param fechaPresentacion
     * @param rutaArchivo
     * @param isAutoISR
     * @return
     */
    @Override
    public List<InformacionEnvioATESOFE> modificar(Date fechaPago, Date fechaPresentacion, String rutaArchivo, Boolean isAutoISR) {
        int motivoRechazo = 0;

        String administracion = "";
        String nombreArchivo = "";
        DatosPagoDTO objetoDatoPago = null;
        this.esAutomaticaISR = isAutoISR;

        calcularFechaDePresentacionyPago(fechaPago, fechaPresentacion);
        BigDecimal importeTotal;
        List<String> lista = null;
        List<InformacionEnvioATESOFE> listaRetorno = new ArrayList<InformacionEnvioATESOFE>();
        Integer numeroDevoluciones = 0;
        try {
            Integer numeroDeArchivo = datosTESOFEDAO.obtenerNumerDeArchivo();
            numeroDeArchivo++;
            lista = leerClavesDeRastreo(rutaArchivo);

            if (lista != null && lista.size() > 0) {
                administracion = (!this.esAutomaticaISR) ? verificarAdminOrigen(lista.get(0)) : ConstantesTESOFE.PDA_NOMBRE;

                motivoRechazo = datosTESOFEDAO.obtenerMotivoRechazo(lista.get(0));

                nombreArchivo = generarNombreParaArchivos(administracion);

                objetoDatoPago
                        = generarArchivosDAT(nombreArchivo, administracion, motivoRechazo, numeroDeArchivo, Boolean.TRUE, lista);
                importeTotal = objetoDatoPago.getImporteTotal();
                numeroDevoluciones = objetoDatoPago.getNumeroDeRegistros();

                generarArchivoCSV(nombreArchivo, importeTotal, motivoRechazo);

                listaRetorno.add(regresarObjeto(nombreArchivo, "N/A", ConstantesDyCNumerico.VALOR_3, importeTotal,
                        numeroDevoluciones));
            }
        } catch (SIATException e) {
            LOGGER.error("modificarArxhivoGeneradoAnteriormente(): " + e);
        }
        return listaRetorno;
    }

    /**
     * Obtiene las claves de rastreo del archivo a modificar
     *
     * @param rutaArchivo
     * @return
     */
    private List<String> leerClavesDeRastreo(String rutaArchivo) {
        String renglon = "";
        BufferedReader bufferLectura = null;
        List<String> listaClavesDeRastreo = new ArrayList<String>();
        try {
            bufferLectura = new BufferedReader(new InputStreamReader(new FileInputStream(rutaArchivo), "UTF-8"));

            // Leer el archivo linea por linea
            while ((renglon = bufferLectura.readLine()) != null) {
                if (renglon.length() == ConstantesTESOFE.LONGITUD_RENGLON) {
                    boolean bandera
                            = renglon.substring(ConstantesTESOFE.INICIO_CVE_RASTREO, ConstantesTESOFE.FIN_CVE_RASTREO).trim().length()
                            > 0;
                    if (bandera) {
                        listaClavesDeRastreo.add(renglon.substring(ConstantesTESOFE.INICIO_CVE_RASTREO,
                                ConstantesTESOFE.FIN_CVE_RASTREO));
                    }
                } else {
                    LOGGER.warn("Error, longitud del regnlon:  " + renglon.length());
                }
            }
            if (bufferLectura != null) {
                bufferLectura.close();
            }
        } catch (Exception ioe) {
            LOGGER.error("retroalimentarTESOFE(), Hubo un error al leer el archivo: " + ioe);

        } finally {
            try {
                bufferLectura.close();

            } catch (Exception e) {
                LOGGER.error("retroalimentarTESOFE(), Hubo un error al leer el archivo: " + e);
            }
        }
        return listaClavesDeRastreo;
    }

    /**
     * Genera toConstantesTESOFE.DOS los archivos DAT que se pueden enviar a
     * TESOFE durante el dia.
     *
     * @param nombreArchivo
     * @param claveADM
     */
    private DatosPagoDTO generarArchivosDAT(String nombreArchivo, String claveADM, Integer rechazo,
            int numeroDeArchivo, boolean banderaModificacion,
            List<String> listaDeClavesDeArchivo) throws SIATException {
        Integer numeroDevoluciones = 0;
        BigDecimal importeTotal = new BigDecimal(ConstantesTESOFE.CERO);
        DatosPagoDTO objetoDatoPago = new DatosPagoDTO();
        String clavesRastreo = "";
        try {
            Long consecutivoDAT = 0L;
            List<DatosEMP> listaDatosTESOFE = null;
            PrintWriter archivoDAT
                    = new PrintWriter(new File(ConstantesDyCURL.URL_GENERADOS_TESOFE + nombreArchivo + ConstantesTESOFE.DAT),
                            ConstantesTESOFE.CODIFICACION);
            if (!banderaModificacion) {
                listaDatosTESOFE = datosTESOFEDAO.listarDatosTESOFE(claveADM, rechazo, this.esAutomaticaISR);

                if (listaDatosTESOFE != null && listaDatosTESOFE.size() > 0) {
                    numeroDevoluciones = listaDatosTESOFE.size();
                    consecutivoDAT++;
                    importeTotal
                            = escribirEnArchivoDAT(archivoDAT, numeroDeArchivo, listaDatosTESOFE, numeroDevoluciones,
                                    consecutivoDAT);
                }

            } else {
                int inicio = 0;
                int fin = 0;
                Long consecutivoInterno = 0L;
                numeroDevoluciones = listaDeClavesDeArchivo.size();
                String[] arregloDeClavesDeRastreo = new String[listaDeClavesDeArchivo.size()];
                arregloDeClavesDeRastreo = listaDeClavesDeArchivo.toArray(arregloDeClavesDeRastreo);
                int numeroDeParticiones =listaDeClavesDeArchivo.size() / ConstantesTESOFE.MAXIMO_CLAVES_A_CONSULTAR;
                consecutivoDAT++;

                archivoDAT.print(generarEncabezadoParaDAT(String.format(FORMAT_INTEGER, numeroDeArchivo))
                        + ConstantesTESOFE.SALTO_DE_LINEA);
                
                if (this.esAutomaticaISR) {
                
                    if (listaDeClavesDeArchivo.size() > 0) {
                        consecutivoInterno++;
                        listaDatosTESOFE = generarClavesDeRastreoQuery(arregloDeClavesDeRastreo);
                        if (listaDatosTESOFE != null && listaDatosTESOFE.size() > 0) {
                            importeTotal
                                    = importeTotal.add(escribirEnArchivoDATModificacion(archivoDAT, numeroDeArchivo, listaDatosTESOFE,
                                                    consecutivoInterno));
                        }
                    }
                } else  {
                    for (int i = 0; i <= numeroDeParticiones; i++) {
                        if (i == 0) {
                            consecutivoInterno++;
                        }
                        if ((listaDeClavesDeArchivo.size() < ConstantesTESOFE.MAXIMO_CLAVES_A_CONSULTAR) || i == numeroDeParticiones) {
                            fin = listaDeClavesDeArchivo.size() - 1;
                        } else {
                            fin = inicio + ConstantesTESOFE.MAXIMO_CLAVES_A_CONSULTAR;
                        }
                        clavesRastreo
                                = UtileriasTESOFE.generarClavesDeRastreo(Arrays.asList(Arrays.copyOfRange(arregloDeClavesDeRastreo,
                                                        inicio,
                                                        (fin + 1))));
                        listaDatosTESOFE = datosTESOFEDAO.listarDatosTESOFEConClaveDeRastreo(clavesRastreo);
                        if (listaDatosTESOFE != null && listaDatosTESOFE.size() > 0) {
                            importeTotal
                                    = importeTotal.add(escribirEnArchivoDATModificacion(archivoDAT, numeroDeArchivo, listaDatosTESOFE,
                                                    consecutivoInterno));
                        }
                        inicio = fin + 1;
                        consecutivoInterno = consecutivoModificacion;
                    }
                }
                archivoDAT.print(generarSumarioParaDAT(numeroDevoluciones, String.valueOf(importeTotal),
                        String.format(FORMAT_INTEGER, numeroDeArchivo)));
            }
            archivoDAT.close();

        } catch (FileNotFoundException e) {
            LOGGER.error("Error al crear el archivo: " + e);
            throw new SIATException(e);
        } catch (UnsupportedEncodingException uce) {
            LOGGER.error("Error al crear el archivo: " + uce);
            throw new SIATException(uce);
        }
        objetoDatoPago.setImporteTotal(importeTotal);
        objetoDatoPago.setNumeroDeRegistros(numeroDevoluciones);
        return objetoDatoPago;
    }
    
    String[] getGrupoClavesRastreo(String[] indices) {

        int numeroIndice = 0;

        int numeroGrupos = indices.length / tamanoGrupo;
        int diferenciaGrupo = indices.length % tamanoGrupo;

        if (diferenciaGrupo != 0) {
            numeroGrupos++;
        }

        String[] grupoIndices = new String[numeroGrupos];

        for (int i = 0; i < numeroGrupos; i++) {

            if (i == numeroGrupos - 1 && diferenciaGrupo != 0) {
                tamanoGrupo = diferenciaGrupo;
            }

            String[] grupo = new String[tamanoGrupo];
            for (int j = 0; j < tamanoGrupo; j++) {
                grupo[j] = indices[numeroIndice++];
            }

            grupoIndices[i] = UtileriasTESOFE.generarClavesDeRastreo(Arrays.asList(grupo));
            LOGGER.info(grupoIndices[i]);
        }

        return grupoIndices;
    }

    private BigDecimal escribirEnArchivoDATModificacion(PrintWriter archivoDAT, int numeroDeArchivo,
            List<DatosEMP> listaDatosTESOFE, Long consecutivoInterno) {
        BigDecimal importeTotal = new BigDecimal(ConstantesTESOFE.CERO);

        try {
            importeTotal
                    = recorrerInformacionModificacion(listaDatosTESOFE, archivoDAT, numeroDeArchivo, consecutivoInterno);
        } catch (SIATException e) {
            LOGGER.error("Error al calcular el importe total: " + e);
        }
        return importeTotal;
    }

    private BigDecimal escribirEnArchivoDAT(PrintWriter archivoDAT, int numeroDeArchivo,
            List<DatosEMP> listaDatosTESOFE, Integer numeroDevoluciones,
            Long consecutivoDAT) {

        BigDecimal importeTotal = new BigDecimal(ConstantesTESOFE.CERO);

        archivoDAT.print(generarEncabezadoParaDAT(String.format(FORMAT_INTEGER, numeroDeArchivo))
                + ConstantesTESOFE.SALTO_DE_LINEA);
        try {
            importeTotal = recorrerInformacion(listaDatosTESOFE, archivoDAT, numeroDeArchivo, consecutivoDAT);
        } catch (SIATException e) {
            LOGGER.error("Error al calcular el importe total: " + e);
        }
        archivoDAT.print(generarSumarioParaDAT(numeroDevoluciones, String.valueOf(importeTotal),
                String.format(FORMAT_INTEGER, numeroDeArchivo)));
        return importeTotal;
    }

    /**
     * Genera el detalle del contenido del archivo dat, ademas si no hay error
     * durante la generacion, marca el registro creao como pago enviado a
     * TESOFE.
     *
     * @param listaDatosTESOFE
     * @param archivoDAT
     * @param numeroDeArchivo
     *
     * @return
     */
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW,
            rollbackFor = SIATException.class)
    private BigDecimal recorrerInformacion(List<DatosEMP> listaDatosTESOFE, PrintWriter archivoDAT,
            int numeroDeArchivo, Long consecutivoDAT) throws SIATException {
        BigDecimal total = new BigDecimal(ConstantesTESOFE.CERO);
        Long consecutivoInterno = consecutivoDAT;

        for (DatosEMP dato : listaDatosTESOFE) {
            consecutivoInterno++;
            archivoDAT.print(generarContenidoParaDAT(dato, String.format(FORMAT_INTEGER, consecutivoInterno),
                    numeroDeArchivo, dato.getNumControl())
                    + ConstantesTESOFE.SALTO_DE_LINEA);
            dyctResolucionDAO.actualizarPagoEnviado(1, dato.getNumControl());
            total = total.add(new BigDecimal(dato.getImporte()));
        }
        return total;
    }

    /**
     * Genera el detalle del contenido del archivo dat, ademas si no hay error
     * durante la generacion, marca el registro creao como pago enviado a
     * TESOFE.
     *
     * @param listaDatosTESOFE
     * @param archivoDAT
     * @param numeroDeArchivo
     *
     * @return
     */
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW,
            rollbackFor = SIATException.class)
    private BigDecimal recorrerInformacionModificacion(List<DatosEMP> listaDatosTESOFE, PrintWriter archivoDAT,
            int numeroDeArchivo,
            Long consecutivoInterno) throws SIATException {
        BigDecimal total = new BigDecimal(ConstantesTESOFE.CERO);
        Long auxConsecutivoInterno = consecutivoInterno;
        if (consecutivoInterno == null) {
            auxConsecutivoInterno = 0L;
        }

        for (DatosEMP dato : listaDatosTESOFE) {
            auxConsecutivoInterno++;
            archivoDAT.print(generarContenidoParaDAT(dato, String.format(FORMAT_INTEGER, auxConsecutivoInterno),
                    numeroDeArchivo, dato.getNumControl())
                    + ConstantesTESOFE.SALTO_DE_LINEA);
            dyctResolucionDAO.actualizarPagoEnviado(1, dato.getNumControl());
            total = total.add(new BigDecimal(dato.getImporte()));
        }
        
        if (!this.esAutomaticaISR) {
            consecutivoModificacion = auxConsecutivoInterno;
        }
        
        return total;
    }

    /**
     * Genera el nombre de los archivos que se van a generar para ser
     * enviaConstantesTESOFE.DOS a TESOFE
     *
     * @return
     * @throws SIATException
     */
    private String generarNombreParaArchivos(String claves) throws SIATException {

        String nombreArchivo = "";

        if (claves.equals(ConstantesTESOFE.AGAFF)) {
            nombreArchivo = ConstantesTESOFE.CERO_SEIS;
        } else if (claves.equals(ConstantesTESOFE.AGGC)) {
            nombreArchivo = ConstantesTESOFE.CERO_SIETE;
        } else if (claves.equals(ConstantesTESOFE.AGH)) {
            nombreArchivo = ConstantesTESOFE.CERO_CINCO;
        } else if (claves.equals(ConstantesTESOFE.AGACE)) {
            nombreArchivo = ConstantesTESOFE.CERO_CUATRO;
        } else if (claves.equals(ConstantesTESOFE.PDA_NOMBRE)) {
            nombreArchivo = ConstantesTESOFE.CERO_TRES;
        }

        SimpleDateFormat formateador = new SimpleDateFormat(ConstantesTESOFE.ANIO_MES_DIA);
        nombreArchivo
                += "66E00" + String.format("%03d", datosTESOFEDAO.obtenerSecuencia()) + formateador.format(fechaPresentacion);
        return nombreArchivo;
    }

    /**
     * Genera el encabezado para el archivo .dat que se le enviará a la TESOFE.
     *
     * @return Regresa un string con los datos que contendrá el primer renglón.
     */
    private String generarEncabezadoParaDAT(String consecutivo) {
        String header = "";
        SimpleDateFormat formatoFecha1 = new SimpleDateFormat("dd");
        SimpleDateFormat formatoFecha2 = new SimpleDateFormat(ConstantesTESOFE.ANIO_MES_DIA);
        header
                = ConstantesTESOFE.TIPO_DE_REGISTRO + "0000001" + ConstantesTESOFE.CODIGO_OPERACION + ConstantesTESOFE.BANCO
                + "E2" + formatoFecha1.format(fechaPresentacion) + "211" + consecutivo
                + formatoFecha2.format(fechaPresentacion) + "01002" + ConstantesTESOFE.ESPACIOS_41
                + ConstantesTESOFE.ESPACIOS_345;
        return header;
    }

    /**
     * Genera el contenido de los datos que van a cruzar con TESOFE. En pocas
     * palabras, manda uno a uno los registros que se enviaran como solicitud de
     * devolucion por parte de los contribuyentes, registros que fueron
     * aprobados por DyC por un aprobador.
     *
     * @param datos
     * @param consecutivo
     * @return
     */
    private String generarContenidoParaDAT(DatosEMP datos, String consecutivo, int numeroDeArchivo,
            String numControl) throws SIATException {
        String beneficiario = datos.getBeneficiario();
        String claveRastreo = "";
        String contenido = "";
        SimpleDateFormat formatoFecha = new SimpleDateFormat(ConstantesTESOFE.ANIO_MES_DIA);

        beneficiario = beneficiario.trim();
        beneficiario = UtileriasTESOFE.validarYCorregirTexto(beneficiario);
        beneficiario
                = UtileriasTESOFE.agregarEspaciosALaDerecha(beneficiario, ConstantesTESOFE.LONGITUD_NOMBRE_RECEPTOR);

        if (beneficiario.length() > ConstantesTESOFE.LONGITUD_NOMBRE_RECEPTOR) {
            beneficiario = beneficiario.substring(0, (ConstantesTESOFE.LONGITUD_NOMBRE_RECEPTOR));
        }
        claveRastreo
                = generarClaveDeRastreo((Long.valueOf(consecutivo) - 1L), datos.getTipoTramite(), UtileriasTESOFE.agregarCeros(String.valueOf(numeroDeArchivo),
                                ConstantesTESOFE.DOS));
        contenido
                = "02" + UtileriasTESOFE.agregarCeros(consecutivo, ConstantesTESOFE.LONGITUD_MAXIMA_CONSECUTIVO) + "6001"
                + formatoFecha.format(fechaPago) + ConstantesTESOFE.BANCO
                + String.format("%03d", Integer.valueOf(datos.getBanco()))
                + UtileriasTESOFE.agregarCeros(UtileriasTESOFE.convertirImporteEnString(datos.getImporte()),
                        ConstantesTESOFE.LONGITUD_MAXIMA) + ConstantesTESOFE.ESPACIOS16 + "01"
                + formatoFecha.format(fechaPago) + "40" + ConstantesTESOFE.NO_CUENTA_ORDENANTE
                + ConstantesTESOFE.NOMBRE_ORDENANTE + ConstantesTESOFE.RFC_CURP_ORDENANTE
                + ConstantesTESOFE.TIPO_DE_CUENTA_RECEPTOR + "00" + datos.getClabe() + beneficiario
                + UtileriasTESOFE.agregarEspaciosALaDerecha(datos.getRfc(), ConstantesTESOFE.LONGITUD_MAXIMA_RFC)
                + ConstantesTESOFE.ESPACIOS40 + ConstantesTESOFE.ESPACIOS40 + ConstantesTESOFE.CEROS
                + ((datos.getClaveComputo()) == null ? ConstantesTESOFE.SIN_CLAVE_COMPUTO
                        : UtileriasTESOFE.agregarCeros(datos.getClaveComputo(), ConstantesTESOFE.LONGITUD_MAXIMA_CONSECUTIVO))
                + ConstantesTESOFE.LEYENDA + claveRastreo + ConstantesTESOFE.ESPACIOS4
                + ConstantesTESOFE.MOTIVO_DEVOLUCION + formatoFecha.format(fechaPresentacion) + "1"
                + ConstantesTESOFE.ESPACIOS11;
        contenido = contenido.replace("Ñ", "N");
        dyctResolucionDAO.actualizarClaveRastreo(datos.getNumControl(), claveRastreo);
        dyctResolucionDAO.actualizarFechasDeEnvioATESOFE(fechaPresentacion, fechaPago, numControl);

        return contenido;
    }

    /**
     * Del archivo dat, se genera un footer, el cual tiene un resumen de lo que
     * se envia en el archivo.
     *
     * @param consecutivo Es el número de renglón o de registro (es un simple
     * consecutivo):
     * @param importeTotal Es el importe total de t
     * @return
     */
    private String generarSumarioParaDAT(Integer consecutivo, String importeTotal, String numeroDeArchivo) {
        String sumario = "";
        SimpleDateFormat formatoFecha1 = new SimpleDateFormat("dd");
        sumario
                = "09" + UtileriasTESOFE.agregarCeros(Long.valueOf(consecutivo + ConstantesTESOFE.TOTAL_REGISTROS_MAS2).toString(),
                        ConstantesTESOFE.LONGITUD_MAXIMA_CONSECUTIVO) + "60"
                + formatoFecha1.format(fechaPresentacion) + "211" + numeroDeArchivo
                + UtileriasTESOFE.agregarCeros(String.valueOf(consecutivo),
                        ConstantesTESOFE.LONGITUD_MAXIMA_CONSECUTIVO)
                + UtileriasTESOFE.agregarCeros(UtileriasTESOFE.convertirImporteEnString(importeTotal),
                        ConstantesTESOFE.LONGITUD_MAXIMA_IMPORTE_SUMARIO)
                + ConstantesTESOFE.ESPACIOS40 + ConstantesTESOFE.ESPACIOS339;
        return sumario;
    }

    /**
     * Genera el campo de clave de rastreo el cual se
     *
     * @param secuencia
     * @param tipoTramite
     * @return
     */
    private String generarClaveDeRastreo(Long secuencia, int tipoTramite, String consecutivo) {
        String claveRastreo = "";
        Date fechaActual = new Date();
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(fechaPresentacion);
        SimpleDateFormat formatoFecha = new SimpleDateFormat(ConstantesTESOFE.ANIO);
        claveRastreo
                = "30" + formatoFecha.format(fechaActual) + "66211" + String.format("%03d", calendario.get(Calendar.DAY_OF_YEAR))
                + consecutivo + tipoTramite + String.format("%06d", secuencia);

        claveRastreo = claveRastreo + calcularDigitoVerificador(claveRastreo);

        return claveRastreo;
    }

    /**
     * Calcula el digito verificador el cual se hace de la siguiente manera: Se
     * calcula el cuadrado de cada digito que se ha generado de la clave de
     * rastreo, se suman y se divide el resultado entre 9. El residuo sera igual
     * al digito verificador. Esta clave de rastreo es un identificador ùnico
     * por registro y el SAT, nunca debe de repetirse.
     *
     * @param claveRastreo
     * @return
     */
    private String calcularDigitoVerificador(String claveRastreo) {
        int sumatoria = 0;
        String digitoVerificador = "";

        for (int i = 0; i < claveRastreo.length(); i++) {
            sumatoria
                    = (int) (sumatoria + Math.pow(Double.valueOf(claveRastreo.substring(i, (i + 1))), ConstantesTESOFE.DOS));
        }
        digitoVerificador = String.valueOf(sumatoria % ConstantesTESOFE.DIVIDENDO_DIGITO_VERIFICADOR);

        return digitoVerificador;
    }

    /**
     * Genera el archivo CSV que acompaña al .dat para el envio de datos a SIAFF
     *
     * @param nombreArchivo
     * @param importeTotal
     * @param rechazo
     * @throws SIATException
     */
    private void generarArchivoCSV(String nombreArchivo, BigDecimal importeTotal,
            Integer rechazo) throws SIATException {
        try {
            PrintWriter archivoCSV
                    = new PrintWriter(new File(ConstantesDyCURL.URL_GENERADOS_TESOFE + nombreArchivo + ConstantesTESOFE.CSV),
                            ConstantesTESOFE.CODIFICACION);
            archivoCSV.print(generarEncabezadoParaCSV(nombreArchivo, rechazo));
            archivoCSV.print(generarDetalleParaCSV(importeTotal));
            archivoCSV.close();
        } catch (Exception e) {
            throw new SIATException(e);
        }
    }

    /**
     * Genera el encabezado para los archivos CSV
     *
     * @param nombreArchivo
     * @param rechazo
     * @return
     */
    private String generarEncabezadoParaCSV(String nombreArchivo, Integer rechazo) {
        String encabezado = "";
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        encabezado
                = "H," + UtileriasTESOFE.agregarCeros(nombreArchivo.substring(ConstantesTESOFE.INICIO_CONSECUTIVO, ConstantesTESOFE.FIN_CONSECUTIVO),
                        ConstantesTESOFE.LONGITUD_MAXIMA_CONSECUTIVO) + "-"
                + nombreArchivo.substring(0, ConstantesTESOFE.FIN_CADENA)
                + ",66,E00,1,MXN,1,999,6270,22800100000100,16,,,,,," + formatoFecha.format(fechaPago) + ",,,,,,,,,,,,"
                + rechazo + ConstantesTESOFE.SALTO_DE_LINEA;
        return encabezado;
    }

    /**
     * Genera el detalle para el archivo CSV el cual comprende de solo un
     * renglon.
     *
     * @param importeTotal
     * @return
     */
    private String generarDetalleParaCSV(BigDecimal importeTotal) {
        Locale locale = new Locale("es", "MX");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);
        symbols.setDecimalSeparator('.');
        String pattern = "#0.00#";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);

        return "121,390001,66,E00," + decimalFormat.format(importeTotal);
    }

    /**
     * Calcula las fechas de presentacion y de pago sumando de la fecha actual
     * el numero de dias correspondiente.
     */
    private void calcularFechaDePresentacionyPago(Date fechaPagoDePantalla, Date fechaPresentacionDePantalla) {
        this.fechaPago = fechaPagoDePantalla;
        this.fechaPresentacion = fechaPresentacionDePantalla;
    }

    /**
     * Consulta la información que va a ser mostrada en el reporte de Excel, la
     * cual incluye los datos de diario que se generan a partir de la
     * retroalimentación de TESOFE.
     *
     * @param fecha Dia sobre el cual se realiza la consulta.
     * @return Lista con los datos del reporte.
     * @throws SIATException
     */
    @Override
    public byte[] listarDatosParaReporteDeRetroDeTESOFE(Date fecha, Boolean esAutoISR) throws SIATException {

        Class<?> clasePadre = null;
        Object valorObjeto = null;
        Field[] campos = null;
        Object origen = null;
        String nombre = "";
        Method getter = null;

        //Cambios para formato CSV
        StringBuilder resultado = new StringBuilder();

        byte[] b = null;
        List<DatosRetroTESOFE> lista = obtenerListaDeDatosDeRetro(fecha, esAutoISR);

        if (lista.size() > 0) {
            try {
                for (String titulo : UtileriasTESOFE.retornarTitulosDeExcel()) {
                    resultado.append(titulo).append(",");
                }

                resultado.append("\n");

                for (DatosRetroTESOFE objeto : lista) {

                    origen = objeto;
                    clasePadre = Class.forName(objeto.getClass().getName());
                    campos = clasePadre.getDeclaredFields();
                    for (Field field : campos) {
                        String fieldName = field.getName();
                        nombre = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length());
                        getter = clasePadre.getDeclaredMethod("get" + nombre);
                        valorObjeto = getter.invoke(origen, new Object[0]);
                        //Agregando el valor de columna
                        resultado.append(String.valueOf(valorObjeto)).append(",");
                    }
                    resultado.append("\n");
                }
                b = resultado.toString().getBytes("ISO-8859-1");
            } catch (Exception e) {
                LOGGER.error("Error al leer del objeto y depositarlo en csv: " + e, e);
            }
            return b;
        } else {
            return new byte[0];
        }
    }

    /**
     * Obtiene de base de datos la lista de datos de la retroalimentación de la
     * TESOFE.
     *
     * @param fecha Fecha de consulta.
     * @return la lista de datos de la retroalimentación de la TESOFE.
     * @throws SIATException
     */
    private List<DatosRetroTESOFE> obtenerListaDeDatosDeRetro(Date fecha, Boolean esAutoISR) throws SIATException {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return gestionPagoDAO.listarDatosParaReporteDeRetroDeTESOFE(formatoFecha.format(fecha),
                formatoFecha.format(calendar.getTime()), esAutoISR);
    }

    /**
     * Lista todos los archivos de pago que se han generado en una fecha
     *
     * @param fecha D&iacute;a a coonsultar los archivos de pago.
     * @return Lista de archivos de pago.
     * @throws SIATException
     */
    @Override
    public List<File> listarArchivosDePagoPorFecha(Date fecha, Boolean isAutoISR) throws SIATException {
        Date fechaInicio = null;
        Date fechaFin = null;
        Date fechaArchivo = null;
        SimpleDateFormat formatoSimple = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatoCompleto = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        File rutaBase = new File(rutaEnviarTESOFE);
        List<File> listaDeArchivos = new ArrayList<File>();

        try {
            fechaInicio = formatoCompleto.parse(formatoSimple.format(fecha) + " 00:00:00");
            fechaFin = formatoCompleto.parse(formatoSimple.format(fecha) + " 23:59:59");
        } catch (Exception e) {
            throw new SIATException(e);
        }
        for (File archivo : Arrays.asList(rutaBase.listFiles())) {
            fechaArchivo = new Date(archivo.lastModified());
            if (fechaArchivo.after(fechaInicio) && fechaArchivo.before(fechaFin)) {
                String cveArchivo = archivo.getName().substring(INICIO_NOMBRE_ISR, FIN_NOMBRE_ISR);
                if (isAutoISR) {
                    if (cveArchivo.equalsIgnoreCase(ConstantesTESOFE.CERO_TRES)) {
                        listaDeArchivos.add(archivo);
                    }
                } else {
                    if (!cveArchivo.equalsIgnoreCase(ConstantesTESOFE.CERO_TRES)) {
                        listaDeArchivos.add(archivo);
                    }
                }
            }
        }

        return listaDeArchivos;
    }

    private String verificarAdminOrigen(String claveRastreo) {
        String administracionOrigen = null;
        List<String> lstAdministraciones = new ArrayList<String>();
        lstAdministraciones.add(ConstantesTESOFE.AGGC);
        lstAdministraciones.add(ConstantesTESOFE.AGH);
        lstAdministraciones.add(ConstantesTESOFE.AGACE);
        lstAdministraciones.add(ConstantesTESOFE.AGAFF);
        
        for (String administracion : lstAdministraciones) {
            if (dyctResolucionDAO.verificarSiEsAGAFoAGGC(claveRastreo, administracion)) {
                administracionOrigen = administracion;
                break;
            }
        }

        return administracionOrigen;
    }

    private List<DatosEMP> generarClavesDeRastreoQuery(String[] arregloDeClavesDeRastreo) {

        List<DatosEMP> listaDatosTESOFE = new ArrayList<DatosEMP>();
        int particiones = arregloDeClavesDeRastreo.length / LIMITE_PARTICION;
        int sobrante = arregloDeClavesDeRastreo.length % LIMITE_PARTICION;
        particiones = (sobrante != 0) ? (particiones + 1) : (particiones + 0);

        for (int i = 0; i < particiones; i++) {
            List<DatosEMP> listaDatosTESOFETmp = new ArrayList<DatosEMP>();
            String clavesRastreo = "";

            int inicio = (i == 0) ? 0 : ((i * LIMITE_PARTICION));
            int fin = (((i + 1) * LIMITE_PARTICION));

            clavesRastreo = UtileriasTESOFE.generarClavesDeRastreo(Arrays.asList(Arrays.copyOfRange(arregloDeClavesDeRastreo, inicio, fin)));
            clavesRastreo = clavesRastreo.replaceAll(CONSTANTE_NULA, "");
            listaDatosTESOFETmp.addAll(datosTESOFEDAO.listarDatosTESOFEConClaveDeRastreo(clavesRastreo));
            listaDatosTESOFE.addAll(listaDatosTESOFETmp);
        }

        return listaDatosTESOFE;
    }
    
    public int getTamanoGrupo() {
        return tamanoGrupo;
    }
    
    public void setTamanoGrupo(int tamanoGrupo) {
        this.tamanoGrupo = tamanoGrupo;
    }
}
