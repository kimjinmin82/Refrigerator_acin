package com.example.kimseolki.refrigerator_acin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kimseolki.refrigerator_acin.model.Food;
import com.example.kimseolki.refrigerator_acin.service.GetFoods;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";

    public static final String ENDPOINT_URLF = "http://192.168.63.110:8000/";
    private TextView resultTv;
    private GetFoods getFoods;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ListView lvFoods;
    ArrayList<String> food_list;
    ArrayAdapter<String> food_adapter;
    private final long FINISH_INTERVAL_TIME = 2000;
    private long   backPressedTime = 0;
    private Fragment mFragment;
    int Navigation_id;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);

        /*if (savedInstanceState != null) {
            return;
        }*/

        setContentView(R.layout.activity_main);
        Navigation_id = R.id.nav_refigearator; // 초기화면 프래크먼트 설정
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                Intent sIntent = new Intent(getApplicationContext(), RefrigeratorRegister.class);
                startActivity(sIntent);

//                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.addTab(tabLayout.newTab().setText("냉장"));
        tabLayout.addTab(tabLayout.newTab().setText("냉동"));
        tabLayout.addTab(tabLayout.newTab().setText("실온"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Initializing ViewPager
       viewPager = (ViewPager) findViewById(R.id.view);

        // Creating TabPagerAdapter adapter
        TabPagerAdapter pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    private void loadFoods() {
        Call<ArrayList<Food>> call = getFoods.all();
        call.enqueue(new Callback<ArrayList<Food>>() {
            @Override
            public void onResponse(Call<ArrayList<Food>> call, Response<ArrayList<Food>> response) {
                List<Food> foods = response.body();
                displayResult(foods);
            }

            @Override
            public void onFailure(Call<ArrayList<Food>> call, Throwable t) {

            }
        });
    }

    private void displayResult(List<Food> foods) {
        if (foods != null) {
            String details ="";
            food_list.clear();
            for(int i=0; i<foods.size(); i++) {
                String name = foods.get(i).getFood_number() + " | " + foods.get(i).getFood_name() + "\n";
                details += name;
                food_list.add(name);
                food_adapter.notifyDataSetChanged();
            }
        } else {
            resultTv.setText("error");
        }
    }
    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if(Navigation_id != R.id.nav_refigearator){
            String title = "냉장고 관리";
            tabLayout.setVisibility(View.VISIBLE);
            mFragment = new Tab1();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_fragment_layout, mFragment);
            ft.commit();
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(title);
            }
            Navigation_id = R.id.nav_refigearator;
        }
        else if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime)
        {
            super.onBackPressed();
        }
        else
        {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "한번 더 뒤로가기 누르면 꺼버린다.", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Navigation_id = item.getItemId();

        String title = getString(R.string.app_name);
        if (Navigation_id == R.id.nav_refigearator) {
            tabLayout.setVisibility(View.VISIBLE);
            viewPager.setVisibility(View.VISIBLE);
            fab.setVisibility(View.VISIBLE);
            mFragment = new EmptyFragment();
            title="냉장고관리";

        } else if (Navigation_id == R.id.nav_expirationdate) {
            tabLayout.setVisibility(View.GONE);
            viewPager.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
            mFragment = new ExpriationFragment();
            title = "유통기한";

        } else if (Navigation_id == R.id.nav_recipe) {
            tabLayout.setVisibility(View.GONE);
            viewPager.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
            mFragment = new RecipeFragment();
            title = "레시피";

        } else if (Navigation_id == R.id.nav_shoppinglist) {
            // Handle the camera action
            tabLayout.setVisibility(View.GONE);
            viewPager.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
            mFragment = new ShoppingFragment();
            title = "쇼핑리스트";

        } else if (Navigation_id == R.id.nav_hazardfood) {
            tabLayout.setVisibility(View.GONE);
            viewPager.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
            mFragment = new HazardfoodFragment();
            title = "위해식품 알리미";

        } else if (Navigation_id == R.id.nav_settings) {
            tabLayout.setVisibility(View.GONE);
            viewPager.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
            mFragment = new SettingsFragment();
            title = "설정";
        }
        if (mFragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_fragment_layout, mFragment);
            // ft.addToBackStack(null); // 스택에 이전 프래그먼트 저장
            ft.commit();
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
