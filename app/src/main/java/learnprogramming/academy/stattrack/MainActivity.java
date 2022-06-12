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
    public AppCompatButton loginButton, logoutButton, getFavoritesButton;
    private LoginInstanceDao loginInstanceDao;
    private LoginInstance loginInstance;
    private String username;
    private String password;
    private String userEmail;
    private static final int NOTIFICATION_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setBackgroundDrawableResource(R.drawable.background);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //2 lines below are for purging the DB entries
        //UserDatabase.getInstance(this).userDao().nukeTable();
        //UserDatabase.getInstance(this).favoritePlayerDao().nukeTable();

        loginButton = findViewById(R.id.loginButton);
        logoutButton = findViewById(R.id.logoutButton);

        getFavoritesButton = findViewById(R.id.favorites_button);


        summonerNameInput = findViewById(R.id.summoner_name_input);
        serverSpinner = findViewById(R.id.server_spinner);

        loginInstanceDao = UserDatabase.getInstance(this).loginInstanceDao();
        loginInstance = loginInstanceDao.checkIfLoggedIn();




        if(loginInstance != null) {
            username = loginInstance.getUsername();
            password = loginInstance.getPassword();
            userEmail = loginInstance.getEmail();

            Log.d("MainActivitylol: ", loginInstance.getPassword());
            Log.d("MainActivitylol: ", loginInstance.getUsername());
            Log.d("MainActivitylol: ", loginInstance.getEmail());

            logoutButton.setVisibility(AppCompatButton.VISIBLE);
            loginButton.setVisibility(AppCompatButton.GONE);
            getFavoritesButton.setVisibility(AppCompatButton.VISIBLE);
        }else {
            logoutButton.setVisibility(AppCompatButton.GONE);
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

    public void getMatchHistory(View view){
        //ovdje radi

        //String url = "https://" + serverSpinner.getSelectedItem().toString() +
                //".api.riotgames.com/lol/summoner/v4/summoners/by-name/" + summonerNameInput.getText().toString();

        if(!userEmail.isEmpty()) {
            Intent intent = new Intent(MainActivity.this, ShowMatches.class);
            intent.putExtra("summonerName", summonerNameInput.getText().toString());
            intent.putExtra("server", serverSpinner.getSelectedItem().toString());
            intent.putExtra("userEmail", userEmail);
            //intent.putExtra("URL", url);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(MainActivity.this, ShowMatches.class);
            intent.putExtra("summonerName", summonerNameInput.getText().toString());
            intent.putExtra("server", serverSpinner.getSelectedItem().toString());
            intent.putExtra("userEmail", "");
            //intent.putExtra("URL", url);
            startActivity(intent);
        }
    }

    public void goToLogin(View view){
        Intent intent = new Intent(MainActivity.this, RegisterLoginFragActivity.class);
        startActivity(intent);
    }

    public void logout(View view){
        loginInstanceDao.logout();

        Intent intent = new Intent(MainActivity.this, MainActivity.class);
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