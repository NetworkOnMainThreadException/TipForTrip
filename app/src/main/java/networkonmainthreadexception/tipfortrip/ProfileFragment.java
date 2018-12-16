package networkonmainthreadexception.tipfortrip;


import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;

import static networkonmainthreadexception.tipfortrip.UiUtilsKt.pushFragment;
import static networkonmainthreadexception.tipfortrip.UtilsKt.getUser;


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
        buttonSetting.setOnClickListener(v -> {
            pushFragment(getFragmentManager(), new SettingFragment());
        });

        //Кнопка "мои события"
        Button buttonMyEvent = root.findViewById(R.id.buttonMyEvents);
        buttonMyEvent.setOnClickListener(v -> {
            pushFragment(getFragmentManager(), new MyEventsFragment());
        });

        TextView textViewName = root.findViewById(R.id.textViewName);
        TextView textViewSurName = root.findViewById(R.id.textViewSurName);

        String name = "";
        String uid = FirebaseAuth.getInstance().getUid(); // uid of current user

        getUser(uid).addOnSuccessListener(user -> {
            textViewName.setText(user.getName());
        });

        getUser(uid).addOnSuccessListener(user -> {
            textViewSurName.setText(user.getSurname());
        });

        return root;
    }


}
