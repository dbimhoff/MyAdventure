package webdev.myadventure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.android.material.tabs.TabLayoutMediator;

import webdev.myadventure.Modules.CardFragment;
import webdev.myadventure.Modules.EventFragment;
import webdev.myadventure.Modules.ProfileFragment;
import webdev.myadventure.Modules.QRScannerFragment;

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

    }

    private void initViewPager() {
        myFragmentStateAdapter adapter = new myFragmentStateAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageSelected(int position) {
                if(position == 0)
                    bottomNav.setSelectedItemId(R.id.cards);
                else if(position ==1)
                    bottomNav.setSelectedItemId(R.id.events);
                else if(position == 2)
                    bottomNav.setSelectedItemId(R.id.qr_scanner);
                else
                    bottomNav.setSelectedItemId(R.id.profile);
            }
        });





    }

    private void initBottomNav() {
        bottomNav.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);

        bottomNav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.cards:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.events:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.qr_scanner:
                    viewPager.setCurrentItem(2);
                    return true;
                case R.id.profile:
                    viewPager.setCurrentItem(3);
                    return true;
            }
            return false;
        });
    }


    class myFragmentStateAdapter extends FragmentStateAdapter {

        public myFragmentStateAdapter(FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            //TODO change so it makes different fragments here
            if(position == 0)
                return new CardFragment();
            else if(position == 1)
                return new EventFragment();
            else if(position ==2)
                return new QRScannerFragment();
            else
                return new ProfileFragment();
        }
        @Override
        public int getItemCount() {
            return 4;
        }
    }

}
