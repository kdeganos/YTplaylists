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
    String playlistName;
    List<VideoObj> videoIds = new ArrayList<>();
    String timestamp;

    public PlaylistObj() {}

    public PlaylistObj(String name, Date timestamp, List<VideoObj> videoIds) {
        this.playlistName = name;
        this.videoIds = videoIds;

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        this.timestamp = dateFormat.format(timestamp);
    }

    public List<VideoObj> getVideos() {
        return videoIds;
    }
    public void addVideoId(VideoObj videoId) {
        this.videoIds.add(videoId);
    }
    public void setVideoIds(List<VideoObj> videoIds) {
        this.videoIds = videoIds;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public String getTimestamp() {
        return timestamp;
    }

}
