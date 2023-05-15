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
        if(Character.isUpperCase(nonterminal)){
            conjuntoNoTerminales.add(nonterminal);
        } else{
            throw new UnsupportedOperationException("Not supported yet.");
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
        if(Character.isLowerCase(terminal)){
            conjuntoTerminales.add(terminal);
        } else{
            throw new UnsupportedOperationException("El elemento no se agregó porque no es un elemento terminal.");
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
           throw new UnsupportedOperationException("El elemento insterado no es parte del conjunto de no terminales.");
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
    HashSet<String> conjuntoProducciones = new HashSet<>();
    @Override
    // He movido el override aquí abajo
    public void addProduction(char nonterminal, String production) throws CYKAlgorithmException {
    if(production.length() != 2 && production.length() != 1){
        throw new UnsupportedOperationException("Not Supported Yet!");
    }

    if(production.length() == 2){
        char[] elementosNoTerminales = production.toCharArray();
        for(char elementoNoTerminal: elementosNoTerminales){
            if(!conjuntoNoTerminales.contains(elementoNoTerminal)){
                throw new UnsupportedOperationException("Not Supported Yet!");
            } else{
                for(char elementoNoTerminal2: elementosNoTerminales) {
                    String elementoNoTerminal2String = String.valueOf(elementoNoTerminal2);
                    conjuntoProducciones.add(elementoNoTerminal2String);
                    productions.put(nonterminal, conjuntoProducciones);
                }
            }
        }
        productions.put(nonterminal, conjuntoProducciones);
    } else if(production.length() == 1){
        char elementoTerminal = production.charAt(0);
        if(!conjuntoTerminales.contains(elementoTerminal)) {
            throw new UnsupportedOperationException("Not Supported Yet!");
        } else{
            String elementoTerminalString = String.valueOf(elementoTerminal);
            conjuntoProducciones.add(elementoTerminalString);
            productions.put(nonterminal, conjuntoProducciones);
        }
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
        
        int matrixLength = word.length();
        String[][] matrix = new String[matrixLength][matrixLength];
        /*for(int i = matrixLength; i >= 0; i--){
            for(int j = 1; i <= matrixLength; i++){
            matrix[i][j] = ""; // TODO
            }
        }*/
        
        // Primero hay que verificar si la palabra 'word' insertada como parámetro no está vacía
        if(word.isEmpty()){
            throw new UnsupportedOperationException("Not supported yet");
        }
        
        // Luego tenemos que verificar si la gramática está vacía-
         if(conjuntoNoTerminales.isEmpty()){
             throw new UnsupportedOperationException("Not supported yet!");
         }
        // Si el propio autómata no contiene el axioma para la producción.
        if(axioma == '\0' || Character.isWhitespace(axioma)){
        throw new UnsupportedOperationException("Not supported yet!");
        }
        
        // Hay que verificar si la palabra 'word' contiene elementos no terminales, y en caso de que las tenga, lanzar una excepción:
        // TODO
        
        
        
        
        
        return true; // Este return es temporal
    }
    
    // Y también tenemos que ver si el autómata tiene axioma o si carece de él
    

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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    /**
     * Elimina todos los elementos que se han introducido hasta el momento en la
     * gramática (elementos terminales, no terminales, axioma y producciones),
     * dejando el algoritmo listo para volver a insertar una gramática nueva.
     */
    public void removeGrammar() {
        throw new UnsupportedOperationException("Not supported yet.");
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    /**
     * Devuelve un String con la gramática completa.
     *
     * @return Devuelve el agregado de hacer getProductions sobre todos los
     * elementos no terminales.
     */
    public String getGrammar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
