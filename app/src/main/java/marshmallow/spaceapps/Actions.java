package marshmallow.spaceapps;

import androidx.fragment.app.FragmentManager;

import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.geom.Offset;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layer.RenderableLayer;
import gov.nasa.worldwind.render.Color;
import gov.nasa.worldwind.render.ImageSource;
import gov.nasa.worldwind.shape.Ellipse;
import gov.nasa.worldwind.shape.ShapeAttributes;

public class Actions {

    public void openDialog(FragmentManager fragmentManager, Location location) {
        Dialog dialog = new Dialog(location);
        dialog.show(fragmentManager, "Info Dialog");
    }

    public void openItemDialog(FragmentManager fragmentManager, String q, String a){
        ItemFragment dialog = new ItemFragment(q, a);
        dialog.show(fragmentManager, "Info Dialog");
    }

}
