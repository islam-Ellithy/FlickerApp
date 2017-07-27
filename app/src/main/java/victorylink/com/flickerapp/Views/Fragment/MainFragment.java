package victorylink.com.flickerapp.Views.Fragment;

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
import victorylink.com.flickerapp.Other.Interfaces.CommonFragmentInterface;
import victorylink.com.flickerapp.Other.Interfaces.IView;
import victorylink.com.flickerapp.Other.Parsers.Photo;
import victorylink.com.flickerapp.Other.Parsers.Result;
import victorylink.com.flickerapp.R;
import victorylink.com.flickerapp.Views.Adapter.PhotoAdapter;

public class MainFragment extends Fragment implements IView, PhotoAdapter.OnAdapterInteractionListener , CommonFragmentInterface{

    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private PhotoAdapter mAdapter;
    private Result resultJson;
    private ArrayList<Photo> photoArrayList;

    public void setListener(OnFragmentInteractionListener mListener) {
        this.mListener = mListener;
    }

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {

        MainFragment fragment = new MainFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        android.support.v7.widget.SearchView searchView =
                (android.support.v7.widget.SearchView) view.findViewById(R.id.searchView);

        search(searchView);

        initView(view);

        if (resultJson == null) {
            request();
        }

        return view;
    }


    public void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new PhotoAdapter(photoArrayList, getContext());
        mAdapter.setAdapterInteractionListener(this);
        recyclerView.setAdapter(mAdapter);
    }

    public void request() {
        HttpController resultController = new HttpController(this);
        resultController.doHttpRequest();
    }


    public void assignResultToUI(Result result) {
        Log.v("TAG", result.getPhotos().getPhotoList().size() + "");
        resultJson = result;
        photoArrayList = result.getPhotos().getPhotoList();

        if (mAdapter == null) {
            mAdapter = new PhotoAdapter(photoArrayList, getContext());
            mAdapter.setAdapterInteractionListener(this);
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

    @Override
    public void updateUI(Result Result) {
        assignResultToUI(Result);
    }


    @Override
    public void onAdapterInteraction(String userId) {
        mListener.onFragmentInteraction(userId);
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String userId);
    }
}
