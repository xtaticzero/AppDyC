/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.dao.tiposerv;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.FilaGridTramitesBean;
import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoServicioDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 * @author J. Isaac Carbajal Bernal
 */
public interface IDycpServicioDAO {

    DycpServicioDTO encontrar(String numControl) throws SIATException;

    List<DycpServicioDTO> selecXTiposervicio(DyccTipoServicioDTO tipoServicio);

    void insertar(DycpServicioDTO dycpServicioDTO) throws SIATException;

    int updateDictaminador(Integer numempleado, String numcontrol);

    List<DycpServicioDTO> obtenerSolicitudServicio(DyccDictaminadorDTO dictaminador) throws SIATException;

    List<DycpServicioDTO> selecXRfc(String rfc);

    List<DycpServicioDTO> selecDinamico(String clausulas, Object[] parametros, String campoOrdenar, String tipoOrdenamiento);

    List<FilaGridTramitesBean> selecDinamicoConsulta(String clausulas, Object[] parametros, String campoOrdenar, String tipoOrdenamiento, Integer idTipoServicio);

    List<FilaGridTramitesBean> selecDinamicoExportacion(String clausulas, Object[] parametros, String campoOrdenar, String tipoOrdenamiento, Integer idTipoServicio);

    Integer selecCountDinamico(String clausulas, Object[] parametros);

    Integer selecCountDinamicoConsulta(String clausulas, Object[] parametros, Integer idTipoServicio);

    Integer selecCountXRfc(String rfc);

    List<DycpServicioDTO> selecPaginadoXRfc(String rfc, Integer regInicial, Integer regFinal, String campoOrdenar, String tipoOrdenamiento);

    String getRFCOwner(String numControl);

    List<DycpServicioDTO> selecDinamicoTodo(String clausulas, Object[] parametros, String campoOrdenar, String tipoOrdenamiento);

    DycpServicioDTO obtenerServicioSinDictaminador(String numeroControl) throws SIATException;

    DycpServicioDTO obtenerUltimoServicioXContribuyente(String rfcContribuyente, int tipoServicio);
}
