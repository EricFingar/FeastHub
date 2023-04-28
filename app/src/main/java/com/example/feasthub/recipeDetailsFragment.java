package com.example.feasthub;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class recipeDetailsFragment extends Fragment {
    private View view;
    private String recipeName;
    private String collectionName;
    private String isMyRecipe;
    private String isTODRecipe;

    private List<String> ingredients;

    private List<String> instructions;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CountDownTimer mCountDownTimer;
    private int milliseconds;
    private int sec;
    private int hr;
    private int min;

    private long millisecondsLeft;

    private boolean favoriteRecipe;

    private String isFavorite;
    private TextView timer;

    private String username;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_recipedetail, container, false);
        String[] key = getArguments().getStringArray("RecipeName");
        recipeName = key[0];
        collectionName = key[1];
        isMyRecipe = key[2];
        isTODRecipe = key[3];
        isFavorite = key[4];
        username = key[5];



        TextView title = (TextView) view.findViewById(R.id.recipeName);
        title.setText(recipeName);
        if(recipeName.length() >= 18){
            title.setTextSize(30);
        }
        if(recipeName.length() >= 24){
            title.setTextSize(25);
        }
        if(recipeName.length() >= 32){
            title.setTextSize(20);
        }


        TextView description = (TextView) view.findViewById(R.id.recipeDescription);
        ListView Ingredientslist = (ListView) view.findViewById(R.id.IngridentsListTemp);
        ListView Instructionlist = (ListView) view.findViewById(R.id.InstructionListTemp);
        RatingBar rate = (RatingBar) view.findViewById(R.id.ratingRecipe);
        Button start = (Button) view.findViewById(R.id.startButton);
        Button reset = (Button) view.findViewById(R.id.resetButton);
        Button pause = (Button) view.findViewById(R.id.pauseButton);
        Button resume = (Button) view.findViewById(R.id.resumeButton);
        ImageButton favorite = (ImageButton) view.findViewById(R.id.favoriteButton);
        ImageView recipeImage = (ImageView) view.findViewById(R.id.recipeImage);
        timer = (TextView) view.findViewById(R.id.timer);

        if(isFavorite == "True"){
            favoriteRecipe = true;
            favorite.setImageResource(R.drawable.baseline_favorite_24);
        } else if (isFavorite == "False") {
            favoriteRecipe = false;
            favorite.setImageResource(R.drawable.baseline_favorite_border_24);
        }
        else{
            favoriteRecipe = false;
            favorite.setImageResource(R.drawable.baseline_favorite_border_24);
        }

        db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection(collectionName).document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    recipeImage.setImageResource(R.drawable.defaultfood);

                    String descrip = document.getString("Description");
                    description.setText(descrip);

                    ingredients = (List<String>) document.get("Ingredients");
                    ArrayAdapter<String> arrayIngredients = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, ingredients);
                    Ingredientslist.setAdapter(arrayIngredients);

                    instructions = (List<String>) document.get("Cooking Instructions");

                    ArrayAdapter<String> arrayInstructions = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, instructions);
                    Instructionlist.setAdapter(arrayInstructions);


                    float rating = document.getLong("Rating");
                    rate.setRating(rating);
                    rate.setEnabled(false);

                    hr = document.getLong("Cook Time HR").intValue();
                    min = document.getLong("Cook Time Min").intValue();
                    sec = document.getLong("Cook Time Sec").intValue();
                    milliseconds = (hr * 3600000) + (min * 60000) + (sec * 1000);

                    timer.setText(String.format("%02d", hr)
                            + ":" + String.format("%02d", min)
                            + ":" + String.format("%02d", sec));

                    start.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startTimer(milliseconds);
                        }

                    });

                    pause.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            pauseTimer();
                        }
                    });

                    resume.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            resumeTimer();
                        }
                    });

                    reset.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            resetTimer();
                        }
                    });

                    favorite.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(!favoriteRecipe){
                                favorite.setImageResource(R.drawable.baseline_favorite_24);
                                favoriteRecipe = true;
                                db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Breakfast").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()){
                                            DocumentSnapshot document = task.getResult();
                                            if(document.exists()){
                                                db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection(collectionName).document(recipeName).update("Favorite",favoriteRecipe);
                                            }
                                        }
                                    }
                                });
                                db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Lunch").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()){
                                            DocumentSnapshot document = task.getResult();
                                            if(document.exists()){
                                                db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection(collectionName).document(recipeName).update("Favorite",favoriteRecipe);
                                            }
                                        }
                                    }
                                });
                                db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Dinner").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()){
                                            DocumentSnapshot document = task.getResult();
                                            if(document.exists()){
                                                db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection(collectionName).document(recipeName).update("Favorite",favoriteRecipe);
                                            }
                                        }
                                    }
                                });
                                db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Snacks").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()){
                                            DocumentSnapshot document = task.getResult();
                                            if(document.exists()){
                                                db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection(collectionName).document(recipeName).update("Favorite",favoriteRecipe);
                                            }
                                        }
                                    }
                                });
                                db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Fruits").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()){
                                            DocumentSnapshot document = task.getResult();
                                            if(document.exists()){
                                                db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection(collectionName).document(recipeName).update("Favorite",favoriteRecipe);
                                            }
                                        }
                                    }
                                });
                                db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Grains").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()){
                                            DocumentSnapshot document = task.getResult();
                                            if(document.exists()){
                                                db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection(collectionName).document(recipeName).update("Favorite",favoriteRecipe);
                                            }
                                        }
                                    }
                                });
                                db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Protein").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()){
                                            DocumentSnapshot document = task.getResult();
                                            if(document.exists()){
                                                db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection(collectionName).document(recipeName).update("Favorite",favoriteRecipe);
                                            }
                                        }
                                    }
                                });
                                db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Vegetables").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()){
                                            DocumentSnapshot document = task.getResult();
                                            if(document.exists()){
                                                db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection(collectionName).document(recipeName).update("Favorite",favoriteRecipe);
                                            }
                                        }
                                    }
                                });
                                db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Dairy").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()){
                                            DocumentSnapshot document = task.getResult();
                                            if(document.exists()){
                                                db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection(collectionName).document(recipeName).update("Favorite",favoriteRecipe);
                                            }
                                        }
                                    }
                                });

                                Map<String,Object> user = new HashMap<>();
                                user.put("Title", recipeName);
                                user.put("Description",descrip);
                                user.put("Ingredients",ingredients);
                                user.put("Cooking Instructions",instructions);
                                user.put("Cook Time HR",hr);
                                user.put("Cook Time Min", min);
                                user.put("Cook Time Sec", sec);
                                user.put("Rating",rating);
                                user.put("Favorite", true);
                                user.put("Image", R.drawable.defaultfood);

                                db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Favorites").document(recipeName).set(user);
                            }
                            else{
                                favorite.setImageResource(R.drawable.baseline_favorite_border_24);
                                favoriteRecipe = false;

                                db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Breakfast").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()){
                                            DocumentSnapshot document = task.getResult();
                                            if(document.exists()){
                                                db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection(collectionName).document(recipeName).update("Favorite",favoriteRecipe);
                                            }
                                        }
                                    }
                                });
                                db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Lunch").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()){
                                            DocumentSnapshot document = task.getResult();
                                            if(document.exists()){
                                                db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection(collectionName).document(recipeName).update("Favorite",favoriteRecipe);
                                            }
                                        }
                                    }
                                });
                                db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Dinner").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()){
                                            DocumentSnapshot document = task.getResult();
                                            if(document.exists()){
                                                db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection(collectionName).document(recipeName).update("Favorite",favoriteRecipe);
                                            }
                                        }
                                    }
                                });
                                db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Snacks").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()){
                                            DocumentSnapshot document = task.getResult();
                                            if(document.exists()){
                                                db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection(collectionName).document(recipeName).update("Favorite",favoriteRecipe);
                                            }
                                        }
                                    }
                                });
                                db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Fruits").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()){
                                            DocumentSnapshot document = task.getResult();
                                            if(document.exists()){
                                                db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection(collectionName).document(recipeName).update("Favorite",favoriteRecipe);
                                            }
                                        }
                                    }
                                });
                                db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Grains").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()){
                                            DocumentSnapshot document = task.getResult();
                                            if(document.exists()){
                                                db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection(collectionName).document(recipeName).update("Favorite",favoriteRecipe);
                                            }
                                        }
                                    }
                                });
                                db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Protein").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()){
                                            DocumentSnapshot document = task.getResult();
                                            if(document.exists()){
                                                db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection(collectionName).document(recipeName).update("Favorite",favoriteRecipe);
                                            }
                                        }
                                    }
                                });
                                db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Vegetables").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()){
                                            DocumentSnapshot document = task.getResult();
                                            if(document.exists()){
                                                db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection(collectionName).document(recipeName).update("Favorite",favoriteRecipe);
                                            }
                                        }
                                    }
                                });
                                db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Dairy").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()){
                                            DocumentSnapshot document = task.getResult();
                                            if(document.exists()){
                                                db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection(collectionName).document(recipeName).update("Favorite",favoriteRecipe);
                                            }
                                        }
                                    }
                                });

                                db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Favorites").document(recipeName).delete();
                            }

                        }
                    });
                }
            }
        });

        if(isTODRecipe == "True"){
            exitTOD();
        } else if (isFavorite == "True") {
            exitFavorite();
        } else {
            if (isMyRecipe == "False") {
                if (collectionName == "Breakfast") {
                    exitBreakfast();
                }
                if (collectionName == "Lunch") {
                    exitLunch();
                }
                if (collectionName == "Dinner") {
                    exitDinner();
                }
                if (collectionName == "Snacks") {
                    exitSnacks();
                }
                if (collectionName == "Fruits" || collectionName == "Grains" || collectionName == "Vegetables" || collectionName == "Protein" || collectionName == "Dairy") {
                    exitPantry();
                }
            }
            else{
                exitMyRecipe();
            }
        }

        delete();
        editRecipe();
        return view;
    }

    private void startTimer(long time){
        mCountDownTimer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                millisecondsLeft = millisUntilFinished;
                NumberFormat f = new DecimalFormat("00");
                long hour = (millisUntilFinished / 3600000) % 24;
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;
                timer.setText(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));

            }

            @Override
            public void onFinish() {
                timer.setText("Finished!");
            }
        }.start();
    }

    private void pauseTimer(){
        mCountDownTimer.cancel();
    }

    private void resumeTimer(){
        startTimer(millisecondsLeft);
    }

    private void resetTimer(){
        timer.setText(String.format("%02d", hr)
                + ":" + String.format("%02d", min)
                + ":" + String.format("%02d", sec));
    }
    private void delete(){
        ImageButton delete_btn = (ImageButton) view.findViewById(R.id.delete_btn);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setCancelable(true);
                builder.setMessage("Are you sure you want to delete " + recipeName + " recipe");
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(collectionName != "Fruits") {
                            db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Fruits").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Fruits").document(recipeName).delete();
                                        }
                                    }
                                }
                            });
                        }
                        if(collectionName != "Grains") {
                            db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Grains").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Grains").document(recipeName).delete();
                                        }
                                    }
                                }
                            });
                        }
                        if(collectionName != "Protein") {
                            db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Protein").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Protein").document(recipeName).delete();
                                        }
                                    }
                                }
                            });
                        }
                        if(collectionName != "Vegetables") {
                            db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Vegetables").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Vegetables").document(recipeName).delete();
                                        }
                                    }
                                }
                            });
                        }
                        if(collectionName != "Dairy") {
                            db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Fruits").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Fruits").document(recipeName).delete();
                                        }
                                    }
                                }
                            });
                        }
                        if(collectionName != "Breakfast") {
                            db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Breakfast").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Breakfast").document(recipeName).delete();
                                        }
                                    }
                                }
                            });
                        }
                        if(collectionName != "Lunch") {
                            db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Lunch").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Lunch").document(recipeName).delete();
                                        }
                                    }
                                }
                            });
                        }
                        if(collectionName != "Dinner") {
                            db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Dinner").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Dinner").document(recipeName).delete();
                                        }
                                    }
                                }
                            });
                        }
                        if(collectionName != "Snacks") {
                            db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Snacks").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Snacks").document(recipeName).delete();
                                        }
                                    }
                                }
                            });
                        }
                        db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection(collectionName).document(recipeName).delete();
                        FragmentTransaction fr = getFragmentManager().beginTransaction();
                        fr.replace(R.id.frame_layout, new HomeFragment());
                        fr.commit();
                    }
                });

                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });
    }

    private void editRecipe(){
        ImageButton edit_btn = (ImageButton) view.findViewById(R.id.edit);
        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                editRecipeFragment editRecipe = new editRecipeFragment();
                String[] array = {recipeName,collectionName,isMyRecipe, isTODRecipe, username};
                args.putStringArray("RecipeName", array);
                editRecipe.setArguments(args);
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, editRecipe);
                fr.commit();
            }
        });
    }

    private void exitFavorite(){

        ImageButton back_btn = (ImageButton) view.findViewById(R.id.back);
        back_btn.setOnClickListener(new View.OnClickListener() {
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

    private void exitBreakfast(){

        ImageButton back_btn = (ImageButton) view.findViewById(R.id.back);
        back_btn.setOnClickListener(new View.OnClickListener() {
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

    private void exitLunch(){

        ImageButton back_btn = (ImageButton) view.findViewById(R.id.back);
        back_btn.setOnClickListener(new View.OnClickListener() {
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

    private void exitDinner(){

        ImageButton back_btn = (ImageButton) view.findViewById(R.id.back);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                DinnerFragment dinner = new DinnerFragment();
                String[] array = {username};
                args.putStringArray("Key", array);
                dinner.setArguments(args);
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout,dinner);
                fr.commit();
            }
        });
    }

    private void exitSnacks(){

        ImageButton back_btn = (ImageButton) view.findViewById(R.id.back);
        back_btn.setOnClickListener(new View.OnClickListener() {
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

    private void exitPantry(){

        ImageButton back_btn = (ImageButton) view.findViewById(R.id.back);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                PantryFragment pantry = new PantryFragment();
                String[] array = {username};
                args.putStringArray("Key", array);
                pantry.setArguments(args);
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, pantry);
                fr.commit();
            }
        });
    }

    private void exitMyRecipe(){

        ImageButton back_btn = (ImageButton) view.findViewById(R.id.back);
        back_btn.setOnClickListener(new View.OnClickListener() {
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

    private void exitTOD(){

        ImageButton back_btn = (ImageButton) view.findViewById(R.id.back);
        back_btn.setOnClickListener(new View.OnClickListener() {
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

}
