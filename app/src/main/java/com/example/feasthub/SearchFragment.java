package com.example.feasthub;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A Fragment subclass for searching recipes by name.
 */
public class SearchFragment extends Fragment {

    private View view;

    private String searchWord;

    private String username;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    /**
     *Called to have the fragment instantiate its user interface view.
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return Return the View for the fragment's UI.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);

        String[] key = getArguments().getStringArray("RecipeName");
        searchWord = key[0];
        username = key[1];

        backButton();
        //search();
        getSearchRecipeCard();
        return view;
    }

    /*private void search(){
        EditText searchInput = (EditText) view.findViewById(R.id.searchbr);
        String searchItem = searchInput.getText().toString();

        searchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if((keyEvent != null && (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER))){
                    getSearchRecipeCard(searchItem);
                }
                return false;
            }
        });

    }*/

    /**
     * Sets a listener to handle clicks on the back button and to navigate to the HomeFragment.
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
     * Queries Firestore database to get recipe names that contain the search keyword and display them in a GridView.
     */
    private void getSearchRecipeCard(){
        GridView recipeCards = (GridView) view.findViewById(R.id.searchRecipeGrid);
        recipeCards.refreshDrawableState();
        ArrayList<recipeModel> recipeModelArrayList = new ArrayList<recipeModel>();
        recipeModelArrayList.clear();


        db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Breakfast").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                //List<String> ids = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if(document.exists()) {
                            if(document.getId().contains(searchWord)) {
                                String id = document.getId();
                                recipeModelArrayList.add(new recipeModel(id, R.drawable.defaultfood));
                            }
                        }
                    }
                }
                recipeGVAdapter adapter = new recipeGVAdapter(view.getContext(), recipeModelArrayList);
                recipeCards.setAdapter(adapter);
            }
        });

        db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Lunch").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                //List<String> ids = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if(document.exists()) {
                            if(document.getId().contains(searchWord)) {
                                String id = document.getId();
                                recipeModelArrayList.add(new recipeModel(id, R.drawable.defaultfood));
                            }
                        }
                    }
                }
                recipeGVAdapter adapter = new recipeGVAdapter(view.getContext(), recipeModelArrayList);
                recipeCards.setAdapter(adapter);
            }
        });

        db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Dinner").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                //List<String> ids = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if(document.exists()) {
                            if(document.getId().contains(searchWord)) {
                                String id = document.getId();
                                recipeModelArrayList.add(new recipeModel(id, R.drawable.defaultfood));
                            }
                        }
                    }
                }
                recipeGVAdapter adapter = new recipeGVAdapter(view.getContext(), recipeModelArrayList);
                recipeCards.setAdapter(adapter);
            }
        });

        db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Snacks").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                //List<String> ids = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if(document.exists()) {
                            if(document.getId().contains(searchWord)) {
                                String id = document.getId();
                                recipeModelArrayList.add(new recipeModel(id, R.drawable.defaultfood));
                            }
                        }
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
                String[] array = {recipeModelArrayList.get(i).getRecipe_name().toString(),"Breakfast", "False", "False", "True", username};
                args.putStringArray("RecipeName", array);
                recipe.setArguments(args);

                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, recipe);
                fr.commit();
            }
        });

    }
}