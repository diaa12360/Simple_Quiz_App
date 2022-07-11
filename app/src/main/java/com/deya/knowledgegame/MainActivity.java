package com.deya.knowledgegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        start.setOnClickListener(view -> {
            Intent in = new Intent(this, QuizActivity.class);
            startActivity(in);
        });
    }
    private void initView(){
        start = findViewById(R.id.button);
    }
    private boolean backTwo = false;
    @Override
    public void onBackPressed() {
        if(backTwo)
            finish();
        else {
            backTwo = true;
            Toast.makeText(this, "click again", Toast.LENGTH_SHORT).show();
        }
    }
}