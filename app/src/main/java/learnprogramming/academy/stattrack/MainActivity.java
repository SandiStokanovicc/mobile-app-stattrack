package learnprogramming.academy.stattrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    public EditText summonerNameInput;
    public Spinner serverSpinner;
    public AppCompatButton loginButton, logoutButton, addFavoriteButton, getFavoritesButton;
    private String username;
    private String password;
    private long userId;
    private static final int NOTIFICATION_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //2 lines below are for purging the DB entries
        //UserDatabase.getInstance(this).userDao().nukeTable();
        //UserDatabase.getInstance(this).favoritePlayerDao().nukeTable();

        loginButton = findViewById(R.id.loginButton);
        logoutButton = findViewById(R.id.logoutButton);
        addFavoriteButton = findViewById(R.id.add_favorite_button);
        getFavoritesButton = findViewById(R.id.favorites_button);


        summonerNameInput = findViewById(R.id.summoner_name_input);
        serverSpinner = findViewById(R.id.server_spinner);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            password = extras.getString("password");
            username = extras.getString("username");
            userId = extras.getLong("user_id");
            Log.d("onCreate: BUTTONVISIBILITY", Integer.toString(extras.getInt("buttonVisibility")));
            loginButton.setVisibility(extras.getInt("loginButtonVisibility"));
            logoutButton.setVisibility(extras.getInt("logoutButtonVisibility"));
            addFavoriteButton.setVisibility(extras.getInt("addFavoriteButtonVisibility"));
            getFavoritesButton.setVisibility(extras.getInt("getFavoritesButtonVisibility"));
            //loginButton.set


//your other codes
        }
        SharedPreferences prefs = getSharedPreferences("name", MODE_PRIVATE);
        boolean isLoggedIn= prefs.getBoolean("isLoggedIn", false);

        if(isLoggedIn) {
            logoutButton.setVisibility(AppCompatButton.VISIBLE);
            addFavoriteButton.setVisibility(AppCompatButton.VISIBLE);
            getFavoritesButton.setVisibility(AppCompatButton.VISIBLE);
        }else {
            logoutButton.setVisibility(AppCompatButton.GONE);
            addFavoriteButton.setVisibility(AppCompatButton.GONE);
            getFavoritesButton.setVisibility(AppCompatButton.GONE);
        }

        //loginButton.setVisibility(AppCompatButton.INVISIBLE);


        //https://docs.oracle.com/javase/7/docs/api/java/util/Calendar.html
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        if(Calendar.getInstance().after(calendar)){
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            //calendar.add(Calendar.SECOND, 10);
        }

        Intent intent = new Intent(MainActivity.this, NotificationBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), NOTIFICATION_ID, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) MainActivity.this.getSystemService(MainActivity.this.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        ArrayAdapter<CharSequence> spinnerAdapter=ArrayAdapter.createFromResource(this, R.array.servers,
                android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        serverSpinner.setAdapter(spinnerAdapter);

    }

    public void showFavorites(View view){
        if(username != null){
            Intent intent = new Intent(MainActivity.this, ShowFavoritePlayers.class);
            intent.putExtra("username", username);
            intent.putExtra("password", password);
            startActivity(intent);
        }
        else{
            Toast.makeText(MainActivity.this, "Log in to see favorite accounts", Toast.LENGTH_LONG).show();
        }

    }

    public void addFavorite(View view){
        long parentUserId = UserDatabase.getInstance(this).userDao().login(username, password).getUser_id();
        FavoritePlayer favoritePlayer = new FavoritePlayer(summonerNameInput.getText().toString(),
                serverSpinner.getSelectedItem().toString(),parentUserId);
        if(!UserDatabase.getInstance(this).favoritePlayerDao()
                .exists(favoritePlayer.getSummonerName(), favoritePlayer.getServer(), userId)) {
            UserDatabase.getInstance(this).favoritePlayerDao().addFavoritePlayer(favoritePlayer);
        }
        else{
            Toast.makeText(this, "This player was already added", Toast.LENGTH_SHORT).show();
        }
    }

    public void jsonParse(View view){
        //ovdje radi
        String url = "https://stattrack.me/rest/summonersMobileAPI/" + summonerNameInput.getText().toString() + "/" + serverSpinner.getSelectedItem().toString();
        //String url = "https://" + serverSpinner.getSelectedItem().toString() +
                //".api.riotgames.com/lol/summoner/v4/summoners/by-name/" + summonerNameInput.getText().toString();

        Intent intent = new Intent(MainActivity.this, ShowMatches.class);
        intent.putExtra("URL", url);
        startActivity(intent);
    }

    public void goToLogin(View view){
        Intent intent = new Intent(MainActivity.this, RegisterLoginFragActivity.class);
        startActivity(intent);
    }

    public void logout(View view){
        SharedPreferences.Editor editor = getSharedPreferences("name", MODE_PRIVATE).edit();
        editor.putString("password", "");
        editor.putString("username", "");
        editor.putBoolean("isLoggedIn", false);
        editor.putLong("id", -1);
        editor.apply();

        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        intent.putExtra("finish", true);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    /*
    public void goToRegister(View view){
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
    */
}