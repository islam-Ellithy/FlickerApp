package victorylink.com.flickerapp.Models;

import android.content.Context;

import java.util.HashMap;

import victorylink.com.flickerapp.Other.database.FlickerDbHelper;
import victorylink.com.flickerapp.Other.database.PhotoRecord;

/**
 * Created by MrHacker on 7/26/2017.
 */

public class DbModel {

    FlickerDbHelper db ;

    public DbModel(Context context)
    {
        db = new FlickerDbHelper(context);
    }
    public HashMap<String, PhotoRecord> getAllDownloadedPhotosFromDb(String userId)
    {
        return db.getAllDownloadedPhotos(userId);
    }

    public HashMap<String, PhotoRecord> getAllFavoritePhotosFromDb(String userId)
    {
        return db.getAllFavoritePhotos(userId);
    }

    public void insertPhotoToDb(PhotoRecord photoRecord)
    {
        db.addPhotoRecordToDB(photoRecord);
    }

    public void deletePhotoFromDb(String photoId)
    {
        db.deleteFavoritePhoto(photoId);
    }

    public void updatePhotoFromDb(String photoId,PhotoRecord record)
    {
        db.updateDownloadedPhoto(photoId,record);
    }
}
