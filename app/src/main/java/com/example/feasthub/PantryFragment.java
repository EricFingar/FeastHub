package com.example.feasthub;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class PantryFragment extends Fragment {

    private View view;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_pantry, container, false);
        getFruitRecipe();
        getGrainRecipe();
        getDairyRecipe();
        getProteinRecipe();
        getVegetablesRecipe();
        return view;
    }

    private void getFruitRecipe() {
        db.collection("Fruits").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<String> ids = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id = document.getId();
                        ids.add(id);
                    }
                }
                ListView fruitListView = (ListView) view.findViewById(R.id.fruitList);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, ids);
                fruitListView.setAdapter(arrayAdapter);
            }
        });
    }

    private void getGrainRecipe(){
        db.collection("Grains").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<String> ids = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id = document.getId();
                        ids.add(id);
                    }
                }
                ListView grainListView = (ListView) view.findViewById(R.id.grainList);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, ids);
                grainListView.setAdapter(arrayAdapter);
            }
        });
    }

    private void getVegetablesRecipe(){
        db.collection("Vegetables").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<String> ids = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id = document.getId();
                        ids.add(id);
                    }
                }
                ListView vegetablesListView = (ListView) view.findViewById(R.id.vegetablesList);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, ids);
                vegetablesListView.setAdapter(arrayAdapter);
            }
        });
    }

    private void getProteinRecipe(){
        db.collection("Protein").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<String> ids = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id = document.getId();
                        ids.add(id);
                    }
                }
                ListView proteinListView = (ListView) view.findViewById(R.id.proteinList);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, ids);
                proteinListView.setAdapter(arrayAdapter);
            }
        });
    }

    private void getDairyRecipe(){
        db.collection("Dairy").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<String> ids = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id = document.getId();
                        ids.add(id);
                    }
                }
                ListView dairyListView = (ListView) view.findViewById(R.id.dairyList);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, ids);
                dairyListView.setAdapter(arrayAdapter);
            }
        });
    }
}
