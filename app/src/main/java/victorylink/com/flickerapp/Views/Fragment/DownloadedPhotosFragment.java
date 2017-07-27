package victorylink.com.flickerapp.Views.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import victorylink.com.flickerapp.Controllers.DownloadController;
import victorylink.com.flickerapp.Other.Interfaces.IGallaryView;
import victorylink.com.flickerapp.R;
import victorylink.com.flickerapp.Views.Adapter.GridViewAdapter;

public class DownloadedPhotosFragment extends Fragment implements IGallaryView {

    private OnFragmentInteractionListener mListener;
    private String[] filePathStrings;
    GridView grid;
    GridViewAdapter adapter;
    public static Bitmap bmp = null;
    ImageView imageview;
    DownloadController controller;

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
        View view = inflater.inflate(R.layout.fragment_downloaded_photos, container, false);

        downloadedGalary(view);

        return view;
    }

    void downloadedGalary(View view) {
        controller = new DownloadController(getContext());
        controller.setView(this);

        filePathStrings = controller.getImagesFromSD();
        initView(view);

        adapter.notifyDataSetChanged();
    }


    public void initView(View view) {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Toast.makeText(getContext(), "Error! No SDCARD Found!",
                    Toast.LENGTH_LONG).show();
        }

        grid = (GridView) view.findViewById(R.id.gridview);
        adapter = new GridViewAdapter(getActivity(), filePathStrings);
        grid.setAdapter(adapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                imageview = (ImageView) view.findViewById(R.id.image);
                int targetWidth = 700;
                int targetHeight = 500;
                BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
                bmpOptions.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(filePathStrings[position],
                        bmpOptions);
                int currHeight = bmpOptions.outHeight;
                int currWidth = bmpOptions.outWidth;
                int sampleSize = 1;
                if (currHeight > targetHeight || currWidth > targetWidth) {
                    if (currWidth > currHeight)
                        sampleSize = Math.round((float) currHeight
                                / (float) targetHeight);
                    else
                        sampleSize = Math.round((float) currWidth
                                / (float) targetWidth);
                }
                bmpOptions.inSampleSize = sampleSize;
                bmpOptions.inJustDecodeBounds = false;
                bmp = BitmapFactory.decodeFile(filePathStrings[position],
                        bmpOptions);
                imageview.setImageBitmap(bmp);
                imageview.setScaleType(ImageView.ScaleType.FIT_XY);
                bmp = null;
            }
        });

    }

    @Override
    public void updateUI(String[] images) {

        filePathStrings = new String[images.length];
        filePathStrings = images;

        Log.v("Files", "sz = " + filePathStrings.length);

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
