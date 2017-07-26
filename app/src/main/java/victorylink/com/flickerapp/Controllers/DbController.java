package victorylink.com.flickerapp.Controllers;

import victorylink.com.flickerapp.Interfaces.IView;
import victorylink.com.flickerapp.Models.DbModel;

/**
 * Created by MrHacker on 7/26/2017.
 */

public class DbController {
    private IView view;
    private DbModel dbModel  ;

    public DbController(IView newResultView) {
        this.view = newResultView;

//        this.dbModel = new DbModel();
    }

    public void getFavoritePhotos(String userId)
    {
        
    }

}
