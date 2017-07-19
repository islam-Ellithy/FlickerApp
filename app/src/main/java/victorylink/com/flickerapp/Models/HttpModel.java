package victorylink.com.flickerapp.Model;
import android.util.Log;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import victorylink.com.flickerapp.Controller.HttpController;
import victorylink.com.flickerapp.Interfaces.FlickerAPIInterface;
import victorylink.com.flickerapp.Parser.Result;

/**
 * Created by MrHacker on 7/17/2017.
 */

public class HttpModel {

    private HttpController controller ;

    public HttpModel(HttpController newController)
    {
        this.controller = newController ;
    }

    public void doHttpRequestRecentPhotos()
    {
        //call data through internet
        FlickerAPIInterface flickerAPIInterface = RetrofitClient.getRetrofit().create(FlickerAPIInterface.class);
        Call<Result> connection = flickerAPIInterface.listRecentPhotos();

        try {

            connection.enqueue(new Callback<Result>() {

                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    Result result = response.body();
                    if(result!=null)
                    {
                        controller.updateView(result);
                        Log.v("TAG", response.body().getPhotos().getPhotoList().get(0).getTitle());
                    }
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    Log.v("TAG", "No Internet Connection");
                }
            });
        }catch (Exception e)
        {
            Log.v("TAG",e.getMessage());

        }
    }

    public void doHttpRequestUserPhotos(String userId)
    {
        Log.v("TAG","ID = "+userId);

        //call data through internet
        FlickerAPIInterface flickerAPIInterface = RetrofitClient.getRetrofit().create(FlickerAPIInterface.class);
        Call<Result> connection = flickerAPIInterface.listUserPhotos(userId);

        connection.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                Result result = response.body();
                if(result!=null)
                {
                    controller.updateView(result);
                    Log.v("TAG", result.getPhotos().getPhotoList().size()+"");
                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.v("TAG","No Internet Connection");
            }
        });
    }

}
