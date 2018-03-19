package mx.gob.sat.siat.dyc.adminprocesos.web.controller.bean.support;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.adminprocesos.dto.Dias;
import mx.gob.sat.siat.dyc.adminprocesos.dto.Lugares;
import mx.gob.sat.siat.dyc.adminprocesos.dto.Mes;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.apache.commons.lang3.StringUtils;


public class DatosPantalla {
    public DatosPantalla() {
        super();
    }
    
    private static final int NUMEROHORAS      = ConstantesDyCNumerico.VALOR_24;
    private static final int NUMEROMAXDIGITOS = ConstantesDyCNumerico.VALOR_2;
    private static final int NUMEROMINUTOS    = ConstantesDyCNumerico.VALOR_60;
    private static final String CER0          = "0";
    
    /**
     * Crea una lista de valores con los cuales se podra contar el primer, segundo, tercer, etc dia de la semana por 
     * cada mes...
     */
    public List<Lugares> listarLugares() {
        List<Lugares> listaLugares = new ArrayList<Lugares>();
        listaLugares.add(new Lugares(ConstantesDyCNumerico.VALOR_1, "Primer"));
        listaLugares.add(new Lugares(ConstantesDyCNumerico.VALOR_2, "Segundo"));
        listaLugares.add(new Lugares(ConstantesDyCNumerico.VALOR_3, "Tercer"));
        listaLugares.add(new Lugares(ConstantesDyCNumerico.VALOR_4, "Cuarto"));
        return listaLugares;
    }
    
    /**
     * Crea una lista de los meses del año.
     */
    public List<Mes> listarMeses() {
        List<Mes> listaMeses = new ArrayList<Mes>();
        listaMeses.add(new Mes(ConstantesDyCNumerico.VALOR_1,  "Enero"));
        listaMeses.add(new Mes(ConstantesDyCNumerico.VALOR_2,  "Febrero"));
        listaMeses.add(new Mes(ConstantesDyCNumerico.VALOR_3,  "Marzo"));
        listaMeses.add(new Mes(ConstantesDyCNumerico.VALOR_4,  "Abril"));
        listaMeses.add(new Mes(ConstantesDyCNumerico.VALOR_5,  "Mayo"));
        listaMeses.add(new Mes(ConstantesDyCNumerico.VALOR_6,  "Junio"));
        listaMeses.add(new Mes(ConstantesDyCNumerico.VALOR_7,  "Julio"));
        listaMeses.add(new Mes(ConstantesDyCNumerico.VALOR_8,  "Agosto"));
        listaMeses.add(new Mes(ConstantesDyCNumerico.VALOR_9,  "Septiembre"));
        listaMeses.add(new Mes(ConstantesDyCNumerico.VALOR_10, "Octubre"));
        listaMeses.add(new Mes(ConstantesDyCNumerico.VALOR_11, "Noviembre"));
        listaMeses.add(new Mes(ConstantesDyCNumerico.VALOR_12, "Diciembre"));
        return listaMeses;
    }
    
    /**
     * Crea la lista de los dias con sus valores para Cron.
     */
    public List<Dias> listarDias() {
        List<Dias> listaDias = new ArrayList<Dias>();
        listaDias.add(new Dias(ConstantesDyCNumerico.VALOR_0, "MON", "Lunes"));
        listaDias.add(new Dias(ConstantesDyCNumerico.VALOR_1, "TUE", "Martes"));
        listaDias.add(new Dias(ConstantesDyCNumerico.VALOR_2, "WED", "Miércoles"));
        listaDias.add(new Dias(ConstantesDyCNumerico.VALOR_3, "THU", "Jueves"));
        listaDias.add(new Dias(ConstantesDyCNumerico.VALOR_4, "FRI", "Viernes"));
        listaDias.add(new Dias(ConstantesDyCNumerico.VALOR_5, "SAT", "Sábado"));
        listaDias.add(new Dias(ConstantesDyCNumerico.VALOR_6, "SUN", "Domingo"));
        return listaDias;
    }
    
    /**
     * Esta lista que se genera, contiene los datos de los campos que son requeridos y los que no al cargar la pantalla 
     * los cuales cambian segun lo que se selecciona en la pantalla.
     *
     * @return
     */
    public List<Boolean> listarBanderas() {
        List<Boolean>listaBanderas = new ArrayList<Boolean>();
        listaBanderas.add(Boolean.TRUE);
        listaBanderas.add(Boolean.FALSE);
        listaBanderas.add(Boolean.FALSE);
        listaBanderas.add(Boolean.FALSE);
        listaBanderas.add(Boolean.FALSE);
        listaBanderas.add(Boolean.FALSE);
        listaBanderas.add(Boolean.FALSE);
        return listaBanderas;
    }
    
    /**
     * Crea una lista de las horas que existen en el dia
     *
     * @return
     */
    public List<String> listarHoras() {
        List<String> listaHoras = new ArrayList<String>();
        for (int i = ConstantesDyCNumerico.VALOR_0; i<NUMEROHORAS; i++) {
            listaHoras.add(StringUtils.leftPad(String.valueOf(i), NUMEROMAXDIGITOS, CER0));
        }  
        return listaHoras;
    }
    
    /**
     * Crea una lista con los minutos que existen dentro de una hora.
     *
     * @return
     */
    public List<String> listarMinutos() {
        List<String> listaMinutos = new ArrayList<String>();
        for (int i = ConstantesDyCNumerico.VALOR_0; i<NUMEROMINUTOS; i++) {
            listaMinutos.add(StringUtils.leftPad(String.valueOf(i), NUMEROMAXDIGITOS, CER0));
        }
        return listaMinutos;
    }
    
    /**
     * Genera una lista la cual va a contener todos los datos de los campos numericos que se encuentran en la pantalla
     *
     * @return
     */
    public List<Double> listarCamposNumericos() {
        ArrayList<Double> camposNumericos = new ArrayList<Double>();
        camposNumericos.add(null);
        camposNumericos.add(null);
        camposNumericos.add(null);
        camposNumericos.add(null);
        camposNumericos.add(null);
        camposNumericos.add(null);
        camposNumericos.add(null);
        return camposNumericos;
    }
    
    /**
     * Genera una lista la cual va a contener todos los datos de los campos que refieren a los dias en la pantalla
     *
     * @return
     */
    public List<String> listarCamposDias() {
        ArrayList<String> dias = new ArrayList<String>();
        dias.add(null);
        dias.add(null);
        return dias;
    }
    
    /**
     *
     * @return
     */
    public List<String> listarCamposMeses() {
        ArrayList<String> meses = new ArrayList<String>();
        meses.add(null);
        meses.add(null);
        return meses;
    }
    
    public List<String> listarCamposHoras() {
        ArrayList<String> horas = new ArrayList<String>();
        horas.add(null);
        horas.add(null);
        horas.add(null);
        horas.add(null);
        horas.add(null);
        return horas;
    }
    
    /**
     * Genera una lista la cual va a contener todos los datos de los campos que refieren a los minutos en la pantalla
     *
     * @return
     */
    public List<String> listarCamposMinutos() {
        ArrayList<String> minutos = new ArrayList<String>();
        minutos.add(null);
        minutos.add(null);
        minutos.add(null);
        minutos.add(null);
        minutos.add(null);
        return minutos;
    }
    
    /**
     * Genera una lista la cual va a contener todos los datos de los campos que refieren a las posiciones en la pantalla
     *
     * @return
     */
    public List<String> listarCamposPosiciones() {
        List<String> posiciones = new ArrayList<String>();
        posiciones.add(null);
        posiciones.add(null);
        return posiciones;
    }
    
    /**
     * Genera una lista la cual va a contener todos los valores de los radios en la pantalla
     *
     * @return
     */
    public List<Double> listarCamposRadios() {
        ArrayList<Double> radios = new ArrayList<Double>();
        radios.add(null);
        radios.add(null);
        radios.add(null);
        radios.add(null);
        radios.add(null);
        return radios;
    }
}
