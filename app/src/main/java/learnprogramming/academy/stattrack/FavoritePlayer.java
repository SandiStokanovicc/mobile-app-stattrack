package learnprogramming.academy.stattrack;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(
        primaryKeys = {"summonerName", "server", "userEmail"},
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = {"email"},
                        childColumns = {"userEmail"},
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE
                )
        }
)
public class FavoritePlayer {
    @NonNull
    @ColumnInfo(name = "userEmail", index = true)
    private String parentEmail;
    @NonNull
    private String summonerName;
    @NonNull
    private String server;

    public FavoritePlayer(){}

    @Ignore
    public FavoritePlayer(String summonerName, String server, String parentEmail){
        this.parentEmail = parentEmail;
        this.summonerName = summonerName.toLowerCase();
        this.server = server;
    }

    public String getParentEmail() {
        return parentEmail;
    }

    public void setParentEmail(String parentEmail) {
        this.parentEmail = parentEmail;
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
