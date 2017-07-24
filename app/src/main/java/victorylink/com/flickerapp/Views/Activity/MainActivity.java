package victorylink.com.flickerapp.Views.Activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import victorylink.com.flickerapp.R;
import victorylink.com.flickerapp.Views.Fragment.DetailFragment;
import victorylink.com.flickerapp.Views.Fragment.MainFragment;

public class MainActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener {
    private DrawerLayout drawerLayout;
    private ListView drawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);


        MainFragment fragment = MainFragment.newInstance();

        fragment.setListener(this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame,fragment)
                //.addToBackStack()
                .commit();

    }

    @Override
    public void onFragmentInteraction(String userId) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, DetailFragment.newInstance(userId))
                //.addToBackStack()
                .commit();
    }


/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

       //         mAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }*/

/*
    @Override
    public void updateUI(Result Result) {
        assignResultToUI(Result);
    }
*/
}
