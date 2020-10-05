package marshmallow.spaceapps;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class HelpFragment extends Fragment {

    private ListView list;
    private TextView text;
    private Actions actions;


    private String[] items = {"Choose this challenge", "Are participating", "Used: Population density, carbon monoxide & nitrogen dioxide", "how % is this project completed?", "Who do we want to help?"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        actions = new Actions();
        return inflater.inflate(R.layout.fragment_help, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        list = view.findViewById(R.id.list);
        text = view.findViewById(R.id.goBack);

        list.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, items));
        ;

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        actions.openItemDialog(getFragmentManager()
                                , items[i], "We thought this category" +
                                        " was the best for us. So we wanted to help people with something we like, in this case, maps.");
                        break;
                    case 1:
                        actions.openItemDialog(getFragmentManager()
                                , items[i]
                                , "We wanted to help people by creating and developing a Software solution to " +
                                        "a problem related with the human lifestyle.");
                        break;
                    case 2:

                        actions.openItemDialog(getFragmentManager()
                                , items[i]
                                , "Many people are exposed to a series of pollutants such as: " +
                                        "carbon monoxide and nitrogen dioxide from burning resources. " +
                                        "Such as: coal, oil, or gas. Generating more than 1000 deaths annually in " +
                                        "the United States from poisoning with symptoms such as: headache, lethargy, weakness, " +
                                        "nausea and muscle aches.");
                        break;
                    case 3:
                        actions.openItemDialog(getFragmentManager()
                                , items[i], "We calculate that his App is at 40% of the total development. " +
                                        "If we get more time we can make a real and useful project. But for " +
                                        "this moment we only made a prototype with an high scalable coding structure.");
                        break;
                    case 4:
                        actions.openItemDialog(getFragmentManager()
                                , items[i]
                                , "We want the NASA Data to be aviable for everyone, they who are not scientists. " +
                                        " By making a friendly NASA Data Visualizing tool that even a child can use.");
                        break;
                }
            }
        });


        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                PlacemarkFragment bs = new PlacemarkFragment();
                ft.replace(R.id.mainFrame, bs);
                ft.commit();
            }
        });
    }
}