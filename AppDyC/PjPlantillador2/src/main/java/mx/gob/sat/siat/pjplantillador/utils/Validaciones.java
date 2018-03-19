/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.siat.pjplantillador.utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author socrates
 */
public class Validaciones {
  /**
   * Funciones para aplicar las reglas a los tipo
   * de campos contenidos en los Anexos 7, 11 y 11A
   * 
   */	
  private String getRFC(String cell){
    String contenidoCelda = cell;
    if(contenidoCelda.length()>13){
      contenidoCelda = contenidoCelda.substring(0,13);
      return contenidoCelda;
    }else{
      return contenidoCelda;
    }
  }

  /**
   * 
   * @param cadena
   * @return 
   */
  private Date getFecha(String  cadena){    
    SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
    String fechaString =sd.format(cadena);
    System.out.println("fecha: "+fechaString);
    try {        
        return sd.parse(cadena);        
    } catch (ParseException ex) {
            
    }
    return null;    
  }

  /**
   * 
   * @param cadena
   * @param longitud
   * @return 
   */
  private String getValorCampoAlfanum(String cadena, int longitud){
    String contenidoCelda = cadena;
    if(contenidoCelda.length()>longitud){
        contenidoCelda = contenidoCelda.substring(0,longitud);
        return contenidoCelda;
    }else{
        return contenidoCelda;
    }
  }

  /**
   * 
   * @param cadena
   * @return 
   */
  private String getValorCLABE(String cadena){
    String contenidoCelda = cadena;
    if(contenidoCelda.length()==18){
        return contenidoCelda;
    }else{
        return null;
    }
  }

  /**
   * 
   * @param dato
   * @param longitud
   * @param negativo
   * @return 
   */
  private Integer getValorNumericoEntero(String dato, int longitud, boolean negativo){
    String valor = dato;

    if(valor.startsWith("$")){
        valor = valor.substring(1);
    }
    if(valor.endsWith("%")){
        valor = valor.substring(0,valor.length()-1);
    }
    if(valor.startsWith("(") && valor.endsWith(")")){
        valor = valor.substring(1);
        valor = valor.substring(0,valor.length()-1);
    }
    if(valor.startsWith("$") ){
        valor = valor.substring(1);
    }
    if(valor.contains(",")){
        String cadena ="";
        String datos[] = valor.split(",");
        for(int ii=0; ii<datos.length; ii++){
                cadena += datos[ii];
        }
        valor = cadena;
    }

    Integer resultado = isNumericInteger(valor);

    if(valor.startsWith("-")){
        valor = valor.substring(1);
    }
    if (resultado!= null){

        if( (negativo==false) && (resultado<0) ){
                return 0;
        }

        if((valor.length() <= longitud)) {
                return resultado;
        }else{
                return 0;
        }

    } else {
        return  0;
    }
  }

  /**
   * 
   * @param cadena
   * @param longitud
   * @param negativo
   * @return 
   */
  private Double getValorNumericoDouble(String cadena, int longitud, boolean negativo){

    Double valor = isNumericDouble(cadena);
    Double resultado =0.0;

    if (valor != null) {
        DecimalFormat myFormatter = new DecimalFormat("0.00");
        String output = myFormatter.format(valor);

        if(!output.equals("0.00")){
            resultado = Double.parseDouble(output);

            if(output.startsWith("-")){
                output = output.substring(1,output.length());
            }

            if(output.contains(",")){
                String datos[] = output.split(",");
                for(int i=0;i<datos.length;i++){
                        output += datos[i];
                }
            }

            if( (negativo==false) && (resultado<0) ){
                return 0.0;
            }

            if((output.length() <= longitud)) {
                return resultado;
            }else{
                return 0.0;
            }

        }else{
            resultado = 0.0;
        }

    }

    return valor;
  }

  /**
   * 
   * @param cadena
   * @param negativo
   * @return 
   */
  private Double getTasa(String cadena, boolean negativo){
    Double valor = isNumericDouble(cadena);
    Double resultado =0.0;

    if (valor != null) {
        DecimalFormat myFormatter = new DecimalFormat("0.0000");
        String output = myFormatter.format(valor);

        if(!output.equals("0.0000")){
            resultado = Double.parseDouble(output);

            if(output.startsWith("-")){
                output = output.substring(1,output.length());
            }

            if(output.contains(",")){
                String datos[] = output.split(",");
                for(int i=0;i<datos.length;i++){
                        output += datos[i];
                }
            }

            if( (negativo==false) && (resultado<0) ){
                return 0.0000;
            }

            if((output.length() <= 9)) {
                return resultado;
            }else{
                return 0.0000;
            }

        }else{
            resultado = 0.0000;
        }

    }

    return valor;
  }

  /**
   * 
   * @param cadenaOriginal
   * @return 
   */
  private Integer isNumericInteger(String cadenaOriginal){
    try {
        return Integer.parseInt(cadenaOriginal);
    } catch (NumberFormatException nfe){
        return 0;
    }
  }

  /**
   * 
   * @param cadena
   * @return 
   */
  private Double isNumericDouble(String cadena){
    Double resultado;
    try{
        resultado = Double.valueOf(cadena);
        return resultado;
    }catch(Exception nfe){				
        return 0.0;			
    }
  }
}
