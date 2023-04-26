package com.example.feasthub;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class ProfileEditFragment extends Fragment {
    private View view;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String dbPassword;
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
                        String emailDB = username;
                        email.setText(emailDB);
                        String phoneDB = document.getString("phone number");
                        phone.setText(phoneDB);
                        dbPassword = document.getString("password");
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
                Bundle args = new Bundle();
                ProfileFragment profile = new ProfileFragment();
                String[] array = {username};
                args.putStringArray("Key", array);
                profile.setArguments(args);
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, profile);
                fr.commit();
            }
        });
    }

    private void submitButton(){
        Button submitButton = (Button) view.findViewById(R.id.editProfileButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText name = (EditText) view.findViewById(R.id.profileName);
                EditText bio = (EditText) view.findViewById(R.id.profileBio);
                EditText phone = (EditText) view.findViewById(R.id.profilePhoneNumber);
                EditText passwordUpdate = (EditText) view.findViewById(R.id.newPasswordInput);
                EditText passwordComfirmed = (EditText) view.findViewById(R.id.comfirmNewPassword);
                EditText oldPassowrd = (EditText) view.findViewById(R.id.oldPassword);


                String nameDB = name.getText().toString();
                String bioDB = bio.getText().toString();
                String phoneDB = phone.getText().toString();
                String newPassword = passwordUpdate.getText().toString();
                String comfirmedPassword = passwordComfirmed.getText().toString();
                String oldpassword = oldPassowrd.getText().toString();


                if(!newPassword.equals("") && !comfirmedPassword.equals("")){
                    if(oldpassword.equals(dbPassword)) {
                        if (newPassword.equals(comfirmedPassword)) {
                            Map<String, Object> user = new HashMap<>();
                            user.put("bio", bioDB);
                            user.put("name", nameDB);
                            user.put("phone number", phoneDB);
                            user.put("password", newPassword);


                            db.collection("Login").document("User").collection(username).document("userInfo").update(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Snackbar uploadedMSG = Snackbar.make(view, "Profile has been updated in the database", 500);
                                    uploadedMSG.show();
                                }
                            });

                        } else {
                            Snackbar uploadedMSG = Snackbar.make(view, "New Password and Comfirmed Password do not match", 500);
                            uploadedMSG.show();
                        }
                    }
                    else{
                        Snackbar uploadedMSG = Snackbar.make(view, "Current Password is incorrect", 500);
                        uploadedMSG.show();
                    }
                }
                else{
                    Map<String,Object> user = new HashMap<>();
                    user.put("bio", bioDB);
                    user.put("name",nameDB);
                    user.put("phone number",phoneDB);


                    db.collection("Login").document("User").collection(username).document("userInfo").update(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Snackbar uploadedMSG = Snackbar.make(view, "Profile has been updated in the database", 500);
                            uploadedMSG.show();
                        }
                    });
                }

            }

        });
    }
}