package mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.backing;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;

import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import mx.gob.sat.siat.dyc.domain.resolucion.FilaGridTramitesBean;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.log4j.Logger;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;


/**
 * Huetzin Cruz Lozano
 */
public class LazyTramiteDataModel extends LazyDataModel<FilaGridTramitesBean>
{
    private static final Logger LOG = Logger.getLogger(LazyTramiteDataModel.class.getName());
    @SuppressWarnings("compatibility:3140856045408265778")
    private static final long serialVersionUID = -4925611420027929767L;

    @ManagedProperty(value = "#{mbBusqTrams}")
    private transient BusquedaTramitesMB manageBean;
    
    private transient List<FilaGridTramitesBean> datasource;
    
    private String mensajeBusqPalabraClave;
    
    private AccesoUsr accesoUsr;
    
    public LazyTramiteDataModel(BusquedaTramitesMB mb,AccesoUsr accesoUsr) {
        this.manageBean = mb;
        this.accesoUsr = accesoUsr;
    }

    private static final String CAMPO_ORDENAMIENTO_ADMINISTRACION = "administracion";
    private static final String CAMPO_ORDENAMIENTO_DICTAMINADOR   = "dictaminador";
    private static final String CAMPO_ORDENAMIENTO_RFC            = "rfc";
    private static final String CAMPO_ORDENAMIENTO_TRAMITE        = "tramite";
    private static final String CAMPO_ORDENAMIENTO_TIPOTRAMITE    = "tipoTramite";
    private static final String CAMPO_ORDENAMIENTO_IMPORTE    = "importe";

    public static final String CAMPO_ORDENAMIENTO_ADMINISTRACION_CONSULTA = "CLAVEADM";
    public static final String CAMPO_ORDENAMIENTO_DICTAMINADOR_CONSULTA   = "NUMEMPLEADODICT";
    public static final String CAMPO_ORDENAMIENTO_RFC_CONSULTA            = "RFCCONTRIBUYENTE";
    public static final String CAMPO_ORDENAMIENTO_TRAMITE_CONSULTA        = "IDTIPOSERVICIO";
    public static final String CAMPO_ORDENAMIENTO_TIPOTRAMITE_CONSULTA    = "IDTIPOTRAMITE";
    public static final String CAMPO_ORDENAMIENTO_TIPOTRAMITE_DEFAULT     = "NUMCONTROL";
    public static final String CAMPO_ORDENAMIENTO_IMPORTE_CONSULTA    = "IMPORTE";
    
    private static final String TIPO_ORDENAMIENTO_ASCENDENTE  = "ASCENDING";
    private static final String TIPO_ORDENAMIENTO_DESCENDENTE = "DESCENDING";

    public static final String TIPO_ORDENAMIENTO_ASCENDENTE_CONSULTA = "ASC";
    public static final String TIPO_ORDENAMIENTO_DESCENDENTE_CONSULTA = "DESC";
    public static final String TIPO_ORDENAMIENTO_DEFAULT_CONSULTA = "";

    @Override
    public FilaGridTramitesBean getRowData(String rowKey) {
        LOG.debug("getRowData(String rowKey) ->" + rowKey + "<-");
        for(FilaGridTramitesBean tramite : datasource) {
            if(tramite.getNumControl().equals(rowKey)){
                return tramite;
            }
        }

        return null;
    }
 
    @Override
    public Object getRowKey(FilaGridTramitesBean car) {
        LOG.debug("getRowKey ->" + car.getNumControl());
        return car.getNumControl();
    }
    
    @Override
    public void setRowIndex(int rowIndex) {
        /*
         * The following is in ancestor (LazyDataModel):
         */
        if (rowIndex == -1 || getPageSize() == 0) {
            super.setRowIndex(-1);
        }
        else{
            super.setRowIndex(rowIndex % getPageSize());
        }
    }

    @Override
    public List<FilaGridTramitesBean> load ( int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters ){
        LOG.debug("INICIO load");

        Map<String, Object> paramsBusqueda = new HashMap<String, Object>();

        paramsBusqueda.putAll( getParametrosBusquedaPaginacion( first, pageSize, sortField, sortOrder, filters ) );
        paramsBusqueda.putAll( manageBean.getParametrosBusquedaXFiltros() );

        Map<String, Object> resultBusqueda = null;
        
        try
        {
            resultBusqueda = manageBean.getDelegate().buscarXFiltros( paramsBusqueda, accesoUsr );
        }
        catch(Exception e)
        {
            this.desplegarError(FacesContext.getCurrentInstance(), "No se puede consultar los tramites de momento");
            LOG.error("No se puede crear la consulta debido al centro de costo , " + e.getMessage());
            return null;
        }
        List<FilaGridTramitesBean> filas = preparaResultadoBusquedaPresentacion( resultBusqueda );

        return filas;
    }

    private Map<String, Object> getParametrosBusquedaPaginacion ( int first, int pageSize, String sortField, 
                                                                    SortOrder sortOrder, Map<String, String> filters ){

        Map<String, Object> paramsBusqueda = new HashMap<String, Object>();

        LOG.debug("first ->" + first + "<-");
        LOG.debug("pageSize ->" + pageSize + "<-");
        LOG.debug("sortField ->" + sortField + "<-");
        LOG.debug("sortOrder ->" +sortOrder + "<-");
        LOG.debug("filters ->" + filters + "<-");

        String tipoOrdenamiento = getTipoOrdenamiento( sortOrder.toString() );
        String campoOrdenamiento = getCampoOrdenamiento( sortField );

        paramsBusqueda.put( "tamanioPagina", pageSize );

        try {

            paramsBusqueda.put( "numeroPagina", (first / pageSize) + 1 );

        } catch( ArithmeticException error ){
            LOG.debug("error " +error.getMessage());
            paramsBusqueda.put( "numeroPagina", 1 );
        }
        
        paramsBusqueda.put( "campoOrdenamiento", campoOrdenamiento );
        paramsBusqueda.put( "tipoOrdenamiento", tipoOrdenamiento );

        return paramsBusqueda;
    }

    private List<FilaGridTramitesBean> preparaResultadoBusquedaPresentacion ( Map<String, Object> resultBusqueda ){

        List<FilaGridTramitesBean> filas = new ArrayList<FilaGridTramitesBean>();

        Integer totalRegistros = 0;

        if ( resultBusqueda != null ){

            filas = (List<FilaGridTramitesBean>) resultBusqueda.get("tramites");
            totalRegistros = (Integer) resultBusqueda.get("totalRegs");
        }

        manageBean.setTotalRegs( totalRegistros );

        this.setRowCount( manageBean.getTotalRegs() );
        indicaTotalResultadosEncontrados();
        datasource = filas;
        
        manageBean.setDatasource( datasource );

        return filas;
    }

    private void indicaTotalResultadosEncontrados (){

        Integer totalRegistros = manageBean.getTotalRegs();
        StringBuilder mensajeTotalRegistros = new StringBuilder();

        if ( totalRegistros !=1 ){

            mensajeTotalRegistros
                .append( "Se encontraron " )
                .append( totalRegistros )
                .append( " trámites" );

        } else {

            mensajeTotalRegistros
                .append( "Se encontró " )
                .append( totalRegistros )
                .append( " trámite" );
        }

        manageBean.setLblTotalResultados( mensajeTotalRegistros.toString() );
    }

    private String getTipoOrdenamiento ( String tipoOrdenamiento ){

        if ( tipoOrdenamiento.equals( TIPO_ORDENAMIENTO_ASCENDENTE ) ){
            return TIPO_ORDENAMIENTO_ASCENDENTE_CONSULTA;
        }

        if ( tipoOrdenamiento.equals( TIPO_ORDENAMIENTO_DESCENDENTE ) ){
            return TIPO_ORDENAMIENTO_DESCENDENTE_CONSULTA;
        }

        return TIPO_ORDENAMIENTO_DEFAULT_CONSULTA;
    }

    private String getCampoOrdenamiento ( String sortField ){

        if ( CAMPO_ORDENAMIENTO_ADMINISTRACION.equals( sortField ) ){
            return CAMPO_ORDENAMIENTO_ADMINISTRACION_CONSULTA;
        }
        if ( CAMPO_ORDENAMIENTO_DICTAMINADOR.equals( sortField ) ){
            return CAMPO_ORDENAMIENTO_DICTAMINADOR_CONSULTA;
        }
        if ( CAMPO_ORDENAMIENTO_RFC.equals( sortField ) ){
            return CAMPO_ORDENAMIENTO_RFC_CONSULTA;
        }
        if ( CAMPO_ORDENAMIENTO_TRAMITE.equals( sortField ) ){
            return CAMPO_ORDENAMIENTO_TRAMITE_CONSULTA;
        }
        if ( CAMPO_ORDENAMIENTO_TIPOTRAMITE.equals( sortField ) ){
            return CAMPO_ORDENAMIENTO_TIPOTRAMITE_CONSULTA;
        }
        if ( CAMPO_ORDENAMIENTO_IMPORTE.equals( sortField ) ){
            return CAMPO_ORDENAMIENTO_IMPORTE_CONSULTA;
        }

        return CAMPO_ORDENAMIENTO_TIPOTRAMITE_DEFAULT;
    }

    public BusquedaTramitesMB getManageBean() {
        return manageBean;
    }

    public void setManageBean(BusquedaTramitesMB manageBean) {
        this.manageBean = manageBean;
    }

    public String getMensajeBusqPalabraClave() {
        return mensajeBusqPalabraClave;
    }

    public void setMensajeBusqPalabraClave(String mensajeBusqPalabraClave) {
        this.mensajeBusqPalabraClave = mensajeBusqPalabraClave;
    }

    public void desplegarError(FacesContext fc, String msg) 
    {
        fc.addMessage("msgError", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", msg));
        fc.renderResponse();
    }
}
