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
    String pushId;
    String playlistName;
    String playlistOwnerId;
    List<VideoObj> videos = new ArrayList<>();
    List<String> playlistUserIds;
    String timestamp;

    public PlaylistObj() {}

    public PlaylistObj(String pushId, String playlistName, String playlistOwnerId, Date timestamp) {
        this.pushId = pushId;
        this.playlistName = playlistName;
        this.playlistOwnerId = playlistOwnerId;

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        this.timestamp = dateFormat.format(timestamp);
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public String getPlaylistOwnerId() {
        return playlistOwnerId;
    }

    public List<VideoObj> getVideos() {
        return videos;
    }
    public void setVideos(List<VideoObj> videos) {
        this.videos = videos;
    }

    public void addVideo(VideoObj video) {
        this.videos.add(video);
    }


    public List<String> getPlaylistUserIds() {
        return playlistUserIds;
    }

    public void setPlaylistUserIds(List<String> playlistUserIds) {
        this.playlistUserIds = playlistUserIds;
    }

    public String getTimestamp() {
        return timestamp;
    }


}