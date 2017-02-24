package com.example.dioncamposano17.loveadvice20.assessment;

/**
 * Created by dioncamposano17 on 06/02/2017.
 */

public class AssessmentWrapper {

    private int id;
    private String question;
    private String answers;
    private int correctAnswer;

    public AssessmentWrapper(int id, String question, String answers, int correctAnswer) {
        this.id = id;
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }
    public String getAnswers() {
        return answers;
    }
    public void setAnswers(String answers) {
        this.answers = answers;
    }
    public int getCorrectAnswer() {
        return correctAnswer;
    }
    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

}
