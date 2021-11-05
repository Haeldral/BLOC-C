package voiture;

public class Voiture {
	
	private int id;
	private int position;
	private int vitesse;
	
	// CONSTRUCTEUR
	public Voiture() {
	}
	
	
	public void deplacement(){
		this.position += this.vitesse;
	}
	
	
	// GETTER AND SETTERS
	public int getPosition() {return this.position;}
	public int getId() {return this.id;}
}
