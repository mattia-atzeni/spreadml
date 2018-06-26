package it.unica.pr2.progetto2015.g48958_49008;

import it.unica.pr2.progetto2015.interfacce.SheetFunction;
import java.util.GregorianCalendar;

public class Semplice implements SheetFunction {

    @Override
    public Object execute(Object... args) {
        Integer days = (Integer) args[0];
        GregorianCalendar date = new GregorianCalendar(1899, 11, 30);
        date.add(GregorianCalendar.DATE, days);
        return date.get(GregorianCalendar.MONTH) + 1;
    }

    @Override
    public final String getCategory() {
        return "Data&Orario";
    }

    @Override
    public final String getHelp() {
        return "Restituisce il mese espresso come numero seriale (1-12).";
    } 
     
    @Override
    public final String getName() {
        return "MONTH";
    }
}

