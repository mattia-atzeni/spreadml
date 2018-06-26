package it.unica.pr2.progetto2015.g48958_49008;

import it.unica.pr2.progetto2015.interfacce.SheetFunction;
import it.unica.pr2.progetto2015.g48958_49008.complessa.*;

import java.util.List;
import org.apache.commons.math3.stat.descriptive.moment.Variance;

public class Complessa implements SheetFunction {

    @Override
    public Object execute(Object... args) {
        Table table = new Table((String[][]) args[0]);
        String field = ((String) args[1]).trim();
        addAllFilters(table, (String[][]) args[2]);

        double[] values = parseDoubleList(table.select(field));
        Variance variance = new Variance();

        if (values.length > 1)
            return variance.evaluate(values);
        else if (values.length == 1)
            throw new ArithmeticException();
        else return 0;
    }

    @Override
    public final String getCategory() {
        return "Database";
    }

    @Override
    public final String getHelp() {
        return "Restituisce la varianza di tutte le celle di un'area di dati che soddisfano i criteri di ricerca specificati.";
    } 

    @Override
    public final String getName() {
        return "DB.VAR";
    }

    private double[] parseDoubleList(List<String> list) {	
        double[] result = new double[list.size()];
        int i = 0;

        for (String current : list) {
                result[i++] = Double.parseDouble(current);
        }

        return result;
    }

    private void addAllFilters(Table table, String[][] criteria) {
        String[] header = criteria[0];
        String[] values = criteria[criteria.length - 1];
		
	    for (int j = 0; j < header.length; j++) {
	        table.addFilter(header[j], values[j]);
	    }
    }
}
