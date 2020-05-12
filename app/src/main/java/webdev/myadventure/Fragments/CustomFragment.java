package webdev.myadventure.Fragments;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amyu.stack_card_layout_manager.StackCardLayoutManager;
import com.littlemango.stacklayoutmanager.DefaultAnimation;
import com.littlemango.stacklayoutmanager.DefaultLayout;
import com.littlemango.stacklayoutmanager.StackLayoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import webdev.myadventure.POJOs.User;
import webdev.myadventure.R;

public class CustomFragment extends Fragment {

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
        for(int i=0; i<6; i++) {
            User n = new User();
            n.setFirstname(i+"");
            temp.add(n);
        }



        myAdapter adapter = new myAdapter(getContext(), temp);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.addItemDecoration(new OverlapDecoration());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        return root;
    }


}

 class OverlapDecoration extends RecyclerView.ItemDecoration {

    private final static int vertOverlap = -200;

    @Override
    public void getItemOffsets (Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        outRect.set(0, 0, 0, vertOverlap);

    }
}
