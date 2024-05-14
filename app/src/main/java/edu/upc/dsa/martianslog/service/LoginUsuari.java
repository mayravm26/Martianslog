package edu.upc.dsa.martianslog.service;

public class LoginUsuari
{
    private String username;
    private String password;

    public LoginUsuari() {}

    public LoginUsuari(String username, String password)
    {
        this();
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
