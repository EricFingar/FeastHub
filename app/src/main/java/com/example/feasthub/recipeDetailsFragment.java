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
import java.util.List;

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

    private TextView timer;


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
        TextView title = (TextView) view.findViewById(R.id.recipeName);
        title.setText(recipeName);

        TextView description = (TextView) view.findViewById(R.id.recipeDescription);
        ListView Ingredientslist = (ListView) view.findViewById(R.id.IngridentsListTemp);
        ListView Instructionlist = (ListView) view.findViewById(R.id.InstructionListTemp);
        RatingBar rate = (RatingBar) view.findViewById(R.id.ratingRecipe);
        Button start = (Button) view.findViewById(R.id.startButton);
        Button reset = (Button) view.findViewById(R.id.resetButton);
        Button pause = (Button) view.findViewById(R.id.pauseButton);
        Button resume = (Button) view.findViewById(R.id.resumeButton);
        timer = (TextView) view.findViewById(R.id.timer);



        db.collection(collectionName).document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    String descrip = document.getString("Description");
                    description.setText(descrip);

                    ingredients = (List<String>) document.get("Ingredients");
                    ArrayAdapter<String> arrayIngredients = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, ingredients);
                    Ingredientslist.setAdapter(arrayIngredients);

                    instructions = (List<String>) document.get("Ingredients");

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
                }
            }
        });

        if(isTODRecipe == "True"){
            exitTOD();
        }
        else {
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
                builder.setPositiveButton("Comfirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(collectionName != "Fruits") {
                            db.collection("Fruits").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            db.collection("Fruits").document(recipeName).delete();
                                        }
                                    }
                                }
                            });
                        }
                        if(collectionName != "Grains") {
                            db.collection("Grains").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            db.collection("Grains").document(recipeName).delete();
                                        }
                                    }
                                }
                            });
                        }
                        if(collectionName != "Protein") {
                            db.collection("Protein").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            db.collection("Protein").document(recipeName).delete();
                                        }
                                    }
                                }
                            });
                        }
                        if(collectionName != "Vegetables") {
                            db.collection("Vegetables").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            db.collection("Vegetables").document(recipeName).delete();
                                        }
                                    }
                                }
                            });
                        }
                        if(collectionName != "Dairy") {
                            db.collection("Fruits").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            db.collection("Fruits").document(recipeName).delete();
                                        }
                                    }
                                }
                            });
                        }
                        if(collectionName != "Breakfast") {
                            db.collection("Breakfast").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            db.collection("Breakfast").document(recipeName).delete();
                                        }
                                    }
                                }
                            });
                        }
                        if(collectionName != "Lunch") {
                            db.collection("Lunch").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            db.collection("Lunch").document(recipeName).delete();
                                        }
                                    }
                                }
                            });
                        }
                        if(collectionName != "Dinner") {
                            db.collection("Dinner").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            db.collection("Dinner").document(recipeName).delete();
                                        }
                                    }
                                }
                            });
                        }
                        if(collectionName != "Snacks") {
                            db.collection("Snacks").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            db.collection("Snacks").document(recipeName).delete();
                                        }
                                    }
                                }
                            });
                        }
                        db.collection(collectionName).document(recipeName).delete();
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
                String[] array = {recipeName,collectionName,isMyRecipe, isTODRecipe};
                args.putStringArray("RecipeName", array);
                editRecipe.setArguments(args);
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, editRecipe);
                fr.commit();
            }
        });
    }

    private void exitBreakfast(){

        ImageButton back_btn = (ImageButton) view.findViewById(R.id.back);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, new BreakfastFragment());
                fr.commit();
            }
        });
    }

    private void exitLunch(){

        ImageButton back_btn = (ImageButton) view.findViewById(R.id.back);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, new LunchFragment());
                fr.commit();
            }
        });
    }

    private void exitDinner(){

        ImageButton back_btn = (ImageButton) view.findViewById(R.id.back);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, new DinnerFragment());
                fr.commit();
            }
        });
    }

    private void exitSnacks(){

        ImageButton back_btn = (ImageButton) view.findViewById(R.id.back);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, new SnacksFragment());
                fr.commit();
            }
        });
    }

    private void exitPantry(){

        ImageButton back_btn = (ImageButton) view.findViewById(R.id.back);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, new PantryFragment());
                fr.commit();
            }
        });
    }

    private void exitMyRecipe(){

        ImageButton back_btn = (ImageButton) view.findViewById(R.id.back);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, new MyRecipesFragment());
                fr.commit();
            }
        });
    }

    private void exitTOD(){

        ImageButton back_btn = (ImageButton) view.findViewById(R.id.back);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, new TimeOfDayFragment());
                fr.commit();
            }
        });
    }

}

