package mx.gob.sat.siat.dyc.registro.service.solicitud.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.regsolicitud.SolicitudDevolucionRegistroVO;
import mx.gob.sat.siat.dyc.registro.dao.solicitud.impl.mapper.CosultaDevolucionesPendientesMapper;
import mx.gob.sat.siat.dyc.registro.service.solicitud.ConsultaDevolucionesPendientesServices;
import mx.gob.sat.siat.dyc.util.common.ParamOutputTO;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository("devPendientes")
public class CosultaDevolucionesPendientesImpl implements ConsultaDevolucionesPendientesServices{

    private static Logger logger = Logger.getLogger(CosultaDevolucionesPendientesImpl.class.getName());

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    @Override
    public ParamOutputTO<SolicitudDevolucionRegistroVO> solicitudesPendientes(String rfc){
        List<SolicitudDevolucionRegistroVO> solicitudes = null;
        try {
            logger.info("SOLICITUDES PENDIENDES INIT " + rfc);
            solicitudes =
                    jdbcTemplateDYC.query(SQLOracleDyC.CONSULTA_SOLICITUDES_PENDIENTES.toString(), new Object[] { rfc }, new CosultaDevolucionesPendientesMapper());
        } catch (DataAccessException dae) {
            logger.info("Se presento un error en la ejecucion : " + dae.getMessage() + " con el query : " +
                        SQLOracleDyC.CONSULTA_SOLICITUDES_PENDIENTES.toString() + " sin parametros : ");
            throw dae;
        }

        return new ParamOutputTO<SolicitudDevolucionRegistroVO>(solicitudes);
    }

}

