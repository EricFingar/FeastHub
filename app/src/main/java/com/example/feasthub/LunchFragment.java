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
 * A fragment for displaying lunch recipes in a grid view.
 */
public class LunchFragment extends Fragment {

    private View view;
    private FirebaseFirestore db = com.google.firebase.firestore.FirebaseFirestore.getInstance();

    private String username;

    /**
     *Inflate the layout for this fragment, and retrieve the username from arguments.
     * @param inflater The LayoutInflater object that can be used to inflate views.
     * @param container The parent view that this fragment's UI should be attached to.
     * @param savedInstanceState This fragment's previously saved state, if any.
     * @return The inflated view for this fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_lunch, container, false);
        String[] key = getArguments().getStringArray("Key");
        username = key[0];

        backButton();
        getLunchRecipeCard();
        return view;
    }

    /**
     * Sets an onClickListener for the back button and replaces the current fragment with the HomeFragment.
     */
    private void backButton(){
        ImageButton back_btn = (ImageButton) view.findViewById(R.id.lunchBackButton);
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
     * Populates the GridView with recipe cards for each lunch recipe in the database.
     */
    private void getLunchRecipeCard(){
        GridView recipeCards = (GridView) view.findViewById(R.id.lunchGrid);
        ArrayList<recipeModel> recipeModelArrayList = new ArrayList<recipeModel>();
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
                String[] array = {recipeModelArrayList.get(i).getRecipe_name().toString(), "Lunch", "False" ,"False","False", username};
                args.putStringArray("RecipeName", array);
                recipe.setArguments(args);

                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, recipe);
                fr.commit();
            }
        });
    }


}