/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.dyc.ws.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Ing. Emmanuel Estrada Gonzalez
 */
public final class DateLoggerUtil {

    private static final String PATTERN = "dd-MM-yyyy HH:mm:ss.SSS";

    private DateLoggerUtil() {
    }

    public static String getTimeToExcecution() {
        return "[.:".concat((new SimpleDateFormat(PATTERN,new java.util.Locale("es","MX"))).format(new Date()).concat(":.]"));
    }
}
