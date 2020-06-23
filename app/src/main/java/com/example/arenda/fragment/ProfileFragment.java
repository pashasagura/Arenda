package com.example.arenda.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arenda.R;
import com.example.arenda.adapters.RecyclerAdapter;
import com.example.arenda.pogo.Home;
import com.example.arenda.pogo.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private TextView textViewProfileName;
    private TextView getTextViewProfileEmail;
    private TextView textTopBar1;
    private TextView textTopBar2;
    BottomNavigationView bottomNavigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        textViewProfileName = getActivity().findViewById(R.id.textViewNameProfile);
        getTextViewProfileEmail = getActivity().findViewById(R.id.textViewEmailProfile);
        textTopBar1 = getActivity().findViewById(R.id.textViewTopBar1);
        textTopBar2 = getActivity().findViewById(R.id.textViewTopBar2);
        bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
        bottomNavigationView.getMenu().getItem(3).setChecked(true);

        textTopBar1.setText("Личный кабинет");
        textTopBar2.setVisibility(View.VISIBLE);
        textTopBar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                bottomNavigationView.setSelectedItemId(R.id.action_navigation2);
            }
        });
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        getUserInfo();

    }

    private void getUserInfo() {
        DocumentReference docRef = db.collection("UserProfile").document(mAuth.getCurrentUser().getEmail());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                UserProfile user = document.toObject(UserProfile.class);
                Toast.makeText(getContext(), "Привет" + user.getName(), Toast.LENGTH_SHORT).show();
                textViewProfileName.setText(user.getName() + " "+ user.getSecondName());
                getTextViewProfileEmail.setText(user.getEmail());

            }
        });
    }

}