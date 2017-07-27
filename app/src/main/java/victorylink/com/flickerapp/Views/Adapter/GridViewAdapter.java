package victorylink.com.flickerapp.Views.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import victorylink.com.flickerapp.R;

public class GridViewAdapter extends BaseAdapter {
    private Activity activity;
    private String[] paths;
    private static LayoutInflater inflater = null;
    Bitmap bmp = null;

    public GridViewAdapter(Activity  activity, String[] fpath) {
        this.activity = activity;
        paths = fpath;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        if(paths!=null)
            return paths.length;
        else
            return 0 ;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.gridview_item, null);
        ImageView image = (ImageView) vi.findViewById(R.id.image);
        int targetWidth = 100;
        int targetHeight = 100;
        BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
        bmpOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(paths[position], bmpOptions);
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
        bmp = BitmapFactory.decodeFile(paths[position], bmpOptions);
        image.setImageBitmap(bmp);
        image.setScaleType(ImageView.ScaleType.FIT_XY);
        bmp = null;
        return vi;
    }
}