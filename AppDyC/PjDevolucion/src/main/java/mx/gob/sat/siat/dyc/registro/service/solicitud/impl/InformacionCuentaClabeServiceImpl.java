package mx.gob.sat.siat.dyc.registro.service.solicitud.impl;

import java.io.File;
import java.io.IOException;

import mx.gob.sat.siat.dyc.domain.archivo.DyctArchivoDTO;
import mx.gob.sat.siat.dyc.generico.util.ArchivoCargaDescarga;
import mx.gob.sat.siat.dyc.generico.util.common.Selladora;
import mx.gob.sat.siat.dyc.generico.util.common.UsuarioFirmadoVO;
import mx.gob.sat.siat.dyc.generico.util.common.Utilerias;
import mx.gob.sat.siat.dyc.gestionsol.dao.registrarinformacion.SolventarRequerimientoDAO;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.CadenaSolicitudDTO;
import mx.gob.sat.siat.dyc.gestionsol.util.recurso.UtileriasGestionSol;
import mx.gob.sat.siat.dyc.gestionsol.vo.solventacion.SolventacionRequerimientoVO;
import mx.gob.sat.siat.dyc.registro.service.solicitud.InformacionCuentaClabeService;
import mx.gob.sat.siat.dyc.service.DyctArchivoService;
import mx.gob.sat.siat.dyc.service.DyctCuentaBancoService;
import mx.gob.sat.siat.dyc.service.DyctExpedienteService;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCURL;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "informacionCuentaClabeService")
public class InformacionCuentaClabeServiceImpl implements InformacionCuentaClabeService {

    private Logger log = Logger.getLogger(InformacionCuentaClabeServiceImpl.class);

    private static final String TIPO = "1";

    @Autowired
    private DyctArchivoService dyctArchivoService;
    @Autowired
    private DyctExpedienteService dyctExpedienteService;
    @Autowired
    private DyctCuentaBancoService dyctCuentaBancoService;
    @Autowired(required = true)
    private SolventarRequerimientoDAO solventarRequerimientoDAO;

    public InformacionCuentaClabeServiceImpl() {
        super();
    }

    @Override
    @Transactional
    public void actualizarCuentaClabe(UsuarioFirmadoVO usuarioFirmado, SolventacionRequerimientoVO objetoSolventar) throws SIATException {
        try {
            String cadena = "";
            String sello = "";
            CadenaSolicitudDTO objetoCadenaSolicitud = null;
            UtileriasGestionSol utilerias = new UtileriasGestionSol();
            Selladora selladora = new Selladora();
            DyctArchivoDTO archivoEliminar
                    = dyctArchivoService.buscaArchivoXNumCtrl(usuarioFirmado.getInfoCuentaClabeFieldDTO().getDyctCuentaBanco().getDycpSolicitudDTO().getNumControl());
            int idArchivo = archivoEliminar.getIdArchivo();
            File archivo = new File(archivoEliminar.getUrl() + "/" + archivoEliminar.getNombreArchivo());
            if (archivo.exists() && archivo.delete()) {
                log.info("Correcto");
            }
            /**
             * Actualizar el archivo
             */
            ArchivoCargaDescarga cargaArchivo = new ArchivoCargaDescarga();
            DyctArchivoDTO dyctArchivo = new DyctArchivoDTO();
            StringBuilder ruta = new StringBuilder();
            dyctArchivo.setNombreArchivo(usuarioFirmado.getInfoCuentaClabeFieldDTO().getNomCorrecto());
            dyctArchivo.setDescripcion(usuarioFirmado.getInfoCuentaClabeFieldDTO().getNomCorrecto());
            /**
             * ruta.append(ConstantesDyCURL.DOCUMENTOS_ADJUNTOS).append(usuarioFirmado.getInfoCuentaClabeFieldDTO().getAdminD().substring(2)).append("/").append(usuarioFirmado.getInfoCuentaClabeFieldDTO().getRfc()).append("/").append(usuarioFirmado.getInfoCuentaClabeFieldDTO().getDyctCuentaBanco().getDycpSolicitudDTO().getNumControl()).append("/").append("Archivos");
             */
            ruta.append(ConstantesDyCURL.DOCUMENTOS_ADJUNTOS).
                    append(usuarioFirmado.getInfoCuentaClabeFieldDTO().getAdminD().substring(2)).
                    append("/").
                    append(Utilerias.corregirRFC(usuarioFirmado.getInfoCuentaClabeFieldDTO().getRfc())).
                    append("/").
                    append(usuarioFirmado.getInfoCuentaClabeFieldDTO().getDyctCuentaBanco().getDycpSolicitudDTO().getNumControl()).
                    append("/").
                    append("Archivos");
            dyctArchivo.setUrl(ruta.toString());

            cargaArchivo.escribirArchivo(dyctArchivo.getNombreArchivo(),
                    usuarioFirmado.getInfoCuentaClabeFieldDTO().getFile().getInputstream(),
                    ruta.toString(), ConstantesDyCNumerico.VALOR_4096);

            dyctArchivoService.actualizarArchivo(dyctArchivo, idArchivo);

            dyctCuentaBancoService.actualizaCuenta(usuarioFirmado.getInfoCuentaClabeFieldDTO().getDyctCuentaBanco());

            if (usuarioFirmado.getInfoCuentaClabeFieldDTO().getOpcionMani().equals("SIA")) {
                log.info("NumCtrl V --> "
                        + usuarioFirmado.getInfoCuentaClabeFieldDTO().getDyctCuentaBanco().getDycpSolicitudDTO().getNumControl());
                dyctExpedienteService.actualizar(ConstantesDyCNumerico.VALOR_1,
                        usuarioFirmado.getInfoCuentaClabeFieldDTO().getDyctCuentaBanco().getDycpSolicitudDTO().getNumControl());
            } else {
                log.info("NumCtrl F --> "
                        + usuarioFirmado.getInfoCuentaClabeFieldDTO().getDyctCuentaBanco().getDycpSolicitudDTO().getNumControl());
                dyctExpedienteService.actualizar(ConstantesDyCNumerico.VALOR_0,
                        usuarioFirmado.getInfoCuentaClabeFieldDTO().getDyctCuentaBanco().getDycpSolicitudDTO().getNumControl());
            }

            solventarRequerimientoDAO.actualizaRequerimientoVencido(objetoSolventar.getIdEstadoReq(), objetoSolventar.getNumControl(), objetoSolventar.getNumControlDoc());
            solventarRequerimientoDAO.actualizaReqInfo(objetoSolventar.getNumControlDoc());

            //Generacion de cadena y sello
            objetoCadenaSolicitud = solventarRequerimientoDAO.consultarDatosDeCadenaDeUnaSolicitud(objetoSolventar.getNumControl(), objetoSolventar.getNumControlDoc());
            cadena = utilerias.generarCadenaParaSolventarRequerimientoSolicitud(objetoCadenaSolicitud);
            sello = Utilerias.isNull(selladora.retornarParametros(TIPO, cadena));

            //Se actualiza cadena y sello en DYCT_REQINFO
            solventarRequerimientoDAO.actualizaCadenaYSello(objetoSolventar.getNumControlDoc(), cadena, sello);

        } catch (IOException e) {
            log.error("Error al generar el acuse de recibo o insertar la compensacion: " + e);
            throw new SIATException(e);
        } catch (SIATException e) {
            log.error("Error al generar el acuse de recibo o insertar la compensacion: " + e);
            throw new SIATException(e);
        }
    }
}
