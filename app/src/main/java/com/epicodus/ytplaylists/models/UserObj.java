package com.epicodus.ytplaylists.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Guest on 7/15/16.
 */
public class UserObj {
    String uid;
    String name;
    String email;
    List<String> ownedPlaylistIds = new ArrayList<>();
    List<String> userPlaylistIds = new ArrayList<>();


    public UserObj() {
    }

    public UserObj(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
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

    public List<String> getOwnedPlaylistIds() {
        return ownedPlaylistIds;
    }

    public void addOwnedPlaylistId(String playlistId) {
        this.ownedPlaylistIds.add(playlistId);
    }

    public void setOwnedPlaylistIds(List<String> ownedPlaylistIds) {
        this.ownedPlaylistIds = ownedPlaylistIds;
    }

    public List<String> getUserPlaylistIds() {
        return userPlaylistIds;
    }

    public void addUserPlaylistId(String userPlaylistId) {
        this.userPlaylistIds.add(userPlaylistId);
    }

    public void setUserPlaylistIds(List<String> userPlaylistIds) {
        this.userPlaylistIds = ownedPlaylistIds;
    }
}
