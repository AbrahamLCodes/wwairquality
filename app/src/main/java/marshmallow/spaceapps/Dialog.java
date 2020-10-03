package marshmallow.spaceapps;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Dialog extends AppCompatDialogFragment {

    private TextView title, lat, lon, identifier, qa, btn;

    private Location location;

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
        lat = view.findViewById(R.id.txtLat);
        lon = view.findViewById(R.id.txtLong);
        identifier = view.findViewById(R.id.txtId);
        qa = view.findViewById(R.id.txtQ);
        btn = view.findViewById(R.id.txtBtn);

        title.setText(location.getRegion());

        lat.setText("Latitud: "+location.getLatitud());
        lon.setText("Longitud: "+location.getLongitud());
        identifier.setText("ID: "+location.getId());
        qa.setText("Calidad del aire: Very bad xd");


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

    }

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);


    }


}