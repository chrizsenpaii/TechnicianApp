package com.example.computechfinalist;

public class Technician {

    private String name;
    private String username;
    private String email;
    private String city;
    private int phone;
    public Technician(String name,String username, String email, String city,int phone ){

            this.name =name;
            this.username =username;
            this.email =email;
            this.city =city;
            this.phone =phone;
    }



    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    public int getPhone() {
        return phone;
    }
}
