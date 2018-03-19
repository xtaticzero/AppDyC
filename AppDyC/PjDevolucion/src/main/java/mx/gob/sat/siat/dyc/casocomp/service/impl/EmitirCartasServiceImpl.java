package mx.gob.sat.siat.dyc.casocomp.service.impl;

import java.io.File;
import java.io.InputStream;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.casocomp.service.EmitirCartasService;
import mx.gob.sat.siat.dyc.dao.compensacion.IDycpCompensacionDAO;
import mx.gob.sat.siat.dyc.dao.documento.DyctDocumentoDAO;
import mx.gob.sat.siat.dyc.dao.usuario.DyccAprobadorDAO;
import mx.gob.sat.siat.dyc.dao.usuario.DyctContribuyenteDAO;
import mx.gob.sat.siat.dyc.domain.documento.DyccMatDocumentosDTO;
import mx.gob.sat.siat.dyc.domain.documento.DyccTipoDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccEstadoCompDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;
import mx.gob.sat.siat.dyc.generico.util.ArchivoCargaDescarga;
import mx.gob.sat.siat.dyc.template.service.TemplateNumberService;
import mx.gob.sat.siat.dyc.util.BuscadorConstantes;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import mx.gob.sat.siat.dyc.util.constante.ConstantesACC;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("serviceEmitirCartas")
public class EmitirCartasServiceImpl implements EmitirCartasService
{
    private static final Logger LOG = Logger.getLogger(EmitirCartasServiceImpl.class);

    @Autowired
    private DyctDocumentoDAO daoDocumento;

    @Autowired
    private DyccAprobadorDAO daoAprobador;
    
    @Autowired
    private DyctContribuyenteDAO dyctContribuyenteDAO;
    
    @Autowired
    private IDycpCompensacionDAO daoCompensacion;
    
    @Autowired
    private TemplateNumberService servicePlantillador;

    public static final String KEY_NUMCONTROL = "numControl";
    public static final String KEY_NUMCONTROLDOC = "numControlDoc";
    public static final String KEY_NOMBREARCHIVO = "nombreArchivo";
    public static final String KEY_SUCCESS = "success";
    public static final String KEY_MENSAJE = "mensaje";

    static final DyccEstadoCompDTO[] ESTATUS_VALIDOS =
        new DyccEstadoCompDTO[] { Constantes.EstadosCompensacion.EN_PROCESO };

    /*
     * Si la carta que existe ya esta vencida se indicará que NO existe ninguna carta vinculada con el servicio
     */
    @Override
    public Map<String, Object> obtenerInfoInicial(Map<String, Object> params)
    {
        Map<String, Object> infoInicial = new HashMap<String, Object>();

        DycpServicioDTO servicio = new DycpServicioDTO();
        servicio.setNumControl((String)params.get(KEY_NUMCONTROLDOC));

        DyctDocumentoDTO carta = obtenerUltimaCartaPersistida(servicio, BuscadorConstantes.obtenerTipoDocumento((Integer)params.get("tipoCarta")));

        infoInicial.put("existe", Boolean.FALSE);

        if(carta != null)
        {
            if(estaVigente(carta))
            {
                infoInicial.put("existe", Boolean.TRUE);
                infoInicial.put(KEY_NUMCONTROLDOC, carta.getNumControlDoc());
                infoInicial.put("estatus", carta.getDyccEstadoDocDTO().getDescripcion());
                infoInicial.put("idEstatus", carta.getDyccEstadoDocDTO().getIdEstadoDoc());
                infoInicial.put("aprobador", carta.getDyccAprobadorDTO().getNombre()+carta.getDyccAprobadorDTO().getApellidoPaterno()+carta.getDyccAprobadorDTO().getApellidoMaterno());
                infoInicial.put(KEY_NOMBREARCHIVO, carta.getNombreArchivo());
            }
            else
            {
                desactivarCarta(carta);
            }
        }

        return infoInicial;
    }

    /*
     * Válida si existe un requerimiento vigente, si existe lo regresa, si no: crea y regresa el nuevo,
     * parametros regresados dentro del map: success, mensaje, numcontrolDoc, nombreArchivo y archivo
     */
    @Override
    public Map<String, Object> obtenerCarta(Map<String, Object> params)
    {
        Map<String, Object> respuesta = new HashMap<String, Object>();

        try
        {
            
            DycpCompensacionDTO compensacion = daoCompensacion.encontrar((String)params.get(KEY_NUMCONTROL));
            compensacion.setDyctContribuyenteDTO(dyctContribuyenteDAO.encontrar((String)params.get(KEY_NUMCONTROL)));
            if(!Arrays.asList(ESTATUS_VALIDOS).contains(compensacion.getDyccEstadoCompDTO())){
                respuesta.put(KEY_MENSAJE, "No es posible generar la carta debido a que el estatus de la compensación es '" + compensacion.getDyccEstadoCompDTO().getDescripcion() + "'");
                respuesta.put(KEY_SUCCESS, Boolean.FALSE);
                return respuesta;
            }

            DyccTipoDocumentoDTO tipoCarta = BuscadorConstantes.obtenerTipoDocumento((Integer)params.get("tipoCarta"));
            DyctDocumentoDTO carta = obtenerUltimaCartaPersistida(compensacion, tipoCarta);
            params.put("servicio", compensacion);
            params.put("tipoCarta", tipoCarta);
            params.put("tipoDocumento",tipoCarta);
            
            if(carta != null)
            {
                LOG.debug("El estatus de la ultima carta persistida es ->" + carta.getDyccEstadoDocDTO().getDescripcion());
                if(carta.getDyccEstadoDocDTO() == Constantes.EstadosDoc.GENERADO && carta.getDyccEstadoReqDTO() == Constantes.EstadosReq.EMITIDO){
                    if(estaVigente(carta)){
                        LOG.debug("La carta ya ha sido generada y esta vigente, se regresara la misma");
                    }
                    else
                    {
                        LOG.debug("La carta ya ha sido generada pero NO esta vigente, se procedera a 'desactivar' carta vencida y generar una nueva");
                        desactivarCarta(carta);
                        carta = crearNuevaCarta(params);
                    }
                }
                else if(carta.getDyccEstadoReqDTO() == Constantes.EstadosReq.RECHAZADO){
                    carta = crearNuevaCarta(params);
                }
                else{
                    LOG.debug("No es posible generar la carta debido a que ya existe una con el número de control '" + carta.getNumControlDoc() + "'" +
                                    " y se encuentra en estatus '" + carta.getDyccEstadoReqDTO().getDescripcion() + "'");
                    respuesta.put(KEY_MENSAJE, "No es posible generar la carta debido a que ya existe una con el número de control '" + carta.getNumControlDoc() + "'" +
                                    " y se encuentra en estatus '" + carta.getDyccEstadoReqDTO().getDescripcion() + "'");
                    respuesta.put(KEY_SUCCESS, Boolean.FALSE);
                    return respuesta;
                }
            }
            else
            {
                LOG.debug("La carta nunca ha sido generada, se solicitara generar y se guardara info ");
                carta = crearNuevaCarta(params);
            }

            respuesta.put(KEY_NOMBREARCHIVO, carta.getNombreArchivo());
            respuesta.put("archivo", obtenerArchivo(carta));
            respuesta.put(KEY_NUMCONTROLDOC, carta.getNumControlDoc());
            respuesta.put(KEY_SUCCESS, Boolean.TRUE);
        }
        catch(SIATException e)
        {
            respuesta.put(KEY_SUCCESS, Boolean.FALSE);
            respuesta.put(KEY_MENSAJE, e.getMessage());
        }

        return respuesta;
    }

    private File obtenerArchivo(DyctDocumentoDTO carta)
    {
        StringBuilder ruta = new StringBuilder();
        ruta.append(carta.getUrl());
        if('/' != ruta.charAt(ruta.length() - 1)){
            ruta.append("/");
        }
        ruta.append(carta.getNombreArchivo());
        return new File(ruta.toString());
    }

    private void desactivarCarta(DyctDocumentoDTO carta)
    {
        StringBuilder ruta = new StringBuilder();
        ruta.append(carta.getUrl());
        if('/' != ruta.charAt(ruta.length() - 1)){
            ruta.append("/");
        }
        ruta.append(carta.getNombreArchivo());
        File archivoVencido = new File(ruta.toString());
        if(!archivoVencido.delete())
        {
            LOG.error("NO se pudo borrar el archivo vencido ->" + archivoVencido + "<-");
        }

        String nomArchBorrado = carta.getNombreArchivo() + "_borrado";
        if(nomArchBorrado.length() > ConstantesACC.LONGITUD_MAX_NOMBRE_ARCHIVO){
            nomArchBorrado = carta.getNombreArchivo().substring(0, ConstantesACC.LONG_MAX_NOM_ARCHIVO_BORRADO) + "_borrado";
        }

        DyctDocumentoDTO dtoDocAux = new DyctDocumentoDTO();
        dtoDocAux.setNumControlDoc(carta.getNumControlDoc());
        dtoDocAux.setUrl("archivo_borrado_x_vencimiento");
        dtoDocAux.setNombreArchivo(nomArchBorrado);
        try {
            daoDocumento.actualizar(dtoDocAux);
        } catch (SIATException e) {
            LOG.error("Ocurrió un error al desactivarCarta ->" + e.getMessage());
            ManejadorLogException.getTraceError(e);
        }
    }

    /*
     * solicita crear un nuevo archivo e inserta un registro en DYCT_DOCUMENTO
     * parametros requeridos numControl(compensacion), tipoDocumento
     */
    
    private DyctDocumentoDTO crearNuevaCarta(Map<String, Object> params) throws SIATException
    {
        DycpServicioDTO servicio = (DycpServicioDTO)params.get("servicio");
        DyccTipoDocumentoDTO tipoDocumento = (DyccTipoDocumentoDTO)params.get("tipoDocumento");
        LOG.debug("tipoDocumento ->" + tipoDocumento.getDescripcion() + "<-");
        LOG.debug("contribuyente ->" + servicio.getDyctContribuyenteDTO() + "<-");
        Integer idPlantilla = 0;

        int claveAdm = (Integer)params.get("claveAdm");
        LOG.debug("claveAdm ->" + claveAdm);

        if(tipoDocumento == Constantes.TiposDocumento.CARTA_SOLIC_PRESENCIA)
        {
            LOG.debug("la carta es de tipo CARTA_SOLIC_PRESENCIA");
            if(claveAdm == ConstantesDyCNumerico.VALOR_81 || claveAdm == ConstantesDyCNumerico.VALOR_82) {
                idPlantilla = ConstantesDyCNumerico.VALOR_136;
            } else {
                idPlantilla = ConstantesACC.PLANTILLA_REQ_PRESC_CONTTE;    
            }
        }
        else if(tipoDocumento == Constantes.TiposDocumento.CARTA_INVITACION)
        {
            LOG.debug("la carta es de tipo CARTA_INVITACION");
                if(claveAdm == ConstantesDyCNumerico.VALOR_81 || claveAdm == ConstantesDyCNumerico.VALOR_82) {
                    idPlantilla = ConstantesDyCNumerico.VALOR_136;
                } else if (servicio.getDyctContribuyenteDTO().getRolGranContrib() ==
                    ConstantesDyCNumerico.VALOR_1) {
                    idPlantilla = ConstantesACC.PLANTILLA_CARTA_INVITACION_GC;
                } else {
                    idPlantilla = ConstantesACC.PLANTILLA_CARTA_INVITACION;
                }
        }

        DyccMatDocumentosDTO plantilla = new DyccMatDocumentosDTO();
        plantilla.setIdPlantilla(idPlantilla);
        LOG.debug("idPlantilla ->" + idPlantilla + "<-");

        Map<String, Object> paramsPlantillador = new HashMap<String, Object>();
        paramsPlantillador.put("idPlantilla", idPlantilla);
        paramsPlantillador.put(KEY_NUMCONTROL, servicio.getNumControl());
        paramsPlantillador.put("queryAConsultar", ConstantesACC.NUM_QUERY_CASOS_COMPENSACION);
        paramsPlantillador.put("claveAdm", claveAdm);

        Map<String, Object> respPlantillador = servicePlantillador.templateCreated(paramsPlantillador);
        if(!(Boolean)respPlantillador.get(KEY_SUCCESS))
        {
            throw new SIATException("Ocurrio un error en el plantillador");
        }

        String nombreArchivo = (String)respPlantillador.get(KEY_NOMBREARCHIVO);
        LOG.debug("nombreArchivo ->" + nombreArchivo + "<-");

        DyctDocumentoDTO documento = new DyctDocumentoDTO();
        documento.setFechaRegistro(new Date());
        documento.setDyccEstadoReqDTO(Constantes.EstadosReq.EMITIDO);
        documento.setDyccEstadoDocDTO(Constantes.EstadosDoc.GENERADO);
        documento.setNombreArchivo(nombreArchivo);
        // TODO: Validar una forma menos expuesta de obtener el numControlDoc
        documento.setNumControlDoc(nombreArchivo.split("_")[ConstantesDyCNumerico.VALOR_0]);
        documento.setUrl((String)respPlantillador.get("url"));
        documento.setDycpServicioDTO(servicio);
        documento.setDyccTipoDocumentoDTO(tipoDocumento);
        documento.setDyccMatDocumentosDTO(plantilla);
        daoDocumento.insertarE(documento);
        
        return documento;
    }

    private Boolean estaVigente(DyctDocumentoDTO carta)
    {
        LOG.debug("INICIO estaVigente");
        Date fechaVigencia = (Date)carta.getFechaRegistro().clone();
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaVigencia);

        cal.set(Calendar.HOUR_OF_DAY, ConstantesACC.HORA_VIGENCIA_CARTA);
        cal.set(Calendar.MINUTE, ConstantesACC.MINUTO_VIGENCIA_CARTA);
        cal.set(Calendar.SECOND, ConstantesACC.SEGUNDO_VIGENCIA_CARTA);
        return new Date().before(cal.getTime());
    }

    @Override
    public Map<String, Object> enviarAAprobacion(Map<String, Object> params)
    {
        LOG.debug("INICIO enviarAAprobacion");
        InputStream secuenciaEntrada = (InputStream)params.get("secuenciaEntrada");
        String nombreArchivoR = (String)params.get(KEY_NOMBREARCHIVO);
        Integer numEmpleadoAprobador = (Integer)params.get("numEmpleadoAprobador");
        String numControlDoc = (String)params.get(KEY_NUMCONTROLDOC);

        LOG.debug("params ->" + params + "<-");

        Map<String, Object> respuesta = new HashMap<String, Object>();

        try
        {
            DyctDocumentoDTO carta = daoDocumento.encontrar(numControlDoc);
            String nombreArchivoBD = carta.getNombreArchivo();
            LOG.debug("nombreArchivoBD ->" + nombreArchivoBD + "<-");
            if(nombreArchivoBD.equals(nombreArchivoR))
            {
                new ArchivoCargaDescarga().escribirArchivo(nombreArchivoR, secuenciaEntrada, carta.getUrl(), ConstantesDyCNumerico.VALOR_4096);

                DyccAprobadorDTO aprobador = new DyccAprobadorDTO();
                aprobador.setNumEmpleado(numEmpleadoAprobador);

                DyctDocumentoDTO documento = new DyctDocumentoDTO();
                documento.setNumControlDoc(numControlDoc);
                documento.setDyccAprobadorDTO(aprobador);
                documento.setDyccEstadoDocDTO(Constantes.EstadosDoc.EN_APROBACION);
                daoDocumento.actualizar(documento);
                
                DycpCompensacionDTO compensacion = new DycpCompensacionDTO();
                String numControl = (String)params.get(KEY_NUMCONTROL);
                compensacion.setNumControl(numControl);
                compensacion.setDyccAprobadorDTO(aprobador);
                compensacion.setDyccEstadoCompDTO(Constantes.EstadosCompensacion.EN_REVISION);
                daoCompensacion.actualizar(compensacion);

                String mensaje = "El requerimiento " + numControlDoc + " se envió a aprobacion satisfactoriamente";

                respuesta.put("seEnvioAAprobacion", Boolean.TRUE);
                respuesta.put(KEY_MENSAJE, mensaje);
            }
            else
            {
                String mensaje = "El requerimiento " + numControlDoc + " no se envió a aprobación porque el nombre del archivo " +
                    "recibido '" + nombreArchivoR + "' NO coincide con el vigente '" + nombreArchivoBD + "'";
                LOG.info(mensaje);
                respuesta.put("seEnvioAAprobacion", Boolean.FALSE);
                respuesta.put(KEY_MENSAJE, mensaje);
            }
            respuesta.put(KEY_SUCCESS, Boolean.TRUE);
        }
        catch(Exception e)
        {
            respuesta.put(KEY_SUCCESS, Boolean.FALSE);
            respuesta.put(KEY_MENSAJE, e.getMessage());
        }

        return respuesta;
    }

    private DyctDocumentoDTO obtenerUltimaCartaPersistida(DycpServicioDTO servicio, DyccTipoDocumentoDTO tipoDocumento)
    {
        LOG.debug("INICIO obtenerCarta");
        List<DyctDocumentoDTO> docs = daoDocumento.selecXServicioTipodocumento(servicio, tipoDocumento);
        LOG.debug("numero de cartas encontradas ->" + docs.size() + "<-");
        if(!docs.isEmpty())
        {
            DyctDocumentoDTO cartaMasReciente = docs.get(0);
            for(int i = 1; i < docs.size(); i++)
            {
                if(cartaMasReciente.getFechaRegistro().before(docs.get(i).getFechaRegistro()))
                {
                    cartaMasReciente = docs.get(i);
                }
            }
            LOG.debug("cartaMasReciente ->" + cartaMasReciente.getNumControlDoc() + "<-");
            LOG.debug("numero de empleado aprobador ->" + cartaMasReciente.getDyccAprobadorDTO().getNumEmpleado() + "<-");
            cartaMasReciente.setDyccAprobadorDTO(daoAprobador.encontrar(cartaMasReciente.getDyccAprobadorDTO().getNumEmpleado()));
            return cartaMasReciente;
        }

        return null;
        
    }

    @Override
    public Map<String, Object> descargarCarta(Map<String, Object> params)
    {
        LOG.debug("INICIO descargarCarta");

        Map<String, Object> respuesta  = new HashMap<String, Object>();
        try {
            DyctDocumentoDTO carta = daoDocumento.encontrar((String)params.get(KEY_NUMCONTROLDOC));
            respuesta.put("archivo", obtenerArchivo(carta));
            respuesta.put(KEY_SUCCESS, Boolean.TRUE);
        } catch (SIATException e) {
            LOG.error("ocurrio un error al descargarCarta; mensaje ->" + e.getMessage());
            ManejadorLogException.getTraceError(e);
            respuesta.put(KEY_SUCCESS, Boolean.FALSE);
            respuesta.put(KEY_MENSAJE, e.getMessage());
        }

        return respuesta;
    }
}
