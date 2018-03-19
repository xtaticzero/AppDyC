package mx.gob.sat.siat.dyc.gestionsol.dao.aprobardocumento;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.BandejaDocumentosDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.CatalogoAprobadorDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.SeguimientoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.vo.DyccMatrizDocVO;


/**
 * @author Ericka Janth Ibarra Ponce
 * @date 02/2014
 *
 * */

public interface ComentarioDAO {
    
    /**
     * Metodo que se utiliza para buscar los aprobadores que son de mayor rago al escalar
     *
     * @param cveADM clave de administracion desconcentrada
     * @param numEmpleado numero de empleado del aprobador que va a escalar la solicitud
     * @param claveNivel Es el puesto o nivel que tene el empleado (mientras mas bajo sea el numero, mayor es el rango)
     * @param centroCostoOP Es el centro de costos en el cual esta trabajando el empleado actualmente
     * @return lista de aprobadores de mayor rango en comparacion del que escala
     * @throws SIATException
     */
    List<CatalogoAprobadorDTO> buscarAprobador(int cveADM, String numEmpleado, Integer claveNivel, Integer centroCostoOP) throws SIATException;

    List<DyccMatrizDocVO> buscarMatrizRR() throws SIATException;

    void insertaSeguimiento(SeguimientoDTO seguimientoDTO) throws SIATException;

    void actualizarDocumento(Integer idEstado, String numControlDoc) throws SIATException;

    void actualizarTipoFirma(Integer idTipoFirma, String numControlDoc) throws SIATException;

    void actualizarResolucion(Integer idEstadoResol, String numControl) throws SIATException;

    void actualizadDocuentoReq(Integer numEmpleado, String numControlDoc) throws SIATException;
    void insertar(DyctDocumentoDTO documento,BandejaDocumentosDTO andejaDoc) throws SIATException;
    void actuaDocuentoReq(Integer idEstadoReq, String numControlDoc)throws SIATException;
    List<DyccTipoTramiteDTO> obtenerTipoTramite(String numControlDoc) throws SIATException;
}
