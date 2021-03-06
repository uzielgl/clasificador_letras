/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package clasificadorletras

/**
 *
 * @author uzielgl
 */
class Perceptron2 {
    
    def alfa = 0.0123;
    
    def String alphabetRaw = "..##......#......#.....#.#....#.#...#####..#...#..#...#.###.#########..#....#.#....#.#....#.#####..#....#.#....#.#....#######...#####.#....##......#......#......#......#.......#....#..####.#####...#...#..#....#.#....#.#....#.#....#.#....#.#...#.#####..#######.#....#.#......#.#....###....#.#....#......#....########...####.....#......#......#......#......#..#...#..#...#...###..###..##.#..#...#.#....##.....##.....#.#....#..#...#...#.###..##...#......#......#.....#.#....#.#...#...#..#####..#...#..#...#.######.#.....##.....##.....#######.#.....##.....##.....#######...###...#...#.#.....##......#......#......#.....#.#...#...###..#####..#....#.#.....##.....##.....##.....##.....##....#.#####..########......#......#......#####..#......#......#......#######.....#......#......#......#......#......#..#...#..#...#...###..#....#.#...#..#..#...#.#....##.....#.#....#..#...#...#..#....#....#......#.....#.#....#.#...#...#..#####.#.....##.....###...########..#....#.#....#.#####..#....#.#....#.#....#.#....#######...###.#.#...###.....##......#......#......#.....#.#...#...###..#####...#...#..#....#.#....#.#....#.#....#.#....#.#...#.#####..#######.#....#.#..#...####...#..#...#......#......#....########....###.....#......#......#......#......#......#..#...#...###..###..##.#...#..#..#...#.#....##.....#.#....#..#...#...#.###..##";
    
    def String alphabetRawTest = "..##.....@#......#.....#.#@...#.#...#####..#.@.#..#...#.#o#.#o#######..o....#.#....#.#@...#.#####..#.@..#.#@...#.#....#######...#####@#..@.##......#@.....o......#......#.......#..@.#..####.#####...#.@.#..#....o.#@...#.#....o.#....#.#....#.#@..#.#####..##o####.#....#.#......#.#....#o#@...#.#....#.....@#....########...##o#.....#......#......#......#......o..#...#..o..@#...###..#o#..o#.#..#...#.#....##.....##.....#.#....#..#@..#...#.##o..##...#......o......#.....#.#....#.o...#...#..###o#.@o...o.@o..@o@######.#.....#o.....##.....#o##o##.#.....##.....##..o..o######...###...#...#.#.....##......#@.@.@.#......#.....o.#...o...###..o####..#....#.#.....##.....##@@...##.....#o.....##....#.o####..o#######......#......#......o###...#......#.@....#.@....o######.....#......#......#......#......#......#..o...#.@o...#.@.o##..o....o.#...#..#..#...#.#....o#.....#.#....#..#...#...#..o....o....#......#.....#.#....#.#...#...#..o#o#o.#.....##.....###@@.########.@#....#.#....#.#####.@#....#.#....#@#....o.#....#######...###.#.#...###.....##......#.....@o......#.....#.#...#...o##..#####...#...#..#....#.#....#.#@@..o.#....#.#....o.#.@.#.#####..#######.#....#.#..#..@o##o...#..#...#......#......#....#o##o###....###.....#@.....#@.....#......o......#......#..#...o...###..o##..#o.#...#..#..#...#.o....##.....#.#....#..#...#...o.o##..##";
    
    def answer = [
        "a" : [ 1, -1, -1, -1, -1, -1, -1],
        "b" : [ -1, 1, -1, -1, -1, -1, -1],
        "c" : [ -1, -1, 1, -1, -1, -1, -1],
        "d" : [ -1, -1, -1, 1, -1, -1, -1],
        "e" : [ -1, -1, -1, -1, 1, -1, -1],
        "j" : [ -1, -1, -1, -1, -1, 1, -1],
        "k" : [ -1, -1, -1, -1, -1, -1, 1]
    ];
    
    def public ArrayList letters = [];
    def public patrones = []; //Tenemos un array de diccionarios de la forma [patron:[], esperado:[]]
    def pesos= [ //Deberan tener los 63 pesos
        "a" : [], 
        "b" : [],
        "c" : [],
        "d" : [],
        "e" : [],
        "j" : [],
        "k" : []
    ]; //Son los pesos, iran cambiando

    public static void main(String[] args){
        Perceptron2 p = new Perceptron2();
        def alfabeto = p.loadAlphabet( p.alphabetRaw );
        def patrones = p.convertToBipolar( alfabeto );
        p.inicializar_pesos();
        
        //******** Entrenamiento ************
        //Recorremos todos los patrones y aplicamos el algoritmo
        def errores_entrenamiento = true;
        def c = 0;
        while( errores_entrenamiento ){
            c++;
            def j=0;
            def errores_patrones = 0;
            def error_acomulado = 0;
            for( Map patron: patrones){
                def i = 0;
                def errores = [];
                
                error_acomulado = 0;
                
                for( String peso: p.pesos.keySet() ) {
                    def fnet = p.fnet( patron.patron, p.pesos[peso] );
                    
                    //Checamos que el valor obtenido sea igual al esperado
                    if( fnet == patron.esperado[i] ){
                        //Creo que no hago nada
                    }else{//Si no son iguales, debemos de aplicar el aprendizaje
                        error_acomulado += patron.esperado[i] - fnet
                        errores.add(1);
                    }   
                    
                }  
                //println error_acomulado;
                if ( ! ( error_acomulado > 0 && error_acomulado < 1 ) ){ //aplicamos aprendizaje
                    i = 0;
                    for( String peso: p.pesos.keySet() ) {
                        def fnet = p.fnet( patron.patron, p.pesos[peso] );
                        

                        //Checamos que el valor obtenido sea igual al esperado
                        if( fnet == patron.esperado[i] ){
                            //Creo que no hago nada
                        }else{//Si no son iguales, debemos de aplicar el aprendizaje
                            def incremento = ( patron.esperado[i] - fnet ) * ( 1 + fnet ) * ( 1 - fnet );
                            p.correcionError( peso, incremento, patron.patron);
                        }   
                        i++;
                    }  
                }
                
                
                if( errores.size() > 0 ){ //si hubo al menos un error, se equivocó en reconocer ese patrón
                    errores_patrones++;
                    //println "se equivocó en  " + j
                }else{
                    //println "no se equivoco en "  + j
                }
                
                //if( j > 2 ) return;
                j++;
            }
            //p.print_pesos();
            print c + " ";
            println "patronees erroneos " +  errores_patrones;
            
            if( errores_patrones == 0)
                break;
            //println p.pesos
        }
        
        //Ya entrenado, con los pesos
        
        println "Pesos ya con el entrenamiento:"
        p.print_pesos();
        /*
        //Hacemos las pruebas
        alfabeto = p.loadAlphabet( p.alphabetRawTest );
        patrones = p.convertToBipolar( alfabeto );
        
        def patrones_reconocidos = p.runTest( patrones );
        println patrones_reconocidos
        */
    }
    
    def runTest( patrones ){
        def j=0;
        //def errores_patrones = 0;
        def patrones_reconocidos = 0;
        for( Map patron: patrones){
            def i = 0;
            def errores = [];
            for( String peso: pesos.keySet() ) {
                def fnet = fnet( patron.patron, pesos[peso] );

                //Checamos que el valor obtenido sea igual al esperado
                if( fnet == patron.esperado[i] ){
                    //Creo que no hago nada
                }else{//Si no son iguales, debemos de aplicar el aprendizaje
                    errores.add(1);
                    //p.correcionError( peso, patron.esperado[i] - fnet, patron.patron);
                }   
                i++;
            }  
            if( errores.size() > 0 ){ //si hubo al menos un error, se equivocó en reconocer ese patrón 
                //errores_patrones++;
                //println "se equivocó en  " + j
            }else{
                patrones_reconocidos++;
                //println "no se equivoco en "  + j
            }
            //if( j > 2 ) return;
            j++;
        }
        
        return patrones_reconocidos;
    }
    
    def print_pesos(){
        for( String p: pesos.keySet() ){
            println pesos[p];
        }
        println "";
    }
   
    
    /** entradas es el patron que se recibio de entrada (los 64 valores)
     * */
    public correcionError( clase, incremento, entradas){
        //print incremento
        //Recorro todos mis pesos de la clase
        for( int x= 0 ; x< pesos[clase].size(); x++){
            pesos[clase][x] += alfa * entradas[x] * incremento;
        }
    }
    
    /** Sacará la net y la f(net)*/
    public fnet( patron, pesos){ 
        def net = 0;
        for( int x=0 ; x < patron.size(); x++){
            net += patron[x] * pesos[x];
        }
        // Aplicamos la fnet
        
        /*if( net > 0 ) return 1;
        else return -1;
        */
       def r = ( ( 1 - Math.exp( -net ) ) / ( 1 + Math.exp( -net ) ) );
       return r
        // /
       
    }
    
    public void inicializar_pesos(){
        for(String s: pesos.keySet()){
            for( int x=0; x < 64 ; x++) pesos[s].add(0);
        }
    }
    
    public convertToBipolar( letters ){
        def patrones = [];
        for( ArrayList l : letters){
            def t;
            def mapped = [];
            for( int x = 0; x < l[0].size(); x++){
                if( l[0][x] == "#") t = 1;
                else if( l[0][x] == "@" ) t = 1;
                else if( l[0][x] == "." ) t = -1;
                else if( l[0][x] == "o" ) t = -1;
                mapped.add( t );
            }
            l[0] = mapped;
            
            patrones.add( [
                patron : [1] + l[0], //Agregamos el lumbral
                esperado : l[1],
                clase : l[2]
            ] );
        }
        
        return patrones;
    }
    
    public loadAlphabet( alphabetRaw ){
        letters = [];
        for( int x = 0 ; x < alphabetRaw.size() ; x+=63){
            letters.add( [ alphabetRaw[x..x+62] ] );
        }
        //Asignamos el valor esperado en la posición 1
        letters[0][1] = answer.a;
        letters[1][1] = answer.b;
        letters[2][1] = answer.c;
        letters[3][1] = answer.d;
        letters[4][1] = answer.e;
        letters[5][1] = answer.j;
        letters[6][1] = answer.k;
        
        letters[7][1] = answer.a;
        letters[8][1] = answer.b;
        letters[9][1] = answer.c;
        letters[10][1] = answer.d;
        letters[11][1] = answer.e;
        letters[12][1] = answer.j;
        letters[13][1] = answer.k;
        
        letters[14][1] = answer.a;
        letters[15][1] = answer.b;
        letters[16][1] = answer.c;
        letters[17][1] = answer.d;
        letters[18][1] = answer.e;
        letters[19][1] = answer.j;
        letters[20][1] = answer.k; 
        
        
        //Asignamos la letra en la posición 2
        letters[0][2] = "a";
        letters[1][2] = "b";
        letters[2][2] = "c";
        letters[3][2] = "d";
        letters[4][2] = "e";
        letters[5][2] = "j";
        letters[6][2] = "k";
        letters[7][2] = "a";
        letters[8][2] = "b";
        letters[9][2] = "c";
        letters[10][2] = "d";
        letters[11][2] = "e";
        letters[12][2] = "j";
        letters[13][2] = "k";
        letters[14][2] = "a";
        letters[15][2] = "b";
        letters[16][2] = "c";
        letters[17][2] = "d";
        letters[18][2] = "e";
        letters[19][2] = "j";
        letters[20][2] = "k"; 
        
        return letters;
    }
    
    
}

