package algorithm;

import org.leibnizcenter.cfg.category.Category;
import org.leibnizcenter.cfg.category.nonterminal.NonTerminal;


//Clase que implementa Category. Sirve para diferenciar claramente los distintos simbolos de la gramática.
public class NoTerminal extends NonTerminal implements Category {
    @SuppressWarnings("WeakerAccess")
    public final String name;

    //Constructor que recibe el nombre de la categoria como gramática. i.e. "A"
    public NoTerminal(String name) {
        super(name);
        if ( name == null ) throw new IllegalArgumentException("Nombre vacio especificado para la categoria del arbol");
        this.name = name;
    }
    
    //Retorna nueva instancia del no terminal
    public static NonTerminal of(String name) {
        return new NonTerminal(name);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NonTerminal that = (NonTerminal) o;

        return name != null ? name.equals(that.name) : that.name == null;

    } 

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return (name.length() == 0) ? "<empty>" : name;
    }
}
