package electronique;

import java.util.List;

public class CircuitParallele extends Circuit{

    public CircuitParallele(List<Composant> composantsCircuit){
        super(composantsCircuit);
    }

    @Override
    public double calculerResistance() {
        return 0;
    }
}
