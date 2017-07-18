package victorylink.com.flickerapp.Model;

/**
 * Created by MrHacker on 7/18/2017.
 */

public class EssentialResources {
    public final static String API_KEY = "5d3a0937e92fe4668c2d1e7f22ef8787";
    public final static String baseURL = "https://api.flickr.com/services/rest/";
    public final static String RecentPhotosURL = "?method=flickr.interestingness.getList&api_key="+API_KEY+"&per_page=10&format=json&nojsoncallback=1";
    public final static String UserPhotosURL = "?method=flickr.people.getPublicPhotos&api_key="+API_KEY+"&format=json&nojsoncallback=1";
}
