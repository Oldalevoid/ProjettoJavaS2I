package start2impactoldani;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Menu2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Rivista> riviste = GestioneRiviste.leggiRivisteDaFile("/Users/oldale98/Desktop/ProjettoJava/riviste (4).csv");
       
        List <Utente> utenti = GestioneUtenti.leggiUtentiDaCSV();

		
		System.out.println("Benvenuto!");
		
		System.out.println();
		
		System.out.println("Premi M per accedere al menu");
		
		 String input = scanner.nextLine();
	        while (!input.equalsIgnoreCase("M")) {
	            System.out.println("Non è un valore valido!");
	            System.out.println("Premi M per accedere al menu");
	            input = scanner.nextLine(); // Leggi nuovamente l'input
	        }
        
        
        int scelta = -1;
        while (scelta != 0) {
            System.out.println("Benvenuto nel Menu di Controllo:");
            System.out.println("1. Gestione Utenti");
            System.out.println("2. Gestione Abbonamenti");
    
            System.out.println("3. Esporta elenco Riviste");
            System.out.println("0. Esci");

            System.out.print("Scelta: ");
            if (scanner.hasNextInt()) {
                scelta = scanner.nextInt();
                scanner.nextLine(); // Consuma il newline

                switch (scelta) {
                    case 1:
                        GestioneUtenti.menuGestioneUtenti(utenti);
                        
         
                        break;
                    case 2:
                    	GestioneAbbonamenti.menuGestioneAbbonamenti(riviste);
                        break;
                 
                    case 3:
                        GestioneRiviste.esportaRivisteInFile(riviste, "/Users/oldale98/Desktop/ProjettoJava/riviste_esportate.csv");
                        break;
                    case 0:
                        System.out.println("Uscita dal Menù Gestione Utenti.");
                        scelta = 0; // Imposta scelta a 0 per uscire dal ciclo
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
    }
}
