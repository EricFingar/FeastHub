package com.example.feasthub;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_screen, container, false);
        SignIn();

        Button login = (Button) view.findViewById(R.id.loginInButton);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignIn();
            }
        });
        return view;
    }

    private void SignIn() {
        EditText emailEntered = (EditText) view.findViewById(R.id.emailInput);
        EditText passwordEntered = (EditText) view.findViewById(R.id.passwordInput);

        String email = emailEntered.getText().toString();
        String password = passwordEntered.getText().toString();

        db.collection("Login").document("User").collection(email).document("userInfo").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot doucment = task.getResult();
                    if(doucment.exists()){
                        if( email.equals(doucment.getString("email"))){
                            if(password.equals(doucment.getString("password"))){

                            }
                            else{
                                Snackbar uploadedMSG = Snackbar.make(view, "Email or Password is incorrect", 500);
                                uploadedMSG.show();
                            }
                        }
                        else{
                            Snackbar uploadedMSG = Snackbar.make(view, "Email or Password is incorrect", 500);
                            uploadedMSG.show();
                        }
                    }
                }
                else{
                    Snackbar uploadedMSG = Snackbar.make(view, "There is no user under that email", 500);
                    uploadedMSG.show();
                }
            }
        });
    }

}
