package mx.gob.sat.siat.dyc.admcat.service.tipotramite.impl;

import java.util.Iterator;
import java.util.List;

import mx.gob.sat.siat.dyc.admcat.service.tipotramite.VerificarInsertarOActualizarService;
import mx.gob.sat.siat.dyc.catalogo.dao.DyccUnidadTramiteDAO;
import mx.gob.sat.siat.dyc.dao.anexo.DyccAnexoTramiteDAO;
import mx.gob.sat.siat.dyc.dao.documento.DyccInfoTramiteDAO;
import mx.gob.sat.siat.dyc.dao.periodo.DycaTipoPeriodoTtDAO;
import mx.gob.sat.siat.dyc.dao.rol.DyccTramiteRolDAO;
import mx.gob.sat.siat.dyc.dao.saldoicep.DyccSubSaldoTramDAO;
import mx.gob.sat.siat.dyc.dao.tipotramite.DyccTtSubTramiteDAO;
import mx.gob.sat.siat.dyc.domain.anexo.DyccAnexoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.periodo.DycaTipoPeriodoTtDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccTtSubtramiteDTO;
import mx.gob.sat.siat.dyc.domain.suborigensal.DyccSubSaldoTramDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccInfoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTramiteRolDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccUnidadTramiteDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author Jesus Alfredo Hernandez Orozco
 */
@Service(value = "verificarInsertarOActualizarService")
public class VerificarInsertarOActualizarServiceImpl implements VerificarInsertarOActualizarService {
    public VerificarInsertarOActualizarServiceImpl() {
        super();
    }
    
    @Autowired
    private DyccUnidadTramiteDAO dyccUnidadTramiteDAO;
    
    @Autowired
    private DyccTramiteRolDAO dyccTramiteRolDAO;
    
    @Autowired
    private DyccAnexoTramiteDAO dyccAnexoTramiteDAO;
    
    @Autowired
    private DyccInfoTramiteDAO dyccInfoTramiteDAO;
    
    @Autowired
    private DyccTtSubTramiteDAO dyccTtSubTramiteDAO;
    
    @Autowired
    private DyccSubSaldoTramDAO dyccSubSaldoTramDAO;
    
    @Autowired
    private DycaTipoPeriodoTtDAO dycaTipoPeriodoTtDAO;
    
    /**
     * Verifica si en DyccUnidadTramite hay un registro en base de datos, si lo esta, actualiza la
     * fecha fin igual a null, en caso contrario la inserta.
     *
     * @param unidadTramite
     * @throws SIATException
     */
    @Override
    public void verificarUnidadTramite(List<DyccUnidadTramiteDTO> unidadTramite) throws SIATException {
        Iterator it = unidadTramite.iterator();
        DyccUnidadTramiteDTO objeto = null;
        
        while (it.hasNext()) {
            objeto = (DyccUnidadTramiteDTO)it.next();
            if(dyccUnidadTramiteDAO.consultarXTipoTramiteUnidadAdmva(objeto.getDyccTipoTramiteDTO().getIdTipoTramite(), 
                                                                     objeto.getIdTipoUnidadAdmva())) {
                dyccUnidadTramiteDAO.actualizarFechaFin(objeto.getDyccTipoTramiteDTO().getIdTipoTramite(), 
                                                                     objeto.getIdTipoUnidadAdmva());
            } else {
                dyccUnidadTramiteDAO.insertarUnidadTramite(objeto);
            }
        }
        it = null;
        objeto = null;
    }

    /**
     * Verifica si en DyccTramiteRol hay un registro en base de datos, si lo esta, actualiza la
     * fecha fin igual a null, en caso contrario la inserta.
     *
     * @param tramiteRol
     * @throws SIATException
     */
    @Override
    public void verificarTramiteRol(List<DyccTramiteRolDTO> tramiteRol) throws SIATException {
        Iterator it = tramiteRol.iterator();
        DyccTramiteRolDTO objeto = null;
        while (it.hasNext()) {
            objeto = (DyccTramiteRolDTO)it.next();
            
            if(dyccTramiteRolDAO.consultarTramiteRolXTipoTramiteRol(objeto.getDyccTipoTramiteDTO().getIdTipoTramite(), 
                                                                    objeto.getDyccRolDTO().getIdRol())) {
                dyccTramiteRolDAO.actualizarFechaFin(objeto.getDyccTipoTramiteDTO().getIdTipoTramite(), 
                                                                     objeto.getDyccRolDTO().getIdRol());
            } else {
                dyccTramiteRolDAO.insertar(objeto);
            }
        }
        it = null;
        objeto = null;
    }

    /**
     * Verifica si en DyccAnexoTramite hay un registro en base de datos, si lo esta, actualiza la
     * fecha fin igual a null, en caso contrario la inserta.
     *
     * @param anexoTramite
     * @throws SIATException
     */
    @Override
    public void verificarAnexoTramite(List<DyccAnexoTramiteDTO> anexoTramite) throws SIATException {
        Iterator it = anexoTramite.iterator();
        DyccAnexoTramiteDTO objeto = null;
        
        while (it.hasNext()) {
            objeto = (DyccAnexoTramiteDTO)it.next();
            if(dyccAnexoTramiteDAO.consultarAnexoXTramiteyAnexo(objeto.getDyccTipoTramiteDTO().getIdTipoTramite(), 
                                                                objeto.getDyccMatrizAnexosDTO().getIdAnexo())) {
                dyccAnexoTramiteDAO.actualizarFechaFin(objeto.getDyccTipoTramiteDTO().getIdTipoTramite(), 
                                                                objeto.getDyccMatrizAnexosDTO().getIdAnexo());
            } else {
                dyccAnexoTramiteDAO.insertar(objeto);
            }
        }
        it = null;
        objeto = null;
    }

    /**
     * Verifica si en DyccInfoTramite hay un registro en base de datos, si lo esta, actualiza la
     * fecha fin igual a null, en caso contrario la inserta.
     *
     * @param infoTramite
     * @throws SIATException
     */
    @Override
    public void verificarInfoTramite(List<DyccInfoTramiteDTO> infoTramite) throws SIATException {
        Iterator it = infoTramite.iterator();
        DyccInfoTramiteDTO objeto = null;
        while (it.hasNext()) {
            objeto = (DyccInfoTramiteDTO)it.next();
            if(dyccInfoTramiteDAO.consultarTramiteRolXTipoTramiteInfo(objeto.getDyccTipoTramiteDTO().getIdTipoTramite(), 
                                                   objeto.getDyccInfoARequerirDTO().getIdInfoArequerir())) {
                dyccInfoTramiteDAO.actualizarFechaFin(objeto.getDyccTipoTramiteDTO().getIdTipoTramite(), 
                                                   objeto.getDyccInfoARequerirDTO().getIdInfoArequerir());
            } else {
                dyccInfoTramiteDAO.insertar(objeto);
            }
        }
        it = null;
        objeto = null;
    }

    /**
     * Verifica si en DyccTtSubtramite hay un registro en base de datos, si lo esta, actualiza la
     * fecha fin igual a null, en caso contrario la inserta.
     *
     * @param ttSubtramite
     * @throws SIATException
     */
    @Override
    public void verificarTtSubTramite(List<DyccTtSubtramiteDTO> ttSubtramite) throws SIATException {
        Iterator it = ttSubtramite.iterator();
        DyccTtSubtramiteDTO objeto = null;
        while (it.hasNext()) {
            objeto = (DyccTtSubtramiteDTO)it.next();
            if(dyccTtSubTramiteDAO.consultarXTipoTramiteSubTramite(objeto.getDyccTipoTramiteDTO().getIdTipoTramite(),
                                               objeto.getDyccSubTramiteDTO().getIdSubTipoTramite())) {
                dyccTtSubTramiteDAO.actualizarFechaFin(objeto.getDyccTipoTramiteDTO().getIdTipoTramite(),
                                               objeto.getDyccSubTramiteDTO().getIdSubTipoTramite());
            } else {
                dyccTtSubTramiteDAO.insertar(objeto);
            }
        }
        it = null;
        objeto = null;
    }

    /**
     * Verifica si en DyccSubSaldoTram hay un registro en base de datos, si lo esta, actualiza la
     * fecha fin igual a null, en caso contrario la inserta.
     *
     * @param subSaldoTram
     * @throws SIATException
     */
    @Override
    public void verificarSubSaldoTram(List<DyccSubSaldoTramDTO> subSaldoTram) throws SIATException {
        Iterator it = subSaldoTram.iterator();
        DyccSubSaldoTramDTO objeto = null;
        while (it.hasNext()) {
            objeto = (DyccSubSaldoTramDTO)it.next();
            if(dyccSubSaldoTramDAO.consultarXTipoTramiteSubSaldoTram(objeto.getDyccTipoTramiteDTO().getIdTipoTramite(), 
                                                                     objeto.getDyccSuborigSaldoDTO().getIdSuborigenSaldo())) {
                dyccSubSaldoTramDAO.actualizarFechaFin(objeto.getDyccTipoTramiteDTO().getIdTipoTramite(), 
                                                                     objeto.getDyccSuborigSaldoDTO().getIdSuborigenSaldo());
            } else {
                dyccSubSaldoTramDAO.insertar(objeto);
            }
        }
        it = null;
        objeto = null;
    }

    /**
     * Verifica si en DycaTipoPeriodoTt hay un registro en base de datos, si lo esta, actualiza la
     * fecha fin igual a null, en caso contrario la inserta.
     *
     * @param tipoPeriodoTt
     * @throws SIATException
     */
    @Override
    public void verificarTipoPeriodoTt(List<DycaTipoPeriodoTtDTO> tipoPeriodoTt) throws SIATException {
        Iterator it = tipoPeriodoTt.iterator();
        DycaTipoPeriodoTtDTO objeto = null;
        while (it.hasNext()) {
            objeto = (DycaTipoPeriodoTtDTO)it.next();
            if(dycaTipoPeriodoTtDAO.consultarXTipoTramiteTipoPeriodo(objeto.getDyccTipoTramiteDTO().getIdTipoTramite(), 
                                                                     objeto.getDyccTipoPeriodoDTO().getIdTipoPeriodo())) {
                dycaTipoPeriodoTtDAO.actualizarFechaFin(objeto.getDyccTipoTramiteDTO().getIdTipoTramite(), 
                                                                     objeto.getDyccTipoPeriodoDTO().getIdTipoPeriodo());
            } else {
                dycaTipoPeriodoTtDAO.insertar(objeto);
            } 
        }
        it = null;
        objeto = null;
    }

    /**
     * Verifica que aquellos datos viejos que se han dado de alta en base de datos y que no se incluyeron en las 
     * actualizaciones se coloquen con fecha fin igual a sysdate.
     *
     * @param datosViejos
     * @param datosNuevos
     * @throws SIATException
     */
    @Override
    public void verificarFechaFinUnidadTramite(List<DyccUnidadTramiteDTO> datosViejos,
                                               List<DyccUnidadTramiteDTO> datosNuevos) throws SIATException {
        
        boolean encontrado = Boolean.FALSE;
        for(DyccUnidadTramiteDTO datoViejo: datosViejos) {
            for(DyccUnidadTramiteDTO datoNuevo: datosNuevos) {
                if(datoViejo.getIdTipoUnidadAdmva().equals(datoNuevo.getIdTipoUnidadAdmva())) {
                    encontrado=Boolean.TRUE;
                    break;
                }
            }
            if(!encontrado) {
                dyccUnidadTramiteDAO.colocarFechaFin(datoViejo.getDyccTipoTramiteDTO().getIdTipoTramite(), 
                                                     datoViejo.getIdTipoUnidadAdmva());
            }
            encontrado = Boolean.FALSE;
        }
        
        if(datosNuevos.size()==0) {
            for(DyccUnidadTramiteDTO datoViejo: datosViejos) {
                dyccUnidadTramiteDAO.colocarFechaFin(datoViejo.getDyccTipoTramiteDTO().getIdTipoTramite(), 
                                                     datoViejo.getIdTipoUnidadAdmva());
            }
        }
    }

    /**
     * Verifica que aquellos datos viejos que se han dado de alta en base de datos y que no se incluyeron en las 
     * actualizaciones se coloquen con fecha fin igual a sysdate.
     *
     * @param datosViejos
     * @param datosNuevos
     * @throws SIATException
     */
    @Override
    public void verificarFechaFinTramiteRol(List<DyccTramiteRolDTO> datosViejos, 
                                            List<DyccTramiteRolDTO> datosNuevos) throws SIATException {
        boolean encontrado = Boolean.FALSE;
        for(DyccTramiteRolDTO datoViejo: datosViejos) {
            for(DyccTramiteRolDTO datoNuevo: datosNuevos) {
                if(datoViejo.getDyccRolDTO().equals(datoNuevo.getDyccRolDTO())) {
                    encontrado=Boolean.TRUE;
                    break;
                }
            }
            if(!encontrado) {
                dyccTramiteRolDAO.colocarFechaFin(datoViejo.getDyccTipoTramiteDTO().getIdTipoTramite(), 
                                                  datoViejo.getDyccRolDTO().getIdRol());
            }
            encontrado = Boolean.FALSE;
        }
        
        if(datosNuevos.size()==0) {
            for(DyccTramiteRolDTO datoViejo: datosViejos) {
                dyccTramiteRolDAO.colocarFechaFin(datoViejo.getDyccTipoTramiteDTO().getIdTipoTramite(), 
                                                  datoViejo.getDyccRolDTO().getIdRol());
            }
        }
    }

    /**
     * Verifica que aquellos datos viejos que se han dado de alta en base de datos y que no se incluyeron en las 
     * actualizaciones se coloquen con fecha fin igual a sysdate.
     *
     * @param datosViejos
     * @param datosNuevos
     * @throws SIATException
     */
    @Override
    public void verificarFechaFinAnexoTramite(List<DyccAnexoTramiteDTO> datosViejos,
                                              List<DyccAnexoTramiteDTO> datosNuevos) throws SIATException {
        boolean encontrado = Boolean.FALSE;
        for(DyccAnexoTramiteDTO datoViejo: datosViejos) {
            for(DyccAnexoTramiteDTO datoNuevo: datosNuevos) {
                if(datoViejo.getDyccMatrizAnexosDTO().getIdAnexo().equals(datoNuevo.getDyccMatrizAnexosDTO().getIdAnexo())) {
                    encontrado=Boolean.TRUE;
                    break;
                }
            }
            if(!encontrado) {
                dyccAnexoTramiteDAO.colocarFechaFin(datoViejo.getDyccTipoTramiteDTO().getIdTipoTramite(), 
                                                    datoViejo.getDyccMatrizAnexosDTO().getIdAnexo());
            }
            encontrado = Boolean.FALSE;
        }
        
        if(datosNuevos.size()==0) {
            for(DyccAnexoTramiteDTO datoViejo: datosViejos) {
                dyccAnexoTramiteDAO.colocarFechaFin(datoViejo.getDyccTipoTramiteDTO().getIdTipoTramite(), 
                                                    datoViejo.getDyccMatrizAnexosDTO().getIdAnexo());
            }
        }
    }

    /**
     * Verifica que aquellos datos viejos que se han dado de alta en base de datos y que no se incluyeron en las 
     * actualizaciones se coloquen con fecha fin igual a sysdate.
     *
     * @param datosViejos
     * @param datosNuevos
     * @throws SIATException
     */
    @Override
    public void verificarFechaFinInfoTramite(List<DyccInfoTramiteDTO> datosViejos,
                                             List<DyccInfoTramiteDTO> datosNuevos) throws SIATException {
        boolean encontrado = Boolean.FALSE;
        for(DyccInfoTramiteDTO datoViejo: datosViejos) {
            for(DyccInfoTramiteDTO datoNuevo: datosNuevos) {
                if(datoViejo.getDyccInfoARequerirDTO().getIdInfoArequerir().equals(datoNuevo.getDyccInfoARequerirDTO().getIdInfoArequerir())) {
                    encontrado=Boolean.TRUE;
                    break;
                }
            }
            if(!encontrado) {
                dyccInfoTramiteDAO.colocarFechaFin(datoViejo.getDyccTipoTramiteDTO().getIdTipoTramite(), 
                                                   datoViejo.getDyccInfoARequerirDTO().getIdInfoArequerir());
            }
            encontrado = Boolean.FALSE;
        }
        
        if(datosNuevos.size()==0) {
            for(DyccInfoTramiteDTO datoViejo: datosViejos) {
                dyccInfoTramiteDAO.colocarFechaFin(datoViejo.getDyccTipoTramiteDTO().getIdTipoTramite(), 
                                                   datoViejo.getDyccInfoARequerirDTO().getIdInfoArequerir());
            }
        }
    }

    /**
     * Verifica que aquellos datos viejos que se han dado de alta en base de datos y que no se incluyeron en las 
     * actualizaciones se coloquen con fecha fin igual a sysdate.
     *
     * @param datosViejos
     * @param datosNuevos
     * @throws SIATException
     */
    @Override
    public void verificarFechaFinTtSubTramite(List<DyccTtSubtramiteDTO> datosViejos,
                                              List<DyccTtSubtramiteDTO> datosNuevos) throws SIATException {        
        boolean encontrado = Boolean.FALSE;
        for(DyccTtSubtramiteDTO datoViejo: datosViejos) {
            for(DyccTtSubtramiteDTO datoNuevo: datosNuevos) {
                if(datoViejo.getDyccSubTramiteDTO().getIdSubTipoTramite().equals(datoNuevo.getDyccSubTramiteDTO().getIdSubTipoTramite())) {
                    encontrado=Boolean.TRUE;
                    break;
                }
            }
            if(!encontrado) {
                dyccTtSubTramiteDAO.colocarFechaFin(datoViejo.getDyccTipoTramiteDTO().getIdTipoTramite(), 
                                                    datoViejo.getDyccSubTramiteDTO().getIdSubTipoTramite());
            }
            encontrado = Boolean.FALSE;
        }
        
        if(datosNuevos.size()==0) {
            for(DyccTtSubtramiteDTO datoViejo: datosViejos) {
                dyccTtSubTramiteDAO.colocarFechaFin(datoViejo.getDyccTipoTramiteDTO().getIdTipoTramite(), 
                                                    datoViejo.getDyccSubTramiteDTO().getIdSubTipoTramite());
            }
        }
    }

    /**
     * Verifica que aquellos datos viejos que se han dado de alta en base de datos y que no se incluyeron en las 
     * actualizaciones se coloquen con fecha fin igual a sysdate.
     *
     * @param datosViejos
     * @param datosNuevos
     * @throws SIATException
     */
    @Override
    public void verificarFechaFinSubSaldoTram(List<DyccSubSaldoTramDTO> datosViejos,
                                              List<DyccSubSaldoTramDTO> datosNuevos) throws SIATException {
        boolean encontrado = Boolean.FALSE;
        for(DyccSubSaldoTramDTO datoViejo: datosViejos) {
            for(DyccSubSaldoTramDTO datoNuevo: datosNuevos) {
                if(datoViejo.getDyccSuborigSaldoDTO().getIdSuborigenSaldo().equals(datoNuevo.getDyccSuborigSaldoDTO().getIdSuborigenSaldo())) {
                    encontrado=Boolean.TRUE;
                    break;
                }
            }
            if(!encontrado) {
                dyccSubSaldoTramDAO.colocarFechaFin(datoViejo.getDyccTipoTramiteDTO().getIdTipoTramite(), 
                                                    datoViejo.getDyccSuborigSaldoDTO().getIdSuborigenSaldo());
            }
            encontrado = Boolean.FALSE;
        }
        
        if(datosNuevos.size()==0) {
            for(DyccSubSaldoTramDTO datoViejo: datosViejos) {
                dyccSubSaldoTramDAO.colocarFechaFin(datoViejo.getDyccTipoTramiteDTO().getIdTipoTramite(), 
                                                    datoViejo.getDyccSuborigSaldoDTO().getIdSuborigenSaldo());
            }
        }
    }

    /**
     * Verifica que aquellos datos viejos que se han dado de alta en base de datos y que no se incluyeron en las 
     * actualizaciones se coloquen con fecha fin igual a sysdate.
     *
     * @param datosViejos
     * @param datosNuevos
     * @throws SIATException
     */
    @Override
    public void verificarFechaFinTipoPeriodoTt(List<DycaTipoPeriodoTtDTO> datosViejos,
                                               List<DycaTipoPeriodoTtDTO> datosNuevos) throws SIATException {
        boolean encontrado = Boolean.FALSE;
        for(DycaTipoPeriodoTtDTO datoViejo: datosViejos) {
            for(DycaTipoPeriodoTtDTO datoNuevo: datosNuevos) {
                if(datoViejo.getDyccTipoPeriodoDTO().getIdTipoPeriodo().equals(datoNuevo.getDyccTipoPeriodoDTO().getIdTipoPeriodo())) {
                    encontrado=Boolean.TRUE;
                    break;
                }
            }
            if(!encontrado) {
                dycaTipoPeriodoTtDAO.colocarFechaFin(datoViejo.getDyccTipoTramiteDTO().getIdTipoTramite(), 
                                                     datoViejo.getDyccTipoPeriodoDTO().getIdTipoPeriodo());
            }
            encontrado = Boolean.FALSE;
        }
        
        if(datosNuevos.size()==0) {
            for(DycaTipoPeriodoTtDTO datoViejo: datosViejos) {
                dycaTipoPeriodoTtDAO.colocarFechaFin(datoViejo.getDyccTipoTramiteDTO().getIdTipoTramite(), 
                                                     datoViejo.getDyccTipoPeriodoDTO().getIdTipoPeriodo());
            }
        }
    }
}
