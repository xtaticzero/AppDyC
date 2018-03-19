/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.admcat.service.institucioncredito.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.print.attribute.standard.Severity;

import mx.gob.sat.siat.dyc.admcat.dao.institucioncredito.ListaInstCreditoDAO;
import mx.gob.sat.siat.dyc.admcat.dto.institucioncredito.ListaInstitucionCreditoDTO;
import mx.gob.sat.siat.dyc.admcat.service.institucioncredito.ListaInstCreditoService;
import mx.gob.sat.siat.dyc.catalogo.dao.DyccInstCreditoDAO;
import mx.gob.sat.siat.dyc.domain.banco.DyccInstCreditoDTO;
import mx.gob.sat.siat.dyc.service.DyccMensajeUsrService;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC2;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Implementacion Service caso de uso institucion credito DYCC_INSTCREDITO
 * @author Luis Fernando Barrios Quiroz [ LuFerMX ]
 * @date Agosto 20, 2013
 * @since 0.1
 *
 * */
@Service(value = "listaInstCreditoService")
public class ListaInstCreditoServiceImpl implements ListaInstCreditoService {

    private static final Logger LOG = Logger.getLogger(ListaInstCreditoServiceImpl.class);

    @Autowired(required = true)
    private ListaInstCreditoDAO listaInstCreditoDAO;
    @Autowired(required = true)
    private DyccInstCreditoDAO dyccInstCreditoDAO;
    @Autowired(required =true)
    private DyccMensajeUsrService dyccMensajeUsrService;

    private ListaInstitucionCreditoDTO institucionCredito;
    private DyccInstCreditoDTO instCredito;
    private StringBuilder strMensaje;
    private Severity tipo;

    public ListaInstCreditoServiceImpl() {
        super();
        strMensaje = new StringBuilder();
    }

    /**
     * @return List DyccInstCreditoDTO
     */
    @Transactional(readOnly = false)
    public List<ListaInstitucionCreditoDTO> listaInstitucionesCredito() {
        List<ListaInstitucionCreditoDTO> listaInstCredito = new ArrayList<ListaInstitucionCreditoDTO>();

        try {
            listaInstCredito = listaInstCreditoDAO.listaInstCredito();
        } catch (Exception e) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage());
        }

        return listaInstCredito;
    }

    /**
     * @param instCredito
     */
    @Transactional(readOnly = false)
    public void insertarInstitucionesCredito(ListaInstitucionCreditoDTO instCredito) {
        this.strMensaje.setLength(0);
        DyccInstCreditoDTO dyccInstCredito = this.parseObj(instCredito);
        institucionCredito = new ListaInstitucionCreditoDTO();

        dyccInstCredito.setOrdenSec(instCredito.getIdInstCredito());
        dyccInstCredito.setFechaInicio(new Date());
        try {
            // EXISTE INSTITUCIÒN CREDITO
            institucionCredito = this.listaInstCreditoDAO.existeInstCredito(instCredito.getIdInstCredito());
            if (institucionCredito.getIdInstCredito() != 0) {
                this.strMensaje.append(this.dyccMensajeUsrService.obtieneMensaje(ConstantesDyC2.MSG_IC_EXT,
                                                                                 ConstantesDyC2.CU_INSTITUCION_CREDITO).getDescripcion());
                this.setTipo(tipo.WARNING);
            }
        } catch (Exception e) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage());
            // NO EXISTE INSTITUCION CREDITO
            try {
                this.dyccInstCreditoDAO.insertaInstCredito(dyccInstCredito);
                this.strMensaje.append(this.dyccMensajeUsrService.obtieneMensaje(ConstantesDyC2.MSG_IC_ADD,
                                                                                 ConstantesDyC2.CU_INSTITUCION_CREDITO).getDescripcion());
                this.setTipo(tipo.REPORT);
            } catch (Exception ex) {
                this.strMensaje.append(ex.getMessage());
                this.setTipo(tipo.ERROR);
                LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage());
            }
        }
    }

    /**
     * @param instCredito
     */
    @Transactional(readOnly = false)
    public void actualizaInstitucionesCredito(ListaInstitucionCreditoDTO instCredito) {
        this.strMensaje.setLength(0);
        DyccInstCreditoDTO dyccInstCredito = this.parseObj(instCredito);

        // CAMBIA ESTADO DE LA INSTITUCION
        if (instCredito.getEstado().equals("Inactivo")) {
            dyccInstCredito.setFechaFin(new Date());
        }
        try {
            this.dyccInstCreditoDAO.actualizaInstCredito(dyccInstCredito);
            this.strMensaje.append(this.dyccMensajeUsrService.obtieneMensaje(ConstantesDyC2.MSG_IC_MOD,
                                                                             ConstantesDyC2.CU_INSTITUCION_CREDITO).getDescripcion());
        } catch (Exception e) {
            this.strMensaje.append("Se presento un error al actualizar la institución");
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage());
        }
    }

    @Transactional(readOnly = false)
    public void bajaInstitucionesCredito(ListaInstitucionCreditoDTO instCredito) {
        this.strMensaje.setLength(0);
        DyccInstCreditoDTO dyccInstCredito = this.parseObj(instCredito);
        try {
            dyccInstCredito.setFechaFin(new Date());

            this.dyccInstCreditoDAO.actualizaInstCredito(dyccInstCredito);
            this.strMensaje.append(this.dyccMensajeUsrService.obtieneMensaje(ConstantesDyC2.MSG_IC_DEL,
                                                                             ConstantesDyC2.CU_INSTITUCION_CREDITO).getDescripcion());
        } catch (Exception e) {
            this.strMensaje.append(e.getMessage());
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage());
            // rollback
        }
    }

    @Transactional(readOnly = false)
    public String obtieneMensaje() {
        return this.strMensaje.toString();
    }

    /**
     * @param instCredito
     * @return
     */
    public DyccInstCreditoDTO parseObj(ListaInstitucionCreditoDTO instCredito) {
        DyccInstCreditoDTO dyccInstCredito = new DyccInstCreditoDTO();
        try {
            dyccInstCredito.setIdInstCredito(instCredito.getIdInstCredito());
            dyccInstCredito.setDescripcion(instCredito.getDescripcion());
            dyccInstCredito.setOrdenSec(instCredito.getOrdenSec());
            dyccInstCredito.setFechaInicio(instCredito.getFechaInicio());
        } catch (Exception e) {
            this.strMensaje.append(e.getMessage());
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage());
        }
        return dyccInstCredito;
    }

    @Transactional(readOnly = false)
    public String obtenerMensaje(String msgt) {
        StringBuilder mensaje = new StringBuilder();
        Integer msg =
            msgt.equals("REQUERIDO") ? ConstantesDyC2.MSG_IC_ERI : (msgt.equals("CONFIRMA") ? ConstantesDyC2.MSG_IC_CNF :
                                                                   0);
        try {
            mensaje.append(this.dyccMensajeUsrService.obtieneMensaje(msg,
                                                                     ConstantesDyC2.CU_INSTITUCION_CREDITO).getDescripcion());
        } catch (Exception e) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage());
        }
        return mensaje.toString();
    }

    @Transactional(readOnly = false)
    public Severity tipoError() {
        return this.tipo;
    }

    // TODO: ACCESSORS *******************************************************************

    public void setListaInstCreditoDAO(ListaInstCreditoDAO listaInstCreditoDAO) {
        this.listaInstCreditoDAO = listaInstCreditoDAO;
    }

    public ListaInstCreditoDAO getListaInstCreditoDAO() {
        return listaInstCreditoDAO;
    }

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

    public void setInstitucionCredito(ListaInstitucionCreditoDTO institucionCredito) {
        this.institucionCredito = institucionCredito;
    }

    public ListaInstitucionCreditoDTO getInstitucionCredito() {
        return institucionCredito;
    }

    public void setInstCredito(DyccInstCreditoDTO instCredito) {
        this.instCredito = instCredito;
    }

    public DyccInstCreditoDTO getInstCredito() {
        return instCredito;
    }

    public void setStrMensaje(StringBuilder strMensaje) {
        this.strMensaje = strMensaje;
    }

    public StringBuilder getStrMensaje() {
        return strMensaje;
    }

    public void setTipo(Severity tipo) {
        this.tipo = tipo;
    }

    public Severity getTipo() {
        return tipo;
    }
}
