/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.dao.documento.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.dao.compensacion.impl.mapper.DycpServicioMapper;
import mx.gob.sat.siat.dyc.dao.usuario.impl.mapper.AprobadorMapper;
import mx.gob.sat.siat.dyc.domain.documento.DyccMatDocumentosDTO;
import mx.gob.sat.siat.dyc.domain.documento.DyccTipoDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccTipoFirmaDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;
import mx.gob.sat.siat.dyc.util.BuscadorConstantes;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author J. Isaac Carbajal Bernal
 */
public class DyctDocumentoMapper implements RowMapper<DyctDocumentoDTO> {

    private DycpServicioDTO servicio;   
    private DyccTipoDocumentoDTO tipoDocumento;
    private DyctDocumentoDTO documento;
    private AprobadorMapper mapperAprobador;
    private DyccTipoFirmaDTO dyccTipoFirmaDTO;
    private DyccMatDocumentosDTO matrizDocumentos;
    private DycpServicioMapper mapperServicio;

    @Override
    public DyctDocumentoDTO mapRow(ResultSet rs, int i) throws SQLException {
        DyctDocumentoDTO documentoL;
        if (documento != null) {
            documentoL = documento;

        } else {
            documentoL = new DyctDocumentoDTO();
        }


        documentoL.setNumControlDoc(rs.getString("NUMCONTROLDOC"));
        if (tipoDocumento != null) {
            documentoL.setDyccTipoDocumentoDTO(tipoDocumento);

        } else {
            DyccTipoDocumentoDTO tipoDocumentoV = new DyccTipoDocumentoDTO();
            tipoDocumentoV.setIdTipoDocumento(rs.getInt("IDTIPODOCUMENTO"));
            documentoL.setDyccTipoDocumentoDTO(tipoDocumentoV);
        }

        DyccAprobadorDTO aprobador;

        if (mapperAprobador != null) {
            aprobador = mapperAprobador.mapRow(rs, i);

        } else {
            aprobador = new DyccAprobadorDTO();
            aprobador.setNumEmpleado(rs.getInt("NUMEMPLEADOAPROB"));
        }

        documentoL.setUrl(rs.getString("URL"));
        documentoL.setFechaRegistro(rs.getTimestamp("FECHAREGISTRO"));
        documentoL.setNombreArchivo(rs.getString("NOMBREARCHIVO"));
        documentoL.setFolioNyv(rs.getString("FOLIONYV"));
        documentoL.setDyccAprobadorDTO(aprobador);
        documentoL.setDyccEstadoReqDTO(BuscadorConstantes.obtenerEstadoReq(rs.getInt("IDESTADOREQ")));
        documentoL.setDyccEstadoDocDTO(BuscadorConstantes.obtenerEstadoDoc(rs.getInt("IDESTADODOC")));

        if (servicio != null) {
            documentoL.setDycpServicioDTO(servicio);
        }
        else if(mapperServicio != null){   
            documentoL.setDycpServicioDTO(mapperServicio.mapRow(rs, i));
        }
        else
        {
            DycpServicioDTO servicioV = new DycpServicioDTO();
            servicioV.setNumControl(rs.getString("NUMCONTROL"));
            documentoL.setDycpServicioDTO(servicioV);
        }

        if (dyccTipoFirmaDTO != null) {
            documentoL.setDyccTipoFirmaDTO(dyccTipoFirmaDTO);

        } else {
            DyccTipoFirmaDTO dyccTipoFirmaDTOD = new DyccTipoFirmaDTO();
            dyccTipoFirmaDTOD.setIdTipoFirma(rs.getInt("IDTIPOFIRMA"));
            documentoL.setDyccTipoFirmaDTO(dyccTipoFirmaDTOD);
        }

        if (matrizDocumentos != null) {
            documentoL.setDyccMatDocumentosDTO(matrizDocumentos);

        } else {
            DyccMatDocumentosDTO dyccMatDocumentosDTO = new DyccMatDocumentosDTO();
            dyccMatDocumentosDTO.setIdPlantilla(rs.getInt("IDPLANTILLA"));
            documentoL.setDyccMatDocumentosDTO(dyccMatDocumentosDTO);
        }
        documentoL.setCadenaOriginal(rs.getString("CADENAORIGINAL"));
        documentoL.setSelloDigital(rs.getString("SELLODIGITAL"));
        documentoL.setFirmaElectronia(rs.getString("FIRMAELECTRONICA"));

        return documentoL;
    }

    public DycpServicioDTO getServicio() {
        return servicio;
    }

    public void setServicio(DycpServicioDTO servicio) {
        this.servicio = servicio;
    }

    public DyccTipoDocumentoDTO getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(DyccTipoDocumentoDTO tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public DyctDocumentoDTO getDocumento() {
        return documento;
    }

    public void setDocumento(DyctDocumentoDTO documento) {
        this.documento = documento;
    }

    public AprobadorMapper getMapperAprobador() {
        return mapperAprobador;
    }

    public void setMapperAprobador(AprobadorMapper mapperAprobador) {
        this.mapperAprobador = mapperAprobador;
    }

    public void setDyccTipoFirmaDTO(DyccTipoFirmaDTO dyccTipoFirmaDTO) {
        this.dyccTipoFirmaDTO = dyccTipoFirmaDTO;
    }

    public DyccTipoFirmaDTO getDyccTipoFirmaDTO() {
        return dyccTipoFirmaDTO;
    }

    public void setMatrizDocumentos(DyccMatDocumentosDTO matrizDocumentos) {
        this.matrizDocumentos = matrizDocumentos;
    }

    public DyccMatDocumentosDTO getMatrizDocumentos() {
        return matrizDocumentos;
    }

    public void setMapperServicio(DycpServicioMapper mapperServicio) {
        this.mapperServicio = mapperServicio;
    }

    public DycpServicioMapper getMapperServicio() {
        return mapperServicio;
    }
}
