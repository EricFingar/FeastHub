package com.example.feasthub;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;



/**
 * A fragment representing the user's profile, containing information such as their name, bio, email, and phone number.
 */
public class ProfileFragment extends Fragment {
    private View view;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String username;

    /**
     *Creates the view for the ProfileFragment, inflating the fragment_profile.xml layout file and populating it with the user's information retrieved from the Firebase database.
     * @param inflater the LayoutInflater object that can be used to inflate any views in the fragment
     * @param container the parent view that the fragment UI should be attached to
     * @param savedInstanceState the saved instance state of the fragment
     * @return the inflated view for this fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_profile, container, false);
        String[] key = getArguments().getStringArray("Key");
        username = key[0];

        TextView name = (TextView) view.findViewById(R.id.profileName);
        TextView bio = (TextView) view.findViewById(R.id.profileBio);
        TextView email = (TextView) view.findViewById(R.id.emailDB);
        TextView phone = (TextView) view.findViewById(R.id.profilePhoneNumber);

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
                        email.setText(username.toString());

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

    /**
     * Sets up the functionality of the back button, which returns the user to the AccountFragment.
     */
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

    /**
     * Sets up the functionality of the edit profile button, which launches the ProfileEditFragment to allow the user to edit their profile.
     */
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