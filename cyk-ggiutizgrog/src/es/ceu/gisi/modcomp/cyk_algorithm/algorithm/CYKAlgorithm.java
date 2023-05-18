package es.ceu.gisi.modcomp.cyk_algorithm.algorithm;

import es.ceu.gisi.modcomp.cyk_algorithm.algorithm.exceptions.CYKAlgorithmException;
import es.ceu.gisi.modcomp.cyk_algorithm.algorithm.interfaces.CYKAlgorithmInterface;
import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;
/**
 * Esta clase contiene la implementación de la interfaz CYKAlgorithmInterface
 * que establece los métodos necesarios para el correcto funcionamiento del
 * proyecto de programación de la asignatura Modelos de Computación.
 *
 * @author Sergio Saugar García <sergio.saugargarcia@ceu.es>
 */
public class CYKAlgorithm implements CYKAlgorithmInterface {
    // Aquí uso la interfaz Set y clase HashSet para inicializar un conjunto
    // vacío donde iremos añadiendo los símbolos no terminales.
    private HashSet<Character> conjuntoNoTerminales = new HashSet<>();
    // Ahora haré lo mismo pero para los elementos terminales
    private HashSet<Character> conjuntoTerminales = new HashSet<>();
    // Aquí declaro la variable para donde se guardará el elemento no terminal
    // que se considerará como el axioma de la gramática.
    char axioma;
    @Override
    /**
     * Método que añade los elementos no terminales de la gramática.
     *
     * @param nonterminal Por ejemplo, 'S'
     * @throws CYKAlgorithmException Si el elemento no es una letra mayúscula.
     */

    // En este método tenemos que pasar como parámetro un caracter no-terminal
    // Que equivale a decir que el parámetro de este método solamente puede ser
    // una letra masyúscula. Para ello voy a usar el método
    // Character isIpperCase().

    // Todos los elementos no terminales los almacenaré en un conjunto Set usando
     // un Set y HashSet.


    public void addNonTerminal(char nonterminal) throws CYKAlgorithmException {
        // Ver si funciona el último esto que he hecho
        
//        if(conjuntoNoTerminales.contains(nonterminal)){
//            throw new CYKAlgorithmException();
//        }
//        if(!conjuntoNoTerminales.contains(nonterminal)){
//            conjuntoNoTerminales.add();
//        } else{
//            conjuntoNoTerminales.add(nonterminal);
//        }
        
//        if(conjuntoNoTerminales.contains(nonterminal)){
//            throw new CYKAlgorithmException();
//        } else if(!conjuntoNoTerminales.contains(nonterminal)){
//            throw new CYKAlgorithmException();
//        } else{
//            conjuntoNoTerminales.add(nonterminal);
//        }
        if(Character.isUpperCase(nonterminal) && !conjuntoNoTerminales.contains(nonterminal)){
            conjuntoNoTerminales.add(nonterminal);
            productions.put(nonterminal, new HashSet<>());
        } else{
            throw new CYKAlgorithmException();
        }
    }

    @Override
    /**
     * Método que añade los elementos terminales de la gramática.
     *
     * @param terminal Por ejemplo, 'a'
     * @throws CYKAlgorithmException Si el elemento no es una letra minúscula.
     */

    // En este método haremos algo parecido al método de arriba, pero añadiremos
    // los elementos terminales en su conjunto correspondiente.
    public void addTerminal(char terminal) throws CYKAlgorithmException {
        if(Character.isLowerCase(terminal) && !conjuntoTerminales.contains(terminal)){
            conjuntoTerminales.add(terminal);
        } else{
            throw new CYKAlgorithmException();
        }
    }

    @Override
    /**
     * Método que indica, de los elementos no terminales, cuál es el axioma de
     * la gramática.
     *
     * @param nonterminal Por ejemplo, 'S'
     * @throws CYKAlgorithmException Si el elemento insertado no forma parte del
     * conjunto de elementos no terminales.
     */
    public void setStartSymbol(char nonterminal) throws CYKAlgorithmException {
        if(conjuntoNoTerminales.contains(nonterminal)){
            axioma = nonterminal;
        } else{
           throw new CYKAlgorithmException();
        }

    }

    //@override --> Por alguna razón poniendo el override aquí me daba un error
    // Así que lo he movido abajo
    /**
     * Método utilizado para construir la gramática. Admite producciones en FNC,
     * es decir de tipo A::=BC o A::=a
     *
     * @param nonterminal A
     * @param production "BC" o "a"
     * @throws CYKAlgorithmException Si la producción no se ajusta a FNC o está
     * compuesta por elementos (terminales o no terminales) no definidos
     * previamente.
     */

    // Para este método lo que conviene usar es un HashMap, porque el HashMap
    // es una estructura de datos que implementa el concepto de "Key-Value Pair".
    // Por lo que, a cada símbolo no terminal, le podemos asociar un String de dos elementos no terminales
    // o de un solo elemento terminal para respeter la forma normal de Chomsky
    // y así definir las derivaciones de la gramática.
    HashMap<Character, HashSet<String>> productions = new HashMap<>();
    // También tiene sentido generar un conjunto de producciones donde se guardarán las reglas de la gramática en forma de cadenas o Strings.
    // HashSet<String> conjuntoProducciones = new HashSet<>();
    @Override
    // He movido el override aquí abajo
    public void addProduction(char nonterminal, String production) throws CYKAlgorithmException {
    // HashSet<String> conjuntoProducciones = new HashSet<>();
    
    if(!conjuntoNoTerminales.contains(nonterminal)){
        throw new CYKAlgorithmException();
    }
    
    for(char caracter: production.toCharArray()){
        if(Character.isLowerCase(caracter) && !conjuntoTerminales.contains(caracter)){
            throw new CYKAlgorithmException();
        } else if(Character.isUpperCase(caracter) && !conjuntoNoTerminales.contains(caracter)){
            throw new CYKAlgorithmException();
        }
    
    }
//     char charPosition1 = production.charAt(0);
//     char charPosition2 = production.charAt(1);

    if(production.length() == 2 && Character.isUpperCase(production.charAt(0)) && Character.isUpperCase(production.charAt(1))){
        HashSet<String> produccionNoTerminal = productions.get(nonterminal);
        if(!produccionNoTerminal.contains(production)){
            produccionNoTerminal.add(production);
        } else{
            throw new CYKAlgorithmException();
        } 
    } else if(production.length() == 1 && Character.isLowerCase(production.charAt(0))){
        productions.get(nonterminal).add(production);
    } else{
        throw new CYKAlgorithmException();
    }
}
    
    @Override
    /**
     * Método que indica si una palabra pertenece al lenguaje generado por la
     * gramática que se ha introducido.
     *
     * @param word La palabra a verificar, tiene que estar formada sólo por
     * elementos no terminales.
     * @return TRUE si la palabra pertenece, FALSE en caso contrario
     * @throws CYKAlgorithmException Si la palabra proporcionada no está formada
     * sólo por terminales, si está formada por terminales que no pertenecen al
     * conjunto de terminales definido para la gramática introducida, si la
     * gramática es vacía o si el autómata carece de axioma.
     */
    
    public boolean isDerived(String word) throws CYKAlgorithmException{
        // Aquí tenemos que aplicar el algoritmo CYK. Primero creamos una matriz del tamaño de la palabra insertada como parámetro 
        // Para poder empezar a aplicar el algoritmo dada una reglas para la gramática.
        
        int longitudPalabra = word.length();
        HashSet<Character>[][] matrix = new HashSet[longitudPalabra][longitudPalabra];
       
        // Primero hay que verificar si la palabra 'word' insertada como parámetro no está vacía
        if(word.isEmpty()){
            throw new CYKAlgorithmException();
        }
        
        // Luego tenemos que verificar si la gramática está vacía-
         if(conjuntoNoTerminales.isEmpty()){
             throw new CYKAlgorithmException();
         }
        // Si el propio autómata no contiene el axioma para la producción.
        if(axioma == '\0' || Character.isWhitespace(axioma)){
        throw new CYKAlgorithmException();
        }
        //System.out.println(conjuntoTerminales.toString());
        // Hay que verificar si la palabra 'word' contiene elementos no terminales, y en caso de que las tenga, lanzar una excepción:
        for(int i = 0; i < longitudPalabra; i++){
            if(!conjuntoTerminales.contains(word.charAt(i))){
                throw new CYKAlgorithmException();
            }
        }
        // Aquí copiamos todas las producciones contenidas dentro del HashMap 'productions' para guardar cada regla de la producción para cada elemento no terminal correspondiente
        HashMap<Character, HashSet<String>> produccionesParaNoTerminales = new HashMap<>();
        for(char noTerminal: conjuntoNoTerminales){
            produccionesParaNoTerminales.put(noTerminal, new HashSet<>());
        }
        // Guardamos cada regla con su elemento no terminal
        for(HashMap.Entry<Character, HashSet<String>> entry: productions.entrySet()){
            char noTerminal = entry.getKey();
            HashSet<String> produciones = entry.getValue();
            produccionesParaNoTerminales.get(noTerminal).addAll(produciones);
        }
        
        for(int i = 0; i < longitudPalabra; i++){
            for(int j= 0; j < longitudPalabra; j++){
                matrix[i][j] = new HashSet<>();
            }  
        }
        // Itera por cada simbolo en la palabra pasada como parámetro al método y para cada no terminal verifica si hay una regla que produce el simbolo actual por el que se está parseando. En caso de que así sea, lo añade a la 
        // matriz que inicialmente estaba vacía.
       for(int i = 0; i < longitudPalabra; i++){
           char simboloTerminal = word.charAt(i);
            for(char noTerminal: conjuntoNoTerminales){
            HashSet<String> producciones = produccionesParaNoTerminales.get(noTerminal);
            
            for(String produccion: producciones){
                if(produccion.length() == 1 && produccion.charAt(0) == simboloTerminal){
                matrix[i][i].add(noTerminal);
                }
            }
        }
    }
       // Aplicación del algirtmo CYK: aquí itera sobre las subcadenas de n en m tal que n <= m, con n = 2 en la primera iteración y llena la matrix/tabla en base a los no terminales que pueden generar esa porción de subcadena
       // para la que sea está iterando en un momento dado.
       for (int longi = 2; longi <= longitudPalabra; longi++) {
        for (int i = 0; i <= longitudPalabra - longi; i++) {
            int j = i + longi - 1;

            for (int k = i; k < j; k++) {
                Set<Character> set1 = matrix[i][k];
                Set<Character> set2 = matrix[k + 1][j];

                for(char noTerminal : conjuntoNoTerminales){
                    Set<String> producciones = produccionesParaNoTerminales.get(noTerminal);

                    for(String produccion : producciones){
                        if (produccion.length() == 2){
                            char simbolo1 = produccion.charAt(0);
                            char simbolo2 = produccion.charAt(1);

                            if(set1.contains(simbolo1) && set2.contains(simbolo2)){
                                matrix[i][j].add(noTerminal);
                            }
                        }
                    }
                }
            }
        }
    }
       // Aquí chequamos que el axioma esté en la celda arriba a la derecha de la matriz 'matrix', si está ahí devuelve true, sino false.
    return matrix[0][longitudPalabra - 1].contains(axioma);
        
}
    
    @Override
    /**
     * Método que, para una palabra, devuelve un String que contiene todas las
     * celdas calculadas por el algoritmo (la visualización debe ser similar al
     * ejemplo proporcionado en el PDF que contiene el paso a paso del
     * algoritmo).
     *
     * @param word La palabra a verificar, tiene que estar formada sólo por
     * elementos no terminales.
     * @return Un String donde se vea la tabla calculada de manera completa,
     * todas las celdas que ha calculado el algoritmo.
     * @throws CYKAlgorithmException Si la palabra proporcionada no está formada
     * sólo por terminales, si está formada por terminales que no pertenecen al
     * conjunto de terminales definido para la gramática introducida, si la
     * gramática es vacía o si el autómata carece de axioma.
     */
    public String algorithmStateToString(String word) throws CYKAlgorithmException {
        // TODO
        int longitudPalabra = word.length();
        HashSet<Character>[][] matrix = new HashSet[longitudPalabra][longitudPalabra];
       
        // Primero hay que verificar si la palabra 'word' insertada como parámetro no está vacía
        if(word.isEmpty()){
            throw new CYKAlgorithmException();
        }
        
        // Luego tenemos que verificar si la gramática está vacía-
         if(conjuntoNoTerminales.isEmpty()){
             throw new CYKAlgorithmException();
         }
        // Si el propio autómata no contiene el axioma para la producción.
        if(axioma == '\0' || Character.isWhitespace(axioma)){
        throw new CYKAlgorithmException();
        }
        //System.out.println(conjuntoTerminales.toString());
        // Hay que verificar si la palabra 'word' contiene elementos no terminales, y en caso de que las tenga, lanzar una excepción:
        // Si comento esto funciona
        for(int i = 0; i < longitudPalabra; i++){
            if(!conjuntoTerminales.contains(word.charAt(i))){
                throw new CYKAlgorithmException();
            }
        }
        HashMap<Character, HashSet<String>> produccionesParaNoTerminales = new HashMap<>();
        for(char noTerminal: conjuntoNoTerminales){
            produccionesParaNoTerminales.put(noTerminal, new HashSet<>());
        }
        
        for(HashMap.Entry<Character, HashSet<String>> entry: productions.entrySet()){
            char noTerminal = entry.getKey();
            HashSet<String> produciones = entry.getValue();
            produccionesParaNoTerminales.get(noTerminal).addAll(produciones);
        }
        
        for(int i = 0; i < longitudPalabra; i++){
            for(int j= 0; j < longitudPalabra; j++){
                matrix[i][j] = new HashSet<>();
            }  
        }
        
       for(int i = 0; i < longitudPalabra; i++){
           char simboloTerminal = word.charAt(i);
            for(char noTerminal: conjuntoNoTerminales){
            HashSet<String> producciones = produccionesParaNoTerminales.get(noTerminal);
            
            for(String produccion: producciones){
                if(produccion.length() == 1 && produccion.charAt(0) == simboloTerminal){
                matrix[i][i].add(noTerminal);
                }
            }
        }
    }
       for (int longi = 2; longi <= longitudPalabra; longi++) {
        for (int i = 0; i <= longitudPalabra - longi; i++) {
            int j = i + longi - 1;

            for(int k = i; k < j; k++){
                Set<Character> set1 = matrix[i][k];
                Set<Character> set2 = matrix[k + 1][j];

                for(char noTerminal : conjuntoNoTerminales){
                    Set<String> producciones = produccionesParaNoTerminales.get(noTerminal);

                    for(String produccion : producciones){
                        if(produccion.length() == 2) {
                            char simbolo1 = produccion.charAt(0);
                            char simbolo2 = produccion.charAt(1);

                            if(set1.contains(simbolo1) && set2.contains(simbolo2)){
                                matrix[i][j].add(noTerminal);
                            }
                        }
                    }
                }
            }
        }
    }
       StringBuilder stb4 = new StringBuilder();
       for(int i = 0; i < longitudPalabra; i++){
           for(int j = 0; j < longitudPalabra; j++){
               
               stb4.append(matrix[i][j]);
               
           }
           stb4.append("\n");
       }
       return stb4.toString();
    }
    

    @Override
    /**
     * Elimina todos los elementos que se han introducido hasta el momento en la
     * gramática (elementos terminales, no terminales, axioma y producciones),
     * dejando el algoritmo listo para volver a insertar una gramática nueva.
     */
    public void removeGrammar() {
        conjuntoTerminales.clear();
        conjuntoNoTerminales.clear();
        productions.clear();
        axioma = '\0';
    }

    @Override
    /**
     * Devuelve un String que representa todas las producciones que han sido
     * agregadas a un elemento no terminal.
     *
     * @param nonterminal
     * @return Devuelve un String donde se indica, el elemento no terminal, el
     * símbolo de producción "::=" y las producciones agregadas separadas, única
     * y exclusivamente por una barra '|' (no incluya ningún espacio). Por
     * ejemplo, si se piden las producciones del elemento 'S', el String de
     * salida podría ser: "S::=AB|BC".
     */
    public String getProductions(char nonterminal) {
        // Usar StringBuilder para luego poder devolverlo como String para indicar el elemento no terminal junto con "::=" separadp por |.
        StringBuilder stb2Producciones = new StringBuilder();
        HashSet<String> produccionesNoTerminales = productions.get(nonterminal);
        
       if(produccionesNoTerminales != null){
            // Insertamos ::= .
            stb2Producciones.append(nonterminal).append("::=");
            for(String produccion: produccionesNoTerminales){
                // Para separar cada produccion generado por los terminales añadimos |.
                stb2Producciones.append(produccion).append("|");
            }
            // Si una produccion ha terminado, tenemos que asegurarnos de que la barra | no sea el último caracter de la cadena.
            if(stb2Producciones.length() > 0){
                stb2Producciones.deleteCharAt(stb2Producciones.length() - 1);
            }
         }
        return stb2Producciones.toString();
    }

    @Override
    /**
     * Devuelve un String con la gramática completa.
     *
     * @return Devuelve el agregado de hacer getProductions sobre todos los
     * elementos no terminales.
     */
    public String getGrammar() {
        StringBuilder stb3GetGrammar = new StringBuilder();
        //  Lo que tenemos que hacer es iterar por cada elemento no terminal en la gramática:
        // Usamos el método keySet() sobre el Map "productions" para obtener un conjunto de las llaves de Map, es decir, el conjunto de no terminales --> los caracteres del Map (HashMap).
        for(char noTerminal: productions.keySet()){
            stb3GetGrammar.append(noTerminal).append("\n");
        }
        return stb3GetGrammar.toString();
    }
}
