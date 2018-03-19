/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.siat.dyc.dao.util.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.DyctDevAutoIvaDTO;
import mx.gob.sat.siat.dyc.util.constante.DyctDevAutoIvaConstantes;

import org.springframework.jdbc.core.RowMapper;


/**
 *
 * @author GAER8674
 */
public class DyctDevIvaMapper implements RowMapper<DyctDevAutoIvaDTO>{

    @Override
    public DyctDevAutoIvaDTO mapRow(ResultSet rs, int i) throws SQLException {
        DyctDevAutoIvaDTO dyctDevAutoIvaDTO = new DyctDevAutoIvaDTO();
        
        dyctDevAutoIvaDTO.setNumeroLote(rs.getLong(DyctDevAutoIvaConstantes.NUMERO_LOTE.getColumna()));
        dyctDevAutoIvaDTO.setNombreContribuyente(rs.getString(DyctDevAutoIvaConstantes.NOMBRE_CONTRIBUYENTE.getColumna()));
        dyctDevAutoIvaDTO.setRfc(rs.getString(DyctDevAutoIvaConstantes.RFC.getColumna()));
        dyctDevAutoIvaDTO.setImpuesto(rs.getString(DyctDevAutoIvaConstantes.IMPUESTO.getColumna()));
        dyctDevAutoIvaDTO.setConcepto(rs.getInt(DyctDevAutoIvaConstantes.CONCEPTO.getColumna()));
        dyctDevAutoIvaDTO.setEjercicio(rs.getInt(DyctDevAutoIvaConstantes.EJERCICIO.getColumna()));
        dyctDevAutoIvaDTO.setPeriodo(rs.getInt(DyctDevAutoIvaConstantes.PERIODO.getColumna()));
        dyctDevAutoIvaDTO.setNombreBanco(rs.getString(DyctDevAutoIvaConstantes.NOMBRE_BANCO.getColumna()));
        dyctDevAutoIvaDTO.setNumeroCuentaClabe(rs.getString(DyctDevAutoIvaConstantes.NUMERO_CUENTA_CLABE.getColumna()));
        dyctDevAutoIvaDTO.setTipoDeclaracion(rs.getString(DyctDevAutoIvaConstantes.TIPO_DECLARACION.getColumna()));
        dyctDevAutoIvaDTO.setFechaHoraPresentacion(rs.getTimestamp(DyctDevAutoIvaConstantes.FECHAHORA_PRESENTACION.getColumna()));
        dyctDevAutoIvaDTO.setNumeroOperacion(rs.getLong(DyctDevAutoIvaConstantes.NUMERO_OPERACION.getColumna()));
        dyctDevAutoIvaDTO.setImporteSaldoFavor(rs.getString(DyctDevAutoIvaConstantes.IMPORTE_SALDO_FAVOR.getColumna()));
        dyctDevAutoIvaDTO.setFechaHoraProcesamiento(rs.getTimestamp(DyctDevAutoIvaConstantes.FECHAHORA_PROCESAMIENTO.getColumna()));
        dyctDevAutoIvaDTO.setMarcaResultado(rs.getString(DyctDevAutoIvaConstantes.MARCA_RESULTADO.getColumna()));
        dyctDevAutoIvaDTO.setMotivoRechazo(rs.getString(DyctDevAutoIvaConstantes.MOTIVO_RECHAZO.getColumna()));
        
        return dyctDevAutoIvaDTO;
    }
    
}
