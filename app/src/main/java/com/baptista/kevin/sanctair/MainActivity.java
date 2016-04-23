package com.baptista.kevin.sanctair;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;
import com.google.android.gms.maps.model.UrlTileProvider;

import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.app.DatePickerDialog.OnDateSetListener;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback,
        OnDateSetListener, DetailsPage.OnFragmentInteractionListener,
        CausesPage.OnFragmentInteractionListener, SolutionsPage.OnFragmentInteractionListener,
        SensitiveGroupsPage.OnFragmentInteractionListener,LongTermPage.OnFragmentInteractionListener
        ,ShortTermPage.OnFragmentInteractionListener, SettingsPage.OnFragmentInteractionListener{

    public String overlay = "MODIS_Terra_Aerosol";
    public String ratingOverlay = "usepa-aqi";
    public Fragment currentFragment = null;
    public String fragmentType = null;
    public String tab = "Overlay";

    static Calendar calendar = Calendar.getInstance();
    static SimpleDateFormat sysFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    static String sysDate = sysFormat.format(calendar.getTime());
    static DateFormat userFormat = (DateFormat.getDateInstance());
    static String userDate = userFormat.format(calendar.getTime());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final SupportMapFragment supportMapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);

        TextView displayDate = (TextView)findViewById(R.id.displayDate);
        TextView displayOverlay = (TextView)findViewById(R.id.overlayDisplay);
        TextView displayMarkers = (TextView)findViewById(R.id.markersDisplay);

        displayOverlay.setText(R.string.displayAerosol);
        displayMarkers.setText(R.string.displayComposite);
        displayDate.setText(userDate);

        displayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "datePicker");
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"sanctairdata@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Sanctair data");
                i.putExtra(Intent.EXTRA_TEXT   , "Write your idea and the area it affects here! If you are reporting symptoms please include any medical conditions you have.");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //There is no database yet, however once there is a dialog box will appear for the use to
        //add their idea which will automatically send their location which can be done  by loading
        //the user's location at startup and then using getLastLocation() or its equivalent after
        //switching from Google Maps to and open source map (probably Mapsforge)
        fab.setVisibility(View.GONE);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final String PREFS_NAME = "MyPrefsFile";

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean("my_first_time", true)) {
            //the app is being launched for first time, do something
            Log.d("Comments", "First time");

            startActivity(new Intent(getApplicationContext(),FirstLaunch.class));

            // record the fact that the app has been started at least once
            settings.edit().putBoolean("my_first_time", false).commit();
        }
    }

    public void reloadMap(){
        SupportMapFragment supportMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
    }

    public void reloadFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentType.equals("details")){
            Bundle bundle = new Bundle();
            bundle.putString("overlay",overlay);
            bundle.putString("ratingOverlay",ratingOverlay);
            bundle.putString("tab",tab);
            currentFragment = new DetailsPage();
            currentFragment.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.fragment, currentFragment).commit();
        }
        else if (fragmentType.equals("causes")){
            Bundle bundle = new Bundle();
            bundle.putString("overlay",overlay);
            bundle.putString("ratingOverlay",ratingOverlay);
            bundle.putString("tab",tab);
            currentFragment = new CausesPage();
            currentFragment.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.fragment, currentFragment).commit();
        }
        else if (fragmentType.equals("solutions")){
            Bundle bundle = new Bundle();
            bundle.putString("overlay",overlay);
            bundle.putString("ratingOverlay",ratingOverlay);
            bundle.putString("tab",tab);
            currentFragment = new SolutionsPage();
            currentFragment.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.fragment, currentFragment).commit();
        }
        else if (fragmentType.equals("sensitive")){
            Bundle bundle = new Bundle();
            bundle.putString("overlay",overlay);
            bundle.putString("ratingOverlay",ratingOverlay);
            bundle.putString("tab",tab);
            currentFragment = new SensitiveGroupsPage();
            currentFragment.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.fragment, currentFragment).commit();
        }
        else if (fragmentType.equals("short")){
            Bundle bundle = new Bundle();
            bundle.putString("overlay",overlay);
            bundle.putString("ratingOverlay",ratingOverlay);
            bundle.putString("tab",tab);
            currentFragment = new ShortTermPage();
            currentFragment.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.fragment, currentFragment).commit();
            fragmentType = "short";
        }
        else if (fragmentType.equals("long")){
            Bundle bundle = new Bundle();
            bundle.putString("overlay",overlay);
            bundle.putString("ratingOverlay",ratingOverlay);
            bundle.putString("tab",tab);
            currentFragment = new LongTermPage();
            currentFragment.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.fragment, currentFragment).commit();
        }
        //Probably not the best way, but it works
    }

    private boolean getHealthFromSP(String key){
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("USER_HEALTH", android.content.Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

    //TODO: Make context menu radio button group (prettier?)

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //Make relevant have an asterisk (Bold doesn't work for some reason)
        //Not sure what to do with allergens yet, maybe set up alerts for specific buildings
        Boolean anemia = getHealthFromSP("anemia");
        Boolean cardio = getHealthFromSP("cardiovascular");
        Boolean copd = getHealthFromSP("copd");
        Boolean pneumonitis = getHealthFromSP("pneumonitis");
        Boolean asthma = getHealthFromSP("asthma");


        if (v.getId() == R.id.overlaySelect) {
            getMenuInflater().inflate(R.menu.overlays_menu, menu);
            menu.setHeaderTitle(R.string.overlays);
            MenuItem co = menu.findItem(R.id.carbonMonoxideOverlay);
            MenuItem aerosol = menu.findItem(R.id.aerosol);
            MenuItem humidity = menu.findItem(R.id.humidity);

            if (anemia || copd || cardio){
                co.setTitle(R.string.boldCarbonMonoxide);
            }
            if (copd || asthma || cardio){
                aerosol.setTitle(R.string.boldAerosol);
            }
            if (pneumonitis){
                humidity.setTitle(R.string.boldHumidity);
            }
        }
        else if (v.getId() == R.id.ratingSelect){
            getMenuInflater().inflate(R.menu.ratings_menu, menu);
            menu.setHeaderTitle(R.string.aqi);
            MenuItem co = menu.getItem(R.id.corating);
            MenuItem pm25 = menu.getItem(R.id.pm25);
            MenuItem pm10 = menu.getItem(R.id.pm10);
            MenuItem ozone = menu.getItem(R.id.ozoneRating);
            MenuItem so2 = menu.getItem(R.id.so2rating);

            if (anemia || copd || cardio){
                co.setTitle(R.string.boldCarbonMonoxide);
            }
            if (copd || asthma || cardio){
                pm25.setTitle("*Fine Particle Pollution*");
                pm10.setTitle("*Coarse Particle Pollution*");
            }
            if (asthma || copd || pneumonitis){
                ozone.setTitle("*Ozone*");
            }
            if (asthma){
                so2.setTitle("*Sulfur Dioxide*");
            }

        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        SupportMapFragment supportMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        TextView displayOverlay = (TextView)findViewById(R.id.overlayDisplay);
        TextView displayMarkers = (TextView)findViewById(R.id.markersDisplay);

        switch (item.getItemId()){
            case R.id.carbonMonoxideOverlay:
                overlay = "AIRS_CO_Total_Column_Day";
                tab = "Overlay";
                displayOverlay.setText(R.string.displayCOOverlay);
                if (supportMapFragment.isVisible()){
                    reloadMap();
                }
                else {
                    reloadFragment();
                }
                tab = "Overlay";
                break;
/*            case R.id.nitricAcid:
                overlay = "MLS_HNO3_46hPa_Day";
                reloadMap();
                break;
            case R.id.dustscore:
                overlay = "AIRS_Dust_Score";
                reloadMap();
                break;*/
            case R.id.ozone:
                overlay = "MLS_O3_46hPa_Day";
                tab = "Overlay";
                displayOverlay.setText(R.string.displayOzoneOverlay);
                if (supportMapFragment.isVisible()){
                    reloadMap();
                }
                else {
                    reloadFragment();
                }
                break;
            case R.id.aerosol:
                overlay = "MODIS_Terra_Aerosol";
                tab = "Overlay";
                displayOverlay.setText(R.string.displayAerosol);
                if (supportMapFragment.isVisible()){
                    reloadMap();
                }
                else {
                    reloadFragment();
                }
                break;
            case R.id.humidity:
                overlay = "AIRS_RelativeHumidity_400hPa_Day";
                tab = "Overlay";
                displayOverlay.setText(R.string.displayHumidity);
                if (supportMapFragment.isVisible()){
                    reloadMap();
                }
                else {
                    reloadFragment();
                }
                break;
            case R.id.noOverlay:
                overlay = "noOverlay";
                tab = "Overlay";
                displayOverlay.setText(R.string.displayNoOverlay);
                if (supportMapFragment.isVisible()){
                    reloadMap();
                }
                else {
                    reloadFragment();
                }
                break;
            case R.id.compositeaqi:
                ratingOverlay = "usepa-aqi";
                tab = "Markers";
                displayMarkers.setText(R.string.displayComposite);
                if (supportMapFragment.isVisible()){
                    reloadMap();
                }
                else {
                    reloadFragment();
                }

                break;
            case R.id.pm25:
                ratingOverlay = "usepa-pm25";
                tab = "Markers";
                displayMarkers.setText(R.string.displayPM25);
                if (supportMapFragment.isVisible()){
                    reloadMap();
                }
                else {
                    reloadFragment();
                }
                break;
            case R.id.pm10:
                ratingOverlay = "usepa-10";
                tab = "Markers";
                displayMarkers.setText(R.string.displayPM10);
                if (supportMapFragment.isVisible()){
                    reloadMap();
                }
                else {
                    reloadFragment();
                }
                break;
            case R.id.ozoneRating:
                ratingOverlay = "usepa-o3";
                tab = "Markers";
                displayMarkers.setText(R.string.displayOzoneMarkers);
                if (supportMapFragment.isVisible()){
                    reloadMap();
                }
                else {
                    reloadFragment();
                }

                break;
/*            case R.id.no2rating:
                ratingOverlay = "usepa-no2";
                if (supportMapFragment.isVisible()){
                    reloadMap();
                }
                else {
                    reloadFragment();
                }
                break;*/
            case R.id.so2rating:
                ratingOverlay = "usepa-so2";
                tab = "Markers";
                displayMarkers.setText(R.string.displaySO2);
                if (supportMapFragment.isVisible()){
                    reloadMap();
                }
                else {
                    reloadFragment();
                }
                break;
            case R.id.corating:
                ratingOverlay = "usepa-co";
                tab = "Markers";
                displayMarkers.setText(R.string.displayCOMarkers);
                if (supportMapFragment.isVisible()){
                    reloadMap();
                }
                else {
                    reloadFragment();
                }
                break;
            case R.id.noRating:
                ratingOverlay = "noMarkers";
                tab = "Markers";
                displayMarkers.setText(R.string.displayNoMarkers);
                if (supportMapFragment.isVisible()){
                    reloadMap();
                }
                else {
                    reloadFragment();
                }
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        FragmentManager fragmentManager = getSupportFragmentManager();
        SupportMapFragment supportMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        TextView displayDate = (TextView)findViewById(R.id.displayDate);
        TextView displayOverlay = (TextView)findViewById(R.id.overlayDisplay);
        TextView displayMarkers = (TextView)findViewById(R.id.markersDisplay);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (!supportMapFragment.isVisible()){
            fragmentManager.beginTransaction().show(supportMapFragment).commit();
            reloadMap();
            //Reloads in case user changed overlay while viewing text
            displayDate.setVisibility(View.VISIBLE);
            displayMarkers.setVisibility(View.VISIBLE);
            displayOverlay.setVisibility(View.VISIBLE);
            fab.setVisibility(View.INVISIBLE);
        }
        else {
            super.onBackPressed();
            //// FIXME: 4/14/2016 Back button taking long to return to home screen SOMETIMES, possibly emulator problem
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        View ratingSelect = findViewById(R.id.ratingSelect);
        registerForContextMenu(ratingSelect);
        View overlaySelect = findViewById(R.id.overlaySelect);
        registerForContextMenu(overlaySelect);

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            SupportMapFragment supportMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
            TextView displayDate = (TextView)findViewById(R.id.displayDate);
            TextView displayOverlay = (TextView)findViewById(R.id.overlayDisplay);
            TextView displayMarkers = (TextView)findViewById(R.id.markersDisplay);
            fragmentManager.beginTransaction().hide(supportMapFragment).commit();
            displayDate.setVisibility(View.GONE);
            displayOverlay.setVisibility(View.GONE);
            displayMarkers.setVisibility(View.GONE);
            ListFragment settings = new SettingsPage();
            fragmentManager.beginTransaction().replace(R.id.fragment, settings).commit();
        }

        if (id == R.id.ratingSelect){
            ratingSelect.showContextMenu();
        }

        if (id == R.id.overlaySelect){
            overlaySelect.showContextMenu();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentManager fragmentManager = getSupportFragmentManager();
        SupportMapFragment supportMapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        TextView displayDate = (TextView)findViewById(R.id.displayDate);
        TextView displayOverlay = (TextView)findViewById(R.id.overlayDisplay);
        TextView displayMarkers = (TextView)findViewById(R.id.markersDisplay);

        int id = item.getItemId();

        if (id == R.id.nav_details) {
            fragmentManager.beginTransaction().hide(supportMapFragment).commit();
            displayDate.setVisibility(View.GONE);
            displayOverlay.setVisibility(View.GONE);
            displayMarkers.setVisibility(View.GONE);
            Bundle bundle = new Bundle();
            bundle.putString("overlay",overlay);
            bundle.putString("ratingOverlay",ratingOverlay);
            bundle.putString("tab",tab);
            currentFragment = new DetailsPage();
            currentFragment.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.fragment, currentFragment).commit();
            fragmentType = "details";

        } else if (id == R.id.nav_causes) {
            fragmentManager.beginTransaction().hide(supportMapFragment).commit();
            displayDate.setVisibility(View.GONE);
            displayOverlay.setVisibility(View.GONE);
            displayMarkers.setVisibility(View.GONE);
            Bundle bundle = new Bundle();
            bundle.putString("overlay",overlay);
            bundle.putString("ratingOverlay",ratingOverlay);
            bundle.putString("tab",tab);
            currentFragment = new CausesPage();
            currentFragment.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.fragment, currentFragment).commit();
            fragmentType = "causes";

        } else if (id == R.id.nav_solutions) {
            fragmentManager.beginTransaction().hide(supportMapFragment).commit();
            displayDate.setVisibility(View.GONE);
            displayOverlay.setVisibility(View.GONE);
            displayMarkers.setVisibility(View.GONE);
            Bundle bundle = new Bundle();
            bundle.putString("overlay",overlay);
            bundle.putString("ratingOverlay",ratingOverlay);
            bundle.putString("tab",tab);
            currentFragment = new SolutionsPage();
            currentFragment.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.fragment, currentFragment).commit();
            fragmentType = "solutions";

        } else if (id == R.id.sensitiveGroups) {
            fragmentManager.beginTransaction().hide(supportMapFragment).commit();
            displayDate.setVisibility(View.GONE);
            displayOverlay.setVisibility(View.GONE);
            displayMarkers.setVisibility(View.GONE);
            Bundle bundle = new Bundle();
            bundle.putString("overlay",overlay);
            bundle.putString("ratingOverlay",ratingOverlay);
            bundle.putString("tab",tab);
            currentFragment = new SensitiveGroupsPage();
            currentFragment.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.fragment, currentFragment).commit();
            fragmentType = "sensitive";
        } else if (id == R.id.nav_shortTerm) {
            fragmentManager.beginTransaction().hide(supportMapFragment).commit();
            displayDate.setVisibility(View.GONE);
            displayOverlay.setVisibility(View.GONE);
            displayMarkers.setVisibility(View.GONE);
            Bundle bundle = new Bundle();
            bundle.putString("overlay",overlay);
            bundle.putString("ratingOverlay",ratingOverlay);
            bundle.putString("tab",tab);
            currentFragment = new ShortTermPage();
            currentFragment.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.fragment, currentFragment).commit();
            fragmentType = "short";
        } else if (id == R.id.nav_longTerm) {
            fragmentManager.beginTransaction().hide(supportMapFragment).commit();
            displayDate.setVisibility(View.GONE);
            displayOverlay.setVisibility(View.GONE);
            displayMarkers.setVisibility(View.GONE);
            Bundle bundle = new Bundle();
            bundle.putString("overlay",overlay);
            bundle.putString("ratingOverlay",ratingOverlay);
            bundle.putString("tab",tab);
            currentFragment = new LongTermPage();
            currentFragment.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.fragment, currentFragment).commit();
            fragmentType = "long";
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.clear();

        if (!overlay.equals("noOverlay")) {

            TileProvider tileProvider = new UrlTileProvider(256, 256) {
                @Override
                public URL getTileUrl(int x, int y, int zoom) {

                    //Define the URL pattern for the tile images
                    String s = String.format("http://map1.vis.earthdata.nasa.gov/wmts-webmerc/" + overlay +
                                    "/default/" + sysDate + "/GoogleMapsCompatible_Level6/%d/%d/%d.png",
                            zoom, y, x);

                    if (!checkTileExists(x, y, zoom)) {
                        return null;
                    }

                    try {
                        return new URL(s);
                    } catch (MalformedURLException e) {
                        throw new AssertionError(e);
                    }

                }


                //Check that the tile server supports the requested x, y and zoom.
                //Complete this stub according to the tile range you support.
                //If you support a limited range of tiles at different zoom levels, then you
                //need to define the supported x, y range at each zoom level.

                private boolean checkTileExists(int x, int y, int zoom) {
                    int minZoom = 1;
                    int maxZoom = 6;

                    if ((zoom < minZoom || zoom > maxZoom)) {
                        return false;
                    }

                    return true;
                }
            };

            TileOverlay tileOverlay = googleMap.addTileOverlay(new TileOverlayOptions()
                    .tileProvider(tileProvider));
        }

        if (!ratingOverlay.equals("noMarkers")) {
            TileProvider tileProvider2 = new UrlTileProvider(256, 256) {
                @Override
                public URL getTileUrl(int x, int y, int zoom) {

                    //Define the URL pattern for the tile images
                    String s = String.format("http://tiles.aqicn.org/tiles/" + ratingOverlay + "/%d/%d/%d.png?token=23fb64767d406291fdb67108a2a91050dab80db5",
                            zoom, x, y);

                    if (!checkTileExists(x, y, zoom)) {
                        return null;
                    }

                    try {
                        return new URL(s);
                    } catch (MalformedURLException e) {
                        throw new AssertionError(e);
                    }
                }


                //Check that the tile server supports the requested x, y and zoom.
                //Complete this stub according to the tile range you support.
                //If you support a limited range of tiles at different zoom levels, then you
                //need to define the supported x, y range at each zoom level.

                private boolean checkTileExists(int x, int y, int zoom) {
                    int minZoom = 1;
                    int maxZoom = 6;

                    if ((zoom < minZoom || zoom > maxZoom)) {
                        return false;
                    }

                    return true;
                }
            };

            TileOverlay tileOverlay2 = googleMap.addTileOverlay(new TileOverlayOptions()
                    .tileProvider(tileProvider2));
        }

    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    public static class DatePickerFragment extends DialogFragment implements OnDateSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }


       @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
           calendar.set(Calendar.YEAR,year);
           calendar.set(Calendar.MONTH,monthOfYear);
           calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
           userDate = userFormat.format(calendar.getTime());
           sysDate = sysFormat.format(calendar.getTime());
           TextView displayDate = (TextView)getActivity().findViewById(R.id.displayDate);
           RelativeLayout root = (RelativeLayout)getActivity().findViewById(R.id.mainLayout);
           displayDate.setText(userDate);
           ((MainActivity)getActivity()).reloadMap();
           Snackbar.make(root, R.string.markersDateNote, Snackbar.LENGTH_SHORT).show();
        }

    }

}


