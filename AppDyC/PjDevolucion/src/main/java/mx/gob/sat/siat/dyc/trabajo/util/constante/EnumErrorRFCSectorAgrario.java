package mx.gob.sat.siat.dyc.trabajo.util.constante;

public enum EnumErrorRFCSectorAgrario {
    ERROR01("EnumErrorRFCSectorAgrario_01", "El RFC proporcionado ya existe en el padrón"),
    ERROR02("EnumErrorRFCSectorAgrario_02", "El RFC debe ser proporcionado"),
    ERROR03("EnumErrorRFCSectorAgrario_03", "El Nombre debe ser proporcionado"),
    ERROR04("EnumErrorRFCSectorAgrario_04", "RFC invalido"),
    ERROR05("EnumErrorRFCSectorAgrario_05", "El Nombre contiene caracteres no validos"),
    ERROR06("EnumErrorRFCSectorAgrario_06",
            "El RFC no tiene asociado un tipo de trámite 130 (IVA Sector Agropecuario)"),
    ERROR07("EnumErrorRFCSectorAgrario_07", "RFC dede contener 13 caracteres ");


    private String descripcion;
    private String id;

    private EnumErrorRFCSectorAgrario(String id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public String getId() {
        return id;
    }

}
