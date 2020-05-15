package webdev.myadventure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

import webdev.myadventure.Modules.CardFragment;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActLog";

    BottomNavigationView bottomNav;
    ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_nav);
        initBottomNav();
        viewPager = findViewById(R.id.view_pager);
        initViewPager();

        CardFragment frag = new CardFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment_holder, frag);
        transaction.commit();
    }

    private void initViewPager() {
        myFragmentStateAdapter adapter = new myFragmentStateAdapter(this);

    }

    private void initBottomNav() {



        bottomNav.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);

        bottomNav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.cards:
                    Log.i(TAG, "onNavigationItemSelected: Cards Clicked");
                    //card icon clicked
                    return true;
                case R.id.events:
                    //events icon clicked
                    Log.i(TAG, "onNavigationItemSelected: Events clicked");
                    return true;
                case R.id.qr_scanner:
                    //qr scanner clicked
                    Log.i(TAG, "onNavigationItemSelected: QR Scanner clicked");
                    return true;
                case R.id.profile:
                    //profile clicked
                    Log.i(TAG, "onNavigationItemSelected: Profile clicked");
                    return true;
            }

            return false;
        });
    }


    class myFragmentStateAdapter extends FragmentStateAdapter {

        public myFragmentStateAdapter(Fragment fragment) {
            super(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            //TODO change so it makes different fragments here
            Fragment frag = new CardFragment();
            return frag;

        }

        @Override
        public int getItemCount() {
            return 4;
        }
    }

}
