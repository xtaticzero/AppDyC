package mx.gob.sat.siat.dyc.controlsaldos.service.icep.impl;


import mx.gob.sat.siat.dyc.controlsaldos.service.icep.CalcularSaldoRemanenteICEPService;
import mx.gob.sat.siat.dyc.controlsaldos.service.icep.ConsultaSaldoIcepService;
import mx.gob.sat.siat.dyc.dao.detalleicep.DyctSaldoIcepDAO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.excepcion.DycDaoExcepcion;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service(value = "consultaSaldoIcepService")
public class ConsultaSaldoIcepServiceImpl implements ConsultaSaldoIcepService
{
    private static final Logger LOG = Logger.getLogger(ConsultaSaldoIcepServiceImpl.class);

    @Autowired
    private DyctSaldoIcepDAO daoSaldoIcep;
    
    @Autowired
    private CalcularSaldoRemanenteICEPService serviceCalcRem;

    @Override
    public DyctSaldoIcepDTO consultaSaldoICEP(DyctSaldoIcepDTO saldoIcep) throws SIATException
    {
        try {
                        
            DyctSaldoIcepDTO saldoIcepN = daoSaldoIcep.encontrar(saldoIcep.getRfc(),
                    saldoIcep.getDyccConceptoDTO(),
                    saldoIcep.getDyccEjercicioDTO(),
                    saldoIcep.getDyccPeriodoDTO(), saldoIcep.getDyccOrigenSaldoDTO().getIdOrigenSaldo());
            
            if(saldoIcepN.getBoId() !=null)
            {
               return null;
            }
            saldoIcepN.setRemanente(serviceCalcRem.execute(saldoIcepN));
            LOG.info("Se encontro el saldoIcep con rfc ->" + saldoIcepN.getRfc() + "; id ->" + saldoIcepN.getIdSaldoIcep() + "; cuyo remanente es ->" + saldoIcepN.getRemanente());
            return saldoIcepN;
        } catch (DycDaoExcepcion ex) {
            LOG.debug("NO Se encontro el saldoIcep con rfc ->" + saldoIcep.getRfc() + "; id ->" + saldoIcep.getIdSaldoIcep(), ex);
            return null;
        }
    }
    
    @Override
    public DyctSaldoIcepDTO encontrarIcep(String rfc, DyccConceptoDTO idConcepto, DyccEjercicioDTO idEjercicio,
            DyccPeriodoDTO idPeriodo,DyccOrigenSaldoDTO origenSaldo){
        return daoSaldoIcep.encontrar(rfc, idConcepto, idEjercicio, idPeriodo, origenSaldo);
    }
}
