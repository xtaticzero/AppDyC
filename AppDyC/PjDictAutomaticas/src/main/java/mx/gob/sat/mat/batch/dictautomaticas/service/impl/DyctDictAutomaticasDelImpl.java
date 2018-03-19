/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.mat.batch.dictautomaticas.service.impl;

import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.mat.batch.dictautomaticas.exception.DictAutomaticasException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import mx.gob.sat.mat.batch.dictautomaticas.service.DyctDictAutomaticasDel;
import mx.gob.sat.mat.batch.dictautomaticas.service.DyctDictAutomaticasService;
import mx.gob.sat.siat.dyc.domain.devolucionaut.DyctDictAutomaticaDTO;

/**
 *
 * @author root
 */
@Component(value = "dycDictAutomaticasDel")
public class DyctDictAutomaticasDelImpl implements DyctDictAutomaticasDel{
    
    private static final Logger LOGGER = Logger.getLogger(DyctDictAutomaticasDelImpl.class);
    private static final String SEPARADOR_INICIAL = "";
    private static final String SEPARADOR = ",";

    @Autowired
    private DyctDictAutomaticasService dycDictAutomaticasService;
    
    @Override
    public void exec() {
        List<DyctDictAutomaticaDTO> tramitesSuccess = new ArrayList();
        List<DyctDictAutomaticaDTO> tramitesError = new ArrayList();

        LOGGER.info( "Buscando tramites " );
        List<DyctDictAutomaticaDTO> lstTramitesDictaminados = dycDictAutomaticasService.getTramitesDictaminados();

        if ( lstTramitesDictaminados != null ){
            LOGGER.info( "Tramites encontrados : " + lstTramitesDictaminados.size() );
            StringBuilder mensajeProcesamiento;

            for (DyctDictAutomaticaDTO tramite : lstTramitesDictaminados){
                mensajeProcesamiento = new StringBuilder( "Procesando el tramite : " );
                mensajeProcesamiento.append( tramite.getNumControl() );
                LOGGER.info( mensajeProcesamiento.toString() );

                procesaTramite( tramite, tramitesSuccess, tramitesError );
            }
            
            LOGGER.info( getMensajeTramites( "Tramites dictaminados"    , tramitesSuccess ) );
            LOGGER.info( getMensajeTramites( "Tramites no dictaminados" , tramitesError ) );

        } else {
            LOGGER.info( "No hay registros por procesar" );
        }
    }
    
    private String getMensajeTramites ( String mensajeLista, List<DyctDictAutomaticaDTO> listaTramites ){
        StringBuilder mensajeListaTramites = new StringBuilder();
        
        mensajeListaTramites
            .append( mensajeLista )
            .append( getListaTramites( listaTramites ) ); 
        
        return mensajeListaTramites.toString();
    }
    
    private String getListaTramites ( List<DyctDictAutomaticaDTO> listaTramites ){
        StringBuilder mensajeListaTramites = new StringBuilder();

        mensajeListaTramites
                .append( " [" )
                .append( listaTramites.size() )
                .append( "] : " );

        String separador = SEPARADOR_INICIAL;
        for ( DyctDictAutomaticaDTO tramite : listaTramites ){
            mensajeListaTramites
                .append( separador )
                .append( tramite.getNumControl() );

            if ( !separador.equals( SEPARADOR ) ) {
                separador = SEPARADOR;
            }
        }
        
        return mensajeListaTramites.toString();
    }

    private void procesaTramite ( DyctDictAutomaticaDTO tramite, List<DyctDictAutomaticaDTO> tramitesSuccess, 
                                        List<DyctDictAutomaticaDTO> tramitesError ){

        try {
            dycDictAutomaticasService.procesarTramite( tramite );
            tramitesSuccess.add( tramite );
        } catch ( DictAutomaticasException ex ){
            tramitesError.add( tramite );
        }
    }
}
