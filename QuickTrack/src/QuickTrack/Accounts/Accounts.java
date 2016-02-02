/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QuickTrack.Accounts;

/**
 * Accounts is a local database to keep track of connection details.
 * Passwords are not stored locally, unless specified. 
 * 
 * @author David Regimbal, Aras Masalaitis, Jesse Wasko, Sumedh Savanur, Gauri Khawadkar <bk.psu.edu>
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSetMetaData;


public class Accounts
{
    private static String dbURL = "jdbc:derby://localhost:1527/myDB;create=true;user=root;password=qTrack";
    private static String tableName = "accounts";
    private static Connection conn = null;
    private static Statement stmt = null;

    public static void main(String[] args)
    {
        createConnection();
        //insert function
        shutdown();
    }
    
    private static void createConnection()
    {
        try
        {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            conn = DriverManager.getConnection(dbURL); 
        }
        catch (Exception except)
        {
            except.printStackTrace();
        }
    }
    
    private static boolean _insertAccount(String username, String url)
    {
        try
        {
            boolean result;
            stmt = conn.createStatement();
            result = stmt.execute("insert into " + tableName + " values (null,'" + username + "','" + url +"')");
            stmt.close();
            
            return result;
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
          
            return false;
        }
    }
    
    public static boolean insertAccount(String username, String url)
    {
        return _insertAccount(username,url);
    }
    
    private static void selectAccount(int acctId)
    {
        try
        {
            stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("select * from " + tableName + " where id = " + acctId);
            ResultSetMetaData rsmd = results.getMetaData();
            int numberCols = rsmd.getColumnCount();
            for (int i=1; i<=numberCols; i++)
            {
                //print Column Names
                System.out.print(rsmd.getColumnLabel(i)+"\t\t");  
            }

            System.out.println("\n-------------------------------------------------");

            while(results.next())
            {
                int id = results.getInt(1);
                String restName = results.getString(2);
                String cityName = results.getString(3);
                System.out.println(id + "\t\t" + restName + "\t\t" + cityName);
            }
            results.close();
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    }
    
    private static void shutdown()
    {
        try
        {
            if (stmt != null)
            {
                stmt.close();
            }
            if (conn != null)
            {
                DriverManager.getConnection(dbURL + ";shutdown=true");
                conn.close();
            }           
        }
        catch (SQLException sqlExcept)
        {
            
        }

    }
}

