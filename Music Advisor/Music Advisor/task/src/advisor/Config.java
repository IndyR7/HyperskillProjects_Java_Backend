package advisor;

public class Config {
    public static final String CLIENT_ID = "a237f6cbbce2468bbf1040edc730407c";
    public static final String CLIENT_SECRET = "04810c82cece4c688f1fc050d3ce043d";
    public static final String REDIRECT_URI = "http://localhost:8080/";
    public static String SERVER_PATH = "https://accounts.spotify.com";
    public static String API_PATH = "https://api.spotify.com";
    public static String ACCESS_TOKEN;
    public static String TOKEN_LINK;
    public static String URI;
    public static String AUTH_CODE;
    public static String CATEGORIES_PATH;
    public static String NEW_RELEASES_PATH;
    public static String PLAYLISTS_PATH;
    public static String FEATURED_PLAYLISTS_PATH;

    public static void setURI() {
        URI = Config.SERVER_PATH + "/authorize" +
                "?client_id=" + Config.CLIENT_ID +
                "&redirect_uri=" + Config.REDIRECT_URI +
                "&response_type=code";
    }

    public static void setTokenLink() {
        TOKEN_LINK = SERVER_PATH + "/api/token";
    }

    public static void setCategoriesPath() {
        CATEGORIES_PATH = API_PATH + "/v1/browse/categories";
    }

    public static void setNewReleasesPath() {
        NEW_RELEASES_PATH = API_PATH + "/v1/browse/new-releases";
    }

    public static void setPlaylistsPath(String id) {
        PLAYLISTS_PATH = API_PATH + String.format("/v1/browse/categories/%s/playlists", id);
    }

    public static void setFeaturedPlaylistsPath() {
        FEATURED_PLAYLISTS_PATH = API_PATH + "/v1/browse/featured-playlists";
    }
}