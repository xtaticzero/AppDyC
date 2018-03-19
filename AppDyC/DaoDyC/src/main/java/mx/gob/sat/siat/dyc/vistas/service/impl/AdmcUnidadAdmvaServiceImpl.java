package mx.gob.sat.siat.dyc.vistas.service.impl;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import mx.gob.sat.siat.dyc.dao.usuario.DyccAprobadorDAO;
import mx.gob.sat.siat.dyc.dao.usuario.DyccDictaminadorDAO;
import mx.gob.sat.siat.dyc.domain.ags.DyccDeptAgsDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;
import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidadAdmDomDTO;
import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidadAdmvaDTO;
import mx.gob.sat.siat.dyc.domain.vistas.DycvEmpleadoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.Procesos;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.vistas.dao.AdmcUnidadAdmvaDAO;
import mx.gob.sat.siat.dyc.vistas.dao.DycvEmpleadoDAO;
import mx.gob.sat.siat.dyc.vistas.service.AdmcUnidadAdmvaService;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


/**
 * @author  Luis Alberto Dominguez Palomino [LADP]
 * @since   20/10/2014
 */
@Service(value = "admcUnidadAdmvaService")
public class AdmcUnidadAdmvaServiceImpl implements AdmcUnidadAdmvaService {
    private static final Logger LOG = Logger.getLogger(AdmcUnidadAdmvaServiceImpl.class.getName());

    @Autowired
    private AdmcUnidadAdmvaDAO daoUnidadAdmva;

    private List<AdmcUnidadAdmvaDTO> listaUnidad;
    private AdmcUnidadAdmvaDTO objUnidad;

    @Autowired
    private DycvEmpleadoDAO daoEmpleadosAGS;

    @Autowired
    private DyccDictaminadorDAO daoDictaminador;

    @Autowired
    private DyccAprobadorDAO daoAprobador;

    private static final String NOM_PARAM_ACCESO = "acceso";
    private static final String NOM_PARAM_ROL_DYC = "rolDYC";

    private static final Integer VARIABLE_136 = 136;
    private static final Integer VARIABLE_137 = 137;
    private static final Integer VARIABLE_182 = 182;
    private static final Integer VARIABLE_908 = 908;
    private static final Integer VARIABLE_1359 = 1359;
    private static final Integer VARIABLE_1372 = 1372;
    private static final Integer VARIABLE_177 = 177;
    private static final Integer VARIABLE_179 = 179;
    private static final Integer VARIABLE_463 = 463;
    private static final Integer VARIABLE_178 = 178;
    private static final Integer VARIABLE_741 = 741;
    private static final Integer VARIABLE_1345 = 1365;
    private static final Integer VARIABLE_722 = 722;
    private static final Integer VARIABLE_1402 = 1402;
    private static final Integer VARIABLE_1403 = 1403;
    private static final Integer VARIABLE_1404 = 1972;
    private static final Integer VARIABLE_1405 = 1973;
    private static final Integer VARIABLE_1406 = 1974;
    private static final Integer VARIABLE_1407 = 1975;
    private static final Integer VARIABLE_1408 = 1976;
    private static final Integer VARIABLE_1409 = 1977;
    private static final Integer VARIABLE_1400 = 1978;
    private static final Integer VARIABLE_104 = 104;
    private static final Integer VARIABLE_105 = 105;
    private static final Integer VARIABLE_106 = 106;
    private static final Integer VARIABLE_107 = 107;

    @Override
    public List<AdmcUnidadAdmvaDTO> consultarUnidadAdmvaList(AdmcUnidadAdmvaDTO admva) {
        try {
            this.listaUnidad = daoUnidadAdmva.consultarUnidadAdmvaList(admva);
        } catch (SQLException ex) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + ex.getMessage());
            this.listaUnidad = Collections.EMPTY_LIST;
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage());
            this.listaUnidad = Collections.EMPTY_LIST;
        }
        return this.listaUnidad;
    }

    @Override
    public List<AdmcUnidadAdmvaDTO> consultarUnidadAdmvaList(DyccDeptAgsDTO depto) {
        try {
            this.listaUnidad = daoUnidadAdmva.consultarUnidadAdmvaList(depto);
        } catch (SQLException ex) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + ex.getMessage());
            this.listaUnidad.clear();
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage());
            this.listaUnidad.clear();
        }
        return this.listaUnidad;
    }

    @Override
    public List<AdmcUnidadAdmvaDTO> consultarUnidadAdmvaCentral(AdmcUnidadAdmvaDTO admva) {
        try {
            this.listaUnidad = daoUnidadAdmva.consultarUnidadAdmvaCentral(admva);
        } catch (SQLException ex) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + ex.getMessage());
            this.listaUnidad.clear();
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage());
            this.listaUnidad.clear();
        }
        return this.listaUnidad;
    }

    @Override
    public boolean isALSC(Integer idTramite) {
        String pertenece = daoUnidadAdmva.isALSC(idTramite);
        return null != pertenece ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public Map<String, Object> crearAccesoUsr(String idUsuario) {
        LOG.debug("INICIO crearAccesoUsr");
        Map<String, Object> respuesta = null;
        Integer numEmpleado = null;
        try {
            numEmpleado = Integer.parseInt(idUsuario);
        } catch (java.lang.NumberFormatException e) {
            LOG.info("el idEmpleado no es un numero");
        }

        if (numEmpleado != null) {
            DycvEmpleadoDTO empleadoSAT = daoEmpleadosAGS.encontrar(numEmpleado);
            respuesta = crearAccesoUsrBueno(empleadoSAT, numEmpleado);
        } else {
            respuesta = crearAccesoUsrContrario(idUsuario);
        }

        return respuesta;
    }

    private Map<String, Object> crearAccesoUsrBueno(DycvEmpleadoDTO empleadoSAT, Integer numEmpleado) {
        AccesoUsr acceso = null;
        String mensaje = null;
        String rolDYC = null;

        if (empleadoSAT != null) {
            LOG.debug("El numero introducido si pertenece a un numero de empleado del SAT");
            Object empleadoDyc = obtenerEmpleadoDyc(numEmpleado);
            if (empleadoDyc != null) {
                if (empleadoDyc instanceof DyccDictaminadorDTO) {
                    acceso = crearAccesoDictaminador((DyccDictaminadorDTO)empleadoDyc, empleadoSAT);
                    rolDYC = "DICTAMINADOR";
                } else {
                    acceso = crearAccesoAprobador((DyccAprobadorDTO)empleadoDyc, empleadoSAT);
                    rolDYC = "APROBADOR";
                }
            } else {
                LOG.debug("Es empleado del SAT pero NO es usuario de DYC. Se creara acceso de contribuyente");
                acceso = crearAccesoContribuyente(empleadoSAT);
                rolDYC = "CONTRIBUYENTE";
            }
        } else {
            mensaje = "El número de empleado no es válido para ningún empleado del SAT ";
        }

        Map<String, Object> respuesta = new HashMap<String, Object>();
        respuesta.put(NOM_PARAM_ACCESO, acceso);
        respuesta.put(NOM_PARAM_ROL_DYC, rolDYC);
        respuesta.put("mensaje", mensaje);
        return respuesta;
    }

    private Map<String, Object> crearAccesoUsrContrario(String idUsuario) {
        AccesoUsr acceso = null;
        String mensaje = null;
        String rolDYC = null;
        String idUsuarioMayusculas = idUsuario.toUpperCase();
        LOG.debug("idUsuario ->" + idUsuarioMayusculas + "<-");
        if (esRFCCorto(idUsuarioMayusculas)) {
            LOG.debug("el parametro introducido es un RFC corto");
            DycvEmpleadoDTO empleadoSAT = daoEmpleadosAGS.selecXRfccorto(idUsuarioMayusculas);
            LOG.debug("empleadoSAT encontrado por RFC corto ->" + empleadoSAT + "<-");
            if (empleadoSAT != null) {
                Map<String, Object> respCAES = crearAccesoEmpleadoSAT(empleadoSAT);
                acceso = (AccesoUsr)respCAES.get(NOM_PARAM_ACCESO);
                rolDYC = (String)respCAES.get(NOM_PARAM_ROL_DYC);
            } else {
                mensaje = "El RFC corto no es válido para ningun empleado del SAT";
            }
        } else {
            LOG.debug("el parametro introducido NO es un RFC corto");
            if (esRFCValido(idUsuarioMayusculas)) {
                LOG.debug("El identificador de usuario introducido es un RFC valido");
                DycvEmpleadoDTO empleadoSAT = daoEmpleadosAGS.selecXRfc(idUsuarioMayusculas);

                if (empleadoSAT != null) {
                    Map<String, Object> respCAES = crearAccesoEmpleadoSAT(empleadoSAT);
                    acceso = (AccesoUsr)respCAES.get(NOM_PARAM_ACCESO);
                    rolDYC = (String)respCAES.get(NOM_PARAM_ROL_DYC);
                } else {
                    acceso = crearAccesoContribuyente(idUsuarioMayusculas);
                    rolDYC = "CONTRIBUYENTE";
                }
            } else {
                LOG.debug("El identificador de usuario introducido NO es un RFC valido");
                mensaje = "La cadena introducida no es válida para ningun usuario";
            }
        }
        Map<String, Object> respuesta = new HashMap<String, Object>();
        respuesta.put(NOM_PARAM_ACCESO, acceso);
        respuesta.put(NOM_PARAM_ROL_DYC, rolDYC);
        respuesta.put("mensaje", mensaje);
        return respuesta;
    }

    private AccesoUsr crearAccesoDictaminador(DyccDictaminadorDTO dictaminador, DycvEmpleadoDTO empleadoSAT) {
        DycvEmpleadoDTO empleadoSATLocal;
        if (empleadoSAT == null) {
            empleadoSATLocal = daoEmpleadosAGS.encontrar(dictaminador.getNumEmpleado());
        } else {
            empleadoSATLocal = empleadoSAT;
        }

        AccesoUsr acceso = new AccesoUsr();
        llenarDatosAcceso(acceso, empleadoSATLocal, dictaminador.getClaveAdm(), 
                dictaminador.getCentroCosto().toString());
        acceso.setNumeroEmp(dictaminador.getNumEmpleado().toString());

        acceso.setRoles("SAT_DYC_DICT,SAT_DYC_ADMIN_APLIC,SAT_DYC_ASESOR_FISCAL,SAT_DYC_CONS_PISTA_DYC,SAT_DYC_REVISOR_CENT," +
                        "SAT_DYC_APROB_LEGAL,SAT_DYC_USR_FISC,SAT_DYC_GEN_REP,  SAT_DYC_CONS_GENERAL");
        acceso.setMenu("1");
        return acceso;
    }

    private AccesoUsr crearAccesoAprobador(DyccAprobadorDTO aprobador, DycvEmpleadoDTO empleadoSAT) {
        LOG.debug("empleadoSAT ->" + empleadoSAT + "<-");
        DycvEmpleadoDTO empleadoSATLocal;
        if (empleadoSAT == null) {
            empleadoSATLocal = daoEmpleadosAGS.encontrar(aprobador.getNumEmpleado());

        } else {
            empleadoSATLocal = empleadoSAT;
        }

        AccesoUsr acceso = new AccesoUsr();

        String roles =
            "SAT_DYC_ADMIN_APLIC,SAT_DYC_ASESOR_FISCAL,SAT_DYC_CONS_PISTA_DYC,SAT_DYC_ADMIN_APRO," + 
                "SAT_DYC_ADMIN_PLAN,SAT_DYC_ADMIN_AUTO," +
                "SAT_DYC_REVISOR_CENT,SAT_DYC_APROB_LEGAL,SAT_DYC_USR_FISC,SAT_DYC_GEN_REP,SAT_SDC_ADMIN_SAL";

        if (aprobador.getNumEmpleado() == ConstantesDyCNumerico.VALOR_32332) {
            roles += ",SAT_DYC_ADMIN_CENT";
        }

        llenarDatosAcceso(acceso, empleadoSATLocal, aprobador.getClaveAdm(),
                aprobador.getCentroCosto().toString());
        acceso.setNumeroEmp(aprobador.getNumEmpleado().toString());
        acceso.setRoles(roles);
        acceso.setMenu("1");
        return acceso;
    }

    private void llenarDatosAcceso(AccesoUsr acceso, DycvEmpleadoDTO empleadoSAT, Integer claveAdm, String centroCostos) {
        LOG.debug("llenarDatosAcceso; empleadoSAT ->" + empleadoSAT + "<-");
        LOG.debug("claveAdm ->" + claveAdm + "<-");
        AdmcUnidadAdmvaDTO unidadAdmva = obtenerUnidadAdmva(empleadoSAT, claveAdm);
        AdmcUnidadAdmvaDTO unidadAdmvaPadre = new AdmcUnidadAdmvaDTO();
        try {
            unidadAdmvaPadre = daoUnidadAdmva.encontrar(unidadAdmva.getIdUnidadmpadre());
        } catch (SIATException e) {
            LOG.debug(e.getMessage());
        }
        acceso.setNombreCompleto(empleadoSAT.getNombreCompleto());
        acceso.setUsuario(empleadoSAT.getRfc());

        acceso.setTipoAuth("");

        acceso.setSessionID("");
        acceso.setSessionIDNovell("");
        acceso.setLocalidad(unidadAdmva.getAdmcUnidadAdmDomDTO().getMunicipio());
        acceso.setLocalidadOp(unidadAdmva.getAdmcUnidadAdmDomDTO().getMunicipio());
        acceso.setUsuarioOficina("");
        acceso.setLocalidadCRM("");
        acceso.setRoles("");
        acceso.setIdTipoPersona("");
        acceso.setIp("");
        acceso.setMac("");

        try {
            LOG.debug("unidadAdmvaPadre.getIdUnidadAdmva() ->" + unidadAdmvaPadre.getIdUnidadAdmva() + "<-");
            //TODO: validar cual es la correcta: idUnidadAdmva o admonGral
            acceso.setClaveAdminGral(String.valueOf(unidadAdmvaPadre.getIdUnidadAdmva()));
            /**acceso.setDescAdminGral(unidadAdmvaPadre.getNombre());*/
        } catch (Exception e) {
            LOG.debug(e.getMessage());
        }

        String claveSIR = unidadAdmva.getClaveSir() != null ? unidadAdmva.getClaveSir().toString() : "";

        acceso.setClaveSir(claveSIR);
        acceso.setClaveAdminCentral(empleadoSAT.getClaveDepto2());
        acceso.setNombres(empleadoSAT.getNombre());
        acceso.setPrimerApellido(empleadoSAT.getPaterno());
        acceso.setSegundoApellido(empleadoSAT.getMaterno());
        acceso.setRfcCorto(empleadoSAT.getRfcCorto());
        acceso.setCentroCosto(centroCostos);
        acceso.setCentroCostoOp(empleadoSAT.getCentroCosto());
        acceso.setJobCode(empleadoSAT.getJobcode());
        acceso.setJobCodeDesc("");
        acceso.setEmail(empleadoSAT.getEmail());
        acceso.setUnidadNegocio("");
        /**acceso.setCentroCostoDesc(empleadoSAT.getCentroCostoDescr());*/

        //Se agrega el mapa de procesos, el centro de datos y el tipo de persona en duro para todos los empleados SAT,
        //para que funcione pistas de auditoría.
        Map<Integer, String> procesos = new HashMap<Integer, String>();
        procesos.put(1, "DYC00012");
        procesos.put(ConstantesDyCNumerico.VALOR_136, Procesos.DYC00002);

        procesos.put(VARIABLE_136, "DYC00002");
        procesos.put(VARIABLE_137, "DYC00003");
        procesos.put(VARIABLE_182, "DYC00008");
        procesos.put(VARIABLE_908, "DYC00013");
        procesos.put(VARIABLE_1359, "DYC00014");
        procesos.put(VARIABLE_1372, "DYC00015");
        procesos.put(VARIABLE_177, "DYC00004");
        procesos.put(VARIABLE_179, "DYC00006");
        procesos.put(VARIABLE_463, "DYC00009");
        procesos.put(VARIABLE_178, "DYC00005");
        procesos.put(VARIABLE_741, "DYC00012");
        procesos.put(VARIABLE_1345, "DYC00010");
        procesos.put(VARIABLE_722, "DYC00011");
        procesos.put(VARIABLE_1403, "DYC00017");
        procesos.put(VARIABLE_1402, "DYC00018");
        procesos.put(VARIABLE_1404, "DYC00019");
        procesos.put(VARIABLE_1405, "DYC00020");
        procesos.put(VARIABLE_1406, "DYC00021");
        procesos.put(VARIABLE_1407, "DYC00022");
        procesos.put(VARIABLE_1408, "DYC00023");
        procesos.put(VARIABLE_1409, "DYC00024");
        procesos.put(VARIABLE_1400, "DYC00025");
        procesos.put(VARIABLE_104, "DYC00104");
        procesos.put(VARIABLE_105, "DYC00105");
        procesos.put(VARIABLE_106, "DYC00106");
        procesos.put(VARIABLE_107, "DYC013");

        acceso.setProcesos(procesos);
        acceso.setCentroDatos("1111");
        acceso.setIdTipoPersona("2");

        unidadAdmva.setClaveSir(Integer.parseInt(acceso.getClaveSir()));

        LOG.debug("claveSir ->" + acceso.getClaveSir() + "<-");
    }

    private AdmcUnidadAdmvaDTO obtenerUnidadAdmva(DycvEmpleadoDTO empleadoSAT, Integer claveAdm) {
        LOG.debug("INICIO obtenerUnidadAdmva");
        LOG.debug("empleadoSAT.getCentroCosto() ->" + empleadoSAT.getCentroCosto() + "<-");
        List<AdmcUnidadAdmvaDTO> unidades = new ArrayList<AdmcUnidadAdmvaDTO>();
        AdmcUnidadAdmvaDTO adm = new AdmcUnidadAdmvaDTO();

        try {
            adm.setClaveSir(claveAdm);
            adm.setClaveAgrs(empleadoSAT.getCentroCosto());
            unidades = daoUnidadAdmva.consultarUnidadAdmvaList(adm);
        } catch (SQLException es) {
            LOG.debug(es.getMessage());
        }
        if (unidades.size() == 1) {
            LOG.debug("solo se encontro una unidad Administrativa por CentroCosto-ClaveAGRS se utilizará para crear el acceso");
            return unidades.get(0);
        } else if (unidades.size() > 1) {
            LOG.debug("No se encontro una unica Unidad Administrativa por CentroCosto-ClaveAGRS se utilizará claveAdm para discriminar");
            for (AdmcUnidadAdmvaDTO unidad : unidades) {
                LOG.debug("unidad.getClaveSir() ->" + unidad.getClaveSir() + "<-");
                try {
                    if (unidad.getClaveSir().intValue() == claveAdm.intValue()) {
                        return unidad;
                    }
                } catch (Exception ie) {
                    LOG.error(ie.getMessage());
                }
            }
        } else {
            LOG.debug("NO se encontró ninguna unidadAdmva... se creará una ficticia");
            AdmcUnidadAdmvaDTO unidadAdmva = new AdmcUnidadAdmvaDTO();
            AdmcUnidadAdmDomDTO aux = new AdmcUnidadAdmDomDTO();
            aux.setMunicipio("Pendiente");
            unidadAdmva.setAdmcUnidadAdmDomDTO(aux);
            unidadAdmva.setClaveSir(claveAdm);
            unidadAdmva.setIdUnidadAdmva(claveAdm);
            return unidadAdmva;
        }

        LOG.error("NO se encontro una Unidad Administrativa válida");
        return null;
    }

    private AccesoUsr crearAccesoContribuyente(String rfc) {
        AccesoUsr acceso = new AccesoUsr();
        acceso.setUsuario(rfc);
        acceso.setMenu("2");
        acceso.setRoles("");
        acceso.setIdTipoPersona("");
        acceso.setClaveAdminCentral("44");
        acceso.setLocalidad("900B000000");
        acceso.setTipoAuth("2");
        acceso.setNombreCompleto("Contribuyente apellidoP apellidoM");
        return acceso;
    }

    private AccesoUsr crearAccesoContribuyente(DycvEmpleadoDTO empleadoSAT) {
        LOG.debug("Se encontro un empleado del SAT pero no esta dado de alta en DYC, se le permitira entrar como contribuyente");
        AccesoUsr acceso = new AccesoUsr();
        acceso.setNombreCompleto(empleadoSAT.getNombreCompleto());
        acceso.setUsuario(empleadoSAT.getRfc());
        acceso.setIdTipoPersona("");
        acceso.setNombres(empleadoSAT.getNombre());
        acceso.setPrimerApellido(empleadoSAT.getPaterno());
        acceso.setSegundoApellido(empleadoSAT.getMaterno());
        acceso.setRfcCorto(empleadoSAT.getRfcCorto());
        acceso.setMenu("2");
        acceso.setRoles("");
        acceso.setTipoAuth("2");
        return acceso;
    }

    private Map<String, Object> crearAccesoEmpleadoSAT(DycvEmpleadoDTO empleadoSAT) {

        AccesoUsr acceso;
        String rolDYC;
        Integer numEmpleadoSAT = empleadoSAT.getNoEmpleado();
        Object empleadoDyc = obtenerEmpleadoDyc(numEmpleadoSAT);
        if (empleadoDyc != null) {
            if (empleadoDyc instanceof DyccDictaminadorDTO) {
                acceso = crearAccesoDictaminador((DyccDictaminadorDTO)empleadoDyc, empleadoSAT);
                rolDYC = "DICTAMINADOR";
            } else {
                acceso = crearAccesoAprobador((DyccAprobadorDTO)empleadoDyc, null);
                rolDYC = "APROBADOR";
            }
        } else {
            LOG.debug("es empleado del SAT pero no es empleado de DyC, se otorgara una sesion de contribuyente");
            acceso = crearAccesoContribuyente(empleadoSAT);
            rolDYC = "CONTRIBUYENTE";
        }

        Map<String, Object> respuesta = new HashMap<String, Object>();
        respuesta.put(NOM_PARAM_ACCESO, acceso);
        respuesta.put(NOM_PARAM_ROL_DYC, rolDYC);
        return respuesta;
    }

    @Override
    public Object obtenerEmpleadoDyc(Integer numEmpleado) {
        Object objEmpleado = daoAprobador.encontrar(numEmpleado);
        if (objEmpleado == null) {
            objEmpleado = daoDictaminador.encontrar(numEmpleado);
        }
        return objEmpleado;
    }
    
    @Override
    public Object obtenerEmpleadoDycApDic(Integer numEmpleado) {
        Object objEmpleado = daoAprobador.encontrarEmpAp(numEmpleado);
        if (objEmpleado == null) {
            objEmpleado = daoDictaminador.encontrarEmpDic(numEmpleado);
        }
        return objEmpleado;
    }
    
    @Override
    public List<AdmcUnidadAdmvaDTO> obtenerClaveSirxCCostoOp(String centroCostoOp) throws SIATException {
        return daoUnidadAdmva.selecXClaveagrs(centroCostoOp);
    }

    @Override
    public boolean esRFCCorto(String idUsuario) {
        Pattern pat = Pattern.compile("[A-Z]{4}\\d{2}[A-Z0-9]{2}\\d{0,1}");
        return pat.matcher(idUsuario).matches();
    }

    @Override
    public Integer obtieneCentroCostos(Integer claveAdm) {
        return daoUnidadAdmva.obtieneCentroCostos(claveAdm);
    }

    @Override
    public boolean esRFCValido(String idUsuario) {
        Pattern pat = Pattern.compile("^[A-Z&Ñ]{3,4}(\\d{6})(([A-Z]|\\d){3})$");
        return pat.matcher(idUsuario).matches();
    }

    public void setListaUnidad(List<AdmcUnidadAdmvaDTO> listaUnidad) {
        this.listaUnidad = listaUnidad;
    }

    public List<AdmcUnidadAdmvaDTO> getListaUnidad() {
        return listaUnidad;
    }

    public void setObjUnidad(AdmcUnidadAdmvaDTO objUnidad) {
        this.objUnidad = objUnidad;
    }

    public AdmcUnidadAdmvaDTO getObjUnidad() {
        return objUnidad;
    }
}
