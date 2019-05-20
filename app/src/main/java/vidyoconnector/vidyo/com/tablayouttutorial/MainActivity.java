package vidyoconnector.vidyo.com.tablayouttutorial;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import vidyoconnector.vidyo.com.tablayouttutorial.pojo.NavMenuClass;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.view_pager)
    public ViewPager viewPager;

    @BindView(R.id.drawerLayout)
    public DrawerLayout drawer;

    @BindView(R.id.tab_layout)
    public TabLayout tabLayout;

    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    public SharedPreferences mPrefs;
    Activity mActivity;

    @BindView(R.id.nav_view)
    public NavigationView nav_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitViews();

        if (mPrefs.getString(getString(R.string.fragment_data), "").equalsIgnoreCase("")) {
            SharedPreferences.Editor mEdito = mPrefs.edit();
            mEdito.putString(getString(R.string.fragment_data), getString(R.string.default_page));
            mEdito.commit();
        }

        String mCompleteData = mPrefs.getString(getString(R.string.fragment_data), "");
        String[] arrayData = mCompleteData.split(",");

        HandlingNavigationView(arrayData);

        AddPageToTabLayouts(arrayData);
    }

    private void InitViews() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        mPrefs = getSharedPreferences(getString(R.string.my_data), MODE_PRIVATE);
        mActivity = MainActivity.this;
    }

    private void HandlingNavigationView(String[] arrayData) {

        Menu drawerMenu = nav_view.getMenu();
        ArrayList items = new ArrayList();
        NavMenuClass navMenuObject = new NavMenuClass(drawerMenu, items);

        NavigationView.OnNavigationItemSelectedListener item_click_listener = new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                String name = item.getTitle().toString();

                if (name.equalsIgnoreCase(getString(R.string.add_page))) {
                    OpenPopUp();
                } else {
                    String mCompleteData = mPrefs.getString(getString(R.string.fragment_data), "");
                    String[] arrayData = mCompleteData.split(",");
                    int tabToSelect = 0;
                    for (int i = 0; i < arrayData.length; i++) {
                        if (arrayData[i].equalsIgnoreCase(name)) {
                            tabToSelect = i;
                            break;
                        }
                    }
                    TabLayout.Tab tab = tabLayout.getTabAt(tabToSelect);
                    tab.select();
                }

                drawer.closeDrawers();
                return true;
            }
        };

        nav_view.setNavigationItemSelectedListener(item_click_listener);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open,
                R.string.close);
        drawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        Menu menu = navMenuObject.getMenu();
        MenuItem item;

        for (int i = 0; i < arrayData.length; i++) {
            menu.removeItem(i);
        }

        boolean IsAddPageAvailable = false;
        int uniqueIdForAddPage = arrayData.length;
        for (int temp = 0; temp <= arrayData.length - 1; temp++) {

            if (!arrayData[temp].equalsIgnoreCase(getString(R.string.default_page))) {
                item = menu.add(temp, temp, temp, arrayData[temp]);
                item.setIcon(R.drawable.football);
            } else {
                item = menu.add(temp, temp, temp, arrayData[temp]);
            }

            /*if (arrayData[temp].equalsIgnoreCase(getString(R.string.add_page))) {
                IsAddPageAvailable = true;
            }*/
        }

        item = menu.add(uniqueIdForAddPage, uniqueIdForAddPage, uniqueIdForAddPage, getString(R.string.add_page));
        item.setIcon(R.drawable.plus);

        TabAdapter pagerAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
    }

    private void AddPageToTabLayouts(String[] arrayData) {
        tabLayout.removeAllTabs();
        for (int k = 0; k < arrayData.length; k++) {
            tabLayout.addTab(tabLayout.newTab().setText(arrayData[k]));
        }

        PlansPagerAdapter adapter = new PlansPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        if (tabLayout.getTabCount() == 2) {
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        } else {
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
    }

    private void OpenPopUp() {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.add_pages_dialog, viewGroup, false);

        final EditText edtAddPage = (EditText) dialogView.findViewById(R.id.edtAddPage);
        Button buttonAddPage = (Button) dialogView.findViewById(R.id.buttonAddPage);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();

        buttonAddPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor mEdito = mPrefs.edit();

                String mExisitingFragments = mPrefs.getString(getString(R.string.fragment_data), "");
                String[] arrayData = mExisitingFragments.split(",");
                boolean IsSameNameAvailable = false;

                for (int i = 0; i < arrayData.length; i++) {
                    if (arrayData[i].equalsIgnoreCase(edtAddPage.getText().toString().trim())) {
                        IsSameNameAvailable = true;
                    }
                }

                if (IsSameNameAvailable) {
                    Toast.makeText(MainActivity.this, getString(R.string.page_already_exist_warning), Toast.LENGTH_SHORT).show();
                } else {
                    if(edtAddPage.getText().toString().trim().equalsIgnoreCase(getString(R.string.add_page))){
                        Toast.makeText(MainActivity.this, getString(R.string.choose_some_other_name), Toast.LENGTH_SHORT).show();
                    }else{
                        String mCompleteData = mExisitingFragments + "," + edtAddPage.getText().toString().trim();

                        mEdito.putString(getString(R.string.fragment_data), mCompleteData);
                        mEdito.commit();

                        String[] arrayCompleteData = mCompleteData.split(",");
                        HandlingNavigationView(arrayCompleteData);
                        AddPageToTabLayouts(arrayCompleteData);
                        alertDialog.dismiss();
                    }

                }

            }
        });

        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}