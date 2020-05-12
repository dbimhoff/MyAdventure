package webdev.myadventure.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.amyu.stack_card_layout_manager.StackCardLayoutManager;

import java.util.ArrayList;
import java.util.List;

import webdev.myadventure.POJOs.User;
import webdev.myadventure.R;

public class StackCardFragment extends Fragment {

    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_stack, container, false);
        recyclerView = root.findViewById(R.id.stackRecyclerView);

        List<User> temp= new ArrayList<>();
        for(int i=0; i<6; i++) {
            User n = new User();
            n.setFirstname(i+"");
            temp.add(n);
        }
        myAdapter adapter = new myAdapter(getContext(), temp);
        recyclerView.setLayoutManager(new StackCardLayoutManager(temp.size()));



        recyclerView.setAdapter(adapter);


        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int fromPos = viewHolder.getAdapterPosition();
                int toPos = target.getAdapterPosition();
                adapter.notifyItemMoved(fromPos, toPos);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        });
        helper.attachToRecyclerView(recyclerView);



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

class FooterAdapter extends RecyclerView.Adapter<footerViewHolder> {

    private List<User> users;
    private Context context;


    public FooterAdapter(Context context, List<User> usersList) {
        users = usersList;
        this.context = context;
    }

    @NonNull
    @Override
    public footerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(context).inflate(R.layout.small_footer_tab, parent, false);
        return new footerViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull footerViewHolder holder, int position) {

        holder.name.setText("Card " +users.get(position).getFirstname());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}

class footerViewHolder extends RecyclerView.ViewHolder {

    TextView name;
    public footerViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.small_footer_name);

    }


}
