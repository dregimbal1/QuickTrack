/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QuickTrack.Accounts;

/**
 * AccountBuilder is the middle man to prepping data to send to the server
 * and store locally. 
 * @author David Regimbal, Aras Masalaitis, Jesse Wasko, Sumedh Savanur, Gauri Khawadkar <bk.psu.edu>
 */
public class AccountBuilder {

    private String _username;
    private String _url;
    
    public boolean buildAccount()
    {
        return Accounts.insertAccount(_username, _url);
    }
    
    public AccountBuilder username(String _username)
    {
        this._username = _username;
        return this;
    }

    public AccountBuilder url(String _url)
    {
        this._url = _url;
        return this;
    }
    
}
