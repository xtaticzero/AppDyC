package mx.gob.sat.mat.dyc.registrarinformaciondeicep.service.impl;

import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;

import mx.gob.sat.mat.dyc.registrarinformaciondeicep.service.RegistrarInformacionDeIcepService;
import mx.gob.sat.siat.dyc.dao.declaracion.DyctDeclaracionDAO;
import mx.gob.sat.siat.dyc.dao.detalleicep.DyctSaldoIcepDAO;
import mx.gob.sat.siat.dyc.dao.movsaldo.DyctMovSaldoDAO;
import mx.gob.sat.siat.dyc.dao.regsolicitud.DycpSolicitudDAO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaracionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctMovSaldoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


/**
 * autor: AdrianAguilar
 */

@Service(value = "registrarInformacionDeIcepService")
public class RegistrarInformacionDeIcepServiceImpl implements RegistrarInformacionDeIcepService {

    @Autowired
    private DyctDeclaracionDAO dyctDeclaracionDAO;

    @Autowired
    private DyctSaldoIcepDAO dyctSaldoIcepDAO;

    @Autowired
    private DyctMovSaldoDAO dyctMovSaldoDAO;

    @Autowired
    private DycpSolicitudDAO dycpSolicitudDAO;
    
    @Override
    public DyctSaldoIcepDTO consultarIcep(int idSaldoIcep) {
        return dyctSaldoIcepDAO.encontrar(idSaldoIcep);
    }
    
    @Override
    public void registrarIcep(DyctSaldoIcepDTO dyctSaldoIcepDTO)
    {
        try {
            dyctSaldoIcepDAO.insert(dyctSaldoIcepDTO);
        } catch (SIATException ex) {
            Logger.getLogger(RegistrarInformacionDeIcepServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void registrarSolicitud(DycpSolicitudDTO dycpSolicitudDTO)
    {
        try {
            dycpSolicitudDAO.insertaServicioSolicitud(dycpSolicitudDTO);
        } catch (SQLException ex) {
            Logger.getLogger(RegistrarInformacionDeIcepServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SIATException ex) {
            Logger.getLogger(RegistrarInformacionDeIcepServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void registrarDeclaracion(DyctDeclaracionDTO dyctDeclaracionDTO) {
        try {
        dyctDeclaracionDAO.createDeclaracion(dyctDeclaracionDTO);        
        } catch (DataAccessException ex) {
            Logger.getLogger(RegistrarInformacionDeIcepServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void registrarMovSaldo(DyctMovSaldoDTO dyctMovSaldoDTO) {
        try {
            dyctMovSaldoDAO.insertar(dyctMovSaldoDTO);
        } catch (SIATException ex) {
            Logger.getLogger(RegistrarInformacionDeIcepServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
