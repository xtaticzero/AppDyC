/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.avisocomp.service.impl;

import java.io.IOException;

import java.math.BigDecimal;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.cobranza.diahabil.service.DiaHabilService;
import mx.gob.sat.siat.dyc.avisocomp.dao.AnexoDAO;
import mx.gob.sat.siat.dyc.avisocomp.dao.ArchivoDAO;
import mx.gob.sat.siat.dyc.avisocomp.dao.AvisoCompensacionDAO;
import mx.gob.sat.siat.dyc.avisocomp.service.AvisoCompensacionService;
import mx.gob.sat.siat.dyc.avisocomp.service.DesistirCompensacionesService;
import mx.gob.sat.siat.dyc.avisocomp.vo.AnexoVO;
import mx.gob.sat.siat.dyc.avisocomp.vo.ArchivoVO;
import mx.gob.sat.siat.dyc.avisocomp.vo.CuadroVO;
import mx.gob.sat.siat.dyc.avisocomp.vo.DataUploadVO;
import mx.gob.sat.siat.dyc.avisocomp.vo.DatosAvisoFieldVO;
import mx.gob.sat.siat.dyc.avisocomp.vo.DatosDestinoAcuseVO;
import mx.gob.sat.siat.dyc.avisocomp.vo.DatosOrigenAcuseVO;
import mx.gob.sat.siat.dyc.avisocomp.vo.PendienteVO;
import mx.gob.sat.siat.dyc.casocomp.dto.districomp.CasoCompensacionVO;
import mx.gob.sat.siat.dyc.casocomp.service.districomp.CrearCasoCompService;
import mx.gob.sat.siat.dyc.catalogo.service.DyccImpuestoService;
import mx.gob.sat.siat.dyc.controlsaldos.service.icep.AfectarSaldosXAvisosCompService;
import mx.gob.sat.siat.dyc.dao.compensacion.DycpAvisoCompDAO;
import mx.gob.sat.siat.dyc.dao.detalleicep.DyctSaldoIcepDAO;
import mx.gob.sat.siat.dyc.dao.icep.DyctDeclaraIcepDAO;
import mx.gob.sat.siat.dyc.dao.inconsistencia.DycaAvInconsistDAO;
import mx.gob.sat.siat.dyc.dao.periodo.DyccTipoPeriodoDAO;
import mx.gob.sat.siat.dyc.dao.secuencia.solicitud.SolNumConsecutivoDAO;
import mx.gob.sat.siat.dyc.dao.tipotramite.DyccTipoTramiteDAO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccImpuestoDTO;
import mx.gob.sat.siat.dyc.domain.regsolicitud.TramiteDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpAvisoCompDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaracionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctOrigenAvisoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccTipoPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaraIcepDTO;
import mx.gob.sat.siat.dyc.domain.secuencia.DycqNumControlDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoDeclaraDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccUsoDecDTO;
import mx.gob.sat.siat.dyc.generico.util.ArchivoCargaDescarga;
import mx.gob.sat.siat.dyc.generico.util.common.Utilerias;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCURL;
import mx.gob.sat.siat.dyc.util.constante2.ConstantesTipoRol;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author  Alfredo Ramirez
 * @since   20/11/2013  under 17 th August
 * @author  Yolanda Martínez Sánchez
 */
@Service(value = "avisoCompensacionService")
public class AvisoCompensacionServiceImpl implements AvisoCompensacionService {

    private static final Logger LOG = Logger.getLogger(AvisoCompensacionServiceImpl.class);
    @Autowired
    private AvisoCompensacionDAO avisoCompensacionDAO;

    @Autowired
    private DyctSaldoIcepDAO dyctSaldoIcepDAO;

    @Autowired
    private SolNumConsecutivoDAO solNumConsecutivoDAO;

    @Autowired
    private ArchivoDAO archivoDAO;

    @Autowired
    private DycpAvisoCompDAO dycpAvisoCompDAO;

    @Autowired
    private DycaAvInconsistDAO dycaAvInconsistDAO;

    @Autowired
    private AnexoDAO anexoDAO;

    @Autowired
    private DyccImpuestoService dyccImpuestoService;

    @Autowired
    private DyccTipoPeriodoDAO dyccTipoPeriodoDAO;

    @Autowired
    private DiaHabilService diaHabilService;

    @Autowired
    private CrearCasoCompService crearCasoCompService;

    @Autowired
    private AfectarSaldosXAvisosCompService afectarSaldosXAvisosCompService;

    @Autowired
    private DyccTipoTramiteDAO dyccTipoTramiteDAO;

    @Autowired
    private DyctDeclaraIcepDAO dyctDeclaraIcepDAO;

    @Autowired
    private DesistirCompensacionesService desistirCompensacionesService;

    private static final String HORAINICIAL = "00:00:00";
    private static final String HORA_INICIAL_LABORAL = "09:00:00";
    private static final String HORALIMITE = "18:00:00";
    private static final String FORMATO_HORA = "HH:mm:ss";
    private static final String FORMATO_FECHA = "dd/MM/yyyy";
    private static final String FORMATO_ANIO_YY = "yy";
    private static final String FORMATO_D_M_Y_H = "dd/MM/yyyy HH:mm:ss";

    public AvisoCompensacionServiceImpl() {
        super();
    }

    @Override
    public List<PendienteVO> obtenerAvisosPendientes(String rfc) {
        List<PendienteVO> pendienteArray = new ArrayList<PendienteVO>();
        try {
            pendienteArray = avisoCompensacionDAO.obtenerAvisosPendientes(rfc);
        } catch (DataAccessException dae) {
            LOG.error(dae.getMessage());
        }
        return pendienteArray;
    }

    @Override
    public List<DataUploadVO> getDatasAvisoCompensacionByFolio(Integer folioAvisoTemp) {
        List<DataUploadVO> dataUploadVOArray = new ArrayList<DataUploadVO>();
        try {
            dataUploadVOArray = avisoCompensacionDAO.getRowsAvisoCompensacion(folioAvisoTemp);
        } catch (DataAccessException dae) {
            LOG.error(dae.getMessage());
        }

        return dataUploadVOArray;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public String obtenerNumeroControl(String claveSirNumControl) throws SIATException {
        boolean existeNumControl=Boolean.TRUE;
        String numControl = null;
        DycqNumControlDTO numConsecutivo = null;
        Date anio = new Date();
        DateFormat dateFormat = new SimpleDateFormat(FORMATO_ANIO_YY);
        String sirNumControl = String.valueOf(claveSirNumControl);
        if (String.valueOf(claveSirNumControl).length() < 2) {
            sirNumControl = 0 + sirNumControl;
        }
        //Cambio para validar que no existe el numero de control en bd
        while(existeNumControl){
           numConsecutivo = solNumConsecutivoDAO.getNumConsecutivoCasoCom(sirNumControl);
           numControl = ConstantesTipoRol.AC + sirNumControl + dateFormat.format(anio) + numConsecutivo.getSecuencia();
           LOG.error("Buscando num de control: " + numControl);
           existeNumControl=solNumConsecutivoDAO.existeNumeroControlSolicitud(numControl);       
       }
        //
        
        return numControl;
    }
    

    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED,
                   rollbackFor = Exception.class)
    public String insertarCompensacion(DatosAvisoFieldVO datosAvisoField, String cadenaOriginal,
                                       String selloDigital) throws SIATException {

        /**-------------------------------------- REGISTRAR ORIGEN(DYCP_DECLARACION)  Y DYCP_COMPENSACION DESTINO -------------*/
        int aux = ConstantesDyCNumerico.VALOR_0;
        /**-------------------------VALOR POR SI SE OBTIENE DE TEMPORAL-------------------------
        boolean banderaTempo =
            avisoCompensacionTempService.actualizarTemporalACClon(datosAvisoField.getDycpCompensacionDTO().getDycpAvisoCompDTO());**/
        /**-------------------------FIN DE VALOR POR SI SE OBTIENE DE TEMPORAL-------------------------**/

        /**------------------------DESISTIR FOLIO AVISO DEL COMPLEMENTARIO-----------------------------**/
        desistirCompensacionesService.desistirAvisosXFolioAvisoNormal(datosAvisoField.getDycpCompensacionDTO().getDycpAvisoCompDTO());
        /**------------------------FIN DE DESISTIR FOLIO AVISO DEL COMPLEMENTARIO-----------------------------**/

        /**Metodo de insertar a dycp_avisocomp*/
        String secuencia = insertarAvisoCompensacion(datosAvisoField, cadenaOriginal, selloDigital);
        
        for (CuadroVO cuadro : datosAvisoField.getListaCuadros()) {
            
            /**:::::::::::::::: CAMPOS FALTANTES PARA LA CREACION DEL AVISO DE COMPENSACION :::::::::::::::::::::*/
            List<DyctOrigenAvisoDTO> listaOrigenAviso = new ArrayList<DyctOrigenAvisoDTO>();

            cuadro.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDycaServOrigenDTO().setDyccTipoServicioDTO(Constantes.TiposServicio.CASO_COMPENSACION);
            cuadro.getDycpCompensacionDTO().getDycpServicioDTO().setDycaOrigenTramiteDTO(cuadro.getDycpServicioDTO().getDycaOrigenTramiteDTO());
            cuadro.getDycpCompensacionDTO().getDycpServicioDTO().setNumControl(datosAvisoField.getListaNumControl().get(aux));
            cuadro.getDycpCompensacionDTO().getDycpServicioDTO().setClaveAdm(datosAvisoField.getClaveAdm());
            /**cuadro.getDycpCompensacionDTO().setDyccEstadoCompDTO(Constantes.EstadosCompensacion.REGISTRO);**/

            cuadro.getDycpCompensacionDTO().setImporteCompensado(cuadro.getDyctOrigenAvisoDTO().getImporteSolicitado());
            cuadro.getDycpCompensacionDTO().setDycpAvisoCompDTO(datosAvisoField.getDycpCompensacionDTO().getDycpAvisoCompDTO());

            DyccImpuestoDTO impuestoDestino =
                dyccImpuestoService.impuestoXConcepto(datosAvisoField.getDycpCompensacionDTO().getDyctSaldoIcepDestinoDTO().getDyccConceptoDTO());
            cuadro.getDycpCompensacionDTO().getDyctSaldoIcepDestinoDTO().getDyccConceptoDTO().setDyccImpuestoDTO(impuestoDestino);

            DyctSaldoIcepDTO dyctSaldoIcepDTO = new DyctSaldoIcepDTO();
            DyccImpuestoDTO impuestoOrigen = dyccImpuestoService.impuestoXConcepto(cuadro.getDyccConceptoOrigen());
            dyctSaldoIcepDTO.setDyccConceptoDTO(cuadro.getDyccConceptoOrigen());
            dyctSaldoIcepDTO.getDyccConceptoDTO().setDyccImpuestoDTO(impuestoOrigen);
            dyctSaldoIcepDTO.setDyccEjercicioDTO(cuadro.getDyccDiEjercicioDTO());
            dyctSaldoIcepDTO.setDyccPeriodoDTO(cuadro.getDyccPeriodoOrigenDTO());
            cuadro.getDycpCompensacionDTO().setDyctSaldoIcepOrigenDTO(dyctSaldoIcepDTO);

            listaOrigenAviso.add(cuadro.getDyctOrigenAvisoDTO());
            cuadro.getDycpCompensacionDTO().setDyctOrigenAvisoList(listaOrigenAviso);

            CasoCompensacionVO avisoCompensacion = datosDeclaracion(cuadro, datosAvisoField.getListaNumControl(), aux);
            avisoCompensacion.setDycpCompensacionDTO(cuadro.getDycpCompensacionDTO());
            avisoCompensacion.setFechaPresentacionOrigen(cuadro.getDyctDeclaracionDTO().getFechaPresentacion());
            avisoCompensacion.setMontoSaldoAFavor(cuadro.getDyctDeclaraTempDTO().getSaldoAFavor());
            avisoCompensacion.setEsRemanente(cuadro.isDdRequiereNumControl());

            /**:::::::::::::::: FIN CAMPOS FALTANTES PARA LA CREACION DEL AVISO DE COMPENSACION :::::::::::::::::*/

            /**:::::::::::::: :::::::::::::::: BLOQUE DE INSERCIONES :::::::::::::::::::::::::::::::*/

            /**:::::::::::::: INSERTAR AVISO ::::::::**/

            /**:::::::::::::: INSERTAR INCONSISTENCIAS ::::::::**/
            insertarAvisoCompensacionD(avisoCompensacion, datosAvisoField.getPersona());

            for (int i = 0; i < datosAvisoField.getListaInconsistencias().size(); i++) {
                datosAvisoField.getListaInconsistencias().get(i).setDycpAvisoCompDTO(datosAvisoField.getDycpCompensacionDTO().getDycpAvisoCompDTO());
            }
            dycaAvInconsistDAO.insertar(datosAvisoField.getListaInconsistencias());

            List<AnexoVO> lstAnexos = new ArrayList<AnexoVO>();
            for(AnexoVO anexo: datosAvisoField.getListaAnexos()){
                if(anexo.getCuadroSaldo().equals("Saldo"+cuadro.getNumCuadro())){
                    lstAnexos.add(anexo);
                }
            }
            /**:::::::::::::: INSERTAR ANEXOS :::::::**/
            insertarAnexos(datosAvisoField.getDycpCompensacionDTO(),
                           datosAvisoField.getDycpCompensacionDTO().getDycpAvisoCompDTO(),
                           lstAnexos, avisoCompensacion);
            aux++;
            cuadro = null;
            listaOrigenAviso = null;
            impuestoDestino = null;
            dyctSaldoIcepDTO = null;
            avisoCompensacion = null;
        }

        /**:::::::::::::: INSERTAR DOCUMENTOS :::::::**/
        insertarDocumentos(datosAvisoField.getDycpCompensacionDTO(),
                           datosAvisoField.getDycpCompensacionDTO().getDycpAvisoCompDTO(),
                           datosAvisoField.getListaDocumentos());

        return secuencia;
    }
    
     
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED,
                   rollbackFor = Exception.class)
    public void insertarCompensacion(DatosAvisoFieldVO datosAvisoField) throws SIATException {

        /**-------------------------------------- REGISTRAR ORIGEN(DYCP_DECLARACION)  Y DYCP_COMPENSACION DESTINO -------------*/
        int aux = ConstantesDyCNumerico.VALOR_0;
        /**-------------------------VALOR POR SI SE OBTIENE DE TEMPORAL-------------------------
        boolean banderaTempo =
            avisoCompensacionTempService.actualizarTemporalACClon(datosAvisoField.getDycpCompensacionDTO().getDycpAvisoCompDTO());**/
        /**-------------------------FIN DE VALOR POR SI SE OBTIENE DE TEMPORAL-------------------------**/

        /**------------------------DESISTIR FOLIO AVISO DEL COMPLEMENTARIO-----------------------------**/
        desistirCompensacionesService.desistirAvisosXFolioAvisoNormal(datosAvisoField.getDycpCompensacionDTO().getDycpAvisoCompDTO());
        /**------------------------FIN DE DESISTIR FOLIO AVISO DEL COMPLEMENTARIO-----------------------------**/

             
        for (CuadroVO cuadro : datosAvisoField.getListaCuadros()) {
            
            /**:::::::::::::::: CAMPOS FALTANTES PARA LA CREACION DEL AVISO DE COMPENSACION :::::::::::::::::::::*/
            List<DyctOrigenAvisoDTO> listaOrigenAviso = new ArrayList<DyctOrigenAvisoDTO>();

            cuadro.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDycaServOrigenDTO().setDyccTipoServicioDTO(Constantes.TiposServicio.CASO_COMPENSACION);
            cuadro.getDycpCompensacionDTO().getDycpServicioDTO().setDycaOrigenTramiteDTO(cuadro.getDycpServicioDTO().getDycaOrigenTramiteDTO());
            cuadro.getDycpCompensacionDTO().getDycpServicioDTO().setNumControl(datosAvisoField.getListaNumControl().get(aux));
            cuadro.getDycpCompensacionDTO().getDycpServicioDTO().setClaveAdm(datosAvisoField.getClaveAdm());
            /**cuadro.getDycpCompensacionDTO().setDyccEstadoCompDTO(Constantes.EstadosCompensacion.REGISTRO);**/

            cuadro.getDycpCompensacionDTO().setImporteCompensado(cuadro.getDyctOrigenAvisoDTO().getImporteSolicitado());
            cuadro.getDycpCompensacionDTO().setDycpAvisoCompDTO(datosAvisoField.getDycpCompensacionDTO().getDycpAvisoCompDTO());

            DyccImpuestoDTO impuestoDestino =
                dyccImpuestoService.impuestoXConcepto(datosAvisoField.getDycpCompensacionDTO().getDyctSaldoIcepDestinoDTO().getDyccConceptoDTO());
            cuadro.getDycpCompensacionDTO().getDyctSaldoIcepDestinoDTO().getDyccConceptoDTO().setDyccImpuestoDTO(impuestoDestino);

            DyctSaldoIcepDTO dyctSaldoIcepDTO = new DyctSaldoIcepDTO();
            DyccImpuestoDTO impuestoOrigen = dyccImpuestoService.impuestoXConcepto(cuadro.getDyccConceptoOrigen());
            dyctSaldoIcepDTO.setDyccConceptoDTO(cuadro.getDyccConceptoOrigen());
            dyctSaldoIcepDTO.getDyccConceptoDTO().setDyccImpuestoDTO(impuestoOrigen);
            dyctSaldoIcepDTO.setDyccEjercicioDTO(cuadro.getDyccDiEjercicioDTO());
            dyctSaldoIcepDTO.setDyccPeriodoDTO(cuadro.getDyccPeriodoOrigenDTO());
            cuadro.getDycpCompensacionDTO().setDyctSaldoIcepOrigenDTO(dyctSaldoIcepDTO);

            listaOrigenAviso.add(cuadro.getDyctOrigenAvisoDTO());
            cuadro.getDycpCompensacionDTO().setDyctOrigenAvisoList(listaOrigenAviso);

            CasoCompensacionVO avisoCompensacion = datosDeclaracion(cuadro, datosAvisoField.getListaNumControl(), aux);
            avisoCompensacion.setDycpCompensacionDTO(cuadro.getDycpCompensacionDTO());
            avisoCompensacion.setFechaPresentacionOrigen(cuadro.getDyctDeclaracionDTO().getFechaPresentacion());
            avisoCompensacion.setMontoSaldoAFavor(cuadro.getDyctDeclaraTempDTO().getSaldoAFavor());
            avisoCompensacion.setEsRemanente(cuadro.isDdRequiereNumControl());

            /**:::::::::::::::: FIN CAMPOS FALTANTES PARA LA CREACION DEL AVISO DE COMPENSACION :::::::::::::::::*/

            /**:::::::::::::: :::::::::::::::: BLOQUE DE INSERCIONES :::::::::::::::::::::::::::::::*/

            /**:::::::::::::: INSERTAR AVISO ::::::::**/

            /**:::::::::::::: INSERTAR INCONSISTENCIAS ::::::::**/
            insertarAvisoCompensacionD(avisoCompensacion, datosAvisoField.getPersona());

            for (int i = 0; i < datosAvisoField.getListaInconsistencias().size(); i++) {
                datosAvisoField.getListaInconsistencias().get(i).setDycpAvisoCompDTO(datosAvisoField.getDycpCompensacionDTO().getDycpAvisoCompDTO());
            }
            dycaAvInconsistDAO.insertar(datosAvisoField.getListaInconsistencias());

            List<AnexoVO> lstAnexos = new ArrayList<AnexoVO>();
            for(AnexoVO anexo: datosAvisoField.getListaAnexos()){
                if(anexo.getCuadroSaldo().equals("Saldo"+cuadro.getNumCuadro())){
                    lstAnexos.add(anexo);
                }
            }
            /**:::::::::::::: INSERTAR ANEXOS :::::::**/
            insertarAnexos(datosAvisoField.getDycpCompensacionDTO(),
                           datosAvisoField.getDycpCompensacionDTO().getDycpAvisoCompDTO(),
                           lstAnexos, avisoCompensacion);
            aux++;
            cuadro = null;
            listaOrigenAviso = null;
            impuestoDestino = null;
            dyctSaldoIcepDTO = null;
            avisoCompensacion = null;
        }

        /**:::::::::::::: INSERTAR DOCUMENTOS :::::::**/
        insertarDocumentos(datosAvisoField.getDycpCompensacionDTO(),
                           datosAvisoField.getDycpCompensacionDTO().getDycpAvisoCompDTO(),
                           datosAvisoField.getListaDocumentos());

    }

    public String insertarAvisoCompensacion(DatosAvisoFieldVO datosAvisoField, String cadenaOriginal,
                                             String selloDigital) throws SIATException {
        String secuencia = "";
        try {
            Date fechaSistem = new Date();
            String anio = new SimpleDateFormat("yy").format(fechaSistem);
            secuencia = "F-" + anio + String.format("%07d", dycpAvisoCompDAO.obtenerSecuencia());
            datosAvisoField.getDycpCompensacionDTO().getDycpAvisoCompDTO().setFolioAviso(secuencia);
            datosAvisoField.getDycpCompensacionDTO().getDycpAvisoCompDTO().setCadenaOriginal(cadenaOriginal);
            datosAvisoField.getDycpCompensacionDTO().getDycpAvisoCompDTO().setSelloDigital(selloDigital);
            dycpAvisoCompDAO.insertar(datosAvisoField.getDycpCompensacionDTO().getDycpAvisoCompDTO());
        } catch (Exception e) {
            LOG.info(e.getMessage());
            throw new SIATException(e);
        }
        return secuencia;
    }

    private CasoCompensacionVO datosDeclaracion(CuadroVO cuadro, List<String> listaNumControl, Integer aux) {
        CasoCompensacionVO avisoCompensacion = new CasoCompensacionVO();
        /**:::::::::::::::: DATOS PARA INSERTAR EN LA DECLARACION :::::::::::::::::::::*/
        if (cuadro.getDyctDeclaraTempDTO().getNormalFechapres() != null) {

            Integer idDeclaraComple = solNumConsecutivoDAO.getIdDeclaracion();
            cuadro.getDyctDeclaracionDTO().setIdDeclaracion(idDeclaraComple);
            DyccUsoDecDTO usoDecDTO = new DyccUsoDecDTO();
            usoDecDTO.setIdUsoDec(ConstantesDyCNumerico.VALOR_3);
            cuadro.getDyctDeclaracionDTO().setDyccUsoDecDTO(usoDecDTO);
            cuadro.getDyctDeclaracionDTO().setFechaPresentacion(cuadro.getDyctDeclaracionDTO().getFechaPresentacion());
            cuadro.getDyctDeclaracionDTO().setFechaCausacion(new Date());
            cuadro.getDyctDeclaracionDTO().setNumDocumento(cuadro.getDyctDeclaraTempDTO().getNumDocumento());
            cuadro.getDyctDeclaracionDTO().setNumOperacion(cuadro.getDyctDeclaraTempDTO().getNumOperacion().toUpperCase()); 
            cuadro.getDyctDeclaracionDTO().setSaldoAfavor(cuadro.getDyctDeclaraTempDTO().getSaldoAFavor());
            cuadro.getDyctDeclaracionDTO().setImporte(cuadro.getDyctOrigenAvisoDTO().getImporteSolicitado());
            cuadro.getDyctDeclaracionDTO().setEtiquetaSaldo("Aviso de compensacion complementaria");
            cuadro.getDyctDeclaracionDTO().setNumControl(listaNumControl.get(aux));
            avisoCompensacion.setDyctDeclaracionDTO(cuadro.getDyctDeclaracionDTO());

            if (cuadro.getDdIdTipoDeclaracion() == ConstantesDyCNumerico.VALOR_2) {

                DyctDeclaracionDTO dyctDeclaracionNormal = llenadoDeObjeto(cuadro, listaNumControl, aux);
                avisoCompensacion.setDyctDeclaracionComplementaria(dyctDeclaracionNormal);
            }
        } else {
            Integer idDeclaraNormal = solNumConsecutivoDAO.getIdDeclaracion();
            cuadro.getDyctDeclaracionDTO().setIdDeclaracion(idDeclaraNormal);
            DyccUsoDecDTO usoDecDTONormal = new DyccUsoDecDTO();
            usoDecDTONormal.setIdUsoDec(ConstantesDyCNumerico.VALOR_3);
            cuadro.getDyctDeclaracionDTO().setDyccUsoDecDTO(usoDecDTONormal);
            cuadro.getDyctDeclaracionDTO().setAcreditamiento(BigDecimal.ZERO);
            cuadro.getDyctDeclaracionDTO().setDevueltoCompensado(BigDecimal.ZERO);
            cuadro.getDyctDeclaracionDTO().setFechaPresentacion(cuadro.getDyctDeclaracionDTO().getFechaPresentacion());
            cuadro.getDyctDeclaracionDTO().setFechaCausacion(new Date());
            
            if (cuadro.getDdNumDocumento() != null && !cuadro.getDdNumDocumento().isEmpty()) {
                cuadro.getDyctDeclaracionDTO().setNumDocumento(cuadro.getDdNumDocumento()); 
                cuadro.getDyctDeclaracionDTO().setNumOperacion("0");
                cuadro.getDyctDeclaracionDTO().getDyccTipoDeclaraDTO().setIdTipoDeclaracion(ConstantesDyCNumerico.VALOR_3);
            } else {
                cuadro.getDyctDeclaracionDTO().setNumOperacion(cuadro.getDyctDeclaraTempDTO().getNumOperacion().toUpperCase()); 
            }
            cuadro.getDyctDeclaracionDTO().setSaldoAfavor(cuadro.getDyctDeclaraTempDTO().getSaldoAFavor());
            cuadro.getDyctDeclaracionDTO().setImporte(cuadro.getDyctOrigenAvisoDTO().getImporteSolicitado());
            cuadro.getDyctDeclaracionDTO().setEtiquetaSaldo("Aviso de compensacion normal");
            cuadro.getDyctDeclaracionDTO().setNumControl(listaNumControl.get(aux));
            avisoCompensacion.setDyctDeclaracionDTO(cuadro.getDyctDeclaracionDTO());
        }
        return avisoCompensacion;
    }

    private DyctDeclaracionDTO llenadoDeObjeto(CuadroVO cuadro, List<String> listaNumControl, Integer aux) {
        Integer idDeclaraNormal = solNumConsecutivoDAO.getIdDeclaracion();
        DyctDeclaracionDTO dyctDeclaracionNormal = new DyctDeclaracionDTO();
        dyctDeclaracionNormal.setIdDeclaracion(idDeclaraNormal);
        DyccUsoDecDTO usoDecDTOCom = new DyccUsoDecDTO();
        usoDecDTOCom.setIdUsoDec(ConstantesDyCNumerico.VALOR_4);
        dyctDeclaracionNormal.setDyccUsoDecDTO(usoDecDTOCom);
        DyccTipoDeclaraDTO tipoDeclaracion = new DyccTipoDeclaraDTO();
        tipoDeclaracion.setIdTipoDeclaracion(ConstantesDyCNumerico.VALOR_1);
        dyctDeclaracionNormal.setDyccTipoDeclaraDTO(tipoDeclaracion);
        dyctDeclaracionNormal.setAcreditamiento(BigDecimal.ZERO);
        dyctDeclaracionNormal.setDevueltoCompensado(BigDecimal.ZERO);
        dyctDeclaracionNormal.setFechaPresentacion(cuadro.getDyctDeclaraTempDTO().getNormalFechapres());
        String numOperacion = String.valueOf(cuadro.getDyctDeclaraTempDTO().getNormalNumoperacion());
        dyctDeclaracionNormal.setNumOperacion(numOperacion.toUpperCase()); 
        dyctDeclaracionNormal.setFechaCausacion(new Date());
        dyctDeclaracionNormal.setEtiquetaSaldo("Aviso de compensacion normal");
        dyctDeclaracionNormal.setNumDocumento(cuadro.getDyctDeclaracionDTO().getNumDocumento());
        dyctDeclaracionNormal.setSaldoAfavor(cuadro.getDyctDeclaraTempDTO().getNormalImportesaf());
        dyctDeclaracionNormal.setImporte(BigDecimal.ZERO);
        dyctDeclaracionNormal.setNumControl(listaNumControl.get(aux));
        return dyctDeclaracionNormal;
    }

    private void insertarAvisoCompensacionD(CasoCompensacionVO avisoCompensacion,
                                            TramiteDTO persona) throws SIATException
    {
        try {
            List<DyctDeclaracionDTO> listaDeclaracion = new ArrayList<DyctDeclaracionDTO>();

            listaDeclaracion.add(avisoCompensacion.getDyctDeclaracionDTO());
            if (avisoCompensacion.getDyctDeclaracionComplementaria() != null) {
                listaDeclaracion.add(avisoCompensacion.getDyctDeclaracionComplementaria());
            }

            DycpCompensacionDTO compensacion =  avisoCompensacion.getDycpCompensacionDTO();
            
            compensacion.getDyctSaldoIcepDestinoDTO().setRfc(compensacion.getDycpServicioDTO().getRfcContribuyente().trim());

            compensacion.getDyctSaldoIcepOrigenDTO().setDyccOrigenSaldoDTO(compensacion.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDycaServOrigenDTO().getDyccOrigenSaldoDTO());
            
            

            compensacion.getDycpServicioDTO().setDycpCompensacionDTO(compensacion);
            compensacion.getDycpServicioDTO().setDyctDeclaracionList(listaDeclaracion);

            Map<String, Object> respServiceAfectarSaldos =
                afectarSaldosXAvisosCompService.afectarXRegistro(avisoCompensacion.getDycpCompensacionDTO());
            compensacion.getDyctSaldoIcepDestinoDTO().setIdSaldoIcep(Integer.parseInt(respServiceAfectarSaldos.get("idSaldoIcepDestino").toString()));
            compensacion.getDyctSaldoIcepOrigenDTO().setIdSaldoIcep(Integer.parseInt(respServiceAfectarSaldos.get("idSaldoIcepOrigen").toString()));

            crearCasoCompService.bloquedeInserciones(avisoCompensacion, persona);
        } catch (Exception e) {ManejadorLogException.getTraceError(e);
            throw new SIATException(e);
        }

    }


    private void insertarAnexos(DycpCompensacionDTO dycpCompensacionDTO, DycpAvisoCompDTO dycpAvisoCompDTO,
            List<AnexoVO> listaAnexos, CasoCompensacionVO avisoCompensacion) throws SIATException {
        /**:::::::::::::: ::::::::::::::::BLOQUE DE INSERCIONES DE ANEXOS :::::::::::::::::::::::::::::::*/

        StringBuilder ruta = new StringBuilder();
        ruta.append(ConstantesDyCURL.URL_DOCUMENTOS);
        ruta.append("/");
        ruta.append(String.valueOf(dycpCompensacionDTO.getDycpServicioDTO().getClaveAdm()).concat("/"));

        ruta.append(Utilerias.corregirRFC(dycpCompensacionDTO.getDycpServicioDTO().getRfcContribuyente()).concat("/"));
        ruta.append(dycpAvisoCompDTO.getFolioAviso().concat("/"));
        ruta.append(dycpCompensacionDTO.getDycpServicioDTO().getNumControl());

        StringBuilder rutaAnexo = new StringBuilder();
        rutaAnexo.append(ruta);
        rutaAnexo.append(ConstantesDyCURL.TIPO_ARCHIVO_ANEXOS);
        
        List<AnexoVO> listaInsertarAnexo = anexosNormales(listaAnexos, avisoCompensacion, rutaAnexo);

        anexoDAO.insertar(listaInsertarAnexo,
                          avisoCompensacion.getDycpCompensacionDTO().getDycpServicioDTO().getNumControl());
        /**:::::::::::::: :::::::::::::: FIN BLOQUE DE INSERCCIONES :::::::::::::::::::::::::::::::*/
    }


    private List<AnexoVO> anexosNormales(List<AnexoVO> listaAnexos, CasoCompensacionVO avisoCompensacion,
                                         StringBuilder rutaAnexo) throws SIATException {
        ArchivoCargaDescarga archivo;
        List<AnexoVO> listaInsertarNormal = new ArrayList<AnexoVO>();
        for (Iterator itera = listaAnexos.iterator(); itera.hasNext(); ) {
            AnexoVO anexos = (AnexoVO)itera.next();
            if (anexos.getTipoTramite().equals(avisoCompensacion.getDycpCompensacionDTO().getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getIdTipoTramite())) {
                String nom = anexos.getFile().getFileName();
                nom = nom.substring(nom.lastIndexOf('\\') + ConstantesDyCNumerico.VALOR_1, nom.length());
                anexos.setNombreAnexo(nom);
                anexos.setUrl(rutaAnexo.toString());
                listaInsertarNormal.add(anexos);
            }
        }

        for (int j = ConstantesDyCNumerico.VALOR_0; j < listaInsertarNormal.size(); j++) {
            try {
                archivo = new ArchivoCargaDescarga();
                archivo.escribirArchivo(listaInsertarNormal.get(j).getNombreAnexo(),
                                        listaInsertarNormal.get(j).getFile().getInputstream(), rutaAnexo.toString(),
                                        ConstantesDyCNumerico.VALOR_4194304);
            } catch (IOException e) {
                LOG.error(e);
            }
        }
        return listaInsertarNormal;
    }

    private void insertarDocumentos(DycpCompensacionDTO dycpCompensacionDTO, DycpAvisoCompDTO dycpAvisoCompDTO,
                                    List<ArchivoVO> listaDocumentos) throws SIATException {

        StringBuilder ruta = new StringBuilder();
        ruta.append(ConstantesDyCURL.URL_DOCUMENTOS);
        ruta.append("/");
        ruta.append(String.valueOf(dycpCompensacionDTO.getDycpServicioDTO().getClaveAdm()).concat("/"));

        ruta.append(Utilerias.corregirRFC(dycpCompensacionDTO.getDycpServicioDTO().getRfcContribuyente()).concat("/"));
        ruta.append(dycpAvisoCompDTO.getFolioAviso());

        StringBuilder rutaDocumento = new StringBuilder();
        rutaDocumento.append(ruta);
        rutaDocumento.append(ConstantesDyCURL.TIPO_ARCHIVO_ARCHIVOS);
        
         documentosNormales(listaDocumentos, rutaDocumento.toString(), dycpAvisoCompDTO);

        archivoDAO.insertar(listaDocumentos);
    }


    private List<ArchivoVO> documentosNormales(List<ArchivoVO> listaDocumentos, String rutaDocumento,
                                               DycpAvisoCompDTO dycpAvisoCompDTO) throws SIATException {
        ArchivoCargaDescarga archivo;
        for (ArchivoVO objeto : listaDocumentos) {
            objeto.setUrl(rutaDocumento);
            objeto.setDycpAvisoCompDTO(dycpAvisoCompDTO);
        }

        for (int i = ConstantesDyCNumerico.VALOR_0; i < listaDocumentos.size(); i++) {
            try {
                archivo = new ArchivoCargaDescarga();
                archivo.escribirArchivo(listaDocumentos.get(i).getNombreArchivo(),
                                        listaDocumentos.get(i).getFile().getInputstream(), rutaDocumento,
                                        ConstantesDyCNumerico.VALOR_4194304);
            } catch (IOException e) {
                LOG.error(e);
            }
        }
        return listaDocumentos;
    }

    @Override
    public DyccTipoPeriodoDTO getDyccTipoPeriodoByIdPeriodo(Integer idPeriodo) {

        return dyccTipoPeriodoDAO.obtenerTipoPeriodoPorIdPeriodo(idPeriodo);

    }

    @Override
    public DycpAvisoCompDTO getDycpAvisoCompDTOByFolioAviso(String folioAviso, String rfcContribuyente) {

        return dycpAvisoCompDAO.buscaFolioAvisoXFolioNormalYRFC(folioAviso, rfcContribuyente);
    }

    @Override
    public DyccTipoTramiteDTO getDycpTipoTramiteDTOById(Integer idTipoTramite) {
        DyccTipoTramiteDTO tipoTramite = new DyccTipoTramiteDTO();
        try {
            tipoTramite = dyccTipoTramiteDAO.encontrar(idTipoTramite);
        } catch (SIATException e) {
            LOG.info("Error aviso --> " + e.getMessage());
        }
        return tipoTramite;
    }

    private String getHoraActual() {
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat(FORMATO_HORA);
        return formateador.format(ahora);
    }

    @Override
    public List<Date> validarHoraDeRegistroCompensacion() {
        List<Date> datesOfReturn = new ArrayList<Date>();

        try {
            DateFormat dateFormat = new SimpleDateFormat(FORMATO_HORA);
            SimpleDateFormat formateador = new SimpleDateFormat(FORMATO_FECHA);

            String horaActual = getHoraActual();
            Date hrInicial, hrLimete, hrTramite;
            hrInicial = dateFormat.parse(HORAINICIAL);
            hrLimete = dateFormat.parse(HORALIMITE);
            hrTramite = dateFormat.parse(horaActual);
            
            boolean consultarDiaHabilSig = Boolean.TRUE;
            if (diaHabilService.esHabil(new Date())) {
            
                Date horaInicioLab = dateFormat.parse(HORA_INICIAL_LABORAL);                
                if (hrTramite.before(horaInicioLab)) {
                    String ha = formateador.format(new Date());
                    formateador = new SimpleDateFormat(FORMATO_D_M_Y_H);
                    datesOfReturn.add(formateador.parse(ha + " " + HORA_INICIAL_LABORAL));
                    datesOfReturn.add(formateador.parse(ha + " " + HORA_INICIAL_LABORAL));
                    
                    consultarDiaHabilSig = Boolean.FALSE;
                } else if ((hrInicial.compareTo(hrTramite) <= ConstantesDyCNumerico.VALOR_0) &&
                    (hrLimete.compareTo(hrTramite) >= ConstantesDyCNumerico.VALOR_0)) {
                    datesOfReturn.add(new Date());
                    datesOfReturn.add(null);
                    consultarDiaHabilSig = Boolean.FALSE;
                }
            }
            
            if(consultarDiaHabilSig) {
                String ha = null;
                Date fecha = null;

                try {
                    fecha = diaHabilService.buscarDiaFederalRecaudacion(new Date(), ConstantesDyCNumerico.VALOR_1);
                    ha = formateador.format(fecha);
                } catch (Exception e) {
                    LOG.error(e.getMessage());
                }
                formateador = new SimpleDateFormat(FORMATO_D_M_Y_H);
                datesOfReturn.add(formateador.parse(ha + " " + HORA_INICIAL_LABORAL));
                datesOfReturn.add(formateador.parse(ha + " " + HORA_INICIAL_LABORAL));

            }
        } catch (ParseException pe) {
            LOG.error(pe.getMessage());
        }

        return datesOfReturn;
    }

    @Override
    public List<DatosDestinoAcuseVO> obtieneDestinoParaAcuse(String folioAviso) {
        return avisoCompensacionDAO.obtieneDestinoParaAcuse(folioAviso);
    }

    @Override
    public List<DatosOrigenAcuseVO> obtieneOrigenParaAcuse(String folioAviso) {
        return avisoCompensacionDAO.obtieneOrigenParaAcuse(folioAviso);
    }

    @Override
    public List<AnexoVO> buscarAnexosPorNumcontrol(String numcontrol) {
        return anexoDAO.buscarAnexosPorNumcontrol(numcontrol);
    }

    @Override
    public List<ArchivoVO> buscaArchivosPorFolioAviso(String folioAviso) {
        return archivoDAO.buscaArchivosPorFolioAviso(folioAviso);
    }

    @Override
    public List<DyctDeclaraIcepDTO> buscaAvisoXNumControlRemanente(String numControlRemanente,
                                                                   String rfcContribuyente ){
        List<DyctDeclaraIcepDTO> declaracion = new ArrayList<DyctDeclaraIcepDTO>();
        try {
            declaracion = dyctDeclaraIcepDAO.buscaDeclaraOrigenXNumCntrol(numControlRemanente, rfcContribuyente);
        } catch (SIATException e) {
            LOG.info("Error aviso --> " + e.getMessage());
        }
        return declaracion;
    }

    @Override
    public DycpAvisoCompDTO buscaAvisoCompensacion(String folioAviso) throws SIATException {
        return dycpAvisoCompDAO.encontrar(folioAviso);
    }

    @Override
    public DyctSaldoIcepDTO encontrarIcep(String rfc, int idConcepto, int idEjercicio, int idPeriodo) {
      return  dyctSaldoIcepDAO.encontrarIcep(rfc, idConcepto, idEjercicio, idPeriodo);
    }

   public DyctSaldoIcepDAO getDyctSaldoIcepDAO() {
        return dyctSaldoIcepDAO;
    }

    public void setDyctSaldoIcepDAO(DyctSaldoIcepDAO dyctSaldoIcepDAO) {
        this.dyctSaldoIcepDAO = dyctSaldoIcepDAO;
    }

}


