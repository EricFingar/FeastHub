package com.example.feasthub;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A fragment that displays the user's favorite recipes.
 */
public class FavoriteFragment extends Fragment {

    private View view;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String username;
    /**
     *Inflates the layout for this fragment and sets the username value to the current user's username.
     * @param inflater the LayoutInflater object that can be used to inflate any views in the fragment
     * @param container the parent view that the fragment UI should be attached to
     * @param savedInstanceState saved state of the fragment
     * @return the view for the fragment's UI
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_favorite, container, false);
        String[] key = getArguments().getStringArray("Key");
        username = key[0];
        backButton();
        getFavoriteRecipeCard();
        return view;
    }


    /**
     * Sets up the back button to return to the home screen.
     */
    private void backButton(){
        ImageButton back_btn = (ImageButton) view.findViewById(R.id.searchBackButton);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                HomeFragment home = new HomeFragment();
                String[] array = {username};
                args.putStringArray("Key", array);
                home.setArguments(args);
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, home);
                fr.commit();
            }
        });
    }

    /**
     * Retrieves the user's favorite recipes from the database and displays them in a GridView.
     */
    private void getFavoriteRecipeCard(){
        GridView recipeCards = (GridView) view.findViewById(R.id.searchRecipeGrid);
        ArrayList<recipeModel> recipeModelArrayList = new ArrayList<recipeModel>();

        db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Favorites").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                //List<String> ids = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id = document.getId();
                        recipeModelArrayList.add(new recipeModel(id, R.drawable.defaultfood));
                    }
                }
                recipeGVAdapter adapter = new recipeGVAdapter(view.getContext(), recipeModelArrayList);
                recipeCards.setAdapter(adapter);
            }
        });

        recipeCards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view1, int i, long l) {

                recipeDetailsFragment recipe = new recipeDetailsFragment();
                Bundle args = new Bundle();
                String[] array = {recipeModelArrayList.get(i).getRecipe_name().toString(),"Favorites", "False", "False", "True",username};
                args.putStringArray("RecipeName", array);
                recipe.setArguments(args);

                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, recipe);
                fr.commit();
            }
        });

    }
}