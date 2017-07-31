package victorylink.com.flickerapp.Views.Fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;

import java.io.File;

import victorylink.com.flickerapp.Other.Interfaces.CommonFragmentInterface;
import victorylink.com.flickerapp.Other.Parsers.Result;
import victorylink.com.flickerapp.R;

public class ProfileFragment extends Fragment implements CommonFragmentInterface {

    private OnFragmentInteractionListener mListener;
    private TextView username;
    private ProfilePictureView profilePictureView;
    private Uri imageUri;
    private ImageView imageView;
    private Button changeCamera;

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

        changeCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });

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
        imageView = (ImageView) view.findViewById(R.id.cameraView);
        changeCamera = (Button) view.findViewById(R.id.camera_button);
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


    public void takePhoto() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File photo = new File(Environment.getExternalStorageDirectory(), "Pic.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);
        getActivity().startActivityForResult(intent, 100);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = imageUri;
                    getActivity().getContentResolver().notifyChange(selectedImage, null);
                    ContentResolver cr = getActivity().getContentResolver();
                    Bitmap bitmap;
                    try {
                        bitmap = android.provider.MediaStore.Images.Media
                                .getBitmap(cr, selectedImage);

                        imageView.setImageBitmap(bitmap);
                        Toast.makeText(getContext(), selectedImage.toString(),
                                Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Failed to load", Toast.LENGTH_SHORT)
                                .show();
                        Log.e("Camera", e.toString());
                    }
                }
        }
    }

}
