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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class ProfileFragment extends Fragment {
    private View view;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_profile, container, false);
        String[] key = getArguments().getStringArray("Key");
        username = key[0];

        EditText name = (EditText) view.findViewById(R.id.profileName);
        EditText bio = (EditText) view.findViewById(R.id.profileBio);
        EditText email = (EditText) view.findViewById(R.id.email);
        EditText phone = (EditText) view.findViewById(R.id.profilePhoneNumber);

        db.collection("Login").document("User").collection(username).document("userInfo").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        String nameDB = document.getString("name");
                        name.setText(nameDB);
                        String bioDB = document.getString("bio");
                        bio.setText(bioDB);
                        String emailDB = document.getString(username);
                        email.setText(emailDB);
                        String phoneDB = document.getString("phone number");
                        phone.setText(phoneDB);
                    }
                }
            }
        });


        backButton();
        editProfileButton();
        return view;
    }

    private void backButton(){
        ImageButton back_btn = (ImageButton) view.findViewById(R.id.profileBackButton);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                AccountFragment account = new AccountFragment();
                String[] array = {username};
                args.putStringArray("Key", array);
                account.setArguments(args);
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, account);
                fr.commit();
            }
        });
    }

    private void editProfileButton(){
        Button editButton = (Button) view.findViewById(R.id.editProfileButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                ProfileEditFragment profileEdit = new ProfileEditFragment();
                String[] array = {username};
                args.putStringArray("Key", array);
                profileEdit.setArguments(args);
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, profileEdit);
                fr.commit();
            }
        });
    }
}