package com.example.feasthub;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.snackbar.Snackbar;


public class ProfileFragment extends Fragment {
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_profile, container, false);
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