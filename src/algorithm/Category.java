/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import org.leibnizcenter.cfg.category.terminal.Terminal;
import org.leibnizcenter.cfg.rule.Rule;
import org.leibnizcenter.cfg.token.Token;

import java.util.function.Function;



/**
 * Las categorias en la gramática son las unidades atómicas del arbol gramatical
 * Pueden ser terminales o no terminales
 * De las terminales no se pueden derivar más categorias
 * De las no terminales se pueden continuar derivando
 */
public interface Category {
    /**
     * Special start category for seeding Earley parsers.
     */
    NoTerminal START = new NoTerminal("<start>") {
        /**
         * Overrides {@link Category#equals(Object)} to compare using the
         * <code>==</code> operator (since there is only ever one start
         * category).
         */
        @Override
        public boolean equals(Object obj) {
            return obj instanceof NoTerminal && this == obj;
        }

    };

    /**
     * Gets the terminal status of this category.
     *
     * @return The terminal status specified for this category upon
     * construction.
     */
    static boolean isTerminal(Category c) {
        return c instanceof Terminal;
    }

    /**
     * Creates a new non-terminal category with the specified name.
     *
     * @see Category#terminal(Function)
     */
    static NoTerminal nonTerminal(String name) {
        return new NoTerminal(name);
    }

    /**
     * Creates a new terminal category with the specified name.
     *
     * @see Category#nonTerminal(String)
     */
    static <T> Terminal<T> terminal(Function<Token<T>, Boolean> categoryFunction) {
        if (categoryFunction == null)
            throw new Error("Can not instantiate category with null function. Did you mean to create a null category?");
        return categoryFunction::apply;
    }

    /**
     * Returns the given category
     *
     * @see Category#terminal(Function)
     */
    static <T> Terminal<T> terminal(Terminal<T> terminal) {
        if (terminal == null)
            throw new Error("Can not instantiate category with null function. Did you mean to create a null category?");
        return terminal;
    }
}