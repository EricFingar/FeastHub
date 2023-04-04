package com.example.feasthub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class recipeGVAdapter extends ArrayAdapter<recipeModel>{
    public recipeGVAdapter(@NonNull Context context, ArrayList<recipeModel> recipeModelArrayList) {
        super(context, 0, recipeModelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.card_item, parent, false);
        }

        recipeModel recipeModel = getItem(position);
        TextView recipeTV = listitemView.findViewById(R.id.idRecipe);
        ImageView recipeIV = listitemView.findViewById(R.id.idImgRecip);

        recipeTV.setText(recipeModel.getRecipe_name());
        recipeIV.setImageResource(recipeModel.getImgid());
        return listitemView;
    }
}
