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
    String videoId;
    String publishedAt;
    String title;
    String description;
    String thumbnail;

    public VideoObj() {
    }

    public VideoObj(String videoId, String publishedAt, String title, String description, String thumbnail) {
        this.videoId = videoId;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;

        //2016-07-08T13:41:40.000Z
        SimpleDateFormat input, output;
        Date date;
        input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            output = new SimpleDateFormat("dd-MMM-yyyy");
            date = input.parse(publishedAt);
            this.publishedAt = output.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getVideoId() {
        return videoId;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
