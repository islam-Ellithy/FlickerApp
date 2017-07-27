package victorylink.com.flickerapp.Views.Activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;

import victorylink.com.flickerapp.R;
import victorylink.com.flickerapp.Views.Fragment.DetailFragment;
import victorylink.com.flickerapp.Views.Fragment.DownloadedPhotosFragment;
import victorylink.com.flickerapp.Views.Fragment.FavoritePhotosFragment;
import victorylink.com.flickerapp.Views.Fragment.MainFragment;
import victorylink.com.flickerapp.Views.Fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity
        implements MainFragment.OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener {

    private static String TAG = "PermissionDemo";
    private static final int REQUEST_WRITE_STORAGE = 112;
    ProfilePictureView profilePictureView;
    TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);

        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);


        username = (TextView) findViewById(R.id.username_nav);
        profilePictureView = (ProfilePictureView) findViewById(R.id.profile_side_menu);

        askForPermission();

        //username.setText(Profile.getCurrentProfile().getName());
//        profilePictureView.setProfileId(Profile.getCurrentProfile().getId());

        ///////////////////////////////////////////////
/*
  */


    }


    private void askForPermission() {
        //ask for the permission in android M
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission to record denied");

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Permission to access the SD-CARD is required for this app to Download PDF.")
                        .setTitle("Permission required");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        Log.i(TAG, "Clicked");
                        makeRequest();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            } else {
                makeRequest();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_WRITE_STORAGE: {

                if (grantResults.length == 0
                        || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED) {

                    Log.i(TAG, "Permission has been denied by user");

                } else {

                    Log.i(TAG, "Permission has been granted by user");

                }
                return ;
            }
        }
    }

/*
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            showAlert();
        }
    }
*/

    protected void makeRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                REQUEST_WRITE_STORAGE);
    }

    public void getFacebookProfilePicture(String userId) {

        //  ImageView profile = (ImageView) findViewById(R.id.pr);

        String url = "https://graph.facebook.com/469210766772872/picture?type=large";

        //Picasso.with(this).load(url).into(profile);

    }


    public void goToMainFragment() {
        MainFragment fragment = MainFragment.newInstance();

        fragment.setListener(this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment)
                .addToBackStack(MainFragment.class.getSimpleName())
                .commit();
    }

    public void goToFavoritePhotosFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, FavoritePhotosFragment.newInstance())
                .addToBackStack(MainFragment.class.getSimpleName())
                .commit();
    }

    public void goToDownloadedPhotosFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, DownloadedPhotosFragment.newInstance())
                .addToBackStack(MainFragment.class.getSimpleName())
                .commit();
    }

    public void goToProfileFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, ProfileFragment.newInstance())
                .addToBackStack(ProfileFragment.class.getSimpleName())
                .commit();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
//            showAlert();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            goToMainFragment();
        } else if (id == R.id.nav_like) {
            goToFavoritePhotosFragment();
        } else if (id == R.id.nav_download) {
            goToDownloadedPhotosFragment();
        } else if (id == R.id.nav_profile) {
            goToProfileFragment();
        } else if (id == R.id.nav_logout) {
            LoginManager.getInstance().logOut();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showAlert() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Exit Application")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        dialog.cancel();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void onFragmentInteraction(String userId) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, DetailFragment.newInstance(userId))
                .addToBackStack(DetailFragment.class.getSimpleName())
                .commit();

    }
}
