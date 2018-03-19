package mx.gob.sat.siat.dyc.adminprocesos.web.controller.bean.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.adminprocesos.service.AdministrarNotificacionesService;
import mx.gob.sat.siat.dyc.domain.fallo.DyctFalloMensajesDTO;
import mx.gob.sat.siat.dyc.util.common.Paginador;

import org.apache.log4j.Logger;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;


public class LazyNotificacionesDetalleDataModel extends LazyDataModel<DyctFalloMensajesDTO> {

    private static final long serialVersionUID = -7211595931901267204L;

    private static final Logger LOGGER = Logger.getLogger(LazyNotificacionesDetalleDataModel.class);

    private static final int MAX_REZAGADOS = 1000;

    private transient AdministrarNotificacionesService administrarNotificacionesService;

    private int idFalloDetalle;

    private List<DyctFalloMensajesDTO> datasource;

    private boolean rezagado;

    public LazyNotificacionesDetalleDataModel() {
        super();
    }

    public LazyNotificacionesDetalleDataModel(AdministrarNotificacionesService administrarNotificacionesService) {
        super();
        this.administrarNotificacionesService = administrarNotificacionesService;
        this.datasource = new ArrayList<DyctFalloMensajesDTO>();
        this.rezagado = Boolean.TRUE;
    }


    public LazyNotificacionesDetalleDataModel(AdministrarNotificacionesService administrarNotificacionesService,
                                              int idFalloDetalle) {
        super();
        this.administrarNotificacionesService = administrarNotificacionesService;
        this.idFalloDetalle = idFalloDetalle;
        this.datasource = new ArrayList<DyctFalloMensajesDTO>();
        this.rezagado = false;
    }

    @Override
    public DyctFalloMensajesDTO getRowData(String rowKey) {
        for (DyctFalloMensajesDTO falloMensaje : datasource) {
            if (falloMensaje.getIdFalloMensaje().equals(Integer.valueOf(rowKey))) {
                return falloMensaje;
            }
        }

        return null;
    }

    @Override
    public Object getRowKey(DyctFalloMensajesDTO falloMensaje) {
        return falloMensaje.getIdFalloMensaje();
    }

    @Override
    public List<DyctFalloMensajesDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder,
                                           Map<String, String> filters) {
        Paginador paginador = new Paginador();
        paginador.setNPaginaActual((first + pageSize) / pageSize);
        paginador.setNRegPorPagina(pageSize);
        int nReg = 0;

        String cveUnidadAdmtva = "";
        String numControl = "";

        if (filters != null) {
            for (Map.Entry<String, String> entry : filters.entrySet()) {
                if (entry.getKey().equals("numControl")) {
                    numControl = entry.getValue().toUpperCase();
                } else if (entry.getKey().equals("cveUnidadAdmtva")) {
                    cveUnidadAdmtva = entry.getValue().toUpperCase();
                } else {
                    LOGGER.error(String.format("No se encuentra el filtro: %s", entry.getKey()));
                }
            }

            LOGGER.debug(String.format("Filtrado por numControl: %s, cveUnidadAdmtva: %s", numControl,
                                       cveUnidadAdmtva));
        }

        if (!rezagado) {
            nReg =
administrarNotificacionesService.getCountFalloMensajesByIdFalloDetalle(idFalloDetalle, cveUnidadAdmtva,
                                                                       numControl).intValue();
            this.datasource =
                    administrarNotificacionesService.getFalloMensajesByIdFalloDetallePaginador(idFalloDetalle,
                                                                                               cveUnidadAdmtva,
                                                                                               numControl, paginador);
        } else {
            nReg =
administrarNotificacionesService.countDocumentosRezagados(cveUnidadAdmtva, numControl, MAX_REZAGADOS).intValue();
            this.datasource =
                    administrarNotificacionesService.obtenDocumentosRezagados(cveUnidadAdmtva, numControl, MAX_REZAGADOS,
                                                                              paginador);
        }

        this.setRowCount(nReg);

        return datasource;
    }

    public int getIdFalloDetalle() {
        return this.idFalloDetalle;
    }

    public void setIdFalloDetalle(int idFalloDetalle) {
        this.idFalloDetalle = idFalloDetalle;
    }

    public List<DyctFalloMensajesDTO> getDatasource() {
        return datasource;
    }

    public void setDatasource(List<DyctFalloMensajesDTO> datasource) {
        this.datasource = datasource;
    }

    public boolean isRezagado() {
        return this.rezagado;
    }

    public void setRezagado(boolean rezagado) {
        this.rezagado = rezagado;
    }
}
