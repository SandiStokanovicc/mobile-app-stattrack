package learnprogramming.academy.stattrack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

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
        //this is useless, but needs to be implemented
        return -1;
        //return favoritePlayersList.get(i).favoritePlayer.getParentEmail();
    }

    public FavoritePlayerAdapter(List<FavoritePlayerAndUser> favoritePlayersList, Context context) {
        this.favoritePlayersList = favoritePlayersList;
        this.context = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.activity_favorite_player_view, viewGroup, false);

        ImageView profileIcon = view.findViewById(R.id.profileIconId);

        FavoritePlayer favoritePlayer = favoritePlayersList.get(position).favoritePlayer;
        favoritePlayer.getSummonerName();
        TextView summonerNameText = view.findViewById(R.id.summonerNameText);
        TextView serverText = view.findViewById(R.id.serverText);

        String profileIconId = favoritePlayer.getProfileIconId();
        int imageId = context.getResources().getIdentifier("p" + profileIconId, "drawable", context.getPackageName());
        //profileIcon.setTag(profileIconName);
        profileIcon.setImageResource(imageId);

        summonerNameText.setText(favoritePlayer.getSummonerName());
        serverText.setText(favoritePlayer.getServer());

        return view;
    }


}
