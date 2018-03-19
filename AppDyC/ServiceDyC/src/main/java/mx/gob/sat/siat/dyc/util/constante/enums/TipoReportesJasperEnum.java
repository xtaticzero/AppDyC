package mx.gob.sat.siat.dyc.util.constante.enums;

public enum TipoReportesJasperEnum {
    PDF(1, "pdf"),
    XLS(2, "xls"),
    XLSX(3, "xlsx"),
    DOCX(4, "docx"),
    PPTX(5, "pptx"),
    ODS(6, "ods"),
    ODT(7, "odt"),
    CSV(8, "csv"),
    RTF(9, "rtf"),
    TXT(10, "txt"),
    HTML(11, "html"),
    XML(12, "xml");

    private final Integer id;
    private final String descripcion;

    private TipoReportesJasperEnum(Integer i, String d) {
        id = i;
        descripcion = d;
    }

    public Integer getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
