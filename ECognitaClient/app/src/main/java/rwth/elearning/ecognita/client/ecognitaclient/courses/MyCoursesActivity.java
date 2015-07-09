package rwth.elearning.ecognita.client.ecognitaclient.courses;

import android.annotation.TargetApi;
import android.graphics.Outline;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import rwth.elearning.ecognita.client.ecognitaclient.R;
import rwth.elearning.ecognita.client.ecognitaclient.authorization.ActivityWithLogoutMenu;
import rwth.elearning.ecognita.client.ecognitaclient.authorization.LogInFragment;
import rwth.elearning.ecognita.client.ecognitaclient.model.NavDrawerItem;
import rwth.elearning.ecognita.client.ecognitaclient.model.User;
import rwth.elearning.ecognita.client.ecognitaclient.settings.SettingsFragment;
import rwth.elearning.ecognita.client.ecognitaclient.statistics.StatisticsFragment;

/**
 * Created by ekaterina on 22.05.2015.
 */
@SuppressWarnings("deprecation")
public class MyCoursesActivity extends ActivityWithLogoutMenu {
    private static final int MENU_REFRESH = Menu.FIRST + 1;
    protected DrawerLayout drawerLayout;
    protected ListView drawerListView;
    protected LinearLayout slideMenu;
    protected TextView emailAddress;
    protected ActionBarDrawerToggle actionBarDrawerToggle;

    protected ListAdapter adapter;
    protected User loggedInUser;

    private static final int HOME_POSITION = 0;
    private static final int SEARCH_POSITION = 1;
    private static final int STATISTICS_POSITION = 2;
    private static final int SETTINGS_POSITION = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity(savedInstanceState);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void initActivity(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_courses);
        Button addCourseButton = (Button) findViewById(R.id.add_course_cutton);

        ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline mOutlineCircle) {
                // Or read size directly from the view's width/height
                int shapeSize = getResources().getDimensionPixelSize(R.dimen.shape_size);
                mOutlineCircle.setRoundRect(0, 0, shapeSize, shapeSize, shapeSize / 2);
            }
        };
        addCourseButton.setOutlineProvider(viewOutlineProvider);
        addCourseButton.setClipToOutline(true);

        if (addCourseButton != null) {
            addCourseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MyCoursesActivity.this, AllCoursesActivity.class);
                    startActivity(intent);
                }
            });
        }
        prepareSlidingMenu(R.id.frame_container);

        if (savedInstanceState == null) {
            displayView(HOME_POSITION, this, R.id.frame_container);
        }
    }

    protected void prepareSlidingMenu(@IdRes int containerViewId) {
        this.loggedInUser = LogInFragment.getConnectedUser();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerListView = (ListView) findViewById(R.id.list_slidermenu);
        slideMenu = (LinearLayout) findViewById(R.id.linear_layout_menu);
        emailAddress = (TextView) findViewById(R.id.email_text_slider_menu);
        emailAddress.setText(loggedInUser.getEmailAddress());

        drawerListView.setOnItemClickListener(new SlideMenuClickListener(this, containerViewId));
        this.adapter = getInitAdapter();
        drawerListView.setAdapter(this.adapter);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        this.actionBarDrawerToggle = getActionBarDrawerToggle();
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
    }

    protected ActionBarDrawerToggle getActionBarDrawerToggle() {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.drawable.ic_slide_menu_drawer,
                R.string.app_name,
                R.string.app_name
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(MyCoursesActivity.this.getTitle());
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(MyCoursesActivity.this.getTitle());
                invalidateOptionsMenu();
            }
        };
        return actionBarDrawerToggle;
    }

    protected ListAdapter getInitAdapter() {
        String[] navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        TypedArray navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);
        NavDrawerListAdapter adapter = new NavDrawerListAdapter(getApplicationContext());
        adapter.add(new NavDrawerItem(navMenuTitles[HOME_POSITION], navMenuIcons.getResourceId(HOME_POSITION, -1)));
        adapter.add(new NavDrawerItem(navMenuTitles[SEARCH_POSITION], navMenuIcons.getResourceId(SEARCH_POSITION, -1)));
        adapter.add(new NavDrawerItem(navMenuTitles[STATISTICS_POSITION], navMenuIcons.getResourceId(STATISTICS_POSITION, -1)));
        adapter.add(new NavDrawerItem(navMenuTitles[SETTINGS_POSITION], navMenuIcons.getResourceId(SETTINGS_POSITION, -1)));
        return adapter;
    }

    protected class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        private ActivityWithLogoutMenu activity;
        private int containerId;

        public SlideMenuClickListener(ActivityWithLogoutMenu activity, @IdRes int containerViewId) {
            this.activity = activity;
            this.containerId = containerViewId;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            displayView(position, activity, containerId);
        }
    }

    protected void displayView(int position, ActivityWithLogoutMenu activity, @IdRes int containerViewId) {
        Fragment fragment = null;
        switch (position) {
            case HOME_POSITION:
                fragment = MyCoursesHomeFragment.newInstance(this.loggedInUser);
                break;
            case SEARCH_POSITION:
                //TODO:
                break;
            case STATISTICS_POSITION:
                fragment = new StatisticsFragment();
                break;
            case SETTINGS_POSITION:
                fragment = new SettingsFragment();
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(containerViewId, fragment).commit();
            Button addCourseButton = (Button) findViewById(R.id.add_course_cutton);
            addCourseButton.setVisibility(fragment instanceof MyCoursesHomeFragment ? View.VISIBLE : View.INVISIBLE);
            drawerListView.setItemChecked(position, true);
            drawerListView.setSelection(position);
            drawerLayout.closeDrawer(slideMenu);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (actionBarDrawerToggle != null) actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        if (item.getItemId() == MENU_REFRESH) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            List<Fragment> currentFragments = fragmentManager.getFragments();
            if (currentFragments != null) {
                for (Fragment fragment : fragmentManager.getFragments()) {
                    fragmentManager.beginTransaction().remove(fragment).commit();
                }
                initActivity(getIntent().getExtras());
            } else {
                initActivity(getIntent().getExtras());
               // displayView(HOME_POSITION, this, R.id.frame_container);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = drawerLayout.isDrawerOpen(slideMenu);
        menu.findItem(ActivityWithLogoutMenu.MENU_LOGOUT).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE, MENU_REFRESH, Menu.NONE, R.string.refresh_menu_label);
        return true;
    }
}
