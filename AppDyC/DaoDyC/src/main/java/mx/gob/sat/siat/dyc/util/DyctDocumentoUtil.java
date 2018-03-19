package mx.gob.sat.siat.dyc.util;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;

import org.springframework.stereotype.Component;


@Component(value = "dyctDocumentoUtil")
public class DyctDocumentoUtil {

    private List<Object> params;
    
    public String updateDocumento(DyctDocumentoDTO documento){
        String utilidadUpdateUno = utilidadUpdateDocumento(documento);
        String utilidadUpdateDos = continuaUnoUpdateDocumento(documento);
        String utilidadUpdateTres = continuaDosUpdateDocumento(documento);
        return utilidadUpdateUno + utilidadUpdateDos + utilidadUpdateTres;
    }

    private String utilidadUpdateDocumento(DyctDocumentoDTO documento) {
        String valoresSettear = "";
        boolean comma = Boolean.FALSE;

        if (documento.getDyccTipoDocumentoDTO() != null) {
            comma = Boolean.TRUE;
            valoresSettear = " IDTIPODOCUMENTO = ?";
            params.add(documento.getDyccTipoDocumentoDTO().getIdTipoDocumento());
        }

        if (documento.getUrl() != null) {
            if (comma) {
                valoresSettear += ",";
            }
            comma = Boolean.TRUE;
            valoresSettear += " URL = ?";
            params.add(documento.getUrl());
        }

        if (documento.getFechaRegistro() != null) {
            if (comma) {
                valoresSettear += ",";
            }
            comma = Boolean.TRUE;
            valoresSettear += " FECHAREGISTRO = ?";
            params.add(documento.getFechaRegistro());
        }

        if (documento.getNombreArchivo() != null) {
            if (comma) {
                valoresSettear += ",";
            }
            comma = Boolean.TRUE;
            valoresSettear += " NOMBREARCHIVO = ?";
            params.add(documento.getNombreArchivo());
        }
        
        return valoresSettear;
    }

    private String continuaUnoUpdateDocumento(DyctDocumentoDTO documento) {
        String valoresSettearUno = "";
        boolean commaUno = Boolean.FALSE;
        if (documento.getDyccAprobadorDTO() != null) {
            if (commaUno) {
                valoresSettearUno += ",";
            }
            commaUno = Boolean.TRUE;
            valoresSettearUno += " NUMEMPLEADOAPROB = ?";
            params.add(documento.getDyccAprobadorDTO().getNumEmpleado());
        }

        if (documento.getDyccEstadoReqDTO() != null) {
            if (commaUno) {
                valoresSettearUno += ",";
            }
            commaUno = Boolean.TRUE;
            valoresSettearUno += " IDESTADOREQ = ?";
            params.add(documento.getDyccEstadoReqDTO().getIdEstadoReq());
        }

        if (documento.getDyccEstadoDocDTO() != null) {
            if (commaUno) {
                valoresSettearUno += ",";
            }
            commaUno = Boolean.TRUE;
            valoresSettearUno += " IDESTADODOC = ?";
            params.add(documento.getDyccEstadoDocDTO().getIdEstadoDoc());
        }

        if (documento.getDycpServicioDTO() != null) {
            if (commaUno) {
                valoresSettearUno += ",";
            }
            commaUno = Boolean.TRUE;
            valoresSettearUno += " NUMCONTROL = ?";
            params.add(documento.getDycpServicioDTO().getNumControl());
        }
        
        return valoresSettearUno;
    }

    private String continuaDosUpdateDocumento(DyctDocumentoDTO documento) {
        String valoresSettearDos = "";
        boolean commaDos = Boolean.FALSE;
        if (documento.getDyccMatDocumentosDTO() != null) {
            if (commaDos) {
                valoresSettearDos += ",";
            }
            commaDos = Boolean.TRUE;
            valoresSettearDos += " IDPLANTILLA = ?";
            params.add(documento.getDyccMatDocumentosDTO().getIdPlantilla());
        }
        
        if (documento.getFechaIniPlazoLegal() != null) {
            if (commaDos) { 
                valoresSettearDos += ",";
            }
            commaDos = Boolean.TRUE;
            valoresSettearDos += " FECHAINIPLAZOLEGAL = ?";
            params.add(documento.getFechaIniPlazoLegal());
        }

        if (documento.getFechaFinPlazoLegal() != null) {
            if (commaDos) {
                valoresSettearDos += ",";
            }
            commaDos = Boolean.TRUE;
            valoresSettearDos += " FECHAFINPLAZOLEGAL = ?";
            params.add(documento.getFechaFinPlazoLegal());
        }

        if (documento.getDyccTipoFirmaDTO() != null) {
            if (commaDos) {
                valoresSettearDos += ",";
            }
            commaDos = Boolean.TRUE;
            valoresSettearDos += " IDTIPOFIRMA = ?";
            params.add(documento.getDyccTipoFirmaDTO().getIdTipoFirma());
        }
        
        return valoresSettearDos;
    }
    

    public void setParams(List<Object> params) {
        this.params = params;
    }

    public List<Object> getParams() {
        return params;
    }
}
