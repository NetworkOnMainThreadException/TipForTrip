package networkonmainthreadexception.tipfortrip;


import android.os.Bundle;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;

import static networkonmainthreadexception.tipfortrip.UiUtilsKt.pushFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    public ProfileFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView textViewAboutMe = root.findViewById(R.id.textView4);
        textViewAboutMe.setMovementMethod(new ScrollingMovementMethod());

        //Кнопка настройки и обработчик нажатия на эту кнопку
        Button buttonSetting = root.findViewById(R.id.buttonSetting);
        buttonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ImageView imageProfile = root.findViewById(R.id.imageProfile);
//                imageProfile.setVisibility(View.VISIBLE);
                pushFragment(getParentFragment().getFragmentManager(), new SettingFragment());

            }
        });

        //Кнопка "мои события"
        Button buttonMyEvent = root.findViewById(R.id.buttonMyEvents);
        buttonMyEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushFragment(getParentFragment().getFragmentManager(), new MyEventsFragment());
            }
        });




        return root;
    }






}
