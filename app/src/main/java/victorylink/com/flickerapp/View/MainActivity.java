package victorylink.com.flickerapp.View;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import victorylink.com.flickerapp.Controller.HttpController;
import victorylink.com.flickerapp.Model.IView;
import victorylink.com.flickerapp.Model.Photo;
import victorylink.com.flickerapp.Model.Result;
import victorylink.com.flickerapp.R;

public class MainActivity extends AppCompatActivity implements IView{

    private  RecyclerView recyclerView;
    private  ResultAdapter mAdapter;
    private Result result ;
    private static RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        result = new Result();
        recyclerView = (RecyclerView) findViewById(R.id.image_items);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        ArrayList<Photo> photoArrayList = result.getPhotos().getPhotoList();
        if(photoArrayList==null)
        {
            photoArrayList = new ArrayList<>();
        }

        mAdapter = new ResultAdapter(photoArrayList,getApplicationContext(),true);
        recyclerView.setAdapter(mAdapter);

        request();

    }


    public void request() {
        HttpController resultController = new HttpController(this);

        resultController.doHttpRequest();
    }

    public void assignResultToUI(Result result) {

        Log.v("TAG",result.getPhotos().getPhotoList().size()+"");
        ArrayList<Photo> photoArrayList = result.getPhotos().getPhotoList();

        if(mAdapter==null)
        {
           mAdapter = new ResultAdapter(photoArrayList,this,true);
           recyclerView.setAdapter(mAdapter);
        }

        mAdapter.setDataset(photoArrayList);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateUI(Result Result) {
        assignResultToUI(Result);
    }
}
