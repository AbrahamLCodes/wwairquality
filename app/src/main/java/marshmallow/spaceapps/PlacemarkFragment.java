package marshmallow.spaceapps;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import gov.nasa.worldwind.BasicWorldWindowController;
import gov.nasa.worldwind.PickedObject;
import gov.nasa.worldwind.PickedObjectList;
import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.geom.Offset;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layer.RenderableLayer;
import gov.nasa.worldwind.render.ImageSource;
import gov.nasa.worldwind.shape.Highlightable;
import gov.nasa.worldwind.shape.Placemark;
import gov.nasa.worldwind.shape.PlacemarkAttributes;


public class PlacemarkFragment extends BasicGlobeFragment {

    private static final double NORMAL_IMAGE_SCALE = 0.1;

    private static final double HIGHLIGHTED_IMAGE_SCALE = 0.2;

    private static int contador;

    private static Places places;

    private Actions actions;

    /**
     * Creates a new WorldWindow (GLSurfaceView) object with a WMS Layer
     *
     * @return The WorldWindow object containing the globe.
     */
    @Override
    public WorldWindow createWorldWindow() {
        // Let the super class (BasicGlobeFragment) do the creation
        WorldWindow wwd = super.createWorldWindow();

        // Override the WorldWindow's built-in navigation behavior by adding picking support.
        wwd.setWorldWindowController(new PickNavigateController());

        // Add a layer for placemarks to the WorldWindow
        RenderableLayer layer = new RenderableLayer("Placemarks");
        wwd.getLayers().addLayer(layer);

        contador = 0;
        places = new Places();
        actions = new Actions();

        // Create a few placemarks with highlight attributes and add them to the layer
        layer.addRenderable(createAirportPlacemark(Position.fromDegrees(-23, -54, 0), "El Amazonas xd"));
        layer.addRenderable(createAirportPlacemark(Position.fromDegrees(20, -101, 0), "El Defectuso"));
        layer.addRenderable(createAirportPlacemark(Position.fromDegrees(33, -117, 0), "Los Angeles"));

        // Position the viewer to look near the airports


        return wwd;
    }

    /**
     * Helper method to create airport placemarks.
     */
    private static Placemark createAirportPlacemark(Position position, String region) {

        Placemark placemark = Placemark.createWithImage(position, ImageSource.fromResource(R.drawable.contamination));
        placemark.getAttributes().setImageOffset(Offset.bottomCenter()).setImageScale(NORMAL_IMAGE_SCALE);
        placemark.setHighlightAttributes(new PlacemarkAttributes(placemark.getAttributes()).setImageScale(HIGHLIGHTED_IMAGE_SCALE));
        placemark.setDisplayName(region);

        places.getLocations().add(contador, new Location(contador, region, (int) position.latitude,
                (int)position.longitude));

        contador++;

        return placemark;
    }

    /**
     * This inner class is a custom WorldWindController that handles both picking and navigation via a combination of
     * the native WorldWind navigation gestures and Android gestures. This class' onTouchEvent method arbitrates
     * between pick events and globe navigation events.
     */
    public class PickNavigateController extends BasicWorldWindowController {

        protected Object pickedObject;          // last picked object from onDown events

        protected Object selectedObject;        // last "selected" object from single tap

        /**
         * Assign a subclassed SimpleOnGestureListener to a GestureDetector to handle the "pick" events.
         */
        protected GestureDetector pickGestureDetector = new GestureDetector(
                getContext().getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent event) {
                pick(event);    // Pick the object(s) at the tap location
                return false;   // By not consuming this event, we allow it to pass on to the navigation gesture handlers
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                toggleSelection();  // Highlight the picked object

                // By not consuming this event, we allow the "up" event to pass on to the navigation gestures,
                // which is required for proper zoom gestures.  Consuming this event will cause the first zoom
                // gesture to be ignored.  As an alternative, you can implement onSingleTapConfirmed and consume
                // event as you would expect, with the trade-off being a slight delay tap response.
                return false;
            }
        });

        /**
         * Delegates events to the pick handler or the native WorldWind navigation handlers.
         */
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            // Allow pick listener to process the event first.
            boolean consumed = this.pickGestureDetector.onTouchEvent(event);

            // If event was not consumed by the pick operation, pass it on the globe navigation handlers
            if (!consumed) {

                // The super class performs the pan, tilt, rotate and zoom
                return super.onTouchEvent(event);
            }
            return consumed;
        }

        /**
         * Performs a pick at the tap location.
         */
        public void pick(MotionEvent event) {
            // Forget our last picked object
            this.pickedObject = null;

            // Perform a new pick at the screen x, y
            PickedObjectList pickList = getWorldWindow().pick(event.getX(), event.getY());

            // Get the top-most object for our new picked object
            PickedObject topPickedObject = pickList.topPickedObject();
            int index = topPickedObject.getIdentifier();

            if (topPickedObject != null) {
                this.pickedObject = topPickedObject.getUserObject();
                if(index > 1){
                    actions.openDialog(getActivity().getSupportFragmentManager(), places.getLocations().get(index - 2));
                }
            }
        }

        /**
         * Toggles the selected state of a picked object.
         */
        public void toggleSelection() {

            // Display the highlight or normal attributes to indicate the
            // selected or unselected state respectively.
            if (pickedObject instanceof Highlightable) {

                // Determine if we've picked a "new" object so we know to deselect the previous selection
                boolean isNewSelection = pickedObject != this.selectedObject;

                // Only one object can be selected at time, deselect any previously selected object
                if (isNewSelection && this.selectedObject instanceof Highlightable) {
                    ((Highlightable) this.selectedObject).setHighlighted(false);
                }

                // Show the selection by showing its highlight attributes
                ((Highlightable) pickedObject).setHighlighted(isNewSelection);
                this.getWorldWindow().requestRedraw();

                // Track the selected object
                this.selectedObject = isNewSelection ? pickedObject : null;
            }
        }
    }
}