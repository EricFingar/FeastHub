package com.example.feasthub;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

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
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar uploadedMSG = Snackbar.make(view, "Recipe has been uploaded to the database", 500);
                uploadedMSG.show();
            }
        });
    }


}