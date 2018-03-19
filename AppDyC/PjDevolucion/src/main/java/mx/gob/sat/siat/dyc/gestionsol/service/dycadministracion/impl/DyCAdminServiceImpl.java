/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.gestionsol.service.dycadministracion.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.admcat.dao.matrizdictaminadores.MatrizDictaminadoresDAO;
import mx.gob.sat.siat.dyc.admcat.dto.matrizdictaminadores.MatrizDictaminadorVO;
import mx.gob.sat.siat.dyc.catalogo.service.DyccEstadoSolService;
import mx.gob.sat.siat.dyc.catalogo.service.DyccTipoTramiteService;
import mx.gob.sat.siat.dyc.dao.compensacion.DyccEstadoCompDAO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccEstadoCompDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccEstadoSolDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidadAdmvaDTO;
import mx.gob.sat.siat.dyc.gestionsol.dao.dycadministracion.DyCAdministracionDAO;
import mx.gob.sat.siat.dyc.gestionsol.dto.dycadministracion.AdmSolicitudesdycVO;
import mx.gob.sat.siat.dyc.gestionsol.service.dycadministracion.DyCAdminService;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesLog;
import mx.gob.sat.siat.dyc.vistas.service.AdmcUnidadAdmvaService;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Clase Implementacion service para administracion devoluciones y casos de compensacion
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @date Septimbre 19, 2013
 * @since 0.1
 *
 * */
@Service(value = "dyCAdminService")
@Transactional
public class DyCAdminServiceImpl implements DyCAdminService {
    private static final Logger LOG = Logger.getLogger(DyCAdminServiceImpl.class);

    @Autowired(required = true)
    private DyCAdministracionDAO dyCAdministracionDAO;
    @Autowired(required = true)
    private AdmcUnidadAdmvaService admcUnidadAdmvaService;
    @Autowired(required = true)
    private MatrizDictaminadoresDAO matrizDictaminadoresDAO;
    @Autowired(required = true)
    private DyccTipoTramiteService dyccTipoTramiteService;
    @Autowired(required = true)
    private DyccEstadoSolService dyccEstadoSolService;
    @Autowired(required = true)
    private DyccEstadoCompDAO dyccEstadoCompDAO;

    private List<AdmcUnidadAdmvaDTO> listaAdministraciones;
    private List<MatrizDictaminadorVO> listaDictaminadores;
    private List<DyccTipoTramiteDTO> listaTipoTramite;
    private List<DyccEstadoSolDTO> listaEstadoSol;

    private AdmcUnidadAdmvaDTO admva;
    private List<AdmSolicitudesdycVO> listaSolicitud;

    public DyCAdminServiceImpl() {
        this.listaAdministraciones = new ArrayList<AdmcUnidadAdmvaDTO>();
        this.listaDictaminadores = new ArrayList<MatrizDictaminadorVO>();
        this.listaTipoTramite = new ArrayList<DyccTipoTramiteDTO>();
        this.listaEstadoSol = new ArrayList<DyccEstadoSolDTO>();
        this.listaSolicitud = new ArrayList<AdmSolicitudesdycVO>();
    }

    public List<AdmSolicitudesdycVO> obtenerSolicitudes(AdmSolicitudesdycVO solicitud, Integer tramite) {
        try {

            if(tramite == ConstantesDyCNumerico.VALOR_1){
                solicitud.setEstadoSolicitud(solicitud.getEstado());
                this.setListaSolicitud(dyCAdministracionDAO.obtenerListaSolicitud(solicitud));
            }else if(tramite == ConstantesDyCNumerico.VALOR_2){
                solicitud.setEstadoComp(solicitud.getEstado());
                this.setListaSolicitud(dyCAdministracionDAO.obtenerListaCompensaciones(solicitud));
            }else{
                this.setListaSolicitud(dyCAdministracionDAO.obtenerListaGeneral(solicitud));
            }

        } catch (Exception e) {
            LOG.error(ConstantesLog.TEXTO_ERROR + e.getMessage());
        }
        return this.getListaSolicitud();
    }

    @Transactional(readOnly = false)
    public List<AdmcUnidadAdmvaDTO> obtenerAdministraciones() {
        try {
            this.setListaAdministraciones(admcUnidadAdmvaService.consultarUnidadAdmvaList(new AdmcUnidadAdmvaDTO()));
        } catch (Exception e) {
            LOG.error(ConstantesLog.TEXTO_ERROR + e.getMessage());
        }
        return this.getListaAdministraciones();
    }

    @Transactional(readOnly = false)
    public List<MatrizDictaminadorVO> obtenerDictaminadores(AdmcUnidadAdmvaDTO adm) {
        try {
            this.setListaDictaminadores(matrizDictaminadoresDAO.obtenerListaDictaminadores(adm));
        } catch (Exception e) {
            LOG.error(ConstantesLog.TEXTO_ERROR + e.getMessage());
        }
        return this.getListaDictaminadores();
    }

    @Transactional(readOnly = false)
    public List<DyccTipoTramiteDTO> obtenerTipoTRamite(Integer tramite) {
        try {
            this.setListaTipoTramite(dyccTipoTramiteService.obtenerTramites(tramite));
        } catch (Exception e) {
            LOG.error(ConstantesLog.TEXTO_ERROR + e.getMessage());
        }
        return this.getListaTipoTramite();
    }

    @Transactional(readOnly = true)
    public List<DyccEstadoSolDTO> obtenerEstadoSol() {
        try {
            this.setListaEstadoSol(dyccEstadoSolService.obtenerLista());
        } catch (Exception e) {
            LOG.error(ConstantesLog.TEXTO_ERROR + e.getMessage());
        }
        return this.getListaEstadoSol();
    }

    @Transactional(readOnly = true)
    public List<DyccEstadoCompDTO> obtenerEstadoComp() {
        return dyccEstadoCompDAO.obtenerLista();
    }

    public void setListaAdministraciones(List<AdmcUnidadAdmvaDTO> listaAdministraciones) {
        this.listaAdministraciones = listaAdministraciones;
    }

    public List<AdmcUnidadAdmvaDTO> getListaAdministraciones() {
        return listaAdministraciones;
    }

    public void setListaDictaminadores(List<MatrizDictaminadorVO> listaDictaminadores) {
        this.listaDictaminadores = listaDictaminadores;
    }

    public List<MatrizDictaminadorVO> getListaDictaminadores() {
        return listaDictaminadores;
    }

    public void setListaTipoTramite(List<DyccTipoTramiteDTO> listaTipoTramite) {
        this.listaTipoTramite = listaTipoTramite;
    }

    public List<DyccTipoTramiteDTO> getListaTipoTramite() {
        return listaTipoTramite;
    }

    public void setListaEstadoSol(List<DyccEstadoSolDTO> listaEstadoSol) {
        this.listaEstadoSol = listaEstadoSol;
    }

    public List<DyccEstadoSolDTO> getListaEstadoSol() {
        return listaEstadoSol;
    }


    public void setAdmva(AdmcUnidadAdmvaDTO admva) {
        this.admva = admva;
    }

    public AdmcUnidadAdmvaDTO getAdmva() {
        return admva;
    }

    public void setListaSolicitud(List<AdmSolicitudesdycVO> listaSolicitud) {
        this.listaSolicitud = listaSolicitud;
    }

    public List<AdmSolicitudesdycVO> getListaSolicitud() {
        return listaSolicitud;
    }

    
}
