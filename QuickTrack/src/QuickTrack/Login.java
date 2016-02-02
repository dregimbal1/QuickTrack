/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QuickTrack;

import java.util.HashMap;

/**
 * The login class will work directly with AccountsManagement.
 * 
 * @author David Regimbal, Aras Masalaitis, Jesse Wasko, Sumedh Savanur, Gauri Khawadkar <bk.psu.edu>
 */
public class Login {
   
    public static String doLogin(String url, String username, String password) {

        /**
         * Validate incoming data.
         */
        if(url.isEmpty())
            return "Please enter a valid URL";
        if(username.isEmpty())
            return "Please enter a username";
        if(password.isEmpty())
            return "Please enter a password";
        
        /**
         * Store data into an array.
         */
        HashMap request = new HashMap();
        request.put("username", username);
        request.put("password", password);
        
        /**
         * Initialize server connection.
         */
        Server auth = new Server(url);
        
        /**
         * Send the outbound request.
         * 
         * @return JSON object
         */
        return auth.call("POST", "/login", request);
        
    }
    
    public static boolean forgotPassword(String email) {
        
        return true;
    }
    
}
