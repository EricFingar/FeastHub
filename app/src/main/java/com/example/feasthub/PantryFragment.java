package com.example.feasthub;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a fragment that displays a list of recipes for each category
 * (fruits, grains, vegetables, protein) that are stored in the Firestore database.
 * The class extends Fragment.
 */
public class PantryFragment extends Fragment {

    private View view;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String username;


    /**
     * Creates and returns the view hierarchy associated with the fragment.
     * @param inflater Layout inflater
     * @param container View group container
     * @param savedInstanceState Saved instance state
     * @return Inflated view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_pantry, container, false);
        String[] key = getArguments().getStringArray("Key");
        username = key[0];

        getFruitRecipe();
        getGrainRecipe();
        getDairyRecipe();
        getProteinRecipe();
        getVegetablesRecipe();
        return view;
    }


    /**
     * Retrieves the recipes under the category "Fruits" from the Firestore database, displays them in a list view,
     * and sets an item click listener that will open a recipe details fragment when an item is clicked.
     * all other categories follow
     */
    private void getFruitRecipe() {
        ListView fruitListView = (ListView) view.findViewById(R.id.fruitList);
        List<String> ids = new ArrayList<>();
        db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Fruits").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id = document.getId();
                        ids.add(id);
                    }
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, ids);
                fruitListView.setAdapter(arrayAdapter);
            }
        });

        fruitListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view1, int i, long l) {

                recipeDetailsFragment recipe = new recipeDetailsFragment();
                Bundle args = new Bundle();
                String[] array = {ids.get(i).toString(), "Fruits", "False","False", "False", username};
                args.putStringArray("RecipeName", array);
                recipe.setArguments(args);

                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, recipe);
                fr.commit();
            }
        });
    }

    private void getGrainRecipe(){
        ListView grainListView = (ListView) view.findViewById(R.id.grainList);
        List<String> ids = new ArrayList<>();
        db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Grains").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id = document.getId();
                        ids.add(id);
                    }
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, ids);
                grainListView.setAdapter(arrayAdapter);
            }
        });

        grainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view1, int i, long l) {

                recipeDetailsFragment recipe = new recipeDetailsFragment();
                Bundle args = new Bundle();
                String[] array = {ids.get(i).toString(), "Grains", "False","False", "False", username};
                args.putStringArray("RecipeName", array);
                recipe.setArguments(args);

                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, recipe);
                fr.commit();
            }
        });
    }

    private void getVegetablesRecipe(){
        ListView vegetablesListView = (ListView) view.findViewById(R.id.vegetablesList);
        List<String> ids = new ArrayList<>();
        db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Vegetables").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id = document.getId();
                        ids.add(id);
                    }
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, ids);
                vegetablesListView.setAdapter(arrayAdapter);
            }
        });

        vegetablesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view1, int i, long l) {

                recipeDetailsFragment recipe = new recipeDetailsFragment();
                Bundle args = new Bundle();
                String[] array = {ids.get(i).toString(), "Vegetables", "False","False", "False", username};
                args.putStringArray("RecipeName", array);
                recipe.setArguments(args);

                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, recipe);
                fr.commit();
            }
        });
    }

    private void getProteinRecipe(){
        ListView proteinListView = (ListView) view.findViewById(R.id.proteinList);
        List<String> ids = new ArrayList<>();
        db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Protein").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id = document.getId();
                        ids.add(id);
                    }
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, ids);
                proteinListView.setAdapter(arrayAdapter);
            }
        });

        proteinListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view1, int i, long l) {

                recipeDetailsFragment recipe = new recipeDetailsFragment();
                Bundle args = new Bundle();
                String[] array = {ids.get(i).toString(), "Protein", "False","False", "False", username};
                args.putStringArray("RecipeName", array);
                recipe.setArguments(args);

                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, recipe);
                fr.commit();
            }
        });

    }

    private void getDairyRecipe(){
        ListView dairyListView = (ListView) view.findViewById(R.id.dairyList);
        List<String> ids = new ArrayList<>();
        db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Dairy").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id = document.getId();
                        ids.add(id);
                    }
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, ids);
                dairyListView.setAdapter(arrayAdapter);
            }
        });

        dairyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view1, int i, long l) {

                recipeDetailsFragment recipe = new recipeDetailsFragment();
                Bundle args = new Bundle();
                String[] array = {ids.get(i).toString(), "Dairy", "False","False", "False", username};
                args.putStringArray("RecipeName", array);
                recipe.setArguments(args);

                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, recipe);
                fr.commit();
            }
        });
    }
}
