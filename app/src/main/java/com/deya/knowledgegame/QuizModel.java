package com.deya.knowledgegame;

public class QuizModel{
    private final String question;
    private final boolean answer;
    private final String aboutURL;
    public QuizModel(String question, boolean answer, String aboutURL){
        this.question = question;
        this.answer = answer;
        this.aboutURL = aboutURL;
    }

    public boolean getAnswer() {
        return answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAboutURL() {
        return aboutURL;
    }

}
