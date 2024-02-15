package start2impactoldani;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

class Abbonamento {
	protected int id;
    protected int idRivista;
    protected int idUtente;
    protected String dataInizio;
    protected String dataFine;
    


    public Abbonamento(int id, int idRivista, int idUtente, String dataInizio, String dataFine) {
        this.id = id;
        this.idRivista = idRivista;
        this.idUtente = idUtente;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
    }

    public int getId() {
        return id;
    }

    public int getIdRivista() {
        return idRivista;
    }

    public int getIdUtente() {
        return idUtente;
    }

    public String getDataInizio() {
        return dataInizio;
    }

    public String getDataFine() {
        return dataFine;
    }

    @Override
    public String toString() {
        return "ID Abbonamento: " + id + "\nID Rivista: " + idRivista + "\nID Utente: " + idUtente + "\nData inizio: " + dataInizio + "\nData fine: " + dataFine + "\n";
    }

    public String toCSV() {
        return id + "," + idRivista + "," + idUtente + "," + dataInizio + "," + dataFine;
    }
}

public class GestioneAbbonamenti  {

    private static final String CSV_FILE = "/Users/oldale98/Desktop/ProjettoJava/abbonamenti - abbonamenti (2).csv";
    private static final String CSV_SEPARATOR = ",";
    private GestioneRiviste gestioneRiviste = new GestioneRiviste();

    public static void main(String[] args) {
        // Inizializza l'elenco delle riviste
    	ArrayList<Rivista> riviste = GestioneRiviste.leggiRivisteDaFile("/Users/oldale98/Desktop/ProjettoJava/riviste (4).csv");

        // Chiama il menu di gestione degli abbonamenti passando l'elenco delle riviste
        menuGestioneAbbonamenti(riviste);
    }
    

    public static void menuGestioneAbbonamenti(ArrayList<Rivista> riviste) {
        ArrayList<Abbonamento> abbonamenti = leggiAbbonamentiDaFile();
        Scanner scanner = new Scanner(System.in);

        int scelta = -1;
        while (scelta != 0) {
            System.out.println("Benvenuto nel Menù Gestione Abbonamenti:");
            System.out.println("1. Aggiungi Abbonamento");
            System.out.println("2. Disdici Abbonamento");
            System.out.println("3. Visualizza Abbonamenti Utente");
            System.out.println("0. Esci");

            System.out.print("Scelta: ");
            if (scanner.hasNextInt()) {
                scelta = scanner.nextInt();
                scanner.nextLine(); // Consuma il newline

                switch (scelta) {
                    case 1:
                        Abbonamento nuovoAbbonamento = chiediNuovoAbbonamento(scanner, abbonamenti.size() + 1, riviste);
                        if (nuovoAbbonamento != null) {
                            aggiungiAbbonamento(abbonamenti, nuovoAbbonamento);
                        }
                        break;
                    case 2:
                        System.out.println("Inserisci l'ID dell'abbonamento da disdire:");
                        int idAbbonamentoDaDisdire = scanner.nextInt();
                        scanner.nextLine(); // Consuma il newline
                        disdiciAbbonamento(abbonamenti, idAbbonamentoDaDisdire);
                        break;
                    case 3:
                        System.out.println("Inserisci l'ID dell'utente:");
                        int idUtente = scanner.nextInt();
                        scanner.nextLine(); // Consuma il newline
                        visualizzaAbbonamentiUtente(abbonamenti, riviste, idUtente);
                        break;
                    case 0:
                        System.out.println("Uscita dal Menù Gestione Abbonamenti.");
                        scelta = 0;
                        break;
                    default:
                        System.out.println("Scelta non valida, riprova.");
                        break;
                }
            } else {
                System.out.println("Input non valido. Inserisci un numero intero.");
                scanner.next(); // Consuma l'input non valido
            }
        }
        scanner.close();
        aggiornaCSV(abbonamenti);
        
    }

    private static Abbonamento chiediNuovoAbbonamento(Scanner scanner, int nuovoId, ArrayList<Rivista> riviste) {
        System.out.println("Inserisci l'ID della rivista:");
        int idRivista = scanner.nextInt();
        scanner.nextLine(); // Consuma il newline
        
        // Verifica la disponibilità della rivista
        Rivista rivistaSelezionata = null;
        for (Rivista rivista : riviste) {
            if (rivista.getId() == idRivista && rivista.isDisponibile()) {
                rivistaSelezionata = rivista;
                break;
            }
        }
        
        if (rivistaSelezionata == null) {
            System.out.println("Rivista non disponibile o non esistente. Inserisci un'altra rivista.");
            return null;
        }
        
        System.out.println("Inserisci l'ID dell'utente:");
        int idUtente = scanner.nextInt();
        scanner.nextLine(); // Consuma il newline
        System.out.println("Inserisci la data di inizio (formato gg/mm/aaaa):");
        String dataInizio = scanner.nextLine();
        System.out.println("Inserisci la data di fine (formato gg/mm/aaaa):");
        String dataFine = scanner.nextLine();

        return new Abbonamento(nuovoId, idRivista, idUtente, dataInizio, dataFine);
    }


    static ArrayList<Abbonamento> leggiAbbonamentiDaFile() {
        ArrayList<Abbonamento> abbonamenti = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            // Skip header line
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(CSV_SEPARATOR);
                int id = Integer.parseInt(data[0]);
                int idRivista = Integer.parseInt(data[1]);
                int idUtente = Integer.parseInt(data[2]);
                String dataInizio = data[3];
                String dataFine = data[4];
                Abbonamento abbonamento = new Abbonamento(id, idRivista, idUtente, dataInizio, dataFine);
                abbonamenti.add(abbonamento);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return abbonamenti;
    }

    private static void aggiungiAbbonamento(ArrayList<Abbonamento> abbonamenti, Abbonamento nuovoAbbonamento) {
        abbonamenti.add(nuovoAbbonamento);
    }

    private static void disdiciAbbonamento(ArrayList<Abbonamento> abbonamenti, int idAbbonamentoDaDisdire) {
        abbonamenti.removeIf(abbonamento -> abbonamento.getId() == idAbbonamentoDaDisdire);
    }

    private static void visualizzaAbbonamentiUtente(ArrayList<Abbonamento> abbonamenti, ArrayList<Rivista> riviste, int idUtente) {
        System.out.println("Abbonamenti dell'utente con ID " + idUtente + ":");
        for (Abbonamento abbonamento : abbonamenti) {
            if (abbonamento.getIdUtente() == idUtente) {
                String nomeRivista = "";
                for (Rivista rivista : riviste) {
                    if (rivista.getId() == abbonamento.getIdRivista()) {
                        nomeRivista = rivista.getNome();
                        break;
                    }
                }
                System.out.println(abbonamento.toString() + "Nome Rivista: " + nomeRivista + "\n");
            }
        }
    }

    private static void aggiornaCSV(ArrayList<Abbonamento> abbonamenti) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(CSV_FILE))) {
            pw.println("ID,ID Rivista,ID Utente,Data inizio,Data fine");
            for (Abbonamento abbonamento : abbonamenti) {
                pw.println(abbonamento.toCSV());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
