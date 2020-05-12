package webdev.myadventure.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.littlemango.stacklayoutmanager.DefaultAnimation;
import com.littlemango.stacklayoutmanager.DefaultLayout;
import com.littlemango.stacklayoutmanager.StackLayoutManager;

import java.util.ArrayList;
import java.util.List;

import webdev.myadventure.POJOs.User;
import webdev.myadventure.R;

public class LittleMangoFragment extends Fragment {

    RecyclerView recyclerView, footerRecylerView;
    String TAG = "testing";
    AppCompatButton change;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.two_fragment_stack, container, false);
        recyclerView = root.findViewById(R.id.stackRecyclerView);
        footerRecylerView = root.findViewById(R.id.footerStackRecyclerView);
       // change = root.findViewById(R.id.view_card_change);

        List<User> temp= new ArrayList<>();
        for(int i=0; i<6; i++) {
            User n = new User();
            n.setFirstname(i+"");
            temp.add(n);
        }

        List<User> footerList = new ArrayList<>();
        FooterAdapter footerAdapter = new FooterAdapter(getContext(), footerList);
        StackLayoutManager footerManager = new StackLayoutManager(StackLayoutManager.ScrollOrientation.BOTTOM_TO_TOP, temp.size(), DefaultAnimation.class,
                DefaultLayout.class);

        footerManager.setItemOffset(100);
        footerManager.setPagerMode(false);

        footerRecylerView.setLayoutManager(footerManager);
        footerRecylerView.setAdapter(footerAdapter);
        //disable footer scrolling
        footerRecylerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener(){
            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                recyclerView.onTouchEvent(e);
            }

            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
               // recyclerView.onTouchEvent(e);
                return true;
            }


        });


        myAdapter adapter = new myAdapter(getContext(), temp);
        StackLayoutManager manager = new StackLayoutManager(StackLayoutManager.ScrollOrientation.TOP_TO_BOTTOM, temp.size(), DefaultAnimation.class,
                DefaultLayout.class);
        manager.setItemOffset(200);
        manager.setPagerMode(false);
        manager.setItemChangedListener(new StackLayoutManager.ItemChangedListener() {
            @Override
            public void onItemChanged(int position) {
                footerList.clear();
                for(int i=position; i>0; i--) {
                    //gets the last card and adds it to the footer
                    footerList.add(temp.get(i-1));

                }
                Log.d(TAG, "onItemChanged: " + footerList.size());
                footerAdapter.notifyDataSetChanged();

            }
        });
        Log.i(TAG, "manager width1 " + manager.getWidth());
        recyclerView.setLayoutManager(manager);
        Log.i(TAG, "manager width2 " + manager.getWidth());

        recyclerView.setAdapter(adapter);




        return root;
    }


}
