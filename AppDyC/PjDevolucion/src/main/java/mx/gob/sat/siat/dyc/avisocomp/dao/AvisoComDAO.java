package mx.gob.sat.siat.dyc.avisocomp.dao;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;

public interface AvisoComDAO {
    
    DyctSaldoIcepDTO buscarSaldoICEPPorFolio(String rfcContribuyente, String folioAviso);
}
