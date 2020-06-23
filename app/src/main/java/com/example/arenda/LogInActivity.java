package com.example.arenda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText editTextEmail;
    EditText editTextPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.editTextLogInEmail);
        editTextPassword = findViewById(R.id.editText2);

    }


    public void Register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);

    }


    public void LogIn(View view) {
        if(!editTextEmail.getText().toString().trim().isEmpty() && !editTextPassword.getText().toString().trim().isEmpty()){
        mAuth.signInWithEmailAndPassword(editTextEmail.getText().toString().trim(), editTextPassword.getText().toString().trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LogInActivity.this, "Успешно", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LogInActivity.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();

                        }


                    }
                });}
        else {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
        }
    }
}
