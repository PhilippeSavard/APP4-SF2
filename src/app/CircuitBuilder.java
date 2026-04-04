package app;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import electronique.Composant;

import java.io.File;
import java.io.IOException;

public class CircuitBuilder {
    private final ObjectMapper mapper; // ObjectMapper = utile pour lecture de JsonNode

    public CircuitBuilder() {
        this.mapper = new ObjectMapper();
    }

    public Composant construireCircuit(String FichierJson) throws IOException { // Intellij me demandais de rajouter l'exception.
        JsonNode CircuitAuComplet = mapper.readTree(new File(FichierJson)); // readTree va lire le fichier au complet et cela représente un JsonNode que l'on vas lire les composants pour calculer la résistance.
        return lireComposant(CircuitAuComplet);
    }

    public Composant lireComposant(JsonNode CircuitAuComplet){ // le truc de noeud dans la matière
        if (CircuitAuComplet == null || CircuitAuComplet.get("type") == null){
            throw new IllegalArgumentException("Le circuit dans son ensemble qui est un JsonNode n'existe pas!");
        }
    }
}
