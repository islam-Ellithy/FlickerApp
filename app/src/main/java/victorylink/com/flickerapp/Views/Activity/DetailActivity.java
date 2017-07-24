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

public class DetailActivity extends AppCompatActivity implements IView {

    private RecyclerView recyclerView;
    private PhotoAdapter mAdapter;
    private Result responseObject;
    private String userId;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("userId", userId);
        outState.putParcelable("result", responseObject);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);


        responseObject = savedInstanceState.getParcelable("result");

        if (responseObject == null)
            request(userId);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        userId = getIntent().getStringExtra("userId");


        toolbar.setTitle("Photo List");

        setSupportActionBar(toolbar);

        initView();

        if (responseObject == null)
            request(userId);
    }

    public void request(String userId) {
        HttpController resultController = new HttpController(this);
        resultController.doHttpRequestUserPhotos(userId);
    }

    public void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void updateUI(Result result) {
        this.assignResultToUI(result);
    }

    public void assignResultToUI(Result result) {
        //Toast.makeText(MainActivity.this,"Hello",Toast.LENGTH_SHORT).show();

        responseObject = result ;
        Log.v("TAG", result.getPhotos().getPhotoList().size() + "");
        ArrayList<Photo> photoArrayList = responseObject.getPhotos().getPhotoList();

        if (mAdapter == null) {
            mAdapter = new PhotoAdapter(photoArrayList, this, false);
            recyclerView.setAdapter(mAdapter);
        }

        mAdapter.swapArray(photoArrayList);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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


}
