package victorylink.com.flickerapp.Other.Interfaces;

import java.util.HashMap;

import victorylink.com.flickerapp.Other.data.PhotoRecord;

/**
 * Created by MrHacker on 7/26/2017.
 */

public interface DbView {
    public void updateUI(HashMap<String,PhotoRecord>photoRecordHashMap);
}
