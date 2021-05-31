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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class PrologFiles {
    private BufferedReader reader;
    private BufferedWriter writer;
    private final Map<Integer, String> Mread;
    private final TreeMap<Integer, String> Mwrite;
    private final TreeMap<String, Integer> Header;
    private TreeMap<Integer, Integer> Header2;
    private final TreeMap<Integer, TreeMap<String, String>> Mwrite2;
    private final TreeMap<String, TreeMap<String, String>> Mwrite3;
    private String[] header;
    private int headersize;

    public PrologFiles() {
        Mread=new HashMap<>();
        Mwrite=new TreeMap<>();
        Mwrite2=new TreeMap<>();
        Mwrite3=new TreeMap<>();
        Header=new TreeMap<>();
    }

    public void setImport(String file, boolean overwrite) throws IOException {
        reader = Files.newBufferedReader(Paths.get("excel//" + file + ".csv"), StandardCharsets.UTF_8);
            
        //Delete target contents
        if(overwrite) {
            new FileOutputStream("pl//"+file+".pl").close();
        }
        
        //header store in file
        String head=reader.readLine();
        header=head.split(",");
        switch(file){
            case"Materias":
                writer=null;
                break;
            case "Alumnos":
                if(overwrite){
                    new FileOutputStream("pl//"+"Grupos"+".pl").close();
                }
                headersize=head.length()-1;
                writer=new BufferedWriter(new FileWriter("pl//"+"Grupos"+".pl", true));
                break;
            case "Calificaciones":
                Header2=new TreeMap<>();
                if(overwrite){
                    new FileOutputStream("pl//"+"Periodos"+".pl").close();
                }
                headersize=head.length()-2;
                writer=null;
                break;
            default:
                System.out.println("Error");
                break;
        }

        //Just header
        if(writer!=null){
            for(int i=1;i<header.length;i++){
                writer.write("grupo('"+header[i]+"').");
                writer.newLine();
                writer.flush();
            }
        }
        
        
        writer=new BufferedWriter(new FileWriter("pl//"+file+".pl", true));
        // Store contents in Map
        String l=reader.readLine();
        int pos=0;
        while(l!=null){
            //Write to map
            Mread.put(pos, l);
            pos++;
            
            // Read next
            l=reader.readLine();
        }
        
    }
    
    public void setExport(String file, String identifier, String[] header) throws FileNotFoundException, IOException { 
        //Delete target contents
        new FileOutputStream("excel//"+file+".csv").close();

        writer=new BufferedWriter(new OutputStreamWriter(new FileOutputStream("excel//"+file+".csv"), StandardCharsets.UTF_8));

        String Tline="";
        if(header!=null){
            for(int i=1;i<header.length;i++){
                Header.put(header[i], i);
            }
            for (Map.Entry<String, Integer> e : Header.entrySet()){
                Tline+=","+e.getKey();
            }
        }
        
        //Just header
        writer.write(identifier+Tline);
        writer.newLine();
        writer.flush();

    }
    
    public String getHeader(int t){
        return header[t];
    }
    public int getHeaderlength(){
        return headersize;
    }
    
    public String readLine(int t) {
        if(t<Mread.size()){
            return Mread.get(t);
        }
        return null;
    }


    public synchronized void writeLine(int t, String line) {
        Mwrite.put(t, line);
    }
    public synchronized void writeLine(int t, String line, String identifier){
        if(!Mwrite2.containsKey(t)){
            Mwrite2.put(t, new TreeMap<>());
        }
        Mwrite2.get(t).put(identifier, line);
    }
    public synchronized void writeLine2(String period, String cal, String identifier){
        if(!Mwrite3.containsKey(period)){
            Mwrite3.put(period, new TreeMap<>());
        }
        Mwrite3.get(period).put(identifier, cal);
    }
    
    public synchronized void period(int p){
        if(!Header2.containsKey(p)){
            Header2.put(p, p);
        }
    }
    public void writePeriod() throws IOException{
        writer=new BufferedWriter(new FileWriter("pl//"+"Periodos"+".pl", true));
        for (Map.Entry<Integer, Integer> e : Header2.entrySet()){
            writer.write("periodo("+e.getValue()+").");
            writer.newLine();
            writer.flush();
        }
        writer.close();

    }
    
    public void close() throws IOException {
        // Write contents
        for (Map.Entry<Integer, String> e : Mwrite.entrySet()){
            writer.write(e.getValue());
            writer.newLine();
            writer.flush();
        }
        
        // Close files
        reader=null;
        writer=null;
        if (reader!=null) {
            reader.close();	
        }
        if(writer!=null) {
            writer.close();
        }
    }
    public void close2() throws IOException{
        for (Map.Entry<Integer, TreeMap<String, String>> e : Mwrite2.entrySet()){
            TreeMap<String, String> f=e.getValue();
            String Tline=e.getKey().toString();
            for(Map.Entry<String, String> g : f.entrySet()){
                Tline+=","+g.getValue();
            }
            writer.write(Tline);
            writer.newLine();
            writer.flush();
        }
        
        
        // Close files
        reader=null;
        writer=null;
        if (reader!=null) {
            reader.close();	
        }
        if(writer!=null) {
            writer.close();
        }
    }
    public void close3() throws IOException{
        for (Map.Entry<String, TreeMap<String, String>> e : Mwrite3.entrySet()){
            TreeMap<String, String> f=e.getValue();
            String Tline=e.getKey();
            for(Map.Entry<String, String> g : f.entrySet()){
                Tline+=","+g.getValue();
            }
            writer.write(Tline);
            writer.newLine();
            writer.flush();
        }
        
        
        // Close files
        reader=null;
        writer=null;
        if (reader!=null) {
            reader.close();	
        }
        if(writer!=null) {
            writer.close();
        }
    }
}