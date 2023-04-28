package com.example.feasthub;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddRecipesFragment extends Fragment{
    private View view;
    private ImageView image;

    private ListView ingList;
    private List<String> list;
    private ArrayAdapter<String> arrayAdapter;
    private String username;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_recipes, container, false);
        String[] key = getArguments().getStringArray("Key");
        username = key[0];

        submitButton();
        return view;
    }


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
        List<String> ing = new ArrayList<>();
        ImageButton addInstruction = (ImageButton) view.findViewById(R.id.addInstructionButton);
        ImageButton removeInstruction = (ImageButton) view.findViewById(R.id.removeInstructionButton);
        ListView instructions = (ListView) view.findViewById(R.id.cookingInstructionList);
        List<String> instruct = new ArrayList<>();
        EditText hr = (EditText) view.findViewById(R.id.cookTimeInputHR);
        EditText min = (EditText) view.findViewById(R.id.cookTimeInputMin);
        EditText sec = (EditText) view.findViewById(R.id.cookTimeInputSec);
        ImageButton addImage = (ImageButton) view.findViewById(R.id.addImageButton);



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
                instruct.add(cookInstructionInput.getText().toString());
                ArrayAdapter<String> arrayInstructions = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1,instruct);
                instructions.setAdapter(arrayInstructions);
                cookInstructionInput.setText("");
            }
        });


        removeInstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                instruct.remove(instruct.size()-1);
                ArrayAdapter<String> arrayInstructions = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1,instruct);
                instructions.setAdapter(arrayInstructions);
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







                Map<String,Object> user = new HashMap<>();
                user.put("Title", recipeTitle);
                user.put("Description",descText);
                user.put("Ingredients",ing);
                user.put("Cooking Instructions",instruct);
                user.put("Cook Time HR",CookTimeHrText);
                user.put("Cook Time Min", CookTimeMinText);
                user.put("Cook Time Sec", CookTimeSecText);
                user.put("Rating",rateScore);
                user.put("Favorite", false);
                user.put("Image", mImageUri);





                if (fruit.isChecked()){
                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Fruits").document(recipeTitle).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            title.setText("");
                            descriptInput.setText("");
                            ingredInput.setText("");
                            ingredInput.setText("");
                            cookInstructionInput.setText("");
                            hr.setText("");
                            min.setText("");
                            sec.setText("");
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
                            ing.clear();
                            instruct.clear();

                            Snackbar uploadedMSG = Snackbar.make(view, "Recipe has been uploaded to the database", 500);
                            uploadedMSG.show();

                            ArrayAdapter<String> arrayInstructions = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1,instruct);
                            instructions.setAdapter(arrayInstructions);

                            ArrayAdapter<String> arrayIngredients = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1,ing);
                            ingredients.setAdapter(arrayIngredients);

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
                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Grains").document(recipeTitle).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            title.setText("");
                            descriptInput.setText("");
                            ingredInput.setText("");
                            ingredInput.setText("");
                            cookInstructionInput.setText("");
                            hr.setText("");
                            min.setText("");
                            sec.setText("");
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
                            ing.clear();
                            instruct.clear();

                            ArrayAdapter<String> arrayInstructions = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1,instruct);
                            instructions.setAdapter(arrayInstructions);

                            ArrayAdapter<String> arrayIngredients = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1,ing);
                            ingredients.setAdapter(arrayIngredients);

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
                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Dairy").document(recipeTitle).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            title.setText("");
                            descriptInput.setText("");
                            ingredInput.setText("");
                            ingredInput.setText("");
                            cookInstructionInput.setText("");
                            hr.setText("");
                            min.setText("");
                            sec.setText("");
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
                            ing.clear();
                            instruct.clear();

                            ArrayAdapter<String> arrayInstructions = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1,instruct);
                            instructions.setAdapter(arrayInstructions);

                            ArrayAdapter<String> arrayIngredients = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1,ing);
                            ingredients.setAdapter(arrayIngredients);

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
                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Vegetables").document(recipeTitle).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            title.setText("");
                            descriptInput.setText("");
                            ingredInput.setText("");
                            ingredInput.setText("");
                            cookInstructionInput.setText("");
                            hr.setText("");
                            min.setText("");
                            sec.setText("");
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
                            ing.clear();
                            instruct.clear();

                            ArrayAdapter<String> arrayInstructions = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1,instruct);
                            instructions.setAdapter(arrayInstructions);

                            ArrayAdapter<String> arrayIngredients = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1,ing);
                            ingredients.setAdapter(arrayIngredients);

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
                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Protein").document(recipeTitle).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            title.setText("");
                            descriptInput.setText("");
                            ingredInput.setText("");
                            ingredInput.setText("");
                            cookInstructionInput.setText("");
                            hr.setText("");
                            min.setText("");
                            sec.setText("");
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
                            ing.clear();
                            instruct.clear();

                            ArrayAdapter<String> arrayInstructions = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1,instruct);
                            instructions.setAdapter(arrayInstructions);

                            ArrayAdapter<String> arrayIngredients = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1,ing);
                            ingredients.setAdapter(arrayIngredients);

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
                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Breakfast").document(recipeTitle).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            title.setText("");
                            descriptInput.setText("");
                            ingredInput.setText("");
                            ingredInput.setText("");
                            cookInstructionInput.setText("");
                            hr.setText("");
                            min.setText("");
                            sec.setText("");
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
                            ing.clear();
                            instruct.clear();

                            ArrayAdapter<String> arrayInstructions = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1,instruct);
                            instructions.setAdapter(arrayInstructions);

                            ArrayAdapter<String> arrayIngredients = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1,ing);
                            ingredients.setAdapter(arrayIngredients);

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
                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Lunch").document(recipeTitle).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            title.setText("");
                            descriptInput.setText("");
                            ingredInput.setText("");
                            ingredInput.setText("");
                            cookInstructionInput.setText("");
                            hr.setText("");
                            min.setText("");
                            sec.setText("");
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
                            ing.clear();
                            instruct.clear();

                            ArrayAdapter<String> arrayInstructions = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1,instruct);
                            instructions.setAdapter(arrayInstructions);

                            ArrayAdapter<String> arrayIngredients = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1,ing);
                            ingredients.setAdapter(arrayIngredients);

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
                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Dinner").document(recipeTitle).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            title.setText("");
                            descriptInput.setText("");
                            ingredInput.setText("");
                            ingredInput.setText("");
                            cookInstructionInput.setText("");
                            hr.setText("");
                            min.setText("");
                            sec.setText("");
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
                            ing.clear();
                            instruct.clear();

                            ArrayAdapter<String> arrayInstructions = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1,instruct);
                            instructions.setAdapter(arrayInstructions);

                            ArrayAdapter<String> arrayIngredients = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1,ing);
                            ingredients.setAdapter(arrayIngredients);

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
                    db.collection("Login").document("User").collection(username).document("userInfo").collection("Recipes").document("Categories").collection("Snacks").document(recipeTitle).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            title.setText("");
                            descriptInput.setText("");
                            ingredInput.setText("");
                            ingredInput.setText("");
                            cookInstructionInput.setText("");
                            hr.setText("");
                            min.setText("");
                            sec.setText("");
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
                            ing.clear();
                            instruct.clear();

                            ArrayAdapter<String> arrayInstructions = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1,instruct);
                            instructions.setAdapter(arrayInstructions);

                            ArrayAdapter<String> arrayIngredients = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1,ing);
                            ingredients.setAdapter(arrayIngredients);

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