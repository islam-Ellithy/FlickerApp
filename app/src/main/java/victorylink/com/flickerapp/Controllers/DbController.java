package victorylink.com.flickerapp.Controllers;

import android.content.Context;

import java.util.HashMap;

import victorylink.com.flickerapp.Other.Interfaces.IView;
import victorylink.com.flickerapp.Models.DbModel;
import victorylink.com.flickerapp.Other.database.PhotoRecord;

/**
 * Created by MrHacker on 7/26/2017.
 */

public class DbController {

    IView view;
    DbModel model;

    public DbController(Context context) {
        this.model = new DbModel(context);
    }

    public void setView(IView view) {
        this.view = view;
    }

    public HashMap<String, PhotoRecord> getAllDownloadedPhotosFromDb(String userId) {
        return model.getAllDownloadedPhotosFromDb(userId);
    }

    public HashMap<String, PhotoRecord> getAllFavoritePhotosFromDb(String userId) {
        return model.getAllFavoritePhotosFromDb(userId);
    }

    public void insertPhotoToDb(PhotoRecord photoRecord) {
        model.insertPhotoToDb(photoRecord);
    }

    public void deletePhotoFromDb(String photoId) {
        model.deletePhotoFromDb(photoId);
    }

    public void updatePhotoFromDb(String photoId, PhotoRecord record) {
        model.updatePhotoFromDb(photoId, record);
    }

}
