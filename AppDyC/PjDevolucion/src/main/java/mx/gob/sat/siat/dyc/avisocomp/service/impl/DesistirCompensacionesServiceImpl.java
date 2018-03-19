package mx.gob.sat.siat.dyc.avisocomp.service.impl;

import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.dyc.avisocomp.service.DesistirCompensacionesService;
import mx.gob.sat.siat.dyc.controlsaldos.service.icep.AfectarSaldosXCompensacionesService;
import mx.gob.sat.siat.dyc.dao.compensacion.IDycpCompensacionDAO;
import mx.gob.sat.siat.dyc.dao.resolucion.DyctResolCompDAO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpAvisoCompDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctResolCompDTO;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service(value = "desistirCompensacionesService")
public class DesistirCompensacionesServiceImpl implements DesistirCompensacionesService
{
    private static final Logger LOG = Logger.getLogger(DesistirCompensacionesServiceImpl.class);
    
    @Autowired
    private AfectarSaldosXCompensacionesService serviceAfectarSaldos;

    @Autowired
    private IDycpCompensacionDAO daoCompensacion;

    @Autowired
    private DyctResolCompDAO daoResolComp;

    @Override
    public void desistirAvisosXFolioAvisoNormal(DycpAvisoCompDTO avisoComp)
    {
        try {
                if (avisoComp.getDycpAvisoCompNormalDTO() != null)
                {
                    List<DycpCompensacionDTO> compsDesistir = daoCompensacion.buscaXFolioAviso(avisoComp.getDycpAvisoCompNormalDTO().getFolioAviso());
                    
                    for(DycpCompensacionDTO comp : compsDesistir)
                    {
                        serviceAfectarSaldos.afectarXDesistimiento(comp.getDycpServicioDTO().getNumControl());
                        comp.setDyccEstadoCompDTO(Constantes.EstadosCompensacion.DESISTIDO);
                        daoCompensacion.actualizarEstadocomp(comp);
                        DyctResolCompDTO resolComp = new DyctResolCompDTO();

                        if (comp.getDyccEstadoCompDTO() == Constantes.EstadosCompensacion.EN_PROCESO)
                        {
                            resolComp.setDycpCompensacionDTO(comp);
                            resolComp.setDyccTipoResolDTO(Constantes.TiposResolucion.DESISTIDA);
                            daoResolComp.actualizarTipoResol(resolComp);
                        } else {
                            resolComp.setDyccAccionSegDTO(Constantes.AccionesSeg.APROBACION);
                            resolComp.setDyccEstResolDTO(Constantes.EstadosResolucion.APROBADA);
                            resolComp.setDyccTipoResolDTO(Constantes.TiposResolucion.DESISTIDA);
                            resolComp.setDycpCompensacionDTO(comp);
                            resolComp.setFechaResolucion(new Date());
                            resolComp.setObservaciones("compensación desistida desde el método desistirAvisosXFolioAvisoNormal");
    
                            try {
                                daoResolComp.insertar(resolComp);
                            } catch (SIATException e) {
                                LOG.error("ocurrio un error al insertar la resolucion de la compensacion ->" + e.getMessage());
                                ManejadorLogException.getTraceError(e);
                            }
                        }
                }
            }
        } catch (SIATException e) {
            LOG.error("ocurrio un error en desistirAvisosXFolioAvisoNormal ->" +   e.getMessage());
            ManejadorLogException.getTraceError(e);
        }
    }
}
