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

public class PrologFiles {
    private BufferedReader reader;
    private BufferedWriter writer;
    private final Map<Integer, String> Mread, Mwrite;

    public PrologFiles() {
        Mread=new HashMap<>();
        Mwrite=new HashMap<>();
    }

    public void setImport(String file, boolean overwrite) throws IOException {
        reader = Files.newBufferedReader(Paths.get("excel//" + file + ".csv"), StandardCharsets.UTF_8);
            
        //Delete target contents
        if(overwrite) {
            new FileOutputStream("pl//"+file+".pl").close();
        }
        writer=new BufferedWriter(new FileWriter("pl//"+file+".pl", true));

        //Just header
        reader.readLine();
        
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
    
    public void setExport(String file, String header) throws FileNotFoundException, IOException { 
        //Delete target contents
        new FileOutputStream("excel//"+file+".csv").close();


        writer=new BufferedWriter(new OutputStreamWriter(new FileOutputStream("excel//"+file+".csv"), StandardCharsets.UTF_8));

        //Just header           
        writer.write(header);
        writer.newLine();
        writer.flush();

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
    
    public void close() throws IOException {
        // Write contents
        for(int i=0;i<Mwrite.size();i++){
            writer.write(Mwrite.get(i));
            writer.newLine();
            writer.flush();
        }
        
        // Close files
        if (reader!=null) {
            reader.close();	
        }
        if(writer!=null) {
            writer.close();
        }
    }
}