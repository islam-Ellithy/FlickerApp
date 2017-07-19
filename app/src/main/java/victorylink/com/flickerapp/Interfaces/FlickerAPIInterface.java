package victorylink.com.flickerapp.Model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by MrHacker on 7/18/2017.
 */

public interface FlickerAPIInterface {
    @GET(EssentialResources.RecentPhotosURL)
    Call<Result> listRecentPhotos();

    @GET(EssentialResources.UserPhotosURL)
    Call<Result> listUserPhotos(@Query("user_id")String userId);

}
