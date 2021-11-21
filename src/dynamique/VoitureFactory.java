package dynamique;

import voiture.Voiture;
import voiture.VoitureSport;

import javax.tools.*;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


public class VoitureFactory {

	public enum ModeConstruction {INSTANCIATION, REFLEXION, META}

	public static Voiture buildVoiture(ModeConstruction mode, boolean sport, int vitesse) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {

		Voiture voiture;
		Class classe;


		if (sport) {
			switch(mode){
				case META:
					voiture = preparationFactory("MetaVoitureSport");
					break;
				case REFLEXION:
					classe = Class.forName("voiture.VoitureSport");
					voiture = (Voiture)classe.getDeclaredConstructor().newInstance();
					break;
				default:
					voiture = new VoitureSport();
					break;
			}
		} else {
			switch(mode){
			case META:
				voiture = preparationFactory("MetaVoiture");
				break;
			case REFLEXION:
				classe = Class.forName("voiture.Voiture");
				voiture = (Voiture)classe.getDeclaredConstructor(int.class).newInstance(vitesse);
				break;
			default:
				voiture = new Voiture(vitesse);
				break;
			}
		}
		return voiture;
	}

	private static StringSource buildString(String nomClasse) {
		StringBuilder sb = new StringBuilder();

		sb.append("package voiture;\n");
		sb.append("public class " + nomClasse + " extends " + nomClasse.replace("Meta","") + "implements Surveillable{\n");
		genererAttributMetaVoitureSport(sb);
		genererConstructeurMetaVoitureSport(nomClasse, sb);
		genererMethodeMetaVoitureSport(sb);
		sb.append("}\n");

		System.out.println("LA CLASSE");
		System.out.println(sb.toString());

		return new StringSource(nomClasse, sb.toString());
	}

	// FACTORY POUR METAVOITURE

    private static void genererConstructeurMetaVoiture(String nomClasse, StringBuilder sb) {

        sb.append("public " + nomClasse + "(int vitesse) {\r\nsuper(vitesse);\r\n}\n");
    }

    private static void genererMethodeMetaVoiture(StringBuilder sb) {

        sb.append("public int surveiller(int limite){int depassement = this.vitesse - limite;\nreturn depassement;}\n");

    }

    private static void genererAttributMetaVoiture(StringBuilder sb) {

        sb.append("private int vitesse = 50;\n");
    }


 // FACTORY POUR METAVOITURESPORT


    private static void genererConstructeurMetaVoitureSport(String nomClasse, StringBuilder sb) {

        sb.append("public " + nomClasse + "() {\r\nsuper();\r\n}\n");
    }

    private static void genererMethodeMetaVoitureSport(StringBuilder sb) {

        sb.append("public int surveiller(int limite){int depassement = this.vitesse - limite;\nreturn depassement;}\n");

    }

    private static void genererAttributMetaVoitureSport(StringBuilder sb) {

        sb.append("private int vitesse = 50;\n");
    }





    static Voiture preparationFactory(String nomClasse){
	 // ******** ETAPE #1 : Préparation pour la compilation
	    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
	    List<ByteArrayClass> classes = new ArrayList<ByteArrayClass>();           // pour mettre les .class   (IMPORTANT)
	    DiagnosticCollector<JavaFileObject> collector = new DiagnosticCollector<JavaFileObject>();
	    JavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

	    // La classe qui se charge de fournir les "conteneurs" au compilateur à la volée, sans accès au disque
	    fileManager = new ForwardingJavaFileManager<JavaFileManager>(fileManager){
	        public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind,
	                                                   FileObject sibling) throws IOException{
	            if (kind == JavaFileObject.Kind.CLASS){
	                ByteArrayClass outFile = new ByteArrayClass(className);
	                classes.add(outFile);           // ICI IMPORTANT
	                return outFile;
	            }
	            else
	                return super.getJavaFileForOutput(location, className, kind, sibling);
	        }
	    };

	    // ******** ETAPE #2 : Génération du code source
	    List<JavaFileObject> sources = List.of(buildString(nomClasse));

	    // ******** ETAPE #3 : Compilation
	    JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, collector, null,
	            null, sources);
	    Boolean result = task.call();

	    for (Diagnostic<? extends JavaFileObject> d : collector.getDiagnostics())
	        System.out.println(d);

	    try {
	        fileManager.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    if (!result) {
	        System.out.println("ECHEC DE LA COMPILATION");
	        System.exit(1);
	    }


	    // ******** ETAPE #4 : Instanciation
	    ByteArrayClasseLoader loader = new ByteArrayClasseLoader(classes);
	    try {
	        // Recherche la classe dans le contexte "local" sinon il passe par le "loader"
	        return (Voiture)(Class.forName("dynamique."+ nomClasse, true, loader).getDeclaredConstructor().newInstance());
	    } catch (ClassNotFoundException | NoSuchMethodException e) {
	        e.printStackTrace();
	    } catch (IllegalAccessException e) {
	        e.printStackTrace();
	    } catch (InstantiationException e) {
	        e.printStackTrace();
	    } catch (InvocationTargetException e) {
	        e.printStackTrace();
	    }
		System.out.println("La creation meta a échouée");
		return new Voiture(1);
	}
}
