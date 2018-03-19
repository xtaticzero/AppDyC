/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.gestionsol.service.aprobardocumento.impl;

import com.ibm.icu.util.Calendar;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mx.gob.sat.siat.dyc.comunica.util.constante.Constantes;
import mx.gob.sat.siat.dyc.dao.resolucion.DyctResolucionDAO;
import mx.gob.sat.siat.dyc.dao.sindocumento.DyctResolSinDocumentoDAO;
import mx.gob.sat.siat.dyc.generico.util.Utils;
import mx.gob.sat.siat.dyc.gestionsol.dao.aprobardocumento.BandejaSinDocumentosDAO;
import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.BandejaDocumentosDTO;
import mx.gob.sat.siat.dyc.gestionsol.service.aprobardocumento.BandejaSinDocumentosService;
import mx.gob.sat.siat.dyc.util.common.Paginador;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.excepcion.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author root
 */
@Service("bandejaSinDocumentosService")
@Transactional
public class BandejaSinDocumentosServiceImpl implements BandejaSinDocumentosService {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(BandejaSinDocumentosServiceImpl.class);

    @Autowired(required = true)
    private BandejaSinDocumentosDAO bandejaSinDocDAO;

    @Autowired
    private DyctResolSinDocumentoDAO resolSinDocumentoDAO;

    @Autowired
    private DyctResolucionDAO dyctResolucionDAO;

    private static final String NUMCONTROL = "AND A.NUMCONTROL like ? ";
    private static final String RFC = "AND B.RFCCONTRIBUYENTE like ? ";
    private static final String FECHAPRESENTACION = " AND trunc(A.FECHAPRESENTACION) > to_date(?,'dd/MM/yyyy') and  A.FECHAPRESENTACION < to_date(?,'dd/MM/yyyy') ";
    private static final String NUMCONTROL_RFC = "AND A.NUMCONTROL like ? AND B.RFCCONTRIBUYENTE like ? ";
    private static final String NUMCONTROL_FECHA = "AND A.NUMCONTROL like ? AND trunc(A.FECHAPRESENTACION) > to_date(?,'dd/MM/yyyy') and  A.FECHAPRESENTACION < to_date(?,'dd/MM/yyyy')  ";
    private static final String RFC_FECHA = "AND B.RFCCONTRIBUYENTE like ? AND trunc(A.FECHAPRESENTACION) > to_date(?,'dd/MM/yyyy') and  A.FECHAPRESENTACION < to_date(?,'dd/MM/yyyy')  ";
    private static final String ALL_FILTROS = "AND A.NUMCONTROL like ? AND B.RFCCONTRIBUYENTE like ? AND trunc(A.FECHAPRESENTACION) > to_date(?,'dd/MM/yyyy') and  A.FECHAPRESENTACION < to_date(?,'dd/MM/yyyy')  ";
    private static final String FORMATOFECHA = "dd/MM/yyyy";
    private static final String FORMAT_SEARCH_STRING = "%%%s%%";

    public BandejaSinDocumentosServiceImpl() {
        super();

    }

    @Override
    public List<BandejaDocumentosDTO> buscarBandejaDoc(String numEmpleado, Paginador paginador, String numControlFiltrado, String rfcFiltrado, Date fechaFecibido) throws SIATException {
        try {
            List<Object> params = new ArrayList<Object>();
            StringBuffer clausulas = new StringBuffer();
            obtenerFiltros(params, clausulas, rfcFiltrado, Integer.parseInt(numEmpleado), numControlFiltrado, fechaFecibido);
            params.add(paginador.getFrom());
            params.add(paginador.getTo());
            clausulas.append("");
            return bandejaSinDocDAO.consultarTramitesBandeja(params.toArray(), clausulas.toString());
        } catch (NumberFormatException e) {
            throw new SIATException(e);
        } catch (DAOException e) {
            throw new SIATException(e);
        } catch (ParseException ex) {
            throw new SIATException(ex);
        }
    }

    @Override
    public Long countBuscarBandejaDoc(String numEmpleado, String numControlFiltrado, String rfcFiltrado, Date fechaFecibido) throws SIATException {
        try {
            List<Object> params = new ArrayList<Object>();
            StringBuffer clausulas = new StringBuffer();
            obtenerFiltros(params, clausulas, rfcFiltrado, Integer.parseInt(numEmpleado), numControlFiltrado, fechaFecibido);
            clausulas.append("");
            return bandejaSinDocDAO.countBuscarBandejaDoc(params.toArray(), clausulas.toString());
        } catch (ParseException ex) {
            throw new SIATException(ex);
        }
    }

    @Override
    public void aprobarSinDocumento(String numControl, Integer idEstadoReq) {
        try {
            LOG.info("aprobarSinDocumentonumControl : " + numControl + " "+ idEstadoReq);
            resolSinDocumentoDAO.aprobarResolucionSinDoc(numControl, idEstadoReq);
            dyctResolucionDAO.actualizarEstResol(Constantes.IDAPROBADA, numControl);
        } catch (SIATException ex) {
            LOG.error(ex);
        }
    }

    private void obtenerFiltros(List<Object> params, StringBuffer clausula, String rfcFiltrado, Integer numEmpleado, String numControlFiltrado, Date fechaFecibido) throws ParseException {

        boolean isValidoRfc = validaRfcFiltro(rfcFiltrado);
        boolean isValidoNumControl = validaNumControlFiltro(numControlFiltrado);
        boolean isValidaFecha = validaFechaFiltro(fechaFecibido);
        
        if (isValidoNumControl && !isValidoRfc && !isValidaFecha) {
            clausula.append(NUMCONTROL);
            params.add(numEmpleado);
            params.add(String.format(FORMAT_SEARCH_STRING, numControlFiltrado));
            params.add(numEmpleado);
            params.add(String.format(FORMAT_SEARCH_STRING, numControlFiltrado));
        } else if (!isValidoNumControl && isValidoRfc && !isValidaFecha) {
            clausula.append(RFC);
            params.add(numEmpleado);
            params.add(String.format(FORMAT_SEARCH_STRING, rfcFiltrado));
            params.add(numEmpleado);
            params.add(String.format(FORMAT_SEARCH_STRING, rfcFiltrado));
        } else if (!isValidoNumControl && !isValidoRfc && isValidaFecha) {
            clausula.append(FECHAPRESENTACION);
            params.add(numEmpleado);
            params.add(Utils.darFormatoFecha(obtenExtremosFecha(fechaFecibido, ConstantesDyCNumerico.VALOR_1_NEG), FORMATOFECHA));
            params.add(Utils.darFormatoFecha(obtenExtremosFecha(fechaFecibido, ConstantesDyCNumerico.VALOR_1), FORMATOFECHA));
            params.add(numEmpleado);
            params.add(Utils.darFormatoFecha(obtenExtremosFecha(fechaFecibido, ConstantesDyCNumerico.VALOR_1_NEG), FORMATOFECHA));
            params.add(Utils.darFormatoFecha(obtenExtremosFecha(fechaFecibido, ConstantesDyCNumerico.VALOR_1), FORMATOFECHA));
        } else if (isValidoNumControl && isValidoRfc && !isValidaFecha) {
            clausula.append(NUMCONTROL_RFC);
            params.add(numEmpleado);
            params.add(String.format(FORMAT_SEARCH_STRING, numControlFiltrado));
            params.add(String.format(FORMAT_SEARCH_STRING, rfcFiltrado));
            params.add(numEmpleado);
            params.add(String.format(FORMAT_SEARCH_STRING, numControlFiltrado));
            params.add(String.format(FORMAT_SEARCH_STRING, rfcFiltrado));
        } else if (isValidoNumControl && !isValidoRfc && isValidaFecha) {
            clausula.append(NUMCONTROL_FECHA);
            params.add(numEmpleado);
            params.add(String.format(FORMAT_SEARCH_STRING, numControlFiltrado));
            params.add(Utils.darFormatoFecha(obtenExtremosFecha(fechaFecibido, ConstantesDyCNumerico.VALOR_1_NEG), FORMATOFECHA));
            params.add(Utils.darFormatoFecha(obtenExtremosFecha(fechaFecibido, ConstantesDyCNumerico.VALOR_1), FORMATOFECHA));
            params.add(numEmpleado);
            params.add(String.format(FORMAT_SEARCH_STRING, numControlFiltrado));
            params.add(Utils.darFormatoFecha(obtenExtremosFecha(fechaFecibido, ConstantesDyCNumerico.VALOR_1_NEG), FORMATOFECHA));
            params.add(Utils.darFormatoFecha(obtenExtremosFecha(fechaFecibido, ConstantesDyCNumerico.VALOR_1), FORMATOFECHA));
        } else if (!isValidoNumControl && isValidoRfc && isValidaFecha) {
            clausula.append(RFC_FECHA);
            params.add(numEmpleado);
            params.add(String.format(FORMAT_SEARCH_STRING, rfcFiltrado));
            params.add(Utils.darFormatoFecha(obtenExtremosFecha(fechaFecibido, ConstantesDyCNumerico.VALOR_1_NEG), FORMATOFECHA));
            params.add(Utils.darFormatoFecha(obtenExtremosFecha(fechaFecibido, ConstantesDyCNumerico.VALOR_1), FORMATOFECHA));
            params.add(numEmpleado);
            params.add(String.format(FORMAT_SEARCH_STRING, rfcFiltrado));
            params.add(Utils.darFormatoFecha(obtenExtremosFecha(fechaFecibido, ConstantesDyCNumerico.VALOR_1_NEG), FORMATOFECHA));
            params.add(Utils.darFormatoFecha(obtenExtremosFecha(fechaFecibido, ConstantesDyCNumerico.VALOR_1), FORMATOFECHA));
        } else if (isValidoNumControl && isValidoRfc && isValidaFecha) {
            clausula.append(ALL_FILTROS);
            params.add(numEmpleado);
            params.add(String.format(FORMAT_SEARCH_STRING, numControlFiltrado));
            params.add(String.format(FORMAT_SEARCH_STRING, rfcFiltrado));
            params.add(Utils.darFormatoFecha(obtenExtremosFecha(fechaFecibido, ConstantesDyCNumerico.VALOR_1_NEG), FORMATOFECHA));
            params.add(Utils.darFormatoFecha(obtenExtremosFecha(fechaFecibido, ConstantesDyCNumerico.VALOR_1), FORMATOFECHA));
            params.add(numEmpleado);
            params.add(String.format(FORMAT_SEARCH_STRING, numControlFiltrado));
            params.add(String.format(FORMAT_SEARCH_STRING, rfcFiltrado));
            params.add(Utils.darFormatoFecha(obtenExtremosFecha(fechaFecibido, ConstantesDyCNumerico.VALOR_1_NEG), FORMATOFECHA));
            params.add(Utils.darFormatoFecha(obtenExtremosFecha(fechaFecibido, ConstantesDyCNumerico.VALOR_1), FORMATOFECHA));
        } else {
            params.add(numEmpleado);
            params.add(numEmpleado);
        }
    }

    private Date obtenExtremosFecha(Date fechaRecibido, int dias) {
        Calendar calFecha = Calendar.getInstance();
        calFecha.setTime(fechaRecibido);
        calFecha.add(Calendar.DAY_OF_YEAR, dias);

        return calFecha.getTime();
    }

    private boolean validaRfcFiltro(String rfcFiltrado) {
        return (rfcFiltrado != null && !rfcFiltrado.isEmpty());
    }

    private boolean validaNumControlFiltro(String numControlFiltrado) {
        return (numControlFiltrado != null && !numControlFiltrado.isEmpty());
    }

    private boolean validaFechaFiltro(Date fechaFecibido) {
        return (fechaFecibido != null);
    }

}
