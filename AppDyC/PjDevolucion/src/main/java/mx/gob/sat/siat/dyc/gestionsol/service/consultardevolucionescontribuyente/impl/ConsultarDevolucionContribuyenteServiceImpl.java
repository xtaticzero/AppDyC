/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.gestionsol.service.consultardevolucionescontribuyente.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mx.gob.sat.siat.dyc.dao.compensacion.DyccEstadoCompDAO;

import mx.gob.sat.siat.dyc.dao.regsolicitud.DyccEstadoSolDAO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccEstadoCompDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccEstadoSolDTO;
import mx.gob.sat.siat.dyc.gestionsol.dao.consultardevolucionescontribuyente.ConsultarDevolucionContribuyenteDAO;
import mx.gob.sat.siat.dyc.gestionsol.dto.consultardevolucionescontribuyente.ConsultarDevolucionesContribuyenteDTO;
import mx.gob.sat.siat.dyc.gestionsol.service.consultardevolucionescontribuyente.ConsultarDevolucionContribuyenteService;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author Alfredo Ramirez
 * @author Jesus Alfredo Hernandez Orozco.
 * @since   08/10/2013
 *
 */
@Service(value = "consultarDevolucionContribuyenteService")
public class ConsultarDevolucionContribuyenteServiceImpl implements ConsultarDevolucionContribuyenteService {

    private static final Integer COMPENSACION = 99;
    public static final Integer DEV_AUTOMATICA_IVA = 139;
    private static final Logger LOGGER = Logger.getLogger(ConsultarDevolucionContribuyenteServiceImpl.class);

    @Autowired
    private ConsultarDevolucionContribuyenteDAO consultarDevolucionContribuyenteDAO;

    @Autowired
    private DyccEstadoSolDAO dyccEstadoSolDAO;
    
    @Autowired 
    private DyccEstadoCompDAO dyccEstadoCompDAO;

    @Override
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT)
    public List<ConsultarDevolucionesContribuyenteDTO> listaSolicitudesPorContribuyente(String rfc,
                                                                                        String status) throws SIATException {
        List<ConsultarDevolucionesContribuyenteDTO> listaSolCon =
            new ArrayList<ConsultarDevolucionesContribuyenteDTO>();
        try {
            if (!status.equals(COMPENSACION.toString())) {
                listaSolCon = consultarDevolucionContribuyenteDAO.listaSolicitudesPorContribuyente(rfc, status);
            } else {
                listaSolCon = consultarDevolucionContribuyenteDAO.listaCompensacionesPorContribuyente(rfc);
            }
        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage());
        }
        return listaSolCon;
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT)
    public List<ConsultarDevolucionesContribuyenteDTO> listaSolicitudesPorContribuyente(final String rfc, final String status, final Integer idTipoSolicitud, final String ejercicio) throws SIATException {
        List<ConsultarDevolucionesContribuyenteDTO> listaSolCon =
            new ArrayList<ConsultarDevolucionesContribuyenteDTO>();
        try {
            switch (idTipoSolicitud) 
            {
               case 1:  
                   listaSolCon = consultarDevolucionContribuyenteDAO.getCompensacionesContribuyente(rfc, status, ejercicio);
                   break;
               case 2: 
                    listaSolCon = consultarDevolucionContribuyenteDAO.getSolicitudesContribuyenteSinDevAutIVA(rfc, status, ejercicio);
                    break;
               case ConstantesDyCNumerico.VALOR_3:
                    listaSolCon = consultarDevolucionContribuyenteDAO.getSolicitudesContribuyenteDevAutIVA(rfc, status, ejercicio);
                    break;
               default: break;
           }
        } catch (DataAccessException e) {
            LOGGER.error(e.getMessage());
        }
        return listaSolCon;
    }

    /**
     * Conulta los datos de la solicitud que se va a solventar que ha sido seleccionada en pantalla.
     *
     * @param numControl
     * @return
     * @throws SIATException
     */
    @Override
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT)
    public ConsultarDevolucionesContribuyenteDTO obtenerDocumento(String numControl) throws SIATException {
        ConsultarDevolucionesContribuyenteDTO objeto = null;
        try {
            objeto = consultarDevolucionContribuyenteDAO.obtenerDocumento(numControl);
        } catch (SIATException siate) {
            LOGGER.warn("No hubo SOLICITUDES para solventar, numControl: "+numControl);
        }
        return objeto;
    }

    /**
     * Conulta los datos de la compensacion que se va a solventar que ha sido seleccionada en pantalla.
     *
     * @param numControl
     * @return
     * @throws SIATException
     */
    @Override
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT)
    public ConsultarDevolucionesContribuyenteDTO obtenercompensacion(String numControl,
                                                                     String rfc) throws SIATException {
        ConsultarDevolucionesContribuyenteDTO objeto = null;
        try {
            objeto = consultarDevolucionContribuyenteDAO.obtenercompensacion(numControl, rfc);
        } catch (SIATException siate) {
            LOGGER.warn("No hubo COMPENSACIONES para solventar, numControl: "+numControl+", rfc: "+rfc);
        }
        return objeto;
    }

    /**
     * Consulta los diferentes estados de las solicitudes que existen y las cruza con los estados de las solicitudes que
     * tiene disponible el contribuyente para mostrar solo estatus que correspondan con los de las solicitudes.
     *
     * @return
     */
    @Override
    public Map<String, String> consultarEstadosDeSolicitud(String rfc) throws SIATException {
        List<DyccEstadoSolDTO> listaDeCatalogo = null;
        Map<String, String> listaDeAcuerdoALasSolicitudes = new HashMap<String, String>();

        listaDeCatalogo = dyccEstadoSolDAO.obtenerLista();
        List<Integer> index = consultarDevolucionContribuyenteDAO.listarStatusDeDevoluciones(rfc);
        
        for (Integer i : index) 
        {
            for (DyccEstadoSolDTO objeto : listaDeCatalogo) 
            {
                if (objeto.getIdEstadoSolicitud().equals(i)) 
                {
                    listaDeAcuerdoALasSolicitudes.put(objeto.getIdEstadoSolicitud().toString(), objeto.getDescripcion());
                }
            }
        }
        return listaDeAcuerdoALasSolicitudes;
    }
    
    /**
     * Consulta los diferentes estados de las solicitudes que existen y las cruza con los estados de las solicitudes que
     * tiene disponible el contribuyente para mostrar solo estatus que correspondan con los de las solicitudes.
     *
     * @return
     */
    @Override
    public Map<String, String> consultarEstadosDeSolicitud(final String rfc, final String ejercicio) throws SIATException {
        List<DyccEstadoSolDTO> listaDeCatalogo = null;
        Map<String, String> listaDeAcuerdoALasSolicitudes = new HashMap<String, String>();

        listaDeCatalogo = dyccEstadoSolDAO.obtenerLista();
        List<Integer> index = consultarDevolucionContribuyenteDAO.listarStatusDeDevoluciones(rfc, ejercicio);
        
        for (Integer i : index) {
            for (DyccEstadoSolDTO objeto : listaDeCatalogo) {
                if (objeto.getIdEstadoSolicitud().equals(i)) {
                    listaDeAcuerdoALasSolicitudes.put(objeto.getIdEstadoSolicitud().toString(), objeto.getDescripcion());
                }
            }
        }
        return listaDeAcuerdoALasSolicitudes;
    }
    
    @Override
    public Map<String, String> consultarEstadosDeSolicitudDevAutIVA(final String rfc, final String ejercicio) throws SIATException {
        List<DyccEstadoSolDTO> listaDeCatalogo = null;
        Map<String, String> listaDeAcuerdoALasSolicitudes = new HashMap<String, String>();

        listaDeCatalogo = dyccEstadoSolDAO.obtenerLista();
        List<Integer> index = consultarDevolucionContribuyenteDAO.listarStatusDeDevolucionesAutIVA(rfc, ejercicio);
        
        for (Integer i : index) {
            for (DyccEstadoSolDTO objeto : listaDeCatalogo) {
                if (objeto.getIdEstadoSolicitud().equals(i)) {
                    listaDeAcuerdoALasSolicitudes.put(objeto.getIdEstadoSolicitud().toString(), objeto.getDescripcion());
                }
            }
        }
        return listaDeAcuerdoALasSolicitudes;
    }
    
    @Override
    public Map<String, String> listaSolicitudesPorContribuyente(String rfc) throws SIATException {
        List<DyccEstadoSolDTO> listaDeCatalogo = null;
        Map<String, String> listaDeAcuerdoALasSolicitudes = new HashMap<String, String>();

        listaDeCatalogo = dyccEstadoSolDAO.obtenerLista();
        List<Integer> index = consultarDevolucionContribuyenteDAO.listarStatusDeDevoluciones(rfc);
        
        for (Integer i : index) {
            for (DyccEstadoSolDTO objeto : listaDeCatalogo) {
                if (objeto.getIdEstadoSolicitud().equals(i)) {
                    listaDeAcuerdoALasSolicitudes.put(objeto.getIdEstadoSolicitud().toString(), objeto.getDescripcion());
                }
            }
        }
        return procesarListaDeSolicitudes(listaDeAcuerdoALasSolicitudes);
    }

    /**
     * Consulta si tiene una compensacion asociada a su lista de documentos a solventar.
     *
     * @param rfc
     * @return
     * @throws SIATException
     */
    @Override
    public Integer consultarSiTieneCompensacion(String rfc) throws SIATException {
        return consultarDevolucionContribuyenteDAO.consultarSiTieneCompensacion(rfc);
    }
    
    @Override
    public Map<String, String> consultarSiTieneCompensacionCC(final String rfc, final Integer idEjercicio) throws SIATException {
        Map<String, String> listaDeAcuerdoALasSolicitudes = new HashMap<String, String>();
        List<DyccEstadoCompDTO> list = null;
        List<Integer> index = consultarDevolucionContribuyenteDAO.consultarSiTieneCompensacionCC(rfc, idEjercicio);
        list = dyccEstadoCompDAO.obtenerLista();
        
          for (Integer i : index) 
          {
            for(DyccEstadoCompDTO objeto : list) 
            {
                if (objeto.getIdEstadoComp().equals(i)) 
                {
                    listaDeAcuerdoALasSolicitudes.put(objeto.getIdEstadoComp().toString(), objeto.getDescripcion());
                }
            }
        }
        
        return listaDeAcuerdoALasSolicitudes;
    }

    

    /**
     * <br />
     * Procesa la lista de las solicitudes fusionando los siguientes estatus en uno solo: <br />
     *  - En Revisión<br />
     *  - Autorizada Parcial con Remanente Disponible<br />
     *  - Autorizada Parcial con Remanente Negado<br />
     *  - Autorizada Total<br />
     *  A sólo en revisión...<br />
     *
     * @param listaDeAcuerdoALasSolicitudes
     * @return
     */
    private Map<String, String> procesarListaDeSolicitudes(Map<String, String> listaDeAcuerdoALasSolicitudes) {
        boolean bandera = false;
        boolean banderaDes = false;
        int contador = 0;
        int contadorDes = 0;
        StringBuilder llaveProvisional = new StringBuilder();
        StringBuilder llaveProvisionalAut = new StringBuilder();
        Map<String, String> listaProcesada = new HashMap<String, String>();
        String key = "";
        for (Map.Entry<String, String> mapa : listaDeAcuerdoALasSolicitudes.entrySet()) {
            key = mapa.getKey();
            bandera = key.equals(String.valueOf(ConstantesDyCNumerico.VALOR_3))
                    || key.equals(String.valueOf(ConstantesDyCNumerico.VALOR_6))
                    || key.equals(String.valueOf(ConstantesDyCNumerico.VALOR_7));

            banderaDes = key.equals(String.valueOf(ConstantesDyCNumerico.VALOR_5))
                    || key.equals(String.valueOf(ConstantesDyCNumerico.VALOR_15));

            if (bandera) {
                if (contador == 0) {
                    llaveProvisional.append(mapa.getKey());
                } else {
                    llaveProvisional.append(",").append(mapa.getKey());
                }
                contador++;

            } else if (banderaDes) {
                if (contadorDes == 0) {
                    llaveProvisionalAut.append(mapa.getKey());
                } else {
                    llaveProvisionalAut.append(",").append(mapa.getKey());
                }
                contadorDes++;

            } else {
                listaProcesada.put(mapa.getKey(), mapa.getValue());
            }
        }
        if (contador > 0) {
            listaProcesada.put(llaveProvisional.toString(), "En proceso");
        }

        if (contadorDes > 0) {
            listaProcesada.put(llaveProvisionalAut.toString(), "Desistida");
        }

        return listaProcesada;
    }

    public DyccEstadoCompDAO getDyccEstadoCompDAO() {
        return dyccEstadoCompDAO;
    }

    public void setDyccEstadoCompDAO(DyccEstadoCompDAO dyccEstadoCompDAO) {
        this.dyccEstadoCompDAO = dyccEstadoCompDAO;
    }
    
    
}
