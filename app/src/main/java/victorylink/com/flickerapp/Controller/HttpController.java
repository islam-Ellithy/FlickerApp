package victorylink.com.flickerapp.Controller;

import victorylink.com.flickerapp.Model.HttpModel;
import victorylink.com.flickerapp.Model.IView;
import victorylink.com.flickerapp.Model.Result;

/**
 * Created by MrHacker on 7/17/2017.
 */

public class HttpController {

    private IView resultView;
    private HttpModel httpModel  ;


    public HttpController(IView newResultView) {
        this.resultView = newResultView;
        this.httpModel = new HttpModel(this);
    }

    public void doHttpRequest() {
        httpModel.doHttpRequestRecentPhotos();
    }

    public void doHttpRequestUserPhotos(String userId){
        httpModel.doHttpRequestUserPhotos(userId);
    }

    public void updateView(Result result) {
        resultView.updateUI(result);
    }
}
