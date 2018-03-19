package mx.gob.sat.siat.dyc.servicio.web.controller.bean.backing;

import java.io.FileNotFoundException;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import mx.gob.sat.siat.dyc.servicio.dto.altex.ConsultarContribuyenteAltamenteExportadorDTO;
import mx.gob.sat.siat.dyc.servicio.dto.compensaciones.ConsultarCompensacionesFechasDTO;
import mx.gob.sat.siat.dyc.servicio.dto.compensaciones.ConsultarCompensacionesInformacionDTO;
import mx.gob.sat.siat.dyc.servicio.dto.creditosfiscales.ConsultarDetalleCreditosFiscalesDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeclaracionIEPS4DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeclaracionISRMoralDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeclaracionISRSociedadControladora1DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeclaracionISRSociedadControladora2DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeclaracionISRsociedadIntegradoraDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionDeImpuestosCdiDpdifDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoCdiDpdifAnioDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoCdiImpuestosDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoDID3DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoDidDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma132DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma13ADTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma13DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma1EDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma22DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma2DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma2a2DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma2aDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoForma32DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestosDID2DTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestosForma3DTO;
import mx.gob.sat.siat.dyc.servicio.dto.devoluciones.ConsultarDevolucionesDTO;
import mx.gob.sat.siat.dyc.servicio.dto.dictamenes.ConsultarDictamenesDTO;
import mx.gob.sat.siat.dyc.servicio.service.ConsultarCompensacionesFechasService;


@ManagedBean(name = "ConsultarCompensacionesFechasMB")
@RequestScoped
public class ConsultarCompensacionesFechasMB {

    @ManagedProperty("#{ConsultarCompensacionesFechasService}")
    private ConsultarCompensacionesFechasService consultarCompensacionesFechasService;

    private List<ConsultarCompensacionesFechasDTO> consultarCompensacionesFechasAL;
    private List<ConsultarCompensacionesInformacionDTO> consultarCompensacionesInformacionAL;
    private List<ConsultarDetalleCreditosFiscalesDTO> consultarDetalleCreditosFiscalesAL;
    private List<ConsultarContribuyenteAltamenteExportadorDTO> consultarALTEXAL;
    private List<ConsultarDevolucionesDTO> consultarDevolucionesList;
    private List<ConsultarDictamenesDTO> consultarDictamenesList;
    private List<ConsultarDeterminacionDeImpuestosCdiDpdifDTO> consulatDetermibacionImptosCDIDPDIF;
    private List<ConsultarDeterminacionImpuestoCdiDpdifAnioDTO> consultarDeterminacionImpuestoCdiDpdifAnioL;
    private List<ConsultarDeterminacionImpuestoCdiImpuestosDTO> consultarDeterminacionImpuestoCdiImpuestosDTOL;
    private List<ConsultarDeterminacionImpuestoForma13DTO> consultarDeterminacionImpuestoForma13DTOL;
    private List<ConsultarDeterminacionImpuestoForma13ADTO> consultarDeterminacionImpuestoForma13ADTOL;
    private List<ConsultarDeterminacionImpuestoDidDTO> consultarDeterminacionImpuestoDidL;
    private List<ConsultarDeterminacionImpuestoForma2DTO> consultarDeterminacionImpuestoForma2L;
    private List<ConsultarDeterminacionImpuestoForma2aDTO> consultarDeterminacionImpuestoForma2aL;
    private List<ConsultarDeterminacionImpuestosForma3DTO> consultarDeterminacionImpuestosForma3List;
    private List<ConsultarDeterminacionImpuestoForma132DTO> consultarDeterminacionImpuestoForma132List;
    private List<ConsultarDeterminacionImpuestosDID2DTO> consultarDeterminacionImpuestosDID2List;
    private List<ConsultarDeterminacionImpuestoForma22DTO> consultarDeterminacionImpuestoForma22List;
    private List<ConsultarDeterminacionImpuestoForma2a2DTO> consultarDeterminacionImpuestoForma2a2List;
    private List<ConsultarDeterminacionImpuestoForma32DTO> consultarDeterminacionImpuestoForma32List;
    private List<ConsultarDeterminacionImpuestoDID3DTO> consultarDeterminacionImpuestoDID3List;
    private List<ConsultarDeterminacionImpuestoForma1EDTO> consultarDeterminacionImpuestoForma1EList;
    private List<ConsultarDeclaracionISRSociedadControladora1DTO> consultarDeclaracionISRSociedadControladora1List;
    private List<ConsultarDeclaracionISRSociedadControladora2DTO> consultarDeclaracionISRSociedadControladora2List;
    private List<ConsultarDeclaracionISRsociedadIntegradoraDTO> consultarDeclaracionISRsociedadIntegradoraList;
    private List<ConsultarDeclaracionISRMoralDTO> consultarDeclaracionISRMoralDTO;
    private List<ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO> consultarDeclaracionIAPersonaFisicaDatosDeclaraciones01List;
    private List<ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO> consultarDeclaracionIAPersonaFisicaDatosDeclaraciones02List;
    private List<ConsultarDeclaracionIEPS4DTO> consultarDeclaracionIEPS4List;


    @PostConstruct
    public void init() {
    }

    public void consultarCompensacionesFechas() throws FileNotFoundException, ClassNotFoundException, SQLException {
        consultarCompensacionesFechasAL = new ArrayList<ConsultarCompensacionesFechasDTO>();
        setConsultarDeclaracionIEPS4List(consultarCompensacionesFechasService.consultarDeclaracionIEPS4());
    }


    public void setConsultarCompensacionesFechasService(ConsultarCompensacionesFechasService consultarCompensacionesFechasService) {
        this.consultarCompensacionesFechasService = consultarCompensacionesFechasService;
    }

    public ConsultarCompensacionesFechasService getConsultarCompensacionesFechasService() {
        return consultarCompensacionesFechasService;
    }

    public void setConsultarCompensacionesFechasAL(List<ConsultarCompensacionesFechasDTO> consultarCompensacionesFechasAL) {
        this.consultarCompensacionesFechasAL = consultarCompensacionesFechasAL;
    }

    public List<ConsultarCompensacionesFechasDTO> getConsultarCompensacionesFechasAL() {
        return consultarCompensacionesFechasAL;
    }

    public void setConsultarCompensacionesInformacionAL(List<ConsultarCompensacionesInformacionDTO> consultarCompensacionesInformacionAL) {
        this.consultarCompensacionesInformacionAL = consultarCompensacionesInformacionAL;
    }

    public List<ConsultarCompensacionesInformacionDTO> getConsultarCompensacionesInformacionAL() {
        return consultarCompensacionesInformacionAL;
    }

    public void setConsultarDetalleCreditosFiscalesAL(List<ConsultarDetalleCreditosFiscalesDTO> consultarDetalleCreditosFiscalesAL) {
        this.consultarDetalleCreditosFiscalesAL = consultarDetalleCreditosFiscalesAL;
    }

    public List<ConsultarDetalleCreditosFiscalesDTO> getConsultarDetalleCreditosFiscalesAL() {
        return consultarDetalleCreditosFiscalesAL;
    }

    public void setConsultarALTEXAL(List<ConsultarContribuyenteAltamenteExportadorDTO> consultarALTEXAL) {
        this.consultarALTEXAL = consultarALTEXAL;
    }

    public List<ConsultarContribuyenteAltamenteExportadorDTO> getConsultarALTEXAL() {
        return consultarALTEXAL;
    }

    public void setConsultarDevolucionesList(List<ConsultarDevolucionesDTO> consultarDevolucionesList) {
        this.consultarDevolucionesList = consultarDevolucionesList;
    }

    public List<ConsultarDevolucionesDTO> getConsultarDevolucionesList() {
        return consultarDevolucionesList;
    }

    public void setConsultarDictamenesList(List<ConsultarDictamenesDTO> consultarDictamenesList) {
        this.consultarDictamenesList = consultarDictamenesList;
    }

    public List<ConsultarDictamenesDTO> getConsultarDictamenesList() {
        return consultarDictamenesList;
    }

    public void setConsulatDetermibacionImptosCDIDPDIF(List<ConsultarDeterminacionDeImpuestosCdiDpdifDTO> consulatDetermibacionImptosCDIDPDIF) {
        this.consulatDetermibacionImptosCDIDPDIF = consulatDetermibacionImptosCDIDPDIF;
    }

    public List<ConsultarDeterminacionDeImpuestosCdiDpdifDTO> getConsulatDetermibacionImptosCDIDPDIF() {
        return consulatDetermibacionImptosCDIDPDIF;
    }

    public void setConsultarDeterminacionImpuestoCdiDpdifAnioL(List<ConsultarDeterminacionImpuestoCdiDpdifAnioDTO> consultarDeterminacionImpuestoCdiDpdifAnioL) {
        this.consultarDeterminacionImpuestoCdiDpdifAnioL = consultarDeterminacionImpuestoCdiDpdifAnioL;
    }

    public List<ConsultarDeterminacionImpuestoCdiDpdifAnioDTO> getConsultarDeterminacionImpuestoCdiDpdifAnioL() {
        return consultarDeterminacionImpuestoCdiDpdifAnioL;
    }

    public void setConsultarDeterminacionImpuestoCdiImpuestosDTOL(List<ConsultarDeterminacionImpuestoCdiImpuestosDTO> consultarDeterminacionImpuestoCdiImpuestosDTOL) {
        this.consultarDeterminacionImpuestoCdiImpuestosDTOL = consultarDeterminacionImpuestoCdiImpuestosDTOL;
    }

    public List<ConsultarDeterminacionImpuestoCdiImpuestosDTO> getConsultarDeterminacionImpuestoCdiImpuestosDTOL() {
        return consultarDeterminacionImpuestoCdiImpuestosDTOL;
    }

    public void setConsultarDeterminacionImpuestoForma13DTOL(List<ConsultarDeterminacionImpuestoForma13DTO> consultarDeterminacionImpuestoForma13DTOL) {
        this.consultarDeterminacionImpuestoForma13DTOL = consultarDeterminacionImpuestoForma13DTOL;
    }

    public List<ConsultarDeterminacionImpuestoForma13DTO> getConsultarDeterminacionImpuestoForma13DTOL() {
        return consultarDeterminacionImpuestoForma13DTOL;
    }

    public void setConsultarDeterminacionImpuestoForma13ADTOL(List<ConsultarDeterminacionImpuestoForma13ADTO> consultarDeterminacionImpuestoForma13ADTOL) {
        this.consultarDeterminacionImpuestoForma13ADTOL = consultarDeterminacionImpuestoForma13ADTOL;
    }

    public List<ConsultarDeterminacionImpuestoForma13ADTO> getConsultarDeterminacionImpuestoForma13ADTOL() {
        return consultarDeterminacionImpuestoForma13ADTOL;
    }

    public void setConsultarDeterminacionImpuestoDidL(List<ConsultarDeterminacionImpuestoDidDTO> consultarDeterminacionImpuestoDidL) {
        this.consultarDeterminacionImpuestoDidL = consultarDeterminacionImpuestoDidL;
    }

    public List<ConsultarDeterminacionImpuestoDidDTO> getConsultarDeterminacionImpuestoDidL() {
        return consultarDeterminacionImpuestoDidL;
    }

    public void setConsultarDeterminacionImpuestoForma2L(List<ConsultarDeterminacionImpuestoForma2DTO> consultarDeterminacionImpuestoForma2L) {
        this.consultarDeterminacionImpuestoForma2L = consultarDeterminacionImpuestoForma2L;
    }

    public List<ConsultarDeterminacionImpuestoForma2DTO> getConsultarDeterminacionImpuestoForma2L() {
        return consultarDeterminacionImpuestoForma2L;
    }

    public void setConsultarDeterminacionImpuestoForma2aL(List<ConsultarDeterminacionImpuestoForma2aDTO> consultarDeterminacionImpuestoForma2aL) {
        this.consultarDeterminacionImpuestoForma2aL = consultarDeterminacionImpuestoForma2aL;
    }

    public List<ConsultarDeterminacionImpuestoForma2aDTO> getConsultarDeterminacionImpuestoForma2aL() {
        return consultarDeterminacionImpuestoForma2aL;
    }

    public void setConsultarDeterminacionImpuestosForma3List(List<ConsultarDeterminacionImpuestosForma3DTO> consultarDeterminacionImpuestosForma3List) {
        this.consultarDeterminacionImpuestosForma3List = consultarDeterminacionImpuestosForma3List;
    }

    public List<ConsultarDeterminacionImpuestosForma3DTO> getConsultarDeterminacionImpuestosForma3List() {
        return consultarDeterminacionImpuestosForma3List;
    }

    public void setConsultarDeterminacionImpuestoForma132List(List<ConsultarDeterminacionImpuestoForma132DTO> consultarDeterminacionImpuestoForma132List) {
        this.consultarDeterminacionImpuestoForma132List = consultarDeterminacionImpuestoForma132List;
    }

    public List<ConsultarDeterminacionImpuestoForma132DTO> getConsultarDeterminacionImpuestoForma132List() {
        return consultarDeterminacionImpuestoForma132List;
    }

    public void setConsultarDeterminacionImpuestosDID2List(List<ConsultarDeterminacionImpuestosDID2DTO> consultarDeterminacionImpuestosDID2List) {
        this.consultarDeterminacionImpuestosDID2List = consultarDeterminacionImpuestosDID2List;
    }

    public List<ConsultarDeterminacionImpuestosDID2DTO> getConsultarDeterminacionImpuestosDID2List() {
        return consultarDeterminacionImpuestosDID2List;
    }

    public void setConsultarDeterminacionImpuestoForma22List(List<ConsultarDeterminacionImpuestoForma22DTO> consultarDeterminacionImpuestoForma22List) {
        this.consultarDeterminacionImpuestoForma22List = consultarDeterminacionImpuestoForma22List;
    }

    public List<ConsultarDeterminacionImpuestoForma22DTO> getConsultarDeterminacionImpuestoForma22List() {
        return consultarDeterminacionImpuestoForma22List;
    }

    public void setConsultarDeterminacionImpuestoForma2a2List(List<ConsultarDeterminacionImpuestoForma2a2DTO> consultarDeterminacionImpuestoForma2a2List) {
        this.consultarDeterminacionImpuestoForma2a2List = consultarDeterminacionImpuestoForma2a2List;
    }

    public List<ConsultarDeterminacionImpuestoForma2a2DTO> getConsultarDeterminacionImpuestoForma2a2List() {
        return consultarDeterminacionImpuestoForma2a2List;
    }

    public void setConsultarDeterminacionImpuestoForma32List(List<ConsultarDeterminacionImpuestoForma32DTO> consultarDeterminacionImpuestoForma32List) {
        this.consultarDeterminacionImpuestoForma32List = consultarDeterminacionImpuestoForma32List;
    }

    public List<ConsultarDeterminacionImpuestoForma32DTO> getConsultarDeterminacionImpuestoForma32List() {
        return consultarDeterminacionImpuestoForma32List;
    }

    public void setConsultarDeterminacionImpuestoDID3List(List<ConsultarDeterminacionImpuestoDID3DTO> consultarDeterminacionImpuestoDID3List) {
        this.consultarDeterminacionImpuestoDID3List = consultarDeterminacionImpuestoDID3List;
    }

    public List<ConsultarDeterminacionImpuestoDID3DTO> getConsultarDeterminacionImpuestoDID3List() {
        return consultarDeterminacionImpuestoDID3List;
    }

    public void setConsultarDeterminacionImpuestoForma1EList(List<ConsultarDeterminacionImpuestoForma1EDTO> consultarDeterminacionImpuestoForma1EList) {
        this.consultarDeterminacionImpuestoForma1EList = consultarDeterminacionImpuestoForma1EList;
    }

    public List<ConsultarDeterminacionImpuestoForma1EDTO> getConsultarDeterminacionImpuestoForma1EList() {
        return consultarDeterminacionImpuestoForma1EList;
    }

    public void setConsultarDeclaracionISRSociedadControladora1List(List<ConsultarDeclaracionISRSociedadControladora1DTO> consultarDeclaracionISRSociedadControladora1List) {
        this.consultarDeclaracionISRSociedadControladora1List = consultarDeclaracionISRSociedadControladora1List;
    }

    public List<ConsultarDeclaracionISRSociedadControladora1DTO> getConsultarDeclaracionISRSociedadControladora1List() {
        return consultarDeclaracionISRSociedadControladora1List;
    }

    public void setConsultarDeclaracionISRSociedadControladora2List(List<ConsultarDeclaracionISRSociedadControladora2DTO> consultarDeclaracionISRSociedadControladora2List) {
        this.consultarDeclaracionISRSociedadControladora2List = consultarDeclaracionISRSociedadControladora2List;
    }

    public List<ConsultarDeclaracionISRSociedadControladora2DTO> getConsultarDeclaracionISRSociedadControladora2List() {
        return consultarDeclaracionISRSociedadControladora2List;
    }

    public void setConsultarDeclaracionISRsociedadIntegradoraList(List<ConsultarDeclaracionISRsociedadIntegradoraDTO> consultarDeclaracionISRsociedadIntegradoraList) {
        this.consultarDeclaracionISRsociedadIntegradoraList = consultarDeclaracionISRsociedadIntegradoraList;
    }

    public List<ConsultarDeclaracionISRsociedadIntegradoraDTO> getConsultarDeclaracionISRsociedadIntegradoraList() {
        return consultarDeclaracionISRsociedadIntegradoraList;
    }

    public void setConsultarDeclaracionISRMoralDTO(List<ConsultarDeclaracionISRMoralDTO> consultarDeclaracionISRMoralDTO) {
        this.consultarDeclaracionISRMoralDTO = consultarDeclaracionISRMoralDTO;
    }

    public List<ConsultarDeclaracionISRMoralDTO> getConsultarDeclaracionISRMoralDTO() {
        return consultarDeclaracionISRMoralDTO;
    }

    public void setConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones01List(List<ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO> consultarDeclaracionIAPersonaFisicaDatosDeclaraciones01List) {
        this.consultarDeclaracionIAPersonaFisicaDatosDeclaraciones01List =
                consultarDeclaracionIAPersonaFisicaDatosDeclaraciones01List;
    }

    public List<ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones01DTO> getConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones01List() {
        return consultarDeclaracionIAPersonaFisicaDatosDeclaraciones01List;
    }

    public void setConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones02List(List<ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO> consultarDeclaracionIAPersonaFisicaDatosDeclaraciones02List) {
        this.consultarDeclaracionIAPersonaFisicaDatosDeclaraciones02List =
                consultarDeclaracionIAPersonaFisicaDatosDeclaraciones02List;
    }

    public List<ConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones02DTO> getConsultarDeclaracionIAPersonaFisicaDatosDeclaraciones02List() {
        return consultarDeclaracionIAPersonaFisicaDatosDeclaraciones02List;
    }

    public void setConsultarDeclaracionIEPS4List(List<ConsultarDeclaracionIEPS4DTO> consultarDeclaracionIEPS4List) {
        this.consultarDeclaracionIEPS4List = consultarDeclaracionIEPS4List;
    }

    public List<ConsultarDeclaracionIEPS4DTO> getConsultarDeclaracionIEPS4List() {
        return consultarDeclaracionIEPS4List;
    }
}
