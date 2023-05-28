package com.example.computechfinalist;

public class hist
{
    private String driver;
    private String place;
    private String date;
    private String price;
    public hist(String price, String place, String date, String driver){
        this.driver =driver;
        this.date =date;
        this.place =place;
        this.price =price;

    }

    public String getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }

    public String getPlace() {
        return place;
    }

    public String getDriver() {
        return driver;
    }
}
