package victorylink.com.flickerapp.Views;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import victorylink.com.flickerapp.Controllers.HttpController;
import victorylink.com.flickerapp.Interfaces.IView;
import victorylink.com.flickerapp.Parsers.Photo;
import victorylink.com.flickerapp.Parsers.Result;
import victorylink.com.flickerapp.R;

public class MainActivity extends AppCompatActivity implements IView {

    private RecyclerView recyclerView;
    private PhotoAdapter mAdapter;
/*
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();

        request();

    }


    public void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public void request() {
        HttpController resultController = new HttpController(this);

        resultController.doHttpRequest();
    }

    public void assignResultToUI(Result result) {

        Log.v("TAG", result.getPhotos().getPhotoList().size() + "");
        ArrayList<Photo> photoArrayList = result.getPhotos().getPhotoList();

        if (mAdapter == null) {
            mAdapter = new PhotoAdapter(photoArrayList, this, true);
            recyclerView.setAdapter(mAdapter);
        }

        mAdapter.swapArray(photoArrayList);

    }


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

                mAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateUI(Result Result) {
        assignResultToUI(Result);
    }
}
