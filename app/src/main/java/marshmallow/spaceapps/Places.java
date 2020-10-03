package marshmallow.spaceapps;

import java.util.ArrayList;

public class Places {

    private ArrayList<Location> locations;

    public Places(){
        locations = new ArrayList<>();
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
    }
}
