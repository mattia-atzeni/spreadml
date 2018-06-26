package it.unica.pr2.progetto2015.interfacce;

/** Implementa una singola funzione del foglio elettronico */
public interface SheetFunction {

    /** 
    Argomenti in input ed output possono essere solo: String, Integer, Long, Double, Character, Boolean e array di questi tipi.
    Ad esempio a runtime si puo' avere, come elementi di args, un Integer ed un Long[], e restituire un Double[];
    */
    Object execute(Object... args) ;

    /** 
    Restituisce la categoria LibreOffice;
    Vedere: https://help.libreoffice.org/Calc/Functions_by_Category/it
    ad esempio, si puo' restituire "Data&Orario" oppure "Foglio elettronico"
    */
    String getCategory();

    /** Informazioni di aiuto */
    String getHelp(); 

    /** 
    Nome della funzione.
    vedere: https://help.libreoffice.org/Calc/Functions_by_Category/it
    ad es. "VAL.DISPARI" 
    */         
    String getName();
}
