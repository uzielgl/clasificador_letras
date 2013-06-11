/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package clasificadorletras

/**
 *
 * @author uzielgl
 */
class InnerNeuron extends Neuron {
    //Estas sólo tienen una entrada
    def public inner;
    //Recibira su valor de entrada
    public InnerNeuron( int inner ){ 
        this.inner = inner;
    }
    
    public String toString(){
        return this.inner;
    }
}

