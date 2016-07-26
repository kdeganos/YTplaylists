package com.epicodus.ytplaylists.models;

import com.google.firebase.database.Exclude;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Guest on 7/15/16.
 */
public class PlaylistObj {
    String playlistName;
    List<VideoObj> videos = new ArrayList<>();
    String timestamp;
    List<String> sharedUsers = new ArrayList<>();

    public PlaylistObj() {}

    public PlaylistObj(String name, Date timestamp, List<VideoObj> videos) {
        this.playlistName = name;
        this.videos = videos;

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        this.timestamp = dateFormat.format(timestamp);
    }

    public List<VideoObj> getVideos() {
        return videos;
    }
    public void addVideos(VideoObj video) {
        this.videos.add(video);
    }
    public void setVideos(List<VideoObj> videos) {
        this.videos = videos;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public List<String> getSharedUsers() {
        return sharedUsers;
    }
    public void addSharedUser(String sharedUser) {
        this.sharedUsers.add(sharedUser);
    }
    public void setSharedUsers(List<String> sharedUsers) {
        this.sharedUsers = sharedUsers;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("sharedUsers", sharedUsers);

        return result;
    }

}