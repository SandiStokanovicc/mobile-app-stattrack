package learnprogramming.academy.stattrack;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface LoginInstanceDao {

    @Insert
    public void addLoginInstance(LoginInstance loginInstance);

    @Query("SELECT * FROM LoginInstance LIMIT 1")
    LoginInstance checkIfLoggedIn();

    @Query("DELETE FROM LoginInstance")
    public void logout();
}
