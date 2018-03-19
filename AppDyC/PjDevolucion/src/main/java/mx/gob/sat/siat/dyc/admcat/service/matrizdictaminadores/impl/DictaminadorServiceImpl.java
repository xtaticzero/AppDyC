/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.admcat.service.matrizdictaminadores.impl;

import java.math.BigInteger;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.mat.dyc.empleados.service.SatAgsEmpleadosMVService;
import mx.gob.sat.siat.dyc.admcat.dto.matrizdictaminadores.EmpleadoVO;
import mx.gob.sat.siat.dyc.admcat.service.matrizdictaminadores.DictaminadorService;
import mx.gob.sat.siat.dyc.admcat.service.matrizdictaminadores.EmpleadoService;
import mx.gob.sat.siat.dyc.dao.tiposerv.IDycpServicioDAO;
import mx.gob.sat.siat.dyc.dao.usuario.DyccDictaminadorDAO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;
import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidadAdmvaDTO;
import mx.gob.sat.siat.dyc.generico.util.common.SessionHandler;
import mx.gob.sat.siat.dyc.service.DyccMensajeUsrService;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.RegistroPistasAuditoria;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesIds;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC3;
import mx.gob.sat.siat.dyc.util.constante2.ConstatesMsgDictaminadores;
import mx.gob.sat.siat.dyc.vistas.dao.AdmcUnidadAdmvaDAO;
import mx.gob.sat.siat.dyc.vistas.service.AdmcUnidadAdmvaService;
import mx.gob.sat.siat.dyc.vo.DictaminadorVO;
import mx.gob.sat.siat.dyc.vo.PistaAuditoriaVO;
import mx.gob.sat.siat.modelo.dao.SegbMovimientosDAO;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Implementaci&oacute;n Service para modulo MantenerMatrizDictaminadores.
 * @author Luis Fernando Barrios Quiroz [ LuFerMX ]
 * @since 0.1
 *
 * @date Julio 29, 2014
 */
@Service("dictaminadorService")
@Transactional
public class DictaminadorServiceImpl implements DictaminadorService {
    private static final Logger LOG = Logger.getLogger(DictaminadorServiceImpl.class);

    @Autowired(required = true)
    private DyccDictaminadorDAO daoDictaminador;

    @Autowired(required = true)
    private EmpleadoService empleadoService;
    @Autowired(required = true)
    private DyccMensajeUsrService dyccMensajeUsrService;
    @Autowired(required = true)
    private AdmcUnidadAdmvaService admcUnidadAdmvaService;
    @Autowired(required = true)
    private IDycpServicioDAO iDycpServicioDAO;
    @Autowired
    private SegbMovimientosDAO segbMovimientosDAO;
    @Autowired(required = true)
    private SessionHandler sessionHandler;
    @Autowired(required = true)
    private RegistroPistasAuditoria registroPistasAuditoria;
    
    @Autowired(required = false)
    private SatAgsEmpleadosMVService satAgsEmpleadosMVService;

    @Autowired
    private AdmcUnidadAdmvaDAO daoUnidadAdmva;

    private List<DictaminadorVO> listaDictaminador;

    private StringBuilder strMensaje;
    private StringBuilder observaciones;
    private String operacion;

    private DictaminadorVO dictaminador;
    private EmpleadoVO empleado;
    private AdmcUnidadAdmvaDTO admin;

    private SegbMovimientoDTO movimientoDTO;
    private BigInteger folio;

    private Map<String, String> remplaceMensajes;
    private PistaAuditoriaVO pistaAuditoria;
    
    public DictaminadorServiceImpl() {
        super();

        strMensaje = new StringBuilder();
        observaciones = new StringBuilder();

        remplaceMensajes = new HashMap<String, String>();
        pistaAuditoria = new PistaAuditoriaVO();
        pistaAuditoria.setIdProceso(mx.gob.sat.siat.dyc.util.constante.Procesos.DYC00002);
        pistaAuditoria.setIdGrupoOperacion(ConstatesMsgDictaminadores.CU_MATRIZ_DICTAMINADOR);
    }

    @Override
    @Transactional(readOnly = false)
    public List<DictaminadorVO> obtenerDictaminadores(DictaminadorVO dictaminador) throws SQLException {
        try {
            listaDictaminador = this.daoDictaminador.obtenerDictaminadores(dictaminador, Boolean.TRUE);
        } catch (Exception se) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + se.getMessage() + ConstantesDyC1.TEXTO_3_ERROR_DAO
                    + ExtractorUtil.ejecutar(dictaminador));
            listaDictaminador = Collections.emptyList();
        }

        return this.listaDictaminador;
    }

    @Override
    @Transactional(readOnly = false)
    public List<DictaminadorVO> obtenerDictaminadoresActivos ( DictaminadorVO dictaminador ) throws SQLException {
        try {
            listaDictaminador = daoDictaminador.obtenerDictaminadoresActivos( dictaminador );
        } catch ( Exception se ){
            listaDictaminador = Collections.emptyList();
        }

        return listaDictaminador;
    }

    @Transactional(readOnly = false)
    @Override
    public EmpleadoVO consultarInformacionEmpleado(EmpleadoVO empleado, Integer cCostosUsrSession,
            boolean isNum, Integer claveAdmon) {
        EmpleadoVO empleadoVO = new EmpleadoVO();

        try {
            String estado = satAgsEmpleadosMVService.getEstatusEmpleado(isNum
                    ? empleado.getNumEmpleado()
                    : empleado.getRfc()
            );

            if (estado != null) {
                if (estado.equals(ConstantesDyC.ESTADO_EMPLEADO_BAJA_AGS)) {

                    this.getStrMensaje().append(ConstantesDyC3.MENSAJE_MSG019);

                    empleadoVO = new EmpleadoVO();
                    empleadoVO.setValidacionEstado(2);

                } else {

                    empleadoVO = empleadoService.consultaInfoEmpleadoSinAdm(empleado);

                    DictaminadorVO dictaminadorVo = getDictaminador(Integer.parseInt(empleadoVO.getNumEmpleado()));

                    if (dictaminadorVo != null ) {

                        String nombreUnidadAdministrativa =  obtenerNombreUnidadAdmva( dictaminadorVo.getClaveAdm() );
                        empleadoVO.setCentroCostoDescr254(nombreUnidadAdministrativa);
                        empleadoVO.setCcosto(cCostosUsrSession);

                        if (dictaminadorVo.getActivo().equals(ConstantesDyCNumerico.VALOR_1)
                                && !empleadoVO.getClaveAdm().equals(claveAdmon)) {

                            empleadoVO.setValidacionEstado(ConstantesDyCNumerico.VALOR_1);
                        }else{
                            empleadoVO.setValidacionEstado(ConstantesDyCNumerico.VALOR_3);
                        }
                    } else {
                        empleadoVO.setValidacionEstado(ConstantesDyCNumerico.VALOR_3);
                    }

                }
            } else {
                empleadoVO.setValidacionEstado(0);
            }

        } catch (SIATException ex) {
            LOG.error("Ocurrio un error al consultar la informacion del empleado");
            ManejadorLogException.getTraceError(ex);
        }

        return empleadoVO;
    }

    private DictaminadorVO getDictaminador ( Integer numEmpleado ){
        try {
            
            return daoDictaminador.obtenerDyccDictaminador( numEmpleado );
        } catch ( SQLException  error ){
            LOG.info( error.getMessage() );
        }
        
        return null;
    }

    @Override
    public String obtenerNombreUnidadAdmva( Integer claveAdmon )
    {
        try{
            List<AdmcUnidadAdmvaDTO> unidades = daoUnidadAdmva.selecXClavesir( claveAdmon );
            return unidades.get(0).getNombre();
        }
        catch(Exception e){
            LOG.error("Error en metodo obtenerNombreUnidadAdmva; mensaje ->" + e.getMessage());
            ManejadorLogException.getTraceError(e);
        }

        return "";
    }

    public void insertarDictaminador(EmpleadoVO empleado) {
        this.getStrMensaje().setLength(ConstantesDyC1.CERO);

        try {
            this.dictaminador = new DictaminadorVO();
            // SET DICTAMINADOR A INSERTAR
            this.dictaminador.setNumEmpleado(Integer.parseInt(empleado.getNumEmpleado()));
            this.dictaminador.setActivo(empleado.getActivo());
            this.dictaminador.setObservaciones(empleado.getDictaminador().getObservaciones());
            this.dictaminador.setClaveDepto(empleado.getClaveDepto());
            this.dictaminador.setNombre(empleado.getNombre());
            this.dictaminador.setApellidoMaterno(empleado.getMaterno());
            this.dictaminador.setApellidoPaterno(empleado.getPaterno());
            this.dictaminador.setEmail(empleado.getEmail());
            this.dictaminador.setRfc(empleado.getRfc());
            this.dictaminador.setNombreCompleto(empleado.getNombreCompleto());
            this.dictaminador.setCentroCosto(empleado.getCcosto());
            this.dictaminador.setCentroCostoDescr254(empleado.getCentroCostoDescr254());

            this.dictaminador.setDescComision(empleado.getDescComision());
            this.dictaminador.setClaveAdm(empleado.getClaveAdm());
            this.dictaminador.setCentroCosto(empleado.getCcosto());
            this.dictaminador.setUn(empleado.getUn());

            daoDictaminador.insertarDictaminador(this.dictaminador);
            this.pistaAuditoria.setMovimiento((int) ConstatesMsgDictaminadores.OPE_MD_ADD_B);
            remplaceMensajes.put(ConstantesDyC1.DB_NOMBRE_ADM, this.dictaminador.getCentroCostoDescr254() + "");
            remplaceMensajes.put(ConstantesDyC1.DB_NUM_EMPLEADO, this.dictaminador.getNumEmpleado().toString());
            remplaceMensajes.put(ConstantesDyC1.MD_NOMBRE_DICT, this.dictaminador.getNombreCompleto());

            pistaAuditoria.setIdentificador(dictaminador.getRfc());
            pistaAuditoria.setRemplaceMensajes(this.remplaceMensajes);
            pistaAuditoria.setIdMensaje(ConstatesMsgDictaminadores.MSG_MD_ADI_ADD);
            registroPistasAuditoria.registrarPistaAuditoria(this.pistaAuditoria);

            this.strMensaje.append(this.registroPistasAuditoria.getStrMensaje());
        } catch (SQLException se) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + se.getMessage() + ConstantesDyC1.TEXTO_3_ERROR_DAO
                    + ExtractorUtil.ejecutar(dictaminador), se);
        } catch (SIATException siat) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + siat.getMessage() + ConstantesDyC1.TEXTO_3_ERROR_DAO
                    + ExtractorUtil.ejecutar(dictaminador), siat);
        }
    }

    /**
     * @param dictaminador
     */
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void actualizarDictaminador(DictaminadorVO dictaminador) {
        this.getStrMensaje().setLength(ConstantesDyC1.CERO);
        try {
            daoDictaminador.actualizarDictaminador(dictaminador);

            pistaAuditoria.setIdMensaje(ConstatesMsgDictaminadores.MSG_MD_ADI_MODI);
            pistaAuditoria.setMovimiento(ConstatesMsgDictaminadores.OPE_MD_MOD_B);
            pistaAuditoria.setIdentificador(dictaminador.getRfc());

            registroPistasAuditoria.registrarPistaAuditoria(this.pistaAuditoria);

        } catch (Exception se) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + se.getMessage() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(dictaminador), se);
        }
    }

    public void eliminarDictaminador(DictaminadorVO dictaminador) {
        try {
            dictaminador.setActivo(ConstantesDyC1.CERO);
            daoDictaminador.actualizarDictaminador(dictaminador);

            // REGISTRA PISTA AUDITORIA
            this.pistaAuditoria.setIdentificador(dictaminador.getRfc());
            this.pistaAuditoria.setIdMensaje(ConstantesIds.MSG_118);
            this.pistaAuditoria.setMovimiento(ConstatesMsgDictaminadores.OPE_MD_DEL_B);
            this.registroPistasAuditoria.registrarPistaAuditoria(this.pistaAuditoria);

        } catch (Exception se) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + se.getMessage() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(dictaminador), se);
        }
    }

    public void consultaDictaminadorPA(Map<String, String> remplaceMensajes) {
        try{
        this.pistaAuditoria.setRemplaceMensajes(remplaceMensajes);
        this.pistaAuditoria.setIdMensaje(ConstatesMsgDictaminadores.MSG_MD_ADI_CONS);
        this.pistaAuditoria.setMovimiento(ConstatesMsgDictaminadores.OPE_MD_CTA_B);
        this.pistaAuditoria.setIdentificador(remplaceMensajes.get(ConstantesDyC1.DB_NUM_EMPLEADO));

        this.registroPistasAuditoria.registrarPistaAuditoria(this.pistaAuditoria);
        } catch (SIATException siat) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + siat.getMessage() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(dictaminador), siat);
        }
    }

    public boolean inactivarComisionarRNFDC615(DyccDictaminadorDTO dic, boolean baja) {
        List<DycpServicioDTO> listaServ;
        this.getStrMensaje().setLength(ConstantesDyC1.CERO);
        try {
            listaServ = iDycpServicioDAO.obtenerSolicitudServicio(dic);
        } catch (Exception e) {
            LOG.error(ConstantesDyC1.TEXTO_ERROR + e.getMessage());
            listaServ = new ArrayList<DycpServicioDTO>();
        }
        boolean paso = ConstantesDyC1.CERO == listaServ.size() ? Boolean.TRUE : Boolean.FALSE;
        if (!paso) {
            try {
                if (!baja) {
                    // El Dictaminador no puede ser comisionado porque actualmente tiene trámites asignados.
                    this.getStrMensaje().append(this.registroPistasAuditoria.obtenerMensaje(ConstatesMsgDictaminadores.MSG_MD_NOT_COMIC,
                                                                                            ConstantesDyC1.UNO,
                                                                                            this.pistaAuditoria.getIdGrupoOperacion()));
                } else if (baja) {
                    // No puede terminar la comisión al Dictaminador porque tiene trámites asignados
                    // en la <Nombre de la Administración (ALAF ó ACGC) a la que fue comisionado el Dictaminador>.
                    this.remplaceMensajes.put(ConstantesDyC1.DB_NOMBRE_ADM, dictaminador.getDescComision());
                    this.registroPistasAuditoria.addMensajesReemplazo(this.remplaceMensajes);
                    this.getStrMensaje().append(this.registroPistasAuditoria.obtenerMensaje(ConstatesMsgDictaminadores.MSG_MD_NOT_TERMN,
                                                                                            ConstantesDyC1.UNO,
                                                                                            this.pistaAuditoria.getIdGrupoOperacion()));
                }
            } catch (Exception e) {
                this.getStrMensaje().append(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage());
                LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage());
            }
        }
        return paso;
    }


    /**
     * @return
     */
    @Transactional(readOnly = false)
    public String obtenerMensaje() {
        return this.strMensaje.toString();
    }

    @Transactional(readOnly = false)
    public String obtenerMensaje(Integer msg) {
        StringBuilder mensaje = new StringBuilder();
        try {
            if (ConstantesDyC1.CERO != msg) {
                mensaje.append(this.dyccMensajeUsrService.obtieneMensaje(msg,
                                                                         ConstatesMsgDictaminadores.CU_MATRIZ_DICTAMINADOR).getDescripcion());
            } else if (ConstantesDyC1.CERO == msg) {
                mensaje.append(this.getStrMensaje());
            }
        } catch (Exception e) {
            mensaje.append("NADA");
            LOG.error("\nObtenerMensaje()" + e.getMessage());
        }
        return mensaje.toString();
    }

    @Override
    public DyccDictaminadorDTO encontrarActivo(Integer numEmpleado) {
        return daoDictaminador.encontrarEmpDic(numEmpleado);
    }
    
    
     @Override
    public DictaminadorVO obtenerDictaminador ( Integer numeroEmpleadoDictaminador ){
        try {
            
            return daoDictaminador.obtenerDictaminador( numeroEmpleadoDictaminador );

        } catch (Exception se) {
            LOG.error( ConstantesDyC1.TEXTO_1_ERROR_DAO + se.getMessage() + ConstantesDyC1.TEXTO_3_ERROR_DAO + 
                numeroEmpleadoDictaminador.toString() );
        }
        
        return null;
    }

    // ACCESSORS ****************************************

    public void setStrMensaje(StringBuilder strMensaje) {
        this.strMensaje = strMensaje;
    }

    public StringBuilder getStrMensaje() {
        return strMensaje;
    }

    public void setDyccMensajeUsrService(DyccMensajeUsrService dyccMensajeUsrService) {
        this.dyccMensajeUsrService = dyccMensajeUsrService;
    }

    public DyccMensajeUsrService getDyccMensajeUsrService() {
        return dyccMensajeUsrService;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setObservaciones(StringBuilder observaciones) {
        this.observaciones = observaciones;
    }

    public StringBuilder getObservaciones() {
        return observaciones;
    }

    public void setAdmin(AdmcUnidadAdmvaDTO admin) {
        this.admin = admin;
    }

    public AdmcUnidadAdmvaDTO getAdmin() {
        return admin;
    }

    public void setDictaminador(DictaminadorVO dictaminador) {
        this.dictaminador = dictaminador;
    }

    public DictaminadorVO getDictaminador() {
        return dictaminador;
    }

    public void setEmpleado(EmpleadoVO empleado) {
        this.empleado = empleado;
    }

    public EmpleadoVO getEmpleado() {
        return empleado;
    }

    public void setFolio(BigInteger folio) {
        this.folio = folio;
    }

    public BigInteger getFolio() {
        return folio;
    }

    public void setSegbMovimientosDAO(SegbMovimientosDAO segbMovimientosDAO) {
        this.segbMovimientosDAO = segbMovimientosDAO;
    }

    public SegbMovimientosDAO getSegbMovimientosDAO() {
        return segbMovimientosDAO;
    }

    public void setSessionHandler(SessionHandler sessionHandler) {
        this.sessionHandler = sessionHandler;
    }

    public SessionHandler getSessionHandler() {
        return sessionHandler;
    }

    public void setMovimientoDTO(SegbMovimientoDTO movimientoDTO) {
        this.movimientoDTO = movimientoDTO;
    }

    public SegbMovimientoDTO getMovimientoDTO() {
        return movimientoDTO;
    }

    public void setListaDictaminador(List<DictaminadorVO> listaDictaminador) {
        this.listaDictaminador = listaDictaminador;
    }

    public List<DictaminadorVO> getListaDictaminador() {
        return listaDictaminador;
    }

    public void setRemplaceMensajes(Map<String, String> remplaceMensajes) {
        this.remplaceMensajes = remplaceMensajes;
    }

    public Map<String, String> getRemplaceMensajes() {
        return remplaceMensajes;
    }

    public void setPistaAuditoria(PistaAuditoriaVO pistaAuditoria) {
        this.pistaAuditoria = pistaAuditoria;
    }

    public PistaAuditoriaVO getPistaAuditoria() {
        return pistaAuditoria;
    }

    public void setRegistroPistasAuditoria(RegistroPistasAuditoria registroPistasAuditoria) {
        this.registroPistasAuditoria = registroPistasAuditoria;
    }

    public RegistroPistasAuditoria getRegistroPistasAuditoria() {
        return registroPistasAuditoria;
    }

    public void setAdmcUnidadAdmvaService(AdmcUnidadAdmvaService admcUnidadAdmvaService) {
        this.admcUnidadAdmvaService = admcUnidadAdmvaService;
    }

    public AdmcUnidadAdmvaService getAdmcUnidadAdmvaService() {
        return admcUnidadAdmvaService;
    }
    
   
    
}
