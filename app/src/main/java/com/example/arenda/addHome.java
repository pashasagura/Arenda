package com.example.arenda;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arenda.pogo.Home;
import com.example.arenda.pogo.UserProfile;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class addHome extends AppCompatActivity {
    FirebaseFirestore db;
    FirebaseStorage storage;
    private FirebaseAuth mAuth;
    TextView textViewRoom;
    EditText editTextRoom;
    EditText editTextAddress;
    TextView textViewAreaChange;
    EditText editTextFlor;
    EditText editTextSpace;
    EditText editTextDescription;
    EditText editTextPrice;
    ScrollView scrollView;
    Dialog dialog;
    LayoutInflater layoutInflater;
    ImageView imageView;
    GridLayout gridLayout;
    ImageView imageView2;
    ProgressBar progressBar;
    Intent intent;
    List<String> photoUri;
    StorageReference storageRef;
    List<Home> homeList;
    UserProfile userProfile;
    TextView textViewFlor;
    DocumentReference reference;


    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_home);
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        editTextAddress = findViewById(R.id.editTextPostalAddres);
        textViewAreaChange = findViewById(R.id.textViewAreaChange);
        textViewRoom = findViewById(R.id.textViewRoom);
        editTextRoom = findViewById(R.id.editTextRoom);
        editTextFlor = findViewById(R.id.editTextFloor);
        editTextSpace = findViewById(R.id.editTextSpace);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextPrice = findViewById(R.id.editTextPrice);
        textViewFlor = findViewById(R.id.textViewFloor);
        photoUri = new ArrayList<>();
        intent = getIntent();
        homeList = new ArrayList<>();
        if (intent.getStringExtra("type").equals("Комната") || intent.getStringExtra("type").equals("Студия")) {
            textViewRoom.setVisibility(View.GONE);
            editTextRoom.setVisibility(View.GONE);
        }
        if (intent.getStringExtra("type").equals("дом")) {
            editTextFlor.setVisibility(View.GONE);
            textViewFlor.setVisibility(View.GONE);

        }
        db = FirebaseFirestore.getInstance();
        db.collection("UserProfile").document(mAuth.getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                userProfile = task.getResult().toObject(UserProfile.class);
            }
        });
        reference = db.collection("UserProfile").document(mAuth.getCurrentUser().getEmail());
        textViewAreaChange = findViewById(R.id.textViewAreaChange);
        scrollView = findViewById(R.id.scrollView2);
        gridLayout = findViewById(R.id.gridLayout);
        imageView2 = findViewById(R.id.imageView5);
        layoutInflater = getLayoutInflater();
        imageView = findViewById(R.id.imageView9);
        progressBar = findViewById(R.id.progressBar2);
        createGridLayoutListener();
        createScrollViewTouchListener();


    }


    public void createDialog(View view) {
        Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();
        dialog = new Dialog(this, R.style.CustomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_change_location);
        dialog.show();
    }

    public void changeArea(View view) {
        TextView textView = (TextView) view;
        textViewAreaChange.setText(textView.getText());
        textViewAreaChange.setTextColor(Color.BLACK);
        dialog.cancel();
    }

    public void closeDialog(View view) {
        dialog.cancel();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 120 && RESULT_OK == resultCode && data != null) {
            if (data.getClipData() != null) {
                for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                    View vie = layoutInflater.inflate(R.layout.item_grid_view, gridLayout, false);
                    ImageView m = vie.findViewById(R.id.imageView5);
                    m.setImageURI(data.getClipData().getItemAt(i).getUri());
                    gridLayout.addView(vie);
                }

            } else {
                View vie = layoutInflater.inflate(R.layout.item_grid_view, gridLayout, false);
                ImageView m = vie.findViewById(R.id.imageView5);
                m.setImageURI(data.getData());
                gridLayout.addView(vie);

            }

            createGridLayoutListener();


        }
    }

    private void createGridLayoutListener() {

        int childCount = gridLayout.getChildCount();

        for (int i = 0; i < childCount; i++) {
            if (gridLayout.getChildAt(i).getId() != R.id.imageView9) {
                final ConstraintLayout container = (ConstraintLayout) gridLayout.getChildAt(i);
                container.getViewById(R.id.imageView3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gridLayout.removeView(container);
                    }
                });


            } else {
                ImageView container = (ImageView) gridLayout.getChildAt(i);
                container.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {

                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/jpeg");
                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                        startActivityForResult(intent, 120);

                    }
                });
            }

        }

    }

    @SuppressLint("ClickableViewAccessibility")
    private void createScrollViewTouchListener() {
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v);
                return false;
            }
        });

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public void back(View view) {


    }

    public void AddHomeButton(View view) {
        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        final String ID = mAuth.getCurrentUser().getEmail() + new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        if (intent.getStringExtra("type").equals("квартира")
                && !editTextAddress.getText().toString().trim().isEmpty()
                && !editTextRoom.getText().toString().trim().isEmpty()
                && !editTextFlor.getText().toString().trim().isEmpty()
                && !editTextSpace.getText().toString().trim().isEmpty()
                && !editTextDescription.getText().toString().trim().isEmpty()
                && !editTextPrice.getText().toString().trim().isEmpty()
                && !textViewAreaChange.getText().equals("Выберите район")
                && gridLayout.getChildCount() > 1) {

            Home home = new Home(intent.getStringExtra("type"),
                    editTextAddress.getText().toString().trim(),
                    textViewAreaChange.getText().toString().trim(),
                    editTextRoom.getText().toString().trim(),
                    editTextFlor.getText().toString().trim(),
                    editTextSpace.getText().toString().trim(),
                    editTextDescription.getText().toString().trim(),
                    editTextPrice.getText().toString().trim(),
                    new ArrayList<String>(),
                    date,
                    userProfile.getEmail(),
                    userProfile.getPhoneNumber(),
                    userProfile.getName(),
                    ID);
            db.collection("HomeList").document(ID).set(home).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    loadImageInStorage(ID);
                }
            });

        } else if (intent.getStringExtra("type").equals("Комната") || intent.getStringExtra("type").equals("Студия")
                && !editTextAddress.getText().toString().trim().isEmpty()
                && !editTextFlor.getText().toString().trim().isEmpty()
                && !editTextSpace.getText().toString().trim().isEmpty()
                && !editTextDescription.getText().toString().trim().isEmpty()
                && !editTextPrice.getText().toString().trim().isEmpty()
                && !textViewAreaChange.getText().equals("Выберите район")
                && gridLayout.getChildCount() > 1) {

            Home home = new Home(intent.getStringExtra("type"),
                    editTextAddress.getText().toString().trim(),
                    textViewAreaChange.getText().toString().trim(),
                    null,
                    editTextFlor.getText().toString().trim(),
                    editTextSpace.getText().toString().trim(),
                    editTextDescription.getText().toString().trim(),
                    editTextPrice.getText().toString().trim(),
                    new ArrayList<String>(),
                    date,
                    userProfile.getEmail(),
                    userProfile.getPhoneNumber(),
                    userProfile.getName(),
                    ID);
            db.collection("HomeList").document(ID).set(home).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    loadImageInStorage(ID);
                }
            });


        } else if (intent.getStringExtra("type").equals("дом")
                && !editTextAddress.getText().toString().trim().isEmpty()
                && !editTextRoom.getText().toString().trim().isEmpty()
                && !editTextSpace.getText().toString().trim().isEmpty()
                && !editTextDescription.getText().toString().trim().isEmpty()
                && !editTextPrice.getText().toString().trim().isEmpty()
                && !textViewAreaChange.getText().equals("Выберите район")
                && gridLayout.getChildCount() > 1) {

            Home home = new Home(intent.getStringExtra("type"),
                    editTextAddress.getText().toString().trim(),
                    textViewAreaChange.getText().toString().trim(),
                    editTextRoom.getText().toString().trim(),
                    null,
                    editTextSpace.getText().toString().trim(),
                    editTextDescription.getText().toString().trim(),
                    editTextPrice.getText().toString().trim(),
                    new ArrayList<String>(),
                    date,
                    userProfile.getEmail(),
                    userProfile.getPhoneNumber(),
                    userProfile.getName(),
                    ID);
            db.collection("HomeList").document(ID).set(home).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    loadImageInStorage(ID);
                }
            });


        } else {
            Toast.makeText(this, "Заполните все поля и добавьте фото", Toast.LENGTH_SHORT).show();

        }


    }

    private void loadImageInStorage(final String HomeID) {

        final DocumentReference sfDocRef = db.collection("HomeList").document(HomeID);
        for (int i = 1; i < gridLayout.getChildCount(); i++) {

            ConstraintLayout constraintLayout = (ConstraintLayout) gridLayout.getChildAt(i);
            ImageView imag = (ImageView) constraintLayout.getViewById(R.id.imageView5);
            imag.setDrawingCacheEnabled(true);
            imag.buildDrawingCache();
            final StorageReference imagesRef;
            imagesRef = storageRef.child("images/" + Calendar.getInstance().getTime().toString() + i);
            Bitmap bitmap = ((BitmapDrawable) imag.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 30, baos);
            byte[] data = baos.toByteArray();
            UploadTask uploadTask = imagesRef.putBytes(data);
            if (i != gridLayout.getChildCount() - 1) {
                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        return imagesRef.getDownloadUrl();

                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {

                            Uri downloadUri = task.getResult();
                            sfDocRef.update("photoUrl", FieldValue.arrayUnion(downloadUri.toString()));


                        }
                    }
                });
            } else {
                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        return imagesRef.getDownloadUrl();

                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {

                            Uri downloadUri = task.getResult();
                            sfDocRef.update("photoUrl", FieldValue.arrayUnion(downloadUri.toString()));
                            Toast.makeText(addHome.this, "Добавлено", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Intent intent = new Intent(addHome.this, MainActivity.class);
                            startActivity(intent);

                        }
                    }
                });
            }
            reference.update("myHome", FieldValue.arrayUnion(HomeID));

        }

    }


}




