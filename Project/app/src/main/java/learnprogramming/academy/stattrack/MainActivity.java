package learnprogramming.academy.stattrack;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private RequestQueue apiRequestQueue;
    private TextView resultsFromApi;
    public EditText summonerNameInput;
    public Spinner serverSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        summonerNameInput = findViewById(R.id.summoner_name_input);
        serverSpinner = findViewById(R.id.server_spinner);
        Button buttonSearch = findViewById(R.id.search_player_button);
        resultsFromApi = findViewById(R.id.results_from_api);
        apiRequestQueue = Volley.newRequestQueue(this); // moguce da ce se ovo morati promijeniti kad se promjeni activity

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jsonParse();

            }
        });

        ArrayAdapter<CharSequence> spinnerAdapter=ArrayAdapter.createFromResource(this, R.array.servers,
                android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        serverSpinner.setAdapter(spinnerAdapter);
    }
    private void jsonParse(){

        String url = "https://" + serverSpinner.getSelectedItem().toString() +
                ".api.riotgames.com/lol/summoner/v4/summoners/by-name/" + summonerNameInput.getText().toString();

        //if it's a JSON array, we will need a JsonArrayRequest
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject responseJSON) {
                JSONArray jsonArray = responseJSON.getJSONArray("participants"); //ovo moze ako ima vec podijeljen JSON object into arrays

                //transform object into array
                //JSONArray jsonArray = new JSONArray();
                jsonArray.put(responseJSON);

                //extract objects from array (lol). Ovo bi moglo za participants. matches, etc,
                // ali samo za summoner call nije potrebno

                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject participants = jsonArray.getJSONObject(i);

                    String firstName = participants.getString("id");
                    String secondValue = participants.getString("secondValueIdk");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        /*
        JsonObjectRequest request = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })*/
    }
}