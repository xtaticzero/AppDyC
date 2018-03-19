package mx.gob.sat.siat.dyc.generico.util.common;

import java.text.MessageFormat;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;

import org.apache.log4j.Logger;


public final class Messages {
    private static final Logger LOG = Logger.getLogger(Messages.class.getName());

    /**
     * Constructor privado puesto que es una
     * clase Utils.
     */
    private Messages() {
    }

    public static FacesMessage getMessage(String resourceId, Object[] params) {
        FacesContext context = FacesContext.getCurrentInstance();
        Application app = context.getApplication();
        String appBundle = app.getMessageBundle();
        Locale locale = getLocale(context);
        ClassLoader loader = getClassLoader();
        String summary = getString(appBundle, null, resourceId, locale, loader, params);
        if (summary == null) {
            summary = "¡" + resourceId + "!";
        }
        String detail = getString(appBundle, null, resourceId + "_detail", locale, loader, params);
        return new FacesMessage(summary, detail);
    }

    public static String getString(String resourceId, Object[] params) {
        FacesContext context = FacesContext.getCurrentInstance();
        Application app = context.getApplication();
        String appBundle = app.getMessageBundle();
        Locale locale = getLocale(context);
        ClassLoader loader = getClassLoader();
        return getString(appBundle, null, resourceId, locale, loader, params);
    }

    public static String getString(String bundle1, String bundle2, String resourceId, Locale locale,
                                   ClassLoader loader, Object[] params) {
        String resource = null;
        ResourceBundle bundle;

        if (bundle1 != null) {
            bundle = ResourceBundle.getBundle(bundle1, locale, loader);
            if (bundle != null) {
                try {
                    resource = bundle.getString(resourceId);
                } catch (MissingResourceException ex) {
                    LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + ex.getMessage());
                }
            }
        }

        if (resource == null) {
            return null;
        }
        if (params == null) {
            return resource;
        }

        MessageFormat formatter = new MessageFormat(resource, locale);
        return formatter.format(params);
    }

    public static Locale getLocale(FacesContext context) {
        Locale locale = null;
        UIViewRoot viewRoot = context.getViewRoot();
        if (viewRoot != null) {
            locale = viewRoot.getLocale();
        }
        if (locale == null) {
            locale = Locale.getDefault();
        }
        return locale;
    }

    public static ClassLoader getClassLoader() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (loader == null) {
            loader = ClassLoader.getSystemClassLoader();
        }
        return loader;
    }
}
