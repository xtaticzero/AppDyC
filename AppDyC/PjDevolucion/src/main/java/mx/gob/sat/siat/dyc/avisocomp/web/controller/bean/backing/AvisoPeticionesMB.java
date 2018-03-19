package mx.gob.sat.siat.dyc.avisocomp.web.controller.bean.backing;

import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import mx.gob.sat.siat.dyc.catalogo.service.DyccMatrizAnexosService;
import mx.gob.sat.siat.dyc.domain.anexo.DyccMatrizAnexosDTO;
import mx.gob.sat.siat.dyc.generico.util.ArchivoCargaDescarga;

import org.apache.log4j.Logger;

import org.primefaces.model.DefaultStreamedContent;


@ManagedBean(name = "avisoPeticionesMB")
@RequestScoped
public class AvisoPeticionesMB {
    
    private Logger log = Logger.getLogger(AvisoCompensacionCtrlMB.class);
    
    @ManagedProperty(value = "#{dyccMatrizAnexosService}")
    private DyccMatrizAnexosService dyccMatrizAnexosService;
    
    private DyccMatrizAnexosDTO seleccionoAnexoDescargar;
    private DefaultStreamedContent descargarAnexo;
    private ArchivoCargaDescarga archivo;
    
    @PostConstruct
    public void inti(){
        archivo = new ArchivoCargaDescarga();
    }
    
    public void downloadAnexoPlantilla() {
        log.info("------------------- DESCARGA DE PLANTILLA DE ANEXO ---------------");
        DyccMatrizAnexosDTO matriz = dyccMatrizAnexosService.buscaAnexoPorId(seleccionoAnexoDescargar.getIdAnexo());
        descargarAnexo = archivo.descargarArchivo(matriz.getUrl());
        setDescargarAnexo(descargarAnexo);
    }

    public void setArchivo(ArchivoCargaDescarga archivo) {
        this.archivo = archivo;
    }

    public ArchivoCargaDescarga getArchivo() {
        return archivo;
    }
    
    public void setDescargarAnexo(DefaultStreamedContent descargarAnexo) {
        this.descargarAnexo = descargarAnexo;
    }

    public DefaultStreamedContent getDescargarAnexo() {
        downloadAnexoPlantilla();
        return descargarAnexo;
    }

    public void setDyccMatrizAnexosService(DyccMatrizAnexosService dyccMatrizAnexosService) {
        this.dyccMatrizAnexosService = dyccMatrizAnexosService;
    }

    public DyccMatrizAnexosService getDyccMatrizAnexosService() {
        return dyccMatrizAnexosService;
    }

    public void setSeleccionoAnexoDescargar(DyccMatrizAnexosDTO seleccionoAnexoDescargar) {
        this.seleccionoAnexoDescargar = seleccionoAnexoDescargar;
    }

    public DyccMatrizAnexosDTO getSeleccionoAnexoDescargar() {
        return seleccionoAnexoDescargar;
    }

}
