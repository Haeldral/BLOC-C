package voiture;

public class MetaVoitureSport extends VoitureSport implements Surveillable{
	
	
	// CONSTRUCTEUR
	public MetaVoitureSport() {
		super();
	}
	
	
	public int surveiller(int limite){
		int depassement = this.getVitesse() - limite;
		return depassement;
	}
	
	
}