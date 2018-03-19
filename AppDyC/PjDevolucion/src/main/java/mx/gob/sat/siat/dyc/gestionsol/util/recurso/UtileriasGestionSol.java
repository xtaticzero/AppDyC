package mx.gob.sat.siat.dyc.gestionsol.util.recurso;

import java.io.UnsupportedEncodingException;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Map;

import mx.gob.sat.siat.dyc.generico.util.common.UsuarioFirmadoVO;
import mx.gob.sat.siat.dyc.generico.util.common.Utilerias;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.CadenaCompensacionDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.CadenaSolicitudDTO;


public class UtileriasGestionSol {
    public UtileriasGestionSol() {
        super();
    }

    private static final String INICIO_FIN = "||";
    private static final String PIPE = "|";

    public String generarCadenaParaSolventarRequerimientoSolicitud(CadenaSolicitudDTO objetoCadenaSolicitud) throws UnsupportedEncodingException {
        StringBuffer cadenaOriginal = new StringBuffer("");
        String nombreDenominacion = new String (objetoCadenaSolicitud.getNombreORazonSocial().getBytes("ISO-8859-1"),"UTF-8");
        cadenaOriginal.append(INICIO_FIN);
        cadenaOriginal.append(objetoCadenaSolicitud.getIdTipoTramite());
        cadenaOriginal.append(PIPE);
        cadenaOriginal.append(objetoCadenaSolicitud.getOrigenTramite());
        cadenaOriginal.append(PIPE);
        cadenaOriginal.append(objetoCadenaSolicitud.getConcepto());
        cadenaOriginal.append(PIPE);
        cadenaOriginal.append(objetoCadenaSolicitud.getPeriodo());
        cadenaOriginal.append(PIPE);
        cadenaOriginal.append(objetoCadenaSolicitud.getEjercicio());
        cadenaOriginal.append(PIPE);
        cadenaOriginal.append(objetoCadenaSolicitud.getFechaSolventacion());
        cadenaOriginal.append(PIPE);
        cadenaOriginal.append("INTERNET");
        cadenaOriginal.append(PIPE);
        cadenaOriginal.append(objetoCadenaSolicitud.getTipoRequerimiento());
        cadenaOriginal.append(PIPE);
        cadenaOriginal.append(objetoCadenaSolicitud.getNumeroControl());
        cadenaOriginal.append(PIPE);
        cadenaOriginal.append(objetoCadenaSolicitud.getRfc());
        cadenaOriginal.append(PIPE);
        cadenaOriginal.append(nombreDenominacion);
        cadenaOriginal.append(INICIO_FIN);
        return cadenaOriginal.toString();
    }

    public String generarCadenaParaSolventarRequerimientoCompensacion(CadenaCompensacionDTO objetoCadenaCompensacion) throws UnsupportedEncodingException {
        StringBuffer cadenaOriginal = new StringBuffer("");
        String nombreDenominacion = new String (objetoCadenaCompensacion.getNombreORazonSocial().getBytes("ISO-8859-1"),"UTF-8");
        cadenaOriginal.append(INICIO_FIN);
        cadenaOriginal.append(objetoCadenaCompensacion.getDescripcionTramiteICEPDestino());
        cadenaOriginal.append(PIPE);
        cadenaOriginal.append(objetoCadenaCompensacion.getDescripcionConceptoICEPDestino());
        cadenaOriginal.append(PIPE);
        cadenaOriginal.append(objetoCadenaCompensacion.getDescripcionPeriodoICEPDestino());
        cadenaOriginal.append(PIPE);
        cadenaOriginal.append(objetoCadenaCompensacion.getEjercicioICEPDestino());
        cadenaOriginal.append(PIPE);
        cadenaOriginal.append(objetoCadenaCompensacion.getFechaSolventacion());
        cadenaOriginal.append(PIPE);
        cadenaOriginal.append("INTERNET");
        cadenaOriginal.append(PIPE);
        cadenaOriginal.append(objetoCadenaCompensacion.getTipoDeRequerimiento());
        cadenaOriginal.append(PIPE);
        cadenaOriginal.append(objetoCadenaCompensacion.getNumeroControl());
        cadenaOriginal.append(PIPE);
        cadenaOriginal.append(objetoCadenaCompensacion.getRfc());
        cadenaOriginal.append(PIPE);
        cadenaOriginal.append(nombreDenominacion);
        cadenaOriginal.append(INICIO_FIN);
        return cadenaOriginal.toString();
    }

    public String crearCadenaOriginal(Map<String, Object> parametros, UsuarioFirmadoVO usuarioFirmado) {
        StringBuffer cadenaOriginalSB = new StringBuffer("");
        cadenaOriginalSB.append(INICIO_FIN);
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
        cadenaOriginalSB.append(INICIO_FIN);

        usuarioFirmado.setCadenaOriginal(cadenaOriginalSB.toString());
        return cadenaOriginalSB.toString();
    }

    public String obtenerCadenaParaFecha(Date fecha, SimpleDateFormat formateadorFecha) {
        if (fecha != null) {
            return (formateadorFecha.format(fecha)).toString();
        }
        return "";
    }
}
