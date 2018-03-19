package mx.gob.sat.siat.dyc.adminprocesos.web.controller.bean.bussiness;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import mx.gob.sat.siat.dyc.adminprocesos.util.constante.Constantes;
import mx.gob.sat.siat.dyc.adminprocesos.util.constante.DiccionarioCron;


public class Validador {
    
    private List<String> listaMesesLetras = null;
    private List<String> listaMesesNumero = null;
    
    private static final String INTERROGACION_CORCHETES="]?[";
    
    
    public Validador() {
        listaMesesNumero = new ArrayList<String>();
        listaMesesNumero.add("1");
        listaMesesNumero.add("2");
        listaMesesNumero.add("3");
        listaMesesNumero.add("4");
        listaMesesNumero.add("5");
        listaMesesNumero.add("6");
        listaMesesNumero.add("7");
        listaMesesNumero.add("8");
        listaMesesNumero.add("9");
        listaMesesNumero.add("10");
        listaMesesNumero.add("11");
        listaMesesNumero.add("12");
        
        listaMesesLetras = new ArrayList<String>();
        listaMesesLetras.add(DiccionarioCron.ENERO);
        listaMesesLetras.add(DiccionarioCron.FEBRERO);
        listaMesesLetras.add(DiccionarioCron.MARZO);
        listaMesesLetras.add(DiccionarioCron.ABRIL);
        listaMesesLetras.add(DiccionarioCron.MAYO);
        listaMesesLetras.add(DiccionarioCron.JUNIO);
        listaMesesLetras.add(DiccionarioCron.JULIO);
        listaMesesLetras.add(DiccionarioCron.AGOSTO);
        listaMesesLetras.add(DiccionarioCron.SEPTIEMBRE);
        listaMesesLetras.add(DiccionarioCron.OCTUBRE);
        listaMesesLetras.add(DiccionarioCron.NOVIEMBRE);
        listaMesesLetras.add(DiccionarioCron.DICIEMBRE);
    }
    
    /**
     * Valida los segundos, minutos y las horas, y el caso que solo sean numeros, vengan con comas, diagonales o 
     * guiones medios.
     *
     * @param segMinHoras el campo que contiene los segundos, monutos u horas  puede que tenga los siguientes signos: , / -
     * @param inicio es el valor minimo que tiene que tener un minuto, segundo u hora y este valor se utiliza en una expresion
     *               regular que realiza la validacion del contenido del campo.
     * @param fin1
     * @param fin2
     * @return verdadero si la validacion fue exitosa, falso en caso contrario.
     */
    public boolean validarSegundosMinutosHoras(String segMinHoras, String inicio, String fin1, String fin2) {
        boolean bandera = false;
        
        if(segMinHoras.matches("^((["+inicio+"-"+fin1+INTERROGACION_CORCHETES+inicio+"-"+fin2+"])|0|\\*|((["+inicio+"-"+fin1+INTERROGACION_CORCHETES+inicio+"-"+fin2+"])/(["+inicio+"-"+fin1+INTERROGACION_CORCHETES+inicio+"-"+fin2+"])))$")) {
            bandera = Boolean.TRUE;
            
        } else if (segMinHoras.matches("^((["+inicio+"-"+fin1+INTERROGACION_CORCHETES+inicio+"-"+fin2+"])-(["+inicio+"-"+fin1+INTERROGACION_CORCHETES+inicio+"-"+fin2+"]))$")) {
            StringTokenizer tokens = new StringTokenizer(segMinHoras, DiccionarioCron.GUIONMEDIO);
            
            if(Integer.valueOf(tokens.nextToken())<=Integer.valueOf(tokens.nextToken())) {
                bandera = Boolean.TRUE;
            }
            
        } else if (segMinHoras.matches("^(["+inicio+"-"+fin1+"]?[0-"+fin2+"])(,(["+inicio+"-"+fin1+INTERROGACION_CORCHETES+inicio+"-"+fin2+"]))*$")) {
            bandera = Boolean.TRUE;
            
        } else if (segMinHoras.matches("^(["+inicio+"-"+fin1+"]?[0-"+fin2+"])$") || segMinHoras.matches("^(([0-2]?[0-3])|([0-1]?[0-9]))$")) {
            bandera = Boolean.TRUE;
        } else if (segMinHoras.matches("^(0/)(([0-1]?[0-9])|("+fin1+"?[0-"+fin2+"]))$")) {
            bandera = Boolean.TRUE;
        }
        return bandera;
    }
    
    /**
     * valida los dias del mes, en el caso que solo sean numeros, vengan con comas, diagonales o guiones medios.
     *
     * @param diasDelMes es un valor entero que debe de ir del numero 1 al 31, puede que tenga los siguientes signos: , / -
     * @return verdadero si la validacion fue exitosa, falso en caso contrario.
     */
    public boolean validarDiaDelMes(String diasDelMes) {
        boolean bandera = Boolean.FALSE;
        
        if (diasDelMes.matches(Constantes.DIAMESNUMERO)) {
            bandera = Boolean.TRUE;
            
        } else if (diasDelMes.matches(Constantes.DIAMESCOMAS)) {
            bandera = Boolean.TRUE;
            
        } else if (diasDelMes.matches(Constantes.DIAMESGUIONMEDIO)) {
            StringTokenizer tokens = new StringTokenizer(diasDelMes, DiccionarioCron.GUIONMEDIO);
            if(Integer.valueOf(tokens.nextToken())<=Integer.valueOf(tokens.nextToken())) {
                bandera = Boolean.TRUE;
            }
        
        } else if (diasDelMes.matches(Constantes.DIAMESDIAGONAL)) {
            StringTokenizer tokens = new StringTokenizer(diasDelMes, DiccionarioCron.DIAGONAL);
            if(Integer.valueOf(tokens.nextToken())<=Integer.valueOf(tokens.nextToken())) {
                bandera = Boolean.TRUE;
            }
        
        } else {
            bandera = validarDiaDelMes2(diasDelMes);
        }
        
        return bandera;
    }
    
    /**
     * Continua con la validacion del metodo anterior.
     *
     * @param diasDelMes
     * @return
     */
    public boolean validarDiaDelMes2(String diasDelMes) {
        boolean bandera = Boolean.FALSE;
        
        if (diasDelMes.matches(Constantes.DIAMESL)) {
            bandera = Boolean.TRUE;
            
        } else if (diasDelMes.matches(Constantes.DIAMESW)) {
            bandera = Boolean.TRUE;
        
        } else if (diasDelMes.equals(Constantes.ASTERISCO)) {
            bandera = Boolean.TRUE;
            
        } else if (diasDelMes.equals(DiccionarioCron.INTERROGACION)) {
            bandera = Boolean.TRUE;
        }
        return bandera;
    }
    
    /**
     * Valida el dia del mes, ya sea que este este escrito con diagonales, guiones, comas o solo el mes.
     *
     * @param mes
     * @return verdadero si la validacion fue exitosa, falso en caso contrario.
     */
    public boolean validarMeses(String mes) {
        boolean bandera = Boolean.FALSE;
        
        String mesCorto = mes.replace(DiccionarioCron.DIAGONAL, Constantes.VACIO).replace(DiccionarioCron.GUIONMEDIO, Constantes.VACIO);
        
        // Valida si viene con un gion, una diagonal o un numero o letra de mes solo:
        if (mes.matches(Constantes.MESGUION) || mes.matches(Constantes.MESDIAGONAL)) {
            StringTokenizer tokens = new StringTokenizer(mes, DiccionarioCron.DIAGONAL);
            if(listaMesesNumero.contains(tokens.nextToken()) || listaMesesNumero.contains(tokens.nextToken())) {
                bandera = Boolean.TRUE;
            }
            
        //Valida si viene con un asterisco:
        } else if (mesCorto.equals(DiccionarioCron.ASTERISCO)) {
            bandera = Boolean.TRUE;
        
        //Valida si viene con comas
        } else if (mesCorto.indexOf(DiccionarioCron.COMA) != -1)  {
            bandera = validarMesesPorComa(mesCorto);
        //valida si solo viene un numero en el mes
        } else if(listaMesesNumero.contains(mesCorto) || listaMesesLetras.contains(mesCorto)) {
            bandera = Boolean.TRUE;
        }
        
        return bandera;
    }
    
    /**
     * Valida el caso en el que los meses estan separados por comas, ya sea que los meses esten escritos con letras 
     * o numeros.
     *
     * @param mesCorto
     * @return verdadero si la validacion fue exitosa, falso en caso contrario.
     */
    private boolean validarMesesPorComa(String mesCorto) {
        boolean bandera = Boolean.TRUE;
        String [] diasPorComas = mesCorto.split(DiccionarioCron.COMA);
        
        for(int i = 0; i<diasPorComas.length; i++) {
            if (!listaMesesLetras.contains(diasPorComas[i])) {
                bandera= Boolean.FALSE;
                break;
            }
        }
        
        if(!bandera) {
            bandera = Boolean.TRUE;
            for(int i = 0; i<diasPorComas.length; i++) {
                if (!listaMesesNumero.contains(diasPorComas[i])) {
                    bandera= Boolean.FALSE;
                    break;
                }
            }
        }
        return bandera;
    }
    
    /**
     * Valida el campo de dias de la semana
     *
     * @param diasDeLaSemana
     * @return verdadero si la validacion fue exitosa, falso en caso contrario.
     */
    public boolean validarDiasDeLaSemana(String diasDeLaSemana) {
        boolean bandera = Boolean.FALSE;
        
        if (diasDeLaSemana.matches(Constantes.DIASEMANANUMEROLETRA)) {
            bandera = Boolean.TRUE;
            
        } else if (diasDeLaSemana.matches(Constantes.DIASEMANACOMA)) {
            bandera = Boolean.TRUE;
            
        } else if (diasDeLaSemana.matches(Constantes.DIASEMANAGUION)) {
            bandera = Boolean.TRUE;
        
        } else if (diasDeLaSemana.matches(Constantes.DIASEMANADIAGONAL)) {
            bandera = Boolean.TRUE;
        
        } else if (diasDeLaSemana.matches(Constantes.DIAMESL)) {
            bandera = Boolean.TRUE;
            
        } else if (diasDeLaSemana.matches(Constantes.DIASEMANANUMERAL)) {
            bandera = Boolean.TRUE;
        
        } else if (diasDeLaSemana.equals(Constantes.ASTERISCO)) {
            bandera = Boolean.TRUE;
            
        } else if (diasDeLaSemana.equals(DiccionarioCron.INTERROGACION)) {
            bandera = Boolean.TRUE;
        }
        
        return bandera;
    }
    
    /**
     * Valida que los anios vengan en el formato correcto, valida que si es que el campo de anios tiene un valor,
     * se valide su estructura si es que solo vienen los anios, viene con comas, guiones o diagonales.
     *
     * @param anios expresion que contiene los los anios en los cuales se ejecutara la expresion.
     * @return verdadero si la validacion fue exitosa, falso en caso contrario.
     */
    public boolean validarAnios(String anios) {
        boolean bandera = Boolean.FALSE;
        
        if (anios.matches(Constantes.ANIONUMERO)) {
            bandera = Boolean.TRUE;
            
        } else if (anios.matches(Constantes.ANIOCOMA)) {
            bandera = Boolean.TRUE;
            
        } else if (anios.matches(Constantes.ANIODIAGONAL)) {
            bandera = Boolean.TRUE;
            
        } else if (anios.matches(Constantes.ANIOGUION)) {
            bandera = Boolean.TRUE;
            
        } else if (anios.equals(DiccionarioCron.ASTERISCO)) {
            bandera = Boolean.TRUE;
        }
        
        return bandera;
    }
}
