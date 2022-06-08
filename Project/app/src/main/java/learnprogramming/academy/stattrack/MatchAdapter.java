package learnprogramming.academy.stattrack;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class MatchAdapter extends BaseAdapter {
    private java.util.List<Match> matchList;
    private Context context;

    @Override
    public int getCount() {
        return matchList.size();
    }

    @Override
    public Object getItem(int i) {
        return matchList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return matchList.get(i).getId();
    }

    public MatchAdapter(List<Match> carList, Context context) {
        this.matchList = carList;
        this.context = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.activity_match_view, viewGroup, false);

        Match match = matchList.get(position);

        ImageView championIcon = view.findViewById(R.id.championId);
        TextView matchResult = view.findViewById(R.id.matchResultText);
        TextView killsDeathsAssists = view.findViewById(R.id.killsDeathsAssistsText);
        TextView kda = view.findViewById(R.id.kdaText);
        TextView controlWardsPlaced = view.findViewById(R.id.controlWardsPlacedText);
        TextView wardsKilled = view.findViewById(R.id.wardsKilledText);
        TextView wardsPlaced = view.findViewById(R.id.wardsPlacedText);
        TextView damageDealt = view.findViewById(R.id.totalDamageDealtText);
        TextView damageTaken = view.findViewById(R.id.totalDamageTakenText);
        TextView minionsKilled = view.findViewById(R.id.minionsKilledText);

        String imageName = match.getChampionIcon().toLowerCase();
        int imageId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());

        championIcon.setImageResource(imageId);
        matchResult.append(match.getMatchResult());
        killsDeathsAssists.append(match.getKillsDeathsAssists());
        kda.append(Double.toString(match.getKda()));
        controlWardsPlaced.append(Integer.toString(match.getControlWardsPlaced()));
        wardsKilled.append(Integer.toString(match.getWardsKilled()));
        wardsPlaced.append(Integer.toString(match.getWardsPlaced()));
        damageDealt.append(Integer.toString(match.getDamageDealt()));
        damageTaken.append(Integer.toString(match.getDamageTaken()));
        minionsKilled.append(Integer.toString(match.getMinionsKilled()));

        return view;
    }

}