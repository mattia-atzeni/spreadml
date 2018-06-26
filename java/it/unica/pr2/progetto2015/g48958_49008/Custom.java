package it.unica.pr2.progetto2015.g48958_49008;

import it.unica.pr2.progetto2015.interfacce.SheetFunction;

import weka.core.Instances;
import weka.core.Instance;
import weka.core.DenseInstance;
import weka.core.Attribute;
import weka.classifiers.Classifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Custom implements SheetFunction {
    
    /**
     * Costruisce un classificatore del tipo indicato e lo addestra sul training set
     * @param args lista dei parametri <br/>
     * args[0]: nome del classificatore da addestrare (String) <br/>
     * args[1]: training set (String[][]) <br/>
     * args[2]: set da classificare (String[][]) <br/>
     * args[3] e seguenti (opzionali): informazioni relative agli attributi non numerici <br/>
     * @return i valori previsti per il set da classificare
     */
    @Override
    public Object execute(Object... args) {
        try {	
            Classifier classifier = (Classifier) Class.forName((String) args[0]).newInstance();
            String[][] dataset = (String[][]) args[1];
            String[][] testSet = (String[][]) args[2];
            Map<String, ArrayList<String>> labelsMap = new HashMap<>();

            for (int i = 3; i < args.length; i++) {
                String[] current = (String[]) args[i];
                ArrayList<String> labels = new ArrayList<>();
                for (int j = 1; j < current.length; j++) {
                    labels.add(current[j]);
                }
                if (!labels.isEmpty())
                    labelsMap.put(current[0], labels);
                else labelsMap.put(current[0], null);
            }

            Instances trainingSet = buildDataSet(dataset, labelsMap);

            classifier.buildClassifier(trainingSet);

            return classify(classifier, trainingSet, testSet);

        } catch (ClassNotFoundException | InstantiationException e) {
            return "Impossibile creare il classificatore " + args[0];
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Costruisce un oggetto che rappresenta il dataset
     * @param table tabella che rappresenta il dataset
     * @param labelsMap associa ad ogni attributo non numerico una lista di stringhe contenente i valori che tale
     *                  attributo può assumere. Se la lista è null, l'attributo è interpretato come attributo di 
     *                  tipo stringa
     */
    private Instances buildDataSet(String[][] table, Map<String, ArrayList<String>> labelsMap) {
    	
    	String[] header = table[0];
    	String[][] values = Arrays.copyOfRange(table, 1, table.length);
		
    	ArrayList<Attribute> attributes = new ArrayList<>();
		
        for (String field : header) {
            if (!labelsMap.containsKey(field)) {
                attributes.add(new Attribute(field));
            } else {
                attributes.add(new Attribute(field, labelsMap.get(field)));
            }			
        }
    	
    	Instances dataset = new Instances("dataset", attributes, values.length);
		
        for (String[] row : values) {
            Instance instance = new DenseInstance(header.length);
            instance.setDataset(dataset);
            for (int j = 0; j < header.length; j++) {
                if (labelsMap.containsKey(header[j])) {
                    instance.setValue(j, row[j]);
                } else {
                    instance.setValue(j, Double.parseDouble(row[j]));
                }
            }
            dataset.add(instance);
        }

        dataset.setClassIndex(dataset.numAttributes() - 1);

        return dataset;
    }
    
    /***
     * Applica il classificatore classifier per classificare i dati contenuti nella matrice testSet
     */
    private Object classify(Classifier classifier, Instances dataset, String[][] testSet) {
        Object[][] result = new Object[testSet.length][1];
        Attribute classAttribute = dataset.classAttribute();

        for (int i = 0; i < testSet.length; i++) {
            String[] row = testSet[i];	
            Instance instance = new DenseInstance(row.length);
            instance.setDataset(dataset);
            for (int j = 0; j < row.length; j++) {
                Attribute current = dataset.attribute(j);
                if (current.isNumeric())
                    instance.setValue(j, Double.parseDouble(row[j]));
                else
                    instance.setValue(j, row[j]);
            }

            Double currentResult;
            try {
                currentResult = classifier.classifyInstance(instance);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            if (classAttribute.isNumeric()) {
                result[i][0] = currentResult;
            } else result[i][0] = classAttribute.value(currentResult.intValue());
    	}
    	
    	return result;
    }

    @Override
     public String getCategory() {
    	return "Custom";
    }

    @Override
    public String getHelp() {
    	return "Costruisce un classificatore del tipo indicato e lo addestra sul training set."
                + "\nRestituisce i valori previsti per il set da classificare.";
    }
       
    @Override
    public String getName() {
    	return "Custom";
    }
}
