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

/**
 * The recipeGVAdapter class is an ArrayAdapter for displaying recipeModel objects in a GridView.
 */
public class recipeGVAdapter extends ArrayAdapter<recipeModel>{
    /**
     * Constructor for the recipeGVAdapter class.
     * @param context The current context.
     * @param recipeModelArrayList The ArrayList of recipeModel objects to be displayed.
     */
    public recipeGVAdapter(@NonNull Context context, ArrayList<recipeModel> recipeModelArrayList) {
        super(context, 0, recipeModelArrayList);
    }
    /**
     *Get a View that displays the data at the specified position in the data set.
     * @param position The position of the item within the adapter's data set.
     * @param convertView The old view to reuse, if possible.
     * @param parent The parent that this view will eventually be attached to.
     * @return A View corresponding to the data at the specified position.
     */
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
