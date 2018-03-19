package mx.gob.sat.siat.dyc.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.apache.log4j.Logger;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;


public final class UtilsDominio {
    private UtilsDominio() {
    }

    private static final int TAMANIO_MAX_ALIAS = 30;

    private static final Logger LOG = Logger.getLogger(UtilsDominio.class.getName());

    public static boolean existeColumna(ResultSet rs, String column) {
        try {
            ResultSetMetaData md = rs.getMetaData();
            int numColumns = md.getColumnCount();
            for (int i = 1; i <= numColumns; i++) {
                if (column.equals(md.getColumnName(i))) {
                    return Boolean.TRUE;
                }
            }
        } catch (SQLException e) {
            LOG.info("error en existeColumna ->" + e.getMessage());
        }

        return false;
    }

    public static String obtenerNombreColumna(String nomColumna, String subfijo, ResultSet rs) {
        String alias = nomColumna.concat(subfijo);
        if (alias.length() > TAMANIO_MAX_ALIAS) {
            alias = alias.substring(0, TAMANIO_MAX_ALIAS);
        }

        if (existeColumna(rs, alias)) {
            return alias;
        } else {
            return nomColumna;
        }

    }

    /*
    PREF.CAMPO1 CAMPO1_BRETABLA, ...
    */

    public static String obtenerAliasColumnas(String nombreTabla, String prefijo, JdbcTemplate jdbcTemplateDYC) {
        String query = " SELECT * FROM " + nombreTabla + " WHERE 0 = 1";
        SqlRowSet rowSet = jdbcTemplateDYC.queryForRowSet(query);
        SqlRowSetMetaData metaData = rowSet.getMetaData();
        String[] nomsColumns = metaData.getColumnNames();
        StringBuilder alias = new StringBuilder(" ");
        for (int i = 0; i < nomsColumns.length; i++) {

            alias.append(prefijo);
            alias.append(".");
            alias.append(nomsColumns[i]);
            alias.append(" ");

            String aliasCampo = nomsColumns[i] + "_" + nombreTabla.substring(ConstantesDyCNumerico.VALOR_5);
            if (aliasCampo.length() > TAMANIO_MAX_ALIAS) {
                aliasCampo = aliasCampo.substring(0, TAMANIO_MAX_ALIAS);
            }

            alias.append(aliasCampo);

            if (i < (nomsColumns.length - 1)) {
                alias.append(", ");
            }
        }
        alias.append(" ");
        return alias.toString();
    }
}
