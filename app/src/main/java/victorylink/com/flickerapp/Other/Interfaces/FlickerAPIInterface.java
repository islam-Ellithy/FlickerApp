package victorylink.com.flickerapp.Other.Interfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import victorylink.com.flickerapp.Models.Constants;
import victorylink.com.flickerapp.Other.Parsers.Result;

/**
 * Created by MrHacker on 7/18/2017.
 */

public interface FlickerAPIInterface {
    @GET(Constants.RECENT_PHOTOS_URL)
    Call<Result> listRecentPhotos();

    @GET(Constants.USER_PHOTOS_URL)
    Call<Result> listUserPhotos(@Query("user_id")String userId);

}
