package electronique;

import java.util.List;

public abstract class Circuit extends Composant {
    protected final List<Composant> composantsCircuit;

    public Circuit(List<Composant> composantsCircuit){
        if (composantsCircuit == null || composantsCircuit.isEmpty()){
            throw new IllegalArgumentException("Un circuit doit contenir au moins une composante, soit par exemple une Résistance!");
        }
        this.composantsCircuit = composantsCircuit;
    }
}
