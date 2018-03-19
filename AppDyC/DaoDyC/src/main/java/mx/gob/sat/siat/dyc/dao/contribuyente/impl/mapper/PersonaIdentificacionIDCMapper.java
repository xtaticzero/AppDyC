/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.dao.contribuyente.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaIdentificacionDTO;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC2;

import org.springframework.jdbc.core.RowMapper;


/**
 * Clase mapper para el dto de PersonaIdentificacionDTO
 * @author Paola Rivera
 */
public class PersonaIdentificacionIDCMapper implements RowMapper<PersonaIdentificacionDTO> {
    public PersonaIdentificacionIDCMapper() {
        super();
    }

    @Override
    public PersonaIdentificacionDTO mapRow(ResultSet rs, int i) throws SQLException {
        PersonaIdentificacionDTO personaIdentificacion = new PersonaIdentificacionDTO();
        personaIdentificacion.setTipoPersona(rs.getString("T_PERSONA"));
        personaIdentificacion.setCurp(null != rs.getString("CURP") ? rs.getString("CURP") : ConstantesDyC2.NULL);
        personaIdentificacion.setClaveSegmento(rs.getString("C_SEGMENTO"));
        personaIdentificacion.setDenominacion(rs.getString("D_SEGMENTO"));
        personaIdentificacion.setTipoSociedad(rs.getString("NIT"));
        personaIdentificacion.setNombre(null != rs.getString("NOMBRE") ? rs.getString("NOMBRE") : ConstantesDyC2.NULL);
        personaIdentificacion.setApPaterno(null != rs.getString("AP_PATERNO") ? rs.getString("AP_PATERNO") :
                                           ConstantesDyC2.NULL);
        personaIdentificacion.setApMaterno(null != rs.getString("AP_MATERNO") ? rs.getString("AP_MATERNO") :
                                           ConstantesDyC2.NULL);
        personaIdentificacion.setNombreComercial(null != rs.getString("NOM_COMERCIAL") ?
                                                 rs.getString("NOM_COMERCIAL") : ConstantesDyC2.NULL);
        personaIdentificacion.setFNacimiento(rs.getDate("F_NACIMIENTO"));
        personaIdentificacion.setRazonSocial(null != rs.getString("RAZON_SOC") ? rs.getString("RAZON_SOC") :
                                             ConstantesDyC2.NULL);
        personaIdentificacion.setTipoSociedad(null != rs.getString("T_SOCIEDAD") ? rs.getString("T_SOCIEDAD") :
                                              ConstantesDyC2.NULL);
        personaIdentificacion.setFConstitucion(rs.getDate("F_CONSTITUCION"));
        personaIdentificacion.setNacionalidad(null != rs.getString("NACIONALIDAD") ? rs.getString("NACIONALIDAD") :
                                              ConstantesDyC2.NULL);
        personaIdentificacion.setFInicioOperacion(rs.getDate("F_INI_OPERS"));
        personaIdentificacion.setClaveSitContribuyente(rs.getString("C_SIT_CONT"));
        personaIdentificacion.setClaveDetSitCont(rs.getString("C_DET_SIT_CONT"));
        personaIdentificacion.setDescSitContribuyente(rs.getString("D_SIT_CONT"));
        personaIdentificacion.setFechaSitContribuyente(rs.getDate("F_SIT_CONT"));
        personaIdentificacion.setClaveSitDomicilio(rs.getString("C_SIT_DOM"));
        personaIdentificacion.setDescSitDomicilio(rs.getString("D_SIT_DOM"));
        personaIdentificacion.setClaveSitContDom(rs.getString("C_SIT_CONT_DOM"));
        personaIdentificacion.setDescSitContDom(rs.getString("D_SIT_CONT_DOM"));
        personaIdentificacion.setPaisOrigen(rs.getString("PAIS_ORIGEN"));
        personaIdentificacion.setDfTipo(rs.getString("DF_TIPO"));
        personaIdentificacion.setDfFolio(rs.getString("DF_FOLIO"));
        personaIdentificacion.setDfFInicio(rs.getDate("DF_F_INICIO"));
        personaIdentificacion.setDfFFin(rs.getDate("DF_F_FIN"));
        personaIdentificacion.setEmail(rs.getString("EMAIL"));


        return personaIdentificacion;
    }
}

