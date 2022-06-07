package learnprogramming.academy.stattrack;

import androidx.appcompat.app.AppCompatActivity;

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
        //Button buttonSearch = findViewById(R.id.search_player_button);
        resultsFromApi = findViewById(R.id.results_from_api);
        apiRequestQueue = Volley.newRequestQueue(this); // moguce da ce se ovo morati promijeniti kad se promjeni activity

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
    public void funkcija(View view){
        Toast.makeText(MainActivity.this, "PORUKA", Toast.LENGTH_LONG).show();
    }
    public void jsonParse(View view){
        //ovdje radi
        //Toast.makeText(MainActivity.this, "PORUKA", Toast.LENGTH_LONG).show();
        String url = "https://stattrack.me/rest/summonersMobileAPI/" + summonerNameInput.getText().toString() + "/" + serverSpinner.getSelectedItem().toString();
        //String url = "https://" + serverSpinner.getSelectedItem().toString() +
                //".api.riotgames.com/lol/summoner/v4/summoners/by-name/" + summonerNameInput.getText().toString();

        //if it's a JSON array, we will need a JsonArrayRequest
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject responseJSON) {
                /*
                JSONArray jsonArray = null; //ovo moze ako ima vec podijeljen JSON object into arrays
                try {
                    jsonArray = responseJSON.getJSONArray("participants");
                    //extract objects from array (lol). Ovo bi moglo za participants. matches, etc,
                    // ali samo za summoner call nije potrebno
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject participants = jsonArray.getJSONObject(i);

                        String firstName = participants.getString("id");
                        String secondValue = participants.getString("secondValueIdk");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
*/

                //transform object into array
                Toast.makeText(MainActivity.this, "PORUKA", Toast.LENGTH_LONG).show();
                try {

                    //Toast toast = Toast.makeText(MainActivity.this, "PORUKA", Toast.LENGTH_LONG);
                    //String id = responseJSON.getString("id");
                    String name = responseJSON.getString("name");
                    Log.d("name", name);
                    String puuid = responseJSON.getString("puuid");
                    Log.d("puuid", puuid);
                    resultsFromApi.setText(name + ", " + puuid + "\n\n");
                    //resultsFromApi.append(name + ", " + puuid + "\n\n");
                } catch (JSONException e) {
                    Log.e("ERROR", "onResponse: ERROR");
                    e.printStackTrace();
                }
                //JSONArray jsonArray = new JSONArray();
                //jsonArray.put(responseJSON);
                //String id = jsonArray.getString("id");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        apiRequestQueue.add(request);

    }
}