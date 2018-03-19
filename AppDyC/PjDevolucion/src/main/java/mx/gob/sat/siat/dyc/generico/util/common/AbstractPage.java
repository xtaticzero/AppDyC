package mx.gob.sat.siat.dyc.generico.util.common;


/**
 * Managed Bean que sirve de base para todos los Bean de JSF
 *
 * @version
 */
public class AbstractPage {


    public AbstractPage() {
        super();
    }

    /** Indica si se muestra la tabla de mensajes globales */
    private boolean showMessageGlobal;

    /** Listado general de una consulta */
    private SIATDataModel<?> dataModel;

    private SIATDataModel<?> dataModelAnexo;
    
    private SIATDataModel<?> dataModelAnexoRequerido;

    private SIATDataModel<?> dataModelDocumento;
    
    private SIATDataModel<?> dataModelDocumentoRequerido;
    
    private SIATDataModel<?> dataModelDocsGestion;
    
    private SIATDataModel<?> dataModelArt22;
    
    /** verifica si se realizo la consulta */
    private boolean resultList;


    public boolean isShowMessageGlobal() {
        return showMessageGlobal;
    }

    public void setShowMessageGlobal(boolean showMessageGlobal) {
        this.showMessageGlobal = showMessageGlobal;
    }

    public SIATDataModel<?> getDataModel() {
        return dataModel;
    }

    public void setDataModel(SIATDataModel<?> dataModel) {
        this.dataModel = dataModel;
    }

    public boolean isResultList() {
        return resultList;
    }

    public void setResultList(boolean resultList) {
        this.resultList = resultList;
    }

    public void setDataModelAnexo(SIATDataModel<?> dataModelAnexo) {
        this.dataModelAnexo = dataModelAnexo;
    }

    public SIATDataModel<?> getDataModelAnexo() {
        return dataModelAnexo;
    }

    public void setDataModelDocumento(SIATDataModel<?> dataModelDocumento) {
        this.dataModelDocumento = dataModelDocumento;
    }

    public SIATDataModel<?> getDataModelDocumento() {
        return dataModelDocumento;
    }

    public void setDataModelAnexoRequerido(SIATDataModel<?> dataModelAnexoRequerido) {
        this.dataModelAnexoRequerido = dataModelAnexoRequerido;
    }

    public SIATDataModel<?> getDataModelAnexoRequerido() {
        return dataModelAnexoRequerido;
    }

    public void setDataModelDocumentoRequerido(SIATDataModel<?> dataModelDocumentoRequerido) {
        this.dataModelDocumentoRequerido = dataModelDocumentoRequerido;
    }

    public SIATDataModel<?> getDataModelDocumentoRequerido() {
        return dataModelDocumentoRequerido;
    }

    public void setDataModelDocsGestion(SIATDataModel<?> dataModelDocsGestion) {
        this.dataModelDocsGestion = dataModelDocsGestion;
    }

    public SIATDataModel<?> getDataModelDocsGestion() {
        return dataModelDocsGestion;
    }

    public void setDataModelArt22(SIATDataModel<?> dataModelArt22) {
        this.dataModelArt22 = dataModelArt22;
    }

    public SIATDataModel<?> getDataModelArt22() {
        return dataModelArt22;
    }
}
