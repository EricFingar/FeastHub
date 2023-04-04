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
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private View view;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

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



}