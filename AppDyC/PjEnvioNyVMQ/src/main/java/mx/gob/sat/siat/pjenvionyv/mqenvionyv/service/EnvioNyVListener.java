/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.siat.pjenvionyv.mqenvionyv.service;

import javax.jms.Message;

/**
 *
 * @author christian.ventura
 */
public interface EnvioNyVListener {
    void onMessage(Message message);
}
