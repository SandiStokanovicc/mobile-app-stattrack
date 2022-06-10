package learnprogramming.academy.stattrack;

import androidx.room.Embedded;

public class FavoritePlayerAndUser {

    @Embedded
    FavoritePlayer favoritePlayer;
    String username;
    String password;
}
