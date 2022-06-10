package learnprogramming.academy.stattrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    public EditText summonerNameInput;
    public Spinner serverSpinner;
    public AppCompatButton loginButton, logoutButton;
    private String username;
    private String password;
    private static final int NOTIFICATION_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        loginButton = findViewById(R.id.loginButton);
        logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setVisibility(AppCompatButton.GONE);
        summonerNameInput = findViewById(R.id.summoner_name_input);
        serverSpinner = findViewById(R.id.server_spinner);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            password = extras.getString("password");
            username = extras.getString("username");
            Log.d("onCreate: BUTTONVISIBILITY", Integer.toString(extras.getInt("buttonVisibility")));
            loginButton.setVisibility(extras.getInt("loginButtonVisibility"));
            logoutButton.setVisibility(extras.getInt("logoutButtonVisibility"));
            //loginButton.set
        }

        //loginButton.setVisibility(AppCompatButton.INVISIBLE);


        //https://docs.oracle.com/javase/7/docs/api/java/util/Calendar.html
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);

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
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
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