package voiture;

public aspect VoitureSportAspect {

	pointcut execCreationVoitureSport() : execution(*
			VoitureSport.VoitureSport());
	
	after() : execCreationVoitureSport() {
		System.out.println("Voiture de sport déployée !");
	}
}