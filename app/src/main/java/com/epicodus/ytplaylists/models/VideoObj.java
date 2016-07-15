package com.epicodus.ytplaylists.models;


import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Guest on 7/7/16.
 */
public class VideoObj {
    String mVideoId;
    String mPublishedAt;
    String mTitle;
    String mDescription;
    String mThumbnail;

    public VideoObj() {
    }

    public VideoObj(String videoId, String publishedAt, String title, String description, String thumbnail) {
        this.mVideoId = videoId;
        this.mTitle = title;
        this.mDescription = description;
        this.mThumbnail = thumbnail;

        //2016-07-08T13:41:40.000Z
        SimpleDateFormat input, output;
        Date date;
        input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            output = new SimpleDateFormat("dd-MMM-yyyy");
            date = input.parse(publishedAt);
            this.mPublishedAt = output.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getVideoId() {
        return mVideoId;
    }

    public String getPublishedAt() {
        return mPublishedAt;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getThumbnail() {
        return mThumbnail;
    }
}
