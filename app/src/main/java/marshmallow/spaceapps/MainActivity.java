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

    public void showHelp(){
        FragmentTransaction ft  = getSupportFragmentManager().beginTransaction();
        HelpFragment fg = new HelpFragment();
        ft.replace(R.id.mainFrame, fg);
        ft.commit();
    }
}