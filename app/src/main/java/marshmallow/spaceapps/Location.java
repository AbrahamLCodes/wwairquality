package marshmallow.spaceapps;

public class Location {
    private int id;
    private String region;
    private int latitud;
    private int longitud;


    public Location(int id, String region, int latitud, int longitud) {
        this.id = id;
        this.region = region;
        this.latitud = latitud;
        this.longitud = longitud;
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

    public int getLatitud() {
        return latitud;
    }

    public void setLatitud(int latitud) {
        this.latitud = latitud;
    }

    public int getLongitud() {
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
}
