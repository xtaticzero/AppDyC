package mx.gob.sat.siat.migradordyc;

public class ColumnaDTO
{
    private String COLUMN_NAME;
    private Integer DATA_LENGTH;
    private String TABLE_NAME;
    private Class tipo;
    private Integer tipoJava;
    private Boolean NULLABLE;

    public String getCOLUMN_NAME() {
        return COLUMN_NAME;
    }

    public void setCOLUMN_NAME(String COLUMN_NAME) {
        this.COLUMN_NAME = COLUMN_NAME;
    }

    public Integer getDATA_LENGTH() {
        return DATA_LENGTH;
    }

    public void setDATA_LENGTH(Integer DATA_LENGTH) {
        this.DATA_LENGTH = DATA_LENGTH;
    }

    public String getTABLE_NAME() {
        return TABLE_NAME;
    }

    public void setTABLE_NAME(String TABLE_NAME) {
        this.TABLE_NAME = TABLE_NAME;
    }

    public Class getTipo() {
        return tipo;
    }

    public void setTipo(Class tipo) {
        this.tipo = tipo;
    }

    public Integer getTipoJava() {
        return tipoJava;
    }

    public void setTipoJava(Integer tipoJava) {
        this.tipoJava = tipoJava;
    }

    public Boolean getNULLABLE() {
        return NULLABLE;
    }

    public void setNULLABLE(Boolean NULLABLE) {
        this.NULLABLE = NULLABLE;
    }
}
