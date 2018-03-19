package mx.gob.sat.siat.dyc.tesofe.service;

import org.primefaces.model.StreamedContent;

public interface ItemDownload {

    String getNombreArchivo();

    StreamedContent getFile();
}
