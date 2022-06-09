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


    @Query("SELECT * from User WHERE username=(:username) and password=(:password)")
    User login(String username,String password);


}