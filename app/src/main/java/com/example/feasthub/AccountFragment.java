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
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**

 This class represents the account fragment that is displayed when the user navigates to their account page.

 It contains buttons to navigate to the user's recipes and edit their profile, a search bar to search for recipes,

 and displays a grid of the user's saved recipes.
 */
public class AccountFragment extends Fragment {

    private View view;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String username;


    /**
     * This method inflates the account fragment layout, initializes the view and sets up the buttons,
     * search bar, and recipe grid to display the user's saved recipes.
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment
     * @param container The parent view that the fragment UI should be attached to
     * @param savedInstanceState This fragment's previously saved state
     * @return The inflated view for the account fragment UI
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_account, container, false);

        String[] key = getArguments().getStringArray("Key");
        username = key[0];
        myRecipesButton();
        editAccountButton();
        getMyRecipeCard();
        logout();
        search();
        return view;
    }

    /**
     * This method sets up the log out button to return the user to the login page.
     */
    private void logout(){
        Button logout = (Button) view.findViewById(R.id.logOutButton);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, new LoginFragment());
                fr.commit();
            }
        });
    }

    /**
     * This method sets up the "My Recipes" button to navigate to the user's saved recipes page when clicked.
     */
    private void myRecipesButton(){
        ImageButton myRecipes_btn = (ImageButton) view.findViewById(R.id.myRecipesButton);
        myRecipes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                MyRecipesFragment myRecipes = new MyRecipesFragment();
                String[] array = {username};
                args.putStringArray("Key", array);
                myRecipes.setArguments(args);
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, myRecipes);
                fr.commit();
            }
        });
    }
    /**
     * This method sets up the "Edit Account" button to navigate to the user's profile page when clicked.
     */
    private void editAccountButton(){
        Button editAccount_btn = (Button) view.findViewById(R.id.accountButton);
        editAccount_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                ProfileFragment profile = new ProfileFragment();
                String[] array = {username};
                args.putStringArray("Key", array);
                profile.setArguments(args);
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, profile);
                fr.commit();
            }
        });
    }

    /**
     * This method sets up the search bar to search for recipes based on user input and navigate to the search results page when enter is clicked.
     */
    private void search(){
        EditText searchInput = (EditText) view.findViewById(R.id.searchbr);

        searchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if((keyEvent != null && (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER))){
                    String searchItem = searchInput.getText().toString();
                    SearchFragment recipe = new SearchFragment();
                    Bundle args = new Bundle();
                    String[] array = {searchItem, username};
                    args.putStringArray("RecipeName", array);
                    recipe.setArguments(args);

                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.frame_layout, recipe);
                    fr.commit();
                }
                return false;
            }
        });

    }

    /**
     * This method sets up the recipe grid view for the user's personal account page
     * by populating it with recipe cards for each of the user's saved recipes in different categories.
     */
    private void getMyRecipeCard(){
        GridView recipeCards = (GridView) view.findViewById(R.id.accountMyRecipeGrid);
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

        recipeCards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view1, int i, long l) {

                recipeDetailsFragment recipe = new recipeDetailsFragment();
                Bundle args = new Bundle();
                String[] array = {recipeModelArrayList.get(i).getRecipe_name().toString(), "Breakfast","True","False", "False", "True"};
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
