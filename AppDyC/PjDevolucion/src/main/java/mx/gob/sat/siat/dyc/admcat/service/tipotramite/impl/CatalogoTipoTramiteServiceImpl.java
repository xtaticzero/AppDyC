package mx.gob.sat.siat.dyc.admcat.service.tipotramite.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mx.gob.sat.siat.dyc.admcat.dto.tipotramite.ConsultaTipoTramiteVO;
import mx.gob.sat.siat.dyc.admcat.dto.tipotramite.DatosTipoTramiteVO;
import mx.gob.sat.siat.dyc.admcat.dto.tipotramite.GuardadoTipoTramiteVO;
import mx.gob.sat.siat.dyc.admcat.service.tipotramite.CatalogoTipoTramiteService;
import mx.gob.sat.siat.dyc.admcat.service.tipotramite.VerificarInsertarOActualizarService;
import mx.gob.sat.siat.dyc.admcat.web.controller.bean.support.DyccTipoTramiteVO;
import mx.gob.sat.siat.dyc.admcat.web.controller.bean.utility.InformacionAModificarTipoTramite;
import mx.gob.sat.siat.dyc.catalogo.dao.DyccRolDAO;
import mx.gob.sat.siat.dyc.catalogo.dao.DyccSubtramiteDAO;
import mx.gob.sat.siat.dyc.catalogo.dao.DyccUnidadTramiteDAO;
import mx.gob.sat.siat.dyc.dao.anexo.DyccAnexoTramiteDAO;
import mx.gob.sat.siat.dyc.dao.anexo.DyccMatrizAnexosDAO;
import mx.gob.sat.siat.dyc.dao.concepto.DyccConceptoDAO;
import mx.gob.sat.siat.dyc.dao.concepto.DyccImpuestoDAO;
import mx.gob.sat.siat.dyc.dao.documento.DyccInfoARequerirDAO;
import mx.gob.sat.siat.dyc.dao.documento.DyccInfoTramiteDAO;
import mx.gob.sat.siat.dyc.dao.movsaldo.DyccOrigenSaldoDAO;
import mx.gob.sat.siat.dyc.dao.periodo.DycaTipoPeriodoTtDAO;
import mx.gob.sat.siat.dyc.dao.periodo.DyccTipoPeriodoDAO;
import mx.gob.sat.siat.dyc.dao.rol.DyccTramiteRolDAO;
import mx.gob.sat.siat.dyc.dao.saldoicep.DyccSubOrigSaldoDAO;
import mx.gob.sat.siat.dyc.dao.saldoicep.DyccSubSaldoTramDAO;
import mx.gob.sat.siat.dyc.dao.tiposerv.DyccTipoPlazoDAO;
import mx.gob.sat.siat.dyc.dao.tiposerv.IDyccTipoServicioDAO;
import mx.gob.sat.siat.dyc.dao.tipotramite.DycaOrigenTramiteDAO;
import mx.gob.sat.siat.dyc.dao.tipotramite.DyccTipoTramiteDAO;
import mx.gob.sat.siat.dyc.dao.tipotramite.DyccTtSubTramiteDAO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccUnidadTramiteDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.vistas.dao.AdmcUnidAdmvaTipoDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Jesus Alfredo Hernandez Orozco
 * @since 08/12/2014
 */
@Service(value = "catalogoTipoTramiteService")
public class CatalogoTipoTramiteServiceImpl implements CatalogoTipoTramiteService {

    private static final String PARENTESIS_DERECHO = ")";

    @Autowired
    private DyccTipoTramiteDAO dyccTipoTramiteDAO;
    
    @Autowired
    private IDyccTipoServicioDAO iDyccTipoServicioDAO;
    
    @Autowired
    private DyccOrigenSaldoDAO dyccOrigenSaldoDAO;
        
    @Autowired
    private DyccTipoPlazoDAO dyccTipoPlazoDAO;
    
    @Autowired
    private DyccImpuestoDAO dyccImpuestoDAO;
    
    @Autowired
    private DyccConceptoDAO dyccConceptoDAO;
    
    @Autowired
    private DyccRolDAO dyccRolDAO;
    
    @Autowired
    private DyccMatrizAnexosDAO dyccMatrizAnexosDAO;
    
    @Autowired
    private DyccInfoARequerirDAO dyccInfoARequerirDAO;
    
    @Autowired
    private DyccSubtramiteDAO dyccSubtramiteDAO;
    
    @Autowired
    private DyccSubOrigSaldoDAO dyccSubOrigSaldoDAO;
    
    @Autowired
    private DyccTipoPeriodoDAO dyccTipoPeriodoDAO;
    
    @Autowired
    private DyccUnidadTramiteDAO dyccUnidadTramite;
    
    @Autowired
    private DyccTramiteRolDAO dyccTramiteRolDAO;
    
    @Autowired
    private DyccAnexoTramiteDAO dyccAnexoTramiteDAO;
    
    @Autowired
    private DyccInfoTramiteDAO dyccInfoTramiteDAO;
    
    @Autowired
    private DyccTtSubTramiteDAO dyccTtSubTramiteDAO;
    
    @Autowired
    private DyccSubSaldoTramDAO dyccSubSaldoTramDAO;
    
    @Autowired
    private DycaTipoPeriodoTtDAO dycaTipoPeriodoTtDAO;
    
    @Autowired
    private DycaOrigenTramiteDAO dycaOrigenTramiteDAO;
    
    @Autowired
    private AdmcUnidAdmvaTipoDAO admcUnidAdmvaTipoDAO;
        
    @Autowired
    private VerificarInsertarOActualizarService verificarInsertarOActualizarService;
        
    /**
     * Consulta los tipos de tramite dados de alta en base de datos.
     *
     * @return lista de los tipos de tramite dados de alta en base de datos.
     */
    @Transactional(readOnly =true)
    @Override
    public List<DyccTipoTramiteVO> obtieneTipoTramite(long idOrigenSaldo) throws SIATException{
        
        List<DyccTipoTramiteDTO> listaTipoTramite = dyccTipoTramiteDAO.obtieneTipoTramite(idOrigenSaldo);
        List<DyccTipoTramiteVO> listaTipoTramiteVO = new ArrayList<DyccTipoTramiteVO>();
        
        for (DyccTipoTramiteDTO objeto : listaTipoTramite) {
            if(objeto.getFechaFin()!= null) {
                listaTipoTramiteVO.add(new DyccTipoTramiteVO(objeto, "Inactivo"));
            } else {
                listaTipoTramiteVO.add(new DyccTipoTramiteVO(objeto, "Activo"));
            }
        }
        return listaTipoTramiteVO;
    }
    
    
    /**
     * Consulta todos los datos necesarios para las altas o modificaciones de los tipos de tramite
     *
     * @return 
     */
    @Transactional(readOnly = true)
    @Override
    public DatosTipoTramiteVO listarDatosParaTipoDeTramite() throws SIATException {
        
        DatosTipoTramiteVO objetoTipoTramite = new DatosTipoTramiteVO();
        
        objetoTipoTramite.setListaTipoServicio(iDyccTipoServicioDAO.seleccionar());   
        objetoTipoTramite.setListaUnidadAdmtvaTipo(admcUnidAdmvaTipoDAO.consultar());
        objetoTipoTramite.setListaTipoPlazo(dyccTipoPlazoDAO.consultar());
        objetoTipoTramite.setListaImpuestos(dyccImpuestoDAO.obtieneImpuestos());
        objetoTipoTramite.setListaRoles(dyccRolDAO.consultarRoles());
        objetoTipoTramite.setListaMatrizAnexos(dyccMatrizAnexosDAO.seleccionar());
        objetoTipoTramite.setListaInfoARequerir(dyccInfoARequerirDAO.seleccionar());
        objetoTipoTramite.setListaSubtramites(dyccSubtramiteDAO.obtieneSubtramite());
        objetoTipoTramite.setListaSubOrigenesSaldo(dyccSubOrigSaldoDAO.consultarSuborigenesDeSaldos());
        objetoTipoTramite.setListaTipoPeriodo(dyccTipoPeriodoDAO.obtieneTipoPeriodo());
        
        return objetoTipoTramite;
    }
    
    /**
     * Guarda un nuevo tipo de tramite en todas las tablas asociadas.
     *
     * @param objeto
     * @throws SIATException
     */
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW, rollbackFor = SIATException.class)
    @Override
    public void guardarNuevoTramite(GuardadoTipoTramiteVO objeto) throws SIATException {
        dyccTipoTramiteDAO.insertar(objeto.getTipoTramite());
        verificarInsertarOActualizarService.verificarUnidadTramite(objeto.getUnidadTramite());
        verificarInsertarOActualizarService.verificarTramiteRol(objeto.getTramiteRol());
        verificarInsertarOActualizarService.verificarAnexoTramite(objeto.getAnexoTramite());
        verificarInsertarOActualizarService.verificarInfoTramite(objeto.getInfoTramite());
        verificarInsertarOActualizarService.verificarTtSubTramite(objeto.getTtSubtramite());
        verificarInsertarOActualizarService.verificarSubSaldoTram(objeto.getSubSaldoTram());
        verificarInsertarOActualizarService.verificarTipoPeriodoTt(objeto.getTipoPeriodoTt());
        dycaOrigenTramiteDAO.insertar(objeto.getOrigenTramite());
    }
    
    /**
     * Retorna los origenes del saldo a partir del tipo de servicio
     *
     * @param tipoServicio
     * @return
     * @throws SIATException
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<DyccOrigenSaldoDTO> obtenerOrigenSaldo(Integer tipoServicio) throws SIATException {
        return dyccOrigenSaldoDAO.consultaXTipoServicio(tipoServicio);
    }
    
    /**
     * Al eliminar el tramite lo unico que hace la aplicacion es poner fechaFin del tramite igual a nulo.
     *
     * @param idTipoTramite
     */
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW, rollbackFor = SIATException.class)
    @Override
    public void eliminarTramite(Integer idTipoTramite, Integer bandera) throws SIATException {
        dyccTipoTramiteDAO.eliminarTramite(idTipoTramite, bandera);
    }
    
    /**
     * Consulta los datos de un tramite a partir del id del tramite. 
     * Este metodo se utiliza para la pantalla de consulta de tramites.
     *
     * @param idTipoTramite identificador del tramite con el cual se van a buscar los datos en BD.
     * @return Un objeto de todos los datos que se asocian con el ID del tramite.
     * @throws SIATException
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public ConsultaTipoTramiteVO consultarDatosDeUnTramite(Integer idTipoTramite) throws SIATException {
        
        ConsultaTipoTramiteVO objeto         = new ConsultaTipoTramiteVO();
        GuardadoTipoTramiteVO objetoGuardado = cargarDatosGuardadosEnBase(idTipoTramite);
        
        if(objetoGuardado!=null) {
            objeto.setTipoServicio(iDyccTipoServicioDAO.encontrar(objetoGuardado.getOrigenTramite().getDycaServOrigenDTO().getDyccTipoServicioDTO().getIdTipoServicio()));   
            objeto.setOrigenSaldo(dyccOrigenSaldoDAO.obtenerOrigenSaldoPorIdTipoTramite(objetoGuardado.getTipoTramite().getIdTipoTramite()));
            objeto.setConcepto(dyccConceptoDAO.obtieneConceptoPorIdTramite(objetoGuardado.getTipoTramite().getIdTipoTramite()));
            objeto.setImpuesto(dyccImpuestoDAO.seleccionarXconcepto(objeto.getConcepto())); 
            objeto.setTipoPlgazo(dyccTipoPlazoDAO.consultar(objetoGuardado.getTipoTramite().getDyccTipoPlazoDTO().getIdTipoPlazo()));
            objeto.setRequiereCCI((objetoGuardado.getTipoTramite().getRequiereCCI())?"SI":"NO");
            objeto.setResolucionAutomatica((objetoGuardado.getTipoTramite().getResolAutomatica()==1)?"SI":"NO");       
            objeto.setListaUnidadAdmvaTipo(admcUnidAdmvaTipoDAO.consultarXID(buscarTipoUnidadAdmva(objetoGuardado.getUnidadTramite())));
            objeto.setGuardadoTipoTramiteVO(objetoGuardado);
        }
        return objeto;
    }
    
    /**
     * Consulta los datos que se dieron de alta en el tramite para la pantalla de modificacion de tramites.
     *
     * @param idTipoTramite
     * @return
     * @throws SIATException
     */
    @Override
    public DatosTipoTramiteVO consultarDatosDeUnTramiteParaModificacion(Integer idTipoTramite) throws SIATException {
        DatosTipoTramiteVO objeto = new DatosTipoTramiteVO();
        try {
            DyccTipoTramiteDTO tipoTramite = dyccTipoTramiteDAO.encontrar(idTipoTramite);
            
            if (tipoTramite!=null) {    
                objeto.setTipoTramite(tipoTramite);
                objeto.setImpuesto(dyccImpuestoDAO.seleccionarXconcepto(tipoTramite.getDyccConceptoDTO()));
            }
            objeto.setListaUnidadAdmtvaTipo(admcUnidAdmvaTipoDAO.consultarXIDConFechaFin(idTipoTramite));
            objeto.setListaRoles(dyccRolDAO.consultarXIdTipoTramiteConFechaFin(idTipoTramite));
            objeto.setListaMatrizAnexos(dyccMatrizAnexosDAO.consultarXIdTipoTramiteConFechaFin(idTipoTramite));
            objeto.setListaInfoARequerir(dyccInfoARequerirDAO.consultarXIdTipoTramiteConFechaFin(idTipoTramite));
            objeto.setListaSubtramites(dyccSubtramiteDAO.consultaXIdTipoTramiteConFechaFin(idTipoTramite));
            objeto.setListaSubOrigenesSaldo(dyccSubOrigSaldoDAO.obtieneSubOrigSaldo(idTipoTramite));
            objeto.setListaTipoPeriodo(dyccTipoPeriodoDAO.consultaXIdTipoTramiteConFechaFin(idTipoTramite));
            objeto.setListaOrigenTramite(dycaOrigenTramiteDAO.selectXTipoTramite(idTipoTramite));
            
        } catch (Exception e) {
            throw new SIATException(e);
        }
        return objeto;
    }
    
    /**
     * Obtiene los datos asociados de un tramite a partir de su ID
     *
     * @param idTipoTramite
     * @return
     */
    private GuardadoTipoTramiteVO cargarDatosGuardadosEnBase(Integer idTipoTramite) throws SIATException {
        GuardadoTipoTramiteVO objetoGuardado = new GuardadoTipoTramiteVO();
        DyccTipoTramiteDTO    tipoTramite    = new DyccTipoTramiteDTO();
        
        tipoTramite.setIdTipoTramite(idTipoTramite);
        
        objetoGuardado.setTipoTramite(dyccTipoTramiteDAO.encontrar(idTipoTramite));
        objetoGuardado.setUnidadTramite(dyccUnidadTramite.consultarUnidadTramiteXIdTipoTramite(idTipoTramite));
        objetoGuardado.setTramiteRol(dyccTramiteRolDAO.consultarTramiteRolXIDTipoTramite(idTipoTramite));
        objetoGuardado.setAnexoTramite(dyccAnexoTramiteDAO.selecXTipotramite(tipoTramite));
        objetoGuardado.setInfoTramite(dyccInfoTramiteDAO.selecXTipotramite(tipoTramite));
        objetoGuardado.setTtSubtramite(dyccTtSubTramiteDAO.consultarXIdTipoTramite(idTipoTramite));
        objetoGuardado.setSubSaldoTram(dyccSubSaldoTramDAO.consultarXIdTipoTramite(idTipoTramite));
        objetoGuardado.setOrigenTramite(dycaOrigenTramiteDAO.selectXTipoTramite(idTipoTramite).get(0));
        objetoGuardado.setTipoPeriodoTt(dycaTipoPeriodoTtDAO.consultarXIdTipoTramite(idTipoTramite));
        
        tipoTramite = null;
        return objetoGuardado;
    }
    
    /**
     * Genera una lista de indices los cuales se utilizaran para generar la consulta que retornara el tipo de unidades administrativas
     *
     * @param unidadesTramite
     * @return
     */
    private String buscarTipoUnidadAdmva(List<DyccUnidadTramiteDTO> unidadesTramite) {
        
        String elementosIn=null;
        Iterator it = unidadesTramite.iterator();
        DyccUnidadTramiteDTO dyccUnidadTramiteDTO = null;
        StringBuilder cadenaTemporal = new StringBuilder();
        cadenaTemporal.append("(");
        
        while(it.hasNext()) {
            dyccUnidadTramiteDTO = (DyccUnidadTramiteDTO)it.next();
            cadenaTemporal.append(dyccUnidadTramiteDTO.getIdTipoUnidadAdmva().toString()).append(",");
        }
        elementosIn =cadenaTemporal.toString();
        elementosIn+=PARENTESIS_DERECHO;
        elementosIn = elementosIn.replaceAll("\\,\\)", PARENTESIS_DERECHO);
        cadenaTemporal=null;
        return elementosIn;
    }

    /**
     * Obtiene una lista de los conceptos a partir del impuesto
     *
     * @param impuesto
     * @return
     * @throws SIATException
     */
    @Override
    public List<DyccConceptoDTO> obtenerConcepto(Integer impuesto) throws SIATException {
        List<DyccConceptoDTO> lista = null;
        try {
            lista = dyccConceptoDAO.obtieneConceptoPorImpuesto(impuesto);
            
        } catch(Exception e) {
            throw new SIATException(e);
        }
        return lista;
    }

    /**
     * Modifica un tramite dado de alta previamente en base de datos
     *
     * @param objeto
     * @throws SIATException
     */
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW, rollbackFor = SIATException.class)
    @Override
    public void modificarTramite(InformacionAModificarTipoTramite objeto, GuardadoTipoTramiteVO datosTramite) throws SIATException {
        
        dyccTipoTramiteDAO.actualizarTipoTramite(datosTramite.getTipoTramite());
        dycaOrigenTramiteDAO.actualizar(datosTramite.getOrigenTramite());
        
        verificarInsertarOActualizarService.verificarUnidadTramite(objeto.getDatosNuevos().getUnidadTramite());
        verificarInsertarOActualizarService.verificarTramiteRol(objeto.getDatosNuevos().getTramiteRol());
        verificarInsertarOActualizarService.verificarAnexoTramite(objeto.getDatosNuevos().getAnexoTramite());
        verificarInsertarOActualizarService.verificarInfoTramite(objeto.getDatosNuevos().getInfoTramite());
        verificarInsertarOActualizarService.verificarTtSubTramite(objeto.getDatosNuevos().getTtSubtramite());
        verificarInsertarOActualizarService.verificarSubSaldoTram(objeto.getDatosNuevos().getSubSaldoTram());
        verificarInsertarOActualizarService.verificarTipoPeriodoTt(objeto.getDatosNuevos().getTipoPeriodoTt());
        
        verificarInsertarOActualizarService.verificarFechaFinUnidadTramite(objeto.getDatosOriginales().getUnidadTramite(), objeto.getDatosNuevos().getUnidadTramite());
        verificarInsertarOActualizarService.verificarFechaFinTramiteRol(objeto.getDatosOriginales().getTramiteRol(), objeto.getDatosNuevos().getTramiteRol());
        verificarInsertarOActualizarService.verificarFechaFinAnexoTramite(objeto.getDatosOriginales().getAnexoTramite(), objeto.getDatosNuevos().getAnexoTramite());
        verificarInsertarOActualizarService.verificarFechaFinInfoTramite(objeto.getDatosOriginales().getInfoTramite(), objeto.getDatosNuevos().getInfoTramite());
        verificarInsertarOActualizarService.verificarFechaFinTtSubTramite(objeto.getDatosOriginales().getTtSubtramite(), objeto.getDatosNuevos().getTtSubtramite());
        verificarInsertarOActualizarService.verificarFechaFinSubSaldoTram(objeto.getDatosOriginales().getSubSaldoTram(), objeto.getDatosNuevos().getSubSaldoTram());
        verificarInsertarOActualizarService.verificarFechaFinTipoPeriodoTt(objeto.getDatosOriginales().getTipoPeriodoTt(), objeto.getDatosNuevos().getTipoPeriodoTt());
    }

    /**
     * Valida si el ID del tramite ingresado en pantalla que se intenta dar de alta existe en base de datos
     *
     * @param idTramite
     * @return
     * @throws SIATException
     */
    @Override
    public Integer validarIdTramite(Integer idTramite) throws SIATException {
        return dyccTipoTramiteDAO.validarIdTramite(idTramite);
    }
    
    public DyccTipoTramiteDTO obtieneTipoTramitePorId(int idTipoTramite) throws SIATException
    {
        return  dyccTipoTramiteDAO.obtieneTipoTramite(idTipoTramite);
    }
    
    public DyccTipoTramiteDTO obtieneTipoTramiteID(int idTipoTrmite)
    {
        return  dyccTipoTramiteDAO.obtieneTipoTramiteID(idTipoTrmite);
    }
    
}
