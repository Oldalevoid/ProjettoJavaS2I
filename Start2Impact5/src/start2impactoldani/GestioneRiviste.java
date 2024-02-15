package start2impactoldani;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class Rivista {
    private int id;
    private String nome;
    private String descrizione;
    private String tipoLiga;
    private double prezzo;
    private boolean disponibile;

    public Rivista(int id, String nome, String descrizione, String tipoLiga, double prezzo, boolean disponibile) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.tipoLiga = tipoLiga;
        this.prezzo = prezzo;
        this.disponibile = disponibile;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getTipoLiga() {
        return tipoLiga;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public boolean isDisponibile() {
        return disponibile;
    }

    @Override
    public String toString() {
        return "ID: " + id + "\nNome: " + nome + "\nDescrizione: " + descrizione + "\nTipologia: " + tipoLiga + "\nPrezzo: " + prezzo + "\nDisponibile: " + (disponibile ? "SI" : "NO") + "\n";
    }

    public String toCSV() {
        return id + ";" + nome + ";" + descrizione + ";" + tipoLiga + ";" + prezzo + ";" + (disponibile ? "SI" : "NO") + ";";
    }
}
public class GestioneRiviste {
	private String csvFilePath = "/Users/oldale98/Desktop/ProjettoJava/riviste (4).csv"; // Percorso del file CSV
    private ArrayList<Rivista> riviste = leggiRivisteDaFile(csvFilePath);
    
    public ArrayList<Rivista> getRiviste() {
        return riviste;
    }


	
    public static void main(String[] args) {
    	
    	    String csvFilePath = "/Users/oldale98/Desktop/ProjettoJava/riviste (4).csv"; // Percorso del file CSV
    	    ArrayList<Rivista> riviste = leggiRivisteDaFile(csvFilePath);

    	    // Visualizza le riviste
    	    rivisteView(riviste);

    	    // Passa l'elenco delle riviste al menu di gestione degli abbonamenti
    	    
    	
    }

    public static ArrayList<Rivista> leggiRivisteDaFile(String csvFile) {
        String line;
        String cvsSplitBy = ";";
        ArrayList<Rivista> riviste = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine(); // Salta la prima riga (intestazioni delle colonne)
            
            while ((line = br.readLine()) != null) {
                String[] data = line.split(cvsSplitBy);
                int id = Integer.parseInt(data[0]);
                String nome = data[1];
                String descrizione = data[2];
                String tipoLiga = data[3];
                double prezzo = Double.parseDouble(data[4].replaceAll("[^\\d.]", "")); // Rimuovi i caratteri non numerici
                boolean disponibile = data[5].equalsIgnoreCase("SI");
                riviste.add(new Rivista(id, nome, descrizione, tipoLiga, prezzo, disponibile));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return riviste;
    }

    public static void rivisteView(ArrayList<Rivista> riviste) {
        for (Rivista rivista : riviste) {
            System.out.println(rivista.toString());
        }
    }

    public static void esportaRivisteInFile(ArrayList<Rivista> riviste, String outputFile) {
        try (FileWriter writer = new FileWriter(outputFile)) {
            for (Rivista rivista : riviste) {
                writer.write(rivista.toCSV() + "\n");
            }
            System.out.println("Le riviste sono state esportate correttamente in " + outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
