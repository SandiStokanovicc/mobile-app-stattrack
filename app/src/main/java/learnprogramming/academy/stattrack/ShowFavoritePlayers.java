package learnprogramming.academy.stattrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ShowFavoritePlayers extends AppCompatActivity {
    private List<FavoritePlayerAndUser> favoritePlayerList;
    public ListView listView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_favorite_players);
        listView = findViewById(R.id.favorite_players_listview);

        Bundle extras = getIntent().getExtras();
        FavoritePlayerDao favoritePlayerDao=UserDatabase.getInstance(this).favoritePlayerDao();
        favoritePlayerList = favoritePlayerDao.getFavoritesOfUser(extras.getString("username"), extras.getString("password"));
        if(favoritePlayerList != null) {
            setUpAdapter(favoritePlayerList);
        }
        else {
            Toast.makeText(this, "Incorrect login information", Toast.LENGTH_SHORT).show();
        }


        /* //dodati ako mi ne bude mrsko
        if (savedInstanceState != null){
            favoritePlayerList = savedInstanceState.getParcelableArrayList("favorite_players_list");
            //isPlayingAudio = savedInstanceState.getString("isPlayingAudio");
            setUpAdapter(favoritePlayerList);
        }
        */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                FavoritePlayerAndUser favoritePlayerParent = (FavoritePlayerAndUser) parent.getItemAtPosition(position);
                FavoritePlayer favoritePlayer = favoritePlayerParent.favoritePlayer;

                String url = "https://stattrack.me/rest/summonersMobileAPI/" + favoritePlayer.getSummonerName() + "/" + favoritePlayer.getServer();
                //String url = "https://" + serverSpinner.getSelectedItem().toString() +
                //".api.riotgames.com/lol/summoner/v4/summoners/by-name/" + summonerNameInput.getText().toString();

                Intent intent = new Intent(ShowFavoritePlayers.this, ShowMatches.class);
                intent.putExtra("URL", url);
                startActivity(intent);
            }
        });

    }

    /* //dodati ako mi ne bude mrsko
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putParcelableArrayList("favorite_players_list", (ArrayList<? extends Parcelable>) favoritePlayerList);
    }*/



    public void setUpAdapter(List<FavoritePlayerAndUser> favoritePlayerList){

        FavoritePlayerAdapter favoritePlayerAdapter = new FavoritePlayerAdapter(favoritePlayerList, this);
        listView.setAdapter(favoritePlayerAdapter);
    }

}