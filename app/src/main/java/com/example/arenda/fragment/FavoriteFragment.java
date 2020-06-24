package com.example.arenda.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.arenda.R;
import com.example.arenda.adapters.RecyclerAdapter;
import com.example.arenda.pogo.Home;
import com.example.arenda.pogo.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class FavoriteFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;
    private List<Home> homeList;
    private List<Home> myHomeList;
    private List<String> homeID;
    private UserProfile user;
    private List<String> myHomeID;
    private BottomNavigationView bottomNavigationView;
    private CardView cardViewFavoriteHome;
    private CardView cardViewMyHome;
    private View savedView;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int i = 1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedView == null) {
            savedView = inflater.inflate(R.layout.fragment_favorite, container, false);
        }

        return savedView;
    }

    @Override
    public void onStart() {
        super.onStart();
        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        cardViewMyHome = getActivity().findViewById(R.id.cardViewMyHome);
        cardViewFavoriteHome = getActivity().findViewById(R.id.cardViewFavoriteHome);
        if(i==1){
        cardViewFavoriteHome.setCardBackgroundColor(Color.GRAY);
        cardViewMyHome.setCardBackgroundColor(Color.WHITE);}
        progressBar = getActivity().findViewById(R.id.progressBarFavorite);
        swipeRefreshLayout = getActivity().findViewById(R.id.swipeContainerMain);

        cardViewMyHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 2;
                progressBar.setVisibility(View.VISIBLE);
                getMyHomeList();
                cardViewMyHome.setCardBackgroundColor(Color.GRAY);
                cardViewFavoriteHome.setCardBackgroundColor(Color.WHITE);
            }
        });
        cardViewFavoriteHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 1;
                cardViewFavoriteHome.setCardBackgroundColor(Color.GRAY);
                cardViewMyHome.setCardBackgroundColor(Color.WHITE);
                progressBar.setVisibility(View.VISIBLE);
                getHomeList();
            }
        });
        homeList = new ArrayList<>();
        myHomeID = new ArrayList<>();
        myHomeList = new ArrayList<>();
        bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);

        db.collection("UserProfile").document(firebaseAuth.getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                user = task.getResult().toObject(UserProfile.class);
                homeID = user.getFavoritesHome();
                myHomeID = user.getMyHome();
                if (recyclerView == null || recyclerAdapter == null) {
                    createRecyclerView();
                    getHomeList();
                }


            }
        });


        bottomNavigationView.getMenu().getItem(2).setChecked(true);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (i == 1) {
                    progressBar.setVisibility(View.VISIBLE);
                    getHomeList();

                } else if (i == 2) {
                    progressBar.setVisibility(View.VISIBLE);
                    getMyHomeList();
                }

            }
        });


    }


    private void getHomeList() {
        progressBar.setVisibility(View.VISIBLE);
        homeList.clear();
        for (String id : homeID) {
            if (id.equals(homeID.get(homeID.size() - 1))) {
                db.collection("HomeList").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        Home home1 = task.getResult().toObject(Home.class);
                        homeList.add(home1);
                        recyclerAdapter.setHomeList(homeList);
                    }
                });
            } else {
                db.collection("HomeList").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        Home home1 = task.getResult().toObject(Home.class);
                        homeList.add(home1);
                        recyclerAdapter.setHomeList(homeList);
                        progressBar.setVisibility(View.INVISIBLE);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

            }


        }
    }

    private void getMyHomeList() {
        progressBar.setVisibility(View.VISIBLE);
        myHomeList.clear();
        for (String id : myHomeID) {
            if (id.equals(myHomeID.get(myHomeID.size() - 1))) {

                db.collection("HomeList").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        Home home1 = task.getResult().toObject(Home.class);
                        myHomeList.add(home1);
                        recyclerAdapter.setHomeList(myHomeList);
                    }
                });
            } else {

                db.collection("HomeList").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        Home home1 = task.getResult().toObject(Home.class);
                        myHomeList.add(home1);
                        recyclerAdapter.setHomeList(myHomeList);
                        progressBar.setVisibility(View.INVISIBLE);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });


            }


        }
    }

    private void createRecyclerView() {
        recyclerView = getActivity().findViewById(R.id.recyclerViewFavorite);
        recyclerAdapter = new RecyclerAdapter();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(recyclerAdapter);

    }
}

