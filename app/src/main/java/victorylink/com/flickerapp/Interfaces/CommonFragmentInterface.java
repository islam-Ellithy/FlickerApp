package victorylink.com.flickerapp.Interfaces;

import android.view.View;

import victorylink.com.flickerapp.Parsers.Result;

/**
 * Created by MrHacker on 7/25/2017.
 */

public interface CommonFragmentInterface {

    public void initView(View view);

    public void assignResultToUI(Result result);

    public void search(android.support.v7.widget.SearchView searchView);
}