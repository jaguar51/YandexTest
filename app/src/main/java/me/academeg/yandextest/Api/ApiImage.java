package me.academeg.yandextest.Api;

import org.json.JSONException;
import org.json.JSONObject;


public class ApiImage {

    private String thumbnailPath;
    private String originalPath;


    public ApiImage() {
    }

    public ApiImage(String thumbnailPath, String originalPath) {
        this.thumbnailPath = thumbnailPath;
        this.originalPath = originalPath;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public String getOriginalPath() {
        return originalPath;
    }

    public static ApiImage parse(JSONObject object) throws JSONException {
        ApiImage image = new ApiImage();
        image.thumbnailPath = object.getString("small");
        image.originalPath = object.getString("big");
        return image;
    }
}
