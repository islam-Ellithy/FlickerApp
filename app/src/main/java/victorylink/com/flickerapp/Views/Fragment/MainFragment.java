package victorylink.com.flickerapp.Views.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import victorylink.com.flickerapp.Controllers.HttpController;
import victorylink.com.flickerapp.Interfaces.IView;
import victorylink.com.flickerapp.Parsers.Photo;
import victorylink.com.flickerapp.Parsers.Result;
import victorylink.com.flickerapp.R;
import victorylink.com.flickerapp.Views.PhotoAdapter;

public class MainFragment extends Fragment implements IView {

    private OnFragmentInteractionListener mListener;
    private Context context;
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
        //      Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        //    fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

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
        mAdapter = new PhotoAdapter(photoArrayList, getContext(), true);
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
            mAdapter = new PhotoAdapter(photoArrayList, getContext(), true);
            recyclerView.setAdapter(mAdapter);
        }

        mAdapter.swapArray(photoArrayList);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        super.onCreateOptionsMenu(menu, inflater);

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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String userId);
    }
}
