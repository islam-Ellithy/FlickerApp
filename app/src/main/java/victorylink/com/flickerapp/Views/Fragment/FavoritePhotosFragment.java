package victorylink.com.flickerapp.Views.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import victorylink.com.flickerapp.Controllers.DbController;
import victorylink.com.flickerapp.Other.Interfaces.CommonFragmentInterface;
import victorylink.com.flickerapp.Other.Interfaces.DbView;
import victorylink.com.flickerapp.Other.Parsers.Result;
import victorylink.com.flickerapp.Other.database.PhotoRecord;
import victorylink.com.flickerapp.R;
import victorylink.com.flickerapp.Views.Adapter.FavoritePhotosAdapter;

public class FavoritePhotosFragment extends Fragment implements CommonFragmentInterface, DbView {

    private RecyclerView recyclerView;
    private FavoritePhotosAdapter mAdapter;
    private OnFragmentInteractionListener mListener;
    DbController dbController;

    public FavoritePhotosFragment() {
        // Required empty public constructor
    }

    public static FavoritePhotosFragment newInstance() {
        FavoritePhotosFragment fragment = new FavoritePhotosFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void setmListener(OnFragmentInteractionListener mListener) {
        this.mListener = mListener;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_photos, container, false);

        dbController = new DbController(getContext());

        initView(view);

        request();

        return view;
    }

    public void request() {

        HashMap<String, PhotoRecord> map = dbController.getAllFavoritePhotosFromDb("islam");

        ArrayList<PhotoRecord> arrayList = dbController.getArrayListFromMap(map);


        if (mAdapter == null) {
            mAdapter = new FavoritePhotosAdapter(arrayList, getContext());
            recyclerView.setAdapter(mAdapter);
        }
        mAdapter.swapArray(arrayList);
    }

    @Override
    public void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) view.findViewById(R.id.searchView);
        search(searchView);
    }

    public void assignResultToUI(Result result) {

    }

    @Override
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
    public void updateUI(HashMap<String, PhotoRecord> photoRecordHashMap) {

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
