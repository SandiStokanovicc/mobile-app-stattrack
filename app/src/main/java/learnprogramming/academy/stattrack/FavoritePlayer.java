package learnprogramming.academy.stattrack;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = {"user_id"},
                        childColumns = {"user_id"},
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE
                )
        }
)
public class FavoritePlayer {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "favorite_player_id")
    private long id;
    @ColumnInfo(name = "user_id", index = true)
    private long parentUserId;
    private String summonerName;
    private String server;

    public FavoritePlayer(){}

    @Ignore
    public FavoritePlayer(String summonerName, String server, long parentUserId){
        this.parentUserId = parentUserId;
        this.summonerName = summonerName.toLowerCase();
        this.server = server;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getParentUserId() {
        return parentUserId;
    }

    public void setParentUserId(long parentUserId) {
        this.parentUserId = parentUserId;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public void setSummonerName(String summonerName) {
        this.summonerName = summonerName;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}
