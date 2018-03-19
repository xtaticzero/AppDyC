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

import mx.gob.sat.siat.dyc.catalogo.service.DyccOrigenSaldoService;
import mx.gob.sat.siat.dyc.dao.movsaldo.DyccOrigenSaldoDAO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;
import mx.gob.sat.siat.dyc.util.common.CatalogoTO;
import mx.gob.sat.siat.dyc.util.common.ParamOutputTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesClavesYRoles;
import mx.gob.sat.siat.dyc.util.constante.ConstantesConsultarCompensaciones;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyC;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


@Service(value = "dyccOrigenSaldoService")
public class DyccOrigenSaldoServiceImpl implements DyccOrigenSaldoService {

    private static final Logger LOG = Logger.getLogger(DyccOrigenSaldoServiceImpl.class);

    @Autowired
    private DyccOrigenSaldoDAO dyccOrigenSaldoDAO;

    /**
     * @return
     */
    @Override
    public List<DyccOrigenSaldoDTO> obtieneOrigenesSaldo() {
        List<DyccOrigenSaldoDTO> origenSaldo = new ArrayList<DyccOrigenSaldoDTO>();
        try {
            origenSaldo = dyccOrigenSaldoDAO.obtenerOrigenSaldoTramitesSinOficio();
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return origenSaldo;
    }

    @Override
    public List<DyccOrigenSaldoDTO> obtieneOrigenesDeSaldosPorAvisoCompensacion() {
        List<DyccOrigenSaldoDTO> origenSaldo = new ArrayList<DyccOrigenSaldoDTO>();
        try {
            origenSaldo = dyccOrigenSaldoDAO.obtieneOrigenesDeSaldosPorAvisoCompensacion();
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return origenSaldo;
    }

    /**
     * @see {@link mx.gob.sat.siat.dyc.dao.impl.DyccOrigenSaldoDAOImpl.obtieneOrigenesDeSaldosPorAvisoCompensacion}
     */
        @Override
    public List<DyccOrigenSaldoDTO> obtieneOrigenesDeSaldosPorAvisoCompensacion(final String param1) 
    {
       return  dyccOrigenSaldoDAO.obtieneOrigenesDeSaldosPorAvisoCompensacion(param1);
    }

    
    @Override
    public ParamOutputTO<CatalogoTO> obtieneOrigenesSaldo(String registrador) throws SIATException {
        CatalogoTO itemCat = null;
        List<CatalogoTO> catOrigenSaldo = new ArrayList<CatalogoTO>();

        try {
            for (DyccOrigenSaldoDTO item : dyccOrigenSaldoDAO.obtieneTodos(getConsulta(registrador))) {
                itemCat = new CatalogoTO();
                itemCat.setIdNum(item.getIdOrigenSaldo());
                itemCat.setDescripcion(null != item.getDescripcion() ? item.getDescripcion() :
                                       ConstantesDyC.EMPTY_STRING);
                itemCat.setIdString(item.getIdOrigenSaldo() != ConstantesDyCNumerico.VALOR_1 ?
                                    String.valueOf(ConstantesConsultarCompensaciones.TIPO_SALDO_ICEP_CAF) :
                                    String.valueOf(ConstantesConsultarCompensaciones.TIPO_SALDO_ICEP_SAF));
                catOrigenSaldo.add(itemCat);
            }
        } catch (DataAccessException e) {
            throw new SIATException(e);
        }
        return new ParamOutputTO<CatalogoTO>(catOrigenSaldo);
    }

    private String getConsulta(String registrador) throws SIATException {
        LOG.debug("INICIO getConsulta");
            StringBuilder query = new StringBuilder("os.ordensec in(");
            if (registrador.equals(ConstantesDyC.CONTRIBUYENTE) || 
                    registrador.equals(ConstantesClavesYRoles.ALAF)) {
                query.append("10,20,30,40)");
            } else if (registrador.equals(ConstantesDyC.ACGC)) {
                query.append("30,50,60,70,80)");
            } else if (registrador.equals(ConstantesDyC.ALSC)||
                    registrador.equals(ConstantesDyC.AF_AGACE)) {
                query.append("20,30)");
            } else if (registrador.equals(ConstantesDyC.ALSC_PM)) {
                query.append("30");
            } else if (registrador.equals(ConstantesDyC.AC_DC)) {
                query.append("80)");
            } else {
                throw new SIATException("SIN ORIGEN DE LA DEVOLUCION");
            }
            query.append(" ORDER BY os.ordensec");

            return query.toString();
        }

    @Override
    public ParamOutputTO<CatalogoTO> getOrigenSaldo(int idOrigen) throws SIATException {
        DyccOrigenSaldoDTO dyccOrigenSaldoDTO = null;
        try {
            dyccOrigenSaldoDTO = dyccOrigenSaldoDAO.getOrigenSaldo(idOrigen);
        } catch (DataAccessException e) {
            throw new SIATException(e);
        }
        return new ParamOutputTO<CatalogoTO>(new CatalogoTO(dyccOrigenSaldoDTO.getIdOrigenSaldo(),
                                                            dyccOrigenSaldoDTO.getDescripcion(), null));
    }

    @Override
    public DyccOrigenSaldoDTO encontrar(Integer id) {
        return dyccOrigenSaldoDAO.encontrar(id);
    }

}
