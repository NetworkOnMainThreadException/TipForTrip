package networkonmainthreadexception.tipfortrip;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import kotlin.Unit;

import static networkonmainthreadexception.tipfortrip.UiUtilsKt.pushFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class RouteDetailFragment extends Fragment {


    public RouteDetailFragment() {
        // Required empty public constructor
    }


    String dateEvent = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View detail = inflater.inflate(R.layout.fragment_route_detail, container, false);

        Toolbar tb = detail.findViewById(R.id.detail_toolbar);
        TextView routeText = detail.findViewById(R.id.detail_route_full_text);
        ImageView routeImage = detail.findViewById(R.id.detail_route_image);
        TextView routeLocation = detail.findViewById(R.id.detail_route_location);
        TextView routeDate = detail.findViewById(R.id.detail_route_pub_date);

        final RouteItem route = getArguments().getParcelable("route");
        tb.setTitle(route.getTitle());
        routeText.setText(route.getFullText());
        routeLocation.setText(route.getLocation());
        routeDate.setText(route.getPublishDate().toString());
        Glide.with(detail.getContext()).load(route.getImageUrl()).into(routeImage);

        Button createCalendar = detail.findViewById(R.id.detail_route_create_event);

        createCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiUtilsKt.pickDate(getContext(), date -> {
                    dateEvent = date.toString();
                    // пользователь выбрал дату date
                    pushFragment(getFragmentManager(),
                            CreateNewEventFragmentKt.newInstance(route.getId(), date.getTime()));
                    return Unit.INSTANCE;
                });


            }
        });

        Button button = detail.findViewById(R.id.view_on_map_button);
        button.setOnClickListener(v -> {
                PreviewRouteFragment toFragment = PreviewRouteFragment.newInstance(route);
                UiUtilsKt.pushFragment(getFragmentManager(), toFragment);
        });

        detail.findViewById(R.id.buttonJoin).setOnClickListener(v -> {
            UiUtilsKt.pushFragment(getFragmentManager(), EventListFragmentKt.newInstance(route.getId()));
        });

        return detail;
    }


    public static RouteDetailFragment newInstance(RouteItem route) {
        Bundle args = new Bundle();
        args.putParcelable("route", route);
        RouteDetailFragment fragment = new RouteDetailFragment();
        fragment.setArguments(args);
        return fragment;

    }
}
