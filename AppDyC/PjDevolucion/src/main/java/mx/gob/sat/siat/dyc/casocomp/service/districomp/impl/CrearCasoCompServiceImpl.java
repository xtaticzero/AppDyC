/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.casocomp.service.districomp.impl;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;

import java.math.BigDecimal;

import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;
import mx.gob.sat.siat.dyc.casocomp.dao.districomp.CreaCasoCompenDAO;

import mx.gob.sat.siat.dyc.casocomp.dto.districomp.CasoCompensacionVO;
import mx.gob.sat.siat.dyc.casocomp.service.districomp.CrearCasoCompService;
import mx.gob.sat.siat.dyc.casocomp.service.districomp.DistribuirCompService;
import mx.gob.sat.siat.dyc.casocomp.service.districomp.RecuperarCasosCompenService;
import mx.gob.sat.siat.dyc.casocomp.util.ValidadorCasoComp;
import mx.gob.sat.siat.dyc.controlsaldos.service.icep.AfectarSaldosXAvisosCompService;
import mx.gob.sat.siat.dyc.controlsaldos.service.icep.AfectarSaldosXCompensacionesService;
import mx.gob.sat.siat.dyc.dao.compensacion.DyctOrigenAvisoDAO;
import mx.gob.sat.siat.dyc.dao.compensacion.IDycpCompensacionDAO;
import mx.gob.sat.siat.dyc.dao.contribuyente.PersonaIDCDAO;
import mx.gob.sat.siat.dyc.dao.declaracion.DyctDeclaracionDAO;
import mx.gob.sat.siat.dyc.dao.secuencia.solicitud.SolNumConsecutivoDAO;
import mx.gob.sat.siat.dyc.dao.tiposerv.IDycpServicioDAO;
import mx.gob.sat.siat.dyc.dao.util.DyctCasoPendienteDAO;
import mx.gob.sat.siat.dyc.domain.caso.DyccMotivoCasoDTO;
import mx.gob.sat.siat.dyc.domain.caso.DyctCasoPendienteDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaIdentificacionDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaRolDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaUbicacionDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.RolesContribuyenteDTO;
import mx.gob.sat.siat.dyc.domain.regsolicitud.TramiteDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpAvisoCompDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctContribuyenteDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaracionDTO;
import mx.gob.sat.siat.dyc.domain.secuencia.DycqNumControlDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaOrigenTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoServicioDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccUsoDecDTO;
import mx.gob.sat.siat.dyc.registro.service.contribuyente.ContribuyenteService;
import mx.gob.sat.siat.dyc.registro.service.contribuyente.impl.ContribuyenteServiceImpl;
import mx.gob.sat.siat.dyc.registro.service.expediente.IntegrarExpedienteService;
import mx.gob.sat.siat.dyc.registro.util.validador.ValidadorRNRegistro;
import mx.gob.sat.siat.dyc.util.common.ParamOutputTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import mx.gob.sat.siat.dyc.util.constante.ConstantesAutomaticaDictaminador;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesXSD;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesAsignarAuDic;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.xml.sax.SAXException;


/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
/**
 * CrearCAsoCompServiceImpl
 *
 * @author [LADP] Luis Alberto Dominguez Palomino
 *
 */
@Service(value = "crearCasoCompService")
public class CrearCasoCompServiceImpl implements CrearCasoCompService {

    @Autowired
    private IDycpServicioDAO daoServicio;

    @Autowired
    private IDycpCompensacionDAO daoCompensacion;

    @Autowired
    private PersonaIDCDAO personaIDCDAO;

    @Autowired(required = true)
    private ValidadorRNRegistro validadorRNRegistro;

    @Autowired
    private ValidadorCasoComp validadorCasoComp;

    @Autowired
    private SolNumConsecutivoDAO solNumConsecutivoDAO;

    @Autowired
    private DyctCasoPendienteDAO dyctCasoPendienteDAO;

    @Autowired
    private RecuperarCasosCompenService recuperarCasosCompenService;

    @Autowired
    private ContribuyenteService contribuyenteService;

    @Autowired
    private IntegrarExpedienteService integrarExpedienteService;

    @Autowired
    private DistribuirCompService serviceDistribuirNuevo;

    @Autowired
    private DyctDeclaracionDAO daoDeclaracion;

    @Autowired
    private AfectarSaldosXCompensacionesService serviceAfectarSaldos;

    @Autowired
    private DyctOrigenAvisoDAO dyctOrigenAvisoDAO;

    @Autowired
    private AfectarSaldosXAvisosCompService afectarSaldosXAvisosCompService;

    @Autowired
    private ContribuyenteServiceImpl serviceContribuyente;

    @Autowired
    private CreaCasoCompenDAO creaCasoCompenDAO;

    private Logger log = Logger.getLogger(CrearCasoCompServiceImpl.class.getName());

    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED,
            rollbackFor = SIATException.class)
    public void creaCasoCompensacion(CasoCompensacionVO declaracion) throws SIATException, JAXBException, SAXException,
            FileNotFoundException,
            ClassNotFoundException, SQLException {

        int registradoprevio = creaCasoCompenDAO.buscaCompensacionExistente(declaracion.getDycpCompensacionDTO().getNumOperacionDec());
        if (registradoprevio == 0) {
            int rolParaTramite = 0;
            log.info("INICIO creaCasoCompensacion");
            DycpServicioDTO servicio = new DycpServicioDTO();
            DyccTipoServicioDTO tipoServicio = new DyccTipoServicioDTO();
            DycaOrigenTramiteDTO dycaOrigenTramiteDTO = new DycaOrigenTramiteDTO();

            DycpCompensacionDTO compensacion = declaracion.getDycpCompensacionDTO();

            tipoServicio.setIdTipoServicio(ConstantesAutomaticaDictaminador.SERVICIO_CASO_DE_COMPENSACION);
            compensacion.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDycaServOrigenDTO().setDyccTipoServicioDTO(tipoServicio);
            log.debug(compensacion.getDyccTipoDeclaraDTO());

            /**
             * compensacion.setDyccTipoDeclaraDTO(BuscadorConstantes.obtenerTipoDeclaracion(compensacion.getDyccTipoDeclaraDTO().getIdTipoDeclaracion()));
             */
            compensacion.setDyccTipoDeclaraDTO(validadorCasoComp.identificarTipoDeclaracion(compensacion.getDyccTipoDeclaraDTO().getIdTipoDeclaracion()));

            Date fech = new Date();
            compensacion.setFechaInicioTramite(new java.sql.Date(fech.getTime()));
            compensacion.setFechaPresentacion(new java.sql.Date(fech.getTime()));

            TramiteDTO datosTramite = obtenerPersona(compensacion.getDycpServicioDTO().getRfcContribuyente());

            if (null != datosTramite) {
                servicio.setRfcContribuyente(compensacion.getDycpServicioDTO().getRfcContribuyente().trim());
                servicio.setBoid(datosTramite.getPersona().getBoId());
            } else {
                log.info("RFC Incorrecto --> " + compensacion.getDycpServicioDTO().getRfcContribuyente().trim());
                insertarCasoPendiente(declaracion, ConstantesDyCNumerico.VALOR_2);
                return;
            }

            rolParaTramite = validadorCasoComp.identificarRolParaTramite(datosTramite.getRolesContribuyente().getRoles());

            int origenDev = 0;
            Map<String, Object> respValidador = validadorRNRegistro.identificarAdministracion(origenDev, datosTramite.getRolesContribuyente(),
                    datosTramite.getPersona().getDomicilio().getClaveAdmonLocal(),
                    ConstantesDyCNumerico.VALOR_0);

            String admin = (String) respValidador.get("claveSirNumControl");
            servicio.setClaveAdm((Integer) respValidador.get("claveAdministracion"));

            int administracion = Integer.parseInt(admin);

            int competenciaParaTramite = validadorCasoComp.identificarCompetenciaParaTramite(administracion);

            //Asigna TipoTramite RN1003
            Integer idTipoTramite
                    = validadorCasoComp.rn1003(compensacion.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDycaServOrigenDTO().getDyccOrigenSaldoDTO().getIdOrigenSaldo(),
                            declaracion.getDycpCompensacionDTO().getDyctSaldoIcepOrigenDTO().getDyccConceptoDTO().getIdConcepto(),
                            declaracion.getDycpCompensacionDTO().getDyctSaldoIcepOrigenDTO().getDyccPeriodoDTO().getIdPeriodo(),
                            rolParaTramite, competenciaParaTramite);

            if (idTipoTramite == ConstantesDyCNumerico.VALOR_0) {
                log.debug("errorlinea209:");
                insertarCasoPendiente(declaracion, ConstantesDyCNumerico.VALOR_1);
                return;
            } else {
                DyccTipoTramiteDTO tipoTramite = new DyccTipoTramiteDTO();

                tipoTramite.setIdTipoTramite(idTipoTramite);
                dycaOrigenTramiteDTO.setDyccTipoTramiteDTO(tipoTramite);
                dycaOrigenTramiteDTO.setDycaServOrigenDTO(compensacion.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDycaServOrigenDTO());
                servicio.setDycaOrigenTramiteDTO(dycaOrigenTramiteDTO);
            }

            //Valor para dycpAvisoComp va vacio
            DycpAvisoCompDTO dycpAvisoCompDTO = new DycpAvisoCompDTO();
            dycpAvisoCompDTO.setFolioAviso("");
            compensacion.setDycpAvisoCompDTO(dycpAvisoCompDTO);

            validaCamposCompensacion(declaracion, compensacion, servicio, datosTramite, admin);
        } else {
            log.error("ESTA DECLARACION YA SE ENCUENTRA REGISTRADA CON NUMERO DE OPERACION " + declaracion.getDycpCompensacionDTO().getNumOperacionDec()
                    + " ID DECLARACION:" + declaracion.getIdDeclaracion() + " rfc:" + declaracion.getDycpCompensacionDTO().getDycpServicioDTO().getRfcContribuyente());
        }
    }

    private TramiteDTO obtenerPersona(String rfcContribuyente) throws SIATException {
        //Obtener clave de administracion
        PersonaDTO persona = new PersonaDTO();
        PersonaUbicacionDTO ubicacion = null;
        RolesContribuyenteDTO roles = null;
        TramiteDTO input = null;
        List<PersonaRolDTO> rol;
        try {
            persona.setRfc(rfcContribuyente.trim());
            log.info("<------------------------------->");
            PersonaDTO person = personaIDCDAO.buscaPersonaPorRFC(persona);
            if (person != null) {
                log.info("RFC Correcto --> " + rfcContribuyente.trim());
                PersonaIdentificacionDTO personaIden = personaIDCDAO.buscaPersonaPorBOID(person);
                ubicacion = personaIDCDAO.buscaUbicacionBOID(person);
                rol = personaIDCDAO.buscaRoles(person);
                roles = serviceContribuyente.rolesContribuyente(rol);
                roles.setRoles(rol);
                //Valores para completar el archivo
                persona.setIdPersona(person.getIdPersona());
                persona.setRfcVigente(person.getRfc());
                persona.setBoId(person.getBoId());
                persona.setRfcSolicitado(person.getRfc());
                persona.setDomicilio(ubicacion);
                persona.setPersonaIdentificacion(personaIden);
                input = new TramiteDTO();
                input.setRolesContribuyente(roles);
                input.setPersona(persona);
            }
        } catch (Exception e) {
            log.error("ERROR AL ENCONTRAR DATOS DEL CONTRIBUYENTE RFC:" + rfcContribuyente, e);
        }
        return input;
    }

    private void validaCamposCompensacion(CasoCompensacionVO declaracion, DycpCompensacionDTO compensacion,
            DycpServicioDTO dycpServicioDTO, TramiteDTO input,
            String admin) throws JAXBException, SAXException, FileNotFoundException,
            ClassNotFoundException, SQLException, SIATException {
        int clavePro = 0;
        //inicia validacion para continuar con el proceso de Insercion del Caso de Comopensación
        boolean camposValidados = validadorCasoComp.validarCamposCC(compensacion, declaracion);

        if (camposValidados) {

            /**
             * Flujo alterno 1
             */
            List<DycpCompensacionDTO> casoCompExistente
                    = validadorCasoComp.buscaCompensacionFAUno(declaracion.getDycpCompensacionDTO());

            if (casoCompExistente != null) {
                /**
                 * Disistir caso de compensacion existente
                 */
                log.debug("existecasos compensaciones --->" + casoCompExistente.size());
                desistirCasoCompensacion(casoCompExistente);
                CasoCompensacionVO declaraCompD
                        = insertaSaldoCompensacion(declaracion, compensacion, dycpServicioDTO, admin);

                clavePro = ConstantesDyCNumerico.VALOR_2;
                this.casoInsertProcesoCC(declaraCompD, input, clavePro);
            } else {

                CasoCompensacionVO declaraComp
                        = insertaSaldoCompensacion(declaracion, compensacion, dycpServicioDTO, admin);

                if (declaraComp.getEstadoTemporal().equals("TEMPORAL")) {
                    /**
                     * Metodo insertar temporales y bloquerlas de DyctCasoPendiente
                     */
                    clavePro = ConstantesDyCNumerico.VALOR_1;
                    this.casoInsertProcesoCC(declaraComp, input, clavePro);
                } else {
                    /**
                     * Metodo inserta casos de compensacion normal
                     */
                    clavePro = ConstantesDyCNumerico.VALOR_2;
                    this.casoInsertProcesoCC(declaraComp, input, clavePro);
                }
            }
        } else if (declaracion.getEstadoTemporal().equals("NORMAL")) {
            log.debug("errorlinea305--:");
            insertarCasoPendiente(declaracion, validadorCasoComp.getTipoMotivo());
        }
    }

    private CasoCompensacionVO insertaSaldoCompensacion(CasoCompensacionVO declaracion,
            DycpCompensacionDTO compensacion,
            DycpServicioDTO dycpServicioDTO, String admin) throws SIATException {
        /**
         * Fecha para numero de control
         */
        List<DyctDeclaracionDTO> listaDeclaraciones = new ArrayList<DyctDeclaracionDTO>();
        Date fechaSistem = new Date();
        String anio = new SimpleDateFormat("yy").format(fechaSistem);
        boolean existeNumControl = Boolean.TRUE;
        DycqNumControlDTO numControl;
        //Cambio para validar que el numero de control no este registrado en bd
        while (existeNumControl) {
            numControl = solNumConsecutivoDAO.getNumConsecutivoCasoCom(admin);
            compensacion.setNumControl(ConstantesAutomaticaDictaminador.SIGLAS_CASO_COM + admin + anio + numControl.getSecuencia());
            log.debug("Buscando num de control: " + compensacion.getNumControl());
            existeNumControl = solNumConsecutivoDAO.existeNumeroControlSolicitud(compensacion.getNumControl());
        }
        //

        dycpServicioDTO.setNumControl(compensacion.getNumControl());

        /**
         * DyctDeclaracion
         */
        DyctDeclaracionDTO dyctDeclaracionDTO = new DyctDeclaracionDTO();
        dyctDeclaracionDTO.setAcreditamiento(BigDecimal.ZERO);
        dyctDeclaracionDTO.setDevueltoCompensado(BigDecimal.ZERO);
        dyctDeclaracionDTO.setDyccTipoDeclaraDTO(declaracion.getDycpCompensacionDTO().getDyccTipoDeclaraDTO());
        DyccUsoDecDTO usoDec = new DyccUsoDecDTO();
        usoDec.setIdUsoDec(ConstantesDyCNumerico.VALOR_1);
        dyctDeclaracionDTO.setDyccUsoDecDTO(usoDec);
        dyctDeclaracionDTO.setEtiquetaSaldo("Caso de Compensacion");
        dyctDeclaracionDTO.setFechaCausacion(declaracion.getFechaCausacion());
        dyctDeclaracionDTO.setFechaPresentacion(declaracion.getFechaPresentacionOrigen());
        Integer idDeclara = solNumConsecutivoDAO.getIdDeclaracion();
        dyctDeclaracionDTO.setIdDeclaracion(idDeclara);
        dyctDeclaracionDTO.setImporte(declaracion.getDycpCompensacionDTO().getImporteCompensado());
        dyctDeclaracionDTO.setNumControl(declaracion.getDycpCompensacionDTO().getNumControl());
        dyctDeclaracionDTO.setNumDocumento(ConstantesDyCNumerico.VALOR_0 + "");
        dyctDeclaracionDTO.setNumOperacion(declaracion.getDycpCompensacionDTO().getNumOperacionDec() + "");
        dyctDeclaracionDTO.setSaldoAfavor(declaracion.getMontoSaldoAFavor());
        declaracion.setDyctDeclaracionDTO(dyctDeclaracionDTO);

        listaDeclaraciones.add(dyctDeclaracionDTO);
        /**
         * Fin dyctDeclaracion
         */

        dycpServicioDTO.setDyctDeclaracionList(listaDeclaraciones);
        compensacion.setDycpServicioDTO(dycpServicioDTO);

        compensacion.getDyctSaldoIcepDestinoDTO().setRfc(dycpServicioDTO.getRfcContribuyente());

        compensacion.getDyctSaldoIcepOrigenDTO().setDyccOrigenSaldoDTO(compensacion.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDycaServOrigenDTO().getDyccOrigenSaldoDTO());
        compensacion.getDyctSaldoIcepOrigenDTO().setRfc(dycpServicioDTO.getRfcContribuyente());

        compensacion.getDycpServicioDTO().setDycpCompensacionDTO(compensacion);
        /**
         * Metodo que ingresa la informacion a saldos
         */
        Map<String, Object> idSaldosIcep = afectarSaldosXAvisosCompService.afectarXRegistro(compensacion);
        compensacion.getDyctSaldoIcepDestinoDTO().setIdSaldoIcep((Integer.parseInt(idSaldosIcep.get("idSaldoIcepDestino").toString())));
        compensacion.getDyctSaldoIcepOrigenDTO().setIdSaldoIcep(Integer.parseInt(idSaldosIcep.get("idSaldoIcepOrigen").toString()));

        declaracion.setDycpCompensacionDTO(compensacion);

        return declaracion;
    }

    private void desistirCasoCompensacion(List<DycpCompensacionDTO> compensacionDiferentes) throws SIATException {
        for (DycpCompensacionDTO compensacionDiferente : compensacionDiferentes) {
            if (compensacionDiferente.getDyccEstadoCompDTO().getIdEstadoComp() != ConstantesDyCNumerico.VALOR_7) {
                log.info("Caso de Compensacion existente con numero de control : "
                        + compensacionDiferente.getDycpServicioDTO().getNumControl());
                serviceAfectarSaldos.afectarXDesistimientoCasoCompensacion(compensacionDiferente.getDycpServicioDTO().getNumControl());
                compensacionDiferente.setDyccEstadoCompDTO(Constantes.EstadosCompensacion.DESISTIDO);
                daoCompensacion.actualizarEstadocomp(compensacionDiferente);
            }
        }
    }

    private void casoInsertProcesoCC(CasoCompensacionVO casoCompensacionVO, TramiteDTO input,
            int clavePro) throws SIATException, JAXBException, SAXException,
            FileNotFoundException, ClassNotFoundException, SQLException {
        switch (clavePro) {
            case (ConstantesDyCNumerico.VALOR_1):
                //Metodo de insertar compensacion en tablas relacionadas
                this.bloquedeInserciones(casoCompensacionVO, input);
                // Metodo finaliza el caso pendiente
                dyctCasoPendienteDAO.actualizar(Integer.parseInt(casoCompensacionVO.getIdDeclaracion()));
                break;
            case (ConstantesDyCNumerico.VALOR_2):
                //Metodo General Insertse
                this.bloquedeInserciones(casoCompensacionVO, input);
                break;
            default:
                log.info("No se encuentra el servicio");
                break;
        }
    }

    @Override
    public void bloquedeInserciones(CasoCompensacionVO casoCompensacionVO, TramiteDTO tramite) throws SIATException,
            JAXBException,
            SAXException,
            FileNotFoundException,
            ClassNotFoundException,
            SQLException {
        casoCompensacionVO.getDycpCompensacionDTO().setDycpServicioAnteriorDTO(
                daoServicio.obtenerUltimoServicioXContribuyente(casoCompensacionVO.getDycpCompensacionDTO().getDycpServicioDTO().getRfcContribuyente(),
                        ConstantesAsignarAuDic.SERVICIO_AVISO_DE_COMPENSACION));

        daoServicio.insertar(casoCompensacionVO.getDycpCompensacionDTO().getDycpServicioDTO());
        boolean regAut = serviceDistribuirNuevo.isAutomatica(casoCompensacionVO.getDycpCompensacionDTO());
        //Validacion que inserta cuando solo es Aviso de Compensacion
        if (casoCompensacionVO.getDycpCompensacionDTO().getDycpServicioDTO().getDycaOrigenTramiteDTO().getDycaServOrigenDTO().getDyccTipoServicioDTO().getIdTipoServicio()
                == ConstantesDyCNumerico.VALOR_4) {
            //Metodo de insertar a dycp_compensacion
            daoCompensacion.insertar(casoCompensacionVO.getDycpCompensacionDTO(), regAut);
            //Metodo de insertar a dyct_origenaviso
            dyctOrigenAvisoDAO.insertar(casoCompensacionVO.getDycpCompensacionDTO().getDycpServicioDTO().getNumControl(),
                    casoCompensacionVO.getDycpCompensacionDTO().getDyctOrigenAvisoList().get(0));
        } else {
            //Metodo de insertar a dycp_compensacion
            daoCompensacion.insertar(casoCompensacionVO.getDycpCompensacionDTO(), regAut);
        }

        if (casoCompensacionVO.getDyctDeclaracionComplementaria() != null) {
            daoDeclaracion.createDeclaracion(casoCompensacionVO.getDyctDeclaracionDTO());
            daoDeclaracion.createDeclaracion(casoCompensacionVO.getDyctDeclaracionComplementaria());
        } else {
            daoDeclaracion.createDeclaracion(casoCompensacionVO.getDyctDeclaracionDTO());
        }

        //Metodo que integra el expediente del contribuyente
        this.insertaCCyAContribuyente(casoCompensacionVO.getDycpCompensacionDTO().getDycpServicioDTO().getNumControl(),
                tramite);
        //Integra Expediente
        integrarExpedienteService.validaNumControl(casoCompensacionVO.getDycpCompensacionDTO(),
                ConstantesDyCNumerico.VALOR_3);
        //Distribuir Casos de Compensacion
        //TODO: Esta validacion se genero, ya que la claveadm en algun momento por un bug se cambiada y se almacenaba con una equivocada
        Integer claveAdm = Integer.valueOf(casoCompensacionVO.getDycpCompensacionDTO().getDycpServicioDTO().getNumControl().substring(ConstantesDyCNumerico.VALOR_2, ConstantesDyCNumerico.VALOR_4));
        if (!claveAdm.equals(casoCompensacionVO.getDycpCompensacionDTO().getDycpServicioDTO().getClaveAdm())) {
            log.info("distribuirCompensacion:: Servicio con numero de control " + casoCompensacionVO.getDycpCompensacionDTO().getDycpServicioDTO().getNumControl()
                    + ". La clave adm esta mal: " + casoCompensacionVO.getDycpCompensacionDTO().getDycpServicioDTO().getClaveAdm() + " debe ser " + claveAdm);
            casoCompensacionVO.getDycpCompensacionDTO().getDycpServicioDTO().setClaveAdm(claveAdm);
        }
        serviceDistribuirNuevo.distribuirCompensacion(casoCompensacionVO.getDycpCompensacionDTO());
    }

    @Override
    public ParamOutputTO<String> insertaCCyAContribuyente(String numControl, TramiteDTO persona) throws SIATException,
            JAXBException,
            SAXException {
        DyctContribuyenteDTO contribuyente = new DyctContribuyenteDTO();

        try {

            contribuyente.setNumControl(numControl);
            contribuyente.setRolPf(persona.getRolesContribuyente().isPersonaFisica() ? 1 : 0);
            contribuyente.setRolPm(persona.getRolesContribuyente().isPersonaMoral() ? 1 : 0);
            contribuyente.setRolDictaminado(persona.getRolesContribuyente().isDictaminado() ? 1 : 0);
            contribuyente.setRolGranContrib(persona.getRolesContribuyente().isGranContribuyente() ? 1 : 0);
            contribuyente.setRolSociedadControl(persona.getRolesContribuyente().isSociedadControladora() ? 1 : 0);

            contribuyente.setDatosContribuyente(new ByteArrayInputStream(persona.getPersona().generateXML(ConstantesXSD.CONTRIBUYENTE_XSD)));

            if (contribuyente.getDatosContribuyente() != null) {
                contribuyenteService.createContribuyenteDYCT(SQLOracleDyC.CREATECONTRIBUYENTE.toString(), contribuyente);
            } else {
                log.error("No se pudo almacenar el expediente");
            }
        } catch (Exception siatE) {
            log.error(siatE.getMessage());
            throw new SIATException(siatE);
        }

        return new ParamOutputTO<String>(numControl);
    }

    private void insertarCasoPendiente(CasoCompensacionVO compensacion, Integer motivo) throws SIATException {
        DyctCasoPendienteDTO dyctCasoPenDTO = new DyctCasoPendienteDTO();
        DyccMotivoCasoDTO dyccMotivoCasoDTO = new DyccMotivoCasoDTO();

        dyctCasoPenDTO.setIdDeclaracion(Integer.parseInt(compensacion.getIdDeclaracion()));

        dyctCasoPenDTO.setIdImpuesto(compensacion.getDycpCompensacionDTO().getDyctSaldoIcepDestinoDTO().getDyccConceptoDTO().getDyccImpuestoDTO().getIdImpuesto());
        dyctCasoPenDTO.setIdConcepto(compensacion.getDycpCompensacionDTO().getDyctSaldoIcepDestinoDTO().getDyccConceptoDTO().getIdConcepto());
        dyctCasoPenDTO.setIdEjercicio(compensacion.getDycpCompensacionDTO().getDyctSaldoIcepDestinoDTO().getDyccEjercicioDTO().getIdEjercicio());
        dyctCasoPenDTO.setIdPeriodo(compensacion.getDycpCompensacionDTO().getDyctSaldoIcepDestinoDTO().getDyccPeriodoDTO().getIdPeriodo());

        dyccMotivoCasoDTO.setIdMotivo(motivo);
        dyctCasoPenDTO.setDyccMotivoCasoDTO(dyccMotivoCasoDTO);
        String numOperacion = String.valueOf(compensacion.getDycpCompensacionDTO().getNumOperacionDec());
        dyctCasoPenDTO.setNumOperacion(Integer.parseInt(numOperacion));

        recuperarCasosCompenService.validaInsertarCasoPen(dyctCasoPenDTO);
    }

}
