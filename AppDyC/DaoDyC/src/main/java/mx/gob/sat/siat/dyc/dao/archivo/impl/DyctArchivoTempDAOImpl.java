package mx.gob.sat.siat.dyc.dao.archivo.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.archivo.DyctArchivoTempDAO;
import mx.gob.sat.siat.dyc.dao.archivo.impl.mapper.DyctArchivoTempMapper;
import mx.gob.sat.siat.dyc.domain.archivo.DyctArchivoTempDTO;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository(value = "dyctArchivoTempDAO")
public class DyctArchivoTempDAOImpl implements DyctArchivoTempDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private static final Logger LOG = Logger.getLogger(DyctArchivoTempDAOImpl.class.getName());

    public DyctArchivoTempDAOImpl() {
        super();
    }

    @Override
    public List<DyctArchivoTempDTO> listaArchivoTemp(Integer folioTemp) {
        String sql =
            "select * from dyct_archivotemp where folioservtemp in (select folioservtemp from dycp_comptemp where folioavisotemp = ?)";
        List<DyctArchivoTempDTO> lista =
            jdbcTemplateDYC.query(sql, new Object[] { folioTemp }, new DyctArchivoTempMapper());
        return lista;
    }

    @Override
    public void createArchivoTemp(DyctArchivoTempDTO input) {

        LOG.info("INIT CREATE ArchivoTemp::: " + input.getTipoArchivo());

        jdbcTemplateDYC.update(SQLOracleDyC.CREATE_ARCHIVO_TEMP.toString(),
                               new Object[] { input.getDyctServicioTempDTO().getFolioServTemp(), input.getUrl(),
                                              input.getNombreArchivo(), input.getTipoArchivo(),
                                              input.getDescripcion() });

    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }

    @Override
    public List<DyctArchivoTempDTO> findArchivoSolTemp(Integer folioTemp) {
        LOG.info("INIT findArchivoSolTemp::: " + folioTemp);
        String sql = "select * from dyct_archivotemp where folioservtemp in (?) order by idarchivotemp";
        return jdbcTemplateDYC.query(sql, new Object[] { folioTemp }, new DyctArchivoTempMapper());
    }
}
