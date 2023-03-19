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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
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
        EditText descriptInput = (EditText) view.findViewById(R.id.descriptionInput);
        EditText ingredInput = (EditText) view.findViewById(R.id.ingredientsInput);
        EditText cookInstructionInput = (EditText) view.findViewById(R.id.cookInstructionInput);
        EditText cookTimeInput = (EditText) view.findViewById(R.id.cookTimeInput);
        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String descText = descriptInput.getText().toString();
                String ingredText = ingredInput.getText().toString();
                String cookInstrText = cookInstructionInput.getText().toString();
                String CookTimeText = cookTimeInput.getText().toString();
                Float rateScore = ratingBar.getRating();



                FirebaseFirestore db = FirebaseFirestore.getInstance();

                Map<String,Object> user = new HashMap<>();
                user.put("Description",descText);
                user.put("Ingredients",ingredText);
                user.put("Cooking Instructions",cookInstrText);
                user.put("Cook Time",CookTimeText);
                user.put("Rating",rateScore);

                db.collection("Recipe").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
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
        });
    }


}