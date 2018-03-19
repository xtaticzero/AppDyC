/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.catalogo.service.impl;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.catalogo.service.DyctDeclaracionService;
import mx.gob.sat.siat.dyc.dao.declaracion.DyctDeclaracionDAO;
import mx.gob.sat.siat.dyc.dao.secuencia.solicitud.SolNumConsecutivoDAO;
import mx.gob.sat.siat.dyc.domain.declaraciontemp.DyctDeclaraTempDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaracionDTO;
import mx.gob.sat.siat.dyc.util.common.ParamOutputTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesCompetenciaAgaff;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.vo.InformacionSaldoFavorTO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Servicio para consulta de declaraciones
 * @author Federico Chopin Gachuz
 *
 */
@Service("dyctDeclaracionService")
public class DyctDeclaracionServiceImpl implements DyctDeclaracionService {
    public DyctDeclaracionServiceImpl() {
        super();
    }

    @Autowired
    private DyctDeclaracionDAO dyctDeclaracionDAO;
    @Autowired
    private SolNumConsecutivoDAO solNumConsecutivoDAO;

    private Integer idDeclaracion;

    private static final Logger LOG = Logger.getLogger(DyctDeclaracionServiceImpl.class);

    /**
     * Metodo de servicio para consulta de declaraciones
     * @param String messageSol
     * @return Objeto <DyctDeclaracionDTO>
     *
     */
    @Transactional(readOnly = true)
    public List<DyctDeclaracionDTO> buscarDeclaracionesNumControl(String numControl) {

        List<DyctDeclaracionDTO> dyctDeclaracionDTO = new ArrayList<DyctDeclaracionDTO>();

        try {
            dyctDeclaracionDTO = dyctDeclaracionDAO.buscarDeclaracionesNumControl(numControl);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
        return dyctDeclaracionDTO;

    }

    /**
     * @param paramInput
     * @return
     * @throws SIATException
     */
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void createDeclaracion(DyctDeclaracionDTO paramInput) throws SIATException {
        try {
            idDeclaracion = solNumConsecutivoDAO.getIdDeclaracion();
        } catch (DataAccessException e) {
            throw new SIATException(e);
        }
        LOG.info("INIT createDeclaracion " + paramInput.getNumControl() + " idDelara " + idDeclaracion);
        paramInput.setIdDeclaracion(idDeclaracion);
        idDeclaracion = null;
        dyctDeclaracionDAO.createDeclaracion(paramInput);
        /**  dyctDeclaracionDAO.createRelacionDeclaracion(paramOutput.getIdDeclaracion(), numControl); */


    }


    @Override
    public void createDeclaracionTemp(InformacionSaldoFavorTO inputDec, String numControl) throws SIATException {
        LOG.info("INIT createDeclaracionTemp " + numControl);
        DyctDeclaraTempDTO declaracionTemp = new DyctDeclaraTempDTO();
        declaracionTemp.setFolioServtemp(Integer.parseInt(numControl));
        declaracionTemp.setFechaPresentacion(inputDec.getFechaPresentacion());
        declaracionTemp.setFechaCausacion(inputDec.getFechaCaucion());
        declaracionTemp.setNumOperacion(inputDec.getNumeroOperacion());
        declaracionTemp.setNumDocumento(inputDec.getNumeroDocumento());
        declaracionTemp.setSaldoAFavor(null != inputDec.getImporteSaldoFavor() ? inputDec.getImporteSaldoFavor() : ConstantesCompetenciaAgaff.NUEVO_BIGDECIMAL_CERO);
        declaracionTemp.setDevueltoCompensado(inputDec.getImporteAcreditramientoEfectuado());
        declaracionTemp.setAcreditamiento(inputDec.getImporteEfectuados());
        declaracionTemp.setImporte(inputDec.getImporteSolicitadoDevolucion());
        declaracionTemp.setIdTipoDeclaracion(null != inputDec.getTipoDeclaracion() ?
                                             Integer.parseInt(inputDec.getTipoDeclaracion()) :
                                             ConstantesDyCNumerico.VALOR_3);
        declaracionTemp.setNormalFechapres(inputDec.getNormalFechaPresentacion());
        declaracionTemp.setNormalNumoperacion(null != inputDec.getNormalNumOperacion() ?
                                              Long.parseLong(inputDec.getNormalNumOperacion()) : null);
        declaracionTemp.setNormalImportesaf(inputDec.getNormalSaldoFavor());
        declaracionTemp.setIdUsoDec(ConstantesDyCNumerico.VALOR_1);
        declaracionTemp.setEtiquetaSaldo(inputDec.getEtiquetaSaldo());
        try {
            dyctDeclaracionDAO.createDeclaracionTemp(declaracionTemp);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
            throw new SIATException(e);
        }
    }

    @Override
    public ParamOutputTO<InformacionSaldoFavorTO> findDeclaracionTemp(int folio) throws SIATException {
        InformacionSaldoFavorTO declaracionTemp = null;
        LOG.info("INIT FIND DECLARATEMP, FOLIO " + folio);
        try {
            declaracionTemp = dyctDeclaracionDAO.findDeclaracionTemp(folio);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return new ParamOutputTO<InformacionSaldoFavorTO>(declaracionTemp);
    }


    public void setIdDeclaracion(int idDeclaracion) {
        this.idDeclaracion = idDeclaracion;
    }

    public int getIdDeclaracion() {
        return idDeclaracion;
    }


    public void setDyctDeclaracionDAO(DyctDeclaracionDAO dyctDeclaracionDAO) {
        this.dyctDeclaracionDAO = dyctDeclaracionDAO;
    }

    public DyctDeclaracionDAO getDyctDeclaracionDAO() {
        return dyctDeclaracionDAO;
    }

    public void setSolNumConsecutivoDAO(SolNumConsecutivoDAO solNumConsecutivoDAO) {
        this.solNumConsecutivoDAO = solNumConsecutivoDAO;
    }

    public SolNumConsecutivoDAO getSolNumConsecutivoDAO() {
        return solNumConsecutivoDAO;
    }
}
