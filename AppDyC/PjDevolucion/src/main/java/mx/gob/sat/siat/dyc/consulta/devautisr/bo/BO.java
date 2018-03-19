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
public abstract class BO<T> {

    private Rule<T> rule;
    
    public void setRule(Rule<T> rule) {
        this.rule = rule;
    }

    public Rule<T> getRule() {
        return rule;
    }
}
