package com.epicodus.ytplaylists.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guest on 7/15/16.
 */
public class Playlist {
    String id;
    String name;
    List<VideoObj> videos = new ArrayList<>();
//    String owner;

    public Playlist() {}

    public Playlist(String id, String name, List<VideoObj> videos) {
        this.id = id;
        this.name = name;
        this.videos = videos;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public List<VideoObj> getVideos() {
        return videos;
    }
    public void addVideoId(VideoObj video) {
        this.videos.add(video);
    }
    public void setVideoIds(List<VideoObj> videos) {
        this.videos = videos;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
