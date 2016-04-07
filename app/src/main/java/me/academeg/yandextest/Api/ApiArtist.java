package me.academeg.yandextest.Api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ApiArtist {

    private int id;
    private String name;
    private ArrayList<String> genres;
    private int countTracks;
    private int countAlbums;
    private String link;
    private String description;
    private ApiImage avatar;


    public ApiArtist() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public int getCountTracks() {
        return countTracks;
    }

    public int getCountAlbums() {
        return countAlbums;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public ApiImage getAvatar() {
        return avatar;
    }

    public static ApiArtist parse(JSONObject object) throws JSONException {
        ApiArtist artist = new ApiArtist();
        artist.id = object.getInt("id");
        artist.name = object.getString("name");
        if (object.has("genres")) {
            JSONArray arr = object.getJSONArray("genres");
            artist.genres = new ArrayList<>(arr.length());
            for (int i = 0; i < arr.length(); i++) {
                artist.genres.add(arr.getString(i));
            }
        }
        if (object.has("tracks")) {
            artist.countTracks = object.getInt("tracks");
        }
        if (object.has("albums")) {
            artist.countAlbums = object.getInt("albums");
        }
        if (object.has("link")) {
            artist.link = object.getString("link");
        }
        if (object.has("description")) {
            artist.description = object.getString("description");
        }
        if (object.has("cover")) {
            artist.avatar = ApiImage.parse(object.getJSONObject("cover"));
        }
        return artist;
    }
}
