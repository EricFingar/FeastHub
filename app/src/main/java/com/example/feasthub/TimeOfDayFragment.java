package com.example.feasthub;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TimeOfDayFragment extends Fragment {

    private View view;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    Calendar cal = Calendar.getInstance();

    private String username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_time_of_day, container, false);
        String[] key = getArguments().getStringArray("Key");
        username = key[0];
        backButton();
        getTODRecipeCard();
        return view;
    }

    private void backButton() {
        ImageButton back_btn = (ImageButton) view.findViewById(R.id.timeOfDayBackButton);
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

    private void getTODRecipeCard() {

        GridView recipeCards = (GridView) view.findViewById(R.id.TODGrid);
        ArrayList<recipeModel> recipeModelArrayList = new ArrayList<recipeModel>();

        int currentHour = cal.get(Calendar.HOUR_OF_DAY);

        if (7 <= currentHour && currentHour < 11) {
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
                    String[] array = {recipeModelArrayList.get(i).getRecipe_name().toString(), "Breakfast", "False", "True", "False", username};
                    args.putStringArray("RecipeName", array);
                    recipe.setArguments(args);

                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.frame_layout, recipe);
                    fr.commit();
                }
            });
        }
        if (11 <= currentHour && currentHour < 13) {
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
                    String[] array = {recipeModelArrayList.get(i).getRecipe_name().toString(), "Lunch", "False", "True", "False", username};
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
                    String[] array = {recipeModelArrayList.get(i).getRecipe_name().toString(), "Snacks", "False", "True", "False", username};
                    args.putStringArray("RecipeName", array);
                    recipe.setArguments(args);

                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.frame_layout, recipe);
                    fr.commit();
                }
            });
        }
        if (13 <= currentHour && currentHour < 17) {
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
                    String[] array = {recipeModelArrayList.get(i).getRecipe_name().toString(), "Snacks", "False", "True", "False", username};
                    args.putStringArray("RecipeName", array);
                    recipe.setArguments(args);

                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.frame_layout, recipe);
                    fr.commit();
                }
            });
        }
        if (17 <= currentHour && currentHour < 19) {
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
                    String[] array = {recipeModelArrayList.get(i).getRecipe_name().toString(), "Dinner", "False", "True", "False", username};
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
                    String[] array = {recipeModelArrayList.get(i).getRecipe_name().toString(), "Snacks", "False", "True", "False", username};
                    args.putStringArray("RecipeName", array);
                    recipe.setArguments(args);

                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.frame_layout, recipe);
                    fr.commit();
                }
            });
        }
        if (19 <= currentHour && currentHour > 0) {
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
                    String[] array = {recipeModelArrayList.get(i).getRecipe_name().toString(), "Snacks", "False", "True", "False", username};
                    args.putStringArray("RecipeName", array);
                    recipe.setArguments(args);

                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.frame_layout, recipe);
                    fr.commit();
                }
            });
        }
    }
}