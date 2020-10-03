package marshmallow.spaceapps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showGlobe();
    }

    public void showGlobe(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        PlacemarkFragment bs  = new PlacemarkFragment();
        ft.replace(R.id.mainFrame, bs);
        ft.commit();
    }
}