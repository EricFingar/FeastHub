package com.example.feasthub;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.Settings;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;


public class HomeFragment extends Fragment {
    private View view;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String username;
    private String loginSuccess;
    Calendar cal = Calendar.getInstance();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_home, container, false);
        String[] key = getArguments().getStringArray("Key");
        username = key[0];
        ((MainActivity)getActivity()).setUsername(username);
        getTODRecipeCard();
        breakfastButton();
        lunchButton();
        dinnerButton();
        snacksButton();
        favoriteRecipeButton();
        timeOfDayButton();
        getFavoriteRecipe();
        search();
        return view;


    }

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

    private void breakfastButton(){
        ImageButton breakfast_btn = (ImageButton) view.findViewById(R.id.breakfast_botton);
        breakfast_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                BreakfastFragment breakfast = new BreakfastFragment();
                String[] array = {username};
                args.putStringArray("Key", array);
                breakfast.setArguments(args);
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, breakfast);
                fr.commit();
            }
        });
    }

    private void lunchButton(){
        ImageButton breakfast_btn = (ImageButton) view.findViewById(R.id.lunch_botton);
        breakfast_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                LunchFragment lunch = new LunchFragment();
                String[] array = {username};
                args.putStringArray("Key", array);
                lunch.setArguments(args);
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, lunch);
                fr.commit();
            }
        });
    }

    private void dinnerButton(){
        ImageButton breakfast_btn = (ImageButton) view.findViewById(R.id.dinner_botton);
        breakfast_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                DinnerFragment dinner = new DinnerFragment();
                String[] array = {username};
                args.putStringArray("Key", array);
                dinner.setArguments(args);
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, dinner);
                fr.commit();
            }
        });
    }

    private void snacksButton(){
        ImageButton breakfast_btn = (ImageButton) view.findViewById(R.id.snacks_button);
        breakfast_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                SnacksFragment snack = new SnacksFragment();
                String[] array = {username};
                args.putStringArray("Key", array);
                snack.setArguments(args);
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, snack);
                fr.commit();
            }
        });
    }

    private void favoriteRecipeButton(){
        ImageButton favorite_btn = (ImageButton) view.findViewById(R.id.recent_more_botton);
        favorite_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                FavoriteFragment favorite = new FavoriteFragment();
                String[] array = {username};
                args.putStringArray("Key", array);
                favorite.setArguments(args);
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, favorite);
                fr.commit();
            }
        });
    }
    private void timeOfDayButton(){
        ImageButton TOD_btn = (ImageButton) view.findViewById(R.id.time_of_day_button);
        TOD_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                TimeOfDayFragment TOD = new TimeOfDayFragment();
                String[] array = {username};
                args.putStringArray("Key", array);
                TOD.setArguments(args);
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, TOD);
                fr.commit();
            }
        });
    }

    private void getFavoriteRecipe() {
        GridView recipeCards = (GridView) view.findViewById(R.id.MostUsedRecipeGD);
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
                String[] array = {recipeModelArrayList.get(i).getRecipe_name().toString(),"Favorites", "False", "False", "True", username};
                args.putStringArray("RecipeName", array);
                recipe.setArguments(args);

                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, recipe);
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
                            recipeModelArrayList.add(new recipeModel(id, R.drawable.snacks));
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
                    String[] array = {recipeModelArrayList.get(i).getRecipe_name().toString(), "Snacks", "False", "True", "False",username};
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