import dynamique.VoitureFactory;
import voiture.Surveillable;
import voiture.Voiture;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		List<Voiture> mesVoitures = new ArrayList<Voiture>();
		ajoutVoituresInstanciation(mesVoitures);
		ajoutVoituresReflexion(mesVoitures);
		ajoutVoituresMeta(mesVoitures);
		/*
		try {
			int x = 0;
			while (x++ < 20)
				for (Voiture v : mesVoitures){
					v.deplacement();
					System.out.println(v);
				surveillerVoiture(v);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
	}	
	
	private static void ajoutVoituresInstanciation(List<Voiture> mesVoitures) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		mesVoitures.add(VoitureFactory.buildVoiture(VoitureFactory.ModeConstruction.INSTANCIATION, true, 0));
		mesVoitures.add(VoitureFactory.buildVoiture(VoitureFactory.ModeConstruction.INSTANCIATION,false, 150));
		mesVoitures.add(VoitureFactory.buildVoiture(VoitureFactory.ModeConstruction.INSTANCIATION,false, 100));
	}
	
	private static void ajoutVoituresMeta(List<Voiture> mesVoitures) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		mesVoitures.add(VoitureFactory.buildVoiture(VoitureFactory.ModeConstruction.META, true, 0));
		mesVoitures.add(VoitureFactory.buildVoiture(VoitureFactory.ModeConstruction.META,false, 150));
		mesVoitures.add(VoitureFactory.buildVoiture(VoitureFactory.ModeConstruction.META,false, 100));
	}

	private static void ajoutVoituresReflexion(List<Voiture> mesVoitures) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		mesVoitures.add(VoitureFactory.buildVoiture(VoitureFactory.ModeConstruction.REFLEXION,true, 0));
		mesVoitures.add(VoitureFactory.buildVoiture(VoitureFactory.ModeConstruction.REFLEXION, false, 150));
		mesVoitures.add(VoitureFactory.buildVoiture(VoitureFactory.ModeConstruction.REFLEXION, false, 100));
	}
	
	private static void surveillerVoiture(Voiture v) throws Exception {
		if (v.getPosition() > 1000){
			if (v.getClass().getName().contains("Meta")){
				int depassement = ((Surveillable)v).surveiller(60);
				if (depassement > 10)
					throw new Exception("ID : " + v.getId() + " --> depassement de " + depassement + " (" + v.getClass() + ")");
			}
		}
	}
}