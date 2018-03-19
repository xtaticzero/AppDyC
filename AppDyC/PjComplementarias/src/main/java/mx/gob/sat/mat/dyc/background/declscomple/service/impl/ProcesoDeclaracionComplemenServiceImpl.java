package mx.gob.sat.mat.dyc.background.declscomple.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import mx.gob.sat.mat.dyc.background.declscomple.dao.ProcesoDeclaracionComplemenDAO;
import mx.gob.sat.mat.dyc.background.declscomple.service.ProcesoDeclaracionComplemenService;
import mx.gob.sat.mat.dyc.background.declscomple.vo.DeclaracionComplementariaVO;
import mx.gob.sat.siat.dyc.controlsaldos.service.ProcesarDeclaracionCompBussinesDelInterface;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;

import mx.gob.sat.siat.dyc.util.common.SIATException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "procesoDeclaracionComplemenService")
public class ProcesoDeclaracionComplemenServiceImpl implements ProcesoDeclaracionComplemenService
{
    @Autowired
    private ProcesoDeclaracionComplemenDAO daoProcesoDeclaracionComplemen;

    @Autowired
    private ProcesarDeclaracionCompBussinesDelInterface delegate;

    private Logger log = Logger.getLogger(ProcesoDeclaracionComplemenServiceImpl.class);

    @Override
    @Transactional
    public void selectXDeclaracionComplemen() throws SIATException
    {
        log.debug("entra a selectXDeclaracionComplemen()");
        List<DeclaracionComplementariaVO> declsComp =
            daoProcesoDeclaracionComplemen.selectXDeclaracionComplemen();
        log.info("declsComp.size()"+declsComp.size());
        if (!declsComp.isEmpty())
        {
            for (Iterator itDeclsComp = declsComp.listIterator(); itDeclsComp.hasNext(); ) 
            {
                DeclaracionComplementariaVO voDeclaracionComp = (DeclaracionComplementariaVO)itDeclsComp.next();
                DycpCompensacionDTO dtoComp = voDeclaracionComp.getDycpCompensacionDTO();
                Map<String, Object> parametros = new HashMap<String, Object>();
                parametros.put("rfc",
                               dtoComp.getDycpServicioDTO().getRfcContribuyente().trim());
                parametros.put("idConcepto",
                               dtoComp.getDyctSaldoIcepOrigenDTO().getDyccConceptoDTO().getIdConcepto());
                parametros.put("idEjercicio",
                               dtoComp.getDyctSaldoIcepOrigenDTO().getDyccEjercicioDTO().getIdEjercicio());
                parametros.put("idPeriodo",
                               dtoComp.getDyctSaldoIcepOrigenDTO().getDyccPeriodoDTO().getIdPeriodo());

                DyctSaldoIcepDTO saldoIcep = delegate.obtenerIcep(parametros);

                if (saldoIcep != null) {

                    Map<String, Object> paramsAfectacionSaldos = new HashMap<String, Object>();
                    paramsAfectacionSaldos.put("numOperacion", dtoComp.getNumOperacionDec());
                    paramsAfectacionSaldos.put("fechaPresentacion", dtoComp.getFechaPresentaDec());

                    Double saldoAFavor = voDeclaracionComp.getSaldoAFavor() > 0 ? voDeclaracionComp.getSaldoAFavor() : voDeclaracionComp.getCantidadAFavor();
                    paramsAfectacionSaldos.put("saldoAFavor", new BigDecimal(saldoAFavor));
                    paramsAfectacionSaldos.put("saldoIcep", saldoIcep);

                    delegate.afectarSaldosXDeclaComplementaria(paramsAfectacionSaldos);

                } /*else {
                    log.info("no se encontro saldo icep para declaracion:"+dtoComp.getDycpServicioDTO().getRfcContribuyente());
                    continue;
                }*/
            }
            log.info("Termino el proceso de Declaraciones Complementarias");
        }
    }

}
