package com.example.feasthub;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * MyRecipesFragment class extends Fragment and shows the list of the recipes created by the user.
 * It gets the recipes of breakfast, lunch, dinner and snacks from Firestore and displays in GridView.
 * When a recipe is clicked, it opens the recipe details fragment.
 */
public class MyRecipesFragment extends Fragment {

    private View view;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String username;

    /**
     *onCreateView is called when the fragment is created.
     * It inflates the fragment_my_recipes layout and sets up the GridView with the recipes.
     * It sets up a click listener on the back button which takes the user back to the Account Fragment
     * @param inflater Layout inflater
     * @param container View group container
     * @param savedInstanceState Saved instance state
     * @return Inflated view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_recipes, container, false);
        String[] key = getArguments().getStringArray("Key");

        username = key[0];
        backButton();
        getMyRecipeCard();
        return view;
    }

    /**
     * backButton is a private method that sets up a click listener on the back button.
     * When the button is clicked, it takes the user back to the Account Fragment.
     */
    private void backButton(){
        ImageButton back_btn = (ImageButton) view.findViewById(R.id.recentBackButton);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                AccountFragment account = new AccountFragment();
                String[] array = {username};
                args.putStringArray("Key", array);
                account.setArguments(args);
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, account);
                fr.commit();
            }
        });
    }

    /**
     * getMyRecipeCard is a private method that gets the recipes from Firestore and sets up the recipe cards in the GridView.
     * It gets the recipes of breakfast, lunch, dinner and snacks and sets up a recipeGVAdapter for each of them.
     * When a recipe card is clicked, it opens the recipe details fragment.
     */
    private void getMyRecipeCard(){
        GridView recipeCards = (GridView) view.findViewById(R.id.myRecipeGrid);
        ArrayList<recipeModel> recipeModelArrayList = new ArrayList<recipeModel>();
        db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Breakfast").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

        /**
         * Sets an OnItemClickListener to the recipeCards GridView that listens for when an item in the GridView is clicked.
         * When an item is clicked, a recipeDetailsFragment is created and populated with information about the clicked recipe
         */
        recipeCards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view1, int i, long l) {

                recipeDetailsFragment recipe = new recipeDetailsFragment();
                Bundle args = new Bundle();
                String[] array = {recipeModelArrayList.get(i).getRecipe_name().toString(), "Breakfast","True","False", "False", username};
                args.putStringArray("RecipeName", array);
                recipe.setArguments(args);

                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, recipe);
                fr.commit();
            }
        });

        db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Lunch").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                String[] array = {recipeModelArrayList.get(i).getRecipe_name().toString(), "Lunch","True","False", "False", username};
                args.putStringArray("RecipeName", array);
                recipe.setArguments(args);

                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, recipe);
                fr.commit();
            }
        });

        db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Dinner").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                String[] array = {recipeModelArrayList.get(i).getRecipe_name().toString(), "Dinner","True","False", "False", username};
                args.putStringArray("RecipeName", array);
                recipe.setArguments(args);

                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, recipe);
                fr.commit();
            }
        });

        db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Snacks").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                String[] array = {recipeModelArrayList.get(i).getRecipe_name().toString(), "Snacks","True","False", "False", username};
                args.putStringArray("RecipeName", array);
                recipe.setArguments(args);

                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, recipe);
                fr.commit();
            }
        });

    }
}