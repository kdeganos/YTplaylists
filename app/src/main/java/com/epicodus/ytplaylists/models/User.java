package com.epicodus.ytplaylists.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Guest on 7/15/16.
 */
public class User {
    String name;
    String id;
    String email;
    List<Playlist> playlists = new ArrayList<>();

    public User() {
    }

    public User(String name, String id, String email, List<Playlist> playlists) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.playlists = playlists;

    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }
    public void addPlaylist(Playlist playlist) {
        this.playlists.add(playlist);
    }
    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public void setId(String id) {
        this.id = id;
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
}
