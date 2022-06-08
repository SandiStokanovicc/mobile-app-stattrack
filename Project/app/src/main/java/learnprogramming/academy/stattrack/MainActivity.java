package learnprogramming.academy.stattrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class MainActivity extends AppCompatActivity {
    public EditText summonerNameInput;
    public Spinner serverSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        summonerNameInput = findViewById(R.id.summoner_name_input);
        serverSpinner = findViewById(R.id.server_spinner);
        //Button buttonSearch = findViewById(R.id.search_player_button);
        //resultsFromApi = findViewById(R.id.results_from_api);

        /*
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jsonParse();
            }
        });
        */
        ArrayAdapter<CharSequence> spinnerAdapter=ArrayAdapter.createFromResource(this, R.array.servers,
                android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        serverSpinner.setAdapter(spinnerAdapter);
    }
    public void jsonParse(View view){
        //ovdje radi
        //Toast.makeText(MainActivity.this, "PORUKA", Toast.LENGTH_LONG).show();
        String url = "https://stattrack.me/rest/summonersMobileAPI/" + summonerNameInput.getText().toString() + "/" + serverSpinner.getSelectedItem().toString();
        //String url = "https://" + serverSpinner.getSelectedItem().toString() +
                //".api.riotgames.com/lol/summoner/v4/summoners/by-name/" + summonerNameInput.getText().toString();

        Intent intent = new Intent(MainActivity.this, ShowMatches.class);
        intent.putExtra("URL", url);
        startActivity(intent);

    }
}