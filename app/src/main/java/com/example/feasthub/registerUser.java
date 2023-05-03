package com.example.feasthub;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * A fragment to register a new user to the application.
 */
public class registerUser extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private View view;
    private String email;
    private String name;
    private String bio;
    private String password;
    private Integer phoneNumber;

    /**
     * Creates and returns the view hierarchy associated with the fragment.
     * @param inflater the LayoutInflater object that can be used to inflate any views in the fragment
     * @param container the parent view that the fragment's UI should be attached to
     * @param savedInstanceState the saved state of the fragment
     * @return the view hierarchy associated with the fragment
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.register_user, container, false);

        Button register = view.findViewById(R.id.registerButton);
        Button back = view.findViewById(R.id.BackButton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleRegisterUser();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, new LoginFragment());
                fr.commit();
            }
        });
        return view;
    }

    /**
     * Handles the registration of a new user by retrieving the user details entered by the user,
     * validating the inputs, and adding the user to the Firebase Firestore database.
     */
    private void handleRegisterUser(){
        EditText name = (EditText) view.findViewById(R.id.nameInput);
        EditText bio = (EditText) view.findViewById(R.id.enterBioInput);
        EditText email = (EditText) view.findViewById(R.id.emailInput);
        EditText password = (EditText) view.findViewById(R.id.passwordInput);
        EditText phoneNumber = (EditText) view.findViewById(R.id.phoneNumberInput);

        String nameText = name.getText().toString();
        String bioText = bio.getText().toString();
        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();

        String phoneNumberText = phoneNumber.getText().toString();

        if (TextUtils.isEmpty(nameText)) {
            name.setError("Please enter an name address");
            name.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(bioText)) {
            bio.setError("Please enter an bio address");
            bio.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(emailText)) {
            email.setError("Please enter an email address");
            email.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(passwordText)) {
            password.setError("Please enter an password address");
            password.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(phoneNumberText)) {
            phoneNumber.setError("Please enter an phone number address");
            phoneNumber.requestFocus();
            return;
        }

        Map<String,Object> user = new HashMap<>();
        user.put("bio", bioText);
        user.put("name",nameText);
        user.put("password",passwordText);
        user.put("phone number",phoneNumberText);


        db.collection("Login").document("User").collection(emailText).document("userInfo").set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Snackbar uploadedMSG = Snackbar.make(view, "User has been added", 500);
                uploadedMSG.show();

                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, new LoginFragment());
                fr.commit();
            }

        });
    }

}
