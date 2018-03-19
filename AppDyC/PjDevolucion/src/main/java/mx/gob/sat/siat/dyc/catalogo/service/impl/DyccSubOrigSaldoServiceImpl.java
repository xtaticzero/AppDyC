/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.catalogo.service.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.catalogo.service.DyccSubOrigSaldoService;
import mx.gob.sat.siat.dyc.dao.saldoicep.DyccSubOrigSaldoDAO;
import mx.gob.sat.siat.dyc.domain.mensajes.DyccMensajeUsrDTO;
import mx.gob.sat.siat.dyc.domain.suborigensal.DyccActividadDTO;
import mx.gob.sat.siat.dyc.domain.suborigensal.DyccSubOrigSaldoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.util.common.CatalogoTO;
import mx.gob.sat.siat.dyc.util.common.ParamOutputTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Paola Rivera
 *
 * @Modificado ISCC .- Se añade metodo de busqueda de suborigenes de saldos - 15/08/2013.
 */
@Service(value = "dyccSubOrigSaldoService")
public class DyccSubOrigSaldoServiceImpl implements DyccSubOrigSaldoService {

    private static final Logger LOG = Logger.getLogger(DyccSubOrigSaldoServiceImpl.class.getName());

    @Autowired
    private DyccSubOrigSaldoDAO dyccSubOrigSaldoDAO;

    /**@Autowired
    private DycbPistasAuditoriaDAO dycbPistasAuditoriaDAO;*/

    private DyccMensajeUsrDTO mensajeDTO;

    public DyccSubOrigSaldoServiceImpl() {
        super();
        mensajeDTO = new DyccMensajeUsrDTO();
    }

    @Override
    public List<DyccSubOrigSaldoDTO> obtieneSubOrigSaldo(int idTipoTramite) {
        return this.dyccSubOrigSaldoDAO.obtieneSubOrigSaldo(idTipoTramite);
    }

    /**
     * TODO
     * @return
     */
    @Override
    public List<DyccSubOrigSaldoDTO> obtenerSubOrigenesDeSaldos() {

        List<DyccSubOrigSaldoDTO> subOrigenesDeSaldosList = this.dyccSubOrigSaldoDAO.consultarSuborigenesDeSaldos();

        return subOrigenesDeSaldosList;
    }

    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public int insertarSuborigeDelSaldo(DyccSubOrigSaldoDTO dyccSubOrigSaldoDTO,
                                        List<DyccTipoTramiteDTO> selectedTramites) {

        int resultado = 0;
        mensajeDTO.setIdMensaje(ConstantesDyC.SUBORIGEN_DEL_SALDO_MENSAJE2);
        mensajeDTO.setIdGrupoOperacion(ConstantesDyC.SUBORIGEN_DEL_SALDO_ID_CASO_DE_USO);

        try {
            resultado = this.dyccSubOrigSaldoDAO.insertarSuborigeDelSaldo(dyccSubOrigSaldoDTO, selectedTramites);

            /**************************************************************************
             * TODO Retirar una vez obtenisdos los datos de las ejecucion del usuario
             * FALTA IMPLEMENTAR NUEVA PISTA DE AUDITORIA AQU\u00cd
             * ************************************************************************
            dycbPistasAuditoriaDTO.setIdOperacion(ConstantesDyCNumerico.VALOR_8);
            dycbPistasAuditoriaDTO.setUsuario(ConstantesDyCNumerico.VALOR_1566);
            dycbPistasAuditoriaDTO.setIdUnidadAdmva(ConstantesDyCNumerico.VALOR_99);
            /**************************************************************************
             * TODO Retirar una vez obtenisdos los datos de las ejecucion del usuario**
             * ************************************************************************/

            LOG.debug("Iniciando insercion de nuevo Pista de Auditoria::");
            /**dycbPistasAuditoriaDAO.registrarPistaDeAuditoria(dycbPistasAuditoriaDTO);*/
            LOG.debug("Terminando insercion de nuevo Pista de Auditoria::");

        } catch (Exception e) {

            LOG.error("Se presento un error en la insercion del suborigen rollback es realizado" + e.getMessage());
        }

        return resultado;
    }

    public int actualizarSuborigeDelSaldo(DyccSubOrigSaldoDTO dyccSubOrigSaldoDTO,
                                          List<DyccTipoTramiteDTO> selectedTramitesToShow) {

        int resultado = 0;
        mensajeDTO.setIdMensaje(ConstantesDyC.SUBORIGEN_DEL_SALDO_MENSAJE4);
        mensajeDTO.setIdGrupoOperacion(ConstantesDyC.SUBORIGEN_DEL_SALDO_ID_CASO_DE_USO);

        try {

            resultado =
                    this.dyccSubOrigSaldoDAO.actualizarSuborigeDelSaldo(dyccSubOrigSaldoDTO, selectedTramitesToShow);

            /**************************************************************************
             * TODO Retirar una vez obtenidos los datos de las ejecucion del usuario**
             * ************************************************************************
            dycbPistasAuditoriaDTO.setIdOperacion(ConstantesDyCNumerico.VALOR_10);
            dycbPistasAuditoriaDTO.setUsuario(ConstantesDyCNumerico.VALOR_1566);
            dycbPistasAuditoriaDTO.setIdUnidadAdmva(ConstantesDyCNumerico.VALOR_99);
            /**************************************************************************
             * TODO Retirar una vez obtenisdos los datos de las ejecucion del usuario**
             * ************************************************************************/

            LOG.debug("Iniciando insercion de  Pista de Auditoria (CONSULTA) ::");
            /**dycbPistasAuditoriaDAO.registrarPistaDeAuditoria(dycbPistasAuditoriaDTO);*/
            LOG.debug("Terminando insercion de Pista de Auditoria (CONSULTA) ::");

        } catch (Exception e) {

            LOG.error("Se presento un error en la actualizaciòn del suborigen rollback es realizado" + e.getMessage());
        }

        return resultado;
    }

    @Override
    public List<DyccSubOrigSaldoDTO> obtenerSubOrigenesDeSaldosPorTramite(int idOrigenSaldo,
                                                                          int idTipoTramite) {
        List<DyccSubOrigSaldoDTO> subOrigenesDeSaldosList =
            this.dyccSubOrigSaldoDAO.obtenerSubOrigenesDeSaldosPorTramite(idOrigenSaldo, idTipoTramite);
        return subOrigenesDeSaldosList;
    }

    @Override
    public ParamOutputTO<CatalogoTO> getCatActividades(int idSubOrigenTramite) throws SIATException {
        LOG.info("INIT GET ACTIVIDADES " + idSubOrigenTramite);
        List<CatalogoTO> actividad = new ArrayList<CatalogoTO>();
        CatalogoTO catalogo = null;

        for (DyccActividadDTO dyccActividadDTO : dyccSubOrigSaldoDAO.getActividadesEconomicas(idSubOrigenTramite)) {
            catalogo = new CatalogoTO();
            catalogo.setIdNum(dyccActividadDTO.getIdActividad());
            catalogo.setDescripcion(dyccActividadDTO.getDescripcion());
            actividad.add(catalogo);
        }
        LOG.info("END GET ACTIVIDADES");
        return new ParamOutputTO<CatalogoTO>(actividad);
    }


    public void setDyccSubOrigSaldoDAO(DyccSubOrigSaldoDAO dyccSubOrigSaldoDAO) {
        this.dyccSubOrigSaldoDAO = dyccSubOrigSaldoDAO;
    }

    public DyccSubOrigSaldoDAO getDyccSubOrigSaldoDAO() {
        return dyccSubOrigSaldoDAO;
    }


}
