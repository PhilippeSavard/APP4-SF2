package electronique;

import java.util.List;

public class CircuitSerie extends Circuit{

    public CircuitSerie(List<Composant> composantsCircuit){
        super(composantsCircuit);
    }

    @Override
    public double calculerResistance() {
        double ResistanceTotalSerie = 0.0;
        for (Composant composantDuCircuit : composantsCircuit){
            ResistanceTotalSerie += composantDuCircuit.calculerResistance();
        }
        return ResistanceTotalSerie;
    }
}
