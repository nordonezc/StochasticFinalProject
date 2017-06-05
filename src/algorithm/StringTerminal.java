/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import org.leibnizcenter.cfg.token.Token;

import java.util.Locale;
import org.leibnizcenter.cfg.category.terminal.Terminal;

/**
 * Terminal descrito por su nombre. i.e: "A"
 */
public class StringTerminal implements Terminal<String> {
    public final String string;
    @SuppressWarnings("WeakerAccess")
    public final Locale locale;

    public StringTerminal(String s) {
        this.locale = Locale.ROOT;
        this.string = s.toLowerCase(locale);
    }

    public StringTerminal(String s, Locale locale) {
        this.locale = locale;
        this.string = s.toLowerCase(locale);
    }

    @Override
    public boolean hasCategory(Token<String> token) {
        return string.equalsIgnoreCase(token.obj);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StringTerminal that = (StringTerminal) o;

        if (!string.equals(that.string)) return false;
        if (!locale.equals(that.locale)) return false;

        return true;
    }

    @Override
    public String toString() {
        return string;
    }

    @Override
    public int hashCode() {
        int result = string.hashCode();
        result = 31 * result + locale.hashCode();
        return result;
    }
}
