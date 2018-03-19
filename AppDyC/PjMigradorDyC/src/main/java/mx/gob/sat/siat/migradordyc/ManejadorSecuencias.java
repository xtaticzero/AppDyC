package mx.gob.sat.siat.migradordyc;

import java.util.List;

import mx.gob.sat.siat.migradordyc.dto.SecuenciaDTO;
import mx.gob.sat.siat.migradordyc.mapper.SecuenciaMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


@Service
public class ManejadorSecuencias
{
    @Autowired
    private JdbcTemplate jdbcTemplateOrigen;
    
    @Autowired
    private JdbcTemplate jdbcTemplateDestino;
    
    public void sincronizarSecuencias()
    {
        String query = "SELECT * FROM ALL_SEQUENCES WHERE SEQUENCE_OWNER = 'SIAT_DYC_ADMIN'";
        
        List<SecuenciaDTO> secuenciasOrigen = jdbcTemplateOrigen.query(query, new SecuenciaMapper());
       
        List<SecuenciaDTO> secuenciasDestino = jdbcTemplateDestino.query(query, new SecuenciaMapper());
        
        System.out.println("numero de secuencias origen ->" + secuenciasOrigen.size() + "<-");
        System.out.println("numero de secuencias destino ->" + secuenciasDestino.size() + "<-");
        
        for(SecuenciaDTO secuenciaDestino : secuenciasDestino)
        {
            SecuenciaDTO secuenciaOrigen = encontrarSecuencia(secuenciasOrigen, secuenciaDestino);
            if(secuenciaOrigen != null)
            {
                if(!estanSincronizadas(secuenciaOrigen, secuenciaDestino))
                    sincronizarSecuencias(secuenciaOrigen, secuenciaDestino);
                else
                    System.err.println("La secuencia ->" + secuenciaDestino.getSEQUENCE_NAME() + "<- esta Sincronizada en las bases de datos, no se realizará ninguna acción");
            }
            else
                System.err.println("La secuencia ->" + secuenciaDestino.getSEQUENCE_NAME() + "<- NO existe en la base de datos Origen");
        }
    }
    
    private boolean estanSincronizadas(SecuenciaDTO secuencia1, SecuenciaDTO secuencia2)
    {
        int numActual1 = secuencia1.getLAST_NUMBER().intValue() - secuencia1.getINCREMENT_BY().intValue();
        int numActual2 = secuencia2.getLAST_NUMBER().intValue() - secuencia2.getINCREMENT_BY().intValue();
        return numActual1 == numActual2;
    }
    
    private SecuenciaDTO encontrarSecuencia(List<SecuenciaDTO> secuencias, SecuenciaDTO secuencia)
    {
        for(SecuenciaDTO secAux : secuencias){
            if(secuencia.getSEQUENCE_NAME().equals(secAux.getSEQUENCE_NAME()))
                return secAux;
        }

        return null;
    }

    public void sincronizarSecuencias(SecuenciaDTO so, SecuenciaDTO sd)
    {
        int numActualO = 0;
        int numActualD = 0;
        int incrementAux = 0;

        try
        {
            numActualO = so.getLAST_NUMBER().intValue() - so.getINCREMENT_BY().intValue();
            numActualD = sd.getLAST_NUMBER().intValue() - sd.getINCREMENT_BY().intValue();
                
            incrementAux = numActualO - numActualD; 
            String sentAlterIncrement = "Alter Sequence " + sd.getSEQUENCE_NAME() + " Increment By " + incrementAux;
            jdbcTemplateDestino.update(sentAlterIncrement);
    
            String queryNextValue = "Select " + sd.getSEQUENCE_NAME() +".Nextval From Dual";
            jdbcTemplateDestino.queryForObject(queryNextValue, Integer.class);
    
            String sentNormalizarIncrement = "Alter Sequence " + sd.getSEQUENCE_NAME() + " Increment By " + so.getINCREMENT_BY().intValue();
            jdbcTemplateDestino.update(sentNormalizarIncrement);
        }
        catch(Exception e)
        {
            System.err.println("Ocurrió un error al sincronizarSecuencias ->" + e.getMessage() + "<- ---------------------");
            System.err.println("numero actual sentencia Origen ->" + numActualO);
            System.err.println("numero actual sentencia Destino ->" + numActualD);
            System.err.println("incremento auxiliar ->" + incrementAux);
            System.err.println("------------------------------------------------------------------------");
        }
    }
    
    public void inicializarSecuencias()
    {
        System.out.println("inicializar secuencias manejador");
        String query = "SELECT * FROM ALL_SEQUENCES WHERE SEQUENCE_OWNER = 'SIAT_DYC_ADMIN'";
        
        List<SecuenciaDTO> secuenciasOrigen = jdbcTemplateOrigen.query(query, new SecuenciaMapper());
        for(SecuenciaDTO secuencia : secuenciasOrigen){
            inicializarSecuencia(secuencia, jdbcTemplateOrigen);
        }
        
    }
    
    public void inicializarSecuencia(SecuenciaDTO secuencia, JdbcTemplate jdbcTemplate)
    {
        int incrementAux = 0;

        try
        {
            int numActual  = secuencia.getLAST_NUMBER().intValue() - secuencia.getINCREMENT_BY().intValue();
            if(numActual == 0){
                System.out.println("La secuencia " + secuencia.getSEQUENCE_NAME() + " esta Inicializada; NO se realizará accion");
            }

            String x = "alter sequence " + secuencia.getSEQUENCE_NAME() + " MINVALUE 0";
            jdbcTemplate.update(x);

            incrementAux = numActual * -1; 
            String sentAlterIncrement = "Alter Sequence " + secuencia.getSEQUENCE_NAME() + " Increment By " + incrementAux;
            jdbcTemplate.update(sentAlterIncrement);

            String queryNextValue = "Select " + secuencia.getSEQUENCE_NAME() +".Nextval From Dual";
            jdbcTemplate.queryForObject(queryNextValue, Integer.class);
        
            String sentNormalizarIncrement = "Alter Sequence " + secuencia.getSEQUENCE_NAME() + " Increment By 1";
            jdbcTemplate.update(sentNormalizarIncrement);
        }
        catch(Exception e)
        {
            System.err.println("Ocurrió un error al inicializar secuencia ->" + e.getMessage() + "<- ---------------------");
            e.printStackTrace();
            System.err.println("------------------------------------------------------------------------");
            return;
        }        
        
        System.out.println("se inicializo correctamente la setencia ->" + secuencia.getSEQUENCE_NAME());
    }

}
