/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QuickTrack;

import java.util.HashMap;

/**
 *
 * @author David Regimbal, Aras Masalaitis, Jesse Wasko, Sumedh Savanur, Gauri Khawadkar <bk.psu.edu>
 */
public class Server {
    
    String server = ""; // Where MySQL is hosted
    
    public Server(String url) {
        server = url;
    }
    
    /**
     * API endpoint to communicate authentication and database queries
     * @param method
     * @param request
     * @param body
     * @return 
     */
    public String call(String method, String request, HashMap body) {
        
        return "call method for server";
    }
    
}
