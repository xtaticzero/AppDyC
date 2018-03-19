/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.siat.pjplantillador.servicios;

import java.io.ByteArrayOutputStream;

/**
* Clase para el paso de parametros para el despliegue de una imagen.
* @author Agustin Romero Mata / Softtek
*/
public class ParametrosImagen {
   private ByteArrayOutputStream bytes;
   private String filenameHint;
   private String altText;
   private int id1;
   private int id2; 
   private long cx;

   /**
    * Obtiene los bytes almacenados
    * @return 
    */
   public byte[] getBytes() {            
       return bytes.toByteArray();
   }

   /**
    * Inicializa los bytes almacenados
    * @param bytes 
    */
   public void setBytes(ByteArrayOutputStream bytes) {
       if (this.bytes == null) {
           this.bytes = bytes; 
       }
   }

   /**
    * Obtiene el nombre el archivo de almacenar
    * @return 
    */
   public String getFilenameHint() {
       return filenameHint;
   }

   /**
    * Inicializa el nombre el archivo de almacenar
    * @param filenameHint 
    */
   public void setFilenameHint(String filenameHint) {
       this.filenameHint = filenameHint;
   }

   /**
    * Obtiene el tag a sustituir
    * @return 
    */
   public String getAltText() {
       return altText;
   }

   /**
    * Inicializa el tag a sustituir
    * @param altText 
    */
   public void setAltText(String altText) {
       this.altText = altText;
   }

   /**
    * Obtiene el id1
    * @return 
    */
   public int getId1() {
       return id1;
   }

   /**
    * Inicializa el id1
    * @param id1 
    */
   public void setId1(int id1) {
       this.id1 = id1;
   }

   /**
    * Obtiene el id2
    * @return 
    */
   public int getId2() {
       return id2;
   }

   /**
    * Inicializa el id2
    * @param id2 
    */
   public void setId2(int id2) {
       this.id2 = id2;
   }

   /**
    * Obtiene el CX
    * @return 
    */
   public long getCx() {
       return cx;
   }

   /**
    * Inicializa el CX
    * @param cx 
    */
   public void setCx(long cx) {
       this.cx = cx;
   }                
}
