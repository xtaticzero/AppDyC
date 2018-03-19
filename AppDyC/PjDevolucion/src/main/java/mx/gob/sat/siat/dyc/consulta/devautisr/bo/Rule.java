/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.dyc.consulta.devautisr.bo;

/**
 *
 * @author Ing. Emmanuel Estrada Gonzalez
 * @param <T>
 */
public interface Rule<T> {

    boolean process(T t);
}
