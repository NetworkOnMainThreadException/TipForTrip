package networkonmainthreadexception.tipfortrip;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import kotlin.Unit;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static networkonmainthreadexception.tipfortrip.UiUtilsKt.pushFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateNewRouteFragment extends Fragment {


    public CreateNewRouteFragment() {
        // Required empty public constructor
    }

    String imageUrl = "";
    Date publishDate = new Date();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_create_new_route, container, false);

        Button buttonForCreateMap = root.findViewById(R.id.buttonForCreateMap);

        Button buttonForAddPhoto = root.findViewById(R.id.button2);
        buttonForAddPhoto.setOnClickListener(v -> {
            EasyImage.openChooserWithDocuments(this, "Выберите фото", 0);
        });

        buttonForCreateMap.setOnClickListener(v -> {
            EditText textTitle = root.findViewById(R.id.editTITLE);
            EditText fullTextTitle = root.findViewById(R.id.editFULL_TEXT);
            EditText locationTitle = root.findViewById(R.id.editLOCATION);
            EditText previewTitle = root.findViewById(R.id.editPREVIEW_TEXT);

            String title = textTitle.getText().toString();
            String fullText = fullTextTitle.getText().toString();
            String location = locationTitle.getText().toString();
            String previewText = previewTitle.getText().toString();

            if (title.isEmpty() ||
                    fullText.isEmpty() ||
                    location.isEmpty() ||
                    previewText.isEmpty()
                    ) {
                UiUtilsKt.showToast(getContext(), "Заполните все поля");
                return;
            }

            RouteItem routeItem = new RouteItem(title, imageUrl, previewText, fullText, location, publishDate, "");

            pushFragment(getFragmentManager(), RouteCreationFragment.newInstance(routeItem));
        });


        return root;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
            }

            @Override
            public void onImagesPicked(List<File> imagesFiles, EasyImage.ImageSource source, int type) {
                //Handle the images
                imageUrl = imagesFiles.get(0).toString();
            }
        });
    }

}
