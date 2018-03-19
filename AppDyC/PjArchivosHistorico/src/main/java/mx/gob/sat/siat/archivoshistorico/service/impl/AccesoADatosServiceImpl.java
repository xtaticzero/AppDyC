package mx.gob.sat.siat.archivoshistorico.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.archivoshistorico.dao.ConsultasHistoricoDao;
import mx.gob.sat.siat.archivoshistorico.dto.ArchivoHistoricoDto;
import mx.gob.sat.siat.archivoshistorico.service.AccesoADatosService;
import mx.gob.sat.siat.archivoshistorico.utils.Utils;
import mx.gob.sat.siat.dyc.dao.fallo.DycbCausaFalloFSDAO;
import mx.gob.sat.siat.dyc.dao.regsolicitud.DycpSolicitudDAO;
import mx.gob.sat.siat.dyc.dao.seguimiento.DycbSeguimientoFSDAO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.excepcion.DAOException;
import mx.gob.sat.siat.dyc.vo.DycbCausaFalloFSVO;
import mx.gob.sat.siat.dyc.vo.DycbSeguimientoFSVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Jesus Alfredo Hernandez Orozco
 */
@Service(value = "accesoADatosService")
public class AccesoADatosServiceImpl implements AccesoADatosService {

    public AccesoADatosServiceImpl() {
        super();
    }

    @Autowired
    private ConsultasHistoricoDao consultasHistoricoDAO;

    @Autowired
    private DycpSolicitudDAO dycpSolicitudDAO;

    @Autowired
    private ConsultasHistoricoDao consultasHistoricoDao;

    @Autowired
    private DycbSeguimientoFSDAO dycbSeguimientoFSDAO;

    @Autowired
    private DycbCausaFalloFSDAO dycbCausaFalloFSDAO;
    private static final int CUATRO=4;
    /**
     * Obtiene los numeros de control que se procesar&aacute;n para ser
     * actualizados dentro del filesystem.
     *
     * @return Retorna un arreglo de n&uacute;meros de control.
     * @throws SIATException Error durante tiempo de ejecuci&oacute;n.
     */
 
    @Override
    public List<Map<String, Object>> listarNumerosDeControlAProcesar(int maximoTramitesProcesar) throws SIATException {
        return dycpSolicitudDAO.obtenerNoControlParaHistorico(maximoTramitesProcesar);
    }

    
     @Override
    public List<Map<String, Object>> listarNumerosDeControNYVProcesados(int maximoNYVRegresar) throws SIATException {
        return dycpSolicitudDAO.obtenerNoControlNYVProcesado(maximoNYVRegresar);
    }
    @Override
    public List<ArchivoHistoricoDto> buscarDocumentosNYVProcesados(String numeroControl) throws DAOException {
        return consultasHistoricoDao.buscarDocumentosNYVProcesados(numeroControl);
    }
    /**
     * Obtiene una lista de todos los registros en base de datos que guardan la
     * ubicaci&oacute;n de los documentos adjuntos para un n&uacute;mero de
     * control.
     *
     * @param numeroControl N&uacte;mero de tr&aacute;mite.
     * @return Todos los registros en base de datos que guardan la
     * ubicaci&oacute;n de los documentos adjuntos para un n&uacute;mero de
     * control.
     * @throws DAOException Error durante tiempo de ejecuci&oacute;n
     */
    public List<ArchivoHistoricoDto> listarDatosParaMoverRegistrosDeFileSystem(String numeroControl) throws DAOException {
        List<ArchivoHistoricoDto> lista = new ArrayList<ArchivoHistoricoDto>();
        for (String query : Utils.listarQueries()) {
            lista.addAll(consultasHistoricoDao.consultasParaHistorico(query, numeroControl));
        }
        return lista;
    }

    /**
     * Actualiza la URL de todos los documentos que fueron movidos dentro del
     * fileSystem de todas las tablas donde se ten&iacute;a registro.
     *
     * @param puntoDeMontaje String que indica la carpeta donde est&aacute;
     * instalado el filesystem
     * @param archivoHistorico lista de objetos con toda la informaci&oacute;n
     * necesaria para acutalizar datos.
     * @throws DAOException Error durante tiempo de ejecuci&oacute;n
     */
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW,
            rollbackFor = {Exception.class})
    @Override
    public void actualizarURLs(String puntoDeMontaje, List<ArchivoHistoricoDto> archivoHistorico,int idFileSystem, String numcontrol, int exito, String causa,Object idSeguimiento) throws DAOException {
        String sql = null;
        String nuevaUrl = null;
        for (ArchivoHistoricoDto archivoHistoricoDto : archivoHistorico) {
            if (archivoHistoricoDto.getCampo3() != null) {
                sql
                        = "UPDATE " + archivoHistoricoDto.getTabla() + " SET URL =  ? WHERE " + archivoHistoricoDto.getCampo1() + " = '"
                        + archivoHistoricoDto.getId1() + "' AND " + archivoHistoricoDto.getCampo2() + " =  " + "'"
                        + archivoHistoricoDto.getId2() + "' AND " + archivoHistoricoDto.getCampo3() + " =  " + "'"
                        + archivoHistoricoDto.getId3() + "'";

            } else if (archivoHistoricoDto.getCampo2() != null) {
                sql
                        = "UPDATE " + archivoHistoricoDto.getTabla() + " SET URL =  ? WHERE " + archivoHistoricoDto.getCampo1() + " = '"
                        + archivoHistoricoDto.getId1() + "' AND " + archivoHistoricoDto.getCampo2() + " = " + "'"
                        + archivoHistoricoDto.getId2() + "'";

            }else {
                sql = "UPDATE " + archivoHistoricoDto.getTabla() + " SET URL = ? WHERE "
                        + archivoHistoricoDto.getCampo1() + " = "
                        + "'" + archivoHistoricoDto.getId1() + "'";
            }
            nuevaUrl = archivoHistoricoDto.getUrl().replace(Utils.obtenerPuntoDeMontajeActual(archivoHistoricoDto.getUrl()), puntoDeMontaje);
            consultasHistoricoDAO.actualizarURL(sql, nuevaUrl);
        }
        insertarRegistroEnBitacora(idFileSystem, numcontrol, exito, causa,idSeguimiento);
    }

    /**
     * Inserta en bit&aacute;cora los los n&uacute;meros de control que se
     * mueven de un fileSystem a otro. En caso de fallo; registra la causa por
     * la cual no se pudo mover el documento -ya sea por BD o por DD-
     *
     * @param idFileSystem Identificador del fileSystem sobre el cual se
     * est&aacute; haciendo el copiado.
     * @param numcontrol Identificador cuyo padre es la tabla de DYCP_SERVICIO.
     * @param exito 1 en el caso de que se haya actualizado correctamente. 0 en
     * caso contrario.
     * @param causa Motivo por el cual no se mueve un registro en BD.
     * @throws DAOException Error durante tiempo de ejecuci&oacute;n.
     */
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED,
            rollbackFor = {Exception.class})
    @Override
    public void insertarRegistroEnBitacora(int idFileSystem, String numcontrol, int exito, String causa,Object idSeguimiento) throws DAOException {
        DycbSeguimientoFSVO objetoSeguimiento = new DycbSeguimientoFSVO();
        objetoSeguimiento.setIdFileSystem(idFileSystem);
        objetoSeguimiento.setNumControl(numcontrol);
        objetoSeguimiento.setExito(exito);

        try {
            Integer secuencia;
            if(idSeguimiento==null){
            secuencia = dycbSeguimientoFSDAO.consultarNoDeSecuencia();
            objetoSeguimiento.setIdFSSeguimiento(secuencia);
            dycbSeguimientoFSDAO.insertar(objetoSeguimiento);
            }else{
            secuencia=Integer.valueOf(idSeguimiento.toString());
            objetoSeguimiento.setIdFSSeguimiento(secuencia);
            dycbSeguimientoFSDAO.update(objetoSeguimiento);
            }
            if (exito == 0 || exito == CUATRO) {
                if(idSeguimiento==null ){
                insertarCausa(secuencia,causa);
                }else{
                int idEntero=Integer.valueOf(idSeguimiento.toString());
                int existeCausa=dycbCausaFalloFSDAO.select(idEntero);
                if(existeCausa>0){
                    DycbCausaFalloFSVO objetoCausa = new DycbCausaFalloFSVO();
                    objetoCausa.setIdFSSeguimiento(idEntero);
                    objetoCausa.setCausa(causa.length()>ConstantesDyCNumerico.VALOR_500?causa.substring(0, ConstantesDyCNumerico.VALOR_500):causa);

                    dycbCausaFalloFSDAO.update(objetoCausa); 
                 }else{
                     insertarCausa(idEntero,causa);
                }
                }
            }
        } catch (Exception e) {
            throw new DAOException(new Object[]{idFileSystem, numcontrol, exito, causa}, "insertarRegistroEnBitacora", e);
        }
    }
    private void insertarCausa(int secuencia,String causa) throws DAOException {
        DycbCausaFalloFSVO objetoCausa = new DycbCausaFalloFSVO();
                objetoCausa.setIdFSSeguimiento(secuencia);
                objetoCausa.setCausa(causa.length()>ConstantesDyCNumerico.VALOR_500?causa.substring(0, ConstantesDyCNumerico.VALOR_500):causa);

                dycbCausaFalloFSDAO.insertar(objetoCausa);
    }
}
