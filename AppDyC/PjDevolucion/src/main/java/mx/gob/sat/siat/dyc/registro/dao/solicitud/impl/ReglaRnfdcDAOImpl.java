package mx.gob.sat.siat.dyc.registro.dao.solicitud.impl;


import java.util.List;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.registro.bean.ReglaRnfdcVO;
import mx.gob.sat.siat.dyc.registro.dao.solicitud.ReglaRnfdcDAO;
import mx.gob.sat.siat.dyc.registro.dao.solicitud.impl.mapper.ReglaRnfdcMapper;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository()
public class ReglaRnfdcDAOImpl implements ReglaRnfdcDAO
{
    private static final Logger LOG = Logger.getLogger(ReglaRnfdcDAOImpl.class);
    
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;
    
    
    public static final StringBuilder CONSULTA_DICTAMINADOR_AGAFF = new StringBuilder(
            " SELECT * FROM (SELECT  SER.NUMEMPLEADODICT||'-'||SER.CLAVEADM EMP_CVE ")
            .append(" FROM  DYCP_SERVICIO SER ")
            .append(" INNER JOIN DYCP_SOLICITUD SOL ON SER.NUMCONTROL = SOL.NUMCONTROL ")
            .append(" INNER JOIN DYCC_DICTAMINADOR DIC ON DIC.NUMEMPLEADO = SER.NUMEMPLEADODICT AND DIC.CLAVEADM = SER.CLAVEADM ")
            .append(" INNER JOIN SIAT_DYC.SAT_AGS_EMPLEADOS_MV  AGS ON SER.NUMEMPLEADODICT = AGS.NO_EMPLEADO ")
            .append(" WHERE  SER.IDTIPOSERVICIO = 1  ")
            .append(" AND SOL.IDESTADOSOLICITUD in (3,4)")
            .append(" AND AGS.ESTATUS = 'A' ")
            .append(" AND SER.RFCCONTRIBUYENTE = ?")
            .append(" ORDER BY SER.NUMCONTROL ) WHERE ROWNUM = 1");

    @Override
    public ReglaRnfdcVO consultaReglaRnfdc(DyctSaldoIcepDTO saldo, DycpCompensacionDTO compensacionDTO)
    {
        ReglaRnfdcVO reglaVO = new ReglaRnfdcVO();
        try{
            StringBuilder query =  new  StringBuilder ("  SELECT * FROM ( ")
                             .append (" SELECT dycp.numcontrol as control, dycps.numempleadodict as numempleadodict ")
                             .append (" FROM dycp_servicio dycps ") 
                             .append (" INNER JOIN dycp_compensacion dycp ON dycps.numcontrol = dycp.numcontrol ")
                             .append (" INNER JOIN dycc_tipotramite dycc  ON dycps.idtipotramite = dycc.idtipotramite ")
                             .append (" INNER JOIN dyct_saldoicep dyct_s  ON dycp.idsaldoiceporigen = dyct_s.idsaldoicep ")
                             .append (" INNER JOIN dycc_dictaminador dycd ON dycd.numempleado = dycps.numempleadodict ") 
                             .append (" INNER JOIN SIAT_DYC.SAT_AGS_EMPLEADOS_MV  AGS ")
                             .append (" ON  dycd.NUMEMPLEADO = AGS.NO_EMPLEADO ")
                             .append (" WHERE dycps.rfccontribuyente = ?   ")
                             .append (" AND dycd.claveadm = ? ")
                             .append (" AND  AGS.ESTATUS = 'A' ")
                             .append (" AND dycd.ACTIVO = 1 ")
                             .append (" AND dycp.idestadocomp IN (?, ?, ?) ")
                             .append (" ORDER BY control DESC ") 
                             .append (" ) WHERE ROWNUM = 1");
        
        
         List<ReglaRnfdcVO> lstDatos = 
         jdbcTemplateDYC.query(query.toString(), new Object[] { saldo.getRfc(), compensacionDTO.getDycpServicioDTO().getClaveAdm(),
                                                                        Constantes.EstadosCompensacion.EN_PROCESO.getIdEstadoComp(),
                                                                        Constantes.EstadosCompensacion.REQUERIDO.getIdEstadoComp(),
                                                                        Constantes.EstadosCompensacion.PENDIENTE_RESOLVER.getIdEstadoComp() }, new ReglaRnfdcMapper());
            if(!lstDatos.isEmpty()){
                reglaVO = lstDatos.get(ConstantesDyC1.CERO);
            }
            return reglaVO;
        }
        catch(EmptyResultDataAccessException erdae){
            LOG.debug("NO se encontro el SALDOICEP ->" + saldo.getIdSaldoIcep());
            return null;
        }
    }

    @Override
    public String consultaReglaRnfdcSol(String rfc) {
        try {
            String query = " SELECT DYCP_S.NUMEMPLEADODICT \n"
                    + " FROM  DYCP_SERVICIO DYCP_S \n"
                    + " INNER JOIN DYCP_SOLICITUD DYCP_T \n"
                    + " ON DYCP_T.NUMCONTROL = DYCP_S.NUMCONTROL \n"
                    + " INNER JOIN SIAT_DYC.SAT_AGS_EMPLEADOS_MV  AGS \n"
                    + " ON DYCP_S.NUMEMPLEADODICT = AGS.NO_EMPLEADO \n"
                    + " WHERE DYCP_S.RFCCONTRIBUYENTE = ? \n"
                    + " AND DYCP_S.IDTIPOSERVICIO = 1 \n"
                    + " AND DYCP_T.IDESTADOSOLICITUD IN (3, 4, 15, 16) \n"
                    + " AND ROWNUM = 1 \n"
                    + " AND AGS.ESTATUS = 'A' \n"
                    + " ORDER BY DYCP_S.NUMCONTROL DESC ";

            return jdbcTemplateDYC.queryForObject(query, new Object[] { rfc },
                                                   String.class);
          
        } catch (EmptyResultDataAccessException erdae) {
            LOG.debug("No se encontro el RFC ->" + rfc);
            return null;
        }
    }
    
     @Override
    public String consultaReglaRNFDC35AGAFF(String rfc) {
        try {
            return jdbcTemplateDYC.queryForObject(CONSULTA_DICTAMINADOR_AGAFF.toString(), new Object[] { rfc },
                                                   String.class);
          
        } catch (EmptyResultDataAccessException erdae) {
            LOG.debug("No se encontro el RFC ->" + rfc);
            return null;
        }
    }
    
    

}
