package victorylink.com.flickerapp.Models;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by MrHacker on 7/26/2017.
 */

public class DownloadModel {

    DownloadFile downloadFile;
    Context context;

    public DownloadModel(Context newContext) {
        context = newContext;
        downloadFile = new DownloadFile(context);
    }

    public ArrayList<String> readImagesFromSD(String foldername) {
        File dir = new File(Environment.getExternalStorageDirectory().getPath() + foldername);
        File[] images = dir.listFiles();

        ArrayList<String> paths = new ArrayList<>();

        for (File img : images) {
            paths.add(img.getAbsolutePath());
        }

        return paths;
    }


    //save image
    public void downloadImageFromURL(String url, String photoId) {
        new DownloadFile(context).execute(url, photoId);
    }

}
