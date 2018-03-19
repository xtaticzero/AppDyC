package mx.gob.sat.siat.dyc.servicio.service;

import java.io.FileNotFoundException;

import java.sql.SQLException;

import java.util.List;

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


public interface ConsultarCompensacionesFechasService {


    List<ConsultarCompensacionesFechasDTO> consultarCompensacionesFechas() throws FileNotFoundException,
                                                                                  ClassNotFoundException, SQLException;

    List<ConsultarCompensacionesInformacionDTO> consultarCompensacionesInformacion() throws FileNotFoundException,
                                                                                            ClassNotFoundException,
                                                                                            SQLException;

    List<ConsultarDetalleCreditosFiscalesDTO> consultarDetalleCreditosFiscales() throws FileNotFoundException,
                                                                                        ClassNotFoundException,
                                                                                        SQLException;

    List<ConsultarContribuyenteAltamenteExportadorDTO> consultarContribuyenteAltamenteExportadorDTO() throws FileNotFoundException,
                                                                                                             ClassNotFoundException,
                                                                                                             SQLException;

    List<ConsultarDevolucionesDTO> consultarDevoluciones() throws FileNotFoundException, ClassNotFoundException,
                                                                  SQLException;

    List<ConsultarDictamenesDTO> consultarDictamenes() throws FileNotFoundException, ClassNotFoundException,
                                                              SQLException;

    List<ConsultarDeterminacionDeImpuestosCdiDpdifDTO> consultarDeterminacionDeImptos() throws FileNotFoundException,
                                                                                               ClassNotFoundException,
                                                                                               SQLException;

    List<ConsultarDeterminacionImpuestoCdiDpdifAnioDTO> consultarDeterminacionImpuestoCdiDpdifAnio() throws FileNotFoundException,
                                                                                                            ClassNotFoundException,
                                                                                                            SQLException;

    List<ConsultarDeterminacionImpuestoCdiImpuestosDTO> consultarDeterminacionImpuestoCdiImpuestos() throws FileNotFoundException,
                                                                                                            ClassNotFoundException,
                                                                                                            SQLException;

    List<ConsultarDeterminacionImpuestoForma13DTO> consultarDeterminacionImpuestoForma13() throws FileNotFoundException,
                                                                                                  ClassNotFoundException,
                                                                                                  SQLException;

    List<ConsultarDeterminacionImpuestoForma13ADTO> consultarDeterminacionImpuestoForma13A() throws FileNotFoundException,
                                                                                                    ClassNotFoundException,
                                                                                                    SQLException;

    List<ConsultarDeterminacionImpuestoForma2DTO> consultarDeterminacionImpuestoForma2() throws FileNotFoundException,
                                                                                                ClassNotFoundException,
                                                                                                SQLException;

    List<ConsultarDeterminacionImpuestoForma2aDTO> consultarDeterminacionImpuestoForma2a() throws FileNotFoundException,
                                                                                                  ClassNotFoundException,
                                                                                                  SQLException;

    List<ConsultarDeterminacionImpuestosForma3DTO> consultarDeterminacionImpuestosForma3() throws FileNotFoundException,
                                                                                                  ClassNotFoundException,
                                                                                                  SQLException;

    List<ConsultarDeterminacionImpuestoForma132DTO> consultarDeterminacionImpuestoForma132() throws FileNotFoundException,
                                                                                                      ClassNotFoundException,
                                                                                                      SQLException;

    List<ConsultarDeterminacionImpuestoForma22DTO> consultarDeterminacionImpuestoForma22() throws FileNotFoundException,
                                                                                                    ClassNotFoundException,
                                                                                                    SQLException;

    List<ConsultarDeterminacionImpuestoForma2a2DTO> consultarDeterminacionImpuestoforma2a2() throws FileNotFoundException,
                                                                                                        ClassNotFoundException,
                                                                                                        SQLException;

    List<ConsultarDeterminacionImpuestoForma32DTO> consultarDeterminacionImpuestoForma32() throws FileNotFoundException,
                                                                                                    ClassNotFoundException,
                                                                                                    SQLException;

    List<ConsultarDeterminacionImpuestoForma1EDTO> consultarDeterminacionImpuestoForma1EDTO() throws FileNotFoundException,
                                                                                                     ClassNotFoundException,
                                                                                                     SQLException;

    List<ConsultarDeclaracionISRSociedadControladora1DTO> consultarDeclaracionISRSociedadControladora1() throws FileNotFoundException,
                                                                                                                ClassNotFoundException,
                                                                                                                SQLException;

    List<ConsultarDeclaracionISRSociedadControladora2DTO> consultarDeclaracionISRSociedadControladora2() throws FileNotFoundException,
                                                                                                                ClassNotFoundException,
                                                                                                                SQLException;

    List<ConsultarDeclaracionISRsociedadIntegradoraDTO> consultarDeclaracionISRsociedadIntegradora() throws FileNotFoundException,
                                                                                                            ClassNotFoundException,
                                                                                                            SQLException;

    List<ConsultarDeclaracionISRMoralDTO> consultarDeclaracionISRMoral() throws FileNotFoundException,
                                                                                ClassNotFoundException, SQLException;

    List<ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO> consultarDeclaracionIAPersonaFisicaDatosDeclaraciones01() throws FileNotFoundException,
                                                                                                                                      ClassNotFoundException,
                                                                                                                                      SQLException;

    List<ConsultarDeclaracionIEPS4DTO> consultarDeclaracionIEPS4() throws FileNotFoundException;
}
