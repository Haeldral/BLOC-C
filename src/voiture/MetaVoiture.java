package voiture;

public class MetaVoiture extends Voiture implements Surveillable{
	
	private int vitesse = 50;
	
	public MetaVoiture(int vitesse) {
		super(vitesse);
	}
	
	public int surveiller(int limite){
		int depassement = this.vitesse - limite;
		return depassement;
	}
}