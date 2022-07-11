package com.deya.knowledgegame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class AnswerQuestion extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_question);
        WebView webView = findViewById(R.id.web_view);
        webView.loadUrl(QuizActivity.URL);
        WebSettings ws = webView.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setLoadWithOverviewMode(true);
        MaterialButton back = findViewById(R.id.back_btn_web);
        back.setOnClickListener(view -> finish());
    }
    boolean backTwo = false;
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