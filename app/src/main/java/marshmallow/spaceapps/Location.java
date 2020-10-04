package marshmallow.spaceapps;

import android.graphics.Bitmap;

public class Location {
    private int id;
    private String region;
    private double latitud, longitud;
    private double density, monoxide, dioxide;
    private Bitmap image;

    //Constructor to test weird things

    public Location(int id, String region, double latitud, double longitud, double density,
                    double monoxide, double dioxide) {
        this.id = id;
        this.region = region;
        this.latitud = latitud;
        this.longitud = longitud;
        this.density = density;
        this.monoxide = monoxide;
        this.dioxide = dioxide;
    }

    //int id, double lat, double lon, String region, double density,
    //double monc, double dion, Bitmap img

    public Location(int id, double latitud, double longitud, String region ,double density,
                    double monoxide, double dioxide, Bitmap image) {
        this.id = id;
        this.region = region;
        this.latitud = latitud;
        this.longitud = longitud;
        this.density = density;
        this.monoxide = monoxide;
        this.dioxide = dioxide;
        this.image = image;
    }

    public Location(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(int latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public double getDensity() {
        return density;
    }

    public void setDensity(double density) {
        this.density = density;
    }

    public double getDioxide() {
        return dioxide;
    }

    public void setDioxide(double dioxide) {
        this.dioxide = dioxide;
    }

    public double getMonoxide() {
        return monoxide;
    }

    public void setMonoxide(double monoxide) {
        this.monoxide = monoxide;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
