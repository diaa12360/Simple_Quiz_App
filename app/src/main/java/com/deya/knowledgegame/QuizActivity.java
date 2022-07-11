package com.deya.knowledgegame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class QuizActivity extends AppCompatActivity {
    // Views :
    private MaterialButton trueBtn;
    private MaterialButton falseBtn;
    private MaterialButton about;
    private MaterialButton next;
    private MaterialButton back;
    private TextView question;
    private TextView countTv;
    private TextView resultTv;
    // Quiz Modeling :
    public static ArrayList<QuizModel> quiz = new ArrayList<>();

    private static int indexOfQuestion = 0; // for the index of the current Question
    private static int trueAnswers = 0;     // for counting the true answers
    private int[] index;                    // for store the random Indices
    protected static String URL;            // for store the URL of the current Question
    private boolean answer;                 // to check the answer


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        initView();
        addQuestions();
        generateQuiz();
        next.setOnClickListener(view -> generateQuiz());
        about.setOnClickListener(view -> getAnswer());
        back.setOnClickListener(view -> finish());
    }

    /*
     * this method initialize the models
     * we can call it in onCreate Method to create all or some models
     */
    private void initView() {
        trueBtn = findViewById(R.id.true_btn);
        next = findViewById(R.id.next_btn);
        falseBtn = findViewById(R.id.false_btn);
        about = findViewById(R.id.about_btn);
        question = findViewById(R.id.question_tv);
        resultTv = findViewById(R.id.result_tv);
        countTv = findViewById(R.id.count_tv);
        back = findViewById(R.id.back_btn);
        back.setVisibility(View.GONE);
        indexOfQuestion = 0;
        trueAnswers = 0;

    }

    /*
     * this method to listener of the trueBtn
     */
    @SuppressLint({"UseCompatLoadingForDrawables", "UseCompatLoadingForColorStateLists"})
    private void trueOnClick() {
        next.setVisibility(View.VISIBLE);
        about.setVisibility(View.VISIBLE);
        trueBtn.setIcon(getResources().getDrawable(R.drawable.true_green));
        if (answer) {
            trueAnswers++;
        } else {
            falseBtn.setIcon(getResources().getDrawable(R.drawable.false_red));
            trueBtn.setIconTint(getResources().getColorStateList(R.color.red_ryb));
        }
        trueBtn.setOnClickListener(null);
        falseBtn.setOnClickListener(null);
    }

    /*
     * this method to listener of the falseBtn
     */
    @SuppressLint({"UseCompatLoadingForDrawables", "UseCompatLoadingForColorStateLists"})
    private void falseOnClick() {
        next.setVisibility(View.VISIBLE);
        about.setVisibility(View.VISIBLE);
        falseBtn.setIcon(getResources().getDrawable(R.drawable.false_red));
        if (!answer) {
            trueAnswers++;
        } else {
            trueBtn.setIcon(getResources().getDrawable(R.drawable.true_green));
            falseBtn.setIconTint(getResources().getColorStateList(R.color.red_ryb));
        }
        trueBtn.setOnClickListener(null);
        falseBtn.setOnClickListener(null);
    }

    @SuppressLint({"ResourceAsColor", "SetTextI18n", "UseCompatLoadingForDrawables", "UseCompatLoadingForColorStateLists"})
    private void generateQuiz() {
        next.setVisibility(View.GONE);
        resultTv.setVisibility(View.GONE);
        if (indexOfQuestion >= quiz.size()) {
            resultTv.setTextSize(30);
            trueBtn.setVisibility(View.GONE);
            falseBtn.setVisibility(View.GONE);
            resultTv.setVisibility(View.VISIBLE);
            if (trueAnswers > indexOfQuestion / 2) {
                resultTv.setText("Finish your result is : " + trueAnswers + "/" + quiz.size() + "\nYou WIN!!");
                resultTv.setTextColor(getResources().getColor(R.color.glaucous));
            } else {
                resultTv.setText("Finish your result is : " + trueAnswers + "/" + quiz.size() + "\nYou LOSE !!");
                resultTv.setTextColor(getResources().getColor(R.color.red_ryb));
            }
            trueBtn.setEnabled(false);
            falseBtn.setEnabled(false);
            question.setVisibility(View.INVISIBLE);
            countTv.setVisibility(View.INVISIBLE);
            back.setVisibility(View.VISIBLE);
            about.setText("See All Q&A!");
            about.setOnClickListener(view -> Toast.makeText(this, "Not Ready", Toast.LENGTH_LONG).show());
            return;
        }
        QuizModel q = quiz.get(index[indexOfQuestion++]);
        question.setText(q.getQuestion());
        answer = q.getAnswer();
        trueBtn.setOnClickListener(view -> trueOnClick());
        falseBtn.setOnClickListener(view -> falseOnClick());
        trueBtn.setIcon(getResources().getDrawable(R.drawable.true_outline));
        falseBtn.setIcon(getResources().getDrawable(R.drawable.false_outline));
        trueBtn.setIconTint(getResources().getColorStateList(R.color.glaucous));
        falseBtn.setIconTint(getResources().getColorStateList(R.color.glaucous));
        countTv.setText((indexOfQuestion) + "/" + (quiz.size()));
        about.setVisibility(View.GONE);

    }
    // Read Questions from file and add it to the arrayList
    private void addQuestions() {
        quiz.clear();
        try {
            InputStream fr = getAssets().open("questions_and_answers.txt");
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(fr));
            int n = Integer.parseInt(fileReader.readLine());
            for (int i = 0; i < n; i++) {
                quiz.add(new QuizModel(fileReader.readLine(), Boolean.parseBoolean(fileReader.readLine()), fileReader.readLine()));
            }
        } catch (IOException e) {
            Toast.makeText(this, "Line 154", Toast.LENGTH_LONG).show();
            System.out.println(e.getMessage());
        }
        index = generateRandomIndices(quiz.size());
    }

    // generate random numbers and store it in array, that is uses to random the questions
    private int[] generateRandomIndices(int n) {
        Random randNum = new Random();
        Set<Integer> set = new LinkedHashSet<>();
        while (set.size() < n) {
            set.add(randNum.nextInt(n));
        }
        int[] arr = new int[n];
        Iterator<Integer> is = set.iterator();
        for (int i = 0; i < n; i++) {
            arr[i] = is.next();
        }
        return arr;
    }

    private void getAnswer() {
        Intent in = new Intent(this, AnswerQuestion.class);
        startActivity(in);
        URL = quiz.get(index[indexOfQuestion - 1]).getAboutURL();
    }

    boolean backTwo = false;

    @Override
    public void onBackPressed() {
        if (backTwo)
            finish();
        else {
            backTwo = true;
            Toast.makeText(this, "click again", Toast.LENGTH_SHORT).show();
        }

    }
}
