package me.academeg.yandextest.Api;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


public class ApiImage implements Parcelable {

    private String thumbnailPath;
    private String originalPath;


    public ApiImage() {
    }

    public ApiImage(String thumbnailPath, String originalPath) {
        this.thumbnailPath = thumbnailPath;
        this.originalPath = originalPath;
    }

    protected ApiImage(Parcel in) {
        thumbnailPath = in.readString();
        originalPath = in.readString();
    }

    public static final Creator<ApiImage> CREATOR = new Creator<ApiImage>() {
        @Override
        public ApiImage createFromParcel(Parcel in) {
            return new ApiImage(in);
        }

        @Override
        public ApiImage[] newArray(int size) {
            return new ApiImage[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(thumbnailPath);
        dest.writeString(originalPath);
    }
}
