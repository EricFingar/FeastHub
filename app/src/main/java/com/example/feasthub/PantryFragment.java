package com.example.feasthub;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;


public class PantryFragment extends Fragment {

    private View view;
    private FirebaseFirestore data = FirebaseFirestore.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_pantry, container, false);
        getFruitRecipe();
        return view;
    }

    private void getFruitRecipe(){
        ArrayList<String> name = new ArrayList<String>();
        ArrayList<String> cookT = new ArrayList<String>();
        ArrayList<String> cookInst = new ArrayList<String>();
        ArrayList<String> descript = new ArrayList<String>();
        ArrayList<String> Ingr = new ArrayList<String>();
        ArrayList<String> rate = new ArrayList<String>();

        ListView fruit = view.findViewById(R.id.fruitList);
        data.collection("RecipeTest").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){

                }
                for(DocumentChange documentChange : value.getDocumentChanges()){
                    String nameRecipe = documentChange.getDocument().getData().get("Title").toString();
                    name.add(nameRecipe);
                    String CookTime = documentChange.getDocument().getData().get("Cook Time").toString();
                    cookT.add(CookTime);
                    String CookingInst = documentChange.getDocument().getData().get("Cooking Instructions").toString();
                    cookInst.add(CookingInst);
                    String Descript = documentChange.getDocument().getData().get("Description").toString();
                    descript.add(Descript);
                    String Ingred = documentChange.getDocument().getData().get("Ingredients").toString();
                    Ingr.add(Ingred);
                    String rating = documentChange.getDocument().getData().get("Rating").toString();
                    rate.add(rating);

                    fruitAdapter fruitAd = new fruitAdapter(name);
                    fruit.setAdapter(fruitAd);


                }
            }
        });

    }
}

class fruitAdapter extends BaseAdapter {

    List<String> items;

    public fruitAdapter(List<String> items){
        super();
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return items.get(i).hashCode();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView textView = new TextView(view.getContext());
        textView.setText(items.get(i));
        return textView;
    }
}