package webdev.myadventure.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bakerj.infinitecards.AnimationTransformer;
import com.bakerj.infinitecards.CardItem;
import com.bakerj.infinitecards.InfiniteCardView;
import com.bakerj.infinitecards.ZIndexTransformer;
import com.bakerj.infinitecards.transformer.DefaultCommonTransformer;
import com.bakerj.infinitecards.transformer.DefaultTransformerToBack;
import com.bakerj.infinitecards.transformer.DefaultTransformerToFront;
import com.bakerj.infinitecards.transformer.DefaultZIndexTransformerCommon;

import java.util.ArrayList;
import java.util.List;

import webdev.myadventure.POJOs.User;
import webdev.myadventure.R;

public class CardViewFragment extends Fragment {

    InfiniteCardView cardScroller;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_card_view, container, false);
        cardScroller = root.findViewById(R.id.card_scroller);
        List<User> tempList = new ArrayList<>();
        for (int i =0; i<4; i++) {
            User newUser = new User(i+"", i+"", i, null);
            tempList.add(newUser);
        }

        myCardAdapter adapter = new myCardAdapter(getContext(), tempList);
        cardScroller.setAdapter(adapter);


        AppCompatButton next = root.findViewById(R.id.view_card_next);
        AppCompatButton prev = root.findViewById(R.id.view_card_prev);
        AppCompatButton change = root.findViewById(R.id.view_card_change);

        change.setOnClickListener((View v) -> {
            LittleMangoFragment frag = new LittleMangoFragment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.fragment_holder, frag);
            transaction.addToBackStack(null);
            transaction.commit();

        });

        next.setOnClickListener((View v) -> {
            setStyle4();
            cardScroller.bringCardToFront(1);

        });
        prev.setOnClickListener((View v) ->  {
            setStyle1();
            cardScroller.bringCardToFront(adapter.getCount()-1);
        });



        return root;
    }

    private void setStyle1() {
        cardScroller.setClickable(true);
        cardScroller.setAnimType(InfiniteCardView.ANIM_TYPE_FRONT);
        cardScroller.setAnimInterpolator(new LinearInterpolator());
        cardScroller.setTransformerToFront(new AnimationTransformer() {
            @Override
            public void transformAnimation(View view, float fraction, int cardWidth, int cardHeight,
                                           int fromPosition, int toPosition) {
                int positionCount = fromPosition - toPosition;
                float scale = (0.8f - 0.1f * fromPosition) + (0.1f * fraction * positionCount);
                view.setScaleX(scale);
                view.setScaleY(scale);
                view.setRotationX(20 * (1 - fraction));
                if (fraction < 0.5) {
                    view.setTranslationY(-cardHeight * (0.8f - scale) * 0.5f - cardWidth * 0.02f
                            * fromPosition - cardHeight * fraction);
                } else {
                    view.setTranslationY(-cardHeight * (0.8f - scale) * 0.5f - cardWidth * (0.02f *
                            fromPosition - 0.02f * fraction * positionCount) - cardHeight * (1 - fraction));
                }
            }

            @Override
            public void transformInterpolatedAnimation(View view, float fraction, int cardWidth,
                                                       int cardHeight, int fromPosition, int toPosition) {
            }
        });
        cardScroller.setTransformerToBack(new DefaultTransformerToBack());
        cardScroller.setZIndexTransformerToBack(new DefaultZIndexTransformerCommon());
    }

    private void setStyle2() {
        cardScroller.setClickable(true);
        cardScroller.setAnimType(InfiniteCardView.ANIM_TYPE_FRONT_TO_LAST);
        cardScroller.setAnimInterpolator(new OvershootInterpolator(-18));
        cardScroller.setTransformerToFront(new DefaultTransformerToFront());
        cardScroller.setTransformerToBack(new AnimationTransformer() {
            @Override
            public void transformAnimation(View view, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {
                int positionCount = fromPosition - toPosition;
                float scale = (0.8f - 0.1f * fromPosition) + (0.1f * fraction * positionCount);
                view.setScaleX(scale);
                view.setScaleY(scale);
                if (fraction < 0.5) {
                    view.setRotationX(180 * fraction);
                } else {
                    view.setRotationX(180 * (1 - fraction));
                }
            }

            @Override
            public void transformInterpolatedAnimation(View view, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {
                int positionCount = fromPosition - toPosition;
                float scale = (0.8f - 0.1f * fromPosition) + (0.1f * fraction * positionCount);
                view.setTranslationY(-cardHeight * (0.8f - scale) * 0.5f - cardWidth * (0.02f *
                        fromPosition - 0.02f * fraction * positionCount));
            }
        });
        cardScroller.setZIndexTransformerToBack(new ZIndexTransformer() {
            @Override
            public void transformAnimation(CardItem card, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {
                if (fraction < 0.4f) {
                    card.zIndex = 1f + 0.01f * fromPosition;
                } else {
                    card.zIndex = 1f + 0.01f * toPosition;
                }
            }

            @Override
            public void transformInterpolatedAnimation(CardItem card, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {

            }
        });
    }

    private void setStyle3() {
        cardScroller.setClickable(false);
        cardScroller.setAnimType(InfiniteCardView.ANIM_TYPE_FRONT_TO_LAST);
        cardScroller.setAnimInterpolator(new OvershootInterpolator(-8));
        cardScroller.setTransformerToFront(new DefaultCommonTransformer());
        cardScroller.setTransformerToBack(new AnimationTransformer() {
            @Override
            public void transformAnimation(View view, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {
                int positionCount = fromPosition - toPosition;
                float scale = (0.8f - 0.1f * fromPosition) + (0.1f * fraction * positionCount);
                view.setScaleX(scale);
                view.setScaleY(scale);
                if (fraction < 0.5) {
                    view.setTranslationX(cardWidth * fraction * 1.5f);
                    view.setRotationY(-45 * fraction);
                } else {
                    view.setTranslationX(cardWidth * 1.5f * (1f - fraction));
                    view.setRotationY(-45 * (1 - fraction));
                }
            }

            @Override
            public void transformInterpolatedAnimation(View view, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {
                int positionCount = fromPosition - toPosition;
                float scale = (0.8f - 0.1f * fromPosition) + (0.1f * fraction * positionCount);
                view.setTranslationY(-cardHeight * (0.8f - scale) * 0.5f - cardWidth * (0.02f *
                        fromPosition - 0.02f * fraction * positionCount));
            }
        });
        cardScroller.setZIndexTransformerToBack(new ZIndexTransformer() {
            @Override
            public void transformAnimation(CardItem card, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {
                if (fraction < 0.5f) {
                    card.zIndex = 1f + 0.01f * fromPosition;
                } else {
                    card.zIndex = 1f + 0.01f * toPosition;
                }
            }

            @Override
            public void transformInterpolatedAnimation(CardItem card, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {

            }
        });
    }

    private void setStyle4() {

        cardScroller.setClickable(false);
        cardScroller.setAnimType(InfiniteCardView.ANIM_TYPE_FRONT_TO_LAST);
        cardScroller.setAnimInterpolator(new OvershootInterpolator(-20));
        cardScroller.setTransformerToFront(new DefaultCommonTransformer() );
        cardScroller.setTransformerToBack(new AnimationTransformer() {
            @Override
            public void transformAnimation(View view, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {
                int positionCount = fromPosition - toPosition;
                float scale = (0.8f - 0.1f * fromPosition) + (0.1f * fraction * positionCount);
                view.setScaleX(scale);
                view.setScaleY(scale);
                if (fraction < 0.5) {
                    view.setRotationX(100 * fraction);
                } else {
                    view.setRotationX(100 * (1 - fraction));
                }
            }

            @Override
            public void transformInterpolatedAnimation(View view, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {
                int positionCount = fromPosition - toPosition;
                float scale = (0.8f - 0.1f * fromPosition) + (0.1f * fraction * positionCount);
                view.setTranslationY(-cardHeight * (0.8f - scale) * 0.5f - cardWidth * (0.02f *
                        fromPosition - 0.02f * fraction * positionCount));
            }
        });
        cardScroller.setZIndexTransformerToBack(new ZIndexTransformer() {
            @Override
            public void transformAnimation(CardItem card, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {
                if (fraction < 0.4f) {
                    card.zIndex = 1f + 0.01f * fromPosition;
                } else {
                    card.zIndex = 1f + 0.01f * toPosition;
                }
            }

            @Override
            public void transformInterpolatedAnimation(CardItem card, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {

            }
        });



    }
}



class myCardAdapter extends BaseAdapter {

    List<User> users;
    Context context;

    public myCardAdapter(@NonNull Context context, @NonNull List<User> objects) {
        users = objects;
        this.context = context;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public User getItem(int i) {
        return users.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View root = LayoutInflater.from(context).inflate(R.layout.view_card, parent, false);
        TextView name = root.findViewById(R.id.view_card_name);
        name.setText(users.get(position).getFirstname());


        return root;
    }
}