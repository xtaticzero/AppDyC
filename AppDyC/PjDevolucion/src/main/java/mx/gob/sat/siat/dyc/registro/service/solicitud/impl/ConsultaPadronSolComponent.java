/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.registro.service.solicitud.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Map;

import mx.gob.sat.siat.dyc.admcat.dao.contribuyente.DyccRFCSectorAgroDAO;
import mx.gob.sat.siat.dyc.controlsaldos.service.icep.ConsultaSaldoIcepService;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccImpuestoDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.registro.service.empresascertificadas.EmpresaCertificadaService;
import mx.gob.sat.siat.dyc.registro.service.solicitud.RegistraSolDevService;
import mx.gob.sat.siat.dyc.servicio.dto.devoluciones.ValidaDatosSolicitud;
import mx.gob.sat.siat.dyc.servicio.dto.immex.ImmexDTO;
import mx.gob.sat.siat.dyc.servicio.service.immex.ImmexService;
import mx.gob.sat.siat.dyc.util.BuscadorConstantes;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante1.ConstanteValidacionRNFDC10;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC2;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;


/**
 * Consulta los diversos Padrones para el Registro de la Solicitud(IMMEX,Agroprcuario,Retenedor,EmpCertificadas,SaldoICEP)
 * @author JEFG
 * @since 1.0 , 17/02/15
 */
@Component(value = "consultaPadronSolComponent")
public class ConsultaPadronSolComponent {

    private static final Logger LOG = Logger.getLogger(ConsultaPadronSolComponent.class);


    @Autowired
    private ImmexService immexService;
    @Autowired
    private DyccRFCSectorAgroDAO dyccRFCSectorAgroDAO;
    @Autowired
    private EmpresaCertificadaService empCertificadas;
    @Autowired
    private RegistraSolDevService registraSolDevService;
    @Autowired
    private ConsultaSaldoIcepService saldoIcepService;


    public ConsultaPadronSolComponent() {
        super();
    }


    public boolean consultaIMMEX(String rfc, int idTramite, String tipoPeriodo, int ejercicio) throws SIATException {
        ImmexDTO immex = new ImmexDTO();
        immex.setTxtrfc(rfc);
        immex.setTxtusr(ConstantesDyC.USR_STORED_PROCEDURES);
        try {
            immex = this.immexService.obtienenImmexSP(immex);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
        if (null == immex.getVdBandera()) {
            /**  showMessageIMMEX(); */
            return Boolean.TRUE;
        }

        String fechaInicio = null;
        String fechaFin = null;
        DateFormat parse = new SimpleDateFormat(ConstanteValidacionRNFDC10.DATE_FORMAT);
        Date fechInicio = new Date();
        Date fechFin = new Date();
        Date fechPeriodo = new Date();

        if (ValidaDatosSolicitud.consultaCertificadasIMMEX(idTramite, immex.getVdBandera())) {
            String selectPeriodo = tipoPeriodo.substring(ConstantesDyCNumerico.VALOR_2);
            String periodo =
                String.valueOf(ejercicio + ValidaDatosSolicitud.GUION + selectPeriodo + ValidaDatosSolicitud.INICIO).trim();

            fechaInicio = immex.getVdInivig().replace(ConstanteValidacionRNFDC10.UTC, ConstantesDyC2.CADENA_VACIA);
            fechaFin = immex.getVdFinvig().replace(ConstanteValidacionRNFDC10.UTC, ConstantesDyC2.CADENA_VACIA);
            try {
                fechInicio = parse.parse(fechaInicio);
                fechFin = parse.parse(fechaFin);
                fechPeriodo = parse.parse(periodo);
            } catch (ParseException e) {
                LOG.error(e.getMessage());
            }
            if (fechPeriodo.getTime() >= fechInicio.getTime() && fechPeriodo.getTime() <= fechFin.getTime()) {
                return Boolean.FALSE;
            } else {
                /** showMessageIMMEX(); */
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    public DyctSaldoIcepDTO consultaDyctSaldoIcep(Map<String, Object> datos) throws SIATException {
        DyctSaldoIcepDTO dyctSaldoIcepDTO = new DyctSaldoIcepDTO();
        DyccConceptoDTO dyccConceptoDTO = new DyccConceptoDTO();
        DyccImpuestoDTO dyccImpuestoDTO = new DyccImpuestoDTO();
        DyccEjercicioDTO dyccEjercicioDTO = new DyccEjercicioDTO();
        DyccPeriodoDTO dyccPeriodoDTO = new DyccPeriodoDTO();
        
        dyccConceptoDTO.setIdConcepto((Integer)datos.get("concepto"));
        dyccImpuestoDTO.setIdImpuesto((Integer)datos.get("impuesto"));
        dyccConceptoDTO.setDyccImpuestoDTO(dyccImpuestoDTO);
        dyccEjercicioDTO.setIdEjercicio((Integer)datos.get("ejercicio"));
        dyccPeriodoDTO.setIdPeriodo((Integer)datos.get("periodo"));

        dyctSaldoIcepDTO.setRfc(datos.get("rfc").toString());
        dyctSaldoIcepDTO.setDyccConceptoDTO(dyccConceptoDTO);
        dyctSaldoIcepDTO.setDyccEjercicioDTO(dyccEjercicioDTO);
        dyctSaldoIcepDTO.setDyccPeriodoDTO(dyccPeriodoDTO);
        dyctSaldoIcepDTO.setDyccOrigenSaldoDTO(BuscadorConstantes.obtenerOrigenSaldo((Integer)datos.get("origen")));
        return saldoIcepService.consultaSaldoICEP(dyctSaldoIcepDTO);
    }

    public PersonaDTO consultaRetenedor(String rfc) throws SIATException {
        return registraSolDevService.validaRFCRetenedor(rfc).getOutput();
    }


    public String consultaEmpresaCertificada(int tramite, String rfc) throws SIATException {
        return empCertificadas.isEmpresaCertificada(rfc, tramite).getOutput();
    }

    public Integer consultaSectorAgro(String rfc) throws SIATException {
        return dyccRFCSectorAgroDAO.findRfcSectorAgro(rfc);
    }

}


