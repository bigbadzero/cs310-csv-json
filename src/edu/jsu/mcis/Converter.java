package edu.jsu.mcis;

import java.io.*;
import java.util.*;
import com.opencsv.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Converter {
    
    /*
    
        Consider the following CSV data:
        
        "ID","Total","Assignment 1","Assignment 2","Exam 1"
        "111278","611","146","128","337"
        "111352","867","227","228","412"
        "111373","461","96","90","275"
        "111305","835","220","217","398"
        "111399","898","226","229","443"
        "111160","454","77","125","252"
        "111276","579","130","111","338"
        "111241","973","236","237","500"
        
        The corresponding JSON data would be similar to the following (tabs and
        other whitespace have been added for clarity).  Note the curly braces,
        square brackets, and double-quotes!  These indicate which values should
        be encoded as strings, and which values should be encoded as integers!
        
        {
            "colHeaders":["ID","Total","Assignment 1","Assignment 2","Exam 1"],
            "rowHeaders":["111278","111352","111373","111305","111399","111160",
            "111276","111241"],
            "data":[[611,146,128,337],
                    [867,227,228,412],
                    [461,96,90,275],
                    [835,220,217,398],
                    [898,226,229,443],
                    [454,77,125,252],
                    [579,130,111,338],
                    [973,236,237,500]
            ]
        }
    
        Your task for this program is to complete the two conversion methods in
        this class, "csvToJson()" and "jsonToCsv()", so that the CSV data shown
        above can be converted to JSON format, and vice-versa.  Both methods
        should return the converted data as strings, but the strings do not need
        to include the newlines and whitespace shown in the examples; again,
        this whitespace has been added only for clarity.
    
        NOTE: YOU SHOULD NOT WRITE ANY CODE WHICH MANUALLY COMPOSES THE OUTPUT
        STRINGS!!!  Leave ALL string conversion to the two data conversion
        libraries we have discussed, OpenCSV and json-simple.  See the "Data
        Exchange" lecture notes for more details, including example code.
    
    */
    
    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {
        
        String results = "";
        
        try {
            
            CSVReader reader = new CSVReader(new StringReader(csvString));
            List<String[]> full = reader.readAll();
            Iterator<String[]> iterator = full.iterator();
            
            // INSERT YOUR CODE HERE
            LinkedHashMap<String,ArrayList> jsonObject = new LinkedHashMap<>();
            ArrayList<String> columnHeader = new ArrayList<>();
            ArrayList<String> rowHeader = new ArrayList<>();
            ArrayList<ArrayList> data = new ArrayList<>();
  
            String[] line = iterator.next();
            
            for(String field : line){
                columnHeader.add(field);
            }
            while(iterator.hasNext()){
                line = iterator.next();
                ArrayList<Integer> row = new ArrayList<>();
                for(int i = 0; i< line.length; i++){
                    if(i == 0){
                        rowHeader.add(line[i]);
                    }
                    else{
                        row.add(Integer.parseInt(line[i]));
                    }
                }
                data.add(row);
            }
            jsonObject.put("rowHeaders", rowHeader);
            jsonObject.put("data", data);
            jsonObject.put("colHeaders", columnHeader);
            results = JSONValue.toJSONString(jsonObject);
            
            
            
            
        }        
        catch(Exception e) { return e.toString(); }
        
        return results.trim();
        
    }
    
    public static String jsonToCsv(String jsonString) {
        
        String results = "";
        
        try {

            StringWriter writer = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(writer, ',', '"', '\n');
            
            // INSERT YOUR CODE HERE
            
            //parse JSON Data to a JSONObject
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject)parser.parse(jsonString);
            
            //gets the JSON data from the JSONObject and stores into JSONArrays
            
            JSONArray colHeaders = (JSONArray)jsonObject.get("colHeaders");
            JSONArray rowHeaders = (JSONArray)jsonObject.get("rowHeaders");
            JSONArray data = (JSONArray)jsonObject.get("data");
            
            // create empty csv containers as string arrays
            // will copy data from JSONArrays into String arrays
            
            String[] colStringArray = new String[colHeaders.size()];
            String[] rowStringArray = new String[rowHeaders.size()];
            String[] dataStringArray = new String[data.size()];
            
            for(int i =0; i < colHeaders.size(); i ++){
                colStringArray[i] = colHeaders.get(i).toString();
            }
            
            // outputs the column headers to the csv writer
            
            csvWriter.writeNext(colStringArray);
            
            
            
            for(int i = 0; i < rowHeaders.size(); i ++){
                rowStringArray[i] = rowHeaders.get(i).toString();
                dataStringArray[i] = data.get(i).toString();
            }
            
            // iterates through the lists of row headers and row data
            // combine each into a single array of strings
            
            for (int i = 0; i < dataStringArray.length; i++){
                //parses row data into JSON array called "dataValues"
                JSONArray dataValues = (JSONArray)parser.parse(dataStringArray[i]);
                //create a row array
                String[] row = new String[dataValues.size() + 1];
                
                row[0] = rowStringArray[i];
                for(int j = 0; j<dataValues.size(); j++){
                    row[j + 1] = dataValues.get(j).toString();
                }
                csvWriter.writeNext(row);
                
                }
                
       
                
                
                
                //insert code here
                
                
                // output it to the csv writer
                
                
            
            
            results = writer.toString();
            
            
            
            
            
            
        }
        
        catch(Exception e) { return e.toString(); }
        
        return results.trim();
        
    }

}