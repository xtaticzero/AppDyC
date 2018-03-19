package mx.gob.sat.siat.migradordyc.dto;

public class SecuenciaDTO
{
    private String SEQUENCE_OWNER;
    private String SEQUENCE_NAME;
    private Integer MIN_VALUE;
    private Number MAX_VALUE;
    private Integer INCREMENT_BY;
    private Boolean CYCLE_FLAG;
    private Boolean ORDER_FLAG;
    private Number CACHE_SIZE;
    private Long LAST_NUMBER;

    public String getSEQUENCE_OWNER() {
        return SEQUENCE_OWNER;
    }

    public void setSEQUENCE_OWNER(String SEQUENCE_OWNER) {
        this.SEQUENCE_OWNER = SEQUENCE_OWNER;
    }

    public String getSEQUENCE_NAME() {
        return SEQUENCE_NAME;
    }

    public void setSEQUENCE_NAME(String SEQUENCE_NAME) {
        this.SEQUENCE_NAME = SEQUENCE_NAME;
    }

    public Integer getMIN_VALUE() {
        return MIN_VALUE;
    }

    public void setMIN_VALUE(Integer MIN_VALUE) {
        this.MIN_VALUE = MIN_VALUE;
    }

    public Number getMAX_VALUE() {
        return MAX_VALUE;
    }

    public void setMAX_VALUE(Number MAX_VALUE) {
        this.MAX_VALUE = MAX_VALUE;
    }

    public Integer getINCREMENT_BY() {
        return INCREMENT_BY;
    }

    public void setINCREMENT_BY(Integer INCREMENT_BY) {
        this.INCREMENT_BY = INCREMENT_BY;
    }

    public Boolean getCYCLE_FLAG() {
        return CYCLE_FLAG;
    }

    public void setCYCLE_FLAG(Boolean CYCLE_FLAG) {
        this.CYCLE_FLAG = CYCLE_FLAG;
    }

    public Boolean getORDER_FLAG() {
        return ORDER_FLAG;
    }

    public void setORDER_FLAG(Boolean ORDER_FLAG) {
        this.ORDER_FLAG = ORDER_FLAG;
    }

    public Number getCACHE_SIZE() {
        return CACHE_SIZE;
    }

    public void setCACHE_SIZE(Number CACHE_SIZE) {
        this.CACHE_SIZE = CACHE_SIZE;
    }

    public Long getLAST_NUMBER() {
        return LAST_NUMBER;
    }

    public void setLAST_NUMBER(Long LAST_NUMBER) {
        this.LAST_NUMBER = LAST_NUMBER;
    }
}
