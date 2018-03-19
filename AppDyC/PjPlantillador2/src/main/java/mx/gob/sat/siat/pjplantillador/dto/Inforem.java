/*
*  Todos los Derechos Reservados 2014 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.pjplantillador.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que almacena la informacion para generar el documento.
 * @author Agustin Romero Mata / Softtek
 */
public class Inforem {
    private String               nombre;
    private String               tag;
    private List<Integer>        lstarea;
    private int                  tamano;
    private char                 tipo;
    private String               idTabla;              
    private int                  columna;
    private String               imagen; 
    private List<String>         contenido;
    
    /**
     * Constructor de default de la clase.
     */
    public Inforem() {
        super();
        this.nombre = "";
        this.lstarea = null;
        this.tamano = 0;
        this.tipo = ' ';
        this.columna = 0;
        this.imagen = "";
        this.contenido = null;
    }

    /**
     * Obtiene el nombre del campo de sustitucion
     * @return 
     */
    public String getNombre() {
    
        return nombre;
    }
    
    /**
     * Inicializa el nombre del campo de sustitucion
     * @param nombre 
     */
    public void setNombre(String nombre) {
    
        this.nombre = nombre;
    }
    
    /**
     * Obtiene el tamaño del campo de sustitucion
     * @return 
     */
    public int getTamano() {
    
        return tamano;
    }
    
    /**
     * Inicializa el tamaño del campo de sustitucion
     * @param tamano 
     */
    public void setTamano(int tamano) {
    
        this.tamano = tamano;
    }
    
    /**
     * Obtiene el tipo del campo de sustitucion
     * @return 
     */
    public char getTipo() {
    
        return tipo;
    }
    
    /**
     * Inicializa el tipo del campo de sustitucion
     * @param tipo 
     */
    public void setTipo(char tipo) {
    
        this.tipo = tipo;
    }
    
    /**
     * Obtiene la colunma en caso de que pertenesca a una tabla,
     * del campo de sustitucion
     * @return 
     */
    public int getColumna() {
    
        return columna;
    }
    
    /**
     * Inicializa la columna del campo de sustitucion
     * @param columna 
     */
    public void setColumna(int columna) {
    
        this.columna = columna;
    }

    /**
     * Obtiene el contenido inicial del campo de sustitucion
     * @return 
     */
    public String getContenido() {
    
        return contenido.get(0);
    }
    
    /**
     * Obtiene el contenido inicial del campo de sustitucion
     * @return 
     */
    public String getUltimoContenido() {
    
        return contenido.get(contenido.size()-1);
    }
    
    /**
     * Obtiene la lista de contenidos del campo de sustitucion
     * @return 
     */
    public List<String> getContenidoList() {
        
        return contenido;
    }
    
    /**
     * Obtiene el contenido seleccionado del campo de sustitucion
     * @param index
     * @return 
     */
    public String getContenido(int index) {
        
        if (this.contenido == null) {
            this.contenido = new ArrayList<String>();        
            return null;
        }
        
        return contenido.get(index);
    }
    
    /**
     * Inicializa el contenido del campo de sustitucion
     * @param contenido 
     */
    public void setContenido(String contenido) {
    
        if (this.contenido == null) {
            this.contenido = new ArrayList<String>();
        }
        
        this.contenido.add(contenido);
    }

    /**
     * Obtiene la lista de areas del campo de sustitucion
     * @return 
     */
    public List<Integer> getLstarea() {
    
        return lstarea;
    }

    /**
     * Inicializa la lista de areas del campo de sustitucion
     * @param lstarea 
     */
    public void setLstarea(List<Integer> lstarea) {
    
        this.lstarea = (ArrayList<Integer>) lstarea;
    }
    
    /**
     * Obtiene el tag del campo de sustitucion
     * @return 
     */
    public String getTag() {
    
        return tag;
    }

    /**
     * Inicializa el tag del campo de sustitucion
     * @param tag 
     */
    public void setTag(String tag) {
    
        this.tag = tag;
    }

    /**
     * Obtiene el identificador de la tabla del campo de sustitucion
     * @return 
     */
    public String getIdTabla() {
    
        return idTabla;
    }
    
    /**
     * Inicializa el id de la tabla del campo de sustitucion
     * @param idTabla 
     */
    public void setIdTabla(String idTabla) {
    
        this.idTabla = idTabla;
    }
    
    /**
     * Obtiene la informacion de la imagen del campo de sustitucion
     * @return 
     */
    public String getImagen() {
    
        return imagen;
    }
    
    /**
     * Inicializa la informacion de la imagen del campo de sustitucion
     * @param imagen 
     */
    public void setImagen(String imagen) {
    
        this.imagen = imagen;
    }      
    
    public String toString() {
        String retorno;
        
        retorno =  " < ---------------------------- > \n";
        retorno += "   Nombre campo " + nombre + '\n';
        retorno += "   Nombre tag " + tag + '\n';
        retorno += "   lista de area ";
        if (lstarea == null) {
            retorno += "es nula";
        } else {
            for(Integer area : lstarea) {
                retorno += area + ",";
            }
        };
        
        retorno += '\n';
        retorno += "   Nombre tipo " + tipo + '\n';               
        retorno += "   lista de contenido ";
        if (contenido == null) {
            retorno += "es nula";
        } else {
            for(String dato : contenido) {
               retorno += dato + ",";
            }
        }
        retorno += '\n';                
        return retorno;        
    }
}
