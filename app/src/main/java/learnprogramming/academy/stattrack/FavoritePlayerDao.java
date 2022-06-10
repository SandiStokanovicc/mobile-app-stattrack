package learnprogramming.academy.stattrack;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoritePlayerDao {

    @Insert
    public void addFavoritePlayer(FavoritePlayer favoritePlayer);

    //@Query("SELECT * from User WHERE username=(:username) and password=(:password)")
    //FavoritePlayer getFavoritePlayer(String username,String password); //ova se vjerovatno nece koristiti

    @Query("DELETE FROM FavoritePlayer")
    public void nukeTable();

    @Query("SELECT * FROM FavoritePlayer INNER JOIN User ON FavoritePlayer.user_id = User.user_id WHERE User.username=(:username) and password=(:password)")
    List<FavoritePlayerAndUser> getFavoritesOfUser(String username, String password);

    @Query("SELECT EXISTS (SELECT 1 FROM FavoritePlayer WHERE summonerName = :summonerName AND server = :server AND user_id = :user_id)")
    Boolean exists(String summonerName, String server, long user_id);
}
