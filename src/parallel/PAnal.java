package parallel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import GUI.TableData;
import prolog.*;

public class PAnal implements Runnable {
    private int pos;
    private final int total;
    private final String job;
    private final PrologFiles plFiles;
    private final PrologQueries plQueries;
    private final Pattern p;
    private final Map <Integer, TableData> data;


    public PAnal(int pos, int total, PrologQueries plQueries, PrologFiles plFiles, Pattern p, Map<Integer, TableData> data, String job) {
        this.pos = pos;
        this.total=total;
        this.plQueries = plQueries;
        this.plFiles=plFiles;
        this.p=p;
        this.job = job;
        this.data=data;
    }
    
    private String fixFormatOut(String line) {
        return StringUtils.replaceEach(line,
                new String[] { "a|", "e|", "i|", "o|", "u|", "A|", "E|", "I|", "O|", "U|", "n|", "N|"},
                new String[] { "á",  "é",  "í",  "ó",  "ú",  "Á",  "É",  "Í",  "Ó",  "Ú",  "ñ",  "Ñ"});
    }
    private String fixFormatIn(String line) {
        return StringUtils.replaceEach(line,
                new String[] { "á",  "é",  "í",  "ó",  "ú",  "Á", "É",  "Í",  "Ó",  "Ú",  "ñ",  "Ñ", "'" },
                new String[] { "a|", "e|", "i|", "o|", "u|", "A|", "E|", "I|", "O|", "U|", "n|", "N|", "\\'" });
    }

    
    //? Prolog queries
    //? listaMaterias(X).
    private void Consultar_Materias() {
        // Get first solution
        String line = plQueries.getSolution(pos);
        while (line != null) {
            // Process info
            line = fixFormatOut(line);
            
            // Write to map
            Matcher m = p.matcher(line);
            if (m.find()) {
                synchronized(data){
                    data.put(pos, new TableData(m.group(2), m.group(4)));
                }
            } else {
                System.out.println("Error in: "+line);
            }
            pos+=total;

            // Get next solution
            line = plQueries.getSolution(pos);
        }
    }
    private void Materia_Filter() {
        // Get first solution
    	String line = plQueries.getSolution(pos);
        while (line != null) {
            //Process info
            line = fixFormatOut(line);
            
           // Write to map
            Matcher m = p.matcher(line);
            if (m.find()) {
                synchronized(data){
                    data.put(pos, new TableData(m.group(2)));
                }
            } else {
                System.out.println("Error in: "+line);
            }
            pos+=total;

            // Get next solution
            line = plQueries.getSolution(pos);
        }
    }
    
    
    //? listaAlumnos(X).
    private void Consultar_Alumnos() {
        // Get first solution
    	String line=plQueries.getSolution(pos);
    	while(line!=null) {
            // Process info
            line=fixFormatOut(line);
            

            // Write to map
            Matcher m = p.matcher(line);
            if (m.find()) {
                synchronized(data){
                    data.put(pos, new TableData(m.group(8), m.group(2), m.group(4).toUpperCase(), m.group(6)));
                }
            } else {
                System.out.println("Error in: "+line);
            }
            pos+=total;
    		
            // Get next solution
            line = plQueries.getSolution(pos);
    	}
    	
    }

    
    //? listaAlumnos(X).
    private void Consultar_Calif() {
        // Get first solution
    	String line=plQueries.getSolution(pos);
    	while(line!=null) {
    		// Process info
    		line=fixFormatOut(line);
    		
    		// Write to map
    		Matcher m = p.matcher(line);
    		if (m.find()) {
                    synchronized(data){
                        data.put(pos, new TableData(m.group(2), m.group(4), m.group(6).toUpperCase(), m.group(8), m.group(10), m.group(12)));
                    }
            } else {
                System.out.println("Error in: "+line);
            }
            pos+=total;
    		
            // Get next solution
            line = plQueries.getSolution(pos);
    	}
    	
    }
    
    
    
    //? Prolog Files
    //? Materias
    private void Importar_Materias() {
    	//Read first line
        String line = plFiles.readLine(pos);
        while (line != null) {
            //Process
            line=fixFormatIn(line);
            
            Matcher m = p.matcher(line);
            if (m.find()) {
                line = "materia('" + m.group(2) + "'," + m.group(4) + ").";
            } else {
                line = "Error in: "+line;
            }

            //Write to map
            plFiles.writeLine(pos, line);
            pos+=total;

            //Read next
            line = plFiles.readLine(pos);
        }
    }
    private void Exportar_Materias() {
    	String line = plQueries.getSolution(pos);
        while (line != null) {
            //Process info
            line = fixFormatOut(line);
            Matcher m = p.matcher(line);
            if (m.find()) {
            	line="\""+m.group(2)+"\","+m.group(4);
            } else {
                System.out.println("Error in: "+line);
            }
            
            //Write
            plFiles.writeLine(pos, line);
            pos+=total;
            
            //Read
            line = plQueries.getSolution(pos);
        }
	}
    
    
    private void Importar_Alumnos() {
    	//Read first line
        String line = plFiles.readLine(pos);
        while (line != null) {
            //Process
        	line=fixFormatIn(line);
            Matcher m = p.matcher(line);
            if (m.find()) {
                line = "alumno('" + m.group(7) + "'," + m.group(5) +"," + m.group(1)+"," + m.group(3).toLowerCase() + ").";
            } else {
                line = "Error in: "+line;
            }

            //Write
            plFiles.writeLine(pos, line);
            pos+=total;

            //Read
            line = plFiles.readLine(pos);
        }
    }
    private void Exportar_Alumnos() {
    	String line = plQueries.getSolution(pos);
        while (line != null) {
            //Process info
            line = fixFormatOut(line);
            Matcher m = p.matcher(line);
            if (m.find()) {
            	line=m.group(2)+","+m.group(4).toUpperCase()+","+m.group(6)+",\""+m.group(8)+"\"";
            } else {
                System.out.println("Error in: "+line);
            }
            
            //Write
            plFiles.writeLine(pos, line);
            pos+=total;
            
            //Read
            line = plQueries.getSolution(pos);
        }
	}
    
    
    private void Importar_Calificaciones() {
    	//Read first line
        String line = plFiles.readLine(pos);
        while (line != null) {
            //Process
        	line=fixFormatIn(line);
            Matcher m = p.matcher(line);
            if (m.find()) {
            	String materia=plQueries.materia(m.group(3));
            	String[] alumno=plQueries.alumno(m.group(5));
            	if(materia==null || alumno==null) {
            		line=null;
            	}
            	else {
            		line = "calificacion(" + m.group(1) + "," + materia +"," +alumno[1] +"," + alumno[2]+"," +alumno[0]+"," + m.group(7) + ").";
            	}
                
            } else {
                line = "Error in: "+line;
            }

            //Write
            plFiles.writeLine(pos, line);
            pos+=total;

            //Read
            line = plFiles.readLine(pos);
        }
	}
    private void Exportar_Calificaciones() {
    	String line = plQueries.getSolution(pos);
        while (line != null) {
            //Process info
            line = fixFormatOut(line);
            Matcher m = p.matcher(line);
            if (m.find()) {
            	line=m.group(2)+",\""+m.group(8)+"\",\""+m.group(10)+"\","+m.group(12);
            } else {
                System.out.println("Error in: "+line);
            }
            
            //Write
            plFiles.writeLine(pos, line);
            pos+=total;
            
            //Read
            line = plQueries.getSolution(pos);
        }
	}
    
    //? Start thread
    @Override
    public void run() {
        switch (job) {
        case "Consultar_Materias":
            Consultar_Materias();
            break;
        case "Consultar_Alumnos":
        	Consultar_Alumnos();
        	break;
        case "Consultar_Calif":
        	Consultar_Calif();
        	break;
        case "Materia_Filter":
        	Materia_Filter();
        	break;
        case "Importar_Materias":
        	Importar_Materias();
        	break;
        case "Exportar_Materias":
        	Exportar_Materias();
        	break;
        case "Importar_Alumnos":
        	Importar_Alumnos();
        	break;
        case "Exportar_Alumnos":
        	Exportar_Alumnos();
        	break;
        case "Importar_Calificaciones":
        	Importar_Calificaciones();
        	break;
        case "Exportar_Calificaciones":
        	Exportar_Calificaciones();
        	break;
        default:
            System.out.println(pos + " Error");
            break;
        }
    }
}
