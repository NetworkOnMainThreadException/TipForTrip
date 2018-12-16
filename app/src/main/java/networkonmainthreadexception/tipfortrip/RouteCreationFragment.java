package networkonmainthreadexception.tipfortrip;


import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.*;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class RouteCreationFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private Polyline polyline;
    private RouteItem routeItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_route_creation, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        routeItem = getArguments().getParcelable("route");

        Button button = root.findViewById(R.id.button);
        button.setOnClickListener(v -> uploadPhoto());

        return root;
    }

    private void uploadPhoto() {
        Uri file = Uri.fromFile(new File(routeItem.getImageUrl()));
        StorageReference routesFolder = FirebaseStorage.getInstance().getReference().child("routes");
        StorageReference fileRef = routesFolder.child(UUID.randomUUID().toString());
        fileRef.putFile(file).continueWithTask(task -> {
                    if (!task.isSuccessful()) throw task.getException();
                    return fileRef.getDownloadUrl();
                })
                .addOnSuccessListener(downloadUri -> {
                    routeItem.setImageUrl(downloadUri.toString());
                    sendPoints();
                })
                .addOnFailureListener(e -> {
                    UiUtilsKt.showToast(getContext(), "Ошибка загрузки фото");
                });
    }

    private void sendPoints() {
        UtilsKt.setPoints(polyline.getPoints())
                .addOnSuccessListener(doc -> {
                    routeItem.setPointsId(doc.getId());
                    sendRoute();
                })
                .addOnFailureListener(e -> {
                    UiUtilsKt.showToast(getContext(), "Ошибка добавления точек");
                });
    }

    private void sendRoute() {
        FirebaseFirestore.getInstance()
                .collection("routes")
                .add(routeItem)
                .addOnSuccessListener(doc -> {
                    UiUtilsKt.showToast(getContext(), "Маршрут добавлен");
                    UiUtilsKt.setFragment(getFragmentManager(), new TabsFragment());
                })
                .addOnFailureListener(e -> {
                    UiUtilsKt.showToast(getContext(), "Ошибка добавления маршрута");
                });
    }

    public static RouteCreationFragment newInstance(RouteItem route) {
        Bundle args = new Bundle();
        args.putParcelable("route", route);
        RouteCreationFragment fragment = new RouteCreationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.remove();
        ArrayList<LatLng> list = (ArrayList<LatLng>) polyline.getPoints();
        list.remove(marker.getPosition());
        polyline.setPoints(list);
        return false;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mMap.addMarker(new MarkerOptions().position(latLng).title("Marker not in Sydney"));
        if (polyline == null)
            polyline = mMap.addPolyline(new PolylineOptions()
                    .clickable(false)
            );
        ArrayList<LatLng> list = (ArrayList<LatLng>) polyline.getPoints();
        list.add(latLng);
        polyline.setPoints(list);
    }
}
