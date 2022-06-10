package learnprogramming.academy.stattrack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class FavoritePlayerAdapter extends BaseAdapter {
    private java.util.List<FavoritePlayerAndUser> favoritePlayersList;
    private Context context;

    @Override
    public int getCount() {
        return favoritePlayersList.size();
    }

    @Override
    public Object getItem(int i) {
        return favoritePlayersList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return favoritePlayersList.get(i).favoritePlayer.getId();
    }

    public FavoritePlayerAdapter(List<FavoritePlayerAndUser> favoritePlayersList, Context context) {
        this.favoritePlayersList = favoritePlayersList;
        this.context = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.activity_favorite_player_view, viewGroup, false);

        FavoritePlayer favoritePlayer = favoritePlayersList.get(position).favoritePlayer;
        TextView summonerNameText = view.findViewById(R.id.summonerNameText);
        TextView serverText = view.findViewById(R.id.serverText);

        summonerNameText.setText(favoritePlayer.getSummonerName());
        serverText.setText(favoritePlayer.getServer());

        return view;
    }
}
