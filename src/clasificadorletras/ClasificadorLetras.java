/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadorletras;

/**
 *
 * @author uzielgl
 */
public class ClasificadorLetras {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Perceptron p = new Perceptron();
        p.loadAlphabet();
        p.convertToBipolar();
        
        //Creamos los perceptrones de la capa de entrada
        
        for(int x = 0; x <= p.letters.size(); x++){
            
        }
    }
}
