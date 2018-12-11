package com.projects.codepractitioner.POJO;

public class Quiz {
    private QuizItems quizItems;
    private int numCorrect;
    private int numIncorrect;
    private int currentQuestion;
    private int currentAnswer;

    public Quiz(QuizItems quizItems) {
        this.quizItems = quizItems;
        this.numIncorrect = 0;
        this.numCorrect = 0;
        this.currentQuestion = 0;
        this.currentAnswer = 0;
    }

    public int getNumCorrect() {
        return numCorrect;
    }

    public int getNumIncorrect() {
        return numIncorrect;
    }

    public void addCorrect() {
        numCorrect++;
    }

    public void addIncorrect() {
        numIncorrect++;
    }

    public String getQuestion() {
        if (currentQuestion >= quizItems.getQuizItems().size()) {
            return "no more questions.";
        }
        String question = quizItems.getQuizItems().get(currentQuestion).getQuestion();
        currentQuestion++;
        return question;
    }

    public String getAnswer() {
        if (currentAnswer >= quizItems.getQuizItems().size()) {
            return "no more answers.";
        }
        String answer = quizItems.getQuizItems().get(currentAnswer).getAnswer();
        currentAnswer++;
        return answer;
    }
}
