package mx.gob.sat.siat.dyc.controlsaldos.service.impl;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.casocomp.util.Utils;
import mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.utility.FilaGridAccionesAjusteBean;
import mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.utility.FilaGridMovsSaldoBean;
import mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.utility.FilaGridPermisosAjusteBean;
import mx.gob.sat.siat.dyc.dao.accion.DyctAccionMovSalDAO;
import mx.gob.sat.siat.dyc.dao.documento.DyctAccionPrivAjusDAO;
import mx.gob.sat.siat.dyc.dao.movsaldo.DyctMovSaldoDAO;
import mx.gob.sat.siat.dyc.dao.usuario.DyccAprobadorDAO;
import mx.gob.sat.siat.dyc.dao.usuario.DyccDictaminadorDAO;
import mx.gob.sat.siat.dyc.domain.movsaldo.DyctAccionPrivAjusDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccMovIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctAccionMovSalDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctMovSaldoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;
import mx.gob.sat.siat.dyc.domain.vistas.DycvEmpleadoDTO;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante1.ConstantePerfilUsr;
import mx.gob.sat.siat.dyc.vistas.dao.DycvEmpleadoDAO;
import mx.gob.sat.siat.dyc.vistas.service.AdmcUnidadAdmvaService;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author Softtek
 */
@Service(value = "delegateMovimientosSaldo")
public class MovimientosSaldoBussinesDel {
    private static final Logger LOG = Logger.getLogger(MovimientosSaldoBussinesDel.class.getName());

    @Autowired
    private DyctMovSaldoDAO daoMovSaldo;

    @Autowired
    private DyctAccionMovSalDAO daoAccionMovSaldo;

    @Autowired
    private DyccAprobadorDAO daoAprobador;

    @Autowired
    private DyccDictaminadorDAO daoDictaminador;

    @Autowired
    private DyctAccionPrivAjusDAO daoPermisosAjuste;

    @Autowired
    private AdmcUnidadAdmvaService serviceUnidadAdmva;

    @Autowired
    private DycvEmpleadoDAO daoVistaEmpleado;

    private static final Integer[] IDS_PERMISOS_ESPECIALES = new Integer[] { 1, 2 };

    private static final String KEY_MENSAJE = "mensaje";
    private static final String KEY_PERMISO = "permiso";
    private static final String KEY_EXISTEPERMISO_MATDYC = "existePermisoMatDyc";

    public List<FilaGridMovsSaldoBean> obtenerMovimientos(Integer idSaldoIcep) {
        DyctSaldoIcepDTO saldoIcep = new DyctSaldoIcepDTO();
        saldoIcep.setIdSaldoIcep(idSaldoIcep);
        List<DyctMovSaldoDTO> dtos = daoMovSaldo.selecXSaldoicep(saldoIcep);
        List<FilaGridMovsSaldoBean> beans = new ArrayList<FilaGridMovsSaldoBean>();
        for (DyctMovSaldoDTO dto : dtos) {
            beans.add(crearFilaBean(dto));
        }
        return beans;
    }

    public Map<String, Object> obtenerInfoInicial(Integer idSaldoIcep) {
        LOG.debug("INICIO obtenerInfoInicial; idSaldoIcep ->" + idSaldoIcep);
        Map<String, Object> infoInicial = new HashMap<String, Object>();

        infoInicial.put("filasMovimientos", obtenerMovimientos(idSaldoIcep));

        return infoInicial;
    }

    private FilaGridMovsSaldoBean crearFilaBean(DyctMovSaldoDTO dto) {
        String estatus = "Inactivado";
        StringBuilder sbInfoAdicional = new StringBuilder("");

        if (dto.getDyctAccionMovSalDTOList() == null || dto.getDyctAccionMovSalDTOList().isEmpty()) {
            estatus = "Vigente";
        } else {
            DyctAccionMovSalDTO ultimaAccion =
                dto.getDyctAccionMovSalDTOList().get(dto.getDyctAccionMovSalDTOList().size() - 1);
            if (ultimaAccion.getTipoAccionMovSal() ==
                mx.gob.sat.siat.dyc.domain.resolucion.DyctAccionMovSalDTO.TipoAccionMovSaldo.VALIDAR) {
                estatus = "Vigente";
                sbInfoAdicional.append("Activado por ");
            } else if (ultimaAccion.getTipoAccionMovSal() ==
                       mx.gob.sat.siat.dyc.domain.resolucion.DyctAccionMovSalDTO.TipoAccionMovSaldo.CREAR) {
                estatus = "Vigente";
                sbInfoAdicional.append("Creado por ");
            } else {
                sbInfoAdicional.append("Inactivada por ");
            }

            sbInfoAdicional.append(ultimaAccion.getDyctAccionPrivAjusDTO().getIdAccionPrivAjus());
            sbInfoAdicional.append(" el ");
            sbInfoAdicional.append(Utils.formatearFechaHora(ultimaAccion.getFechaRegistro()));
            sbInfoAdicional.append(" Justificación: ");
            sbInfoAdicional.append(ultimaAccion.getJustificacion());
        }

        FilaGridMovsSaldoBean filaBean = new FilaGridMovsSaldoBean();
        filaBean.setIdMovSaldo(dto.getIdMovSaldo());
        filaBean.setFechaRegistro(dto.getFechaRegistro());
        filaBean.setFechaOrigen(dto.getFechaOrigen());
        filaBean.setTipoMovimiento(dto.getDyccMovIcepDTO().getDyccAfectaIcepDTO().getDescripcion());
        filaBean.setOrigen(dto.getIdOrigen());
        filaBean.setMonto(dto.getImporte().doubleValue());
        filaBean.setDescrOrigen(dto.getDyccMovIcepDTO().getDescripcionLarga());
        filaBean.setEstatus(estatus);
        filaBean.setInfoAdicional(sbInfoAdicional.toString());
        filaBean.setIdMovimiento(dto.getDyccMovIcepDTO().getIdMovimiento());

        return filaBean;
    }

    public void eliminarCargo(Integer idMovSaldo) {
        LOG.debug("INICIO eliminarCargo");
        try {
            daoMovSaldo.borrar(idMovSaldo);
        } catch (SIATException ex) {
            LOG.error("error al borrar el movSaldo; ->" + ex.getMessage());
            ManejadorLogException.getTraceError(ex);
        }
    }

    @Transactional
    public void insertarMovimiento(Map<String, Object> params) {
        try {
            LOG.debug("INICIO insertarMovimiento");
            DyctSaldoIcepDTO saldoIcep = new DyctSaldoIcepDTO();
            saldoIcep.setIdSaldoIcep((Integer)params.get("idSaldoIcep"));

            DyccMovIcepDTO tipoMov = null;
            if ((Integer)params.get("idTipoAfectacion") ==
                Constantes.AfectacionesIcep.ABONO.getIdAfectacion().intValue()) {
                tipoMov = Constantes.MovsIcep.ABONO_AJUSTE;
            } else if ((Integer)params.get("idTipoAfectacion") ==
                       Constantes.AfectacionesIcep.CARGO.getIdAfectacion().intValue()) {
                tipoMov = Constantes.MovsIcep.CARGO_AJUSTE;
            }

            DyctMovSaldoDTO movSaldo = new DyctMovSaldoDTO();

            movSaldo.setDyctSaldoIcepDTO(saldoIcep);
            movSaldo.setImporte((BigDecimal)params.get("monto"));
            movSaldo.setFechaRegistro(new Date());
            movSaldo.setFechaOrigen((Date)params.get("fechaOrigen"));
            movSaldo.setDyccMovIcepDTO(tipoMov);
            movSaldo.setIdOrigen((String)params.get("origen"));
            daoMovSaldo.insertar(movSaldo);

            LOG.debug("se debío de haber insertado el movSaldo ->" + movSaldo.getIdMovSaldo());

            DyctAccionMovSalDTO accionCrearMov = new DyctAccionMovSalDTO();
            accionCrearMov.setDyctMovSaldoDTO(movSaldo);
            accionCrearMov.setTipoAccionMovSal(mx.gob.sat.siat.dyc.domain.resolucion.DyctAccionMovSalDTO.TipoAccionMovSaldo.CREAR);
            accionCrearMov.setFechaRegistro(new Date());
            accionCrearMov.setDyctAccionPrivAjusDTO((DyctAccionPrivAjusDTO)params.get("permisoAjustar"));
            accionCrearMov.setJustificacion((String)params.get("justificacion"));
            daoAccionMovSaldo.insertar(accionCrearMov);
        } catch (SIATException ex) {
            LOG.error(ex.getMessage());
        }
    }

    public void actualizarMovimiento(Map<String, Object> params) {
        DyctMovSaldoDTO movSaldo = new DyctMovSaldoDTO();
        movSaldo.setIdMovSaldo((Integer)params.get("idMovSaldo"));

        LOG.debug("IdMovSaldo ->" + movSaldo.getIdMovSaldo());

        DyctAccionMovSalDTO accionMovSal = new DyctAccionMovSalDTO();
        accionMovSal.setDyctMovSaldoDTO(movSaldo);
        accionMovSal.setTipoAccionMovSal((DyctAccionMovSalDTO.TipoAccionMovSaldo)params.get("tipoAccion"));
        accionMovSal.setFechaRegistro(new Date());
        accionMovSal.setDyctAccionPrivAjusDTO((DyctAccionPrivAjusDTO)params.get("permisoAjustar"));
        accionMovSal.setJustificacion((String)params.get("justificacion"));
        daoAccionMovSaldo.insertar(accionMovSal);
    }

    public List<FilaGridAccionesAjusteBean> obtenerFilasAcciones() {
        List<FilaGridAccionesAjusteBean> filasAcciones = new ArrayList<FilaGridAccionesAjusteBean>();
        List<DyctAccionMovSalDTO> dtos = daoAccionMovSaldo.selecBitacora();
        for (DyctAccionMovSalDTO accionIt : dtos) {
            filasAcciones.add(crearFilaBitacora(accionIt));
        }

        return filasAcciones;
    }

    private FilaGridAccionesAjusteBean crearFilaBitacora(DyctAccionMovSalDTO accionDTO) {
        String tipoAfectacion =
            accionDTO.getDyctMovSaldoDTO().getDyccMovIcepDTO().getDyccAfectaIcepDTO().getDescripcion();
        String movimiento = tipoAfectacion + "(" + accionDTO.getDyctMovSaldoDTO().getIdMovSaldo() + ")";

        String responsable;
        LOG.debug("accionDTO.getDyctAccionPrivAjusDTO().getIdAccionPrivAjus() ->" +
                  accionDTO.getDyctAccionPrivAjusDTO().getIdAccionPrivAjus());
        if (accionDTO.getDyctAccionPrivAjusDTO().getIdAccionPrivAjus() ==
            Constantes.PermisosEspecialesAjustarSaldo.MAT_DYC.getIdAccionPrivAjus().intValue()) {
            responsable = "MAT-DYC";
        } else if (accionDTO.getDyctAccionPrivAjusDTO().getIdAccionPrivAjus() ==
                   Constantes.PermisosEspecialesAjustarSaldo.ADMIN_CENTRAL.getIdAccionPrivAjus().intValue()) {
            responsable = "Administrador central";
        } else {
            LOG.debug("accionDTO.getDyctAccionPrivAjusDTO() ->" + accionDTO.getDyctAccionPrivAjusDTO());
            LOG.debug("accionDTO.getDyctAccionPrivAjusDTO().getEmpleado() ->" +
                      accionDTO.getDyctAccionPrivAjusDTO().getEmpleado());
            LOG.debug("accionDTO.getDyctAccionPrivAjusDTO().getEmpleado().getNombreCompleto() ->" +
                      accionDTO.getDyctAccionPrivAjusDTO().getEmpleado().getNombreCompleto());
            responsable = accionDTO.getDyctAccionPrivAjusDTO().getEmpleado().getNombreCompleto();
        }

        FilaGridAccionesAjusteBean filaAccionBean = new FilaGridAccionesAjusteBean();

        filaAccionBean.setIdAccionMovSal(accionDTO.getIdAccionMovSal());

        filaAccionBean.setMovimiento(movimiento);
        filaAccionBean.setMonto(accionDTO.getDyctMovSaldoDTO().getImporte().doubleValue());
        filaAccionBean.setIdSaldoIcep(accionDTO.getDyctMovSaldoDTO().getDyctSaldoIcepDTO().getIdSaldoIcep());
        filaAccionBean.setResponsable(responsable);
        filaAccionBean.setJustificacion(accionDTO.getJustificacion());
        filaAccionBean.setFecha(accionDTO.getFechaRegistro());

        switch (accionDTO.getTipoAccionMovSal().getId()) {
        case ConstantesDyCNumerico.VALOR_1:
            filaAccionBean.setStyleClass("ui-button-icon-center ui-icon ui-icon-trash");
            filaAccionBean.setTipoAccion("Se invalidó " + tipoAfectacion);
            break;
        case ConstantesDyCNumerico.VALOR_2:
            filaAccionBean.setStyleClass("ui-button-icon-center ui-icon ui-icon-check");
            filaAccionBean.setTipoAccion("Se validó " + tipoAfectacion);
            break;
        case ConstantesDyCNumerico.VALOR_3:
            filaAccionBean.setStyleClass("ui-button-icon-center ui-icon ui-icon-document");
            filaAccionBean.setTipoAccion("Se creó " + tipoAfectacion);
            break;
        default:
            LOG.info("Tipo accion no es válida");
        }

        return filaAccionBean;
    }

    public Map<String, Object> obtenerInfoEmpleado(String palabraClave) {
        LOG.debug("INICIO obtenerInfoEmpleado palabraClave->" + palabraClave);
        Map<String, Object> infoEmpleado = new HashMap<String, Object>();
        infoEmpleado.put("userValido", Boolean.FALSE);

        Integer numEmpleado = null;
        try {
            numEmpleado = Integer.parseInt(palabraClave);
        } catch (java.lang.NumberFormatException e) {
            LOG.debug("la palabra clave no es un número");
        }

        DycvEmpleadoDTO empleadoSAT = null;

        if (numEmpleado != null) {
            empleadoSAT = daoVistaEmpleado.encontrar(numEmpleado);
        } else {
            LOG.debug("verificar si la palabra clave es un RFC");
            if (serviceUnidadAdmva.esRFCCorto(palabraClave)) {
                empleadoSAT = daoVistaEmpleado.selecXRfccorto(palabraClave);
            } else {
                LOG.debug("el parametro introducido NO es un RFC corto");
                if (serviceUnidadAdmva.esRFCValido(palabraClave)) {
                    empleadoSAT = daoVistaEmpleado.selecXRfc(palabraClave);
                } else {
                    LOG.debug("El identificador de usuario introducido NO es un RFC valido");
                    infoEmpleado.put(KEY_MENSAJE,
                                     "La cadena '" + palabraClave + "' no es un parámetro de búsqueda válido");
                    return infoEmpleado;
                }
            }
        }

        if (empleadoSAT != null) {
            if ("A".equals(empleadoSAT.getEstatus())) {
                Map<String, Object> respPermisoMatDyc = obtenerPermisoMATDYC(empleadoSAT);

                if ((Boolean)respPermisoMatDyc.get(KEY_EXISTEPERMISO_MATDYC)) {
                    infoEmpleado.put("userValido", Boolean.TRUE);

                    infoEmpleado.put("numEmpleado", empleadoSAT.getNoEmpleado());
                    infoEmpleado.put("rfc", empleadoSAT.getRfc());
                    infoEmpleado.put("nombre", empleadoSAT.getNombreCompleto());
                    Object permiso = respPermisoMatDyc.get(KEY_PERMISO);
                    if (permiso instanceof DyccAprobadorDTO) {
                        DyccAprobadorDTO aprobador = (DyccAprobadorDTO)permiso;

                        infoEmpleado.put("unidadAdministrativa", aprobador.getClaveAdm().toString());
                        infoEmpleado.put("rolMatDyc", "Aprobador");
                    } else {
                        DyccDictaminadorDTO dictaminador = (DyccDictaminadorDTO)permiso;

                        infoEmpleado.put("unidadAdministrativa", dictaminador.getClaveAdm().toString());
                        infoEmpleado.put("rolMatDyc", "Dictaminador");
                    }

                    return infoEmpleado;
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (String msj : (List<String>)respPermisoMatDyc.get("mensajes")) {
                        sb.append(msj);
                        sb.append(" - ");
                    }

                    infoEmpleado.put(KEY_MENSAJE, sb.toString());

                }
            } else {
                infoEmpleado.put(KEY_MENSAJE,
                                 "El empleado " + empleadoSAT.getNombreCompleto() + " no se encuentra activo en la base de datos de AGRS");
            }
        } else {
            infoEmpleado.put(KEY_MENSAJE, "No se encontró a ningún empleado del SAT con ese parámetro de búsqueda");
        }

        return infoEmpleado;
    }

    private Map<String, Object> obtenerPermisoMATDYC(DycvEmpleadoDTO empleadoSAT) {
        Map<String, Object> respuesta = new HashMap<String, Object>();
        List<String> mensajes = new ArrayList<String>();

        DyccAprobadorDTO aprobador = daoAprobador.encontrar(empleadoSAT.getNoEmpleado());
        if (aprobador != null) {
            if (aprobador.getActivo() == 1) {
                respuesta.put(KEY_EXISTEPERMISO_MATDYC, Boolean.TRUE);
                respuesta.put(KEY_PERMISO, aprobador);
                return respuesta;
            } else {
                mensajes.add("El usuario " + empleadoSAT.getNombreCompleto() +
                             " esta registrado como aprobador pero esta inactivo");
            }
        }

        DyccDictaminadorDTO dictaminador = daoDictaminador.encontrar(empleadoSAT.getNoEmpleado());
        if (dictaminador != null) {
            if (dictaminador.getActivo() == 1) {
                respuesta.put(KEY_EXISTEPERMISO_MATDYC, Boolean.TRUE);
                respuesta.put(KEY_PERMISO, dictaminador);
                return respuesta;
            } else {
                mensajes.add("El usuario " + empleadoSAT.getNombreCompleto() +
                             " esta registrado como dictaminador pero esta inactivo");
            }
        } else {
            mensajes.add("El empleado del SAT " + empleadoSAT.getNombreCompleto() +
                         " NO esta autorizado para operar MAT-DYC");
        }

        respuesta.put(KEY_EXISTEPERMISO_MATDYC, Boolean.FALSE);
        respuesta.put("mensajes", mensajes);

        return respuesta;
    }

    public Map<String, Object> insertarPermiso(Integer numEmpleado, Integer numEmpResp) {

        DyctAccionPrivAjusDTO permiso = new DyctAccionPrivAjusDTO();
        permiso.setNumEmpleado(numEmpleado);
        permiso.setRespPriv(numEmpResp);
        permiso.setFechaRegistroPriv(new Date());
        permiso.setTipoAccionPriv(mx.gob.sat.siat.dyc.domain.resolucion.DyctAccionMovSalDTO.TipoAccionPermisoAjuste.OTORGAR.getId());

        daoPermisosAjuste.insertar(permiso);

        return null;
    }

    public List<FilaGridPermisosAjusteBean> obtenerPermisosVigentes() {
        List<FilaGridPermisosAjusteBean> permisos = new ArrayList<FilaGridPermisosAjusteBean>();
        List<DyctAccionPrivAjusDTO> accionesPrivsDtos = daoPermisosAjuste.selecUltimasAccionesXEmpleado();
        for (DyctAccionPrivAjusDTO dto : accionesPrivsDtos) {
            if (!Arrays.asList(IDS_PERMISOS_ESPECIALES).contains(dto.getIdAccionPrivAjus()) &&
                (dto.getTipoAccionPriv() ==
                 mx.gob.sat.siat.dyc.domain.resolucion.DyctAccionMovSalDTO.TipoAccionPermisoAjuste.OTORGAR.getId().intValue())) {
                FilaGridPermisosAjusteBean permiso = new FilaGridPermisosAjusteBean();
                permiso.setNumEmpleado(dto.getNumEmpleado());
                permiso.setRfc(dto.getEmpleado().getRfc());
                permiso.setNombre(dto.getEmpleado().getNombreCompleto());
                permiso.setUnidadAdmva(dto.getEmpleado().getCentroCostoDescr());
                permiso.setFechaPrivilegio(dto.getFechaRegistroPriv());
                permisos.add(permiso);
            }
        }
        return permisos;
    }

    public void revocarGrantAjuste(Integer numEmpleado, Integer numEmpResp) {

        DyctAccionPrivAjusDTO revocacion = new DyctAccionPrivAjusDTO();
        revocacion.setNumEmpleado(numEmpleado);
        revocacion.setRespPriv(numEmpResp);
        revocacion.setFechaRegistroPriv(new Date());
        revocacion.setTipoAccionPriv(mx.gob.sat.siat.dyc.domain.resolucion.DyctAccionMovSalDTO.TipoAccionPermisoAjuste.REVOCAR.getId());

        daoPermisosAjuste.insertar(revocacion);
    }

    public DyctAccionPrivAjusDTO obtenerPermisoAjuste(AccesoUsr accesoUsrL) {
        String roles = accesoUsrL.getRoles();
        if (roles.contains(ConstantePerfilUsr.PERFIL_ADM_CENTRAL) ||
            Integer.parseInt(accesoUsrL.getNumeroEmp()) == ConstantesDyCNumerico.VALOR_75768) {
            return Constantes.PermisosEspecialesAjustarSaldo.ADMIN_CENTRAL;
        }

        DyctAccionPrivAjusDTO ultAccionPerm =
            daoPermisosAjuste.selecUltimaAccionEmpleado(Integer.parseInt(accesoUsrL.getNumeroEmp()));
        if (ultAccionPerm != null &&
            ultAccionPerm.getTipoAccionPriv() == mx.gob.sat.siat.dyc.domain.resolucion.DyctAccionMovSalDTO.TipoAccionPermisoAjuste.OTORGAR.getId().intValue()) {
            return ultAccionPerm;
        }
        return null;
    }

    public List<FilaGridAccionesAjusteBean> obtenerBitacoraAjustesSaldoIcep(Integer idSaldoIcep) {
        DyctSaldoIcepDTO saldoIcep = new DyctSaldoIcepDTO();
        saldoIcep.setIdSaldoIcep(idSaldoIcep);
        List<DyctAccionMovSalDTO> ajustesIcep =
            daoAccionMovSaldo.selecOrderXSaldoicep(saldoIcep, " ORDER BY ACCMOV.FECHAREGISTRO DESC ");
        List<FilaGridAccionesAjusteBean> accionesAjuste = new ArrayList<FilaGridAccionesAjusteBean>();
        for (DyctAccionMovSalDTO ajusteDTO : ajustesIcep) {
            accionesAjuste.add(crearFilaBitacora(ajusteDTO));
        }
        LOG.debug("numero de filas ajuste para el icep ->" + idSaldoIcep + "; -->" + accionesAjuste.size());
        return accionesAjuste;
    }
}
