package webdev.myadventure.Modules;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import webdev.myadventure.POJOs.User;
import webdev.myadventure.R;

public class CardFragment extends Fragment {

    RecyclerView recyclerView;
    String TAG = "testing";
    AppCompatButton change;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_custom, container, false);
        recyclerView = root.findViewById(R.id.custom_recycler_view);
        // change = root.findViewById(R.id.view_card_change);

        List<User> temp= new ArrayList<>();
        for(int i=0; i<100; i++) {
            User n = new User();
            n.setFirstname(i+"");
            temp.add(n);
        }
        myAdapter adapter = new myAdapter(getContext(), temp);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.addItemDecoration(new OverlapDecoration(getContext()));
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        return root;
    }
}



class myAdapter extends RecyclerView.Adapter<myViewHolder> {

    private List<User> users;
    private Context context;


    public myAdapter(Context context, List<User> usersList) {
        users = usersList;
        this.context = context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(context).inflate(R.layout.view_card_small, parent, false);
        return new myViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.name.setText(users.get(position).getFirstname());
        holder.title.setText("Card" + position);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}

class myViewHolder extends RecyclerView.ViewHolder {

    TextView name, title;
    public myViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.view_card_small_name);
        title = itemView.findViewById(R.id.view_card_small_title);
    }
}


 class OverlapDecoration extends RecyclerView.ItemDecoration {

    private final static int vertOverlap = -420;
     float dpOffset;
     String TAG = "testing";
     int listSize;

    public OverlapDecoration(Context context) {
        //converting dp to pixels
        dpOffset = vertOverlap * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    @Override
    public void getItemOffsets (Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int position = parent.getChildAdapterPosition(view);
        int listSize = parent.getAdapter().getItemCount();
//        if (position != 0)
//            outRect.top = (int)dpOffset;
        if(position != listSize-1)
        outRect.bottom = (int)dpOffset;

        Log.i(TAG, "getItemOffsets: position:" + position);
        Log.i(TAG, "getItemOffsets: listSize:" + listSize);

    }
}
