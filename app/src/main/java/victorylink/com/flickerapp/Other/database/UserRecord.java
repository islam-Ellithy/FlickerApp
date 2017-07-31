package victorylink.com.flickerapp.Other.database;

import com.facebook.Profile;

import java.io.Serializable;

/**
 * Created by MrHacker on 7/20/2017.
 */



public class UserRecord implements Serializable {
    private Integer id ;
    private String userId;
    private String photoId;
    private String photoPath;
    private String photoTitle;
    private byte[] image;
    private String username ;

    public static UserRecord setUserRecord(byte[] image, Profile profile, String userId) {
        UserRecord mUserRecord = new UserRecord();
        mUserRecord.setPhotoTitle(profile.getId());
        mUserRecord.setUserId(profile.getId());
        mUserRecord.setPhotoId(profile.getId());
        mUserRecord.setImage(image);
        mUserRecord.setUsername(profile.getName());
        return mUserRecord;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getPhotoTitle() {
        return photoTitle;
    }

    public void setPhotoTitle(String photoTitle) {
        this.photoTitle = photoTitle;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
