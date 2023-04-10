package com.example.feasthub;

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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class recipeDetailsFragment extends Fragment {
    private View view;
    private int counter;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_recipedetail, container, false);
        String[] key = getArguments().getStringArray("RecipeName");
        String recipeName = key[0];
        String collectionName = key[1];
        String isMyRecipe = key[2];
        String isTODRecipe = key[3];
        TextView title = (TextView) view.findViewById(R.id.recipeName);
        title.setText(recipeName);

        TextView description = (TextView) view.findViewById(R.id.recipeDescription);
        ListView Ingredientslist = (ListView) view.findViewById(R.id.IngridentsListTemp);
        ListView Instructionlist = (ListView) view.findViewById(R.id.InstructionListTemp);
        RatingBar rate = (RatingBar) view.findViewById(R.id.ratingRecipe);
        Button start = (Button) view.findViewById(R.id.startButton);
        Button restart = (Button) view.findViewById(R.id.restartButton);
        Button stop = (Button) view.findViewById(R.id.stopButton);
        TextView timer = (TextView) view.findViewById(R.id.timer);

        db.collection(collectionName).document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    String descrip = document.getString("Description");
                    description.setText(descrip);

                    List<String> ingredients = (List<String>) document.get("Ingredients");

                    ArrayAdapter<String> arrayIngredients = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, ingredients);
                    Ingredientslist.setAdapter(arrayIngredients);

                    List<String> instructions = (List<String>) document.get("Ingredients");

                    ArrayAdapter<String> arrayInstructions = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, instructions);
                    Instructionlist.setAdapter(arrayInstructions);

                    float rating = document.getLong("Rating");
                    rate.setRating(rating);
                    rate.setEnabled(false);

                    int hr = document.getLong("Cook Time HR").intValue();
                    int min = document.getLong("Cook Time Min").intValue();
                    int sec = document.getLong("Cook Time Sec").intValue();

                    int milliseconds = (hr * 3600000) + (min * 60000) + (sec * 1000);
                    int second = milliseconds/1000;
                    int minute = second/60;
                    second = second%60;
                    timer.setText(String.format("%02d", minute)
                            + ":" + String.format("%02d", second));

                    start.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new CountDownTimer(milliseconds, 1000){
                                public void onTick(long millisUntilFinished){
                                    int seconds = (int)(millisUntilFinished/1000);
                                    int minutes = seconds/60;
                                    seconds = seconds % 60;
                                    timer.setText(String.format("%02d", minutes)
                                            + ":" + String.format("%02d", seconds));
                                }
                                public  void onFinish(){
                                    timer.setText("FINISH!!");
                                }
                            }.start();
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



        return view;
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


