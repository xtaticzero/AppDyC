package mx.gob.sat.siat.dyc.dao.icep.impl.mapper;

/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/


import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaraIcepDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoDeclaraDTO;
import mx.gob.sat.siat.dyc.util.BuscadorConstantes;

import org.apache.log4j.Logger;

import org.springframework.jdbc.core.RowMapper;


/**
 * Clase Mapper para la tabla [DYCT_DECLARAICEP].
 * @author Huetzin Cruz L.
 * @since Diciembre 10, 2014
 *
 */
public class DyctDeclaraIcepMapper implements RowMapper<DyctDeclaraIcepDTO>
{
    private static final Logger LOG = Logger.getLogger(DyctDeclaraIcepMapper.class);

    private DyctSaldoIcepDTO saldoIcep;
    
    @Override
    public DyctDeclaraIcepDTO mapRow(ResultSet rs, int i) throws SQLException
    {
        DyctSaldoIcepDTO saldoIcepL;
        if(saldoIcep != null){
            saldoIcepL = getSaldoIcep();
        }
        else
        {
            saldoIcepL = new DyctSaldoIcepDTO();
            saldoIcepL.setIdSaldoIcep(rs.getInt("IDSALDOICEP"));
        }

        Integer idTipoDeclaracion = rs.getInt("IDTIPODECLARACION");
        LOG.debug("idTipoDeclaracion ->" + idTipoDeclaracion + "<-");
        DyccTipoDeclaraDTO tipoDeclaracion = BuscadorConstantes.obtenerTipoDeclaracion(idTipoDeclaracion);
        
        DyctDeclaraIcepDTO declaraIcep = new DyctDeclaraIcepDTO();

        declaraIcep.setNumOperacion(rs.getLong("NUMOPERACION"));
        declaraIcep.setFechaPresentacion(rs.getTimestamp("FECHAPRESENTACION"));
        declaraIcep.setDyccTipoDeclaraDTO(tipoDeclaracion);
        declaraIcep.setIdDeclaraIcep(rs.getInt("IDDECLARAICEP"));
        declaraIcep.setDyctSaldoIcepDTO(saldoIcepL);
        declaraIcep.setSaldoAFavor(rs.getBigDecimal("SALDOAFAVOR"));
        declaraIcep.setValidacionDWH(rs.getTimestamp("VALIDACIONDWH"));
        declaraIcep.setOrigenDeclara(BuscadorConstantes.obtenerOrigenDeclaracion(rs.getInt("ORIGENDECLARA")));
        declaraIcep.setFechaRegistro(rs.getTimestamp("FECHAREGISTRO"));
        declaraIcep.setUsrRegistro(rs.getString("USRREGISTRO")); 
        declaraIcep.setNotas(rs.getString("NOTAS"));
        declaraIcep.setFechaFin(rs.getTimestamp("FECHAFIN"));
        
        return declaraIcep;
    }

    public DyctSaldoIcepDTO getSaldoIcep() {
        return saldoIcep;
    }

    public void setSaldoIcep(DyctSaldoIcepDTO saldoIcep) {
        this.saldoIcep = saldoIcep;
    }
}
