package mx.gob.sat.mat.dyc.controlsaldos.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.compensacion.IDycpCompensacionDAO;
import mx.gob.sat.siat.dyc.dao.declaracion.DyctDeclaracionDAO;
import mx.gob.sat.siat.dyc.dao.icep.DyctDeclaraIcepDAO;
import mx.gob.sat.siat.dyc.dao.regsolicitud.DycpSolicitudDAO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaracionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaraIcepDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service(value = "serviceObtenerFechaHistorica")
public class ObtenerFechaHistoricaServiceImpl
{
    private static final Logger LOG = Logger.getLogger(ObtenerFechaHistoricaServiceImpl.class);

    @Autowired
    private DyctDeclaraIcepDAO daoDeclaraIcep;

    @Autowired
    private DycpSolicitudDAO daoSolicitud;

    @Autowired
    private IDycpCompensacionDAO daoCompensacion;

    @Autowired
    private DyctDeclaracionDAO daoDeclaracion;

    public Date execute(DyctSaldoIcepDTO saldoIcep) throws SIATException
    {
        List<DyctDeclaraIcepDTO> declaraIceps;
        if(saldoIcep.getDyctDeclaraIcepList() != null){
            declaraIceps = saldoIcep.getDyctDeclaraIcepList();
        }
        else{
            declaraIceps= daoDeclaraIcep.selecXSaldoicepOrder(saldoIcep, " ORDER BY TRUNC(FECHAPRESENTACION) ASC, IDTIPODECLARACION ASC, FECHAREGISTRO ASC ");
        }

        if(!declaraIceps.isEmpty())
        {
            List<DyctDeclaraIcepDTO> declsValidadas = new ArrayList<DyctDeclaraIcepDTO>();
            for(DyctDeclaraIcepDTO declAux : declaraIceps)
            {
                if(declAux.getFechaFin() == null && declAux.getValidacionDWH() != null){
                    declsValidadas.add(declAux);
                }
            }

            if(!declsValidadas.isEmpty()){
                return declsValidadas.get(declsValidadas.size() - 1).getFechaPresentacion();
            }
            else{
                return declaraIceps.get(declaraIceps.size() - 1).getFechaPresentacion();
            }
        }
        else{
            List<DycpSolicitudDTO> solicitudes = daoSolicitud.selecXSaldoicep(saldoIcep);
            List<DycpCompensacionDTO> compensaciones = daoCompensacion.selecXSaldoiceporigen(saldoIcep);

            List<DyctDeclaracionDTO> infoSaldoAFavor =  new ArrayList<DyctDeclaracionDTO>();

            for(DycpSolicitudDTO solicitud : solicitudes){
                infoSaldoAFavor.addAll(daoDeclaracion.selecXServicio(solicitud.getDycpServicioDTO()));
            }

            for(DycpCompensacionDTO compensacion : compensaciones){
                infoSaldoAFavor.addAll(daoDeclaracion.selecXServicio(compensacion.getDycpServicioDTO()));
            }

            for(DyctDeclaracionDTO infoSalFav : infoSaldoAFavor)
            {
                if(infoSalFav.getFechaCausacion() != null)
                {
                    return infoSalFav.getFechaCausacion();
                }
            }
        }

        LOG.error("El sistema no fue capaz de determinar la fecha origen del saldo/cantidad a favor del icep ->" + saldoIcep.getIdSaldoIcep() + "<- se regresará el día anterior para evitar errores al llenar la tabla");
        return new Date(System.currentTimeMillis()-ConstantesDyC.UNDIA);
    }
    
    public Date obtenerPrimerDeclaracionValida(DyctSaldoIcepDTO saldoIcep) throws SIATException
    {
        List<DyctDeclaraIcepDTO> declaraIceps;
        if(saldoIcep.getDyctDeclaraIcepList() != null){
            declaraIceps = saldoIcep.getDyctDeclaraIcepList();
        }
        else{
            declaraIceps= daoDeclaraIcep.selecXSaldoicepOrder(saldoIcep, " ORDER BY TRUNC(FECHAPRESENTACION) ASC, IDTIPODECLARACION ASC, FECHAREGISTRO ASC ");
        }

        if(!declaraIceps.isEmpty())
        {
            for(DyctDeclaraIcepDTO declAux : declaraIceps)
            {
                if(declAux.getFechaFin() == null && declAux.getValidacionDWH() != null){
                    return declAux.getFechaPresentacion();
                }
            }
        }
        else{
            List<DycpSolicitudDTO> solicitudes = daoSolicitud.selecXSaldoicep(saldoIcep);
            List<DycpCompensacionDTO> compensaciones = daoCompensacion.selecXSaldoiceporigen(saldoIcep);

            List<DyctDeclaracionDTO> infoSaldoAFavor =  new ArrayList<DyctDeclaracionDTO>();

            for(DycpSolicitudDTO solicitud : solicitudes){
                infoSaldoAFavor.addAll(daoDeclaracion.selecXServicio(solicitud.getDycpServicioDTO()));
            }

            for(DycpCompensacionDTO compensacion : compensaciones){
                infoSaldoAFavor.addAll(daoDeclaracion.selecXServicio(compensacion.getDycpServicioDTO()));
            }

            for(DyctDeclaracionDTO infoSalFav : infoSaldoAFavor)
            {
                if(infoSalFav.getFechaCausacion() != null)
                {
                    return infoSalFav.getFechaCausacion();
                }
            }
        }

        LOG.error("El sistema no fue capaz de determinar la fecha origen del saldo/cantidad a favor del icep ->" + saldoIcep.getIdSaldoIcep() + "<- se regresará el día anterior para evitar errores al llenar la tabla");
        return new Date(System.currentTimeMillis()-ConstantesDyC.UNDIA);
    }
}
