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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_time_of_day, container, false);
        backButton();
        getTODRecipeCard();
        return view;
    }

    private void backButton(){
        ImageButton back_btn = (ImageButton) view.findViewById(R.id.timeOfDayBackButton);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, new HomeFragment());
                fr.commit();
            }
        });
    }

    private void getTODRecipeCard(){

        GridView recipeCards = (GridView) view.findViewById(R.id.TODGrid);
        ArrayList<recipeModel> recipeModelArrayList = new ArrayList<recipeModel>();

        int currentHour = cal.get(Calendar.HOUR_OF_DAY);

        if (7 <= currentHour && currentHour < 11) {
            db.collection("Breakfast").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    //List<String> ids = new ArrayList<>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String id = document.getId();
                            recipeModelArrayList.add(new recipeModel(id, R.drawable.breakfest));
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
                    String[] array = {recipeModelArrayList.get(i).getRecipe_name().toString(), "Breakfast","False","True"};
                    args.putStringArray("RecipeName", array);
                    recipe.setArguments(args);

                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.frame_layout, recipe);
                    fr.commit();
                }
            });
        }
        if (11 <= currentHour && currentHour < 13) {
            db.collection("Lunch").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    //List<String> ids = new ArrayList<>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String id = document.getId();
                            recipeModelArrayList.add(new recipeModel(id, R.drawable.lunch));
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
                    String[] array = {recipeModelArrayList.get(i).getRecipe_name().toString(), "Lunch","False","True"};
                    args.putStringArray("RecipeName", array);
                    recipe.setArguments(args);

                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.frame_layout, recipe);
                    fr.commit();
                }
            });

            db.collection("Snacks").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                    String[] array = {recipeModelArrayList.get(i).getRecipe_name().toString(), "Snacks","False","True"};
                    args.putStringArray("RecipeName", array);
                    recipe.setArguments(args);

                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.frame_layout, recipe);
                    fr.commit();
                }
            });
        }
        if (13 <= currentHour && currentHour < 17) {
            db.collection("Snacks").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                    String[] array = {recipeModelArrayList.get(i).getRecipe_name().toString(), "Snacks","False","True"};
                    args.putStringArray("RecipeName", array);
                    recipe.setArguments(args);

                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.frame_layout, recipe);
                    fr.commit();
                }
            });
        }
        if (17 <= currentHour && currentHour < 19) {
            db.collection("Dinner").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    //List<String> ids = new ArrayList<>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String id = document.getId();
                            recipeModelArrayList.add(new recipeModel(id, R.drawable.dinner));
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
                    String[] array = {recipeModelArrayList.get(i).getRecipe_name().toString(), "Dinner","False","True"};
                    args.putStringArray("RecipeName", array);
                    recipe.setArguments(args);

                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.frame_layout, recipe);
                    fr.commit();
                }
            });

            db.collection("Snacks").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                    String[] array = {recipeModelArrayList.get(i).getRecipe_name().toString(), "Snacks","False","True"};
                    args.putStringArray("RecipeName", array);
                    recipe.setArguments(args);

                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.frame_layout, recipe);
                    fr.commit();
                }
            });
        }
        if (19 <= currentHour && currentHour > 0) {
            db.collection("Snacks").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                    String[] array = {recipeModelArrayList.get(i).getRecipe_name().toString(), "Snacks","False","True"};
                    args.putStringArray("RecipeName", array);
                    recipe.setArguments(args);

                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.frame_layout, recipe);
                    fr.commit();
                }
            });
        }
    }

    public static class editRecipeFragment extends Fragment {
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
                    String descText = descriptInput.getText().toString();
                    int CookTimeHrText = Integer.parseInt(hr.getText().toString());
                    int CookTimeMinText = Integer.parseInt(min.getText().toString());
                    int CookTimeSecText = Integer.parseInt(sec.getText().toString());
                    Float rateScore = ratingBar.getRating();
                    String recipeTitle = title.getText().toString();



                    FirebaseFirestore db = FirebaseFirestore.getInstance();



                    Map<String,Object> user = new HashMap<>();
                    user.put("Title", recipeTitle);
                    user.put("Description",descText);
                    user.put("Ingredients",ing);
                    user.put("Cooking Instructions",instruct);
                    user.put("Cook Time HR",CookTimeHrText);
                    user.put("Cook Time Min", CookTimeMinText);
                    user.put("Cook Time Sec", CookTimeSecText);
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
                        db.collection("Grains").document(recipeTitle).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                        db.collection("Dairy").document(recipeTitle).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                        db.collection("Vegetables").document(recipeTitle).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                        db.collection("Protein").document(recipeTitle).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                        db.collection("Breakfast").document(recipeTitle).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                        db.collection("Lunch").document(recipeTitle).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                        db.collection("Dinner").document(recipeTitle).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                        db.collection("Snacks" +
                                "").document(recipeTitle).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
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



    }
}