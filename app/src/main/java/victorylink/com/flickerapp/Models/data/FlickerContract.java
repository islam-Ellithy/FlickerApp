package victorylink.com.flickerapp.Models.data;

import android.provider.BaseColumns;

/**
 * Created by MrHacker on 7/20/2017.
 */

public class FlickerContract  {

    private FlickerContract()
    {

    }

    public static class FavoritePhotosEntry implements BaseColumns {
        public static final String TABLE_NAME = "favoritephotos";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_USER_ID = "userId";
        public static final String COLUMN_PHOTO_ID = "photoId";
        public static final String COLUMN_IS_DOWNLOADED = "isDownloaded";
        public static final String COLUMN_IS_LIKED = "isLiked";
        public static final String COLUMN_PHOTO_TITLE = "photoTitle";

    }
}