/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import mx.gob.sat.siat.cobranza.diahabil.service.DiaHabilService;
import mx.gob.sat.siat.dyc.generico.util.calculo.CalcularFechasService;
import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.BandejaDocumentosDTO;
import mx.gob.sat.siat.dyc.gestionsol.service.administrarsolicitudes.AdministrarSolicitudesService;
import mx.gob.sat.siat.dyc.gestionsol.service.aprobardocumento.BandejaSinDocumentosService;
import mx.gob.sat.siat.dyc.gestionsol.vo.administrarsolicitudes.AdministrarSolicitudesVO;
import mx.gob.sat.siat.dyc.util.common.Paginador;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author root
 */
public class LazyBandejaSinDocAprobadorDataModel extends LazyDataModel<BandejaDocumentosDTO> {

    private static final long serialVersionUID = -6175784111780918502L;

    private static final Logger LOGGER = Logger.getLogger(LazyBandejaSinDocAprobadorDataModel.class);

    private transient BandejaSinDocumentosService bandejaSinDocumentosSer;

    private transient CalcularFechasService calcularFechasService;

    private transient AdministrarSolicitudesService administrarSolicitudesService;

    private transient DiaHabilService diaHabilService;

    private String numEmpleadoStr;
    private String numControlFiltrado;
    private String rfcFiltrado;
    private Date fechaRecibido;

    private transient List<BandejaDocumentosDTO> datasource;

    private static final String NOMBRE_FILTRO_NUM_CONTROL = "numControl";
    private static final String NOMBRE_FILTRO_RFC = "rfcContribuyente";
    private static final String NOMBRE_FILTRO_FECHARECIBIDO = "fechaPresentacion";

    public LazyBandejaSinDocAprobadorDataModel() {
        super();
    }

    public LazyBandejaSinDocAprobadorDataModel(BandejaSinDocumentosService bandejaSinDocumentosSer,
            CalcularFechasService calcularFechasService,
            AdministrarSolicitudesService administrarSolicitudesService,
            DiaHabilService diaHabilService, String numEmpleadoStr) {
        super();
        this.bandejaSinDocumentosSer = bandejaSinDocumentosSer;
        this.calcularFechasService = calcularFechasService;
        this.administrarSolicitudesService = administrarSolicitudesService;
        this.diaHabilService = diaHabilService;
        this.numEmpleadoStr = numEmpleadoStr;
        this.datasource = new ArrayList<BandejaDocumentosDTO>();
    }

    @Override
    public BandejaDocumentosDTO getRowData(String rowKey) {
        for (BandejaDocumentosDTO documento : datasource) {
            if (documento.getNumControl().equals(rowKey)) {
                return documento;
            }
        }

        return null;
    }

    @Override
    public Object getRowKey(BandejaDocumentosDTO documentos) {
        return documentos.getNumControl();
    }

    @Override
    public List<BandejaDocumentosDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder,
            Map<String, String> filters) {
        try {
            Paginador paginador = new Paginador();
            paginador.setNPaginaActual((first + pageSize) / pageSize);
            paginador.setNRegPorPagina(pageSize);

            int nReg = 0;
            inicializaParametrosFiltrado(filters);

            nReg = bandejaSinDocumentosSer.countBuscarBandejaDoc(numEmpleadoStr, numControlFiltrado, rfcFiltrado, fechaRecibido).intValue();
            this.setRowCount(nReg);
            this.datasource = bandejaSinDocumentosSer.buscarBandejaDoc(numEmpleadoStr, paginador, numControlFiltrado, rfcFiltrado, fechaRecibido);

        } catch (SIATException e) {
            LOGGER.error(e.getCause());
        }

        if (datasource != null) {
            for (BandejaDocumentosDTO objeto : datasource) {
                List listaDatos = calculoFecha(objeto);
                objeto.setFechaFinalizacion((Date) listaDatos.get(0));
            }
        }

        return datasource;
    }

    private void inicializaParametrosFiltrado(Map<String, String> filters) {
        numControlFiltrado = "";
        rfcFiltrado = "";
        fechaRecibido = null;

        for (Map.Entry<String, String> entry : filters.entrySet()) {

            if (entry.getKey().equals(NOMBRE_FILTRO_NUM_CONTROL)) {
                numControlFiltrado = entry.getValue().toUpperCase();
            }

            if (entry.getKey().equals(NOMBRE_FILTRO_RFC)) {
                rfcFiltrado = entry.getValue().toUpperCase();
            }

            if (entry.getKey().equals(NOMBRE_FILTRO_FECHARECIBIDO)) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    fechaRecibido = formatter.parse(entry.getValue());
                } catch (ParseException ex) {
                    LOGGER.error(ex);
                }
            }

            LOGGER.info(numControlFiltrado + " " + rfcFiltrado + " " + fechaRecibido);
            LOGGER.error(String.format("No se encuentra el filtro: %s", entry.getKey()));
        }
    }

    public List calculoFecha(BandejaDocumentosDTO objeto) {

        List listaDatos = new ArrayList();
        Date fechaMaxima = null;
        Date fechaLimite = null;
        int diasHabNotSol = 0;

        try {
            fechaMaxima
                    = calcularFechasService.calcularFechaFinal(objeto.getFechaPresentacion(), objeto.getDias(), objeto.getTipoPlazo());

            diasHabNotSol = calculaDiasNotSol(objeto, objeto.getDias());

            if (diasHabNotSol > 0) {
                fechaLimite
                        = calcularFechasService.calcularFechaFinal(fechaMaxima, diasHabNotSol, objeto.getTipoPlazo());
            } else {
                fechaLimite = fechaMaxima;
            }
            listaDatos.add(fechaLimite);
            listaDatos.add(diasHabNotSol);

        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getCause());
        }
        return listaDatos;
    }

    public int calculaDiasNotSol(BandejaDocumentosDTO objeto, long dias) {

        int diasHabNotSol = 0;
        int diasHabiles = 0;
        AdministrarSolicitudesVO administrarSolicitudesVO;

        try {

            if (null != objeto.getNumControlDoc()) {
                administrarSolicitudesVO = administrarSolicitudesService.obtenerDiferencia(objeto.getNumControlDoc());
            } else {
                administrarSolicitudesVO = null;
            }

            if (null != administrarSolicitudesVO && null != administrarSolicitudesVO.getFechaNotificacion()
                    && null != administrarSolicitudesVO.getFechaSolventacion()
                    && administrarSolicitudesVO.getDiasHabiles() > 0) {
                List<String> lista = new ArrayList<String>();
                lista.add("TIPO_CAL_TODOS");
                diasHabiles
                        = diaHabilService.cantidadDiasHabilesPorFechas(administrarSolicitudesVO.getFechaNotificacion(),
                                administrarSolicitudesVO.getFechaSolventacion(),
                                lista, "LUGAR_FEDERAL", 0);
            }

            diasHabNotSol = diasHabiles;

        } catch (Exception e) {
            LOGGER.error("ERROR: " + e.getMessage());
        }

        return diasHabNotSol;
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        this.datasource = new ArrayList<BandejaDocumentosDTO>();
    }

    public String getNumEmpleadoStr() {
        return numEmpleadoStr;
    }

    public void setNumEmpleadoStr(String numEmpleadoStr) {
        this.numEmpleadoStr = numEmpleadoStr;
    }

    public List<BandejaDocumentosDTO> getDatasource() {
        return datasource;
    }

    public void setDatasource(List<BandejaDocumentosDTO> datasource) {
        this.datasource = datasource;
    }

}
