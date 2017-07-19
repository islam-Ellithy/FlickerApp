package victorylink.com.flickerapp.Models;

import victorylink.com.flickerapp.Parsers.Photo;

import static victorylink.com.flickerapp.BuildConfig.FLICKER_API_KEY;

/**
 * Created by MrHacker on 7/18/2017.
 */

public class Constants {
    public final static String BASE_URL = "https://api.flickr.com/services/rest/";
    public final static String RECENT_PHOTOS_URL = "?method=flickr.interestingness.getList&api_key="+FLICKER_API_KEY+"&per_page=10&format=json&nojsoncallback=1";
    public final static String USER_PHOTOS_URL = "?method=flickr.people.getPublicPhotos&api_key="+FLICKER_API_KEY+"&format=json&nojsoncallback=1";
    public static String getPhotoUrl(Photo photoItem)
    {
        return "http://farm" + photoItem.getFarm() + ".staticflickr.com/" + photoItem.getServer() + "/" + photoItem.getId() + "_" + photoItem.getSecret() + ".jpg";
    }
}
