package com.example.arenda.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.arenda.DetailHomesActivity;
import com.example.arenda.pogo.Home;
import com.example.arenda.R;
import com.example.arenda.adapters.RecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private FirebaseFirestore db;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private HorizontalScrollView horizontalScrollView;
    private List<Home> homeList;
    private SwipeRefreshLayout swipeContainer;
    private BottomNavigationView bottomNavigationView;
    private View view1;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();


    }

    @Nullable
    @Override
    public View getView() {
        return super.getView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view1==null){
            view1 =inflater.inflate(R.layout.fragment_main, container, false);
        }

        return view1;
    }

    @Override
    public void onStart() {
        super.onStart();
        bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
        progressBar = getActivity().findViewById(R.id.progressBarFavorite);
        Toast.makeText(getContext(), Integer.toString(getActivity().getSupportFragmentManager().getBackStackEntryCount()), Toast.LENGTH_SHORT).show();
        if (adapter == null || recyclerView == null) {
            createRecyclerView();
        }

        createRecyclerViewListener();
        horizontalScrollView = getActivity().findViewById(R.id.horizontalScrollView);
        swipeContainer = getActivity().findViewById(R.id.swipeContainerMa);
        mAuth = FirebaseAuth.getInstance();
        createSwipeContainerListener();


    }


    private void createSwipeContainerListener() {
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                createRecyclerView();
                createRecyclerViewListener();
            }
        });
    }

    private void createRecyclerViewListener() {
        adapter.setClickListener(new RecyclerAdapter.ClickListener() {
            @Override
            public void clickListener(int position) {
                Intent intent = new Intent(getActivity(), DetailHomesActivity.class);
                intent.putExtra("1", homeList.get(position).getAddress());
                intent.putExtra("2", homeList.get(position).getArea());
                intent.putExtra("3", homeList.get(position).getDate());
                intent.putExtra("4", homeList.get(position).getDescription());
                intent.putExtra("5", homeList.get(position).getFlor());
                intent.putExtra("6", homeList.get(position).getPrice());
                intent.putExtra("7", homeList.get(position).getRoom());
                intent.putExtra("8", homeList.get(position).getSq());
                intent.putExtra("9", homeList.get(position).getType());
                intent.putStringArrayListExtra("10", (ArrayList<String>) homeList.get(position).getPhotoUrl());
                intent.putExtra("11", homeList.get(position).getEmail());
                intent.putExtra("12", homeList.get(position).getName());
                intent.putExtra("13", homeList.get(position).getPhoneNumber());
                intent.putExtra("14", homeList.get(position).getID());
                startActivity(intent);
            }
        });

    }

    private void createRecyclerView() {
        recyclerView = getActivity().findViewById(R.id.recyclerViewFavorite);
        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        getHomeList();


    }


    private void getHomeList() {
        homeList = new ArrayList<>();
        progressBar.setVisibility(View.VISIBLE);
        db.collection("HomeList").orderBy("date").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            homeList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Home home = document.toObject(Home.class);
                                homeList.add(home);
                            }
                            adapter.setHomeList(homeList);
                            progressBar.setVisibility(View.INVISIBLE);
                            swipeContainer.setRefreshing(false);
                        } else {

                        }
                    }
                });
    }


}
