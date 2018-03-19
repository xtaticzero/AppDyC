/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.catalogo.service.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.catalogo.service.DyccPeriodoService;
import mx.gob.sat.siat.dyc.dao.periodo.DyccPeriodoDAO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDictaminacionAutomatica;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Servicio para la implementacion de la logica de negocio del dto DyccSubtramiteDTO
 * @author Paola Rivera
 */
@Service(value = "dyccPeriodoService")
public class DyccPeriodoServiceImpl implements DyccPeriodoService {

    @Autowired
    private DyccPeriodoDAO dyccPeriodoDAO;

    @Override
    public List<DyccPeriodoDTO> obtienePeriodoPorTipoPeriodo(String idTipoPeriodo) {
        return this.dyccPeriodoDAO.obtienePeriodoPorTipoPeriodo(idTipoPeriodo);
    }

    @Override
    public DyccPeriodoDTO obtienePeriodoPorIdPeriodo(DyccPeriodoDTO dyccPeriodo) {
        return this.dyccPeriodoDAO.obtienePeriodoPorId(dyccPeriodo);
    }

    @Override
    public List<DyccPeriodoDTO> obtenerPeriodos() {
        return this.dyccPeriodoDAO.obtenerPeriodos();
    }

    @Override
    public List<DyccPeriodoDTO> obtenerPeriodosPorTipoDePeriodo(String tipoPeriodo) {
        return this.dyccPeriodoDAO.obtenerPeriodosPorTipoDePeriodo(tipoPeriodo);
    }

    @Override
    public String obtenerPeriodoDiot(int idPeriodo) {
        return this.dyccPeriodoDAO.obtenerPeriodoDiot(idPeriodo);
    }

    @Override
    public Integer obtenerFinPeriodo(int idPeriodo) {
        String result = dyccPeriodoDAO.obtenerFinPeriodo(idPeriodo);
        return result.equals(ConstantesDictaminacionAutomatica.NO_APLICA)?ConstantesDyCNumerico.VALOR_12: Integer.parseInt(result);
    }

}
