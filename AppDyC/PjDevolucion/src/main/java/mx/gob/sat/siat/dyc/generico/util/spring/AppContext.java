package mx.gob.sat.siat.dyc.generico.util.spring;

import org.springframework.context.ApplicationContext;

public final class AppContext {

    private static ApplicationContext ctx;

    private AppContext() {
        super();
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        ctx = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return ctx;
    }
}
