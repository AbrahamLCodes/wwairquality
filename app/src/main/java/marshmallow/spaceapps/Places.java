package marshmallow.spaceapps;

import java.util.ArrayList;

public class Places {

    private static ArrayList<Location> locations;

    public Places() {
        locations = new ArrayList<>();
    }

    public static ArrayList<Location> getLocations() {
        return locations;
    }

    public static void setLocations(ArrayList<Location> locations) {
        locations = locations;
    }
}
