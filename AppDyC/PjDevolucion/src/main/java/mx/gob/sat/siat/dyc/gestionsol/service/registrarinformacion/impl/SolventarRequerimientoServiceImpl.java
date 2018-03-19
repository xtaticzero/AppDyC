/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
 */
package mx.gob.sat.siat.dyc.gestionsol.service.registrarinformacion.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.archivo.DyctArchivoInfReqDAO;
import mx.gob.sat.siat.dyc.dao.archivo.DyctOtraArchivoDAO;
import mx.gob.sat.siat.dyc.dao.documento.DyctDocumentoDAO;
import mx.gob.sat.siat.dyc.dao.req.DyctInfoRequeridaDAO;
import mx.gob.sat.siat.dyc.dao.usuario.DyctContribuyenteDAO;
import mx.gob.sat.siat.dyc.domain.anexo.DyccAnexoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.anexo.DyccMatrizAnexosDTO;
import mx.gob.sat.siat.dyc.domain.anexo.DyctAnexoReqDTO;
import mx.gob.sat.siat.dyc.domain.archivo.DyctOtraArchivoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctArchivoInfReqDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctInfoRequeridaDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctOtraInfoReqDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctReqInfoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctContribuyenteDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccInfoARequerirDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccInfoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.generico.util.ArchivoCargaDescarga;
import mx.gob.sat.siat.dyc.generico.util.common.Selladora;
import mx.gob.sat.siat.dyc.generico.util.common.Utilerias;
import mx.gob.sat.siat.dyc.gestionsol.dao.registrarinformacion.SolventarRequerimientoDAO;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.AnexoRequeridoDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.ArchivoSolventadoDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.CadenaCompensacionDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.CadenaSolicitudDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.DocRequeridoDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.EstatusRequerimientoDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.NotaExpedienteDTO;
import mx.gob.sat.siat.dyc.gestionsol.service.acuserecibo.AcuseReciboService;
import mx.gob.sat.siat.dyc.gestionsol.service.registrarinformacion.SolventarRequerimientoService;
import mx.gob.sat.siat.dyc.gestionsol.util.recurso.UtileriasGestionSol;
import mx.gob.sat.siat.dyc.gestionsol.vo.solventacion.SolventacionRequerimientoVO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesArchivo;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCURL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author David Guerrero Reyes
 * @author Jesus Alfredo Hernandez Orozco
 * @since 13/11/2013
 *
 */
@Service(value = "solventarRequerimientoService")
public class SolventarRequerimientoServiceImpl implements SolventarRequerimientoService {

    private static final String TIPO = "1";

    @Autowired(required = true)
    private SolventarRequerimientoDAO solventarRequerimientoDAO;

    @Autowired
    private DyctArchivoInfReqDAO dyctArchivoInfReqDAO;

    @Autowired
    private DyctDocumentoDAO dyctDocumentoDAO;

    @Autowired
    private DyctInfoRequeridaDAO dyctInfoRequeridaDAO;

    @Autowired
    private DyctOtraArchivoDAO dyctOtraArchivoDAO;

    @Autowired
    private DyctContribuyenteDAO dyctContribuyenteDAO;

    @Autowired
    private AcuseReciboService acuseReciboService;

    /**
     * Consulta el estatus en el que se encuentra el requerimiento.
     *
     * @param numeroControlDoc
     * @param numeroControl
     * @return
     */
    @Override
    public EstatusRequerimientoDTO consultaEstatusReq(String numeroControlDoc, String numeroControl) throws SIATException {
        return solventarRequerimientoDAO.consultaEstatusReq(numeroControlDoc, numeroControl);
    }

    /**
     * Consulta los documentos requeridos e inicializa la lista de los archivos
     * solventados
     *
     * @param numeroControl
     * @param numControlDoc
     * @return
     */
    @Override
    public List<DocRequeridoDTO> consultaDocumentosReq(String numeroControl, String numControlDoc) throws SIATException {
        List<DocRequeridoDTO> lstDocRequerido = solventarRequerimientoDAO.consultaDocumentosReq(numeroControl, numControlDoc);
        for (DocRequeridoDTO objeto : lstDocRequerido) {
            objeto.setArchivoSol(new ArrayList<ArchivoSolventadoDTO>());
        }
        return lstDocRequerido;
    }

    /**
     * Consulta los anexos requeridos para solventar el requerimiento e
     * inicializa las listas de los archivos solventados
     *
     * @param numeroControl
     * @param numControlDoc
     * @return
     */
    @Override
    public List<AnexoRequeridoDTO> consultaAnexoReq(String numeroControl, String numControlDoc) throws SIATException {
        List<AnexoRequeridoDTO> lstAnexoRequerido = solventarRequerimientoDAO.consultaAnexoReq(numeroControl, numControlDoc);
        for (AnexoRequeridoDTO objeto : lstAnexoRequerido) {
            objeto.setListaArchivosSolventados(new ArrayList<ArchivoSolventadoDTO>());
        }
        return lstAnexoRequerido;
    }

    /**
     * Guarda las notas ingresadas por el contribuyente al adjuntar información
     * adicional a la solicitud o compensación.
     *
     * @param notaExpediente
     * @throws SIATException
     */
    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW,
            rollbackFor = {SIATException.class})
    public void insertaComentarioSolventacion(NotaExpedienteDTO notaExpediente) throws SIATException {
        try {
            solventarRequerimientoDAO.insertaComentarioSolventacion(notaExpediente);
        } catch (Exception e) {
            throw new SIATException(e);
        }
    }

    /**
     * Actualiza el requerimiento cuando este se encuentra vencido.
     *
     * @param idEstadoReq
     * @param numControl
     * @param numControlDoc
     * @throws SIATException
     */
    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void actualizaRequerimientoVencido(Integer idEstadoReq, String numControl, String numControlDoc) throws SIATException {
        try {
            solventarRequerimientoDAO.actualizaRequerimientoVencido(idEstadoReq, numControl, numControlDoc);
        } catch (Exception e) {
            throw new SIATException(e);
        }
    }

    /**
     * Actualiza los siguientes datos: - El estado de la solicitud. - El
     * requerimiento vencido. - La informacion del requerimiento. - El
     * seguimiento. - Los comentarios para la solventacion
     *
     * @param objetoSolventar
     *
     * @throws SIATException
     */
    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED,
            rollbackFor = {SIATException.class})
    public void actualizarInformacion(SolventacionRequerimientoVO objetoSolventar) throws SIATException {

        String cadena = "";
        String sello = "";
        CadenaCompensacionDTO objetoCadenaCompensacion = null;
        CadenaSolicitudDTO objetoCadenaSolicitud = null;
        Selladora selladora = new Selladora();
        UtileriasGestionSol utilerias = new UtileriasGestionSol();
        try {
            setDocumentosRequeridos(objetoSolventar.getIdTipoTramite(), objetoSolventar.getNumControlDoc(), objetoSolventar.getLstDocumentosReqDTO(), objetoSolventar.getUrlArchivo());
            setAnexosRequeridos(objetoSolventar.getLstAnexosReqDTO(), objetoSolventar.getUrlArchivo());

            solventarRequerimientoDAO.actualizaRequerimientoVencido(objetoSolventar.getIdEstadoReq(), objetoSolventar.getNumControl(), objetoSolventar.getNumControlDoc());
            solventarRequerimientoDAO.actualizaReqInfo(objetoSolventar.getNumControlDoc());
            solventarRequerimientoDAO.insertaSeguimiento(objetoSolventar.getSeguimientoDTO());
            if (objetoSolventar.getNotaExpedienteDTO().getDescripcion() != null && objetoSolventar.getNotaExpedienteDTO().getDescripcion().length() > 0) {
                solventarRequerimientoDAO.insertaComentarioSolventacion(objetoSolventar.getNotaExpedienteDTO());
            }
            if (objetoSolventar.getNumControl().startsWith("AC") || objetoSolventar.getNumControl().startsWith("CC")) {
                solventarRequerimientoDAO.actualizaCompensacion(objetoSolventar.getIdEstadoSol(), objetoSolventar.getNumControl());
                objetoCadenaCompensacion = solventarRequerimientoDAO.consultarDatosDeCadenaDeUnaCompensacion(objetoSolventar.getNumControl(), objetoSolventar.getNumControlDoc());
                cadena = utilerias.generarCadenaParaSolventarRequerimientoCompensacion(objetoCadenaCompensacion);
            } else {
                solventarRequerimientoDAO.actualizaSolicitud(objetoSolventar.getIdEstadoSol(), objetoSolventar.getNumControl());
                objetoCadenaSolicitud = solventarRequerimientoDAO.consultarDatosDeCadenaDeUnaSolicitud(objetoSolventar.getNumControl(), objetoSolventar.getNumControlDoc());
                cadena = utilerias.generarCadenaParaSolventarRequerimientoSolicitud(objetoCadenaSolicitud);
            }
            sello = Utilerias.isNull(selladora.retornarParametros(TIPO, cadena));
            solventarRequerimientoDAO.actualizaCadenaYSello(objetoSolventar.getNumControlDoc(), cadena, sello);
            acuseReciboService.mostrarAcuseContestarRequerimiento(objetoSolventar.getNumControlDoc());

        } catch (Exception e) {
            throw new SIATException(e);
        }
    }

    /**
     * Guarda los documentos requeridos que fueron adjuntados por el
     * contribuyente en pantalla y los guarda en el file system y los registra
     * en base de datos.
     *
     * @param idTipoTramite
     * @param numControlDoc
     * @param lstDocumentosReqDTO
     * @param urlArchivo
     * @throws SIATException
     */
    private void setDocumentosRequeridos(Integer idTipoTramite, String numControlDoc, List<DocRequeridoDTO> lstDocumentosReqDTO, String urlArchivo) throws SIATException {

        ArchivoCargaDescarga archivo = new ArchivoCargaDescarga();
        DyctArchivoInfReqDTO dyctArchivoInfReqDTO = null;
        DyccInfoARequerirDTO dyccInfoARequerirDTO = null;
        DyctInfoRequeridaDTO dyctInfoRequeridaDTO = null;

        DyctReqInfoDTO dyctReqInfoDTO = new DyctReqInfoDTO();
        DyccInfoTramiteDTO dyccInfoTramiteDTO = new DyccInfoTramiteDTO();
        DyccTipoTramiteDTO dyccTipoTramiteDTO = new DyccTipoTramiteDTO();
        dyccTipoTramiteDTO.setIdTipoTramite(idTipoTramite);
        dyctReqInfoDTO.setNumControlDoc(numControlDoc);

        try {
            for (DocRequeridoDTO documentoReq : lstDocumentosReqDTO) {
                dyctInfoRequeridaDTO = new DyctInfoRequeridaDTO();
                dyctArchivoInfReqDTO = new DyctArchivoInfReqDTO();
                dyccInfoARequerirDTO = new DyccInfoARequerirDTO();
                dyctInfoRequeridaDTO.setDyctReqInfoDTO(dyctReqInfoDTO);
                dyctArchivoInfReqDTO.setUrl(urlArchivo + ConstantesDyCURL.TIPO_DOC_INFO_REQUERIDA);
                dyccInfoARequerirDTO.setIdInfoArequerir(documentoReq.getIdInfoARequerir());
                dyccInfoTramiteDTO.setDyccInfoARequerirDTO(dyccInfoARequerirDTO);
                dyccInfoTramiteDTO.setDyccTipoTramiteDTO(dyccTipoTramiteDTO);
                dyctInfoRequeridaDTO.setDyccInfoTramiteDTO(dyccInfoTramiteDTO);

                for (ArchivoSolventadoDTO archivoSolventado : documentoReq.getArchivoSol()) {
                    dyctArchivoInfReqDTO.setNombreArchivo(archivoSolventado.getNombre());
                    dyctArchivoInfReqDTO.setDyctInfoRequeridaDTO(dyctInfoRequeridaDTO);

                    if (dyctInfoRequeridaDAO.buscar(asignarValoresInfoRequerida(numControlDoc, idTipoTramite, documentoReq.getIdInfoARequerir())) != null) {
                        dyctArchivoInfReqDAO.insertar(dyctArchivoInfReqDTO);
                    } else {
                        insertarEnOtraArchivo(dyctArchivoInfReqDTO);
                    }
                    archivo.escribirArchivo(archivoSolventado.getNombre(),
                            archivoSolventado.getArchivoFisico().getInputstream(),
                            urlArchivo + ConstantesDyCURL.TIPO_DOC_INFO_REQUERIDA,
                            ConstantesDyCNumerico.VALOR_4096);
                }
            }
        } catch (Exception e) {
            throw new SIATException(e);
        }
    }

    /**
     * Prepara los datos para insertar en la tabla de DYCT_OTRAARCHIVO
     *
     * @param dyctArchivoInfReqDTO
     * @throws SIATException
     */
    private void insertarEnOtraArchivo(DyctArchivoInfReqDTO dyctArchivoInfReqDTO) throws SIATException {
        DyctOtraArchivoDTO dyctOtraArchivoDTO = new DyctOtraArchivoDTO();
        DyctOtraInfoReqDTO dyctOtraInfoReqDTO = new DyctOtraInfoReqDTO();
        dyctOtraInfoReqDTO.setIdOtraInfoReq(dyctArchivoInfReqDTO.getDyctInfoRequeridaDTO().getDyccInfoTramiteDTO().getDyccInfoARequerirDTO().getIdInfoArequerir());
        dyctOtraArchivoDTO.setDyctOtraInfoReqDTO(dyctOtraInfoReqDTO);

        Integer numero = solventarRequerimientoDAO.retornarNumeroDeArchivo(dyctArchivoInfReqDTO.getDyctInfoRequeridaDTO().getDyccInfoTramiteDTO().getDyccInfoARequerirDTO().getIdInfoArequerir());
        if (numero == null) {
            numero = 1;
        }
        dyctOtraArchivoDTO.setNumeroArchivo(numero);
        dyctOtraArchivoDTO.setUrl(dyctArchivoInfReqDTO.getUrl());
        dyctOtraArchivoDTO.setNombreArchivo(dyctArchivoInfReqDTO.getNombreArchivo());

        dyctOtraArchivoDAO.insertar(dyctOtraArchivoDTO);
    }

    /**
     * Guarda los anexos requeridos que fueron adjuntados por el contribuyente
     * en pantalla y los guarda en el file system y los registra en base de
     * datos.
     *
     * @param lstAnexosReqDTO
     * @param urlArchivo
     * @throws SIATException
     */
    private void setAnexosRequeridos(List<AnexoRequeridoDTO> lstAnexosReqDTO, String urlArchivo) throws SIATException {

        ArchivoCargaDescarga archivo = new ArchivoCargaDescarga();
        DyctAnexoReqDTO dyctAnexoReqDTO = null;
        DyccAnexoTramiteDTO dyccAnexoTramiteDTO = null;
        DyccMatrizAnexosDTO dyccMatrizAnexosDTO = null;
        DyctReqInfoDTO dyctReqInfoDTO = null;
        DyccTipoTramiteDTO dyccTipoTramiteDTO = null;
        Integer idDocumentoSeq = null;

        for (AnexoRequeridoDTO anexoReq : lstAnexosReqDTO) {
            dyctAnexoReqDTO = new DyctAnexoReqDTO();

            if (anexoReq.getArchivoSol() != null
                    && anexoReq.getArchivoSol().getEstatus().equals(ConstantesArchivo.EDO_ANEXO_ADJUNTADO)) {

                try {
                    dyctReqInfoDTO = new DyctReqInfoDTO();
                    dyccAnexoTramiteDTO = new DyccAnexoTramiteDTO();
                    dyccMatrizAnexosDTO = new DyccMatrizAnexosDTO();
                    dyccTipoTramiteDTO = new DyccTipoTramiteDTO();
                    String fileName = anexoReq.getArchivoSol().getArchivoFisico().getFileName();
                    fileName = fileName.substring(fileName.lastIndexOf('\\') + 1, fileName.length());
                    archivo.escribirArchivo(fileName,
                            anexoReq.getArchivoSol().getArchivoFisico().getInputstream(),
                            urlArchivo + ConstantesDyCURL.TIPO_DOC_ANEXO_REQUERIDO,
                            ConstantesDyCNumerico.VALOR_4096);

                    anexoReq.setUrl(anexoReq.getNumRequerimiento());

                    idDocumentoSeq = solventarRequerimientoDAO.getIdDocumento();
                    dyctAnexoReqDTO.setUrl(urlArchivo + ConstantesDyCURL.TIPO_DOC_ANEXO_REQUERIDO);
                    dyctAnexoReqDTO.setNombreArchivo(anexoReq.getArchivoSol().getNombre());
                    dyctReqInfoDTO.setNumControlDoc(anexoReq.getNumRequerimiento());
                    dyctAnexoReqDTO.setDyctReqInfoDTO(dyctReqInfoDTO);
                    dyccTipoTramiteDTO.setIdTipoTramite(anexoReq.getIdTipoTramite());
                    dyccMatrizAnexosDTO.setIdAnexo(anexoReq.getIdAnexo());
                    dyccAnexoTramiteDTO.setDyccMatrizAnexosDTO(dyccMatrizAnexosDTO);
                    dyccAnexoTramiteDTO.setDyccTipoTramiteDTO(dyccTipoTramiteDTO);
                    dyctAnexoReqDTO.setDyccAnexoTramiteDTO(dyccAnexoTramiteDTO);

                    solventarRequerimientoDAO.actualizaAnexo(dyctAnexoReqDTO);

                    anexoReq.getArchivoSol().setIdConsecutivoDoc(idDocumentoSeq);
                    anexoReq.getArchivoSol().setEstatus(ConstantesArchivo.EDO_ANEXO_ADJUNTADO);

                } catch (Exception e) {
                    throw new SIATException(e);
                }
            }
        }
    }

    /**
     * Se utiliza para buscar si se va a insertar en dyct_ArchivoInfReq o en
     * dyct_otraArchivo.
     *
     * @param numControlDoc
     * @param idTipoTramite
     * @param idInfoARequerir
     * @return
     */
    private DyctInfoRequeridaDTO asignarValoresInfoRequerida(String numControlDoc, Integer idTipoTramite, Integer idInfoARequerir) {
        DyctInfoRequeridaDTO objeto = new DyctInfoRequeridaDTO();
        DyctReqInfoDTO dyctReqInfoDTO = new DyctReqInfoDTO();
        DyccInfoTramiteDTO dyccInfoTramiteDTO = new DyccInfoTramiteDTO();
        DyccTipoTramiteDTO dyccTipoTramiteDTO = new DyccTipoTramiteDTO();
        DyccInfoARequerirDTO dyccInfoARequerirDTO = new DyccInfoARequerirDTO();

        dyctReqInfoDTO.setNumControlDoc(numControlDoc);

        dyccTipoTramiteDTO.setIdTipoTramite(idTipoTramite);
        dyccInfoTramiteDTO.setDyccTipoTramiteDTO(dyccTipoTramiteDTO);

        dyccInfoARequerirDTO.setIdInfoArequerir(idInfoARequerir);
        dyccInfoTramiteDTO.setDyccInfoARequerirDTO(dyccInfoARequerirDTO);

        objeto.setDyctReqInfoDTO(dyctReqInfoDTO);
        objeto.setDyccInfoTramiteDTO(dyccInfoTramiteDTO);

        return objeto;
    }

    /**
     * Consulta los datos del contribuyente a traves del numero de control
     *
     * @param numControl
     * @return
     * @throws SIATException
     */
    @Override
    public DyctContribuyenteDTO encontrarContribuyente(String numControl) throws SIATException {
        DyctContribuyenteDTO objeto = null;
        try {
            objeto = dyctContribuyenteDAO.encontrar(numControl);
        } catch (Exception e) {
            throw new SIATException(e);
        }
        return objeto;
    }

    @Override
    public DyctDocumentoDTO consultaDocCuentaClabeXSolventar(String numControl) throws SIATException {
        DyctDocumentoDTO objetoDocumento = null;
        try {
            objetoDocumento = dyctDocumentoDAO.consultarDocumentoSolventarCtaClabe(numControl);
        } catch (Exception e) {
            throw new SIATException(e);
        }

        return objetoDocumento;
    }
}
