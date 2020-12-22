package com.cobacobms.finalproject.entity;

public class Coordination {
    String longitude;
    String latitude;
    String altitude;

    public Coordination(String longitude, String latitude, String altitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
    }

    public Coordination(String longitude, String latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }
}
