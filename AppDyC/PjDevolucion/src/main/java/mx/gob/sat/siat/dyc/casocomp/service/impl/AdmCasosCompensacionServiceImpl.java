package mx.gob.sat.siat.dyc.casocomp.service.impl;

import java.io.InputStream;

import java.sql.Date;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.casocomp.service.IAdmCasosCompensacionService;
import mx.gob.sat.siat.dyc.casocomp.web.controller.bean.utility.FilaGridUsuariosLoginAuxBean;
import mx.gob.sat.siat.dyc.dao.declaracion.DyctNotaDAO;
import mx.gob.sat.siat.dyc.dao.tiposerv.IDycpServicioDAO;
import mx.gob.sat.siat.dyc.dao.usuario.DyccAprobadorDAO;
import mx.gob.sat.siat.dyc.dao.usuario.DyccDictaminadorDAO;
import mx.gob.sat.siat.dyc.domain.declaracion.DyctNotaDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;
import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidadAdmvaDTO;
import mx.gob.sat.siat.dyc.domain.vistas.EnumTipoUnidadAdmvaDyC;
import mx.gob.sat.siat.dyc.generico.util.ArchivoCargaDescarga;
import mx.gob.sat.siat.dyc.generico.util.common.Utilerias;
import mx.gob.sat.siat.dyc.trabajo.util.constante.EnumRol;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.ItemComboBean;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCURL;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.vistas.dao.AdmcUnidadAdmvaDAO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service("serviceAdmCasosComp")
public class AdmCasosCompensacionServiceImpl implements IAdmCasosCompensacionService {
    private static final Logger LOG = Logger.getLogger(AdmCasosCompensacionServiceImpl.class);

    @Autowired
    private DyccAprobadorDAO daoAprobador;

    @Autowired
    private DyccDictaminadorDAO daoDictaminador;

    @Autowired
    private DyctNotaDAO daoNota;

    @Autowired
    private IDycpServicioDAO daoServicio;

    @Autowired
    private AdmcUnidadAdmvaDAO daoUnidadesAdmvas;

    public void insertarNota(Map<String, Object> params) {
        DyctNotaDTO dto = new DyctNotaDTO();
        DycpServicioDTO servicio = new DycpServicioDTO();
        servicio.setNumControl((String)params.get("numControlComp"));
        dto.setDycpServicioDTO(servicio);
        dto.setTexto((String)params.get("texto"));
        dto.setFecha(new Date(new java.util.Date().getTime()));
        dto.setUsuario((String)params.get("nomCompUsuario"));
        daoNota.insertar(dto);
    }

    @Override
    public List<ItemComboBean> obtenerSuperiores() {
        LOG.info("INICIO obtenerSuperiores");
        List<ItemComboBean> superiores = new ArrayList<ItemComboBean>();
        List<DyccAprobadorDTO> aprobadores = daoAprobador.seleccionar();
        for (DyccAprobadorDTO dto : aprobadores) {
            ItemComboBean bean = new ItemComboBean();
            String empleado = String.valueOf(dto.getNumEmpleado());
            bean.setIdLong(Long.parseLong(empleado));
            String nc = dto.getNombre() + " " + dto.getApellidoPaterno() + " " + dto.getApellidoMaterno();
            bean.setDescripcion(nc);
            superiores.add(bean);
        }
        LOG.info("numero de superiores ->" + superiores.size());
        return superiores;
    }

    @Override
    public List<FilaGridUsuariosLoginAuxBean> obtenerUsuariosLoginAux() {
        List<FilaGridUsuariosLoginAuxBean> usuarios = new ArrayList<FilaGridUsuariosLoginAuxBean>();
        List<DyccDictaminadorDTO> dtos = daoDictaminador.seleccionar();
        for (DyccDictaminadorDTO dto : dtos) {
            FilaGridUsuariosLoginAuxBean usuario = new FilaGridUsuariosLoginAuxBean();
            usuario.setNumEmpleado(dto.getNumEmpleado().toString());
            usuario.setNombre(dto.getNombre() + " " + dto.getApellidoPaterno() + " " + dto.getApellidoMaterno());
            usuario.setRol("Dictaminador");
            usuarios.add(usuario);
        }
        return usuarios;
    }

    /**
     * @param claveADM
     * @return
     */
    @Override
    public List<ItemComboBean> obtenerSuperiores(Integer claveADM, Integer cCosto) {

        List<ItemComboBean> superiores = new ArrayList<ItemComboBean>();
        List<DyccAprobadorDTO> aprobadores = daoAprobador.consultarAprobadores(claveADM, cCosto);
        for (DyccAprobadorDTO dto : aprobadores) {
            ItemComboBean bean = new ItemComboBean();
            bean.setIdLong(dto.getNumEmpleado().longValue());
            bean.setId(dto.getNumEmpleado());
            bean.setDescripcion(dto.getNombre() + " " + dto.getApellidoPaterno() + " " + dto.getApellidoMaterno());
            superiores.add(bean);
        }
        LOG.info("numero de superiores encontrados ->" + superiores.size());
        return superiores;
    }


    /**
     * @param claveADM
     * @return
     */
    @Override
    public List<ItemComboBean> obtenerSuperioresAprobarResol(Integer claveADM, Integer cCosto, Integer numEmpleado,
                                                             Integer nivel) {

        List<ItemComboBean> superiores = new ArrayList<ItemComboBean>();
        List<DyccAprobadorDTO> aprobadores =
            daoAprobador.consultarAprobadoresAprobarResol(claveADM, cCosto, numEmpleado, nivel);
        for (DyccAprobadorDTO dto : aprobadores) {
            ItemComboBean bean = new ItemComboBean();
            bean.setIdLong(dto.getNumEmpleado().longValue());
            bean.setId(dto.getNumEmpleado());
            bean.setDescripcion(dto.getNombre() + " " + dto.getApellidoPaterno() + " " + dto.getApellidoMaterno());
            superiores.add(bean);
        }
        LOG.info("numero de superiores encontrados ->" + superiores.size());
        return superiores;
    }


    @Override
    public Map<String, Object> obtenerDatosSesion(Integer numEmpleado, EnumRol rol) {
        LOG.debug("INICIO obtenerDatosSesion");
        LOG.debug("numEmpleado ->" + numEmpleado + "<-");
        Map<String, Object> datos = new HashMap<String, Object>();
        if (rol == EnumRol.DICTAMINADOR) {
            DyccDictaminadorDTO dictaminador = daoDictaminador.encontrar(numEmpleado);
            LOG.debug("dictaminador ->" + dictaminador + "<-");
            datos.put("nombreCompleto",
                      dictaminador.getNombre() + " " + dictaminador.getApellidoPaterno() + " " + dictaminador.getApellidoMaterno());
            datos.put("claveAdm", dictaminador.getClaveAdm());
        }
        if (rol == EnumRol.ADMINISTRADOR) {
            DyccAprobadorDTO aprobador = daoAprobador.encontrar(numEmpleado);
            LOG.debug("aprobador->" + aprobador + "<-");
            LOG.debug("datos Aprobador ->" + ExtractorUtil.ejecutar(aprobador) + "<-");
            String nc =
                aprobador.getNombre() + " " + aprobador.getApellidoPaterno() + " " + aprobador.getApellidoMaterno();
            datos.put("nombreCompleto", nc);
            datos.put("claveAdm", aprobador.getClaveAdm());
        }

        LOG.debug("claveAdm ->" + datos.get("claveAdm") + "<-");

        return datos;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public String guardarArchivo(DycpServicioDTO servicioR, InputStream secuenciaEntrada,
                                 String nombreArchivo) throws SIATException {
        DycpServicioDTO servicioL;
        StringBuilder ruta = new StringBuilder();
        try {
            if (servicioR.getClaveAdm() == null) {
                servicioL = daoServicio.encontrar(servicioR.getNumControl());
            } else {
                servicioL = servicioR;
            }
            ruta.append(ConstantesDyCURL.URL_DOCUMENTOS);
            ruta.append("/");
            ruta.append(String.format("%02d", servicioL.getClaveAdm()));
            ruta.append("/");
            ruta.append(Utilerias.corregirRFC(servicioL.getRfcContribuyente()));
            ruta.append("/");
            ruta.append(servicioL.getNumControl());
            ruta.append(ConstantesDyCURL.TIPO_ARCHIVO_DOC);
            ruta.append("/");

            ArchivoCargaDescarga utileriaArchivos = new ArchivoCargaDescarga();
            utileriaArchivos.escribirArchivo(nombreArchivo, secuenciaEntrada, ruta.toString(),
                                             ConstantesDyCNumerico.VALOR_4096);
        } catch (SIATException se) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + se.getMessage() + ConstantesDyC1.TEXTO_8_CAUSAS + se);
            throw new SIATException(se);
        }
        return ruta.toString();
    }

    @Override
    public EnumTipoUnidadAdmvaDyC obtnerTipoUnidadAdmva(Integer claveAdm) {
        List<AdmcUnidadAdmvaDTO> unidadesAdmvas = daoUnidadesAdmvas.selecXClavesir(claveAdm);
        for (AdmcUnidadAdmvaDTO unidadAdmva : unidadesAdmvas) {
            if (unidadAdmva.getTipoUnidadAdmva() != null) {
                LOG.debug("se encontro el tipo de la unidad administrativa ->" + unidadAdmva.getTipoUnidadAdmva());
                return unidadAdmva.getTipoUnidadAdmva();
            }
        }
        LOG.debug("NO se encontro el tipo de la unidad administrativa");

        return null;
    }
}
