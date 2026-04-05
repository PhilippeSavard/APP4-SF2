package app;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import electronique.CircuitParallele;
import electronique.CircuitSerie;
import electronique.Composant;
import electronique.Resistance;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CircuitBuilder {
    private final ObjectMapper mapper; // ObjectMapper = utile pour lecture de JsonNode

    public CircuitBuilder() {
        this.mapper = new ObjectMapper();
    }

    public Composant construireCircuit(String FichierJson) throws IOException { // Intellij me demandais de rajouter l'exception.
        JsonNode CircuitAuComplet = mapper.readTree(new File(FichierJson)); // readTree va lire le fichier au complet et cela représente un JsonNode que l'on vas lire les composants pour calculer la résistance.
        return lireComposant(CircuitAuComplet); // utilise le Circuit globale que l'on vient de décrire comme un fichier qu'on analyse et lit sa composante.
    }
        /* RÉSUMÉ!!!

        donc construireCircuit appellle LireComposant qui soit

        1. Ressort une Résistance directement qui en soit est une composante
        ou
        2. utilise lireListeComposants pour avoir précisement:

        une liste de composant que lireComposant pourra en resortir individuellement les composants.

        tout cela pour dire que si on à un JsonNode on le donne à lireComposant

        ou

        si on à un String qu'on veut
        interpréter comme un JsonNode alors on le donne à construireCircuit qui lui nous donne une composant grace à
        LireComposant.

         */

    public Composant lireComposant(JsonNode CircuitAuComplet){ // il doit retourner un composant qu'il va interpréter du JsonNode
        if (CircuitAuComplet == null || CircuitAuComplet.get("type") == null){ // si le circuit n'existe pas ou que le champ "type" est null alors il n'est pas réel.
            throw new IllegalArgumentException("Le circuit dans son ensemble est un JsonNode qui n'existe pas!");
        }

        String type = CircuitAuComplet.get("type").asText().toLowerCase(); // il déclare type comme le string qu'il devra adapté.

        return switch (type){
            case "resistance" -> new Resistance(CircuitAuComplet.get("valeur").asDouble()); // le String qu'il lit est "resistance" on fait alors face à une résistance.
            case "serie" -> new CircuitSerie(lireListeComposants(CircuitAuComplet.get("composants"))); //même chose mais c'est un circuit relié en Série.
            case "parallele" -> new CircuitParallele(lireListeComposants(CircuitAuComplet.get("composants"))); //même chose mais c'est un circuit relié en parralèle.
            default -> throw new IllegalStateException("Je ne sais pas quelle est cette composante : " + type); // composante inconnue par le programme et vient préciser le string analyser.
        };
    }

    private List<Composant> lireListeComposants(JsonNode ListeComposants) { // on lit le JsonNode de manière à resortir la liste de composantes pour l'interpréter.
        if (ListeComposants == null || !ListeComposants.isArray()){ //est ce que le JsonNode existe et s'il n'est pas une s'il n'y a pas au moins une liste de Résistance contenant une Résistance.
            throw new IllegalArgumentException("Le champ 'composants' doit être une array list de différentes Résistance!"); // à cette étape composants ressemble à un type entre parrallele et série qui contient des résistance.
        }

        List<Composant> composants = new ArrayList<>(); // on crée une array liste de composantes

        for (JsonNode CircuitEnfant : ListeComposants){ // Pour l'enfant de Circuit la classe parent qu'on vient chercher dans le JsonNode,
            composants.add(lireComposant(CircuitEnfant)); // on vient ajouter à notre liste de composantes chaque composante individuelle de CircuitEnfant ce qui inclue,
        } // son type qui est soit un un circuit branché en série ou un circuit branché en parrallelle et son nombre de Résistance.
        return composants; // tout cela se fait retourner comme une liste de Composantes interprétable.
    }
}
