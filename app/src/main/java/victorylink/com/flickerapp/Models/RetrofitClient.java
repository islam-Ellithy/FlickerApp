package victorylink.com.flickerapp.Models;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import victorylink.com.flickerapp.Other.Constants;

/**
 * Created by MrHacker on 7/18/2017.
 */

public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit()
    {
        if(retrofit==null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit ;
    }

}
