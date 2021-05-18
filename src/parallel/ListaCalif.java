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
    	plFiles.setExport("Materias", "Materia,Código");
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
        p = Pattern.compile("^(.+)(\\,)(.+)(\\,)(.+)(\\,\\\"?)(.+[^\"])(\\\"?)$");
        job="Importar_Alumnos";
        
        // Start Job
        startJob();
        plFiles.close();
    }
    public void Exportar_Alumnos() throws IOException, InterruptedException {
        // Setup
    	plFiles.setExport("Alumnos", "Grado,Grupo,N�mero de Lista,Nombre");
    	plQueries.listaAlumnos("_", "_");
    	p = Pattern.compile("^(\\[)(.*)(\\, )(.*)(\\, )(.*)(\\, \\')(.*)(\\'\\])");
        job="Exportar_Alumnos";
        
        // Start Job
        startJob();
        plFiles.close();
    }
    

    public void Importar_Calificaciones(boolean overwrite) throws InterruptedException, IOException {
        // Setup
    	plFiles.setImport("Calificaciones", overwrite);
    	p = Pattern.compile("^(.+)(\\,\\\"?)(.+[^\\\"])(\\\"?\\,\\\"?)(.+[^\\\"])(\\\"?\\,)(.+)$");
        job="Importar_Calificaciones";
        
        // Start Job
        startJob();
        plFiles.close();
    }
    public void Exportar_Calificaciones() throws InterruptedException, IOException {
        // Setup
    	plFiles.setExport("Calificaciones", "Periodo,Materia,Nombre del Alumno,Calificaci�n");
    	plQueries.listaCalif("_", "_", "_", "_");
    	p = Pattern.compile("^(\\[)(.*)(\\, )(.*)(\\, )(.*)(\\, \\')(.*)(\\'\\, \\')(.*)(\\'\\, )(.*)(\\])");
        job="Exportar_Calificaciones";
        
        // Start Job
        startJob();
        plFiles.close();
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
  
    public Map<Integer, TableData> Consultar_Alumnos(String grado, String grupo) throws InterruptedException{
        // Setup
    	if(grado.compareTo("Todos")==0) {
    		grado="_";
    	}
    	if(grupo.compareTo("Todos")==0) {
    		grupo="_";
    	}
    	else {
    		grupo=grupo.toLowerCase();
    	}
    	plQueries.listaAlumnos(grado, grupo);
    	p = Pattern.compile("^(\\[)(.*)(\\, )(.*)(\\, )(.*)(\\, \\')(.*)(\\'\\])");
        job="Consultar_Alumnos";
        
        // Start job
        return startJob();
    }

    public Map<Integer, TableData> Consultar_Calif(String periodo, String materia, String grado, String grupo) throws InterruptedException{
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
    	if(grado.compareTo("Todos")==0) {
    		grado="_";
    	}
    	if(grupo.compareTo("Todos")==0) {
    		grupo="_";
    	}
    	else {
    		grupo=grupo.toLowerCase();
    	}
    	plQueries.listaCalif(periodo, materia, grado, grupo);
    	p = Pattern.compile("^(\\[)(.*)(\\, )(.*)(\\, )(.*)(\\, \\')(.*)(\\'\\, \\')(.*)(\\'\\, )(.*)(\\])");
        job="Consultar_Calif";
        
        // Start job
        return startJob();
    }
}
