/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.util.exportador.informe.determinaciondeimpuestos;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.sat.siat.dyc.generico.util.exportador.informe.Marshal;
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


/**
 *
 * @author Alfredo Ramirez
 * @since 31/07/2013
 *
 */
@XmlRootElement
public class ConsultarDeterminacionDeImpuestosXML extends Marshal implements Serializable {

    @SuppressWarnings("compatibility:7729897824240332048")
    private static final long serialVersionUID = 1L;

    private List<ConsultarDeterminacionDeImpuestosCdiDpdifDTO> consultarDeterminacionDeImpuestosCdiDpdifDto =
        new ArrayList<ConsultarDeterminacionDeImpuestosCdiDpdifDTO>();

    private List<ConsultarDeterminacionImpuestoCdiDpdifAnioDTO> consultarDeterminacionDeImpuestosCdiDpdifAnioDto =
        new ArrayList<ConsultarDeterminacionImpuestoCdiDpdifAnioDTO>();

    private List<ConsultarDeterminacionImpuestoCdiImpuestosDTO> consultarDeterminacionImpuestoCdiImpuestosDto =
        new ArrayList<ConsultarDeterminacionImpuestoCdiImpuestosDTO>();

    private List<ConsultarDeterminacionImpuestoForma13DTO> consultarDeterminacionImpuestoForma13Dto =
        new ArrayList<ConsultarDeterminacionImpuestoForma13DTO>();

    private List<ConsultarDeterminacionImpuestoForma13ADTO> consultarDeterminacionImpuestoForma13ADto =
        new ArrayList<ConsultarDeterminacionImpuestoForma13ADTO>();

    private List<ConsultarDeterminacionImpuestoDidDTO> consultarDeterminacionImpuestoDidDto =
        new ArrayList<ConsultarDeterminacionImpuestoDidDTO>();

    private List<ConsultarDeterminacionImpuestoForma2DTO> consultarDeterminacionImpuestoForma2Dto =
        new ArrayList<ConsultarDeterminacionImpuestoForma2DTO>();

    private List<ConsultarDeterminacionImpuestoForma2aDTO> consultarDeterminacionImpuestoForma2aDto =
        new ArrayList<ConsultarDeterminacionImpuestoForma2aDTO>();

    private List<ConsultarDeterminacionImpuestosForma3DTO> consultarDeterminacionImpuestosForma3Dto =
        new ArrayList<ConsultarDeterminacionImpuestosForma3DTO>();

    private List<ConsultarDeterminacionImpuestoForma132DTO> consultarDeterminacionImpuestoForma132Dto =
        new ArrayList<ConsultarDeterminacionImpuestoForma132DTO>();

    private List<ConsultarDeterminacionImpuestosDID2DTO> consultarDeterminacionImpuestosDID2Dto =
        new ArrayList<ConsultarDeterminacionImpuestosDID2DTO>();

    private List<ConsultarDeterminacionImpuestoForma22DTO> consultarDeterminacionImpuestoForma22Dto =
        new ArrayList<ConsultarDeterminacionImpuestoForma22DTO>();

    private List<ConsultarDeterminacionImpuestoForma2a2DTO> consultarDeterminacionImpuestoForma2a2Dto =
        new ArrayList<ConsultarDeterminacionImpuestoForma2a2DTO>();

    private List<ConsultarDeterminacionImpuestoForma32DTO> consultarDeterminacionImpuestoForma32Dto =
        new ArrayList<ConsultarDeterminacionImpuestoForma32DTO>();

    private List<ConsultarDeterminacionImpuestoDID3DTO> consultarDeterminacionImpuestoDID3Dto =
        new ArrayList<ConsultarDeterminacionImpuestoDID3DTO>();

    private List<ConsultarDeterminacionImpuestoForma1EDTO> consultarDetalleDeterminacionImpuestoForma1EDto =
        new ArrayList<ConsultarDeterminacionImpuestoForma1EDTO>();

    public void setConsultarDeterminacionDeImpuestosCdiDpdifDto(List<ConsultarDeterminacionDeImpuestosCdiDpdifDTO> consultarDeterminacionDeImpuestosCdiDpdifDto) {
        this.consultarDeterminacionDeImpuestosCdiDpdifDto = consultarDeterminacionDeImpuestosCdiDpdifDto;
    }

    @XmlElement(name = "consultarDeterminacionDeImpuestosCdiDpdif")
    public List<ConsultarDeterminacionDeImpuestosCdiDpdifDTO> getConsultarDeterminacionDeImpuestosCdiDpdifDto() {
        return consultarDeterminacionDeImpuestosCdiDpdifDto;
    }

    public void setConsultarDeterminacionDeImpuestosCdiDpdifAnioDto(List<ConsultarDeterminacionImpuestoCdiDpdifAnioDTO> consultarDeterminacionDeImpuestosCdiDpdifAnioDto) {
        this.consultarDeterminacionDeImpuestosCdiDpdifAnioDto = consultarDeterminacionDeImpuestosCdiDpdifAnioDto;
    }

    @XmlElement(name = "consultarDeterminacionDeImpuestosCdiDpdifAnio")
    public List<ConsultarDeterminacionImpuestoCdiDpdifAnioDTO> getConsultarDeterminacionDeImpuestosCdiDpdifAnioDto() {
        return consultarDeterminacionDeImpuestosCdiDpdifAnioDto;
    }

    public void setConsultarDeterminacionImpuestoCdiImpuestosDto(List<ConsultarDeterminacionImpuestoCdiImpuestosDTO> consultarDeterminacionImpuestoCdiImpuestosDto) {
        this.consultarDeterminacionImpuestoCdiImpuestosDto = consultarDeterminacionImpuestoCdiImpuestosDto;
    }

    @XmlElement(name = "consultarDeterminacionImpuestoCdiImpuestos")
    public List<ConsultarDeterminacionImpuestoCdiImpuestosDTO> getConsultarDeterminacionImpuestoCdiImpuestosDto() {
        return consultarDeterminacionImpuestoCdiImpuestosDto;
    }

    public void setConsultarDeterminacionImpuestoForma13Dto(List<ConsultarDeterminacionImpuestoForma13DTO> consultarDeterminacionImpuestoForma13Dto) {
        this.consultarDeterminacionImpuestoForma13Dto = consultarDeterminacionImpuestoForma13Dto;
    }

    @XmlElement(name = "consultarDeterminacionImpuestoForma13")
    public List<ConsultarDeterminacionImpuestoForma13DTO> getConsultarDeterminacionImpuestoForma13Dto() {
        return consultarDeterminacionImpuestoForma13Dto;
    }

    public void setConsultarDeterminacionImpuestoForma13ADto(List<ConsultarDeterminacionImpuestoForma13ADTO> consultarDeterminacionImpuestoForma13ADto) {
        this.consultarDeterminacionImpuestoForma13ADto = consultarDeterminacionImpuestoForma13ADto;
    }

    @XmlElement(name = "consultarDeterminacionImpuestoForma13A")
    public List<ConsultarDeterminacionImpuestoForma13ADTO> getConsultarDeterminacionImpuestoForma13ADto() {
        return consultarDeterminacionImpuestoForma13ADto;
    }

    public void setConsultarDeterminacionImpuestoDidDto(List<ConsultarDeterminacionImpuestoDidDTO> consultarDeterminacionImpuestoDidDto) {
        this.consultarDeterminacionImpuestoDidDto = consultarDeterminacionImpuestoDidDto;
    }

    @XmlElement(name = "consultarDeterminacionImpuestoDid")
    public List<ConsultarDeterminacionImpuestoDidDTO> getConsultarDeterminacionImpuestoDidDto() {
        return consultarDeterminacionImpuestoDidDto;
    }

    public void setConsultarDeterminacionImpuestoForma2Dto(List<ConsultarDeterminacionImpuestoForma2DTO> consultarDeterminacionImpuestoForma2Dto) {
        this.consultarDeterminacionImpuestoForma2Dto = consultarDeterminacionImpuestoForma2Dto;
    }

    @XmlElement(name = "consultarDeterminacionImpuestoForma2")
    public List<ConsultarDeterminacionImpuestoForma2DTO> getConsultarDeterminacionImpuestoForma2Dto() {
        return consultarDeterminacionImpuestoForma2Dto;
    }

    public void setConsultarDeterminacionImpuestoForma2aDto(List<ConsultarDeterminacionImpuestoForma2aDTO> consultarDeterminacionImpuestoForma2aDto) {
        this.consultarDeterminacionImpuestoForma2aDto = consultarDeterminacionImpuestoForma2aDto;
    }

    @XmlElement(name = "consultarDeterminacionImpuestoForma2a")
    public List<ConsultarDeterminacionImpuestoForma2aDTO> getConsultarDeterminacionImpuestoForma2aDto() {
        return consultarDeterminacionImpuestoForma2aDto;
    }

    public void setConsultarDeterminacionImpuestosForma3Dto(List<ConsultarDeterminacionImpuestosForma3DTO> consultarDeterminacionImpuestosForma3Dto) {
        this.consultarDeterminacionImpuestosForma3Dto = consultarDeterminacionImpuestosForma3Dto;
    }

    @XmlElement(name = "consultarDeterminacionImpuestosForma3")
    public List<ConsultarDeterminacionImpuestosForma3DTO> getConsultarDeterminacionImpuestosForma3Dto() {
        return consultarDeterminacionImpuestosForma3Dto;
    }

    public void setConsultarDeterminacionImpuestoForma132Dto(List<ConsultarDeterminacionImpuestoForma132DTO> consultarDeterminacionImpuestoForma132Dto) {
        this.consultarDeterminacionImpuestoForma132Dto = consultarDeterminacionImpuestoForma132Dto;
    }

    @XmlElement(name = "consultarDeterminacionImpuestoForma132")
    public List<ConsultarDeterminacionImpuestoForma132DTO> getConsultarDeterminacionImpuestoForma132Dto() {
        return consultarDeterminacionImpuestoForma132Dto;
    }

    public void setConsultarDeterminacionImpuestosDID2Dto(List<ConsultarDeterminacionImpuestosDID2DTO> consultarDeterminacionImpuestosDID2Dto) {
        this.consultarDeterminacionImpuestosDID2Dto = consultarDeterminacionImpuestosDID2Dto;
    }

    @XmlElement(name = "consultarDeterminacionImpuestosDID2")
    public List<ConsultarDeterminacionImpuestosDID2DTO> getConsultarDeterminacionImpuestosDID2Dto() {
        return consultarDeterminacionImpuestosDID2Dto;
    }

    public void setConsultarDeterminacionImpuestoForma22Dto(List<ConsultarDeterminacionImpuestoForma22DTO> consultarDeterminacionImpuestoForma22Dto) {
        this.consultarDeterminacionImpuestoForma22Dto = consultarDeterminacionImpuestoForma22Dto;
    }

    @XmlElement(name = "consultarDeterminacionImpuestoForma22")
    public List<ConsultarDeterminacionImpuestoForma22DTO> getConsultarDeterminacionImpuestoForma22Dto() {
        return consultarDeterminacionImpuestoForma22Dto;
    }

    public void setConsultarDeterminacionImpuestoForma2a2Dto(List<ConsultarDeterminacionImpuestoForma2a2DTO> consultarDeterminacionImpuestoForma2a2Dto) {
        this.consultarDeterminacionImpuestoForma2a2Dto = consultarDeterminacionImpuestoForma2a2Dto;
    }

    @XmlElement(name = "consultarDeterminacionImpuestoforma2a2")
    public List<ConsultarDeterminacionImpuestoForma2a2DTO> getConsultarDeterminacionImpuestoForma2a2Dto() {
        return consultarDeterminacionImpuestoForma2a2Dto;
    }

    public void setConsultarDeterminacionImpuestoForma32Dto(List<ConsultarDeterminacionImpuestoForma32DTO> consultarDeterminacionImpuestoForma32Dto) {
        this.consultarDeterminacionImpuestoForma32Dto = consultarDeterminacionImpuestoForma32Dto;
    }

    @XmlElement(name = "consultarDeterminacionImpuestoForma32")
    public List<ConsultarDeterminacionImpuestoForma32DTO> getConsultarDeterminacionImpuestoForma32Dto() {
        return consultarDeterminacionImpuestoForma32Dto;
    }

    public void setConsultarDeterminacionImpuestoDID3Dto(List<ConsultarDeterminacionImpuestoDID3DTO> consultarDeterminacionImpuestoDID3Dto) {
        this.consultarDeterminacionImpuestoDID3Dto = consultarDeterminacionImpuestoDID3Dto;
    }

    @XmlElement(name = "consultarDeterminacionImpuestoDID3")
    public List<ConsultarDeterminacionImpuestoDID3DTO> getConsultarDeterminacionImpuestoDID3Dto() {
        return consultarDeterminacionImpuestoDID3Dto;
    }

    public void setConsultarDetalleDeterminacionImpuestoForma1EDto(List<ConsultarDeterminacionImpuestoForma1EDTO> consultarDetalleDeterminacionImpuestoForma1EDto) {
        this.consultarDetalleDeterminacionImpuestoForma1EDto = consultarDetalleDeterminacionImpuestoForma1EDto;
    }

    @XmlElement(name = "consultarDetalleDeterminacionImpuestoForma1E")
    public List<ConsultarDeterminacionImpuestoForma1EDTO> getConsultarDetalleDeterminacionImpuestoForma1EDto() {
        return consultarDetalleDeterminacionImpuestoForma1EDto;
    }

}
