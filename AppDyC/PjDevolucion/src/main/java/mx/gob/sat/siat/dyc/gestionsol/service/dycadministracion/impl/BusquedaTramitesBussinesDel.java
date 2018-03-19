package mx.gob.sat.siat.dyc.gestionsol.service.dycadministracion.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mx.gob.sat.siat.dyc.dao.ags.SatAgsEmpleadosMVDAO;

import mx.gob.sat.siat.dyc.dao.compensacion.IDycpCompensacionDAO;
import mx.gob.sat.siat.dyc.dao.detalleicep.DyctSaldoIcepDAO;
import mx.gob.sat.siat.dyc.dao.regsolicitud.DycpSolicitudDAO;
import mx.gob.sat.siat.dyc.dao.tiposerv.IDycpServicioDAO;
import mx.gob.sat.siat.dyc.dao.tipotramite.DyccTipoTramiteDAO;
import mx.gob.sat.siat.dyc.dao.usuario.DyccDictaminadorDAO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccImpuestoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpAvisoCompDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoServicioDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidadAdmvaDTO;
import mx.gob.sat.siat.dyc.generico.util.Utils;
import mx.gob.sat.siat.dyc.domain.resolucion.FilaGridTramitesBean;
import mx.gob.sat.siat.dyc.gestionsol.web.controller.utility.FilaGridUnidadesAdmvas;
import mx.gob.sat.siat.dyc.trabajo.util.constante.ConstantesDyCWeb;
import mx.gob.sat.siat.dyc.util.BuscadorConstantes;
import mx.gob.sat.siat.dyc.util.common.ItemComboBean;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyC;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesEstados;
import mx.gob.sat.siat.dyc.util.constante.ConstantesPerfiles;
import mx.gob.sat.siat.dyc.util.constante.enums.ClavesAdministraciones;
import mx.gob.sat.siat.dyc.vistas.dao.AdmcUnidadAdmvaDAO;
import mx.gob.sat.siat.dyc.vistas.service.AdmcUnidadAdmvaService;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;
import org.apache.commons.lang3.StringUtils;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "delegateBusquedaTramites")
public class BusquedaTramitesBussinesDel {

    private static final Logger LOG = Logger.getLogger(BusquedaTramitesBussinesDel.class);

    @Autowired
    private IDycpServicioDAO daoServicio;

    @Autowired
    private IDycpCompensacionDAO daoCompensacion;

    @Autowired
    private DycpSolicitudDAO daoSolicitud;

    @Autowired
    private DyccDictaminadorDAO daoDictaminador;

    @Autowired
    private DyccTipoTramiteDAO daoTipoTramite;

    @Autowired
    private DyctSaldoIcepDAO daoSaldoIcep;

    @Autowired
    private SatAgsEmpleadosMVDAO satAgsEmpleadosMVDAO;

    @Autowired
    private AdmcUnidadAdmvaDAO daoUnidadesAdmvas;

    @Autowired
    private AdmcUnidadAdmvaService serviceUnidadAdmva;

    public static class TiposCadena {

        public static final int NUMCONTROL = 1;
        public static final int RFC = 2;
        public static final int FOLIO_AVISO_MAT = 3;
        public static final int PATRON_NO_RECONOCIDO = 4;
    }

    public static final String KEY_FILTRAR_DICTS = "permitirFiltrarDictaminadores";
    public static final String KEY_TRAMS_ECONTRADOS = "tramites";
    private static final String DESC_ARTICULO22 = "En revisión artículo 22 CFF";
    public static final String CAMPO_ORDENAMIENTO_IMPORTE_CONSULTA    = "IMPORTE";
    public static final String IDTIPOSERVICIO = "idTipoServicio";
    private static final int MAX_REGISTROS_EXPORTACION = 65535;

    public Map<String, Object> obtenerInfoInicial(AccesoUsr accesoUsr) {
        Map<String, Object> infoInicial = new HashMap<String, Object>();
        Integer claveSirUsr = Integer.parseInt(accesoUsr.getClaveSir());
        List<AdmcUnidadAdmvaDTO> unidadesAdmvasUsr = daoUnidadesAdmvas.selecXClavesir(claveSirUsr);

        if (!unidadesAdmvasUsr.isEmpty()) {
            AdmcUnidadAdmvaDTO unidadAdmva = unidadesAdmvasUsr.get(0);
            infoInicial.put("nombreUnidadAdmva", unidadAdmva.getNombre());
        } else {
            infoInicial.put("nombreUnidadAdmva", "No se encontró la unidad administrativa");
        }

        if (claveSirUsr == ClavesAdministraciones.CENTRAL_DYC) {
            List<AdmcUnidadAdmvaDTO> unidadesAdmvas = daoUnidadesAdmvas.seleccionarOrderBy(" ORDER BY CLAVE_SIR ASC ");
            List<FilaGridUnidadesAdmvas> beansUnidadesAdmvas = new ArrayList<FilaGridUnidadesAdmvas>();
            for (AdmcUnidadAdmvaDTO dtoUniAdmva : unidadesAdmvas) {
                if (dtoUniAdmva.getClaveSir() != null && dtoUniAdmva.getClaveSir() != 0) {
                    beansUnidadesAdmvas.add(crearFilaUnidadAdmva(dtoUniAdmva));
                }
            }

            infoInicial.put("unidadesAdmvas", beansUnidadesAdmvas);
            infoInicial.put("dictaminadores", obtenerDictaminadores((Integer) null));

            infoInicial.put("permitirFiltrarUnidadesAdmvas", Boolean.TRUE);
            infoInicial.put(KEY_FILTRAR_DICTS, Boolean.TRUE);
        } else {
            infoInicial.put("permitirFiltrarUnidadesAdmvas", Boolean.FALSE);

            Object empleadoDyc = serviceUnidadAdmva.obtenerEmpleadoDyc(Integer.parseInt(accesoUsr.getNumeroEmp()));

            if (empleadoDyc instanceof DyccAprobadorDTO) {
                infoInicial.put(KEY_FILTRAR_DICTS, Boolean.TRUE);
                infoInicial.put("dictaminadores", obtenerDictaminadores(Integer.parseInt(accesoUsr.getClaveSir())));
            } else {
                infoInicial.put(KEY_FILTRAR_DICTS, Boolean.FALSE);
            }
        }

        return infoInicial;
    }

    public FilaGridUnidadesAdmvas crearFilaUnidadAdmva(AdmcUnidadAdmvaDTO dto) {
        FilaGridUnidadesAdmvas fila = new FilaGridUnidadesAdmvas();
        fila.setClaveSir(dto.getClaveSir());
        fila.setNombre(dto.getNombre());
        fila.setEntidadFederativa(dto.getAdmcUnidadAdmDomDTO() != null ? dto.getAdmcUnidadAdmDomDTO().getEntidadFed() : "");

        return fila;
    }

    public int validarPatron(String palabraClave) {
        if (palabraClave.matches(ConstantesDyCWeb.ExpresionesRegulares.NUMCONTROL)) {
            return TiposCadena.NUMCONTROL;
        } else if (palabraClave.matches(ConstantesDyCWeb.ExpresionesRegulares.RFC)) {
            return TiposCadena.RFC;
        } else if (palabraClave.matches(ConstantesDyCWeb.ExpresionesRegulares.FOLIOAVISO_MAT)) {
            return TiposCadena.FOLIO_AVISO_MAT;
        } else {
            return TiposCadena.PATRON_NO_RECONOCIDO;
        }
    }

    public Map<String, Object> buscarXPalabraClave(Map<String, Object> paramsBusqueda) {
        Map<String, Object> respuesta = new HashMap<String, Object>();
        List<FilaGridTramitesBean> tramites = new ArrayList<FilaGridTramitesBean>();
        String palabraClave = (String) paramsBusqueda.get("palabraClave");
        LOG.debug("palabraClave ->" + palabraClave + "<-");
        int tipoCadena = (Integer) paramsBusqueda.get("tipoCadena");
        AccesoUsr accesoUsr = (AccesoUsr) paramsBusqueda.get("accesoUsr");

        String mensaje = null;
        int totalRegs = 0;
        try {
            switch (tipoCadena) {
                case TiposCadena.NUMCONTROL:
                    DycpServicioDTO servicio = daoServicio.encontrar(palabraClave);

                    if (servicio != null) {
                        if (tramiteAutorizado(servicio, accesoUsr)) {
                            tramites.add(crearFilaGrid(servicio));
                            totalRegs = 1;
                        } else {
                            mensaje = "El trámite " + servicio.getNumControl() + " si existe, Usted no cuenta con los permisos necesarios para consultarlo.";
                        }
                    }
                    break;
                case TiposCadena.RFC:
                    List<DycpServicioDTO> servicios = daoServicio.selecXRfc(palabraClave);
                    int contTramsNoAutorizados1 = 0;
                    for (DycpServicioDTO s : servicios) {
                        if (tramiteAutorizado(s, accesoUsr)) {
                            tramites.add(crearFilaGrid(s));
                        } else {
                            contTramsNoAutorizados1++;
                        }
                    }
                    if (contTramsNoAutorizados1 > 0) {
                        mensaje = "No se muestra(n) " + contTramsNoAutorizados1 + " trámite(s) del contribuyente '" + palabraClave
                                + "' debido a que no se cuenta con los permisos necesarios para consultarlo(s)";
                    }

                    totalRegs = tramites.size();

                    break;
                case TiposCadena.FOLIO_AVISO_MAT:
                    LOG.debug("buscar por FOLIO aviso MAT");
                    DycpAvisoCompDTO avisoComp = new DycpAvisoCompDTO();
                    avisoComp.setFolioAviso(palabraClave);
                    List<DycpCompensacionDTO> compensaciones = daoCompensacion.selecXAvisocomp(avisoComp);
                    LOG.debug("numero de compensaciones encontradas para el aviso ->" + compensaciones.size());
                    int contTramsNoAutorizados = 0;
                    for (DycpCompensacionDTO c : compensaciones) {
                        if (tramiteAutorizado(c, accesoUsr)) {
                            tramites.add(crearFilaGrid(c.getDycpServicioDTO()));
                        } else {
                            contTramsNoAutorizados++;
                        }
                    }
                    if (contTramsNoAutorizados > 0) {
                        mensaje = "No se muestra(n) " + contTramsNoAutorizados + " trámite(s) del aviso de compensación '" + palabraClave
                                + "' debido a que no se cuenta con los permisos necesarios para consultarlo(s)";
                    }
                    totalRegs = tramites.size();
                    break;
                default:
                    LOG.error("Tipo de cadena no válida");
                    break;

            }
        } catch (SIATException e) {
            LOG.error("error en BusquedaTramitesBussinesDel");
            ManejadorLogException.getTraceError(e);
        }

        respuesta.put(KEY_TRAMS_ECONTRADOS, tramites);
        respuesta.put("mensaje", mensaje);
        respuesta.put("totalRegs", totalRegs);
        return respuesta;
    }

    private boolean tramiteAutorizado(DycpServicioDTO tramite, AccesoUsr accesoUsr) {
        Integer numEmpleado = Integer.parseInt(accesoUsr.getNumeroEmp());
        Object empleadoDyc = serviceUnidadAdmva.obtenerEmpleadoDyc(numEmpleado);
        Integer claveAdmTramite = obtieneClaveADM(tramite, null, Boolean.TRUE);
        if (!accesoUsr.getRoles().contains(ConstantesPerfiles.PERFIL_CONS_GENERAL)) {
            if (empleadoDyc != null) {
                if (empleadoDyc instanceof DyccDictaminadorDTO) {
                    if (tramite.getDyccDictaminadorDTO() != null
                            && tramite.getDyccDictaminadorDTO().getNumEmpleado() == numEmpleado.intValue()) {
                        return Boolean.TRUE;
                    }
                } else {
                    DyccAprobadorDTO aprobador = (DyccAprobadorDTO) empleadoDyc;
                    if (aprobador.getClaveAdm() != null && aprobador.getClaveAdm() == ClavesAdministraciones.CENTRAL_DYC) {
                        return Boolean.TRUE;
                    }

                    if (claveAdmTramite != null && claveAdmTramite.intValue() == aprobador.getClaveAdm()) {
                        return Boolean.TRUE;
                    }
                }
            } else {
                LOG.error("El empleado del SAT " + accesoUsr.getNumeroEmp() + " NO esta dado de alta en MAT-DYC, NO se le mostrará ningun trámite");
            }
        } else {
            Integer[] arrayAdm = {Integer.parseInt(accesoUsr.getClaveSir()), claveAdmTramite};
            return autorizaConsulta(arrayAdm);
        }

        return false;
    }

    public boolean autorizaConsulta(Integer[] clavesAdm) {
        Integer centroCostos = null;
        Integer[] arrAdministracion = new Integer[clavesAdm.length];

        for (int i = 0; i < clavesAdm.length; i++) {
            centroCostos = serviceUnidadAdmva.obtieneCentroCostos(clavesAdm[i]);
            if (null != centroCostos) {
                if (centroCostos == (ConstantesDyC.CENTRO_COSTOS_ACDC)) {
                    arrAdministracion[i] = ConstantesDyC.ID_ADM_ACDC;
                    return Boolean.TRUE;
                } else if (centroCostos >= ConstantesDyC.CENTRO_COSTOS_INI_AGSC
                        && centroCostos <= ConstantesDyC.CENTRO_COSTOS_FIN_AGSC) {
                    arrAdministracion[i] = ConstantesDyC.ID_ADM_AGSC;
                    return Boolean.TRUE;
                } else if (centroCostos >= ConstantesDyC.CENTRO_COSTOS_INI_ADAF
                        && centroCostos <= ConstantesDyC.CENTRO_COSTOS_FIN_ADAF) {
                    arrAdministracion[i] = ConstantesDyC.ID_ADM_ADAF;
                } else if (centroCostos == (ConstantesDyC.CENTRO_COSTOS_AGACE_INT)) {
                    arrAdministracion[i] = ConstantesDyC.ID_ADM_AGACE;
                } else if (centroCostos == (ConstantesDyC.CENTRO_COSTOS_AGGC)) {
                    arrAdministracion[i] = ConstantesDyC.ID_ADM_AGGC;
                } else if (centroCostos == (ConstantesDyC.CENTRO_COSTOS_AGH)) {
                    arrAdministracion[i] = ConstantesDyC.ID_ADM_AGH;
                }
            } else {
                return Boolean.TRUE;
            }

        }

        return arrAdministracion[ConstantesDyCNumerico.VALOR_0].intValue() == arrAdministracion[ConstantesDyCNumerico.VALOR_1].intValue();
    }

    private Integer obtieneClaveADM(DycpServicioDTO tramite, Object empleadoDyc, boolean isTramite) {
        if (isTramite) {
            if (tramite.getClaveAdm() == null) {
                DycpCompensacionDTO comp = (DycpCompensacionDTO) tramite;
                return comp.getDycpServicioDTO().getClaveAdm();
            } else {
                return tramite.getClaveAdm();
            }
        } else if (empleadoDyc instanceof DyccDictaminadorDTO) {
            return tramite.getDyccDictaminadorDTO().getClaveAdm();
        } else {
            DyccAprobadorDTO aprobador = (DyccAprobadorDTO) empleadoDyc;
            return aprobador.getClaveAdm();
        }
    }

    private FilaGridTramitesBean crearFilaGrid(DycpServicioDTO servicio) {

        DyccTipoServicioDTO tipoServicio = servicio.getDycaOrigenTramiteDTO().getDycaServOrigenDTO().getDyccTipoServicioDTO();

        FilaGridTramitesBean fila = generarFilaTramite(servicio, tipoServicio);

        if (tipoServicioEsCasoCompensacion(tipoServicio) || tipoServicioEsAvisoCompensacion(tipoServicio)) {

            complementaFilaDatosCompensacion(fila, servicio);

        } else if (tipoServicioEsSolicitudDevolucion(tipoServicio) || tipoServicioEsDevolucionAutomatica(tipoServicio)) {

            complementaFilaDatosSolicitudDevolucion(fila, servicio);
        }

        return fila;
    }

    private boolean tipoServicioEsCasoCompensacion(DyccTipoServicioDTO tipoServicio) {

        return tipoServicioSonIguales(tipoServicio, Constantes.TiposServicio.CASO_COMPENSACION);
    }

    private boolean tipoServicioEsAvisoCompensacion(DyccTipoServicioDTO tipoServicio) {

        return tipoServicioSonIguales(tipoServicio, Constantes.TiposServicio.AVISO_COMPENSACION);
    }

    private boolean tipoServicioEsSolicitudDevolucion(DyccTipoServicioDTO tipoServicio) {

        return tipoServicioSonIguales(tipoServicio, Constantes.TiposServicio.SOLICITUD_DEVOLUCION);
    }

    private boolean tipoServicioEsDevolucionAutomatica(DyccTipoServicioDTO tipoServicio) {

        return tipoServicioSonIguales(tipoServicio, Constantes.TiposServicio.DEVOLUCION_AUTOMATICA);
    }

    private boolean tipoServicioSonIguales(DyccTipoServicioDTO tipoServicioA, DyccTipoServicioDTO tipoServicioB) {

        return tipoServicioA.getIdTipoServicio().intValue() == tipoServicioB.getIdTipoServicio().intValue();
    }

    private String getNombreDictaminadorService(DyccDictaminadorDTO dict) {

        LOG.debug("dict ->" + dict + "<-");

        StringBuilder nombDict = new StringBuilder("");

        if (dict != null && dict.getNumEmpleado() != null) {

            nombDict
                    .append(dict.getNumEmpleado())
                    .append(" - ")
                    .append(dict.getNombre())
                    .append(" ")
                    .append(dict.getApellidoPaterno())
                    .append(" ")
                    .append(dict.getApellidoMaterno());
        }

        return nombDict.toString();
    }

    private FilaGridTramitesBean generarFilaTramite(DycpServicioDTO servicio, DyccTipoServicioDTO tipoServicio) {

        FilaGridTramitesBean fila = new FilaGridTramitesBean();

        DyccDictaminadorDTO dict = servicio.getDyccDictaminadorDTO();

        String rfcContribuyente = servicio.getRfcContribuyente();
        String tramite = tipoServicio.getDescripcion();
        String numeroControl = servicio.getNumControl();
        String tipoTramite = servicio.getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getDescripcion();
        String nombDict = getNombreDictaminadorService(dict);
        String administracion = getNombreAdministracion(servicio);

        Integer dias = servicio.getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getPlazo();
        Integer tipoDia = servicio.getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getDyccTipoPlazoDTO().getIdTipoPlazo();

        fila.setRfc(rfcContribuyente);
        fila.setDias(dias);
        fila.setTipoDia(tipoDia);
        fila.setTramite(tramite);
        fila.setNumControl(numeroControl);
        fila.setTipoTramite(tipoTramite);
        fila.setDictaminador(nombDict);
        fila.setAdministracion(administracion);

        return fila;
    }

    private String getNombreAdministracion(DycpServicioDTO servicio) {

        if (servicio.getDyccUnidadAdmvaDTO() != null) {
            return servicio.getDyccUnidadAdmvaDTO().getNomAbreviado();
        }

        return "";
    }

    private void complementaFilaDatosCompensacion(FilaGridTramitesBean fila, DycpServicioDTO servicio) {

        DycpCompensacionDTO compensacion = getCompensacionServicio(servicio);
        if (compensacion != null) {
            vaciarDatosCompensacionFila(fila, compensacion);
        }
    }

    private void vaciarDatosCompensacionFila(FilaGridTramitesBean fila, DycpCompensacionDTO compensacion) {
        fila.setEstado((compensacion.getDyccEstadoCompDTO().getIdEstadoComp().equals(ConstantesDyCNumerico.VALOR_4)) ? DESC_ARTICULO22 : compensacion.getDyccEstadoCompDTO().getDescripcion());
        fila.setImporte(compensacion.getImporteCompensado().doubleValue());
        fila.setFechaPresentacion(compensacion.getFechaPresentacion());

        if (compensacion.getDyctSaldoIcepOrigenDTO() != null) {
            if (compensacion.getDyctSaldoIcepOrigenDTO().getDyccPeriodoDTO() != null) {
                fila.setPeriodo(compensacion.getDyctSaldoIcepOrigenDTO().getDyccPeriodoDTO().getDescripcion());
            }
            if (compensacion.getDyctSaldoIcepOrigenDTO().getDyccEjercicioDTO() != null) {
                LOG.info("Valor periodo: " + compensacion.getDyctSaldoIcepOrigenDTO().getDyccEjercicioDTO().getIdEjercicio().toString());
                fila.setEjercicio(compensacion.getDyctSaldoIcepOrigenDTO().getDyccEjercicioDTO().getIdEjercicio().toString());
            }
            if (compensacion.getDyctSaldoIcepOrigenDTO().getDyccConceptoDTO() != null) {
                fila.setConceptoImpuestos(compensacion.getDyctSaldoIcepOrigenDTO().getDyccConceptoDTO().getDescripcion());
                if (compensacion.getDyctSaldoIcepOrigenDTO().getDyccConceptoDTO().getDyccImpuestoDTO() != null) {
                    fila.setImpuestos(compensacion.getDyctSaldoIcepOrigenDTO().getDyccConceptoDTO().getDyccImpuestoDTO().getDescripcion());
                }
            }
        }
        if (compensacion.getDyctResolCompDTO() != null) {
            LOG.info("Tiene resolucion, Num control: " + compensacion.getNumControl() + " Fecha resolucion: " + compensacion.getDyctResolCompDTO().getFechaResolucion());
            fila.setFechaResolucion(compensacion.getDyctResolCompDTO().getFechaResolucion());
        }
    }

    private DycpCompensacionDTO getCompensacionServicio(DycpServicioDTO servicio) {

        DycpCompensacionDTO compensacion = null;
        
        try {
            compensacion = daoCompensacion.encontrar(servicio.getNumControl());
            if (null == compensacion.getDyctResolCompDTO()) {
                List<DycpCompensacionDTO> list = daoCompensacion.obtenerCompensacionResol(servicio.getNumControl());
                if (list.size()>0) {
                    compensacion.setDyctResolCompDTO(list.get(0).getDyctResolCompDTO());
                }
            }
        } catch (SIATException e) {

            LOG.debug(e.getMessage());
        }

        return compensacion;
    }

    private void complementaFilaDatosSolicitudDevolucion(FilaGridTramitesBean fila, DycpServicioDTO servicio) {
        DycpSolicitudDTO solicitud = daoSolicitud.encontrar(servicio.getNumControl());

        if (solicitud != null) {

            vaciarDatosSolicitudFila(fila, solicitud);
        }
    }

    private void vaciarDatosSolicitudFila(FilaGridTramitesBean fila, DycpSolicitudDTO solicitud) {
        fila.setEstado((solicitud.getDyccEstadoSolDTO().getIdEstadoSolicitud().equals(ConstantesDyCNumerico.VALOR_6) ? DESC_ARTICULO22 : solicitud.getDyccEstadoSolDTO().getDescripcion()));
        fila.setFechaPresentacion(solicitud.getFechaPresentacion());
        fila.setImporte(solicitud.getImporteSolicitado().doubleValue());
        if (solicitud.getDyctSaldoIcepDTO() != null) {
            if (solicitud.getDyctSaldoIcepDTO().getDyccPeriodoDTO() != null) {
                fila.setPeriodo(solicitud.getDyctSaldoIcepDTO().getDyccPeriodoDTO().getDescripcion());
            }
            if (solicitud.getDyctSaldoIcepDTO().getDyccEjercicioDTO() != null) {
                LOG.info("Valor periodo: " + solicitud.getDyctSaldoIcepDTO().getDyccEjercicioDTO().getIdEjercicio().toString());
                fila.setEjercicio(solicitud.getDyctSaldoIcepDTO().getDyccEjercicioDTO().getIdEjercicio().toString());
            }
            if (solicitud.getDyctSaldoIcepDTO().getDyccConceptoDTO() != null) {
                fila.setConceptoImpuestos(solicitud.getDyctSaldoIcepDTO().getDyccConceptoDTO().getDescripcion());
                if (solicitud.getDyctSaldoIcepDTO().getDyccConceptoDTO().getDyccImpuestoDTO() != null) {
                    fila.setImpuestos(solicitud.getDyctSaldoIcepDTO().getDyccConceptoDTO().getDyccImpuestoDTO().getDescripcion());
                }
            }
        }
        if (solicitud.getDyctResolucionDTO() != null) {
            fila.setFechaResolucion(solicitud.getDyctResolucionDTO().getFechaAprobacion());
            fila.setImporteAutorizado(solicitud.getDyctResolucionDTO().getImpAutorizado().doubleValue());
            if (solicitud.getDyccEstadoSolDTO() != null && solicitud.getDyccEstadoSolDTO().getIdEstadoSolicitud().equals(ConstantesEstados.ESTADO_SOL_PAGADA)) {
                fila.setImportePagado(solicitud.getDyctResolucionDTO().getImporteNeto().doubleValue());
                fila.setFechaPago(solicitud.getDyctResolucionDTO().getFechaPago());
            }
        }
    }

    public Map<String, Object> buscarXFiltros(Map<String, Object> paramsBusqueda, AccesoUsr accesoUsr) {

        LOG.debug("INICIO buscarXFiltros");
        Map<String, Object> resultBusqueda = new HashMap<String, Object>();

        List<Object> params = new ArrayList<Object>();
        StringBuilder clausulas = new StringBuilder();
        
        if(paramsBusqueda.get(IDTIPOSERVICIO) == null){
            paramsBusqueda.put(IDTIPOSERVICIO, Constantes.TiposServicio.SOLICITUD_DEVOLUCION.getIdTipoServicio());
        }
        generaClausulasYParametrosDeFiltros(clausulas, params, paramsBusqueda, accesoUsr);
        String clausulasFiltros = clausulas.toString();
        
        Integer idTipoServicio = (Integer) paramsBusqueda.get(IDTIPOSERVICIO);
        Integer totalRegs = getTotalRegistrosConsulta(clausulasFiltros, params.toArray(), idTipoServicio);
        resultBusqueda.put("totalRegs", totalRegs);

        String campoOrdenamiento = (String) paramsBusqueda.get("campoOrdenamiento");
        String tipoOrdenamiento = (String) paramsBusqueda.get("tipoOrdenamiento");

        LOG.info("campoOrdenamiento ->" + campoOrdenamiento);
        /**se agrega condicion para ir a ordenar importe solicitado en 2 tablas dycp_solicitud y dycp_compensacion
         **/
        String campoOrdenamientoAcotado;
        if(campoOrdenamiento.compareToIgnoreCase(CAMPO_ORDENAMIENTO_IMPORTE_CONSULTA)==0){
            if(idTipoServicio.equals(Constantes.TiposServicio.CASO_COMPENSACION.getIdTipoServicio())
                    || idTipoServicio.equals(Constantes.TiposServicio.AVISO_COMPENSACION.getIdTipoServicio())){
                campoOrdenamientoAcotado="COMP.IMPORTECOMPENSADO";    
            }else{
                campoOrdenamientoAcotado="SOL.IMPORTESOLICITADO";
            }            
        }else {
            campoOrdenamientoAcotado = "SERV." + campoOrdenamiento;
        }
        generaClausulasYParametrosDePaginacion(params, paramsBusqueda);

        clausulasFiltros = clausulas.toString();
        List<FilaGridTramitesBean> tramites = daoServicio.selecDinamicoConsulta(clausulasFiltros, params.toArray(), campoOrdenamientoAcotado, tipoOrdenamiento, idTipoServicio);
        resultBusqueda.put(KEY_TRAMS_ECONTRADOS, tramites);

        return resultBusqueda;
    }

    public Map<String, Object> buscarXFiltrosExportacion(Map<String, Object> paramsBusqueda, AccesoUsr accesoUsr) {

        LOG.debug("INICIO buscarXFiltros-Exportacion");
        Map<String, Object> resultBusqueda = new HashMap<String, Object>();

        List<Object> params = new ArrayList<Object>();
        StringBuilder clausulas = new StringBuilder();

        if(paramsBusqueda.get(IDTIPOSERVICIO) == null){
            paramsBusqueda.put(IDTIPOSERVICIO, Constantes.TiposServicio.SOLICITUD_DEVOLUCION.getIdTipoServicio());
        }
        generaClausulasYParametrosDeFiltros(clausulas, params, paramsBusqueda, accesoUsr);
        String clausulasFiltros = clausulas.toString();

        Integer idTipoServicio = (Integer) paramsBusqueda.get(IDTIPOSERVICIO);
        Integer totalRegs = getTotalRegistrosConsulta(clausulasFiltros, params.toArray(), idTipoServicio);
        List<FilaGridTramitesBean> tramites = new ArrayList<FilaGridTramitesBean>();
        if (totalRegs <= MAX_REGISTROS_EXPORTACION) {

            String campoOrdenamiento = (String) paramsBusqueda.get("campoOrdenamiento");
            String tipoOrdenamiento = (String) paramsBusqueda.get("tipoOrdenamiento");

            LOG.debug("campoOrdenamiento ->" + campoOrdenamiento);

            String campoOrdenamientoAcotado = "SERV." + campoOrdenamiento;
            
            tramites = daoServicio.selecDinamicoExportacion(clausulasFiltros, params.toArray(), campoOrdenamientoAcotado, tipoOrdenamiento, idTipoServicio);
        }
        resultBusqueda.put("totalRegs", tramites.size());
        resultBusqueda.put(KEY_TRAMS_ECONTRADOS, tramites);
        return resultBusqueda;
    }

    private Integer getTotalRegistrosConsulta(String clausulas, Object[] parametros, Integer idTipoServicio) {
        Integer totalRegs = daoServicio.selecCountDinamicoConsulta(clausulas, parametros, idTipoServicio);
        LOG.debug("totalRegs ->" + totalRegs);

        return totalRegs;
    }

    public List<FilaGridTramitesBean> getListaTramitesInfoCompleta(List<DycpServicioDTO> servicios) {
        List<FilaGridTramitesBean> tramites = new ArrayList<FilaGridTramitesBean>();

        if(servicios != null) {
            for (DycpServicioDTO servicio : servicios) {
                tramites.add(crearFilaGrid(servicio));
            }
        }

        return tramites;
    }

    private void generaClausulasYParametrosDeFiltros(StringBuilder clausulas, List<Object> params, Map<String, Object> paramsBusqueda, AccesoUsr accesoUsr) {

        String idEstados = Utils.sqlCreaParamIn((String[]) paramsBusqueda.get("idEstadoSelec"));
        String idsUnidadesAdmvas = (String) paramsBusqueda.get("idsUnidadesAdmvas");

        Integer idTipoServicio = (Integer) paramsBusqueda.get("idTipoServicio");
        Integer idTipoTramite = (Integer) paramsBusqueda.get("idTipoTramite");
        Integer numEmpleadoDict = (Integer) paramsBusqueda.get("numEmpleadoDict");
        Integer idImpuesto = (Integer) paramsBusqueda.get("idImpuesto");

        Date fechaInicioPresTram = (Date) paramsBusqueda.get("fechaInicioPresTram");
        Date fechaFinPresTra = (Date) paramsBusqueda.get("fechaFinPresTra");
        Date fechaIniResolucion = (Date) paramsBusqueda.get("fechaIniResolucion");
        Date fechaFinResolucion = (Date) paramsBusqueda.get("fechaFinResolucion");
        Date fechaIniPago = (Date) paramsBusqueda.get("fechaIniPago");
        Date fechaFinPago = (Date) paramsBusqueda.get("fechaFinPago");

        LOG.debug("idsUnidadesAdmvas ->" + idsUnidadesAdmvas);
        LOG.debug("idTipoServicio ->" + idTipoServicio);
        LOG.debug("idTipoTramite ->" + idTipoTramite);
        LOG.debug("numEmpleadoDict ->" + numEmpleadoDict);
        LOG.debug("idEstados ->" + idEstados);

        clausulas.append(" WHERE 1 = 1 ");

        if (idsUnidadesAdmvas != null && !"".equals(idsUnidadesAdmvas)) {
            clausulas.append(" AND SERV.CLAVEADM IN (");
            clausulas.append(idsUnidadesAdmvas);
            clausulas.append(") ");
        }

        if (idTipoServicio != null && idTipoServicio != 0) {
            clausulas.append(" AND SERV.IDTIPOSERVICIO = ? ");
            params.add(idTipoServicio);
        } /**else {
            clausulas.append(" AND SERV.IDTIPOSERVICIO IN ( ");
            clausulas.append(Constantes.TiposServicio.SOLICITUD_DEVOLUCION.getIdTipoServicio());
            clausulas.append(", ");
            clausulas.append(Constantes.TiposServicio.DEVOLUCION_AUTOMATICA.getIdTipoServicio());
            clausulas.append(", ");
            clausulas.append(Constantes.TiposServicio.AVISO_COMPENSACION.getIdTipoServicio());
            clausulas.append(", ");
            clausulas.append(Constantes.TiposServicio.CASO_COMPENSACION.getIdTipoServicio());
            clausulas.append(" ) ");
        }*/

        if (idTipoTramite != null) {
            clausulas.append(" AND SERV.IDTIPOTRAMITE = ? ");
            params.add(idTipoTramite);
        }

        if (StringUtils.isNotBlank(idEstados)) {
            if (idTipoServicio == Constantes.TiposServicio.SOLICITUD_DEVOLUCION.getIdTipoServicio().intValue()
                    || idTipoServicio == Constantes.TiposServicio.DEVOLUCION_AUTOMATICA.getIdTipoServicio().intValue()) {
                clausulas.append(" AND SOL.IDESTADOSOLICITUD IN ( ");
                clausulas.append(idEstados);
                clausulas.append(") ");
            } else if (idTipoServicio == Constantes.TiposServicio.AVISO_COMPENSACION.getIdTipoServicio().intValue()
                    || idTipoServicio == Constantes.TiposServicio.CASO_COMPENSACION.getIdTipoServicio().intValue()) {
                clausulas.append(" AND COMP.IDESTADOCOMP IN ( ");
                clausulas.append(idEstados);
                clausulas.append(") ");
            }
        }

        if (idImpuesto != null) {
            clausulas.append(" AND IMP.IDIMPUESTO = ? ");
            params.add(idImpuesto);
        }

        if (numEmpleadoDict != null) {
            clausulas.append(" AND SERV.NUMEMPLEADODICT = ? ");
            params.add(numEmpleadoDict);
        }

        if (fechaInicioPresTram != null) {

            if(idTipoServicio == null){
                clausulas.append("AND (SOL.FECHAPRESENTACION >= ? OR SOL.FECHAPRESENTACION IS NULL)");
                params.add(fechaInicioPresTram);
                clausulas.append("AND (COMP.FECHAPRESENTACION >= ? OR COMP.FECHAPRESENTACION IS NULL)");
                params.add(fechaInicioPresTram);
            } else if (idTipoServicio == Constantes.TiposServicio.SOLICITUD_DEVOLUCION.getIdTipoServicio().intValue()
                    || idTipoServicio == Constantes.TiposServicio.DEVOLUCION_AUTOMATICA.getIdTipoServicio().intValue()) {
                clausulas.append("AND (SOL.FECHAPRESENTACION >= ? OR SOL.FECHAPRESENTACION IS NULL)");
                params.add(fechaInicioPresTram);
            } else if (idTipoServicio == Constantes.TiposServicio.AVISO_COMPENSACION.getIdTipoServicio().intValue()
                    || idTipoServicio == Constantes.TiposServicio.CASO_COMPENSACION.getIdTipoServicio().intValue()) {
                clausulas.append("AND (COMP.FECHAPRESENTACION >= ? OR COMP.FECHAPRESENTACION IS NULL)");
                params.add(fechaInicioPresTram);
            }
        }

        if (fechaFinPresTra != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(fechaFinPresTra);
            cal.set(Calendar.HOUR, ConstantesDyCNumerico.VALOR_23);
            cal.set(Calendar.MINUTE, ConstantesDyCNumerico.VALOR_59);
            cal.set(Calendar.SECOND, ConstantesDyCNumerico.VALOR_59);
            cal.set(Calendar.MILLISECOND, ConstantesDyCNumerico.VALOR_999);

            fechaFinPresTra = cal.getTime();

            /**if(idTipoServicio == null){
                clausulas.append(" AND (SOL.FECHAPRESENTACION <= ? OR SOL.FECHAPRESENTACION IS NULL) ");
                params.add(fechaFinPresTra);
                clausulas.append(" AND (COMP.FECHAPRESENTACION <= ? OR COMP.FECHAPRESENTACION IS NULL) ");
                params.add(fechaFinPresTra);
            } else*/ if (idTipoServicio == Constantes.TiposServicio.SOLICITUD_DEVOLUCION.getIdTipoServicio().intValue()
                    || idTipoServicio == Constantes.TiposServicio.DEVOLUCION_AUTOMATICA.getIdTipoServicio().intValue()) {
                clausulas.append(" AND (SOL.FECHAPRESENTACION <= ? OR SOL.FECHAPRESENTACION IS NULL) ");
                params.add(fechaFinPresTra);
            } else if (idTipoServicio == Constantes.TiposServicio.AVISO_COMPENSACION.getIdTipoServicio().intValue()
                    || idTipoServicio == Constantes.TiposServicio.CASO_COMPENSACION.getIdTipoServicio().intValue()) {
                clausulas.append(" AND (COMP.FECHAPRESENTACION <= ? OR COMP.FECHAPRESENTACION IS NULL) ");
                params.add(fechaFinPresTra);
            }

        }

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
        String formatted;

        if (fechaIniResolucion != null && fechaFinResolucion != null) {

            Calendar calini = Calendar.getInstance();
            Calendar calfin = Calendar.getInstance();
            calini.setTime(fechaIniResolucion);
            calfin.setTime(fechaFinResolucion);

            /**if(idTipoServicio == null){
                clausulas.append(" AND TRUNC( RES.FECHAAPROBACION ) BETWEEN  TO_DATE (?, 'yyyy/MM/dd') AND TO_DATE (?, 'yyyy/MM/dd')  ");
                formatted = format1.format(calini.getTime());
                params.add(formatted);
                formatted = format1.format(calfin.getTime());
                params.add(formatted);
                clausulas.append(" AND TRUNC( RESC.FECHARESOLUCION ) BETWEEN  TO_DATE (?, 'yyyy/MM/dd') AND TO_DATE (?, 'yyyy/MM/dd')  ");
                formatted = format1.format(calini.getTime());
                params.add(formatted);
                formatted = format1.format(calfin.getTime());
                params.add(formatted);
            } else*/ if (idTipoServicio == Constantes.TiposServicio.SOLICITUD_DEVOLUCION.getIdTipoServicio().intValue()
                    || idTipoServicio == Constantes.TiposServicio.DEVOLUCION_AUTOMATICA.getIdTipoServicio().intValue()) {
                clausulas.append(" AND TRUNC( RES.FECHAAPROBACION ) BETWEEN  TO_DATE (?, 'yyyy/MM/dd') AND TO_DATE (?, 'yyyy/MM/dd')  ");
                formatted = format1.format(calini.getTime());
                params.add(formatted);
                formatted = format1.format(calfin.getTime());
                params.add(formatted);
            } else if (idTipoServicio == Constantes.TiposServicio.AVISO_COMPENSACION.getIdTipoServicio().intValue()
                    || idTipoServicio == Constantes.TiposServicio.CASO_COMPENSACION.getIdTipoServicio().intValue()) {
                clausulas.append(" AND (COMP.FECHAPRESENTACION <= ? OR COMP.FECHAPRESENTACION IS NULL) ");
                params.add(fechaFinPresTra);
            }

        }

        if (fechaIniPago != null && fechaFinPago != null) {

            Calendar calini = Calendar.getInstance();
            Calendar calfin = Calendar.getInstance();
            calini.setTime(fechaIniPago);
            calfin.setTime(fechaFinPago);

            clausulas.append("AND TRUNC( PAG.FECHAABONO ) BETWEEN TO_DATE (?, 'yyyy/MM/dd') AND TO_DATE (?, 'yyyy/MM/dd') ");

            formatted = format1.format(calini.getTime());
            params.add(formatted);
            formatted = format1.format(calfin.getTime());
            params.add(formatted);
        }

        agregarFiltrosPermisos(clausulas, params, accesoUsr);
    }

    private void generaClausulasYParametrosDePaginacion(List<Object> params, Map<String, Object> paramsBusqueda) {

        Integer tamanioPagina = (Integer) paramsBusqueda.get("tamanioPagina");
        Integer numeroPagina = (Integer) paramsBusqueda.get("numeroPagina");

        int primeraFila = (tamanioPagina * (numeroPagina - 1)) + 1;
        int ultimafila = tamanioPagina * numeroPagina;

        params.add(primeraFila);
        params.add(ultimafila);

        LOG.debug("primeraFila ->" + primeraFila + "; ultimafila ->" + ultimafila);
    }

    private void agregarFiltrosPermisos(StringBuilder clausulas, List<Object> params, AccesoUsr accesoUsr) {
        Object empleadoDyc = serviceUnidadAdmva.obtenerEmpleadoDyc(Integer.parseInt(accesoUsr.getNumeroEmp()));

        if (!accesoUsr.getRoles().contains(ConstantesPerfiles.PERFIL_CONS_GENERAL)) {
            if (empleadoDyc instanceof DyccAprobadorDTO) {
                if (Integer.parseInt(accesoUsr.getClaveSir()) != ClavesAdministraciones.CENTRAL_DYC) {
                    clausulas.append(" AND SERV.CLAVEADM = ? ");
                    params.add(accesoUsr.getClaveSir());
                }
            } else {
                clausulas.append(" AND SERV.NUMEMPLEADODICT = ? ");
                params.add(Integer.parseInt(accesoUsr.getNumeroEmp()));
            }
        } else {
            LOG.error("checar centro de costo empleado antes:" + accesoUsr.getNumeroEmp() + " centrocosto:" + accesoUsr.getCentroCosto() + " clavesir:" + accesoUsr.getClaveSir());
            if (Integer.parseInt(accesoUsr.getCentroCosto()) == 0) {
                accesoUsr.setCentroCosto(satAgsEmpleadosMVDAO.getCentroCostoEmpleado(accesoUsr.getNumeroEmp()));
            }

            if (accesoUsr.getCentroCosto() == null && Integer.parseInt(accesoUsr.getCentroCosto()) == 0) {
                accesoUsr.setCentroCosto(accesoUsr.getCentroCostoOp());
            }

            String idsUnidadesAdmvas = getClavesAdm(Integer.parseInt(accesoUsr.getCentroCosto()));

            if (null != idsUnidadesAdmvas) {
                clausulas.append(" AND SERV.CLAVEADM IN (").append(idsUnidadesAdmvas).append(") ");
            }

        }

        LOG.error("checar centro de costo empleado:" + accesoUsr.getNumeroEmp() + " centrocosto:" + accesoUsr.getCentroCosto() + " clavesir:" + accesoUsr.getClaveSir());
    }

    public String getClavesAdm(Integer centroCostos) {
        StringBuilder rango = new StringBuilder("SELECT CLAVE_SIR  FROM DYCC_UNIDADADMVA WHERE CLAVE_AGRS  ");
        String igual = " = ";
        String between = " BETWEEN ";
        String and = " AND ";

        if (centroCostos == (ConstantesDyC.CENTRO_COSTOS_ACDC)) {
            return null;
        } else if (centroCostos >= ConstantesDyC.CENTRO_COSTOS_INI_AGSC && centroCostos <= ConstantesDyC.CENTRO_COSTOS_FIN_AGSC) {
            return null;
        } else if (centroCostos >= ConstantesDyC.CENTRO_COSTOS_INI_ADAF && centroCostos <= ConstantesDyC.CENTRO_COSTOS_FIN_ADAF) {
            rango.append(between).append(ConstantesDyC.CENTRO_COSTOS_INI_ADAF);
            rango.append(and).append(ConstantesDyC.CENTRO_COSTOS_FIN_ADAF);
        } else if (centroCostos == (ConstantesDyC.CENTRO_COSTOS_AGACE_INT)) {
            rango.append(igual).append(ConstantesDyC.CENTRO_COSTOS_AGACE_INT);
        } else if (centroCostos == (ConstantesDyC.CENTRO_COSTOS_AGGC)) {
            rango.append(igual).append(ConstantesDyC.CENTRO_COSTOS_AGGC);
        } else if (centroCostos == (ConstantesDyC.CENTRO_COSTOS_AGH)) {
            rango.append(igual).append(ConstantesDyC.CENTRO_COSTOS_AGH);
        } else {
            return null;
        }
        return rango.toString();
    }

    private List<ItemComboBean> obtenerDictaminadores(Integer claveAdm) {
        List<ItemComboBean> beansDicts = new ArrayList<ItemComboBean>();
        List<DyccDictaminadorDTO> dicts;

        if (claveAdm != null) {
            dicts = daoDictaminador.selecOrderXClaveadm(claveAdm, " ORDER BY NOMBRE ASC ");
        } else {
            dicts = daoDictaminador.seleccionarOrder(" ORDER BY NOMBRE ASC ");
        }

        for (DyccDictaminadorDTO dictaminador : dicts) {
            ItemComboBean beanDictaminador = new ItemComboBean();
            beanDictaminador.setId(dictaminador.getNumEmpleado());
            beanDictaminador.setDescripcion(dictaminador.getNombre() + " " + dictaminador.getApellidoPaterno() + " " + dictaminador.getApellidoMaterno());
            beansDicts.add(beanDictaminador);
        }
        LOG.debug("numero de beansDicts ->" + beansDicts.size());
        return beansDicts;
    }

    public List<ItemComboBean> obtenerDictaminadores(String clavesUnidsAdmvas) {
        List<ItemComboBean> beansDicts = new ArrayList<ItemComboBean>();

        if (clavesUnidsAdmvas != null && !"".equals(clavesUnidsAdmvas)) {
            String[] arrClavesAdm = clavesUnidsAdmvas.split(",");
            for (int i = 0; i < arrClavesAdm.length; i++) {
                beansDicts.addAll(obtenerDictaminadores(Integer.parseInt(arrClavesAdm[i].trim())));
            }
        } else {
            beansDicts.addAll(obtenerDictaminadores((Integer) null));
        }

        LOG.debug("numero de beansDicts ->" + beansDicts.size());
        return beansDicts;
    }

    public List<ItemComboBean> obtenerTiposTramite(Integer idTipoServicio, Integer idImpuesto) {
        List<ItemComboBean> beansTiposTramites = new ArrayList<ItemComboBean>();
        List<DyccTipoTramiteDTO> tiposTramite = null;
        LOG.debug("idTipoServicio ->" + idTipoServicio);
        LOG.debug("idImpuesto ->" + idImpuesto);
        if (idTipoServicio != null && idTipoServicio != 0 && idImpuesto != null && idImpuesto != 0) {
            DyccTipoServicioDTO tipoServicio = BuscadorConstantes.obtenerTipoServicio(idTipoServicio);
            DyccImpuestoDTO impuesto = new DyccImpuestoDTO();
            impuesto.setIdImpuesto(idImpuesto);
            tiposTramite = daoTipoTramite.selecOrderXTiposervicioImpuesto(tipoServicio, impuesto, " ORDER BY TT.IDTIPOTRAMITE ASC ");

        } else if (idTipoServicio != null && idTipoServicio != 0) {
            DyccTipoServicioDTO tipoServicio = BuscadorConstantes.obtenerTipoServicio(idTipoServicio);
            tiposTramite = daoTipoTramite.selecOrderXTiposervicio(tipoServicio, " ORDER BY TT.IDTIPOTRAMITE ASC ");
        } else if (idImpuesto != null && idImpuesto != 0) {
            DyccImpuestoDTO impuesto = new DyccImpuestoDTO();
            impuesto.setIdImpuesto(idImpuesto);
            tiposTramite = daoTipoTramite.selecOrderXImpuesto(impuesto, " ORDER BY IDTIPOTRAMITE ASC ");
        } else {
            return beansTiposTramites;
        }

        for (DyccTipoTramiteDTO tipoTramite : tiposTramite) {
            ItemComboBean beanTipoTram = new ItemComboBean();
            beanTipoTram.setId(tipoTramite.getIdTipoTramite());
            beanTipoTram.setDescripcion(tipoTramite.getDescripcion());
            beansTiposTramites.add(beanTipoTram);
        }
        LOG.debug("numero de tiposTramite ->" + beansTiposTramites.size());
        return beansTiposTramites;
    }

    public DycpSolicitudDTO obtenerObjetoDevolucion(String numControl) {
        DycpSolicitudDTO solicitud = daoSolicitud.encontrar(numControl);
        solicitud.setDyctSaldoIcepDTO(daoSaldoIcep.encontrar(solicitud.getDyctSaldoIcepDTO().getIdSaldoIcep()));
        return solicitud;
    }

    public SatAgsEmpleadosMVDAO getSatAgsEmpleadosMVDAO() {
        return satAgsEmpleadosMVDAO;
    }

    public void setSatAgsEmpleadosMVDAO(SatAgsEmpleadosMVDAO satAgsEmpleadosMVDAO) {
        this.satAgsEmpleadosMVDAO = satAgsEmpleadosMVDAO;
    }

}
