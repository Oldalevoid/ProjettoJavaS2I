package start2impactoldani;

import java.io.*;
import java.util.*;

class Utente {
    protected String id;
    protected String name;
    protected String surname;
    protected String dateOfBirth;
    protected String address;
    protected String idNumber;

    // Il resto del codice rimane invariato...

    public Utente(String id, String name, String surname, String dateOfBirth, String address, String idNumber) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.idNumber = idNumber;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public String getIdNumber() {
        return idNumber;
    }

    @Override
    public String toString() {
        return id + ";" + name + ";" + surname + ";" + dateOfBirth + ";" + address + ";" + idNumber;
    }
}
public class GestioneUtenti  {
	
	
	private static final String CSV_FILE = "/Users/oldale98/Desktop/ProjettoJava/utenti (3).csv";
	private static final String CSV_SEPARATOR = ";";
    private static final List<Utente> utenti = leggiUtentiDaCSV();
   
   

    public static void main(String[] args) {
    	
    menuGestioneUtenti(utenti);
       
    }

    public static void menuGestioneUtenti(List<Utente> utenti) {
              Scanner scanner = new Scanner(System.in);

        int scelta = -1;
        while (scelta != 0) {
            System.out.println("Benvenuto nel Menù Gestione Utenti:");
            System.out.println("1. Aggiungi Utente");
            System.out.println("2. Rimuovi Utente");
            System.out.println("3. Visualizza Lista Utenti");
            System.out.println("0. Esci");

            System.out.print("Scelta: ");
            if (scanner.hasNextInt()) {
                scelta = scanner.nextInt();
                scanner.nextLine(); // Consuma il newline

                switch (scelta) {
                    case 1:
                        Utente nuovoUtente = chiediNuovoUtente(scanner, utenti.size() + 1);
                        aggiungiUtente(utenti, nuovoUtente);
                        break;
                    case 2:
                        System.out.println("Inserisci il nome dell'utente da rimuovere:");
                        String nome = scanner.nextLine();
                        System.out.println("Inserisci il cognome dell'utente da rimuovere:");
                        String cognome = scanner.nextLine();
                        rimuoviUtente(utenti, nome, cognome);
                        break;
                    case 3:
                        visualizzaListaUtenti(utenti);
                        break;
                    case 0:
                        System.out.println("Uscita dal Menù Gestione Utenti.");
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
        aggiornaCSV(utenti);
    }

    private static Utente chiediNuovoUtente(Scanner scanner, int nuovoId) {
        String nome, cognome, dataDiNascita, indirizzo, id;

        do {
            System.out.println("Inserisci il nome:");
            nome = scanner.nextLine().trim();
        } while (nome.isEmpty());

        do {
            System.out.println("Inserisci il cognome:");
            cognome = scanner.nextLine().trim();
        } while (cognome.isEmpty());

        do {
            System.out.println("Inserisci la data di nascita (formato gg/mm/aaaa):");
            dataDiNascita = scanner.nextLine().trim();
        } while (!isValidDateFormat(dataDiNascita));

        System.out.println("Inserisci l'indirizzo:");
        indirizzo = scanner.nextLine();

        do {
            System.out.println("Inserisci il numero di identificazione:");
            id = scanner.nextLine().trim();
        } while (id.isEmpty());

        return new Utente(Integer.toString(nuovoId), nome, cognome, dataDiNascita, indirizzo, id);
    }
    
    private static boolean isValidDateFormat(String date) {
        if (date == null || date.isEmpty()) {
            return false;
        }

        // Verifica la lunghezza della stringa
        if (date.length() != 10) {
            return false;
        }

        // Verifica i separatori
        if (date.charAt(2) != '/' || date.charAt(5) != '/') {
            return false;
        }

        // Estrai giorno, mese e anno
        String[] parts = date.split("/");
        if (parts.length != 3) {
            return false;
        }

        int day, month, year;
        try {
            day = Integer.parseInt(parts[0]);
            month = Integer.parseInt(parts[1]);
            year = Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) {
            return false; // Se una parte non è un numero, la data non è valida
        }

        // Verifica la validità del giorno, del mese e dell'anno
        if (day < 1 || month < 1 || year < 1) {
            return false;
        }
        if (month > 12) {
            return false;
        }
        int maxDayOfMonth;
        switch (month) {
            case 4: case 6: case 9: case 11:
                maxDayOfMonth = 30;
                break;
            case 2:
                maxDayOfMonth = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) ? 29 : 28;
                break;
            default:
                maxDayOfMonth = 31;
                break;
        }
        if (day > maxDayOfMonth) {
            return false;
        }

        // La data è nel formato corretto
        return true;
    }

    static List<Utente> leggiUtentiDaCSV() {
        List<Utente> utenti = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            // Skip header line
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(CSV_SEPARATOR);
                Utente utente = new Utente(data[0], data[1], data[2], data[3], data[4], data[5]);
                utenti.add(utente);
            }
           

        } catch (IOException e) {
            e.printStackTrace();
        }
        return utenti;
    }

    private static void aggiungiUtente(List<Utente> utenti, Utente nuovoUtente) {
        utenti.add(nuovoUtente);
    }

    private static void rimuoviUtente(List<Utente> utenti, String nome, String cognome) {
        utenti.removeIf(utente -> utente.name.equalsIgnoreCase(nome) && utente.surname.equalsIgnoreCase(cognome));
    }

    private static void visualizzaListaUtenti(List<Utente> utenti) {
        System.out.println("Lista Utenti:");
        for (Utente utente : utenti) {
            System.out.println(utente.id + ": " + utente.name + " " + utente.surname);
        }
    }

    private static void aggiornaCSV(List<Utente> utenti) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(CSV_FILE))) {
            pw.println("ID;Nome;Cognome;Data di nascita;Indirizzo;Documento ID;");
            for (Utente utente : utenti) {
                pw.println(utente);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
