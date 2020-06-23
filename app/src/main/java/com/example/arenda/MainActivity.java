package com.example.arenda;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.TooltipCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arenda.adapters.RecyclerAdapter;
import com.example.arenda.fragment.FavoriteFragment;
import com.example.arenda.fragment.MainFragment;
import com.example.arenda.fragment.ProfileFragment;
import com.example.arenda.fragment.RegisterFragment;
import com.example.arenda.pogo.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db;
    Fragment mainFragment;
    Fragment profileFragment;
    Fragment registerFragment;
    Fragment favoriteFragment;
    BottomNavigationView bottomNavigationView;
    private FirebaseAuth mAuth;

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {

            super.onBackPressed();

        } else {

            getSupportFragmentManager().popBackStack();

        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseFirestore.getInstance();
        mainFragment = new MainFragment();
        profileFragment = new ProfileFragment();
        registerFragment = new RegisterFragment();
        favoriteFragment = new FavoriteFragment();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        createBottomNavigationListener();
        bottomNavigationView.setSelectedItemId(R.id.action_search);
        mAuth = FirebaseAuth.getInstance();



    }

    private void createBottomNavigationListener() {


        bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);

        BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();

                ft.addToBackStack("1");


                switch (item.getItemId()) {
                    case R.id.action_search:

                        ft.replace(R.id.frame, mainFragment);
                        ft.commit();

                        bottomNavigationView.getMenu().getItem(0).setChecked(true);
                        break;
                    case R.id.action_settings:



                        bottomNavigationView.getMenu().getItem(1).setChecked(true);
                        if(mAuth.getCurrentUser()!=null){
                            Intent intent = new Intent(MainActivity.this, RoomOrHome.class);
                            startActivity(intent);
                        }
                        else {
                            ft.replace(R.id.frame, registerFragment);
                            ft.commit();
                        }
                        break;
                    case R.id.action_navigation:

                        bottomNavigationView.getMenu().getItem(2).setChecked(true);
                        if(mAuth.getCurrentUser()!=null){
                            ft.replace(R.id.frame, favoriteFragment);
                            ft.commit();}
                        else {
                            ft.replace(R.id.frame, registerFragment);
                            ft.commit();
                        }
                        break;
                    case R.id.action_navigation2:

                        if(mAuth.getCurrentUser()!=null){
                        ft.replace(R.id.frame, profileFragment);
                        ft.commit();}
                        else {
                            ft.replace(R.id.frame, registerFragment);
                            ft.commit();
                        }
                        bottomNavigationView.getMenu().getItem(3).setChecked(true);
                        break;

                }
                TooltipCompat.setTooltipText(findViewById(R.id.action_search), null);
                TooltipCompat.setTooltipText(findViewById(R.id.action_settings), null);
                TooltipCompat.setTooltipText(findViewById(R.id.action_navigation2), null);
                TooltipCompat.setTooltipText(findViewById(R.id.action_navigation), null);

                return true;


            }
        };

        bottomNavigationView.setOnNavigationItemSelectedListener(listener);

    }





}
