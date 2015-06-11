package rwth.elearning.ecognita.client.ecognitaclient.courses;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import rwth.elearning.ecognita.client.ecognitaclient.R;
import rwth.elearning.ecognita.client.ecognitaclient.authorization.ActivityWithLogoutMenu;
import rwth.elearning.ecognita.client.ecognitaclient.authorization.LogInFragment;
import rwth.elearning.ecognita.client.ecognitaclient.model.NavDrawerItem;
import rwth.elearning.ecognita.client.ecognitaclient.model.User;

/**
 * Created by ekaterina on 22.05.2015.
 */
@SuppressWarnings("deprecation")
public class MyCoursesActivity extends ActivityWithLogoutMenu {

    private DrawerLayout drawerLayout;
    private ListView drawerListView;
    private LinearLayout slideMenu;
    private TextView emailAddress;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private ListAdapter adapter;
    private User loggedInUser;

    private static final int HOME_POSITION = 0;
    private static final int SEARCH_POSITION = 1;
    private static final int STATISTICS_POSITION = 2;
    private static final int SETTINGS_POSITION = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_courses);

        this.loggedInUser = LogInFragment.getConnectedUser();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerListView = (ListView) findViewById(R.id.list_slidermenu);
        slideMenu = (LinearLayout) findViewById(R.id.linear_layout_menu);
        emailAddress = (TextView) findViewById(R.id.email_text_slider_menu);
        emailAddress.setText(loggedInUser.getEmailAddress());

        drawerListView.setOnItemClickListener(new SlideMenuClickListener());
        this.adapter = getInitAdapter();
        drawerListView.setAdapter(this.adapter);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        this.actionBarDrawerToggle = getActionBarDrawerToggle();
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        if (savedInstanceState == null) {
            displayView(HOME_POSITION);
        }
    }

    private ActionBarDrawerToggle getActionBarDrawerToggle() {
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

    private ListAdapter getInitAdapter() {
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

    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            displayView(position);
        }
    }

    private void displayView(int position) {
        Fragment fragment = null;
        switch (position) {
            case HOME_POSITION:
                fragment = MyCoursesHomeFragment.newInstance(this.loggedInUser);
                break;
            case SEARCH_POSITION:
                //TODO:
                break;
            case STATISTICS_POSITION:
                //TODO
                break;
            case SETTINGS_POSITION:
                //TODO
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

            drawerListView.setItemChecked(position, true);
            drawerListView.setSelection(position);
            drawerLayout.closeDrawer(slideMenu);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
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
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = drawerLayout.isDrawerOpen(slideMenu);
        menu.findItem(ActivityWithLogoutMenu.MENU_LOGOUT).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }
}
