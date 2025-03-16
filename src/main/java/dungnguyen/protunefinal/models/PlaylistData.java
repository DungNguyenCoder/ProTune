package dungnguyen.protunefinal.models;

import java.util.ArrayList;
import java.util.List;

public class PlaylistData {
    private String username;
    private List<SongData> songs;

    public PlaylistData(String username) {
        this.username = username;
        this.songs = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public List<SongData> getSongs() {
        return songs;
    }

    public void addSong(SongData song) {
        songs.add(song);
    }

    public void deleteSong(SongData song) {
        songs.remove(song);
    }
}
