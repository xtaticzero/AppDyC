package mx.gob.sat.siat.dyc.generico.util.common;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.faces.context.FacesContext;

import javax.servlet.http.HttpSession;

import mx.gob.sat.siat.dyc.domain.ags.DyccDeptAgsDTO;
import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidadAdmvaDTO;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.Procesos;
import mx.gob.sat.siat.dyc.util.constante1.ConstanteInfoAdicional;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC2;
import mx.gob.sat.siat.dyc.vistas.dao.AdmcUnidadAdmvaDAO;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("sessionHandler")
public class SessionHandler {
    private static final Logger LOG = Logger.getLogger(SessionHandler.class);

    @Autowired(required = true)
    private AdmcUnidadAdmvaDAO unidadAdm;

    private AccesoUsr accesoUsr;
    private AdmcUnidadAdmvaDTO admva;
    private DyccDeptAgsDTO depto;

    /**
     * Recupera las variables de session y en su caso los envia al service.
     */
    public AccesoUsr obtenerSession() {
        return this.obtenerSession(ConstantesDyC2.TIPO_ACCESO_EMPL);
    }

    /**
     * Recupera las variables de session y en su caso los envia al service.
     * con parametro de tipo sesion
     */
    public AccesoUsr obtenerSession(String tipoAcceso) {
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession( Boolean.TRUE);
        accesoUsr = (AccesoUsr)session.getAttribute(tipoAcceso);

        if (null != accesoUsr) {
            obtieneAdministracion(accesoUsr);
        }
        
        if (null == accesoUsr) {
            session.setAttribute(tipoAcceso, this.llenaSessionDummy(tipoAcceso));
            session.setAttribute(ConstanteInfoAdicional.USUARIO, this.llenaSessionDummy(tipoAcceso).getUsuario());
        }
        return accesoUsr;
    }

    public AccesoUsr obtieneAdministracion(AccesoUsr accesoUsr) {
        if (this.accesoUsr.getClaveSir().equals("90")) {
            if (accesoUsr.getClaveAdminCentral().contains("900I")) {
                accesoUsr.setClaveSir("97");
            } else if (accesoUsr.getClaveAdminCentral().contains("900F")) {
                accesoUsr.setClaveSir("91");
            }
        }
        return accesoUsr;
    }

    /**
     *
     * @return String ip del usuario
     */
    public String obtenerSessionIp() {
        Enumeration e;
        String ip = null;
        try {
            e = NetworkInterface.getNetworkInterfaces();
            NetworkInterface n = (NetworkInterface)e.nextElement();
            Enumeration ee = n.getInetAddresses();
            InetAddress i = (InetAddress)ee.nextElement();
            ip = i.getHostAddress();
        } catch (SocketException f) {
            LOG.info(f.getMessage());
            LOG.info(f.getMessage());
        }
        return ip;
    }

    public void setSession(AccesoUsr accesoUsr, String tipoAcceso) {
        LOG.debug("INICIO setSession");
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession( Boolean.TRUE);
        LOG.debug("tipoAcceso ->" + tipoAcceso);
        session.setAttribute(tipoAcceso, accesoUsr);
        session.setAttribute(ConstanteInfoAdicional.USUARIO, accesoUsr.getUsuario());
        session.setAttribute("roles", accesoUsr.getRoles());
        session.setAttribute("portal", tipoAcceso);
    }

    /**
     * @return
     */
    public HttpSession obtenerSessionID() {
        HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession( Boolean.TRUE);
        if (null != session.getAttribute(ConstantesDyC2.TIPO_ACCESO_EMPL)) {
            return session;
        }
        if (null != session.getAttribute(ConstantesDyC2.TIPO_ACCESO_CONT)) {
            return session;
        }
        session.setAttribute(ConstanteInfoAdicional.USUARIO,
                             this.llenaSessionDummy(ConstantesDyC2.TIPO_ACCESO_EMPL).getUsuario());
        session.setAttribute("roles", this.llenaSessionDummy(ConstantesDyC2.TIPO_ACCESO_EMPL).getRoles());

        return session;
    }

    public AdmcUnidadAdmvaDTO obtenerUnidadAdmitivaSession() {
        try {
            this.accesoUsr = obtenerSession();
            if (null != this.accesoUsr.getClaveSir()) {
                this.admva = new AdmcUnidadAdmvaDTO();
                this.admva.setClaveSir(Integer.parseInt(this.accesoUsr.getClaveSir()));
                this.admva = unidadAdm.consultarUnidadAdmvaList(this.admva).get(ConstantesDyC1.CERO);
            }
        } catch (Exception e) {
            LOG.error("ERROR: " + e.getMessage());
            this.admva = new AdmcUnidadAdmvaDTO();
        }
        return this.admva;
    }

    /**
     * Agrega al objeto de session AccesoUsr datos obtenidos de una session de desarrollo.
     * @param tipoAcceso
     * @return
     */
    public AccesoUsr llenaSessionDummy(String tipoAcceso) {
        accesoUsr = new AccesoUsr();
        Random ran = new Random();
        int limite = ConstantesDyCNumerico.VALOR_9000000;

        if (ConstantesDyC2.TIPO_ACCESO_EMPL.equals(tipoAcceso)) {
            //102C000000
            accesoUsr.setClaveAdminCentral("9000000000");
            accesoUsr.setLocalidad("5620010602");
            accesoUsr.setUsuario("RAHM580403ME3");
            accesoUsr.setMenu("1");
            accesoUsr.setTipoAuth("4");
            accesoUsr.setTipoAuth("2");

        }
        if (ConstantesDyC2.TIPO_ACCESO_CONT.equals(tipoAcceso)) {
            accesoUsr.setClaveAdminCentral("44");
            accesoUsr.setLocalidad("900B000000");
            accesoUsr.setUsuario("SPP060608EN5");

            accesoUsr.setMenu("2");
            accesoUsr.setTipoAuth("2");
        }

        accesoUsr.setClaveSir("13");
        accesoUsr.setRfcCorto("GOFC786O");
        accesoUsr.setNombreCompleto("CARLOS ANTONIO GONZALEZ FIGUEROA ");
        accesoUsr.setClaveAdminGral("9000000000");

        accesoUsr.setSessionID("1404100" + ran.nextInt(limite + 1));
        accesoUsr.setSessionIDNovell("2B9238F207F94C272A325A40EA7952FF");
        accesoUsr.setNumeroEmp("53408");
        accesoUsr.setIdTipoPersona("00");
        accesoUsr.setIp("[lo (Software Loopback Interface 1)=/127.0.0.1][lo (Software Loopback Interface 1)=/0:0:0:0:0:0:0:1][eth3 (Intel(R) 82579LM Gigabit Network Connection)=/99.91.8.68][eth3 (Intel(R) 82579LM Gigabit Network Connection)=/fe80:0:0:0:8cb7:9e8f:c0ce:c501%11][net5 (Adaptador 6to4 de Microsoft)=/2002:635b:844:0:0:0:635b:844][eth7 (Cisco Systems VPN Adapter for 64-bit Windows)=/10.57.232.16][eth7 (Cisco Systems VPN Adapter for 64-bit Windows)=/fe80:0:0:0:799c:770b:4c10:6569%18][eth10 (VirtualBox Host-Only Ethernet Adapter)=/192.168.56.1][eth10 (VirtualBox Host-Only Ethernet Adapter)=/fe80:0:0:0:a8e5:c230:89e1:9d8b%22] ");
        accesoUsr.setLocalidadOp("900E040301");
        accesoUsr.setLocalidadCRM("G-96");
        accesoUsr.setNombres("CARLOS ANTONIO");
        accesoUsr.setPrimerApellido("GONZALEZ");
        accesoUsr.setRoles("SAT_NYV_ADM_GRL_AC,SAT_DYC_DICT,SAT_DYC_CONS_PISTA_COBR");
        accesoUsr.setSegundoApellido("FIGUEROA");
        accesoUsr.setUsuarioOficina("ADMON GRAL GRANDES CONTRIB");

        accesoUsr.setCentroCosto("562");
        accesoUsr.setCentroCostoOp("562");
        accesoUsr.setCentroDatos("1");
        accesoUsr.setMac("[lo (Software Loopback Interface 1)=][eth3 (Intel(R) 82579LM Gigabit Network Connection)=90-B1-1C-7C-C0-E0][net5 (Adaptador 6to4 de Microsoft)=00-00-00-00-00-00-00-E0][eth7 (Cisco Systems VPN Adapter for 64-bit Windows)=00-05-9A-3C-78-00][eth10 (VirtualBox Host-Only Ethernet Adapter)=08-00-27-00-44-9E] ");

        /**accesoUsr.setLstAtributos(new ArrayList<String>());*/
        Map<Integer, String> proceso = new HashMap<Integer, String>();
        proceso.put(ConstantesDyCNumerico.VALOR_136, Procesos.DYC00002);
        proceso.put(ConstantesDyCNumerico.VALOR_137, Procesos.DYC00003);
        proceso.put(ConstantesDyCNumerico.VALOR_130, Procesos.DYC00005);
        proceso.put(ConstantesDyCNumerico.VALOR_132, Procesos.DYC00006);
        proceso.put(ConstantesDyCNumerico.VALOR_722, Procesos.DYC00011);


        accesoUsr.setProcesos(proceso);

        return accesoUsr;
    }

    public void setAccesoUsr(AccesoUsr accesoUsr) {
        this.accesoUsr = accesoUsr;
    }

    public AccesoUsr getAccesoUsr() {
        return accesoUsr;
    }

    public void setAdmva(AdmcUnidadAdmvaDTO admva) {
        this.admva = admva;
    }

    public AdmcUnidadAdmvaDTO getAdmva() {
        return admva;
    }

    public void setDepto(DyccDeptAgsDTO depto) {
        this.depto = depto;
    }

    public DyccDeptAgsDTO getDepto() {
        return depto;
    }

}
