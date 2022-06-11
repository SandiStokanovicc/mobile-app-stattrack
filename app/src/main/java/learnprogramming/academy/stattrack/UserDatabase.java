package learnprogramming.academy.stattrack;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities={User.class, FavoritePlayer.class, LoginInstance.class},version=3,exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract LoginInstanceDao loginInstanceDao();

    public abstract FavoritePlayerDao favoritePlayerDao();

    public static UserDatabase INSTANCE = null;

    public static UserDatabase getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context, UserDatabase.class, "users-database").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }
}

