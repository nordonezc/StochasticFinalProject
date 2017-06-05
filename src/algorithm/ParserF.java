/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import org.leibnizcenter.cfg.earleyparser.Parser;
import org.leibnizcenter.cfg.grammar.Grammar;

/**
 *
 * @author itbd
 */
public class ParserF<T> extends Parser {
    
    public ParserF(GrammarF grammar) {
        super(grammar);
    }
    
    public static GrammarF conv(Grammar g){
        return (GrammarF) g;
    }
    
    
    
}
