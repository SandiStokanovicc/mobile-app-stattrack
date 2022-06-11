package learnprogramming.academy.stattrack;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    public void addUser(User user);

    @Query("SELECT * FROM User WHERE username=(:username) and password=(:password)")
    User login(String username,String password);

    @Query("DELETE FROM User")
    public void nukeTable();

    @Query("SELECT EXISTS (SELECT 1 FROM User WHERE email = :email)")
    Boolean exists(String email);

    //@Query("SELECT User.user_id FROM User WHERE username=(:username) and password=(:password)")
    //User getUserIdDB(String username,String password);


}