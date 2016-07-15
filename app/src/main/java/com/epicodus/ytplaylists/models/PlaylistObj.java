package com.epicodus.ytplaylists.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Guest on 7/15/16.
 */
public class PlaylistObj {
    List<String> videoIds = new ArrayList<>();
    String timestamp;

    public PlaylistObj() {}

    public PlaylistObj(Date timestamp, List<String> videoIds) {
        this.videoIds = videoIds;

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        this.timestamp = dateFormat.format(timestamp);
    }

    public List<String> getVideos() {
        return videoIds;
    }
    public void addVideoId(String videoId) {
        this.videoIds.add(videoId);
    }
    public void setVideoIds(List<String> videoIds) {
        this.videoIds = videoIds;
    }


}
