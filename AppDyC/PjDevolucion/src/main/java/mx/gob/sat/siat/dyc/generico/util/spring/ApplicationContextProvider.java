/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.siat.dyc.generico.util.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextProvider implements ApplicationContextAware {

    public static ApplicationContext getApplicationContext() {
        return AppContext.getApplicationContext();
    }

    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        AppContext.setApplicationContext(ac);
    }
}
