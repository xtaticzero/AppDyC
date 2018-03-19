package mx.gob.sat.siat.dyc.generico.util.common;

import java.lang.reflect.Field;

import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;

import org.apache.log4j.Logger;


public class ReflexionDyC {
    private static Logger log = Logger.getLogger("loggerDYC");

    private StringBuilder strb;
    private Class claseParametros;
    private Object obj;

    public ReflexionDyC() {
        super();
    }

    public void setStrb(StringBuilder strb) {
        this.strb = strb;
    }

    public StringBuilder getStrb() {
        return strb;
    }

    public void setClaseParametros(Class claseParametros) {
        this.claseParametros = claseParametros;
    }

    public Class getClaseParametros() {
        return claseParametros;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Object getObj() {
        return obj;
    }


    /**
     * Regresa String con todos los atributos no nulos que tenga el objeto.
     * @param objeto
     * @return
     */
    public String obtenerParametros(Object objeto) {
        try {
            this.obj = objeto;
            this.claseParametros = objeto.getClass();
        } catch (Exception e) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e);
        }
        return this.getParametros().toString();
    }

    public StringBuilder getParametros() {
        this.strb = new StringBuilder();
        /**Class<?> thisClass = null;*/
        try {
            Field[] aClassFields = this.claseParametros.getDeclaredFields();
            this.strb.append(this.claseParametros.getSimpleName() + " [ ");
            for (Field f : aClassFields) {
                f.setAccessible( Boolean.TRUE);
                String fName = f.getName();
                Object valor = f.get(this.obj);
                if (null != valor) {
                    this.strb.append("(" + f.getType() + ") " + fName + " = " + valor + ", ");
                }
            }
            this.strb.append("]");
        } catch (Exception ex) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + ex);
        }

        return this.strb;
    }

    public static void setLog(Logger log) {
        ReflexionDyC.log = log;
    }

    public static Logger getLog() {
        return log;
    }
}
