package it.unica.pr2.progetto2015.g48958_49008.complessa;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

/***
 * La classe Table rappresenta la tabella su cui vengono applicate le funzioni della categoria Database
 */
public class Table {
    private String[][] values;
    private String[] header;
    private Set<Filter> filters;
    
    
    /***
     * @param table Matrice di stringhe che rappresenta la tabella 
     */
    public Table(String[][] table) {
        header = table[0];
        values = Arrays.copyOfRange(table, 1, table.length);
        filters = new HashSet<>();	
    }
    
    /***
     * @param field campo della tabella su cui operare la selezione
     * @return i valori del campo field per le righe che rispettano le condizioni imposte
     * dai filtri
     */
    public List<String> select(String field) {
        List<String> result = new ArrayList<>();
        int fieldIndex = getColumnIndex(field);

        for (String[] row : values) {
            if (isValid(row)) {
                result.add(row[fieldIndex]);
            } 
        }

        return result;
    }

    private boolean isValid(String[] row) {
        for (Filter filter : filters) {
            if (!filter.matches(row)) {
                return false;
            }
        }

        return true;
    }
    
    /***
     * Aggiunge un filtro alla tabella
     * @param field nome del campo a cui il filtro si riferisce
     * @param expression condizione imposta dal filtro
     */
    public void addFilter(String field, String expression) {
        filters.add( new Filter(getColumnIndex(field), expression) );
    }

    public void removeFilters() {
        filters.clear();
    }

    private int getColumnIndex(String field) {
        for (int i = 0; i < header.length; i++) {
            if (header[i].trim().equalsIgnoreCase(field.trim())) {
                return i;
            }
        }
        throw new UnknownFieldException(field);
    }
}

class UnknownFieldException extends RuntimeException {
    UnknownFieldException() {
        super();	
    }

    UnknownFieldException(String message) {
        super(message);
    }
}
