package mx.gob.sat.siat.dyc.gestionsol.dao.aprobardocumento.impl.mapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.ResumenDevolucionDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author Ericka Janth Ibarra Ponce
 * @date 02/2014
 *
 * */

public class ResumenDevolucionMapper implements RowMapper<ResumenDevolucionDTO> {
    @Override
    public ResumenDevolucionDTO mapRow(ResultSet resultSet, int i) throws SQLException{
        
        ResumenDevolucionDTO resumenDevolucionSolDTO = new ResumenDevolucionDTO();
        
        resumenDevolucionSolDTO.setNumControl(           resultSet.getString(     "NUMCONTROL" ) );
        resumenDevolucionSolDTO.setRfcContribuyente(     resultSet.getString(     "RFC" ) );
        resumenDevolucionSolDTO.setTipoPersona(          resultSet.getString(     "TIPOPERSONA" ) );
        resumenDevolucionSolDTO.setNombre(               resultSet.getString(     "NOMBRE" ) +" "+ resultSet.getString( "APPATERNO" ) +" "+ resultSet.getString( "APMATERNO" ) );
        resumenDevolucionSolDTO.setRazonSocial(          resultSet.getString(     "RAZONSOCIAL" ) );
        resumenDevolucionSolDTO.setDescTipoTramite(      resultSet.getString(     "TIPOTRAMITE" ) );
        resumenDevolucionSolDTO.setTipoRequerimiento(    resultSet.getString(     "TIPOREQUERIMIENTO" ) );
        resumenDevolucionSolDTO.setTipoResolucion(       resultSet.getString(     "TIPORESOLUCION" ) );
        resumenDevolucionSolDTO.setImporteSolicitado(    resultSet.getBigDecimal( "IMPORTESOLICITADO" ) );
        resumenDevolucionSolDTO.setImporteAutorizado(    resultSet.getBigDecimal( "IMPAUTORIZADO" ) );
        resumenDevolucionSolDTO.setImporteCompensado(    resultSet.getBigDecimal( "IMPCOMPENSADO" ) );
        resumenDevolucionSolDTO.setImporteNeto(          resultSet.getBigDecimal( "IMPORTENETO" ) );
        resumenDevolucionSolDTO.setSaldoNegado(          resultSet.getBigDecimal( "IMPNEGADO" ) );
        resumenDevolucionSolDTO.setDescTipoTramiteICEP(  resultSet.getString(     "ICEPIMPORTECOMP1" ) );
        resumenDevolucionSolDTO.setPeriodoCompensacion(  resultSet.getInt(        "ICEPIMPORTECOMP2" ) );
        resumenDevolucionSolDTO.setImporteCompensacion(  resultSet.getBigDecimal( "ICEPIMPORTECOMP3" ) );
        resumenDevolucionSolDTO.setDescOrigenSaldo(      resultSet.getString(     "ICEPIMPORTECOMP" ) );
        resumenDevolucionSolDTO.setFechaInicioOrigen(    resultSet.getDate(       "ICEPIMPORTECOM" ) );
        resumenDevolucionSolDTO.setIdTipoServicio(       resultSet.getInt(        "IDTIPOSERVICIO" ) );
        if (esColumnaValida(resultSet, "IDESTADODOC")) {
           resumenDevolucionSolDTO.setIdEstadoDoc(          resultSet.getInt(        "IDESTADODOC" ) );   
        }
        resumenDevolucionSolDTO.setIdEstadoSol(          resultSet.getInt(        "IDESTADOSOLICITUD" ) );
        resumenDevolucionSolDTO.setConcepto(             resultSet.getString(     "CONCEPTO" ) );
        resumenDevolucionSolDTO.setPeriodo(              resultSet.getString(     "PERIODO" ) );
        resumenDevolucionSolDTO.setImpuesto(             resultSet.getString(     "IMPUESTO" ) );
        resumenDevolucionSolDTO.setIdTipoTramite(        resultSet.getString(     "IDTIPOTRAMITE" ) );
        resumenDevolucionSolDTO.setResolucionAutomatica( resultSet.getInt(        "RESOLAUTOMATICA" ) );
        resumenDevolucionSolDTO.setSaldoAplicar(         resultSet.getBigDecimal( "SALDOAPLICAR" ) );
        resumenDevolucionSolDTO.setCepOrigen(            resultSet.getString(     "CEPORIGEN" ) );
        resumenDevolucionSolDTO.setCepDestino(           resultSet.getString(     "CEPDESTINO" ) );
        resumenDevolucionSolDTO.setIdTipoResol(          resultSet.getInt(        "IDTIPORESOL" ) );

        String numeroEmpleadoDictaminador = resultSet.getString( "NUMEMPLEADODICT" );
        String nombreDictaminador         = resultSet.getString( "NOMBREDIC" );
        String aPaternoDictaminador       = resultSet.getString( "APPATERNODIC" );
        String aMaternoDictaminador       = resultSet.getString( "APMATERNODIC" );

        numeroEmpleadoDictaminador = numeroEmpleadoDictaminador != null ? numeroEmpleadoDictaminador : "";
        nombreDictaminador         = nombreDictaminador != null ? nombreDictaminador : "";
        aPaternoDictaminador       = aPaternoDictaminador != null ? aPaternoDictaminador : "";
        aMaternoDictaminador       = aMaternoDictaminador != null ? aMaternoDictaminador : "";
        
        String nombreCompletoDictaminador = String.format( 
            "%s %s %s" 
            , nombreDictaminador
            , aPaternoDictaminador
            , aMaternoDictaminador
        );
        
        nombreCompletoDictaminador = nombreCompletoDictaminador.trim();
        nombreCompletoDictaminador = nombreCompletoDictaminador.length() > 0 ? nombreCompletoDictaminador : "";
        
        resumenDevolucionSolDTO.setNumEmpleadoDict( numeroEmpleadoDictaminador );
        resumenDevolucionSolDTO.setNombreDict( nombreCompletoDictaminador );
        
        return resumenDevolucionSolDTO;
    }
    
        private static boolean esColumnaValida(ResultSet rs, String columnName) {

        try {
            ResultSetMetaData metaData = rs.getMetaData();
            for (int columna = 1; columna <= metaData.getColumnCount(); columna++) {
                if (columnName.equalsIgnoreCase(metaData.getColumnName(columna))) {
                    return Boolean.TRUE;
                }
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }
}
