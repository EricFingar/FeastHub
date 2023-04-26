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
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private View view;
    private String email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_screen, container, false);

        Button loginButton = view.findViewById(R.id.loginInButton);
        Button registerButton = view.findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, new registerUser());
                fr.commit();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleLogin();
            }
        });

        return view;
    }

    private void handleLogin() {
        EditText emailEditText = view.findViewById(R.id.emailInput);
        EditText passwordEditText = view.findViewById(R.id.passwordInput);

        email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Please enter an email address");
            emailEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Please enter a password");
            passwordEditText.requestFocus();
            return;
        }

        db.collection("Login").document("User").collection(email).document("userInfo").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String storedPassword = document.getString("password");
                        if (password.equals(storedPassword)) {
                            // Start the HomeFragment upon successful login
                            Bundle args = new Bundle();
                            HomeFragment loginSuccess = new HomeFragment();
                            String[] array = {email,"True"};
                            args.putStringArray("Key", array);
                            loginSuccess.setArguments(args);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, loginSuccess).commit();
                        } else {
                            Snackbar.make(view, "Incorrect email or password", Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        Snackbar.make(view, "There is no user with that email", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(view, "An error occurred. Please try again later.", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}
