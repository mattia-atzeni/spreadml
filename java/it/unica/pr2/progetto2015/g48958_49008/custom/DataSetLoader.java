package it.unica.pr2.progetto2015.g48958_49008.custom;

import it.unica.pr2.progetto2015.interfacce.SheetFunction;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.io.File;

public class DataSetLoader implements SheetFunction {
    
    /**
     * Legge un dataset da un file che contiene i valori separati da virgole
     * @param args lista dei parametri <br/>
     * args[0]: nome del file (String) <br/>
     * args[1]: offset (Integer) <br/>
     * args[2]: numero di righe da leggere (Integer) <br/>
     * args[3] (opzionale): Directory in cui si trova il file: se il nome del file è un
     * path relativo, questo parametro è necessario per poter leggere il file dal foglio elettronico  <br/>
     * @return il dataset rappresentato com matrice di stringhe
     */
    @Override
    public Object execute(Object... args) {
        String path = (String) args[0];
        Integer offset = (Integer) args[1];
        Integer length = (Integer) args[2];
        
        if (args.length > 3)
            path = args[3] + "/" + path;

        if (length < 0 || offset < 0) throw new IllegalArgumentException();

        try (Scanner in = new Scanner(new File(path))) {
            String[][] result = null;
            if (in.hasNextLine()) {
            	ArrayList<String> header = new ArrayList<>();
                Scanner lineScanner = new Scanner(in.nextLine());
                lineScanner.useDelimiter(",");
                while (lineScanner.hasNext()) {
                    header.add(lineScanner.next());
                }
                lineScanner.close();
                result = new String[length + 1][header.size()];
                for (int i = 0; i < header.size(); i++) {
                    result[0][i] = header.get(i);
                }

                int offsetCounter = 0;
                while (in.hasNextLine() && offsetCounter < offset) {
                    in.nextLine();
                    offsetCounter++;
                }

                int i = 1;
                while (in.hasNextLine() && i <= length) {
                    lineScanner = new Scanner(in.nextLine());
                    lineScanner.useDelimiter(",");
                    int j = 0;
                    while (lineScanner.hasNext()) {
                        result[i][j] = lineScanner.next();
                        j++;
                    }
                    i++;
                    lineScanner.close();
                }	
            }		
            return result;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
	
    @Override
    public String getCategory() {
    	return "Custom";
    }

    @Override
    public String getHelp() {
    	return "Carica il dataset da un file";
    }
       
    @Override
    public String getName() {
    	return "DSLOAD";
    }	
	
}
