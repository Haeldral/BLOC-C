package dynamique;

import voiture.Voiture;

public class VoitureFactory {
	
	public enum ModeConstruction {INSTANCIATION, REFLEXION, META}
	
	public static Voiture buildVoiture(ModeConstruction mode, boolean sport, int vitesse) {
		
		switch(mode){
			case INSTANCIATION:
				break;
			case REFLEXION:
				break;
			case META:
				break;
		}
		
		// A ENLEVER
		Voiture voiture = new Voiture();
		return voiture;
	}
	
	

}
