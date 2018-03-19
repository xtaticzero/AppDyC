package mx.gob.sat.siat.dyc.trabajo.util;

import java.math.BigDecimal;

import java.util.Date;

import mx.gob.sat.siat.dyc.domain.resolucion.DyccAfectaIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccMovIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDetalleIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;


public final class DyctDetalleICEPUtil {
    
    
    private DyctDetalleICEPUtil() {
        super();
    }
    
    
    public static DyctDetalleIcepDTO crear(Integer idSaldoICEP, double monto, long idTipoMovimiento, int idAfectacion, String numControl) {

        DyctSaldoIcepDTO dyctSaldoIcepDTO = new DyctSaldoIcepDTO();
        dyctSaldoIcepDTO.setIdSaldoIcep      (idSaldoICEP);

        DyccAfectaIcepDTO dyccAfectaIcepDTO = new DyccAfectaIcepDTO();
        dyccAfectaIcepDTO.setIdAfectacion    (idAfectacion);

        DyccMovIcepDTO dyccMovIcepDTO = new DyccMovIcepDTO();
        String idTipoMov = String.valueOf(idTipoMovimiento);
        dyccMovIcepDTO.setIdMovimiento       (Integer.parseInt(idTipoMov));
        dyccMovIcepDTO.setDyccAfectaIcepDTO  (dyccAfectaIcepDTO);

        DyctDetalleIcepDTO dyctDetalleIcepDTO = new DyctDetalleIcepDTO();
        dyctDetalleIcepDTO.setDyctSaldoIcepDTO  (dyctSaldoIcepDTO);
        dyctDetalleIcepDTO.setImporteMovimiento (new BigDecimal(monto));
        dyctDetalleIcepDTO.setFechaMovimiento   (new Date());
        dyctDetalleIcepDTO.setDyccMovIcepDTO    (dyccMovIcepDTO);
        dyctDetalleIcepDTO.setNumControl   (numControl);

        return dyctDetalleIcepDTO;

    }


    public static DyctDetalleIcepDTO crear(DyctDetalleIcepDTO oldDyctDetalleIcepDTO) {

        DyctSaldoIcepDTO dyctSaldoIcepDTO = new DyctSaldoIcepDTO();
        dyctSaldoIcepDTO.setIdSaldoIcep        ( oldDyctDetalleIcepDTO.getDyctSaldoIcepDTO().getIdSaldoIcep());

        DyccAfectaIcepDTO dyccAfectaIcepDTO = new DyccAfectaIcepDTO();
        dyccAfectaIcepDTO.setIdAfectacion      ( oldDyctDetalleIcepDTO.getDyccMovIcepDTO().getDyccAfectaIcepDTO().getIdAfectacion());

        DyccMovIcepDTO dyccMovIcepDTO = new DyccMovIcepDTO();
        dyccMovIcepDTO.setIdMovimiento         ( oldDyctDetalleIcepDTO.getDyccMovIcepDTO().getIdMovimiento());
        dyccMovIcepDTO.setDyccAfectaIcepDTO    ( dyccAfectaIcepDTO);


        DyctDetalleIcepDTO dyctDetalleIcepDTO = new DyctDetalleIcepDTO();
        dyctDetalleIcepDTO.setDyctSaldoIcepDTO  ( dyctSaldoIcepDTO);
        dyctDetalleIcepDTO.setImporteMovimiento ( oldDyctDetalleIcepDTO.getImporteMovimiento());
        dyctDetalleIcepDTO.setFechaMovimiento   ( new Date());
        dyctDetalleIcepDTO.setDyccMovIcepDTO    ( dyccMovIcepDTO);
        dyctDetalleIcepDTO.setNumControl   ( oldDyctDetalleIcepDTO.getNumControl());

        return dyctDetalleIcepDTO;

    }
}
