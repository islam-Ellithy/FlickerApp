package victorylink.com.flickerapp.Parser;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("photos")
    @Expose
    private Photos photos;

    @SerializedName("stat")
    @Expose
    private String stat;

    protected Result(Parcel in) {
        stat = in.readString();
    }


    public Photos getPhotos() {
        return photos;
    }

    public void setPhotos(Photos photos) {
        this.photos = photos;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public Result() {
    }

}
