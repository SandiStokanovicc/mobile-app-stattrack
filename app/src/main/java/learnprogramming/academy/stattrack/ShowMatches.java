package learnprogramming.academy.stattrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ShowMatches extends AppCompatActivity {
    private RequestQueue apiRequestQueue;
    private List<Match> matchList;
    public ListView listView;
    public static String isPlayingAudio;
    public AppCompatButton visitWebsiteButton;
    public TextView visitWebsiteText;
    public AppCompatButton addFavoriteButton;


    private String summonerName;
    private String server;
    private String userEmail;
    private String profileIconId;
    private static final String CHANNEL_ID = "notification_channel";
    private NotificationManager notifyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_matches);

        visitWebsiteButton = findViewById(R.id.visitWebsiteButton);
        visitWebsiteButton.setVisibility(AppCompatButton.INVISIBLE);
        visitWebsiteText = findViewById(R.id.visitWebsiteText);
        visitWebsiteText.setVisibility(TextView.INVISIBLE);
        addFavoriteButton = findViewById(R.id.add_favorite_button);
        addFavoriteButton.setVisibility(AppCompatButton.INVISIBLE);
        matchList = new ArrayList<>();
        listView = findViewById(R.id.match_listview);
        hideSystemUI();


        createNotificationChannel();
        isPlayingAudio = "false";


        if (savedInstanceState != null){
            matchList = savedInstanceState.getParcelableArrayList("match_list");
            server = savedInstanceState.getString("server");
            summonerName = savedInstanceState.getString("summonerName");
            userEmail = savedInstanceState.getString("userEmail");
            profileIconId = savedInstanceState.getString("profileIconId");
            //isPlayingAudio = savedInstanceState.getString("isPlayingAudio");
            setUpAdapter(matchList);
            visitWebsiteButton.setVisibility(AppCompatButton.VISIBLE);
            visitWebsiteText.setVisibility(TextView.VISIBLE);
        }
        else {

            apiRequestQueue = Volley.newRequestQueue(this); // moguce da ce se ovo morati promijeniti kad se promjeni activity

            Bundle extras = getIntent().getExtras();


            Log.d(extras.getString("URL"), "onCreate: ");
            Toast.makeText(this, "Loading matches!", Toast.LENGTH_SHORT).show();
            userEmail = extras.getString("userEmail");
            summonerName = extras.getString("summonerName");
            server = extras.getString("server");
            String url = "https://stattrack.me/rest/summonersMobileAPI/" + summonerName + "/" + server;
            jsonParseMatches(url);
        }
        //finish();
    }


    public void visitWebsite(View view){
        String url = visitWebsiteButton.getTag().toString();

        Uri website = Uri.parse(url);
        Intent implicitIntent = new Intent(Intent.ACTION_VIEW, website);

        if(implicitIntent.resolveActivity(getPackageManager()) != null){
            startActivity(implicitIntent);
        }
        else{
            Toast.makeText(ShowMatches.this, "Unable to open website",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void buttonTapped(View view) {
        String audioFileName = String.valueOf(view.getTag());
        int resourceId = getResources().getIdentifier(audioFileName, "raw", this.getPackageName());
        MediaPlayer mediaPlayer = MediaPlayer.create(this, resourceId);
        if (isPlayingAudio == "false") {
            isPlayingAudio = "true";
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    isPlayingAudio = "false";
                    mp.reset();
                    mp.release();
                }
            });
        }
        else if(isPlayingAudio == "true"){
            //mediaPlayer.reset();
            mediaPlayer.release();
        }
    }

    public void sendNotification(String title, String text){
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder(title, text);
        notifyManager.notify(1, notifyBuilder.build());
    }

    private NotificationCompat.Builder getNotificationBuilder(String title, String text){
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this, 1,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.background)
                .setContentTitle(title)
                .setContentText(text)
                //.setContentIntent(pendingNotificationIntent)
                .setAutoCancel(true);

        return notifyBuilder;
    }

    public void createNotificationChannel(){
        notifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,
                    "My notification", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("This is my notification!");

            notifyManager.createNotificationChannel(notificationChannel);
        }



    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString("profileIconId", profileIconId);
        savedInstanceState.putString("server", server);
        savedInstanceState.putString("summonerName", summonerName);
        savedInstanceState.putString("isPlayingAudio", isPlayingAudio);
        savedInstanceState.putParcelableArrayList("match_list", (ArrayList<? extends Parcelable>) matchList);
    }

    public void setUpAdapter(List<Match> matchList){

        MatchAdapter matchAdapter = new MatchAdapter(matchList, this);
        listView.setAdapter(matchAdapter);
    }

    public void jsonParseMatches(String url){
        //if it's a JSON array, we will need a JsonArrayRequest
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject responseJSON) {

                JSONArray jsonArray = null;
                try {
                    profileIconId = responseJSON.getString("profileIconId");
                    jsonArray = responseJSON.getJSONArray("matches");

                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject matchInfo = jsonArray.getJSONObject(i);

                        ArrayList<String> items = new ArrayList<>();
                        items.add("i" + matchInfo.getString("item0"));
                        items.add("i" + matchInfo.getString("item1"));
                        items.add("i" + matchInfo.getString("item2"));
                        items.add("i" + matchInfo.getString("item3"));
                        items.add("i" + matchInfo.getString("item4"));
                        items.add("i" + matchInfo.getString("item5"));
                        items.add("i" + matchInfo.getString("item6"));

                        ArrayList<String> spells = new ArrayList<>();
                        spells.add("s" + matchInfo.getString("summonerSpell1Id"));
                        spells.add("s" + matchInfo.getString("summonerSpell2Id"));

                        Match match = new Match(i, matchInfo.getString("championIcon"), matchInfo.getDouble("kda"),
                                matchInfo.getString("matchResult"), matchInfo.getString("killsDeathsAssists"),
                                matchInfo.getInt("controlWardsPlaced"), matchInfo.getInt("wardsKilled"),
                                matchInfo.getInt("wardsPlaced"), matchInfo.getInt("damageDealt"),
                                matchInfo.getInt("damageTaken"), matchInfo.getInt("minionsKilled"), items, spells);
                        matchList.add(match);
                    }

                    setUpAdapter(matchList);
                    View constraintLayout = findViewById(R.id.constraintLayoutShowMatches);
                    constraintLayout.setBackground(null);
                    constraintLayout.setBackgroundColor(Color.parseColor("#484c54"));
                    visitWebsiteButton.setVisibility(AppCompatButton.VISIBLE);
                    visitWebsiteText.setVisibility(TextView.VISIBLE);
                    addFavoriteButton.setVisibility(AppCompatButton.VISIBLE);
                    sendNotification("Match history", "Match history successfully loaded");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Writer writer = new StringWriter();
                error.printStackTrace(new PrintWriter(writer));
                String errorMessage = writer.toString();

                if (errorMessage.contains("AuthFailureError"))
                    Toast.makeText(ShowMatches.this, "API key needs to be changed",
                            Toast.LENGTH_LONG).show();

                else if(errorMessage.contains("ClientError"))
                    Toast.makeText(ShowMatches.this, "No such user found",
                            Toast.LENGTH_LONG).show();

                sendNotification("Match history", "Match history could not be loaded");
                error.printStackTrace();
                Intent intent = new Intent(ShowMatches.this, MainActivity.class);
                startActivity(intent);
            }
        });
        apiRequestQueue.add(request);
    }

    public void addFavorite(View view){
        if(!userEmail.isEmpty()){
            FavoritePlayer favoritePlayer = new FavoritePlayer(summonerName, server, userEmail, profileIconId);
            //if doesn't already exist
            if (!UserDatabase.getInstance(this).favoritePlayerDao() .exists(favoritePlayer.getSummonerName(), favoritePlayer.getServer(), userEmail)) {
                        UserDatabase.getInstance(this).favoritePlayerDao().addFavoritePlayer(favoritePlayer);
                        Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "This player was already added", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Log in to add favorites", Toast.LENGTH_SHORT).show();
        }
    }
    public void hideSystemUI() {
        View decorView = this.getWindow().getDecorView();
        this.getSupportActionBar().hide();
        int uiOptions = decorView.getSystemUiVisibility();
        int newUiOptions = uiOptions;
        newUiOptions |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
        newUiOptions |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions |= View.SYSTEM_UI_FLAG_IMMERSIVE;
        newUiOptions |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(newUiOptions);
    }
}