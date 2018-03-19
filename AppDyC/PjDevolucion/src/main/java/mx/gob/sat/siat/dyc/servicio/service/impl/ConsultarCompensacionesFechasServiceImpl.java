package mx.gob.sat.siat.dyc.servicio.service.impl;

import java.io.FileNotFoundException;

import java.sql.SQLException;

import java.util.List;

import mx.gob.sat.siat.dyc.servicio.dao.altex.ConsultarContribuyenteAltamenteExportadorDAO;
import mx.gob.sat.siat.dyc.servicio.dao.compensaciones.ConsultarCompensacionesDao;
import mx.gob.sat.siat.dyc.servicio.dao.creditosfiscales.ConsultarDetalleCreditosFiscalesDAO;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.ConsultarDeclaracionIAPersonaFisicaDAO;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.ConsultarDeclaracionIEPS4DAO;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.ConsultarDeclaracionISRMoralDAO;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.ConsultarDeclaracionISRSociedadControladora1DAO;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.ConsultarDeclaracionISRsociedadIntegradoraDAO;
import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.ConsultarDeterminacionDeImpuestosDAO;
import mx.gob.sat.siat.dyc.servicio.dao.devoluciones.ConsultarDevolucionesDAO;
import mx.gob.sat.siat.dyc.servicio.dao.dictamenes.ConsultarDictamenesDAO;
import mx.gob.sat.siat.dyc.servicio.dto.altex.ConsultarContribuyenteAltamenteExportadorDTO;
import mx.gob.sat.siat.dyc.servicio.dto.compensaciones.ConsultarCompensacionesFechasDTO;
import mx.gob.sat.siat.dyc.servicio.dto.compensaciones.ConsultarCompensacionesInformacionDTO;
import mx.gob.sat.siat.dyc.servicio.dto.creditosfiscales.ConsultarDetalleCreditosFiscalesDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeclaracionIEPS4DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeclaracionISRMoralDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeclaracionISRSociedadControladora1DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeclaracionISRSociedadControladora2DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeclaracionISRsociedadIntegradoraDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionDeImpuestosCdiDpdifDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoCdiDpdifAnioDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoCdiImpuestosDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma132DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma13ADTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma13DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma1EDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma22DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma2DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma2a2DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma2aDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma32DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestosForma3DTO;
import mx.gob.sat.siat.dyc.servicio.dto.devoluciones.ConsultarDevolucionesDTO;
import mx.gob.sat.siat.dyc.servicio.dto.dictamenes.ConsultarDictamenesDTO;
import mx.gob.sat.siat.dyc.servicio.service.ConsultarCompensacionesFechasService;
import mx.gob.sat.siat.dyc.util.constante.ConstantesConsultarCompensaciones;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyC;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service(value = "ConsultarCompensacionesFechasService")
public class ConsultarCompensacionesFechasServiceImpl implements ConsultarCompensacionesFechasService {

    public ConsultarCompensacionesFechasServiceImpl() {
        super();
    }
    @Autowired
    private ConsultarCompensacionesDao consultarCompensacionesDao;

    @Autowired
    private ConsultarDetalleCreditosFiscalesDAO consultarDetalleCreditosFiscalesDAO;

    @Autowired
    private ConsultarContribuyenteAltamenteExportadorDAO consultarContribuyenteAltamenteExportadorDao;

    @Autowired
    private ConsultarDevolucionesDAO consultarDevolucionesDAO;

    @Autowired
    private ConsultarDictamenesDAO consultarDictamenesDAO;

    @Autowired
    private ConsultarDeterminacionDeImpuestosDAO consultardeterminacionesDAO;

    @Autowired
    private ConsultarDeclaracionISRSociedadControladora1DAO consultarDeclaracionISRSociedadControladora1DAO;

    @Autowired
    private ConsultarDeclaracionISRsociedadIntegradoraDAO consultarDeclaracionISRsociedadIntegradoraDAO;

    @Autowired
    private ConsultarDeclaracionISRMoralDAO consultarDeclaracionISRMoralDAO;

    @Autowired
    private ConsultarDeclaracionIAPersonaFisicaDAO consultarDeclaracionIAPersonaFisicaDAO;

    @Autowired
    private ConsultarDeclaracionIEPS4DAO consultarDeclaracionIEPS4DAO;

    /**
     * @return
     * @throws FileNotFoundException
     */
    @Override
    public List<ConsultarCompensacionesFechasDTO> consultarCompensacionesFechas() throws FileNotFoundException

    {
        ConsultarCompensacionesFechasDTO consultarCompensacionesFechasDto = new ConsultarCompensacionesFechasDTO();

        List<ConsultarCompensacionesFechasDTO> informacionRecuperada =
            consultarCompensacionesDao.consultarCompensacionesFechas(consultarCompensacionesFechasDto);

        return informacionRecuperada;
    }

    /**
     * @return
     * @throws FileNotFoundException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Override
    public List<ConsultarCompensacionesInformacionDTO> consultarCompensacionesInformacion() throws FileNotFoundException,
                                                                                                   ClassNotFoundException,
                                                                                                   SQLException {

        ConsultarCompensacionesInformacionDTO consultarCompensacionesInformacionDto =
            new ConsultarCompensacionesInformacionDTO();

        consultarCompensacionesInformacionDto.setRfceeog1("COS850425822");
        consultarCompensacionesInformacionDto.setPsfieaa1("072000");
        consultarCompensacionesInformacionDto.setPsffeaa1("072000");
        consultarCompensacionesInformacionDto.setCStiCcloanv1(ConstantesDyCNumerico.VALOR_301);

        List<ConsultarCompensacionesInformacionDTO> informacionRecuperada =
            consultarCompensacionesDao.consultarCompensacionesInformacionCompensaciones(consultarCompensacionesInformacionDto);

        return informacionRecuperada;
    }

    @Override
    public List<ConsultarDetalleCreditosFiscalesDTO> consultarDetalleCreditosFiscales() throws FileNotFoundException,
                                                                                               ClassNotFoundException,
                                                                                               SQLException {

        ConsultarDetalleCreditosFiscalesDTO consultarDetalleCreditosFiscalesDTO =
            new ConsultarDetalleCreditosFiscalesDTO();

        consultarDetalleCreditosFiscalesDTO.setRfceeog1("HCO990119E51");


        List<ConsultarDetalleCreditosFiscalesDTO> informacionRecuperada =
            consultarDetalleCreditosFiscalesDAO.consultaDetalleCreditosFiscales(consultarDetalleCreditosFiscalesDTO);

        return informacionRecuperada;
    }

    @Override
    public List<ConsultarContribuyenteAltamenteExportadorDTO> consultarContribuyenteAltamenteExportadorDTO() throws FileNotFoundException,
                                                                                                                    ClassNotFoundException,
                                                                                                                    SQLException {


        ConsultarContribuyenteAltamenteExportadorDTO consultarContribuyenteAltamenteExportadorDTO =
            new ConsultarContribuyenteAltamenteExportadorDTO();

        consultarContribuyenteAltamenteExportadorDTO.setRfc("PCM930618MD4");
        consultarContribuyenteAltamenteExportadorDTO.setNaltex(ConstantesDyCNumerico.VALOR_4513);


        List<ConsultarContribuyenteAltamenteExportadorDTO> informacionRecuperada =
            consultarContribuyenteAltamenteExportadorDao.consultarContribuyenteAltamenteExportador(consultarContribuyenteAltamenteExportadorDTO);

        return informacionRecuperada;
    }

    @Override
    public List<ConsultarDevolucionesDTO> consultarDevoluciones() throws FileNotFoundException, ClassNotFoundException,
                                                                         SQLException {

        ConsultarDevolucionesDTO consultarDevolucionesDTO = new ConsultarDevolucionesDTO();

        consultarDevolucionesDTO.setRfceeog1("PCM930618MD4");
        consultarDevolucionesDTO.setCDecCplearv1(ConstantesDyCNumerico.VALOR_22005);
        consultarDevolucionesDTO.setPsfieaa1("022005");
        consultarDevolucionesDTO.setCDecCplearv12(ConstantesDyCNumerico.VALOR_22005);
        consultarDevolucionesDTO.setPsffeaa1("022005");
        consultarDevolucionesDTO.setCStiCtslria1(ConstantesDyCNumerico.VALOR_111);

        List<ConsultarDevolucionesDTO> informacionRecuperada =
            consultarDevolucionesDAO.consultarDevoluciones(consultarDevolucionesDTO);

        return informacionRecuperada;
    }

    @Override
    public List<ConsultarDictamenesDTO> consultarDictamenes() throws FileNotFoundException, ClassNotFoundException,
                                                                     SQLException {

        ConsultarDictamenesDTO consultarDictamenesDTO = new ConsultarDictamenesDTO();
        consultarDictamenesDTO.setRfcContribuyente("SAEV5903169Y0");
        consultarDictamenesDTO.setEjercicioFiscal(ConstantesDyCNumerico.VALOR_2008);


        List<ConsultarDictamenesDTO> informacionRecuperada =
            consultarDictamenesDAO.consultarDictamenes(consultarDictamenesDTO);

        return informacionRecuperada;
    }

    @Override
    public List<ConsultarDeterminacionDeImpuestosCdiDpdifDTO> consultarDeterminacionDeImptos() throws FileNotFoundException,
                                                                                                      ClassNotFoundException,
                                                                                                      SQLException {

        ConsultarDeterminacionDeImpuestosCdiDpdifDTO ceterminacionDeImpuestosCdiDpdifDTO =
            new ConsultarDeterminacionDeImpuestosCdiDpdifDTO();

        ceterminacionDeImpuestosCdiDpdifDTO.setCIdeRfceeog1("TOMA670807PSA");
        ceterminacionDeImpuestosCdiDpdifDTO.setCDecCplearv1("99");
        ceterminacionDeImpuestosCdiDpdifDTO.setNDecEjercic1(ConstantesDyCNumerico.VALOR_2009);
        ceterminacionDeImpuestosCdiDpdifDTO.setCOblCcloanv1("303");

        List<ConsultarDeterminacionDeImpuestosCdiDpdifDTO> informacionRecuperada =
            consultardeterminacionesDAO.consultarDeterminacionDeImpuestosCdiDpdif(ceterminacionDeImpuestosCdiDpdifDTO);

        return informacionRecuperada;
    }

    @Override
    public List<ConsultarDeterminacionImpuestoCdiDpdifAnioDTO> consultarDeterminacionImpuestoCdiDpdifAnio() throws FileNotFoundException,
                                                                                                                   ClassNotFoundException,
                                                                                                                   SQLException {

        ConsultarDeterminacionImpuestoCdiDpdifAnioDTO determinacionImpuestoCdiDpdifAnioDTO =
            new ConsultarDeterminacionImpuestoCdiDpdifAnioDTO();

        determinacionImpuestoCdiDpdifAnioDTO.setRfceeog1("BER931008SH8");
        determinacionImpuestoCdiDpdifAnioDTO.setCStiCcloanv1(ConstantesDyCNumerico.VALOR_102);
        determinacionImpuestoCdiDpdifAnioDTO.setCNPeriodo(ConstantesDyCNumerico.VALOR_1);
        determinacionImpuestoCdiDpdifAnioDTO.setNEjercicio(ConstantesDyCNumerico.VALOR_2008);

        List<ConsultarDeterminacionImpuestoCdiDpdifAnioDTO> informacionRecuperada =
            consultardeterminacionesDAO.consultarDeterminacionDeImpuestosCdiDpdifAnio(determinacionImpuestoCdiDpdifAnioDTO);

        return informacionRecuperada;
    }

    @Override
    public List<ConsultarDeterminacionImpuestoCdiImpuestosDTO> consultarDeterminacionImpuestoCdiImpuestos() throws FileNotFoundException,
                                                                                                                   ClassNotFoundException,
                                                                                                                   SQLException {

        ConsultarDeterminacionImpuestoCdiImpuestosDTO determinacionImpuestoCdiImpuestosDTO =
            new ConsultarDeterminacionImpuestoCdiImpuestosDTO();

        determinacionImpuestoCdiImpuestosDTO.setCStiCcloanv1(ConstantesDyCNumerico.VALOR_301);


        List<ConsultarDeterminacionImpuestoCdiImpuestosDTO> informacionRecuperada =
            consultardeterminacionesDAO.consultarDeterminacionImpuestoCdiImpuestos(determinacionImpuestoCdiImpuestosDTO);

        return informacionRecuperada;
    }

    @Override
    public List<ConsultarDeterminacionImpuestoForma13DTO> consultarDeterminacionImpuestoForma13() throws FileNotFoundException,
                                                                                                         ClassNotFoundException,
                                                                                                         SQLException {

        ConsultarDeterminacionImpuestoForma13DTO determinacionImpuestoForma13DTO =
            new ConsultarDeterminacionImpuestoForma13DTO();

        determinacionImpuestoForma13DTO.setRfceeog1(ConstantesConsultarCompensaciones.RFCEEOG1);
        determinacionImpuestoForma13DTO.setCNPeriodo(ConstantesDyCNumerico.VALOR_35);
        determinacionImpuestoForma13DTO.setNEjercicio(ConstantesDyCNumerico.VALOR_2006);


        List<ConsultarDeterminacionImpuestoForma13DTO> informacionRecuperada =
            consultardeterminacionesDAO.consultarDeterminacionImpuestoForma13(determinacionImpuestoForma13DTO);

        return informacionRecuperada;
    }

    @Override
    public List<ConsultarDeterminacionImpuestoForma13ADTO> consultarDeterminacionImpuestoForma13A() throws FileNotFoundException,
                                                                                                           ClassNotFoundException,
                                                                                                           SQLException {

        ConsultarDeterminacionImpuestoForma13ADTO determinacionImpuestoForma13ADTO =
            new ConsultarDeterminacionImpuestoForma13ADTO();

        determinacionImpuestoForma13ADTO.setRfceeog1(ConstantesConsultarCompensaciones.RFCEEOG1);
        determinacionImpuestoForma13ADTO.setCNPeriodo(ConstantesDyCNumerico.VALOR_35);
        determinacionImpuestoForma13ADTO.setNEjercicio(ConstantesDyCNumerico.VALOR_2007);


        List<ConsultarDeterminacionImpuestoForma13ADTO> informacionRecuperada =
            consultardeterminacionesDAO.consultarDeterminacionImpuestoForma13A(determinacionImpuestoForma13ADTO);

        return informacionRecuperada;
    }

    @Override
    public List<ConsultarDeterminacionImpuestoForma2DTO> consultarDeterminacionImpuestoForma2() throws FileNotFoundException,
                                                                                                       ClassNotFoundException,
                                                                                                       SQLException {


        ConsultarDeterminacionImpuestoForma2DTO determinacionImpuestoForma2DTO =
            new ConsultarDeterminacionImpuestoForma2DTO();

        determinacionImpuestoForma2DTO.setRfceeog1(ConstantesDyC.AAC810320RQ1);
        determinacionImpuestoForma2DTO.setCDecCplearv1(ConstantesDyCNumerico.VALOR_34);
        determinacionImpuestoForma2DTO.setIapidne1(ConstantesDyCNumerico.VALOR_2000);
        determinacionImpuestoForma2DTO.setIapfdne1(ConstantesDyCNumerico.VALOR_2000);


        List<ConsultarDeterminacionImpuestoForma2DTO> informacionRecuperada =
            consultardeterminacionesDAO.consultarDeterminacionImpuestoForma2(determinacionImpuestoForma2DTO);

        return informacionRecuperada;
    }

    @Override
    public List<ConsultarDeterminacionImpuestoForma2aDTO> consultarDeterminacionImpuestoForma2a() throws FileNotFoundException,
                                                                                                         ClassNotFoundException,
                                                                                                         SQLException {


        ConsultarDeterminacionImpuestoForma2aDTO determinacionImpuestoForma2aDTO =
            new ConsultarDeterminacionImpuestoForma2aDTO();

        determinacionImpuestoForma2aDTO.setRfceeog1(ConstantesDyC.CME590904RE3);
        determinacionImpuestoForma2aDTO.setCDecCplearv1(ConstantesDyCNumerico.VALOR_34);
        determinacionImpuestoForma2aDTO.setIapidne1(ConstantesDyCNumerico.VALOR_96);
        determinacionImpuestoForma2aDTO.setIapfdne1(ConstantesDyCNumerico.VALOR_96);


        List<ConsultarDeterminacionImpuestoForma2aDTO> informacionRecuperada =
            consultardeterminacionesDAO.consultarDeterminacionImpuestoForma2a(determinacionImpuestoForma2aDTO);

        return informacionRecuperada;
    }

    @Override
    public List<ConsultarDeterminacionImpuestosForma3DTO> consultarDeterminacionImpuestosForma3() throws FileNotFoundException,
                                                                                                         ClassNotFoundException,
                                                                                                         SQLException {


        ConsultarDeterminacionImpuestosForma3DTO determinacionImpuestosForma3DTO =
            new ConsultarDeterminacionImpuestosForma3DTO();

        determinacionImpuestosForma3DTO.setRfceeog1("PPR910701LEA");
        determinacionImpuestosForma3DTO.setCDecCplearv1(ConstantesDyCNumerico.VALOR_34);
        determinacionImpuestosForma3DTO.setIapidne1(ConstantesDyCNumerico.VALOR_2000);
        determinacionImpuestosForma3DTO.setIapfdne1(ConstantesDyCNumerico.VALOR_2000);


        List<ConsultarDeterminacionImpuestosForma3DTO> informacionRecuperada =
            consultardeterminacionesDAO.consultarDeterminacionImpuestosForma3(determinacionImpuestosForma3DTO);

        return informacionRecuperada;
    }

    @Override
    public List<ConsultarDeterminacionImpuestoForma132DTO> consultarDeterminacionImpuestoForma132() throws FileNotFoundException,
                                                                                                            ClassNotFoundException,
                                                                                                            SQLException {


        ConsultarDeterminacionImpuestoForma132DTO determinacionImpuestoForma132DTO =
            new ConsultarDeterminacionImpuestoForma132DTO();

        determinacionImpuestoForma132DTO.setRfceeog1(ConstantesConsultarCompensaciones.RFCEEOG1);
        determinacionImpuestoForma132DTO.setCNPeriodo(ConstantesDyCNumerico.VALOR_35);
        determinacionImpuestoForma132DTO.setNEjercicio(ConstantesDyCNumerico.VALOR_2006);


        List<ConsultarDeterminacionImpuestoForma132DTO> informacionRecuperada =
            consultardeterminacionesDAO.consultarDeterminacionImpuestoForma132(determinacionImpuestoForma132DTO);

        return informacionRecuperada;
    }

    @Override
    public List<ConsultarDeterminacionImpuestoForma22DTO> consultarDeterminacionImpuestoForma22() throws FileNotFoundException,
                                                                                                          ClassNotFoundException,
                                                                                                          SQLException {


        ConsultarDeterminacionImpuestoForma22DTO determinacionImpuestoForma22DTO =
            new ConsultarDeterminacionImpuestoForma22DTO();

        determinacionImpuestoForma22DTO.setRfceeog1(ConstantesDyC.AAC810320RQ1);
        determinacionImpuestoForma22DTO.setCDecCplearv1(ConstantesDyCNumerico.VALOR_34);
        determinacionImpuestoForma22DTO.setIapidne1(ConstantesDyCNumerico.VALOR_2000);
        determinacionImpuestoForma22DTO.setIapfdne1(ConstantesDyCNumerico.VALOR_2000);


        List<ConsultarDeterminacionImpuestoForma22DTO> informacionRecuperada =
            consultardeterminacionesDAO.consultarDeterminacionImpuestoForma22(determinacionImpuestoForma22DTO);

        return informacionRecuperada;
    }

    @Override
    public List<ConsultarDeterminacionImpuestoForma2a2DTO> consultarDeterminacionImpuestoforma2a2() throws FileNotFoundException,
                                                                                                             ClassNotFoundException,
                                                                                                             SQLException {


        ConsultarDeterminacionImpuestoForma2a2DTO determinacionImpuestoForma2a2DTO =
            new ConsultarDeterminacionImpuestoForma2a2DTO();

        determinacionImpuestoForma2a2DTO.setRfceeog1(ConstantesDyC.CME590904RE3);
        determinacionImpuestoForma2a2DTO.setCDecCplearv1(ConstantesDyCNumerico.VALOR_34);
        determinacionImpuestoForma2a2DTO.setIapidne1(ConstantesDyCNumerico.VALOR_96);
        determinacionImpuestoForma2a2DTO.setIapfdne1(ConstantesDyCNumerico.VALOR_96);


        List<ConsultarDeterminacionImpuestoForma2a2DTO> informacionRecuperada =
            consultardeterminacionesDAO.consultarDeterminacionImpuestoforma2a2(determinacionImpuestoForma2a2DTO);

        return informacionRecuperada;
    }

    @Override
    public List<ConsultarDeterminacionImpuestoForma32DTO> consultarDeterminacionImpuestoForma32() throws FileNotFoundException,
                                                                                                          ClassNotFoundException,
                                                                                                          SQLException {


        ConsultarDeterminacionImpuestoForma32DTO determinacionImpuestoForma32DTO =
            new ConsultarDeterminacionImpuestoForma32DTO();

        determinacionImpuestoForma32DTO.setRfceeog1("PPR910701LEA");
        determinacionImpuestoForma32DTO.setCDecCplearv1(ConstantesDyCNumerico.VALOR_34);
        determinacionImpuestoForma32DTO.setIapidne1(ConstantesDyCNumerico.VALOR_2000);
        determinacionImpuestoForma32DTO.setIapfdne1(ConstantesDyCNumerico.VALOR_2000);


        List<ConsultarDeterminacionImpuestoForma32DTO> informacionRecuperada =
            consultardeterminacionesDAO.consultarDeterminacionImpuestoForma32(determinacionImpuestoForma32DTO);

        return informacionRecuperada;
    }

    @Override
    public List<ConsultarDeterminacionImpuestoForma1EDTO> consultarDeterminacionImpuestoForma1EDTO() throws FileNotFoundException,
                                                                                                            ClassNotFoundException,
                                                                                                            SQLException {


        ConsultarDeterminacionImpuestoForma1EDTO consultarDeterminacionImpuestoForma1EDTO =
            new ConsultarDeterminacionImpuestoForma1EDTO();

        consultarDeterminacionImpuestoForma1EDTO.setRfceeog1("SHE190630V37");
        consultarDeterminacionImpuestoForma1EDTO.setCDecCplearv1(ConstantesDyCNumerico.VALOR_1);
        consultarDeterminacionImpuestoForma1EDTO.setIapidne1(ConstantesDyCNumerico.VALOR_2001);
        consultarDeterminacionImpuestoForma1EDTO.setIapfdne1(ConstantesDyCNumerico.VALOR_2001);


        List<ConsultarDeterminacionImpuestoForma1EDTO> informacionRecuperada =
            consultardeterminacionesDAO.consultarDeterminacionImpuestoForma1E(consultarDeterminacionImpuestoForma1EDTO);

        return informacionRecuperada;
    }

    @Override
    public List<ConsultarDeclaracionISRSociedadControladora1DTO> consultarDeclaracionISRSociedadControladora1() throws FileNotFoundException,
                                                                                                                       ClassNotFoundException,
                                                                                                                       SQLException {


        ConsultarDeclaracionISRSociedadControladora1DTO consultarDeclaracionISRSociedadControladora1DTO =
            new ConsultarDeclaracionISRSociedadControladora1DTO();

        consultarDeclaracionISRSociedadControladora1DTO.setRfceeog1(ConstantesDyC.CME590904RE3);
        consultarDeclaracionISRSociedadControladora1DTO.setCDecCplearv1(ConstantesDyCNumerico.VALOR_34);
        consultarDeclaracionISRSociedadControladora1DTO.setIapidne1(ConstantesDyCNumerico.VALOR_96);
        consultarDeclaracionISRSociedadControladora1DTO.setIapfdne1(ConstantesDyCNumerico.VALOR_96);


        List<ConsultarDeclaracionISRSociedadControladora1DTO> informacionRecuperada =
            consultarDeclaracionISRSociedadControladora1DAO.consultarDeclaracionISRSociedadControladora1(consultarDeclaracionISRSociedadControladora1DTO);

        return informacionRecuperada;
    }

    @Override
    public List<ConsultarDeclaracionISRSociedadControladora2DTO> consultarDeclaracionISRSociedadControladora2() throws FileNotFoundException,
                                                                                                                       ClassNotFoundException,
                                                                                                                       SQLException {


        ConsultarDeclaracionISRSociedadControladora2DTO consultarDeclaracionISRSociedadControladora2DTO =
            new ConsultarDeclaracionISRSociedadControladora2DTO();

        consultarDeclaracionISRSociedadControladora2DTO.setRfceeog1(ConstantesDyC.CME590904RE3);
        consultarDeclaracionISRSociedadControladora2DTO.setCDecCplearv1(ConstantesDyCNumerico.VALOR_34);
        consultarDeclaracionISRSociedadControladora2DTO.setIapidne1(ConstantesDyCNumerico.VALOR_2006);
        consultarDeclaracionISRSociedadControladora2DTO.setIapfdne1(ConstantesDyCNumerico.VALOR_2006);


        List<ConsultarDeclaracionISRSociedadControladora2DTO> informacionRecuperada =
            consultarDeclaracionISRSociedadControladora1DAO.consultarDeclaracionISRSociedadControladora2(consultarDeclaracionISRSociedadControladora2DTO);

        return informacionRecuperada;
    }

    @Override
    public List<ConsultarDeclaracionISRsociedadIntegradoraDTO> consultarDeclaracionISRsociedadIntegradora() throws FileNotFoundException,
                                                                                                                   ClassNotFoundException,
                                                                                                                   SQLException {


        ConsultarDeclaracionISRsociedadIntegradoraDTO consultarDeclaracionISRsociedadIntegradoraDTO =
            new ConsultarDeclaracionISRsociedadIntegradoraDTO();

        consultarDeclaracionISRsociedadIntegradoraDTO.setRfceeog1("PPR910701LEA");
        consultarDeclaracionISRsociedadIntegradoraDTO.setCDecCplearv1(ConstantesDyCNumerico.VALOR_34);
        consultarDeclaracionISRsociedadIntegradoraDTO.setIapidne1(ConstantesDyCNumerico.VALOR_2000);
        consultarDeclaracionISRsociedadIntegradoraDTO.setIapfdne1(ConstantesDyCNumerico.VALOR_2000);


        List<ConsultarDeclaracionISRsociedadIntegradoraDTO> informacionRecuperada =
            consultarDeclaracionISRsociedadIntegradoraDAO.consultarDeclaracionISRsociedadIntegradora(consultarDeclaracionISRsociedadIntegradoraDTO);

        return informacionRecuperada;
    }

    @Override
    public List<ConsultarDeclaracionISRMoralDTO> consultarDeclaracionISRMoral() throws FileNotFoundException,
                                                                                       ClassNotFoundException,
                                                                                       SQLException {


        ConsultarDeclaracionISRMoralDTO consultarDeclaracionISRMoralDTO = new ConsultarDeclaracionISRMoralDTO();

        consultarDeclaracionISRMoralDTO.setRfceeog1(ConstantesDyC.AAC810320RQ1);
        consultarDeclaracionISRMoralDTO.setRfceeog2(ConstantesDyC.AAC810320RQ1);
        consultarDeclaracionISRMoralDTO.setRfceeog3(ConstantesDyC.AAC810320RQ1);
        consultarDeclaracionISRMoralDTO.setCDecCplearv1(ConstantesDyCNumerico.VALOR_34);
        consultarDeclaracionISRMoralDTO.setIapidne1(ConstantesDyCNumerico.VALOR_2002);
        consultarDeclaracionISRMoralDTO.setIapfdne1(ConstantesDyCNumerico.VALOR_2002);


        List<ConsultarDeclaracionISRMoralDTO> informacionRecuperada =
            consultarDeclaracionISRMoralDAO.consultarDeclaracionISRMoral(consultarDeclaracionISRMoralDTO);

        return informacionRecuperada;
    }

    @Override
    public List<ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO> consultarDeclaracionIAPersonaFisicaDatosDeclaraciones01() throws FileNotFoundException,
                                                                                                                                             ClassNotFoundException,
                                                                                                                                             SQLException {


        ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO consultarDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO =
            new ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO();

        consultarDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO.setRfceeog1(ConstantesConsultarCompensaciones.RFCEEOG1);
        consultarDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO.setCNPeriodo(ConstantesDyCNumerico.VALOR_35);
        consultarDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO.setNEjercicio(ConstantesDyCNumerico.VALOR_2006);

        List<ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO> informacionRecuperada =
            consultarDeclaracionIAPersonaFisicaDAO.consultarDeclaracionIAPersonaFisicaDatosDeclaraciones01(consultarDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO);

        return informacionRecuperada;
    }

    @Override
    public List<ConsultarDeclaracionIEPS4DTO> consultarDeclaracionIEPS4() throws FileNotFoundException
                                                                                {


        ConsultarDeclaracionIEPS4DTO consultarDeclaracionIEPS4DTO = new ConsultarDeclaracionIEPS4DTO();

        consultarDeclaracionIEPS4DTO.setRfceeog1("SHE190630V37");
        consultarDeclaracionIEPS4DTO.setIapidne1(ConstantesDyCNumerico.VALOR_2001);
        consultarDeclaracionIEPS4DTO.setIapfdne1(ConstantesDyCNumerico.VALOR_2001);
        consultarDeclaracionIEPS4DTO.setCDecCplearv1(ConstantesDyCNumerico.VALOR_1);

        List<ConsultarDeclaracionIEPS4DTO> informacionRecuperada =
            consultarDeclaracionIEPS4DAO.consultarDeclaracionIEPS4(consultarDeclaracionIEPS4DTO);

        return informacionRecuperada;
    }
}
