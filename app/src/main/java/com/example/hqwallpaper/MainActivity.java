package com.example.hqwallpaper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ShareCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolBar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private sectionpagerAdapter adapter;

    private AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // for admob initialize //

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        // for admob initialize //

        toolBar = findViewById(R.id.toolBar);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
       // adView = findViewById(R.id.adView);

        /*// for Banner add
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);*/

        setSupportActionBar(toolBar);

        adapter = new sectionpagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_baseline_lock_24);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_baseline_settings_24);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.actionFavorites) {
            Intent in = new Intent(MainActivity.this, FavoritesActivity.class);
            startActivity(in);
        }
        else if (id == R.id.actionAbout) {

            Toast.makeText(this, "About clicked", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.actionSetting) {

            Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.actionPrivacy) {
            String privacyLink = "https://sites.google.com/view/hqwallpaper/home";
            /*Intent privacyIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(privacyLink));
            startActivity(privacyIntent);*/
            Intent in = new Intent(MainActivity.this, WebActivity.class);
            in.putExtra("URL", privacyLink);
            startActivity(in);
        } else if (id == R.id.actionShare) {

            try {
                ShareCompat.IntentBuilder intentBuilder = ShareCompat.IntentBuilder.from(MainActivity.this);
                intentBuilder.setType("text/plain");
                intentBuilder.setText("Hi! check out this app with Amazing new HD Wallpapers https://play.google.com/store/apps/details?id=" + getPackageName());
                intentBuilder.setSubject(getString(R.string.app_name));
                startActivity(intentBuilder.getIntent());
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Unable to perform this action", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.actionRate) {
            try {
                String storeURL = "market://details?id=" + getPackageName();
                Intent storeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(storeURL));
                startActivity(storeIntent);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    String storeURL = "https://play.google.com/store/apps/details?id=" + getPackageName();
                    Intent storeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(storeURL));
                    startActivity(storeIntent);
                } catch (Exception exception) {
                    exception.printStackTrace();
                    Toast.makeText(this, "Unable to perform this action", Toast.LENGTH_SHORT).show();
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }


    public class sectionpagerAdapter extends FragmentPagerAdapter {

        public sectionpagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {

            if (position == 0) {
                return new LatestFragment();
            }else if (position == 1) {
                return new CategoriesFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
           if (position == 0) {
               return "Latest";
           } else if (position == 1){
               return "Categories";
           }
            return super.getPageTitle(position);
        }
    }
}

