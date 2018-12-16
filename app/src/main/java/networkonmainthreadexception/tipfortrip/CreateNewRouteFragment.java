package networkonmainthreadexception.tipfortrip;


import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import kotlin.Unit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static networkonmainthreadexception.tipfortrip.UiUtilsKt.pushFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateNewRouteFragment extends Fragment {


    public CreateNewRouteFragment() {
        // Required empty public constructor
    }

    Date publishDate = new Date();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_create_new_route, container, false);

        Button buttonForCreateMap = root.findViewById(R.id.buttonForCreateMap);

        Button buttonCalendar = root.findViewById(R.id.calendarButton);
        buttonCalendar.setOnClickListener(v -> {
            UiUtilsKt.pickDate(getContext(), date -> {
                // пользователь выбрал дату date
                publishDate = date;
                return Unit.INSTANCE;
            });
        });

        buttonForCreateMap.setOnClickListener(v -> {
            EditText textTitle = root.findViewById(R.id.editTITLE);
            EditText imageUrlTitle = root.findViewById(R.id.editIMAGE_URL);
            EditText fullTextTitle = root.findViewById(R.id.editFULL_TEXT);
            EditText locationTitle = root.findViewById(R.id.editLOCATION);
            EditText previewTitle = root.findViewById(R.id.editPREVIEW_TEXT);

            String title = textTitle.getText().toString();
            String imageUrl = imageUrlTitle.getText().toString();
            String fullText = fullTextTitle.getText().toString();
            String location = locationTitle.getText().toString();
            String previewText = previewTitle.getText().toString();

            if (title.isEmpty() ||
                    imageUrl.isEmpty() ||
                    fullText.isEmpty() ||
                    location.isEmpty() ||
                    previewText.isEmpty()
                    ) {
                // доделать
            }

            RouteItem routeItem = new RouteItem(title, imageUrl, previewText, fullText, location, publishDate );

            pushFragment(getParentFragment().getFragmentManager(), new RouteCreationFragment());
        });


        return root;
    }


}
