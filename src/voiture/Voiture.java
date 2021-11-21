package voiture;

public class Voiture {
	
	private int id;
	static private int _id = 0;
	private int position;
	private int vitesse = 50;
	
	// CONSTRUCTEUR
	public Voiture(int vitesse) {
		this.id = _id;
		_id++;
		this.vitesse = vitesse;
		this.position = 0;		
	}
	
	
	public void deplacement(){
		this.position += this.vitesse;
	}
	
	
	// GETTER AND SETTERS
	public int getPosition() {return this.position;}
	public int getId() {return this.id;}
	public int getVitesse() {return this.vitesse;}
	
	@Override public String toString() {
		return "test";
	}
}
