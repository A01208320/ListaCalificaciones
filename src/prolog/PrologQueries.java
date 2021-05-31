/*
	<Lista Calificaciones: List of Scores for Schools>
	Copyright (C) <2021>  <A01208320> <A01208320@itesm.mx>

	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/
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
    
    //? lista materias
    public void listaMaterias() {
        q = new Query("listaMaterias(X).");
        solutions = q.allSolutions();
    }
    
    //? lista alumnos
    public void listaAlumnos(String grupo) {
        q = new Query("listaAlumnos("+grupo+", X).");
        solutions = q.allSolutions();
    }
    public void listaAlumnosAll(String grupo) {
        q = new Query("listaAlumnosAll("+grupo+", X).");
        solutions = q.allSolutions();
    }
    
  //? lista calificaciones
    public void listaCalif(String periodo, String materia, String grupo) {
        q = new Query("getCalif("+periodo+", "+materia+", "+grupo+", X).");
        solutions = q.allSolutions();
    }
    public void listaCalifAll(String periodo, String materia, String grupo) {
        q = new Query("getCalifAll("+periodo+", "+materia+", "+grupo+", X).");
        solutions = q.allSolutions();
    }
    
    //? lista los grupos
    public void listaGrupos(){
        q=new Query("getGrupos(X).");
        solutions=q.allSolutions();
    }
    public void listaPeriodos(){
        q=new Query("getPeriods(X).");
        solutions=q.allSolutions();
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
    	qa=new Query("alumno('"+nombre+"' , X, Y)");
    	if(qa.hasSolution()) {
    		String[] alumn= {qa.oneSolution().get("X").toString(), qa.oneSolution().get("Y").toString()};
    		return alumn;
    	}
    	return null;
    }
}
