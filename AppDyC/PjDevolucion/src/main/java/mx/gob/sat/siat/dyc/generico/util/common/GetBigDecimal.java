package mx.gob.sat.siat.dyc.generico.util.common;

import java.math.BigDecimal;
import java.math.BigInteger;

import mx.gob.sat.siat.dyc.util.common.SIATException;


public final class GetBigDecimal {

    private GetBigDecimal(){
            throw new AssertionError("Se esta instanciando una clase de utility");
            }
                         
    public static BigDecimal parse(Object value) throws SIATException {
        BigDecimal bdecimal = null;
        if (value != null) {
            if (value instanceof BigDecimal) {
                bdecimal = (BigDecimal)value;
            } else if (value instanceof String) {
                bdecimal = new BigDecimal((String)value);
            } else if (value instanceof BigInteger) {
                bdecimal = new BigDecimal((BigInteger)value);
            } else if (value instanceof Number) {
                bdecimal = new BigDecimal(((Number)value).doubleValue());
            } else {
                throw new SIATException("No es posible convertir el valor [" + value + "] de " + value.getClass() +
                                        "  a BigDecimal");
            }
        }
        return bdecimal;
    }
}
