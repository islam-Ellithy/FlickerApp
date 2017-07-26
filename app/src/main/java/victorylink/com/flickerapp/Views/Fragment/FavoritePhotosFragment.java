package victorylink.com.flickerapp.Views.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import victorylink.com.flickerapp.Interfaces.CommonFragmentInterface;
import victorylink.com.flickerapp.Interfaces.DbView;
import victorylink.com.flickerapp.Parsers.Result;
import victorylink.com.flickerapp.R;
import victorylink.com.flickerapp.Views.Adapter.PhotoAdapter;

public class FavoritePhotosFragment extends Fragment implements CommonFragmentInterface , DbView {

    private RecyclerView recyclerView;
    private PhotoAdapter mAdapter;
    private Result responseObject;
    private OnFragmentInteractionListener mListener;

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
        return inflater.inflate(R.layout.fragment_favorite_photos, container, false);
    }

    public void request()
    {
//        HttpController resultController = new HttpController(this);
//        resultController.doHttpRequestUserPhotos(userId);

    }
    @Override
    public void initView(View view) {

    }

    @Override
    public void assignResultToUI(Result result) {

    }

    @Override
    public void search(SearchView searchView) {

    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
