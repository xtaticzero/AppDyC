package mx.gob.sat.siat.dyc.casocomp.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.cobranza.diahabil.service.DiaHabilService;
import mx.gob.sat.siat.dyc.casocomp.service.BandejaCompensacionesService;
import mx.gob.sat.siat.dyc.casocomp.util.Utils;
import mx.gob.sat.siat.dyc.casocomp.web.controller.bean.utility.ContribuyenteBean;
import mx.gob.sat.siat.dyc.casocomp.web.controller.bean.utility.FilaGridCasosCompBean;
import mx.gob.sat.siat.dyc.dao.compensacion.IDycpCompensacionDAO;
import mx.gob.sat.siat.dyc.dao.usuario.DyctContribuyenteDAO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctContribuyenteDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoPlazoDTO;
import mx.gob.sat.siat.dyc.trabajo.util.constante.EnumRol;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import mx.gob.sat.siat.dyc.util.constante.ConstantesACC;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service("serviceBandejaCompensaciones")
public class BandejaCompensacionesServiceImpl implements BandejaCompensacionesService {
    private static final Logger LOG = Logger.getLogger(BandejaCompensacionesServiceImpl.class);

    @Autowired(required = true)
    @Qualifier(value = "diaHabilService")
    private DiaHabilService diaHabilService;

    @Autowired
    private DyctContribuyenteDAO daoContribuyente;

    @Autowired
    private IDycpCompensacionDAO daoCompensacion;

    @Override
    public List<FilaGridCasosCompBean> obtenerCompensaciones(Map<String, Object> params) {
        LOG.debug("INICIO obtenerCasosCompensacion");
        EnumRol rol = (EnumRol)params.get("rol");

        List<FilaGridCasosCompBean> filas = new ArrayList<FilaGridCasosCompBean>();

        List<DycpCompensacionDTO> compensaciones = new ArrayList<DycpCompensacionDTO>();
        
        Integer numEmpleado = (Integer)params.get("numEmpleado");

        if ((rol == EnumRol.DICTAMINADOR) && !(params.get("url").equals("R"))) {
            DyccDictaminadorDTO dictaminador = new DyccDictaminadorDTO();
            dictaminador.setNumEmpleado((Integer)params.get("numEmpleado"));
            LOG.debug("numEmpleado ->" + dictaminador.getNumEmpleado() + "<-");
            String orderBy =
                " ORDER BY C.NUMCONTROL, S.RFCCONTRIBUYENTE, C.FECHAPRESENTADEC, C.IMPORTECOMPENSADO, C.IDESTADOCOMP ";
            compensaciones.addAll(daoCompensacion.selecXDictaminadorEstadocompOrder(dictaminador,
                                                                                    Constantes.EstadosCompensacion.EN_PROCESO,
                                                                                    orderBy));
            compensaciones.addAll(daoCompensacion.selecXDictaminadorEstadocompOrder(dictaminador,
                                                                                    Constantes.EstadosCompensacion.REQUERIDO,
                                                                                    orderBy));
        }

        if (rol == EnumRol.ADMINISTRADOR) {
            Integer claveAdm = (Integer)params.get("claveAdm");
            LOG.debug("claveAdm ->" + claveAdm + "<-");
            String orderBy = " ORDER BY C.FECHAPRESENTADEC ASC ";
            if (params.get("url").equals("B")) {
                compensaciones.addAll(daoCompensacion.selecXEstadocompClaveadmOrder(Constantes.EstadosCompensacion.EN_PROCESO,
                                                                                    claveAdm, orderBy));
                compensaciones.addAll(daoCompensacion.selecXEstadocompClaveadmOrder(Constantes.EstadosCompensacion.REQUERIDO,
                                                                                    claveAdm, orderBy));
                compensaciones.addAll(daoCompensacion.selecXEstadocompClaveadmOrder(Constantes.EstadosCompensacion.EN_REVISION,
                                                                                    claveAdm, orderBy));
            } else if (params.get("url").equals("R")) {
                compensaciones.addAll(daoCompensacion.selecXEstadocompClaveadmOrderRegistrada(Constantes.EstadosCompensacion.PENDIENTE_RESOLVER,
                                                                                    claveAdm, orderBy, numEmpleado));
                compensaciones.addAll(daoCompensacion.selecXEstadocompClaveadmOrderRegistrada(Constantes.EstadosCompensacion.EN_REVISION,
                                                                                    claveAdm, orderBy, numEmpleado));
            }
        }

        if (rol == EnumRol.USUARIO_FISCALIZACION) {
            Integer claveAdm = (Integer)params.get("claveAdm");
            String orderBy = " ORDER BY C.FECHAPRESENTADEC ASC ";
            compensaciones.addAll(daoCompensacion.selecXEstadocompClaveadmOrder(Constantes.EstadosCompensacion.EN_REVISION,
                                                                                claveAdm, orderBy));
        }

        filas.addAll(crearListaFilas(compensaciones));

        return filas;
    }

    private List<FilaGridCasosCompBean> crearListaFilas(List<DycpCompensacionDTO> compensaciones) {
        List<FilaGridCasosCompBean> filas = new ArrayList<FilaGridCasosCompBean>();
        for (DycpCompensacionDTO compensacion : compensaciones) {
            String er =
                compensacion.getDyctResolCompDTO() != null ? compensacion.getDyctResolCompDTO().getDyccEstResolDTO().getDescripcion() :
                "Sin Resolución";

            DyccDictaminadorDTO dtoDict = compensacion.getDycpServicioDTO().getDyccDictaminadorDTO();
            String dictaminador =
                dtoDict != null ? dtoDict.getNombre() + " " + dtoDict.getApellidoPaterno() + " " + dtoDict.getApellidoMaterno() :
                "";

            FilaGridCasosCompBean fila = new FilaGridCasosCompBean();
            fila.setNumeroControl(compensacion.getDycpServicioDTO().getNumControl());
            fila.setRfc(compensacion.getDycpServicioDTO().getRfcContribuyente());
            fila.setTipoTramite(compensacion.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getDescripcion());
            fila.setFechaPresDecl(Utils.formatearFecha(compensacion.getFechaPresentaDec()));
            fila.setMontoCompensado(compensacion.getImporteCompensado());
            fila.setEstadoResolucion(er);
            fila.setVencido(vencido(compensacion));
            fila.setEstadoCasoComp(compensacion.getDyccEstadoCompDTO().getDescripcion());
            fila.setDictaminador(dictaminador);
            fila.setNumDictaminador(compensacion.getDycpServicioDTO().getDyccDictaminadorDTO().getNumEmpleado());
            fila.setNivel(compensacion.getDyccAprobadorDTO().getClaveNivel());
            filas.add(fila);
        }
        return filas;
    }

    private Boolean vencido(DycpCompensacionDTO comp) {
        LOG.debug("comp ->" + comp + "<-");
        try {
            Integer numeroDias =
                comp.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getPlazo();
            DyccTipoPlazoDTO tipoPlazo =
                comp.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getDyccTipoPlazoDTO();
            Date fechaVencimiento;
            Calendar calendar = new GregorianCalendar();

            if (tipoPlazo == Constantes.TiposPlazo.DIAS_HABILES) {
                fechaVencimiento =
                        diaHabilService.buscarDiaFederalRecaudacion(comp.getFechaInicioTramite(), numeroDias);
                calendar.setTime(fechaVencimiento);
            } else if (tipoPlazo == Constantes.TiposPlazo.DIAS_NATURALES) {
                fechaVencimiento = comp.getFechaInicioTramite();
                calendar.setTime(fechaVencimiento);
                calendar.add(Calendar.DAY_OF_MONTH, numeroDias);
            }

            calendar.set(Calendar.HOUR_OF_DAY, ConstantesACC.HORA_VENCIMIENTO);
            calendar.set(Calendar.MINUTE, ConstantesACC.MINUTO_VENCIMIENTO);
            calendar.set(Calendar.SECOND, ConstantesACC.SEGUNDO_VENCIMIENTO);

            fechaVencimiento = calendar.getTime();

            LOG.info("fechaVencimiento ->" + fechaVencimiento + "<-");
            return new Date().after(fechaVencimiento);
        } catch (Exception e) {
            LOG.error("Ocurrio un error al validar si la compensacion ->" + comp.getNumControl() +
                      "<- esta vencida. Mensaje de la excepción ->" + e.getMessage());
        }

        return Boolean.FALSE;
    }

    @Override
    public ContribuyenteBean obtenerContribuyente(String numControl) {
        ContribuyenteBean contribuyente = new ContribuyenteBean();
        DyctContribuyenteDTO dtoContte = daoContribuyente.encontrar(numControl);

        contribuyente.setEsPF(dtoContte.getRolPf() == ConstantesDyCNumerico.VALOR_1);
        contribuyente.setEsPM(dtoContte.getRolPm() == ConstantesDyCNumerico.VALOR_1);
        contribuyente.setEsGranContribuyente(dtoContte.getRolGranContrib() == ConstantesDyCNumerico.VALOR_1);
        contribuyente.setEsDictaminado(dtoContte.getRolDictaminado() == ConstantesDyCNumerico.VALOR_1);
        contribuyente.setEsSociedadControladora(dtoContte.getRolSociedadControl() == ConstantesDyCNumerico.VALOR_1);
        return contribuyente;
    }

}
