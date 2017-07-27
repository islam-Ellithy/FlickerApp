package victorylink.com.flickerapp.Controllers;

import victorylink.com.flickerapp.Models.HttpModel;
import victorylink.com.flickerapp.Other.Interfaces.IView;
import victorylink.com.flickerapp.Other.Parsers.Result;

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
