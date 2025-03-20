//package dungnguyen.protunefinal.models;
//
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//
//import java.io.*;
//import java.lang.reflect.Type;
//import java.util.HashMap;
//import java.util.Map;
//
//import static dungnguyen.protunefinal.utilz.Constants.PLAYLIST_DATA;
//
//public class PlaylistManager {
//    private static final String FILE_PATH = PLAYLIST_DATA;
//    private Map<String, Map<String, PlaylistData>> userPlaylists = new HashMap<>();
//    private String currentUser;
//    private Gson gson = new Gson();
//
//    public void setCurrentUser(String username) {
//        this.currentUser = username;
//        if (!userPlaylists.containsKey(username)) {
//            userPlaylists.putIfAbsent(username, new HashMap<>());
//        }
//    }
//
//    public void addPlaylist(String playlistName, PlaylistData playlist) {
//        if (currentUser != null) {
//            userPlaylists.get(currentUser).put(playlistName, playlist);
//            saveToFile();
//        } else {
//            System.out.println("User chưa đăng nhập!");
//        }
//    }
//
//    public PlaylistData getPlaylist(String playlistName) {
//        if (currentUser != null) {
//            return userPlaylists.getOrDefault(currentUser, new HashMap<>()).get(playlistName);
//        }
//        return null;
//    }
//
//    public Map<String, PlaylistData> getAllPlaylists() {
//        if (currentUser != null) {
//            return new HashMap<>(userPlaylists.getOrDefault(currentUser, new HashMap<>()));
//        }
//        return new HashMap<>();
//    }
//
//    public void loadFromFile() {
//        try (Reader reader = new FileReader(FILE_PATH)) {
//            Type type = new TypeToken<Map<String, Map<String, PlaylistData>>>() {}.getType();
//            userPlaylists = gson.fromJson(reader, type);
//            if (userPlaylists == null) {
//                userPlaylists = new HashMap<>();
//            }
//        } catch (FileNotFoundException e) {
//            System.out.println("File không tồn tại, tạo dữ liệu mới.");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void saveToFile() {
//        try (Writer writer = new FileWriter(FILE_PATH)) {
//            gson.toJson(userPlaylists, writer);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
