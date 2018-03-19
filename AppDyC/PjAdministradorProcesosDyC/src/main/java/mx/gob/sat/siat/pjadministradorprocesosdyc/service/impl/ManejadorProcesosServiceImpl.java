package mx.gob.sat.siat.pjadministradorprocesosdyc.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import mx.gob.sat.siat.pjadministradorprocesosdyc.dao.DyctAdminProcesosDAO;
import mx.gob.sat.siat.pjadministradorprocesosdyc.dto.DyctAdminProcesosDTO;
import mx.gob.sat.siat.pjadministradorprocesosdyc.service.ManejadorProcesosService;
import mx.gob.sat.siat.pjadministradorprocesosdyc.util.excepcion.SIATException;

import org.apache.log4j.Logger;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "manejadorProcesosService")
public class ManejadorProcesosServiceImpl implements ManejadorProcesosService {
    
    private static final int EJECUCIONOK  = 1;
    private static final Logger LOGGER    = Logger.getLogger(ManejadorProcesosServiceImpl.class);
    
    @Autowired
    private DyctAdminProcesosDAO dyctAdminProcesosDAO;
    
    /**
     * <pre>
     * Ejecuta uno a uno los procesos que se encuentran en la base de datos.
     * 
     * </pre>
     * @throws SIATException
     */
    @Override
    public void ejecutarProcesos() throws SIATException {
        List<DyctAdminProcesosDTO> listaProcesos = dyctAdminProcesosDAO.consultar();
        
        List<DyctAdminProcesosDTO> listaProcesosOk  = new ArrayList<DyctAdminProcesosDTO>();
        List<DyctAdminProcesosDTO> listaProcesosMal = new ArrayList<DyctAdminProcesosDTO>();
        
        for(DyctAdminProcesosDTO proceso : listaProcesos) {
            LOGGER.info("Nombre del proceso obtenido: "+proceso.getNombre());
            
            if(proceso.getEjecucionCorrecta()==EJECUCIONOK) {
                listaProcesosOk.add(proceso);
            } else {
                listaProcesosMal.add(proceso);
            }
        }
        ordenarProcesos(listaProcesosOk, listaProcesosMal);
    }
    
    
    /**
     * <pre>
     * Ordena los procesos, si hay procesos erroneos los ordena primero de acuerdo a su prioridad
     * y despues coloca los procesos que fueron ejecutados correctamente al final, ordenandolos 
     * tambien de acuerdo a su proridad y al final los coloca en una sola lista.
     * 
     * Cuando esto termina, se ordenan los elementos en base a su prioridad.
     * </pre>
     * 
     * @param listaProcesosOk  Son todos aquellos procesos que se tienen en el campo EjecucionCorrecta=1
     * @param listaProcesosMal Son todos aquellos procesos que se tienen en el campo EjecucionCorrecta=0
     * @return Una lista con todos los procesos ordenados, primero los procesos con errores y despues los procesos ejecu
     * tados correctamente.
     */
    private List<DyctAdminProcesosDTO> ordenarProcesos(List<DyctAdminProcesosDTO> listaProcesosOk, List<DyctAdminProcesosDTO> listaProcesosMal) {
        List<DyctAdminProcesosDTO> listaProcesos = new ArrayList<DyctAdminProcesosDTO>();
        
        listaProcesos.addAll(ordenarPorPrioridad(listaProcesosMal));
        listaProcesos.addAll(ordenarPorPrioridad(listaProcesosOk));
        
        asignarOrdenEjecucion(listaProcesos);
        return listaProcesos;
    }
    
    /**
     * Ordena una lista de procesos de acuerdo a la prioridad
     *
     * @param lista
     * @return
     */
    private List<DyctAdminProcesosDTO> ordenarPorPrioridad(List<DyctAdminProcesosDTO> lista) {
        Collections.sort(lista, new Comparator<DyctAdminProcesosDTO>(){
            @Override
            public int compare(DyctAdminProcesosDTO o1, DyctAdminProcesosDTO o2) {
                return o1.getPrioridad().compareTo(o2.getPrioridad());
            }
        });
        return lista;
    }
    
    /**
     * Asigna un orden de ejecucion, de acuerdo a las prioridades de los procesos, si tienen un mismo nivel 
     * de prioridad, se les asgina el mismo numero de orden de ejecucion, en caso de ser diferentes, se les 
     * asigna un numero de ejecucion distinto.
     * 
     * A los procesos de mayor prioridad, se les asignan los primeros numeros de orden de ejecucion, por
     * el contrario, a los procesos de baja prioridad se les asignan los ultimos numeros del orden de 
     * ejecucion.
     * 
     * Ejemplo:
     * 
     * Proceso1 - Prioridad 9
     * Proceso2 - Prioridad 1
     * 
     * Al asignarles el orden de ejecucion quedaria de la siguiente manera:
     * 
     * Proceso1 - Prioridad 9 - Orden de ejecucion: 1
     * Proceso2 - Prioridad 1 - Orden de ejecucion: 2
     *
     * @param listaProcesos
     * @return
     */
    private List<DyctAdminProcesosDTO> asignarOrdenEjecucion(List<DyctAdminProcesosDTO> listaProcesos) {
        
        int i = 0;
        int indiceActual   = 0;
        int indiceAnterior = 0;
        Iterator it = null;
        DyctAdminProcesosDTO proceso = null;
        
        it = listaProcesos.iterator();
        while (it.hasNext()) {
            proceso = (DyctAdminProcesosDTO)it.next();
            indiceActual = proceso.getPrioridad();
            
            if (i==0) {
                i++;    
                indiceAnterior = indiceActual;
                proceso.setOrdenDeEjecucion(i);
            }
            
            if(indiceAnterior==indiceActual) {
                proceso.setOrdenDeEjecucion(i);
                
            } else {
                i++;
                proceso.setOrdenDeEjecucion(i);
            }
        }
        
        return listaProcesos;
    }
    
    public void setDyctAdminProcesosDAO(DyctAdminProcesosDAO dyctAdminProcesosDAO) {
        this.dyctAdminProcesosDAO = dyctAdminProcesosDAO;
    }

    public DyctAdminProcesosDAO getDyctAdminProcesosDAO() {
        return dyctAdminProcesosDAO;
    }
}
