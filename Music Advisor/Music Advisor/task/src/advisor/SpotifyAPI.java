package advisor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class SpotifyAPI {
    private static boolean isAuthorized = false;
    private static HttpServer server;
    private static HttpClient client;
    private static final Map<String, String> playlists = new HashMap<>();

    public static boolean isAuthorized() {
        return isAuthorized;
    }

    public static void runServer() throws IOException {
        setServer();
        setClient();
        setContext();
    }

    private static void setClient() {
        client = HttpClient.newBuilder().build();
    }

    private static void setServer() throws IOException {
        server = HttpServer.create();
        server.bind(new InetSocketAddress(8080), 0);
    }

    private static void setContext() {
        Config.setURI();

        System.out.println(Prompt.AUTHORIZE_HERE);

        server.start();

        server.createContext("/", exchange -> {
            String query = exchange.getRequestURI().getQuery();
            String response;

            if (query != null && query.contains("code")) {
                Config.AUTH_CODE = query.substring(5);
                response = Prompt.SUCCESSFUL_RESPONSE;
                isAuthorized = true;
                System.out.println(Prompt.CODE_RECEIVED);
            } else {
                response = Prompt.FAILED_RESPONSE;
            }

            exchange.sendResponseHeaders(200, response.length());
            exchange.getResponseBody().write(response.getBytes());
            exchange.getResponseBody().close();
        });

        System.out.println(Prompt.WAITING);

        while (Config.AUTH_CODE == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        server.stop(5);
    }

    public static String getAccessToken() throws IOException, InterruptedException {
        Config.setTokenLink();

        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create(Config.TOKEN_LINK))
                .POST(HttpRequest.BodyPublishers.ofString(
                        "grant_type=authorization_code" +
                                "&code=" + Config.AUTH_CODE +
                                "&client_id=" + Config.CLIENT_ID +
                                "&client_secret=" + Config.CLIENT_SECRET +
                                "&redirect_uri=" + Config.REDIRECT_URI))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assert response != null;

        Config.ACCESS_TOKEN = response.body().split(":")[1].replaceAll("\"", "");

        return response.body();
    }

    public static String getCategories() throws IOException, InterruptedException {
        StringBuilder sbCategories = new StringBuilder();

        Config.setCategoriesPath();

        HttpRequest request = getRequest(Config.CATEGORIES_PATH);

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assert response != null;

        String json = response.body();
        JsonObject jo = JsonParser.parseString(json).getAsJsonObject();
        JsonObject categoriesObj = jo.getAsJsonObject("categories");

        for (JsonElement category : categoriesObj.getAsJsonArray("items")) {
            JsonObject categoryObj = JsonParser.parseString(String.valueOf(category)).getAsJsonObject();

            sbCategories.append(getName(categoryObj)).append("&&");

            playlists.put(getName(categoryObj), getID(categoryObj));
        }
        return sbCategories.toString();
    }

    public static String getNewReleases() throws IOException, InterruptedException {
        StringBuilder sbNewReleases = new StringBuilder();

        Config.setNewReleasesPath();

        HttpRequest request = getRequest(Config.NEW_RELEASES_PATH);

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assert response != null;

        String json = response.body();
        JsonObject jo = JsonParser.parseString(json).getAsJsonObject();
        JsonObject albumsObj = jo.getAsJsonObject("albums");

        for (JsonElement newRelease : albumsObj.getAsJsonArray("items")) {
            JsonObject newReleaseObj = JsonParser.parseString(String.valueOf(newRelease)).getAsJsonObject();

            sbNewReleases
                    .append("\n")
                    .append(getName(newReleaseObj))
                    .append("\n").append(getArtists(newReleaseObj))
                    .append("\n").append(getExternalURL(newReleaseObj))
                    .append("&&");
        }
        return sbNewReleases.toString();
    }

    public static String getFeaturedPlaylists() throws IOException, InterruptedException {
        Config.setFeaturedPlaylistsPath();

        HttpRequest request = getRequest(Config.FEATURED_PLAYLISTS_PATH);

        return getPlaylists(request);
    }

    public static String getPlaylists(String name) throws IOException, InterruptedException {
        getCategories();

        if (!playlists.containsKey(name)) {
            return Prompt.UNKNOWN_CATEGORY_NAME;
        }

        String playlistID = playlists.get(name);
        Config.setPlaylistsPath(playlistID);

        HttpRequest request = getRequest(Config.PLAYLISTS_PATH);

        return getPlaylists(request);
    }

    private static String getPlaylists(HttpRequest request) throws IOException, InterruptedException {
        StringBuilder sbPlaylists = new StringBuilder();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assert response != null;

        String json = response.body();

        JsonObject jo = JsonParser.parseString(json).getAsJsonObject();
        JsonObject playlistsObj = jo.getAsJsonObject("playlists");

        try {
            for (JsonElement playlist : playlistsObj.getAsJsonArray("items")) {
                JsonObject playlistObj = JsonParser.parseString(String.valueOf(playlist)).getAsJsonObject();

                sbPlaylists
                        .append("\n")
                        .append(getName(playlistObj))
                        .append("\n")
                        .append(getExternalURL(playlistObj))
                        .append("&&");
            }
            return sbPlaylists.toString();
        } catch (Exception e) {
            return response.body();
        }
    }

    private static HttpRequest getRequest(String path) {
        return HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + Config.ACCESS_TOKEN)
                .uri(URI.create(path))
                .GET()
                .build();
    }

    private static String getExternalURL(JsonObject jo) {
        JsonObject externalURLObj = jo.get("external_urls").getAsJsonObject();
        return externalURLObj.get("spotify").getAsString();
    }

    private static String getID(JsonObject jo) {
        return jo.get("id").getAsString();
    }

    private static String getName(JsonObject jo) {
        return jo.get("name").getAsString();
    }

    private static String getArtists(JsonObject jo) {
        JsonArray artistsArray = jo.get("artists").getAsJsonArray();
        StringBuilder sbArtists = new StringBuilder().append("[");

        for (JsonElement artist : artistsArray) {
            JsonObject artistObj = JsonParser.parseString(String.valueOf(artist)).getAsJsonObject();
            String artistName = artistObj.get("name").getAsString();
            sbArtists.append(artistName).append(", ");
        }
        return sbArtists.replace(sbArtists.length() - 2, sbArtists.length(), "")
                .append("]")
                .toString();
    }
}