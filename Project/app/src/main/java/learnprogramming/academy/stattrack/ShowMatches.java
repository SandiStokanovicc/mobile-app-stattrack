package learnprogramming.academy.stattrack;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;

public class ShowMatches extends AppCompatActivity {
    private RequestQueue apiRequestQueue;
    private TextView resultsFromApi;
    private List<Match> matchList;
    public ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_matches);

        matchList = new ArrayList<>();
        listView = findViewById(R.id.match_listview);

        if (savedInstanceState != null){
            matchList = savedInstanceState.getParcelableArrayList("match_list");
            setUpAdapter(matchList);
        }
        else {

            //Toast.makeText(this, "LOADING MATCHES!!!", Toast.LENGTH_LONG).show();


            apiRequestQueue = Volley.newRequestQueue(this); // moguce da ce se ovo morati promijeniti kad se promjeni activity

            Bundle extras = getIntent().getExtras();


            Log.d(extras.getString("URL"), "onCreate: ");
            Toast.makeText(this, "LOADING MATCHES!", Toast.LENGTH_LONG).show();
            jsonParse(extras.getString("URL"));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putParcelableArrayList("match_list", (ArrayList<? extends Parcelable>) matchList);
    }

    public void setUpAdapter(List<Match> matchList){

        MatchAdapter matchAdapter = new MatchAdapter(matchList, this);
        listView.setAdapter(matchAdapter);
    }

    public void jsonParse(String url){
        //if it's a JSON array, we will need a JsonArrayRequest
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject responseJSON) {

                JSONArray jsonArray = null;
                try {
                    jsonArray = responseJSON.getJSONArray("matches");

                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject matchInfo = jsonArray.getJSONObject(i);

                        ArrayList<String> items = new ArrayList<>();
                        items.add("a" + matchInfo.getString("item0"));
                        items.add("a" + matchInfo.getString("item1"));
                        items.add("a" + matchInfo.getString("item2"));
                        items.add("a" + matchInfo.getString("item3"));
                        items.add("a" + matchInfo.getString("item4"));
                        items.add("a" + matchInfo.getString("item5"));
                        items.add("a" + matchInfo.getString("item6"));

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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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