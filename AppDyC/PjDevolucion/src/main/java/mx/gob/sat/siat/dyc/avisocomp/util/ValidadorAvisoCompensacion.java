package mx.gob.sat.siat.dyc.avisocomp.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import java.math.BigDecimal;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import mx.gob.sat.siat.dyc.avisocomp.vo.AnexoVO;
import mx.gob.sat.siat.dyc.avisocomp.vo.ArchivoVO;
import mx.gob.sat.siat.dyc.avisocomp.vo.CuadroVO;
import mx.gob.sat.siat.dyc.avisocomp.vo.DatosAvisoFieldVO;
import mx.gob.sat.siat.dyc.avisocomp.vo.DatosDestinoAcuseVO;
import mx.gob.sat.siat.dyc.avisocomp.vo.DatosOrigenAcuseVO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaDTO;
import mx.gob.sat.siat.dyc.domain.regsolicitud.TramiteDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctContribuyenteDTO;
import mx.gob.sat.siat.dyc.generico.util.common.Selladora;
import mx.gob.sat.siat.dyc.generico.util.common.Utilerias;
import mx.gob.sat.siat.dyc.registro.service.contribuyente.ConsultarExpedienteService;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesAvisoCompensacion;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component(value = "validadorAvisoCompensacion")
public class ValidadorAvisoCompensacion {

    @Autowired
    private ConsultarExpedienteService consultarExpedienteService;

    private static final String PIPE = "|";
    private static final String PIPE_DOBLE = "||";
    public ValidadorAvisoCompensacion() {
        super();
    }

    public Map<String, Object> datosCadenaOriginal(DycpCompensacionDTO dypCompensacionDTO, TramiteDTO personsa,
                                                   Integer claveAdm) {

        Map<String, Object> parametros = new HashMap<String, Object>();
        String nombre = null;
        if (null != personsa.getPersona().getPersonaIdentificacion().getRazonSocial()) {
            nombre = personsa.getPersona().getPersonaIdentificacion().getRazonSocial();
        } else {
            nombre =
                    personsa.getPersona().getPersonaIdentificacion().getNombre() + personsa.getPersona().getPersonaIdentificacion().getApPaterno() +
                    personsa.getPersona().getPersonaIdentificacion().getApMaterno();
        }

        parametros.put("rfc", Utilerias.isNull(personsa.getPersona().getRfcVigente()));
        parametros.put("razonsocial", Utilerias.isNull(nombre));
        parametros.put("cveadmin", Utilerias.isNull(claveAdm));
        parametros.put("origendevolucion", "");
        parametros.put("tipotramite", "");
        parametros.put("plantilla", "");
        parametros.put("numempleadoaprob", "");
        parametros.put("impuesto", "");
        parametros.put("concepto",
                       Utilerias.isNull(dypCompensacionDTO.getDyctSaldoIcepDestinoDTO().getDyccConceptoDTO().getIdConcepto()));
        parametros.put("ejercicio",
                       Utilerias.isNull(dypCompensacionDTO.getDyctSaldoIcepDestinoDTO().getDyccEjercicioDTO().getIdEjercicio()));
        parametros.put("periodo",
                       Utilerias.isNull(dypCompensacionDTO.getDyctSaldoIcepDestinoDTO().getDyccPeriodoDTO().getIdPeriodo()));
        parametros.put("importesolic", Utilerias.isNull(dypCompensacionDTO.getImporteCompensado()));
        parametros.put("fecharegistro",
                       Utilerias.isNull(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date())));
        parametros.put("numcontroldoc", "");

        return parametros;
    }

    private String crearCadenaOriginal(Map<String, Object> parametros) {
        StringBuffer cadenaOriginalSB = new StringBuffer("");
        cadenaOriginalSB.append(PIPE_DOBLE);
        cadenaOriginalSB.append(Utilerias.isNull(parametros.get("rfc")));
        cadenaOriginalSB.append(PIPE);
        cadenaOriginalSB.append(Utilerias.isNull(parametros.get("razonsocial")));
        cadenaOriginalSB.append(PIPE);
        cadenaOriginalSB.append(Utilerias.isNull(parametros.get("cveadmin")));
        cadenaOriginalSB.append(PIPE);
        cadenaOriginalSB.append(Utilerias.isNull(parametros.get("origendevolucion")));
        cadenaOriginalSB.append(PIPE);
        cadenaOriginalSB.append(Utilerias.isNull(parametros.get("tipotramite")));
        cadenaOriginalSB.append(PIPE);
        cadenaOriginalSB.append(Utilerias.isNull(parametros.get("plantilla")));
        cadenaOriginalSB.append(PIPE);
        cadenaOriginalSB.append(Utilerias.isNull(parametros.get("numempladoaprob")));
        cadenaOriginalSB.append(PIPE);
        cadenaOriginalSB.append(Utilerias.isNull(parametros.get("impuesto")));
        cadenaOriginalSB.append(PIPE);
        cadenaOriginalSB.append(Utilerias.isNull(parametros.get("concepto")));
        cadenaOriginalSB.append(PIPE);
        cadenaOriginalSB.append(Utilerias.isNull(parametros.get("ejercicio")));
        cadenaOriginalSB.append(PIPE);
        cadenaOriginalSB.append(Utilerias.isNull(parametros.get("periodo")));
        cadenaOriginalSB.append(PIPE);
        cadenaOriginalSB.append(Utilerias.isNull(parametros.get("importesolic")));
        cadenaOriginalSB.append(PIPE);
        cadenaOriginalSB.append(Utilerias.isNull(parametros.get("fecharegistro")));
        cadenaOriginalSB.append(PIPE);
        cadenaOriginalSB.append(Utilerias.isNull(parametros.get("numcontroldoc")));
        cadenaOriginalSB.append(PIPE_DOBLE);
        return cadenaOriginalSB.toString();
    }

    public Map<String, Object> generaCadenaySello(DatosAvisoFieldVO datosAvisoField) throws IOException {
        Map<String, Object> datos = null;
        String cadOriginal = null;
        Selladora selladora = new Selladora();
        if (null != datosAvisoField) {
            datos = new HashMap<String, Object>();
            cadOriginal =
                    Utilerias.isNull(crearCadenaOriginal(datosCadenaOriginal(datosAvisoField.getDycpCompensacionDTO(),
                                                                             datosAvisoField.getPersona(),
                                                                             datosAvisoField.getClaveAdm())));
            datos.put("cadena", cadOriginal);
            datos.put("sello", Utilerias.isNull(selladora.retornarParametros("1", cadOriginal)));
        }
        return datos;
    }


    public Map<String, Object> datosParaAcuseAviso(List<DatosDestinoAcuseVO> datosDestino, List<AnexoVO> listaAnexos,
                                                   List<ArchivoVO> listaArchivos,
                                                   List<DatosOrigenAcuseVO> datosOrigen) throws SIATException{
        double totalImportes = ConstantesAvisoCompensacion.VALUE_ZEROD;
        Map<String, Object> aviso = new HashMap<String, Object>();
        aviso.put("RUTA_IMAGEN", "/siat/imagenes");

        aviso.put("fechaPresentacion", datosDestino.get(ConstantesDyCNumerico.VALOR_0).getFechaPresentacion());
        aviso.put("fechaInicioTramite", datosDestino.get(ConstantesDyCNumerico.VALOR_0).getFechaInicioTramite());
        aviso.put("claveRFC", datosDestino.get(ConstantesDyCNumerico.VALOR_0).getRfcContribuyente());
        aviso.put("folioAviso", datosDestino.get(ConstantesDyCNumerico.VALOR_0).getFolioAviso());

        String tipo = datosDestino.get(ConstantesDyCNumerico.VALOR_0).getTipoPersona();
        String nombreContribuyente = null;
        
        /**Parte del Codigo que obtiene e interpreta el nombre contribuyente a codificacion UTF-8*/
        DyctContribuyenteDTO contribuyenteDto = consultarExpedienteService.buscarNumcontrol(datosOrigen.get(0).getNumControl());        
        InputStreamReader isr;
        JAXBContext jaxbContext;
        Unmarshaller jaxbUnmarshaller;
        PersonaDTO personaDTO;

        try {
            isr = new InputStreamReader(contribuyenteDto.getDatosContribuyente(),"UTF-8");
            jaxbContext = JAXBContext.newInstance(PersonaDTO.class);
            jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            personaDTO = (PersonaDTO)jaxbUnmarshaller.unmarshal(isr);
        } catch (UnsupportedEncodingException e) {
            throw new SIATException(e);
        } catch (JAXBException e) {
            throw new SIATException(e);
        }

        if (tipo.equals("F")) {
            nombreContribuyente = personaDTO.getPersonaIdentificacion().getNombre()+ " " + personaDTO.getPersonaIdentificacion().getApPaterno() +
                                " " + personaDTO.getPersonaIdentificacion().getApMaterno();
        } else {
            nombreContribuyente = personaDTO.getPersonaIdentificacion().getRazonSocial();
        }
        
        aviso.put("nombreDenominacion", nombreContribuyente);
        /**Termina razon Social*/
        
        aviso.put("adminLocal", datosDestino.get(ConstantesDyCNumerico.VALOR_0).getNombreAdm());
        aviso.put("tipoAvisoDG", datosDestino.get(ConstantesDyCNumerico.VALOR_0).getTipoAviso());
        if (datosDestino.get(ConstantesDyCNumerico.VALOR_0).getTipoAviso().equals("Complementaria")) {
            aviso.put("numControlDG", datosDestino.get(ConstantesDyCNumerico.VALOR_0).getFolioAvisoNormal());
        }
        aviso.put("conceptoDG", datosDestino.get(ConstantesDyCNumerico.VALOR_0).getConcepto());
        aviso.put("periodoDG", datosDestino.get(ConstantesDyCNumerico.VALOR_0).getPeriodo());
        aviso.put("ejercicioDG", datosDestino.get(ConstantesDyCNumerico.VALOR_0).getEjercicio());
        aviso.put("fechaPresentacionDG",
                  new SimpleDateFormat("dd/MM/yyyy").format(datosDestino.get(ConstantesDyCNumerico.VALOR_0).getFechaPresentaDec()));
        aviso.put("numOperacionDG", datosDestino.get(ConstantesDyCNumerico.VALOR_0).getNumOperacionDec());

        for (int i = 0; i < datosDestino.size(); i++) {
            totalImportes += datosDestino.get(i).getImporteCompensado();
        }
        aviso.put("importeCompensadoDG", new DecimalFormat("$ ###,###.##").format(totalImportes));
        aviso.put("cadenaOriginal", datosDestino.get(ConstantesDyCNumerico.VALOR_0).getCadenaOriginal());
        aviso.put("selloDigital", datosDestino.get(ConstantesDyCNumerico.VALOR_0).getSelloDigital());
        aviso.put("listaAnexos", listaAnexos);
        aviso.put("listaDocumentos", listaArchivos);
        aviso.put("listaCuadros", datosOrigen);
        return aviso;
    }

    public BigDecimal validaImporteExistente(Map<Integer, CuadroVO> hmCuadros) {
        BigDecimal importeSolicitadoTotal = BigDecimal.ZERO;
        Set<Map.Entry<Integer, CuadroVO>> cuadroEntry = hmCuadros.entrySet();
        for (Map.Entry<Integer, CuadroVO> cuadros : cuadroEntry) {
            Integer key = cuadros.getKey();
            importeSolicitadoTotal =
                    importeSolicitadoTotal.add(hmCuadros.get(key).getDyctOrigenAvisoDTO().getImporteSolicitado());
        }

        /**Iterator it = hmCuadros.keySet().iterator();
        while (it.hasNext()) {
            Integer key = (Integer)it.next();
            importeSolicitadoTotal =
                    importeSolicitadoTotal + hmCuadros.get(key).getDyctOrigenAvisoDTO().getImporteSolicitado();
        }*/
        return importeSolicitadoTotal;
    }

}
