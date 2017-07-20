package victorylink.com.flickerapp.Models.data;

import java.io.Serializable;

import victorylink.com.flickerapp.Parsers.Photo;

/**
 * Created by MrHacker on 7/20/2017.
 */



public class PhotoRecord implements Serializable {
    private  String userId ;
    private String photoId ;
    private String photoTitle ;
    private boolean isDownloaded ;
    private boolean isLiked ;

    public String getPhotoTitle() {

        return photoTitle;
    }

    public static PhotoRecord setFavoritePhoto(Photo photo, String userId)
    {
        PhotoRecord mPhotoRecord = new PhotoRecord();
        mPhotoRecord.setPhotoTitle(photo.getTitle());
        mPhotoRecord.setLiked(true);
        mPhotoRecord.setPhotoId(photo.getId());
        mPhotoRecord.setDownloaded(false);
        mPhotoRecord.setUserId(userId);

        return mPhotoRecord;
    }

    public void setPhotoTitle(String photoTitle) {
        this.photoTitle = photoTitle;
    }

    public void setDownloaded(boolean downloaded) {
        isDownloaded = downloaded;
    }

    public boolean isDownloaded() {
        return isDownloaded;
    }


    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

}
