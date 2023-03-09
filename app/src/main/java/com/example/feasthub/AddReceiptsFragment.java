package com.example.feasthub;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;


public class AddReceiptsFragment extends Fragment {
    private View view;
    private ImageView image;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_receipts, container, false);
        submitButton();
        return view;
    }

    private void submitButton(){
        Button submitButton = (Button) view.findViewById(R.id.addRecipeSubmitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar uploadedMSG = Snackbar.make(view, "Recipe has been uploaded to the database", 500);
                uploadedMSG.show();
            }
        });
    }





}