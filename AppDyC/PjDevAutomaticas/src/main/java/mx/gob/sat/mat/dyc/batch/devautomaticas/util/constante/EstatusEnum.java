
package mx.gob.sat.mat.dyc.batch.devautomaticas.util.constante;

/**
 *
 * @author Ing. Gregorio Serapio Ramírez
 */
public enum EstatusEnum {
    WAIT(1, "WAIT"),
    SUCCESS(2, "SUCCESS"),
    ERROR(3, "ERROR");

    private int key;
    private String value;

    private EstatusEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}