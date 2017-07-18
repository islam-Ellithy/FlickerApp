package victorylink.com.flickerapp.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;

import victorylink.com.flickerapp.Controller.HttpController;
import victorylink.com.flickerapp.Model.IView;
import victorylink.com.flickerapp.Model.Photo;
import victorylink.com.flickerapp.Model.Result;
import victorylink.com.flickerapp.R;

public class DetailActivity extends AppCompatActivity implements IView {

    private RecyclerView recyclerView;
    private ResultAdapter mAdapter;
    private Result result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        result = new Result();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        ArrayList<Photo> photoArrayList = result.getPhotos().getPhotoList();
        if (photoArrayList == null) {
            photoArrayList = new ArrayList<>();
        }


        mAdapter = new ResultAdapter(photoArrayList, this, false);
        recyclerView.setAdapter(mAdapter);

        HttpController resultController = new HttpController(this);
        String userId = getIntent().getStringExtra("userId");

        resultController.doHttpRequestUserPhotos(userId);
    }

    @Override
    public void updateUI(Result result) {
        this.assignResultToUI(result);
    }

    public void assignResultToUI(Result result) {
        // specify an adapter
        //Toast.makeText(MainActivity.this,"Hello",Toast.LENGTH_SHORT).show();
        Log.v("TAG", result.getPhotos().getPhotoList().size() + "");
        ArrayList<Photo> photoArrayList = result.getPhotos().getPhotoList();

        if (mAdapter == null) {
            mAdapter = new ResultAdapter(photoArrayList, this, false);
            recyclerView.setAdapter(mAdapter);
        }

        mAdapter.setDataset(photoArrayList);

    }

}
