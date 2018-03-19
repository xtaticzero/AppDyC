package mx.gob.sat.siat.dyc.avisocomp.bo.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.avisocomp.bo.AvisoComBO;
import mx.gob.sat.siat.dyc.avisocomp.exception.FolioComplementarioException;
import mx.gob.sat.siat.dyc.dao.compensacion.DycpAvisoCompDAO;
import mx.gob.sat.siat.dyc.dao.compensacion.IDycpCompensacionDAO;
import mx.gob.sat.siat.dyc.dao.tiposerv.DyccTipoDeclaracionDAO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpAvisoCompDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoDeclaraDTO;
import mx.gob.sat.siat.dyc.trabajo.util.constante.ConstantesDyCWeb;
import mx.gob.sat.siat.dyc.util.common.SIATException;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service(value = "boAvisoComp")
public class AvisoComBOImpl implements AvisoComBO
{
    private static final Logger LOG = Logger.getLogger(AvisoComBOImpl.class);
    
    @Autowired
    private DycpAvisoCompDAO daoAvisoComp;

    @Autowired
    private IDycpCompensacionDAO daoCompensacion;

    @Autowired
    private DyccTipoDeclaracionDAO daoTipoDeclara;

    @Override
    public DycpAvisoCompDTO buscarAvisoNormal(String folioAvisoNormal) throws FolioComplementarioException
    {
        try{
            DycpAvisoCompDTO avisoNormal = daoAvisoComp.encontrar(folioAvisoNormal);
            if(avisoNormal != null){
                avisoNormal.setDycpCompensacionDTOList(daoCompensacion.selecXAvisocomp(avisoNormal));
            }
            return avisoNormal;
        }
        catch(SIATException se){
            throw new FolioComplementarioException("Ocurrió un error al buscar el folio del aviso normal en la base de datos", se);
        }
    }

    @Override
    public DycpAvisoCompDTO validarFolioAvisoNormal(String folioAvisoNormal, String rfcSession) throws FolioComplementarioException
    {
        LOG.debug("folioAvisoNormal ->" + folioAvisoNormal);
        LOG.debug("rfcSession ->" + rfcSession);

        if(folioAvisoNormal.matches(ConstantesDyCWeb.ExpresionesRegulares.FOLIOAVISO_MAT))
        {
            LOG.debug("es aviso MAT ->" + folioAvisoNormal);
            DycpAvisoCompDTO avisoNormal = buscarAvisoNormal(folioAvisoNormal); 
           
            if(avisoNormal != null)
            {
                //validar que el aviso si corresponda al contribuyente loggeado
                String rfcPerComp = avisoNormal.getDycpCompensacionDTOList().get(0).getDyctSaldoIcepOrigenDTO().getRfc();
                LOG.debug("rfcPerComp ->" + rfcPerComp);
                
                if(!rfcPerComp.equals(rfcSession)){
                    throw new FolioComplementarioException("El aviso de compensación introducido pertenece a otro contribuyente");
                }
                
                // El aviso ya fue desistido por algún otro aviso?
                if(!daoAvisoComp.selecXAvisonormal(avisoNormal).isEmpty()){
                    throw new FolioComplementarioException("El aviso normal introducido NO es el último (ya está desistido)");
                }
                else{
                    return avisoNormal;
                }
            }
            else{
                throw new FolioComplementarioException("El número de folio no existe");
            }
        }
        else if(folioAvisoNormal.matches(ConstantesDyCWeb.ExpresionesRegulares.FOLIOAVISO_SAC) || 
                folioAvisoNormal.matches(ConstantesDyCWeb.ExpresionesRegulares.FOLIOAVISO_DYP) || 
                folioAvisoNormal.matches(ConstantesDyCWeb.ExpresionesRegulares.FOLIOAVISO_PAPEL))
        {
            // indicar que el aviso normal se ingreso en un sistema externo a DyC MAT
            return null;
        }
        else{
            throw new FolioComplementarioException("El número de folio no es válido");
        }
    }

    @Override
    public List<DyccTipoDeclaraDTO> obtenerTiposDeclaracion() {
        return daoTipoDeclara.obtenerTiposDeDeclaraciones();
    }

    @Override
    public DyccTipoDeclaraDTO encontrarTipoDeclaracion(Integer idTipoDeclaracion) {
        return daoTipoDeclara.encontrar(idTipoDeclaracion);
    }
}
