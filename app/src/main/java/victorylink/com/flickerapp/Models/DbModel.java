package victorylink.com.flickerapp.Models;

import android.content.Context;

import java.util.Map;

import victorylink.com.flickerapp.Controllers.DbController;
import victorylink.com.flickerapp.Models.data.FlickerDbHelper;
import victorylink.com.flickerapp.Models.data.PhotoRecord;

/**
 * Created by MrHacker on 7/26/2017.
 */

public class DbModel {

    FlickerDbHelper db ;
    DbController controller ;

    public DbModel(DbController newController, Context context)
    {
        db = new FlickerDbHelper(context);
        controller = newController ;
    }
    public Map  getAllDownloadedPhotosFromDb(String userId)
    {
        return db.getAllDownloadedPhotos(userId);
    }

    public Map getAllFavoritePhotosFromDb(String userId)
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
