package edu.upc.dsa.martianslog.models;

public class ProfileUser {

    private String name;
    private String surname;
    private String username;
    double coins;
    int fuel;
    int food;

    public ProfileUser(){}
    public ProfileUser(String name, String surname, String username, double coins){
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.coins = coins;
        this.fuel = fuel;
        this.food = food;
    }

    public double getCoins() {
        return coins;
    }

    public void setCoins(double coins) {
        this.coins = coins;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
