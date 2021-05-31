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
package parallel;

import java.util.Map;
import java.util.HashMap;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import GUI.TableData;
import java.io.IOException;
import prolog.*;

public class ListaCalif {
    private PrologFiles plFiles;
    private PrologQueries plQueries;
    private final int total;
    private final Thread[] T;
    private Pattern p;
    private String job;

    public ListaCalif(String type) {
    	if(type.compareTo("Both")==0) {
            plQueries=new PrologQueries();
            plFiles=new PrologFiles();
    	}
    	else if(type.compareTo("Prolog")==0) {
            plQueries = new PrologQueries();
            plFiles=null;
    	}
    	else if(type.compareTo("Files")==0){
            plQueries=null;
            plFiles = new PrologFiles();
    	}
        total=2;
        T=new Thread[total];
    }
    private String fixFormatIn(String line) {
        return StringUtils.replaceEach(line,
                new String[] {  "á",  "é",  "í",  "ó",  "ú",  "Á", "É",  "Í",  "Ó",  "Ú",  "ñ",  "Ñ", "'" },
                new String[] { "a|", "e|", "i|", "o|", "u|", "A|", "E|", "I|", "O|", "U|", "n|", "N|", "\\'" });
    }
    
    
    private Map<Integer, TableData> startJob() throws InterruptedException{
        Map<Integer, TableData> data=new HashMap<>();
        
        // Create Threads
        for(int i=0;i<total;i++){
            T[i]=new Thread(new PAnal(i, total, plQueries, plFiles, p, data, job));
        }
        
        // Start threads
        for(int i=0;i<total;i++){
            T[i].start();
        }

       // Wait Threads to join
       for(int i=0;i<total;i++){
           T[i].join();
       }
        
        return data;
    }
    
    
    public void Importar_Materias(boolean overwrite) throws InterruptedException, IOException {
        // Setup
        plFiles.setImport("Materias", overwrite);
        p = Pattern.compile("^(\\\"?)(.+[^\"])(\\\"?\\,)(.+)$");
        job="Importar_Materias";
        
        // Start Job
        startJob();
        plFiles.close();
    }
    public void Exportar_Materias() throws IOException, InterruptedException {
        // Setup
    	plFiles.setExport("Materias", "Materia,Código", null);
    	plQueries.listaMaterias();
    	p = Pattern.compile("^(\\[\\')(.*)(\\'\\, )(.*)(\\])");
        job="Exportar_Materias";
        
        // Start Job
        startJob();
        plFiles.close();
    }
    
    public void Importar_Alumnos(boolean overwrite) throws IOException, InterruptedException {
        // Setup
        plFiles.setImport("Alumnos", overwrite);
        job="Importar_Alumnos";
        
        // Start Job
        startJob();
        plFiles.close();
    }
    public void Exportar_Alumnos() throws IOException, InterruptedException {
        // Setup
    	plFiles.setExport("Alumnos", "Número de Lista", Grupo_Filter());
    	plQueries.listaAlumnosAll("_");
    	p = Pattern.compile("^(\\[\\')(.*)(\\'\\, \\')(.*)(\\'\\, )(.*)(\\])$");
        job="Exportar_Alumnos";
        
        // Start Job
        startJob();
        plFiles.close2();
    }
    

    public void Importar_Calificaciones(boolean overwrite) throws InterruptedException, IOException {
        // Setup
    	plFiles.setImport("Calificaciones", overwrite);
        job="Importar_Calificaciones";
        
        // Start Job
        startJob();
        plFiles.close();
        plFiles.writePeriod();
    }
    public void Exportar_Calificaciones() throws InterruptedException, IOException {
        // Setup
    	plFiles.setExport("Calificaciones", "Periodo,Nombre del Alumno", Materia_Filter());
    	plQueries.listaCalifAll("_", "_", "_");
    	p = Pattern.compile("^(\\[)(.*)(\\, \\')(.*)(\\'\\, \\')(.*)(\\'\\, )(.*)(\\, \\')(.*)(\\'\\, )(.*)(\\])");
        job="Exportar_Calificaciones";
        
        // Start Job
        startJob();
        plFiles.close3();
    }
    
    
    
    
    public Map<Integer, TableData> Consultar_Materias() throws InterruptedException {
        // Setup
        plQueries.listaMaterias();
        p = Pattern.compile("^(\\[\\')(.*)(\\'\\, )(.*)(\\])");
        job="Consultar_Materias";
        
        // Start Job
        
        return startJob();
    }
    public String[] Materia_Filter() throws InterruptedException {
        // Setup
    	plQueries.listaMaterias();
    	p = Pattern.compile("^(\\[\\')(.*)(\\'.*)");
        job="Materia_Filter";
        
        // Start Job
        Map<Integer, TableData> MapF=startJob();
        
        // Store data into array
        String[] filter=new String[MapF.size()+1];
        filter[0]="Todos";
        for(int i=0;i<MapF.size();i++){
            filter[i+1]=MapF.get(i).getFilter();
        }
    	return filter;
    }
    public String[] Grupo_Filter() throws InterruptedException {
        //Setup
        plQueries.listaGrupos();
        p = Pattern.compile("^(\\[\\')(.*)(\\'.*)");
        job="Grupo_Filter";
        
        //Start job
        Map<Integer, TableData> MapF=startJob();
        
        //Store data
        String[] filter=new String[MapF.size()+1];
        filter[0]="Todos";
        for(int i=0;i<MapF.size();i++){
            filter[i+1]=MapF.get(i).getFilter();
        }
    	return filter;
    }
    public String[] Periodo_Filter() throws InterruptedException{
        //Setup
        plQueries.listaPeriodos();
        p = Pattern.compile("^(\\[)(.*)(\\].*)");
        job="Periodo_Filter";
        
        //Start job
        Map<Integer, TableData> MapF=startJob();
        
        //Store data
        String[] filter=new String[MapF.size()+1];
        filter[0]="Todos";
        for(int i=0;i<MapF.size();i++){
            filter[i+1]=MapF.get(i).getFilter();
        }
    	return filter;
    }
  
    public Map<Integer, TableData> Consultar_Alumnos(String grupo) throws InterruptedException{
        // Setup
    	if(grupo.compareTo("Todos")==0) {
            grupo="_";
    	}
        else{
            grupo="'"+grupo+"'";
        }
    	plQueries.listaAlumnos(grupo);
    	p = Pattern.compile("^(\\[\\')(.*)(\\'\\, \\')(.*)(\\'\\, )(.*)(\\])$");
        job="Consultar_Alumnos";
        
        // Start job
        return startJob();
    }

    public Map<Integer, TableData> Consultar_Calif(String periodo, String materia, String grupo) throws InterruptedException{
        // Setup
    	if(periodo.compareTo("Todos")==0) {
    		periodo="_";
    	}
    	if(materia.compareTo("Todos")==0) {
    		materia="_";
    	}
    	else {
    		materia="'"+fixFormatIn(materia)+"'";
    	}
    	if(grupo.compareTo("Todos")==0) {
    		grupo="_";
    	}
        else{
                grupo="'"+grupo+"'";
        }
    	plQueries.listaCalif(periodo, materia, grupo);
    	p = Pattern.compile("^(\\[)(.*)(\\, \\')(.*)(\\'\\, \\')(.*)(\\'\\, )(.*)(\\, \\')(.*)(\\'\\, )(.*)(\\])");
        job="Consultar_Calif";
        
        // Start job
        return startJob();
    }
}
