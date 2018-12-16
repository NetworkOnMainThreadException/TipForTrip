package networkonmainthreadexception.tipfortrip;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PreviewRouteFragment extends Fragment {

    Polyline polyline;
    private GoogleMap mMap;
    String id;

    public static RouteCreationFragment newInstance(RouteItem route) {
        Bundle args = new Bundle();
        args.putString("ключ", route.getPointsId());

        RouteCreationFragment fragment = new RouteCreationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_route_creation, container, false);
        polyline = mMap.addPolyline(new PolylineOptions()
                .clickable(false));
        ArrayList<LatLng> list = new ArrayList<>();//ПАША ПАЧИНИ
        polyline.setPoints((List)list);
        id = getArguments().getString("ключ");
        UtilsKt.getPoints(id).addOnSuccessListener(points -> {
            polyline.setPoints(points);
            for(LatLng point: points)
                mMap.addMarker(new MarkerOptions().position(point).title("Marker not in Sydney"));
        });
        return root;
    }

}
