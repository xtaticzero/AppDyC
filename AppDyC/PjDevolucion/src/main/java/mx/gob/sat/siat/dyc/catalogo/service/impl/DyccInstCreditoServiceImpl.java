/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.catalogo.service.impl;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.catalogo.dao.DyccInstCreditoDAO;
import mx.gob.sat.siat.dyc.catalogo.service.DyccInstCreditoService;
import mx.gob.sat.siat.dyc.domain.banco.DyccInstCreditoDTO;
import mx.gob.sat.siat.dyc.domain.banco.DyctCuentaBancoDTO;
import mx.gob.sat.siat.dyc.service.DyccMensajeUsrService;
import mx.gob.sat.siat.dyc.util.common.ParamOutputTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.mensajes.ConstantesMensajesInstitucionCredito;
import mx.gob.sat.siat.dyc.util.constante.mensajes.ConstantesMensajesMatrizDictaminadores;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Implementacion Service catalogo DYCC_INSTCREDITO
 * @author Luis Fernando Barrios Quiroz [ LuFerMX ]
 * @date Agosto 20, 2013
 * @since 0.1
 *
 * */
@Service(value = "dyccInstCreditoService")
public class DyccInstCreditoServiceImpl implements DyccInstCreditoService {
    private static final Logger LOG = Logger.getLogger(DyccInstCreditoServiceImpl.class);

    @Autowired(required = true)
    private DyccInstCreditoDAO dyccInstCreditoDAO;
    @Autowired(required = true)
    private DyccMensajeUsrService dyccMensajeUsrService;

    private List<DyccInstCreditoDTO> listaInstCredito;
    private DyccInstCreditoDTO objInstCredito;
    private StringBuilder strMensaje;

    public DyccInstCreditoServiceImpl() {
        super();
        listaInstCredito = new ArrayList<DyccInstCreditoDTO>();
        strMensaje = new StringBuilder();
    }

    /**
     * @return List DyccInstCreditoDTO
     */
    @Transactional(readOnly = false)
    public List<DyccInstCreditoDTO> listaInstitucionesCredito() {
        try {
            this.listaInstCredito = dyccInstCreditoDAO.listaInstCredito();
            this.strMensaje.append(this.dyccMensajeUsrService.obtieneMensaje(ConstantesMensajesMatrizDictaminadores.MSG_MD_REGIST_DT,
                                                                             ConstantesMensajesInstitucionCredito.CU_INSTITUCION_CREDITO).getDescripcion());
        } catch (SQLException ex) {
            this.strMensaje.append(ex.getMessage());
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + ex.getMessage());
        } catch (DataAccessException dae) {
            this.strMensaje.append(dae.getMessage());
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage());
        } catch (SIATException dae) {
            this.strMensaje.append(dae.getMessage());
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage());
        }
        return listaInstCredito;
    }

    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void insertarInstitucionesCredito(DyccInstCreditoDTO instCredito) {

        try {
            dyccInstCreditoDAO.insertaInstCredito(instCredito);
            this.strMensaje.append(this.dyccMensajeUsrService.obtieneMensaje(ConstantesMensajesMatrizDictaminadores.MSG_MD_REGIST_DT,
                                                                             ConstantesMensajesInstitucionCredito.CU_INSTITUCION_CREDITO).getDescripcion());
        } catch (Exception e) {
            this.strMensaje.append(e.getMessage());
            LOG.error(e.getMessage());
        }

    }

    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void actualizaInstitucionesCredito(DyccInstCreditoDTO instCredito) {

        try {
            dyccInstCreditoDAO.actualizaInstCredito(instCredito);
            this.strMensaje.append(this.dyccMensajeUsrService.obtieneMensaje(ConstantesMensajesMatrizDictaminadores.MSG_MD_REGIST_DT,
                                                                             ConstantesMensajesInstitucionCredito.CU_INSTITUCION_CREDITO).getDescripcion());
        } catch (Exception e) {
            this.strMensaje.append(e.getMessage());
            LOG.error(e.getMessage());
        }

    }

    @Transactional(readOnly = false)
    public String obtieneMensaje() {
        return this.strMensaje.toString();
    }

    @Override
    public ParamOutputTO<DyccInstCreditoDTO> getInstitucion(int paramInput) {
        this.objInstCredito = dyccInstCreditoDAO.getInstitucion(paramInput);
        return new ParamOutputTO<DyccInstCreditoDTO>(this.objInstCredito);
    }

    @Override
    public ParamOutputTO<DyctCuentaBancoDTO> getCunetaBancosPorRFC(String paramInput) throws SIATException {
        return new ParamOutputTO<DyctCuentaBancoDTO>(dyccInstCreditoDAO.getCunetaBancosPorRFC(paramInput));
    }

    /**
     *Obtiene todas las instituciones disponibles en el catalogo
     *  --LADP[Luis ALberto Dominguez Palomino]
     * @return
     */
    @Override
    public List<DyccInstCreditoDTO> listaInstituciones() {
        try {
            listaInstCredito = dyccInstCreditoDAO.listaInstCredito();
        } catch (SQLException e) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage());
        }
        return listaInstCredito;
    }

    /**
     *Obtiene la institucion por el nombre de la descripcion
     *  --LADP[Luis ALberto Dominguez Palomino]
     * @param institucion
     * @return
     */
    @Override
    public DyccInstCreditoDTO institucionXDescripcion(String institucion) {
        DyccInstCreditoDTO dyccInstCreditoDTO = null;
        try {
            dyccInstCreditoDTO = dyccInstCreditoDAO.institucionXDescripcion(institucion);
        } catch (SIATException siatE) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + siatE.getMessage());
        }
        return dyccInstCreditoDTO;
    }
    // TODO: ACCESSORS *******************************************************************

    public void setDyccInstCreditoDAO(DyccInstCreditoDAO dyccInstCreditoDAO) {
        this.dyccInstCreditoDAO = dyccInstCreditoDAO;
    }

    public DyccInstCreditoDAO getDyccInstCreditoDAO() {
        return dyccInstCreditoDAO;
    }

    public void setDyccMensajeUsrService(DyccMensajeUsrService dyccMensajeUsrService) {
        this.dyccMensajeUsrService = dyccMensajeUsrService;
    }

    public DyccMensajeUsrService getDyccMensajeUsrService() {
        return dyccMensajeUsrService;
    }

    public void setStrMensaje(StringBuilder strMensaje) {
        this.strMensaje = strMensaje;
    }

    public StringBuilder getStrMensaje() {
        return strMensaje;
    }

    public void setListaInstCredito(List<DyccInstCreditoDTO> listaInstCredito) {
        this.listaInstCredito = listaInstCredito;
    }

    public List<DyccInstCreditoDTO> getListaInstCredito() {
        return listaInstCredito;
    }

    public void setObjInstCredito(DyccInstCreditoDTO objInstCredito) {
        this.objInstCredito = objInstCredito;
    }

    public DyccInstCreditoDTO getObjInstCredito() {
        return objInstCredito;
    }

}
