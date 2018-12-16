package networkonmainthreadexception.tipfortrip;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static networkonmainthreadexception.tipfortrip.UiUtilsKt.pushFragment;
import static networkonmainthreadexception.tipfortrip.UtilsKt.getUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    public ProfileFragment() {
    }

    String imageUrl = "";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView textViewAboutMe = root.findViewById(R.id.textView4);
        textViewAboutMe.setMovementMethod(new ScrollingMovementMethod());

        ImageView imageButton = root.findViewById(R.id.imageProfile);

        //Кнопка настройки и обработчик нажатия на эту кнопку

        Button buttonSetting = root.findViewById(R.id.buttonSetting);
        buttonSetting.setOnClickListener(v -> {
            pushFragment(getParentFragment().getFragmentManager(), new SettingFragment());
        });
        //Кнопка "мои события"
        Button buttonMyEvent = root.findViewById(R.id.buttonMyEvents);
        buttonMyEvent.setOnClickListener(v -> {
            pushFragment(getParentFragment().getFragmentManager(), new MyEventsFragment());
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

        getUser(uid).addOnSuccessListener(user -> {
            Glide.with(root.getContext()).load(user.getAvatar()).into(imageButton);
        });

        imageButton.setOnClickListener(v -> {
            EasyImage.openChooserWithDocuments(this, "Выберите фото", 0);
        });

        return root;
    }

    private void uploadPhoto() {
        Uri file = Uri.fromFile(new File(imageUrl));
        StorageReference routesFolder = FirebaseStorage.getInstance().getReference().child("routes");
        StorageReference fileRef = routesFolder.child(UUID.randomUUID().toString());
        fileRef.putFile(file).continueWithTask(task -> {
            if (!task.isSuccessful()) throw task.getException();
            return fileRef.getDownloadUrl();
        })
                .addOnSuccessListener(downloadUri -> {
                    sendData();
                })
                .addOnFailureListener(e -> {
                    UiUtilsKt.showToast(getContext(), "Ошибка загрузки фото");
                });
    }

    private void sendData() {
        HashMap<String, Object> temp = new HashMap<>();
        temp.put("avatar", imageUrl);
        String uid = FirebaseAuth.getInstance().getUid();
        FirebaseFirestore.getInstance()
                .collection("users")
                .document(uid)
                .update(temp)
                .addOnSuccessListener(doc -> {
                    UiUtilsKt.showToast(getContext(), "Фото добавлено");
                    UiUtilsKt.setFragment(getParentFragment().getFragmentManager(), new ProfileFragment());
                }).addOnFailureListener(e -> {
            UiUtilsKt.showToast(getContext(), "Ошибка добавления");
        });
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
                uploadPhoto();
            }
        });
    }
}
