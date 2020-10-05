package marshmallow.spaceapps;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class Dialog extends AppCompatDialogFragment {

    private TextView title, density, monoxide, dioxide, qa, btn;
    private Location location;
    private ImageView image;

    public Dialog(Location location) {

        this.location = location;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return inflater.inflate(R.layout.fragment_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title = view.findViewById(R.id.txtTit);
        density = view.findViewById(R.id.txtLat);
        monoxide = view.findViewById(R.id.txtLong);
        dioxide = view.findViewById(R.id.txtId);
        qa = view.findViewById(R.id.txtQ);
        btn = view.findViewById(R.id.txtBtn);
        image = view.findViewById(R.id.imageView);

        title.setText(location.getRegion());
        if (location.getDensity() < 1000) {
            density.setText("Density: " + location.getDensity() + "/Km2");
        } else {
            density.setText("Density: +" + location.getDensity() + "/Km2");
        }

        monoxide.setText("Monoxide: " + location.getMonoxide() + "ppvp");
        dioxide.setText("Dioxide " + location.getDioxide() + "ppb");
        qa.setText("Air Quality: Bad                     \n                             ");
        image.setImageBitmap(location.getImage());
        selectImageTest();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

    }

    public void selectImageTest() {
        switch (location.getRegion()) {
            case "Guadalajara":
                image.setImageResource(R.drawable.guadalajara);
                break;
            case "CDMX":
                image.setImageResource(R.drawable.cdmx);
                break;
            case "Chihuhaua":
                image.setImageResource(R.drawable.chihuahua);
                break;
            case "Barcelona":
                image.setImageResource(R.drawable.barcelona);
                break;
            case "Córdoba":
                image.setImageResource(R.drawable.cordoba);
                break;
            case "Winchester":
                image.setImageResource(R.drawable.winchester);
                break;
            case "LA":
                image.setImageResource(R.drawable.la);
                break;
            case "Trinidad":
                image.setImageResource(R.drawable.trinidad);
                break;
            case "Santa Maria":
                image.setImageResource(R.drawable.santamaria);
                break;
        }
    }

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }
}