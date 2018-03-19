/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.vo;

import java.lang.reflect.Field;

import java.util.Map;

import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;

import org.apache.log4j.Logger;


/**
 * Clase VO simple para generar un objeto he insertar a la pista de auditoria.
 * @author Luis Fernando Barrios Quiroz [ LuFerMX ]
 * @since 0.1
 *
 * @date Abril 3, 2014
 */
public class PistaAuditoriaVO {
    private static Logger log = Logger.getLogger("loggerDYC");

    private Map<String, String> remplaceMensajes;
    private Integer idGrupoOperacion;
    private String idProceso;
    private String claveSistema;
    private Integer idMensaje;
    private Integer movimiento;
    private String identificador;

    public PistaAuditoriaVO() {
        super();
    }

    public void setRemplaceMensajes(Map<String, String> remplaceMensajes) {
        this.remplaceMensajes = remplaceMensajes;
    }

    public Map<String, String> getRemplaceMensajes() {
        return remplaceMensajes;
    }

    public void setIdProceso(String idProceso) {
        this.idProceso = idProceso;
    }

    public String getIdProceso() {
        return idProceso;
    }

    public void setClaveSistema(String claveSistema) {
        this.claveSistema = claveSistema;
    }

    public String getClaveSistema() {
        return claveSistema;
    }

    public void setMovimiento(int movimiento) {
        this.movimiento = movimiento;
    }

    public int getMovimiento() {
        return movimiento;
    }

    public void setIdGrupoOperacion(Integer idGrupoOperacion) {
        this.idGrupoOperacion = idGrupoOperacion;
    }

    public Integer getIdGrupoOperacion() {
        return idGrupoOperacion;
    }

    public void setIdMensaje(Integer idMensaje) {
        this.idMensaje = idMensaje;
    }

    public Integer getIdMensaje() {
        return idMensaje;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getIdentificador() {
        return identificador;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        Class<?> thisClass = null;
        try {
            thisClass = Class.forName(this.getClass().getName());

            Field[] aClassFields = thisClass.getDeclaredFields();
            sb.append(this.getClass().getSimpleName() + " [ ");
            for (Field f : aClassFields) {
                f.setAccessible(Boolean.TRUE);
                String fName = f.getName();
                Object valor = f.get(this);
                if (null != valor) {
                    sb.append("(" + f.getType() + ") " + fName + " = " + valor + ", ");
                }
            }
            sb.append("]");
        } catch (Exception ex) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + ex);
        }

        return sb.toString();
    }
}
