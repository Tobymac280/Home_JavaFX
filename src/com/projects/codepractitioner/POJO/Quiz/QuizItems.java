package com.projects.codepractitioner.POJO.Quiz;

import java.util.ArrayList;

public class QuizItems {
    private ArrayList<QuizItem> quizItems;

    public QuizItems() {
        quizItems = new ArrayList<>();
    }

    public ArrayList<QuizItem> getQuizItems() {
        return quizItems;
    }

    public void setQuizItems(ArrayList<QuizItem> items) {
        quizItems = items;
    }
}
