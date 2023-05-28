package com.example.computechfinalist;

public class acts
{
    private String driverx;
    private String placex;
    private String datex;
    private String pricex;
    public acts(String pricex, String placex, String datex, String driverx){
        this.driverx =driverx;
        this.datex =datex;
        this.placex =placex;
        this.pricex =pricex;

    }

    public String getPricex() {
        return pricex;
    }

    public String getDatex() {
        return datex;
    }

    public String getPlacex() {
        return placex;
    }

    public String getDriverx() {
        return driverx;
    }
}
