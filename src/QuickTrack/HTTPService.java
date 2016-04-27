/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QuickTrack;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import org.apache.http.client.methods.HttpGet;

/**
 * HTTP Service.
 * This is the outbound service to our remote server.
 * 
 * Available endpoints:
 *  login
 *  register
 *  addTask
 *  getTask
 *  createGroup
 *  joinGroup
 *  getGroups
 *  leaveGroup
 *  getDetails
 * 
 * 
 * @author David Regimbal, Aras Masalaitis, Jesse Wasko, Sumedh Savanur, Gauri Khawadkar <bk.psu.edu>
 */
public class HTTPService {
    
    // local store and initialize
    public static String access_token = "";
    public static String server_url = "";
    public static int userId;
    
    LocalStorage storage = new LocalStorage();
    
    public HTTPService()
    {
        access_token = storage.get("access_token");
        server_url = storage.get("server_url");
        userId = Integer.parseInt(storage.get("userId"));
    }
    
    /**
     * Login Service.
     * Request the server to login a user. 
     * 
     * @param serverUrl
     * @param username
     * @param password
     * @return JSONObject callback
     * @throws ClientProtocolException
     * @throws IOException 
     */
    public JSONObject login(String serverUrl, String username, String password) throws ClientProtocolException, IOException
    { 
        
        JSONObject callback = new JSONObject();
        
        // set the server_url and localstorage
        server_url = serverUrl;
        
        // Connect out to the server URL
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(serverUrl + "/login");
        StringEntity input = new StringEntity("{\"username\":\""+username+"\",\"password\":\""+password+"\"}");
        post.setEntity(input);
        HttpResponse response = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        while ((line = rd.readLine()) != null) {
         
         callback = new JSONObject(line);
         if("error".equals(callback.getString("status")))
         {
             // Login failed
         }
         else
         {
             // Login success; Set and store the access token
             JSONObject data = (JSONObject) callback.get("response");
             access_token = data.getString("token");
             storage.set("access_token", access_token);
         }
         
         return callback;

        }
        
        // Promise
        return callback;
           
    }
    
    /**
     * Register Service.
     * Create a new account and log them in
     * 
     * @param serverUrl
     * @param serverKey
     * @param username
     * @param password
     * @param email
     * @return JSONObject callback
     * @throws org.apache.http.client.ClientProtocolException 
     */
    public JSONObject register(String serverUrl, String serverKey, String username, String password, String email) throws ClientProtocolException, IOException
    {
        JSONObject callback = new JSONObject();
        
        // set the server_url and localstorage
        server_url = serverUrl;
        
        // Connect out to the server URL
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(serverUrl + "/register");
        StringEntity input = new StringEntity("{\"username\":\""+username+"\",\"password\":\""+password+"\",\"email\":\""+email+"\",\"key\":\""+serverKey+"\"}");
        post.setEntity(input);
        HttpResponse response = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        while ((line = rd.readLine()) != null) {
         
         callback = new JSONObject(line);
         if("error".equals(callback.getString("status")))
         {
             // Register failed
         }
         else
         {
             // Register success; Set and store the access token
             JSONObject data = (JSONObject) callback.get("response");
             access_token = data.getString("token");
             storage.set("access_token", access_token);
         }
         
         return callback;

        }
        
        // Promise
        return callback;
    }
    
    /**
     * addTask.
     * Create a new account and log them in
     * 
     * @return JSONObject callback
     * @throws org.apache.http.client.ClientProtocolException 
     */
    public static JSONObject addTask(String name, String description, Date date, Boolean notify) throws ClientProtocolException, IOException
    {
        
        // initialize our object
        JSONObject callback = new JSONObject();
        
        // Connect out to the server URL
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(server_url + "/queryInsert");
        
        // This request must be authenticated
        post.setHeader("IstAuth", "Bearer " + access_token);
        post.setHeader("Content-Type","application/json");
        
        // convert due date to UNIX
        long unixTime = date.getTime()/1000;
        
        // Build our JSON payload
        StringEntity input = new StringEntity("{\"table\": \"tasks\", \"data\": { \"name\": \""+name+"\", \"description\": \""+description+"\", \"duedate\": \""+unixTime+"\", \"notify\": \""+notify.booleanValue()+"\"  }}");
        post.setEntity(input);
        
        // Call our server
        HttpResponse response = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        while ((line = rd.readLine()) != null) {
         
            // Return our JSON response
            return new JSONObject(line);

        }
     
        // Promise
        return callback;
    }
    
    /**
     * editTask.
     * Update an already made task
     * 
     * @return JSONObject callback
     * @throws org.apache.http.client.ClientProtocolException 
     */
    public static JSONObject editTask(int id, String name, String description, Date date) throws ClientProtocolException, IOException
    {
        
        // initialize our object
        JSONObject callback = new JSONObject();
        
        // Connect out to the server URL
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(server_url + "/queryUpdate");
        
        // This request must be authenticated
        post.setHeader("IstAuth", "Bearer " + access_token);
        post.setHeader("Content-Type","application/json");
        
        // convert due date to UNIX
        long unixTime = date.getTime()/1000;
        
        // Build our JSON payload
        StringEntity input = new StringEntity("{\"table\":\"tasks\", \"id\": "+id+",\"data\":{\"name\":\""+name+"\",\"description\":\""+description+"\",\"duedate\":\""+date+"\"}}");
        post.setEntity(input);
        
        // Call our server
        HttpResponse response = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        while ((line = rd.readLine()) != null) {
         
            // Return our JSON response
            return new JSONObject(line);

        }
     
        // Promise
        return callback;
    }
    
    /**
     * getGroupTasks.
     * get all tasks for a specific user
     * 
     * @return JSONObject callback
     * @throws org.apache.http.client.ClientProtocolException 
     */
    public static JSONObject getGroupTasks() throws ClientProtocolException, IOException
    {
        
        // initialize our object
        JSONObject callback = new JSONObject();
        
        // Connect out to the server URL
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(server_url + "/querySelect");
        
        // This request must be authenticated
        post.setHeader("IstAuth", "Bearer " + access_token);
        post.setHeader("Content-Type","application/json");
        
        // Build our JSON payload
        StringEntity input = new StringEntity("{\"table\": \"tasks\", \"fields\": \"*\", \"where\": { \"createdBy\":\"" + userId + "\", \"notify\": \"true\"}}");
        post.setEntity(input);
        
        // Call our server
        HttpResponse response = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        while ((line = rd.readLine()) != null) {
            
            // Return our JSON response
            return new JSONObject(line);

        }
     
        // Promise
        return callback;
    }
    
    /**
     * getTasks.
     * get all tasks for a specific user
     * 
     * @return JSONObject callback
     * @throws org.apache.http.client.ClientProtocolException 
     */
    public static JSONObject getTasks() throws ClientProtocolException, IOException
    {
        
        // initialize our object
        JSONObject callback = new JSONObject();
        
        // Connect out to the server URL
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(server_url + "/querySelect");
        
        // This request must be authenticated
        post.setHeader("IstAuth", "Bearer " + access_token);
        post.setHeader("Content-Type","application/json");
        
        // Build our JSON payload
        StringEntity input = new StringEntity("{\"table\": \"tasks\", \"fields\": \"*\", \"where\": { \"createdBy\":\"" + userId + "\"}}");
        post.setEntity(input);
        
        // Call our server
        HttpResponse response = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        while ((line = rd.readLine()) != null) {
            
            // Return our JSON response
            return new JSONObject(line);

        }
     
        // Promise
        return callback;
    }
 
    /**
     * createGroup.
     * make a new group with this user as the owner
     * 
     * @return JSONObject callback
     * @throws org.apache.http.client.ClientProtocolException 
     */
    public static JSONObject createGroup(String name) throws ClientProtocolException, IOException
    {
        
        // initialize our object
        JSONObject callback = new JSONObject();
        
        // Connect out to the server URL
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(server_url + "/queryInsert");
        
        // This request must be authenticated
        post.setHeader("IstAuth", "Bearer " + access_token);
        post.setHeader("Content-Type","application/json");
        
        // Build our JSON payload
        StringEntity input = new StringEntity("{\"table\": \"groups\", \"data\": { \"name\":\"" + name + "\"}}");
        post.setEntity(input);
        
        // Call our server
        HttpResponse response = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        while ((line = rd.readLine()) != null) {
            
            // Return our JSON response
            return new JSONObject(line);

        }
     
        // Promise
        return callback;
    }
    
    /**
     * joinGroup.
     * add a user to specified group id
     * 
     * @return JSONObject callback
     * @throws org.apache.http.client.ClientProtocolException 
     */
    public static JSONObject joinGroup(String id) throws ClientProtocolException, IOException
    {
        
        // initialize our object
        JSONObject callback = new JSONObject();
        
        // Connect out to the server URL
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(server_url + "/querySelect");
        
        // This request must be authenticated
        post.setHeader("IstAuth", "Bearer " + access_token);
        post.setHeader("Content-Type","application/json");
        
        // Build our JSON payload
        StringEntity input = new StringEntity("{\"table\": \"groups\", \"fields\": \"*\", \"where\": { \"createdBy\":\"" + userId + "\"}}");
        post.setEntity(input);
        
        // Call our server
        HttpResponse response = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        while ((line = rd.readLine()) != null) {
            
            // Return our JSON response
            return new JSONObject(line);

        }
     
        // Promise
        return callback;
    }
    
    /**
     * getGroups.
     * get all group[s for a specific user
     * 
     * @return JSONObject callback
     * @throws org.apache.http.client.ClientProtocolException 
     */
    public static JSONObject getGroups() throws ClientProtocolException, IOException
    {
        
        // initialize our object
        JSONObject callback = new JSONObject();
        
        // Connect out to the server URL
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(server_url + "/querySelect");
        
        // This request must be authenticated
        post.setHeader("IstAuth", "Bearer " + access_token);
        post.setHeader("Content-Type","application/json");
       
        // Build our JSON payload
        ///StringEntity input = new StringEntity("{\"table\": \"groups, usersGroupsLink\", \"fields\": \"groups.*\", \"where\": { \"groups.id\": \"usersGroupsLink.id\", \"usersGroupsLink.userId\":\"%USERID%\"}}");
        StringEntity input = new StringEntity("{\"table\": \"groups\", \"fields\": \"*\", \"where\": { \"createdBy\":\"" + userId + "\"}}");
        post.setEntity(input);
        
        // Call our server
        HttpResponse response = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        while ((line = rd.readLine()) != null) {
            
            // Return our JSON response
            return new JSONObject(line);

        }
     
        // Promise
        return callback;
    }
    
    /**
     * leaveGroup.
     * remove the user from a specified group
     * 
     * @return JSONObject callback
     * @throws org.apache.http.client.ClientProtocolException 
     */
    public static JSONObject leaveGroup(int id) throws ClientProtocolException, IOException
    {
        
        // initialize our object
        JSONObject callback = new JSONObject();
        
        // Connect out to the server URL
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(server_url + "/leaveGroup");
        
        // This request must be authenticated
        post.setHeader("IstAuth", "Bearer " + access_token);
        post.setHeader("Content-Type","application/json");
        
        // Build our JSON payload
        StringEntity input = new StringEntity("{ \"groupId\":" + id + "}");
        post.setEntity(input);
        
        // Call our server
        HttpResponse response = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        while ((line = rd.readLine()) != null) {
            System.out.println("line = " + line);
            // Return our JSON response
            return new JSONObject(line);

        }
     
        // Promise
        return callback;
    }
 
    public static JSONObject getTask(int id) throws IOException
    {
        
                
        // initialize our object
        JSONObject callback = new JSONObject();
        
        // Connect out to the server URL
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(server_url + "/querySelect");
        
        // This request must be authenticated
        post.setHeader("IstAuth", "Bearer " + access_token);
        post.setHeader("Content-Type","application/json");
        
        // Build our JSON payload
        StringEntity input = new StringEntity("{\"table\": \"tasks\", \"where\":{ \"id\":"+id+"}}");
        post.setEntity(input);
        
        // Call our server
        HttpResponse response = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        while ((line = rd.readLine()) != null) {
            
            // Return our JSON response
            return new JSONObject(line);

        }
     
        // Promise
        return callback;
        
    }
        
    public static JSONObject getDetails() throws IOException
    {
        JSONObject callback = new JSONObject();

        if(access_token != null)
        {   

            // Connect out to the server URL
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(server_url + "/getUserDetails");
            request.setHeader("IstAuth", "Bearer " + access_token);
            request.setHeader("Content-Type","application/json");
            HttpResponse response = client.execute(request);
            BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            
            while ((line = rd.readLine()) != null) {
                
              callback = new JSONObject(line);
              JSONObject data = (JSONObject) callback.get("response");
              userId = Integer.parseInt(data.getString("id"));

              return callback;
            }
            
        }// if not null

        // Promise
        return callback;
    }
    
}
