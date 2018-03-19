package mx.gob.sat.mat.dyc.obtenersesion.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import mx.gob.sat.siat.dyc.dao.ags.SatAgsEmpleadosMVDAO;
import mx.gob.sat.siat.dyc.domain.ags.SatAgsEmpleadosMVDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;
import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidadAdmvaDTO;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesIds;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC2;
import mx.gob.sat.siat.dyc.vistas.dao.AdmcUnidadAdmvaDAO;
import mx.gob.sat.siat.dyc.vistas.service.AdmcUnidadAdmvaService;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service(value = "serviceObtenerSesion")
public class ObtenerSesionServiceImpl
{
    private static final Logger LOG = Logger.getLogger(ObtenerSesionServiceImpl.class);

    @Autowired
    private SatAgsEmpleadosMVDAO satAgsEmpleadosMVDAO;
    
    @Autowired
    private AdmcUnidadAdmvaDAO daoUnidadAdmva;

    @Autowired
    private AdmcUnidadAdmvaService serviceUnidadAdmva;
    
    private static final String CENTRO_COSTOS_GRANDESCONT = "900";
    private static final String CENTRO_COSTOS_HIDROCARBUROS = "199";

    private static final String PREFIJO_HIDRO_FISC = "199C";
    private static final String KEY_BANDERA_VALIDACION = "validado";
    private static final String KEY_PORTAL = "portal";
    private static final String CADENA_VACIA = "";

    public AccesoUsr execute(){
        FacesContext context= FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        Map mapaSession = externalContext.getSessionMap();
        
        try{
            
            if (mapaSession == null) {
                LOG.error("mapaSession = externalContext.getSessionMap() -> null");
            }
            
            LOG.info("map.get(portal) ->" + mapaSession.get(KEY_PORTAL));
            String portalAcceso = (String)mapaSession.get(KEY_PORTAL);
            LOG.info("portalAcceso ->" + portalAcceso);
            if(portalAcceso == null){
                LOG.debug("mapaSession ->" + mapaSession);
                portalAcceso = obtenerPortalAcceso(mapaSession);
                LOG.info("portalAcceso actualizado->" + portalAcceso);
            }

            LOG.debug("mapaSession.get(KEY_BANDERA_VALIDACION) ->" + mapaSession.get(KEY_BANDERA_VALIDACION));
            if(mapaSession.get(KEY_BANDERA_VALIDACION) != null){
                LOG.info("La sesion ya fue validada el ->" + mapaSession.get(KEY_BANDERA_VALIDACION));
                return (AccesoUsr)mapaSession.get(portalAcceso);
            }

            LOG.debug("La sesion NO ha sido validada se procedera a validar");
            if(ConstantesDyC2.TIPO_ACCESO_EMPL.equals(portalAcceso))
            {
                org.apache.log4j.Level nivelLogAct = LogManager.getRootLogger().getLevel();
                if(nivelLogAct.isGreaterOrEqual(Level.INFO)){
                    LogManager.getRootLogger().setLevel(Level.INFO);
                }
    
                AccesoUsr sesionEmpleado = (AccesoUsr)mapaSession.get(ConstantesDyC2.TIPO_ACCESO_EMPL);
                LOG.debug("accesoUsr ->" + sesionEmpleado);
    
                LOG.info(new Date() + "<- validando clavesir del empleado ->"  + sesionEmpleado.getNumeroEmp());
    
                String claveSirDYC = obtenerClaveAdmDYC(sesionEmpleado).toString();
                if(claveSirDYC.equals(sesionEmpleado.getClaveSir())){
                    LOG.debug("La claveSir recibida del portal y la claveSir obtenida de MAT-DYC es la misma, no se realizará ninguna accion; claveSIR ->" + claveSirDYC);
                }
                else{
                    LOG.debug("La claveSir recibida del portal y la claveSir obtenida de MAT-DYC son diferentes, se substituira la clave de sesion; claveSirPortal ->" + 
                              sesionEmpleado.getClaveSir()  + "<; claveSirMAT-DYC ->" + claveSirDYC + "<-");
                    sesionEmpleado.setClaveSir(claveSirDYC);
                }
    
                mapaSession.put(KEY_BANDERA_VALIDACION, new Date());
                LogManager.getRootLogger().setLevel(nivelLogAct);
                return sesionEmpleado;
            }
            else{
                LOG.debug("La sesion es de contribuyente, el objeto AccesoUsr se regresara tal cual");
                AccesoUsr sesionContribuyente = (AccesoUsr)mapaSession.get(ConstantesDyC2.TIPO_ACCESO_CONT);
                mapaSession.put(KEY_BANDERA_VALIDACION, new Date());
                return sesionContribuyente;
            }
        }
        catch(Exception ex){
            LOG.error("Ocurrio un error en el Service ObtenerSesionServiceImpl; mensaje ->" + ex.getMessage());
            ManejadorLogException.getTraceError(ex);
            //TODO: eliminar la siguiente forma, la excepción no debería tratarse así.
            LOG.error("El objeto AccesoUsr se regresará tal cual. Revisar Servicio ObtenerSesionServiceImpl");
            AccesoUsr sesionEmpleado = (AccesoUsr)mapaSession.get(ConstantesDyC2.TIPO_ACCESO_EMPL);
            if(sesionEmpleado != null){
                return sesionEmpleado;
            }
            else{
                return (AccesoUsr)mapaSession.get(ConstantesDyC2.TIPO_ACCESO_CONT);
            }
        }
    }

    private String obtenerPortalAcceso(Map mapaSession)
    {
        if(mapaSession.get(ConstantesDyC2.TIPO_ACCESO_EMPL) != null){
            mapaSession.put(KEY_PORTAL, ConstantesDyC2.TIPO_ACCESO_EMPL);
            return ConstantesDyC2.TIPO_ACCESO_EMPL;
        }
        else{
            mapaSession.put(KEY_PORTAL, ConstantesDyC2.TIPO_ACCESO_CONT);
            return ConstantesDyC2.TIPO_ACCESO_CONT;
        }
    }

    private Integer obtenerClaveAdmDYC(AccesoUsr sesionEmpleado) {

        try {

            Integer numeroEmpleado = Integer.parseInt(sesionEmpleado.getNumeroEmp());

            Integer claveAdmEmpleado = 0;
            Integer centroCostosEmpleado = 0;

            Object empleadoDyc = serviceUnidadAdmva.obtenerEmpleadoDyc(numeroEmpleado);
            
            SatAgsEmpleadosMVDTO empleado = satAgsEmpleadosMVDAO.getEmpleadoAGS(numeroEmpleado, Boolean.TRUE);
            verificacionAdminCentralEmleadoSesion(sesionEmpleado, empleado);
            if (empleadoDyc != null) {
                if (empleadoDyc instanceof DyccDictaminadorDTO) {

                    claveAdmEmpleado = ((DyccDictaminadorDTO) empleadoDyc).getClaveAdm();
                    centroCostosEmpleado = ((DyccDictaminadorDTO) empleadoDyc).getCentroCosto();
                    sesionEmpleado.setClaveSir(claveAdmEmpleado.toString());
                    sesionEmpleado.setCentroCosto(centroCostosEmpleado.toString());
                    return ((DyccDictaminadorDTO) empleadoDyc).getClaveAdm();

                } else {

                    claveAdmEmpleado = ((DyccAprobadorDTO) empleadoDyc).getClaveAdm();
                    centroCostosEmpleado = ((DyccAprobadorDTO) empleadoDyc).getCentroCosto();
                    sesionEmpleado.setClaveSir(claveAdmEmpleado.toString());
                    sesionEmpleado.setCentroCosto(centroCostosEmpleado.toString());
                    return ((DyccAprobadorDTO) empleadoDyc).getClaveAdm();

                }
            }

            

            String centroDeCosto = empleado.getCentroCosto();
            List<AdmcUnidadAdmvaDTO> admsClaveAgrs = daoUnidadAdmva.selecXClaveagrs(centroDeCosto);

            if (admsClaveAgrs.size() == 1) {
                AdmcUnidadAdmvaDTO unidadAdmva = admsClaveAgrs.get(0);

                sesionEmpleado.setClaveSir(unidadAdmva.getClaveSir().toString());
                sesionEmpleado.setCentroCosto(centroCostosEmpleado.toString());

                return unidadAdmva.getClaveSir();
            }

            if (admsClaveAgrs.size() > 1) {
                LOG.debug("se encontró mas de una administracion, se utilizara el deptid y el centro de costo del empleado para identificar la buena");

                if (centroDeCosto.equals(CENTRO_COSTOS_GRANDESCONT)) {
                    claveAdmEmpleado = getClaveAdmGrandesContribuyentes(empleado);
                    sesionEmpleado.setClaveSir(claveAdmEmpleado.toString());
                    sesionEmpleado.setCentroCosto(centroCostosEmpleado.toString());

                    return claveAdmEmpleado;
                }

                if (centroDeCosto.equals(CENTRO_COSTOS_HIDROCARBUROS)) {
                    claveAdmEmpleado = getClaveAdmHidrocarburos(empleado);
                    sesionEmpleado.setClaveSir(claveAdmEmpleado.toString());
                    sesionEmpleado.setCentroCosto(centroCostosEmpleado.toString());

                    return claveAdmEmpleado;
                }
            }

        } catch (SIATException e) {
            LOG.error("Ocurrio un error al intentar obtener la claveADM dentro de MAT-DYC; mensaje ->" + e.getMessage());
            ManejadorLogException.getTraceError(e);
        }

        return 0;
    };

    private Integer getClaveAdmGrandesContribuyentes ( SatAgsEmpleadosMVDTO empleado ){
        LOG.debug( "EL CENTRO costro pert a grandes" );

        String departamentoId = empleado.getDeptid();

        if ( departamentoId.startsWith( "900E" ) ){
            return ConstantesIds.ClavesAdministraciones.CENTRAL_EMPR_CONSOLIDAN_FISC;
        }

        if ( departamentoId.contains( "900F" ) ) {
            return ConstantesIds.ClavesAdministraciones.CENTRAL_FISC_SECTOR_FINANCIERO;
        }
        return ConstantesIds.ClavesAdministraciones.CENTRAL_GRANDES_CONTRIBUYENTES;
    }

    private void verificacionAdminCentralEmleadoSesion ( AccesoUsr sesionEmpleado, SatAgsEmpleadosMVDTO empleado ){

        if ( sesionEmpleado.getClaveAdminCentral() == null || 
                sesionEmpleado.getClaveAdminCentral().equals( CADENA_VACIA ) ) {

            LOG.error( "Se detecta que el objeto AccesoUsr no contiene una ClaveAdminCentral valida, se setteara el DEPTID del objeto DycvEmpleadoDTO" );
            sesionEmpleado.setClaveAdminCentral( empleado.getDeptid() );
        }
    }

    private Integer getClaveAdmHidrocarburos ( SatAgsEmpleadosMVDTO empleado ){
        LOG.debug( "Por el centro de costo se determino que es de hidro falta ver si de 81 u 82" );
        if ( empleado.getDeptid().startsWith( PREFIJO_HIDRO_FISC ) ){
            return ConstantesIds.ClavesAdministraciones.CENTRAL_FISC_HIDROCARBUROS;
        }

        return ConstantesIds.ClavesAdministraciones.CENTRAL_VERIF_HIDROCARBUROS;
    }

}
