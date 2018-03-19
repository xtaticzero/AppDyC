package mx.gob.sat.siat.dyc.servicio.resolucion.impl;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctResolucionDTO;
import mx.gob.sat.siat.dyc.servicio.resolucion.DyctResolucionService;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.vo.EmitirResolucionVO;

import java.util.Date;
import java.util.List;
import mx.gob.sat.siat.dyc.dao.resolucion.DyctResolucionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("dyctResolucionService")
public class DyctResolucionServiceImpl implements DyctResolucionService {

    @Autowired
    private DyctResolucionDAO dyctResolucionDAO;

    @Override
    public DyctResolucionDTO consultarDatosResolucionXNumeroControl(String numControl) throws SIATException {
        return dyctResolucionDAO.consultarDatosResolucionXNumeroControl(numControl);
    }

    @Override
    public DyctResolucionDTO obtener(String numControl) throws SIATException {
        return dyctResolucionDAO.obtener(numControl);
    }

    @Override
    public Integer existeResolucionAprobadaEnAprobacion(String numControl) throws SIATException {
        return dyctResolucionDAO.existeResolucionAprobadaEnAprobacion(numControl);
    }

    @Override
    public Integer existeResolucionNoAprobada(String numControl) throws SIATException {
        return dyctResolucionDAO.existeResolucionNoAprobada(numControl);
    }

    @Override
    public Integer existeResolucion(String numControl) throws SIATException {
        return dyctResolucionDAO.existeResolucion(numControl);
    }

    @Override
    public void actualizarEstResol(Integer idEstResol, String numControl) {
        dyctResolucionDAO.actualizarEstResol(idEstResol, numControl);
    }

    @Override
    public boolean existe(String numControl) {
        return dyctResolucionDAO.existe(numControl);
    }

    @Override
    public void actualizarSaldoAFavor(String numControl, double saldoAFavorAntRes, double saldoAFavorDesRes) throws SIATException {
        dyctResolucionDAO.actualizarSaldoAFavor(numControl, saldoAFavorAntRes, saldoAFavorDesRes);
    }

    @Override
    public List<EmitirResolucionVO> buscarInformacionRequerida(String numControl, int tipoDocumento) {
        return dyctResolucionDAO.buscarInformacionRequerida(numControl, tipoDocumento);
    }

    @Override
    public List<EmitirResolucionVO> buscarAnexosRequerir(String numControl, int tipoDocumento) {
        return dyctResolucionDAO.buscarAnexosRequerir(numControl, tipoDocumento);
    }

    @Override
    public List<EmitirResolucionVO> buscarOtraInfoRequerir(String numControl, int tipoDocumento) {
        return dyctResolucionDAO.buscarOtraInfoRequerir(numControl, tipoDocumento);
    }

    @Override
    public void actualizarPagoEnviado(Integer pagoEnviado, String numControl) throws SIATException {
        dyctResolucionDAO.actualizarPagoEnviado(pagoEnviado, numControl);
    }

    @Override
    public List<DyctResolucionDTO> obtenerAprobadasPorIdSaldoICEP(Integer idSaldoICEP) throws SIATException {
        return dyctResolucionDAO.obtenerAprobadasPorIdSaldoICEP(idSaldoICEP);
    }

    @Override
    public void insertar(DyctResolucionDTO dyctResolucionDTO) throws SIATException {
        dyctResolucionDAO.insertar(dyctResolucionDTO);
    }

    @Override
    public void insertResolucionSimulador(String numControl, double importeAutorizado, double importeNegado, double interes) throws SIATException {
        dyctResolucionDAO.insertResolucionSimulador(numControl, importeAutorizado, importeNegado, interes);
    }

    @Override
    public void actualizarFechaAprobacion(String numeroControl) throws SIATException {
        dyctResolucionDAO.actualizarFechaAprobacion(numeroControl);
    }

    @Override
    public void actualizarClaveRastreo(String numeroControl, String claveRastreo) throws SIATException {
        dyctResolucionDAO.actualizarClaveRastreo(numeroControl, claveRastreo);
    }

    @Override
    public DyctResolucionDTO consultarDatosResolucionXCveRastreo(String numControl) throws SIATException {
        return dyctResolucionDAO.consultarDatosResolucionXCveRastreo(numControl);
    }

    @Override
    public DyctResolucionDTO encontrar(DycpSolicitudDTO solicitud) {
        return dyctResolucionDAO.encontrar(solicitud);
    }

    @Override
    public boolean verificarSiEsAGAFoAGGC(String claveRastreo, String cveAdministracion) {
        return dyctResolucionDAO.verificarSiEsAGAFoAGGC(claveRastreo, cveAdministracion);
    }

    @Override
    public void actualizarFechasDeEnvioATESOFE(Date fechaPresentacion, Date fechaPago, String numControl) throws SIATException {
        dyctResolucionDAO.actualizarFechasDeEnvioATESOFE(fechaPresentacion, fechaPago, numControl);
    }
}
