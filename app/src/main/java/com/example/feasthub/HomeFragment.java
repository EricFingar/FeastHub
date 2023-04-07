package com.example.feasthub;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeFragment extends Fragment {
    private View view;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    Calendar cal = Calendar.getInstance();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_home, container, false);
        breakfastButton();
        lunchButton();
        dinnerButton();
        snacksButton();
        recentButton();
        timeOfDayButton();
        getTODRecipeCard();
        return view;


    }

    private void breakfastButton(){
        ImageButton breakfast_btn = (ImageButton) view.findViewById(R.id.breakfast_botton);
        breakfast_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, new BreakfastFragment());
                fr.commit();
            }
        });
    }

    private void lunchButton(){
        ImageButton breakfast_btn = (ImageButton) view.findViewById(R.id.lunch_botton);
        breakfast_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, new LunchFragment());
                fr.commit();
            }
        });
    }

    private void dinnerButton(){
        ImageButton breakfast_btn = (ImageButton) view.findViewById(R.id.dinner_botton);
        breakfast_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, new DinnerFragment());
                fr.commit();
            }
        });
    }

    private void snacksButton(){
        ImageButton breakfast_btn = (ImageButton) view.findViewById(R.id.snacks_button);
        breakfast_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, new SnacksFragment());
                fr.commit();
            }
        });
    }

    private void recentButton(){
        ImageButton breakfast_btn = (ImageButton) view.findViewById(R.id.recent_more_botton);
        breakfast_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, new RecentFragment());
                fr.commit();
            }
        });
    }
    private void timeOfDayButton(){
        ImageButton breakfast_btn = (ImageButton) view.findViewById(R.id.time_of_day_button);
        breakfast_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame_layout, new TimeOfDayFragment());
                fr.commit();
            }
        });
    }

    private void getTODRecipeCard(){

        GridView recipeCards = (GridView) view.findViewById(R.id.TODGrid);
        ArrayList<recipeModel> recipeModelArrayList = new ArrayList<recipeModel>();

        int currentHour = cal.get(Calendar.HOUR_OF_DAY);

        if (7 <= currentHour && currentHour < 11) {
            for(int i =0; i < 2; i++) {
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
        if (11 <= currentHour && currentHour < 13) {
            for(int i =0; i < 2; i++) {
                int caseNum = (int)Math.random() * 2;
                switch(caseNum){
                    case 1:db.collection("Lunch").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            //List<String> ids = new ArrayList<>();
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String id = document.getId();
                                    recipeModelArrayList.add(new recipeModel(id, R.drawable.lunch));
                                }
                            }
                            recipeGVAdapter adapter = new recipeGVAdapter(view.getContext(), recipeModelArrayList);
                            recipeCards.setAdapter(adapter);
                        }
                    });

                    case 2:db.collection("Snacks").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            //List<String> ids = new ArrayList<>();
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String id = document.getId();
                                    recipeModelArrayList.add(new recipeModel(id, R.drawable.snacks));
                                }
                            }
                            recipeGVAdapter adapter = new recipeGVAdapter(view.getContext(), recipeModelArrayList);
                            recipeCards.setAdapter(adapter);
                        }
                    });
                }
            }



        }
        if (13 <= currentHour && currentHour < 17) {
            for(int i =0; i < 2; i++) {
                db.collection("Snacks").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //List<String> ids = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String id = document.getId();
                                recipeModelArrayList.add(new recipeModel(id, R.drawable.snacks));
                            }
                        }
                        recipeGVAdapter adapter = new recipeGVAdapter(view.getContext(), recipeModelArrayList);
                        recipeCards.setAdapter(adapter);
                    }
                });
            }
        }
        if (17 <= currentHour && currentHour < 19) {
            for(int i =0; i < 2; i++) {
                int caseNum = (int)Math.random() * 2;
                switch(caseNum){
                    case 1:db.collection("Dinner").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            //List<String> ids = new ArrayList<>();
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String id = document.getId();
                                    recipeModelArrayList.add(new recipeModel(id, R.drawable.dinner));
                                }
                            }
                            recipeGVAdapter adapter = new recipeGVAdapter(view.getContext(), recipeModelArrayList);
                            recipeCards.setAdapter(adapter);
                        }
                    });

                    case 2:db.collection("Snacks").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            //List<String> ids = new ArrayList<>();
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String id = document.getId();
                                    recipeModelArrayList.add(new recipeModel(id, R.drawable.snacks));
                                }
                            }
                            recipeGVAdapter adapter = new recipeGVAdapter(view.getContext(), recipeModelArrayList);
                            recipeCards.setAdapter(adapter);
                        }
                    });
                }



            }
        }
        if (19 <= currentHour && currentHour > 0) {
            for(int i =0; i < 2; i++) {
                db.collection("Snacks").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //List<String> ids = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String id = document.getId();
                                recipeModelArrayList.add(new recipeModel(id, R.drawable.snacks));
                            }
                        }
                        recipeGVAdapter adapter = new recipeGVAdapter(view.getContext(), recipeModelArrayList);
                        recipeCards.setAdapter(adapter);
                    }
                });
            }
        }
    }

}