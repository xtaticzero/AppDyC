/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.backing;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.dyc.generico.util.Utils;
import mx.gob.sat.siat.dyc.generico.util.common.AbstractPage;
import mx.gob.sat.siat.dyc.generico.util.common.SIATDataModel;
import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.BandejaDocumentosDTO;
import mx.gob.sat.siat.dyc.gestionsol.service.aprobardocumento.BandejaDocumentosService;
import mx.gob.sat.siat.dyc.util.common.Paginador;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.Procesos;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.log4j.Logger;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;


/**
 *
 * @author Softtek
 */
@ManagedBean(name = "bandejaAprobarSivadMorsa")
@ViewScoped
public class BandejaAprobarSivadMorsa extends AbstractPage {
    private static final Logger LOG = Logger.getLogger( BandejaAprobarSivadMorsa.class );
    
    @ManagedProperty(value="#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;
    
    @ManagedProperty(value="#{bandejaDocumentosSer}")
    private BandejaDocumentosService bandejaDocumentosService;
    
    @ManagedProperty(value="#{resumenDevMB}")
    private ResumenDevolucionMB resumenDevMB;
    
    private AccesoUsr accesoUsr;
    private int idPlantilla;
    
    private LazyDataModel<BandejaDocumentosDTO> listaSolicitudesAprobarSivadMorsa;
    private List<BandejaDocumentosDTO> registros;    
    
    private static final String NOMBRE_FILTRO_NUM_CONTROL = "numControl";
    private static final String NOMBRE_FILTRO_RFC = "rfcContribuyente";    
    
    private BandejaDocumentosDTO bandejaDocumentosSolDTO;
    
    private boolean varBotonCon;
    
    private String numControl;
    private String nombreDoc;
    
    @PostConstruct
    public void init (){
        inicializaUsuario();
        inicializaBandejaRegistros();
        
        inicializaControlesRolUsuario();
        listaSolicitudesAprobarSivadMorsa = generarListaSolicitudesAprobarSivadMorsa();
    }
    
    private void inicializaUsuario (){
        this.accesoUsr = serviceObtenerSesion.execute();
        Utils.validarSesion(accesoUsr, Procesos.DYC00024);
        
        idPlantilla = verificarQuePlantillaPoner(accesoUsr.getClaveSir());
    }
    
    private void inicializaBandejaRegistros (){
        setDataModel( new SIATDataModel() );
    }
    
    private void inicializaControlesRolUsuario (){
        /**activa o desactiva la presentacion de los controles dependiendo del rol del usuario*/
        varBotonCon = Boolean.TRUE;
    }
    
    private LazyDataModel<BandejaDocumentosDTO> generarListaSolicitudesAprobarSivadMorsa (){
        
        return new LazyDataModel<BandejaDocumentosDTO>(){
            private static final long serialVersionUID = 117509451819635906L;

            @Override
            public BandejaDocumentosDTO getRowData ( String rowKey ){

                if ( registros != null ){

                    for ( BandejaDocumentosDTO solicitud : registros ){

                        if ( solicitud.getNumControl().equals( rowKey ) ) {
                            return solicitud;
                        }
                    }

                }
                
                return null;
            }
            
            @Override
            public Object getRowKey ( BandejaDocumentosDTO solicitud ){
                return solicitud.getNumControl();
            }
            
            @Override
            public List<BandejaDocumentosDTO> load ( int first, int pageSize, String sortField,
                                                                SortOrder sortOrder, Map<String, String> filters ){
                Paginador paginador = new Paginador();
                paginador.setNPaginaActual( (first + pageSize) / pageSize );
                paginador.setNRegPorPagina( pageSize );
                
                String numControlFiltrar = getValor( filters, NOMBRE_FILTRO_NUM_CONTROL ).toUpperCase();
                String rfcFiltrar = getValor( filters, NOMBRE_FILTRO_RFC ).toUpperCase();
                
                try {
                    Long cuantos = bandejaDocumentosService.countBuscarBandejaSivadMorsa(idPlantilla, numControlFiltrar, rfcFiltrar, accesoUsr.getClaveSir());
                    setRowCount( cuantos.intValue() );
                    registros = bandejaDocumentosService.buscarBandejaSivadMorsa( idPlantilla, numControlFiltrar, rfcFiltrar, paginador, accesoUsr.getClaveSir());
                } catch ( SIATException error ){
                    LOG.error( error.getCause() );
                }
                                                
                return registros;
            }                        
            
            private String getValor ( Map<String, String> registros, String identificador ){
                String valor = (String) registros.get( identificador );

                if ( valor == null ){
                    String filtros = Arrays.toString( registros.keySet().toArray( new String [ registros.size() ] ) );
                    LOG.error( String.format( "No se encuentra el filtro: %s", identificador ) );
                    LOG.error( String.format( "indices[ %s ] - { %s }", registros.size(), filtros ) );

                    return "";
                }
                
                return valor;
            }

        };
    }
    
    private int verificarQuePlantillaPoner(String claveSir) {
        int numeroDePlantilla;
        
        /**VERIFICA SI ES DE HHIDROCARBUROS:*/
        if (claveSir.equals("81") || claveSir.startsWith("82")) {
            numeroDePlantilla = ConstantesDyCNumerico.VALOR_131;
        
        /**VERIFICA SI ES DE DE AGACE:*/
        } else if (claveSir.equals("80")) {
            numeroDePlantilla = ConstantesDyCNumerico.VALOR_114;
        
        /**VERIFICA SI ES DE GRANDES CONTRIBUYENTES:*/
        } else if (claveSir.equals("90") || claveSir.equals("91") || claveSir.equals("97")) {
            numeroDePlantilla = ConstantesDyCNumerico.VALOR_66;
        
        /**O DE AGAFF:*/
        } else {
            numeroDePlantilla = ConstantesDyCNumerico.VALOR_22;
            
        }
        return numeroDePlantilla;
    }
    
    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }
    
    public LazyDataModel<BandejaDocumentosDTO> getListaSolicitudesAprobarSivadMorsa (){
        return listaSolicitudesAprobarSivadMorsa;
    }
    
    public void setBandejaDocumentosSolDTO ( BandejaDocumentosDTO bandejaDocumentosSolDTO ){
        this.bandejaDocumentosSolDTO = bandejaDocumentosSolDTO;
    }

    public BandejaDocumentosDTO getBandejaDocumentosSolDTO (){
        return bandejaDocumentosSolDTO;
    }
    
    public void setVarBotonCon (boolean varBotonCon){
        this.varBotonCon = varBotonCon;
    }

    public boolean isVarBotonCon () {
        return varBotonCon;
    }
    
    public void onRowSelect ( SelectEvent evento ){
        habilitaConsultaDetalleRegistro();
    }
    
    private void habilitaConsultaDetalleRegistro (){
        varBotonCon = false;
    }
    
    public void setBandejaDocumentosService ( BandejaDocumentosService bandejaDocumentosSer ){
        this.bandejaDocumentosService = bandejaDocumentosSer;
    }

    public BandejaDocumentosService getBandejaDocumentosService () {
        return bandejaDocumentosService;
    }
    
    public String irAresumenAbonoNoEfectuado() {
        try {
            almacenaDatosRegistroSeleccionado();
            traeInformacionResumenRegistro();
        } catch (Exception e) {
            LOG.error( "irAresumenAbonoNoEfectuado() : " + e );
        }

        return "resumenDevolucion";
    }
    
    private void almacenaDatosRegistroSeleccionado (){
        numControl = bandejaDocumentosSolDTO.getNumControl();
        nombreDoc = bandejaDocumentosSolDTO.getNombreDocumento();
    }
    
    private void traeInformacionResumenRegistro (){
        resumenDevMB.setNumControl( numControl );
        resumenDevMB.setNombreDoc( nombreDoc );
        resumenDevMB.setBandejaDocumentosSolDTO( bandejaDocumentosSolDTO );
        resumenDevMB.setAccesoUsr( accesoUsr ); 
        resumenDevMB.setBanderaAprobarORevisionCentral( Boolean.FALSE );
        resumenDevMB.setEsAbonoNoEfectuado( Boolean.TRUE );
        resumenDevMB.init();
    }
    
    public void setResumenDevMB(ResumenDevolucionMB resumenDevMB) {
        this.resumenDevMB = resumenDevMB;
    }

    public ResumenDevolucionMB getResumenDevMB() {
        return resumenDevMB;
    }
        
}
