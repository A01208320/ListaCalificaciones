package prolog;

import org.jpl7.*;
import java.util.Map;


public class PrologQueries {
    /*
    ! Due to the natrue of JPL, trying to pass a Query between threads will result in a segmentation fault
    ! Because of this instead the results are stored in a map, and then the threads read that map
    */
    private final Query data;
    private Query q, qm, qa;
    private Map<String, Term>[] solutions;

    public PrologQueries() {
        data = new Query("consult", new Term[] { new Atom("pl//data.pl") });
        if (!data.hasSolution()) {
            System.out.println("Fatal error reading data");
        }
    }

    //? Get an element of the map
    public String getSolution(int t) {
        if (t < solutions.length) {
            return solutions[t].get("X").toString();
        }
        return null;
    }
    
    //? listaMaterias(X). % Regresa una lista de todas las Materias.
    public void listaMaterias() {
        q = new Query("listaMaterias(X).");
        solutions = q.allSolutions();
    }
    
    //? listaAlumnos(X) % Regresa una lista de Alumnos
    public void listaAlumnos(String grado, String grupo) {
        q = new Query("listaAlumnos("+grado+", "+grupo+", X).");
        //System.out.println(q);
        solutions = q.allSolutions();
    }
    
  //? listaAlumnos(X) % Regresa una lista de Alumnos
    public void listaCalif(String periodo, String materia, String grado, String grupo) {
        q = new Query("getCalif("+periodo+", "+materia+", "+grado+", "+grupo+", X).");
        solutions = q.allSolutions();
    }
    
    //? Regresa un unico codigo de materia
    public synchronized String materia(String nombre) {
    	qm=new Query("materia('"+nombre+"' , X)");
    	if(qm.hasSolution()) {
    		return qm.oneSolution().get("X").toString();
    	}
    	return null;
    }
    
    //? Regresa la informacion de un unico alumno
    public synchronized String[] alumno(String nombre) {
    	qa=new Query("alumno('"+nombre+"' , X, Y, Z)");
    	if(qa.hasSolution()) {
    		String[] alumn= {qa.oneSolution().get("X").toString(), qa.oneSolution().get("Y").toString(), qa.oneSolution().get("Z").toString()};
    		return alumn;
    	}
    	return null;
    }
}
