package mx.gob.sat.siat.dyc.adminprocesos.web.controller.bean.bussiness;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import mx.gob.sat.siat.dyc.adminprocesos.util.constante.Constantes;
import mx.gob.sat.siat.dyc.adminprocesos.util.constante.DiccionarioCron;


public class TraductorCron {
    
    private List<String> listaValidacionSegundo = null;
    private List<String> listaValidacionMinuto  = null;
    private List<String> listaValidacionHora    = null;
    private List<String> listaValidacionMes     = null;
    private List<String> listaValidacionAnio    = null;
    
    
    private static final String ES_ESPACIO    = "es ";
    private static final String S_ESPACIO     = "s ";
    private static final String TODOSLOSDIAS  = "MON,TUE,WED,THU,FRI,SAT,SUN";
    
    public TraductorCron() {
        inicializarListaValidaciones();
    }
    
    /**
     * Inicializa las listas de validacion que contendran las diferentes expresiones regulares que validaran los
     * segundos, minutos u horas.
     */
    private void inicializarListaValidaciones() {
        listaValidacionSegundo = new ArrayList<String>();
        listaValidacionSegundo.add(Constantes.SEGUNDOMINUTOGUION);
        listaValidacionSegundo.add(Constantes.SEGUNDOMINUTODIAGONAL);
        listaValidacionSegundo.add(Constantes.SEGUNDOMINUTOCOMAS);
        listaValidacionSegundo.add(Constantes.SEGUNDONUMERO);
        
        listaValidacionMinuto = new ArrayList<String>();
        listaValidacionMinuto.add(Constantes.SEGUNDOMINUTOGUION);
        listaValidacionMinuto.add(Constantes.SEGUNDOMINUTODIAGONAL);
        listaValidacionMinuto.add(Constantes.SEGUNDOMINUTOCOMAS);
        listaValidacionMinuto.add(Constantes.SEGUNDONUMERO);
        
        listaValidacionHora = new ArrayList<String>();
        listaValidacionHora.add(Constantes.HORAGUION);
        listaValidacionHora.add(Constantes.HORADIAGONAL);
        listaValidacionHora.add(Constantes.HORACOMAS);
        listaValidacionHora.add(Constantes.HORANUMERO);
        
        listaValidacionMes = new ArrayList<String>();
        listaValidacionMes.add(Constantes.MESGUION);
        listaValidacionMes.add(Constantes.MESDIAGONAL);
        listaValidacionMes.add(Constantes.MESCOMA);
        listaValidacionMes.add(Constantes.MESNUMERO_O_NOMBRE);        
        
        listaValidacionAnio = new ArrayList<String>();
        listaValidacionAnio.add(Constantes.ANIOGUION);
        listaValidacionAnio.add(Constantes.ANIODIAGONAL);
        listaValidacionAnio.add(Constantes.ANIOCOMA);
        listaValidacionAnio.add(Constantes.ANIONUMERO);   
    }
    
    /**
     * Genera la descripcion de la expresion cron completa, este metodo ensambla cada unas de las descripciones de 
     * los segundos, minutos, horas, dias del mes, meses, dias de la semana y anios.
     *
     * @param expresionCron
     * @return String con la descripcion del horario de ejecucion de una expresion cron.
     */
    public String generarDescripcionEjecucion(String expresionCron) {
        StringBuilder descripcion = new StringBuilder();
        
        if(validarExpresionCron(expresionCron)) {
            StringTokenizer token = new StringTokenizer(expresionCron);
            descripcion.append(generarDescripcionSegMinHoraMesAnio(token.nextToken(), Constantes.SEGUNDO, listaValidacionSegundo));
            descripcion.append(generarDescripcionSegMinHoraMesAnio(token.nextToken(), Constantes.MINUTO, listaValidacionMinuto));
            descripcion.append(generarDescripcionSegMinHoraMesAnio(token.nextToken(), Constantes.HORA, listaValidacionHora));
            descripcion.append(generarDescripcionDiasDelMes(token.nextToken()));
            descripcion.append(generarDescripcionSegMinHoraMesAnio(token.nextToken(), Constantes.MES, listaValidacionMes));
            descripcion.append(generarDescripcionDiaSemana(token.nextToken()));
            descripcion.append(generarDescripcionSegMinHoraMesAnio(token.nextToken(), Constantes.ANIO, listaValidacionAnio));
        }
        
        return descripcion.toString();
    }
    
    /**
     * Valida que la expresion cron sea correcta para que posteriormente se genere la descripcion de dicha expresion.
     *
     * @param expresionCron es la expresion a validar, la cual se va a dividir en segundos, minutos, horas, dias, meses, dias de la semana y anios.
     * @return verdadero en caso de que la validacion haya sido exitosa y falso en caso contrario.
     */
    private boolean validarExpresionCron(String expresionCron) {
        boolean bandera = false;
        StringTokenizer token = new StringTokenizer(expresionCron);
        Validador objetoValidador = new Validador();
        
        if(objetoValidador.validarSegundosMinutosHoras(token.nextToken(), DiccionarioCron.NUMEROINICIO, DiccionarioCron.NUMEROFINMINSEG1, DiccionarioCron.NUMEROFINMINSEG2) 
           && objetoValidador.validarSegundosMinutosHoras(token.nextToken(), DiccionarioCron.NUMEROINICIO, DiccionarioCron.NUMEROFINMINSEG1, DiccionarioCron.NUMEROFINMINSEG2) 
           && objetoValidador.validarSegundosMinutosHoras(token.nextToken(), DiccionarioCron.NUMEROINICIO, DiccionarioCron.NUMEROFINHORA1, DiccionarioCron.NUMEROFINHORA2)) {
            
            bandera = validarDiaDelMes(objetoValidador, token);           
        }
        objetoValidador = null;
        return bandera;
    }
    
    /**
     * Valida el dia del mes.
     *
     * @param objetoValidador
     * @param token
     * @return
     */
    private boolean validarDiaDelMes(Validador objetoValidador, StringTokenizer token) {
        boolean bandera = false;
        
        if(objetoValidador.validarDiaDelMes(token.nextToken())) {
            bandera = objetoValidador.validarMeses(token.nextToken());
            
            if (bandera) {
                bandera = objetoValidador.validarDiasDeLaSemana(token.nextToken());
                
                if (bandera) {
                    bandera = objetoValidador.validarAnios(token.nextToken());   
                }
            }
        }  
        return bandera;
    }
    
    /**
     * Regresa la descripcion escrita de la expresion cron en el caso de los segundos, miutos y horas. 
     *
     * @param expresion es la expresion que se va a validar para encontrar su descripcion
     * @param identificador indica si se trata de segundos, minutos u horas.
     * @param listaValidacion es una lista que contiene las validaciones de los segundos, minutos u horas.
     * @return cadena de texto con la descripcion dada la expresion.
     */
    private String generarDescripcionSegMinHoraMesAnio(String expresion, String identificador, List<String> listaValidacion) {
        StringBuilder descripcion  = new StringBuilder();
        
        /** VALIDA EL CASO EN QUE SE USA UN GUION:*/
        if(expresion.matches(listaValidacion.get(0))) {
            descripcion.append(validacionGuion(expresion, identificador));
        
        /** VALIDA EL CASO EN QUE SE USA UNA DIAGONAL:*/
        } else if(expresion.matches(listaValidacion.get(1))) {
            descripcion.append(validacionDiagonal(expresion, identificador));
        
        /** VALIDA EL CASO EN QUE VENGA UN ASTERISCO:*/
        } else if(expresion.equals(Constantes.ASTERISCO)) {
            descripcion.append(validacionAsterisco(identificador));
        
        /** VALIDA EL CASO EN QUE SE USAN COMAS:*/
        } else if(expresion.matches(listaValidacion.get(Constantes.DOS))) {
            descripcion.append(validacionComas(expresion, identificador));            
        
        /** VALIDA EL CASO EN QUE SE USA UN SOLO NUMERO:*/
        } else if(expresion.matches(listaValidacion.get(Constantes.TRES))) {
            descripcion.append(validacionSoloNumero(expresion, identificador));
        }
        return descripcion.toString();
    }
    
    /**
     * Valida que cada vez que se valide un segundo, minuto u hora se y lleve un guion
     *
     * @param expresion expresion de segundos, minutos u horas con guion
     * @param identificador señala si es un segundo, minuto u hora.
     * @return descripcion del segundo, minuto u hora.
     */
    private String validacionGuion(String expresion, String identificador) {
        StringBuilder descripcion = new StringBuilder();
        StringTokenizer token  = new StringTokenizer(expresion, DiccionarioCron.GUIONMEDIO);
        String primerElemento  = token.nextToken();
        String segundoElemento = token.nextToken();
        if (identificador.equals(Constantes.SEGUNDO)) {
            descripcion.append(Constantes.DURANTE).append(Math.abs((Integer.valueOf(primerElemento)-Integer.valueOf(segundoElemento)))+1).append(Constantes.ESPACIO).append(Constantes.SEGUNDO).append(S_ESPACIO).append(Constantes.EMPEZANDOENEL).append(Constantes.SEGUNDO).append(Constantes.ESPACIO).append(primerElemento).append(Constantes.FINALIZANDOEN).append(Constantes.ESPACIO).append(Constantes.SEGUNDO).append(Constantes.ESPACIO).append(segundoElemento);
            
        } else if (identificador.equals(Constantes.MINUTO)) {
            descripcion.append(Constantes.DURANTE).append(Math.abs((Integer.valueOf(primerElemento)-Integer.valueOf(segundoElemento)))+1).append(Constantes.ESPACIO).append(Constantes.MINUTO).append(S_ESPACIO).append(Constantes.EMPEZANDOENEL).append(Constantes.MINUTO).append(Constantes.ESPACIO).append(primerElemento).append(Constantes.FINALIZANDOEN).append(Constantes.ESPACIO).append(Constantes.MINUTO).append(Constantes.ESPACIO).append(segundoElemento);
            
        } else if (identificador.equals(Constantes.HORA)) {
            descripcion.append(Constantes.DURANTE).append(Math.abs((Integer.valueOf(primerElemento)-Integer.valueOf(segundoElemento)))+1).append(Constantes.ESPACIO).append(Constantes.HORA).append(S_ESPACIO).append(Constantes.EMPEZANDOALAS).append(Constantes.HORA).append(Constantes.ESPACIO).append(primerElemento).append(Constantes.FINALIZANDOALAS).append(Constantes.ESPACIO).append(segundoElemento);
            
        } else if (identificador.equals(Constantes.DIA)) {
            descripcion.append(Constantes.DURANTE).append(Math.abs((Integer.valueOf(primerElemento)-Integer.valueOf(segundoElemento)))+1).append(Constantes.ESPACIO).append(Constantes.DIA).append(S_ESPACIO).append(Constantes.EMPEZANDOENEL).append(Constantes.DIA).append(Constantes.ESPACIO).append(primerElemento).append(Constantes.FINALIZANDOEN).append(Constantes.ESPACIO).append(Constantes.DIA).append(Constantes.ESPACIO).append(segundoElemento);   
            
        } else if (identificador.equals(Constantes.MES)) {
            descripcion.append(Constantes.DURANTE).append(Math.abs((Integer.valueOf(primerElemento)-Integer.valueOf(segundoElemento)))+1).append(Constantes.ESPACIO).append(Constantes.MES).append(ES_ESPACIO).append(Constantes.EMPEZANDOENEL).append(Constantes.MES).append(Constantes.ESPACIO).append(Constantes.DE).append(Constantes.ESPACIO).append(encontrarNombreDeMeses(primerElemento)).append(Constantes.FINALIZANDOEN).append(Constantes.ESPACIO).append(Constantes.MES).append(Constantes.ESPACIO).append(Constantes.DE).append(Constantes.ESPACIO).append(encontrarNombreDeMeses(segundoElemento));   
            
        } else if (identificador.equals(Constantes.ANIO)) {
            descripcion.append(Constantes.DURANTE).append(Math.abs((Integer.valueOf(primerElemento)-Integer.valueOf(segundoElemento)))+1).append(Constantes.ESPACIO).append(Constantes.ANIO).append(S_ESPACIO).append(Constantes.EMPEZANDOENEL).append(Constantes.ANIO).append(Constantes.ESPACIO).append(primerElemento).append(Constantes.FINALIZANDOEN).append(Constantes.ESPACIO).append(Constantes.ANIO).append(Constantes.ESPACIO).append(segundoElemento);   
        }
        return descripcion.toString();
    }
    
    /**
     * Valida que cada vez que se valide un segundo, minuto u hora se y lleve una diagonal
     *
     * @param expresion expresion de segundos, minutos u horas con guion
     * @param identificador señala si es un segundo, minuto u hora.
     * @return descripcion del segundo, minuto u hora.
     */
    private String validacionDiagonal(String expresion, String identificador) {
        
        StringBuilder descripcion = new StringBuilder();
        StringTokenizer token  = new StringTokenizer(expresion, DiccionarioCron.DIAGONAL);
        String primerElemento  = token.nextToken();
        String segundoElemento = token.nextToken();
        
        if (identificador.equals(Constantes.SEGUNDO)) {
            descripcion = validarPorSegundo(primerElemento, segundoElemento);
            
        } else if (identificador.equals(Constantes.MINUTO)) {
            descripcion = validarPorMinuto(primerElemento, segundoElemento);
            
        } else if (identificador.equals(Constantes.HORA)) {
            descripcion = validarPorHora(primerElemento, segundoElemento);
            
        } else if (identificador.equals(Constantes.DIA)) {
            descripcion = validarPorDia(primerElemento, segundoElemento);
            
        } else if (identificador.equals(Constantes.MES)) {
            descripcion = validarPorMes(primerElemento, segundoElemento);
            
        } else if (identificador.equals(Constantes.ANIO)) {
            descripcion = validarPorAnio(primerElemento, segundoElemento);
        }
        
        return descripcion.toString();
    }
        
    /**
     * Valida que cada vez que se valide un segundo, minuto u hora se y lleve un asterisco
     *
     * @param identificador señala si es un segundo, minuto u hora.
     * @return descripcion del segundo, minuto u hora.
     */
    private String validacionAsterisco(String identificador) {
        StringBuilder descripcion = new StringBuilder();
        if (identificador.equals(Constantes.SEGUNDO)) {
            descripcion.append(Constantes.CADA).append(Constantes.SEGUNDO);
            
        } else if (identificador.equals(Constantes.MINUTO)) {
            descripcion.append(Constantes.CADA).append(Constantes.MINUTO);
            
        } else if (identificador.equals(Constantes.HORA)) {
            descripcion.append(Constantes.CADA).append(Constantes.HORA);
            
        } else if (identificador.equals(Constantes.MES)) {
            descripcion.append(Constantes.ESPACIO).append(Constantes.DE).append(Constantes.CADA).append(Constantes.MES);
            
        } else if (identificador.equals(Constantes.ANIO)) {
            descripcion.append(Constantes.VACIO);
        }
        return descripcion.toString();
    }
    
    /**
     * Valida que cada vez que se valide un segundo, minuto u hora se y lleve comas
     *
     * @param expresion expresion de segundos, minutos u horas con guion
     * @param identificador señala si es un segundo, minuto u hora.
     * @return descripcion del segundo, minuto u hora.
     */
    private String validacionComas(String expresion, String identificador) {
        StringBuilder descripcion = new StringBuilder();
        if (identificador.equals(Constantes.SEGUNDO)) {
            descripcion.append(Constantes.LOS).append(Constantes.SEGUNDO).append(S_ESPACIO).append(expresion).append(Constantes.ESPACIO);
            
        } else if (identificador.equals(Constantes.MINUTO)) {
            descripcion.append(Constantes.LOS).append(Constantes.MINUTO).append(S_ESPACIO).append(expresion).append(Constantes.ESPACIO);
            
        } else if (identificador.equals(Constantes.HORA)) {
            descripcion.append(Constantes.LAS).append(Constantes.HORA).append(S_ESPACIO).append(expresion).append(Constantes.ESPACIO);
            
        } else if (identificador.equals(Constantes.MES)) {
            descripcion.append(Constantes.LOS).append(Constantes.MES).append(S_ESPACIO).append(expresion).append(Constantes.ESPACIO);   
            
        } else if (identificador.equals(Constantes.ANIO)) {
            descripcion.append(Constantes.LOS).append(Constantes.ANIO).append(S_ESPACIO).append(expresion).append(Constantes.ESPACIO);      
        }
        return descripcion.toString();
    }
    
    /**
     * Valida que cada vez que se valide un segundo, minuto u hora se y solo un numero
     *
     * @param expresion expresion de segundos, minutos u horas con guion
     * @param identificador señala si es un segundo, minuto u hora.
     * @return descripcion del segundo, minuto u hora.
     */
    private String validacionSoloNumero(String expresion, String identificador) {
        
        StringBuilder descripcion = new StringBuilder();
        
        if (identificador.equals(Constantes.SEGUNDO) && !expresion.equals(Constantes.CERO)) {
            descripcion.append(Constantes.ENEL).append(Constantes.SEGUNDO).append(Constantes.ESPACIO).append(expresion);
            
        } else if (identificador.equals(Constantes.MINUTO) && !expresion.equals(Constantes.CERO)) {
            descripcion.append(Constantes.ENEL).append(Constantes.MINUTO).append(Constantes.ESPACIO).append(expresion);
            
        } else if (identificador.equals(Constantes.HORA)) {
            descripcion.append(Constantes.ALAS).append(expresion);
            
        } else if (identificador.equals(Constantes.MES)) {
            descripcion.append(Constantes.ESPACIO).append(Constantes.DE).append(Constantes.ESPACIO).append(encontrarNombreDeMeses(expresion));
            
        } else if (identificador.equals(Constantes.ANIO)) {
            descripcion.append(Constantes.ESPACIO).append(Constantes.DEL).append(Constantes.ESPACIO).append(expresion);   
        }
        return descripcion.toString();
    }
    
    /**
     * Genera la descripcion de ejecucion del dia del mes.
     *
     * @param diasDelMes
     * @return
     */
    private String generarDescripcionDiasDelMes(String diasDelMes) {
        
        StringBuilder descripcion = new StringBuilder();
        if (diasDelMes.matches(Constantes.DIAMESNUMERO)) {
            descripcion.append(Constantes.ESPACIO).append(Constantes.EL).append(Constantes.ESPACIO).append(diasDelMes);
            
        } else if (diasDelMes.matches(Constantes.DIAMESCOMAS)) {
            descripcion.append(Constantes.LOSDIAS).append(Constantes.ESPACIO).append(diasDelMes);
            
        } else if (diasDelMes.matches(Constantes.DIAMESGUIONMEDIO)) {
            descripcion.append(validacionGuion(diasDelMes, Constantes.DIA));
        
        } else if (diasDelMes.matches(Constantes.DIAMESDIAGONAL)) {
            descripcion.append(validacionDiagonal(diasDelMes, Constantes.DIA));
        
        } else if (diasDelMes.matches(Constantes.DIAMESL)) {
            descripcion.append(validarDiasDelMesL(diasDelMes));
            
        } else if (diasDelMes.matches(Constantes.DIAMESW)) {
            descripcion.append(Constantes.ESPACIO).append(Constantes.DIAMASCERCANO).append(Constantes.ESPACIO).append(diasDelMes.replace(DiccionarioCron.W, Constantes.VACIO)).append(Constantes.ESPACIO).append(Constantes.DECADAMES);
        
        } else if (diasDelMes.equals(Constantes.ASTERISCO)) {
            descripcion.append(Constantes.ESPACIO).append(Constantes.DIARIO);
            
        } else if (diasDelMes.equals(DiccionarioCron.INTERROGACION)) {
            descripcion.append(Constantes.VACIO);
        }
        
        return descripcion.toString();
    }
    
    /**
     * Valida la expresion del dia del mes en el caso que tiene una L
     *
     * @param expresion
     * @return
     */
    private String validarDiasDelMesL(String expresion) {
        StringBuilder descripcion = new StringBuilder();
        
        if(expresion.equals(DiccionarioCron.L)) {
            descripcion.append(Constantes.ESPACIO).append(Constantes.ELULTIMODIADE);
            
        } else {
            descripcion.append(encontrarDiaDelMes(expresion.replace(DiccionarioCron.L, Constantes.ESPACIO)));
        }
        
        return descripcion.toString();
    }
    
    /**
     * A partir del numero de dia, devuelve el nombre del dia en idioma espaniol
     *
     * @param numeroDeDia
     * @return
     */
    private String encontrarDiaDelMes(String numeroDeDia) {
        String diaDeLaSemana = "";
        
        if(numeroDeDia.equals(DiccionarioCron.NUMERODOMINGO)) {
            diaDeLaSemana=DiccionarioCron.NOMBREDOMINGO;
            
        } else if(numeroDeDia.equals(DiccionarioCron.NUMEROLUNES)) {
            diaDeLaSemana=DiccionarioCron.NOMBRELUNES;
        
        } else if(numeroDeDia.equals(DiccionarioCron.NUMEROMARTES)) {
            diaDeLaSemana=DiccionarioCron.NOMBREMARTES;
        
        } else if(numeroDeDia.equals(DiccionarioCron.NUMEROMIERCOLES)) {
            diaDeLaSemana=DiccionarioCron.NOMBREMIERCOLES;
        
        } else if(numeroDeDia.equals(DiccionarioCron.NUMEROJUEVES)) {
            diaDeLaSemana=DiccionarioCron.NOMBREJUEVES;
        
        } else if(numeroDeDia.equals(DiccionarioCron.NUMEROVIERNES)) {
            diaDeLaSemana=DiccionarioCron.NOMBREVIERNES;
        
        } else if(numeroDeDia.equals(DiccionarioCron.NUMEROSABADO)) {
            diaDeLaSemana=DiccionarioCron.NOMBRESABADO;
        } 
        
        return diaDeLaSemana;
    }
    
    /**
     * Dependiendo del numero de mes o su abreviatura en idioma ingles retorna el nombre del mes.
     *
     * @param mes
     * @return
     */
    private String encontrarNombreDeMeses (String mes) {
        StringBuilder descripcion = new StringBuilder();
        
        if(mes.equals(DiccionarioCron.NUMEROENERO) || mes.equals(DiccionarioCron.ENERO)) {
            descripcion.append(DiccionarioCron.NOMBREENERO);
            
        } else if(mes.equals(DiccionarioCron.NUMEROFEBRERO) || mes.equals(DiccionarioCron.FEBRERO)) {
            descripcion.append(DiccionarioCron.NOMBREFEBRERO);
            
        } else if(mes.equals(DiccionarioCron.NUMEROMARZO) || mes.equals(DiccionarioCron.MARZO)) {
            descripcion.append(DiccionarioCron.NOMBREMARZO);
            
        } else if(mes.equals(DiccionarioCron.NUMEROABRIL) || mes.equals(DiccionarioCron.ABRIL)) {
            descripcion.append(DiccionarioCron.NOMBREABRIL);
            
        } else {
            descripcion = encontrarNombreDeMesParte2(mes, descripcion);
        }
        
        return descripcion.toString();
    }
    
    /**
     * Continua con el flujo del metodo anterior
     *
     * @param mes
     * @param descripcion
     * @return
     */
    private StringBuilder encontrarNombreDeMesParte2(String mes, StringBuilder descripcion) {
        
        if(mes.equals(DiccionarioCron.NUMEROMAYO) || mes.equals(DiccionarioCron.MAYO)) {
            descripcion.append(DiccionarioCron.NOMBREMAYO);
            
        } else if(mes.equals(DiccionarioCron.NUMEROJUNIO) || mes.equals(DiccionarioCron.JUNIO)) {
            descripcion.append(DiccionarioCron.NOMBREJUNIO);
                    
        } else if(mes.equals(DiccionarioCron.NUMEROJULIO) || mes.equals(DiccionarioCron.JULIO)) {
            descripcion.append(DiccionarioCron.NOMBREJULIO);
            
        } else if(mes.equals(DiccionarioCron.NUMEROAGOSTO) || mes.equals(DiccionarioCron.AGOSTO)) {
            descripcion.append(DiccionarioCron.NOMBREAGOSTO);
            
        } else {
            encontrarNombreDeMesParte3(mes, descripcion);
        }
        return descripcion;
    }
    
    /**
     * Continua con el flujo del metodo anterior
     *
     * @param mes
     * @param descripcion
     * @return
     */
    private StringBuilder encontrarNombreDeMesParte3(String mes, StringBuilder descripcion) {
        if(mes.equals(DiccionarioCron.NUMEROSEPTIEMBRE) || mes.equals(DiccionarioCron.SEPTIEMBRE)) {
            descripcion.append(DiccionarioCron.NOMBRESEPTIEMBRE);
            
        } else if(mes.equals(DiccionarioCron.NUMEROOCTUBRE) || mes.equals(DiccionarioCron.OCTUBRE)) {
            descripcion.append(DiccionarioCron.NOMBREOCTUBRE);
            
        } else if(mes.equals(DiccionarioCron.NUMERONOVIEMBRE) || mes.equals(DiccionarioCron.NOVIEMBRE)) {
            descripcion.append(DiccionarioCron.NOMBRENOVIEMBRE);
            
        } else if(mes.equals(DiccionarioCron.NUMERODICIEMBRE) || mes.equals(DiccionarioCron.DICIEMBRE)) {
            descripcion.append(DiccionarioCron.NOMBREDICIEMBRE);
        }
        return descripcion;
    }
    
    /**
     * Genera la descripcion del dia de la semana
     *
     * @param diasDeLaSemana
     * @return
     */
    private String generarDescripcionDiaSemana(String diasDeLaSemana) {
        StringBuilder descripcion = new StringBuilder();
        
        if (diasDeLaSemana.matches(Constantes.DIASEMANANUMEROLETRA)) {
            descripcion.append(Constantes.ESPACIO).append(Constantes.ELDIA).append(Constantes.ESPACIO).append(encontrarDiaLaSemana(diasDeLaSemana));
            
        } else if (diasDeLaSemana.matches(Constantes.DIASEMANACOMA)) {
            if(!diasDeLaSemana.equals(TODOSLOSDIAS)) {
                descripcion.append(Constantes.ESPACIO).append(Constantes.LOS).append(Constantes.DIA).append(S_ESPACIO).append(encontrarDiaLaSemana(diasDeLaSemana));
            
            } else {
                descripcion.append(Constantes.ESPACIO).append(Constantes.DIARIO);
            }
            
        } else if (diasDeLaSemana.matches(Constantes.DIASEMANAGUION)) {
            StringTokenizer token  = new StringTokenizer(diasDeLaSemana, DiccionarioCron.GUIONMEDIO);
            String primerElemento  = token.nextToken();
            String segundoElemento = token.nextToken();
            descripcion.append(Constantes.DURANTE).append(Math.abs((Integer.valueOf(retornarNumeroDeDia(primerElemento))-Integer.valueOf(retornarNumeroDeDia(segundoElemento))))+1).append(Constantes.ESPACIO).append(Constantes.DIA).append(S_ESPACIO).append(Constantes.EMPEZANDOENEL).append(Constantes.DIA).append(Constantes.ESPACIO).append(encontrarDiaLaSemana(primerElemento)).append(Constantes.FINALIZANDOEN).append(Constantes.ESPACIO).append(Constantes.DIA).append(Constantes.ESPACIO).append(encontrarDiaLaSemana(segundoElemento));   
        
        } else {
            descripcion= generarDescripcionDiaSemanaParte2(diasDeLaSemana, descripcion);
        }
        
        return descripcion.toString();
    }
    
    /**
     * Continua con el flujo del metodo anterior
     *
     * @param diasDeLaSemana
     * @param descripcion
     * @return
     */
    private StringBuilder generarDescripcionDiaSemanaParte2(String diasDeLaSemana, StringBuilder descripcion) {
        if (diasDeLaSemana.matches(Constantes.DIASEMANADIAGONAL)) {
            StringTokenizer token  = new StringTokenizer(diasDeLaSemana, DiccionarioCron.GUIONMEDIO);
            String primerElemento  = token.nextToken();
            String segundoElemento = token.nextToken();
            descripcion.append(Constantes.CADA).append(Math.abs((Integer.valueOf(retornarNumeroDeDia(primerElemento))-Integer.valueOf(retornarNumeroDeDia(segundoElemento))))+1).append(Constantes.ESPACIO).append(Constantes.DIA).append(S_ESPACIO).append(Constantes.EMPEZANDOENEL).append(Constantes.DIA).append(Constantes.ESPACIO).append(encontrarDiaLaSemana(primerElemento));
        
        } else if (diasDeLaSemana.matches(Constantes.DIASEMANAL)) {
            descripcion.append(Constantes.ESPACIO).append(Constantes.ELULTIMO).append(Constantes.ESPACIO).append(encontrarDiaLaSemana(diasDeLaSemana));
        
        } else if (diasDeLaSemana.matches(Constantes.DIASEMANANUMERAL)) {
            StringTokenizer token  = new StringTokenizer(diasDeLaSemana, DiccionarioCron.NUMERAL);
            String segundoElemento = token.nextToken();
            String primerElemento  = token.nextToken();
            descripcion.append(Constantes.ESPACIO).append(Constantes.EL).append(Constantes.ESPACIO).append(encontrarPosicion(Integer.valueOf(primerElemento))).append(Constantes.ESPACIO).append(encontrarDiaLaSemana(segundoElemento));
        
        } else if (diasDeLaSemana.equals(Constantes.ASTERISCO) || diasDeLaSemana.equals(DiccionarioCron.INTERROGACION)) {
            descripcion.append(Constantes.VACIO);
        }
        return descripcion;
    }
    
    /**
     * Busca a partir de el nombre numero de dia o de su abreviacion en el idioma ingles el nombre del dia en idioma 
     * espaniol.
     *
     * @param diasDeLaSemana
     * @return
     */
    private String encontrarDiaLaSemana(String diasDeLaSemana) {
        int i = 0;
        String diaDeLaSemana = "";
        StringBuilder dia = new StringBuilder();
        StringTokenizer token = new StringTokenizer(diasDeLaSemana, DiccionarioCron.COMA);
        
        while (token.hasMoreTokens()) {
            
            if(i>0) {
                dia.append(DiccionarioCron.COMA).append(Constantes.ESPACIO);
            }
            diaDeLaSemana=token.nextToken();
            
            if(diasDeLaSemana.equals(DiccionarioCron.NUMERODOMINGO) || diaDeLaSemana.equals(DiccionarioCron.DOMINGO)) {
                dia.append(DiccionarioCron.NOMBREDOMINGO);
                
            } else if(diasDeLaSemana.equals(DiccionarioCron.NUMEROLUNES) || diaDeLaSemana.equals(DiccionarioCron.LUNES)) {
                dia.append(DiccionarioCron.NOMBRELUNES);
            
            } else if(diasDeLaSemana.equals(DiccionarioCron.NUMEROMARTES) || diaDeLaSemana.equals(DiccionarioCron.MARTES)) {
                dia.append(DiccionarioCron.NOMBREMARTES);
            
            } else {
                dia.append(encontrarDiaDeLaSemana2(diasDeLaSemana, diaDeLaSemana));
            }
            
            i++;
        }
        
        return dia.toString();
    }
    
    /**
     * Continua con el flujo del metodo anterior
     *
     * @param diasDeLaSemana
     * @param diaDeLaSemana
     * @return
     */
    private StringBuilder encontrarDiaDeLaSemana2(String diasDeLaSemana, String diaDeLaSemana) {
        
        StringBuilder dia = new StringBuilder();
        if(diasDeLaSemana.equals(DiccionarioCron.NUMEROMIERCOLES) || diaDeLaSemana.equals(DiccionarioCron.MIERCOLES)) {
            dia.append(DiccionarioCron.NOMBREMIERCOLES);
        
        } else if(diasDeLaSemana.equals(DiccionarioCron.NUMEROJUEVES) || diaDeLaSemana.equals(DiccionarioCron.JUEVES)) {
            dia.append(DiccionarioCron.NOMBREJUEVES);
        
        } else if(diasDeLaSemana.equals(DiccionarioCron.NUMEROVIERNES) || diaDeLaSemana.equals(DiccionarioCron.VIERNES)) {
            dia.append(DiccionarioCron.NOMBREVIERNES);
        
        } else if(diasDeLaSemana.equals(DiccionarioCron.NUMEROSABADO) || diaDeLaSemana.equals(DiccionarioCron.SABADO)) {
            dia.append(DiccionarioCron.NOMBRESABADO);
        } 
        
        return dia;
    }
    
    /**
     *
     * @param expresion
     * @return
     */
    private int retornarNumeroDeDia(String expresion) {
        int numeroDia=0;
        
        if(expresion.matches(Constantes.DIASEMANA)) {
            numeroDia = Integer.valueOf(expresion);
            
        } else if (expresion.equals(DiccionarioCron.DOMINGO)) {
            numeroDia = Integer.valueOf(DiccionarioCron.NUMERODOMINGO);
            
        } else if (expresion.equals(DiccionarioCron.LUNES)) {
            numeroDia = Integer.valueOf(DiccionarioCron.NUMEROLUNES);
            
        } else if (expresion.equals(DiccionarioCron.MARTES)) {
            numeroDia = Integer.valueOf(DiccionarioCron.NUMEROMARTES);
            
        } else if (expresion.equals(DiccionarioCron.MIERCOLES)) {
            numeroDia = Integer.valueOf(DiccionarioCron.NUMEROMIERCOLES);
            
        } else if (expresion.equals(DiccionarioCron.JUEVES)) {
            numeroDia = Integer.valueOf(DiccionarioCron.NUMEROJUEVES);
            
        } else if (expresion.equals(DiccionarioCron.VIERNES)) {
            numeroDia = Integer.valueOf(DiccionarioCron.NUMEROVIERNES);
            
        } else if (expresion.equals(DiccionarioCron.SABADO)) {
            numeroDia = Integer.valueOf(DiccionarioCron.NUMEROSABADO);
        }
        
        return numeroDia;
    }
    
    /**
     *
     * @param posicion
     * @return
     */
    private String encontrarPosicion(int posicion) {
        String nombre = "";
        if(posicion==Constantes.UNO) {
            nombre = Constantes.PRIMERO;
            
        } else if(posicion==Constantes.DOS) {
            nombre = Constantes.SEGUNDO;
            
        } else if(posicion==Constantes.TRES) {
            nombre = Constantes.TERCER;
            
        } else if(posicion==Constantes.CUATRO) {
            nombre = Constantes.CUARTO;
            
        } else if(posicion==Constantes.CINCO) {
            nombre = Constantes.QUINTO;
        }
        return nombre;
    }
    
    /**
     *
     * @param primerElemento
     * @param segundoElemento
     * @return
     */
    private StringBuilder validarPorSegundo(String primerElemento, String segundoElemento) {
        StringBuilder descripcion = new StringBuilder();
        if(!primerElemento.equals(Constantes.CERO)) {
            descripcion.append(Constantes.CADA).append(Math.abs((Integer.valueOf(primerElemento)-Integer.valueOf(segundoElemento)))+1).append(Constantes.ESPACIO).append(Constantes.SEGUNDO).append(S_ESPACIO).append(Constantes.EMPEZANDOENEL).append(Constantes.SEGUNDO).append(Constantes.ESPACIO).append(primerElemento);
        } else {
            descripcion.append(Constantes.CADA).append(Math.abs((Integer.valueOf(primerElemento)-Integer.valueOf(segundoElemento)))).append(Constantes.ESPACIO).append(Constantes.SEGUNDO).append(S_ESPACIO).append(Constantes.EMPEZANDOENEL).append(Constantes.SEGUNDO).append(Constantes.ESPACIO).append(primerElemento);
        }
        return descripcion; 
    }
    
    private StringBuilder validarPorMinuto(String primerElemento, String segundoElemento) {
        StringBuilder descripcion = new StringBuilder();
        if(!primerElemento.equals(Constantes.CERO)) {
            descripcion.append(Constantes.CADA).append(Math.abs((Integer.valueOf(primerElemento)-Integer.valueOf(segundoElemento)))+1).append(Constantes.ESPACIO).append(Constantes.MINUTO).append(S_ESPACIO).append(Constantes.EMPEZANDOENEL).append(Constantes.MINUTO).append(Constantes.ESPACIO).append(primerElemento);
        } else {
            if(!segundoElemento.equals(Constantes.UNO.toString())) {
                descripcion.append(Constantes.CADA).append(Math.abs((Integer.valueOf(primerElemento)-Integer.valueOf(segundoElemento)))).append(Constantes.ESPACIO).append(Constantes.MINUTO).append(S_ESPACIO).append(Constantes.EMPEZANDOENEL).append(Constantes.MINUTO).append(Constantes.ESPACIO).append(primerElemento);
            
            } else {
                descripcion.append(Constantes.CADA).append(Constantes.MINUTO);
            }
        }
        return descripcion; 
    }
    
    /**
     *
     * @param primerElemento
     * @param segundoElemento
     * @return
     */
    private StringBuilder validarPorHora(String primerElemento, String segundoElemento) {
        StringBuilder descripcion = new StringBuilder();
        if(!primerElemento.equals(Constantes.CERO)) {
            descripcion.append(Constantes.CADA).append(Math.abs((Integer.valueOf(primerElemento)-Integer.valueOf(segundoElemento)))+1).append(Constantes.ESPACIO).append(Constantes.HORA).append(S_ESPACIO).append(Constantes.EMPEZANDOALAS).append(Constantes.ESPACIO).append(primerElemento);
        } else {
            if(!segundoElemento.equals(Constantes.UNO.toString())) {
                descripcion.append(Constantes.CADA).append(Math.abs((Integer.valueOf(primerElemento)-Integer.valueOf(segundoElemento)))).append(Constantes.ESPACIO).append(Constantes.HORA).append(S_ESPACIO).append(Constantes.EMPEZANDOALAS).append(Constantes.ESPACIO).append(primerElemento);
                
            } else {
                descripcion.append(Constantes.CADA).append(Constantes.HORA);
            }
        }
        return descripcion; 
    }
    
    private StringBuilder validarPorDia(String primerElemento, String segundoElemento) {
        StringBuilder descripcion = new StringBuilder();
        if(!primerElemento.equals(Constantes.CERO)) {
            if(segundoElemento.equals(Constantes.UNO.toString()) && primerElemento.equals(Constantes.UNO.toString())) {
                descripcion.append(Constantes.ESPACIO).append(Constantes.DIARIO);
            } else {
                descripcion.append(Constantes.CADA).append(Math.abs((Integer.valueOf(primerElemento)-Integer.valueOf(segundoElemento)))+1).append(Constantes.ESPACIO).append(Constantes.DIA).append(S_ESPACIO).append(Constantes.EMPEZANDOENEL).append(Constantes.DIA).append(Constantes.ESPACIO).append(primerElemento);
            }
        } else {
            if(!segundoElemento.equals(Constantes.UNO.toString())) {
                descripcion.append(Constantes.CADA).append(Math.abs((Integer.valueOf(primerElemento)-Integer.valueOf(segundoElemento)))).append(Constantes.ESPACIO).append(Constantes.DIA).append(S_ESPACIO).append(Constantes.EMPEZANDOENEL).append(Constantes.DIA).append(Constantes.ESPACIO).append(primerElemento);
                
            } else {
                descripcion.append(Constantes.ESPACIO).append(Constantes.DIARIO);
            }
        }
        return descripcion; 
    }
    
    /**
     *
     * @param primerElemento
     * @param segundoElemento
     * @return
     */
    private StringBuilder validarPorMes(String primerElemento, String segundoElemento) {
        StringBuilder descripcion = new StringBuilder();
        if(!primerElemento.equals(Constantes.CERO)) {
            descripcion.append(Constantes.CADA).append(Math.abs((Integer.valueOf(primerElemento)-Integer.valueOf(segundoElemento)))+1).append(Constantes.ESPACIO).append(Constantes.MES).append(S_ESPACIO).append(Constantes.EMPEZANDOENEL).append(Constantes.MES).append(Constantes.ESPACIO).append(primerElemento);
        } else {
            if(!segundoElemento.equals(Constantes.UNO.toString())) {
                descripcion.append(Constantes.CADA).append(Math.abs((Integer.valueOf(primerElemento)-Integer.valueOf(segundoElemento)))).append(Constantes.ESPACIO).append(Constantes.MES).append(ES_ESPACIO).append(Constantes.EMPEZANDOENEL).append(Constantes.MES).append(Constantes.ESPACIO).append(Constantes.DE).append(Constantes.ESPACIO).append(encontrarNombreDeMeses(primerElemento));
            
            } else {
                descripcion.append(Constantes.CADA).append(Constantes.MES);
            }
        }
        return descripcion; 
    }
    
    /**
     *
     * @param primerElemento
     * @param segundoElemento
     * @return
     */
    private StringBuilder validarPorAnio(String primerElemento, String segundoElemento) {
        StringBuilder descripcion = new StringBuilder();
        if(!segundoElemento.equals(Constantes.CERO)) {
            descripcion.append(Constantes.CADA).append(Math.abs((Integer.valueOf(primerElemento)-Integer.valueOf(segundoElemento)))+1).append(Constantes.ESPACIO).append(Constantes.ANIO).append(S_ESPACIO).append(Constantes.EMPEZANDOENEL).append(Constantes.ANIO);
        } else {
            descripcion.append(Constantes.CADA).append(Math.abs((Integer.valueOf(primerElemento)-Integer.valueOf(segundoElemento)))).append(Constantes.ESPACIO).append(Constantes.ANIO).append(S_ESPACIO).append(Constantes.EMPEZANDOENEL).append(Constantes.ANIO);
        }
        return descripcion; 
    }
}