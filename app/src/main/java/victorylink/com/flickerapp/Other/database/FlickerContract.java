package victorylink.com.flickerapp.Other.data;

import android.provider.BaseColumns;

/**
 * Created by MrHacker on 7/20/2017.
 */


//this class is the scelton of the
public class FlickerContract  {

    private FlickerContract()
    {

    }

    //this method take a para that is boolean var and return either 1 or 0 in integer
    public static Integer booleanInInteger(boolean state)
    {
        //is a a conditional expression
        return (state ? 1 : 0);
    }

    public static class PhotoEntry implements BaseColumns {
        public static final String TABLE_NAME = "photoEntry";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_USER_ID = "userId";
        public static final String COLUMN_PHOTO_ID = "photoId";
        public static final String COLUMN_IS_DOWNLOADED = "isDownloaded";
        public static final String COLUMN_IS_LIKED = "isLiked";
        public static final String COLUMN_PHOTO_TITLE = "photoTitle";
        public static final String COLUMN_IMAGE_DATA = "imageData";
    }
}