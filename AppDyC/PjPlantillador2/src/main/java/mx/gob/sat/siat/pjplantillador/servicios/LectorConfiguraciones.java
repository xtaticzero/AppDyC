/**
*  Todos los Derechos Reservados 2014 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.pjplantillador.servicios;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import mx.gob.sat.siat.dyc.generico.util.common.SIATException;
import mx.gob.sat.siat.pjplantillador.dto.Inforem;
import mx.gob.sat.siat.pjplantillador.dto.Items;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;


/**
 * Clase que implementa la lectura de las configuraciones.
 * @author Agustin Romero Mata / Softtek
 */
public class LectorConfiguraciones {
    private static final Logger LOG = Logger.getLogger(LectorConfiguraciones.class);
    private String conf;
    private List<IdentificadorDeTags> listaTags;
    private int numTags;
    public static final int HEADER = 1;
    public static final int DOCUMENT = 2;
    public static final int FOOTER = 3;
    public static final int POS1 = 0;
    public static final int POS2 = 2;
    public static final int POS3 = 4;
    public static final String VALORNOENCONTRADO = "Valor no encontrado en la configuracion ";
    public static final String INICIOTAG = "${";
    public static final String FINALVN = "]";
    public static final String TAGNULL = "$[NULO = ";
    public static final String TAGVACIO = "$[VACIO = ";
    public static final String VALORVACIO = "";
    public static final String VALORPIPE = "|";
    public static final String SIMBIGUAL = "=";
    public static final String DESCCOL = "Col";
    public static final String DESCRENG = "Reng";

    /**
     * Funcion de la lectura del archivo de configuraciones y
     * la desgloza para su procesamiento.
     * @param archConfiguracion
     * @param datos
     * @return
     */
    public List<Inforem> obtenerConfiguraciones(String archConfiguracion, List<Items> datos) throws SIATException {
        List<Inforem> retorno;

        retorno = obtenerConfiguraciones(archConfiguracion);

        for (Items info : datos) {
            Inforem temp = null;
            for (Inforem info2 : retorno) {
                if (info2.getNombre().equals(info.getId())) {
                    temp = info2;
                    break;
                }
            }

            if (temp == null) {
                LOG.error(VALORNOENCONTRADO + info.getId());
            } else if (temp.getContenidoList().size() >= 2) {
                Inforem temp3 = validacionVacioNulo(info);
                temp.setContenido(temp3.getUltimoContenido());
            } else {

                if (temp.getContenido(0).length() > 1) {

                    String temp2 = temp.getContenido(0).substring(0, 2);

                    if (temp2.equals(INICIOTAG)) {
                        temp.getContenidoList().remove(0);
                    }

                }

                Inforem temp3 = validacionVacioNulo(info);
                temp.setContenido(temp3.getUltimoContenido());


            }
        }

        this.revisarTagsFaltantes(retorno);

        return retorno;
    }

    /**
     *
     * @param info
     * @return
     */
    private Inforem validacionVacioNulo(Items info) {
        Inforem resultado = new Inforem();
        if (info.getValor() == null) {
            resultado.setContenido(TAGNULL + info.getId() + FINALVN);
        } else if (info.getValor().equals(VALORVACIO)) {
            resultado.setContenido(TAGVACIO + info.getId() + FINALVN);
        } else {
            resultado.setContenido(info.getValor());
        }
        return resultado;
    }

    /**
     * Funcion de la lectura del archivo de configuraciones y
     * la desgloza para su procesamiento.
     *
     * @param archConfiguracion
     * @param valor
     * @param numReng
     * @return
     */
    public List<Inforem> obtenerConfiguraciones(String archConfiguracion, String valor, int numReng) {
        List<Inforem> retorno = new ArrayList<Inforem>();
        Properties propiedades  = new Properties();
            
        try {
            LOG.debug("Funcion obtenerConfiguraciones");
            FileInputStream archivo = new FileInputStream(archConfiguracion);
            propiedades.load(archivo);
            
            this.obtenerListaDeTags(archConfiguracion);

            int cont = 1;
            for (IdentificadorDeTags info : listaTags) {
                String temp = propiedades.getProperty(info.getTag());
                Inforem item = getInformacion(info.getTag(), VALORVACIO, temp);

                retorno.add(inicContenido(item, valor, cont, numReng));
                cont++;
            }

            LOG.debug("Termina seleccion");

            if (numTags > retorno.size()) {
                LOG.debug("El numero de datos es menos a la configuracion");
                LOG.debug("numero de Tags = " + numTags);
                LOG.debug("numero de datos = " + retorno.size());
            }

        } catch (FileNotFoundException ex) {
            LOG.error(ex);
        } catch (IOException ex) {
            LOG.error(ex);
        }

        this.revisarTagsFaltantes(retorno);

        return retorno;
    }

    /**
     *
     * @param item
     * @param valor
     * @param cont
     * @param numReng
     * @return
     */
    private Inforem inicContenido(Inforem item, String valor, int cont, int numReng) {
        Inforem resultado = null;

        if (item.getTipo() == 'I') {
            item.setContenido(valor + cont);
        } else if (item.getTipo() == 'T') {
            for (int ii = 1; ii <= numReng; ii++) {
                item.setContenido(valor + DESCCOL + item.getColumna() + DESCRENG + ii);
            }
        }

        return resultado;
    }

    /**
     *
     * @param archConfiguracion
     * @return
     */
    private List<Inforem> obtenerConfiguraciones(String archConfiguracion) {
        List<Inforem> retorno = new ArrayList<Inforem>();
        Configuration config = null;
        Properties propiedades  = new Properties();
        try {
            FileInputStream archivo = new FileInputStream(archConfiguracion);
            propiedades.load(archivo);
        } catch (FileNotFoundException ex) {
            LOG.error(ex);
        } catch (IOException ex) {
            LOG.error(ex);
        }
        this.obtenerListaDeTags(archConfiguracion);

        for (IdentificadorDeTags info : listaTags) {
            String temp = propiedades.getProperty(info.getTag());
            Inforem item = getInformacion(info.getTag(), info.getNumTag(), temp);
            retorno.add(item);
        }
        return retorno;
    }

    /**
     * Funcion que obtiene los tokens para el proceso de configuracion de etiquedas.
     * @param nombre
     * @param contenido
     * @param configuracion
     * @return
     */
    private Inforem getInformacion(String nombre, String contenido, String configuracion) {

        Inforem retorno = new Inforem();
        this.conf = configuracion;
        retorno.setNombre(nombre);

        retorno.setTag(getItemConfiguracion());
        retorno.setLstarea(getAreas(getItemConfiguracion()));
        retorno.setTamano(getConvInt(getItemConfiguracion()));
        retorno.setTipo(getItemConfiguracion().charAt(0));
        retorno.setIdTabla(getItemConfiguracion());
        retorno.setColumna(getConvInt(getItemConfiguracion()));
        retorno.setImagen(conf);

        if (!contenido.equals(VALORVACIO)) {
            retorno.setContenido(contenido);
        }

        return retorno;
    }

    /**
     * Funcion que obtiene el item de la configuracion.
     * @return
     */
    private String getItemConfiguracion() {
        String retorno;
        int position;

        position = conf.indexOf(VALORPIPE);
        retorno = conf.substring(0, position);
        conf = conf.substring(position + 1);

        return retorno;
    }

    /**
     * Funcion que obtiene las areas que abarca el token.
     * @param tmp
     * @return
     */
    private List<Integer> getAreas(String tmp) {

        List<Integer> retorno = new ArrayList<Integer>();
        Character tmpChar = Character.valueOf(tmp.charAt(POS1));
        if (tmpChar.compareTo('H') == 0) {
            retorno.add(Integer.valueOf(HEADER));
        }
        tmpChar = Character.valueOf(tmp.charAt(POS2));
        if (tmpChar.compareTo('D') == 0) {
            retorno.add(Integer.valueOf(DOCUMENT));
        }
        tmpChar = Character.valueOf(tmp.charAt(POS3));
        if (tmpChar.compareTo('F') == 0) {
            retorno.add(Integer.valueOf(FOOTER));
        }

        return retorno;
    }

    /**
     * Funcion que convierte un string a un entero.
     * @param tmp
     * @return
     */
    private int getConvInt(String tmp) {
        return Integer.valueOf(tmp).intValue();
    }

    /**
     *
     * @param archConfiguracion
     * @return
     */
    public void obtenerListaDeTags(String archConfiguracion) {
        int contador = 0;
        try {
            FileReader fr = new FileReader(archConfiguracion);
            BufferedReader entrada = new BufferedReader(fr);
            listaTags = new ArrayList<IdentificadorDeTags>();
            String cadena = entrada.readLine();
            while (cadena != null) {
                String tagnombre = obtenerTag(cadena);
                String id = obtenerId(cadena);
                if (tagnombre == null) {
                    LOG.warn("El archivo de configuracion contiene informacion en formato invalido.");
                } else {
                    IdentificadorDeTags tag = new IdentificadorDeTags(0, tagnombre, id);
                    listaTags.add(tag);
                    contador++;
                }
                cadena = entrada.readLine();
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        numTags = contador;
    }

    /**
     *
     * @param lista
     */
    private void revisarTagsFaltantes(List<Inforem> lista) {
        for (Inforem item : lista) {
            for (IdentificadorDeTags itemBusq : listaTags) {
                if (itemBusq.getTag().equals(item.getNombre())) {
                    itemBusq.setBandera(1);
                    break;
                }
            }
        }
    }

    /**
     *
     * @param cadena
     * @return
     */
    private String obtenerTag(String cadena) {
        int posicion = cadena.indexOf(SIMBIGUAL);

        if (posicion > 0) {
            return cadena.substring(0, posicion);
        }

        return null;
    }

    /**
     *
     * @param cadena
     * @return
     */
    private String obtenerId(String cadena) {
        int posicion = cadena.indexOf(SIMBIGUAL);

        if (posicion > 0) {
            String tmp = cadena.substring(posicion + 1);
            posicion = tmp.indexOf(VALORPIPE);
            if (posicion > 0) {
                return tmp.substring(0, posicion);
            }
        }

        return null;
    }

    /**
     *
     * @return
     */
    public List<IdentificadorDeTags> getListaTags() {
        return listaTags;
    }

    /**
     *
     * @return
     */
    public int getNumTags() {
        return numTags;
    }
}
