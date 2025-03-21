package dungnguyen.protunefinal.models;

import java.io.File;

public class SongData {
    private String songName;
    private String artist;
    private String playlist;
    private String thumbnailPath;
    private String filePath;
    private String uploader;

    public SongData(String songName, String artist, String playlistName, String thumbnailPath, String filePath,  String uploader) {
        this.songName = songName;
        this.artist = artist;
        this.playlist = playlistName;
        this.thumbnailPath = thumbnailPath;
        this.filePath = filePath;
        this.uploader = uploader;
    }

    @Override
    public String toString() {
        return "Song name: " +  songName + "\nArtist: " + artist + "\nUploader: " + uploader;
    }

    public String getSongName() {
        return songName;
    }

    public String getArtist() {
        return artist;
    }

    public String getPlaylist() {
        return playlist;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public String getFilePath() {
        return new File(filePath).getName();
    }

    public String getUploader() {
        return uploader;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setPlaylist(String playlist) {
        this.playlist = playlist;
    }
}
