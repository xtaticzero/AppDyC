package principal.service;

import java.io.File;

import java.util.List;

import principal.bean.ProcesoDocumentoBean;

public interface AdminDocumentoService {
    
    void eliminar();
    
    void moverBloque();
    
    void eliminarArchivosTemp();
    
    List<ProcesoDocumentoBean> pruebaLista();
    
    void eliminarDirectorios(File directorios);
    
    void moverTesofe();
}
