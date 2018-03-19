/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.admcat.service.matrizaprobador.impl;

import java.sql.SQLException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import mx.gob.sat.mat.dyc.empleados.service.SatAgsEmpleadosMVService;
import mx.gob.sat.siat.dyc.admcat.dto.matrizdictaminadores.EmpleadoVO;
import mx.gob.sat.siat.dyc.admcat.service.matrizaprobador.AprobadorService;
import mx.gob.sat.siat.dyc.admcat.service.matrizdictaminadores.EmpleadoService;
import mx.gob.sat.siat.dyc.dao.declaracion.DyctFacultadesDAO;
import mx.gob.sat.siat.dyc.dao.documento.impl.DyctDocumentoDAOImpl;
import mx.gob.sat.siat.dyc.dao.usuario.DyccAprobadorDAO;
import mx.gob.sat.siat.dyc.domain.declaracion.DyctFacultadesDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;
import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidadAdmvaDTO;
import mx.gob.sat.siat.dyc.service.DyccMensajeUsrService;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.RegistroPistasAuditoria;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.Procesos;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC3;
import mx.gob.sat.siat.dyc.util.constante2.ConstantesMatrizAprobadores;
import mx.gob.sat.siat.dyc.vistas.dao.AdmcUnidadAdmvaDAO;
import mx.gob.sat.siat.dyc.vistas.service.AdmcUnidadAdmvaService;
import mx.gob.sat.siat.dyc.vo.AprobadorVO;
import mx.gob.sat.siat.dyc.vo.PistaAuditoriaVO;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @since 0.1
 *
 * @date Octubre 14, 2013
 * */
@Service("aprobadorService")
public class AprobadorServiceImpl implements AprobadorService {

    private static final Logger LOG = Logger.getLogger(AprobadorServiceImpl.class);

    @Autowired(required = true)
    private DyccAprobadorDAO daoAprobador;
    @Autowired(required = true)
    private DyccMensajeUsrService dyccMensajeUsrService;
    @Autowired(required = true)
    private AdmcUnidadAdmvaService admcUnidadAdmvaService;
    @Autowired(required = false)
    private SatAgsEmpleadosMVService satAgsEmpleadosMVService;
    @Autowired(required = true)
    private EmpleadoService empleadoService;
    @Autowired(required =true)
    private DyctDocumentoDAOImpl daoDocumento;
    @Autowired(required = true)
    private DyctFacultadesDAO facultadesDAO;

    @Autowired
    private AdmcUnidadAdmvaDAO daoUnidadAdmva;

    @Autowired(required = true)
    private RegistroPistasAuditoria registroPistasAuditoria;

    private AccesoUsr accesoUsr;
    private HttpSession session;

    private StringBuilder strMensaje;
    private StringBuilder observaciones;

    private List<AprobadorVO> listaAprobador;
    private AprobadorVO aprobador;

    private AdmcUnidadAdmvaDTO admin;

    private Map<String, String> remplaceMensajes;
    private PistaAuditoriaVO pistaAuditoria;

    public AprobadorServiceImpl() {
        strMensaje = new StringBuilder();
        observaciones = new StringBuilder();

        admin = new AdmcUnidadAdmvaDTO();

        remplaceMensajes = new HashMap<String, String>();
        pistaAuditoria = new PistaAuditoriaVO();
        pistaAuditoria.setIdProceso(Procesos.DYC00008);
        pistaAuditoria.setIdGrupoOperacion(ConstantesMatrizAprobadores.CU_MATRIZ_APROBADOR);
    }

    public List<AprobadorVO> obtenerAprobadores(AprobadorVO aprobador, boolean isActivo) {
        return daoAprobador.obtenerAprobadores(aprobador, isActivo);
    }

    /**
     * @param aprobador objeto tipo [DyccAprobadorDTO] base para busquedas o nulo para todos los parametros
     * @return Lista de tipo ArrayList [DyccAprobadorDTO]
     */
    public List<DyccAprobadorDTO> obtenerListaAprobadores(DyccAprobadorDTO aprobador) {
        return daoAprobador.obtenerListaAprobadores(aprobador);
    }

    public List<EmpleadoVO> obtenerListaNivelDireccion() {
        return this.empleadoService.consultaNivelDireccion();
    }

    public List<DyccAprobadorDTO> consultarAprobadores(int unidad, int centroCosto) {
        return daoAprobador.consultarAprobadores(unidad, centroCosto);
    }

    @Override
    public DyccAprobadorDTO consultarAprobadorPorClaveAdm(Integer claveAdm) {
        return daoAprobador.consultarAprobadorPorClaveAdm(claveAdm);
    }

    @Transactional(readOnly = false)
    @Override
    public EmpleadoVO consultarInformacionEmpleado(EmpleadoVO empleado, Integer cCostosUsrSession, boolean isNum,
                                                   Integer claveAdmon) {
        EmpleadoVO empleadoVO = new EmpleadoVO();

        try {
            String estado =
                satAgsEmpleadosMVService.getEstatusEmpleado(isNum ? empleado.getNumEmpleado() : empleado.getRfc());

            if (estado != null) {
                if (estado.equals(ConstantesDyC.ESTADO_EMPLEADO_BAJA_AGS)) {

                    this.getStrMensaje().append(ConstantesDyC3.MENSAJE_MSG019);

                    empleadoVO = new EmpleadoVO();
                    empleadoVO.setValidacionEstado(2);

                } else {

                    empleadoVO = empleadoService.consultaInfoEmpleadoSinAdm(empleado);

                    AprobadorVO aprobadorVo = getAprobador(Integer.parseInt(empleadoVO.getNumEmpleado()));

                    if (aprobadorVo != null) {

                        String nombreUnidadAdministrativa = obtenerNombreUnidadAdmva(aprobadorVo.getClaveAdm());
                        empleadoVO.setCentroCostoDescr254(nombreUnidadAdministrativa);
                        empleadoVO.setCcosto(cCostosUsrSession);

                        if (aprobadorVo.getActivo().equals(ConstantesDyCNumerico.VALOR_1) &&
                            !empleadoVO.getClaveAdm().equals(claveAdmon)) {

                            empleadoVO.setValidacionEstado(ConstantesDyCNumerico.VALOR_1);
                        } else {
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

    private AprobadorVO getAprobador(Integer numEmpleado) {
        return daoAprobador.obtenerAprobadoresDycc(numEmpleado);
    }


    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void insertarAprobador(EmpleadoVO empleado) {
        this.getStrMensaje().setLength(ConstantesDyCNumerico.VALOR_0);
        /** EN LA MISMA ADMISITRACION **************************
        // aprobador.setClaveAdm(admin.getClaveSir());   ???  */
        try {
            this.aprobador = new AprobadorVO();
            if (null == empleado.getAprobador().getActivo()) {
                // SET DICTAMINADOR A INSERTAR
                this.aprobador.setNumEmpleado(Integer.parseInt(empleado.getNumEmpleado()));
                this.aprobador.setActivo(empleado.getActivo());
                this.aprobador.setObservaciones(empleado.getAprobador().getObservaciones());
                this.aprobador.setClaveDepto(empleado.getClaveDepto());
                this.aprobador.setNombre(empleado.getNombre());
                this.aprobador.setApellidoMaterno(empleado.getMaterno());
                this.aprobador.setApellidoPaterno(empleado.getPaterno());
                this.aprobador.setEmail(empleado.getEmail());
                this.aprobador.setRfc(empleado.getRfc());
                this.aprobador.setNombreCompleto(empleado.getNombreCompleto());
                this.aprobador.setRfcCorto(empleado.getRfcCorto());
                this.aprobador.setCurp(empleado.getCurp());
                this.aprobador.setClaveSir(empleado.getClaveAdm());
                this.aprobador.setClaveAdm(empleado.getClaveAdm());
                this.aprobador.setUn(empleado.getUn());
                this.aprobador.setAdmonGral(empleado.getAdmGral());
                this.aprobador.setNombrePuesto(empleado.getPuesto());
                this.aprobador.setClaveNivel(empleado.getClaveNivel());
                this.aprobador.setDescClaveNivel(empleado.getDescClaveNivel());
                this.aprobador.setCentroCosto(empleado.getCcosto());
                this.aprobador.setCentroCostoDescr254(empleado.getCentroCostoDescr254());

                this.aprobador.setClaveComision(empleado.getClaveComision());
                this.aprobador.setDescComision(empleado.getDescComision());
                this.aprobador.setNumEmpleadoJefe(ConstantesDyCNumerico.VALOR_0);

                this.daoAprobador.insertarAprobador(aprobador);
                this.pistaAuditoria.setMovimiento(ConstantesMatrizAprobadores.OPE_MA_ADD_BD);
            } else if (ConstantesDyCNumerico.VALOR_0 == empleado.getAprobador().getActivo()) {
                this.aprobador = empleado.getAprobador();
                this.aprobador.setActivo(ConstantesDyC1.UNO);
                this.aprobador.setClaveAdm( Integer.parseInt( accesoUsr.getClaveAdminCentral() ) );
                this.aprobador.setCentroCosto( Integer.parseInt( accesoUsr.getCentroCosto() ) );

                this.daoAprobador.actualizarAprobador(this.aprobador);
                this.pistaAuditoria.setMovimiento(ConstantesMatrizAprobadores.OPE_MA_MOD_BD);
            }

            // Se ha modificado la información del Aprobador <Numero de empleado> y  Nombre: <Nombre>.
            //Se ha registrado a <Nombre Empleado> en la Matriz de Aprobadores de la <Administración ACGC, ACDC ò ALAF> exitosamente
            remplaceMensajes.put(ConstantesDyC1.DB_NUM_EMPLEADO, aprobador.getNumEmpleado() + "");
            remplaceMensajes.put(ConstantesMatrizAprobadores.MA_NOMBRE_APROB, aprobador.getNombreCompleto());
            // Se ha registrado a <Nombre y Apellidos Empleado> en la Matriz de Aprobadores de la <Nombre Administración ALAF, ACGC ó ACDC > exitosamente.
            remplaceMensajes.put(ConstantesDyC1.DB_NOMBRE_ADM, aprobador.getCentroCostoDescr254());
            remplaceMensajes.put(ConstantesMatrizAprobadores.MA_NOMBRE_APROB, aprobador.getNombreCompleto());

            // REGISTRA PISTA AUDITORIA
            pistaAuditoria.setRemplaceMensajes(this.remplaceMensajes);
            pistaAuditoria.setIdMensaje(ConstantesMatrizAprobadores.MSG_MA_16);
            pistaAuditoria.setIdentificador(empleado.getNumEmpleado().toString());
            registroPistasAuditoria.registrarPistaAuditoria(this.pistaAuditoria);
            // REGRESA MENSAJE FRONT
            strMensaje.append(this.registroPistasAuditoria.obtenerMensaje(ConstantesDyCNumerico.VALOR_85,
                                                                          ConstantesDyCNumerico.VALOR_1,
                                                                          pistaAuditoria.getIdGrupoOperacion()));
        } catch (SQLException e) {
            this.getStrMensaje().append(ConstantesDyC1.TEXTO_ERROR + e.getMessage());
            LOG.error(ConstantesDyC1.TEXTO_ERROR + e.getMessage());
        } catch (SIATException siat) {
            this.getStrMensaje().append(ConstantesDyC1.TEXTO_ERROR + siat.getMessage());
            LOG.error(ConstantesDyC1.TEXTO_ERROR + siat.getMessage());
        }

    }

    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void actualizarAprobador(AprobadorVO aprobador) {
        this.getStrMensaje().setLength(ConstantesDyC1.CERO);
        try {
            daoAprobador.actualizarAprobador(aprobador);

            this.remplaceMensajes.put(ConstantesDyC1.DB_NUM_EMPLEADO, String.valueOf(aprobador.getNumEmpleado()));
            this.remplaceMensajes.put(ConstantesMatrizAprobadores.MA_NOMBRE_APROB, aprobador.getNombreCompleto());
            this.registroPistasAuditoria.addMensajesReemplazo(this.remplaceMensajes);
            strMensaje.append(this.registroPistasAuditoria.obtenerMensaje(ConstantesMatrizAprobadores.MSG_MA_16, ConstantesDyC1.TRES,
                                                                          ConstantesMatrizAprobadores.CU_MATRIZ_APROBADOR));
            pistaAuditoria.setMovimiento(ConstantesMatrizAprobadores.OPE_MA_ADD_BD);
            pistaAuditoria.setRemplaceMensajes(this.remplaceMensajes);
            pistaAuditoria.setIdMensaje(ConstantesMatrizAprobadores.MSG_MA_16);
            pistaAuditoria.setIdentificador(String.valueOf(aprobador.getNumEmpleado()));
            registroPistasAuditoria.registrarPistaAuditoria(this.pistaAuditoria);

        } catch (Exception e) {
            this.getStrMensaje().append(ConstantesDyC1.TEXTO_ERROR + e.getMessage());
            LOG.error(ConstantesDyC1.TEXTO_ERROR + e.getMessage());
        }
    }

    @Override
    public boolean inactivarComisionarRNFDC615(DyccAprobadorDTO aprobador, boolean baja) {
        List<DyctDocumentoDTO> listaServ;
        List<DyctFacultadesDTO> listaFac;
        boolean paso = false;
        this.getStrMensaje().setLength(ConstantesDyCNumerico.VALOR_0);
        try {
            listaServ = daoDocumento.consultarDocumentoAprobador(aprobador);
            if(listaServ.isEmpty()){
                listaFac = facultadesDAO.consultarDocumentoAprobador(aprobador.getNumEmpleado());
                paso = isAprobadorDoc(ConstantesDyCNumerico.VALOR_0 == listaFac.size(), baja);
            }else{
                paso = isAprobadorDoc(ConstantesDyCNumerico.VALOR_0 == listaServ.size(), baja);
            }
        } catch (Exception e) {
            LOG.error(ConstantesDyC1.TEXTO_ERROR + e.getMessage());
        }
        
        return paso;
    }
    
    private boolean isAprobadorDoc(boolean paso,  boolean baja) {
        if (!paso) {
            try {
                this.remplaceMensajes.put(ConstantesDyC1.DB_NOMBRE_ADM, aprobador.getClaveDepto());
                this.remplaceMensajes.put(ConstantesMatrizAprobadores.MA_NOMBRE_APROB, aprobador.getNombre());
                this.registroPistasAuditoria.addMensajesReemplazo(this.remplaceMensajes);
                if (!baja) {
                    // El Dictaminador no puede ser comisionado porque actualmente tiene trámites asignados.
                    this.getStrMensaje().append(this.registroPistasAuditoria.obtenerMensaje(ConstantesDyCNumerico.VALOR_90,
                                                                                            ConstantesDyCNumerico.VALOR_1,
                                                                                            pistaAuditoria.getIdGrupoOperacion()));
                } else if (baja) {
                    // No puede terminar la comisión al Dictaminador porque tiene trámites asignados
                    // en la <Nombre de la Administración (ALAF ó ACGC) a la que fue comisionado el Dictaminador>.
                    this.getStrMensaje().append(this.registroPistasAuditoria.obtenerMensaje(ConstantesDyCNumerico.VALOR_91,
                                                                                            ConstantesDyCNumerico.VALOR_1,
                                                                                            pistaAuditoria.getIdGrupoOperacion()));
                }
            } catch (Exception e) {
                this.getStrMensaje().append(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage());
                LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage());
            }
        }
        return paso;
    }

    public void eliminarAprobador(AprobadorVO aprobador) {
        try {
            aprobador.setActivo(ConstantesDyC1.CERO);
            daoAprobador.actualizarAprobador(aprobador);

            // REGISTRA PISTA AUDITORIA
            pistaAuditoria.setIdentificador(aprobador.getNumEmpleado().toString());
            pistaAuditoria.setIdMensaje(ConstantesDyCNumerico.VALOR_99);
            pistaAuditoria.setMovimiento(ConstantesMatrizAprobadores.OPE_MA_MOD_BD);
            pistaAuditoria.setIdGrupoOperacion(ConstantesMatrizAprobadores.CU_MATRIZ_APROBADOR);
            registroPistasAuditoria.registrarPistaAuditoria(this.pistaAuditoria);

        } catch (Exception se) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + se.getMessage() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(aprobador), se);
        }
    }

    /**
     * @return Regresa String Mensajes de la Base de Datos
     */
    @Transactional(readOnly = false)
    public String obtenerMensaje() {
        return this.strMensaje.toString().isEmpty() ? "" : this.strMensaje.toString();
    }

    @Transactional(readOnly = false)
    public String obtenerMensaje(Integer msg) {
        StringBuilder mensaje = new StringBuilder();
        try {
            if (ConstantesDyCNumerico.VALOR_0 != msg) {
                mensaje.append(this.dyccMensajeUsrService.obtieneMensaje(msg, ConstantesMatrizAprobadores.CU_MATRIZ_APROBADOR));
            } else if (ConstantesDyCNumerico.VALOR_0 == msg) {
                mensaje.append(this.getStrMensaje());
            }
        } catch (Exception e) {
            LOG.error(ConstantesDyC1.TEXTO_ERROR + e.getMessage(), e);
        }
        return mensaje.toString();
    }

    @Override
    public void setSessionID(HttpSession ss) {
        this.session = ss;
    }

    public void consultaAprobadorPA(Map<String, String> remplaceMensajes) {
        // REGISTRA PISTA AUDITORIA
        pistaAuditoria.setRemplaceMensajes(remplaceMensajes);
        pistaAuditoria.setIdMensaje(ConstantesDyCNumerico.VALOR_97);
        pistaAuditoria.setMovimiento(ConstantesMatrizAprobadores.OPE_MA_CTA_BD);
        pistaAuditoria.setIdentificador(remplaceMensajes.get(ConstantesDyC1.DB_NUM_EMPLEADO));
        registroPistasAuditoria.registrarPistaAuditoriaSTrans(pistaAuditoria);
    }

    private String obtenerNombreUnidadAdmva(Integer claveAdmon) {
        try {
            List<AdmcUnidadAdmvaDTO> unidades = daoUnidadAdmva.selecXClavesir(claveAdmon);
            return unidades.get(0).getNombre();
        } catch (Exception e) {
            LOG.error("Error en metodo obtenerNombreUnidadAdmva; mensaje ->" + e.getMessage());
            ManejadorLogException.getTraceError(e);
        }

        return "";
    }


    @Override
    public DyccAprobadorDTO encontrarActivo(Integer numEmpleado) {
        return this.daoAprobador.encontrarEmpAp(numEmpleado);
    }

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

    public void setObservaciones(StringBuilder observaciones) {
        this.observaciones = observaciones;
    }

    public StringBuilder getObservaciones() {
        return observaciones;
    }

    public void setAccesoUsr(AccesoUsr accesoUsr) {
        this.accesoUsr = accesoUsr;
    }

    public AccesoUsr getAccesoUsr() {
        return accesoUsr;
    }

    public void setAdmin(AdmcUnidadAdmvaDTO admin) {
        this.admin = admin;
    }

    public AdmcUnidadAdmvaDTO getAdmin() {
        return admin;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setPistaAuditoria(PistaAuditoriaVO pistaAuditoria) {
        this.pistaAuditoria = pistaAuditoria;
    }

    public PistaAuditoriaVO getPistaAuditoria() {
        return pistaAuditoria;
    }

    public void setRemplaceMensajes(Map<String, String> remplaceMensajes) {
        this.remplaceMensajes = remplaceMensajes;
    }

    public Map<String, String> getRemplaceMensajes() {
        return remplaceMensajes;
    }

    public void setListaAprobador(List<AprobadorVO> listaAprobador) {
        this.listaAprobador = listaAprobador;
    }

    public List<AprobadorVO> getListaAprobador() {
        return listaAprobador;
    }

    public void setAprobador(AprobadorVO aprobador) {
        this.aprobador = aprobador;
    }

    public AprobadorVO getAprobador() {
        return aprobador;
    }

    public void setAdmcUnidadAdmvaService(AdmcUnidadAdmvaService admcUnidadAdmvaService) {
        this.admcUnidadAdmvaService = admcUnidadAdmvaService;
    }

    public AdmcUnidadAdmvaService getAdmcUnidadAdmvaService() {
        return admcUnidadAdmvaService;
    }

}
