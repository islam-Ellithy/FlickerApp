package victorylink.com.flickerapp.Other.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

import static victorylink.com.flickerapp.Other.data.FlickerContract.PhotoEntry.TABLE_NAME;

/**
 * Created by MrHacker on 7/20/2017.
 */

public class FlickerDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "flickerDb.db";
    private static final int DATABASE_VERSION = 2;

    public FlickerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_PHOTO_ENTRY_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                FlickerContract.PhotoEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , " +
                FlickerContract.PhotoEntry.COLUMN_PHOTO_ID + " TEXT NOT NULL , " +
                FlickerContract.PhotoEntry.COLUMN_IS_DOWNLOADED + " INTEGER NOT NULL , " +
                FlickerContract.PhotoEntry.COLUMN_USER_ID + " TEXT NOT NULL , " +
                FlickerContract.PhotoEntry.COLUMN_IS_LIKED + " INTEGER NOT NULL , " +
                FlickerContract.PhotoEntry.COLUMN_PHOTO_TITLE + " TEXT NOT NULL ); ";

        db.execSQL(SQL_CREATE_PHOTO_ENTRY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }



    public boolean addPhotoRecordToDB(PhotoRecord newRecord) {
        ContentValues values = new ContentValues();

        values.put(FlickerContract.PhotoEntry.COLUMN_PHOTO_ID, newRecord.getPhotoId());
        values.put(FlickerContract.PhotoEntry.COLUMN_USER_ID, newRecord.getUserId());
        values.put(FlickerContract.PhotoEntry.COLUMN_IS_DOWNLOADED, FlickerContract.booleanInInteger(newRecord.isDownloaded()));
        values.put(FlickerContract.PhotoEntry.COLUMN_IS_LIKED, FlickerContract.booleanInInteger(newRecord.isLiked()));
        values.put(FlickerContract.PhotoEntry.COLUMN_PHOTO_TITLE, newRecord.getPhotoTitle());

        SQLiteDatabase db = this.getWritableDatabase();
        long rowId = 0;

        try {

            if (!checkIsDataAlreadyInDBorNot(newRecord.getPhotoId())) {
                rowId = db.insert(TABLE_NAME, null, values);
                Log.v("DB", "Insert done");
            }

        } catch (SQLException e) {
            //Toast.makeText(context,"due to add : "+e,Toast.LENGTH_LONG).show();
        } finally {
            db.close();
        }

        return rowId > 0;
    }


    public boolean updatePhotoRecord(String photoId, PhotoRecord newRecord) {
        ContentValues values = new ContentValues();
        values.put(FlickerContract.PhotoEntry.COLUMN_PHOTO_ID, newRecord.getPhotoId());
        values.put(FlickerContract.PhotoEntry.COLUMN_USER_ID, newRecord.getUserId());
        values.put(FlickerContract.PhotoEntry.COLUMN_IS_DOWNLOADED, FlickerContract.booleanInInteger(newRecord.isDownloaded()));
        values.put(FlickerContract.PhotoEntry.COLUMN_IS_LIKED, FlickerContract.booleanInInteger(newRecord.isLiked()));
        values.put(FlickerContract.PhotoEntry.COLUMN_PHOTO_TITLE, newRecord.getPhotoTitle());

        SQLiteDatabase db = getWritableDatabase();

        long rowId = 0;
        try {
            rowId = db.update(TABLE_NAME, values, FlickerContract.PhotoEntry.COLUMN_PHOTO_ID + " = " + photoId, null);

        } catch (SQLException e) {
            //Toast.makeText(context,"due to add : "+e,Toast.LENGTH_LONG).show();
        } finally {
            db.close();
        }
        return rowId > 0;
    }


    public boolean updateDownloadedPhoto(String photoId, PhotoRecord newRecord) {
        ContentValues values = new ContentValues();
        values.put(FlickerContract.PhotoEntry.COLUMN_PHOTO_ID, newRecord.getPhotoId());
        values.put(FlickerContract.PhotoEntry.COLUMN_USER_ID, newRecord.getUserId());
        values.put(FlickerContract.PhotoEntry.COLUMN_IS_DOWNLOADED, FlickerContract.booleanInInteger(newRecord.isDownloaded()));
        values.put(FlickerContract.PhotoEntry.COLUMN_IS_LIKED, FlickerContract.booleanInInteger(newRecord.isLiked()));
        values.put(FlickerContract.PhotoEntry.COLUMN_PHOTO_TITLE, newRecord.getPhotoTitle());


        SQLiteDatabase db = getWritableDatabase();

        long rowId = 0;
        try {
            rowId = db.update(TABLE_NAME, values, FlickerContract.PhotoEntry.COLUMN_PHOTO_ID + " = " + photoId, null);

        } catch (SQLException e) {
            //Toast.makeText(context,"due to add : "+e,Toast.LENGTH_LONG).show();
        } finally {
            db.close();
        }
        return rowId > 0;
    }




    public void deleteFavoritePhoto(String photoId) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
//            Toast.makeText(context,"deleteMovie method",Toast.LENGTH_LONG).show();

            if (checkIsDataAlreadyInDBorNot(photoId)) {
                db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + FlickerContract.PhotoEntry.COLUMN_PHOTO_ID + "=\"" + photoId + "\" ;");
            }

            Log.v("DB", "Delete done");

        } catch (SQLException e) {
            //Toast.makeText(context,"due to delete: "+e,Toast.LENGTH_LONG).show();
        } finally {
            db.close();
        }
    }

    public void deleteAll() {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE 1 ; ");

        } catch (SQLException e) {
            //Toast.makeText(context,"due to delete: "+e,Toast.LENGTH_LONG).show();
        } finally {
            db.close();
        }
    }


    public HashMap<String, PhotoRecord> getAllFavoritePhotos(String userId) {

        HashMap<String, PhotoRecord> favoritelist = new HashMap<String, PhotoRecord>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " +
                FlickerContract.PhotoEntry.COLUMN_IS_LIKED + " = 1 " +
                " AND " + FlickerContract.PhotoEntry.COLUMN_USER_ID + " = \"" + userId + "\" ;";
        Cursor c = null;

        PhotoRecord t = null;

        try {
            c = db.rawQuery(query, null);

            //      Toast.makeText(context,"databaseToString method",Toast.LENGTH_SHORT).show();
            if (c != null) {
                c.moveToFirst();

                while (!c.isAfterLast()) {
                    t = new PhotoRecord();
                    t.setPhotoId(c.getString(c.getColumnIndex(FlickerContract.PhotoEntry.COLUMN_PHOTO_ID)));
                    t.setUserId(c.getString(c.getColumnIndex(FlickerContract.PhotoEntry.COLUMN_USER_ID)));
                    t.setUserId(c.getString(c.getColumnIndex(FlickerContract.PhotoEntry.COLUMN_PHOTO_TITLE)));
                    t.setLiked(c.getInt(c.getColumnIndex(FlickerContract.PhotoEntry.COLUMN_IS_LIKED)));
                    t.setDownloaded(c.getInt(c.getColumnIndex(FlickerContract.PhotoEntry.COLUMN_IS_DOWNLOADED)));
                    favoritelist.put(t.getPhotoId(), t);
                    c.moveToNext();
                }
            }
            Log.v("select all fav", "size = " + favoritelist.size());
        } catch (SQLException e) {
            // Toast.makeText(context,"due to: "+e,Toast.LENGTH_LONG).show();
        } finally {
            if (c != null)
                c.close();
            db.close();
        }

        return favoritelist;
    }


    public boolean checkIsDataAlreadyInDBorNot(String photoId) {
        SQLiteDatabase db = getReadableDatabase();
        String Query = "Select * from " +
                TABLE_NAME +
                " where " +
                FlickerContract.PhotoEntry.COLUMN_PHOTO_ID +
                " = \"" + photoId + "\" ; ";
        Cursor cursor = db.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }


    public HashMap<String, PhotoRecord> getAllDownloadedPhotos(String userId) {
        HashMap<String, PhotoRecord> favoritelist = new HashMap<String, PhotoRecord>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " +
                FlickerContract.PhotoEntry.COLUMN_IS_DOWNLOADED + " = 1 " +
                " AND " + FlickerContract.PhotoEntry.COLUMN_USER_ID + " = \"" + userId + "\" ;";
        Cursor c = db.rawQuery(query, null);


        PhotoRecord t = null;

        try {
            //      Toast.makeText(context,"databaseToString method",Toast.LENGTH_SHORT).show();
            c.moveToFirst();

            while (!c.isAfterLast()) {
                t = new PhotoRecord();
                t.setPhotoId(c.getString(c.getColumnIndex(FlickerContract.PhotoEntry.COLUMN_PHOTO_ID)));
                t.setUserId(c.getString(c.getColumnIndex(FlickerContract.PhotoEntry.COLUMN_USER_ID)));
                t.setUserId(c.getString(c.getColumnIndex(FlickerContract.PhotoEntry.COLUMN_PHOTO_TITLE)));
                t.setLiked(c.getInt(c.getColumnIndex(FlickerContract.PhotoEntry.COLUMN_IS_LIKED)));
                t.setDownloaded(c.getInt(c.getColumnIndex(FlickerContract.PhotoEntry.COLUMN_IS_DOWNLOADED)));

                favoritelist.put(t.getPhotoId(), t);

                c.moveToNext();
            }
        } catch (SQLException e) {
            // Toast.makeText(context,"due to: "+e,Toast.LENGTH_LONG).show();
        } finally {
            c.close();
            db.close();
        }
        return favoritelist;
    }


}