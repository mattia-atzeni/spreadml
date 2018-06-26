package it.unica.pr2.progetto2015.g48958_49008.complessa;

import java.util.Objects;
import java.util.regex.PatternSyntaxException;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/***
 * La classe Filter è usata per rappresentare un criterio di ricerca per le
 * funzioni di categoria Database
 */

public class Filter {
    private int field;
    private String value;
    private String operator;
    private ScriptEngine engine;
    
    /***
     * @param field il campo del database a cui il filtro fa riferimento
     * @param expression condizione imposta dal filtro
     */
    public Filter(int field, String expression) {
        this.field = field;
        setExpression(expression);
        this.engine = new ScriptEngineManager().getEngineByExtension("js");
    }

    private void setExpression(String expression) {
        int operatorLength = 2;
        if (! (expression == null || expression.equals("")) ) {
            expression = expression.trim();

            if (expression.startsWith(">") || expression.startsWith("<")) {
                if (expression.charAt(1) != '=') {
                        operatorLength = 1;
                }
            } else if (expression.startsWith("=")) {
                expression = "=" + expression;
            } else {
                expression = "==" + expression;
            }

            this.operator = expression.substring(0, operatorLength);
            this.value = expression.substring(operatorLength).trim();
        } else {
            this.operator = "==";
            this.value = ".*";
        }
    }
    /***
     * @param row una riga appartenente alla tabella a cui è applicato il filtro
     * @return true se la riga rispetta la condizione imposta dal filtro, false altrimenti
     */
    public Boolean matches(String[] row) {
        String lValue = row[field].trim().toLowerCase();
        String rValue = value.toLowerCase();
        try {
            try {
                Double.parseDouble(lValue);
                Double.parseDouble(rValue);
            } catch (NumberFormatException e) {
                if (operator.equals("==")) {
                    return lValue.matches(rValue);
                } else {
                    lValue = "\"" + lValue + "\"";
                    rValue = "\"" + rValue + "\"";
                }
            }

            try {
                return (Boolean) engine.eval(lValue + operator + rValue);		
            } catch (ScriptException e) {	
                return lValue.matches(rValue);
            }
        } catch (PatternSyntaxException e) {
            return lValue.equals(rValue);
        }

    }

    @Override
    public int hashCode() {
        final int HASH_MULTIPLIER = 31;
        int hash = 3;
        hash = HASH_MULTIPLIER * hash + this.field;
        hash = HASH_MULTIPLIER * hash + Objects.hashCode(this.value);
        hash = HASH_MULTIPLIER * hash + Objects.hashCode(this.operator);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if ( !(obj instanceof Filter) ) {
            return false;
        }
        Filter other = (Filter) obj;
        
        return this.field == other.field && this.value.equals(other.value) && this.operator.equals(other.operator);
    }
}
