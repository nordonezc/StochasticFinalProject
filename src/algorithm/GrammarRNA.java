/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

//Credits to https://github.com/digitalheir/java-probabilistic-earley-parser
import gui.Principal;
import org.leibnizcenter.cfg.algebra.semiring.dbl.LogSemiring;
import org.leibnizcenter.cfg.category.terminal.Terminal;
//import org.leibnizcenter.cfg.category.terminal.stringterminal.StringTerminal;
//import org.leibnizcenter.cfg.category.terminal.stringterminal.ExactStringTerminal;
import org.leibnizcenter.cfg.earleyparser.Parser;
//import org.leibnizcenter.cfg.grammar.Grammar;
import org.leibnizcenter.cfg.token.Tokens;
import static java.nio.charset.StandardCharsets.*;
import java.util.ArrayList;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import org.leibnizcenter.cfg.earleyparser.ParseTreeWithScore;

public class GrammarRNA {
    // NonTerminals are just wrappers around a string
	// NonTerminal for RNAChain
    private static final NoTerminal S = Category.nonTerminal("S");
    private static final NoTerminal SA = Category.nonTerminal("SA");
    private static final NoTerminal ST = Category.nonTerminal("ST");
    private static final NoTerminal SC = Category.nonTerminal("SC");
    private static final NoTerminal SG = Category.nonTerminal("SG");
    private static final NoTerminal L1 = Category.nonTerminal("L1");
    private static final NoTerminal L2 = Category.nonTerminal("L2");
    private static final NoTerminal N = Category.nonTerminal("N");
    
    //Example
    private static final NoTerminal NP = Category.nonTerminal("NP");
    private static final NoTerminal VP = Category.nonTerminal("VP");
    private static final NoTerminal TV = Category.nonTerminal("TV");
    private static final NoTerminal Det = Category.nonTerminal("Det");
    private static final NoTerminal Mod = Category.nonTerminal("Mod");

    // Terminal types are realized by implementing the Terminal interface, specifically the function hasCategory. Terminal is a functional interface.
    // Note that tokens can be of multiple terminal types (homographs: "bank" as a noun or "bank" as a verb), so you can use this method to pool many words to a single terminal 
    private static final Terminal<String> transitiveVerb = token -> token.obj.matches("(hit|chased)");
    // Some utility terminal types are pre-defined:

    //Terminal for RNA Chain
    private static final Terminal<String> A = new StringTerminal("A");
    private static final Terminal<String> C = new StringTerminal("C");
    private static final Terminal<String> G = new StringTerminal("G");
    private static final Terminal<String> T = new StringTerminal("T");
    
    //Terminal Example
    private static final Terminal<String> the = new StringTerminal("the");
    private static final Terminal<String> a = new StringTerminal("a");
   // private static final Terminal<String> man = new ExactStringTerminal("man");
   // private static final Terminal<String> stick = new ExactStringTerminal("stick");
   // private static final Terminal<String> with = new ExactStringTerminal("with");
    private static final Terminal<String> man = new StringTerminal("man");
    private static final Terminal<String> stick = new StringTerminal("stick");
    private static final Terminal<String> with = new StringTerminal("with");
    
    //Number of rules
    private static final int NUMBER_OF_RULES = 15;
    private static final String POSIBLE_PAIRS[] = {" A T ", " G C ", " T A ", " C G "};
    private static final String POSIBLE_LETTERS[] = {"A", "C", "T", "G"};
    
    //Variable to determine probability :v
    //private static double probability[] = new double[NUMBER_OF_RULES]; 
    private static double probability[] = {0.25,0.25,0.25,0.25,1,1,1,1,1,1,1,0.25,0.25,0.25,0.25};
    //private static double probability[] = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
    
    /**
     * Gramar RNA Chain
     * Credits to http://www.cs.tau.ac.il/~rshamir/algmb/presentations/SCFG-for-posting.pdf
     */
    private static final GrammarF grammarRNA = new GrammarF.Builder("RNA").setSemiring(LogSemiring.get())
    		.addRule(probability[0],
    				S, 
    				A, ST)
    		.addRule(probability[1],
    				S, 
    				T, SA)
    		.addRule(probability[2],
    				S, 
    				C, SG)
    		.addRule(probability[3],
    				S, 
    				G, SC)
    		.addRule(probability[4],
    				S, 
    				N, L1)
    		.addRule(probability[5],
    				SA, 
    				S, A)
    		.addRule(probability[6],
    				ST, 
    				S, T)
    		.addRule(probability[7],
    				SC, 
    				S, C)
    		.addRule(probability[8],
    				SG, 
    				S, G)
    		.addRule(probability[9],
    				L1, 
    				N, L2)
    		.addRule(probability[10],
    				L2, 
    				N, N)
    		.addRule(probability[11],N, A)
    		.addRule(probability[12],N, C)
    		.addRule(probability[13],N, G)
    		.addRule(probability[14],N, T)
    		.build();
    /*
    //Grammar example
    private static final Grammar grammar = new Grammar.Builder("test")
            .setSemiring(LogSemiring.get()) // If not set, defaults to Log semiring which is probably what you want. The builder takes care of converting probabilties to semiring elements
            .addRule(
                    1.0,   // Probability between 0.0 and 1.0, defaults to 1.0
                    S,     // Left hand side of the rule
                    NP, VP // Right hand side of the rule
            )
            .addRule(
                    NP,
                    Det, N // eg. The man
            )
            .addRule(
                    NP,
                    Det, N, Mod // eg. The man (with a stick)
            )
            .addRule(
                    0.4,
                    VP,
                    TV, NP, Mod // eg. (chased) (the man) (with a stick)
            )
            .addRule(
                    0.6,
                    VP,
                    TV, NP // eg. (chased) (the man with a stick)
            )
            .addRule(Det, a)
            .addRule(Det, the)
            .addRule(N, man)
            .addRule(N, stick)
            .addRule(TV, transitiveVerb)
            .addRule(Mod, with, NP) // eg. with a stick
            .build();
*/
    //Calculate random probabilities
    private static void calculateProbability(){
    	double min = 0;
    	int counter = 0;
    	int positionsToApply[] = {0, 11};
    	// 4 4
    	for (int i=0; i<probability.length; i++) {
    		probability[i] = ThreadLocalRandom.current().nextDouble(0, 1-min);
			if((i == positionsToApply[0] + counter || i == positionsToApply[1] + counter) && counter <4 ){
				min += probability[i]; 
				counter++;
			}
			else{
				System.out.println(min);
				counter = 0; min = 0;
			}

		    System.err.print(probability[i] + " ");
		}
    	
	    System.err.println();
    }
    
    public static ArrayList<ParseTreeWithScore> theBest(String chain, int iterations, int beginSize, int finalSize){
    	Parser<String> parser = new Parser<>(grammarRNA); 	
    	parser.recognize(S, Tokens.tokenize("A T C G"));
    	ArrayList<ParseTreeWithScore> bestTrees = new ArrayList<>();
    	ArrayList<ParseTreeWithScore> currentTrees = new ArrayList<>();
    	double currentProbability = 0;
    	double bestProbability = 0;
    	String iterableChain;
    	Random ran = new Random();
    	int j = iterations;
        while(j >= 0){
            currentTrees.clear();
            iterableChain = chain;
            for(int i=0; i<(finalSize/2 - beginSize/2); i++){
                if(i >= 4){
                        iterableChain = joinCenter(iterableChain, 0);
                }
                else{
                        iterableChain = joinCenter(iterableChain, ran.nextInt(4) + 1);
                }
                currentTrees.add(parser.getViterbiParseWithScore(S, Tokens.tokenize(iterableChain.trim())));  	        
            }
                currentProbability = currentTrees.get(currentTrees.size()-1).score.getProbability();

                if (currentProbability > bestProbability) {
                    bestProbability = currentProbability;
                    bestTrees.clear();
                    for (ParseTreeWithScore parseTreeWithScore : currentTrees) {
                            bestTrees.add(parseTreeWithScore);
                    }
                }
                j--;
        }
        return bestTrees;
    }
    
    /**
     * Insert pair of letters into a chainRNA
     * @param chain Actual chainRNA
     * @param option Option that tells what it gonna insert
     * @return chain + newPair
     */
    public static String joinCenter(String chain, int option){
    	String answer = chain;
    	String answerSplit_1 = answer.substring(0, answer.length()/2);
    	String answerSplit_2 = answer.substring(answer.length()/2, answer.length());
    	switch(option){
    	case 1:
    		answer = answerSplit_1 + POSIBLE_PAIRS[0] + answerSplit_2;
    		break;
    	case 2:
    		answer = answerSplit_1 + POSIBLE_PAIRS[1] + answerSplit_2;
    		break;
    	case 3:
    		answer = answerSplit_1 + POSIBLE_PAIRS[2] + answerSplit_2;
    		break;
    	case 4:
    		answer = answerSplit_1 + POSIBLE_PAIRS[3] + answerSplit_2;
    		break;
    	default:
    		answer = answerSplit_1 + " " + POSIBLE_LETTERS[ThreadLocalRandom.current().nextInt(0, 3)] + " " + POSIBLE_LETTERS[ThreadLocalRandom.current().nextInt(0, 3)] + " " + answerSplit_2;
    	}
    	
    	return answer;
    }
    
    
    public static void main(String[] args) {      
        //Initially pair
         /*String begin = POSIBLE_PAIRS[ThreadLocalRandom.current().nextInt(1, 4)];
        
        //Final size of RNA
        int finalSizeRNA = 12;
        int beginSizeRNA = 2;
        int beginSizeObjetive = 4;
        
        while(beginSizeRNA < beginSizeObjetive){
            begin = joinCenter(begin, ThreadLocalRandom.current().nextInt(1,4));
            beginSizeRNA += 2;
        }
               
        ArrayList<ParseTreeWithScore> winner = theBest(begin, 5, beginSizeObjetive, finalSizeRNA);
        for (ParseTreeWithScore tree : winner) {
            System.out.println(tree);
        }*/
        
        
        //save max probability
        //¿Porque en una cadena de RNA porque las 4 proteinas del centro porque pueden romper el patrón de pares? 
    }
    
}