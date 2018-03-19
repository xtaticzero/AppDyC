package mx.gob.sat.siat.dyc.generico.web.controller.bean.backing;

import java.io.IOException;
import java.io.Serializable;

import java.nio.charset.Charset;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.sat.siat.dyc.generico.util.common.AbstractPage;
import mx.gob.sat.siat.dyc.generico.util.common.SIATDataModel;
import mx.gob.sat.siat.dyc.generico.util.common.UriDTO;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.Procesos;
import mx.gob.sat.siat.dyc.util.constante1.ConstanteInfoAdicional;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC2;
import mx.gob.sat.siat.dyc.vistas.service.AdmcUnidadAdmvaService;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.log4j.Logger;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;


@ManagedBean(name = "MenuIndexMB")
@SessionScoped
public class MenuIndexMB extends AbstractPage {
    private static final Logger LOG = Logger.getLogger(MenuIndexMB.class.getName());

    @ManagedProperty(value = "#{admcUnidadAdmvaService}")
    private AdmcUnidadAdmvaService serviceAdmcUnidadAdmva;

    private AccesoUsr accesoUsr;
    private AccesoUsr accesoSeleccionado;
    private List<AccesoUsr> listaAcceso;

    private boolean dibujarMenu;
    private TreeNode arbol;

    private List<TreeNode> empleadoMenuTree;
    private List<TreeNode> empleadoSubMenuTree;
    private List<TreeNode> contribuyenteMenuTree;
    private List<TreeNode> contribuyenteSubMenuTree;

    private String idUsuario;
    private String tituloMenu;
    private String txt2;

    private static final String CODIFICACION = "UTF-8";
    private static final String TEXTO_SOLICITUD_DE_DEVOLUCION = "Solicitud de devolución";
    private static final String DOCUMENTO = "Documento";

    public MenuIndexMB() {
        LOG.debug("constructor MenuIndexMB");
        this.listaAcceso = new ArrayList<AccesoUsr>();
        this.setDataModel(new SIATDataModel<Serializable>());

        this.arbol = new DefaultTreeNode("DyC", null);

        this.empleadoMenuTree = new ArrayList<TreeNode>();
        this.empleadoSubMenuTree = new ArrayList<TreeNode>();
        this.contribuyenteMenuTree = new ArrayList<TreeNode>();
        this.contribuyenteSubMenuTree = new ArrayList<TreeNode>();
    }

    @PostConstruct
    public void init() {
        LOG.debug("INICIO init");
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(Boolean.TRUE);
        if (null != session.getAttribute("portal")) {
            LOG.debug("session.getAttribute(\"portal\").toString() ->" + session.getAttribute("portal").toString() +
                      "<-");
        } else {
            LOG.debug("ConstantesDyC.TIPO_ACCESO_EMPL ->" + ConstantesDyC2.TIPO_ACCESO_EMPL);
        }

        accesoUsr = (AccesoUsr)session.getAttribute("accesoEF");

        if (null != accesoUsr) {
            LOG.info("YA EXISTE UN OBJETO AccesoUsr EN SESION >>> " + accesoUsr);
            this.dibujarMenu = Boolean.TRUE;
            this.generaArbol(accesoUsr.getMenu());
        } else {
            LOG.info("AUN NO EXISTE UN OBJETO AccesoUsr EN SESION");
            this.dibujarMenu = Boolean.FALSE;
            this.llenaLista();
        }
    }

    public void registraSession() {
        LOG.debug("INICIO registraSession");
        LOG.debug("idUsuario ->" + this.idUsuario + "<-");
        LOG.debug("txt2 ->" + txt2 + "<-");
        if (null != idUsuario && !"".equals(idUsuario) /*&& fdqwhacer(txt2)*/) {
            LOG.debug("se solicitara el servicio para identificar al usuario");
            LOG.debug("serviceAdmcUnidadAdmva ->" + serviceAdmcUnidadAdmva + "<-");

            Map<String, Object> respCrearSesion = serviceAdmcUnidadAdmva.crearAccesoUsr(idUsuario);
            accesoUsr = (AccesoUsr)respCrearSesion.get("acceso");
            LOG.debug("acceso ->" + accesoUsr + "<-");
            if (accesoUsr != null) {
                accesoUsr.setTipoAuth("1");
                /**if(accesoUsr.getClaveAdminCentral()!=null){*/
                String tipoUsuario =
                    "1".equals(accesoUsr.getMenu()) ? ConstantesDyC2.TIPO_ACCESO_EMPL : ConstantesDyC2.TIPO_ACCESO_CONT;
                setSession(accesoUsr, tipoUsuario);
                this.generaArbol(accesoUsr.getMenu());
                this.dibujarMenu = Boolean.TRUE;
                LOG.debug("se cambia el titulo del menu");
                tituloMenu = (String)respCrearSesion.get("rolDYC");
                LOG.debug("usuario ->" + accesoUsr.getUsuario() + "<-");
                HttpSession session =
                    (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                accesoUsr.setSessionID(session.getId());
                LOG.debug("id de Session ->" + accesoUsr.getSessionID() + "<-");
                /**}else{
                    String msjAdminCentralNula = "No ha sido posible asignarle una Admin. Central, verifique sus datos en el portal del SAT.";
                    FacesContext.getCurrentInstance().addMessage(null,
                                                                 new FacesMessage(FacesMessage.SEVERITY_INFO, msjAdminCentralNula,
                                                                                  ""));
                }*/
            } else {
                String mensaje = (String)respCrearSesion.get("mensaje");
                LOG.debug("mensaje ->" + mensaje + "<-");
                FacesContext.getCurrentInstance().addMessage(null,
                                                             new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje,
                                                                              ""));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                                                         new FacesMessage(FacesMessage.SEVERITY_INFO, "DEBE INTRODUCIR NUMEMPLEADO O RFC!",
                                                                          ""));
            LOG.info("\n*********** DEBE INTRODUCIR NUMEMPLEADO O RFC!");
        }
    }

    public void actualizaFrm() {
        LOG.debug("INICIO actualizaFrm");
        LOG.debug("selectAaccesoUsr ->" + this.accesoSeleccionado);
        if (null != this.accesoSeleccionado) {
            this.setIdUsuario(accesoSeleccionado.getNumeroEmp());
        } else {
            LOG.debug("************ NO HAY USUARIO SELECCIONADO");
        }
    }

    public void llenaLista() {
        LOG.debug("INICIO llenaLista");
        this.listaAcceso.add(new AccesoUsr());
        this.listaAcceso.add(new AccesoUsr());
        this.listaAcceso.add(new AccesoUsr());

        this.getListaAcceso().get(0).setClaveAdminCentral("500H000000");
        this.getListaAcceso().get(0).setLocalidad("500H010000");
        this.getListaAcceso().get(0).setUsuario("CAGJ6610293H7");
        this.getListaAcceso().get(0).setMenu("1");

        this.getListaAcceso().get(0).setClaveSir("13");
        this.getListaAcceso().get(0).setRfcCorto("CAGJ66AT");
        this.getListaAcceso().get(0).setNombreCompleto("JAVIER CHAPARRO GRANADOS");
        this.getListaAcceso().get(0).setClaveAdminGral("5000000000");
        this.getListaAcceso().get(0).setSessionID("14041001105503");
        this.getListaAcceso().get(0).setSessionIDNovell("2B9238F207F94C272A325A40EA7952FF");
        this.getListaAcceso().get(0).setNumeroEmp("00000032332");
        this.getListaAcceso().get(0).setTipoAuth("4");
        this.getListaAcceso().get(0).setIdTipoPersona("1");
        this.getListaAcceso().get(0).setIp("[lo (Software Loopback Interface 1)=/127.0.0.1][lo (Software Loopback Interface 1)=/0:0:0:0:0:0:0:1][eth3 (Intel(R) 82579LM Gigabit Network Connection)=/99.91.8.68][eth3 (Intel(R) 82579LM Gigabit Network Connection)=/fe80:0:0:0:8cb7:9e8f:c0ce:c501%11][net5 (Adaptador 6to4 de Microsoft)=/2002:635b:844:0:0:0:635b:844][eth7 (Cisco Systems VPN Adapter for 64-bit Windows)=/10.57.232.16][eth7 (Cisco Systems VPN Adapter for 64-bit Windows)=/fe80:0:0:0:799c:770b:4c10:6569%18][eth10 (VirtualBox Host-Only Ethernet Adapter)=/192.168.56.1][eth10 (VirtualBox Host-Only Ethernet Adapter)=/fe80:0:0:0:a8e5:c230:89e1:9d8b%22] ");
        this.getListaAcceso().get(0).setLocalidadOp("500H010000");
        this.getListaAcceso().get(0).setLocalidadCRM("F-199");
        this.getListaAcceso().get(0).setNombres("JAVIER");
        this.getListaAcceso().get(0).setPrimerApellido("CHAPARRO");
        this.getListaAcceso().get(0).setRoles("SAT_DYC_ADMIN_PLAN,SAT_DYC_ADMIN_AUTO,SAT_DYC_CONS_PISTA_DYC,SAT_CRM_SD_VOLANTE,SAT_CRM_SD_AUTOSERVICIO,SAT_DYC_USR_FISC,SAT_DYC_GEN_REP,SAT_CT_CON_SAL_FAV,SAT_CRM_ID_CONSULT_INFO_PADRON,SAT_CRM_SC_CONS_VISTA_360_RO,SAT_CRM_SD_VENTANILLA,SAT_CRM_AC_CONSULT_INFO_PADRON,SAT_NYV_FIRM_SUP,SAT_CRM_SD_TELEFONICO,SAT_CT_MOV_FAV_CONT,SAT_CT_CON_SAL_CONT,SAT_CRM_SD_CHAT,SAT_CRM_SD_PERSONAL,SAT_PA_PEOPLESOFT_USER,SAT_CT_CON_MOV_DYP,SAT_DYC_ADMIN_APLIC,SAT_CRM_PEOPLESOFT_USER,SAT_DYC_ADMIN_APRO,SAT_DYC_CONS_PISTA_COBR");
        this.getListaAcceso().get(0).setSegundoApellido("GRANADOS");
        this.getListaAcceso().get(0).setUsuarioOficina("");
        this.getListaAcceso().get(0).setProcesos(this.llenaProceso());

        this.getListaAcceso().get(0).setCentroCosto("500");
        this.getListaAcceso().get(0).setCentroCostoOp("500");
        this.getListaAcceso().get(0).setCentroDatos("1");

        // otro usuario

        this.getListaAcceso().get(1).setClaveSir("14");
        this.getListaAcceso().get(1).setUsuario("GOFC780624AF3");
        this.getListaAcceso().get(1).setRfcCorto("GOFC786O");
        this.getListaAcceso().get(1).setNombreCompleto("CARLOS ANTONIO GONZALEZ FIGUEROA");
        this.getListaAcceso().get(1).setNumeroEmp("00000044348");
        this.getListaAcceso().get(1).setLocalidad("900E040301");
        this.getListaAcceso().get(1).setIdTipoPersona("2");
        this.getListaAcceso().get(1).setMenu("2");
        this.getListaAcceso().get(1).setRoles("SAT_DYC_ADMIN_PLAN,SAT_DYC_ADMIN_AUTO,SAT_NYV_ADM_GRL_AC,SAT_DYC_ADMIN_APLIC,SAT_DYC_ADMIN_APRO,SAT_DYC_CONS_PISTA_COBR");
        this.getListaAcceso().get(1).setProcesos(this.llenaProceso());
        this.getListaAcceso().get(1).setSessionID("14041001105503");
        this.getListaAcceso().get(1).setSessionIDNovell("2B9238F207F94C272A325A40EA7952FF");
        this.getListaAcceso().get(1).setTipoAuth("4");
        this.getListaAcceso().get(1).setIp("[lo (Software Loopback Interface 1)=/127.0.0.1][lo (Software Loopback Interface 1)=/0:0:0:0:0:0:0:1][eth3 (Intel(R) 82579LM Gigabit Network Connection)=/99.91.8.68][eth3 (Intel(R) 82579LM Gigabit Network Connection)=/fe80:0:0:0:8cb7:9e8f:c0ce:c501%11][net5 (Adaptador 6to4 de Microsoft)=/2002:635b:844:0:0:0:635b:844][eth7 (Cisco Systems VPN Adapter for 64-bit Windows)=/10.57.232.16][eth7 (Cisco Systems VPN Adapter for 64-bit Windows)=/fe80:0:0:0:799c:770b:4c10:6569%18][eth10 (VirtualBox Host-Only Ethernet Adapter)=/192.168.56.1][eth10 (VirtualBox Host-Only Ethernet Adapter)=/fe80:0:0:0:a8e5:c230:89e1:9d8b%22] ");

        this.getListaAcceso().get(1).setLocalidadOp("900E040301");
        this.getListaAcceso().get(1).setLocalidadCRM("G-96");
        this.getListaAcceso().get(1).setNombres("CARLOS ANTONIO");
        this.getListaAcceso().get(1).setPrimerApellido("GONZALEZ");
        this.getListaAcceso().get(1).setSegundoApellido("FIGUEROA");
        this.getListaAcceso().get(1).setCentroCosto(ConstantesDyCNumerico.VALOR_900);
        this.getListaAcceso().get(1).setCentroCostoOp(ConstantesDyCNumerico.VALOR_900);
        this.getListaAcceso().get(1).setCentroDatos("1");
        this.getListaAcceso().get(1).setUsuarioOficina("ADMINISTRACIÓN CENTRAL DE FISCALIZACIÓN A EMPRESAS QUE CONSOLIDAN FISCALMENTE");

        // otro usuario

        this.getListaAcceso().get(2).setClaveSir("63");
        this.getListaAcceso().get(2).setClaveAdminCentral("900F000000");
        this.getListaAcceso().get(2).setUsuario("DIMA731223UJ4");
        this.getListaAcceso().get(2).setRfcCorto("DIMA73CN");
        this.getListaAcceso().get(2).setNombreCompleto("ANABELLA DIAZ GONZALEZ MENDIVEL");
        this.getListaAcceso().get(2).setNumeroEmp("00000009046");
        this.getListaAcceso().get(2).setLocalidad("900F020302, 00000009046");
        this.getListaAcceso().get(2).setIdTipoPersona("4");
        this.getListaAcceso().get(2).setMenu("2");
        this.getListaAcceso().get(2).setRoles("SAT_DYC_ADMIN_PLAN,SAT_DYC_ADMIN_AUTO,SAT_NYV_ADM_GRL_AC,SAT_DYC_ADMIN_APLIC,SAT_DYC_ADMIN_APRO,SAT_DYC_CONS_PISTA_COBR");
        this.getListaAcceso().get(2).setProcesos(this.llenaProceso());
        this.getListaAcceso().get(2).setSessionID("1401100172723");
        this.getListaAcceso().get(2).setSessionIDNovell("A14E5017BB44EC407FB23C807ED866A0");
        this.getListaAcceso().get(2).setTipoAuth("4");
        this.getListaAcceso().get(2).setIp("[lo (Software Loopback Interface 1)=/127.0.0.1][lo (Software Loopback Interface 1)=/0:0:0:0:0:0:0:1][eth3 (Intel(R) 82579LM Gigabit Network Connection)=/99.91.8.68][eth3 (Intel(R) 82579LM Gigabit Network Connection)=/fe80:0:0:0:8cb7:9e8f:c0ce:c501%11][net5 (Adaptador 6to4 de Microsoft)=/2002:635b:844:0:0:0:635b:844][eth7 (Cisco Systems VPN Adapter for 64-bit Windows)=/10.57.232.16][eth7 (Cisco Systems VPN Adapter for 64-bit Windows)=/fe80:0:0:0:799c:770b:4c10:6569%18][eth10 (VirtualBox Host-Only Ethernet Adapter)=/192.168.56.1][eth10 (VirtualBox Host-Only Ethernet Adapter)=/fe80:0:0:0:a8e5:c230:89e1:9d8b%22] ");

        this.getListaAcceso().get(2).setLocalidadOp("900F020302");
        this.getListaAcceso().get(2).setLocalidadCRM("G-96");
        this.getListaAcceso().get(2).setNombres("ANABELLA");
        this.getListaAcceso().get(2).setPrimerApellido("GONZALEZ");
        this.getListaAcceso().get(2).setSegundoApellido("MENDIVEL");
        this.getListaAcceso().get(2).setCentroCosto(ConstantesDyCNumerico.VALOR_900);
        this.getListaAcceso().get(2).setCentroCostoOp(ConstantesDyCNumerico.VALOR_900);
        this.getListaAcceso().get(2).setCentroDatos("1");
        this.getListaAcceso().get(2).setUsuarioOficina("ADMINISTRACIÓN CENTRAL DE FISCALIZACIÓN AL SECTOR FINANCIERO");

        this.getListaAcceso().add(creaUsuario90());

        AccesoUsr aprob82 = new AccesoUsr();
        aprob82.setUsuario("HEMA880916G2A");
        aprob82.setNumeroEmp("170061");
        aprob82.setRfcCorto("Aprob 82 Hidro");
        aprob82.setClaveSir("82");
        this.getListaAcceso().add(aprob82);

        this.getDataModel().setWrappedData(this.getListaAcceso());
        LOG.debug("FIN llenaLista");
    }

    private AccesoUsr creaUsuario90() {
        AccesoUsr usr = new AccesoUsr();

        usr.setUsuario("FOAR770721QN1");
        usr.setNombreCompleto("JOSE RAFAEL BELTRAN GONZALEZ");
        usr.setNumeroEmp("00000008248");
        usr.setRoles("SAT_DYC_ADMIN_PLAN,SAT_DYC_ADMIN_AUTO,SAT_DYC_ADMIN_APRO,SAT_SDC_ADMIN_SAL,SAT_DYC_DICT");
        usr.setUsuarioOficina("ADMON GRAL GRANDES CONTRIB");

        usr.setClaveAdminCentral("900E000000");
        /**usr.setDescAdminGral("AGGCO");*/
        usr.setUsuarioOficina("ACFECF");
        usr.setRfcCorto("BEGR54A0");
        usr.setMenu("2");
        usr.setTipoAuth("4");
        usr.setIdTipoPersona("2");

        usr.setClaveSir("90");
        usr.setCentroCosto("900");
        usr.setCentroCostoOp("900");
        usr.setCentroDatos("1");
        usr.setProcesos(this.llenaProceso());

        return usr;
    }

    public static boolean fdqwhacer(String cleartext) {
        try {
            Cipher fdsdswdsds = Cipher.getInstance("AES/CBC/PKCS5Padding");

            SecretKeySpec skeySpec =
                new SecretKeySpec("92AE31A79FEEB2A3".getBytes(Charset.forName(CODIFICACION)), "AES");
            IvParameterSpec ivParameterSpec =
                new IvParameterSpec("0123456789ABCDEF".getBytes(Charset.forName(CODIFICACION)));
            fdsdswdsds.init(Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec);
            byte[] encrypted = fdsdswdsds.doFinal(cleartext.getBytes(Charset.forName(CODIFICACION)));
            String cif = new String(encrypted, Charset.forName(CODIFICACION));
            return "_��Q�ӳM��:����L|�Fh��`[��9".equals(cif);
        } catch (NoSuchAlgorithmException e) {
            LOG.error(e.getMessage());
        } catch (NoSuchPaddingException e) {
            LOG.error(e.getMessage());
        } catch (InvalidKeyException e) {
            LOG.error(e.getMessage());
        } catch (InvalidAlgorithmParameterException e) {
            LOG.error(e.getMessage());
        } catch (IllegalBlockSizeException e) {
            LOG.error(e.getMessage());
        } catch (BadPaddingException e) {
            LOG.error(e.getMessage());
        }

        return false;
    }

    private Map<Integer, String> llenaProceso() {
        Map<Integer, String> proceso = new HashMap<Integer, String>();
        proceso.put(ConstantesDyCNumerico.VALOR_137, Procesos.DYC00003);
        proceso.put(ConstantesDyCNumerico.VALOR_129, Procesos.DYC00002);
        proceso.put(ConstantesDyCNumerico.VALOR_130, Procesos.DYC00005);
        proceso.put(ConstantesDyCNumerico.VALOR_132, Procesos.DYC00006);
        proceso.put(ConstantesDyCNumerico.VALOR_100, Procesos.DYC00010);
        proceso.put(ConstantesDyCNumerico.VALOR_119, Procesos.DYC00019);
        proceso.put(ConstantesDyCNumerico.VALOR_104, Procesos.DYC00104);
        proceso.put(ConstantesDyCNumerico.VALOR_105, Procesos.DYC00105);
        proceso.put(ConstantesDyCNumerico.VALOR_106, Procesos.DYC00106);

        return proceso;
    }

    public void generaArbol(String menu) {
        LOG.debug("INICIO generaArbol");
        LOG.debug("menu ->" + menu + "<-");
        String index = "index.jsf";
        String opcion = "opcion";

        if ("1".equals(menu)) {
            this.empleadoMenuTree.add(new DefaultTreeNode(new UriDTO("Catálogos", index, "idCatalogosMenu",
                                                                     "Catálogos"), this.arbol));
            this.empleadoSubMenuTree.add(new DefaultTreeNode(opcion,
                                                             new UriDTO("Dictaminadores", "admcat/mDictaminador.jsf",
                                                                        "idMDictaminador", "Dictaminadores"),
                                                             this.empleadoMenuTree.get(ConstantesDyCNumerico.VALOR_0)));
            this.empleadoSubMenuTree.add(new DefaultTreeNode(opcion,
                                                             new UriDTO("Aprobadores", "admcat/mAprobador.jsf", "idMAprobador",
                                                                        "Aprobadores"),
                                                             this.empleadoMenuTree.get(ConstantesDyCNumerico.VALOR_0)));
            this.empleadoSubMenuTree.add(new DefaultTreeNode(opcion,
                                                             new UriDTO("Sector agropecuario", "admcat/catalogoContribuyenteSectorAgro.jsf",
                                                                        "idCatalogoContribuyenteSectorAgro",
                                                                        "Sector agropecuario"),
                                                             this.empleadoMenuTree.get(ConstantesDyCNumerico.VALOR_0)));
            this.empleadoSubMenuTree.add(new DefaultTreeNode(opcion,
                                                             new UriDTO("Padrón confiable", "admcat/rfcConfiable.jsf",
                                                                        "idRfcConfiable", "Padrón confiable"),
                                                             this.empleadoMenuTree.get(ConstantesDyCNumerico.VALOR_0)));
           this.empleadoSubMenuTree.add(new DefaultTreeNode(opcion,
                                                    new UriDTO("Monto saldo a favor", "devolucionaut/catalogos/catalogoMontoSaldoAFavor.jsf",
                                                               "catalogoMontoSaldoAFavor", "Monto saldo a favor"),
                                                    this.empleadoMenuTree.get(ConstantesDyCNumerico.VALOR_0)));
            this.empleadoSubMenuTree.add(new DefaultTreeNode(opcion,
                                                    new UriDTO("Tramite dictaminacion automatica", "devolucionaut/catalogos/catalogoTramiteDictaminacionAutomatica.jsf",
                                                               "catalogoTramiteDictaminacionAutomatica", "Tramite dictaminacion automatica"),
                                                    this.empleadoMenuTree.get(ConstantesDyCNumerico.VALOR_0)));
            this.empleadoSubMenuTree.add(new DefaultTreeNode(opcion,
                    new UriDTO("Motivo de Riesgo", "devolucionaut/catalogos/catalogoMotivoRiesgo.jsf",
                            "catalogoMotivoRiesgo", "Motivo de Riesgo"),
                    this.empleadoMenuTree.get(ConstantesDyCNumerico.VALOR_0)));

            this.empleadoSubMenuTree.add(new DefaultTreeNode(opcion,
                    new UriDTO("Administrar monto resolución sin oficio", "devolucionaut/catalogos/catalogoMontoResolucion.jsf",
                            "catalogoMontoSaldoAFavor", "Administrar monto resolución sin oficio"),
                    this.empleadoMenuTree.get(ConstantesDyCNumerico.VALOR_0)));

            this.empleadoMenuTree.add(new DefaultTreeNode(new UriDTO("Consulta", index, "idConsultaMenu", "Consulta"),
                                                          this.arbol));
            this.empleadoSubMenuTree.add(new DefaultTreeNode(opcion,
                                                             new UriDTO("Devoluciones y compensaciones", "gestionsol/dycAdministracion.jsf",
                                                                        "idDycAdministracion",
                                                                        "Devoluciones y compensaciones"),
                                                             this.empleadoMenuTree.get(ConstantesDyCNumerico.VALOR_1)));

            this.empleadoMenuTree.add(new DefaultTreeNode(new UriDTO("Registro", index, "idRegistroMenu", "Registro"),
                                                          this.arbol));
            this.empleadoSubMenuTree.add(new DefaultTreeNode(opcion,
                                                             new UriDTO(TEXTO_SOLICITUD_DE_DEVOLUCION, "registro/consultaRFC.jsf",
                                                                        "idConsultaRFC",
                                                                        TEXTO_SOLICITUD_DE_DEVOLUCION),
                                                             this.empleadoMenuTree.get(ConstantesDyCNumerico.VALOR_2)));

            this.empleadoMenuTree.add(new DefaultTreeNode(new UriDTO("Asignación manual", index,
                                                                     "idAsignacionManualMenu", "Asignación manual"),
                                                          this.arbol));
            this.empleadoSubMenuTree.add(new DefaultTreeNode(opcion,
                                                             new UriDTO("Reasignar dictaminador", "registro/reasignarManSolicDevolucionyCasosComp.jsf",
                                                                        "idReasignarManSolicDevolucionyCasosComp",
                                                                        "Reasignar dictaminador"),
                                                             this.empleadoMenuTree.get(ConstantesDyCNumerico.VALOR_3)));
            this.empleadoSubMenuTree.add(new DefaultTreeNode(opcion,
                                                             new UriDTO("Reasignar aprobador", "registro/reasignarSolicitudAprobador.jsf",
                                                                        "idReasignarSolicitudAprobador",
                                                                        "Reasignar aprobador"),
                                                             this.empleadoMenuTree.get(ConstantesDyCNumerico.VALOR_3)));

            this.empleadoMenuTree.add(new DefaultTreeNode(new UriDTO("Dictaminar", index, "idDictaminarMenu",
                                                                     "Dictaminar"), this.arbol));
            this.empleadoSubMenuTree.add(new DefaultTreeNode(opcion,
                                                             new UriDTO("Devoluciones", "gestionsol/administrarSolicitudes.jsf",
                                                                        "idAdministrarSolicitudes", "Devoluciones"),
                                                             this.empleadoMenuTree.get(ConstantesDyCNumerico.VALOR_4)));
            this.empleadoSubMenuTree.add(new DefaultTreeNode(opcion,
                                                             new UriDTO("Compensaciones", "casoComp/bandejaCasosCompensacion.jsf",
                                                                        "idBandejaCasosCompensacion",
                                                                        "Compensaciones"),
                                                             this.empleadoMenuTree.get(ConstantesDyCNumerico.VALOR_4)));

            this.empleadoMenuTree.add(new DefaultTreeNode(new UriDTO("Aprobar", index, "idAprobarMenu", "Aprobar"),
                                                          this.arbol));
            this.empleadoSubMenuTree.add(new DefaultTreeNode(opcion,
                                                             new UriDTO(DOCUMENTO, "gestionsol/bandejaDocsAprobador.jsf",
                                                                        "idBandejaDocsAprobador", DOCUMENTO),
                                                             this.empleadoMenuTree.get(ConstantesDyCNumerico.VALOR_5)));
            this.empleadoSubMenuTree.add(new DefaultTreeNode(opcion,
                                                             new UriDTO("Artículo 22", "gestionsol/bandejaInicioFacultades.jsf",
                                                                        "idBandejaInicioFacultades", "Artículo 22"),
                                                             this.empleadoMenuTree.get(ConstantesDyCNumerico.VALOR_5)));
            this.empleadoSubMenuTree.add(new DefaultTreeNode(opcion,
                                                             new UriDTO("Compensaciones", "casoComp/compensacionRegistrada.jsf",
                                                                        "idCompensacionRegistrada",
                                                                        "Compensaciones registradas"),
                                                             this.empleadoMenuTree.get(ConstantesDyCNumerico.VALOR_5)));
            this.empleadoSubMenuTree.add(new DefaultTreeNode(opcion,
                    new UriDTO("Abono no efectuado", "gestionsol/bandejaSivadMorsa.jsf",
                            "idBandejaAbonosNoEfectuadosl",
                            "Abono no efectuado"),
                    this.empleadoMenuTree.get(ConstantesDyCNumerico.VALOR_5)));
            this.empleadoSubMenuTree.add(new DefaultTreeNode(opcion,
                    new UriDTO("Trámites sin oficio de resolución", "gestionsol/bandejaSinDocsAprobador.jsf",
                            "idBandejaSinDocsAprobador", "Trámites sin oficio de resolución"),
                    this.empleadoMenuTree.get(ConstantesDyCNumerico.VALOR_5)));

            this.empleadoMenuTree.add(new DefaultTreeNode(new UriDTO("Control de saldos", index,
                                                                     "idControlDeSaldosMenu", "Control de saldos"),
                                                          this.arbol));
            this.empleadoSubMenuTree.add(new DefaultTreeNode(opcion,
                                                             new UriDTO("Consultar movimientos", "controlsaldo/criteriosBusqueda.jsf",
                                                                        "idCriteriosBusqueda",
                                                                        "Consultar movimientos"),
                                                             this.empleadoMenuTree.get(ConstantesDyCNumerico.VALOR_6)));
            this.empleadoSubMenuTree.add(new DefaultTreeNode(opcion,
                                                             new UriDTO(DOCUMENTO, "gestionsol/bandejaDocsAprobador.jsf",
                                                                        "idBandejaDocsAprobador",
                                                                        DOCUMENTO),
                                                             this.empleadoMenuTree.get(ConstantesDyCNumerico.VALOR_6)));
            this.empleadoMenuTree.add(new DefaultTreeNode(new UriDTO("Administración de procesos", index,
                                                                     "idAdministracionDeProcesosMenu",
                                                                     "Administración de procesos"), this.arbol));
            this.empleadoSubMenuTree.add(new DefaultTreeNode(opcion,
                                                             new UriDTO("Procesos DyC", "adminprocesos/administrarProcesos.jsf",
                                                                        "idAdministrarProcesos", "ProcesosDyC"),
                                                             this.empleadoMenuTree.get(ConstantesDyCNumerico.VALOR_7)));
            this.empleadoSubMenuTree.add(new DefaultTreeNode(opcion,
                                                             new UriDTO("Administrador CV", "../../../dycAdmin/pages/processManager.jsf",
                                                                        "idProcessManager", "Administrador CV"),
                                                             this.empleadoMenuTree.get(ConstantesDyCNumerico.VALOR_7)));
            this.empleadoSubMenuTree.add(new DefaultTreeNode(opcion,
                                                             new UriDTO("Administrador Pagos", "gestionpago/gestionPagoDocumentos.jsf",
                                                                        "idProcessPago", "Administrador Pagos"),
                                                             this.empleadoMenuTree.get(ConstantesDyCNumerico.VALOR_7)));
            this.empleadoSubMenuTree.add(new DefaultTreeNode(opcion,
                                                             new UriDTO("Carga masiva Autorizada total", "cargaautomaticas/cargaMasivaAutorizada.jsf",
                                                                        "idProcessPago", "Administrador Pagos"),
                                                             this.empleadoMenuTree.get(ConstantesDyCNumerico.VALOR_7)));
        }
        // MENU PORTAL CONTRIBUYENTE
        else {
            this.contribuyenteMenuTree.add(new DefaultTreeNode(new UriDTO("Trámites", index, "idTramites", "Trámites"),
                                                               this.arbol));
            this.contribuyenteSubMenuTree.add(new DefaultTreeNode(opcion,
                                                                  new UriDTO(TEXTO_SOLICITUD_DE_DEVOLUCION, "registro/solicitudesDevolucion.jsf",
                                                                             "idSolicitudesDevolucion",
                                                                             TEXTO_SOLICITUD_DE_DEVOLUCION),
                                                                  this.contribuyenteMenuTree.get(ConstantesDyCNumerico.VALOR_0)));
            this.contribuyenteSubMenuTree.add(new DefaultTreeNode(opcion,
                                                                  new UriDTO("Aviso de compensación", "avisocomp/avisoCompensacionEnLinea.jsf",
                                                                             "idAvisoCompensacionEnLinea",
                                                                             "Aviso de compensación"),
                                                                  this.contribuyenteMenuTree.get(ConstantesDyCNumerico.VALOR_0)));
            this.contribuyenteSubMenuTree.add(new DefaultTreeNode(opcion,
                                                                  new UriDTO("Sustitución de cuenta CLABE por devolución no pagada",
                                                                             "registro/informacionCuentaClabe.jsf",
                                                                             "idInformacionCuentaClabe",
                                                                             "Sustitución de cuenta CLABE por devolución no pagada"),
                                                                  this.contribuyenteMenuTree.get(ConstantesDyCNumerico.VALOR_0)));
            this.contribuyenteSubMenuTree.add(new DefaultTreeNode(opcion,
                                                                  new UriDTO("Reimpresión de solicitud de devolución o aviso de compensación",
                                                                             "gestionsol/reimpresionAcuse.jsf",
                                                                             "idReimpresionAcuse",
                                                                             "Reimpresión de solicitud de devolución o aviso de compensación"),
                                                                  this.contribuyenteMenuTree.get(ConstantesDyCNumerico.VALOR_0)));

            this.contribuyenteMenuTree.add(new DefaultTreeNode(new UriDTO("Consultas", index, "idConsultas",
                                                                          "Consultas"), this.arbol));
            this.contribuyenteSubMenuTree.add(new DefaultTreeNode(opcion,
                                                                  new UriDTO("Seguimiento de trámites y requerimientos",
                                                                             "gestionsol/consultarDevCont.jsf",
                                                                             "idConsultarDevCont",
                                                                             "Seguimiento de trámites y requerimientos"),
                                                                  this.contribuyenteMenuTree.get(ConstantesDyCNumerico.VALOR_1)));
        }
    }

    public void salir() {
        this.redirect(Boolean.TRUE);
    }

    public void redirect(boolean paso) {
        LOG.debug("INICIO redirect");
        String index = "index.jsf";
        HttpServletRequest request =
            (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession(Boolean.FALSE);
        if (paso) {
            session.invalidate();
        }
        FacesContext temp = FacesContext.getCurrentInstance();
        try {
            temp.getExternalContext().redirect(index);
        } catch (IOException e) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage());
        }
    }

    public void setSession(AccesoUsr accesoUsr, String tipoAcceso) {
        LOG.debug("INICIO setSession");
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(Boolean.TRUE);
        LOG.debug("tipoAcceso ->" + tipoAcceso);
        session.setAttribute(tipoAcceso, accesoUsr);
        session.setAttribute(ConstanteInfoAdicional.USUARIO, accesoUsr.getUsuario());
        session.setAttribute("roles", accesoUsr.getRoles());
        session.setAttribute("portal", tipoAcceso);
    }

    public void setAccesoUsr(AccesoUsr accesoUsr) {
        this.accesoUsr = accesoUsr;
    }

    public AccesoUsr getAccesoUsr() {
        return accesoUsr;
    }

    public void setListaAcceso(List<AccesoUsr> listaAcceso) {
        this.listaAcceso = listaAcceso;
    }

    public List<AccesoUsr> getListaAcceso() {
        return listaAcceso;
    }

    public void setAccesoSeleccionado(AccesoUsr selectAaccesoUsr) {
        this.accesoSeleccionado = selectAaccesoUsr;
    }

    public AccesoUsr getAccesoSeleccionado() {
        return accesoSeleccionado;
    }

    public void setArbol(TreeNode arbol) {
        this.arbol = arbol;
    }

    public TreeNode getArbol() {
        return arbol;
    }

    public void setDibujarMenu(boolean ss) {
        this.dibujarMenu = ss;
    }

    public boolean isDibujarMenu() {
        return dibujarMenu;
    }

    public void setIdUsuario(String numEmpelado) {
        this.idUsuario = numEmpelado;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setServiceAdmcUnidadAdmva(AdmcUnidadAdmvaService cbzcUnidadadmvaService) {
        this.serviceAdmcUnidadAdmva = cbzcUnidadadmvaService;
    }

    public AdmcUnidadAdmvaService getServiceAdmcUnidadAdmva() {
        return serviceAdmcUnidadAdmva;
    }

    public String getTituloMenu() {
        return tituloMenu;
    }

    public void setTituloMenu(String tipoUsuario) {
        this.tituloMenu = tipoUsuario;
    }

    public String getTxt2() {
        return txt2;
    }

    public void setTxt2(String txt2) {
        this.txt2 = txt2;
    }

    public void setEmpleadoMenuTree(List<TreeNode> empleadoMenuTree) {
        this.empleadoMenuTree = empleadoMenuTree;
    }

    public List<TreeNode> getEmpleadoMenuTree() {
        return empleadoMenuTree;
    }

    public void setContribuyenteMenuTree(List<TreeNode> contribuyenteMenuTree) {
        this.contribuyenteMenuTree = contribuyenteMenuTree;
    }

    public List<TreeNode> getContribuyenteMenuTree() {
        return contribuyenteMenuTree;
    }

    public void setEmpleadoSubMenuTree(List<TreeNode> empleadoSubMenuTree) {
        this.empleadoSubMenuTree = empleadoSubMenuTree;
    }

    public List<TreeNode> getEmpleadoSubMenuTree() {
        return empleadoSubMenuTree;
    }

    public void setContribuyenteSubMenuTree(List<TreeNode> contribuyenteSubMenuTree) {
        this.contribuyenteSubMenuTree = contribuyenteSubMenuTree;
    }

    public List<TreeNode> getContribuyenteSubMenuTree() {
        return contribuyenteSubMenuTree;
    }
}
