package networkonmainthreadexception.tipfortrip;


import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import kotlin.Unit;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterNewEventFragment extends Fragment {


    public RegisterNewEventFragment() {
        // Required empty public constructor
    }

    String dateEvent = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_register_new_event, container, false);

        Button buttonCalendar = root.findViewById(R.id.calendarButton);
        buttonCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiUtilsKt.pickDate(getContext(), date -> {
                    dateEvent = date.toString();
                    // пользователь выбрал дату date
                    return Unit.INSTANCE;
                });


            }
        });



        return root;
    }

}
