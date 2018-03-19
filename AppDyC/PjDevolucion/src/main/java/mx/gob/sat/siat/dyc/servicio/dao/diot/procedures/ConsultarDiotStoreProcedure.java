/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.servicio.dao.diot.procedures;

import java.sql.SQLException;
import java.sql.Types;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.domain.diot.DiotDTO;
import mx.gob.sat.siat.dyc.servicio.dao.diot.impl.mapper.ConsultarDiotMapper;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyC;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.sp.ConstantesSPDIOT;
import mx.gob.sat.siat.dyc.util.constante.sp.ConstantesSPICEP;

import org.apache.log4j.Logger;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.object.StoredProcedure;


/**
 * @author Israel Chavez
 */
public class ConsultarDiotStoreProcedure extends StoredProcedure {

    private static final Logger LOGGER = Logger.getLogger(ConsultarDiotStoreProcedure.class);


    /**
     * Variable estatica temporal para validar la entrada de las DIOT en el sistema
     */
    public static final String MENSAJE_LOG_ERROR_DATOS = ">>> ConsultarDiotStoreProcedure Se recuperaron: ";

    /**
     * Variable estatica temporal para validar la entrada de las DIOT en el sistema
     */
    public static final String MENSAJE_LOG_ERROR_DATOS_RFC = ">>> ConsultarDiotStoreProcedure RFC: ";


    /**
     * Variable estatica temporal para validar la entrada de las DIOT en el sistema
     */
    public static final String MENSAJE_LOG_ERROR_DATOS_NUMERO_DIOT = ">>> ConsultarDiotStoreProcedure NumeroDiot: ";

    /**
     * Variable estatica temporal para validar la entrada de las DIOT en el sistema
     */
    public static final String MENSAJE_LOG_ERROR_DATOS_NUMERO_FECHA = ">>> ConsultarDiotStoreProcedure FechaDiot: ";

    /**
     * Variable estatica temporal para validar la entrada de las DIOT en el sistema
     */
    public static final String MENSAJE_LOG_ERROR_DATOS_NUMERO_USUARIO = ">>> ConsultarDiotStoreProcedure Usuario: ";


    public ConsultarDiotStoreProcedure() {
        super();
    }


    /**
     * @param jdbcTemplate
     * @param callStoreProcedure
     */
    public ConsultarDiotStoreProcedure(JdbcTemplate jdbcTemplate, String callStoreProcedure) {

        super(jdbcTemplate, callStoreProcedure);

        declareParameter(new SqlReturnResultSet(ConstantesSPICEP.RESULTSETNAMEFORICEPURDCFAT,
                                                new ConsultarDiotMapper()));

        declareParameter(new SqlParameter(ConstantesSPICEP.TXT_RFC, Types.VARCHAR));
        declareParameter(new SqlParameter(ConstantesSPDIOT.NUMERO_OPERACION_DIOT, Types.VARCHAR));
        declareParameter(new SqlParameter(ConstantesSPDIOT.FECHA_PRESENTACION_DIOT, Types.VARCHAR));
        declareParameter(new SqlParameter(ConstantesSPICEP.TXT_USR, Types.VARCHAR));


        compile();
    }

    public DiotDTO buscaDiot(DiotDTO diot) throws SQLException {

        DiotDTO diotDTO = new DiotDTO();

        Map inParameters = new HashMap();

        inParameters.put(ConstantesSPICEP.TXT_RFC, diot.getTxtrfc());
        inParameters.put(ConstantesSPDIOT.NUMERO_OPERACION_DIOT, diot.getTxtnumoperacion());
        inParameters.put(ConstantesSPDIOT.FECHA_PRESENTACION_DIOT, diot.getTxtfechapresen());
        inParameters.put(ConstantesSPICEP.TXT_USR, diot.getTxtusr());

        LOGGER.error(MENSAJE_LOG_ERROR_DATOS_RFC + diot.getTxtrfc());
        LOGGER.error(MENSAJE_LOG_ERROR_DATOS_NUMERO_DIOT + diot.getTxtnumoperacion());
        LOGGER.error(MENSAJE_LOG_ERROR_DATOS_NUMERO_FECHA + diot.getTxtfechapresen());
        LOGGER.error(MENSAJE_LOG_ERROR_DATOS_NUMERO_USUARIO + diot.getTxtusr());

        Map out = this.execute(inParameters);


        List diotDTOList = (List)out.get(ConstantesSPICEP.RESULTSETNAMEFORICEPURDCFAT);

        if (diotDTOList.size() > ConstantesDyCNumerico.VALOR_0) {

            diotDTO = (DiotDTO)diotDTOList.get(ConstantesDyC.PRIMER_ELEMENTO_DE_LISTA);

            LOGGER.error(MENSAJE_LOG_ERROR_DATOS + diotDTO.getVdNombre());
            LOGGER.error(MENSAJE_LOG_ERROR_DATOS + diotDTO.getVdRfceeog1());
            LOGGER.error(MENSAJE_LOG_ERROR_DATOS + diotDTO.getVdCplearv1());
            LOGGER.error(MENSAJE_LOG_ERROR_DATOS + diotDTO.getVdCtdliea1());
            LOGGER.error(MENSAJE_LOG_ERROR_DATOS + diotDTO.getVdEjercic1());
            LOGGER.error(MENSAJE_LOG_ERROR_DATOS + diotDTO.getVdFperceh1());
        }

        return diotDTO;
    }

}
