package mx.gob.sat.siat.dyc.domain.regsolicitud;

import java.io.Serializable;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.declaraciontemp.DyctContribTempDTO;
import mx.gob.sat.siat.dyc.domain.declaraciontemp.DyctSolicitudTempDTO;
import mx.gob.sat.siat.dyc.util.common.CatalogoTO;
import mx.gob.sat.siat.dyc.vo.ArchivoVO;
import mx.gob.sat.siat.dyc.vo.InformacionSaldoFavorTO;


public class SolicitudDevTempVO implements Serializable {

    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;

    private DyctSolicitudTempDTO solicitudTemp;
    private InformacionSaldoFavorTO solDeclaracionTemp;
    private DyctContribTempDTO solContribTemp;
    private List<CatalogoTO> inconsistTemp;
    private List<ArchivoVO> archivos;

    public SolicitudDevTempVO() {
        super();
    }

    public void setSolicitudTemp(DyctSolicitudTempDTO solicitudTemp) {
        this.solicitudTemp = solicitudTemp;
    }

    public DyctSolicitudTempDTO getSolicitudTemp() {
        return solicitudTemp;
    }

    public void setSolDeclaracionTemp(InformacionSaldoFavorTO solDeclaracionTemp) {
        this.solDeclaracionTemp = solDeclaracionTemp;
    }

    public InformacionSaldoFavorTO getSolDeclaracionTemp() {
        return solDeclaracionTemp;
    }

    public void setSolContribTemp(DyctContribTempDTO solContribTemp) {
        this.solContribTemp = solContribTemp;
    }

    public DyctContribTempDTO getSolContribTemp() {
        return solContribTemp;
    }

    public void setInconsistTemp(List<CatalogoTO> inconsistTemp) {
        this.inconsistTemp = inconsistTemp;
    }

    public List<CatalogoTO> getInconsistTemp() {
        return inconsistTemp;
    }

    public void setArchivos(List<ArchivoVO> archivos) {
        this.archivos = archivos;
    }

    public List<ArchivoVO> getArchivos() {
        return archivos;
    }
}
