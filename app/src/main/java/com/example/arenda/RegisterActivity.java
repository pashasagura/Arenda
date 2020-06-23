package com.example.arenda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.arenda.pogo.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {
    EditText editTextName;
    EditText editTextSecondName;
    EditText editTextPhoneNumber;
    EditText editTextEmail;
    EditText editTextPassword;
    EditText editTextPasswordAgain;
    Button buttonRegister;
    private FirebaseAuth mAuth;
    private ConstraintLayout constraintLayout;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        editTextName = findViewById(R.id.editTextName);
        editTextSecondName = findViewById(R.id.editTextSecondName);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextPasswordAgain = findViewById(R.id.editTextPasswordAgain);
        buttonRegister = findViewById(R.id.buttonRegister);
        constraintLayout = findViewById(R.id.consLayoutRegisterActivity);
        ConstLayoutClickListener();
        db = FirebaseFirestore.getInstance();
    }

    public void RegisterClick(View view) {
        if(!editTextName.getText().toString().trim().isEmpty()
                && !editTextSecondName.getText().toString().trim().isEmpty()
                && !editTextPhoneNumber.getText().toString().trim().isEmpty()
                && !editTextEmail.getText().toString().trim().isEmpty()
                && !editTextPassword.getText().toString().trim().isEmpty()
                && !editTextPasswordAgain.getText().toString().trim().isEmpty())
        {
            if (editTextPassword.getText().toString().trim().equals(editTextPasswordAgain.getText().toString().trim()))
            {
                mAuth.createUserWithEmailAndPassword(editTextEmail.getText().toString().trim(), editTextPassword.getText().toString().trim())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Успешно",
                                            Toast.LENGTH_SHORT).show();
                                    UserProfile userProfile = new UserProfile(
                                            editTextName.getText().toString().trim(),
                                            editTextSecondName.getText().toString().trim(),
                                            editTextPhoneNumber.getText().toString().trim(),
                                            editTextEmail.getText().toString().trim(),
                                            new ArrayList<String>(),
                                            new ArrayList<String>());

                                    db.collection("UserProfile").document(editTextEmail.getText().toString().trim()).set(userProfile);

                                } else {

                                    Toast.makeText(RegisterActivity.this, task.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();

                                }

                            }
                        });



            }
            else {
                Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
            }
        }

        else {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private  void ConstLayoutClickListener() {
        constraintLayout.setOnTouchListener(new View.OnTouchListener() {
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
}


