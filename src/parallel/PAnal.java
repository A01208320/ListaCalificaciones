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
    private void Grupo_Filter(){
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
    private void Periodo_Filter(){
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
                    data.put(pos, new TableData(m.group(2), m.group(4), m.group(6)));
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
                        data.put(pos, new TableData(m.group(2), m.group(4), m.group(6), m.group(8), m.group(10), m.group(12)));
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
            	line=""+m.group(2)+","+m.group(4);
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
    
    //? Alumnos
    private void Importar_Alumnos() {
    	//Read first line
        String line = plFiles.readLine(pos);
        String[] parts;
        while (line != null) {
            //Process
            line=fixFormatIn(line);
            
            //Write
            parts=line.split(",");
            //parts[0] has number of list
            for(int i=1;i<parts.length;i++){
                //System.out.println(pos+"|"+plFiles.getHeaderlength()+"||"+((pos*plFiles.getHeaderlength())+(i-1))+" : alumno('"+parts[i]+"','"+plFiles.getHeader(i)+"',"+parts[0]+").");
                if(parts[i].length()==0){
                    parts[i]="-1";
                }
                line="alumno('"+parts[i]+"','"+plFiles.getHeader(i)+"',"+parts[0]+").";
                plFiles.writeLine((pos*plFiles.getHeaderlength())+(i-1),line);
            }
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
            
            //Write
            Matcher m = p.matcher(line);
            if (m.find()) {
                if(m.group(2).compareTo("-1")==0){
                    line="";
                }
                else{
                    line=m.group(2);
                }
                plFiles.writeLine(Integer.valueOf(m.group(6)), line, m.group(4));
            } else {
                System.out.println("Error in: "+line);
            }
            pos+=total;
            
            //Read
            line = plQueries.getSolution(pos);
        }
    }
    
    //? Calificaciones
    private void Importar_Calificaciones() {
    	//Read first line
        String line = plFiles.readLine(pos);
        String[] parts;
        while (line != null) {
            //Process
            line=fixFormatIn(line);
            
            //Write
            parts=line.split(",");
            //parts[0] have period
            //parts[1] have student
            plFiles.period(Integer.valueOf(parts[0]));
            for(int i=2;i<parts.length;i++){
                if(parts[i].length()==0){
                    parts[i]="-1";
                }
                String materiaC=plQueries.materia(plFiles.getHeader(i));
                String[] alumnoG=plQueries.alumno(parts[1]);
                if(materiaC!=null && alumnoG!=null){
                    line="calificacion("+parts[0]+","+materiaC+","+alumnoG[0]+","+alumnoG[1]+","+parts[i]+").";
                }
                else{
                    line=null;
                }
                plFiles.writeLine((pos*plFiles.getHeaderlength())+(i-1),line);
                
            }
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
            
            //Write
            Matcher m = p.matcher(line);
            if (m.find()) {
                if(m.group(12).compareTo("-1")==0){
                    line="";
                }
                else{
                    line=m.group(12);
                }
                plFiles.writeLine2(m.group(2)+","+m.group(10), line, m.group(4));
            } else {
                System.out.println("Error in: "+line);
            }
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
        case "Grupo_Filter":
                Grupo_Filter();
                break;
        case "Periodo_Filter":
                Periodo_Filter();
                break;
        default:
            System.out.println(pos + " Error");
            break;
        }
    }
}
