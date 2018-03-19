/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.dyc.consulta.devautisr.service.impl;

import java.util.List;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

/**
 *
 * @author Ing. Emmanuel Estrada Gonzalez
 */
public class DefaultHandlerResolver implements HandlerResolver {

    private List<Handler> handlerList;

    @Override
    public List<Handler> getHandlerChain(final PortInfo portInfo) {
        return handlerList;
    }

    public void setHandlerList(final List<Handler> handlerList) {
        this.handlerList = handlerList;
    }
}
