package com.example.feasthub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.feasthub.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        replaceFragment(new LoginFragment());
        bottom_navBar();

    }


    private void bottom_navBar(){
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.home:
                    Bundle args = new Bundle();
                    HomeFragment loginSuccess = new HomeFragment();
                    String[] array = {username};
                    args.putStringArray("Key", array);
                    loginSuccess.setArguments(args);
                    replaceFragment(loginSuccess);
                    break;
                case R.id.addReceipts:
                    Bundle args1 = new Bundle();
                    AddReceiptsFragment add = new AddReceiptsFragment();
                    String[] array1 = {username};
                    args1.putStringArray("Key", array1);
                    add.setArguments(args1);
                    replaceFragment(add);
                    break;
                case R.id.pantry:
                    Bundle args2 = new Bundle();
                    PantryFragment pantry = new PantryFragment();
                    String[] array2 = {username};
                    args2.putStringArray("Key", array2);
                    pantry.setArguments(args2);
                    replaceFragment(pantry);
                    break;
                case R.id.account:
                    Bundle args3 = new Bundle();
                    AccountFragment account = new AccountFragment();
                    String[] array3 = {username};
                    args3.putStringArray("Key", array3);
                    account.setArguments(args3);
                    replaceFragment(account);
                    break;
            }

            return true;
        });
    }

    public void setUsername(String username){
        this.username = username;
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}