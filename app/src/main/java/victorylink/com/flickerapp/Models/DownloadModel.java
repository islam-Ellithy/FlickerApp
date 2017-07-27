package victorylink.com.flickerapp.Models;

import android.content.Context;
import android.os.Environment;

import java.io.File;

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

    public String[] readImagesFromSD(String foldername) {

        File dir = new File(Environment.getExternalStorageDirectory().getPath() + "/" + foldername);
        String[] paths = null;

        if (dir.isDirectory()) {
            File[] images = dir.listFiles();

            paths = new String[images.length];

            for (int i = 0; i < paths.length; i++) {
                paths[i] = images[i].getAbsolutePath();
            }
        }

        return paths;
    }


    //save image
    public void downloadImageFromURL(String url, String photoId) {
        new DownloadFile(context).execute(url, photoId);
    }

}
