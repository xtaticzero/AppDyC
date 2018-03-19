/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.servicio.service.cpr.impl;

import javax.xml.bind.JAXBException;

import mx.gob.sat.siat.dyc.dao.periodo.DyccPeriodoDAO;
import mx.gob.sat.siat.dyc.domain.cpr.CPRDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.servicio.dao.cpr.CPRDAO;
import mx.gob.sat.siat.dyc.servicio.service.cpr.CPRService;
import mx.gob.sat.siat.dyc.util.constante.ConstantesXSD;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.xml.sax.SAXException;


/**
 * @author Israel Chavez
 */
@Service(value = "cprService")
public class CPRServiceimpl implements CPRService {

    @Autowired
    private CPRDAO cprDao;

    @Autowired
    private DyccPeriodoDAO dyccPeriodoDAO;

    public CPRServiceimpl() {
        super();
    }

    /**
     * @param
     * @return
     */
    @Override
    public CPRDTO obtieneCPR(CPRDTO cprDto) {

        DyccPeriodoDTO dyccPeriodoDTO =
            dyccPeriodoDAO.getFIniFFinPeriodo(cprDto.getIdTipoPeriodo(), cprDto.getEjercicio());

        cprDto.setTxtfeciniper(dyccPeriodoDTO.getFechaInicio());
        cprDto.setTxtfecfinper(dyccPeriodoDTO.getFechaFin());
        return cprDao.obtenerCPR(cprDto);
    }

    /**
     * @param
     * @return
     */
    @Override
    public byte[] obtieneCprXmlByteArray(CPRDTO cprDto) throws JAXBException, SAXException {

        DyccPeriodoDTO dyccPeriodoDTO =
            dyccPeriodoDAO.getFIniFFinPeriodo(cprDto.getIdTipoPeriodo(), cprDto.getEjercicio());

        cprDto.setTxtfeciniper(dyccPeriodoDTO.getFechaInicio());
        cprDto.setTxtfecfinper(dyccPeriodoDTO.getFechaFin());

        CPRDTO cpr = this.cprDao.obtenerCPR(cprDto);

        return cpr.generateXML(ConstantesXSD.CPR_XSD);
    }

}
