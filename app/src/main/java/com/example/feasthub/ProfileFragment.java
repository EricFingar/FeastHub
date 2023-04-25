package com.example.feasthub;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class ProfileFragment extends Fragment {
    private View view;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_profile, container, false);

        EditText name = (EditText) view.findViewById(R.id.nameInput);
        EditText bio = (EditText) view.findViewById(R.id.bio);
        EditText email = (EditText) view.findViewById(R.id.email);
        EditText phone = (EditText) view.findViewById(R.id.phoneNumber);

        db.collection("Login").document("User").collection("testuser@gmail.com").document("userInfo").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        String nameDB = document.getString("name");
                        name.setText(nameDB);
                        String bioDB = document.getString("bio");
                        bio.setText(bioDB);
                        String emailDB = "testuser@gmail.com";
                        email.setText(emailDB);
                        String phoneDB = document.getString("phone number");
                        phone.setText(phoneDB);
                    }
                }
            }
        });


        backButton();
        submitButton();
        return view;
    }

    private void backButton(){
        ImageButton back_btn = (ImageButton) view.findViewById(R.id.profileBackButton);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, new AccountFragment());
                fr.commit();
            }
        });
    }

    private void submitButton(){
        Button submitButton = (Button) view.findViewById(R.id.profileSubmitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar updateMSG = Snackbar.make(view, "Profile has been updated", 500);
                updateMSG.show();
            }
        });
    }
}