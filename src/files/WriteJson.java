/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;

import com.google.gson.Gson;
import contents.Content;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author hyeoxx
 */
public class WriteJson {
    public static void write(Object a) {
        Gson gson = new Gson();
        String json = gson.toJson(a);
        
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("plans.json"));
            out.write(json);
            out.close(); 
  
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
