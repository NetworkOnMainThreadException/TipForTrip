package networkonmainthreadexception.tipfortrip;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;


/**
 * A simple {@link Fragment} subclass.
 */
public class RouteDetailFragment extends Fragment {


    public RouteDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View detail = inflater.inflate(R.layout.fragment_route_detail, container, false);

        Toolbar tb = detail.findViewById(R.id.detail_toolbar);
        TextView routeText = detail.findViewById(R.id.detail_route_full_text);
        ImageView routeImage = detail.findViewById(R.id.detail_route_image);
        TextView routeLocation = detail.findViewById(R.id.detail_route_location);
        TextView routeDate = detail.findViewById(R.id.detail_route_pub_date);


        tb.setTitle(getArguments().getString("Title"));
        routeText.setText(getArguments().getString("FullText"));
        routeLocation.setText(getArguments().getString("Location"));
        routeDate.setText(getArguments().getString("Date"));
        Glide.with(detail.getContext()).load(getArguments().getString("ImageUrl")).into(routeImage);


        return detail;
    }


    public static RouteDetailFragment newInstance(String title, String full, String image, String date, String location) {

        Bundle args = new Bundle();

        args.putString("Title", title);
        args.putString("FullText", full);
        args.putString("ImageUrl", image);
        args.putString("Date", date);
        args.putString("Location", location);

        RouteDetailFragment fragment = new RouteDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
