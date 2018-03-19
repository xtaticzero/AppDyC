package mx.gob.sat.siat.dyc.adminprocesos.dao.impl;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.dyc.adminprocesos.dao.ArchivosHistoricoDAO;
import mx.gob.sat.siat.dyc.adminprocesos.dao.impl.mapper.ArchivosHistoricoMapper;
import mx.gob.sat.siat.dyc.adminprocesos.dto.RegistroFallidoDTO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @author Jesus Alfredo Hernandez Orozco
 */
@Repository(value = "archivosHistoricoDAO")
public class ArchivosHistoricoDAOImpl implements ArchivosHistoricoDAO {
    public ArchivosHistoricoDAOImpl() {
        super();
    }
    
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;
    
    private static final String CONSULTA_DETALLE = "SELECT a.numcontrol, b.causa, C.PUNTODEMONTAJE\n" + 
                                                   "FROM Dycb_SeguimientoFS A \n" + 
                                                   "INNER JOIN DYCB_CAUSAFALLOFS B ON (A.IDFSSEGUIMIENTO = b.IDFSSEGUIMIENTO)\n" + 
                                                   "INNER JOIN DYCC_FILESYSTEM   C ON (A.IDFILESYSTEM=C.IDFILESYSTEM)\n" + 
                                                   "WHERE A.FECHA BETWEEN ? AND ? AND A.EXITO = 0";
    private static final Logger LOGGER = Logger.getLogger(ArchivosHistoricoDAOImpl.class);

    /**
     * Lista todos los tr&aacute;mites que tuvieron un fallo en el copiado de los documentos que contienen de un
     * filesystem a otro.
     *
     * @param fecha Fecha sobre la cual se consulta
     * @return
     */
    @Override
    public List<RegistroFallidoDTO> listarDetalleDeFallo(Date fecha) {
        Date fechaInicio = null;
        Date fechaFin    = null;
        SimpleDateFormat formatoSimple   = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatoCompleto = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        List<RegistroFallidoDTO> lista   = null;
        
        try {
            fechaInicio = formatoCompleto.parse(formatoSimple.format(fecha)+" 00:00:01");
            fechaFin    = formatoCompleto.parse(formatoSimple.format(fecha)+" 23:59:59");
            
            lista = jdbcTemplateDYC.query(CONSULTA_DETALLE, 
                                          new Object[]{fechaInicio, fechaFin}, 
                                          new ArchivosHistoricoMapper());
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return lista;
    }
}
