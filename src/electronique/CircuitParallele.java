package electronique;

import java.util.List;

public class CircuitParallele extends Circuit{

    public CircuitParallele(List<Composant> composantsCircuit){
        super(composantsCircuit);
    }

    @Override
    public double calculerResistance() {
        double ResistanceTotalParallele = 0.0;

        for (Composant composantDuCircuit : composantsCircuit){
            double Resistance = composantDuCircuit.calculerResistance();
            if (Resistance == 0){
                throw new ArithmeticException("On ne peut pas diviser par 0!!!");
            }
            ResistanceTotalParallele += 1.0/Resistance;
        }
        return 1.0/ResistanceTotalParallele;
    }
}
