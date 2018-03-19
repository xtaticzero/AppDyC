/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.registro.service.expediente.impl;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;

import java.math.BigDecimal;

import java.sql.SQLException;

import javax.xml.bind.JAXBException;

import mx.gob.sat.mat.dyc.integrarexpediente.service.IcepService;
import mx.gob.sat.siat.dyc.dao.contribuyente.PersonaIDCDAO;
import mx.gob.sat.siat.dyc.dao.documento.DyctExpedienteDAO;
import mx.gob.sat.siat.dyc.dao.expediente.IntegrarExpedienteDAO;
import mx.gob.sat.siat.dyc.dao.tipotramite.DyccTipoTramiteDAO;
import mx.gob.sat.siat.dyc.domain.altex.SpConsultarAltexDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaDTO;
import mx.gob.sat.siat.dyc.domain.diot.DiotDTO;
import mx.gob.sat.siat.dyc.domain.icep.IcepSioUrucple1DTO;
import mx.gob.sat.siat.dyc.domain.icep.IcepUrdcFatDTO;
import mx.gob.sat.siat.dyc.domain.icep.IcepUrdcsilDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctExpedienteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaOrigenTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.registro.service.expediente.IntegrarExpedienteService;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesAsignarAuDic;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC2;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.xml.sax.SAXException;


/**
 * Servicio para la inserciòn de un expediente.
 * @author Luis Fernando Barrios Quiroz [ LuFerMX ]
 * @modificacion ISCC se codifica logica de negocio de integracion 29-08-2013.
 * @modificacion J. Isaac Carbajal Bernal se acompletan pendientes
 * @see LuFerMX Modificacion borra
 * @ Autowired.
 * private IcepDAO icepDAO.
 */
@Service("integrarExpedienteService")
public class IntegrarExpedienteServiceImpl implements IntegrarExpedienteService {

    public IntegrarExpedienteServiceImpl() {
        super();
    }

    private Logger log = Logger.getLogger(IntegrarExpedienteServiceImpl.class.getName());

    @Autowired
    private IntegrarExpedienteDAO integrarExpedienteDAO;

    @Autowired
    private IcepService icepService;

    @Autowired
    private DyctExpedienteDAO dyctExpedienteDAO;

    @Autowired
    private PersonaIDCDAO personaIDCDAO;

    @Autowired
    private DyccTipoTramiteDAO tipoTramiteDAO;

    @Override
    public void validaNumControl(Object expedienteDTO, int tipoServicio) throws FileNotFoundException,
                                                                                ClassNotFoundException, SQLException,
                                                                                JAXBException, SAXException {

        DycpSolicitudDTO solicitudDTO;
        switch (tipoServicio) {

        case (ConstantesAsignarAuDic.SERVICIO_SOLICITUD_DEVOLUCION):
            solicitudDTO = (DycpSolicitudDTO)expedienteDTO;

            if (dyctExpedienteDAO.encontrar(solicitudDTO.getDycpServicioDTO().getNumControl()) != 0) {
                log.info("Si existe vamos a actualizarlo");
                integrarExpediente(solicitudDTO);
            } else {
                log.info("No existe vamos a crearlo");
                /**crearExpediente(solicitudDTO);*/
            }
            break;
        case (ConstantesAsignarAuDic.SERVICIO_DEVOLUCION_AUTOMATICA):
            log.debug("Aqui va el cast de devolucion automatica DTO");
            break;
        case (ConstantesAsignarAuDic.SERVICIO_CASO_DE_COMPENSACION):
            DycpCompensacionDTO compensacionDTO = (DycpCompensacionDTO)expedienteDTO;

            if (dyctExpedienteDAO.encontrar(compensacionDTO.getNumControl()) != 0) {
                log.info("Si existe vamos a actualizarlo");
                actualizaExpedienteComp();
            } else {
                log.info("No existe vamos a crearlo");
                crearExpedienteComp(compensacionDTO);
            }
            break;
        case (ConstantesAsignarAuDic.SERVICIO_AVISO_DE_COMPENSACION):
            log.debug("Aqui va el cast de devolucion automatica DTO");
            break;
        default:
            break;
        }
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    private void crearExpedienteComp(DycpCompensacionDTO compensacionDTO) throws FileNotFoundException, ClassNotFoundException, SQLException {


        DyctExpedienteDTO expediente = new DyctExpedienteDTO();

        expediente.setServicioDTO(compensacionDTO.getDycpServicioDTO());

        integrarExpedienteDAO.insertarExpediente(expediente);


        /** DyctOrigenSafCcDTO  origenSafCc = dyctOrigenSafCcDAO.encontrar(compensacionDTO.getServicio().getNumControl());
            Integer idTipoTramite = origenSafCc.getOrigenTramite().getTipoTramite().getIdTipoTramite();
            if(idTipoTramite == ConstantesDyC1Numerico.VALOR_201){

                //  Almacena datos del DIOT

                // Revisar con Adriana Najera la consulta que se utilizara en lugar del store procedure ya que los casos
                   no poseen el numero de operaciòn de la DIOT, Javier Chaparro sugiere el RFC y el periodo y ejercicio.
                }
            */

    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void actualizaExpedienteComp() throws FileNotFoundException, ClassNotFoundException, SQLException {

    }

    /**
     * Metodo de servicio para integrar el expediente asociado a la solicitud (un solo expediente).
     * @throws FileNotFoundException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void integrarExpediente(DycpSolicitudDTO solicitud) throws FileNotFoundException, ClassNotFoundException,
                                                                      SQLException, JAXBException, SAXException {
    }

    /**
     * TODO
     * @throws FileNotFoundException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public void crearExpediente(DycpSolicitudDTO solicitud, PersonaDTO personaRetenedor, DiotDTO diot,
                                SpConsultarAltexDTO altex) throws FileNotFoundException, ClassNotFoundException,
                                                                  SQLException, JAXBException, SAXException {

        //(3.1) Datos de la solicitud fecha y numero de control DYCP_SOLICITUD
        //(3.2) Datos deL CONTRIBUYENTE DYCT_CONTRIBUYENTE
        //(3.3) Aun no definido (perfil de riezgo)
        //(3.4) Verificacion de inconsistencias persistidas en DYCC_INCONSISTENCIAS
        //(3.6.1) Origen del saldo : DYCC_ORIGENSALDO
        //(3.6.1) SubOrigen del saldo : DYCC_SUBORIGSALDO
        //(3.6.1) Subtipo tramite : DYCC_SUBTRAMITE
        //(3.6.1) tipo Periodo : DYCC_TIPOPERIODO
        //(3.6.1) Nombre del Banco : DYCC_INSTCREDITO
        //(3.6.1) Numero de cuenta CLABE : DYCT_CUENTABANCO

        DyctExpedienteDTO expedienteDto = new DyctExpedienteDTO();
        DycpServicioDTO dycpServicioDTO = new DycpServicioDTO();
        DycaOrigenTramiteDTO dycaOrigenTramiteDTO = new DycaOrigenTramiteDTO();

        /**
         * Complementando el expediente
         */
        expedienteDto.setAplicaFacilidad(null !=
                                         solicitud.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getDescripcion() ?
                                         Integer.valueOf(solicitud.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getDescripcion()) :
                                         null);
        expedienteDto.setInfoAgropecuario(null !=
                                          solicitud.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getClaveContable() ?
                                          Integer.valueOf(solicitud.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getClaveContable()) :
                                          null);
        expedienteDto.setEstadoPadron(solicitud.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getResolAutomatica());
        expedienteDto.setManifiestaEdocta(solicitud.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getPlazo());
        expedienteDto.setProtesta(solicitud.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getPuntosAsignacion());
        expedienteDto.setEstadoActual(solicitud.getDyctSaldoIcepDTO().getBloqueado());

        try {
            DyccTipoTramiteDTO tipoTramite;

            tipoTramite =
                    tipoTramiteDAO.encontrar(solicitud.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getIdTipoTramite());


            dycaOrigenTramiteDTO.setDyccTipoTramiteDTO(tipoTramite);
        } catch (SIATException e) {
            log.error(e.getMessage());
        }
        
        dycpServicioDTO.setDycaOrigenTramiteDTO(dycaOrigenTramiteDTO);
        solicitud.setDycpServicioDTO(dycpServicioDTO);


        IcepSioUrucple1DTO icepSioUrucple1DTO = new IcepSioUrucple1DTO();
        IcepUrdcFatDTO icepUrdcFatDTO = new IcepUrdcFatDTO();
        IcepUrdcsilDTO icepUrdcsilDTO = new IcepUrdcsilDTO();


        /**
         * 3.2.1
         * en el objeto personaDTORetenedor
         */

        if (personaRetenedor != null) {
            /**
             * Consultar el Store de Identificacion y Domicilio utilisando el BoID de personaDTORetenedor
             */
            try {
                personaRetenedor.setPersonaIdentificacion(personaIDCDAO.buscaPersonaPorBOID(personaRetenedor));

                personaRetenedor.setDomicilio(personaIDCDAO.buscaUbicacionBOID(personaRetenedor));
                expedienteDto.setDatosRetenedorN(new ByteArrayInputStream(personaRetenedor.generateXML(ConstantesDyC2.CONTRIBUYENTE_XSD)));

            } catch (SIATException e) {
                log.error(e.getMessage());
            }

            /**
             * 3.6.2.2
             * IcepService
             * ObtenerIcep
             * Agregar SaldoRetenedorN en la tabla Expediente
             */

            /** Store 1 Informix */

            icepUrdcFatDTO = icepService.obtieneIcepUrdFat(icepUrdcFatDTO.retrieveDTOFromSolicitud(solicitud));
            if (null != icepUrdcFatDTO.getSaldoFavor() &&
                !icepUrdcFatDTO.getSaldoFavor().equals(ConstantesDyC2.NULLVALUE)) {
                expedienteDto.setSaldoRetenedorN(new BigDecimal(icepUrdcFatDTO.getSaldoFavor()));
            } else {
                if (solicitud.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getDyccConceptoDTO().getIdConcepto() ==
                    ConstantesDyC2.CONCEPTO_404) {
                    /** Store 2 Oracle */
                    icepSioUrucple1DTO =
                            icepService.obtieneIcepUrdcsil1(icepSioUrucple1DTO.retrieveDTOFromSolicitud(solicitud));

                    if (null != icepUrdcsilDTO.getSaldoFavor() &&
                        !icepUrdcsilDTO.getSaldoFavor().equals(ConstantesDyC2.NULLVALUE)) {

                        expedienteDto.setSaldoRetenedorN(new BigDecimal(icepUrdcsilDTO.getSaldoFavor()));

                    } else {
                        /** Store 3 Informix */
                        icepSioUrucple1DTO =
                                icepService.obtieneIcepSioUrucple1DTO(icepSioUrucple1DTO.retrieveDTOFromSolicitud(solicitud));
                        if (null != icepSioUrucple1DTO.getSaldoFavor() &&
                            !icepSioUrucple1DTO.getSaldoFavor().equals(ConstantesDyC2.NULLVALUE)) {
                            expedienteDto.setSaldoRetenedorN(new BigDecimal(icepSioUrucple1DTO.getSaldoFavor()));
                        }
                    }
                } else {
                    /** Store 3 Inforlmix */
                    icepSioUrucple1DTO =
                            icepService.obtieneIcepSioUrucple1DTO(icepSioUrucple1DTO.retrieveDTOFromSolicitud(solicitud));
                    if (!icepSioUrucple1DTO.getSaldoFavor().equals(ConstantesDyC2.NULLVALUE)) {
                        expedienteDto.setSaldoRetenedorN(new BigDecimal(icepSioUrucple1DTO.getSaldoFavor()));
                    }

                }

            }
        }
        /**
         * DIOT
         */
        if (diot != null) {
            expedienteDto.setDatosDIOT(new ByteArrayInputStream(diot.generateXML(ConstantesDyC2.DIOT_XSD)));
        }
        /**
         * Altex
         */
        if (altex != null) {
            expedienteDto.setDatosDIOT(new ByteArrayInputStream(altex.generateXML(ConstantesDyC2.ALTEX_XSD)));
        }
        DycpServicioDTO servicio = new DycpServicioDTO();
        servicio.setNumControl(solicitud.getNumControl());
        expedienteDto.setServicioDTO(servicio);

        expedienteDto.setSaldoIcep(BigDecimal.ZERO);

        integrarExpedienteDAO.insertarExpediente(expedienteDto);
    }

    /**
     * TODO
     * @throws FileNotFoundException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Transactional(readOnly = false)
    public void actualizarExpediente(DycpSolicitudDTO solicitud) throws FileNotFoundException, ClassNotFoundException,
                                                                        SQLException {

    }

}
