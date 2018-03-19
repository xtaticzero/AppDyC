package mx.gob.sat.mat.dyc.compensaciones.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import mx.gob.sat.mat.dyc.compensaciones.dao.CreaCasoCompDWHDAO;
import mx.gob.sat.mat.dyc.compensaciones.service.CasoCompensacionPrincipalService;
import mx.gob.sat.siat.cobranza.diahabil.api.DiasHabilFacade;
import mx.gob.sat.siat.dyc.casocomp.dto.districomp.CasoCompensacionVO;
import mx.gob.sat.siat.dyc.casocomp.service.districomp.CrearCasoCompService;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/


@Service("casoCompensacionPrincipalService")
public class CasoCompensacionPrincipalServiceImpl implements CasoCompensacionPrincipalService
{
    @Autowired
    private CreaCasoCompDWHDAO daoCreaCasoCompDWH;

    @Autowired
    private CrearCasoCompService serviceCrearCasoComp;
    @Autowired
    private  DiasHabilFacade diasHabilFacade;
    private static final Logger LOG = Logger.getLogger(CasoCompensacionPrincipalServiceImpl.class);
    /*@Autowired
    private SiosConsultaPagosStoredProcedureInterface spBuscarPagos*/
    @Override
    public void procesoCasoCompensacion()
    {
        LOG.error("inicio procesoCasoCompensacion....");
     
       Date fechaActual=new Date();
        boolean esdiaHabil=diasHabilFacade.esHabilComputoPlazo(fechaActual,fechaActual);
         LOG.error(" fdia habil "+esdiaHabil);
        if(esdiaHabil){
              LOG.error("  ES DIA HABIL" );
       
        Date fechaQuery=  encontrarFechaAnterioresDias(ConstantesDyCNumerico.VALOR_3);
         LOG.error(" fechaquery encontrada "+fechaQuery);
        List<CasoCompensacionVO> declaraciones = daoCreaCasoCompDWH.selecXDeclaracion(fechaQuery);
        LOG.error("se encontraron num decls ->" + declaraciones.size());
     
        for(CasoCompensacionVO declaracion: declaraciones){
            try {
                serviceCrearCasoComp.creaCasoCompensacion(declaracion);
            } catch (Exception ex) {
                LOG.error("Error al intentar crear un caso de compensacion en MAT-DYC; mensaje error ->" + ex.getMessage());
                ManejadorLogException.getTraceError(ex);
            }
        }
        }else{
              LOG.error(" NO ES DIA HABIL" );
        }
    }
    // Suma los días recibidos a la fecha  

 private Date encontrarFechaAnterioresDias( int dias){

      Calendar calendar = Calendar.getInstance();
      calendar.setTime(new Date());
   
     
      for(int i=0;i<dias;i++){
         
          boolean esdiaHabil=false;
          do{
             calendar.add(Calendar.DAY_OF_YEAR, -1);
             esdiaHabil= diasHabilFacade.esHabilComputoPlazo(calendar.getTime(),calendar.getTime());
          }while(!esdiaHabil);
      }
      return calendar.getTime();  

 }
}


