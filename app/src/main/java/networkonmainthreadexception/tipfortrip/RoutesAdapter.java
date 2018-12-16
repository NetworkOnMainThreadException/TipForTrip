package networkonmainthreadexception.tipfortrip;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import java.util.List;

public class RoutesAdapter extends RecyclerView.Adapter<RoutesAdapter.ViewHolder> {

    private final List<RouteItem> routeItems;
    private final Context context;
    private final LayoutInflater inflater;
    private final FragmentManager fragmentManager;

    public RoutesAdapter(Context context, List<RouteItem> routes, FragmentManager fragmentManager){
        this.fragmentManager = fragmentManager;
        this.context = context;
        this.routeItems = routes;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RoutesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                inflater.inflate(R.layout.fragment_route_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull RoutesAdapter.ViewHolder holder, int position) {
        final RouteItem routeItem = routeItems.get(position);

        holder.routeTitle.setText(routeItem.getTitle());
        holder.routePreviewText.setText(routeItem.getPreviewText());
        holder.routeLocation.setText(routeItem.getLocation());

        Glide.with(context).load(routeItem.getImageUrl()).into(holder.routeImage);

        holder.itemView.setOnClickListener(view ->
        {
            RouteDetailFragment toFragment = RouteDetailFragment.newInstance(routeItem);
            UiUtilsKt.pushFragment(fragmentManager, toFragment);
        });
    }

    @Override
    public int getItemCount() {
        return routeItems.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView routeTitle;
        final TextView routeLocation;
        final TextView routePreviewText;
        final ImageView routeImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            routeLocation = itemView.findViewById(R.id.route_location);
            routePreviewText = itemView.findViewById(R.id.route_prev_text);
            routeTitle = itemView.findViewById(R.id.route_title);
            routeImage = itemView.findViewById(R.id.route_image);
        }
    }
}
