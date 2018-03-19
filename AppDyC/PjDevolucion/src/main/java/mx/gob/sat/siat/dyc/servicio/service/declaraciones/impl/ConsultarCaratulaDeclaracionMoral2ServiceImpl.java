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

import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.ConsultarCaratulaDeclaracionMoral2DAO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionmoral.ConsultarCaratulaDeclaracionMoral2Forma2ADTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionmoral.ConsultarCaratulaDeclaracionMoral2RegistroDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionmoral.ConsultarCaratulaDeclaracionMoral2RenglonDTO;
import mx.gob.sat.siat.dyc.servicio.service.declaraciones.ConsultarCaratulaDeclaracionMoral2Service;

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
@Service(value = "consultarCaratulaDeclaracionMoral2Service")
public class ConsultarCaratulaDeclaracionMoral2ServiceImpl implements ConsultarCaratulaDeclaracionMoral2Service {

    private Logger logger = Logger.getLogger(ConsultarCaratulaDeclaracionMoral2ServiceImpl.class);

    @Autowired
    private ConsultarCaratulaDeclaracionMoral2DAO consultarCaratulaDeclaracionMoral2DAO;

    @Override
    public List<ConsultarCaratulaDeclaracionMoral2RenglonDTO> consultaDatosRenglon(ConsultarCaratulaDeclaracionMoral2RenglonDTO consultarCaratulaDeclaracionMoral2RenglonDTO) {
        List<ConsultarCaratulaDeclaracionMoral2RenglonDTO> declaracionMoral2RenglonArray =
            new ArrayList<ConsultarCaratulaDeclaracionMoral2RenglonDTO>();
        try {
            declaracionMoral2RenglonArray =
                    consultarCaratulaDeclaracionMoral2DAO.consultaDatosRenglon(consultarCaratulaDeclaracionMoral2RenglonDTO);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return declaracionMoral2RenglonArray;
    }

    @Override
    public List<ConsultarCaratulaDeclaracionMoral2Forma2ADTO> consultaDatosForma2(ConsultarCaratulaDeclaracionMoral2Forma2ADTO consultarCaratulaDeclaracionMoral2Forma2ADTO) {
        List<ConsultarCaratulaDeclaracionMoral2Forma2ADTO> declaracionMoral2FormaArray =
            new ArrayList<ConsultarCaratulaDeclaracionMoral2Forma2ADTO>();
        try {
            declaracionMoral2FormaArray =
                    consultarCaratulaDeclaracionMoral2DAO.consultaDatosForma2(consultarCaratulaDeclaracionMoral2Forma2ADTO);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return declaracionMoral2FormaArray;
    }

    @Override
    public List<ConsultarCaratulaDeclaracionMoral2RegistroDTO> consultaDatosRegistro2002(ConsultarCaratulaDeclaracionMoral2RegistroDTO consultarCaratulaDeclaracionMoral2RegistroDTO) {
        List<ConsultarCaratulaDeclaracionMoral2RegistroDTO> declaracionMoral2RegistroArray =
            new ArrayList<ConsultarCaratulaDeclaracionMoral2RegistroDTO>();
        try {
            declaracionMoral2RegistroArray =
                    consultarCaratulaDeclaracionMoral2DAO.consultaDatosRegistro2002(consultarCaratulaDeclaracionMoral2RegistroDTO);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return declaracionMoral2RegistroArray;
    }

    @Override
    public List<ConsultarCaratulaDeclaracionMoral2RegistroDTO> consultaDatosRegistro(ConsultarCaratulaDeclaracionMoral2RegistroDTO consultarCaratulaDeclaracionMoral2RegistroDTO) {
        List<ConsultarCaratulaDeclaracionMoral2RegistroDTO> declaracionMoral2RegistroArray =
            new ArrayList<ConsultarCaratulaDeclaracionMoral2RegistroDTO>();
        try {
            declaracionMoral2RegistroArray =
                    consultarCaratulaDeclaracionMoral2DAO.consultaDatosRegistro(consultarCaratulaDeclaracionMoral2RegistroDTO);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return declaracionMoral2RegistroArray;
    }

    public void setConsultarCaratulaDeclaracionMoral2DAO(ConsultarCaratulaDeclaracionMoral2DAO consultarCaratulaDeclaracionMoral2Dao) {
        this.consultarCaratulaDeclaracionMoral2DAO = consultarCaratulaDeclaracionMoral2Dao;
    }

    public ConsultarCaratulaDeclaracionMoral2DAO getConsultarCaratulaDeclaracionMoral2DAO() {
        return consultarCaratulaDeclaracionMoral2DAO;
    }

}
