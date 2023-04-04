package com.example.feasthub;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class BreakfastFragment extends Fragment {

    private View view;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_breakfast, container, false);
        backButton();
        getBreakfastRecipeCard();
        return view;
    }

    private void backButton(){
        ImageButton back_btn = (ImageButton) view.findViewById(R.id.breakfastBackButton);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, new HomeFragment());
                fr.commit();
            }
        });
    }

    private void getBreakfastRecipeCard(){
        GridView recipeCards = (GridView) view.findViewById(R.id.breakfastGrid);
        ArrayList<recipeModel> recipeModelArrayList = new ArrayList<recipeModel>();
        db.collection("Breakfast").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                //List<String> ids = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id = document.getId();
                        recipeModelArrayList.add(new recipeModel(id, R.drawable.breakfest));
                    }
                }
                recipeGVAdapter adapter = new recipeGVAdapter(view.getContext(), recipeModelArrayList);
                recipeCards.setAdapter(adapter);
            }
        });
    }

}