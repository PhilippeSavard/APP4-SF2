package app;

import electronique.Composant;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class  CircuitApp {

    private static final char fSep = File.separatorChar;
    private static final String pathIn = System.getProperty("user.dir") + fSep + "src" + fSep + "donnees" + fSep + "fichiers_json";

    public static void main(String[] args) {
        CircuitApp Client = new CircuitApp(); // imprime directement le "Client-Side" qui vas géré les interaction client
        Client.executer(); // marche parce que le Client-Side c'est un scanner qui gère les interractions donc on lui demande de déjà l'imprimer.
    }

    private void executer() {
        Scanner scanner = new Scanner(System.in); // création d'un scanner qui interprète le client-side
        CircuitBuilder CircuitGlobale = new CircuitBuilder(); // important parce que prouve la différence entre client side et back-end side.
        File dossier = new File(pathIn); // désigne le dossier où vont être ranger les fichiers JSON.

        boolean pasFini = true; //tant que ce n'est pas finit alors il faut continuer l'application.

        while (pasFini){ // tant que tu n'a pas fini, continue.
            List<File> fichierJson = obtenirFichiersJson(dossier); // obtien les fichiers json du fichier dossier.

            if (fichierJson.isEmpty()){ //l'array list de tout les fichiers à l'intérieur du fichier dossier est vide.
                System.out.println("Aucun fichier JSON à été trouvé dans le dossier fichiers_json.");
                return; //pour arrêter la méthode
            }
            System.out.println("\nFichiers JSON disponibles :"); // présente les fichiers disponibles s'il existe.
            for (int index = 0; index < fichierJson.size(); index++){
                System.out.println("[" + (index + 1) + "]" + fichierJson.get(index).getName());
            }

            int choix = 0; // initialiser tout comme choix valide
            boolean choixValide = false;

            while(!choixValide){ // vus que initialiser false alors il vas l'éxecuter au début
                System.out.println("Entrez le numéro du fichier : "); // section pour aller chercher le fichier voulu de l'utilisateur.
                String entreeUtilisateur = scanner.nextLine().trim();

                try {
                    choix = Integer.parseInt(entreeUtilisateur);

                    if (choix >= 1 && choix <= fichierJson.size()){
                        choixValide = true;
                    } else System.out.println("Numéro invalide. Réessayer.");
                } catch (NumberFormatException e){
                    System.out.println("Entrez un nombre valide.");
                }
            }

            File fichierChoisi = fichierJson.get(choix - 1); // logique de index de java qui commence à 0 au lieu de 1.

            try{
                Composant circuit = CircuitGlobale.construireCircuit(fichierChoisi.getPath()); // construit le circuit
                double resistance = circuit.calculerResistance(); // calcule la résistance du circuit
                System.out.printf("Résistance équivalente calculée : %.2f Ω%n", resistance); // un print qui précise la manière dont tu veux qu'il imprime.
                // %f = affiche un nombre décimal, .2 = garde 2 chiffre après la virgule, Ω = ohm l'unité, %n = va à la ligne de manière propre. tout cela chercher sur google.
                // si une explication est nécessaire cela me fera un plaisir de le faire.
            } catch (IOException e){ // dans le cas ou c'est illisible
                System.out.println("Impossible de lire le fichier JSON.");
            }catch (IllegalArgumentException e){ // dans le cas ou le fichier JSON contient des données invalides.
                System.out.println("Le fichier JSON contient des données invalides : " + e.getMessage());
            }
            pasFini = demanderNouvelleAction(scanner); // si le programme s'arrête ou non dépend de demanderNouvelleAction qui demande avec le R et le Q.
        }

        System.out.println("Fermeture de l'application."); // arrête l'application une fois que l'utilisateur appui sur Q voir ligne 67.
    }

    private List<File> obtenirFichiersJson(File dossier) { // ****** Je ne comprend pas pourquoi il ne marche pas c'est ce qui fait que j'avance pas live.
        List<File> fichiersJson = new ArrayList<>();

        File[] tousLesFichiersJson = dossier.listFiles();

        if (tousLesFichiersJson == null){ // si il n'y a pas de File Json alors retourne une array list null.
            return fichiersJson;
        }

        for (File fichier : tousLesFichiersJson){ // pour chaque fichier du tableau de fichiers vérifier que c'est bien un fichier et qu'il finit bien par .json
            if (fichier.isFile() && fichier.getName().toLowerCase().endsWith(".json")){
                fichiersJson.add(fichier);
            }
        }
        return fichiersJson;
    }
    private boolean demanderNouvelleAction(Scanner scanner){ // demande une nouvelle action npour changer ou non la valeur de pasFini dans éxécuter.
        while(true){
            System.out.println("\n[R] Tester un autre fichier | [Q] Quitter : "); // question
            String entreeUtilisateur = scanner.nextLine().trim().toUpperCase(); // passe une ligne enlève les espace inutile et met le en majuscule.

            if (entreeUtilisateur.equals("R")){ // si r ou R
                return true;
            }

            if (entreeUtilisateur.equals("Q")){ // si q ou Q
                return false;
            }

            System.out.println("Réponse invalide. Entrez R ou Q."); // autre chose
        }
    }
}
