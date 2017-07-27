package victorylink.com.flickerapp.Controllers;

import android.content.Context;

import java.util.ArrayList;

import victorylink.com.flickerapp.Models.DownloadModel;
import victorylink.com.flickerapp.Other.Interfaces.IGallaryView;

/**
 * Created by MrHacker on 7/26/2017.
 */

public class DownloadController {

    DownloadModel model;
    Context context;
    IGallaryView view ;

    public void setView(IGallaryView view) {
        this.view = view;
    }

    public DownloadController(Context newContext) {
        context = newContext;
        model = new DownloadModel(newContext);
    }


    public void DownloadPngImage(String url, String imageId) {
        model.downloadImageFromURL(url, imageId);
    }

    public void showImagesFromSD() {
        ArrayList<String> paths = model.readImagesFromSD("FlickerImages");
        view.updateUI(paths);
    }
}
