/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.registro.service.solicitud.impl;

import java.math.BigDecimal;

import java.sql.SQLException;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import mx.gob.sat.siat.dyc.catalogo.dao.DyccInconsistenciaDAO;
import mx.gob.sat.siat.dyc.dao.documento.DycaSolInconsistDAO;
import mx.gob.sat.siat.dyc.dao.parametros.DyccParametrosAppDAO;
import mx.gob.sat.siat.dyc.dao.regsolicitud.DycpSolicitudDAO;
import mx.gob.sat.siat.dyc.dao.rfc.DycpRfcDAO;
import mx.gob.sat.siat.dyc.dao.tipotramite.DyccTipoTramiteDAO;
import mx.gob.sat.siat.dyc.domain.inconsistencia.DycaSolInconsistDTO;
import mx.gob.sat.siat.dyc.domain.inconsistencia.DyccInconsistDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.registro.service.solicitud.AsignacionAutomaticaDictaminadorService;
import mx.gob.sat.siat.dyc.registro.service.solicitud.DeterminarFlujoSolicitudDevolucionService;
import mx.gob.sat.siat.dyc.registro.util.validador.ValidaTramitesDAO;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.ConsultarDeclaracionProvisionalODefinitivaDeImpuestosFederales36DAO;
import mx.gob.sat.siat.dyc.servicio.dao.pagos.ConsultarPagosElectronicosDAO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.DeclaracionProvicionalIntVO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.DeclaracionProvicionalODefinitivaDeImpuestosFederales36AVO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.DeclaracionProvisionalODefinitivaDeImpuestosFederales36DTO;
import mx.gob.sat.siat.dyc.servicio.dto.pagos.ConsultarPagosElectronicosAnioDTO;
import mx.gob.sat.siat.dyc.util.common.AsignarException;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesIds;
import mx.gob.sat.siat.dyc.util.constante1.ConstanteCompetenciaAGAFF;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC2;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * TODO
 * @author J. Isaac Carbajal Bernal
 * @version 0.2 24/03/2014
 *
 */
@Service(value = "determinarFlujoSolicitudDevolucionService")
public class DeterminarFlujoSolicitudDevolucionServiceImpl implements DeterminarFlujoSolicitudDevolucionService {

    private Logger log = Logger.getLogger(AsignacionAutomaticaDictaminadorServiceImpl.class);

    @Autowired
    private ConsultarDeclaracionProvisionalODefinitivaDeImpuestosFederales36DAO consultarDeclaracionProvisionalODefinitivaDeImpuestosFederales36DAO;

    @Autowired
    private ConsultarPagosElectronicosDAO consultarPagosElectronicosDAO;

    @Autowired
    private AsignacionAutomaticaDictaminadorService asignacionAutomaticaDictaminadorService;

    @Autowired
    private DyccInconsistenciaDAO inconsistenciaDAO;

    @Autowired
    private DycaSolInconsistDAO solInconsistDAO;

    @Autowired
    private DyccParametrosAppDAO parametrosAppDAO;

    @Autowired
    private DycpSolicitudDAO solicitudDAO;

    @Autowired
    private DyccTipoTramiteDAO tipoTramiteDAO;

    @Autowired(required = true)
    private ValidaTramitesDAO validaTramitesDAO;
    @Autowired
    private DycpRfcDAO dycpRfcDAO;

    private BigDecimal sumaCompsProv;
    private BigDecimal sumaImptePagarPagProv;

    private BigDecimal porcCompParaIAoISRoIETU;
    private BigDecimal porcPermCompParaIAoISRoIETU;


    public DeterminarFlujoSolicitudDevolucionServiceImpl() {
        super();
        sumaCompsProv = new BigDecimal(ConstantesDyC1.CERO);
        sumaImptePagarPagProv = new BigDecimal(ConstantesDyC1.CERO);
        porcCompParaIAoISRoIETU = new BigDecimal(ConstantesDyC1.CERO);
        porcPermCompParaIAoISRoIETU = new BigDecimal(ConstantesDyC1.CERO);
    }

    /**
     * Metodo temporal para asignacion de dictaminador automatica
     * @param tramiteDTO
     * @param tipoServicio
     */
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void determinarFlujoSolicitudDevolucionAsigAutm(DycpSolicitudDTO tramiteDTO, int tipoServicio,
                                                           String centroCosto) throws SQLException {

        asignacionAutomaticaDictaminadorService.asignarDictaminador(tramiteDTO, tipoServicio);

    }

    /**
     * @return Int 1 = exito 0 = error
     */
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void determinarFlujoSolicitudDevolucion(DycpSolicitudDTO tramiteDTO) throws SQLException, SIATException, AsignarException {

            List<Integer> validatramites = validaTramitesDAO.tramitesPorDeclaracion(ConstantesDyCNumerico.VALOR_26);
            if (validatramites.contains(tramiteDTO.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getIdTipoTramite())) {
                DeclaracionProvicionalIntVO declaracion = fillParametrosImpuestosFederales36(tramiteDTO);
                /**
                * 2.1 Consulta las declaraciones provisionales presentadas por el contribuyente
                * Interfaz: ConsultarDeclaracionProvisionalODefinitivaDeImpiestosFederales [DWH_DFSD-36]
                */
                List<DeclaracionProvisionalODefinitivaDeImpuestosFederales36DTO> declaracionesProvicionales =
                    consultarDeclaracionProvisionalODefinitivaDeImpuestosFederales36DAO.consultaDeImpuestos(declaracion);
                List<DeclaracionProvicionalODefinitivaDeImpuestosFederales36AVO> declaracionesProvicionalesA =
                    consultarDeclaracionProvisionalODefinitivaDeImpuestosFederales36DAO.consultaDeImpuestosA(declaracion);
                log.info("36 " + declaracionesProvicionales.size());
                log.info("36A " + declaracionesProvicionalesA.size());
                /**
                * 2.1.2 Calcula Total de las compensaciones  y de importe a pagar
                */
                if (declaracionesProvicionalesA.size() != ConstantesDyC2.SIZE_DE_LISTA_VACIA) {
                    sumarTotalCompensaciones(declaracionesProvicionalesA);
                }
                if (declaracionesProvicionales.size() != ConstantesDyC2.SIZE_DE_LISTA_VACIA) {
                    sumarImptePagarPagProv(declaracionesProvicionales);
                }
                /**
                * 2.2 Consulta las declaraciones provisionales presentadas por el contribuyente de forma electronica
                * Interfaz: ConsultarPagosElectronicos [DWH_DFSD-9]
                */
                List<ConsultarPagosElectronicosAnioDTO> pagosElectronicos =
                    consultarPagosElectronicosDAO.consultaRegistrosPorAnio(fillPagosElectronicosDto(tramiteDTO));

                if (pagosElectronicos.size() != ConstantesDyC2.SIZE_DE_LISTA_VACIA) {
                    /**
                 * 2.2.2
                 */
                    this.sumaCompsProv =
                            sumaCompsProv.add(pagosElectronicos.get(ConstantesDyC2.PRIMER_ELEMENTO_DE_LISTA).getVCompensacion());
                    sumaImptePagarPagProv =
                            sumaImptePagarPagProv.add(pagosElectronicos.get(ConstantesDyC2.PRIMER_ELEMENTO_DE_LISTA).getVImporteAcargo());
                }
                /**
                 * 3.1
                 */
                if (sumaImptePagarPagProv.compareTo(ConstanteCompetenciaAGAFF.NUEVO_BIGDECIMAL_CERO) != ConstantesDyC2.ZERO_VALUE) {
                    /**
                     * 3.1.1
                     */
                    porcCompParaIAoISRoIETU = this.sumaCompsProv.divide(sumaImptePagarPagProv);


                    log.info("<PorcentajeCompensacionesParaIAoISRoIETU>" + porcCompParaIAoISRoIETU);

                    /**
                     * 3.1.2 PorcentajePermitidoCompensacionesParaIAoISRoIETU
                     */
                    porcPermCompParaIAoISRoIETU =
                            BigDecimal.valueOf(this.parametrosAppDAO.encontrar(ConstantesIds.PORCENTAJE_PERM_COMP_IA_ISR).getValor().doubleValue());

                    /**
                     * 3.1.3 ProcentajePermitidoCompensacionesParaIAISR Regla RNFDC27
                     */
                    if (porcentajePermitido()) {

                        solInconsistDAO.insertar(fillDycaSolInconsistenciaDTO(tramiteDTO,
                                                                              ConstanteCompetenciaAGAFF.INCONSISTENCIA_MSJ1));

                    } else if (sumaCompsProv.compareTo(ConstanteCompetenciaAGAFF.NUEVO_BIGDECIMAL_CERO) !=
                               ConstantesDyC2.ZERO_VALUE) {

                        solInconsistDAO.insertar(fillDycaSolInconsistenciaDTO(tramiteDTO,
                                                                              ConstanteCompetenciaAGAFF.INCONSISTENCIA_MSJ2));
                    }
                }
            }
    }


    private boolean porcentajePermitido() {
        return ((porcPermCompParaIAoISRoIETU.compareTo(ConstanteCompetenciaAGAFF.PORC_PERM_COMP_IA_ISR_IETU) == 0 ||
                 porcPermCompParaIAoISRoIETU.compareTo(ConstanteCompetenciaAGAFF.PORC_PERM_COMP_IA_ISR_IETU_LIMP_SUP) == -1) &&
                porcPermCompParaIAoISRoIETU.compareTo(ConstanteCompetenciaAGAFF.NUEVO_BIGDECIMAL_CERO) == 1);
    }

    @Override
    public DycpSolicitudDTO encontar(String numControl) {
        return solicitudDAO.encontrar(numControl);
    }


    private DeclaracionProvicionalIntVO fillParametrosImpuestosFederales36(DycpSolicitudDTO tramiteDTO) {

        DeclaracionProvicionalIntVO filledDTO = new DeclaracionProvicionalIntVO();

        filledDTO.setRfc1(tramiteDTO.getDycpServicioDTO().getRfcContribuyente());
        filledDTO.setRfc2(tramiteDTO.getDycpServicioDTO().getRfcContribuyente());
        filledDTO.setRfc3(tramiteDTO.getDycpServicioDTO().getRfcContribuyente());
        filledDTO.setEjercicio(tramiteDTO.getDyctSaldoIcepDTO().getDyccEjercicioDTO().getIdEjercicio());
        filledDTO.setPeriodo(tramiteDTO.getDyctSaldoIcepDTO().getDyccPeriodoDTO().getIdPeriodo());

        return filledDTO;
    }

    /**
     * TODO
     * @param tramiteDTO
     * @return
     */
    private DycaSolInconsistDTO fillDycaSolInconsistenciaDTO(DycpSolicitudDTO tramiteDTO, Integer idInconsistencia) {

        Date fechaRegistro = new Date();
        DycaSolInconsistDTO filledDTO = new DycaSolInconsistDTO();
        DyccInconsistDTO inconsistencia = new DyccInconsistDTO();
        inconsistencia.setIdInconsistencia(idInconsistencia);
        filledDTO.setDyccInconsistDTO(inconsistencia);
        filledDTO.setDescripcion((inconsistenciaDAO.buscarInconsistencia(idInconsistencia)).getDescripcion());
        filledDTO.setDycpSolicitudDTO(tramiteDTO);
        filledDTO.setFechaRegistro(fechaRegistro);
        return filledDTO;
    }


    /**
     * TODO
     * @param tramiteDTO
     * @return
     */
    private ConsultarPagosElectronicosAnioDTO fillPagosElectronicosDto(DycpSolicitudDTO tramiteDTO) throws SIATException {

        ConsultarPagosElectronicosAnioDTO filledDTO = new ConsultarPagosElectronicosAnioDTO();

        filledDTO.setTxtrfc(tramiteDTO.getDycpServicioDTO().getRfcContribuyente());
        filledDTO.setTxtrfc2(tramiteDTO.getDycpServicioDTO().getRfcContribuyente());
        filledDTO.setTxtrfc3(tramiteDTO.getDycpServicioDTO().getRfcContribuyente());
        filledDTO.setNumper(tramiteDTO.getDyctSaldoIcepDTO().getDyccPeriodoDTO().getIdPeriodo());
        filledDTO.setNumeje(tramiteDTO.getDyctSaldoIcepDTO().getDyccEjercicioDTO().getIdEjercicio());
        DyccTipoTramiteDTO tipoTramite =
            tipoTramiteDAO.encontrar(tramiteDTO.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getIdTipoTramite());
        filledDTO.setNumcon(tipoTramite.getDyccConceptoDTO().getIdConcepto());

        return filledDTO;
    }

    /**
     * Suma de las compensaciones de los pagos provicionales
     * @param declaracionesProvicionalesA
     */
    private void sumarTotalCompensaciones(List<DeclaracionProvicionalODefinitivaDeImpuestosFederales36AVO> declaracionesProvicionalesA) {
        Iterator it;
        it = declaracionesProvicionalesA.iterator();
        while (it.hasNext()) {
            this.sumaCompsProv =
                    sumaCompsProv.add(((DeclaracionProvicionalODefinitivaDeImpuestosFederales36AVO)it.next()).getIDecIsqamau1());
        }
    }

    /**
     * Suma del importe a pagar de pagos provisionales
     * @param declaracionesProvicionales
     */
    private void sumarImptePagarPagProv(List<DeclaracionProvisionalODefinitivaDeImpuestosFederales36DTO> declaracionesProvicionales) {
        Iterator it;
        it = declaracionesProvicionales.iterator();
        while (it.hasNext()) {
            sumaImptePagarPagProv =
                    sumaImptePagarPagProv.add(((DeclaracionProvisionalODefinitivaDeImpuestosFederales36DTO)it.next()).getIPagIcmapru1());
        }
    }

    public void setPorcCompParaIAoISRoIETU(BigDecimal porcCompParaIAoISRoIETU) {
        this.porcCompParaIAoISRoIETU = porcCompParaIAoISRoIETU;
    }

    public BigDecimal getPorcCompParaIAoISRoIETU() {
        return porcCompParaIAoISRoIETU;
    }

    public void setPorcPermCompParaIAoISRoIETU(BigDecimal porcPermCompParaIAoISRoIETU) {
        this.porcPermCompParaIAoISRoIETU = porcPermCompParaIAoISRoIETU;
    }

    public BigDecimal getPorcPermCompParaIAoISRoIETU() {
        return porcPermCompParaIAoISRoIETU;
    }

    public void setSumaImptePagarPagProv(BigDecimal sumaImptePagarPagProv) {
        this.sumaImptePagarPagProv = sumaImptePagarPagProv;
    }

    public BigDecimal getSumaImptePagarPagProv() {
        return sumaImptePagarPagProv;
    }

    public void setDycpRfcDAO(DycpRfcDAO dycpRfcDAO) {
        this.dycpRfcDAO = dycpRfcDAO;
    }

    public DycpRfcDAO getDycpRfcDAO() {
        return dycpRfcDAO;
    }
}
