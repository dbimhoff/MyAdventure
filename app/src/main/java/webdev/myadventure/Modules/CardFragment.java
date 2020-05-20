package webdev.myadventure.Modules;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.Transformation;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import webdev.myadventure.POJOs.User;
import webdev.myadventure.R;
import webdev.myadventure.myOnClickListener;

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
        for(int i=0; i<10; i++) {
            User n = new User();
            n.setFirstname(i+"");
            temp.add(n);
        }


        RecyclerView.SmoothScroller mySmoothScroller = new LinearSmoothScroller(getContext()) {
            private static final float MILLISECONDS_PER_INCH = 150f;
            @Override
            protected int getVerticalSnapPreference() {
                return SNAP_TO_START;
            }

            @Nullable
            @Override
            public PointF computeScrollVectorForPosition(int targetPosition) {
                return super.computeScrollVectorForPosition(targetPosition);
            }

            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return MILLISECONDS_PER_INCH / displayMetrics.densityDpi;
            }
        };



        OverlapDecoration decoration =new OverlapDecoration(getContext(), .85f, temp.size()-1);
        myAdapter adapter = new myAdapter(getContext(), temp, new myOnClickListener() {
            @Override
            public void onItemClicked(View v, int position) {
                //if the position we just clicked is the same as the one expanded, make it smaller by changing the cardToShow to -1 to not show any card
                if(position == decoration.getCardToShow()) {
                    decoration.setCardToShow(-1);
                   // decoration.collapse(v);
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
                else {
                    decoration.setCardToShow(position);
                    //decoration.expand(v);
                    recyclerView.getAdapter().notifyDataSetChanged();
                    //smooth scroll the view
                    mySmoothScroller.setTargetPosition(position);
                    recyclerView.getLayoutManager().startSmoothScroll(mySmoothScroller);
                }
                Log.i(TAG, "onCreateView: position clicked: " + position);
            }
        });
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        return root;
    }


}



class myAdapter extends RecyclerView.Adapter<myAdapter.myViewHolder> {

    private List<User> users;
    private Context context;
    private static myOnClickListener itemListener;
    String TAG = "testing";


    public myAdapter(Context context, List<User> usersList, myOnClickListener itemListener) {
        users = usersList;
        this.context = context;
        this.itemListener = itemListener;
    }



    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(context).inflate(R.layout.fragment_profile, parent, false);
        return new myViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
//        holder.name.setText(users.get(position).getFirstname());
//        holder.title.setText("Card" + position);


    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Log.i(TAG, "onClick: pos clicked:: "+ getAdapterPosition());
            itemListener.onItemClicked(v, this.getLayoutPosition());

        }

        TextView name, title;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
//        name = itemView.findViewById(R.id.view_card_small_name);
//        title = itemView.findViewById(R.id.view_card_small_title);
        }
    }
}




 class OverlapDecoration extends RecyclerView.ItemDecoration {

    private float overlapPercentage;
     float dpOffset;
     int cardToShow;
     String TAG = "testing";
     Context context;

     /**
      * Creates overlap with recycler view to make views appear to be overlapping by drawing the bottom of the view with negative offset.
      * @param context context
      * @param overlapPercentage value from 0-1 of the amount to overlap the views in the recycler view (.9 = 90% overlap)
      * @param cardToShow value from 0 to list length of which card should be expanded
      */
    public OverlapDecoration(Context context, float overlapPercentage, int cardToShow) {
        //converting dp to pixels
        this.context = context;
        this.overlapPercentage = overlapPercentage;
        this.cardToShow = cardToShow;
    }

    public void setCardToShow(int newCardToShow) {
        cardToShow = newCardToShow;
    }

    public int getCardToShow() {
        return cardToShow;
    }

    public void animateViewOpening(View view) {


    }

    public void animateViewClosing(RecyclerView recyclerView, View view) {
        RecyclerView.ItemDecoration decor = recyclerView.getItemDecorationAt(0);



    }

    @Override
    public void getItemOffsets (Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //measures screen and wrapped content height then decides offset based on that. Should scale well on all devices
        view.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        float currViewHeight = view.getMeasuredHeight();
        dpOffset = currViewHeight * -1 *overlapPercentage / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);

        //If we are at the card to show, don't offset the bottom. We want to show the whole card.
        //we change the card to show when clicking on it
        int position = parent.getChildAdapterPosition(view);
            if (position != cardToShow)
                outRect.bottom = (int) dpOffset;

    }

//     public static void expand(final View v) {
//         int matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec(((View) v.getParent()).getWidth(), View.MeasureSpec.EXACTLY);
//         int wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//         v.measure(matchParentMeasureSpec, wrapContentMeasureSpec);
//         final int targetHeight = v.getMeasuredHeight();
//
//         // Older versions of android (pre API 21) cancel animations for views with a height of 0.
//         v.getLayoutParams().height = 1;
//         v.setVisibility(View.VISIBLE);
//         Animation a = new Animation()
//         {
//             @Override
//             protected void applyTransformation(float interpolatedTime, Transformation t) {
//                 v.getLayoutParams().height = interpolatedTime == 1
//                         ? ViewGroup.LayoutParams.WRAP_CONTENT
//                         : (int)(targetHeight * interpolatedTime);
//                 v.requestLayout();
//             }
//
//             @Override
//             public boolean willChangeBounds() {
//                 return true;
//             }
//         };
//
//         // Expansion speed of 1dp/ms
//         a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
//         v.startAnimation(a);
//     }
//
//     public static void collapse(final View v) {
//         final int initialHeight = v.getMeasuredHeight();
//
//         Animation a = new Animation()
//         {
//             @Override
//             protected void applyTransformation(float interpolatedTime, Transformation t) {
//                 if(interpolatedTime == 1){
//                     v.setVisibility(View.GONE);
//                 }else{
//                     v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
//                     v.requestLayout();
//                 }
//             }
//
//             @Override
//             public boolean willChangeBounds() {
//                 return true;
//             }
//         };
//
//         // Collapse speed of 1dp/ms
//         a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
//         v.startAnimation(a);
//     }
}
