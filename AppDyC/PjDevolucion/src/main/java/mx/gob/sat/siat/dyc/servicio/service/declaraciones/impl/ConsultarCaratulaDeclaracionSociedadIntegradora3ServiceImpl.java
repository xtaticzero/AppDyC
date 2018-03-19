/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.service.declaraciones.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.ConsultarCaratulaDeclaracionSociedadIntegradora3DAO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionsociedad.ConsultarCaratulaDeclaracionSociedadIntegradora3Forma3DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionsociedad.ConsultarCaratulaDeclaracionSociedadIntegradora3RegDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionsociedad.ConsultarCaratulaDeclaracionSociedadIntegradora3RenglonDTO;
import mx.gob.sat.siat.dyc.servicio.service.declaraciones.ConsultarCaratulaDeclaracionSociedadIntegradora3Service;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


/**
 *
 * @author Alfredo Ramirez
 * @since 29/07/2013
 *
 */
@Service(value = "consultarCaratulaDeclaracionSociedadIntegradora3Service")
public class ConsultarCaratulaDeclaracionSociedadIntegradora3ServiceImpl implements ConsultarCaratulaDeclaracionSociedadIntegradora3Service {

    private Logger logger = Logger.getLogger(ConsultarCaratulaDeclaracionSociedadIntegradora3ServiceImpl.class);

    @Autowired
    private ConsultarCaratulaDeclaracionSociedadIntegradora3DAO consultarCaratulaDeclaracionSociedadIntegradora3DAO;

    @Override
    public List<ConsultarCaratulaDeclaracionSociedadIntegradora3RenglonDTO> consultaDatosRenglon(ConsultarCaratulaDeclaracionSociedadIntegradora3RenglonDTO consultarCaratulaDeclaracionSociedadIntegradora3RenglonDTO) {
        List<ConsultarCaratulaDeclaracionSociedadIntegradora3RenglonDTO> declaracionSociedadArray =
            new ArrayList<ConsultarCaratulaDeclaracionSociedadIntegradora3RenglonDTO>();
        try {
            declaracionSociedadArray =
                    consultarCaratulaDeclaracionSociedadIntegradora3DAO.consultaDatosRenglon(consultarCaratulaDeclaracionSociedadIntegradora3RenglonDTO);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return declaracionSociedadArray;
    }

    @Override
    public List<ConsultarCaratulaDeclaracionSociedadIntegradora3Forma3DTO> consultaDatosForma3(ConsultarCaratulaDeclaracionSociedadIntegradora3Forma3DTO consultarCaratulaDeclaracionSociedadIntegradora3Forma3DTO) {
        List<ConsultarCaratulaDeclaracionSociedadIntegradora3Forma3DTO> declaracionSociedadArray =
            new ArrayList<ConsultarCaratulaDeclaracionSociedadIntegradora3Forma3DTO>();
        try {
            declaracionSociedadArray =
                    consultarCaratulaDeclaracionSociedadIntegradora3DAO.consultaDatosForma3(consultarCaratulaDeclaracionSociedadIntegradora3Forma3DTO);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return declaracionSociedadArray;
    }

    @Override
    public List<ConsultarCaratulaDeclaracionSociedadIntegradora3RegDTO> consultaDatosRegistro2002(ConsultarCaratulaDeclaracionSociedadIntegradora3RegDTO consultarCaratulaDeclaracionSociedadIntegradora3Reg2002DTO) {
        List<ConsultarCaratulaDeclaracionSociedadIntegradora3RegDTO> declaracionSociedadArray =
            new ArrayList<ConsultarCaratulaDeclaracionSociedadIntegradora3RegDTO>();
        try {
            declaracionSociedadArray =
                    consultarCaratulaDeclaracionSociedadIntegradora3DAO.consultaDatosRegistro2002(consultarCaratulaDeclaracionSociedadIntegradora3Reg2002DTO);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return declaracionSociedadArray;
    }

    @Override
    public List<ConsultarCaratulaDeclaracionSociedadIntegradora3RegDTO> consultaDatosRegistro(ConsultarCaratulaDeclaracionSociedadIntegradora3RegDTO consultarCaratulaDeclaracionSociedadIntegradora3RegDTO) {
        List<ConsultarCaratulaDeclaracionSociedadIntegradora3RegDTO> declaracionSociedadArray =
            new ArrayList<ConsultarCaratulaDeclaracionSociedadIntegradora3RegDTO>();
        try {
            declaracionSociedadArray =
                    consultarCaratulaDeclaracionSociedadIntegradora3DAO.consultaDatosRegistro(consultarCaratulaDeclaracionSociedadIntegradora3RegDTO);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return declaracionSociedadArray;
    }

    public void setConsultarCaratulaDeclaracionSociedadIntegradora3DAO(ConsultarCaratulaDeclaracionSociedadIntegradora3DAO consultarCaratulaDeclaracionSociedadIntegradora3Dao) {
        this.consultarCaratulaDeclaracionSociedadIntegradora3DAO = consultarCaratulaDeclaracionSociedadIntegradora3Dao;
    }

    public ConsultarCaratulaDeclaracionSociedadIntegradora3DAO getConsultarCaratulaDeclaracionSociedadIntegradora3DAO() {
        return consultarCaratulaDeclaracionSociedadIntegradora3DAO;
    }

}
