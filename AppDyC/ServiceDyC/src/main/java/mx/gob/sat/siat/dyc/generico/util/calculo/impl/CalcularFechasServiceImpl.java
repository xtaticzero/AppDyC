/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.generico.util.calculo.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import mx.gob.sat.siat.cobranza.diahabil.service.DiaHabilService;
import mx.gob.sat.siat.dyc.generico.util.calculo.CalcularFechasService;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Federico Chopin Gachuz
 * @date Enero 21, 2014
 *
 *
 */
@Service("calcularFechasService")
@Transactional
public class CalcularFechasServiceImpl implements CalcularFechasService {

    @Autowired(required = false)
    @Qualifier(value = "diaHabilService")
    private DiaHabilService diaHabilService;

    public CalcularFechasServiceImpl() {
        super();
    }

    @Override
    public Date calcularFechaFinal(Date fechaInicial, int dias, int tipoDias) throws SIATException {

        Date fechaMaxima = null;

        if (null == fechaInicial) {

            return null;

        } else if (tipoDias == ConstantesDyCNumerico.VALOR_1) {

            try {
                fechaMaxima = getDiaHabilService().buscarDiaFederalRecaudacion(fechaInicial, dias);
            } catch (Exception e) {
                throw new SIATException(e);
            }
            return fechaMaxima;

        } else if (tipoDias == ConstantesDyCNumerico.VALOR_2) {

            fechaMaxima = agregarDias(fechaInicial, dias);
            return fechaMaxima;

        } else {

            return fechaInicial;

        }
    }

    public Date agregarDias(Date fecha, int dia) {

        Calendar cal = new GregorianCalendar();
        cal.setLenient(false);
        cal.setTime(fecha);

        cal.add(Calendar.DAY_OF_MONTH, dia);

        return cal.getTime();

    }

    @Override
    public boolean isDiaHabil(Date date) {
        return diaHabilService.esHabil(date);
    }

    public void setDiaHabilService(DiaHabilService diaHabilService) {
        this.diaHabilService = diaHabilService;
    }

    public DiaHabilService getDiaHabilService() {
        return diaHabilService;
    }

    @Override
    public Date calculaFechaSugerida(Date fechaInicial, int dias) throws SIATException {
        Date fechaSugerida = null;
        int diaSemana = 0;

        fechaSugerida = agregarDias(fechaInicial, dias);

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(fechaSugerida);

        diaSemana = cal.get(Calendar.DAY_OF_WEEK);

        switch (diaSemana) {
            case ConstantesDyCNumerico.VALOR_1:
                fechaSugerida = agregarDias(fechaInicial, (dias + ConstantesDyCNumerico.VALOR_1));
                break;
            case ConstantesDyCNumerico.VALOR_7:
                fechaSugerida = agregarDias(fechaInicial, (dias + ConstantesDyCNumerico.VALOR_2));
                break;
            default:
                break;
        }

        return fechaSugerida;

    }
}
