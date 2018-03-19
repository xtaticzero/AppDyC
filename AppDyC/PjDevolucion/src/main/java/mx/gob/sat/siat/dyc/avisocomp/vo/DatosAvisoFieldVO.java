package mx.gob.sat.siat.dyc.avisocomp.vo;

import java.io.Serializable;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.inconsistencia.DycaAvInconsistDTO;
import mx.gob.sat.siat.dyc.domain.regsolicitud.TramiteDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;


public class DatosAvisoFieldVO implements Serializable{
    
    @SuppressWarnings("compatibility:5597814063152872783")
    private static final long serialVersionUID = 1L;

    private List<String> listaNumControl;
    private DycpCompensacionDTO dycpCompensacionDTO;
    private List<CuadroVO> listaCuadros;
    private List<DycaAvInconsistDTO> listaInconsistencias;
    private List<AnexoVO> listaAnexos;
    private List<ArchivoVO> listaDocumentos;
    private TramiteDTO persona;
    private Integer claveAdm; 
    
    
    public DatosAvisoFieldVO() {
        super();
    }

    public void setListaNumControl(List<String> listaNumControl) {
        this.listaNumControl = listaNumControl;
    }

    public List<String> getListaNumControl() {
        return listaNumControl;
    }

    public void setDycpCompensacionDTO(DycpCompensacionDTO dycpCompensacionDTO) {
        this.dycpCompensacionDTO = dycpCompensacionDTO;
    }

    public DycpCompensacionDTO getDycpCompensacionDTO() {
        return dycpCompensacionDTO;
    }

    public void setListaCuadros(List<CuadroVO> listaCuadros) {
        this.listaCuadros = listaCuadros;
    }

    public List<CuadroVO> getListaCuadros() {
        return listaCuadros;
    }

    public void setListaInconsistencias(List<DycaAvInconsistDTO> listaInconsistencias) {
        this.listaInconsistencias = listaInconsistencias;
    }

    public List<DycaAvInconsistDTO> getListaInconsistencias() {
        return listaInconsistencias;
    }

    public void setListaAnexos(List<AnexoVO> listaAnexos) {
        this.listaAnexos = listaAnexos;
    }

    public List<AnexoVO> getListaAnexos() {
        return listaAnexos;
    }

    public void setListaDocumentos(List<ArchivoVO> listaDocumentos) {
        this.listaDocumentos = listaDocumentos;
    }

    public List<ArchivoVO> getListaDocumentos() {
        return listaDocumentos;
    }

    public void setPersona(TramiteDTO persona) {
        this.persona = persona;
    }

    public TramiteDTO getPersona() {
        return persona;
    }

    public void setClaveAdm(Integer claveAdm) {
        this.claveAdm = claveAdm;
    }

    public Integer getClaveAdm() {
        return claveAdm;
    }
}
