package networkonmainthreadexception.tipfortrip;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * A simple {@link Fragment} subclass.
 */
public class PreviewRouteFragment extends Fragment implements OnMapReadyCallback {

    Polyline polyline;
    private GoogleMap mMap;
    String id;
    static RouteItem thisroute;

    public static PreviewRouteFragment newInstance(RouteItem route) {
        Bundle args = new Bundle();
        thisroute = route;
        args.putString("ключ", route.getPointsId());


        PreviewRouteFragment fragment = new PreviewRouteFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        polyline = mMap.addPolyline(new PolylineOptions()
                .clickable(false));
        id = getArguments().getString("ключ");
        UtilsKt.getPoints(id).addOnSuccessListener(points -> {
            polyline.setPoints(points);
            for(LatLng point: points)
                mMap.addMarker(new MarkerOptions().position(point).title("Marker not in Sydney"));
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_preview_route, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
        Toolbar toolbar = root.findViewById(R.id.toolbar);
        toolbar.setTitle(thisroute.getTitle());
        return root;
    }

  }
