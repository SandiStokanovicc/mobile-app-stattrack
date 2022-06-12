package learnprogramming.academy.stattrack;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoritePlayerDao {

    @Insert
    public void addFavoritePlayer(FavoritePlayer favoritePlayer);

    @Query("DELETE FROM FavoritePlayer")
    public void nukeTable();

    @Query("DELETE FROM FavoritePlayer WHERE summonerName = :summonerName AND server = :server AND userEmail = :userEmail")
    public void deleteFavoritePlayer(String summonerName, String server, String userEmail);

    @Query("SELECT * FROM FavoritePlayer INNER JOIN User ON FavoritePlayer.userEmail = User.email WHERE User.username=(:username) and password=(:password)")
    List<FavoritePlayerAndUser> getFavoritesOfUser(String username, String password);

    @Query("SELECT EXISTS (SELECT 1 FROM FavoritePlayer WHERE summonerName = :summonerName AND server = :server AND userEmail = :userEmail)")
    Boolean exists(String summonerName, String server, String userEmail);
}
