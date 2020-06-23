package com.example.arenda;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arenda.adapters.PagerViewAdapter;
import com.example.arenda.pogo.Home;
import com.example.arenda.pogo.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;

public class DetailHomesActivity extends AppCompatActivity {
    Intent intent;
    ArrayList<String> homeList;
    TextView textViewCount;
    TextView textViewAll;
    TextView textViewType;
    TextView textViewPrice;
    TextView textViewArea;
    TextView textViewAddress;
    TextView textViewDescription;
    TextView textViewUserName;
    TextView textViewUserEmail;
    String type;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    UserProfile userProfile;
    List<String> favoritesHome;
    DocumentReference sfDocRef;
    ImageView imageViewFavorite;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_homes);
        intent = getIntent();
        favoritesHome = new ArrayList<>();
        textViewCount = findViewById(R.id.textViewCount);
        textViewAll = findViewById(R.id.textViewAll);
        textViewType = findViewById(R.id.textViewDetailType);
        textViewPrice = findViewById(R.id.textViewDetailPrice);
        textViewArea = findViewById(R.id.textViewDetailArea);
        textViewAddress = findViewById(R.id.textViewDetailAddres);
        textViewDescription = findViewById(R.id.textViewDetailDescription);
        textViewUserName = findViewById(R.id.textViewNameUser);
        textViewUserEmail = findViewById(R.id.textViewEmailUser);
        imageViewFavorite = findViewById(R.id.imageViewAddToFavorite);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        if (intent.getStringExtra("7") != null && intent.getStringExtra("5") != null) {
            type = intent.getStringExtra("7") + "-к " + intent.getStringExtra("9") + ", " + intent.getStringExtra("8") + " м², " + intent.getStringExtra("5") + " этаж";
        } else if (intent.getStringExtra("7") == null) {
            type = intent.getStringExtra("9") + ", " + intent.getStringExtra("8") + " м², " + intent.getStringExtra("5") + " этаж";

        } else if (intent.getStringExtra("5") == null) {
            type = intent.getStringExtra("7") + "-к " + intent.getStringExtra("9") + ", " + intent.getStringExtra("8") + " м²";
        }

        homeList = intent.getStringArrayListExtra("10");
        ViewPager viewPager = findViewById(R.id.ViewPager);
        PagerViewAdapter adapter = new PagerViewAdapter(homeList);
        viewPager.setAdapter(adapter);
        textViewAll.setText(Integer.toString(homeList.size()));
        textViewCount.setText(Integer.toString(1) + " - ");
        textViewType.setText(type);
        textViewPrice.setText(intent.getStringExtra("6") + " ₽ в месяц");
        textViewArea.setText(intent.getStringExtra("2"));
        textViewAddress.setText(intent.getStringExtra("1"));
        textViewDescription.setText(intent.getStringExtra("4"));
        textViewUserName.setText(intent.getStringExtra("12"));
        textViewUserEmail.setText(intent.getStringExtra("11"));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                textViewAll.setText(Integer.toString(homeList.size()));
                textViewCount.setText(Integer.toString(position + 1) + " - ");
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(0);
        sfDocRef = db.collection("UserProfile").document(mAuth.getCurrentUser().getEmail());
        createSnapshotListener();


    }

    public void back(View view) {
        onBackPressed();

    }

    public void Call(View view) {
        Intent intentCall = new Intent(Intent.ACTION_DIAL);
        intentCall.setData(Uri.parse("tel:" + intent.getStringExtra("13")));
        startActivity(intentCall);
    }

    public void AddToFavorite(View view) {
        boolean low = true;

        for (String homeId : favoritesHome) {

            if (homeId != null) {
                Log.i("test", homeId);
            }
            if (homeId.equals(intent.getStringExtra("14"))) {
                low = false;
            }


        }
        if (low) {

            sfDocRef.update("favoritesHome", FieldValue.arrayUnion(intent.getStringExtra("14")));

            imageViewFavorite.setImageResource(R.drawable.icon_delete_to_favorite);

        } else {
            sfDocRef.update("favoritesHome", FieldValue.arrayRemove(intent.getStringExtra("14")));

            imageViewFavorite.setImageResource(R.drawable.icon_add_to_favorite);
        }
    }

    private void FavoriteOrNot() {
        boolean low = false;

        for (String homeId : favoritesHome) {


            if (homeId.equals(intent.getStringExtra("14"))) {
                low = true;
            }


        }
        if (low) {

            imageViewFavorite.setImageResource(R.drawable.icon_delete_to_favorite);

        }
    }

    private  void createSnapshotListener() {
        sfDocRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {

                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    userProfile = snapshot.toObject(UserProfile.class);
                    if (userProfile.getFavoritesHome() != null) {
                        favoritesHome = userProfile.getFavoritesHome();
                        FavoriteOrNot();
                    }
                }
            }
        });

    }


}
