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

import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.ConsultarCaratulaDeclaracionSociedadControladora2ADAO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionsociedad.ConsultarCaratulaDeclaracionSociedadControladora2AForma2DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionsociedad.ConsultarCaratulaDeclaracionSociedadControladora2ARegDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionsociedad.ConsultarCaratulaDeclaracionSociedadControladora2ARenglonDTO;
import mx.gob.sat.siat.dyc.servicio.service.declaraciones.ConsultarCaratulaDeclaracionSociedadControladora2AService;

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
@Service(value = "consultarCaratulaDeclaracionSociedadControladora2AService")
public class ConsultarCaratulaDeclaracionSociedadControladora2AServiceImpl implements ConsultarCaratulaDeclaracionSociedadControladora2AService {

    private Logger logger = Logger.getLogger(ConsultarCaratulaDeclaracionSociedadControladora2AServiceImpl.class);

    @Autowired
    private ConsultarCaratulaDeclaracionSociedadControladora2ADAO consultarCaratulaDeclaracionSociedadControladora2ADAO;

    @Override
    public List<ConsultarCaratulaDeclaracionSociedadControladora2ARenglonDTO> consultaDatosRenglon(ConsultarCaratulaDeclaracionSociedadControladora2ARenglonDTO consultarCaratulaDeclaracionSociedadControladora2RenglonDTO) {
        List<ConsultarCaratulaDeclaracionSociedadControladora2ARenglonDTO> declaracionSociedadArray =
            new ArrayList<ConsultarCaratulaDeclaracionSociedadControladora2ARenglonDTO>();
        try {
            declaracionSociedadArray =
                    consultarCaratulaDeclaracionSociedadControladora2ADAO.consultaDatosRenglon(consultarCaratulaDeclaracionSociedadControladora2RenglonDTO);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return declaracionSociedadArray;
    }

    @Override
    public List<ConsultarCaratulaDeclaracionSociedadControladora2AForma2DTO> consultaDatosForma2(ConsultarCaratulaDeclaracionSociedadControladora2AForma2DTO consultarCaratulaDeclaracionSociedadControladora2AForma2DTO) {
        List<ConsultarCaratulaDeclaracionSociedadControladora2AForma2DTO> declaracionSociedadArray =
            new ArrayList<ConsultarCaratulaDeclaracionSociedadControladora2AForma2DTO>();
        try {
            declaracionSociedadArray =
                    consultarCaratulaDeclaracionSociedadControladora2ADAO.consultaDatosForma2(consultarCaratulaDeclaracionSociedadControladora2AForma2DTO);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return declaracionSociedadArray;
    }

    @Override
    public List<ConsultarCaratulaDeclaracionSociedadControladora2ARegDTO> consultaDatosRegistro2002(ConsultarCaratulaDeclaracionSociedadControladora2ARegDTO consultarCaratulaDeclaracionSociedadIntegradora2ARegDTO) {
        List<ConsultarCaratulaDeclaracionSociedadControladora2ARegDTO> declaracionSociedadArray =
            new ArrayList<ConsultarCaratulaDeclaracionSociedadControladora2ARegDTO>();
        try {
            declaracionSociedadArray =
                    consultarCaratulaDeclaracionSociedadControladora2ADAO.consultaDatosRegistro2002(consultarCaratulaDeclaracionSociedadIntegradora2ARegDTO);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return declaracionSociedadArray;
    }

    @Override
    public List<ConsultarCaratulaDeclaracionSociedadControladora2ARegDTO> consultaDatosRegistro(ConsultarCaratulaDeclaracionSociedadControladora2ARegDTO consultarCaratulaDeclaracionSociedadIntegradora2ARegDTO) {
        List<ConsultarCaratulaDeclaracionSociedadControladora2ARegDTO> declaracionSociedadArray =
            new ArrayList<ConsultarCaratulaDeclaracionSociedadControladora2ARegDTO>();
        try {
            declaracionSociedadArray =
                    consultarCaratulaDeclaracionSociedadControladora2ADAO.consultaDatosRegistro(consultarCaratulaDeclaracionSociedadIntegradora2ARegDTO);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return declaracionSociedadArray;
    }

    public void setConsultarCaratulaDeclaracionSociedadControladora2ADAO(ConsultarCaratulaDeclaracionSociedadControladora2ADAO consultarCaratulaDeclaracionSociedadControladora2ADao) {
        this.consultarCaratulaDeclaracionSociedadControladora2ADAO =
                consultarCaratulaDeclaracionSociedadControladora2ADao;
    }

    public ConsultarCaratulaDeclaracionSociedadControladora2ADAO getConsultarCaratulaDeclaracionSociedadControladora2ADAO() {
        return consultarCaratulaDeclaracionSociedadControladora2ADAO;
    }

}
