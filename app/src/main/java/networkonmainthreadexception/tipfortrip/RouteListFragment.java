package networkonmainthreadexception.tipfortrip;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RouteListFragment extends Fragment {
    public RouteListFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_routes, container, false);

        UtilsKt.getRoutes().addOnSuccessListener( routes-> {
            RecyclerView recyclerView = root.findViewById(R.id.recycler_fragment);
            recyclerView.setAdapter(new RoutesAdapter(root.getContext(), routes, getParentFragment().getFragmentManager()));

            recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

            DividerItemDecoration decoration = new DividerItemDecoration(recyclerView.getContext(), 1);
            decoration.setDrawable(ContextCompat.getDrawable(root.getContext(), R.drawable.list_divider));
            recyclerView.addItemDecoration(decoration);

        }).addOnFailureListener(e -> {
            UiUtilsKt.showToast(root.getContext(), "Ошибка загрузки");
        });

        return root;
    }
}
