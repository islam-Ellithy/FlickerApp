package victorylink.com.flickerapp.Views.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;

import victorylink.com.flickerapp.Other.Interfaces.CommonFragmentInterface;
import victorylink.com.flickerapp.Other.Parsers.Result;
import victorylink.com.flickerapp.R;
import victorylink.com.flickerapp.Views.Adapter.PhotoAdapter;

public class ProfileFragment extends Fragment implements CommonFragmentInterface {

    private RecyclerView recyclerView;
    private PhotoAdapter mAdapter;
    private Result responseObject;
    private OnFragmentInteractionListener mListener;
    private TextView username;
    private ProfilePictureView profilePictureView;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        initView(view);

        profilePictureView.setProfileId(Profile.getCurrentProfile().getId());
        username.setText(Profile.getCurrentProfile().getName());

        return view;
    }

    public void request() {
//        HttpController resultController = new HttpController(this);
//        resultController.doHttpRequestUserPhotos(userId);

    }

    @Override
    public void initView(View view) {
        username = (TextView) view.findViewById(R.id.username);
        profilePictureView = (ProfilePictureView) view.findViewById(R.id.profile_photo);
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
