package marshmallow.spaceapps;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
    private static Context context;
    private static Bitmap bm;
    private FloatingActionButton fb;


    public PlacemarkFragment() {
        contador = 0;
        places = new Places();
        actions = new Actions();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fb = view.findViewById(R.id.fab);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                HelpFragment fg = new HelpFragment();
                ft.replace(R.id.mainFrame, fg);
                ft.commit();

            }
        });
    }

    /**
     * Creates a new WorldWindow (GLSurfaceView) object with a WMS Layer
     *
     * @return The WorldWindow object containing the globe.
     */
    @Override
    public WorldWindow createWorldWindow() {
        bm = BitmapFactory.decodeResource(getResources(), R.drawable.chihuahua);
        context = getContext();
        // Let the super class (BasicGlobeFragment) do the creation
        WorldWindow wwd = super.createWorldWindow();

        // Override the WorldWindow's built-in navigation behavior by adding picking support.
        wwd.setWorldWindowController(new PickNavigateController());


        // Add a layer for placemarks to the WorldWindow
        RenderableLayer layer = new RenderableLayer("Placemarks");


        DBManager dbManager = new DBManager(getContext());

        if (dbManager.getCount() == 0) {
            //Creating and inserting placemarks into BasicGlobe and Database
            layer.addRenderable(createPlacemark(
                    Position.fromDegrees(
                            20.5895
                            , -103.2831
                            , 0)
                    , 1000
                    , "Guadalajara"
                    , 90
                    , 2.28));

            layer.addRenderable(createPlacemark(
                    Position.fromDegrees(
                            19.4978
                            , -99.1269
                            , 0)
                    , 1000
                    , "Ciudad de Mexico"
                    , 120
                    , 9.28));

            layer.addRenderable(createPlacemark(
                    Position.fromDegrees(
                            28.6353
                            , -106.089
                            , 0)
                    , 1000
                    , "Chihuhaua"
                    , 70
                    , 120));

            layer.addRenderable(createPlacemark(
                    Position.fromDegrees(
                            41.3879
                            , 2.16992
                            , 0)
                    , 1000
                    , "Barcelona"
                    , 140
                    , 4.37));

            layer.addRenderable(createPlacemark(
                    Position.fromDegrees(38.2297
                            , -4.9622
                            , 0)
                    , 325
                    , "Córdoba"
                    , 120
                    , 0.94));

            layer.addRenderable(createPlacemark(
                    Position.fromDegrees(
                            51.062
                            , -1.317
                            , 0)
                    , 75,
                    "Winchester"
                    , 160
                    , 3.28));

            layer.addRenderable(createPlacemark(
                    Position.fromDegrees(
                            34.0194
                            , -118.441
                            , 0),
                    1000
                    , "LA"
                    , 260
                    , 9.28));

            layer.addRenderable(createPlacemark(
                    Position.fromDegrees(
                            37.10
                            , -104.300
                            , 0),
                    925
                    , "Trinidad"
                    , 120
                    , 45));

            layer.addRenderable(createPlacemark(
                    Position.fromDegrees(
                            33.59
                            , -118.40
                            , 0),
                    925
                    , "Santa Maria"
                    , 200
                    , 0.94));


            dbManager.initList();
        } else {

            dbManager.initList();
            int i = 0;
            while (i < Places.getLocations().size()) {
                layer.addRenderable(returnPlacemark(new Position(
                                Places.getLocations().get(i).getLatitud()
                                , Places.getLocations().get(i).getLongitud()
                                , 0)
                        , Places.getLocations().get(i).getRegion()));
                i++;
            }
        }

        wwd.getLayers().addLayer(layer);
        return wwd;
    }

    /**
     * Helper method to create airport placemarks.
     */
    private static Placemark createPlacemark(Position position, double density, String region, double monoxide, double dioxide) {

        Placemark placemark = Placemark.createWithImage(position, ImageSource.fromResource(R.drawable.contamination));
        placemark.getAttributes().setImageOffset(Offset.bottomCenter()).setImageScale(NORMAL_IMAGE_SCALE);
        placemark.setHighlightAttributes(new PlacemarkAttributes(placemark.getAttributes()).setImageScale(HIGHLIGHTED_IMAGE_SCALE));
        placemark.setDisplayName(region);

        DBManager dbManager = new DBManager(context);
        Log.d("Column count", "     " + dbManager.getColumnCount());


        dbManager.insert(contador
                , position.latitude
                , position.longitude
                , region
                , density
                , monoxide
                , dioxide
                , bm);

        contador++;

        return placemark;
    }

    private static Placemark returnPlacemark(Position position, String region) {


        Placemark placemark = Placemark.createWithImage(position, ImageSource.fromResource(R.drawable.contamination));
        placemark.getAttributes().setImageOffset(Offset.bottomCenter()).setImageScale(NORMAL_IMAGE_SCALE);
        placemark.setHighlightAttributes(new PlacemarkAttributes(placemark.getAttributes()).setImageScale(HIGHLIGHTED_IMAGE_SCALE));
        placemark.setDisplayName(region);


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


            /**
             Message from Abraham Luna (Team's Android Developer for NASA Space Apps Challenge 2020).

             This part has a bug that i couldn't fix. It works but sometimes doesen't show the Placemark i want.
             I needed more time to study and modify the List sort algorithm used for this handle the picking
             and toggling. :(

             At least the idea is kinda showed.
             */

            if (topPickedObject != null) {
                this.pickedObject = topPickedObject.getUserObject();

                int index = topPickedObject.getIdentifier();
                if (index > 1) {
                    //Toast.makeText(getContext(), "Name: "+Places.getLocations().get(index-2).getRegion(), Toast.LENGTH_SHORT).show();
                    actions.openDialog(getActivity().getSupportFragmentManager(), Places.getLocations().get(index - 2));
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