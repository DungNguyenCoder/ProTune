package dungnguyen.protunefinal.utilz;

import dungnguyen.protunefinal.models.SongData;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LoadSave {

    private static final String SONG_DATA = "data/song.txt";

    public static void saveSongsToFile(List<SongData> songs) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SONG_DATA))) {
            for (SongData song : songs) {
                writer.write(song.getSongName() + "|" + song.getArtist() + "|" + song.getPlaylist() + "|" + song.getThumbnailPath() + "|" + song.getFilePath() + "|" + song.getUploader());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<SongData> loadSongsFromFile() {
        List<SongData> songs = new ArrayList<>();
        File file = new File(SONG_DATA);
        if (!file.exists()) return songs;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 6) {
                    songs.add(new SongData(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return songs;
    }

    public static void deleteSongFromFile(SongData Song) {
        List<SongData> songs = loadSongsFromFile();
        songs.removeIf(song -> song.getSongName().equals(Song.getSongName()) && song.getArtist().equals(Song.getArtist()));
        saveSongsToFile(songs);
    }

    public static void updateSongInFile(SongData oldSong ,SongData updatedSong) {
        List<SongData> songs = loadSongsFromFile();
        for (int i = 0; i < songs.size(); i++) {
            if (songs.get(i).getSongName().equals(oldSong.getSongName()) &&
                    songs.get(i).getArtist().equals(oldSong.getArtist())) {
                songs.set(i, updatedSong);
                break;
            }
        }
        saveSongsToFile(songs);
        for(SongData song : songs) {
            System.out.println(song + "---");
        }
    }

}
