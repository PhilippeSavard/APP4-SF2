package electronique;

public class Resistance extends Composant{
    private final double valeur;

    public Resistance(double valeur){
        if (valeur <= 0){
            throw new IllegalArgumentException("La résistance doit être positive!");
        }
        this.valeur = valeur;
    }
    @Override
    public double calculerResistance() {
        return this.valeur;
    }
}
