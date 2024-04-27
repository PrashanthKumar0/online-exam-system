package org.example.Structs;

import java.util.ArrayList;

public class Question {
    private String question = "";
    private ArrayList<Option> options = new ArrayList<Option>();
    private int num_correct_opts = 0;

    public Question() {
    }

    public Question(String question, ArrayList<Option> options) {
        this.question = question;
        this.options = options;
    }

    public void addOption(String option, EOptionType option_type) {
        if(option_type==EOptionType.CORRECT) num_correct_opts++;
        options.add(new Option(option, option_type));
    }

    public ArrayList<Option> getOptions() {
        return options;
    }

    public boolean isMultipleChoice() {
        return num_correct_opts > 1;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }
}
