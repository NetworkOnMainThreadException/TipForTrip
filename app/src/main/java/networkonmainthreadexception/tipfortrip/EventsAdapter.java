package networkonmainthreadexception.tipfortrip;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder>{
    private final List<EventItem> itemList;
    private final Context context;
    private final LayoutInflater inflater;
    private final FragmentManager fragmentManager;

    public EventsAdapter(Context context, List<EventItem> events, FragmentManager fragmentManager){
        this.fragmentManager = fragmentManager;
        this.context = context;
        this.itemList = events;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                inflater.inflate(R.layout.fragment_event_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final EventItem eventItem = itemList.get(position);
        holder.eventTitle.setText(eventItem.getEventTitle());
        holder.eventMemberCount.setText("Число участников: " + eventItem.getMembers().size());
        if (eventItem.getMembers().size() > 0) {
            holder.eventMemberList.setText(EventListFragmentKt.join(", ", eventItem.getMembers()));
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        final TextView eventTitle;
        final TextView eventMemberCount;
        final TextView eventMemberList;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            eventTitle = itemView.findViewById(R.id.events_item_title);
            eventMemberCount = itemView.findViewById(R.id.events_members_count);
            eventMemberList = itemView.findViewById(R.id.events_members_list);
        }
    }
}
