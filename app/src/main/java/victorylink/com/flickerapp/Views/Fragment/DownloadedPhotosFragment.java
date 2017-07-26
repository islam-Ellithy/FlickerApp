package victorylink.com.flickerapp.Views.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import victorylink.com.flickerapp.Parsers.Result;
import victorylink.com.flickerapp.R;
import victorylink.com.flickerapp.Views.Adapter.PhotoAdapter;

public class DownloadedPhotosFragment extends Fragment {

    private RecyclerView recyclerView;
    private PhotoAdapter mAdapter;
    private Result responseObject;
    private OnFragmentInteractionListener mListener;

    public DownloadedPhotosFragment() {
        // Required empty public constructor
    }

    public static DownloadedPhotosFragment newInstance() {
        DownloadedPhotosFragment fragment = new DownloadedPhotosFragment();
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
        return inflater.inflate(R.layout.fragment_downloaded_photos, container, false);
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
