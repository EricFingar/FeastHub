package com.example.feasthub;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddReceiptsFragment extends Fragment {
    private View view;
    private ImageView image;
    private ListView ingList;
    private List<String> list;
    private ArrayAdapter<String> arrayAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_receipts, container, false);

        submitButton();
        return view;
    }


    private void submitButton(){
        Button submitButton = (Button) view.findViewById(R.id.addRecipeSubmitButton);
        EditText title = (EditText) view.findViewById(R.id.Title);
        EditText descriptInput = (EditText) view.findViewById(R.id.descriptionInput);
        EditText ingredInput = (EditText) view.findViewById(R.id.ingredientsInput);
        EditText cookInstructionInput = (EditText) view.findViewById(R.id.cookInstructionInput);
        EditText cookTimeInput = (EditText) view.findViewById(R.id.cookTimeInput);
        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        CheckBox fruit = (CheckBox) view.findViewById(R.id.fruitCheckBox);
        CheckBox grain = (CheckBox) view.findViewById(R.id.gainCheckBox);
        CheckBox dairy = (CheckBox) view.findViewById(R.id.dairyCheckBox);
        CheckBox vegetables = (CheckBox) view.findViewById(R.id.vegetablesCheckBox);
        CheckBox protein = (CheckBox) view.findViewById(R.id.proteinCheckBox);
        CheckBox breakfast = (CheckBox) view.findViewById(R.id.breakfastCheckBox);
        CheckBox lunch = (CheckBox) view.findViewById(R.id.lunchCheckBox);
        CheckBox dinner = (CheckBox) view.findViewById(R.id.dinnerCheckBox);
        CheckBox snacks = (CheckBox) view.findViewById(R.id.snacksCheckBox);



        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String descText = descriptInput.getText().toString();
                String ingredText = ingredInput.getText().toString();
                String cookInstrText = cookInstructionInput.getText().toString();
                String CookTimeText = cookTimeInput.getText().toString();
                Float rateScore = ratingBar.getRating();
                String recipeTitle = title.getText().toString();



                FirebaseFirestore db = FirebaseFirestore.getInstance();



                Map<String,Object> user = new HashMap<>();
                //user.put("Title", recipeTitle);
                user.put("Description",descText);
                user.put("Ingredients",ingredText);
                user.put("Cooking Instructions",cookInstrText);
                user.put("Cook Time",CookTimeText);
                user.put("Rating",rateScore);

                if (fruit.isChecked()){
                    db.collection("Fruits").document(recipeTitle).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            title.setText("");
                            descriptInput.setText("");
                            ingredInput.setText("");
                            ingredInput.setText("");
                            cookInstructionInput.setText("");
                            cookTimeInput.setText("");
                            ratingBar.setRating(0F);
                            fruit.setChecked(false);
                            grain.setChecked(false);
                            dairy.setChecked(false);
                            vegetables.setChecked(false);
                            protein.setChecked(false);
                            breakfast.setChecked(false);
                            lunch.setChecked(false);
                            dinner.setChecked(false);
                            snacks.setChecked(false);

                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has been uploaded to the database", 500);
                            uploadedMSG.show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has failed to upload", 500);
                            uploadedMSG.show();
                        }
                    });;

                }
                if (grain.isChecked()){
                    db.collection("Grains").document(recipeTitle).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            title.setText("");
                            descriptInput.setText("");
                            ingredInput.setText("");
                            ingredInput.setText("");
                            cookInstructionInput.setText("");
                            cookTimeInput.setText("");
                            ratingBar.setRating(0F);
                            fruit.setChecked(false);
                            grain.setChecked(false);
                            dairy.setChecked(false);
                            vegetables.setChecked(false);
                            protein.setChecked(false);
                            breakfast.setChecked(false);
                            lunch.setChecked(false);
                            dinner.setChecked(false);
                            snacks.setChecked(false);

                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has been uploaded to the database", 500);
                            uploadedMSG.show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has failed to upload", 500);
                            uploadedMSG.show();
                        }
                    });


                }
                if (dairy.isChecked()){
                    db.collection("Dairy").document(recipeTitle).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            title.setText("");
                            descriptInput.setText("");
                            ingredInput.setText("");
                            ingredInput.setText("");
                            cookInstructionInput.setText("");
                            cookTimeInput.setText("");
                            ratingBar.setRating(0F);
                            fruit.setChecked(false);
                            grain.setChecked(false);
                            dairy.setChecked(false);
                            vegetables.setChecked(false);
                            protein.setChecked(false);
                            breakfast.setChecked(false);
                            lunch.setChecked(false);
                            dinner.setChecked(false);
                            snacks.setChecked(false);

                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has been uploaded to the database", 500);
                            uploadedMSG.show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has failed to upload", 500);
                            uploadedMSG.show();
                        }
                    });;
                }
                if (vegetables.isChecked()){
                    db.collection("Vegetables").document(recipeTitle).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            title.setText("");
                            descriptInput.setText("");
                            ingredInput.setText("");
                            ingredInput.setText("");
                            cookInstructionInput.setText("");
                            cookTimeInput.setText("");
                            ratingBar.setRating(0F);
                            fruit.setChecked(false);
                            grain.setChecked(false);
                            dairy.setChecked(false);
                            vegetables.setChecked(false);
                            protein.setChecked(false);
                            breakfast.setChecked(false);
                            lunch.setChecked(false);
                            dinner.setChecked(false);
                            snacks.setChecked(false);

                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has been uploaded to the database", 500);
                            uploadedMSG.show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has failed to upload", 500);
                            uploadedMSG.show();
                        }
                    });;
                }
                if (protein.isChecked()){
                    db.collection("Protein").document(recipeTitle).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            title.setText("");
                            descriptInput.setText("");
                            ingredInput.setText("");
                            ingredInput.setText("");
                            cookInstructionInput.setText("");
                            cookTimeInput.setText("");
                            ratingBar.setRating(0F);
                            fruit.setChecked(false);
                            grain.setChecked(false);
                            dairy.setChecked(false);
                            vegetables.setChecked(false);
                            protein.setChecked(false);
                            breakfast.setChecked(false);
                            lunch.setChecked(false);
                            dinner.setChecked(false);
                            snacks.setChecked(false);

                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has been uploaded to the database", 500);
                            uploadedMSG.show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has failed to upload", 500);
                            uploadedMSG.show();
                        }
                    });;
                }

                if (breakfast.isChecked()){
                    db.collection("Breakfast").document(recipeTitle).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            title.setText("");
                            descriptInput.setText("");
                            ingredInput.setText("");
                            ingredInput.setText("");
                            cookInstructionInput.setText("");
                            cookTimeInput.setText("");
                            ratingBar.setRating(0F);
                            fruit.setChecked(false);
                            grain.setChecked(false);
                            dairy.setChecked(false);
                            vegetables.setChecked(false);
                            protein.setChecked(false);
                            breakfast.setChecked(false);
                            lunch.setChecked(false);
                            dinner.setChecked(false);
                            snacks.setChecked(false);

                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has been uploaded to the database", 500);
                            uploadedMSG.show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has failed to upload", 500);
                            uploadedMSG.show();
                        }
                    });;
                }

                if (lunch.isChecked()){
                    db.collection("Lunch").document(recipeTitle).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            title.setText("");
                            descriptInput.setText("");
                            ingredInput.setText("");
                            ingredInput.setText("");
                            cookInstructionInput.setText("");
                            cookTimeInput.setText("");
                            ratingBar.setRating(0F);
                            fruit.setChecked(false);
                            grain.setChecked(false);
                            dairy.setChecked(false);
                            vegetables.setChecked(false);
                            protein.setChecked(false);
                            breakfast.setChecked(false);
                            lunch.setChecked(false);
                            dinner.setChecked(false);
                            snacks.setChecked(false);

                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has been uploaded to the database", 500);
                            uploadedMSG.show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has failed to upload", 500);
                            uploadedMSG.show();
                        }
                    });;
                }

                if (dinner.isChecked()){
                    db.collection("Dinner").document(recipeTitle).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            title.setText("");
                            descriptInput.setText("");
                            ingredInput.setText("");
                            ingredInput.setText("");
                            cookInstructionInput.setText("");
                            cookTimeInput.setText("");
                            ratingBar.setRating(0F);
                            fruit.setChecked(false);
                            grain.setChecked(false);
                            dairy.setChecked(false);
                            vegetables.setChecked(false);
                            protein.setChecked(false);
                            breakfast.setChecked(false);
                            lunch.setChecked(false);
                            dinner.setChecked(false);
                            snacks.setChecked(false);

                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has been uploaded to the database", 500);
                            uploadedMSG.show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has failed to upload", 500);
                            uploadedMSG.show();
                        }
                    });;
                }

                if (snacks.isChecked()){
                    db.collection("Snacks").document(recipeTitle).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            title.setText("");
                            descriptInput.setText("");
                            ingredInput.setText("");
                            ingredInput.setText("");
                            cookInstructionInput.setText("");
                            cookTimeInput.setText("");
                            ratingBar.setRating(0F);
                            fruit.setChecked(false);
                            grain.setChecked(false);
                            dairy.setChecked(false);
                            vegetables.setChecked(false);
                            protein.setChecked(false);
                            breakfast.setChecked(false);
                            lunch.setChecked(false);
                            dinner.setChecked(false);
                            snacks.setChecked(false);

                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has been uploaded to the database", 500);
                            uploadedMSG.show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has failed to upload", 500);
                            uploadedMSG.show();
                        }
                    });;
                }
            }
        });
    }



}