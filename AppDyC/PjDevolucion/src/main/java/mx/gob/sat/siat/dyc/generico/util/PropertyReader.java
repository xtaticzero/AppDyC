package mx.gob.sat.siat.dyc.generico.util;


import java.io.FileInputStream;
import java.io.IOException;

import java.util.Properties;


public final class PropertyReader {

    private static PropertyReader instance = null;

    private PropertyReader() {
    }

    // creador sincronizado para protegerse de posibles problemas  multi-hilo

    private static synchronized void createInstance() {
        if (instance == null) {
            instance = new PropertyReader();
        }
    }

    public static PropertyReader getInstance() {
        createInstance();
        return instance;
    }

    public static String getData(String key) throws IOException {
        //Reading properties
        Properties props = new Properties();
        FileInputStream fis;
        fis =
 new FileInputStream("/siat/aplicativo/Desarrollo/AppDyC/PjDevoluciones/src/mx/gob/sat/siat/dyc/generico/util/recurso/applicationProperties.xml");
        //loading properites from properties file
        props.loadFromXML(fis);
        return props.getProperty(key);
    }

}
