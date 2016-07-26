package com.epicodus.ytplaylists.models;

import com.google.firebase.database.Exclude;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Guest on 7/15/16.
 */
public class UserObj {

    String userId;
    String name;
    String email;
    List<String> playlistIds = new ArrayList<>();
    List<String> friendIds = new ArrayList<>();

    public UserObj() {
    }

    public UserObj(String userId, String name, String email, List<String> playlistIds) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.playlistIds = playlistIds;

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getName() {
        return name;
    }


    public List<String> getPlaylistIds() {
        return playlistIds;
    }
    public void addPlaylistId(String playlistId) {
        this.playlistIds.add(playlistId);
    }
    public void setPlaylistIds(List<String> playlistIds) {
        this.playlistIds = playlistIds;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getFriendIds() {
        return friendIds;
    }
    public void addFriendId(String friendId) {
        this.friendIds.add(friendId);
    }
    public void setFriendIds(List<String> friendIds) {
        this.friendIds = friendIds;
    }

}
