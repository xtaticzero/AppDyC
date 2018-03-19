package mx.gob.sat.siat.dyc.controlsaldos.service.icep.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.siat.dyc.controlsaldos.util.DeclaracionUtil;

import mx.gob.sat.siat.dyc.controlsaldos.vo.MovimientoSaldoBean;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaraIcepDTO;

import org.apache.log4j.Logger;

import org.springframework.stereotype.Service;

@Service(value = "serviceObtenerMovsDiferidos")
public class ObtenerMovsDiferidosServiceImpl {

    private static final Logger LOG = Logger.getLogger(ObtenerMovsDiferidosServiceImpl.class);

    public List<MovimientoSaldoBean> execute(List<DyctDeclaraIcepDTO> decls) {
        List<MovimientoSaldoBean> movsDiferidosSAF = new ArrayList<MovimientoSaldoBean>();
        if (decls == null || decls.isEmpty()) {
            return movsDiferidosSAF;
        }
        LOG.debug("número de declaraciones ->" + decls.size() + "<-");

        List<DyctDeclaraIcepDTO> decls2 = DeclaracionUtil.obtenerDeclsEfectivas(decls);
        LOG.debug("num de declaracionesEfectivas ->" + decls2.size() + "<-");
        if (decls2.isEmpty()) {
            return movsDiferidosSAF;
        }

        BigDecimal saldoDeclaracion;
        BigDecimal saldoDecActual;
        BigDecimal saldoDeclaracionAnterior = BigDecimal.ZERO;
        BigDecimal saldoDeclaracionAntTemp = BigDecimal.ZERO;
        for (int i = 0; i < decls2.size(); i++) {

            saldoDeclaracion = decls2.get(i).getSaldoAFavor();
            if (i > 0) {
                saldoDeclaracionAnterior = decls2.get(i - 1).getSaldoAFavor();
            }
            saldoDecActual = saldoDeclaracion.subtract(saldoDeclaracionAnterior);
            if (saldoDeclaracionAntTemp.compareTo(BigDecimal.ZERO) <= 0) {
                saldoDecActual = saldoDecActual.add(saldoDeclaracionAntTemp);
            }

            MovimientoSaldoBean mov = new MovimientoSaldoBean();
            mov.setTipo(1);
            mov.setImporte(saldoDecActual.doubleValue());
            mov.setFecha(decls2.get(i).getFechaPresentacion());
            mov.setOrigen(decls2.get(i).getNumOperacion().toString());
            mov.setValidacionDWH(decls2.get(i).getValidacionDWH());
            movsDiferidosSAF.add(mov);

            saldoDeclaracionAntTemp = saldoDecActual;
        }

        if (movsDiferidosSAF.isEmpty()) {
            MovimientoSaldoBean movAux = new MovimientoSaldoBean();
            movAux.setTipo(1);
            movAux.setImporte(0d);
            movAux.setFecha(decls2.get(decls2.size() - 1).getFechaPresentacion());
            movAux.setValidacionDWH(decls2.get(decls2.size() - 1).getValidacionDWH());
            movsDiferidosSAF.add(movAux);
        }

        return movsDiferidosSAF;
    }
}
