/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.mat.dyc.automaticasiva.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBException;

import mx.gob.sat.mat.dyc.automaticasiva.service.CrearCasoAutomaticasIvaService;
import mx.gob.sat.mat.rfcampl.client.IdCInterno;
import mx.gob.sat.mat.rfcampl.client.Identificacion;
import mx.gob.sat.mat.rfcampl.client.Ubicacion;
import mx.gob.sat.siat.dyc.automaticasiva.domain.DycAutomaticasIvaInsertarServicioTO;
import mx.gob.sat.siat.dyc.automaticasiva.domain.DycAutomaticasIvaInsertarSolicitudTO;
import mx.gob.sat.siat.dyc.automaticasiva.util.constante.DycConstantesAutomaticasIva;
import mx.gob.sat.siat.dyc.automaticasiva.util.constante.EDycAutomaticasIvaEstadoCasoDevolucion;
import mx.gob.sat.siat.dyc.dao.archivo.DyctArchivoDAO;
import mx.gob.sat.siat.dyc.dao.declaracion.impl.DyctDeclaracionDAOImpl;
import mx.gob.sat.siat.dyc.dao.regsolicitud.DycpSolicitudDAO;
import mx.gob.sat.siat.dyc.dao.tiposerv.IDycpServicioDAO;
import mx.gob.sat.siat.dyc.domain.archivo.DyctArchivoDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaIdentificacionDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaUbicacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccEstadoSolDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctContribuyenteDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaracionDTO;
import mx.gob.sat.siat.dyc.domain.suborigensal.DyccActividadDTO;
import mx.gob.sat.siat.dyc.domain.suborigensal.DyccSubOrigSaldoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaOrigenTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaServOrigenDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoDeclaraDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccUsoDecDTO;
import mx.gob.sat.siat.dyc.generico.util.exportador.informe.Marshal;
import mx.gob.sat.siat.dyc.registro.dao.contribuyente.ContribuyenteDAO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCURL;
import mx.gob.sat.siat.dyc.util.constante.EDycServiceCodigoError;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC3;
import mx.gob.sat.siat.dyc.util.excepcion.DycDaoExcepcion;
import mx.gob.sat.siat.dyc.util.excepcion.DycServiceExcepcion;
import mx.gob.sat.siat.dyc.util.recurso.DycFechaUtil;
import mx.gob.sat.siat.dyc.vo.DycLogEstadoVariable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author GAER8674
 */
@Service
public class CrearCasoAutomaticasIvaServiceTestImpl implements CrearCasoAutomaticasIvaService{
    @Autowired
    private IDycpServicioDAO dycpServicioDAO;
    
    @Autowired
    private DycpSolicitudDAO dycpSolicitudDAO;
    
    @Autowired
    private ContribuyenteDAO contribuyenteDAO;

    @Autowired
    private DyctDeclaracionDAOImpl dyctDeclaracionDAO;

    @Autowired
    private DyctArchivoDAO dyctArchivoDAO;
    
    
    private void insertarServicio(DycAutomaticasIvaInsertarServicioTO servicioTO) throws DycServiceExcepcion{
        List<DycLogEstadoVariable> estadoVariables = new ArrayList<DycLogEstadoVariable>();
        estadoVariables.add( new DycLogEstadoVariable("numeroControl",     servicioTO.getNumeroControl()) );
        estadoVariables.add( new DycLogEstadoVariable("idTipoServicio", ""+servicioTO.getIdTipoServicio()) );
        estadoVariables.add( new DycLogEstadoVariable("idOrigenSaldo",  ""+servicioTO.getIdOrigenSaldo()) );
        estadoVariables.add( new DycLogEstadoVariable("idTipoTramite",  ""+servicioTO.getIdTipoTramite()) );
        estadoVariables.add( new DycLogEstadoVariable("rfc",               servicioTO.getRfc()) );
        estadoVariables.add( new DycLogEstadoVariable("claveSir",       ""+servicioTO.getClaveSir()) );
        estadoVariables.add( new DycLogEstadoVariable("boid",              servicioTO.getBoid()) );
        
        try {
            DycpServicioDTO servicio = new DycpServicioDTO();
            DycaOrigenTramiteDTO origenTramite = new DycaOrigenTramiteDTO();
            DycaServOrigenDTO servOrigen = new DycaServOrigenDTO();

            servicio.setNumControl(      servicioTO.getNumeroControl());
            servOrigen.getDyccTipoServicioDTO()
                    .setIdTipoServicio(  servicioTO.getIdTipoServicio());
            servicio.getDyccDictaminadorDTO()
                    .setNumEmpleado(     servicioTO.getNumEmpleadoDict());
            servOrigen.getDyccOrigenSaldoDTO()
                    .setIdOrigenSaldo(   servicioTO.getIdOrigenSaldo());
            origenTramite.getDyccTipoTramiteDTO()
                    .setIdTipoTramite(   servicioTO.getIdTipoTramite());
            servicio.setRfcContribuyente(servicioTO.getRfc());
            servicio.setClaveAdm(        servicioTO.getClaveSir());
            servicio.setBoid(            servicioTO.getBoid());

            origenTramite.setDycaServOrigenDTO(servOrigen);
            servicio.setDycaOrigenTramiteDTO(origenTramite);

            dycpServicioDAO.insertar(servicio);
        } catch (SIATException ex) {
            throw new DycServiceExcepcion(EDycServiceCodigoError.BD_DYC_SERVICIO_INSERT_GENERAL, estadoVariables, ex);
        }
    }

    @Override
    public void insertarServicioAutomaticasIva(String numeroControl, String rfc, Integer claveSir, String boid) throws DycServiceExcepcion{
        DycAutomaticasIvaInsertarServicioTO servicioTO = new DycAutomaticasIvaInsertarServicioTO();
        servicioTO.setNumeroControl(numeroControl);
        servicioTO.setIdTipoServicio(DycConstantesAutomaticasIva.DYCPSERVICIO_TIPOSERVICIO_DEVAUTOMATICA);
        servicioTO.setIdOrigenSaldo(DycConstantesAutomaticasIva.DYCPSERVICIO_ORIGENSALDO_SALDOAFAVOR);
        servicioTO.setIdTipoTramite(DycConstantesAutomaticasIva.CASODEVOLUCION_TIPOTRAMITE_IDEVAUTOMATICAIVA);
        servicioTO.setRfc(rfc);
        servicioTO.setClaveSir(claveSir);
        servicioTO.setBoid(boid);
        
        insertarServicio(servicioTO);
    }
    
    @Override
    public void insertarSolicitud(DycAutomaticasIvaInsertarSolicitudTO solicitudTO) throws DycServiceExcepcion{
        List<DycLogEstadoVariable> estadoVariables = new ArrayList<DycLogEstadoVariable>();
        estadoVariables.add( new DycLogEstadoVariable("numeroControl",     solicitudTO.getNumeroControl()) );
        estadoVariables.add( new DycLogEstadoVariable("fechaInicioTramite",solicitudTO.getFechaInicioTramite().toString()) );
        estadoVariables.add( new DycLogEstadoVariable("importeSolicitado", solicitudTO.getImporteSolicitado().toString()) );
        estadoVariables.add( new DycLogEstadoVariable("retenedorRfc",      solicitudTO.getRetenedorRfc()) );
        
        try {
            DycpSolicitudDTO dycpSolicitudDTO = new DycpSolicitudDTO();
            dycpSolicitudDTO.setDyccEstadoSolDTO( new DyccEstadoSolDTO() );
            DyccActividadDTO dyccActividadDTO = new DyccActividadDTO();
            dyccActividadDTO.setDyccSubOrigSaldoDTO( new DyccSubOrigSaldoDTO() );
            dycpSolicitudDTO.setDyccActividadDTO( dyccActividadDTO );

            dycpSolicitudDTO.setNumControl(        solicitudTO.getNumeroControl());
            dycpSolicitudDTO.setFechaInicioTramite(solicitudTO.getFechaInicioTramite());
            dycpSolicitudDTO.setImporteSolicitado( solicitudTO.getImporteSolicitado());
            dycpSolicitudDTO.setInfoAdicional(     solicitudTO.getInfoAdicional());
            dycpSolicitudDTO.setDiotNumOperacion(  solicitudTO.getDiotNumOperacion());
            dycpSolicitudDTO.setDiotFechaPresenta( solicitudTO.getDiotFechaPresenta());
            dycpSolicitudDTO.setRetenedorRfc(      solicitudTO.getRetenedorRfc());
            dycpSolicitudDTO.setAltexConstancia(   solicitudTO.getAltexConstancia());
            dycpSolicitudDTO.setEsCertificado(     solicitudTO.getEsCertificado());
            dycpSolicitudDTO.getDyccEstadoSolDTO().
                    setIdEstadoSolicitud(          solicitudTO.getIdEstadoSolicitud());
            dycpSolicitudDTO.getDyccActividadDTO().getDyccSubOrigSaldoDTO()
                    .setIdSuborigenSaldo(          solicitudTO.getIdSuborigenSaldo());
            
            Integer idSubTipoTramite = solicitudTO.getIdSubTipoTramite() != 0 
                    ? solicitudTO.getIdSubTipoTramite() 
                    : null;

            dycpSolicitudDTO.getDyccSubtramiteDTO()
                    .setIdSubTipoTramite(          idSubTipoTramite);
            dycpSolicitudDTO.getDyccActividadDTO()
                    .setIdActividad(               solicitudTO.getIdActividad());
            dycpSolicitudDTO.getDyctSaldoIcepDTO()
                    .setIdSaldoIcep(               solicitudTO.getIdSaldoIcep());
            dycpSolicitudDTO.setCadenaOriginal(    solicitudTO.getCadenaOriginal());
            dycpSolicitudDTO.setSelloDigital(      solicitudTO.getSelloDigital());
            dycpSolicitudDTO.setFechaPresentacion( solicitudTO.getFechaPresentacion());
            
            dycpSolicitudDAO.insertaSolicitud(dycpSolicitudDTO);
        } catch (DycDaoExcepcion ex) {
            throw new DycServiceExcepcion(EDycServiceCodigoError.BD_DYC_SOLICITUD_INSERT_GENERAL, estadoVariables, ex);
        }
    }
    
    /**
     * <p>Esto para desvincular la estructura XML de la respuesta del servicio RFC Ampliado
     * con la estructura XML que utiliza DyC y de la cual dependen algunas consultas.</p>
     * @param rfcAmpliado
     * @return 
     */
    private PersonaDTO remapearRFCAmpliadoResponse(IdCInterno rfcAmpliado){
        PersonaDTO personaDTO = new PersonaDTO();
        PersonaIdentificacionDTO identificacionDTO = new PersonaIdentificacionDTO();
        PersonaUbicacionDTO ubicacionDTO = new PersonaUbicacionDTO();
        
        personaDTO.setRfc(rfcAmpliado.getRFCOriginal());
        personaDTO.setRfcVigente(rfcAmpliado.getRFCVigente());
        personaDTO.setRfcSolicitado(rfcAmpliado.getRFCSolicitado());
        personaDTO.setBoId(rfcAmpliado.getBoid());
        personaDTO.setPersonaIdentificacion(identificacionDTO);
        personaDTO.setDomicilio(ubicacionDTO);
        
        Identificacion identificacion = rfcAmpliado.getIdentificacion();
        if(null!=identificacion){
            identificacionDTO.setApMaterno(            identificacion.getApMaterno());
            identificacionDTO.setApPaterno(            identificacion.getApPaterno());
            identificacionDTO.setClaveDetSitCont(      identificacion.getCDetSitCont());
            identificacionDTO.setClaveSegmento(        identificacion.getCSegmento());
            identificacionDTO.setClaveSitContDom(      identificacion.getCSitContDom());
            identificacionDTO.setClaveSitContribuyente(identificacion.getCSitCont());
            identificacionDTO.setClaveSitDomicilio(    identificacion.getCSitDom());
            identificacionDTO.setCurp(                 identificacion.getCURP());
            identificacionDTO.setDenominacion(         identificacion.getDSegmento());
            identificacionDTO.setDescSitContDom(       identificacion.getDSitContDom());
            identificacionDTO.setDescSitContribuyente( identificacion.getDSitCont());
            identificacionDTO.setDescSitDomicilio(     identificacion.getDSitDom());
            identificacionDTO.setDescripcionSegmento(  identificacion.getDSegmento());
            
            Date dfFFin = DycFechaUtil.stringToDate(identificacion.getDFFFin(), ConstantesDyC3.RFCAMPLIADO_RESPONSE_FECHA_FORMATO);
            identificacionDTO.setDfFFin(               dfFFin);
            Date dFFInicio = DycFechaUtil.stringToDate(identificacion.getDFFInicio(), ConstantesDyC3.RFCAMPLIADO_RESPONSE_FECHA_FORMATO);
            identificacionDTO.setDfFInicio(            dFFInicio);
            
            identificacionDTO.setDfFolio(              identificacion.getDFFolio());
            identificacionDTO.setDfTipo(               identificacion.getDFTipo());
            identificacionDTO.setEmail(                identificacion.getEmail());
            
            Date fConstitucion = DycFechaUtil.stringToDate(identificacion.getFConstitucion(), ConstantesDyC3.RFCAMPLIADO_RESPONSE_FECHA_FORMATO);
            identificacionDTO.setFConstitucion(        fConstitucion);
            Date fIniOpers = DycFechaUtil.stringToDate(identificacion.getFIniOpers(), ConstantesDyC3.RFCAMPLIADO_RESPONSE_FECHA_FORMATO);
            identificacionDTO.setFInicioOperacion(     fIniOpers);
            Date fNacimiento = DycFechaUtil.stringToDate(identificacion.getFNacimiento(), ConstantesDyC3.RFCAMPLIADO_RESPONSE_FECHA_FORMATO);
            identificacionDTO.setFNacimiento(          fNacimiento);
            Date fSitCont = DycFechaUtil.stringToDate(identificacion.getFSitCont(), ConstantesDyC3.RFCAMPLIADO_RESPONSE_FECHA_FORMATO);
            identificacionDTO.setFechaSitContribuyente(fSitCont);
            
            identificacionDTO.setNacionalidad(         identificacion.getNacionalidad());
            identificacionDTO.setNit(                  identificacion.getNIT());
            identificacionDTO.setNombre(               identificacion.getNombre());
            identificacionDTO.setNombreComercial(      identificacion.getNomComercial());
            identificacionDTO.setPaisOrigen(           identificacion.getPaisOrigen());
            identificacionDTO.setRazonSocial(          identificacion.getRazonSoc());
            identificacionDTO.setTipoPersona(          identificacion.getTPersona());
            identificacionDTO.setTipoSociedad(         identificacion.getTSociedad());
        }
        
        Ubicacion ubicacion = rfcAmpliado.getUbicacion();
        if(null!=ubicacion){
            ubicacionDTO.setAdmonLocal(         ubicacion.getDALR());
            ubicacionDTO.setCalle(              ubicacion.getCalle());
            ubicacionDTO.setCaracDomicilio(     ubicacion.getCaractDomicilio());

            /**
             * Solo se puede obtener mediante el StoreProcedure de RFC Ampliado
             * ubicacionDTO.setCentroRegional(     ubicacion.getCentroRegional());
             * ubicacionDTO.setDescCentroRegional( ubicacion.getDCentroRegional());
             */

            ubicacionDTO.setClaveAdmonLocal(    Integer.parseInt(ubicacion.getCALR()));
            ubicacionDTO.setClaveColonia(       ubicacion.getCColonia());
            ubicacionDTO.setClaveEntFed(        ubicacion.getCEntFed());
            ubicacionDTO.setClaveLocalidad(     ubicacion.getCLocalidad());
            ubicacionDTO.setClaveMunicipio(     ubicacion.getCMunicipio());
            ubicacionDTO.setCodigoPostal(       ubicacion.getCp());
            ubicacionDTO.setColonia(            ubicacion.getDColonia());
            ubicacionDTO.setDescInmueble(       ubicacion.getDInmueble());
            ubicacionDTO.setDescVialidad(       ubicacion.getDVialidad());
            ubicacionDTO.setEmail(              ubicacion.getEmail());
            ubicacionDTO.setEntFed(             ubicacion.getDEntFed());
            ubicacionDTO.setEntreCalle1(        ubicacion.getDEntreCalle1());
            ubicacionDTO.setEntreCalle2(        ubicacion.getDEntreCalle2());

            Date fAltaDom = DycFechaUtil.stringToDate(ubicacion.getFAltaDom(), ConstantesDyC3.RFCAMPLIADO_RESPONSE_FECHA_FORMATO);
            ubicacionDTO.setFechaAltaDomicilio( fAltaDom);
            
            ubicacionDTO.setLocalidad(          ubicacion.getDLocalidad());
            ubicacionDTO.setMunicipio(          ubicacion.getDMunicipio());
            ubicacionDTO.setNumeroExt(          ubicacion.getNExterior());
            ubicacionDTO.setNumeroInt(          ubicacion.getNInterior());
            ubicacionDTO.setPais(               ubicacion.getPaisResidencia());
            ubicacionDTO.setRefAdicionales(     ubicacion.getDReferencia());
            ubicacionDTO.setTelefono1(          ubicacion.getTelefono1());
            ubicacionDTO.setTelefono2(          ubicacion.getTelefono2());
            ubicacionDTO.setTipoInmueble(       ubicacion.getTInmueble());
            ubicacionDTO.setTipoTelefono1(      ubicacion.getTTel1());
            ubicacionDTO.setTipoTelefono2(      ubicacion.getTTel2());
            ubicacionDTO.setTipoVialidad(       ubicacion.getTVialidad());
        }
        
        return personaDTO;
    }
    
    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.MANDATORY, 
            rollbackFor = { Exception.class })
    public void insertarContribuyente(String numeroControl, Date fechaConsultaARFCAmpliado, 
                IdCInterno rfcAmpliadoResponse) throws DycServiceExcepcion{
        List<DycLogEstadoVariable> estadoVariables = new ArrayList<DycLogEstadoVariable>();
        estadoVariables.add( new DycLogEstadoVariable("numeroControl", numeroControl) );
        estadoVariables.add( new DycLogEstadoVariable("fechaConsultaARFCAmpliado", fechaConsultaARFCAmpliado.toString()) );
        estadoVariables.add( new DycLogEstadoVariable("tipoPersona", rfcAmpliadoResponse.getIdentificacion().getTPersona().trim().toUpperCase()) );
                
        try {
            DyctContribuyenteDTO dyctContribuyenteDTO = new DyctContribuyenteDTO();
            PersonaDTO personaDTO = remapearRFCAmpliadoResponse(rfcAmpliadoResponse);
            String xmlContribuyente = Marshal.objectToXMLString(personaDTO);
            int personaFisica = 0;
            int personaMoral  = 0;
                    
            String tipoPersona = rfcAmpliadoResponse.getIdentificacion().getTPersona().trim().toUpperCase();
            if(tipoPersona.equals(ConstantesDyC3.RFCAMPLIADO_RESPONSE_TIPOPERSONA_FISICA)){
                personaFisica = 1;
            }
            if(tipoPersona.equals(ConstantesDyC3.RFCAMPLIADO_RESPONSE_TIPOPERSONA_MORAL)){
                personaMoral = 1;
            }
            
            dyctContribuyenteDTO.setFechaConsulta(fechaConsultaARFCAmpliado);
            dyctContribuyenteDTO.setXmlContribuyente(xmlContribuyente);
            dyctContribuyenteDTO.setRolPf(personaFisica);
            dyctContribuyenteDTO.setRolPm(personaMoral);
            dyctContribuyenteDTO.setNumControl(numeroControl);
            
            contribuyenteDAO.createContribuyente(dyctContribuyenteDTO);
        } catch (JAXBException ex) {
            throw new DycServiceExcepcion(EDycServiceCodigoError.BD_DYC_CONTRIBUYENTE_DATOSCONTRIB_XML, estadoVariables, ex);
        } catch (DycDaoExcepcion ex) {
            throw new DycServiceExcepcion(EDycServiceCodigoError.BD_DYC_CONTRIBUYENTE_INSERT_GENERAL, estadoVariables, ex);
        }
    }

    @Override
    public void insertarDeclaracion(Date fechaPresentacion, BigDecimal importeSaldoF, 
            String numeroControl, String numeroOperacion) throws DycServiceExcepcion{
        List<DycLogEstadoVariable> estadoVariables = new ArrayList<DycLogEstadoVariable>();
        //estadoVariables.add( new DycLogEstadoVariable("numeroControl", numeroControl) );
        
        try {
            DyctDeclaracionDTO dyctDeclaracionDTO = new DyctDeclaracionDTO();
            DyccUsoDecDTO dyccUsoDecDTO = new DyccUsoDecDTO();
            dyctDeclaracionDTO.setDyccUsoDecDTO(dyccUsoDecDTO);
            DyccTipoDeclaraDTO dyccTipoDeclaraDTO = new DyccTipoDeclaraDTO();
            dyctDeclaracionDTO.setDyccTipoDeclaraDTO(dyccTipoDeclaraDTO);
            
            dyctDeclaracionDTO.setFechaPresentacion(fechaPresentacion);
            dyctDeclaracionDTO.setSaldoAfavor(importeSaldoF);
            dyctDeclaracionDTO.setImporte(importeSaldoF);
            dyccUsoDecDTO.setIdUsoDec(1);
            dyccTipoDeclaraDTO.setIdTipoDeclaracion(1); //Normal
            dyctDeclaracionDTO.setNumControl(numeroControl);
            dyctDeclaracionDTO.setNumOperacion(numeroOperacion);
            
            dyctDeclaracionDAO.insertar(dyctDeclaracionDTO);
        } catch (DycDaoExcepcion ex) {
            throw new DycServiceExcepcion(EDycServiceCodigoError.BD_DYC_DECLARACION_INSERT_GENERAL, estadoVariables, ex);
        }
    }

    @Override
    public Integer insertarCuentaBancoArchivo(String numeroControl) throws DycServiceExcepcion{
        Integer idArchivo = null;
        
        try {
            DyctArchivoDTO dyctArchivoDTO = new DyctArchivoDTO();
            DycpServicioDTO dycpServicioDTO = new DycpServicioDTO();
            dyctArchivoDTO.setDycpServicioDTO(dycpServicioDTO);

            dyctArchivoDTO.setNombreArchivo(DycConstantesAutomaticasIva.DYCTARCHIVO_NOMBREARCHIVO_GENERADOPORSISTEMA);
            dyctArchivoDTO.setUrl(ConstantesDyCURL.URL_DOCUMENTOS);
            dyctArchivoDTO.setDescripcion(DycConstantesAutomaticasIva.DYCTARCHIVO_DESCRIPCION_GENERADOPORSISTEMA);
            dycpServicioDTO.setNumControl(numeroControl);
            dyctArchivoDTO.setFechaRegistro(new Date());
                    
            idArchivo = dyctArchivoDAO.insert(dyctArchivoDTO);
        } catch (SIATException ex) {
            throw new DycServiceExcepcion(EDycServiceCodigoError.BD_DYC_CUENTABANCO_ARCHIVO_INSERT_GENERAL, null, ex);
        }
        
        return idArchivo;
    }

    @Override
    public void insertarCuentaBanco(String clabe, String numeroControl, Date fechaPresentacion, Integer idArchivo) throws DycServiceExcepcion {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insertarResolucion(String numeroControl, Date fechaPresentacion, Date fechaAprobacionMorsa, BigDecimal importeSaldoF, EDycAutomaticasIvaEstadoCasoDevolucion estadoCasoDevolucion, BigDecimal importeAutorizado) throws DycServiceExcepcion {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insertarResolucionAut(String numeroControl, Date fechaPresentacion, Date fechaAprobacionMorsa, BigDecimal importeSaldoF, EDycAutomaticasIvaEstadoCasoDevolucion estadoCasoDevolucion, BigDecimal importeAutorizado, BigDecimal importeNeto) throws DycServiceExcepcion {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
