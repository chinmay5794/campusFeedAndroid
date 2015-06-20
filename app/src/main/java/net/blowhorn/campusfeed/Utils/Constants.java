package net.blowhorn.campusfeed.Utils;

/**
 * Created by chinmay on 18/6/15.
 */
public class Constants {

    public class Keys {

        public static final String USER_ID = "user_id";
        public static final String FIRST_NAME = "first_name";
        public static final String LAST_NAME = "last_name";
        public static final String STATUS = "status";
        public static final String PASSWORD = "password";
        public static final String AUTH_TOKEN = "mAuthToken";
        public static final String IS_LOGGED_IN = "is_logged_in";
        public static final String USER_CHANNELS = "user_channels";
        public static final String ALL_CHANNELS = "all_channels";
        public static final String CHANNEL_ID = "channel_id";
        public static final String CHANNEL_NAME = "channel_name";
        public static final String CHANNEL_IMAGE_URL = "channel_img_url";
        public static final String CHANNEL_NUM_FOLLOWERS = "num_followers";
        public static final String CHANNEL_DESCRIPTION = "description";
        public static final String CHANNEL_CREATED_TIME = "created_time";
        public static final String CHANNEL_ADMINS = "admins";
        public static final String CHANNEL_POSTS = "posts";
        public static final String POST_ID = "post_id";
        public static final String POST_IMAGE_URL = "img_url";
        public static final String POST_TIMESTAMP = "time";
        public static final String POST_TEXT = "text";
        public static final String POST_AUTHOR_FULL_NAME = "user_full_name";
        public static final String POST_AUTHOR_IMAGE_URL = "user_img_url";
        public static final String POST_AUTHOR_BRANCH = "user_branch";
    }

    public class SharedPrefs {
        public static final String USER_CREDENTIALS = "user_credentials";
    }

    public static class Status {
        public static final String SUCCESS = "200";
    }

    public static boolean allChannelsDataFetched = false;
    public static boolean feedDataFetched = false;
    public static boolean miscellaneousDataFetched = false;
    public static boolean myChannelsDataFetched = false;

    public static String mAuthToken = null;

    public static final String URL_ROOT = "http://192.168.1.12:8080";
    public static final String URL_LOGIN = URL_ROOT + "/login";
    public static final String URL_GET_ALL_CHANNELS = URL_ROOT + "/channels";

    public static String URL_GET_CHANNEL_DETAILS(String channelId) {
        return URL_GET_ALL_CHANNELS + "?" + channelId;
    }

    public static String URL_GET_MY_CHANNELS(String userId, String limit, String offset) {
        return URL_ROOT + "/users/" + userId + "/channels" + "?limit=" + limit + "&offset=" + offset;
    }

    public static String URL_GET_POSTS_OF_CHANNEL(String channelId, String limit, String offset) {
        return URL_ROOT + "/channels/" + channelId + "/posts" + "?limit=" + limit + "&offset=" + offset;
    }

    /*public static final String URL_SHOW_PENDING_CHANNELS = URL_ROOT + "/pendingchannels";

    public static String URL_SHOW_PENDING_POSTS(String channelId) {
        return URL_ROOT + "/channels/" + channelId + "/pendingposts";
    }*/

    public static String URL_SHOW_PENDING_REQUESTS(String userId) {
        return URL_LOGIN + "/requests/" + userId;
    }

    public static String URL_CREATE_POST(String channelId) {
        return URL_ROOT + "/channels/" + channelId + "/posts";
    }
    public static final String URL_GET_FEED = URL_ROOT + "/feed";
}

