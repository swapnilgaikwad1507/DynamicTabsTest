package vidyoconnector.vidyo.com.tablayouttutorial;

import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vidyoconnector.vidyo.com.tablayouttutorial.adapters.PlansPagerAdapter;
import vidyoconnector.vidyo.com.tablayouttutorial.adapters.TabAdapter;
import vidyoconnector.vidyo.com.tablayouttutorial.utils.MySharedPreferences;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.view_pager)
    public ViewPager viewPager;

    @BindView(R.id.drawerLayout)
    public DrawerLayout drawer;

    @BindView(R.id.tab_layout)
    public TabLayout tabLayout;

    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @Inject
    MySharedPreferences mySharedPreferences;

    Activity mActivity;
    int mCurrentTabPosition = 0;
    String mExisitingFragments = "";
    String[] arrayCompleteData;

    @BindView(R.id.nav_view)
    public NavigationView nav_view;

    @BindView(R.id.imgDeleteCurrentTab)
    public ImageView imgDeleteCurrentTab;

    //test commit

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitViews();

        if (mySharedPreferences.getStringData(getString(R.string.fragment_data), "").equalsIgnoreCase("")) {
            mySharedPreferences.putStringData(getString(R.string.fragment_data), getString(R.string.default_page));
        }

        mExisitingFragments = mySharedPreferences.getStringData(getString(R.string.fragment_data), "");
        arrayCompleteData = mExisitingFragments.split(",");

        HandlingNavigationView(arrayCompleteData);

        AddPageToTabLayouts(arrayCompleteData);
    }

    private void InitViews() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setIcon(R.drawable.football);
        mActivity = MainActivity.this;
        ((MyApplication) getApplicationContext()).getMyComponent().inject(this);
    }

    private void HandlingNavigationView(String[] arrayData) {

        Menu menu = nav_view.getMenu();
        MenuItem item;
        menu.clear();

//        for (int i = 0; i < arrayData.length; i++) {
//        }
//        Menu drawerMenu = nav_view.getMenu();
//        ArrayList items = new ArrayList();
//        NavMenuClass navMenuObject = new NavMenuClass(menu, items);

        NavigationView.OnNavigationItemSelectedListener item_click_listener = new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                String name = item.getTitle().toString();

                if (name.equalsIgnoreCase(getString(R.string.add_page))) {
                    OpenPopUp();
                } else {
                    String mCompleteData = mySharedPreferences.getStringData(getString(R.string.fragment_data), "");
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

        /*Menu menu = navMenuObject.getMenu();
        MenuItem item;

        for (int i = 0; i < arrayData.length; i++) {
            menu.removeItem(i);
        }*/

        int uniqueIdForAddPage = arrayData.length;
        for (int temp = 0; temp <= arrayData.length - 1; temp++) {

            if (!arrayData[temp].equalsIgnoreCase(getString(R.string.default_page))) {
                item = menu.add(temp, temp, temp, arrayData[temp]);
                item.setIcon(R.drawable.football);
            } else {
                item = menu.add(temp, temp, temp, arrayData[temp]);
            }

        }

        item = menu.add(uniqueIdForAddPage, uniqueIdForAddPage, uniqueIdForAddPage, getString(R.string.add_page));
        item.setIcon(R.drawable.plus);

        TabAdapter pagerAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mCurrentTabPosition = tab.getPosition();
                viewPager.setCurrentItem(mCurrentTabPosition);

                if (mCurrentTabPosition == 0) {
                    imgDeleteCurrentTab.setVisibility(View.INVISIBLE);
                } else {
                    imgDeleteCurrentTab.setVisibility(View.VISIBLE);
                }
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
//                SharedPreferences.Editor mEdito = mPrefs.edit();

                mExisitingFragments = mySharedPreferences.getStringData(getString(R.string.fragment_data), "");
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
                    if (edtAddPage.getText().toString().trim().equalsIgnoreCase(getString(R.string.add_page))) {
                        Toast.makeText(MainActivity.this, getString(R.string.choose_some_other_name), Toast.LENGTH_SHORT).show();
                    } else {
                        String mCompleteData = mExisitingFragments + "," + edtAddPage.getText().toString().trim();

                        mySharedPreferences.putStringData(getString(R.string.fragment_data), mCompleteData);

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

    private void RemoveTabAndNavigationMenu() {
        mExisitingFragments = mySharedPreferences.getStringData(getString(R.string.fragment_data), "");
        arrayCompleteData = mExisitingFragments.split(",");
        ArrayList<String> arrData = new ArrayList<String>();
        arrData.addAll(Arrays.asList(arrayCompleteData));

        arrData.remove(mCurrentTabPosition);

        String mUpdatedFragments = "";
        for (int i = 0; i < arrData.size(); i++) {
            if (mUpdatedFragments.equalsIgnoreCase("")) {
                mUpdatedFragments = arrData.get(i);
            } else {
                mUpdatedFragments = mUpdatedFragments + "," + arrData.get(i);
            }
        }
        mySharedPreferences.putStringData(getString(R.string.fragment_data), mUpdatedFragments);


        mExisitingFragments = mySharedPreferences.getStringData(getString(R.string.fragment_data), "");
        arrayCompleteData = mExisitingFragments.split(",");

        HandlingNavigationView(arrayCompleteData);

        AddPageToTabLayouts(arrayCompleteData);
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

    @OnClick({R.id.imgDeleteCurrentTab})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgDeleteCurrentTab:{
                RemoveTabAndNavigationMenu();
            }
            break;
        }
    }
}