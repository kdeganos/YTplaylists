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
    String playlistId;
    String playlistName;
    String ownerId;
    List<VideoObj> videoObjects = new ArrayList<>();
    String timestamp;

    List<String> sharedUsers = new ArrayList<>();


    public PlaylistObj() {}

    public PlaylistObj(String playlistName, Date timestamp, String ownerId) {

        this.playlistName = playlistName;

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        this.timestamp = dateFormat.format(timestamp);
        this.ownerId = ownerId;

    }

    public PlaylistObj(List<VideoObj> videoObjects) {
        this.videoObjects = videoObjects;
    }

        public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public List<VideoObj> getVideos() {
        return this.videoObjects;
    }
    public void setVideos(List<VideoObj> videoObjects) {
        this.videoObjects = videoObjects;
    }

    public void addVideo(VideoObj video) {
        this.videoObjects.add(video);
    }

    public String getTimestamp() {
        return timestamp;
    }


    public List<String> getSharedUsers() {
        return sharedUsers;
    }

    public void setSharedUsers(List<String> sharedUsers) {
        this.sharedUsers = sharedUsers;
    }

    public String getOwnerId() {
        return this.ownerId;
    }


}