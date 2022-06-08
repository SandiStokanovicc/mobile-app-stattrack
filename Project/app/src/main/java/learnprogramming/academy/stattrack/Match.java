package learnprogramming.academy.stattrack;

import android.widget.ImageView;
import android.widget.TextView;

public class Match {

    private int id;
    private String championIcon;
    private double kda;
    private String matchResult;
    private String killsDeathsAssists;
    private int controlWardsPlaced;
    private int wardsKilled;
    private int wardsPlaced;
    private int damageDealt;
    private int damageTaken;
    private int minionsKilled;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Match(int id, String championIcon, double kda, String matchResult, String killsDeathsAssists, int controlWardsPlaced,
                 int wardsKilled, int wardsPlaced, int damageDealt, int damageTaken, int minionsKilled) {
        this.id = id;
        this.championIcon = championIcon;
        this.kda = kda;
        this.matchResult = matchResult;
        this.killsDeathsAssists = killsDeathsAssists;
        this.controlWardsPlaced = controlWardsPlaced;
        this.wardsKilled = wardsKilled;
        this.wardsPlaced = wardsPlaced;
        this.damageDealt = damageDealt;
        this.damageTaken = damageTaken;
        this.minionsKilled = minionsKilled;
    }

    public String getChampionIcon() {
        return championIcon;
    }

    public void setChampionIcon(String championIcon) {
        this.championIcon = championIcon;
    }

    public double getKda() {
        return kda;
    }

    public void setKda(double kda) {
        this.kda = kda;
    }

    public String getMatchResult() {
        return matchResult;
    }

    public void setMatchResult(String matchResult) {
        this.matchResult = matchResult;
    }

    public String getKillsDeathsAssists() {
        return killsDeathsAssists;
    }

    public void setKillsDeathsAssists(String killsDeathsAssists) {
        this.killsDeathsAssists = killsDeathsAssists;
    }

    public int getControlWardsPlaced() {
        return controlWardsPlaced;
    }

    public void setControlWardsPlaced(int controlWardsPlaced) {
        this.controlWardsPlaced = controlWardsPlaced;
    }

    public int getWardsKilled() {
        return wardsKilled;
    }

    public void setWardsKilled(int wardsKilled) {
        this.wardsKilled = wardsKilled;
    }

    public int getWardsPlaced() {
        return wardsPlaced;
    }

    public void setWardsPlaced(int wardsPlaced) {
        this.wardsPlaced = wardsPlaced;
    }

    public int getDamageDealt() {
        return damageDealt;
    }

    public void setDamageDealt(int damageDealt) {
        this.damageDealt = damageDealt;
    }

    public int getDamageTaken() {
        return damageTaken;
    }

    public void setDamageTaken(int damageTaken) {
        this.damageTaken = damageTaken;
    }

    public int getMinionsKilled() {
        return minionsKilled;
    }

    public void setMinionsKilled(int minionsKilled) {
        this.minionsKilled = minionsKilled;
    }




}
