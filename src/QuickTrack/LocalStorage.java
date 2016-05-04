/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QuickTrack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
 
public class LocalStorage {
 
    Connection c = null;
    Statement s;
    
    public LocalStorage(){
        
        try {

            c = DriverManager.getConnection("jdbc:sqlite:resources/QuickTrack.db",
                    "postgres", "root");
            
            s = c.createStatement();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void set(String name, String value)
    {
        String query = "INSERT OR IGNORE INTO quicktrack VALUES (\""+name+"\", \""+value+"\")";
        String update = "UPDATE quicktrack SET value = \""+value+"\" WHERE name = \""+name+"\";";  
       
        try {
            s.execute(query);
            s.execute(update);
            s.close();
        } catch (SQLException ex) {
            Logger.getLogger(LocalStorage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String get(String name)
    {
        try {
            
            String query = "SELECT value FROM quicktrack WHERE name = \""+name+"\"";
            
            ResultSet result = s.executeQuery(query);
      
            if(result.next())
            {
              return result.getString("value");  
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(LocalStorage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void close()
    {
        try {
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(LocalStorage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
}