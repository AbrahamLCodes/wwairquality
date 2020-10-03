package marshmallow.spaceapps;

import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layer.RenderableLayer;
import gov.nasa.worldwind.render.Color;
import gov.nasa.worldwind.shape.Ellipse;
import gov.nasa.worldwind.shape.ShapeAttributes;

public class Actions {


    public void makeEllipse(int latitude, int longitude, int altitude, int majorRadius, int minorRadius, WorldWindow wwd){
        // Create a surface ellipse with the default attributes, a 500km major-radius and a 300km minor-radius. Surface
        // ellipses are configured with a CLAMP_TO_GROUND altitudeMode and followTerrain set to true.
        Ellipse ellipse = new Ellipse(new Position(latitude, longitude, altitude), majorRadius, minorRadius);
        ellipse.setAltitudeMode(WorldWind.CLAMP_TO_GROUND); // clamp the ellipse's center position to the terrain surface
        ellipse.setFollowTerrain(true); // cause the ellipse geometry to follow the terrain surface

        RenderableLayer tutorialLayer = new RenderableLayer();
        tutorialLayer.addRenderable(ellipse);



        wwd.getLayers().addLayer(tutorialLayer);
    }

    public void makeCustomEllipse(int latitude, int longitude, int altitude, int majorRadius, int minorRadius, WorldWindow wwd){

        // Create a surface ellipse with with custom attributes that make the interior 50% transparent and increase the
        // outline width.
        ShapeAttributes attrs = new ShapeAttributes();
        attrs.setInteriorColor(new Color(1, 1, 1, 0.5f)); // 50% transparent white
        attrs.setOutlineWidth(3);


        Ellipse ellipse = new Ellipse(new Position(latitude, longitude, altitude), majorRadius, minorRadius, attrs);
        ellipse.setAltitudeMode(WorldWind.CLAMP_TO_GROUND); // clamp the ellipse's center position to the terrain surface
        ellipse.setFollowTerrain(true); // cause the ellipse geometry to follow the terrain surface
        ellipse.putUserProperty("SELECTABLE", null);

        RenderableLayer tutorialLayer = new RenderableLayer();
        tutorialLayer.addRenderable(ellipse);
        wwd.getLayers().addLayer(tutorialLayer);
    }
}
