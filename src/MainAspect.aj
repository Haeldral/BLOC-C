import java.util.List;
import voiture.Voiture;

public aspect MainAspect {
	
	pointcut execInstanciation() : execution(*
			Main.ajoutVoituresInstanciation(List<Voiture>));
	
	before() : execInstanciation() {
		System.out.println("L'usine chauffe, les voitures s'apprêtent à être créées.");
	}
}