/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.dyc.controlsaldos.service.impl;

import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.siat.dyc.casocomp.util.Utils;
import mx.gob.sat.siat.dyc.controlsaldos.service.DevueltoHelper;
import mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.utility.FilaGridDevolucionesBean;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctMovDevolucionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctResolucionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gregorio.Serapio
 */
@Component
public class DevueltoHelperIml implements DevueltoHelper {

    private static final Logger LOG = Logger.getLogger(DevueltoHelperIml.class);

    @Override
    public List<FilaGridDevolucionesBean> obtenerDevoluciones(DyctSaldoIcepDTO saldoIcep) {
        List<FilaGridDevolucionesBean> filas = new ArrayList<FilaGridDevolucionesBean>();
        List<DyctMovDevolucionDTO> movsDevolucion = saldoIcep.getDyctMovDevolucionList();
        for (DyctMovDevolucionDTO movDevolucion : movsDevolucion) {
            Double importeNegado = 0d;
            if (movDevolucion.getIdTipoResol() == Constantes.TiposResolucion.AUTORIZADA_PARCIAL_REM_NEGADO) {
                importeNegado = movDevolucion.getImporteSolDev().doubleValue() - movDevolucion.getImporteAutorizado().doubleValue();
            } else if (movDevolucion.getIdTipoResol() == Constantes.TiposResolucion.NEGADA) {
                importeNegado = movDevolucion.getImporteSolDev().doubleValue();
            }

            Double importeNetoDevuelto = movDevolucion.getImporteAutorizado().doubleValue() + movDevolucion.getActualizacion().doubleValue()
                    + movDevolucion.getIntereses().doubleValue() - movDevolucion.getRetIntereses().doubleValue() - movDevolucion.getImpCompensado().doubleValue();

            FilaGridDevolucionesBean fila = new FilaGridDevolucionesBean();
            fila.setIdMovDevolucion(movDevolucion.getIdMovDevolucion());
            fila.setTipoDevolucion("HIST");
            fila.setNumControl(movDevolucion.getNumControl());
            fila.setTipoResolucion(movDevolucion.getIdTipoResol().getDescripcion());
            fila.setFechaResolucion(movDevolucion.getFechaResolucion());
            fila.setImporteSolDev(movDevolucion.getImporteSolDev().doubleValue());
            fila.setImporteAutorizado(movDevolucion.getImporteAutorizado().doubleValue());
            fila.setImporteNegado(importeNegado);
            fila.setActualizacion(movDevolucion.getActualizacion().doubleValue());
            fila.setIntereses(movDevolucion.getIntereses().doubleValue());
            fila.setRetIntereses(movDevolucion.getRetIntereses().doubleValue());
            fila.setImpCompensado(movDevolucion.getImpCompensado().doubleValue());
            fila.setImporteNetoDev(importeNetoDevuelto);
            fila.setIdEstatusSolicitud(0);
            filas.add(fila);
        }

        List<DycpSolicitudDTO> solicitudes = saldoIcep.getDycpSolicitudList();
        for (DycpSolicitudDTO solicitud : solicitudes) {
            LOG.debug("solicitud ->" + solicitud + "<- ");

            DyctResolucionDTO resol = solicitud.getDyctResolucionDTO();

            FilaGridDevolucionesBean fila = new FilaGridDevolucionesBean();
            fila.setTipoDevolucion("DYC");
            fila.setNumControl(solicitud.getDycpServicioDTO().getNumControl());
            fila.setImporteSolDev(solicitud.getImporteSolicitado().doubleValue());
            fila.setImporteSolDevStr(Utils.formatearMoneda(solicitud.getImporteSolicitado().doubleValue()));

            if (resol != null && resol.getDyccEstreSolDTO() != null && resol.getDyccEstreSolDTO().getIdEstResol().equals(Constantes.EstadosResolucion.APROBADA.getIdEstResol())) {
                fila.setTipoResolucion(resol.getDyccTipoResolDTO().getDescripcion());

                fila.setFechaResolucion(resol.getFechaAprobacion());
                fila.setImporteAutorizado(resol.getImpAutorizado().doubleValue());
                fila.setImporteNegado(resol.getImpNegado().doubleValue());
                fila.setActualizacion(resol.getImpActualizacion().doubleValue());
                fila.setIntereses(resol.getIntereses().doubleValue());
                fila.setRetIntereses(resol.getRetIntereses().doubleValue());
                fila.setImpCompensado(resol.getImpCompensado().doubleValue());
                fila.setImporteNetoDev(resol.getImporteNeto().doubleValue());

                fila.setFechaResolucionStr(Utils.formatearFecha(resol.getFechaAprobacion()));
                fila.setImporteAutorizadoStr(Utils.formatearMoneda(resol.getImpAutorizado().doubleValue()));
                fila.setImporteNegadoStr(Utils.formatearMoneda(resol.getImpNegado().doubleValue()));
                fila.setActualizacionStr(Utils.formatearMoneda(resol.getImpActualizacion().doubleValue()));
                fila.setInteresesStr(Utils.formatearMoneda(resol.getIntereses().doubleValue()));
                fila.setRetInteresesStr(Utils.formatearMoneda(resol.getRetIntereses().doubleValue()));
                fila.setImpCompensadoStr(Utils.formatearMoneda(resol.getImpCompensado().doubleValue()));
                fila.setImporteNetoDevStr(Utils.formatearMoneda(resol.getImporteNeto().doubleValue()));
            } else {
                fila.setFechaResolucionStr("");
                fila.setImporteAutorizadoStr("");
                fila.setImporteNegadoStr("");
                fila.setActualizacionStr("");
                fila.setInteresesStr("");
                fila.setRetInteresesStr("");
                fila.setImpCompensadoStr("");
                fila.setImporteNetoDevStr("");
            }

            if (solicitud.getDyccEstadoSolDTO() != null) {
                fila.setIdEstatusSolicitud(solicitud.getDyccEstadoSolDTO().getIdEstadoSolicitud());
            }

            filas.add(fila);
        }
        return filas;
    }

}
