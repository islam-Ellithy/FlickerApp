package victorylink.com.flickerapp.Controllers;

import android.app.Activity;
import android.content.Intent;

import com.facebook.login.LoginManager;

import victorylink.com.flickerapp.R;
import victorylink.com.flickerapp.Views.Activity.LoginActivity;
import victorylink.com.flickerapp.Views.Fragment.DownloadedPhotosFragment;
import victorylink.com.flickerapp.Views.Fragment.FavoritePhotosFragment;
import victorylink.com.flickerapp.Views.Fragment.MainFragment;
import victorylink.com.flickerapp.Views.Fragment.ProfileFragment;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by MrHacker on 7/26/2017.
 */

public class MainActivityController {

    Activity activity;

    public MainActivityController(Activity newActivity)
    {
        activity = newActivity ;
    }


    public Class getFragmentClassName(int id) {
        // Handle navigation view item clicks here.


        Class fragmentClass = null;

        if (id == R.id.nav_home) {
            fragmentClass = MainFragment.class;
        } else if (id == R.id.nav_like) {
            fragmentClass = FavoritePhotosFragment.class ;
        } else if (id == R.id.nav_download) {
            fragmentClass = DownloadedPhotosFragment.class;
        } else if (id == R.id.nav_profile) {
            fragmentClass = ProfileFragment.class ;
        } else if (id == R.id.nav_logout) {
            LoginManager.getInstance().logOut();
            activity.startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            activity.finish();
        }

        return fragmentClass;
    }

}
