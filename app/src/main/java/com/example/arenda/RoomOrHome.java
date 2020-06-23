package com.example.arenda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

public class RoomOrHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_or_home);

    }

    public void back(View view) {
        onBackPressed();
    }

    public void nextStep(View view) {
        Intent intent = new Intent(this, addHome.class);
        String type = null;
        switch (view.getId()){
            case R.id.button5:
                type = "квартира";
                break;
            case R.id.button:
                type = "Комната";
                break;
            case R.id.button7:
                type ="дом";
                break;
            case R.id.button2:
                type = "Студия";
                break;
        }

        intent.putExtra("type", type);
        startActivity(intent);
    }
}
