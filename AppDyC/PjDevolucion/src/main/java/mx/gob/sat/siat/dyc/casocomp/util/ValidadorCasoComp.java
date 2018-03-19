package mx.gob.sat.siat.dyc.casocomp.util;

import java.sql.SQLException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import mx.gob.sat.siat.dyc.casocomp.dao.districomp.CreaCasoCompenDAO;
import mx.gob.sat.siat.dyc.casocomp.dto.districomp.CasoCompensacionVO;
import mx.gob.sat.siat.dyc.catalogo.service.DyccParametrosAppService;
import mx.gob.sat.siat.dyc.dao.compensacion.IDycpCompensacionDAO;
import mx.gob.sat.siat.dyc.dao.detalleicep.DyctSaldoIcepDAO;
import mx.gob.sat.siat.dyc.dao.tipotramite.DyccTipoTramiteDAO;
import mx.gob.sat.siat.dyc.domain.DyccParametrosAppDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaRolDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoDeclaraDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesAutomaticaDictaminador;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyC;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.validacion.ConstantesValidaAdministracion;
import mx.gob.sat.siat.dyc.util.constante.validacion.ConstantesValidaRNFDC16;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

/**
 * Validador Caso de Uso Aviso o Compensaciones
 * @author [LADP] Luis Alberto Dominguez Palomino
 * @since 1.0 , 28 Noviembre 2013
 */
@Component(value = "validadorCasoComp")
public class ValidadorCasoComp {

    @Autowired
    private DyccParametrosAppService dyccParametrosAppService;

    @Autowired
    private DyccTipoTramiteDAO dyccTipoTramiteDAO;

    @Autowired
    private IDycpCompensacionDAO dycpCompensacionDAO;

    @Autowired
    private CreaCasoCompenDAO creaCasoCompenDAO;

    @Autowired
    private DyctSaldoIcepDAO dyctSaldoIcepDAO;

    private Logger log = Logger.getLogger(ValidadorCasoComp.class.getName());

    private Integer montoCompensa;
    private Integer result;

    private Integer estadoCII;

    private Integer tipoMotivo;
    private boolean resultaValida = Boolean.TRUE;

    public ValidadorCasoComp() {
        super();
    }

    public void setMontoCompensa(Integer montoCompensa) {
        this.montoCompensa = montoCompensa;
    }

    public Integer getMontoCompensa() {
        return montoCompensa;
    }

    public void setDyccParametrosAppService(DyccParametrosAppService dyccParametrosAppService) {
        this.dyccParametrosAppService = dyccParametrosAppService;
    }

    public DyccParametrosAppService getDyccParametrosAppService() {
        return dyccParametrosAppService;
    }

    public void setEstadoCII(Integer estadoCII) {
        this.estadoCII = estadoCII;
    }

    public Integer getEstadoCII() {
        return estadoCII;
    }

    public void setDyccTipoTramiteDAO(DyccTipoTramiteDAO dyccTipoTramiteDAO) {
        this.dyccTipoTramiteDAO = dyccTipoTramiteDAO;
    }

    public DyccTipoTramiteDAO getDyccTipoTramiteDAO() {
        return dyccTipoTramiteDAO;
    }

    public void setTipoMotivo(Integer tipoMotivo) {
        this.tipoMotivo = tipoMotivo;
    }

    public Integer getTipoMotivo() {
        return tipoMotivo;
    }

    public void setLog(Logger log) {
        this.log = log;
    }

    public Logger getLog() {
        return log;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Integer getResult() {
        return result;
    }

    public void setDycpCompensacionDAO(IDycpCompensacionDAO dycpCompensacionDAO) {
        this.dycpCompensacionDAO = dycpCompensacionDAO;
    }

    public IDycpCompensacionDAO getDycpCompensacionDAO() {
        return dycpCompensacionDAO;
    }

    public void setResultaValida(boolean resultaValida) {
        this.resultaValida = resultaValida;
    }

    public boolean isResultaValida() {
        return resultaValida;
    }

    public void setCreaCasoCompenDAO(CreaCasoCompenDAO creaCasoCompenDAO) {
        this.creaCasoCompenDAO = creaCasoCompenDAO;
    }

    public CreaCasoCompenDAO getCreaCasoCompenDAO() {
        return creaCasoCompenDAO;
    }

    /** --Seleccion monto para Determinar cambio de estado del Caso de Compensacion
     * a "Registrada"--
     * Si el caso de compensacion pertenece a una Administracion Central de Grandes Contribuyentes (ACGC) entonces
     * usa el parametro MontoParaDeterminarRegistroCasoCompensacionGC --> Regla Negocio RNFDC1005
     * De lo contrario si el caso de compensacion pertenece a una Administracion Desconcentrada de Auditoria Fiscal (ALAF) entonces
     * usa el parametro MontoParaDeterminarRegistroCasoCompensacionALAF --> Regla Negocio RNFDC1006
     *
     * @param administracion
     * @return
     */
    public Integer rn1004(int administracion) {
        if (administracion == ConstantesValidaAdministracion.ACFECF || administracion == ConstantesValidaAdministracion.ACFSF ||
            administracion == ConstantesValidaAdministracion.ACFGCD) {
            List<DyccParametrosAppDTO> montoACGC;
            try {
                montoACGC = dyccParametrosAppService.obtieneParametroDto(ConstantesDyC.ID_MONTO_ACGC);
                result = Integer.parseInt(String.valueOf(montoACGC.get(0).getValor()));
                this.setMontoCompensa(result);
                log.info("El monto por defecto para ACGC es " + result);
            } catch (SQLException e) {
                log.info("Error al obtener el monto");
            }
        } else {
            List<DyccParametrosAppDTO> montoALAF;
            try {
                montoALAF = dyccParametrosAppService.obtieneParametroDto(ConstantesDyC.ID_MONTO_ALAF);
                result = Integer.parseInt(String.valueOf(montoALAF.get(0).getValor()));
                this.setMontoCompensa(result);
                log.info("El monto por defecto para alaf es " + result);
            } catch (SQLException e) {
                log.info("Error al obtener el monto");
            }
        }
        return this.getMontoCompensa();
    }

    public List<DyccParametrosAppDTO> obtenerMonto() {
        List<DyccParametrosAppDTO> montoACGC = null;
        try {
            montoACGC = dyccParametrosAppService.obtieneParametroDto(ConstantesDyC.ID_MONTO_ACGC);
        } catch (SQLException e) {
            log.info("Error al obtener el monto");
        }
        return montoACGC;
    }

    /**--Restriccion parametro DeterminarTipoDeTramite--
     * Si el Caso de Compensacion es originado a partir de una Declaracion
     * Provisional o Definitiva de Impuesto Federales entonces
     * El Tipo de Tramite del Caso Compensacion es el que tenga el mismo
     * <Concepto> y <Tipo de Origen del Saldo> del origen con mayor importe compensado
     * De lo contrario S el Caso de Compensacion es originado a partir de una Declaracion anual de
     * personas fisicas (Forma 13) o una declaracion presentadad electronicamente (Fomra 18,19,20 y FU)
     * o una delcaracion anual de personas morales (Forma 2, 2A, 3) entonces
     * El tipo de Tramite del Caso de Compensacion es el que tenga el mismo <Concepto> del origen de saldo
     * compensado y el >Tipo de Origen de Saldo> sea igual a "Saldo a Favor".
     *
     * NOTA:
     * De acuerdo ala matriz de Tipos de Tramites de Compensacion los tipos de Origen Saldo son:
     * -Saldo a Favor, IA a Recuperar Ejercicios Anteriores(Tipo Tramites 200s)
     * -Pago de lo indebido(Tipo Tramites 500s)
     * -Resolucion o Sentencia(Tipo Tramites 600s)
     *
     * @param origenSaldo
     * @param concepto
     * @param periodo
     * @return
     */
    public Integer rn1003(int origenSaldo, int concepto, int periodo, int rol, int competencia) throws SIATException {
        int tramite = ConstantesDyCNumerico.VALOR_0;
        if (periodo != ConstantesValidaRNFDC16.EJERCICIO) {
            tramite =
                    dyccTipoTramiteDAO.selecXTipoTramite(concepto, rol, competencia, origenSaldo, ConstantesAutomaticaDictaminador.SERVICIO_CASO_DE_COMPENSACION);
            return tramite;
        } else if (periodo == ConstantesValidaRNFDC16.EJERCICIO && origenSaldo == ConstantesDyCNumerico.VALOR_1) {
            tramite =
                    dyccTipoTramiteDAO.selecXTipoTramite(concepto, rol, competencia, origenSaldo, ConstantesAutomaticaDictaminador.SERVICIO_CASO_DE_COMPENSACION);
            return tramite;
        }
        return tramite;
    }

    /**
     *Metodo que valida campos del objeto DyctCasoCompDTO para el seguimiento de la creacion del caso de compensacion
     * @param dycpCompensacionDTO
     * @param casoCompensa
     * @return 
     */
    public boolean validarCamposCC(DycpCompensacionDTO dycpCompensacionDTO, CasoCompensacionVO casoCompensa) {
        boolean resultaVali;
        resultaValida= Boolean.TRUE;
        if (casoCompensa.getIdDeclaracion() == null ||
            casoCompensa.getDycpCompensacionDTO().getDyctSaldoIcepDestinoDTO().getDyccConceptoDTO().getDyccImpuestoDTO().getIdImpuesto() ==
            ConstantesDyCNumerico.VALOR_0 ||
            casoCompensa.getDycpCompensacionDTO().getDyctSaldoIcepDestinoDTO().getDyccPeriodoDTO().getIdPeriodo() ==
            ConstantesDyCNumerico.VALOR_0 ||
            casoCompensa.getDycpCompensacionDTO().getDyctSaldoIcepDestinoDTO().getDyccConceptoDTO().getIdConcepto() ==
            ConstantesDyCNumerico.VALOR_0) {
            resultaValida = Boolean.FALSE;
        }
         if ( 
            casoCompensa.getDycpCompensacionDTO().getDyctSaldoIcepOrigenDTO().getDyccEjercicioDTO().getIdEjercicio() ==
            ConstantesDyCNumerico.VALOR_0 ||
            casoCompensa.getDycpCompensacionDTO().getDyctSaldoIcepOrigenDTO().getDyccPeriodoDTO().getIdPeriodo() ==
            ConstantesDyCNumerico.VALOR_0 ||
            casoCompensa.getDycpCompensacionDTO().getDyctSaldoIcepOrigenDTO().getDyccConceptoDTO().getIdConcepto() ==
            ConstantesDyCNumerico.VALOR_0) {
            resultaValida = false;
        }
        resultaVali = validarCamposCCD(dycpCompensacionDTO, casoCompensa);
        if (!resultaValida && !resultaVali) {
            this.setTipoMotivo(ConstantesDyCNumerico.VALOR_1);
            resultaValida = Boolean.FALSE;
        }
        return resultaValida;
    }

    public boolean validarCamposCCD(DycpCompensacionDTO dycpCompensacionDTO, CasoCompensacionVO casoCompensa) {
        boolean resultaBloqU = Boolean.TRUE;
        boolean resultaBloqD = Boolean.TRUE;
        if (casoCompensa.getDycpCompensacionDTO().getDyctSaldoIcepDestinoDTO().getDyccEjercicioDTO().getIdEjercicio() ==
            ConstantesDyCNumerico.VALOR_0 ||
            dycpCompensacionDTO.getDyccTipoDeclaraDTO().getIdTipoDeclaracion() == ConstantesDyCNumerico.VALOR_0 ||
            dycpCompensacionDTO.getImporteCompensado().doubleValue() == ConstantesDyCNumerico.VALOR_0) {
            resultaBloqU = false;
        }
        if (dycpCompensacionDTO.getFechaInicioTramite() == null || dycpCompensacionDTO.getFechaPresentaDec() == null) {
            resultaBloqD = false;
        }
        if (!resultaBloqU && !resultaBloqD) {
            resultaValida = false;
        }
        return resultaValida;
    }

    /**
     *
     * @param casoCompensacionVO
     * @return
     */
    @Transactional(readOnly = true)
    public List<DycpCompensacionDTO> buscaCompensacionFAUno(DycpCompensacionDTO casoCompensacionVO) {
        return creaCasoCompenDAO.buscaCompensacion(casoCompensacionVO);
    }

    public DycpCompensacionDTO buscaCompensacionDiferente(DycpCompensacionDTO casoCompensacionVO) {
        DycpCompensacionDTO compensacionDiferente = creaCasoCompenDAO.buscaCompensacionDiferente(casoCompensacionVO);
        if (compensacionDiferente != null) {
            return compensacionDiferente;
        } else {
            return null;
        }
    }

    /***
     * Metodo encargado de validar la fecha de la presentacion de la declaracion con el periodo
     * */
    public boolean validadorPresentvsPeriodo(String periodoInicioFin, Integer idEjercicio,
                                             Date fechaPresentaDeclaracion) {
        SimpleDateFormat formateador = new SimpleDateFormat(ConstantesDyC.DATE_FORMAT);

        String periodoOrigen =
            periodoInicioFin.substring(ConstantesDyCNumerico.VALOR_0, ConstantesDyCNumerico.VALOR_2);
        String ejercicioOrigen = String.valueOf(idEjercicio + "-");
        ejercicioOrigen = ejercicioOrigen + (periodoOrigen.equals("NA") ? "01" : periodoOrigen);

        try {
            Date fechaOrigen = formateador.parse(ejercicioOrigen + "-01");
            if (fechaPresentaDeclaracion.before(fechaOrigen)) {
                return Boolean.FALSE;
            }
        } catch (ParseException e) {
            log.info(e.getMessage());
        }

        return Boolean.TRUE;
    }

    /**
     * Metodo encargado para validar el periodo del concepto
     * @return
     */
    public Boolean validaFechaPeresentacion(String periodoInicioFinD, String periodoInicioFinO, Integer idEjercicioD,
                                            Integer idEjercicioO) {
        SimpleDateFormat formateador = new SimpleDateFormat(ConstantesDyC.DATE_FORMAT);

        String periodoDestino =
            periodoInicioFinD.substring(ConstantesDyCNumerico.VALOR_0, ConstantesDyCNumerico.VALOR_2);
        String ejercicioDestino = String.valueOf(idEjercicioD + "-");
        ejercicioDestino = ejercicioDestino + (periodoDestino.equals("NA") ? "01" : periodoDestino);

        String periodoOrigen =
            periodoInicioFinO.substring(ConstantesDyCNumerico.VALOR_0, ConstantesDyCNumerico.VALOR_2);
        String ejercicioOrigen = String.valueOf(idEjercicioO + "-");
        ejercicioOrigen = ejercicioOrigen + (periodoOrigen.equals("NA") ? "01" : periodoOrigen);

        try {
            Date fechaDestino = formateador.parse(ejercicioDestino + "-01");
            Date fechaOrigen = formateador.parse(ejercicioOrigen + "-01");

            if (fechaOrigen.before(fechaDestino)) {
                log.info("Periodo Destino " + fechaDestino + " | " + "Periodo Origen " + fechaOrigen);
                return Boolean.TRUE;
            }
        } catch (ParseException e) {
            log.info(e.getMessage());
        }
        return Boolean.FALSE;
    }

    /**
     *Metodo que identifica si tiene rol para el tramite
     * @param rol
     * @return 
     **/
    public Integer identificarRolParaTramite(List<PersonaRolDTO> rol) {
        int rolParaTramite = 0;
        for (Iterator itera = rol.iterator(); itera.hasNext(); ) {
            PersonaRolDTO personRol = (PersonaRolDTO)itera.next();

            if (personRol.getClaveRol() == ConstantesDyC.PERSONA_FISICA ||
                personRol.getClaveRol() == ConstantesDyC.PERSONA_MORAL ||
                personRol.getClaveRol() == ConstantesValidaAdministracion.SOC_MERCAN_CONTROL) {
                log.info("Clave de rol puede ser (9-2-300063) " + personRol.getClaveRol());
                rolParaTramite = personRol.getClaveRol();
                break;
            } else {
                log.info("No aplica el rol ");
            }
        }
        return rolParaTramite;
    }

    /**
     * Metodo que identifica la compentencia para el tramite
     * @param administracion
     * @return 
     */
    public Integer identificarCompetenciaParaTramite(Integer administracion) {
        int competenciaParaTramite;
        if (administracion != ConstantesValidaAdministracion.ACFECF || administracion != ConstantesValidaAdministracion.ACFSF ||
            administracion != ConstantesValidaAdministracion.ACFGCD) {
            log.info("La administracion es AGAF " + administracion);
            competenciaParaTramite = ConstantesDyCNumerico.VALOR_13;
        } else {
            log.info("La administracion es AGGC " + administracion);
            competenciaParaTramite = ConstantesDyCNumerico.VALOR_16;
        }
        return competenciaParaTramite;
    }

    /**
     * Identifica el tipo de la declaracion
     * @param tipoDeclaracion
     * @return 
     */
    public DyccTipoDeclaraDTO identificarTipoDeclaracion(Integer tipoDeclaracion) {
        DyccTipoDeclaraDTO dyccTipoDeclaracionDTO = new DyccTipoDeclaraDTO();
        if (tipoDeclaracion != ConstantesDyCNumerico.VALOR_1) {
            dyccTipoDeclaracionDTO.setIdTipoDeclaracion(ConstantesDyCNumerico.VALOR_2);
        } else {
            dyccTipoDeclaracionDTO.setIdTipoDeclaracion(ConstantesDyCNumerico.VALOR_1);
        }
        return dyccTipoDeclaracionDTO;
    }

    public List<DycpCompensacionDTO> buscaICEPDestinoAvisoOCaso(String rfc, DyccConceptoDTO concepto,
                                                                DyccEjercicioDTO ejercicio, DyccPeriodoDTO periodo) {
        List<DyctSaldoIcepDTO> iceps = dyctSaldoIcepDAO.selecXRfcConceptoEjercicioPeriodo(rfc, concepto, ejercicio, periodo);
        if (iceps != null && !iceps.isEmpty()) {
            
            List<DycpCompensacionDTO> listReturn = new ArrayList<DycpCompensacionDTO>();
            for (DyctSaldoIcepDTO icep : iceps) {
                List<DycpCompensacionDTO> listTemp = dycpCompensacionDAO.selecXSaldoicepdestino(icep);
                if(listTemp !=null && !listTemp.isEmpty()){
                    listReturn.addAll(listTemp);
                }
            }
            
            return listReturn;
        } else {
            return null;
        }
    }
    
        /**
     * @see {@link mx.gob.sat.siat.dyc.dao.impl.DyctSaldoIcepDAOImpl.encontrar}
     */
    public List<DycpCompensacionDTO> buscaICEPDestinoAvisoOCaso(final String rfc, final DyccConceptoDTO concepto,
                                                                final DyccEjercicioDTO ejercicio,final DyccPeriodoDTO periodo, final DyccOrigenSaldoDTO dyccOrigenSaldoEjercicioDTO) 
    {
              

        DyctSaldoIcepDTO icep = dyctSaldoIcepDAO.encontrar(rfc, concepto, ejercicio, periodo, dyccOrigenSaldoEjercicioDTO);
        
        if (icep != null) 
        {
            return dycpCompensacionDAO.selecXSaldoicepdestino(icep);
        } 
        else 
        {
            return null;
        }
    }

    public DyctSaldoIcepDTO buscaInfoICEP(Integer idSaldoIcep) {
        return dyctSaldoIcepDAO.encontrar(idSaldoIcep);
    }

    public List<DycpCompensacionDTO> buscaFolioAvisoEnCompensacion(String folioAvisoNormal) {
        return dycpCompensacionDAO.buscaXFolioAviso(folioAvisoNormal);
    }

    public void setDyctSaldoIcepDAO(DyctSaldoIcepDAO dyctSaldoIcepDAO) {
        this.dyctSaldoIcepDAO = dyctSaldoIcepDAO;
    }

    public DyctSaldoIcepDAO getDyctSaldoIcepDAO() {
        return dyctSaldoIcepDAO;
    }
}
