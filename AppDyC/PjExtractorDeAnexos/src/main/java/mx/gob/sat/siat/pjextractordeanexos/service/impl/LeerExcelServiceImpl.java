package mx.gob.sat.siat.pjextractordeanexos.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.math.BigDecimal;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import jxl.Cell;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;

import jxl.read.biff.BiffException;

import mx.gob.sat.siat.dyc.dao.anexo.DdCdmAnexo701DAO;
import mx.gob.sat.siat.dyc.dao.ddcdm.DdCdmSda1OEn1DAO;
import mx.gob.sat.siat.dyc.dao.ddcdm.DdCdmSda1Oen2DAO;

import mx.gob.sat.siat.dyc.dao.anexo.DycaSolAnexoTramDAO;
import mx.gob.sat.siat.dyc.dao.anexo.DyctAnexoReqDAO;
import mx.gob.sat.siat.dyc.dao.documento.DyccDatosAnexoDAO;
import mx.gob.sat.siat.dyc.domain.ddcdm.DdCdmAnexo701DTO;
import mx.gob.sat.siat.dyc.domain.ddcdm.DdCdmSda1OEn1DTO;
import mx.gob.sat.siat.dyc.domain.ddcdm.DdCdmSda1OEn2DTO;
import mx.gob.sat.siat.dyc.domain.anexo.DycaSolAnexoTramDTO;
import mx.gob.sat.siat.dyc.domain.anexo.DyccDatosAnexoDTO;
import mx.gob.sat.siat.dyc.domain.anexo.DyctAnexoReqDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.pjextractordeanexos.service.LeerExcelService;
import mx.gob.sat.siat.pjextractordeanexos.vo.DocumentoVO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * <html>
 * <body>
 * Se utiliza este servicio para leer de los registros que son importados de la base de datos los exceles que han sido
 * adjuntados por el contribuyente, los cuales se depositaran con este en la base de datos en las tablas:<br />
 *  - DD_CDM_ANEXO701<br />
 *  - DD_CDM_SDA1OEN1<br />
 *  - DD_CDM_SDA1OEN2<br />
 * </body>
 * </html>
 *
 *  @author Jesus Alfredo Hernandez Orozco.
 */
@Service(value = "leerExcelService")
public class LeerExcelServiceImpl implements LeerExcelService {
    public LeerExcelServiceImpl() {
        super();
    }
    
    @Autowired
    private DdCdmAnexo701DAO ddCdmAnexo701DAO;
    
    @Autowired
    private DdCdmSda1OEn1DAO ddCdmSda1OEn1DAO;
    
    @Autowired
    private DdCdmSda1Oen2DAO ddCdmSda1Oen2DAO;
    
    @Autowired
    private DyccDatosAnexoDAO dyccDatosAnexoDAO;
    
    @Autowired
    private DycaSolAnexoTramDAO dycaSolAnexoTramDAO;
    
    @Autowired
    private DyctAnexoReqDAO dyctAnexoReqDAO;
    
    private static final int BUFFER_LECTURA = 1024;
    private static final int IDANEXO7       = 4;
    private static final int IDANEXO11      = 6;
    private static final int IDANEXO11A     = 7;
    private static final int LONGITUD_CLABE = 18;
    private static final int LONGITUD_FECHA = 10;
    private static final int LONGITUD_RFC   = 13;
    private static final int LONGITUD_TASA  = 9;
    private static final int POSICION       = 6;
    
    private static final long PARAMETRO_TIEMPO = 86400000;
    
    private static final String COMA     = ",";
    private static final String DIAGONAL = "/";
    private static final Logger LOGGER = Logger.getLogger(LeerExcelServiceImpl.class);
    
    /**
     * <html>
     * <body>
     * Se le pasa un objeto que contiene donde se encuentra el excel (extraido de la tabla de Dyca_SolAnexoTram) y se 
     * utiliza para extraer la informacion que se guardará en la base de datos.
     * </body>
     * </html>
     *
     * @param objeto
     * @throws SIATException
     */
    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW,
                    rollbackFor = { SIATException.class })
    public void importarInformacionDeExcel(DycaSolAnexoTramDTO objeto) throws SIATException {
        DocumentoVO documento = new DocumentoVO();
        documento.setNombreArchivo(objeto.getNombreArchivo());
        documento.setUrl(objeto.getUrl());
        documento.setNunControl(objeto.getDycpSolicitudDTO().getNumControl());
        identificarAnexo(documento);
        dycaSolAnexoTramDAO.actualizarAProcesado(objeto.getDycpSolicitudDTO().getNumControl());
    }
    
    /**
     * <html>
     * <body>
     * Se le pasa un objeto que contiene donde se encuentra el excel (extraido de la tabla de Dyct_AnexoReq) y se 
     * utiliza para extraer la informacion que se guardará en la base de datos.
     * </body>
     * </html>
     *
     * @param objeto
     * @throws SIATException
     */
    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW,
                    rollbackFor = { SIATException.class })
    public void importarInformacionDeExcel(DyctAnexoReqDTO objeto) throws SIATException {
        DocumentoVO documento = new DocumentoVO();
        documento.setNombreArchivo(objeto.getNombreArchivo());
        documento.setUrl(objeto.getUrl());
        String [] textoTemporal = objeto.getUrl().split(DIAGONAL);
        documento.setNunControl(textoTemporal[POSICION]);
        identificarAnexo(documento);
        dyctAnexoReqDAO.actualizarAProcesado(objeto.getDyctReqInfoDTO().getNumControlDoc());
    }
    
    /**
     * <html>
     * <body>
     * Identifica el tipo de anexo mediante la lectura del encabezado del archivo excel. Si este coincide con el anexo 7, 
     * anexo 11 o anexo 11A se procede a extraer su información y a guardarla en la base de datos.
     * </body>
     * </html>
     *
     */
    private void identificarAnexo(DocumentoVO documento) throws SIATException {
        Cell celda = null;
        Sheet hojaDeExcel = null;
        try {
            hojaDeExcel = getHojaDeExcel(documento.getUrl().concat(DIAGONAL).concat(documento.getNombreArchivo()));

            //Busca el encabezado para anexo 7:
            celda = hojaDeExcel.getCell(0, ConstantesDyCNumerico.VALOR_4);
            if (celda.getContents().contains("ANEXO 7")) {
                DdCdmAnexo701DTO objeto = new DdCdmAnexo701DTO();
                objeto=(DdCdmAnexo701DTO)retornarInformacionDelExcel(objeto, hojaDeExcel, IDANEXO7, false, 0);
                objeto.setNumControl(documento.getNunControl());
                ddCdmAnexo701DAO.insertar(objeto);
                
            }

            //Busca el encabezado para anexo 11:
            celda = hojaDeExcel.getCell(ConstantesDyCNumerico.VALOR_3, ConstantesDyCNumerico.VALOR_3);
            if (celda.getContents().contains("ANEXO 11")) {
                DdCdmSda1OEn1DTO objeto = new DdCdmSda1OEn1DTO();
                objeto=(DdCdmSda1OEn1DTO)retornarInformacionDelExcel(objeto, hojaDeExcel, IDANEXO11, false, 0);
                objeto.setNumControl(documento.getNunControl());
                ddCdmSda1OEn1DAO.insertar(objeto);
            }

            //Busca el encabezado para anexo 11A:
            celda = hojaDeExcel.getCell(0, ConstantesDyCNumerico.VALOR_4);
            if (celda.getContents().contains("ANEXO 11 A")) {
                DdCdmSda1OEn2DTO objeto = new DdCdmSda1OEn2DTO();
                for (int i = 0; i < 18; i++) {
                    objeto=(DdCdmSda1OEn2DTO)retornarInformacionDelExcel(objeto, hojaDeExcel, IDANEXO11A, Boolean.TRUE, i);
                    objeto.setNumControl(documento.getNunControl());
                    objeto.setCIDConsc(String.valueOf(i));
                    if(objeto.getNCBLAANB1()!=null) {
                        ddCdmSda1Oen2DAO.insertar(objeto);   
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("identificarAnexo(); "+e);
            throw new SIATException(e);
        }
    }
    
    /**
     * <html>
     * <body>
     * Obtiene la primera hoja del archivo de Excel.
     * </body>
     * </html>
     *  
     * @param rutaDeArchivo
     * @return Sheet del Excel a leer
     * @throws BiffException
     * @throws IOException
     */
    private Sheet getHojaDeExcel(String rutaDeArchivo) throws BiffException, IOException {
        
        byte[]      bytes              = null;
        InputStream informacionEnBytes = null;
        Sheet       hojaDeExcel        = null;
        Workbook    libroDeExcel       = null;
        
        File archivo = new File(rutaDeArchivo);
        FileInputStream fis = new FileInputStream(archivo);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        
        byte[] buf = new byte[BUFFER_LECTURA];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum); 
            }
        } catch (IOException e) {
            LOGGER.info("Fallo al leer el archivo: "+e);
        }
        bytes              = bos.toByteArray();
        informacionEnBytes = new ByteArrayInputStream(bytes);
        libroDeExcel       = Workbook.getWorkbook(informacionEnBytes);
        hojaDeExcel        = libroDeExcel.getSheet(0);
        return hojaDeExcel;
    }

    /**
     * <html>
     * <body>
     * Obtiene el RFC, si el valor del mismo es mayor a 13 posiciones se trunca hasta los 13 caracteres
     * </body>
     * </html>
     *
     * @param cell
     * @return
     */
    private String getRFC(Cell cell) {
        String contenidoCelda = cell.getContents();
        if (contenidoCelda.length() > LONGITUD_RFC) {
            contenidoCelda = contenidoCelda.substring(0, LONGITUD_RFC);
            return contenidoCelda;
        } else {
            return contenidoCelda;
        }
    }
    
    /**
     * <html>
     * <body>
     * Obtiene la fecha de la celda.
     * </body>
     * </html>
     *
     * @param cell
     * @return
     */
    private Date getFecha(Cell cell) {
        Date fecha = ((DateCell)cell).getDate();
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(fecha.getTime() + PARAMETRO_TIEMPO);
        fecha = c.getTime();
        SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
        String fechaString = sd.format(fecha);
        if (fechaString.length() == LONGITUD_FECHA) {
            return fecha;
        } else {
            return null;
        }
    }
    
    /**
     * <html>
     * <body>
     * Obtiene el valor alfanumérico de la celda, validando también la longitud del mismo. Si la longitud de dicho 
     * campo es mayor a la establecida, este se trunca a la longitud máxima.
     * </body>
     * </html>
     *
     * @param cell
     * @return
     */
    private String getValorCampoAlfanum(Cell cell, int longitud) {
        String contenidoCelda = cell.getContents();
        if (contenidoCelda.length() > longitud) {
            contenidoCelda = contenidoCelda.substring(0, longitud);
            return contenidoCelda;
        } else {
            return contenidoCelda;
        }
    }

    /**
     * <html>
     * <body>
     * Obtiene el valor de la CLABE de la celda siempre y cuando este tenga una longitud de 18 caracteres.
     * </body>
     * </html>
     *
     * @param cell
     * @return
     */
    private String getValorCLABE(Cell cell) {
        String contenidoCelda = cell.getContents();
        if (contenidoCelda.length() == LONGITUD_CLABE) {
            return contenidoCelda;
        } else {
            return null;
        }
    }

    /**
     * <html>
     * <body>
     * Obtiene el valor numeroco de un campo, quitandole las comas que este posee (si es que las tiene para que sea 
     * compatible con el formato bigdeciaml.
     * </body>
     * </html>
     *
     * @param cell
     * @param longitud
     * @param negativo
     * @return
     */
    private BigDecimal getValorNumerico(Cell cell, int longitud, boolean negativo) {
        BigDecimal valor = isNumerico(cell);
        BigDecimal resultado = null;

        if (valor != null) {
            DecimalFormat myFormatter = new DecimalFormat("0.00");
            String output = myFormatter.format(valor);
            output=output.replaceAll("[,]+", ".");
            if (!output.equals("0.00")) {
                resultado = new BigDecimal(output);

                if (output.startsWith("-")) {
                    output = output.substring(1, output.length());
                }

                if (output.contains(COMA)) {
                    String datos[] = output.split(COMA);
                    for (int i = 0; i < datos.length; i++) {
                        output += datos[i];
                    }
                }

                if ((negativo == false) && (resultado.compareTo(BigDecimal.ZERO)<0)) {
                    return BigDecimal.ZERO;
                }

                if ((output.length() <= longitud)) {
                    return resultado;
                } else {
                    return BigDecimal.ZERO;
                }

            } else {
                resultado = BigDecimal.ZERO;
            }
        }
        return valor;
    }

    private BigDecimal getTasa(Cell cell, boolean negativo) {
        BigDecimal valor = isNumerico(cell);
        BigDecimal resultado = BigDecimal.ZERO;

        if (valor != null) {
            DecimalFormat myFormatter = new DecimalFormat("0.0000");
            String output = myFormatter.format(valor);
            output = output.replaceAll("[,]+", ".");

            if (!output.equals("0.0000")) {
                resultado = new BigDecimal(output);

                if (output.startsWith("-")) {
                    output = output.substring(1, output.length());
                }

                if ((negativo == false) && (resultado.compareTo(BigDecimal.ZERO) < 0)) {
                    return BigDecimal.ZERO;
                }

                if ((output.length() <= LONGITUD_TASA)) {
                    return resultado;
                } else {
                    return BigDecimal.ZERO;
                }

            } else {
                resultado = BigDecimal.ZERO;
            }
        }
        return valor;
    }
    
    /**
     * <html>
     * <body>
     * Verifica si el valor de una celda es numérico o no.
     * </body>
     * </html>
     *
     * @param cell
     * @return
     */
    private BigDecimal isNumerico(Cell cell) {
        BigDecimal resultado;
        try {
            resultado = new BigDecimal(((jxl.NumberCell)cell).getValue());
            return resultado;
        } catch (Exception nfe) {

            String datosTemp = cell.getContents();
            try {
                resultado = new BigDecimal(datosTemp);
                return resultado;
            } catch (Exception e) {
                return BigDecimal.ZERO;
            }
        }
    }
    
    private Object retornarInformacionDelExcel(final Object clase, Sheet hojaDeExcel, int idAnexo, boolean bandera, int noIndice) throws SIATException {
        
        int i=0;
        
        if(!bandera) {
            i=1;
        } else {
            i=ConstantesDyCNumerico.VALOR_2;
        }
        Field campo = null;
        Field[] listaDeCampos  = null;
        
        String nombreDelCampo  = "";
        String nombreDelCampoConMayuscula = "";
        String nombreDeLaClase = clase.getClass().getName();
        
        Class<?> clasePadre;
        Method metodo        = null;
        Object claseTemporal = null;
        List<DyccDatosAnexoDTO> lista = null;
        DyccDatosAnexoDTO objeto = null;
        try {
            clasePadre    = Class.forName(nombreDeLaClase);
            listaDeCampos = clasePadre.getDeclaredFields();
            lista = dyccDatosAnexoDAO.consultarPorIDAnexo(idAnexo);
            Iterator it = lista.iterator();
            
            while(it.hasNext()) {
                objeto = (DyccDatosAnexoDTO)it.next();
                
                //SE OBTIENE EL METODO QUE SE VA A UTILIZAR PARA LLENAR LA INFORMACIÓN CON LA CELDA DE EXCEL:
                campo = listaDeCampos[i];
                nombreDelCampo = campo.getName();
                nombreDelCampoConMayuscula = nombreDelCampo.substring(0, 1).toUpperCase() + nombreDelCampo.substring(1, nombreDelCampo.length());                
                
                metodo = clasePadre.getDeclaredMethod(("set" + nombreDelCampoConMayuscula),retornarTipoDeDatoValido(objeto.getTiposDeDato()));
                claseTemporal=insertarInformacionEnElObjeto(objeto, objeto.getTiposDeDato(), hojaDeExcel, metodo, clase, noIndice);
                i++;
            }
        } catch (Exception e) {
            throw new SIATException(e);
        }
        return claseTemporal;
    }
    
    /**
     * Lee de la base de datos si la información registrada que se va a leer proviene de una sola celda o de un conjunto,
     * si proviene de un conjunto de celdas, se verá si este conjunto de datos se repite en renglones o columnas y lo 
     * depsosita en mas de un atributo del objeto
     *
     * @param objeto
     * @param tiposDeDato
     * @param hojaDeExcel
     * @param metodo
     * @param clase
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private Object insertarInformacionEnElObjeto(DyccDatosAnexoDTO objeto, String tiposDeDato, Sheet hojaDeExcel, Method metodo, Object clase, int noIndice) throws IllegalAccessException, InvocationTargetException {
        Cell celda = null;
        Integer y = ConstantesDyCNumerico.VALOR_17;
        Integer coordenadaY  = 0;
        Object claseTemporal = null;
        
        //BUSCA SI LA INFORMACIÓN PROVIENE SÓLO DE UNA CELDA.
        if (objeto.getNoRepeticiones().compareTo(1)==0) {
            celda=hojaDeExcel.getCell(Integer.valueOf(objeto.getCoordenadasX()),Integer.valueOf(objeto.getCoordenadasY()));
            claseTemporal = ejecutarMetodo(tiposDeDato, metodo, clase, celda, objeto.getLontigud());
            
        } else {        
            coordenadaY=Integer.valueOf(y+noIndice);
            celda=hojaDeExcel.getCell(Integer.valueOf(objeto.getCoordenadasX()),coordenadaY);
            claseTemporal = ejecutarMetodo(tiposDeDato, metodo, clase, celda, objeto.getLontigud());
        }
        return claseTemporal;
    }
    
    /**
     * <html>
     * <body>
     * Se leen diferentes tipos de datos, los cuales son los siguientes: <br /><br />
     *  1 - RFC.<br />
     *  2 - Fecha.<br />
     *  3 - Numérico.<br />
     *  4 - Alfanumérico.<br />
     *  5 - Tasa.<br />
     *  6 - CLABE.<br />
     *  <br />
     *  Cada uno de estos datos son asignados a una clase, en el caso de los tipos de dato 1, 4 y 6 se les asignara la 
     *  clase String, para el tipo 2 se utilizará Date y para los tipos de datos 3 y 5 se utilizará BigDecimal.<br />
     *  Esto es para relacionar los tipos de dato registrados en base de datos, con los que se asignan en Java.
     *  </body>
     *  </html>
     *
     * @param tiposDeDato
     * @return
     */
    private Class retornarTipoDeDatoValido(String tiposDeDato) {
        if(tiposDeDato.equals("1") || tiposDeDato.equals("4") || tiposDeDato.equals("6")) {
            return String.class;
            
        } else if(tiposDeDato.equals("2")) {
            return Date.class;
            
        } else if (tiposDeDato.equals("3") || tiposDeDato.equals("5")) {
            return BigDecimal.class;
        } else {
            return String.class;
        }
    }
    
    /**
     * Ejecuta un metodo de tipo set de cualquiera de las tres clases: DdCdmAnexo701DTO, DdCdmSda1OEn1DTO o 
     * DdCdmSda1OEn2DTO el cual se utiliza para llenar los datos que vienen dentro del excel en el objeto.
     * 
     * Se leen diferentes tipos de datos, los cuales son los siguientes: <br />
     *  1 - RFC.<br />
     *  2 - Fecha.<br />
     *  3 - Numérico.<br />
     *  4 - Alfanumérico.<br />
     *  5 - Tasa.<br />
     *  6 - CLABE.<br />
     *
     * @param tiposDeDato
     * @param metodo
     * @param clase
     * @param celda
     * @param longitud
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private Object ejecutarMetodo(String tiposDeDato, Method metodo, Object clase, Cell celda, int longitud) throws IllegalAccessException, InvocationTargetException {
        Object claseTemporal=null;
        if(tiposDeDato.equals("1")) {
            metodo.invoke(clase, getRFC(celda));
            
        } else if(tiposDeDato.equals("2")) {
            metodo.invoke(clase, getFecha(celda));
            
        } else if(tiposDeDato.equals("3")) {
            metodo.invoke(clase, getValorNumerico(celda, longitud, Boolean.TRUE));
            
        } else if(tiposDeDato.equals("4")) {
            metodo.invoke(clase, getValorCampoAlfanum(celda, longitud));
            
        } else if(tiposDeDato.equals("5")) {
            metodo.invoke(clase, getTasa(celda, Boolean.TRUE));
            
        } else if(tiposDeDato.equals("6")) {
            metodo.invoke(clase, getValorCLABE(celda));
        }
        claseTemporal=clase;
        return claseTemporal;
    }
}