package victorylink.com.flickerapp.Views.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import victorylink.com.flickerapp.R;
import victorylink.com.flickerapp.Views.Fragment.DetailFragment;
import victorylink.com.flickerapp.Views.Fragment.DownloadedPhotosFragment;
import victorylink.com.flickerapp.Views.Fragment.FavoritePhotosFragment;
import victorylink.com.flickerapp.Views.Fragment.MainFragment;

public class MainActivity extends AppCompatActivity
        implements MainFragment.OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener {

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


        //ProfilePictureView pictureView = (ProfilePictureView)findViewById(R.id.profile);

        Profile profile = com.facebook.Profile.getCurrentProfile();
        if (profile != null) {
            Toast.makeText(this, profile.getCurrentProfile().getId().toString(), Toast.LENGTH_LONG).show();
            Log.v("UserID", Profile.getCurrentProfile().getId());


            //pictureView.setProfileId("469210766772872");

//            getFacebookProfilePicture("469210766772872");
        }


        ///////////////////////////////////////////////
/*
  */
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

    public void getFacebookProfilePicture(String userId) {

        ImageView profile = (ImageView) findViewById(R.id.pr);

        String url = "https://graph.facebook.com/" + userId + "/picture?type=large";

        Picasso.with(this).load(url).into(profile);

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
