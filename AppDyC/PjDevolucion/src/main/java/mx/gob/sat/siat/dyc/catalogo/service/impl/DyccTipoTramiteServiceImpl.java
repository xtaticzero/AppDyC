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
import java.util.logging.Logger;

import mx.gob.sat.siat.dyc.catalogo.service.DyccTipoTramiteService;
import mx.gob.sat.siat.dyc.dao.tipotramite.DyccTipoTramiteDAO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.util.common.CatalogoTO;
import mx.gob.sat.siat.dyc.util.common.ParamOutputTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


/**
 * @author Paola Rivera
 * Ultima actalizacion I.chavez
 */
@Service("dyccTipoTramiteService")
public class DyccTipoTramiteServiceImpl implements DyccTipoTramiteService {

    private static final Logger LOG = Logger.getLogger(DyccTipoTramiteServiceImpl.class.getName());

    @Autowired
    private DyccTipoTramiteDAO dyccTipoTramiteDAO;

    public DyccTipoTramiteServiceImpl() {
        super();
    }

    /**
     * @param idOrigenSaldo
     * @return
     */
    @Override
    public ParamOutputTO<CatalogoTO> obtieneTipoTramite(int idOrigenSaldo, int competencia,
                                                        List<Integer> roles, boolean isAgace) throws SIATException {
        LOG.info("obtieneTipoTramite idOrigenSaldo: " + idOrigenSaldo);
        LOG.info("obtieneTipoTramite   competencia: " + competencia);
        LOG.info("obtieneTipoTramite      isAgace : " + isAgace);
        LOG.info("obtieneTipoTramite: ");
        List<DyccTipoTramiteDTO> tipoTramite = null;
        List<CatalogoTO> catTipoTramite = new ArrayList<CatalogoTO>();
        try {
            tipoTramite =
                    this.dyccTipoTramiteDAO.obtieneTipoTramitePorIdOrigen(idOrigenSaldo, competencia, isAgace, roles.get(0),
                                                                         roles.get(1)); 
        } catch (DataAccessException dae) {
            throw new SIATException(dae);
        } 

        CatalogoTO catalogo = null;
        if (null != tipoTramite) {
            for (DyccTipoTramiteDTO s : tipoTramite) {
                catalogo = new CatalogoTO();
                catalogo.setIdNum(s.getIdTipoTramite());
                catalogo.setIdString(s.getIdTipoTramite() + " " + s.getDescripcion());
                catTipoTramite.add(catalogo);
            }
        }
        return new ParamOutputTO<CatalogoTO>(catTipoTramite);
    }

    @Override
    public List<DyccTipoTramiteDTO> obtenerTramites(Integer tramite) throws SQLException {
        return this.dyccTipoTramiteDAO.obtieneTodosTipoTramite(tramite);
    }

    @Override
    public List<DyccTipoTramiteDTO> obtieneTipoTramitePorIdSubOrigenSaldo(int idOrigenSaldo) throws SQLException {
        return this.dyccTipoTramiteDAO.obtieneTipoTramitePorIdSubOrigenSaldo(idOrigenSaldo);
    }

    @Override
    public List<DyccTipoTramiteDTO> obtieneTipoTramitePorIdConcepto(int idConcepto) throws SQLException {

        return this.dyccTipoTramiteDAO.obtieneTipoTramitePorIdConcepto(idConcepto);

    }

    @Override
    public List<DyccTipoTramiteDTO> obtieneTipoTramitePorAnexo(int anexo) throws SQLException {
        return this.dyccTipoTramiteDAO.obtieneTipoTramitePorAnexo(anexo);
    }

    public void setDyccTipoTramiteDAO(DyccTipoTramiteDAO dyccTipoTramiteDAO) {
        this.dyccTipoTramiteDAO = dyccTipoTramiteDAO;
    }

    public DyccTipoTramiteDAO getDyccTipoTramiteDAO() {
        return dyccTipoTramiteDAO;
    }

    @Override
    public ParamOutputTO<CatalogoTO> tramitesPagoDeLoIndebido() throws SIATException {
        List<DyccTipoTramiteDTO> tipoTramite = null;
        List<CatalogoTO> catTipoTramite = new ArrayList<CatalogoTO>();
        try {
            tipoTramite = this.dyccTipoTramiteDAO.tramitesPagoDeLoIndebido();
        } catch (DataAccessException dae) {
            throw new SIATException(dae);
        } catch (SQLException e) {
            throw new SIATException(e);
        }

        CatalogoTO catalogo = null;
        if (null != tipoTramite) {
            for (DyccTipoTramiteDTO s : tipoTramite) {
                catalogo = new CatalogoTO();
                catalogo.setIdNum(s.getIdTipoTramite());
                catalogo.setIdString(s.getIdTipoTramite() + " " + s.getDescripcion());
                catTipoTramite.add(catalogo);
            }
        }
        return new ParamOutputTO<CatalogoTO>(catTipoTramite);
    }

    @Override
    public Integer obtenerTipoTramiteXConceptoAvisos(int concepto) {
        return this.dyccTipoTramiteDAO.obtenerTipoTramiteXConceptoAvisos(concepto);
    }

    @Override
    public ParamOutputTO<DyccTipoTramiteDTO> getTramite(int idTipoTramite) throws SIATException {
        DyccTipoTramiteDTO dyccTipoTramiteDTO = null;
        try {
            dyccTipoTramiteDTO = dyccTipoTramiteDAO.obtieneTipoTramite(idTipoTramite);
        } catch (DataAccessException e) {
            throw new SIATException(e);
        }
        return new ParamOutputTO<DyccTipoTramiteDTO>(dyccTipoTramiteDTO);
    }

    @Override
    public DyccTipoTramiteDTO encontrar(Integer idTipoTramite) throws SIATException {
        return dyccTipoTramiteDAO.encontrar(idTipoTramite);
    }

    /**
     * OBTIENE EL TIPO DE TRAMITE POR IDCONCEPTO ORIGEN, ORIGENSALDO Y TIPO DE SERVICIO = 3
     * PARA AVISO DE COMPENSACION
     * @author Luis Alberto Dominguez Palomino [LADP]
     * @param idConcepto
     * @return
     * @throws SQLException
     */
    @Override
    public List<DyccTipoTramiteDTO> obtieneTipoTramitePorConceptoOrigen(int idConcepto, int origensaldo,
                                                                        int tipoRol) throws SQLException {
        return this.dyccTipoTramiteDAO.obtieneTipoTramitePorConceptoOrigen(idConcepto, origensaldo, tipoRol);

    }
}
