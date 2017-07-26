package victorylink.com.flickerapp.Views.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import victorylink.com.flickerapp.Controllers.HttpController;
import victorylink.com.flickerapp.Interfaces.CommonFragmentInterface;
import victorylink.com.flickerapp.Interfaces.IView;
import victorylink.com.flickerapp.Parsers.Photo;
import victorylink.com.flickerapp.Parsers.Result;
import victorylink.com.flickerapp.R;
import victorylink.com.flickerapp.Views.Adapter.PhotoDetailAdapter;

public class DetailFragment extends Fragment implements IView, CommonFragmentInterface {

    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private PhotoDetailAdapter mAdapter;
    private Result responseObject;
    private String userId;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(String userId) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString("userId", userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            Bundle args = getArguments();
            userId = args.getString("userId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        initView(view);

        if (responseObject == null)
            request(userId);

        return view ;
    }

    public void request(String userId) {
        HttpController resultController = new HttpController(this);
        resultController.doHttpRequestUserPhotos(userId);
    }

    public void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) view.findViewById(R.id.searchView);
        search(searchView);

    }


    @Override
    public void updateUI(Result result) {
        this.assignResultToUI(result);
    }

    public void assignResultToUI(Result result) {
        //Toast.makeText(MainActivity.this,"Hello",Toast.LENGTH_SHORT).show();

        responseObject = result;
        Log.v("TAG", result.getPhotos().getPhotoList().size() + "");
        ArrayList<Photo> photoArrayList = responseObject.getPhotos().getPhotoList();

        if (mAdapter == null) {
            mAdapter = new PhotoDetailAdapter(photoArrayList , getContext());
            recyclerView.setAdapter(mAdapter);
        }

        mAdapter.swapArray(photoArrayList);

    }


    public void search(android.support.v7.widget.SearchView searchView) {

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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
