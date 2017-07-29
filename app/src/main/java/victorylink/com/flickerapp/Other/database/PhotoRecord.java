package victorylink.com.flickerapp.Other.database;

import java.io.Serializable;

import victorylink.com.flickerapp.Other.Parsers.Photo;

/**
 * Created by MrHacker on 7/20/2017.
 */


public class PhotoRecord implements Serializable {
    private String userId;
    private String photoId;
    private String photoTitle;
    private boolean isDownloaded;
    private boolean isLiked;
    private byte[] image ;


    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getPhotoTitle() {

        return photoTitle;
    }

    public static PhotoRecord setPhotoRecord(Photo photo, String userId) {
        PhotoRecord mPhotoRecord = new PhotoRecord();
        mPhotoRecord.setPhotoTitle(photo.getTitle());
        mPhotoRecord.setLiked(1);
        mPhotoRecord.setPhotoId(photo.getId());
        mPhotoRecord.setDownloaded(0);
        mPhotoRecord.setUserId(userId);
        return mPhotoRecord;
    }

    public void setPhotoTitle(String photoTitle) {
        this.photoTitle = photoTitle;
    }

    public void setDownloaded(Integer downloaded) {
        isDownloaded = (downloaded == 1);
    }

    public boolean isDownloaded() {
        return isDownloaded;
    }


    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(Integer state) {

        isLiked = (state == 1);
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
