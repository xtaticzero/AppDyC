package mx.gob.sat.mat.dyc.controlsaldos.service.impl;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.gob.sat.mat.dyc.controlsaldos.service.RegistrarDeclaracionesService;
import mx.gob.sat.siat.dyc.controlsaldos.vo.DeclaracionDwhVO;
import mx.gob.sat.siat.dyc.dao.icep.DyctDeclaraIcepDAO;
import mx.gob.sat.siat.dyc.dao.movsaldo.DyctMovSaldoDAO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccMovIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaracionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaraIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctMovSaldoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoDeclaraDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("serviceRegistrarDeclaraciones")
public class RegistrarDeclaracionesServiceImpl implements RegistrarDeclaracionesService
{
    private static final Logger LOG = Logger.getLogger(RegistrarDeclaracionesServiceImpl.class);

    @Autowired
    private ObtenerDeclaracionesDHWServiceImpl serviceObtDeclsDWH;

    @Autowired
    private DyctDeclaraIcepDAO daoDeclaraIcep;

    @Autowired
    private DyctMovSaldoDAO daoMovSaldo;

    @Override
    public Boolean execute(DycpServicioDTO servicio) throws SIATException
    {
        return execute(servicio, false);
    }

    @Override
    public Boolean executeDevAutomaticasIva(DycpServicioDTO servicio) throws SIATException
    {
        return execute(servicio, Boolean.TRUE);
    }

    private Boolean execute(DycpServicioDTO servicio, boolean esDevolucionAutomatica) throws SIATException
    {
        Boolean encontroDeclsDWH;
        DyctSaldoIcepDTO saldoIcep = obtenerSaldoIcep(servicio);
        List<DyctDeclaraIcepDTO> declaraciones = new ArrayList<DyctDeclaraIcepDTO>();
        
        List<DeclaracionDwhVO> declaracionesDwh;
        if(esDevolucionAutomatica){
            declaracionesDwh = new ArrayList<DeclaracionDwhVO>();
        }
        else{
            declaracionesDwh = serviceObtDeclsDWH.execute(saldoIcep);
        }

        if(!declaracionesDwh.isEmpty())
        {
            encontroDeclsDWH = Boolean.TRUE;
            LOG.debug("Se encontro al menos una declaracion en DWH para el icep solicitado, se ignorarán las declaraciones que capturó el contribuyente");
            for(DeclaracionDwhVO voDeclaraDwh : declaracionesDwh)
            {
                declaraciones.add(crearDTODeclaraIcep(voDeclaraDwh, saldoIcep));
            }
        }
        else
        {
            encontroDeclsDWH = Boolean.FALSE;
            LOG.debug("NO se encontro ninguna declaracion en DWH para el icep solicitado, se insertarán las declaraciones que capturo el contribuyente");
            for (DyctDeclaracionDTO infoSaldoAFavor : servicio.getDyctDeclaracionList())
            {
                DyctDeclaraIcepDTO declaraIcep = new DyctDeclaraIcepDTO();
                declaraIcep.setDyctSaldoIcepDTO(saldoIcep);
                declaraIcep.setNumOperacion(Long.parseLong(infoSaldoAFavor.getNumOperacion()));
                declaraIcep.setFechaPresentacion(infoSaldoAFavor.getFechaPresentacion());
                declaraIcep.setDyccTipoDeclaraDTO(infoSaldoAFavor.getDyccTipoDeclaraDTO());
                declaraIcep.setSaldoAFavor(infoSaldoAFavor.getSaldoAfavor());
                declaraIcep.setOrigenDeclara(Constantes.OrigenesDeclaracion.CONTRIBUYENTE);
                declaraIcep.setFechaRegistro(new Date());
                declaraciones.add(declaraIcep);
            }
        }

        for(DyctDeclaraIcepDTO declaraIcep : declaraciones)
        {
            daoDeclaraIcep.insertar(declaraIcep);

            DyctMovSaldoDTO abonoAltaSaldo = new DyctMovSaldoDTO();
            abonoAltaSaldo.setDyctSaldoIcepDTO(saldoIcep);
            abonoAltaSaldo.setImporte(declaraIcep.getSaldoAFavor());
            abonoAltaSaldo.setFechaRegistro(new Date());
            abonoAltaSaldo.setFechaOrigen(declaraIcep.getFechaPresentacion());
            abonoAltaSaldo.setDyccMovIcepDTO(obtenerTipoAbono(servicio, declaraIcep));
            abonoAltaSaldo.setIdOrigen(declaraIcep.getNumOperacion().toString());
            daoMovSaldo.insertar(abonoAltaSaldo);
        }

        return encontroDeclsDWH;
    }

    private DyccMovIcepDTO obtenerTipoAbono(DycpServicioDTO servicio, DyctDeclaraIcepDTO declaraIcep)
    {
        if(declaraIcep.getValidacionDWH() != null) {
            return Constantes.MovsIcep.ABONO_SAFDWH;
        }
        else{
            if(servicio.getDycaOrigenTramiteDTO().getDycaServOrigenDTO().getDyccTipoServicioDTO().getIdTipoServicio().intValue() == 
                    ConstantesDyCNumerico.VALOR_4){
                return Constantes.MovsIcep.ABONO_SAFCONTRIB_AVICOMP;
            }
            else{
                return Constantes.MovsIcep.ABONO_SAFCONTRIB;
            }                        
        }
    }

    private DyctDeclaraIcepDTO crearDTODeclaraIcep(DeclaracionDwhVO declaracionDwh, DyctSaldoIcepDTO saldoIcep)
    {
        DyccTipoDeclaraDTO tipoDeclaracion;
        if(declaracionDwh.getTipoDeclaracion().intValue() == ConstantesDyCNumerico.VALOR_1 || declaracionDwh.getTipoDeclaracion().intValue() == ConstantesDyCNumerico.VALOR_41){
            tipoDeclaracion = Constantes.TiposDeclaracion.NORMAL;
        }
        else{
            tipoDeclaracion = Constantes.TiposDeclaracion.COMPLEMENTARIA;
        }

        DyctDeclaraIcepDTO declaraIcep = new DyctDeclaraIcepDTO();
        declaraIcep.setNumOperacion(declaracionDwh.getNumOperacion());
        declaraIcep.setFechaPresentacion(declaracionDwh.getFechaPresentacion());
        declaraIcep.setDyccTipoDeclaraDTO(tipoDeclaracion);
        declaraIcep.setDyctSaldoIcepDTO(saldoIcep);
        declaraIcep.setSaldoAFavor(new BigDecimal(declaracionDwh.getSaldo()));
        declaraIcep.setValidacionDWH(new Date());
        declaraIcep.setOrigenDeclara(Constantes.OrigenesDeclaracion.DATAWAREHOUSE);
        declaraIcep.setFechaRegistro(new Date());
        return declaraIcep;
    }

    private DyctSaldoIcepDTO obtenerSaldoIcep(DycpServicioDTO servicio)
    {
        switch (servicio.getDycaOrigenTramiteDTO().getDycaServOrigenDTO().getDyccTipoServicioDTO().getIdTipoServicio().intValue())
        {
            case ConstantesDyCNumerico.VALOR_1:
            case ConstantesDyCNumerico.VALOR_2:
                if(servicio.getDycpSolicitudDTO() != null){
                    return servicio.getDycpSolicitudDTO().getDyctSaldoIcepDTO();
                }
                else{
                    return ((DycpSolicitudDTO)servicio).getDyctSaldoIcepDTO();
                }
            case ConstantesDyCNumerico.VALOR_3:
                return servicio.getDycpCompensacionDTO().getDyctSaldoIcepOrigenDTO();

            case ConstantesDyCNumerico.VALOR_4:
                return servicio.getDycpCompensacionDTO().getDyctSaldoIcepOrigenDTO();
            
        }
        return null;
    }
}
