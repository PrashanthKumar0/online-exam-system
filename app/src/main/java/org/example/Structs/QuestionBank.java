package org.example.Structs;

import java.util.ArrayList;

public class QuestionBank {
    private ArrayList<Question> questions = new ArrayList<Question>();

    public void addQuestion(Question question){
        questions.add(question);
    }
    
    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void print(){
        for(Question question : questions) {
            System.out.println("-------------------------------------");
            System.out.println("Q : "+question.getQuestion());
            for(Option option :  question.getOptions()) {
                if(option.getOptionType() == EOptionType.CORRECT) {
                    System.out.print("C : ");
                }else{
                    System.out.print("W : ");
                }
                System.out.println(option.getOption());
            }
        }
    }
}
