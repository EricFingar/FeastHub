package com.example.feasthub;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents a fragment that allows the user to edit a recipe.
 */
public class editRecipeFragment extends Fragment {
    private View view;
    private ImageView image;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String recipeName;
    private String collectionName;
    private String isMyRecipe;
    private String isTODRecipe;

    private String isFavorite;
    private List<String> ing = new ArrayList<>();
    private List<String> instructions = new ArrayList<>();

    private String recipeNameOld;

    private boolean veggieFlag = false;

    private boolean fruitFlag = false;

    private boolean grainFlag = false;

    private boolean dairyFlag = false;

    private boolean protienFlag = false;

    private boolean breakfastFlag = false;

    private boolean lunchFlag = false;

    private boolean dinnerFlag = false;

    private boolean snacksFlag = false;

    private String username;

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;

    /**
     * Overrides the onCreateView method of Fragment class to inflate the layout of the fragment
     * and initialize its view elements.
     * @param inflater - The LayoutInflater object that can be used to inflate any views in the fragment
     * @param container - The parent view that the fragment's UI should be attached to
     * @param savedInstanceState - This fragment is being re-constructed from a previous saved state as given here
     * @return The View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_editrecipe, container, false);
        String[] key = getArguments().getStringArray("RecipeName");

        collectionName = key[1];
        recipeName = key[0];
        isMyRecipe = key[2];
        isTODRecipe = key[3];
        username = key[4];
        recipeNameOld = recipeName;



        EditText title = (EditText) view.findViewById(R.id.Title);
        EditText descriptInput = (EditText) view.findViewById(R.id.descriptionInput);
        EditText ingredInput = (EditText) view.findViewById(R.id.ingredientsInput);
        EditText cookInstructionInput = (EditText) view.findViewById(R.id.cookInstructionInput);
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
        ImageButton add = (ImageButton) view.findViewById(R.id.addIngredButton);
        ImageButton subtract = (ImageButton) view.findViewById(R.id.removeIngredButton);
        ListView ingredients = (ListView) view.findViewById(R.id.ingredientsList);

        ImageButton addInstruction = (ImageButton) view.findViewById(R.id.addInstructionButton);
        ImageButton removeInstruction = (ImageButton) view.findViewById(R.id.removeInstructionButton);
        ListView instructionsList = (ListView) view.findViewById(R.id.cookingInstructionList);

        EditText hr = (EditText) view.findViewById(R.id.cookTimeInputHR);
        EditText min = (EditText) view.findViewById(R.id.cookTimeInputMin);
        EditText sec = (EditText) view.findViewById(R.id.cookTimeInputSec);

    /**
     * Retrieves recipe information from Firestore and populates the UI fields with the corresponding values
     * @param username the username of the user whose recipe is being retrieved
     * @param collectionName the name of the collection where the recipe is stored
     * @param recipeName the name of the recipe being retrieved
     * @param view the parent view that contains the UI fields to be populated
     * @param title the EditText field for the recipe title
     * @param descriptInput the EditText field for the recipe description
     * @param ing the list of ingredients for the recipe
     * @param arrayIngredients the ArrayAdapter for the list of ingredients
     * @param ingredients the ListView for the list of ingredients
     * @param instructions the list of cooking instructions for the recipe
     * @param arrayInstructions the ArrayAdapter for the list of cooking instructions
     * @param instructionsList the ListView for the list of cooking instructions
     * @param hr the EditText field for the number of hours needed to cook the recipe
     * @param min the EditText field for the number of minutes needed to cook the recipe
     * @param sec the EditText field for the number of seconds needed to cook the recipe
     * @param ratingBar the RatingBar for the recipe rating
     */
        db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection(collectionName).document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();

                    title.setText(recipeName);

                    String descrip = document.getString("Description");
                    descriptInput.setText(descrip);

                    ing = (List<String>)document.get("Ingredients");
                    ArrayAdapter<String> arrayIngredients = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, ing);
                    ingredients.setAdapter(arrayIngredients);


                    instructions = (List<String>) document.get("Cooking Instructions");

                    ArrayAdapter<String> arrayInstructions = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, instructions);
                    instructionsList.setAdapter(arrayInstructions);

                    int hrDB = document.getLong("Cook Time HR").intValue();
                    String hrVal = String.valueOf(hrDB);
                    hr.setText(hrVal);
                    int minDB = document.getLong("Cook Time Min").intValue();
                    String minVal = String.valueOf(minDB);
                    min.setText(minVal);
                    int secDB = document.getLong("Cook Time Sec").intValue();
                    String secVal = String.valueOf(secDB);
                    sec.setText(secVal);

                    float rating = document.getLong("Rating");
                    ratingBar.setRating(rating);
                    ratingBar.setEnabled(true);
                }
            }
        });

        /**
         * Retrieves the fruit category from the Firestore
         * All other categories follow
         */
        db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Fruits").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        fruit.setChecked(true);
                        fruitFlag = true;
                    }
                }
            }
        });

        db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Grains").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        grain.setChecked(true);
                        grainFlag = true;
                    }
                }
            }
        });

        db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Vegetables").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        vegetables.setChecked(true);
                        veggieFlag = true;
                    }
                }
            }
        });

        db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Protein").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        dairy.setChecked(true);
                        dairyFlag=true;
                    }
                }
            }
        });

        db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Breakfast").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        breakfast.setChecked(true);
                        breakfastFlag = true;
                    }
                }
            }
        });

        db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Lunch").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        lunch.setChecked(true);
                        lunchFlag = false;
                    }
                }
            }
        });

        db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Dinner").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        dinner.setChecked(true);
                        dinnerFlag = true;
                    }
                }
            }
        });

        db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Snacks").document(recipeName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        snacks.setChecked(true);
                        snacksFlag = true;
                    }
                }
            }
        });

        submitButton();
        exitButton();
        return view;
    }

    /**
     * Sets up the behavior for the exit button that returns the user to the previous screen.
     */
    private void exitButton(){
        ImageButton exit = (ImageButton) view.findViewById(R.id.exit);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Still need to get it work, just have it set to home right now.
                Bundle args = new Bundle();
                recipeDetailsFragment recipe = new recipeDetailsFragment();
                String[] array = {recipeName,collectionName,isMyRecipe, isTODRecipe, isFavorite, username};
                args.putStringArray("RecipeName", array);
                recipe.setArguments(args);
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, recipe);
                fr.commit();
            }
        });
    }


    /**
     * This method sets up the submit button functionality on the add recipe page.
     * It retrieves all of the user input from the various EditText and CheckBox fields,
     * and stores them in an array to be passed to the recipe details fragment. It also
     * sets up listeners for the add and subtract ingredient and instruction buttons, which
     * add or remove items from the ListView displaying the user's input.
     */
    private void submitButton(){
        Button submitButton = (Button) view.findViewById(R.id.addRecipeSubmitButton);
        EditText title = (EditText) view.findViewById(R.id.Title);
        EditText descriptInput = (EditText) view.findViewById(R.id.descriptionInput);
        EditText ingredInput = (EditText) view.findViewById(R.id.ingredientsInput);
        EditText cookInstructionInput = (EditText) view.findViewById(R.id.cookInstructionInput);
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
        ImageButton add = (ImageButton) view.findViewById(R.id.addIngredButton);
        ImageButton subtract = (ImageButton) view.findViewById(R.id.removeIngredButton);
        ListView ingredients = (ListView) view.findViewById(R.id.ingredientsList);

        ImageButton addInstruction = (ImageButton) view.findViewById(R.id.addInstructionButton);
        ImageButton removeInstruction = (ImageButton) view.findViewById(R.id.removeInstructionButton);
        ListView instructionsList = (ListView) view.findViewById(R.id.cookingInstructionList);

        EditText hr = (EditText) view.findViewById(R.id.cookTimeInputHR);
        EditText min = (EditText) view.findViewById(R.id.cookTimeInputMin);
        EditText sec = (EditText) view.findViewById(R.id.cookTimeInputSec);
        ImageButton addImage = (ImageButton) view.findViewById(R.id.addImageEditButton);

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);


            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ing.add(ingredInput.getText().toString());
                ArrayAdapter<String> arrayIngredients = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1,ing);
                ingredients.setAdapter(arrayIngredients);
                ingredInput.setText("");
            }
        });


        subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ing.remove(ing.size()-1);
                ArrayAdapter<String> arrayIngredients = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1,ing);
                ingredients.setAdapter(arrayIngredients);
            }
        });

        addInstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                instructions.add(cookInstructionInput.getText().toString());
                ArrayAdapter<String> arrayInstructions = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1,instructions);
                instructionsList.setAdapter(arrayInstructions);
                cookInstructionInput.setText("");
            }
        });


        removeInstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                instructions.remove(instructions.size()-1);
                ArrayAdapter<String> arrayInstructions = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1,instructions);
                instructionsList.setAdapter(arrayInstructions);
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String descText;
                if (descriptInput.getText().toString().matches("")){
                    descText = "[Blank]";
                }
                else{
                    descText = descriptInput.getText().toString();
                }

                int CookTimeHrText;
                if (hr.getText().toString().matches("")){
                    CookTimeHrText = 0;
                }
                else{
                    CookTimeHrText = Integer.parseInt(hr.getText().toString());
                }
                int CookTimeMinText;
                if (min.getText().toString().matches("")){
                    CookTimeMinText = 0;
                }
                else{
                    CookTimeMinText = Integer.parseInt(min.getText().toString());
                }
                int CookTimeSecText;
                if (sec.getText().toString().matches("")){
                    CookTimeSecText = 0;
                }
                else{
                    CookTimeSecText = Integer.parseInt(sec.getText().toString());
                }
                Float rateScore = ratingBar.getRating();
                String recipeTitle;
                if(title.getText().toString().matches("")){
                    recipeTitle = "[Blank]";
                }
                else{
                    recipeTitle = title.getText().toString();
                }



                FirebaseFirestore db = FirebaseFirestore.getInstance();



                Map<String,Object> user = new HashMap<>();
                user.put("Title", recipeTitle);
                user.put("Description",descText);
                user.put("Ingredients",ing);
                user.put("Cooking Instructions",instructions);
                user.put("Cook Time HR",CookTimeHrText);
                user.put("Cook Time Min", CookTimeMinText);
                user.put("Cook Time Sec", CookTimeSecText);
                user.put("Rating",rateScore);
                user.put("Image", R.drawable.defaultfood);


                /**
                 * This method checks if the "Fruits" category is selected and updates or adds the recipe accordingly in the Firebase database.
                 * If the recipe already exists in the "Fruits" category, it updates the existing data, otherwise it adds the new recipe data.
                 * each category follows
                 */
                if (fruit.isChecked()){
                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Fruits").document(recipeTitle).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                DocumentSnapshot document = task.getResult();
                                if(document.exists()){
                                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Fruits").document(recipeTitle).update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has been updated in the database", 500);
                                            uploadedMSG.show();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has failed to update", 500);
                                            uploadedMSG.show();
                                        }
                                    });
                                }
                                else{
                                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Fruits").document(recipeNameOld).delete();
                                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Fruits").document(recipeTitle).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has been added to Fruits", 500);
                                            uploadedMSG.show();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has failed to add to Fruits", 500);
                                            uploadedMSG.show();
                                        }
                                    });
                                }
                            }
                        }
                    });

                }
                if (grain.isChecked()) {
                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Grains").document(recipeTitle).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Grains").document(recipeTitle).update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has been updated in the database", 500);
                                            uploadedMSG.show();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has failed to update", 500);
                                            uploadedMSG.show();
                                        }
                                    });
                                } else {
                                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Grains").document(recipeNameOld).delete();
                                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Grains").document(recipeTitle).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has been added to Grains", 500);
                                            uploadedMSG.show();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has failed to add to Fruits", 500);
                                            uploadedMSG.show();
                                        }
                                    });
                                }
                            }
                        }
                    });
                }

                if (vegetables.isChecked()) {
                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Vegetables").document(recipeTitle).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Vegetables").document(recipeTitle).update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has been updated in the database", 500);
                                            uploadedMSG.show();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has failed to update", 500);
                                            uploadedMSG.show();
                                        }
                                    });
                                } else {
                                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Vegetables").document(recipeNameOld).delete();
                                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Vegetables").document(recipeTitle).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has been added to Vegetables", 500);
                                            uploadedMSG.show();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has failed to add to Vegetables", 500);
                                            uploadedMSG.show();
                                        }
                                    });
                                }
                            }
                        }
                    });
                }

                if (protein.isChecked()) {
                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Protein").document(recipeTitle).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Protein").document(recipeTitle).update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has been updated in the database", 500);
                                            uploadedMSG.show();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has failed to update", 500);
                                            uploadedMSG.show();
                                        }
                                    });
                                } else {
                                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Protein").document(recipeNameOld).delete();
                                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Protein").document(recipeTitle).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has been added to Protein", 500);
                                            uploadedMSG.show();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has failed to add to Protein", 500);
                                            uploadedMSG.show();
                                        }
                                    });
                                }
                            }
                        }
                    });
                }

                if (dairy.isChecked()) {
                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Dairy").document(recipeTitle).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Dairy").document(recipeTitle).update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has been updated in the database", 500);
                                            uploadedMSG.show();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has failed to update", 500);
                                            uploadedMSG.show();
                                        }
                                    });
                                } else {
                                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Dairy").document(recipeNameOld).delete();
                                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Dairy").document(recipeTitle).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has been added to Dairy", 500);
                                            uploadedMSG.show();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has failed to add to Dairy", 500);
                                            uploadedMSG.show();
                                        }
                                    });
                                }
                            }
                        }
                    });
                }

                if (breakfast.isChecked()) {
                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Breakfast").document(recipeTitle).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Breakfast").document(recipeTitle).update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has been updated in the database", 500);
                                            uploadedMSG.show();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has failed to update", 500);
                                            uploadedMSG.show();
                                        }
                                    });
                                } else {
                                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Breakfast").document(recipeNameOld).delete();
                                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Breakfast").document(recipeTitle).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has been added to Breakfast", 500);
                                            uploadedMSG.show();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has failed to add to Breakfast", 500);
                                            uploadedMSG.show();
                                        }
                                    });
                                }
                            }
                        }
                    });
                }

                if (lunch.isChecked()) {
                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Lunch").document(recipeTitle).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Lunch").document(recipeTitle).update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has been updated in the database", 500);
                                            uploadedMSG.show();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has failed to update", 500);
                                            uploadedMSG.show();
                                        }
                                    });
                                } else {
                                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Lunch").document(recipeNameOld).delete();
                                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Lunch").document(recipeTitle).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has been added to Lunch", 500);
                                            uploadedMSG.show();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has failed to add to Lunch", 500);
                                            uploadedMSG.show();
                                        }
                                    });
                                }
                            }
                        }
                    });
                }

                if (dinner.isChecked()) {
                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Dinner").document(recipeTitle).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Dinner").document(recipeTitle).update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has been updated in the database", 500);
                                            uploadedMSG.show();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has failed to update", 500);
                                            uploadedMSG.show();
                                        }
                                    });
                                } else {
                                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Dinner").document(recipeNameOld).delete();
                                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Dinner").document(recipeTitle).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has been added to Dinner", 500);
                                            uploadedMSG.show();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has failed to add to Dinner", 500);
                                            uploadedMSG.show();
                                        }
                                    });
                                }
                            }
                        }
                    });
                }

                if (snacks.isChecked()) {
                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Snacks").document(recipeTitle).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Snacks").document(recipeTitle).update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has been updated in the database", 500);
                                            uploadedMSG.show();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has failed to update", 500);
                                            uploadedMSG.show();
                                        }
                                    });
                                } else {
                                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Snacks").document(recipeNameOld).delete();
                                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Snacks").document(recipeTitle).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has been added to Snacks", 500);
                                            uploadedMSG.show();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has failed to add to Snacks", 500);
                                            uploadedMSG.show();
                                        }
                                    });
                                }
                            }
                        }
                    });
                }
            }
        });



    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TextView addImageText = (TextView) view.findViewById(R.id.addImageTitle);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            addImageText.setText(R.string.imageSaved);

            // Do something with the selected image URI
        }
    }

}